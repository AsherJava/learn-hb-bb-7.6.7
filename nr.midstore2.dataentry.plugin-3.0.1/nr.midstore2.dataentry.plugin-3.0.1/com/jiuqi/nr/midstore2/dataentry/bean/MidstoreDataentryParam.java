/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.midstore2.dataentry.bean;

public class MidstoreDataentryParam {
    private String taskKey;
    private String period;
    private String midstoreCodes;
    private boolean pullData;
    private String formScheme;
    private String entityId;

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getMidstoreCodes() {
        return this.midstoreCodes;
    }

    public void setMidstoreCodes(String midstoreCodes) {
        this.midstoreCodes = midstoreCodes;
    }

    public boolean isPullData() {
        return this.pullData;
    }

    public void setPullData(boolean pullData) {
        this.pullData = pullData;
    }

    public String getFormScheme() {
        return this.formScheme;
    }

    public void setFormScheme(String formScheme) {
        this.formScheme = formScheme;
    }

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }
}

