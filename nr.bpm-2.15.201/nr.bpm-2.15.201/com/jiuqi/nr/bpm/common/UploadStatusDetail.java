/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.common;

import com.jiuqi.nr.bpm.common.UploadRecordDetail;
import java.util.List;

public class UploadStatusDetail {
    private List<UploadRecordDetail> uploadRecordDetail;
    private int unitCount = 0;
    private int originalNum = 0;
    private int uploadedNum = 0;
    private int submitedNum = 0;
    private int returnedNum = 0;
    private int confirmedNum = 0;
    private int rejectedNum = 0;

    public List<UploadRecordDetail> getUploadRecordDetail() {
        return this.uploadRecordDetail;
    }

    public void setUploadRecordDetail(List<UploadRecordDetail> uploadRecordDetail) {
        this.uploadRecordDetail = uploadRecordDetail;
    }

    public int getUnitCount() {
        return this.unitCount;
    }

    public void setUnitCount(int unitCount) {
        this.unitCount = unitCount;
    }

    public int getOriginalNum() {
        return this.originalNum;
    }

    public void incrementOriginalNum() {
        this.originalNum = this.getOriginalNum() + 1;
    }

    public void setOriginalNum(int originalNum) {
        this.originalNum = originalNum;
    }

    public int getUploadedNum() {
        return this.uploadedNum;
    }

    public void incrementUploadedNum() {
        this.uploadedNum = this.getUploadedNum() + 1;
    }

    public void setUploadedNum(int uploadedNum) {
        this.uploadedNum = uploadedNum;
    }

    public int getSubmitedNum() {
        return this.submitedNum;
    }

    public void incrementSubmitedNum() {
        this.submitedNum = this.getSubmitedNum() + 1;
    }

    public void setSubmitedNum(int submitedNum) {
        this.submitedNum = submitedNum;
    }

    public int getReturnedNum() {
        return this.returnedNum;
    }

    public void incrementReturnedNum() {
        this.returnedNum = this.getReturnedNum() + 1;
    }

    public void setReturnedNum(int returnedNum) {
        this.returnedNum = returnedNum;
    }

    public int getConfirmedNum() {
        return this.confirmedNum;
    }

    public void incrementConfirmedNum() {
        this.confirmedNum = this.getConfirmedNum() + 1;
    }

    public void setConfirmedNum(int confirmedNum) {
        this.confirmedNum = confirmedNum;
    }

    public int getRejectedNum() {
        return this.rejectedNum;
    }

    public void incrementRejectedNum() {
        this.rejectedNum = this.getRejectedNum() + 1;
    }

    public void setRejectedNum(int rejectedNum) {
        this.rejectedNum = rejectedNum;
    }
}

