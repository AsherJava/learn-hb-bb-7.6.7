/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.taskscheduling.logquery.client.dto;

import java.util.List;

public class ReBuildParamDTO {
    private List<String> unitCodes;
    private Integer startYear;
    private Integer startPeriod;

    public List<String> getUnitCodes() {
        return this.unitCodes;
    }

    public void setUnitCodes(List<String> unitCodes) {
        this.unitCodes = unitCodes;
    }

    public Integer getStartYear() {
        return this.startYear;
    }

    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }

    public Integer getStartPeriod() {
        return this.startPeriod;
    }

    public void setStartPeriod(Integer startPeriod) {
        this.startPeriod = startPeriod;
    }
}

