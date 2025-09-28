package com.infina.webhook.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/set-webhook")
public class WebhookSetServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        StringBuilder jsonBuffer = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuffer.append(line);
            }
        }

        JsonObject body = JsonParser.parseString(jsonBuffer.toString()).getAsJsonObject();
        String name = body.get("name").getAsString();
        String url = body.get("url").getAsString();

        WebhookUrlCache.set(name, url);
        resp.getWriter().write("Webhook URL set edildi: " + url);
    }
}
