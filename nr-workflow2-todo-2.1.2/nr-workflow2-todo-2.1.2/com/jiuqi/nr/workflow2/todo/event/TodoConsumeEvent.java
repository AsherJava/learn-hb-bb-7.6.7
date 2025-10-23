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
import com.jiuqi.nr.workflow2.todo.event.TodoConsumeItem;
import java.util.List;
import org.springframework.context.ApplicationEvent;

public class TodoConsumeEvent
extends ApplicationEvent {
    private static final long serialVersionUID = 7268538820220385603L;
    private final TaskDefine task;
    private final FormSchemeDefine formScheme;
    private final List<TodoConsumeItem> todoConsumeItems;

    public TodoConsumeEvent(Object source, TaskDefine task, FormSchemeDefine formScheme, List<TodoConsumeItem> todoConsumeItems) {
        super(source);
        this.task = task;
        this.formScheme = formScheme;
        this.todoConsumeItems = todoConsumeItems;
    }

    public TaskDefine getTask() {
        return this.task;
    }

    public FormSchemeDefine getFormScheme() {
        return this.formScheme;
    }

    public Iterable<TodoConsumeItem> todoItems() {
        return this.todoConsumeItems;
    }
}

