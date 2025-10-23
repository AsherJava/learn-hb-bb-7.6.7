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
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class TodoTaskCommentRowMapper
implements RowMapper<TodoTaskImpl> {
    private final WorkFlowStateTableModel stateTableModel;

    public TodoTaskCommentRowMapper(WorkFlowStateTableModel stateTableModel) {
        this.stateTableModel = stateTableModel;
    }

    public TodoTaskImpl mapRow(ResultSet rs, int rowNum) throws SQLException {
        TodoTaskImpl todoTask = new TodoTaskImpl();
        todoTask.setWorkflowInstance(rs.getString("PROCESSID"));
        todoTask.setComment(rs.getString(this.stateTableModel.getCommentColumn()));
        todoTask.setTime(rs.getString(this.stateTableModel.getTimeColumn()));
        return todoTask;
    }
}

