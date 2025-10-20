/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.coop.feign.config.domain;

public class ClbrItemDTO {
    private static final long serialVersionUID = 1L;
    private String clbrBillCode;
    private String clbrReceiveBillCode;
    private String srcId;
    private String sn;
    private String clbrCode;
    private Integer confirmType;

    public String getClbrBillCode() {
        return this.clbrBillCode;
    }

    public void setClbrBillCode(String clbrBillCode) {
        this.clbrBillCode = clbrBillCode;
    }

    public String getSrcId() {
        return this.srcId;
    }

    public void setSrcId(String srcId) {
        this.srcId = srcId;
    }

    public String getSn() {
        return this.sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getClbrCode() {
        return this.clbrCode;
    }

    public void setClbrCode(String clbrCode) {
        this.clbrCode = clbrCode;
    }

    public String getClbrReceiveBillCode() {
        return this.clbrReceiveBillCode;
    }

    public void setClbrReceiveBillCode(String clbrReceiveBillCode) {
        this.clbrReceiveBillCode = clbrReceiveBillCode;
    }

    public Integer getConfirmType() {
        return this.confirmType;
    }

    public void setConfirmType(Integer confirmType) {
        this.confirmType = confirmType;
    }
}

