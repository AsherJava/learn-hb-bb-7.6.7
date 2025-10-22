/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.setting.tree.pojo;

import com.jiuqi.nr.bpm.setting.tree.pojo.IFlowNode;
import java.util.Date;

public class WorkflowData
implements IFlowNode {
    private String key;
    private String title;
    private String dataType;
    private String dataId;
    private String workflowId;
    private Date effectiveTime;
    private boolean defaultWorkflow;
    private int type;

    @Override
    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDataType() {
        return this.dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataId() {
        return this.dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getWorkflowId() {
        return this.workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public Date getEffectiveTime() {
        return this.effectiveTime;
    }

    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public boolean isDefaultWorkflow() {
        return this.defaultWorkflow;
    }

    public void setDefaultWorkflow(boolean defaultWorkflow) {
        this.defaultWorkflow = defaultWorkflow;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

