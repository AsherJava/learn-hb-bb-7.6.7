/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.todo;

import com.jiuqi.nr.todo.bean.TodoParam;
import com.jiuqi.nr.todo.bean.TodoResult;
import com.jiuqi.nr.todo.entity.TodoVO;
import java.util.List;
import java.util.Map;

public interface TodoService {
    public boolean complete(List<String> var1, String var2, String var3);

    public void doAction(List<String> var1, String var2, String var3, String var4, Map<String, Object> var5);

    public boolean existNewTodo(String var1, long var2);

    public TodoResult queryTodoParam(TodoParam var1);

    public List<TodoVO> todoList(List<String> var1, int var2);
}

