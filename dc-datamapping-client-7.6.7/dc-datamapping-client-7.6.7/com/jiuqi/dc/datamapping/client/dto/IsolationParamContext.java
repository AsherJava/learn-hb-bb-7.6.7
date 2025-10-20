/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.datamapping.client.dto;

import java.util.Objects;

public class IsolationParamContext {
    private String schemeCode;
    private String unitCode;
    private String bookCode;
    private Integer acctYear;
    private Integer accPeriod;
    private String assistCode;

    public IsolationParamContext() {
    }

    public IsolationParamContext(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public IsolationParamContext(String schemeCode, String assistCode) {
        this.schemeCode = schemeCode;
        this.assistCode = assistCode;
    }

    public IsolationParamContext(String schemeCode, String unitCode, String bookCode, Integer acctYear, Integer period) {
        this.schemeCode = schemeCode;
        this.unitCode = unitCode;
        this.bookCode = bookCode;
        this.acctYear = acctYear;
        this.accPeriod = this.accPeriod;
    }

    public IsolationParamContext(String schemeCode, String unitCode, String bookCode, Integer acctYear) {
        this.schemeCode = schemeCode;
        this.unitCode = unitCode;
        this.bookCode = bookCode;
        this.acctYear = acctYear;
    }

    public String getSchemeCode() {
        return this.schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getBookCode() {
        return this.bookCode;
    }

    public void setBookCode(String bookCode) {
        this.bookCode = bookCode;
    }

    public Integer getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(Integer acctYear) {
        this.acctYear = acctYear;
    }

    public Integer getAccPeriod() {
        return this.accPeriod;
    }

    public void setAccPeriod(Integer accPeriod) {
        this.accPeriod = accPeriod;
    }

    public String getAssistCode() {
        return this.assistCode;
    }

    public void setAssistCode(String assistCode) {
        this.assistCode = assistCode;
    }

    public int hashCode() {
        return Objects.hash(this.unitCode, this.bookCode, this.acctYear, this.accPeriod, this.assistCode);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        IsolationParamContext param = (IsolationParamContext)o;
        return Objects.equals(this.unitCode, param.unitCode) && Objects.equals(this.acctYear, param.acctYear) && Objects.equals(this.bookCode, param.bookCode) && Objects.equals(this.accPeriod, param.accPeriod) && Objects.equals(this.assistCode, param.assistCode);
    }
}

