/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.jtable.service;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.jtable.params.base.FormulaSchemeData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.JtableData;

public interface IJtableContextService {
    public JtableData getReportFormData(JtableContext var1);

    public boolean isFormCondition(JtableContext var1);

    public DimensionValueSet getDimensionValueSet(JtableContext var1);

    public FormulaSchemeData getFormulaSchemeData(JtableContext var1);

    public JtableData initSurveryCardData(String var1, String var2, String var3, String var4, String var5);
}

