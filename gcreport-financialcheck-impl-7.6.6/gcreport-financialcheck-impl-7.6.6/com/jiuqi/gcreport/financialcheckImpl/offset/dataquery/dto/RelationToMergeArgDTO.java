/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckImpl.offset.dataquery.dto;

import java.util.Set;

public class RelationToMergeArgDTO {
    private String periodScheme;
    private Integer acctYear;
    private Integer acctPeriod;
    private String currency;
    private String orgCode;
    private String orgType;
    private Integer periodType;
    private Set<String> boundSubjects;
    private boolean checked;
    private String systemId;

    public String getPeriodScheme() {
        return this.periodScheme;
    }

    public void setPeriodScheme(String periodScheme) {
        this.periodScheme = periodScheme;
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

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public Integer getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(Integer periodType) {
        this.periodType = periodType;
    }

    public Set<String> getBoundSubjects() {
        return this.boundSubjects;
    }

    public void setBoundSubjects(Set<String> boundSubjects) {
        this.boundSubjects = boundSubjects;
    }

    public boolean isChecked() {
        return this.checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getSystemId() {
        return this.systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }
}

