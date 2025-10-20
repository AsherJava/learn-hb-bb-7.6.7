/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.inputdata.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class InputRuleFilterCondition {
    private String taskId;
    private String nrPeriod;
    private String relUnitId1;
    private String relUnitId2;
    private String currency;
    private String orgType;
    private String orgId;
    private String ruleId;
    private String offsetState;
    private String checkState;
    private String schemeId;
    private String selectAdjustCode;
    private Boolean convertCheckOrgFlag = false;
    private Set<String> checkOrgCodes;
    private Set<String> checkOppUnitIds;

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getNrPeriod() {
        return this.nrPeriod;
    }

    public void setNrPeriod(String nrPeriod) {
        this.nrPeriod = nrPeriod;
    }

    public String getRelUnitId1() {
        return this.relUnitId1;
    }

    public void setRelUnitId1(String relUnitId1) {
        this.relUnitId1 = relUnitId1;
    }

    public String getRelUnitId2() {
        return this.relUnitId2;
    }

    public void setRelUnitId2(String relUnitId2) {
        this.relUnitId2 = relUnitId2;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getOrgId() {
        return this.orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getRuleId() {
        return this.ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getOffsetState() {
        return this.offsetState;
    }

    public void setOffsetState(String offsetState) {
        this.offsetState = offsetState;
    }

    public String getCheckState() {
        return this.checkState;
    }

    public void setCheckState(String checkState) {
        this.checkState = checkState;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getSelectAdjustCode() {
        return this.selectAdjustCode;
    }

    public void setSelectAdjustCode(String selectAdjustCode) {
        this.selectAdjustCode = selectAdjustCode;
    }

    public Set<String> getCheckOrgCodes() {
        return this.checkOrgCodes;
    }

    public void setCheckOrgCodes(Set<String> checkOrgCodes) {
        this.checkOrgCodes = checkOrgCodes;
    }

    public Set<String> getCheckOppUnitIds() {
        return this.checkOppUnitIds;
    }

    public void setCheckOppUnitIds(Set<String> checkOppUnitIds) {
        this.checkOppUnitIds = checkOppUnitIds;
    }

    public List<String> getRelUnitIds() {
        ArrayList<String> unitIds = new ArrayList<String>();
        unitIds.add(this.relUnitId1);
        unitIds.add(this.relUnitId2);
        if (this.convertCheckOrgFlag.booleanValue()) {
            unitIds.addAll(this.checkOrgCodes);
            unitIds.addAll(this.checkOppUnitIds);
        }
        return unitIds;
    }

    public Boolean getConvertCheckOrgFlag() {
        return this.convertCheckOrgFlag;
    }

    public void setConvertCheckOrgFlag(Boolean convertCheckOrgFlag) {
        this.convertCheckOrgFlag = convertCheckOrgFlag;
    }
}

