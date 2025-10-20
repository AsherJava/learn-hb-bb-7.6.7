/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.vo.SelectOptionVO
 */
package com.jiuqi.dc.query.client.balance.vo;

import com.jiuqi.dc.base.common.vo.SelectOptionVO;
import java.math.BigDecimal;

public class BalanceQueryListVO {
    private String unitCode;
    private String unitName;
    private String subjectCode;
    private String subjectName;
    private String currencyCode;
    private BigDecimal debitNC;
    private BigDecimal creditNC;
    private BigDecimal debitQC;
    private BigDecimal creditQC;
    private BigDecimal debitBQ;
    private BigDecimal creditBQ;
    private BigDecimal debitLJ;
    private BigDecimal creditLJ;
    private BigDecimal debitYE;
    private BigDecimal creditYE;
    private SelectOptionVO dimColumns;

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitName() {
        return this.unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return this.subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public BigDecimal getDebitNC() {
        return this.debitNC;
    }

    public void setDebitNC(BigDecimal debitNC) {
        this.debitNC = debitNC;
    }

    public BigDecimal getCreditNC() {
        return this.creditNC;
    }

    public void setCreditNC(BigDecimal creditNC) {
        this.creditNC = creditNC;
    }

    public BigDecimal getDebitQC() {
        return this.debitQC;
    }

    public void setDebitQC(BigDecimal debitQC) {
        this.debitQC = debitQC;
    }

    public BigDecimal getCreditQC() {
        return this.creditQC;
    }

    public void setCreditQC(BigDecimal creditQC) {
        this.creditQC = creditQC;
    }

    public BigDecimal getDebitBQ() {
        return this.debitBQ;
    }

    public void setDebitBQ(BigDecimal debitBQ) {
        this.debitBQ = debitBQ;
    }

    public BigDecimal getCreditBQ() {
        return this.creditBQ;
    }

    public void setCreditBQ(BigDecimal creditBQ) {
        this.creditBQ = creditBQ;
    }

    public BigDecimal getDebitLJ() {
        return this.debitLJ;
    }

    public void setDebitLJ(BigDecimal debitLJ) {
        this.debitLJ = debitLJ;
    }

    public BigDecimal getCreditLJ() {
        return this.creditLJ;
    }

    public void setCreditLJ(BigDecimal creditLJ) {
        this.creditLJ = creditLJ;
    }

    public BigDecimal getDebitYE() {
        return this.debitYE;
    }

    public void setDebitYE(BigDecimal debitYE) {
        this.debitYE = debitYE;
    }

    public BigDecimal getCreditYE() {
        return this.creditYE;
    }

    public void setCreditYE(BigDecimal creditYE) {
        this.creditYE = creditYE;
    }

    public SelectOptionVO getDimColumns() {
        return this.dimColumns;
    }

    public void setDimColumns(SelectOptionVO dimColumns) {
        this.dimColumns = dimColumns;
    }
}

