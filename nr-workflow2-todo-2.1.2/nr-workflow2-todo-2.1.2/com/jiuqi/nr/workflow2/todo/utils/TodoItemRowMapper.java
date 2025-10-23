/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.workflow2.todo.utils;

import com.jiuqi.nr.workflow2.todo.entityimpl.TodoItem;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class TodoItemRowMapper
implements RowMapper<TodoItem> {
    public TodoItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        TodoItem todoItem = new TodoItem();
        todoItem.setTaskId(rs.getString("BIZDEFINE"));
        todoItem.setBusinessKey(rs.getString("BIZCODE"));
        todoItem.setBusinessTitle(rs.getString("BIZTITLE"));
        todoItem.setWorkflowNodeTask(rs.getString("TASKID"));
        todoItem.setParticipant(rs.getString("PARTICIPANT"));
        todoItem.setWorkflowNode(rs.getString("TASKDEFINEKEY"));
        todoItem.setWorkflowInstance(rs.getString("PROCESSID"));
        todoItem.setFormSchemeKey(rs.getString("PROCESSDEFINEKEY"));
        todoItem.setPeriod(rs.getString("BIZDATE"));
        todoItem.setRemark(rs.getString("REMARK"));
        return todoItem;
    }
}

