/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.nr.workflow2.todo.event;

import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.workflow2.todo.event.TodoItem;
import java.util.List;
import org.springframework.context.ApplicationEvent;

public class TodoSendEvent
extends ApplicationEvent {
    private static final long serialVersionUID = 108096615726812856L;
    private final TaskDefine task;
    private final FormSchemeDefine formScheme;
    private final List<TodoItem> todoItems;

    public TodoSendEvent(Object source, TaskDefine task, FormSchemeDefine formScheme, List<TodoItem> todoItems) {
        super(source);
        this.task = task;
        this.formScheme = formScheme;
        this.todoItems = todoItems;
    }

    public TaskDefine getTask() {
        return this.task;
    }

    public FormSchemeDefine getFormScheme() {
        return this.formScheme;
    }

    public Iterable<TodoItem> todoItems() {
        return this.todoItems;
    }
}

