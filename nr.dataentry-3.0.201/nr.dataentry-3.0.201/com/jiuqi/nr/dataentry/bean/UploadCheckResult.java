/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.bean;

public class UploadCheckResult {
    private String unitCode;
    private String unitTitle;
    private String period;
    private String periodTitle;

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitTitle() {
        return this.unitTitle;
    }

    public void setUnitTitle(String unitTitle) {
        this.unitTitle = unitTitle;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPeriodTitle() {
        return this.periodTitle;
    }

    public void setPeriodTitle(String periodTitle) {
        this.periodTitle = periodTitle;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        UploadCheckResult uploadCheckResult = (UploadCheckResult)o;
        boolean unitCheck = false;
        boolean periodCheck = false;
        if (this.unitCode == uploadCheckResult.unitCode) {
            unitCheck = true;
        } else if (this.unitCode != null && this.unitCode.equals(uploadCheckResult.unitCode)) {
            unitCheck = true;
        }
        if (this.period == uploadCheckResult.period) {
            periodCheck = true;
        } else if (this.period != null && this.period.equals(uploadCheckResult.period)) {
            periodCheck = true;
        }
        return unitCheck && periodCheck;
    }

    public int hashCode() {
        int result = 17;
        result = 31 * result + (this.unitCode == null ? 0 : this.unitCode.hashCode());
        result = 31 * result + (this.period == null ? 0 : this.period.hashCode());
        return result;
    }
}

