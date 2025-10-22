/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodUtil
 */
package com.jiuqi.gcreport.offsetitem.dto;

import com.jiuqi.np.period.PeriodUtil;

public class OffsetVchrCodeDTO {
    private String periodStr;
    private int periodType = -1;
    private int acctYear = -1;
    private int acctPeriod = -1;

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }

    public int getPeriodType() {
        if (this.periodType > -1) {
            return this.periodType;
        }
        return PeriodUtil.getPeriodWrapper((String)this.periodStr).getType();
    }

    public void setPeriodType(int periodType) {
        this.periodType = periodType;
    }

    public int getAcctYear() {
        if (this.acctYear > 0) {
            return this.acctYear;
        }
        return PeriodUtil.getPeriodWrapper((String)this.periodStr).getYear();
    }

    public void setAcctYear(int acctYear) {
        this.acctYear = acctYear;
    }

    public int getAcctPeriod() {
        if (this.acctPeriod > 0) {
            return this.acctPeriod;
        }
        return PeriodUtil.getPeriodWrapper((String)this.periodStr).getPeriod();
    }

    public void setAcctPeriod(int acctPeriod) {
        this.acctPeriod = acctPeriod;
    }
}

