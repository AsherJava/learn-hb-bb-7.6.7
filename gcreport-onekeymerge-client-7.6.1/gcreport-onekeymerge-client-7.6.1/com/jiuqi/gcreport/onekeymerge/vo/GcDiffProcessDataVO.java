/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.onekeymerge.vo;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class GcDiffProcessDataVO {
    private String unitTitle;
    private String oppUnitTitle;
    private String subjectTitle;
    private BigDecimal endAmt;
    private String endAmtStr;
    private BigDecimal offsetAmt;
    private String offsetAmtStr;
    private BigDecimal diffAmt;
    private String diffAmtStr;
    private Integer subjectOrient;
    private DecimalFormat df = new DecimalFormat("###,##0.00");

    public String getUnitTitle() {
        return this.unitTitle;
    }

    public void setUnitTitle(String unitTitle) {
        this.unitTitle = unitTitle;
    }

    public String getOppUnitTitle() {
        return this.oppUnitTitle;
    }

    public void setOppUnitTitle(String oppUnitTitle) {
        this.oppUnitTitle = oppUnitTitle;
    }

    public String getSubjectTitle() {
        return this.subjectTitle;
    }

    public void setSubjectTitle(String subjectTitle) {
        this.subjectTitle = subjectTitle;
    }

    public BigDecimal getEndAmt() {
        return this.endAmt;
    }

    public void setEndAmt(BigDecimal endAmt) {
        this.endAmt = endAmt == null ? new BigDecimal(0) : endAmt;
        this.endAmtStr = this.df.format(endAmt);
    }

    public BigDecimal getOffsetAmt() {
        return this.offsetAmt;
    }

    public void setOffsetAmt(BigDecimal offsetAmt) {
        this.offsetAmt = offsetAmt == null ? new BigDecimal(0) : offsetAmt;
        this.offsetAmtStr = this.df.format(offsetAmt);
    }

    public BigDecimal getDiffAmt() {
        return this.diffAmt;
    }

    public void setDiffAmt(BigDecimal diffAmt) {
        this.diffAmt = diffAmt == null ? new BigDecimal(0) : diffAmt;
        this.diffAmtStr = this.df.format(diffAmt);
    }

    public Integer getSubjectOrient() {
        return this.subjectOrient;
    }

    public void setSubjectOrient(Integer subjectOrient) {
        this.subjectOrient = subjectOrient;
    }

    public String getEndAmtStr() {
        return this.endAmtStr;
    }

    public void setEndAmtStr(String endAmtStr) {
        this.endAmtStr = endAmtStr;
    }

    public String getOffsetAmtStr() {
        return this.offsetAmtStr;
    }

    public void setOffsetAmtStr(String offsetAmtStr) {
        this.offsetAmtStr = offsetAmtStr;
    }

    public String getDiffAmtStr() {
        return this.diffAmtStr;
    }

    public void setDiffAmtStr(String diffAmtStr) {
        this.diffAmtStr = diffAmtStr;
    }
}

