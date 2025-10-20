/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.vo;

public class OffsetVchrPreItemConditonVO {
    private String unitId;
    private String oppUnitId;
    private Integer acctYear;
    private Integer month;
    private String ruleId;
    private String subjectCode;
    private String currenctCode;
    private String gcBusinessTypeCode;
    private String areaCode;
    private String ywbkCode;
    private String gcywlxCode;
    private String tzyzmsCode;
    private String projectCode;
    private Boolean reclassed;
    private Boolean summary;
    private Integer pageNum;
    private Integer pageSize;
    private Integer total;

    public String getUnitId() {
        return this.unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getOppUnitId() {
        return this.oppUnitId;
    }

    public void setOppUnitId(String oppUnitId) {
        this.oppUnitId = oppUnitId;
    }

    public Integer getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(Integer acctYear) {
        this.acctYear = acctYear;
    }

    public Integer getMonth() {
        return this.month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getRuleId() {
        return this.ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getCurrenctCode() {
        return this.currenctCode;
    }

    public void setCurrenctCode(String currenctCode) {
        this.currenctCode = currenctCode;
    }

    public String getGcBusinessTypeCode() {
        return this.gcBusinessTypeCode;
    }

    public void setGcBusinessTypeCode(String gcBusinessTypeCode) {
        this.gcBusinessTypeCode = gcBusinessTypeCode;
    }

    public String getAreaCode() {
        return this.areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getYwbkCode() {
        return this.ywbkCode;
    }

    public void setYwbkCode(String ywbkCode) {
        this.ywbkCode = ywbkCode;
    }

    public String getGcywlxCode() {
        return this.gcywlxCode;
    }

    public void setGcywlxCode(String gcywlxCode) {
        this.gcywlxCode = gcywlxCode;
    }

    public String getTzyzmsCode() {
        return this.tzyzmsCode;
    }

    public void setTzyzmsCode(String tzyzmsCode) {
        this.tzyzmsCode = tzyzmsCode;
    }

    public String getProjectCode() {
        return this.projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public Boolean getReclassed() {
        return this.reclassed;
    }

    public void setReclassed(Boolean reclassed) {
        this.reclassed = reclassed;
    }

    public Boolean getSummary() {
        return this.summary;
    }

    public void setSummary(Boolean summary) {
        this.summary = summary;
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

    public Integer getStartPosition() {
        return (this.pageNum - 1) * this.pageSize;
    }

    public Integer getTotal() {
        return this.total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String toString() {
        return "OffsetVchrPreItemConditonVO [unitId=" + this.unitId + ", oppUnitId=" + this.oppUnitId + ", acctYear=" + this.acctYear + ", month=" + this.month + ", ruleId=" + this.ruleId + ", subjectCode=" + this.subjectCode + ", currenctCode=" + this.currenctCode + ", gcBusinessTypeCode=" + this.gcBusinessTypeCode + ", areaCode=" + this.areaCode + ", ywbkCode=" + this.ywbkCode + ", gcywlxCode=" + this.gcywlxCode + ", tzyzmsCode=" + this.tzyzmsCode + ", projectCode=" + this.projectCode + ", pageNum=" + this.pageNum + ", pageSize=" + this.pageSize + ", total=" + this.total + "]";
    }
}

