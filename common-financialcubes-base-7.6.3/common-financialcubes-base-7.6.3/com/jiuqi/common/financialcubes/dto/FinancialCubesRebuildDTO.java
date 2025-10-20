/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.financialcubes.dto;

import java.util.List;

public class FinancialCubesRebuildDTO {
    private String unitCode;
    private String periodType;
    private String dataTime;
    private String orgType;
    private String bblx;
    private List<String> rebuildScope;
    private List<String> subjectCodeList;
    private Integer year;
    private Integer period;
    private boolean mappingFlag;

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getDataTime() {
        return this.dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public List<String> getRebuildScope() {
        return this.rebuildScope;
    }

    public void setRebuildScope(List<String> rebuildScope) {
        this.rebuildScope = rebuildScope;
    }

    public String getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public String getBblx() {
        return this.bblx;
    }

    public void setBblx(String bblx) {
        this.bblx = bblx;
    }

    public Integer getYear() {
        return this.year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getPeriod() {
        return this.period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public List<String> getSubjectCodeList() {
        return this.subjectCodeList;
    }

    public void setSubjectCodeList(List<String> subjectCodeList) {
        this.subjectCodeList = subjectCodeList;
    }

    public boolean getMappingFlag() {
        return this.mappingFlag;
    }

    public void setMappingFlag(boolean mappingFlag) {
        this.mappingFlag = mappingFlag;
    }

    public String toString() {
        return "FinancialCubesRebuildDTO{unitCode='" + this.unitCode + '\'' + ", periodType='" + this.periodType + '\'' + ", dataTime='" + this.dataTime + '\'' + ", orgType='" + this.orgType + '\'' + ", bblx='" + this.bblx + '\'' + ", rebuildScope=" + this.rebuildScope + ", subjectCodeList=" + this.subjectCodeList + ", year=" + this.year + ", period=" + this.period + '}';
    }
}

