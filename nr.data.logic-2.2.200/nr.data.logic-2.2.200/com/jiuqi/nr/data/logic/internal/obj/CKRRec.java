/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.internal.obj;

public class CKRRec {
    private String key;
    private String batchId;
    private String userID;
    private long startTime;
    private long finishTime;
    private String formSchemeKey;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getBatchId() {
        return this.batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getUserID() {
        return this.userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getFinishTime() {
        return this.finishTime;
    }

    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String toString() {
        return "key:" + this.key + ";batchId:" + this.batchId + ";usrID:" + this.userID + ";formSchemeKey:" + this.formSchemeKey + ";startTime:" + this.startTime + ";finishTime:" + this.finishTime;
    }
}

