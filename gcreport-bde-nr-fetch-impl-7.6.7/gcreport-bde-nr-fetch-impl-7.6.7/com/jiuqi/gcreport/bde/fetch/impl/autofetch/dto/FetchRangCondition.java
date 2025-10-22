/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.financialcubes.common.FinancialCubesPeriodTypeEnum
 */
package com.jiuqi.gcreport.bde.fetch.impl.autofetch.dto;

import com.jiuqi.common.financialcubes.common.FinancialCubesPeriodTypeEnum;

public class FetchRangCondition {
    private String financialTableName;
    private FinancialCubesPeriodTypeEnum periodType;
    private String orgCode;
    private Integer endBatchNum;
    private Integer startBatchNum;
    private String dataTime;

    public String getFinancialTableName() {
        return this.financialTableName;
    }

    public void setFinancialTableName(String financialTableName) {
        this.financialTableName = financialTableName;
    }

    public FinancialCubesPeriodTypeEnum getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(FinancialCubesPeriodTypeEnum periodType) {
        this.periodType = periodType;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public Integer getEndBatchNum() {
        return this.endBatchNum;
    }

    public void setEndBatchNum(Integer endBatchNum) {
        this.endBatchNum = endBatchNum;
    }

    public Integer getStartBatchNum() {
        return this.startBatchNum;
    }

    public void setStartBatchNum(Integer startBatchNum) {
        this.startBatchNum = startBatchNum;
    }

    public String getDataTime() {
        return this.dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }
}

