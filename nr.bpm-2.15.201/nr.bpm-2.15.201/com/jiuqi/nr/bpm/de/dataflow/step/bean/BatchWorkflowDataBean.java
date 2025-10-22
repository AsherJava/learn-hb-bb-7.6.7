/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.de.dataflow.step.bean;

import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowDataBean;
import java.util.List;

public class BatchWorkflowDataBean
extends WorkflowDataBean {
    private List<WorkflowDataBean> workflowList;

    public List<WorkflowDataBean> getWorkflowDataList() {
        return this.workflowList;
    }

    public void setWorkflowDataList(List<WorkflowDataBean> workflowList) {
        this.workflowList = workflowList;
    }
}

