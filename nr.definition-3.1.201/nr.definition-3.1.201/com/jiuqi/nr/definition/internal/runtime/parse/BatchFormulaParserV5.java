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
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
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
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.common.FormulaSyntaxStyle;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.formula.FormulaCondition;
import com.jiuqi.nr.definition.facade.formula.FormulaConditionLink;
import com.jiuqi.nr.definition.internal.dao.formula.RunTimeFormulaConditionDao;
import com.jiuqi.nr.definition.internal.dao.formula.RunTimeFormulaConditionLinkDao;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.internal.runtime.parse.FormulaParserConfigDTO;
import com.jiuqi.nr.definition.internal.runtime.parse.FormulaScriptService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

@Component
public class BatchFormulaParserV5 {
    private static final Logger FML_LOGGER = LoggerFactory.getLogger("com.jiuqi.nr.data.logic.fml");
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionController;
    @Autowired
    private IRunTimeViewController viewController;
    @Autowired
    private IEntityViewRunTimeController entityViewController;
    @Autowired
    private RunTimeFormulaConditionDao formulaConditionDao;
    @Autowired
    private RunTimeFormulaConditionLinkDao formulaConditionLinkDao;

    public BatchFormulaParserV5 getFormulaParser(IRunTimeViewController viewController) {
        BatchFormulaParserV5 parser = new BatchFormulaParserV5();
        parser.viewController = viewController;
        parser.dataDefinitionController = this.dataDefinitionController;
        parser.entityViewController = this.entityViewController;
        parser.formulaConditionDao = this.formulaConditionDao;
        parser.formulaConditionLinkDao = this.formulaConditionLinkDao;
        return parser;
    }

    public BatchParsedExpressionCollection parseFormulas(FormulaSchemeDefine formulaScheme, List<FormulaDefine> formulas) throws ParseException {
        List<FormulaConditionLink> formulaConditionLinks = this.formulaConditionLinkDao.listConditionLinkByFormulaScheme(formulaScheme.getKey());
        Map<String, List<FormulaCondition>> conditionsMap = this.getFormulaConditions(formulaConditionLinks);
        FormulaParserConfigDTO formulaParserConfigDTO = new FormulaParserConfigDTO();
        formulaParserConfigDTO.setFormulaDefines(formulas);
        formulaParserConfigDTO.setFormulaSchemeDefine(formulaScheme);
        formulaParserConfigDTO.setConditionsMap(conditionsMap);
        FormulaParserExcutor parserExcutor = new FormulaParserExcutor(formulaParserConfigDTO);
        return parserExcutor.ParseFormulas();
    }

    public BatchParsedExpressionCollection parseFormulas(FormulaParserConfigDTO formulaParserConfigDTO) throws ParseException {
        FormulaParserExcutor parserExcutor = new FormulaParserExcutor(formulaParserConfigDTO);
        return parserExcutor.ParseFormulas();
    }

    private Map<String, List<FormulaCondition>> getFormulaConditions(List<FormulaConditionLink> formulaConditionLinks) {
        Map linkMap = formulaConditionLinks.stream().collect(Collectors.groupingBy(FormulaConditionLink::getFormulaKey, Collectors.mapping(FormulaConditionLink::getConditionKey, Collectors.toList())));
        List<String> conditionKeys = formulaConditionLinks.stream().map(FormulaConditionLink::getConditionKey).distinct().collect(Collectors.toList());
        Map<String, FormulaCondition> conditionMap = this.formulaConditionDao.listFormulaConditions(conditionKeys).stream().collect(Collectors.toMap(IBaseMetaItem::getKey, f -> f));
        HashMap<String, List<FormulaCondition>> conditionsMap = new HashMap<String, List<FormulaCondition>>();
        for (Map.Entry entry : linkMap.entrySet()) {
            if (CollectionUtils.isEmpty(entry.getValue())) continue;
            if (!conditionsMap.containsKey(entry.getKey())) {
                conditionsMap.put(entry.getKey(), new ArrayList());
            }
            ((List)conditionsMap.get(entry.getKey())).addAll(entry.getValue().stream().map(conditionMap::get).filter(Objects::nonNull).collect(Collectors.toList()));
        }
        return conditionsMap;
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
            if (formKey == null || "00000000-0000-0000-0000-000000000000".equals(formKey)) {
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

    private class FormulaParserExcutor {
        private FormulaSchemeDefine formulaScheme;
        private Collection<FormulaDefine> formulasNeedtoParse;
        private String formSchemeKey;
        private String formulaSchemeKey;
        private FormulaSyntaxStyle formulaSyntaxStyle;
        private ExecutorContext context;
        private List<Formula> calcFormulas;
        private List<Formula> checkFormulas;
        private List<Formula> balanceFormulas;
        private BatchParsedExpressionCollection expressionCollection;
        private Map<String, List<FormulaCondition>> conditionsMap;

        public FormulaParserExcutor(FormulaSchemeDefine formulaScheme, List<FormulaDefine> formulas) throws ParseException {
            this(new FormulaParserConfigDTO(formulaScheme, formulas, null));
        }

        public FormulaParserExcutor(FormulaParserConfigDTO formulaParserConfigDTO) throws ParseException {
            FormulaSchemeDefine formulaScheme = formulaParserConfigDTO.getFormulaSchemeDefine();
            List<FormulaDefine> formulas = formulaParserConfigDTO.getFormulaDefines();
            if (formulaScheme == null || formulaScheme.getFormSchemeKey() == null) {
                throw new ParseException("formula scheme not found or invalid. ".concat(this.formulaSchemeKey));
            }
            if (formulas == null) {
                throw new ParseException("formulas must not null. ");
            }
            this.formulaScheme = formulaScheme;
            this.formulasNeedtoParse = formulas;
            this.formSchemeKey = formulaScheme.getFormSchemeKey();
            this.formulaSchemeKey = formulaScheme.getKey();
            FormSchemeDefine formScheme = BatchFormulaParserV5.this.viewController.getFormScheme(this.formSchemeKey);
            if (formScheme == null || formScheme.getTaskKey() == null) {
                throw new ParseException("form scheme not found or invalid. ".concat(formulaScheme.getFormSchemeKey()));
            }
            TaskDefine task = BatchFormulaParserV5.this.viewController.queryTaskDefine(formScheme.getTaskKey());
            if (task == null) {
                throw new ParseException("task not found.  ".concat(formScheme.getTaskKey()));
            }
            this.formulaSyntaxStyle = task.getFormulaSyntaxStyle();
            if (this.formulaSyntaxStyle == null) {
                throw new ParseException("syntax style not found. ");
            }
            this.expressionCollection = new BatchParsedExpressionCollection();
            this.context = this.createExecutorContext();
            this.calcFormulas = new ArrayList<Formula>();
            this.checkFormulas = new ArrayList<Formula>();
            this.balanceFormulas = new ArrayList<Formula>();
            this.conditionsMap = formulaParserConfigDTO.getConditionsMap();
        }

        public BatchParsedExpressionCollection ParseFormulas() throws ParseException {
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

        private void buildFormula() {
            Map<String, FormDefine> formMap = this.queryFormMap();
            for (FormulaDefine formulaDefine : this.formulasNeedtoParse) {
                Formula formula = new Formula();
                formula.setCode(formulaDefine.getCode());
                String formKey = formulaDefine.getFormKey();
                if (formKey != null) {
                    FormDefine form = formMap.get(formKey);
                    if (form == null) {
                        FML_LOGGER.error("\u89e3\u6790\u516c\u5f0f\u51fa\u9519\uff0c\u8868\u5355\u4e0d\u5b58\u5728\u3002\u516c\u5f0f\u65b9\u6848\uff1a{}\uff0c\u516c\u5f0f\uff1a{}\uff0c\u8868\u5355\uff1a{}", this.formulaSchemeKey, formulaDefine.getExpression(), formKey);
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
                Collection<Formula> conditions = this.buildFormulaConditions(formula);
                if (conditions != null) {
                    formula.getConditions().addAll(conditions);
                }
                if (formulaDefine.getUseCalculate()) {
                    this.calcFormulas.add(formula);
                }
                if (formulaDefine.getUseCheck()) {
                    this.checkFormulas.add(formula);
                }
                if (!formulaDefine.getUseBalance()) continue;
                formula.setBalanceZBExp(formulaDefine.getBalanceZBExp());
                this.balanceFormulas.add(formula);
            }
        }

        private Collection<Formula> buildFormulaConditions(Formula formula) {
            if (this.conditionsMap == null) {
                return null;
            }
            Collection formulaConditions = this.conditionsMap.get(formula.getId());
            if (formulaConditions != null) {
                ArrayList<Formula> formulas = new ArrayList<Formula>();
                for (FormulaCondition formulaCondition : formulaConditions) {
                    Formula f = new Formula();
                    f.setId(formulaCondition.getKey());
                    f.setCode(formulaCondition.getCode());
                    f.setFormula(formulaCondition.getFormulaCondition());
                    f.setOrder(formulaCondition.getOrder());
                    f.setReportName(null);
                    f.setFormKey(formula.getFormKey());
                    f.setMeanning(null);
                    f.setChecktype(Integer.valueOf(4));
                    f.setAutoCalc(false);
                    f.setBalanceZBExp(null);
                    formulas.add(f);
                }
                return formulas;
            }
            return null;
        }

        private Map<String, FormDefine> queryFormMap() {
            List<FormDefine> forms = BatchFormulaParserV5.this.viewController.queryAllFormDefinesByFormScheme(this.formSchemeKey);
            return forms.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, t -> t));
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
            FML_LOGGER.debug("\u62a5\u8868\u53c2\u6570\u7f13\u5b58\u52a0\u8f7d\uff1a\u89e3\u6790{}\u516c\u5f0f\uff0c\u5171\u89e3\u6790{}\u6761\u516c\u5f0f\uff0c\u5f97\u5230{}\u6761\u8bed\u6cd5\u6811", formulaType.getTitle(), formulas.size(), expressions.size());
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
                FormulaScriptService formulaScriptService = new FormulaScriptService(BatchFormulaParserV5.this.dataDefinitionController, BatchFormulaParserV5.this.viewController, BatchFormulaParserV5.this.entityViewController);
                String script = formulaScriptService.getFormulaScript(this.formSchemeKey, formKey, expressionsInForm, this.formulaSyntaxStyle);
                this.expressionCollection.jsFormulas.put(formKey, script);
            }
        }

        private Map<String, List<IParsedExpression>> mapByForm(List<IParsedExpression> expressions) {
            LinkedHashMap<String, List<IParsedExpression>> expressionMap = new LinkedHashMap<String, List<IParsedExpression>>();
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
            return expressionMap;
        }

        private ExecutorContext createExecutorContext() {
            ExecutorContext context = new ExecutorContext(BatchFormulaParserV5.this.dataDefinitionController);
            ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(BatchFormulaParserV5.this.viewController, BatchFormulaParserV5.this.dataDefinitionController, BatchFormulaParserV5.this.entityViewController, this.formSchemeKey, false);
            context.setEnv((IFmlExecEnvironment)environment);
            return context;
        }
    }
}

