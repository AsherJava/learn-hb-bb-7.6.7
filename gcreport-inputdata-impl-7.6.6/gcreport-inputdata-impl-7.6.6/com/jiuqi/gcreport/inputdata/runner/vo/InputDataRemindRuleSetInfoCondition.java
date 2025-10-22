/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.inputdata.runner.vo;

import java.util.Set;

public class InputDataRemindRuleSetInfoCondition {
    private Set<String> unitCodes;
    private Set<String> oppUnitCodes;
    private Set<String> ruleIds;

    public Set<String> getUnitCodes() {
        return this.unitCodes;
    }

    public void setUnitCodes(Set<String> unitCodes) {
        this.unitCodes = unitCodes;
    }

    public Set<String> getOppUnitCodes() {
        return this.oppUnitCodes;
    }

    public void setOppUnitCodes(Set<String> oppUnitCodes) {
        this.oppUnitCodes = oppUnitCodes;
    }

    public Set<String> getRuleIds() {
        return this.ruleIds;
    }

    public void setRuleIds(Set<String> ruleIds) {
        this.ruleIds = ruleIds;
    }
}

