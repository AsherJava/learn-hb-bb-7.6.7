/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.activiti.engine.task.IdentityLink
 */
package com.jiuqi.nr.bpm.todo.service.impl;

import com.jiuqi.nr.bpm.Actor.Actor;
import com.jiuqi.nr.bpm.ProcessEngineProvider;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.service.RunTimeService;
import com.jiuqi.nr.bpm.todo.entity.TodoItemEntity;
import com.jiuqi.nr.bpm.todo.service.TodoItemService;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.activiti.engine.task.IdentityLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoItemServiceImpl
implements TodoItemService {
    @Autowired
    private ProcessEngineProvider processEngineProvider;

    @Override
    public List<TodoItemEntity> personTodo() {
        ArrayList<TodoItemEntity> todoItemList = new ArrayList<TodoItemEntity>();
        TodoItemEntity todoItem = new TodoItemEntity();
        Actor actor = Actor.fromNpContext();
        RunTimeService runTimeService = this.processEngineProvider.getProcessEngine().get().getRunTimeService();
        List<Task> taskList = runTimeService.queryTasks(actor);
        for (Task task : taskList) {
            todoItem.setId(task.getId());
            todoItem.setQueryParam(task.getProcessInstanceId());
            todoItem.setTitle(task.getName());
            Set<IdentityLink> actors = task.getIdentityLink();
            todoItem.setActors(actors);
            todoItemList.add(todoItem);
        }
        return todoItemList;
    }
}

