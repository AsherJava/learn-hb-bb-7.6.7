/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.api;

import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaFunctionDefine;
import com.jiuqi.nr.definition.facade.FormulaFunctionParamterDefine;
import com.jiuqi.nr.definition.facade.FormulaVariDefine;
import com.jiuqi.nr.definition.facade.formula.DesignFormulaCondition;
import com.jiuqi.nr.definition.facade.formula.DesignFormulaConditionLink;
import java.util.List;

public interface IDesignTimeFormulaController {
    public DesignFormulaSchemeDefine initFormulaScheme();

    public void insertFormulaScheme(DesignFormulaSchemeDefine var1);

    public void updateFormulaScheme(DesignFormulaSchemeDefine var1);

    public void deleteFormulaScheme(String[] var1);

    public DesignFormulaSchemeDefine getFormulaScheme(String var1);

    public List<DesignFormulaSchemeDefine> listFormulaSchemeByFormScheme(String var1);

    public List<DesignFormulaSchemeDefine> listFormulaSchemeByFormSchemeAndType(String var1, FormulaSchemeType var2);

    public DesignFormulaSchemeDefine getDefaultFormulaSchemeByFormScheme(String var1);

    public List<DesignFormulaSchemeDefine> listAllFormulaScheme();

    public DesignFormulaSchemeDefine getFormulaSchemeByTitle(DesignFormulaSchemeDefine var1);

    public DesignFormulaDefine initFormula();

    public void insertFormula(DesignFormulaDefine[] var1);

    public void updateFormula(DesignFormulaDefine[] var1);

    public void deleteFormula(String[] var1);

    public void deleteFormulaByForm(String var1);

    public void deleteFormulaBySchemeAndForm(String var1, String var2);

    public void deleteFormulaByScheme(String var1);

    public DesignFormulaDefine getFormula(String var1);

    public DesignFormulaDefine getFormulaByCodeAndScheme(String var1, String var2);

    public List<DesignFormulaDefine> getFormulaByCodeAndSchemeAndForm(String var1, String var2, String var3);

    public List<DesignFormulaDefine> listFormulaByScheme(String var1);

    public List<DesignFormulaDefine> listAllFormula();

    public List<DesignFormulaDefine> listCalculateFormulaByScheme(String var1);

    public List<DesignFormulaDefine> listCheckFormulaByScheme(String var1);

    public List<DesignFormulaDefine> listBalanceFormulaByScheme(String var1);

    public List<DesignFormulaDefine> listFormulaBySchemeAndForm(String var1, String var2);

    public List<DesignFormulaDefine> listCalculateFormulaBySchemeAndForm(String var1, String var2);

    public List<DesignFormulaDefine> listCheckFormulaBySchemeAndForm(String var1, String var2);

    public List<DesignFormulaDefine> listBalanceFormulaBySchemeAndForm(String var1, String var2);

    public void insertFormulaVariable(FormulaVariDefine var1);

    public void updateFormulaVariable(FormulaVariDefine var1);

    public void deleteFormulaVariable(String var1);

    public void deleteFormulaVariableByFormScheme(String var1);

    public List<FormulaVariDefine> listFormulaVariByFormScheme(String var1);

    public FormulaVariDefine getFormulaVariByCodeAndFormScheme(String var1, String var2);

    public List<FormulaFunctionDefine> listAllFormulaFunction();

    public List<FormulaFunctionParamterDefine> listAllFormulaFunctionParamter();

    public DesignFormulaCondition initFormulaCondition();

    public DesignFormulaConditionLink initFormulaConditionLink();

    public List<DesignFormulaCondition> listFormulaConditionByTask(String var1);

    public List<DesignFormulaCondition> listFormulaConditionByTaskAndCode(String var1, String ... var2);

    public List<DesignFormulaCondition> listFormulaConditionByScheme(String var1);

    public List<DesignFormulaConditionLink> listFormulaConditionLinkByScheme(String var1);

    public void insertFormulaConditionLinks(List<DesignFormulaConditionLink> var1);

    public void insertFormulaConditions(List<DesignFormulaCondition> var1);

    public void insertFormulaCondition(DesignFormulaCondition var1);

    public void updateFormulaCondition(DesignFormulaCondition var1);

    public void updateFormulaConditions(List<DesignFormulaCondition> var1);

    public List<DesignFormulaCondition> listFormulaConditionByKey(List<String> var1);

    public void deleteFormulaCondition(String var1);

    public void deleteFormulaConditions(List<String> var1);

    public void deleteFormulaConditionLinkByCondition(String ... var1);

    public void deleteFormulaConditionLinks(List<DesignFormulaConditionLink> var1);

    public List<DesignFormulaConditionLink> listConditionLinksByCondition(List<String> var1);

    public void deleteFormulaConditionLinkByScheme(String var1);

    public void deleteFormulaConditionLinkByFormula(String ... var1);
}

