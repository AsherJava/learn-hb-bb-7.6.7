/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 */
package com.jiuqi.nr.definition.internal.runtime.controller;

import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.definition.common.CalcItem;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.controller.IExtFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.formula.FormulaCondition;
import com.jiuqi.nr.definition.facade.formula.FormulaConditionLink;
import com.jiuqi.nr.definition.internal.dao.RunTimeFormulaDefineDao;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeExpressionService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormulaSchemeService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormulaService;
import com.jiuqi.nr.definition.internal.runtime.service.RunTimeFormulaConditionService;
import com.jiuqi.nr.definition.internal.runtime.service.RuntimeFormConditionService;
import com.jiuqi.nr.definition.paramlanguage.common.LanguageTypeUtil;
import com.jiuqi.nr.definition.paramlanguage.service.DefaultLanguageService;
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

@Component
class RuntimeFormulaController
implements IFormulaRunTimeController {
    private static final Logger logger = LoggerFactory.getLogger(RuntimeFormulaController.class);
    @Autowired
    private IRuntimeFormulaSchemeService formulaSchemeService;
    @Autowired
    private IRuntimeFormulaService formulaService;
    @Autowired
    private IRuntimeExpressionService expressionService;
    @Autowired
    private RunTimeFormulaDefineDao formulaDao;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private IDataDefinitionRuntimeController iDataDefinitionRuntimeController;
    @Autowired
    private IEntityViewRunTimeController iEntityViewRunTimeController;
    @Autowired
    private DefaultLanguageService defaultLanguageService;
    @Autowired
    private LanguageTypeUtil languageTypeUtil;
    @Autowired
    private RuntimeFormConditionService formConditionService;
    @Autowired
    private IExtFormulaRunTimeController extFormulaRunTimeController;
    @Autowired
    private RunTimeFormulaConditionService formulaConditionService;
    private static final int FORMULA_TYPE = 1;

    RuntimeFormulaController() {
    }

    @Override
    public FormulaSchemeDefine queryFormulaSchemeDefine(String formulaSchemeKey) {
        return this.formulaSchemeService.queryFormulaScheme(formulaSchemeKey);
    }

    @Override
    public FormulaSchemeDefine getDefaultFormulaSchemeInFormScheme(String fromSchemeKey) {
        return this.formulaSchemeService.getDefaultFormulaSchemeInFormScheme(fromSchemeKey);
    }

    @Override
    public List<FormulaSchemeDefine> getAllFormulaSchemeDefinesByFormScheme(String formSchemeKey) {
        return this.formulaSchemeService.getFormulaSchemesByFormScheme(formSchemeKey);
    }

    @Override
    public List<FormulaSchemeDefine> getAllCWFormulaSchemeDefinesByFormScheme(String formSchemeKey) {
        List<FormulaSchemeDefine> formulaSchemesByFormScheme = this.formulaSchemeService.getFormulaSchemesByFormScheme(formSchemeKey, FormulaSchemeType.FORMULA_SCHEME_TYPE_FINANCIAL);
        return formulaSchemesByFormScheme;
    }

    @Override
    public List<FormulaSchemeDefine> getAllRPTFormulaSchemeDefinesByFormScheme(String formSchemeKey) {
        List<FormulaSchemeDefine> formulaSchemesByFormScheme = this.formulaSchemeService.getFormulaSchemesByFormScheme(formSchemeKey, FormulaSchemeType.FORMULA_SCHEME_TYPE_REPORT);
        return formulaSchemesByFormScheme;
    }

    @Override
    public List<FormulaSchemeDefine> getAllPickFormulaSchemeDefinesByFormScheme(String formSchemeKey) {
        return this.formulaSchemeService.getFormulaSchemesByFormScheme(formSchemeKey, FormulaSchemeType.FORMULA_SCHEME_TYPE_PICKNUM);
    }

    @Override
    public FormulaDefine queryFormulaDefine(String formulaKey) {
        FormulaDefine formulaDefine = this.formulaService.queryFormula(formulaKey);
        if (null == formulaDefine && this.extFormulaRunTimeController.existPrivateFormula()) {
            return this.extFormulaRunTimeController.queryFormulaDefine(formulaKey);
        }
        return formulaDefine;
    }

    @Override
    public FormulaDefine findFormulaDefine(String formulaDefineCode, String formulaSchemeKey) {
        FormulaDefine formula = this.formulaService.findFormula(formulaDefineCode, formulaSchemeKey);
        if (null == formula && this.extFormulaRunTimeController.existPrivateFormula()) {
            return this.extFormulaRunTimeController.findFormulaDefine(formulaDefineCode, formulaSchemeKey);
        }
        return formula;
    }

    @Override
    public List<FormulaDefine> getAllFormulasInScheme(String formulaSchemeKey) {
        return this.formulaService.getFormulasInScheme(formulaSchemeKey);
    }

    @Override
    public List<FormulaDefine> getCalculateFormulasInScheme(String formulaSchemeKey) {
        return this.formulaService.getFormulasInScheme(formulaSchemeKey, DataEngineConsts.FormulaType.CALCULATE);
    }

    @Override
    public List<FormulaDefine> getCheckFormulasInScheme(String formulaSchemeKey) {
        return this.formulaService.getFormulasInScheme(formulaSchemeKey, DataEngineConsts.FormulaType.CHECK);
    }

    @Override
    public List<FormulaDefine> getBalanceFormulasInScheme(String formulaSchemeKey) {
        return this.formulaService.getFormulasInScheme(formulaSchemeKey, DataEngineConsts.FormulaType.BALANCE);
    }

    @Override
    public List<FormulaDefine> getAllFormulasInForm(String formulaSchemekey, String formkey) {
        return this.formulaService.getFormulasInForm(formulaSchemekey, formkey);
    }

    @Override
    public List<FormulaDefine> getCalculateFormulasInForm(String formulaSchemekey, String formkey) {
        List<FormulaDefine> formulaDefines = this.formulaService.getFormulasInFormByType(formulaSchemekey, formkey, DataEngineConsts.FormulaType.CALCULATE);
        return formulaDefines;
    }

    @Override
    public List<FormulaDefine> getCheckFormulasInForm(String formulaSchemekey, String formkey) {
        List<FormulaDefine> formulaDefines = this.formulaService.getFormulasInFormByType(formulaSchemekey, formkey, DataEngineConsts.FormulaType.CHECK);
        return formulaDefines;
    }

    @Override
    public List<FormulaDefine> getBalanceFormulasInForm(String formulaSchemekey, String formkey) {
        return this.formulaService.getFormulasInFormByType(formulaSchemekey, formkey, DataEngineConsts.FormulaType.BALANCE);
    }

    @Override
    public List<IParsedExpression> getParsedExpressionByForm(String formulaSchemeKey, String formKey, DataEngineConsts.FormulaType type) {
        ArrayList<IParsedExpression> expressionByPublicForm = new ArrayList<IParsedExpression>();
        List<IParsedExpression> expressionByForm = this.expressionService.getParsedExpressionByForm(formulaSchemeKey, formKey, type);
        List<IParsedExpression> result = this.filterExpressions(expressionByPublicForm, expressionByForm);
        return result;
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
    public List<IParsedExpression> getParsedExpressionByForms(String formulaSchemeKey, List<String> formKeys, DataEngineConsts.FormulaType type) {
        ArrayList<IParsedExpression> expressionByPublicForm = new ArrayList<IParsedExpression>();
        List<IParsedExpression> expressionByForm = this.expressionService.getParsedExpressionByForms(formulaSchemeKey, formKeys, type);
        List<IParsedExpression> result = this.filterExpressions(expressionByPublicForm, expressionByForm);
        return result;
    }

    @Override
    public List<IParsedExpression> getParsedExpressionBetweenTable(String formulaSchemeKey, DataEngineConsts.FormulaType type) {
        ArrayList<IParsedExpression> result = new ArrayList<IParsedExpression>();
        List<IParsedExpression> parsedExpressionBetweenTable = this.expressionService.getParsedExpressionBetweenTable(formulaSchemeKey, type);
        result.addAll(parsedExpressionBetweenTable);
        return result;
    }

    @Override
    public List<IParsedExpression> getParsedExpressionByDataLink(String dataLinkCode, String formulaSchemeKey, String formKey, DataEngineConsts.FormulaType type) {
        ArrayList<IParsedExpression> expressionByPublicForm = new ArrayList<IParsedExpression>();
        List<IParsedExpression> expressionByForm = this.expressionService.getParsedExpressionByDataLink(dataLinkCode, formulaSchemeKey, formKey, type);
        List<IParsedExpression> result = this.filterExpressions(expressionByPublicForm, expressionByForm);
        return result;
    }

    @Override
    public IParsedExpression getParsedExpression(String formulaSchemeKey, String expressionKey) {
        IParsedExpression parsedExpression2 = this.expressionService.getParsedExpression(formulaSchemeKey, expressionKey);
        if (parsedExpression2 != null) {
            return parsedExpression2;
        }
        return null;
    }

    @Override
    public IParsedExpression getParsedExpression(String formulaSchemeKey, String formKey, String expressionKey) {
        IParsedExpression parsedExpression2 = this.expressionService.getParsedExpression(formulaSchemeKey, expressionKey);
        if (parsedExpression2 != null) {
            return parsedExpression2;
        }
        if (this.extFormulaRunTimeController.existPrivateFormula() && (parsedExpression2 = this.extFormulaRunTimeController.getParsedExpression(formulaSchemeKey, formKey, expressionKey)) != null) {
            return parsedExpression2;
        }
        return null;
    }

    @Override
    public Collection<String> getCalcCellDataLinks(String formulaSchemeKey, String formKey) {
        Collection<String> links = this.expressionService.getCalcCellDataLinks(formulaSchemeKey, formKey);
        ArrayList<String> result = new ArrayList<String>();
        if (links != null) {
            result.addAll(links);
        }
        return result;
    }

    @Override
    public String getCalculateJsFormulasInForm(String formulaSchemeKey, String formKey) {
        return this.expressionService.getCalculateJsFormulasInForm(formulaSchemeKey, formKey);
    }

    @Override
    public List<FormulaDefine> searchFormulaInScheme(String formulaCode, String formulaSchemeKey) {
        return this.formulaService.searchFormulaInScheme(formulaCode, formulaSchemeKey);
    }

    @Override
    public List<CalcItem> getDimensionCalcCells(String formulaSchemeKey, String formKey) {
        return this.expressionService.getDimensionCalcCells(formulaSchemeKey, formKey);
    }

    @Override
    public List<IParsedExpression> getParsedExpressionByDataLink(List<String> linkCodes, String formulaSchemeKey, String formKey, DataEngineConsts.FormulaType formulaType, Integer direction) {
        List<IParsedExpression> expressionByForm = this.expressionService.getParsedExpressionByDataLink(linkCodes, formulaSchemeKey, formKey, formulaType, direction);
        ArrayList<IParsedExpression> expressionByPublicForm = new ArrayList<IParsedExpression>();
        return this.filterExpressions(expressionByPublicForm, expressionByForm);
    }

    @Override
    public List<FormulaDefine> queryPublicFormulaDefineByScheme(String formulaSchemeKey, String formKey) throws Exception {
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<FormulaDefine> queryPublicFormulaDefineByScheme(String formulaSchemeKey) throws Exception {
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<IParsedExpression> getFormConditionsByFormScheme(String formSchemeKey) {
        try {
            ExecutorContext executorContext = new ExecutorContext(this.iDataDefinitionRuntimeController);
            ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.iRunTimeViewController, this.iDataDefinitionRuntimeController, this.iEntityViewRunTimeController, formSchemeKey, true);
            executorContext.setEnv((IFmlExecEnvironment)environment);
            return this.formConditionService.getFormConditionsByFormScheme(formSchemeKey, executorContext);
        }
        catch (Exception e) {
            logger.error("\u62a5\u8868\u65b9\u6848\uff1a".concat(formSchemeKey).concat("\u9002\u5e94\u6027\u6761\u4ef6\u89e3\u6790\u51fa\u9519"), e);
            return null;
        }
    }

    @Override
    public HashSet<String> getReloadFormsByFormScheme(String formSchemeKey) {
        HashSet<String> reloadForms = new HashSet<String>();
        try {
            ExecutorContext executorContext = new ExecutorContext(this.iDataDefinitionRuntimeController);
            ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.iRunTimeViewController, this.iDataDefinitionRuntimeController, this.iEntityViewRunTimeController, formSchemeKey, true);
            executorContext.setEnv((IFmlExecEnvironment)environment);
            return this.formConditionService.getReloadFormsByFormScheme(formSchemeKey, executorContext);
        }
        catch (Exception e) {
            logger.error("\u62a5\u8868\u65b9\u6848\uff1a".concat(formSchemeKey).concat("\u9002\u5e94\u6027\u6761\u4ef6\u89e3\u6790\u51fa\u9519"), e);
            return reloadForms;
        }
    }

    @Override
    public HashSet<String> getConditionFieldsByFormScheme(String formSchemeKey) {
        HashSet<String> reloadFields = new HashSet<String>();
        try {
            ExecutorContext executorContext = new ExecutorContext(this.iDataDefinitionRuntimeController);
            ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.iRunTimeViewController, this.iDataDefinitionRuntimeController, this.iEntityViewRunTimeController, formSchemeKey, true);
            executorContext.setEnv((IFmlExecEnvironment)environment);
            return this.formConditionService.getConditionFieldsByFormScheme(formSchemeKey, executorContext);
        }
        catch (Exception e) {
            logger.error("\u62a5\u8868\u65b9\u6848\uff1a".concat(formSchemeKey).concat("\u9002\u5e94\u6027\u6761\u4ef6\u89e3\u6790\u51fa\u9519"), e);
            return reloadFields;
        }
    }

    @Override
    public Map<String, List<IParsedExpression>> getParsedFormulaConditionExpression(String formulaSchemeKey, String ... formulaKeys) {
        if (formulaSchemeKey == null) {
            return Collections.emptyMap();
        }
        return this.formulaConditionService.getParsedFormulaConditionExpression(formulaSchemeKey, formulaKeys);
    }

    @Override
    public List<FormulaCondition> getFormulaConditions(String task) {
        return this.formulaConditionService.listFormulaConditionByTask(task);
    }

    @Override
    public List<FormulaConditionLink> getFormulaConditionLinks(String formulaScheme) {
        return this.formulaConditionService.listConditionLinkByScheme(formulaScheme);
    }

    @Override
    public List<FormulaCondition> getFormulaConditions(List<String> conditionKeys) {
        return this.formulaConditionService.listFormulaConditionByKey(conditionKeys);
    }

    @Override
    public List<FormulaConditionLink> getFormulaConditionLinksByCondition(List<String> conditionKeys) {
        return this.formulaConditionService.listConditionLinksByCondition(conditionKeys);
    }
}

