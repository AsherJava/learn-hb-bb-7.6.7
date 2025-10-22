/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.workingpaper.vo;

import java.util.List;

public class ArbitrarilyMergeOrgParamVO {
    private List<String> orgCodes;
    private String org_type;
    private String periodStr;

    public List<String> getOrgCodes() {
        return this.orgCodes;
    }

    public void setOrgCodes(List<String> orgCodes) {
        this.orgCodes = orgCodes;
    }

    public String getOrg_type() {
        return this.org_type;
    }

    public void setOrg_type(String org_type) {
        this.org_type = org_type;
    }

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }
}

