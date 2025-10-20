/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.controller.event;

import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import org.springframework.context.ApplicationEvent;

public class FormSchemeUpdateEvent
extends ApplicationEvent {
    private static final long serialVersionUID = 1L;
    private DesignFormSchemeDefine formScheme;
    private DesignFormSchemeDefine oldFormScheme;
    private DesignTaskDefine taskDefine;
    private DesignTaskDefine oldTaskDefine;
    private TaskFlowsDefine taskFlows;
    private TaskFlowsDefine oldTaskFlows;

    public FormSchemeUpdateEvent(Object define, Object oldDefine, TaskFlowsDefine designTaskFlowsDefine, TaskFlowsDefine oldDesignTaskFlowsDefine) {
        super(define);
        this.taskFlows = designTaskFlowsDefine;
        this.oldTaskFlows = oldDesignTaskFlowsDefine;
        if (define instanceof DesignFormSchemeDefine) {
            this.formScheme = (DesignFormSchemeDefine)define;
            this.oldFormScheme = (DesignFormSchemeDefine)oldDefine;
        } else if (define instanceof DesignTaskDefine) {
            this.taskDefine = (DesignTaskDefine)define;
            this.oldTaskDefine = (DesignTaskDefine)oldDefine;
        }
    }

    public DesignFormSchemeDefine getFormScheme() {
        return this.formScheme;
    }

    public void setFormScheme(DesignFormSchemeDefine formScheme) {
        this.formScheme = formScheme;
    }

    public DesignFormSchemeDefine getOldFormScheme() {
        return this.oldFormScheme;
    }

    public void setOldFormScheme(DesignFormSchemeDefine oldFormScheme) {
        this.oldFormScheme = oldFormScheme;
    }

    public DesignTaskDefine getTaskDefine() {
        return this.taskDefine;
    }

    public void setTaskDefine(DesignTaskDefine taskDefine) {
        this.taskDefine = taskDefine;
    }

    public DesignTaskDefine getOldTaskDefine() {
        return this.oldTaskDefine;
    }

    public void setOldTaskDefine(DesignTaskDefine oldTaskDefine) {
        this.oldTaskDefine = oldTaskDefine;
    }

    public TaskFlowsDefine getTaskFlows() {
        return this.taskFlows;
    }

    public void setTaskFlows(TaskFlowsDefine taskFlows) {
        this.taskFlows = taskFlows;
    }

    public TaskFlowsDefine getOldTaskFlows() {
        return this.oldTaskFlows;
    }

    public void setOldTaskFlows(TaskFlowsDefine oldTaskFlows) {
        this.oldTaskFlows = oldTaskFlows;
    }
}

