/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.financialcubes.dto;

import java.util.List;

public class FinancialCubesRebuildParam {
    private List<String> unitCodes;
    private List<String> rebuildScopes;
    private String periodType;
    private String dataTime;
    private String orgType;
    private List<String> subjectCodes;

    public List<String> getUnitCodes() {
        return this.unitCodes;
    }

    public void setUnitCodes(List<String> unitCodes) {
        this.unitCodes = unitCodes;
    }

    public List<String> getRebuildScopes() {
        return this.rebuildScopes;
    }

    public void setRebuildScopes(List<String> rebuildScopes) {
        this.rebuildScopes = rebuildScopes;
    }

    public String getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
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

    public List<String> getSubjectCodes() {
        return this.subjectCodes;
    }

    public void setSubjectCodes(List<String> subjectCodes) {
        this.subjectCodes = subjectCodes;
    }

    public String toString() {
        return "FinancialCubesRebuildParam{unitCodes=" + this.unitCodes + ", rebuildScopes=" + this.rebuildScopes + ", periodType='" + this.periodType + '\'' + ", dataTime='" + this.dataTime + '\'' + ", orgType='" + this.orgType + '\'' + ", subjectCodes=" + this.subjectCodes + '}';
    }
}

