/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.todo.service;

import com.jiuqi.nr.workflow2.todo.entity.TodoFilterCondition;
import com.jiuqi.nr.workflow2.todo.entity.TodoTabInfo;
import com.jiuqi.nr.workflow2.todo.entityimpl.TodoTableDataInfoImpl;
import com.jiuqi.nr.workflow2.todo.env.TodoBaseContext;
import com.jiuqi.nr.workflow2.todo.env.TodoFilterConditionContext;
import com.jiuqi.nr.workflow2.todo.env.TodoTableDataContext;
import com.jiuqi.nr.workflow2.todo.env.TodoTabsQuantityContext;
import java.util.Map;

public interface TodoQueryService {
    public TodoTabInfo getTodoTabInfos(TodoBaseContext var1);

    public Map<String, Integer> getTodoTabsQuantity(TodoTabsQuantityContext var1);

    public TodoFilterCondition getFilterConditions(TodoFilterConditionContext var1);

    public TodoTableDataInfoImpl getTodoTableData(TodoTableDataContext var1);
}

