/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.entity.AbstractFieldDynamicDeclarator
 */
package com.jiuqi.gcreport.offsetitem.vo;

import com.jiuqi.gcreport.definition.impl.basic.entity.AbstractFieldDynamicDeclarator;

public class EndCarryForwarditemVO
extends AbstractFieldDynamicDeclarator {
    private String unitCode;
    private String unitTitle;
    private String oppUnitCode;
    private String oppUnitTitle;
    private String subjectCode;
    private Double debit;
    private Double credit;

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

    public Double getDebit() {
        return this.debit;
    }

    public void setDebit(Double debit) {
        this.debit = debit;
    }

    public Double getCredit() {
        return this.credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }
}

