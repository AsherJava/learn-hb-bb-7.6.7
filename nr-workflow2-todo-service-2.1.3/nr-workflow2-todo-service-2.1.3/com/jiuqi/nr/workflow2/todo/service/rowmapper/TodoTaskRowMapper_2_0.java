/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.nr.workflow2.todo.entityimpl.TodoTaskImpl
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.workflow2.todo.service.rowmapper;

import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.todo.entityimpl.TodoTaskImpl;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class TodoTaskRowMapper_2_0
implements RowMapper<TodoTaskImpl> {
    private final WorkflowObjectType workflowObjectType;

    public TodoTaskRowMapper_2_0(WorkflowObjectType workflowObjectType) {
        this.workflowObjectType = workflowObjectType;
    }

    public TodoTaskImpl mapRow(ResultSet rs, int rowNum) throws SQLException {
        TodoTaskImpl todoTask = new TodoTaskImpl();
        todoTask.setPeriod(rs.getString("DATATIME"));
        todoTask.setUnit(rs.getString("MDCODE"));
        todoTask.setWorkflowNodeTask(rs.getString("TASKID"));
        if (this.workflowObjectType.equals((Object)WorkflowObjectType.FORM)) {
            todoTask.setWorkflowObject(rs.getString("IST_FORMKEY"));
        } else if (this.workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP)) {
            todoTask.setWorkflowObject(rs.getString("IST_FORMGROUPKEY"));
        }
        todoTask.setWorkflowInstance(rs.getString("PROCESSID"));
        todoTask.setUploadState(rs.getString("IST_STATUS"));
        todoTask.setComment(rs.getString("REMARK"));
        todoTask.setTime(rs.getString("RECEIVETIME"));
        return todoTask;
    }
}

