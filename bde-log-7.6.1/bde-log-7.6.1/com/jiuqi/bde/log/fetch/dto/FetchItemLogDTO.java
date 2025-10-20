/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.log.fetch.dto;

public class FetchItemLogDTO {
    private String runnerId;
    private String unitCode;
    private String unitName;
    private String periodScheme;
    private String currency;
    private String logDigest;
    private String log;

    public String getRunnerId() {
        return this.runnerId;
    }

    public void setRunnerId(String runnerId) {
        this.runnerId = runnerId;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitName() {
        return this.unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getPeriodScheme() {
        return this.periodScheme;
    }

    public void setPeriodScheme(String periodScheme) {
        this.periodScheme = periodScheme;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getLogDigest() {
        return this.logDigest;
    }

    public void setLogDigest(String logDigest) {
        this.logDigest = logDigest;
    }

    public String getLog() {
        return this.log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String toString() {
        return "FetchItemLogDTO [runnerId=" + this.runnerId + ", unitCode=" + this.unitCode + ", unitName=" + this.unitName + ", periodScheme=" + this.periodScheme + ", currency=" + this.currency + ", logDigest=" + this.logDigest + ", log=" + this.log + "]";
    }
}

