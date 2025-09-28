package com.infina.webhook.service.enums;
public enum JiraEventEnum {

    // Issue Events
    ISSUE_CREATED("jira:issue_created", "🆕 Yeni issue oluşturuldu: *%s* - %s _(by %s)_", EventCategory.ISSUE),
    ISSUE_UPDATED("jira:issue_updated", "✏️ Issue güncellendi: *%s* - %s _(by %s)_", EventCategory.ISSUE),
    ISSUE_DELETED("jira:issue_deleted", "❌ Issue silindi: *%s*", EventCategory.ISSUE),
    ISSUE_MOVED("jira:issue_moved", "📦 Issue başka projeye taşındı: *%s*", EventCategory.ISSUE),

    // Comment Events
    COMMENT_CREATED("comment_created", "💬 Yorum eklendi: *%s* - %s _(by %s)_", EventCategory.COMMENT),
    COMMENT_UPDATED("comment_updated", "✏️ Yorum güncellendi: *%s* - %s _(by %s)_", EventCategory.COMMENT),
    COMMENT_DELETED("comment_deleted", "🗑️ Yorum silindi: *%s*", EventCategory.COMMENT),

    // Worklog Events
    WORKLOG_CREATED("jira:worklog_created", "🕒 Worklog eklendi: *%s*", EventCategory.WORKLOG),
    WORKLOG_UPDATED("jira:worklog_updated", "📝 Worklog güncellendi: *%s*", EventCategory.WORKLOG),
    WORKLOG_DELETED("jira:worklog_deleted", "🗑️ Worklog silindi: *%s*", EventCategory.WORKLOG),

    // Project / Version Events
    PROJECT_CREATED("jira:project_created", "🛠️ Yeni proje oluşturuldu: *%s*", EventCategory.PROJECT),
    PROJECT_UPDATED("project_updated", "🔄 Proje güncellendi: *%s*", EventCategory.PROJECT),
    PROJECT_DELETED("project_deleted", "🗑️ Proje silindi: *%s*", EventCategory.PROJECT),

    VERSION_CREATED("jira:version_created", "🧩 Versiyon oluşturuldu: *%s*", EventCategory.VERSION),
    VERSION_UPDATED("jira:version_updated", "✏️ Versiyon güncellendi: *%s*", EventCategory.VERSION),
    VERSION_RELEASED("jira:version_released", "🚀 Versiyon release edildi: *%s*", EventCategory.VERSION),
    VERSION_UNRELEASED("jira:version_unreleased", "🔙 Versiyon release kaldırıldı: *%s*", EventCategory.VERSION),
    VERSION_DELETED("jira:version_deleted", "🗑️ Versiyon silindi: *%s*", EventCategory.VERSION),

    // User / Group Events
    USER_CREATED("user_created", "👤 Kullanıcı oluşturuldu: *%s*", EventCategory.USER),
    USER_UPDATED("user_updated", "✏️ Kullanıcı güncellendi: *%s*", EventCategory.USER),
    USER_DELETED("user_deleted", "🗑️ Kullanıcı silindi: *%s*", EventCategory.USER),

    GROUP_CREATED("group_created", "👥 Grup oluşturuldu: *%s*", EventCategory.GROUP),
    GROUP_DELETED("group_deleted", "🗑️ Grup silindi: *%s*", EventCategory.GROUP),

    // Attachment
    ATTACHMENT_CREATED("jira:attachment_created", "📎 Dosya eklendi: *%s*", EventCategory.ATTACHMENT),
    ATTACHMENT_DELETED("jira:attachment_deleted", "🗑️ Dosya silindi: *%s*", EventCategory.ATTACHMENT),

    // Sprint / Board Events
    SPRINT_STARTED("sprint_started", "🏁 Sprint başladı: *%s*", EventCategory.SPRINT),
    SPRINT_CLOSED("sprint_closed", "🏁 Sprint kapandı: *%s*", EventCategory.SPRINT),
    SPRINT_UPDATED("sprint_updated", "🔄 Sprint güncellendi: *%s*", EventCategory.SPRINT),
    BOARD_CREATED("board_created", "📋 Board oluşturuldu: *%s*", EventCategory.BOARD),

    // Fallback
    UNKNOWN("unknown", "⚠️ Bilinmeyen etkinlik: %s", EventCategory.UNKNOWN);

    private final String eventName;
    private final String template;
    private final EventCategory category;

    JiraEventEnum(String eventName, String template, EventCategory category) {
        this.eventName = eventName;
        this.template = template;
        this.category = category;
    }

    public String getEventName() {
        return eventName;
    }

    public String getTemplate() {
        return template;
    }

    public EventCategory getCategory() {
        return category;
    }

    public static JiraEventEnum from(String eventName) {
        for (JiraEventEnum event : values()) {
            if (event.eventName.equalsIgnoreCase(eventName)) {
                return event;
            }
        }
        return UNKNOWN;
    }
}