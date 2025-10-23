/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.period.util.JacksonUtils
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.fmdm.internal.formula;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.period.util.JacksonUtils;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public class FormulaUtil {
    private FormulaUtil() {
    }

    public static List<Formula> getFormulas(String formulaSchemeKey, String formKey) {
        IFormulaRunTimeController formulaRunTimeController = (IFormulaRunTimeController)BeanUtil.getBean(IFormulaRunTimeController.class);
        IRunTimeViewController runtimeView = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        ArrayList<Formula> formulas = new ArrayList<Formula>();
        INvwaSystemOptionService systemOptionService = (INvwaSystemOptionService)BeanUtil.getBean(INvwaSystemOptionService.class);
        String optionValue = systemOptionService.findValueById("OPTION_SAVE_CHECK");
        if (StringUtils.isEmpty((String)optionValue)) {
            return new ArrayList<Formula>();
        }
        List enableCheckType = JacksonUtils.toList((String)optionValue, String.class);
        List formulaDefines = formulaRunTimeController.getCheckFormulasInForm(formulaSchemeKey, formKey);
        if (!CollectionUtils.isEmpty(formulaDefines)) {
            for (FormulaDefine formulaDefine : formulaDefines) {
                FormDefine formulaForm;
                int checkType = formulaDefine.getCheckType();
                if (!enableCheckType.contains(String.valueOf(checkType))) continue;
                Formula formula = new Formula();
                formula.setCode(formulaDefine.getCode());
                formula.setId(formulaDefine.getKey());
                formula.setMeanning(formulaDefine.getDescription());
                if (StringUtils.isEmpty((String)formKey)) {
                    formulaForm = runtimeView.queryFormById(formulaDefine.getFormKey());
                    formula.setReportName(formulaForm.getFormCode());
                    formula.setFormKey(formKey);
                } else if (formulaDefine.getFormKey() != null) {
                    formula.setFormKey(formulaDefine.getFormKey());
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

    public static List<DataField> getDataField(String formKey) {
        ArrayList<DataField> fields = new ArrayList<DataField>();
        IRunTimeViewController runtimeView = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        List linkData = runtimeView.getAllLinksInForm(formKey);
        List fieldKeys = linkData.stream().filter(e -> !e.getType().equals((Object)DataLinkType.DATA_LINK_TYPE_FMDM)).map(DataLinkDefine::getLinkExpression).collect(Collectors.toList());
        IRuntimeDataSchemeService dataSchemeService = (IRuntimeDataSchemeService)BeanUtil.getBean(IRuntimeDataSchemeService.class);
        List dataFields = dataSchemeService.getDataFields(fieldKeys);
        for (DataField field : dataFields) {
            List validationRules = field.getValidationRules();
            if (CollectionUtils.isEmpty(validationRules)) continue;
            fields.add(field);
        }
        return fields;
    }
}

