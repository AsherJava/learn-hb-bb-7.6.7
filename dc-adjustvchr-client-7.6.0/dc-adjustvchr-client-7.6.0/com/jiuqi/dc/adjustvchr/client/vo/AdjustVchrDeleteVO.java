/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.adjustvchr.client.vo;

import java.util.List;

public class AdjustVchrDeleteVO {
    private List<String> delVchrIds;
    private String deleteRange;
    private Integer currentPeriod;
    private String periodType;

    public List<String> getDelVchrIds() {
        return this.delVchrIds;
    }

    public void setDelVchrIds(List<String> delVchrIds) {
        this.delVchrIds = delVchrIds;
    }

    public String getDeleteRange() {
        return this.deleteRange;
    }

    public void setDeleteRange(String deleteRange) {
        this.deleteRange = deleteRange;
    }

    public Integer getCurrentPeriod() {
        return this.currentPeriod;
    }

    public void setCurrentPeriod(Integer currentPeriod) {
        this.currentPeriod = currentPeriod;
    }

    public String getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }
}

