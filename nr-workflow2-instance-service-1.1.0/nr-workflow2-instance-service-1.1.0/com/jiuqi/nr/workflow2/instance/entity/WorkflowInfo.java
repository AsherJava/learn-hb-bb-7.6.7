/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 */
package com.jiuqi.nr.workflow2.instance.entity;

import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.instance.entity.InfoItem;

public class WorkflowInfo {
    private boolean showEditWorkflowButton;
    private InfoItem workflowDefine;
    private WorkflowObjectType workflowObjectType;

    public boolean isShowEditWorkflowButton() {
        return this.showEditWorkflowButton;
    }

    public void setShowEditWorkflowButton(boolean showEditWorkflowButton) {
        this.showEditWorkflowButton = showEditWorkflowButton;
    }

    public InfoItem getWorkflowDefine() {
        return this.workflowDefine;
    }

    public void setWorkflowDefine(InfoItem workflowDefine) {
        this.workflowDefine = workflowDefine;
    }

    public WorkflowObjectType getWorkflowObjectType() {
        return this.workflowObjectType;
    }

    public void setWorkflowObjectType(WorkflowObjectType workflowObjectType) {
        this.workflowObjectType = workflowObjectType;
    }
}

