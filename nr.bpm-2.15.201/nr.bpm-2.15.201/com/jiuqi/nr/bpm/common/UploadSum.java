/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.common;

public class UploadSum {
    private String entityId;
    private String period;
    private String formKey;
    private int masterSum = 0;
    private int unSubmitedNum = 0;
    private int originalNum = 0;
    private int uploadedNum = 0;
    private int submitedNum = 0;
    private int returnedNum = 0;
    private int confirmedNum = 0;
    private int rejectedNum = 0;
    private String uploadDate;

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public int getMasterSum() {
        return this.masterSum;
    }

    public void setMasterSum(int masterSum) {
        this.masterSum = masterSum;
    }

    public int getOriginalNum() {
        return this.originalNum;
    }

    public void setOriginalNum(int originalNum) {
        this.originalNum = originalNum;
    }

    public int getUploadedNum() {
        return this.uploadedNum;
    }

    public void setUploadedNum(int uploadedNum) {
        this.uploadedNum = uploadedNum;
    }

    public int getSubmitedNum() {
        return this.submitedNum;
    }

    public void setSubmitedNum(int submitedNum) {
        this.submitedNum = submitedNum;
    }

    public int getReturnedNum() {
        return this.returnedNum;
    }

    public void setReturnedNum(int returnedNum) {
        this.returnedNum = returnedNum;
    }

    public int getConfirmedNum() {
        return this.confirmedNum;
    }

    public void setConfirmedNum(int confirmedNum) {
        this.confirmedNum = confirmedNum;
    }

    public int getRejectedNum() {
        return this.rejectedNum;
    }

    public void setRejectedNum(int rejectedNum) {
        this.rejectedNum = rejectedNum;
    }

    public String getUploadDate() {
        return this.uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public int getUnSubmitedNum() {
        return this.unSubmitedNum;
    }

    public void setUnSubmitedNum(int unSubmitedNum) {
        this.unSubmitedNum = unSubmitedNum;
    }
}

