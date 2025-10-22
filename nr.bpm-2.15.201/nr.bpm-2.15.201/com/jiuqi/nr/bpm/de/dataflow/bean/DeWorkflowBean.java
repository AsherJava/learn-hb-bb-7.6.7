/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.de.dataflow.bean;

import com.jiuqi.nr.bpm.de.dataflow.bean.ActionState;
import com.jiuqi.nr.bpm.de.dataflow.bean.ReadOnlyBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowDataInfo;
import java.util.List;

public class DeWorkflowBean {
    private List<WorkflowDataInfo> workflowDataInfoList;
    private ActionState actionState;
    private ReadOnlyBean readOnlyBean;

    public List<WorkflowDataInfo> getWorkflowDataInfoList() {
        return this.workflowDataInfoList;
    }

    public void setWorkflowDataInfoList(List<WorkflowDataInfo> workflowDataInfoList) {
        this.workflowDataInfoList = workflowDataInfoList;
    }

    public ActionState getActionState() {
        return this.actionState;
    }

    public void setActionState(ActionState actionState) {
        this.actionState = actionState;
    }

    public ReadOnlyBean getReadOnlyBean() {
        return this.readOnlyBean;
    }

    public void setReadOnlyBean(ReadOnlyBean readOnlyBean) {
        this.readOnlyBean = readOnlyBean;
    }
}

