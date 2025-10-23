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
import com.jiuqi.nr.workflow2.todo.event.BeforeTodoSendEvent;
import com.jiuqi.nr.workflow2.todo.event.TodoSendItem;
import java.util.List;

public class BeforeApplyreturnTodoSendEvent
extends BeforeTodoSendEvent {
    private static final long serialVersionUID = 2603644325162973244L;

    public BeforeApplyreturnTodoSendEvent(Object source, TaskDefine task, FormSchemeDefine formScheme, List<TodoSendItem> todoItems) {
        super(source, task, formScheme, todoItems);
    }
}

