/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.investbill.vo;

import java.util.Date;

public class GcInvestmentItemBillVO {
    private String id;
    private String changeScenario;
    private Date changeDate;
    private Double changeAmt;
    private Double changeRatio;
    private Double fewShareholderAdd;
    private Double disposingPrice;
    private Integer acctYear;
    private Integer acctPeriod;
    private String masterId;
    private String srcId;
    private Integer srcType;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChangeScenario() {
        return this.changeScenario;
    }

    public void setChangeScenario(String changeScenario) {
        this.changeScenario = changeScenario;
    }

    public Date getChangeDate() {
        return this.changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public Double getChangeAmt() {
        return this.changeAmt;
    }

    public void setChangeAmt(Double changeAmt) {
        this.changeAmt = changeAmt;
    }

    public Double getChangeRatio() {
        return this.changeRatio;
    }

    public void setChangeRatio(Double changeRatio) {
        this.changeRatio = changeRatio;
    }

    public Double getFewShareholderAdd() {
        return this.fewShareholderAdd;
    }

    public void setFewShareholderAdd(Double fewShareholderAdd) {
        this.fewShareholderAdd = fewShareholderAdd;
    }

    public Double getDisposingPrice() {
        return this.disposingPrice;
    }

    public void setDisposingPrice(Double disposingPrice) {
        this.disposingPrice = disposingPrice;
    }

    public Integer getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(Integer acctYear) {
        this.acctYear = acctYear;
    }

    public Integer getAcctPeriod() {
        return this.acctPeriod;
    }

    public void setAcctPeriod(Integer acctPeriod) {
        this.acctPeriod = acctPeriod;
    }

    public String getMasterId() {
        return this.masterId;
    }

    public void setMasterId(String masterId) {
        this.masterId = masterId;
    }

    public String getSrcId() {
        return this.srcId;
    }

    public void setSrcId(String srcId) {
        this.srcId = srcId;
    }

    public Integer getSrcType() {
        return this.srcType;
    }

    public void setSrcType(Integer srcType) {
        this.srcType = srcType;
    }
}

