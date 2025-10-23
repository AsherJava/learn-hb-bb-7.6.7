/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.todo.extend;

public interface WorkFlowStateTableModel {
    public String getTableName();

    public String getHistoryTableName();

    public String getWorkflowInstanceColumn();

    public String getPeriodColumn();

    public String getUnitColumn();

    public String getWorkFlowObjectColumn();

    public String getUploadStateColumn();

    public String getCurrentWorkFlowNodeColumn();

    public String getReportDimensionColumn();

    public String getCurrentActionColumn();

    public String getTimeColumn();

    public String getAdjustColumn();

    public String getCommentColumn();
}

