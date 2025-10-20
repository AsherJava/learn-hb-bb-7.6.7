/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckapi.checkquery.vo;

import java.util.List;

public class FinancialCheckQueryVO {
    private Integer acctYear;
    private Integer acctPeriod;
    private String localUnit;
    private String oppUnit;
    private String checkLevel;
    private String schemeId;
    private Integer businessRole;
    private List<Integer> checkStates;
    private List<String> currency;
    private Double minDiffAmount;
    private Double maxDiffAmount;
    private Integer pageNum;
    private Integer pageSize;
    private String checkAttribute;
    private String orgType;

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

    public String getLocalUnit() {
        return this.localUnit;
    }

    public void setLocalUnit(String localUnit) {
        this.localUnit = localUnit;
    }

    public String getOppUnit() {
        return this.oppUnit;
    }

    public void setOppUnit(String oppUnit) {
        this.oppUnit = oppUnit;
    }

    public String getCheckLevel() {
        return this.checkLevel;
    }

    public void setCheckLevel(String checkLevel) {
        this.checkLevel = checkLevel;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public Integer getBusinessRole() {
        return this.businessRole;
    }

    public void setBusinessRole(Integer businessRole) {
        this.businessRole = businessRole;
    }

    public List<Integer> getCheckStates() {
        return this.checkStates;
    }

    public void setCheckStates(List<Integer> checkStates) {
        this.checkStates = checkStates;
    }

    public Double getMinDiffAmount() {
        return this.minDiffAmount;
    }

    public void setMinDiffAmount(Double minDiffAmount) {
        this.minDiffAmount = minDiffAmount;
    }

    public Double getMaxDiffAmount() {
        return this.maxDiffAmount;
    }

    public void setMaxDiffAmount(Double maxDiffAmount) {
        this.maxDiffAmount = maxDiffAmount;
    }

    public Integer getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<String> getCurrency() {
        return this.currency;
    }

    public void setCurrency(List<String> currency) {
        this.currency = currency;
    }

    public String getCheckAttribute() {
        return this.checkAttribute;
    }

    public void setCheckAttribute(String checkAttribute) {
        this.checkAttribute = checkAttribute;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }
}

