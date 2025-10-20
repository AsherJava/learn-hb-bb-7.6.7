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
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IRuntimeExtExpressionService {
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

    public void refreshExpressionCache(String var1, String var2, boolean var3) throws Exception;
}

