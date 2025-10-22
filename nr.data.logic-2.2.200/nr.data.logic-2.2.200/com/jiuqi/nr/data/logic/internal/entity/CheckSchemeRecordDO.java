/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.internal.entity;

public class CheckSchemeRecordDO {
    private String key;
    private String userId;
    private String formSchemeKey;
    private long checkTime;
    @Deprecated
    private byte[] checkResultQueryParamJson;
    private String ckrParJson;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public long getCheckTime() {
        return this.checkTime;
    }

    public void setCheckTime(long checkTime) {
        this.checkTime = checkTime;
    }

    public byte[] getCheckResultQueryParamJson() {
        return this.checkResultQueryParamJson;
    }

    public void setCheckResultQueryParamJson(byte[] checkResultQueryParamJson) {
        this.checkResultQueryParamJson = checkResultQueryParamJson;
    }

    public String getCkrParJson() {
        return this.ckrParJson;
    }

    public void setCkrParJson(String ckrParJson) {
        this.ckrParJson = ckrParJson;
    }
}

