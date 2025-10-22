/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.investworkpaper.vo;

import java.util.List;

public class InvestWorkPaperQueryCondition {
    private String taskId;
    private String schemeId;
    private String orgType;
    private String mergeUnitId;
    private String periodStr;
    private String currencyCode;
    private String selectAdjustCode;
    private String systemId;
    private List<String> ruleIds;
    private List<String> investedUnitIds;

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

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

    public String getMergeUnitId() {
        return this.mergeUnitId;
    }

    public void setMergeUnitId(String mergeUnitId) {
        this.mergeUnitId = mergeUnitId;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public List<String> getRuleIds() {
        return this.ruleIds;
    }

    public void setRuleIds(List<String> ruleIds) {
        this.ruleIds = ruleIds;
    }

    public List<String> getInvestedUnitIds() {
        return this.investedUnitIds;
    }

    public void setInvestedUnitIds(List<String> investedUnitIds) {
        this.investedUnitIds = investedUnitIds;
    }

    public String getSelectAdjustCode() {
        return this.selectAdjustCode;
    }

    public void setSelectAdjustCode(String selectAdjustCode) {
        this.selectAdjustCode = selectAdjustCode;
    }

    public String getSystemId() {
        return this.systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }
}

