/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.todo.service;

import com.jiuqi.nr.workflow2.todo.entityimpl.PeriodItem;
import com.jiuqi.nr.workflow2.todo.entityimpl.TodoTableDataExtend;
import java.util.List;

public interface TodoExtendQueryService {
    public List<PeriodItem> getPeriodsWithTodo(String var1);

    public List<TodoTableDataExtend> getTodoTaskExtendInfo(String var1, String var2, String var3);
}

