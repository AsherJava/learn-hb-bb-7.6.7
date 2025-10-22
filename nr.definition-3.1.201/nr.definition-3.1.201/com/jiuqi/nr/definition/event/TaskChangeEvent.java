/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.event;

import com.jiuqi.nr.definition.event.ParamChangeEvent;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.util.List;
import java.util.stream.Collectors;

public class TaskChangeEvent
extends ParamChangeEvent {
    private final List<ParamChangeEvent.ChangeParam<TaskDefine>> tasks;

    public TaskChangeEvent(ParamChangeEvent.ChangeType type, List<ParamChangeEvent.ChangeParam<TaskDefine>> tasks) {
        super(type, tasks.stream().map(p -> ((TaskDefine)p.getValue()).getKey()).collect(Collectors.toList()));
        this.tasks = tasks;
    }

    public List<TaskDefine> getTasks() {
        return this.tasks.stream().map(ParamChangeEvent.ChangeParam::getValue).collect(Collectors.toList());
    }

    public List<ParamChangeEvent.ChangeParam<TaskDefine>> getChangeParams() {
        return this.tasks;
    }
}

