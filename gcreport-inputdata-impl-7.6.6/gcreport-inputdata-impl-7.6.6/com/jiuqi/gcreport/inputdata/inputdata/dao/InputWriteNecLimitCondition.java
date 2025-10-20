/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.inputdata.inputdata.dao;

public class InputWriteNecLimitCondition {
    private final String taskId;
    private final String nrPeriod;
    private final String currenctCode;
    private final String leafOrgId;

    public static InputWriteNecLimitCondition newLeafOrgLimit(String taskId, String nrPeriod, String currenctCode, String leafOrgId) {
        return new InputWriteNecLimitCondition(taskId, nrPeriod, currenctCode, leafOrgId);
    }

    public static InputWriteNecLimitCondition newMergeOrgLimit(String taskId, String nrPeriod, String currenctCode) {
        return new InputWriteNecLimitCondition(taskId, nrPeriod, currenctCode, null);
    }

    public String getTaskId() {
        return this.taskId;
    }

    public String getCurrenctCode() {
        return this.currenctCode;
    }

    public String getNrPeriod() {
        return this.nrPeriod;
    }

    public String getLeafOrgId() {
        return this.leafOrgId;
    }

    private InputWriteNecLimitCondition(String taskId, String nrPeriod, String currenctCode, String leafOrgId) {
        this.taskId = taskId;
        this.currenctCode = currenctCode;
        this.nrPeriod = nrPeriod;
        this.leafOrgId = leafOrgId;
    }
}

