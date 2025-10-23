/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zbquery.rest.vo;

import java.util.ArrayList;
import java.util.List;

public class OrgInfoVO {
    private String dataSchemeCode;
    private String entityCode;
    private String periodValue;
    private String orgValue;
    private List<String> orgValues = new ArrayList<String>();

    public OrgInfoVO() {
    }

    public OrgInfoVO(String dataSchemeCode, String entityCode) {
        this.dataSchemeCode = dataSchemeCode;
        this.entityCode = entityCode;
    }

    public String getDataSchemeCode() {
        return this.dataSchemeCode;
    }

    public void setDataSchemeCode(String dataSchemeCode) {
        this.dataSchemeCode = dataSchemeCode;
    }

    public String getEntityCode() {
        return this.entityCode;
    }

    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }

    public String getPeriodValue() {
        return this.periodValue;
    }

    public void setPeriodValue(String periodValue) {
        this.periodValue = periodValue;
    }

    public String getOrgValue() {
        return this.orgValue;
    }

    public void setOrgValue(String orgValue) {
        this.orgValue = orgValue;
    }

    public List<String> getOrgValues() {
        return this.orgValues;
    }

    public void setOrgValues(List<String> orgValues) {
        this.orgValues = orgValues;
    }
}

