/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.va.mapper.runner.StorageSyncFinishedEvent
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.workflow2.todo.listener;

import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.workflow2.todo.utils.TodoUtil;
import com.jiuqi.va.mapper.runner.StorageSyncFinishedEvent;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component(value="TodoCatTableListener")
public class TodoCatTableListener
implements ApplicationListener<StorageSyncFinishedEvent> {
    @Resource
    private TodoUtil todoUtil;
    @Resource
    private IRunTimeViewController iRunTimeViewController;

    @Override
    public void onApplicationEvent(StorageSyncFinishedEvent event) {
        this.todoUtil.establishTodoGroup();
        List allTaskDefines = this.iRunTimeViewController.getAllTaskDefines();
        for (TaskDefine taskDefine : allTaskDefines) {
            this.todoUtil.establishTodoDefine(taskDefine.getKey());
        }
    }
}

