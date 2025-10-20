/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.data;

public class AcctPeriodInfo {
    private Integer acctYear;
    private Integer acctPeriod;

    public AcctPeriodInfo() {
    }

    public AcctPeriodInfo(Integer acctYear, Integer acctPeriod) {
        this.acctYear = acctYear;
        this.acctPeriod = acctPeriod;
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
}

