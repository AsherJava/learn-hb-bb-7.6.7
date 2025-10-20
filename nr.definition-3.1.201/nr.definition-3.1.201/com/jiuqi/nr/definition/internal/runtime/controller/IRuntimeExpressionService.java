/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 */
package com.jiuqi.nr.definition.internal.runtime.controller;

import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.nr.definition.common.CalcItem;
import com.jiuqi.nr.definition.facade.FormulaField;
import com.jiuqi.nr.definition.facade.FormulaParsedExp;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IRuntimeExpressionService {
    public List<IParsedExpression> getParsedExpressionByForm(String var1, String var2, DataEngineConsts.FormulaType var3);

    public List<IParsedExpression> getParsedExpressionByForms(String var1, List<String> var2, DataEngineConsts.FormulaType var3);

    public List<IParsedExpression> getParsedExpressionBetweenTable(String var1, DataEngineConsts.FormulaType var2);

    public List<IParsedExpression> getParsedExpressionByDataLink(String var1, String var2, String var3, DataEngineConsts.FormulaType var4);

    public IParsedExpression getParsedExpression(String var1, String var2);

    public Collection<String> getCalcCellDataLinks(String var1, String var2);

    public String getCalculateJsFormulasInForm(String var1, String var2);

    public List<CalcItem> getDimensionCalcCells(String var1, String var2);

    public List<IParsedExpression> getParsedExpressionByDataLink(List<String> var1, String var2, String var3, DataEngineConsts.FormulaType var4, Integer var5);

    public Map<String, String> getBalanceZBExpressionByForm(String var1, String var2);

    public List<IParsedExpression> getParsedExpressionByFormulas(String var1, String ... var2);

    public boolean isParsedFormulaFieldException(String var1);

    public FormulaField getFormulaField(String var1, String var2);

    public List<FormulaField> getFormulaFields(String var1, List<String> var2);

    public FormulaParsedExp getFormulaParsedExp(String var1, String var2);

    public List<FormulaParsedExp> getFormulaParsedExps(String var1, List<String> var2);
}

