/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge
 *  com.jiuqi.nr.workflow2.todo.entity.TodoFilterCondition
 *  com.jiuqi.nr.workflow2.todo.entity.TodoTabInfo
 *  com.jiuqi.nr.workflow2.todo.entityimpl.TodoTableDataInfoImpl
 *  com.jiuqi.nr.workflow2.todo.env.TodoBaseContext
 *  com.jiuqi.nr.workflow2.todo.env.TodoFilterConditionContext
 *  com.jiuqi.nr.workflow2.todo.env.TodoTableDataContext
 *  com.jiuqi.nr.workflow2.todo.env.TodoTabsQuantityContext
 *  com.jiuqi.nr.workflow2.todo.service.TodoQueryService
 */
package com.jiuqi.nr.workflow2.converter.todo;

import com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge;
import com.jiuqi.nr.workflow2.todo.entity.TodoFilterCondition;
import com.jiuqi.nr.workflow2.todo.entity.TodoTabInfo;
import com.jiuqi.nr.workflow2.todo.entityimpl.TodoTableDataInfoImpl;
import com.jiuqi.nr.workflow2.todo.env.TodoBaseContext;
import com.jiuqi.nr.workflow2.todo.env.TodoFilterConditionContext;
import com.jiuqi.nr.workflow2.todo.env.TodoTableDataContext;
import com.jiuqi.nr.workflow2.todo.env.TodoTabsQuantityContext;
import com.jiuqi.nr.workflow2.todo.service.TodoQueryService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class TodoQueryServiceConverter
implements TodoQueryService {
    @Autowired
    @Qualifier(value="todoQueryService_1_0")
    private TodoQueryService todoQueryService_1_0;
    @Autowired
    @Qualifier(value="todoQueryService_2_0")
    private TodoQueryService todoQueryService_2_0;
    @Autowired
    private DefaultEngineVersionJudge defaultEngineVersionJudge;

    public TodoTabInfo getTodoTabInfos(TodoBaseContext context) {
        if (this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(context.getTaskId())) {
            return this.todoQueryService_1_0.getTodoTabInfos(context);
        }
        return this.todoQueryService_2_0.getTodoTabInfos(context);
    }

    public Map<String, Integer> getTodoTabsQuantity(TodoTabsQuantityContext context) {
        if (this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(context.getTaskId())) {
            return this.todoQueryService_1_0.getTodoTabsQuantity(context);
        }
        return this.todoQueryService_2_0.getTodoTabsQuantity(context);
    }

    public TodoFilterCondition getFilterConditions(TodoFilterConditionContext context) {
        if (this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(context.getTaskId())) {
            return this.todoQueryService_1_0.getFilterConditions(context);
        }
        return this.todoQueryService_2_0.getFilterConditions(context);
    }

    public TodoTableDataInfoImpl getTodoTableData(TodoTableDataContext context) {
        if (this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(context.getTaskId())) {
            return this.todoQueryService_1_0.getTodoTableData(context);
        }
        return this.todoQueryService_2_0.getTodoTableData(context);
    }
}

