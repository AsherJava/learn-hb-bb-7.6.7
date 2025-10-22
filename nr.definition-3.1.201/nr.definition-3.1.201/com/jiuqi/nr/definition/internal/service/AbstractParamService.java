/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.nr.definition.event.FormSchemePeriodChangeEvent;
import com.jiuqi.nr.definition.event.ParamChangeEvent;
import com.jiuqi.nr.definition.event.TaskChangeEvent;
import com.jiuqi.nr.definition.event.TaskGroupChangeEvent;
import com.jiuqi.nr.definition.event.TaskGroupLinkChangeEvent;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public abstract class AbstractParamService {
    @Autowired
    private ApplicationContext applicationContext;

    protected void onTaskGroupChanged(ParamChangeEvent.ChangeType type, List<String> sources) {
        this.applicationContext.publishEvent(new TaskGroupChangeEvent(type, sources));
    }

    protected void onTaskGroupLinkChanged(ParamChangeEvent.ChangeType type, List<String> sources) {
        this.applicationContext.publishEvent(new TaskGroupLinkChangeEvent(type, sources));
    }

    protected void onTaskChanged(ParamChangeEvent.ChangeType type, TaskDefine newTask, TaskDefine oldTask) {
        this.applicationContext.publishEvent(new TaskChangeEvent(type, Collections.singletonList(new ParamChangeEvent.ChangeParam<TaskDefine>(newTask, oldTask))));
    }

    protected void onFormSchemePeriodChanged(ParamChangeEvent.ChangeType type, List<String> sources) {
        this.applicationContext.publishEvent(new FormSchemePeriodChangeEvent(type, sources));
    }
}

