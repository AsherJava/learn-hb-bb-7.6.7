/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.nr.workflow2.engine.core.process.runtime.common;

import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;

public class RuntimeBusinessKeyCollection {
    private final IBusinessKeyCollection businessKeys;
    private final TaskDefine task;
    private final FormSchemeDefine formScheme;
    private final WorkflowSettingsDO workflowSettings;

    public RuntimeBusinessKeyCollection(IBusinessKeyCollection businessKeys, TaskDefine task, FormSchemeDefine formScheme, WorkflowSettingsDO workflowSettings) {
        if (businessKeys == null) {
            throw new IllegalArgumentException("'businessKey' must not be null.");
        }
        if (task == null) {
            throw new IllegalArgumentException("'task' must not be null.");
        }
        if (formScheme == null) {
            throw new IllegalArgumentException("'formScheme' must not be null.");
        }
        if (workflowSettings == null) {
            throw new IllegalArgumentException("'workflowSettings' must not be null.");
        }
        this.businessKeys = businessKeys;
        this.task = task;
        this.formScheme = formScheme;
        this.workflowSettings = workflowSettings;
    }

    public IBusinessKeyCollection getBusinessKeys() {
        return this.businessKeys;
    }

    public TaskDefine getTask() {
        return this.task;
    }

    public String getTaskKey() {
        return this.task.getKey();
    }

    public FormSchemeDefine getFormScheme() {
        return this.formScheme;
    }

    public String getFormSchemeKey() {
        return this.formScheme.getKey();
    }

    public WorkflowSettingsDO getWorkflowSettings() {
        return this.workflowSettings;
    }
}

