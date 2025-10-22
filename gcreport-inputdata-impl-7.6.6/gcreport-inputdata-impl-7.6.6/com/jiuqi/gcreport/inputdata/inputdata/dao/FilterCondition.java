/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.inputdata.inputdata.dao;

import java.util.List;

public class FilterCondition {
    private String schemeKey;
    private String nrPeriod;
    private String unitId;
    private String oppUnitId;
    private String currency;
    private String ruleId;
    private List<String> localSubjectIds;
    private List<String> oppSubjectIds;
    private String offsetState;

    public String getSchemeKey() {
        return this.schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    public String getNrPeriod() {
        return this.nrPeriod;
    }

    public void setNrPeriod(String nrPeriod) {
        this.nrPeriod = nrPeriod;
    }

    public String getUnitId() {
        return this.unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getOppUnitId() {
        return this.oppUnitId;
    }

    public void setOppUnitId(String oppUnitId) {
        this.oppUnitId = oppUnitId;
    }

    public String getRuleId() {
        return this.ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<String> getLocalSubjectIds() {
        return this.localSubjectIds;
    }

    public void setLocalSubjectIds(List<String> localSubjectIds) {
        this.localSubjectIds = localSubjectIds;
    }

    public List<String> getOppSubjectIds() {
        return this.oppSubjectIds;
    }

    public void setOppSubjectIds(List<String> oppSubjectIds) {
        this.oppSubjectIds = oppSubjectIds;
    }

    public String getOffsetState() {
        return this.offsetState;
    }

    public void setOffsetState(String offsetState) {
        this.offsetState = offsetState;
    }
}

