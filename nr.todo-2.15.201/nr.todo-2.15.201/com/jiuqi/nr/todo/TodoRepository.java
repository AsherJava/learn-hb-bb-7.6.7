/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.todo;

import com.jiuqi.nr.todo.entity.TodoPO;
import com.jiuqi.nr.todo.entity.TodoVO;
import java.time.LocalDateTime;
import java.util.List;

public interface TodoRepository {
    public List<TodoVO> list(List<String> var1);

    public boolean save(TodoPO var1);

    public TodoVO get(String var1);

    public void delete(String var1);

    public boolean updateCompleteTime(String var1, String var2, LocalDateTime var3);

    public boolean updateCompleteTime(List<String> var1, LocalDateTime var2);

    public boolean updateCompleteTimeByFormSchemeKey(String var1, String var2, LocalDateTime var3);

    public List<TodoVO> compeleteTodoList(List<String> var1);
}

