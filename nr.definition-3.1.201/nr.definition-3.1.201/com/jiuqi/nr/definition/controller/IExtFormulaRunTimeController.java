/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 */
package com.jiuqi.nr.definition.controller;

import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.IFormulaUnitGroupGetter;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface IExtFormulaRunTimeController
extends IFormulaUnitGroupGetter {
    public boolean existPrivateFormula();

    public FormulaDefine queryFormulaDefine(String var1);

    public FormulaDefine findFormulaDefine(String var1, String var2);

    public List<FormulaDefine> getAllFormulasInScheme(String var1);

    public List<FormulaDefine> getCheckFormulasInScheme(String var1);

    public List<FormulaDefine> getAllFormulasInForm(String var1, String var2);

    public List<FormulaDefine> getCheckFormulasInForm(String var1, String var2);

    public List<IParsedExpression> getParsedExpressionByForm(String var1, String var2);

    public List<IParsedExpression> getParsedExpressionByForms(String var1, List<String> var2);

    public List<IParsedExpression> getParsedExpressionBetweenTable(String var1);

    public List<IParsedExpression> getParsedExpressionByDataLink(String var1, String var2, String var3);

    public IParsedExpression getParsedExpression(String var1, String var2);

    public IParsedExpression getParsedExpression(String var1, String var2, String var3);

    public Collection<String> getCalcCellDataLinks(String var1, String var2);

    public List<FormulaDefine> searchFormulaInScheme(String var1, String var2);

    public List<FormulaDefine> getFormulaByUnit(String var1, String var2);

    public List<FormulaDefine> getFormulaByUnits(String var1, Set<String> var2);
}

