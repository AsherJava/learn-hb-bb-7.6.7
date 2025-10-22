/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.multicriteria.client.vo;

import java.math.BigDecimal;

public class GcMultiCriteriaZbDataVO {
    private String mcid;
    private String beforeFieldKey;
    private String beforeTitle;
    private BigDecimal beforeAmt;
    private BigDecimal beforeTotalAmt;
    private String afterFieldKey;
    private String afterTitle;
    private BigDecimal afterAmt;
    private BigDecimal afterTotalAmt;
    private BigDecimal adjustAmt;
    private int rowspan = 0;
    private int dataRowspan = 0;
    private int index;
    private String mulCriTypeTitle;
    private Integer hasFormula;
    private String criAfterZbId;

    public String getMcid() {
        return this.mcid;
    }

    public void setMcid(String mcid) {
        this.mcid = mcid;
    }

    public String getBeforeFieldKey() {
        return this.beforeFieldKey;
    }

    public void setBeforeFieldKey(String beforeFieldKey) {
        this.beforeFieldKey = beforeFieldKey;
    }

    public String getBeforeTitle() {
        return this.beforeTitle;
    }

    public void setBeforeTitle(String beforeTitle) {
        this.beforeTitle = beforeTitle;
    }

    public BigDecimal getBeforeAmt() {
        return this.beforeAmt;
    }

    public void setBeforeAmt(BigDecimal beforeAmt) {
        this.beforeAmt = beforeAmt;
    }

    public BigDecimal getBeforeTotalAmt() {
        return this.beforeTotalAmt;
    }

    public void setBeforeTotalAmt(BigDecimal beforeTotalAmt) {
        this.beforeTotalAmt = beforeTotalAmt;
    }

    public String getAfterFieldKey() {
        return this.afterFieldKey;
    }

    public void setAfterFieldKey(String afterFieldKey) {
        this.afterFieldKey = afterFieldKey;
    }

    public String getAfterTitle() {
        return this.afterTitle;
    }

    public void setAfterTitle(String afterTitle) {
        this.afterTitle = afterTitle;
    }

    public BigDecimal getAfterAmt() {
        return this.afterAmt;
    }

    public void setAfterAmt(BigDecimal afterAmt) {
        this.afterAmt = afterAmt;
    }

    public BigDecimal getAfterTotalAmt() {
        return this.afterTotalAmt;
    }

    public void setAfterTotalAmt(BigDecimal afterTotalAmt) {
        this.afterTotalAmt = afterTotalAmt;
    }

    public BigDecimal getAdjustAmt() {
        return this.adjustAmt;
    }

    public void setAdjustAmt(BigDecimal adjustAmt) {
        this.adjustAmt = adjustAmt;
    }

    public int getRowspan() {
        return this.rowspan;
    }

    public void setRowspan(int rowspan) {
        this.rowspan = rowspan;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Integer getHasFormula() {
        return this.hasFormula;
    }

    public void setHasFormula(Integer hasFormula) {
        this.hasFormula = hasFormula;
    }

    public int getDataRowspan() {
        return this.dataRowspan;
    }

    public void setDataRowspan(int dataRowspan) {
        this.dataRowspan = dataRowspan;
    }

    public String getCriAfterZbId() {
        return this.criAfterZbId;
    }

    public void setCriAfterZbId(String criAfterZbId) {
        this.criAfterZbId = criAfterZbId;
    }

    public String getMulCriTypeTitle() {
        return this.mulCriTypeTitle;
    }

    public void setMulCriTypeTitle(String mulCriTypeTitle) {
        this.mulCriTypeTitle = mulCriTypeTitle;
    }
}

