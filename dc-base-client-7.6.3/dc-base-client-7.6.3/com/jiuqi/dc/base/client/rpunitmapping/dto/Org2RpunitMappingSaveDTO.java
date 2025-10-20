/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.client.rpunitmapping.dto;

import java.io.Serializable;

public class Org2RpunitMappingSaveDTO
implements Serializable {
    private static final long serialVersionUID = -7553213173492477629L;
    private int acctYear;
    private int acctPeriod;
    private String unitCode;
    private String reportUnitCode;

    public int getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(int acctYear) {
        this.acctYear = acctYear;
    }

    public int getAcctPeriod() {
        return this.acctPeriod;
    }

    public void setAcctPeriod(int acctPeriod) {
        this.acctPeriod = acctPeriod;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getReportUnitCode() {
        return this.reportUnitCode;
    }

    public void setReportUnitCode(String reportUnitCode) {
        this.reportUnitCode = reportUnitCode;
    }
}

