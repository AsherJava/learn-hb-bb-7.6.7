/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.workflow2.todo.utils;

import com.jiuqi.nr.workflow2.todo.entityimpl.TodoTaskExtend;
import com.jiuqi.nr.workflow2.todo.extend.WorkFlowStateTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class TodoTaskExtendRowMapper
implements RowMapper<TodoTaskExtend> {
    private final WorkFlowStateTableModel stateTableModel;
    private final boolean isAdjust;
    private final boolean isReportDimensionEnable;

    public TodoTaskExtendRowMapper(WorkFlowStateTableModel stateTableModel, boolean isAdjust, boolean isReportDimensionEnable) {
        this.stateTableModel = stateTableModel;
        this.isAdjust = isAdjust;
        this.isReportDimensionEnable = isReportDimensionEnable;
    }

    public TodoTaskExtend mapRow(ResultSet rs, int rowNum) throws SQLException {
        TodoTaskExtend todoTaskExtend = new TodoTaskExtend();
        todoTaskExtend.setPeriod(rs.getString(this.stateTableModel.getPeriodColumn()));
        todoTaskExtend.setUnit(rs.getString(this.stateTableModel.getUnitColumn()));
        todoTaskExtend.setWorkflowNodeTask(rs.getString("TASKID"));
        todoTaskExtend.setWorkflowObject(rs.getString(this.stateTableModel.getWorkFlowObjectColumn()));
        todoTaskExtend.setWorkflowInstance(rs.getString("PROCESSID"));
        todoTaskExtend.setUploadState(rs.getString(this.stateTableModel.getUploadStateColumn()));
        todoTaskExtend.setComment(rs.getString("REMARK"));
        todoTaskExtend.setTime(rs.getString("RECEIVETIME"));
        todoTaskExtend.setAdjust(this.isAdjust ? rs.getString(this.stateTableModel.getAdjustColumn()) : "");
        todoTaskExtend.setReportDimension(this.isReportDimensionEnable ? rs.getString(this.stateTableModel.getReportDimensionColumn()) : "");
        todoTaskExtend.setWorkflowNode(rs.getString("TASKDEFINEKEY"));
        return todoTaskExtend;
    }
}

