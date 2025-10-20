/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckapi.scheme.vo;

import java.util.List;

public class FinancialCheckMatchSchemeVO {
    private String id;
    private Integer acctYear;
    private Integer acctPeriod;
    private List<String> subjectCodes;

    public Integer getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(Integer acctYear) {
        this.acctYear = acctYear;
    }

    public List<String> getSubjectCodes() {
        return this.subjectCodes;
    }

    public void setSubjectCodes(List<String> subjectCodes) {
        this.subjectCodes = subjectCodes;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getAcctPeriod() {
        return this.acctPeriod;
    }

    public void setAcctPeriod(Integer acctPeriod) {
        this.acctPeriod = acctPeriod;
    }
}

