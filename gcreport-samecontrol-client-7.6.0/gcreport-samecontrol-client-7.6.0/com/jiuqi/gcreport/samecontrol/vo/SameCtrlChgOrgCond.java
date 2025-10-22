/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.samecontrol.vo;

public class SameCtrlChgOrgCond {
    private String orgType;
    private String periodStr;
    private String orgCode;

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String toString() {
        return "SameCtrlChgOrgCond{orgType='" + this.orgType + '\'' + ", periodStr='" + this.periodStr + '\'' + ", orgCode='" + this.orgCode + '\'' + '}';
    }
}

