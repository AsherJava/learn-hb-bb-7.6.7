/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckapi.checkquery.vo;

import java.util.Map;

public class FinancialCheckQueryInitDataVO {
    private Integer acctYear;
    private Integer acctPeriod;
    private String orgVer;
    private String orgType;
    private Map<String, String> mergeNode;
    private Map<String, String> schemeNode;
    private Map<String, String> groupItem;

    public Integer getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(Integer acctYear) {
        this.acctYear = acctYear;
    }

    public String getOrgVer() {
        return this.orgVer;
    }

    public void setOrgVer(String orgVer) {
        this.orgVer = orgVer;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public Map<String, String> getMergeNode() {
        return this.mergeNode;
    }

    public void setMergeNode(Map<String, String> mergeNode) {
        this.mergeNode = mergeNode;
    }

    public Integer getAcctPeriod() {
        return this.acctPeriod;
    }

    public void setAcctPeriod(Integer acctPeriod) {
        this.acctPeriod = acctPeriod;
    }

    public Map<String, String> getSchemeNode() {
        return this.schemeNode;
    }

    public void setSchemeNode(Map<String, String> schemeNode) {
        this.schemeNode = schemeNode;
    }

    public Map<String, String> getGroupItem() {
        return this.groupItem;
    }

    public void setGroupItem(Map<String, String> groupItem) {
        this.groupItem = groupItem;
    }
}

