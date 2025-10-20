/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckapi.check.vo;

import java.math.BigDecimal;
import java.util.List;

public class FinancialCheckSelectAllVO {
    private List<String> selectIds;
    private BigDecimal debitSum;
    private BigDecimal creditSum;

    public List<String> getSelectIds() {
        return this.selectIds;
    }

    public void setSelectIds(List<String> selectIds) {
        this.selectIds = selectIds;
    }

    public BigDecimal getDebitSum() {
        return this.debitSum;
    }

    public void setDebitSum(BigDecimal debitSum) {
        this.debitSum = debitSum;
    }

    public BigDecimal getCreditSum() {
        return this.creditSum;
    }

    public void setCreditSum(BigDecimal creditSum) {
        this.creditSum = creditSum;
    }

    public String toString() {
        return "FinancialCheckSelectAllVO [selectIds=" + this.selectIds + ", debitSum=" + this.debitSum + ", creditSum=" + this.creditSum + ", getSelectIds()=" + this.getSelectIds() + ", getDebitSum()=" + this.getDebitSum() + ", getCreditSum()=" + this.getCreditSum() + ", getClass()=" + this.getClass() + ", hashCode()=" + this.hashCode() + ", toString()=" + super.toString() + "]";
    }
}

