package com.infina.webhook.service;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/set-webhook")
public class WebhookSetServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String url = req.getParameter("url");
        String name = req.getParameter("name");
        WebhookUrlCache.set(name, url);
        resp.getWriter().write("Webhook URL set edildi: " + url);
    }
}
