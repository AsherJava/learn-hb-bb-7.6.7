/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 */
package com.jiuqi.nr.workflow2.todo.event;

import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.todo.entity.TodoInfo;
import com.jiuqi.nr.workflow2.todo.event.TodoItem;

public class TodoSendItem
extends TodoItem {
    private boolean isCanceled = false;

    public TodoSendItem(IBusinessObject businessObject, TodoInfo todoInfo) {
        super(businessObject, todoInfo);
    }

    public void cancel() {
        this.isCanceled = true;
    }

    public boolean isCanceled() {
        return this.isCanceled;
    }
}

