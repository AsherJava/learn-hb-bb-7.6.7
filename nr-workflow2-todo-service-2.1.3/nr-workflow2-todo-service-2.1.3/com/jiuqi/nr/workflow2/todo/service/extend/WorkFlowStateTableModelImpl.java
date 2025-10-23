/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.todo.extend.WorkFlowStateTableModel
 */
package com.jiuqi.nr.workflow2.todo.service.extend;

import com.jiuqi.nr.workflow2.todo.extend.WorkFlowStateTableModel;

public class WorkFlowStateTableModelImpl
implements WorkFlowStateTableModel {
    private String tableName;
    private String historyTableName;
    private String workflowInstanceColumn;
    private String periodColumn;
    private String unitColumn;
    private String workFlowObjectColumn;
    private String uploadStateColumn;
    private String currentWorkFlowNodeColumn;
    private String reportDimensionColumn;
    private String currentActionColumn;
    private String timeColumn;
    private String adjustColumn;
    private String commentColumn;

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getHistoryTableName() {
        return this.historyTableName;
    }

    public void setHistoryTableName(String historyTableName) {
        this.historyTableName = historyTableName;
    }

    public String getWorkflowInstanceColumn() {
        return this.workflowInstanceColumn;
    }

    public void setWorkflowInstanceColumn(String workflowInstanceColumn) {
        this.workflowInstanceColumn = workflowInstanceColumn;
    }

    public String getPeriodColumn() {
        return this.periodColumn;
    }

    public void setPeriodColumn(String periodColumn) {
        this.periodColumn = periodColumn;
    }

    public String getUnitColumn() {
        return this.unitColumn;
    }

    public void setUnitColumn(String unitColumn) {
        this.unitColumn = unitColumn;
    }

    public String getWorkFlowObjectColumn() {
        return this.workFlowObjectColumn;
    }

    public void setWorkFlowObjectColumn(String workFlowObjectColumn) {
        this.workFlowObjectColumn = workFlowObjectColumn;
    }

    public String getUploadStateColumn() {
        return this.uploadStateColumn;
    }

    public void setUploadStateColumn(String uploadStateColumn) {
        this.uploadStateColumn = uploadStateColumn;
    }

    public String getCurrentWorkFlowNodeColumn() {
        return this.currentWorkFlowNodeColumn;
    }

    public void setCurrentWorkFlowNodeColumn(String currentWorkFlowNodeColumn) {
        this.currentWorkFlowNodeColumn = currentWorkFlowNodeColumn;
    }

    public String getReportDimensionColumn() {
        return this.reportDimensionColumn;
    }

    public void setReportDimensionColumn(String reportDimensionColumn) {
        this.reportDimensionColumn = reportDimensionColumn;
    }

    public String getCurrentActionColumn() {
        return this.currentActionColumn;
    }

    public void setCurrentActionColumn(String currentActionColumn) {
        this.currentActionColumn = currentActionColumn;
    }

    public String getTimeColumn() {
        return this.timeColumn;
    }

    public void setTimeColumn(String timeColumn) {
        this.timeColumn = timeColumn;
    }

    public String getAdjustColumn() {
        return this.adjustColumn;
    }

    public void setAdjustColumn(String adjustColumn) {
        this.adjustColumn = adjustColumn;
    }

    public String getCommentColumn() {
        return this.commentColumn;
    }

    public void setCommentColumn(String commentColumn) {
        this.commentColumn = commentColumn;
    }
}

