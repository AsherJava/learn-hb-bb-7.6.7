/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 */
package com.jiuqi.nr.definition.api;

import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.IFormulaUnitGroupGetter;
import java.util.List;
import java.util.Set;

public interface IRunTimeExtFormulaController
extends IFormulaUnitGroupGetter {
    public FormulaDefine getFormula(String var1);

    public FormulaDefine getFormulaByCodeAndScheme(String var1, String var2);

    public IParsedExpression getExpressionBySchemeAndFormAndExpression(String var1, String var2, String var3);

    public List<FormulaDefine> listFormulaBySchemeAndEntity(String var1, Set<String> var2);

    public boolean getExistPrivateFormula();
}

