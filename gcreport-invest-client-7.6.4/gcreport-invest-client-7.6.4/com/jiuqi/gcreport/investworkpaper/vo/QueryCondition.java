/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.investworkpaper.vo;

import java.util.List;

public class QueryCondition {
    private String taskId;
    private String schemeId;
    private String orgType;
    private String orgId;
    private String periodStr;
    private String currency;
    private String selectedShowType;
    private Boolean showRuleDetails;
    private Boolean exportAllTable;
    private List<String> showTypes;
    private String ruleId;
    private String columnProp;
    private String investBillId;
    private String investUnitId;
    private String investedUnitId;
    private String pentrateType;
    private String selectAdjustCode;
    private List<String> otherShowColumns;
    private List<String> otherShowColumnTitles;
    private int pageNum;
    private int pageSize;

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

    public String getOrgId() {
        return this.orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSelectedShowType() {
        return this.selectedShowType;
    }

    public void setSelectedShowType(String selectedShowType) {
        this.selectedShowType = selectedShowType;
    }

    public Boolean getShowRuleDetails() {
        return this.showRuleDetails;
    }

    public void setShowRuleDetails(Boolean showRuleDetails) {
        this.showRuleDetails = showRuleDetails;
    }

    public Boolean getExportAllTable() {
        return this.exportAllTable;
    }

    public void setExportAllTable(Boolean exportAllTable) {
        this.exportAllTable = exportAllTable;
    }

    public List<String> getShowTypes() {
        return this.showTypes;
    }

    public void setShowTypes(List<String> showTypes) {
        this.showTypes = showTypes;
    }

    public String getRuleId() {
        return this.ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getColumnProp() {
        return this.columnProp;
    }

    public void setColumnProp(String columnProp) {
        this.columnProp = columnProp;
    }

    public String getInvestBillId() {
        return this.investBillId;
    }

    public void setInvestBillId(String investBillId) {
        this.investBillId = investBillId;
    }

    public String getInvestUnitId() {
        return this.investUnitId;
    }

    public void setInvestUnitId(String investUnitId) {
        this.investUnitId = investUnitId;
    }

    public String getInvestedUnitId() {
        return this.investedUnitId;
    }

    public void setInvestedUnitId(String investedUnitId) {
        this.investedUnitId = investedUnitId;
    }

    public String getPentrateType() {
        return this.pentrateType;
    }

    public void setPentrateType(String pentrateType) {
        this.pentrateType = pentrateType;
    }

    public int getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<String> getOtherShowColumns() {
        return this.otherShowColumns;
    }

    public void setOtherShowColumns(List<String> otherShowColumns) {
        this.otherShowColumns = otherShowColumns;
    }

    public List<String> getOtherShowColumnTitles() {
        return this.otherShowColumnTitles;
    }

    public void setOtherShowColumnTitles(List<String> otherShowColumnTitles) {
        this.otherShowColumnTitles = otherShowColumnTitles;
    }

    public String getSelectAdjustCode() {
        return this.selectAdjustCode;
    }

    public void setSelectAdjustCode(String selectAdjustCode) {
        this.selectAdjustCode = selectAdjustCode;
    }
}

