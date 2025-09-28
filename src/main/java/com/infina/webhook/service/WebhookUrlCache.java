package com.infina.webhook.service;

import java.util.HashMap;

public class WebhookUrlCache {
    private static HashMap<String, String> webhookUrls;

    private WebhookUrlCache() {
    }

    public static String get(String name) {
        return webhookUrls.get(name);
    }

    public static void set(String name, String url) {
        webhookUrls.put(name, url);
    }

    public static boolean isSet(String name) {
        return webhookUrls.containsKey(name);
    }
}
