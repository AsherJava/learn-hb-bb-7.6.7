/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.investbill.vo;

import java.util.Date;

public class FairValueOtherItemVO {
    private String id;
    private String assetType;
    private String assetTitle;
    private Double bookValue;
    private Double fairValue;
    private Date bizDate;
    private Double initDisposeValue;
    private Double currDisposeValue;
    private Integer srcType;
    private String investUnit;
    private String investedUnit;
    private Integer acctYear;
    private String srcId;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssetType() {
        return this.assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getAssetTitle() {
        return this.assetTitle;
    }

    public void setAssetTitle(String assetTitle) {
        this.assetTitle = assetTitle;
    }

    public Double getBookValue() {
        return this.bookValue;
    }

    public void setBookValue(Double bookValue) {
        this.bookValue = bookValue;
    }

    public Double getFairValue() {
        return this.fairValue;
    }

    public void setFairValue(Double fairValue) {
        this.fairValue = fairValue;
    }

    public Date getBizDate() {
        return this.bizDate;
    }

    public void setBizDate(Date bizDate) {
        this.bizDate = bizDate;
    }

    public Double getInitDisposeValue() {
        return this.initDisposeValue;
    }

    public void setInitDisposeValue(Double initDisposeValue) {
        this.initDisposeValue = initDisposeValue;
    }

    public Double getCurrDisposeValue() {
        return this.currDisposeValue;
    }

    public void setCurrDisposeValue(Double currDisposeValue) {
        this.currDisposeValue = currDisposeValue;
    }

    public Integer getSrcType() {
        return this.srcType;
    }

    public void setSrcType(Integer srcType) {
        this.srcType = srcType;
    }

    public String getInvestUnit() {
        return this.investUnit;
    }

    public void setInvestUnit(String investUnit) {
        this.investUnit = investUnit;
    }

    public String getInvestedUnit() {
        return this.investedUnit;
    }

    public void setInvestedUnit(String investedUnit) {
        this.investedUnit = investedUnit;
    }

    public Integer getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(Integer acctYear) {
        this.acctYear = acctYear;
    }

    public String getSrcId() {
        return this.srcId;
    }

    public void setSrcId(String srcId) {
        this.srcId = srcId;
    }
}

