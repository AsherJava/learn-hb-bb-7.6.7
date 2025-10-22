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
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.common.FormulaSyntaxStyle;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeExtFormulaService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormulaSchemeService;
import com.jiuqi.nr.definition.internal.runtime.parse.FormulaScriptService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class BatchExtFormulaParserV2 {
    private static final Logger LOGGER = LogFactory.getLogger(BatchExtFormulaParserV2.class);
    @Autowired
    private IRuntimeFormulaSchemeService formulaSchemeService;
    @Autowired
    private IRuntimeExtFormulaService formulaService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionController;
    @Autowired
    private IRunTimeViewController viewController;
    @Autowired
    private IEntityViewRunTimeController entityViewController;

    private InternalParser createParser() {
        return new InternalParser();
    }

    public BatchParsedExpressionCollection parseFormulas(String formulaSchemeKey) throws ParseException {
        return this.createParser().parseFormulas(formulaSchemeKey);
    }

    public BatchParsedExpressionCollection parseFormulas(String formulaSchemeKey, String formKey) throws ParseException {
        return this.createParser().parseFormulas(formulaSchemeKey, formKey);
    }

    public BatchParsedExpressionCollection parseFormulas(String formulaSchemeKey, String formKey, DataEngineConsts.FormulaType formulaType) throws ParseException {
        return this.createParser().parseFormulas(formulaSchemeKey, formKey, formulaType);
    }

    public static class BatchParsedExpressionCollection {
        private final Map<DataEngineConsts.FormulaType, Map<String, List<IParsedExpression>>> expressions = new HashMap<DataEngineConsts.FormulaType, Map<String, List<IParsedExpression>>>();
        private final Map<String, String> jsFormulas = new HashMap<String, String>();

        public Map<String, List<IParsedExpression>> getExpressions(DataEngineConsts.FormulaType formulaType) {
            return this.expressions.containsKey(formulaType) ? Collections.unmodifiableMap(this.expressions.get(formulaType)) : Collections.emptyMap();
        }

        public List<IParsedExpression> getExpressions(String formKey, DataEngineConsts.FormulaType formulaType) {
            Map<String, List<IParsedExpression>> expByType;
            if (formKey == null) {
                formKey = "00000000-0000-0000-0000-000000000000";
            }
            if ((expByType = this.expressions.get(formulaType)) == null) {
                return Collections.emptyList();
            }
            List<IParsedExpression> expInFormByType = expByType.get(formKey);
            if (expInFormByType == null) {
                return Collections.emptyList();
            }
            return expInFormByType;
        }

        public String getJsFormula(String formKey, DataEngineConsts.FormulaType formulaType) {
            Assert.isTrue(formulaType == DataEngineConsts.FormulaType.CALCULATE, "\u4ec5\u8fd0\u7b97\u516c\u5f0f\u652f\u6301\u7ffb\u8bd1\u811a\u672c\u516c\u5f0f\u3002");
            if (formKey == null || formKey == "00000000-0000-0000-0000-000000000000") {
                return null;
            }
            return this.jsFormulas.get(formKey);
        }

        public Map<String, IParsedExpression> getExpressionMap(String formKey, DataEngineConsts.FormulaType formulaType) {
            List<IParsedExpression> expressions = this.getExpressions(formKey, formulaType);
            HashMap<String, IParsedExpression> expMap = new HashMap<String, IParsedExpression>();
            for (IParsedExpression iParsedExpression : expressions) {
                expMap.put(iParsedExpression.getKey(), iParsedExpression);
            }
            return expMap;
        }
    }

    private static enum ParseArea {
        FORMULASCHEMA,
        FORM,
        FORM_TYPE;

    }

    class InternalParser {
        private String formulaSchemeKey;
        private TaskDefine task;
        private FormSchemeDefine formScheme;
        private FormulaSchemeDefine formulaScheme;
        private BatchParsedExpressionCollection expressionCollection;
        private ExecutorContext context;
        private List<Formula> calcFormulas;
        private List<Formula> checkFormulas;
        private List<Formula> balanceFormulas;
        private FormulaSyntaxStyle formulaSyntaxStyle;
        private ParseArea parseArea;
        private String formKey;
        private DataEngineConsts.FormulaType formulaType;

        InternalParser() {
        }

        public BatchParsedExpressionCollection parseFormulas(String formulaSchemeKey) throws ParseException {
            this.parseArea = ParseArea.FORMULASCHEMA;
            this.prepare(formulaSchemeKey);
            return this.internalParseFormulas();
        }

        public BatchParsedExpressionCollection parseFormulas(String formulaSchemeKey, String formKey) throws ParseException {
            this.parseArea = ParseArea.FORM;
            this.formKey = formKey == null ? "00000000-0000-0000-0000-000000000000" : formKey;
            this.prepare(formulaSchemeKey);
            return this.internalParseFormulas();
        }

        public BatchParsedExpressionCollection parseFormulas(String formulaSchemeKey, String formKey, DataEngineConsts.FormulaType formulaType) throws ParseException {
            this.parseArea = ParseArea.FORM_TYPE;
            this.formKey = formKey == null ? "00000000-0000-0000-0000-000000000000" : formKey;
            this.formulaType = formulaType;
            this.prepare(formulaSchemeKey);
            return this.internalParseFormulas();
        }

        private BatchParsedExpressionCollection internalParseFormulas() throws ParseException {
            if (this.formulaScheme.getFormulaSchemeType() == FormulaSchemeType.FORMULA_SCHEME_TYPE_REPORT) {
                this.buildFormula();
                FmlEngineBaseMonitor monitor = new FmlEngineBaseMonitor(DataEngineConsts.DataEngineRunType.PARSE);
                monitor.start();
                this.parseCalcFormulas((IMonitor)monitor);
                this.parseCheckFormulas((IMonitor)monitor);
                this.parseBalanceFormulas((IMonitor)monitor);
                monitor.finish();
            }
            return this.expressionCollection;
        }

        private void prepare(String formulaSchemeKey) throws ParseException {
            this.formulaScheme = BatchExtFormulaParserV2.this.formulaSchemeService.queryFormulaScheme(formulaSchemeKey);
            if (this.formulaScheme == null || this.formulaScheme.getFormSchemeKey() == null) {
                throw new ParseException("formula scheme not found or invalid. ".concat(formulaSchemeKey));
            }
            this.formScheme = BatchExtFormulaParserV2.this.viewController.getFormScheme(this.formulaScheme.getFormSchemeKey());
            if (this.formScheme == null || this.formScheme.getTaskKey() == null) {
                throw new ParseException("form scheme not found or invalid. ".concat(this.formulaScheme.getFormSchemeKey()));
            }
            this.task = BatchExtFormulaParserV2.this.viewController.queryTaskDefine(this.formScheme.getTaskKey());
            if (this.task == null) {
                throw new ParseException("task not found.  ".concat(this.formScheme.getTaskKey()));
            }
            this.formulaSyntaxStyle = this.task.getFormulaSyntaxStyle();
            if (this.formulaSyntaxStyle == null) {
                throw new ParseException("syntax style not found. ");
            }
            this.formulaSchemeKey = formulaSchemeKey;
            this.expressionCollection = new BatchParsedExpressionCollection();
            this.context = this.createExecutorContext();
            this.calcFormulas = new ArrayList<Formula>();
            this.checkFormulas = new ArrayList<Formula>();
            this.balanceFormulas = new ArrayList<Formula>();
        }

        private void buildFormula() {
            Map<String, FormDefine> formMap = this.queryFormMap();
            List<FormulaDefine> formulaDefines = this.queryFormulas();
            for (FormulaDefine formulaDefine : formulaDefines) {
                Formula formula = new Formula();
                formula.setCode(formulaDefine.getCode());
                String formKey = formulaDefine.getFormKey();
                if (formKey != null) {
                    FormDefine form = formMap.get(formKey);
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
                this.appendFormula(formulaDefine, formula);
            }
        }

        private Map<String, FormDefine> queryFormMap() {
            List<Object> forms;
            switch (this.parseArea) {
                case FORMULASCHEMA: {
                    forms = BatchExtFormulaParserV2.this.viewController.queryAllFormDefinesByFormScheme(this.formScheme.getKey());
                    break;
                }
                case FORM: 
                case FORM_TYPE: {
                    if (this.formKey.equals("00000000-0000-0000-0000-000000000000")) {
                        forms = Collections.emptyList();
                        break;
                    }
                    forms = Arrays.asList(BatchExtFormulaParserV2.this.viewController.queryFormById(this.formKey));
                    break;
                }
                default: {
                    forms = Collections.emptyList();
                }
            }
            LinkedHashMap<String, FormDefine> formMap = new LinkedHashMap<String, FormDefine>();
            for (FormDefine form : forms) {
                formMap.put(form.getKey(), form);
            }
            return formMap;
        }

        private List<FormulaDefine> queryFormulas() {
            switch (this.parseArea) {
                case FORMULASCHEMA: {
                    return BatchExtFormulaParserV2.this.formulaService.getFormulasInScheme(this.formulaSchemeKey);
                }
                case FORM: {
                    return BatchExtFormulaParserV2.this.formulaService.getFormulasInForm(this.formulaSchemeKey, this.formKey);
                }
                case FORM_TYPE: {
                    return BatchExtFormulaParserV2.this.formulaService.getFormulasInFormByType(this.formulaSchemeKey, this.formKey);
                }
            }
            return Collections.emptyList();
        }

        private void appendFormula(FormulaDefine formulaDefine, Formula formula) {
            block0 : switch (this.parseArea) {
                case FORMULASCHEMA: 
                case FORM: {
                    if (formulaDefine.getUseCalculate()) {
                        this.calcFormulas.add(formula);
                    }
                    if (formulaDefine.getUseCheck()) {
                        this.checkFormulas.add(formula);
                    }
                    if (!formulaDefine.getUseBalance()) break;
                    this.balanceFormulas.add(formula);
                    break;
                }
                case FORM_TYPE: {
                    switch (this.formulaType) {
                        case CALCULATE: {
                            if (!formulaDefine.getUseCalculate()) break block0;
                            this.calcFormulas.add(formula);
                            break block0;
                        }
                        case CHECK: {
                            if (!formulaDefine.getUseCheck()) break block0;
                            this.checkFormulas.add(formula);
                            break block0;
                        }
                        case BALANCE: {
                            if (!formulaDefine.getUseBalance()) break block0;
                            this.balanceFormulas.add(formula);
                            break block0;
                        }
                    }
                    break;
                }
            }
        }

        private void parseCalcFormulas(IMonitor monitor) throws ParseException {
            List<IParsedExpression> expressions = this.parseFormulas(this.calcFormulas, DataEngineConsts.FormulaType.CALCULATE, monitor);
            this.buildJsFormula(expressions);
        }

        private void parseCheckFormulas(IMonitor monitor) throws ParseException {
            this.parseFormulas(this.checkFormulas, DataEngineConsts.FormulaType.CHECK, monitor);
        }

        private void parseBalanceFormulas(IMonitor monitor) throws ParseException {
            this.parseFormulas(this.balanceFormulas, DataEngineConsts.FormulaType.BALANCE, monitor);
        }

        private List<IParsedExpression> parseFormulas(List<Formula> formulas, DataEngineConsts.FormulaType formulaType, IMonitor monitor) throws ParseException {
            if (formulas == null || formulas.isEmpty()) {
                return Collections.emptyList();
            }
            List expressions = DataEngineFormulaParser.parseFormula((ExecutorContext)this.context, formulas, (DataEngineConsts.FormulaType)formulaType, (IMonitor)monitor);
            LinkedHashMap<String, LinkedList<IParsedExpression>> expByType = (LinkedHashMap<String, LinkedList<IParsedExpression>>)this.expressionCollection.expressions.get(formulaType);
            if (expByType == null) {
                expByType = new LinkedHashMap<String, LinkedList<IParsedExpression>>();
                this.expressionCollection.expressions.put(formulaType, expByType);
            }
            for (IParsedExpression expression : expressions) {
                LinkedList<IParsedExpression> expInFormByType;
                String formKey = expression.getFormKey();
                if (formKey == null) {
                    formKey = "00000000-0000-0000-0000-000000000000";
                }
                if ((expInFormByType = (LinkedList<IParsedExpression>)expByType.get(formKey)) == null) {
                    expInFormByType = new LinkedList<IParsedExpression>();
                    expByType.put(formKey, expInFormByType);
                }
                expInFormByType.add(expression);
            }
            return expressions;
        }

        private void buildJsFormula(List<IParsedExpression> expressions) {
            for (Map.Entry<String, List<IParsedExpression>> entry : this.mapByForm(expressions).entrySet()) {
                String formKey = entry.getKey();
                if ("00000000-0000-0000-0000-000000000000".equals(formKey)) continue;
                List<IParsedExpression> expressionsInForm = entry.getValue();
                FormulaScriptService formulaScriptService = new FormulaScriptService(BatchExtFormulaParserV2.this.dataDefinitionController, BatchExtFormulaParserV2.this.viewController, BatchExtFormulaParserV2.this.entityViewController);
                String script = formulaScriptService.getFormulaScript(this.formScheme.getKey(), formKey, expressionsInForm, this.formulaSyntaxStyle);
                this.expressionCollection.jsFormulas.put(formKey, script);
            }
        }

        private Map<String, List<IParsedExpression>> mapByForm(List<IParsedExpression> expressions) {
            LinkedHashMap<String, List<IParsedExpression>> expressionMap = new LinkedHashMap<String, List<IParsedExpression>>();
            switch (this.parseArea) {
                case FORMULASCHEMA: {
                    for (IParsedExpression expression : expressions) {
                        ArrayList<IParsedExpression> expressionsByForm;
                        String formKey = expression.getFormKey();
                        if (formKey == null) {
                            formKey = "00000000-0000-0000-0000-000000000000";
                        }
                        if ((expressionsByForm = (ArrayList<IParsedExpression>)expressionMap.get(formKey)) == null) {
                            expressionsByForm = new ArrayList<IParsedExpression>();
                            expressionMap.put(formKey, expressionsByForm);
                        }
                        expressionsByForm.add(expression);
                    }
                    break;
                }
                case FORM: 
                case FORM_TYPE: {
                    expressionMap.put(this.formKey, expressions);
                    break;
                }
            }
            return expressionMap;
        }

        private ExecutorContext createExecutorContext() {
            ExecutorContext context = new ExecutorContext(BatchExtFormulaParserV2.this.dataDefinitionController);
            ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(BatchExtFormulaParserV2.this.viewController, BatchExtFormulaParserV2.this.dataDefinitionController, BatchExtFormulaParserV2.this.entityViewController, this.formScheme.getKey(), true);
            context.setEnv((IFmlExecEnvironment)environment);
            return context;
        }
    }
}

