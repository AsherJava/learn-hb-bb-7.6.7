/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.form.selector.service;

import com.jiuqi.nr.form.selector.entity.FormulaDataInputParam;
import com.jiuqi.nr.form.selector.entity.FormulaTableDataSet;

public interface IQueryFormulaListService {
    public int getFormulaNumByType(String var1, String var2, String var3);

    public FormulaTableDataSet queryFormulaListOfForms(FormulaDataInputParam var1) throws Exception;
}

