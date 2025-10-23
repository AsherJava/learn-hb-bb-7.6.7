/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.instance.vo;

import com.jiuqi.nr.workflow2.instance.entity.PeriodComponentParam;
import com.jiuqi.nr.workflow2.instance.entity.WorkflowInfo;
import com.jiuqi.nr.workflow2.instance.vo.InstanceInitDataVO;

public class InstanceInitDataVOImpl
implements InstanceInitDataVO {
    private String entityCaliber;
    private String entityDimensionName;
    private String curPeriod;
    private String adjust;
    private WorkflowInfo workflow;
    private PeriodComponentParam periodComponentParam;

    @Override
    public String getEntityCaliber() {
        return this.entityCaliber;
    }

    public void setEntityCaliber(String entityCaliber) {
        this.entityCaliber = entityCaliber;
    }

    @Override
    public String getEntityDimensionName() {
        return this.entityDimensionName;
    }

    public void setEntityDimensionName(String entityDimensionName) {
        this.entityDimensionName = entityDimensionName;
    }

    @Override
    public String getCurPeriod() {
        return this.curPeriod;
    }

    public void setCurPeriod(String curPeriod) {
        this.curPeriod = curPeriod;
    }

    @Override
    public String getAdjust() {
        return this.adjust;
    }

    public void setAdjust(String adjust) {
        this.adjust = adjust;
    }

    @Override
    public WorkflowInfo getWorkflow() {
        return this.workflow;
    }

    public void setWorkflow(WorkflowInfo workflow) {
        this.workflow = workflow;
    }

    @Override
    public PeriodComponentParam getPeriodComponentParam() {
        return this.periodComponentParam;
    }

    public void setPeriodComponentParam(PeriodComponentParam periodComponentParam) {
        this.periodComponentParam = periodComponentParam;
    }
}

