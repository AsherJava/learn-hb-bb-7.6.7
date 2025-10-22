/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckImpl.dataentry.context;

public class DataCollectionContext {
    private String checkMode;
    private String orgType;
    private String dataTime;
    private String unitId;
    private String taskName;

    public static DataCollectionContext newInstance() {
        DataCollectionContext context = new DataCollectionContext();
        return context;
    }

    public String getCheckMode() {
        return this.checkMode;
    }

    public void setCheckMode(String checkMode) {
        this.checkMode = checkMode;
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

    public String getUnitId() {
        return this.unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}

