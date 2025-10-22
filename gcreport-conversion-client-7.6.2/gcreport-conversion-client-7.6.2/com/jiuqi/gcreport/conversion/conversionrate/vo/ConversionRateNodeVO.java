/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.conversion.conversionrate.vo;

import java.util.Date;

public class ConversionRateNodeVO {
    private String id;
    private String periodId;
    private String systemId;
    private String groupId;
    private String groupName;
    private String nodeName;
    private String sourceCurrencyCode;
    private String sourceCurrencyTitle;
    private String targetCurrencyCode;
    private String targetCurrencyTitle;
    private String creator;
    private Date createTime;
    private Date updateTime;

    public String getNodeName() {
        if (this.sourceCurrencyTitle != null && this.targetCurrencyTitle != null) {
            this.nodeName = this.sourceCurrencyTitle + "-" + this.targetCurrencyTitle;
        }
        return this.nodeName;
    }

    public String getSystemId() {
        return this.systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getPeriodId() {
        return this.periodId;
    }

    public void setPeriodId(String periodId) {
        this.periodId = periodId;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getSourceCurrencyTitle() {
        return this.sourceCurrencyTitle;
    }

    public void setSourceCurrencyTitle(String sourceCurrencyTitle) {
        this.sourceCurrencyTitle = sourceCurrencyTitle;
    }

    public String getTargetCurrencyTitle() {
        return this.targetCurrencyTitle;
    }

    public void setTargetCurrencyTitle(String targetCurrencyTitle) {
        this.targetCurrencyTitle = targetCurrencyTitle;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getSourceCurrencyCode() {
        return this.sourceCurrencyCode;
    }

    public void setSourceCurrencyCode(String sourceCurrencyCode) {
        this.sourceCurrencyCode = sourceCurrencyCode;
    }

    public String getTargetCurrencyCode() {
        return this.targetCurrencyCode;
    }

    public void setTargetCurrencyCode(String targetCurrencyCode) {
        this.targetCurrencyCode = targetCurrencyCode;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}

