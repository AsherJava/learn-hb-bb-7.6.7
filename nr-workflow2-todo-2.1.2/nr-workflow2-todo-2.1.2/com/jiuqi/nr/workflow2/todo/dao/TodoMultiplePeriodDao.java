/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.todo.dao;

import com.jiuqi.nr.workflow2.todo.dto.TodoQueryDTO;
import com.jiuqi.nr.workflow2.todo.entityimpl.TodoTaskImpl;
import java.util.List;

public interface TodoMultiplePeriodDao {
    public int getFilterTaskTodoQuantity(TodoQueryDTO var1);

    public List<TodoTaskImpl> getTodoTask(TodoQueryDTO var1);

    public List<TodoTaskImpl> getTodoTaskComment(TodoQueryDTO var1, List<TodoTaskImpl> var2);

    public List<TodoTaskImpl> getHistoryTodoTask(TodoQueryDTO var1);
}

