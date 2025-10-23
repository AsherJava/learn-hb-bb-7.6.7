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
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;

public class RuntimeBusinessKey {
    private final IBusinessKey businessKey;
    private final TaskDefine task;
    private final FormSchemeDefine formScheme;
    private final WorkflowSettingsDO workflowSettings;

    public boolean isFormOrGroupWorkflow() {
        return this.workflowSettings.getWorkflowObjectType() == WorkflowObjectType.FORM || this.workflowSettings.getWorkflowObjectType() == WorkflowObjectType.FORM_GROUP;
    }

    public RuntimeBusinessKey(IBusinessKey businessKey, TaskDefine task, FormSchemeDefine formScheme, WorkflowSettingsDO workflowSettings) {
        if (businessKey == null) {
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
        this.businessKey = businessKey;
        this.task = task;
        this.formScheme = formScheme;
        this.workflowSettings = workflowSettings;
    }

    public IBusinessKey getBusinessKey() {
        return this.businessKey;
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

