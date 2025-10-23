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
import com.jiuqi.nr.workflow2.todo.event.TodoSendItem;
import java.util.List;
import org.springframework.context.ApplicationEvent;

public class BeforeTodoSendEvent
extends ApplicationEvent {
    private static final long serialVersionUID = 434084038303215570L;
    private final TaskDefine task;
    private final FormSchemeDefine formScheme;
    private final List<TodoSendItem> todoItems;

    public BeforeTodoSendEvent(Object source, TaskDefine task, FormSchemeDefine formScheme, List<TodoSendItem> todoItems) {
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

    public Iterable<TodoSendItem> todoItems() {
        return this.todoItems;
    }
}

