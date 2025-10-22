/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 */
package com.jiuqi.nr.definition.controller;

import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.nr.definition.common.CalcItem;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.formula.FormulaCondition;
import com.jiuqi.nr.definition.facade.formula.FormulaConditionLink;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public interface IFormulaRunTimeController {
    public FormulaSchemeDefine queryFormulaSchemeDefine(String var1);

    public FormulaSchemeDefine getDefaultFormulaSchemeInFormScheme(String var1);

    public List<FormulaSchemeDefine> getAllFormulaSchemeDefinesByFormScheme(String var1);

    public List<FormulaSchemeDefine> getAllCWFormulaSchemeDefinesByFormScheme(String var1);

    public List<FormulaSchemeDefine> getAllPickFormulaSchemeDefinesByFormScheme(String var1);

    public FormulaDefine queryFormulaDefine(String var1);

    public FormulaDefine findFormulaDefine(String var1, String var2);

    public List<FormulaDefine> getAllFormulasInScheme(String var1);

    public List<FormulaDefine> getCalculateFormulasInScheme(String var1);

    public List<FormulaDefine> getCheckFormulasInScheme(String var1);

    public List<FormulaDefine> getBalanceFormulasInScheme(String var1);

    public List<FormulaDefine> getAllFormulasInForm(String var1, String var2);

    public List<FormulaDefine> getCalculateFormulasInForm(String var1, String var2);

    public List<FormulaDefine> getCheckFormulasInForm(String var1, String var2);

    public List<FormulaDefine> getBalanceFormulasInForm(String var1, String var2);

    public List<IParsedExpression> getParsedExpressionByForm(String var1, String var2, DataEngineConsts.FormulaType var3);

    public List<IParsedExpression> getParsedExpressionByForms(String var1, List<String> var2, DataEngineConsts.FormulaType var3);

    public List<IParsedExpression> getParsedExpressionBetweenTable(String var1, DataEngineConsts.FormulaType var2);

    public List<IParsedExpression> getParsedExpressionByDataLink(String var1, String var2, String var3, DataEngineConsts.FormulaType var4);

    public IParsedExpression getParsedExpression(String var1, String var2);

    public IParsedExpression getParsedExpression(String var1, String var2, String var3);

    public Collection<String> getCalcCellDataLinks(String var1, String var2);

    public String getCalculateJsFormulasInForm(String var1, String var2);

    public List<FormulaSchemeDefine> getAllRPTFormulaSchemeDefinesByFormScheme(String var1);

    public List<FormulaDefine> searchFormulaInScheme(String var1, String var2);

    public List<CalcItem> getDimensionCalcCells(String var1, String var2);

    public List<IParsedExpression> getParsedExpressionByDataLink(List<String> var1, String var2, String var3, DataEngineConsts.FormulaType var4, Integer var5);

    @Deprecated
    public List<FormulaDefine> queryPublicFormulaDefineByScheme(String var1, String var2) throws Exception;

    @Deprecated
    public List<FormulaDefine> queryPublicFormulaDefineByScheme(String var1) throws Exception;

    public List<IParsedExpression> getFormConditionsByFormScheme(String var1);

    public HashSet<String> getReloadFormsByFormScheme(String var1);

    public HashSet<String> getConditionFieldsByFormScheme(String var1);

    public Map<String, List<IParsedExpression>> getParsedFormulaConditionExpression(String var1, String ... var2);

    public List<FormulaCondition> getFormulaConditions(String var1);

    public List<FormulaConditionLink> getFormulaConditionLinks(String var1);

    public List<FormulaCondition> getFormulaConditions(List<String> var1);

    public List<FormulaConditionLink> getFormulaConditionLinksByCondition(List<String> var1);
}

