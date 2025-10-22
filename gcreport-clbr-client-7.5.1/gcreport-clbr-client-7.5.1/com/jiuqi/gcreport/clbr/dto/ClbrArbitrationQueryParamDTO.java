/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbr.dto;

public class ClbrArbitrationQueryParamDTO {
    private String sysCode;
    private String clbrType;
    private String relation;
    private String oppRelation;
    private Integer pageNum;
    private Integer pageSize;
    private String billCode;
    private String arbitrationUserName;

    public String getSysCode() {
        return this.sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public String getArbitrationUserName() {
        return this.arbitrationUserName;
    }

    public void setArbitrationUserName(String arbitrationUserName) {
        this.arbitrationUserName = arbitrationUserName;
    }

    public String getClbrType() {
        return this.clbrType;
    }

    public void setClbrType(String clbrType) {
        this.clbrType = clbrType;
    }

    public String getRelation() {
        return this.relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getOppRelation() {
        return this.oppRelation;
    }

    public void setOppRelation(String oppRelation) {
        this.oppRelation = oppRelation;
    }

    public Integer getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getBillCode() {
        return this.billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }
}

