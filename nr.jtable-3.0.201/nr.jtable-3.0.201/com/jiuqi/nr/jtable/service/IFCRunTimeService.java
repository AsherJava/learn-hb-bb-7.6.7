/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.service;

import com.jiuqi.nr.jtable.params.base.FormulaConditionFile;
import com.jiuqi.nr.jtable.params.base.JtableContext;

public interface IFCRunTimeService {
    public FormulaConditionFile getFormulaConditionInForm(String var1, String var2, String var3);

    public FormulaConditionFile getFormulaConditionInForm(JtableContext var1);
}

