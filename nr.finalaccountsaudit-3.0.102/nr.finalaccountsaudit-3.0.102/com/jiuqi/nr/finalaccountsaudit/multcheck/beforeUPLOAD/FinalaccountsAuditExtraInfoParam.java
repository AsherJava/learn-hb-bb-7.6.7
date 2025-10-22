/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.finalaccountsaudit.multcheck.beforeUPLOAD;

import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.io.Serializable;
import java.util.List;

public class FinalaccountsAuditExtraInfoParam
implements Serializable {
    private static final long serialVersionUID = 1L;
    private JtableContext context;
    private String formulaSchemeKeys;
    private List<String> formulas;

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public String getFormulaSchemeKeys() {
        return this.formulaSchemeKeys;
    }

    public void setFormulaSchemeKeys(String formulaSchemeKeys) {
        this.formulaSchemeKeys = formulaSchemeKeys;
    }

    public List<String> getFormulas() {
        return this.formulas;
    }

    public void setFormulas(List<String> formulas) {
        this.formulas = formulas;
    }
}

