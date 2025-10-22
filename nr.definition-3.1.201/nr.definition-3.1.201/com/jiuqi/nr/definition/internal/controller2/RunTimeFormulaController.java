/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 */
package com.jiuqi.nr.definition.internal.controller2;

import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.nr.definition.api.IRunTimeExtFormulaController;
import com.jiuqi.nr.definition.api.IRunTimeFormulaController;
import com.jiuqi.nr.definition.common.CalcItem;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaField;
import com.jiuqi.nr.definition.facade.FormulaParsedExp;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaVariDefine;
import com.jiuqi.nr.definition.facade.formula.DesignFormulaCondition;
import com.jiuqi.nr.definition.facade.formula.DesignFormulaConditionLink;
import com.jiuqi.nr.definition.internal.runtime.controller.IRunTimeFormulaVariableService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeExpressionService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormulaSchemeService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormulaService;
import com.jiuqi.nr.definition.internal.service.ParamStreamService;
import com.jiuqi.nr.definition.internal.stream.param.FormulaListStream;
import com.jiuqi.nr.definition.internal.stream.param.FormulaSchemeListStream;
import com.jiuqi.nr.definition.internal.stream.param.FormulaSchemeStream;
import com.jiuqi.nr.definition.internal.stream.param.FormulaStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class RunTimeFormulaController
implements IRunTimeFormulaController {
    private static final Logger logger = LoggerFactory.getLogger(RunTimeFormulaController.class);
    @Autowired
    private IRuntimeFormulaSchemeService formulaSchemeService;
    @Autowired
    private IRuntimeFormulaService formulaService;
    @Autowired
    private IRuntimeExpressionService expressionService;
    @Autowired
    private IRunTimeExtFormulaController extFormulaRunTimeController;
    @Autowired
    private IRunTimeFormulaVariableService runtimeFormulaVariableService;
    @Autowired
    private ParamStreamService paramStreamService;
    @Autowired
    private IFormulaRunTimeController oldFormulaRunTimeController;

    @Override
    public FormulaSchemeStream getFormulaScheme(String formulaScheme) {
        FormulaSchemeDefine formulaSchemeDefine = this.formulaSchemeService.queryFormulaScheme(formulaScheme);
        return this.paramStreamService.getFormulaSchemeStream(formulaSchemeDefine);
    }

    @Override
    public FormulaSchemeStream getDefaultFormulaSchemeByFormScheme(String formSchemeKey) {
        FormulaSchemeDefine defaultFormulaSchemeInFormScheme = this.formulaSchemeService.getDefaultFormulaSchemeInFormScheme(formSchemeKey);
        return this.paramStreamService.getFormulaSchemeStream(defaultFormulaSchemeInFormScheme);
    }

    @Override
    public FormulaSchemeListStream listFormulaSchemeByFormScheme(String formSchemeKey) {
        List<FormulaSchemeDefine> formulaSchemesByFormScheme = this.formulaSchemeService.getFormulaSchemesByFormScheme(formSchemeKey);
        return this.paramStreamService.getFormulaSchemeListStream(formulaSchemesByFormScheme);
    }

    @Override
    public FormulaSchemeListStream listFinanceFormulaSchemeByFormScheme(String formSchemeKey) {
        List<FormulaSchemeDefine> formulaSchemesByFormScheme = this.formulaSchemeService.getFormulaSchemesByFormScheme(formSchemeKey, FormulaSchemeType.FORMULA_SCHEME_TYPE_FINANCIAL);
        return this.paramStreamService.getFormulaSchemeListStream(formulaSchemesByFormScheme);
    }

    @Override
    public FormulaSchemeListStream listPickFormulaSchemeByFormScheme(String formSchemeKey) {
        List<FormulaSchemeDefine> formulaSchemesByFormScheme = this.formulaSchemeService.getFormulaSchemesByFormScheme(formSchemeKey, FormulaSchemeType.FORMULA_SCHEME_TYPE_PICKNUM);
        return this.paramStreamService.getFormulaSchemeListStream(formulaSchemesByFormScheme);
    }

    @Override
    public FormulaSchemeListStream listReportFormulaSchemeByFormScheme(String formSchemeKey) {
        List<FormulaSchemeDefine> formulaSchemesByFormScheme = this.formulaSchemeService.getFormulaSchemesByFormScheme(formSchemeKey, FormulaSchemeType.FORMULA_SCHEME_TYPE_REPORT);
        return this.paramStreamService.getFormulaSchemeListStream(formulaSchemesByFormScheme);
    }

    @Override
    public FormulaStream getFormula(String key) {
        FormulaDefine formulaDefine = this.formulaService.queryFormula(key);
        if (null == formulaDefine && this.extFormulaRunTimeController.getExistPrivateFormula()) {
            FormulaDefine formula = this.extFormulaRunTimeController.getFormula(key);
            return this.paramStreamService.getFormulaStream(formula);
        }
        return this.paramStreamService.getFormulaStream(formulaDefine);
    }

    @Override
    public FormulaStream getFormulaByCodeAndScheme(String formulaCode, String formulaSchemeKey) {
        FormulaDefine formula = this.formulaService.findFormula(formulaCode, formulaSchemeKey);
        if (null == formula && this.extFormulaRunTimeController.getExistPrivateFormula()) {
            FormulaDefine formulaByCodeAndScheme = this.extFormulaRunTimeController.getFormulaByCodeAndScheme(formulaCode, formulaSchemeKey);
            return this.paramStreamService.getFormulaStream(formulaByCodeAndScheme);
        }
        return this.paramStreamService.getFormulaStream(formula);
    }

    @Override
    public FormulaListStream listFormulaByScheme(String formulaSchemeKey) {
        List<FormulaDefine> formulasInScheme = this.formulaService.getFormulasInScheme(formulaSchemeKey);
        return this.paramStreamService.getFormulaListStream(formulasInScheme);
    }

    @Override
    public FormulaListStream listCalculateFormulaByScheme(String formulaSchemeKey) {
        List<FormulaDefine> formulasInScheme = this.formulaService.getFormulasInScheme(formulaSchemeKey, DataEngineConsts.FormulaType.CALCULATE);
        return this.paramStreamService.getFormulaListStream(formulasInScheme);
    }

    @Override
    public FormulaListStream listCheckFormulaByScheme(String formulaSchemeKey) {
        List<FormulaDefine> formulasInScheme = this.formulaService.getFormulasInScheme(formulaSchemeKey, DataEngineConsts.FormulaType.CHECK);
        return this.paramStreamService.getFormulaListStream(formulasInScheme);
    }

    @Override
    public FormulaListStream listBalanceFormulaByScheme(String formulaSchemeKey) {
        List<FormulaDefine> formulasInScheme = this.formulaService.getFormulasInScheme(formulaSchemeKey, DataEngineConsts.FormulaType.BALANCE);
        return this.paramStreamService.getFormulaListStream(formulasInScheme);
    }

    @Override
    public FormulaListStream listFormulaBySchemeAndForm(String formulaSchemeKey, String formKey) {
        List<FormulaDefine> formulasInForm = this.formulaService.getFormulasInForm(formulaSchemeKey, formKey);
        return this.paramStreamService.getFormulaListStream(formulasInForm);
    }

    @Override
    public FormulaListStream listCalculateFormulaBySchemeAndForm(String formulaSchemeKey, String formKey) {
        List<FormulaDefine> formulasInFormByType = this.formulaService.getFormulasInFormByType(formulaSchemeKey, formKey, DataEngineConsts.FormulaType.CALCULATE);
        return this.paramStreamService.getFormulaListStream(formulasInFormByType);
    }

    @Override
    public FormulaListStream listCheckFormulaBySchemeAndForm(String formulaSchemeKey, String formKey) {
        List<FormulaDefine> formulasInFormByType = this.formulaService.getFormulasInFormByType(formulaSchemeKey, formKey, DataEngineConsts.FormulaType.CHECK);
        return this.paramStreamService.getFormulaListStream(formulasInFormByType);
    }

    @Override
    public FormulaListStream listBalanceFormulaBySchemeAndForm(String formulaSchemeKey, String formKey) {
        List<FormulaDefine> formulasInFormByType = this.formulaService.getFormulasInFormByType(formulaSchemeKey, formKey, DataEngineConsts.FormulaType.BALANCE);
        return this.paramStreamService.getFormulaListStream(formulasInFormByType);
    }

    @Override
    public List<IParsedExpression> listExpressionBySchemeAndFormAndType(String formulaSchemeKey, String form, DataEngineConsts.FormulaType formulaType) {
        ArrayList<IParsedExpression> expressionByPublicForm = new ArrayList<IParsedExpression>();
        List<IParsedExpression> expressionByForm = this.expressionService.getParsedExpressionByForm(formulaSchemeKey, form, formulaType);
        List<IParsedExpression> result = this.filterExpressions(expressionByPublicForm, expressionByForm);
        return result;
    }

    @Override
    public List<IParsedExpression> listExpressionByFormula(String formulaScheme, String formula) {
        return this.expressionService.getParsedExpressionByFormulas(formulaScheme, formula);
    }

    @Override
    public List<IParsedExpression> listExpressionBySchemeAndFormsAndType(String formulaSchemeKey, List<String> form, DataEngineConsts.FormulaType formulaType) {
        ArrayList<IParsedExpression> expressionByPublicForm = new ArrayList<IParsedExpression>();
        List<IParsedExpression> expressionByForm = this.expressionService.getParsedExpressionByForms(formulaSchemeKey, form, formulaType);
        List<IParsedExpression> result = this.filterExpressions(expressionByPublicForm, expressionByForm);
        return result;
    }

    @Override
    public List<IParsedExpression> listBetweenExpressionBySchemeAndType(String formulaSchemeKey, DataEngineConsts.FormulaType formulaType) {
        ArrayList<IParsedExpression> result = new ArrayList<IParsedExpression>();
        List<IParsedExpression> parsedExpressionBetweenTable = this.expressionService.getParsedExpressionBetweenTable(formulaSchemeKey, formulaType);
        result.addAll(parsedExpressionBetweenTable);
        return result;
    }

    @Override
    public List<IParsedExpression> listExpressionBySchemeAndFormAndTypeAndLinkCode(String formulaSchemeKey, DataEngineConsts.FormulaType formulaType, String form, String datalinkCode) {
        ArrayList<IParsedExpression> expressionByPublicForm = new ArrayList<IParsedExpression>();
        List<IParsedExpression> expressionByForm = this.expressionService.getParsedExpressionByDataLink(datalinkCode, formulaSchemeKey, form, formulaType);
        List<IParsedExpression> result = this.filterExpressions(expressionByPublicForm, expressionByForm);
        return result;
    }

    @Override
    public IParsedExpression getExpressionBySchemeAndExpression(String formulaSchemeKey, String expression) {
        IParsedExpression parsedExpression2 = this.expressionService.getParsedExpression(formulaSchemeKey, expression);
        if (parsedExpression2 != null) {
            return parsedExpression2;
        }
        return null;
    }

    @Override
    public IParsedExpression getExpressionBySchemeAndFormAndExpression(String formulaSchemeKey, String form, String expression) {
        IParsedExpression parsedExpression2 = this.expressionService.getParsedExpression(formulaSchemeKey, expression);
        if (parsedExpression2 != null) {
            return parsedExpression2;
        }
        if (this.extFormulaRunTimeController.getExistPrivateFormula() && (parsedExpression2 = this.extFormulaRunTimeController.getExpressionBySchemeAndFormAndExpression(formulaSchemeKey, form, expression)) != null) {
            return parsedExpression2;
        }
        return null;
    }

    @Override
    public Collection<String> listCalcCellDataLinkBySchemeAndForm(String formulaSchemeKey, String form) {
        Collection<String> links = this.expressionService.getCalcCellDataLinks(formulaSchemeKey, form);
        ArrayList<String> result = new ArrayList<String>();
        if (links != null) {
            result.addAll(links);
        }
        return result;
    }

    @Override
    public String getScriptBySchemeAndForm(String formulaSchemeKey, String form) {
        return this.expressionService.getCalculateJsFormulasInForm(formulaSchemeKey, form);
    }

    @Override
    public List<CalcItem> listDimensionCalcCellsBySchemeAndForm(String formulaSchemeKey, String form) {
        return this.expressionService.getDimensionCalcCells(formulaSchemeKey, form);
    }

    @Override
    public List<IParsedExpression> listFormConditionsExpressionByFormScheme(String formScheme) {
        return this.oldFormulaRunTimeController.getFormConditionsByFormScheme(formScheme);
    }

    @Override
    public HashSet<String> listFormKeyByFormSchemeHaveFormConditions(String formScheme) {
        return this.oldFormulaRunTimeController.getReloadFormsByFormScheme(formScheme);
    }

    @Override
    public HashSet<String> listFieldKeyByFormSchemeHaveFormConditions(String formScheme) {
        return this.oldFormulaRunTimeController.getConditionFieldsByFormScheme(formScheme);
    }

    @Override
    public List<FormulaVariDefine> listFormulaVariByFormScheme(String formScheme) {
        return this.runtimeFormulaVariableService.queryAllFormulaVariable(formScheme);
    }

    private List<IParsedExpression> filterExpressions(List<IParsedExpression> expressionByPublicForm, List<IParsedExpression> expressionByForm) {
        ArrayList<IParsedExpression> result = new ArrayList<IParsedExpression>();
        HashSet formulaKeys = new HashSet();
        if (expressionByPublicForm != null && expressionByPublicForm.size() > 0) {
            result.addAll(expressionByPublicForm);
            formulaKeys.addAll(expressionByPublicForm.stream().map(t -> t.getSource().getId()).collect(Collectors.toList()));
        }
        if (expressionByForm != null) {
            if (formulaKeys.size() > 0) {
                for (IParsedExpression expression : expressionByForm) {
                    if (formulaKeys.contains(expression.getSource().getId())) continue;
                    result.add(expression);
                }
            } else {
                result.addAll(expressionByForm);
            }
        }
        return result;
    }

    @Override
    public boolean isParsedFormulaFieldException(String formulaSchemeKey) {
        return this.expressionService.isParsedFormulaFieldException(formulaSchemeKey);
    }

    @Override
    public FormulaField getFormulaField(String formulaSchemeKey, String fieldKey) {
        return this.expressionService.getFormulaField(formulaSchemeKey, fieldKey);
    }

    @Override
    public List<FormulaField> listFormulaFields(String formulaSchemeKey, List<String> fieldKeys) {
        if (CollectionUtils.isEmpty(fieldKeys)) {
            return Collections.emptyList();
        }
        return this.expressionService.getFormulaFields(formulaSchemeKey, fieldKeys);
    }

    @Override
    public FormulaParsedExp getFormulaParsedExp(String formulaSchemeKey, String expKey) {
        return this.expressionService.getFormulaParsedExp(formulaSchemeKey, expKey);
    }

    @Override
    public List<FormulaParsedExp> listFormulaParsedExps(String formulaSchemeKey, List<String> expKeys) {
        if (CollectionUtils.isEmpty(expKeys)) {
            return Collections.emptyList();
        }
        return this.expressionService.getFormulaParsedExps(formulaSchemeKey, expKeys);
    }

    @Override
    public Map<String, String> getEffectiveForms(String formulaSchemeKey, List<String> formulaKeys) {
        if (CollectionUtils.isEmpty(formulaKeys)) {
            return Collections.emptyMap();
        }
        return this.formulaService.getEffectiveForms(formulaSchemeKey, formulaKeys);
    }

    @Override
    public List<DesignFormulaCondition> listFormulaConditionByTask(String task) {
        return Collections.emptyList();
    }

    @Override
    public List<DesignFormulaCondition> listFormulaConditionByScheme(String formulaScheme) {
        return Collections.emptyList();
    }

    @Override
    public List<DesignFormulaConditionLink> listFormulaConditionLinkByScheme(String formulaScheme) {
        return Collections.emptyList();
    }
}

