/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.data.estimation.web.request;

import com.jiuqi.nr.data.estimation.web.request.ActionParameter;
import com.jiuqi.nr.jtable.params.base.JtableContext;

public class ActionOfCalculateParam
extends ActionParameter {
    private String formulaSchemeKey;
    private JtableContext context;

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }
}

