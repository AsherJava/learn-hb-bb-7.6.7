/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import java.util.List;

public class DWorkflowConfig {
    private boolean flowStarted;
    private WorkFlowType flowsType;
    private List<EntityViewData> entitys;
    public List<Integer> erroStatus;

    public List<EntityViewData> getEntitys() {
        return this.entitys;
    }

    public void setEntitys(List<EntityViewData> entitys) {
        this.entitys = entitys;
    }

    public List<Integer> getErroStatus() {
        return this.erroStatus;
    }

    public void setErroStatus(List<Integer> erroStatus) {
        this.erroStatus = erroStatus;
    }

    public WorkFlowType getFlowsType() {
        return this.flowsType;
    }

    public void setFlowsType(WorkFlowType flowsType) {
        this.flowsType = flowsType;
    }

    public boolean isFlowStarted() {
        return this.flowStarted;
    }

    public void setFlowStarted(boolean flowStarted) {
        this.flowStarted = flowStarted;
    }
}

