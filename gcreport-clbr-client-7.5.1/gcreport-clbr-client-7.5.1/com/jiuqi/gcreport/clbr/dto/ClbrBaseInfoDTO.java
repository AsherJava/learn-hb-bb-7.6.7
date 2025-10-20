/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbr.dto;

import java.util.Date;

public class ClbrBaseInfoDTO {
    private String clbrCode;
    private Double currentClbrAmount;
    private Date clbrTime;
    private String groupId;
    private Integer confirmStatus;

    public String getClbrCode() {
        return this.clbrCode;
    }

    public void setClbrCode(String clbrCode) {
        this.clbrCode = clbrCode;
    }

    public Double getCurrentClbrAmount() {
        return this.currentClbrAmount;
    }

    public void setCurrentClbrAmount(Double currentClbrAmount) {
        this.currentClbrAmount = currentClbrAmount;
    }

    public Date getClbrTime() {
        return this.clbrTime;
    }

    public void setClbrTime(Date clbrTime) {
        this.clbrTime = clbrTime;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Integer getConfirmStatus() {
        return this.confirmStatus;
    }

    public void setConfirmStatus(Integer confirmStatus) {
        this.confirmStatus = confirmStatus;
    }
}

