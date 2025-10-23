/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.todo.service;

import com.jiuqi.nr.workflow2.todo.entity.TodoConsumeInfo;
import com.jiuqi.nr.workflow2.todo.entity.TodoDeleteInfo;
import com.jiuqi.nr.workflow2.todo.entity.TodoInfo;
import java.util.List;

public interface TodoManipulationService {
    public boolean createTodo(TodoInfo var1);

    public boolean batchCreateTodo(List<TodoInfo> var1);

    public boolean createUpdateTodo(TodoInfo var1);

    public boolean batchCreateUpdateTodo(List<TodoInfo> var1);

    public boolean consumeTodo(TodoConsumeInfo var1);

    public boolean batchConsumeTodo(List<TodoConsumeInfo> var1);

    public boolean batchClearTodo(List<String> var1);

    public boolean deleteTodo(TodoDeleteInfo var1);

    public boolean batchDeleteTodo(List<TodoDeleteInfo> var1);

    public boolean deleteTodoMessageByFormSchemeKey(String var1);

    public boolean deleteTodoMessageByTaskId(String var1);

    public boolean deleteTodoMessageByCurrentUser(String var1);
}

