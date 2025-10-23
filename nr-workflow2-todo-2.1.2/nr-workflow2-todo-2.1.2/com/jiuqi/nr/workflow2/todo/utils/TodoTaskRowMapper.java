/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.workflow2.todo.utils;

import com.jiuqi.nr.workflow2.todo.entityimpl.TodoTaskImpl;
import com.jiuqi.nr.workflow2.todo.extend.WorkFlowStateTableModel;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class TodoTaskRowMapper
implements RowMapper<TodoTaskImpl> {
    private final WorkFlowStateTableModel stateTableModel;
    private final boolean isAdjust;
    private final boolean isReportDimensionEnable;

    public TodoTaskRowMapper(WorkFlowStateTableModel stateTableModel, boolean isAdjust, boolean isReportDimensionEnable) {
        this.stateTableModel = stateTableModel;
        this.isAdjust = isAdjust;
        this.isReportDimensionEnable = isReportDimensionEnable;
    }

    public TodoTaskImpl mapRow(ResultSet rs, int rowNum) throws SQLException {
        boolean isHistoryTable = false;
        ResultSetMetaData resultSetMetaData = rs.getMetaData();
        for (int i = 1; i <= resultSetMetaData.getColumnCount(); ++i) {
            String columnName = resultSetMetaData.getColumnName(i);
            if (!columnName.equals(this.stateTableModel.getCommentColumn())) continue;
            isHistoryTable = true;
            break;
        }
        TodoTaskImpl todoTask = new TodoTaskImpl();
        todoTask.setPeriod(rs.getString(this.stateTableModel.getPeriodColumn()));
        todoTask.setUnit(rs.getString(this.stateTableModel.getUnitColumn()));
        todoTask.setWorkflowNodeTask(rs.getString("TASKID"));
        todoTask.setWorkflowObject(rs.getString(this.stateTableModel.getWorkFlowObjectColumn()));
        todoTask.setWorkflowInstance(rs.getString("PROCESSID"));
        todoTask.setUploadState(isHistoryTable ? rs.getString(this.stateTableModel.getCurrentActionColumn()) : rs.getString(this.stateTableModel.getUploadStateColumn()));
        String comment = rs.getString("REMARK");
        if (isHistoryTable && (comment == null || comment.isEmpty())) {
            comment = rs.getString(this.stateTableModel.getCommentColumn());
        }
        todoTask.setComment(comment);
        todoTask.setTime(isHistoryTable ? rs.getString(this.stateTableModel.getTimeColumn()) : rs.getString("RECEIVETIME"));
        todoTask.setAdjust(this.isAdjust ? rs.getString(this.stateTableModel.getAdjustColumn()) : "");
        todoTask.setReportDimension(this.isReportDimensionEnable ? rs.getString(this.stateTableModel.getReportDimensionColumn()) : "");
        return todoTask;
    }
}

