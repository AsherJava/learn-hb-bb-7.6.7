/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeExpressionService
 */
package com.jiuqi.nr.jtable.util;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeExpressionService;
import com.jiuqi.nr.jtable.filter.IFormulaFilter;
import com.jiuqi.nr.jtable.params.base.FormulaData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FormulaUtil {
    private static final Logger logger = LoggerFactory.getLogger(FormulaUtil.class);
    public static String betweenParsedForm = "betweenParsed";

    public static List<IParsedExpression> getParsedExpressions(String formulaSchemeKey, String formKey, DataEngineConsts.FormulaType formulaType) throws Exception {
        return FormulaUtil.getParsedExpressions(null, formulaSchemeKey, formKey, formulaType);
    }

    public static List<IParsedExpression> getParsedExpressions(JtableContext context, String formulaSchemeKey, String formKey, DataEngineConsts.FormulaType formulaType) throws Exception {
        IFormulaRunTimeController formulaRunTimeController = (IFormulaRunTimeController)BeanUtil.getBean(IFormulaRunTimeController.class);
        ArrayList<IParsedExpression> parsedExpressions = new ArrayList();
        try {
            parsedExpressions = formulaRunTimeController.getParsedExpressionByForm(formulaSchemeKey, formKey, formulaType);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            throw e;
        }
        if (parsedExpressions == null) {
            parsedExpressions = new ArrayList();
        }
        JtableContext jtableContext = new JtableContext();
        jtableContext.setFormulaSchemeKey(formulaSchemeKey);
        jtableContext.setFormKey(formKey);
        if (context != null) {
            jtableContext.setVariableMap(context.getVariableMap());
            jtableContext.setTaskKey(context.getTaskKey());
        }
        parsedExpressions = FormulaUtil.filterParsedExpression(jtableContext, parsedExpressions);
        return parsedExpressions;
    }

    public static List<IParsedExpression> getBetweenParsedExpressions(JtableContext context, String formulaSchemeKey, DataEngineConsts.FormulaType formulaType) throws Exception {
        IFormulaRunTimeController formulaRunTimeController = (IFormulaRunTimeController)BeanUtil.getBean(IFormulaRunTimeController.class);
        ArrayList<IParsedExpression> parsedExpressions = new ArrayList();
        try {
            parsedExpressions = formulaRunTimeController.getParsedExpressionBetweenTable(formulaSchemeKey, formulaType);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            throw e;
        }
        if (parsedExpressions == null) {
            parsedExpressions = new ArrayList();
        }
        JtableContext jtableContext = new JtableContext();
        jtableContext.setFormulaSchemeKey(formulaSchemeKey);
        jtableContext.setFormKey("00000000-0000-0000-0000-000000000000");
        if (context != null) {
            jtableContext.setVariableMap(context.getVariableMap());
            jtableContext.setTaskKey(context.getTaskKey());
        }
        parsedExpressions = FormulaUtil.filterParsedExpression(jtableContext, parsedExpressions);
        return parsedExpressions;
    }

    public static List<IParsedExpression> getBetweenParsedExpressions(String formulaSchemeKey, DataEngineConsts.FormulaType formulaType) throws Exception {
        return FormulaUtil.getBetweenParsedExpressions(null, formulaSchemeKey, formulaType);
    }

    public static List<FormulaData> getFormulaDatas(String formulaSchemeKey, String formKey, DataEngineConsts.FormulaType formulaType) {
        IFormulaRunTimeController formulaRunTimeController = (IFormulaRunTimeController)BeanUtil.getBean(IFormulaRunTimeController.class);
        List formulaDefines = new ArrayList();
        try {
            switch (formulaType) {
                case CALCULATE: {
                    formulaDefines = formulaRunTimeController.getCalculateFormulasInForm(formulaSchemeKey, formKey);
                    break;
                }
                case CHECK: {
                    formulaDefines = formulaRunTimeController.getCheckFormulasInForm(formulaSchemeKey, formKey);
                    break;
                }
                case BALANCE: {
                    formulaDefines = formulaRunTimeController.getBalanceFormulasInForm(formulaSchemeKey, formKey);
                    break;
                }
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        if (formulaDefines == null) {
            formulaDefines = new ArrayList();
        }
        ArrayList<FormulaData> formulas = new ArrayList<FormulaData>();
        for (FormulaDefine formulaDefine : formulaDefines) {
            formulas.add(new FormulaData(formulaDefine));
        }
        return formulas;
    }

    public static List<Formula> getFormulas(String formulaSchemeKey, String formKey, DataEngineConsts.FormulaType formulaType) {
        IFormulaRunTimeController formulaRunTimeController = (IFormulaRunTimeController)BeanUtil.getBean(IFormulaRunTimeController.class);
        IRunTimeViewController runtimeView = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        ArrayList<Formula> formulas = new ArrayList<Formula>();
        List formulaDefines = null;
        if (StringUtils.isEmpty((String)formKey)) {
            if (DataEngineConsts.FormulaType.CALCULATE == formulaType) {
                formulaDefines = formulaRunTimeController.getCalculateFormulasInScheme(formulaSchemeKey);
            } else if (DataEngineConsts.FormulaType.CHECK == formulaType) {
                formulaDefines = formulaRunTimeController.getCheckFormulasInScheme(formulaSchemeKey);
            }
        } else if (DataEngineConsts.FormulaType.CALCULATE == formulaType) {
            formulaDefines = formulaRunTimeController.getCalculateFormulasInForm(formulaSchemeKey, formKey);
        } else if (DataEngineConsts.FormulaType.CHECK == formulaType) {
            formulaDefines = formulaRunTimeController.getCheckFormulasInForm(formulaSchemeKey, formKey);
        }
        if (formulaDefines != null && formulaDefines.size() > 0) {
            for (FormulaDefine formulaDefine : formulaDefines) {
                FormDefine formulaForm;
                Formula formula = new Formula();
                formula.setCode(formulaDefine.getCode());
                formula.setId(formulaDefine.getKey().toString());
                formula.setMeanning(formulaDefine.getDescription());
                if (StringUtils.isEmpty((String)formKey)) {
                    formulaForm = runtimeView.queryFormById(formulaDefine.getFormKey());
                    formula.setReportName(formulaForm.getFormCode());
                    formula.setFormKey(formKey);
                } else if (formulaDefine.getFormKey() != null) {
                    formula.setFormKey(formulaDefine.getFormKey().toString());
                    formulaForm = runtimeView.queryFormById(formulaDefine.getFormKey());
                    if (formulaForm != null) {
                        formula.setReportName(formulaForm.getFormCode());
                    }
                }
                formula.setFormula(formulaDefine.getExpression());
                formula.setChecktype(Integer.valueOf(formulaDefine.getCheckType()));
                formulas.add(formula);
            }
        }
        return formulas;
    }

    public static List<FormulaSchemeDefine> getFormulaSchemeList(String formSchemeKey, String formulaSchemeKeyStr) {
        List formulaSchemes;
        IFormulaRunTimeController formulaRunTimeController = (IFormulaRunTimeController)BeanUtil.getBean(IFormulaRunTimeController.class);
        ArrayList<FormulaSchemeDefine> formulaSchemeDefine = new ArrayList<FormulaSchemeDefine>();
        if (StringUtils.isNotEmpty((String)formulaSchemeKeyStr)) {
            String[] idArray = formulaSchemeKeyStr.split(";");
            for (int i = 0; i < idArray.length; ++i) {
                String formulaSchemeKey = idArray[i];
                FormulaSchemeDefine define = formulaRunTimeController.queryFormulaSchemeDefine(formulaSchemeKey);
                if (define == null) continue;
                formulaSchemeDefine.add(define);
            }
        }
        if (!formulaSchemeDefine.isEmpty()) {
            return formulaSchemeDefine;
        }
        if (StringUtils.isNotEmpty((String)formSchemeKey) && (formulaSchemes = formulaRunTimeController.getAllFormulaSchemeDefinesByFormScheme(formSchemeKey)) != null && formulaSchemes.size() > 0) {
            for (FormulaSchemeDefine formulaScheme : formulaSchemes) {
                if (!formulaScheme.isDefault()) continue;
                formulaSchemeDefine.add(formulaScheme);
            }
        }
        return formulaSchemeDefine;
    }

    public static List<IParsedExpression> getFormulaList(String formulaSchemeKey, Map<String, List<String>> formulas, List<String> forms, String formSchemeKey, DataEngineConsts.FormulaType formulaType) throws Exception {
        ArrayList<IParsedExpression> parsedExpressionList = new ArrayList<IParsedExpression>();
        for (String formKey : forms) {
            Formula formula;
            List<IParsedExpression> parsedExpressions;
            List<String> formulaList;
            if (formKey.equals("00000000-0000-0000-0000-000000000000")) {
                formulaList = formulas.get(betweenParsedForm);
                if (formulaList == null || formulaList.isEmpty()) {
                    formulaList = formulas.get("00000000-0000-0000-0000-000000000000");
                }
                parsedExpressions = FormulaUtil.getBetweenParsedExpressions(formulaSchemeKey, formulaType);
                if (formulaList == null || formulaList.isEmpty()) {
                    parsedExpressionList.addAll(parsedExpressions);
                    continue;
                }
                for (IParsedExpression parsedExpression : parsedExpressions) {
                    formula = parsedExpression.getSource();
                    if (!formulaList.contains(formula.getId())) continue;
                    parsedExpressionList.add(parsedExpression);
                }
                continue;
            }
            formulaList = formulas.get(formKey);
            parsedExpressions = FormulaUtil.getParsedExpressions(formulaSchemeKey, formKey, formulaType);
            if (formulaList == null || formulaList.isEmpty()) {
                parsedExpressionList.addAll(parsedExpressions);
                continue;
            }
            for (IParsedExpression parsedExpression : parsedExpressions) {
                formula = parsedExpression.getSource();
                if (!formulaList.contains(formula.getId())) continue;
                parsedExpressionList.add(parsedExpression);
            }
        }
        return parsedExpressionList;
    }

    public static Map<String, String> getBalanceFormulaMap(String formulaSchemeKey, String formKey) {
        Map<String, String> balanceFormulaMap = new HashMap<String, String>();
        IRuntimeExpressionService runtimeExpressionService = (IRuntimeExpressionService)BeanUtil.getBean(IRuntimeExpressionService.class);
        Map balanceMap = runtimeExpressionService.getBalanceZBExpressionByForm(formulaSchemeKey, formKey);
        if (balanceMap != null) {
            balanceFormulaMap = balanceMap;
        }
        return balanceFormulaMap;
    }

    private static List<IParsedExpression> filterParsedExpression(JtableContext jtableContext, List<IParsedExpression> parsedExpressions) {
        Map<String, IFormulaFilter> formulaFilterMap = SpringBeanUtils.getApplicationContext().getBeansOfType(IFormulaFilter.class);
        for (IFormulaFilter formulaFilter : formulaFilterMap.values()) {
            parsedExpressions = formulaFilter.filterParsedExpression(jtableContext, parsedExpressions);
        }
        return parsedExpressions;
    }
}

