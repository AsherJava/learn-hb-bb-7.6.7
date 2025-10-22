/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.conversion.conversionrate.vo;

import com.jiuqi.gcreport.conversion.conversionrate.vo.RateTypeItemVO;
import java.util.List;

public class ConversionRateItemVO {
    private String periodId;
    private String periodTitle;
    private String systemId;
    private String groupId;
    private String groupName;
    private String nodeId;
    private String nodeName;
    private String periodStr;
    private String sourceCurrencyCode;
    private String sourceCurrencyTitle;
    private String targetCurrencyCode;
    private String targetCurrencyTitle;
    private String periodValueTitle;
    private String rowId;
    private List<RateTypeItemVO> rateTypeItems;

    public String getNodeId() {
        return this.nodeId;
    }

    public String getSystemId() {
        return this.systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
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

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getPeriodValueTitle() {
        return this.periodValueTitle;
    }

    public void setPeriodValueTitle(String periodValueTitle) {
        this.periodValueTitle = periodValueTitle;
    }

    public String getRowId() {
        return this.rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getPeriodId() {
        return this.periodId;
    }

    public void setPeriodId(String periodId) {
        this.periodId = periodId;
    }

    public String getPeriodTitle() {
        return this.periodTitle;
    }

    public void setPeriodTitle(String periodTitle) {
        this.periodTitle = periodTitle;
    }

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
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

    public List<RateTypeItemVO> getRateTypeItems() {
        return this.rateTypeItems;
    }

    public void setRateTypeItems(List<RateTypeItemVO> rateTypeItems) {
        this.rateTypeItems = rateTypeItems;
    }
}

