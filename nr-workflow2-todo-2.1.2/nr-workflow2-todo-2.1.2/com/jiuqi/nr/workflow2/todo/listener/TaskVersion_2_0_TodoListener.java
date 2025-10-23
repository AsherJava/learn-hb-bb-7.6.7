/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.applicationevent.WorkflowSettingsSaveEvent
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.workflow2.todo.listener;

import com.jiuqi.nr.workflow2.engine.core.applicationevent.WorkflowSettingsSaveEvent;
import com.jiuqi.nr.workflow2.todo.utils.TodoUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class TaskVersion_2_0_TodoListener
implements ApplicationListener<WorkflowSettingsSaveEvent> {
    @Autowired
    private TodoUtil todoUtil;

    @Override
    public void onApplicationEvent(@NotNull WorkflowSettingsSaveEvent event) {
        String taskKey = event.getTaskId();
        this.todoUtil.establishTodoDefine(taskKey);
    }
}

