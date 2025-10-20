/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.bill.intf.BillContext
 */
package com.jiuqi.gcreport.bde.bill.setting.client.dto;

import com.jiuqi.va.bill.intf.BillContext;

public class EvaluateBillFormulaDTO {
    private String billType;
    private String formula;
    private BillContext context;

    public EvaluateBillFormulaDTO(String billType, String formula, BillContext context) {
        this.billType = billType;
        this.formula = formula;
        this.context = context;
    }

    public EvaluateBillFormulaDTO() {
    }

    public String getBillType() {
        return this.billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public BillContext getContext() {
        return this.context;
    }

    public void setContext(BillContext context) {
        this.context = context;
    }
}

