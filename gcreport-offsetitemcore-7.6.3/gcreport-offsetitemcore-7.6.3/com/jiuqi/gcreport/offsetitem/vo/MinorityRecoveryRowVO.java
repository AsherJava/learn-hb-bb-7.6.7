/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.entity.AbstractFieldDynamicDeclarator
 */
package com.jiuqi.gcreport.offsetitem.vo;

import com.jiuqi.gcreport.definition.impl.basic.entity.AbstractFieldDynamicDeclarator;
import java.math.BigDecimal;

public class MinorityRecoveryRowVO
extends AbstractFieldDynamicDeclarator {
    private String unitCode;
    private String unitTitle;
    private String oppUnitCode;
    private String oppUnitTitle;
    private String subjectCode;
    private String subjectTitle;
    private Integer offsetType;
    private Integer minorityType;
    private BigDecimal unitEquityRatio;
    private BigDecimal oppUnitEquityRatio;
    private BigDecimal offsetAmt = BigDecimal.ZERO;
    private static BigDecimal ONE = BigDecimal.ONE;
    private BigDecimal diTaxRate;
    private BigDecimal diTaxAmt = BigDecimal.ZERO;
    private BigDecimal minorityOffsetAmt = BigDecimal.ZERO;
    private BigDecimal minorityDiTaxAmt = BigDecimal.ZERO;
    private BigDecimal minorityTotalAmt = BigDecimal.ZERO;
    private BigDecimal lossGainAmt = BigDecimal.ZERO;
    private Boolean lossGainRecovery;

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitTitle() {
        return this.unitTitle;
    }

    public void setUnitTitle(String unitTitle) {
        this.unitTitle = unitTitle;
    }

    public String getOppUnitCode() {
        return this.oppUnitCode;
    }

    public void setOppUnitCode(String oppUnitCode) {
        this.oppUnitCode = oppUnitCode;
    }

    public String getOppUnitTitle() {
        return this.oppUnitTitle;
    }

    public void setOppUnitTitle(String oppUnitTitle) {
        this.oppUnitTitle = oppUnitTitle;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectTitle() {
        return this.subjectTitle;
    }

    public void setSubjectTitle(String subjectTitle) {
        this.subjectTitle = subjectTitle;
    }

    public BigDecimal getUnitEquityRatio() {
        return this.unitEquityRatio;
    }

    public void setUnitEquityRatio(BigDecimal unitEquityRatio) {
        this.unitEquityRatio = unitEquityRatio;
    }

    public BigDecimal getOppUnitEquityRatio() {
        return this.oppUnitEquityRatio;
    }

    public void setOppUnitEquityRatio(BigDecimal oppUnitEquityRatio) {
        this.oppUnitEquityRatio = oppUnitEquityRatio;
    }

    public BigDecimal getOffsetAmt() {
        return this.offsetAmt;
    }

    public void setOffsetAmt(BigDecimal offsetAmt) {
        this.offsetAmt = offsetAmt;
    }

    public BigDecimal getDiTaxAmt() {
        return this.diTaxAmt;
    }

    public void setDiTaxAmt(BigDecimal diTaxAmt) {
        this.diTaxAmt = diTaxAmt;
    }

    public BigDecimal getMinorityOffsetAmt() {
        return this.minorityOffsetAmt;
    }

    public void setMinorityOffsetAmt(BigDecimal minorityOffsetAmt) {
        this.minorityOffsetAmt = minorityOffsetAmt;
    }

    public BigDecimal getMinorityDiTaxAmt() {
        return this.minorityDiTaxAmt;
    }

    public void setMinorityDiTaxAmt(BigDecimal minorityDiTaxAmt) {
        this.minorityDiTaxAmt = minorityDiTaxAmt;
    }

    public BigDecimal getMinorityTotalAmt() {
        return this.minorityTotalAmt;
    }

    public void setMinorityTotalAmt(BigDecimal minorityTotalAmt) {
        this.minorityTotalAmt = minorityTotalAmt;
    }

    public BigDecimal getLossGainAmt() {
        return this.lossGainAmt;
    }

    public void setLossGainAmt(BigDecimal lossGainAmt) {
        this.lossGainAmt = lossGainAmt;
    }

    public BigDecimal getDiTaxRate() {
        return this.diTaxRate;
    }

    public void setDiTaxRate(BigDecimal diTaxRate) {
        this.diTaxRate = diTaxRate;
    }

    public Boolean getLossGainRecovery() {
        return this.lossGainRecovery;
    }

    public void setLossGainRecovery(Boolean lossGainRecovery) {
        this.lossGainRecovery = lossGainRecovery;
    }

    public Integer getOffsetType() {
        return this.offsetType;
    }

    public void setOffsetType(Integer offsetType) {
        this.offsetType = offsetType;
    }

    public Integer getMinorityType() {
        return this.minorityType;
    }

    public void setMinorityType(Integer minorityType) {
        this.minorityType = minorityType;
    }

    public static BigDecimal getONE() {
        return ONE;
    }

    public static void setONE(BigDecimal ONE) {
        MinorityRecoveryRowVO.ONE = ONE;
    }

    public void doCalc(Boolean enableDeferredIncomeTax) {
        this.diTaxAmt = this.offsetAmt.multiply(this.diTaxRate).setScale(2, 4);
        this.minorityOffsetAmt = this.offsetAmt.multiply(ONE.subtract(this.oppUnitEquityRatio)).setScale(2, 4);
        this.minorityDiTaxAmt = this.diTaxAmt.multiply(this.oppUnitEquityRatio.subtract(ONE)).setScale(2, 4);
        this.minorityTotalAmt = this.minorityOffsetAmt.add(this.minorityDiTaxAmt).setScale(2, 4);
        this.lossGainAmt = enableDeferredIncomeTax != false ? this.offsetAmt.subtract(this.diTaxAmt).subtract(this.minorityTotalAmt).setScale(2, 4) : this.offsetAmt.subtract(this.minorityOffsetAmt).setScale(2, 4);
    }

    public void repair(boolean minusDeferredIncomeTax, boolean enableDeferredIncomeTax) {
        this.minorityDiTaxAmt = BigDecimal.ZERO;
        this.minorityOffsetAmt = BigDecimal.ZERO;
        this.minorityTotalAmt = BigDecimal.ZERO;
        this.lossGainAmt = enableDeferredIncomeTax ? (minusDeferredIncomeTax ? this.offsetAmt.subtract(this.diTaxAmt).subtract(this.minorityTotalAmt).setScale(2, 4) : this.offsetAmt.subtract(this.minorityTotalAmt).setScale(2, 4)) : this.offsetAmt.subtract(this.minorityOffsetAmt).setScale(2, 4);
    }
}

