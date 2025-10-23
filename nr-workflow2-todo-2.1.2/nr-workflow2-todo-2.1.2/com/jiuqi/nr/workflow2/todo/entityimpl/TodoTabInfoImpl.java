/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 */
package com.jiuqi.nr.workflow2.todo.entityimpl;

import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.workflow2.todo.entity.TodoTabInfo;
import com.jiuqi.nr.workflow2.todo.extend.env.WorkFlowNode;
import java.util.List;

public class TodoTabInfoImpl
implements TodoTabInfo {
    private List<WorkFlowNode> flowNodes;
    private WorkFlowType flowObjectType;
    private String entityCaliber;
    private String period;
    private String workflowType;
    private boolean submitExplain;
    private boolean backDescriptionNeedWrite;
    private boolean reportDimensionEnable;
    private boolean multiEntityWithReportDimensionEnable;

    @Override
    public List<WorkFlowNode> getFlowNodes() {
        return this.flowNodes;
    }

    public void setFlowNodes(List<WorkFlowNode> flowNodes) {
        this.flowNodes = flowNodes;
    }

    @Override
    public WorkFlowType getFlowObjectType() {
        return this.flowObjectType;
    }

    public void setFlowObjectType(WorkFlowType flowObjectType) {
        this.flowObjectType = flowObjectType;
    }

    @Override
    public String getEntityCaliber() {
        return this.entityCaliber;
    }

    public void setEntityCaliber(String entityCaliber) {
        this.entityCaliber = entityCaliber;
    }

    @Override
    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    @Override
    public String getWorkflowType() {
        return this.workflowType;
    }

    public void setWorkflowType(String workflowType) {
        this.workflowType = workflowType;
    }

    @Override
    public boolean isSubmitExplain() {
        return this.submitExplain;
    }

    public void setSubmitExplain(boolean submitExplain) {
        this.submitExplain = submitExplain;
    }

    @Override
    public boolean isBackDescriptionNeedWrite() {
        return this.backDescriptionNeedWrite;
    }

    public void setBackDescriptionNeedWrite(boolean backDescriptionNeedWrite) {
        this.backDescriptionNeedWrite = backDescriptionNeedWrite;
    }

    @Override
    public boolean isReportDimensionEnable() {
        return this.reportDimensionEnable;
    }

    public void setReportDimensionEnable(boolean reportDimensionEnable) {
        this.reportDimensionEnable = reportDimensionEnable;
    }

    @Override
    public boolean isMultiEntityWithReportDimensionEnable() {
        return this.multiEntityWithReportDimensionEnable;
    }

    public void setMultiEntityWithReportDimensionEnable(boolean multiEntityWithReportDimensionEnable) {
        this.multiEntityWithReportDimensionEnable = multiEntityWithReportDimensionEnable;
    }
}

