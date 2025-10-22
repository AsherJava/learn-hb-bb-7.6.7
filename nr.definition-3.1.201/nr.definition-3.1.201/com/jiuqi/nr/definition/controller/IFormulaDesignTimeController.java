/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.definition.controller;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.definition.common.DesignFormulaDTO;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaFunctionDefine;
import com.jiuqi.nr.definition.facade.FormulaFunctionParamterDefine;
import com.jiuqi.nr.definition.facade.FormulaVariDefine;
import com.jiuqi.nr.definition.facade.formula.DesignFormulaCondition;
import com.jiuqi.nr.definition.facade.formula.DesignFormulaConditionLink;
import java.util.List;
import java.util.Map;

public interface IFormulaDesignTimeController {
    public DesignFormulaSchemeDefine createFormulaSchemeDefine();

    public String insertFormulaSchemeDefine(DesignFormulaSchemeDefine var1);

    public void updateFormulaSchemeDefine(DesignFormulaSchemeDefine var1);

    public void checkFormulaTitle(DesignFormulaSchemeDefine var1) throws JQException;

    public void deleteFormulaSchemeDefine(String var1);

    public void exchangeFormulaSchemeOrder(String var1, String var2);

    public DesignFormulaSchemeDefine queryFormulaSchemeDefine(String var1);

    public List<DesignFormulaSchemeDefine> getAllFormulaSchemeDefines(String var1);

    public List<DesignFormulaSchemeDefine> getAllFormulaSchemeDefinesByFormScheme(String var1);

    public DesignFormulaSchemeDefine getDefaultFormulaSchemeInFormScheme(String var1);

    public DesignFormulaDefine createFormulaDefine();

    public String insertFormulaDefine(DesignFormulaDefine var1) throws JQException;

    public void updateFormulaDefine(DesignFormulaDefine var1) throws JQException;

    public void deleteFormulaDefine(String var1);

    public void exchangeFormulaOrder(String var1, String var2);

    public DesignFormulaDefine queryFormulaDefine(String var1) throws JQException;

    public DesignFormulaDefine findFormulaDefineInFormulaScheme(String var1, String var2) throws JQException;

    public List<DesignFormulaDefine> findRepeatFormulaDefineFormOutSchemes(String var1, String var2, String var3) throws JQException;

    public List<DesignFormulaDefine> getAllFormulasInScheme(String var1) throws JQException;

    public List<DesignFormulaDefine> getAllFormulas() throws JQException;

    public List<DesignFormulaDefine> getCalculateFormulasInScheme(String var1) throws JQException;

    public List<DesignFormulaDefine> getCheckFormulasInScheme(String var1) throws JQException;

    public List<DesignFormulaDefine> getBalanceFormulasInScheme(String var1) throws JQException;

    public List<DesignFormulaDefine> getAllFormulasInForm(String var1, String var2) throws JQException;

    public List<DesignFormulaDefine> getCalculateFormulasInForm(String var1, String var2) throws JQException;

    public List<DesignFormulaDefine> getCheckFormulasInForm(String var1, String var2) throws JQException;

    public List<DesignFormulaDefine> getBalanceFormulasInForm(String var1, String var2) throws JQException;

    public List<String> insertFormulaDefines(DesignFormulaDefine[] var1) throws JQException;

    public List<String> insertFormulasNotAnalysis(DesignFormulaDefine[] var1) throws JQException;

    public void updateFormulaDefines(DesignFormulaDefine[] var1) throws JQException;

    public void updateFormulasNotAnalysis(DesignFormulaDefine[] var1) throws JQException;

    public void deleteFormulaDefines(String[] var1);

    public List<DesignFormulaDefine> createFormulaDefines(int var1);

    public List<DesignFormulaDefine> findFormulaDefinesInScheme(String[] var1, String var2) throws JQException;

    public List<DesignFormulaDefine> getAllFormulasInForms(String var1, String[] var2);

    public void deleteFormulaDefinesByForm(String var1);

    public void deleteFormulaDefinesByFormInScheme(String var1, String var2);

    public void deleteFormulaDefinesByScheme(String var1);

    public void deleteFormulaDefinesByTask(String var1);

    public Map<String, Integer> getFormulaCodeCountByScheme(String var1) throws JQException;

    public List<DesignFormulaDefine> getAllSoftFormulasInScheme(String var1) throws JQException;

    public List<DesignFormulaDefine> getAllSoftFormulasInForm(String var1, String var2) throws JQException;

    public DesignFormulaDefine querySoftFormulaDefine(String var1) throws JQException;

    public List<DesignFormulaSchemeDefine> getAllFormulaSchemeDefines();

    public List<FormulaVariDefine> queryAllFormulaVariable(String var1);

    public List<FormulaVariDefine> queryFormulaVariableByCodeOrTitle(String var1, String var2);

    public List<FormulaVariDefine> queryFormulaVariableByCode(String var1, String var2);

    public void addFormulaVariable(FormulaVariDefine var1) throws JQException;

    public void updateFormulaVariable(FormulaVariDefine var1) throws JQException;

    public void deleteFormulaVariable(String var1) throws JQException;

    public int getBJFormulaCountByFormulaSchemeKey(String var1, String var2);

    public List<FormulaFunctionDefine> queryAllFormulaFunction();

    public List<FormulaFunctionDefine> queryAllFormulaFunctionByType(int var1);

    public List<FormulaFunctionParamterDefine> queryAllFormulaFunctionParamter();

    public void insertFormulaFunction(FormulaFunctionDefine[] var1) throws JQException;

    public void insertFormulaFunctionParamter(FormulaFunctionParamterDefine[] var1) throws JQException;

    public List<DesignFormulaDTO> parseFormulaExpressionBeforeFormSave(String var1, String var2, String var3);

    public List<DesignFormulaDefine> querySimpleFormulaDefineByScheme(String var1) throws JQException;

    public DesignFormulaConditionLink initDesignFormulaConditionLink();

    public List<DesignFormulaConditionLink> listConditionLinkByScheme(String var1);

    public void deleteFormulaConditionLinks(List<DesignFormulaConditionLink> var1);

    public void insertFormulaConditionLinks(List<DesignFormulaConditionLink> var1);

    public List<DesignFormulaConditionLink> listConditionLinksByCondition(List<String> var1);

    public void deleteFormulaConditionLinkByScheme(String var1);

    public void deleteFormulaConditionLinkByFormula(String ... var1);

    public void deleteFormulaConditionLinkByCondition(String ... var1);

    public List<DesignFormulaCondition> listFormulaConditionByTask(String var1);

    public List<DesignFormulaCondition> listFormulaConditionByKey(List<String> var1);

    public DesignFormulaCondition initFormulaCondition();

    public void updateFormulaCondition(DesignFormulaCondition var1);

    public void deleteFormulaCondition(String var1);

    public void insertFormulaCondition(DesignFormulaCondition var1);

    public List<DesignFormulaCondition> listFormulaConditionByTask(String var1, Long var2, Long var3);

    public void updateFormulaConditions(List<DesignFormulaCondition> var1);

    public void deleteFormulaConditionByTask(String var1);

    public void insertFormulaConditions(List<DesignFormulaCondition> var1);

    public void deleteFormulaConditions(List<String> var1);

    public List<DesignFormulaCondition> listFormulaConditionByTaskAndCode(String var1, String ... var2);

    public List<DesignFormulaCondition> listFormulaConditionByScheme(String var1);

    public Map<String, List<DesignFormulaCondition>> listFormulaConditionBySchemeAndFormula(String var1, String ... var2);
}

