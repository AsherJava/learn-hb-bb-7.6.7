/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.datav.dashboard.web;

public class DashboardRenderDTO {
    private String dashboardGuid;
    private String dashboardModel;
    private String widgetIds;
    private String sessionId;
    private boolean preview;
    private String linkMsg;

    public String getDashboardGuid() {
        return this.dashboardGuid;
    }

    public void setDashboardGuid(String dashboardGuid) {
        this.dashboardGuid = dashboardGuid;
    }

    public String getDashboardModel() {
        return this.dashboardModel;
    }

    public void setDashboardModel(String dashboardModel) {
        this.dashboardModel = dashboardModel;
    }

    public String getWidgetIds() {
        return this.widgetIds;
    }

    public void setWidgetIds(String widgetIds) {
        this.widgetIds = widgetIds;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public boolean isPreview() {
        return this.preview;
    }

    public void setPreview(boolean preview) {
        this.preview = preview;
    }

    public String getLinkMsg() {
        return this.linkMsg;
    }

    public void setLinkMsg(String linkMsg) {
        this.linkMsg = linkMsg;
    }
}

