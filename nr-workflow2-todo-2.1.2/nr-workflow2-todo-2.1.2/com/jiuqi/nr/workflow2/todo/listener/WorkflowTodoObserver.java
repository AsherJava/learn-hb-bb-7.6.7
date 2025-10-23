/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.observer.MessageType
 *  com.jiuqi.np.definition.observer.NpDefinitionObserver
 *  com.jiuqi.np.definition.observer.Observer
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.event.ParamChangeEvent$ChangeType
 *  com.jiuqi.nr.definition.event.TaskChangeEvent
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.nr.workflow2.todo.listener;

import com.jiuqi.np.definition.observer.MessageType;
import com.jiuqi.np.definition.observer.NpDefinitionObserver;
import com.jiuqi.np.definition.observer.Observer;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.event.ParamChangeEvent;
import com.jiuqi.nr.definition.event.TaskChangeEvent;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.workflow2.todo.service.TodoManipulationService;
import com.jiuqi.nr.workflow2.todo.utils.TodoUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

@NpDefinitionObserver(type={MessageType.NRPUBLISHTASK})
public class WorkflowTodoObserver
implements Observer,
ApplicationListener<TaskChangeEvent> {
    @Autowired
    private TodoUtil todoUtil;
    @Autowired
    private TodoManipulationService todoManipulationService;
    @Autowired
    private IRunTimeViewController runTimeViewController;

    public boolean isAsyn() {
        return false;
    }

    public void excute(Object[] objs) throws Exception {
        if (objs == null) {
            return;
        }
        this.todoUtil.establishTodoGroup();
        for (Object obj : objs) {
            String taskKey;
            TaskDefine taskDefine;
            if (!(obj instanceof String) || !(taskDefine = this.runTimeViewController.getTask(taskKey = (String)obj)).getVersion().equals("1.0")) continue;
            this.todoUtil.establishTodoDefine(taskKey);
        }
    }

    @Override
    public void onApplicationEvent(TaskChangeEvent event) {
        ParamChangeEvent.ChangeType changeType = event.getType();
        List taskDefines = event.getTasks();
        if (changeType.equals((Object)ParamChangeEvent.ChangeType.DELETE) && taskDefines != null && !taskDefines.isEmpty()) {
            for (TaskDefine taskDefine : taskDefines) {
                this.todoUtil.deleteTodoDefine(taskDefine.getTaskCode());
                this.todoManipulationService.deleteTodoMessageByTaskId(taskDefine.getKey());
            }
        }
    }
}

