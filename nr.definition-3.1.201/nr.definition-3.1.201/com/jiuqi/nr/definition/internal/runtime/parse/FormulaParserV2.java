/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$DataEngineRunType
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.intf.impl.FmlEngineBaseMonitor
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.dataengine.util.DataEngineFormulaParser
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 */
package com.jiuqi.nr.definition.internal.runtime.parse;

import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.impl.FmlEngineBaseMonitor;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.util.DataEngineFormulaParser;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.nr.definition.common.FormulaSyntaxStyle;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormulaSchemeService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormulaParserV2 {
    private static final Logger LOGGER = LogFactory.getLogger(FormulaParserV2.class);
    private final IRuntimeFormulaSchemeService formulaSchemeService;
    private final IDataDefinitionRuntimeController dataDefinitionController;
    private final IRunTimeViewController viewController;
    private final IEntityViewRunTimeController entityViewController;
    private String formulaSchemeKey;
    private TaskDefine task;
    private FormSchemeDefine formScheme;
    private FormulaSchemeDefine formulaScheme;
    private ParsedExpressionCollectionV2 expressionCollection;
    private ExecutorContext context;
    private List<Formula> calcFormulas;
    private List<Formula> checkFormulas;
    private List<Formula> balanceFormulas;
    private FormulaSyntaxStyle formulaSyntaxStyle;
    private Collection<FormulaDefine> formulaDefines;

    public FormulaParserV2(IRuntimeFormulaSchemeService formulaSchemeService, IDataDefinitionRuntimeController dataDefinitionController, IRunTimeViewController viewController, IEntityViewRunTimeController entityViewController) {
        this.formulaSchemeService = formulaSchemeService;
        this.dataDefinitionController = dataDefinitionController;
        this.viewController = viewController;
        this.entityViewController = entityViewController;
    }

    public ParsedExpressionCollectionV2 parseFormulas(String formulaSchemeKey, Collection<FormulaDefine> formulaDefines) throws ParseException {
        this.formulaDefines = formulaDefines;
        this.prepare(formulaSchemeKey);
        return this.internalParseFormulas();
    }

    private ParsedExpressionCollectionV2 internalParseFormulas() throws ParseException {
        this.buildFormula();
        FmlEngineBaseMonitor monitor = new FmlEngineBaseMonitor(DataEngineConsts.DataEngineRunType.PARSE);
        monitor.start();
        this.parseFormulas(this.calcFormulas, DataEngineConsts.FormulaType.CALCULATE, (IMonitor)monitor);
        this.parseFormulas(this.checkFormulas, DataEngineConsts.FormulaType.CHECK, (IMonitor)monitor);
        this.parseFormulas(this.balanceFormulas, DataEngineConsts.FormulaType.BALANCE, (IMonitor)monitor);
        monitor.finish();
        return this.expressionCollection;
    }

    private void prepare(String formulaSchemeKey) throws ParseException {
        this.formulaScheme = this.formulaSchemeService.queryFormulaScheme(formulaSchemeKey);
        if (this.formulaScheme == null || this.formulaScheme.getFormSchemeKey() == null) {
            throw new ParseException("formula scheme not found or invalid. ".concat(formulaSchemeKey));
        }
        this.formScheme = this.viewController.getFormScheme(this.formulaScheme.getFormSchemeKey());
        if (this.formScheme == null || this.formScheme.getTaskKey() == null) {
            throw new ParseException("form scheme not found or invalid. ".concat(this.formulaScheme.getFormSchemeKey()));
        }
        this.task = this.viewController.queryTaskDefine(this.formScheme.getTaskKey());
        if (this.task == null) {
            throw new ParseException("task not found.  ".concat(this.formScheme.getTaskKey()));
        }
        this.formulaSyntaxStyle = this.task.getFormulaSyntaxStyle();
        if (this.formulaSyntaxStyle == null) {
            throw new ParseException("syntax style not found. ");
        }
        this.formulaSchemeKey = formulaSchemeKey;
        this.expressionCollection = new ParsedExpressionCollectionV2();
        this.context = this.createExecutorContext();
        this.calcFormulas = new ArrayList<Formula>();
        this.checkFormulas = new ArrayList<Formula>();
        this.balanceFormulas = new ArrayList<Formula>();
    }

    private void buildFormula() {
        HashMap<String, FormDefine> formMap = new HashMap<String, FormDefine>();
        for (FormulaDefine formulaDefine : this.formulaDefines) {
            Formula formula = new Formula();
            formula.setCode(formulaDefine.getCode());
            String formKey = formulaDefine.getFormKey();
            if (formKey != null) {
                FormDefine form = null;
                formMap.get(formKey);
                if (formMap.containsKey(formKey)) {
                    form = (FormDefine)formMap.get(formKey);
                } else {
                    form = this.viewController.queryFormById(formKey);
                    formMap.put(formKey, form);
                }
                if (form == null) {
                    LOGGER.error(String.format("\u89e3\u6790\u516c\u5f0f\u51fa\u9519\uff0c\u8868\u5355\u4e0d\u5b58\u5728\u3002\u516c\u5f0f\u65b9\u6848\uff1a%s\uff0c\u516c\u5f0f\uff1a%s\uff0c\u8868\u5355\uff1a%s", this.formulaSchemeKey, formulaDefine.getExpression(), formKey));
                    continue;
                }
                formula.setFormKey(formKey);
                formula.setReportName(form.getFormCode());
            }
            formula.setFormula(formulaDefine.getExpression());
            formula.setId(formulaDefine.getKey());
            formula.setMeanning(formulaDefine.getDescription());
            formula.setChecktype(Integer.valueOf(formulaDefine.getCheckType()));
            formula.setAutoCalc(formulaDefine.getIsAutoExecute());
            if (formulaDefine.getUseCalculate()) {
                this.calcFormulas.add(formula);
            }
            if (formulaDefine.getUseCheck()) {
                this.checkFormulas.add(formula);
            }
            if (!formulaDefine.getUseBalance()) continue;
            this.balanceFormulas.add(formula);
        }
    }

    private List<IParsedExpression> parseFormulas(List<Formula> formulas, DataEngineConsts.FormulaType formulaType, IMonitor monitor) throws ParseException {
        if (formulas == null || formulas.isEmpty()) {
            return Collections.emptyList();
        }
        List expressions = DataEngineFormulaParser.parseFormula((ExecutorContext)this.context, formulas, (DataEngineConsts.FormulaType)formulaType, (IMonitor)monitor);
        for (IParsedExpression expression : expressions) {
            this.expressionCollection.expressions.put(expression.getKey(), expression);
        }
        return expressions;
    }

    private ExecutorContext createExecutorContext() {
        ExecutorContext context = new ExecutorContext(this.dataDefinitionController);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.viewController, this.dataDefinitionController, this.entityViewController, this.formScheme.getKey(), true);
        context.setEnv((IFmlExecEnvironment)environment);
        return context;
    }

    public static class ParsedExpressionCollectionV2 {
        Map<String, IParsedExpression> expressions = new HashMap<String, IParsedExpression>();

        public IParsedExpression getExpression(String expressionKey) {
            return this.expressions.get(expressionKey);
        }

        public Collection<IParsedExpression> getExpressions() {
            return this.expressions.values();
        }

        public Map<String, IParsedExpression> getExpressionMap() {
            return this.expressions;
        }
    }
}

