/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.financialcubes.common.FinancialCubesPeriodTypeEnum
 *  com.jiuqi.va.domain.common.MD5Util
 */
package com.jiuqi.gc.financialcubes.offset.dto;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.financialcubes.common.FinancialCubesPeriodTypeEnum;
import com.jiuqi.va.domain.common.MD5Util;
import java.io.Serializable;
import java.util.List;

public class FinancialCubesOffsetTaskDto
implements Serializable {
    private String diffUnitId;
    private String unitCode;
    private String oppUnitCode;
    private String systemId;
    private String orgType;
    private String dataTime;
    private String equalsString;
    private Integer batchNum;
    private String deleteId;
    private String subjectTempGroupId;
    private boolean rebuildFlag;
    private List<String> subjectCodes;
    private FinancialCubesPeriodTypeEnum periodTypeEnum;

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getOppUnitCode() {
        return this.oppUnitCode;
    }

    public void setOppUnitCode(String oppUnitCode) {
        this.oppUnitCode = oppUnitCode;
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

    public Integer getBatchNum() {
        return this.batchNum;
    }

    public void setBatchNum(Integer batchNum) {
        this.batchNum = batchNum;
    }

    public boolean isRebuildFlag() {
        return this.rebuildFlag;
    }

    public void setRebuildFlag(boolean rebuildFlag) {
        this.rebuildFlag = rebuildFlag;
    }

    public String getDiffUnitId() {
        return this.diffUnitId;
    }

    public void setDiffUnitId(String diffUnitId) {
        this.diffUnitId = diffUnitId;
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

    public String getSubjectTempGroupId() {
        return this.subjectTempGroupId;
    }

    public void setSubjectTempGroupId(String subjectTempGroupId) {
        this.subjectTempGroupId = subjectTempGroupId;
    }

    public FinancialCubesPeriodTypeEnum getPeriodTypeEnum() {
        return this.periodTypeEnum;
    }

    public void setPeriodTypeEnum(FinancialCubesPeriodTypeEnum periodTypeEnum) {
        this.periodTypeEnum = periodTypeEnum;
    }

    public String toString() {
        return "FinancialCubesOffsetTaskDto{diffUnitId='" + this.diffUnitId + '\'' + ", unitCode='" + this.unitCode + '\'' + ", oppUnitCode='" + this.oppUnitCode + '\'' + ", systemId='" + this.systemId + '\'' + ", orgType='" + this.orgType + '\'' + ", dataTime='" + this.dataTime + '\'' + ", equalsString='" + this.equalsString + '\'' + ", batchNum=" + this.batchNum + ", deleteId='" + this.deleteId + '\'' + ", groupId='" + this.subjectTempGroupId + '\'' + ", rebuildFlag=" + this.rebuildFlag + '}';
    }

    public String getEqualsString() {
        if (!StringUtils.isEmpty((String)this.equalsString)) {
            return this.equalsString;
        }
        String equalsString = "FinancialCubesOffsetTaskDto{unitAndOpp='" + this.getUnitAndOpp() + '\'' + ", systemId='" + this.systemId + '\'' + ", orgType='" + this.orgType + '\'' + ", dataTime='" + this.dataTime + '\'' + '}';
        this.equalsString = MD5Util.encrypt((String)equalsString);
        return equalsString;
    }

    public String getUnitAndOpp() {
        return this.unitCode.compareTo(this.oppUnitCode) <= 0 ? this.unitCode + "|" + this.oppUnitCode : this.oppUnitCode + "|" + this.unitCode;
    }
}

