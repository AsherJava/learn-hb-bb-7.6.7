/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.UUIDUtils
 */
package com.jiuqi.common.financialcubes.dto;

import com.jiuqi.common.base.util.UUIDUtils;
import java.io.Serializable;
import java.util.List;

public class FinancialCubesAdjustTaskDTO
implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer acctYear;
    private Integer acctPeriod;
    private String unitCode;
    private Integer count;
    private Integer batchNum;
    private String groupId = UUIDUtils.newUUIDStr();
    private String dataSchemeCode;
    private String periodType;
    private String deleteId;
    private List<String> subjectCodes;

    public FinancialCubesAdjustTaskDTO() {
    }

    public FinancialCubesAdjustTaskDTO(Integer acctYear, Integer acctPeriod, String unitCode, Integer count) {
        this.acctYear = acctYear;
        this.acctPeriod = acctPeriod;
        this.unitCode = unitCode;
        this.count = count;
    }

    public String getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public String getDeleteId() {
        return this.deleteId;
    }

    public void setDeleteId(String deleteId) {
        this.deleteId = deleteId;
    }

    public List<String> getSubjectCodes() {
        return this.subjectCodes;
    }

    public void setSubjectCodes(List<String> subjectCodes) {
        this.subjectCodes = subjectCodes;
    }

    public Integer getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(Integer acctYear) {
        this.acctYear = acctYear;
    }

    public Integer getAcctPeriod() {
        return this.acctPeriod;
    }

    public void setAcctPeriod(Integer acctPeriod) {
        this.acctPeriod = acctPeriod;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public Integer getCount() {
        return this.count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getBatchNum() {
        return this.batchNum;
    }

    public void setBatchNum(Integer batchNum) {
        this.batchNum = batchNum;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getDataSchemeCode() {
        return this.dataSchemeCode;
    }

    public void setDataSchemeCode(String dataSchemeCode) {
        this.dataSchemeCode = dataSchemeCode;
    }

    public String toString() {
        return "FinancialCubesAdjustTaskDTO{acctYear=" + this.acctYear + ", acctPeriod=" + this.acctPeriod + ", unitCode='" + this.unitCode + '\'' + ", count=" + this.count + ", batchNum=" + this.batchNum + ", groupId='" + this.groupId + '\'' + ", dataSchemeCode='" + this.dataSchemeCode + '\'' + ", periodType='" + this.periodType + '\'' + ", deleteId='" + this.deleteId + '\'' + ", subjectCodes=" + this.subjectCodes + '}';
    }
}

