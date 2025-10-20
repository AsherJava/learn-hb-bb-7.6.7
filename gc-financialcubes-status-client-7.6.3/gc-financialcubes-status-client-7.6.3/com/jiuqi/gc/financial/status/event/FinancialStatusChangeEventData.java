/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gc.financial.status.event;

import java.io.Serializable;
import java.util.List;

public class FinancialStatusChangeEventData
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String moduleCode;
    private String dataTime;
    private String periodType;
    private List<String> unitList;
    private String status;

    public FinancialStatusChangeEventData() {
    }

    public FinancialStatusChangeEventData(String moduleCode, String dataTime, String periodType, List<String> unitList, String status) {
        this.moduleCode = moduleCode;
        this.dataTime = dataTime;
        this.periodType = periodType;
        this.unitList = unitList;
        this.status = status;
    }

    public String getDataTime() {
        return this.dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public String getModuleCode() {
        return this.moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public List<String> getUnitList() {
        return this.unitList;
    }

    public void setUnitList(List<String> unitList) {
        this.unitList = unitList;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toString() {
        return "FinancialStatusChangeEventData{moduleCode='" + this.moduleCode + '\'' + ", dataTime='" + this.dataTime + '\'' + ", periodType='" + this.periodType + '\'' + ", unitList=" + this.unitList + ", status='" + this.status + '\'' + '}';
    }
}

