/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.AgingFetchTypeEnum
 *  com.jiuqi.bde.common.constant.AgingPeriodTypeEnum
 */
package com.jiuqi.bde.bizmodel.execute.model.aging;

import com.jiuqi.bde.bizmodel.execute.intf.ModelExecuteContext;
import com.jiuqi.bde.common.constant.AgingFetchTypeEnum;
import com.jiuqi.bde.common.constant.AgingPeriodTypeEnum;

public class AgingExecuteContext
extends ModelExecuteContext {
    private AgingFetchTypeEnum agingFetchType;
    private AgingPeriodTypeEnum agingPeriodType;
    private int agingStartPeriod;
    private int agingEndPeriod;

    public AgingPeriodTypeEnum getAgingPeriodType() {
        return this.agingPeriodType;
    }

    public void setAgingPeriodType(AgingPeriodTypeEnum agingPeriodType) {
        this.agingPeriodType = agingPeriodType;
    }

    public int getAgingStartPeriod() {
        return this.agingStartPeriod;
    }

    public void setAgingStartPeriod(int agingStartPeriod) {
        this.agingStartPeriod = agingStartPeriod;
    }

    public int getAgingEndPeriod() {
        return this.agingEndPeriod;
    }

    public void setAgingEndPeriod(int agingEndPeriod) {
        this.agingEndPeriod = agingEndPeriod;
    }

    public AgingFetchTypeEnum getAgingFetchType() {
        return this.agingFetchType;
    }

    public void setAgingFetchType(AgingFetchTypeEnum agingFetchType) {
        this.agingFetchType = agingFetchType;
    }

    @Override
    public String toString() {
        return "AgingExecuteContext [agingPeriodType=" + this.agingPeriodType + ", agingStartPeriod=" + this.agingStartPeriod + ", agingEndPeriod=" + this.agingEndPeriod + ", toString()=" + super.toString() + "]";
    }
}

