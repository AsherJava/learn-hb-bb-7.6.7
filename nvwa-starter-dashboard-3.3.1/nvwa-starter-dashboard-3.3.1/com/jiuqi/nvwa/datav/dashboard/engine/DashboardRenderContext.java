/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.datav.dashboard.engine;

import java.io.Serializable;

public class DashboardRenderContext
implements Serializable {
    private String themeId;
    private String userGuid;
    private String language;
    private String sessionId;

    public DashboardRenderContext(String themeId, String userGuid, String language, String sessionId) {
        this.themeId = themeId;
        this.userGuid = userGuid;
        this.language = language;
        this.sessionId = sessionId;
    }

    public String getThemeId() {
        return this.themeId;
    }

    public void setThemeId(String themeId) {
        this.themeId = themeId;
    }

    public String getUserGuid() {
        return this.userGuid;
    }

    public void setUserGuid(String userGuid) {
        this.userGuid = userGuid;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}

