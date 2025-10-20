/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.datav.dashboard.web;

public class WidgetRenderDTO {
    private String widgetId;
    private String widgetModel;
    private String sessionId;
    private String linkMsg;
    private boolean renderParams;
    private boolean isDrillMode;
    private boolean updateCache;
    private String drillInfo;
    private boolean forceUpdateDataset;

    public String getWidgetId() {
        return this.widgetId;
    }

    public void setWidgetId(String widgetId) {
        this.widgetId = widgetId;
    }

    public String getWidgetModel() {
        return this.widgetModel;
    }

    public void setWidgetModel(String widgetModel) {
        this.widgetModel = widgetModel;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getLinkMsg() {
        return this.linkMsg;
    }

    public void setLinkMsg(String linkMsg) {
        this.linkMsg = linkMsg;
    }

    public boolean isRenderParams() {
        return this.renderParams;
    }

    public void setRenderParams(boolean renderParams) {
        this.renderParams = renderParams;
    }

    public boolean isDrillMode() {
        return this.isDrillMode;
    }

    public void setDrillMode(boolean drillMode) {
        this.isDrillMode = drillMode;
    }

    public boolean isUpdateCache() {
        return this.updateCache;
    }

    public void setUpdateCache(boolean updateCache) {
        this.updateCache = updateCache;
    }

    public String getDrillInfo() {
        return this.drillInfo;
    }

    public void setDrillInfo(String drillInfo) {
        this.drillInfo = drillInfo;
    }

    public boolean isForceUpdateDataset() {
        return this.forceUpdateDataset;
    }

    public void setForceUpdateDataset(boolean forceUpdateDataset) {
        this.forceUpdateDataset = forceUpdateDataset;
    }
}

