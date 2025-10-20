/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 */
package com.jiuqi.nr.definition.api;

import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.nr.definition.common.CalcItem;
import com.jiuqi.nr.definition.facade.FormulaField;
import com.jiuqi.nr.definition.facade.FormulaParsedExp;
import com.jiuqi.nr.definition.facade.FormulaVariDefine;
import com.jiuqi.nr.definition.facade.formula.DesignFormulaCondition;
import com.jiuqi.nr.definition.facade.formula.DesignFormulaConditionLink;
import com.jiuqi.nr.definition.internal.stream.param.FormulaListStream;
import com.jiuqi.nr.definition.internal.stream.param.FormulaSchemeListStream;
import com.jiuqi.nr.definition.internal.stream.param.FormulaSchemeStream;
import com.jiuqi.nr.definition.internal.stream.param.FormulaStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public interface IRunTimeFormulaController {
    public FormulaSchemeStream getFormulaScheme(String var1);

    public FormulaSchemeStream getDefaultFormulaSchemeByFormScheme(String var1);

    public FormulaSchemeListStream listFormulaSchemeByFormScheme(String var1);

    public FormulaSchemeListStream listFinanceFormulaSchemeByFormScheme(String var1);

    public FormulaSchemeListStream listPickFormulaSchemeByFormScheme(String var1);

    public FormulaSchemeListStream listReportFormulaSchemeByFormScheme(String var1);

    public FormulaStream getFormula(String var1);

    public FormulaStream getFormulaByCodeAndScheme(String var1, String var2);

    public FormulaListStream listFormulaByScheme(String var1);

    public FormulaListStream listCalculateFormulaByScheme(String var1);

    public FormulaListStream listCheckFormulaByScheme(String var1);

    public FormulaListStream listBalanceFormulaByScheme(String var1);

    public FormulaListStream listFormulaBySchemeAndForm(String var1, String var2);

    public FormulaListStream listCalculateFormulaBySchemeAndForm(String var1, String var2);

    public FormulaListStream listCheckFormulaBySchemeAndForm(String var1, String var2);

    public FormulaListStream listBalanceFormulaBySchemeAndForm(String var1, String var2);

    public List<IParsedExpression> listExpressionBySchemeAndFormAndType(String var1, String var2, DataEngineConsts.FormulaType var3);

    public List<IParsedExpression> listExpressionByFormula(String var1, String var2);

    public List<IParsedExpression> listExpressionBySchemeAndFormsAndType(String var1, List<String> var2, DataEngineConsts.FormulaType var3);

    public List<IParsedExpression> listBetweenExpressionBySchemeAndType(String var1, DataEngineConsts.FormulaType var2);

    @Deprecated
    public List<IParsedExpression> listExpressionBySchemeAndFormAndTypeAndLinkCode(String var1, DataEngineConsts.FormulaType var2, String var3, String var4);

    public IParsedExpression getExpressionBySchemeAndExpression(String var1, String var2);

    public IParsedExpression getExpressionBySchemeAndFormAndExpression(String var1, String var2, String var3);

    public Collection<String> listCalcCellDataLinkBySchemeAndForm(String var1, String var2);

    public String getScriptBySchemeAndForm(String var1, String var2);

    public List<CalcItem> listDimensionCalcCellsBySchemeAndForm(String var1, String var2);

    public List<IParsedExpression> listFormConditionsExpressionByFormScheme(String var1);

    public HashSet<String> listFormKeyByFormSchemeHaveFormConditions(String var1);

    public HashSet<String> listFieldKeyByFormSchemeHaveFormConditions(String var1);

    public List<FormulaVariDefine> listFormulaVariByFormScheme(String var1);

    public boolean isParsedFormulaFieldException(String var1);

    public FormulaField getFormulaField(String var1, String var2);

    public List<FormulaField> listFormulaFields(String var1, List<String> var2);

    public FormulaParsedExp getFormulaParsedExp(String var1, String var2);

    public List<FormulaParsedExp> listFormulaParsedExps(String var1, List<String> var2);

    public Map<String, String> getEffectiveForms(String var1, List<String> var2);

    public List<DesignFormulaCondition> listFormulaConditionByTask(String var1);

    public List<DesignFormulaCondition> listFormulaConditionByScheme(String var1);

    public List<DesignFormulaConditionLink> listFormulaConditionLinkByScheme(String var1);
}

