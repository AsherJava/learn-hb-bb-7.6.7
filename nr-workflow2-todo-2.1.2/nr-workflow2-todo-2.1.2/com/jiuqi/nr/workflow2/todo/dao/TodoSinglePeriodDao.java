/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.todo.dao;

import com.jiuqi.nr.workflow2.todo.entityimpl.TodoItem;
import com.jiuqi.nr.workflow2.todo.entityimpl.TodoTaskImpl;
import com.jiuqi.nr.workflow2.todo.env.TodoTableDataContext;
import com.jiuqi.nr.workflow2.todo.envimpl.PageInfo;
import java.util.List;

public interface TodoSinglePeriodDao {
    public int getTaskTodoQuantity(String var1);

    public int getNodeTodoQuantity(String var1, String var2, String var3, String var4);

    public int getFilterTaskTodoQuantity(TodoTableDataContext var1);

    public List<String> getPeriodsWithTodo(String var1);

    public boolean isTodoExistWithEntityCaliber(String var1, String var2, String var3);

    public List<TodoItem> getTodoTask(String var1, PageInfo var2);

    public List<TodoTaskImpl> getTodoTask(TodoTableDataContext var1);

    public List<TodoTaskImpl> getTodoTaskComment(TodoTableDataContext var1, List<TodoTaskImpl> var2);

    public List<TodoItem> getHistoryTodoTask(String var1, PageInfo var2);

    public List<TodoTaskImpl> getHistoryTodoTask(TodoTableDataContext var1);
}

