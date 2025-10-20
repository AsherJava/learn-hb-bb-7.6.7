/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.plugin.common.core.convert;

import java.util.List;

public class BdeDataTmplParam {
    private boolean includeUncharged;
    private Integer acctYear;
    private Integer acctPeriod;
    private List<String> unitCodes;
    private String agingPeriodType;
    private Integer agingStartPeriod;
    private Integer agingEndPeriod;
    private String agingFetchDate;

    public boolean isIncludeUncharged() {
        return this.includeUncharged;
    }

    public void setIncludeUncharged(boolean includeUncharged) {
        this.includeUncharged = includeUncharged;
    }

    public Integer getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(Integer acctYear) {
        this.acctYear = acctYear;
    }

    public Integer getAcctPeriod() {
        return this.acctPeriod;
    }

    public void setAcctPeriod(Integer acctPeriod) {
        this.acctPeriod = acctPeriod;
    }

    public List<String> getUnitCodes() {
        return this.unitCodes;
    }

    public void setUnitCodes(List<String> unitCodes) {
        this.unitCodes = unitCodes;
    }

    public String getAgingPeriodType() {
        return this.agingPeriodType;
    }

    public void setAgingPeriodType(String agingPeriodType) {
        this.agingPeriodType = agingPeriodType;
    }

    public Integer getAgingStartPeriod() {
        return this.agingStartPeriod;
    }

    public void setAgingStartPeriod(Integer agingStartPeriod) {
        this.agingStartPeriod = agingStartPeriod;
    }

    public Integer getAgingEndPeriod() {
        return this.agingEndPeriod;
    }

    public void setAgingEndPeriod(Integer agingEndPeriod) {
        this.agingEndPeriod = agingEndPeriod;
    }

    public String getAgingFetchDate() {
        return this.agingFetchDate;
    }

    public void setAgingFetchDate(String agingFetchDate) {
        this.agingFetchDate = agingFetchDate;
    }
}

