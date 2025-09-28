package com.infina.webhook.service;


import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@WebServlet("/list-webhooks")
public class WebhookListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        StringBuilder json = new StringBuilder();
        json.append("[");

        boolean first = true;
        for (Map.Entry<String, String> entry : WebhookUrlCache.getAll().entrySet()) {
            if (!first) json.append(",");
            first = false;
            json.append("{")
                    .append("\"name\":\"").append(entry.getKey()).append("\",")
                    .append("\"url\":\"").append(maskUrl(entry.getValue())).append("\"")
                    .append("}");
        }

        json.append("]");
        resp.getWriter().write(json.toString());
    }

    private String maskUrl(String url) {
        if (url.length() < 8) return "******";
        return url.substring(0, 8) + "*****" + url.substring(url.length() - 5);
    }
}