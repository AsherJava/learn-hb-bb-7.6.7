/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.billcore.offsetcheck.dto;

public class OffsetCheckResultDTO {
    private String checkInfo;
    private String checkStatus;

    public OffsetCheckResultDTO(String checkInfo, String checkStatus) {
        this.checkInfo = checkInfo;
        this.checkStatus = checkStatus;
    }

    public String getCheckInfo() {
        return this.checkInfo;
    }

    public void setCheckInfo(String checkInfo) {
        this.checkInfo = checkInfo;
    }

    public String getCheckStatus() {
        return this.checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }
}

