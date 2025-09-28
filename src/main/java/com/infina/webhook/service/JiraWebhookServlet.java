package com.infina.webhook.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.infina.webhook.service.enums.JiraEventEnum;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/jira-webhook")
public class JiraWebhookServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        StringBuilder jsonBuffer = new StringBuilder();

        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuffer.append(line);
            }
        }

        try {
            JsonObject body = JsonParser.parseString(jsonBuffer.toString()).getAsJsonObject();

            String incomingEvent = body.has("webhookEvent") ? body.get("webhookEvent").getAsString() : "unknown";
            JiraEventEnum event = JiraEventEnum.from(incomingEvent);

            JsonObject issue = body.has("issue") ? body.getAsJsonObject("issue") : null;
            String issueKey = issue != null && issue.has("key") ? issue.get("key").getAsString() : "N/A";
            String summary = issue != null && issue.has("fields") && issue.getAsJsonObject("fields").has("summary")
                    ? issue.getAsJsonObject("fields").get("summary").getAsString()
                    : "No summary";

            JsonObject user = body.has("user") ? body.getAsJsonObject("user") : null;
            String displayName = user != null && user.has("displayName")
                    ? user.get("displayName").getAsString()
                    : "Unknown user";

            String name = req.getParameter("name");

            String message = String.format(event.getTemplate(), issueKey, summary, displayName);
            sendToTeams(message, name);
            resp.setStatus(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        resp.getWriter().write("Lütfen POST isteği gönderin. GET desteklenmiyor.");
    }


    private void sendToTeams(String messageText, String name) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            String url = WebhookUrlCache.get(name);
            HttpPost post = new HttpPost(url);
            post.setHeader("Content-Type", "application/json");

            String payload = String.format("{\"text\":\"%s\"}", escapeJson(messageText));
            post.setEntity(new StringEntity(payload, "UTF-8"));

            client.execute(post);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String escapeJson(String text) {
        return text.replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }
}
