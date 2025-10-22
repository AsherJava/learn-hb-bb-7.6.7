/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.conversion.conversionrate.vo;

import javax.validation.constraints.NotNull;

public class ConversionRateVO {
    @NotNull(message="\u5468\u671f\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u5468\u671f\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a") String periodId;
    private String periodTitle;
    private String groupId;
    private String groupName;
    private String nodeId;
    private String nodeName;
    private String periodValue;
    private String periodYear;
    private String periodMonth;
    @NotNull(message="\u6e90\u5e01\u79cd\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u6e90\u5e01\u79cd\u4e0d\u80fd\u4e3a\u7a7a") String sourceCurrencyCode;
    private String sourceCurrencyTitle;
    @NotNull(message="\u76ee\u6807\u5e01\u79cd\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u76ee\u6807\u5e01\u79cd\u4e0d\u80fd\u4e3a\u7a7a") String targetCurrencyCode;
    private String targetCurrencyTitle;
    private String treeNodeName;
    private String rateTypeCode;
    private String rateTypeTitle;
    private Double rateValue;

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getNodeId() {
        return this.nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return this.nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public void setTreeNodeName(String treeNodeName) {
        this.treeNodeName = treeNodeName;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getPeriodId() {
        return this.periodId;
    }

    public void setPeriodId(String periodId) {
        this.periodId = periodId;
    }

    public String getTreeNodeName() {
        if (this.getSourceCurrencyTitle() != null && this.getTargetCurrencyTitle() != null) {
            this.treeNodeName = this.sourceCurrencyTitle + "-" + this.targetCurrencyTitle;
        }
        return this.treeNodeName;
    }

    public String getPeriodTitle() {
        return this.periodTitle;
    }

    public void setPeriodTitle(String periodTitle) {
        this.periodTitle = periodTitle;
    }

    public String getPeriodValue() {
        return this.periodValue;
    }

    public void setPeriodValue(String periodValue) {
        this.periodValue = periodValue;
    }

    public String getSourceCurrencyCode() {
        return this.sourceCurrencyCode;
    }

    public void setSourceCurrencyCode(String sourceCurrencyCode) {
        this.sourceCurrencyCode = sourceCurrencyCode;
    }

    public String getSourceCurrencyTitle() {
        return this.sourceCurrencyTitle;
    }

    public void setSourceCurrencyTitle(String sourceCurrencyTitle) {
        this.sourceCurrencyTitle = sourceCurrencyTitle;
    }

    public String getTargetCurrencyCode() {
        return this.targetCurrencyCode;
    }

    public void setTargetCurrencyCode(String targetCurrencyCode) {
        this.targetCurrencyCode = targetCurrencyCode;
    }

    public String getTargetCurrencyTitle() {
        return this.targetCurrencyTitle;
    }

    public void setTargetCurrencyTitle(String targetCurrencyTitle) {
        this.targetCurrencyTitle = targetCurrencyTitle;
    }

    public String getRateTypeCode() {
        return this.rateTypeCode;
    }

    public void setRateTypeCode(String rateTypeCode) {
        this.rateTypeCode = rateTypeCode;
    }

    public String getRateTypeTitle() {
        return this.rateTypeTitle;
    }

    public void setRateTypeTitle(String rateTypeTitle) {
        this.rateTypeTitle = rateTypeTitle;
    }

    public Double getRateValue() {
        return this.rateValue;
    }

    public void setRateValue(Double rateValue) {
        this.rateValue = rateValue;
    }

    public String getPeriodYear() {
        return this.periodYear;
    }

    public void setPeriodYear(String periodYear) {
        this.periodYear = periodYear;
    }

    public String getPeriodMonth() {
        return this.periodMonth;
    }

    public void setPeriodMonth(String periodMonth) {
        this.periodMonth = periodMonth;
    }
}

