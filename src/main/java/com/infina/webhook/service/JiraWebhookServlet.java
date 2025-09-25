package com.infina.webhook.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
    private static final String TEAMS_WEBHOOK_URL = "https://outlook.office.com/webhook/xxx";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Body oku
        StringBuilder jsonBuffer = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuffer.append(line);
            }
        }

        JsonObject body = JsonParser.parseString(jsonBuffer.toString()).getAsJsonObject();
        JsonObject issue = body.getAsJsonObject("issue");
        String issueKey = issue.get("key").getAsString();
        String summary = issue.getAsJsonObject("fields").get("summary").getAsString();

        String message = String.format("📢 Issue `%s - %s` güncellendi!", issueKey, summary);

        sendToTeams(message);
        resp.setStatus(200);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        resp.getWriter().write("Lütfen POST isteği gönderin. GET desteklenmiyor.");
    }
    private void sendToTeams(String messageText) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(TEAMS_WEBHOOK_URL);
            post.setHeader("Content-Type", "application/json");

            String payload = String.format("{\"text\":\"%s\"}", messageText);
            post.setEntity(new StringEntity(payload, "UTF-8"));

            client.execute(post);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
