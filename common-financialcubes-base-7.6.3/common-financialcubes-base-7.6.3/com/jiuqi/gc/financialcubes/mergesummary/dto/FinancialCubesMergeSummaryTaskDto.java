/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gc.financialcubes.mergesummary.dto;

import com.jiuqi.common.financialcubes.common.FinancialCubesPeriodTypeEnum;
import java.io.Serializable;
import java.util.List;

public class FinancialCubesMergeSummaryTaskDto
implements Serializable {
    private String unitCode;
    private String systemId;
    private String orgType;
    private String dataTime;
    private List<String> subjectCodes;
    private Integer batchNum;
    private FinancialCubesPeriodTypeEnum periodType;

    public FinancialCubesMergeSummaryTaskDto() {
    }

    public FinancialCubesMergeSummaryTaskDto(String unitCode, String systemId, String orgType, String dataTime, List<String> subjectCodes, FinancialCubesPeriodTypeEnum periodType) {
        this.unitCode = unitCode;
        this.systemId = systemId;
        this.orgType = orgType;
        this.dataTime = dataTime;
        this.subjectCodes = subjectCodes;
        this.periodType = periodType;
    }

    public FinancialCubesMergeSummaryTaskDto copy() {
        return new FinancialCubesMergeSummaryTaskDto(this.unitCode, this.getSystemId(), this.getOrgType(), this.dataTime, this.subjectCodes, this.periodType);
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getSystemId() {
        return this.systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getDataTime() {
        return this.dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public List<String> getSubjectCodes() {
        return this.subjectCodes;
    }

    public void setSubjectCodes(List<String> subjectCodes) {
        this.subjectCodes = subjectCodes;
    }

    public FinancialCubesPeriodTypeEnum getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(FinancialCubesPeriodTypeEnum periodType) {
        this.periodType = periodType;
    }

    public Integer getBatchNum() {
        return this.batchNum;
    }

    public void setBatchNum(Integer batchNum) {
        this.batchNum = batchNum;
    }

    public String toString() {
        return "FinancialCubesMergeSummaryTaskDto{unitCode='" + this.unitCode + '\'' + ", systemId='" + this.systemId + '\'' + ", orgType='" + this.orgType + '\'' + ", dataTime='" + this.dataTime + '\'' + ", subjectCodes='" + this.subjectCodes + '\'' + ", batchNum='" + this.batchNum + '\'' + ", periodType=" + (Object)((Object)this.periodType) + '}';
    }
}

