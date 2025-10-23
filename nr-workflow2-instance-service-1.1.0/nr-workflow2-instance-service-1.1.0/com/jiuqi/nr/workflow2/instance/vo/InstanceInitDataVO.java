/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.instance.vo;

import com.jiuqi.nr.workflow2.instance.entity.PeriodComponentParam;
import com.jiuqi.nr.workflow2.instance.entity.WorkflowInfo;

public interface InstanceInitDataVO {
    public String getEntityCaliber();

    public String getEntityDimensionName();

    public String getCurPeriod();

    public String getAdjust();

    public WorkflowInfo getWorkflow();

    public PeriodComponentParam getPeriodComponentParam();
}

