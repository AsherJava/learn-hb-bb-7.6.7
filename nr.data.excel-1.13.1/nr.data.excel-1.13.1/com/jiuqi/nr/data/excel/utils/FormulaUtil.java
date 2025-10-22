/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.data.excel.utils;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.nr.data.excel.param.FormulaData;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FormulaUtil {
    private static final Logger logger = LoggerFactory.getLogger(FormulaUtil.class);
    public static String betweenParsedForm = "betweenParsed";

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
                formulaSchemeDefine.add(formulaRunTimeController.queryFormulaSchemeDefine(formulaSchemeKey));
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
}

