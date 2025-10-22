/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.common;

import java.util.Map;

public class UploadSumNew {
    private String formKey;
    private int masterSum = 0;
    private int unSubmitedNum = 0;
    private int originalNum = 0;
    private int uploadedNum = 0;
    private String firstUploadExplain;
    private String uploadExplain;
    private int submitedNum = 0;
    private int returnedNum = 0;
    private int confirmedNum = 0;
    private int rejectedNum = 0;
    private String returnType;
    private String rejectedExplain;
    private int delayNum = 0;
    private int rejectedCount = 0;
    private String uploadDate;
    private String firstUploadTime;
    private String endUploadTime;
    private int uploadNums = 0;
    private String rejectTime;
    private String submitedTime;
    private String returnedTime;
    private String comfirmedTime;
    private String cancelConfirmTime;
    private String time;
    private String cmt;
    private String operator;
    private Map<String, Integer> customStateMap;

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
        this.uploadedNum += uploadedNum;
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

    public String getFirstUploadTime() {
        return this.firstUploadTime;
    }

    public void setFirstUploadTime(String firstUploadTime) {
        this.firstUploadTime = firstUploadTime;
    }

    public String getEndUploadTime() {
        return this.endUploadTime;
    }

    public void setEndUploadTime(String endUploadTime) {
        this.endUploadTime = endUploadTime;
    }

    public String getRejectTime() {
        return this.rejectTime;
    }

    public void setRejectTime(String rejectTime) {
        this.rejectTime = rejectTime;
    }

    public String getSubmitedTime() {
        return this.submitedTime;
    }

    public void setSubmitedTime(String submitedTime) {
        this.submitedTime = submitedTime;
    }

    public String getReturnedTime() {
        return this.returnedTime;
    }

    public void setReturnedTime(String returnedTime) {
        this.returnedTime = returnedTime;
    }

    public String getComfirmedTime() {
        return this.comfirmedTime;
    }

    public void setComfirmedTime(String comfirmedTime) {
        this.comfirmedTime = comfirmedTime;
    }

    public int getUploadNums() {
        return this.uploadNums;
    }

    public void setUploadNums(int uploadNums) {
        this.uploadNums = uploadNums;
    }

    public int getRejectedCount() {
        return this.rejectedCount;
    }

    public void setRejectedCount(int rejectedCount) {
        this.rejectedCount = rejectedCount;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCmt() {
        return this.cmt;
    }

    public void setCmt(String cmt) {
        this.cmt = cmt;
    }

    public String getOperator() {
        return this.operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getCancelConfirmTime() {
        return this.cancelConfirmTime;
    }

    public void setCancelConfirmTime(String cancelConfirmTime) {
        this.cancelConfirmTime = cancelConfirmTime;
    }

    public String getFirstUploadExplain() {
        return this.firstUploadExplain;
    }

    public void setFirstUploadExplain(String firstUploadExplain) {
        this.firstUploadExplain = firstUploadExplain;
    }

    public String getUploadExplain() {
        return this.uploadExplain;
    }

    public void setUploadExplain(String uploadExplain) {
        this.uploadExplain = uploadExplain;
    }

    public String getReturnType() {
        return this.returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getRejectedExplain() {
        return this.rejectedExplain;
    }

    public void setRejectedExplain(String rejectedExplain) {
        this.rejectedExplain = rejectedExplain;
    }

    public int getDelayNum() {
        return this.delayNum;
    }

    public void setDelayNum(int delayNum) {
        this.delayNum = delayNum;
    }

    public Map<String, Integer> getCustomStateMap() {
        return this.customStateMap;
    }

    public void setCustomStateMap(Map<String, Integer> customStateMap) {
        this.customStateMap = customStateMap;
    }

    public void addUploadNum() {
        ++this.uploadedNum;
    }

    public void addConfirmNum() {
        ++this.confirmedNum;
    }
}

