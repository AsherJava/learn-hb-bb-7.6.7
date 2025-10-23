/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataentry.bean.LogInfo
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 */
package com.jiuqi.nr.workflow2.converter.dataentry.manager.dto;

import com.jiuqi.nr.dataentry.bean.LogInfo;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import java.util.List;

public class BatchExecManagerDTO {
    private FormSchemeDefine formSchemeDefine;
    private String period;
    private WorkflowObjectType workflowObjectType;
    private List<String> formKeys;
    private List<String> formGroupKeys;
    private IUserAction userAction;
    private LogInfo logInfo;

    public FormSchemeDefine getFormSchemeDefine() {
        return this.formSchemeDefine;
    }

    public void setFormSchemeDefine(FormSchemeDefine formSchemeDefine) {
        this.formSchemeDefine = formSchemeDefine;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public WorkflowObjectType getWorkflowObjectType() {
        return this.workflowObjectType;
    }

    public void setWorkflowObjectType(WorkflowObjectType workflowObjectType) {
        this.workflowObjectType = workflowObjectType;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }

    public List<String> getFormGroupKeys() {
        return this.formGroupKeys;
    }

    public void setFormGroupKeys(List<String> formGroupKeys) {
        this.formGroupKeys = formGroupKeys;
    }

    public IUserAction getUserAction() {
        return this.userAction;
    }

    public void setUserAction(IUserAction userAction) {
        this.userAction = userAction;
    }

    public LogInfo getLogInfo() {
        return this.logInfo;
    }

    public void setLogInfo(LogInfo logInfo) {
        this.logInfo = logInfo;
    }
}

