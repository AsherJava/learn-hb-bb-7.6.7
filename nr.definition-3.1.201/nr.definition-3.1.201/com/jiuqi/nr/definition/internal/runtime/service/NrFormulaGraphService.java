/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$DataEngineRunType
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.common.QueryFields
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.exception.FormulaParseException
 *  com.jiuqi.np.dataengine.exception.UnknownReadWriteException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.intf.impl.FmlEngineBaseMonitor
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.dataengine.util.DataEngineFormulaParser
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.definition.facade.IMetaItem
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.common.Consts
 *  com.jiuqi.nr.datascheme.internal.dao.impl.DBSimpleQueryUtils
 *  com.jiuqi.nr.graph.GraphHelper
 *  com.jiuqi.nr.graph.IGraph
 *  com.jiuqi.nr.graph.IGraphCacheObserver
 *  com.jiuqi.nr.graph.IGraphEditor
 *  com.jiuqi.nr.graph.INode
 *  com.jiuqi.nr.graph.cache.GraphCacheDefine
 *  com.jiuqi.nr.graph.function.AttrValueGetter
 *  com.jiuqi.nr.graph.internal.GraphBuilder
 *  com.jiuqi.nr.graph.label.IndexLabel
 *  com.jiuqi.nr.graph.label.NodeLabel
 *  com.jiuqi.nr.graph.util.GraphUtils
 */
package com.jiuqi.nr.definition.internal.runtime.service;

import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.exception.FormulaParseException;
import com.jiuqi.np.dataengine.exception.UnknownReadWriteException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.impl.FmlEngineBaseMonitor;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.util.DataEngineFormulaParser;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.definition.facade.IMetaItem;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.common.Consts;
import com.jiuqi.nr.datascheme.internal.dao.impl.DBSimpleQueryUtils;
import com.jiuqi.nr.definition.common.DiskDataUtils;
import com.jiuqi.nr.definition.common.FormulaSyntaxStyle;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaField;
import com.jiuqi.nr.definition.facade.FormulaParsedExp;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.formula.FormulaCondition;
import com.jiuqi.nr.definition.facade.formula.FormulaConditionLink;
import com.jiuqi.nr.definition.internal.dao.RunTimeFormulaDefineDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeFormulaSchemeDefineDao;
import com.jiuqi.nr.definition.internal.dao.formula.RunTimeFormulaConditionDao;
import com.jiuqi.nr.definition.internal.dao.formula.RunTimeFormulaConditionLinkDao;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.internal.runtime.dto.FormulaDTO;
import com.jiuqi.nr.definition.internal.runtime.dto.FormulaFieldDTO;
import com.jiuqi.nr.definition.internal.runtime.dto.FormulaFormDTO;
import com.jiuqi.nr.definition.internal.runtime.dto.FormulaParsedExpDTO;
import com.jiuqi.nr.definition.internal.runtime.parse.FormulaScriptService;
import com.jiuqi.nr.definition.internal.runtime.service.AbstractNrParamCacheExpireService;
import com.jiuqi.nr.definition.util.FormulaTrackUtil;
import com.jiuqi.nr.definition.util.ParsedExpressionFilter;
import com.jiuqi.nr.graph.GraphHelper;
import com.jiuqi.nr.graph.IGraph;
import com.jiuqi.nr.graph.IGraphCacheObserver;
import com.jiuqi.nr.graph.IGraphEditor;
import com.jiuqi.nr.graph.INode;
import com.jiuqi.nr.graph.cache.GraphCacheDefine;
import com.jiuqi.nr.graph.function.AttrValueGetter;
import com.jiuqi.nr.graph.internal.GraphBuilder;
import com.jiuqi.nr.graph.label.IndexLabel;
import com.jiuqi.nr.graph.label.NodeLabel;
import com.jiuqi.nr.graph.util.GraphUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class NrFormulaGraphService {
    public static final Logger FML_LOGGER = LoggerFactory.getLogger("com.jiuqi.nr.data.logic.fml");
    protected static final String JS_DIR = "NR_FORMULA_JS";
    public static final String NR_FORMULA_CACHE_NAME = "NR_FORMULA_CACHE_NAME";
    private static final AttrValueGetter<Object, String> STR_GETTER = o -> null == o ? null : (String)o;
    private static final AttrValueGetter<Object, String> ITEM_KEY_GETTER = o -> null == o ? null : ((IMetaItem)o).getKey();
    private static final AttrValueGetter<Object, String> FORMULA_KEY_GETTER = o -> null == o ? null : ((FormulaDTO)o).getFormulaKey();
    private static final AttrValueGetter<Object, String> FORMULA_FORM_KEY_GETTER = o -> null == o ? null : ((FormulaFormDTO)o).getFormKey();
    private static final AttrValueGetter<Object, String> FORMULA_FIELD_KEY_GETTER = o -> null == o ? null : ((FormulaField)o).getFieldKey();
    private static final AttrValueGetter<Object, String> PARSED_EXP_GETTER = o -> null == o ? null : ((FormulaParsedExp)o).getExpKey();
    private static final GraphBuilder FORMULA_GRAPH_BUILDER = GraphHelper.createGraphBuilder((String)"FORMULA_GRAPH_BUILDER");
    public static final NodeLabel FORMULA_SCHEME = FORMULA_GRAPH_BUILDER.registerNode("FORMULA_SCHEME", ITEM_KEY_GETTER);
    public static final NodeLabel FORMULA_DEFINE = FORMULA_GRAPH_BUILDER.registerNode("FORMULA_DEFINE", FORMULA_KEY_GETTER);
    public static final NodeLabel FORMULA_EXP = FORMULA_GRAPH_BUILDER.registerNode("FORMULA_EXP", PARSED_EXP_GETTER);
    public static final NodeLabel FORMULA_FORM = FORMULA_GRAPH_BUILDER.registerNode("FORMULA_FORM", FORMULA_FORM_KEY_GETTER);
    public static final NodeLabel FORMULA_DATALINK = FORMULA_GRAPH_BUILDER.registerNode("FORMULA_DATALINK");
    public static final NodeLabel FORMULA_FIELD = FORMULA_GRAPH_BUILDER.registerNode("FORMULA_FIELD", FORMULA_FIELD_KEY_GETTER);
    public static final IndexLabel FORMULA_DEFINE_CODE = FORMULA_GRAPH_BUILDER.registerIndex("FORMULA_DEFINE_CODE", FORMULA_DEFINE, n -> {
        FormulaDTO data = (FormulaDTO)n.getData(FormulaDTO.class);
        return null == data ? null : data.getFormulaCode();
    });
    public static final String FORMULA_FIELD_ERROR = "FORMULA_FIELD_ERROR";
    @Value(value="${jiuqi.nr.definition.disk-cache.js:true}")
    private boolean enableJsDiskCache;
    @Value(value="${jiuqi.nr.cache-load.filter.enable:false}")
    private boolean enabledFilter;
    @Value(value="${jiuqi.nr.cache-load.filter.formula-scheme:}")
    private Set<String> disableFormulaSchemes;
    @Autowired
    private RunTimeFormulaSchemeDefineDao formulaSchemeDao;
    @Autowired
    private RunTimeFormulaDefineDao formulaDao;
    @Autowired
    private RunTimeFormulaConditionLinkDao formulaConditionLinkDao;
    @Autowired
    private RunTimeFormulaConditionDao formulaConditionDao;
    @Autowired
    private IRunTimeViewController runtimeViewController;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionController;
    @Autowired
    private IEntityViewRunTimeController entityViewController;
    @Autowired
    private ParsedExpressionFilter parsedExpressionFilter;
    @Autowired
    private DBSimpleQueryUtils queryUtils;
    @Autowired
    private AbstractNrParamCacheExpireService cacheObserverService;
    private static final Logger LOGGER = Consts.NR_PARAM_GRAPH_LOGGER;

    public GraphCacheDefine getGraphCacheDefine() {
        GraphCacheDefine define = new GraphCacheDefine(NR_FORMULA_CACHE_NAME, FORMULA_GRAPH_BUILDER.getGraphDefine());
        define.enableGlobalIndex(() -> Collections.singletonMap(FORMULA_DEFINE, this.queryUtils.queryForMap("NR_PARAM_FORMULA", "FL_KEY", "FL_SCHEME_KEY")));
        define.addObserver((IGraphCacheObserver)this.cacheObserverService);
        return define;
    }

    public IGraph getFormulaGraph(String formulaSchemeKey) {
        return this.getFormulaGraph(formulaSchemeKey, true);
    }

    public IGraph getFormulaGraph(String formulaSchemeKey, boolean loadJs) {
        LOGGER.debug("\u62a5\u8868\u53c2\u6570\u7f13\u5b58\u52a0\u8f7d\uff1a\u52a0\u8f7d\u516c\u5f0f\u65b9\u6848\u5f00\u59cb\uff1a{}", (Object)formulaSchemeKey);
        long millis = LOGGER.isDebugEnabled() ? System.currentTimeMillis() : 0L;
        IGraph graph = this.getGraph(formulaSchemeKey, loadJs);
        millis = LOGGER.isDebugEnabled() ? System.currentTimeMillis() - millis : millis;
        LOGGER.debug("\u62a5\u8868\u53c2\u6570\u7f13\u5b58\u52a0\u8f7d\uff1a\u52a0\u8f7d\u516c\u5f0f\u65b9\u6848\u7ed3\u675f\uff1a{}\uff0c\u8017\u65f6\uff1a{}", (Object)formulaSchemeKey, (Object)millis);
        return graph;
    }

    private IGraph getGraph(String formulaSchemeKey, boolean loadJs) {
        List<IParsedExpression> checkExpressions;
        FormulaSchemeDefine formulaScheme = this.formulaSchemeDao.getDefineByKey(formulaSchemeKey);
        if (null == formulaScheme) {
            return GraphUtils.emptyGraph();
        }
        IGraphEditor graph = FORMULA_GRAPH_BUILDER.createGraph();
        graph.addNode(FORMULA_SCHEME, (Object)formulaScheme);
        if (this.enabledFilter && this.disableFormulaSchemes.contains(formulaSchemeKey)) {
            LOGGER.info("\u62a5\u8868\u53c2\u6570\u7f13\u5b58\u52a0\u8f7d\uff1a\u516c\u5f0f\u65b9\u6848{}[{}]\u7684\u7f13\u5b58\u88ab\u8fc7\u6ee4\uff0c\u5982\u6709\u7591\u95ee\u8bf7\u67e5\u770b\u914d\u7f6e\u6587\u4ef6", (Object)formulaScheme.getTitle(), (Object)formulaScheme.getKey());
            return graph;
        }
        Map<Integer, List<Formula>> formulaTypeMap = this.loadFormulas(graph, formulaScheme);
        ArrayList<FormulaParsedExpDTO> allExpressions = new ArrayList<FormulaParsedExpDTO>();
        Map<DataEngineConsts.FormulaType, List<IParsedExpression>> expressions = this.parseFormula(formulaScheme, formulaTypeMap);
        List<IParsedExpression> calcExpressions = expressions.get(DataEngineConsts.FormulaType.CALCULATE);
        if (!CollectionUtils.isEmpty(calcExpressions)) {
            allExpressions.addAll(this.loadExpression(graph, calcExpressions));
        }
        if (!CollectionUtils.isEmpty(checkExpressions = expressions.get(DataEngineConsts.FormulaType.CHECK))) {
            allExpressions.addAll(this.loadExpression(graph, checkExpressions));
        }
        this.parsedExpressions(graph, allExpressions);
        this.loadBalanceExpression(graph, expressions.get(DataEngineConsts.FormulaType.BALANCE));
        if (loadJs) {
            this.loadFormulaScript(formulaScheme, (IGraph)graph);
        }
        return graph.finish();
    }

    private Map<Integer, List<Formula>> loadFormulas(IGraphEditor graph, FormulaSchemeDefine formulaScheme) {
        List<FormulaDefine> allFormulaDefines = this.formulaDao.queryFormulaDefineByScheme(formulaScheme.getKey());
        Map<String, List<FormulaCondition>> formulaConditions = this.queryFormulaConditions(formulaScheme);
        Map allFormDefines = this.runtimeViewController.queryAllFormDefinesByFormScheme(formulaScheme.getFormSchemeKey()).stream().collect(Collectors.toMap(IBaseMetaItem::getKey, Function.identity()));
        HashMap<Integer, List<Formula>> formulaTypeMap = new HashMap<Integer, List<Formula>>();
        HashMap<String, List> formulaFormMap = new HashMap<String, List>();
        for (FormulaDefine formulaDefine : allFormulaDefines) {
            String formKey = formulaDefine.getFormKey();
            FormDefine form = null;
            if (!StringUtils.hasLength(formKey)) {
                formKey = "00000000-0000-0000-0000-000000000000";
            } else {
                form = (FormDefine)allFormDefines.get(formKey);
            }
            FormulaDTO formula = new FormulaDTO(form, formulaDefine);
            this.loadFormulaConditions(formulaConditions, formula);
            formulaTypeMap.computeIfAbsent(formula.getType(), k -> new ArrayList()).add(formula);
            formulaFormMap.computeIfAbsent(formKey, k -> new ArrayList()).add(formula);
            graph.addNode(FORMULA_DEFINE, (Object)formula);
        }
        for (Map.Entry entry : formulaFormMap.entrySet()) {
            graph.addNode(FORMULA_FORM, (Object)new FormulaFormDTO((String)entry.getKey(), (List)entry.getValue()));
        }
        return formulaTypeMap;
    }

    private Map<String, List<FormulaCondition>> queryFormulaConditions(FormulaSchemeDefine formulaScheme) {
        List<FormulaConditionLink> formulaConditionLinks = this.formulaConditionLinkDao.listConditionLinkByFormulaScheme(formulaScheme.getKey());
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

    private void loadFormulaConditions(Map<String, List<FormulaCondition>> conditions, FormulaDTO formula) {
        if (null == conditions) {
            return;
        }
        Collection formulaConditions = conditions.get(formula.getId());
        if (CollectionUtils.isEmpty(formulaConditions)) {
            return;
        }
        ArrayList<Formula> list = new ArrayList<Formula>();
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
            list.add(f);
        }
        formula.addConditions(list);
    }

    private List<FormulaParsedExpDTO> loadExpression(IGraphEditor graph, List<IParsedExpression> expressions) {
        ArrayList<FormulaParsedExpDTO> allExpressions = new ArrayList<FormulaParsedExpDTO>();
        for (IParsedExpression exp : expressions) {
            FormulaParsedExpDTO dto = new FormulaParsedExpDTO(exp);
            graph.addNode(FORMULA_EXP, (Object)dto);
            allExpressions.add(dto);
        }
        return allExpressions;
    }

    private ExecutorContext createExecutorContext(FormulaSchemeDefine formulaScheme) {
        ExecutorContext context = new ExecutorContext(this.dataDefinitionController);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runtimeViewController, this.dataDefinitionController, this.entityViewController, formulaScheme.getFormSchemeKey(), false);
        context.setEnv((IFmlExecEnvironment)environment);
        context.setJQReportModel(true);
        return context;
    }

    private ExecutorContext createExcelExecutorContext(FormulaSchemeDefine formulaScheme) {
        ExecutorContext context = new ExecutorContext(this.dataDefinitionController);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runtimeViewController, this.dataDefinitionController, this.entityViewController, formulaScheme.getFormSchemeKey(), false);
        context.setEnv((IFmlExecEnvironment)environment);
        context.setJQReportModel(false);
        return context;
    }

    private List<DataEngineConsts.FormulaType> transFormTypes(int type) {
        switch (type) {
            case 1: {
                return Collections.singletonList(DataEngineConsts.FormulaType.CALCULATE);
            }
            case 2: {
                return Collections.singletonList(DataEngineConsts.FormulaType.CHECK);
            }
            case 3: {
                return Arrays.asList(DataEngineConsts.FormulaType.CALCULATE, DataEngineConsts.FormulaType.CHECK);
            }
            case 4: {
                return Collections.singletonList(DataEngineConsts.FormulaType.BALANCE);
            }
            case 5: {
                return Arrays.asList(DataEngineConsts.FormulaType.CALCULATE, DataEngineConsts.FormulaType.BALANCE);
            }
            case 6: {
                return Arrays.asList(DataEngineConsts.FormulaType.CHECK, DataEngineConsts.FormulaType.BALANCE);
            }
            case 7: {
                return Arrays.asList(DataEngineConsts.FormulaType.CALCULATE, DataEngineConsts.FormulaType.CHECK, DataEngineConsts.FormulaType.BALANCE);
            }
        }
        return Collections.emptyList();
    }

    private Map<DataEngineConsts.FormulaType, List<IParsedExpression>> parseFormula(FormulaSchemeDefine formulaScheme, Map<Integer, List<Formula>> formulaTypeMap) {
        HashMap<DataEngineConsts.FormulaType, List<IParsedExpression>> expressions = new HashMap<DataEngineConsts.FormulaType, List<IParsedExpression>>();
        ExecutorContext context = this.createExecutorContext(formulaScheme);
        ExecutorContext excelContext = this.createExcelExecutorContext(formulaScheme);
        FmlEngineBaseMonitor fmlEngineBaseMonitor = new FmlEngineBaseMonitor(DataEngineConsts.DataEngineRunType.PARSE);
        fmlEngineBaseMonitor.start();
        for (Map.Entry<Integer, List<Formula>> entry : formulaTypeMap.entrySet()) {
            List<DataEngineConsts.FormulaType> formulaTypes = this.transFormTypes(entry.getKey());
            if (CollectionUtils.isEmpty(formulaTypes)) continue;
            ArrayList<Formula> formulas = new ArrayList<Formula>();
            ArrayList<Formula> excelFormulas = new ArrayList<Formula>();
            for (Formula formula : entry.getValue()) {
                if (formula instanceof FormulaDTO) {
                    FormulaDTO dto = (FormulaDTO)formula;
                    if (FormulaSyntaxStyle.FORMULA_SYNTAX_STYLE_EXCEL.getValue() == dto.getSyntax()) {
                        excelFormulas.add(formula);
                        continue;
                    }
                    formulas.add(formula);
                    continue;
                }
                formulas.add(formula);
            }
            this.parseFormula(expressions, context, formulaScheme, formulas, formulaTypes, fmlEngineBaseMonitor);
            this.parseFormula(expressions, excelContext, formulaScheme, excelFormulas, formulaTypes, fmlEngineBaseMonitor);
        }
        if (LOGGER.isDebugEnabled()) {
            for (Map.Entry<Integer, List<Object>> entry : expressions.entrySet()) {
                if (CollectionUtils.isEmpty((Collection)entry.getValue())) continue;
                int count = 0;
                for (Map.Entry<Integer, List<Formula>> item : formulaTypeMap.entrySet()) {
                    List<DataEngineConsts.FormulaType> formulaTypes = this.transFormTypes(item.getKey());
                    if (CollectionUtils.isEmpty(formulaTypes) || !formulaTypes.contains(entry.getKey())) continue;
                    count += item.getValue().size();
                }
                LOGGER.debug("\u62a5\u8868\u53c2\u6570\u7f13\u5b58\u52a0\u8f7d\uff1a\u89e3\u6790{}\u516c\u5f0f\uff0c\u5171\u89e3\u6790{}\u6761\u516c\u5f0f\uff0c\u5f97\u5230{}\u6761\u8bed\u6cd5\u6811", ((DataEngineConsts.FormulaType)entry.getKey()).getTitle(), count, entry.getValue().size());
            }
        }
        List<IParsedExpression> calcExpressions = (List<IParsedExpression>)expressions.get(DataEngineConsts.FormulaType.CALCULATE);
        calcExpressions = this.parsedExpressionFilter.removeCrossDimensionFML(formulaScheme.getFormSchemeKey(), calcExpressions, (IMonitor)fmlEngineBaseMonitor);
        expressions.put(DataEngineConsts.FormulaType.CALCULATE, calcExpressions);
        fmlEngineBaseMonitor.finish();
        return expressions;
    }

    private void parseFormula(Map<DataEngineConsts.FormulaType, List<IParsedExpression>> expressions, ExecutorContext context, FormulaSchemeDefine formulaScheme, List<Formula> formulas, List<DataEngineConsts.FormulaType> formulaTypes, FmlEngineBaseMonitor monitor) {
        if (CollectionUtils.isEmpty(formulas)) {
            return;
        }
        Map map = null;
        try {
            map = DataEngineFormulaParser.parseFormula((ExecutorContext)context, formulas, formulaTypes, (IMonitor)monitor);
        }
        catch (ParseException e) {
            monitor.exception((Exception)new FormulaParseException("\u62a5\u8868\u53c2\u6570\u7f13\u5b58\u52a0\u8f7d\uff1a\u89e3\u6790\u516c\u5f0f\u5931\u8d25\uff0c\u516c\u5f0f\u65b9\u6848Key[" + formulaScheme.getKey() + "]", (Throwable)e));
        }
        if (CollectionUtils.isEmpty(map)) {
            return;
        }
        for (Map.Entry item : map.entrySet()) {
            if (null == item.getValue()) continue;
            expressions.computeIfAbsent((DataEngineConsts.FormulaType)item.getKey(), (Function<DataEngineConsts.FormulaType, List<IParsedExpression>>)((Function<DataEngineConsts.FormulaType, List>)k -> new ArrayList())).addAll((Collection)item.getValue());
        }
    }

    private void parsedExpressions(IGraphEditor graph, List<FormulaParsedExpDTO> allExpressions) {
        HashMap<String, FormulaFieldDTO> fields = new HashMap<String, FormulaFieldDTO>();
        HashMap<String, Set<String>> form2calLink = new HashMap<String, Set<String>>();
        HashMap link2exp = new HashMap();
        for (FormulaParsedExpDTO formulaParsedExpDTO : allExpressions) {
            FormulaDTO formula;
            IParsedExpression expression;
            block10: {
                expression = formulaParsedExpDTO.getParsedExpression();
                formula = (FormulaDTO)graph.getNode(FORMULA_DEFINE, expression.getSource().getId()).getData(FormulaDTO.class);
                formula.addExpression(formulaParsedExpDTO);
                try {
                    QueryFields writeFields;
                    QueryFields queryFields = expression.getReadQueryFields();
                    if (null != queryFields) {
                        for (QueryField field : queryFields) {
                            String fieldKey = field.getUID();
                            FormulaFieldDTO dto = (FormulaFieldDTO)fields.get(fieldKey);
                            if (null == dto) {
                                dto = new FormulaFieldDTO(fieldKey);
                                fields.put(fieldKey, dto);
                                graph.addNode(FORMULA_FIELD, (Object)dto);
                            }
                            dto.addReadParsedExp(formulaParsedExpDTO);
                            formulaParsedExpDTO.addReadField(dto);
                        }
                    }
                    if (null != (writeFields = expression.getWriteQueryFields())) {
                        for (QueryField field : writeFields) {
                            String fieldKey = field.getUID();
                            FormulaFieldDTO dto = (FormulaFieldDTO)fields.get(fieldKey);
                            if (null == dto) {
                                dto = new FormulaFieldDTO(fieldKey);
                                fields.put(fieldKey, dto);
                                graph.addNode(FORMULA_FIELD, (Object)dto);
                            }
                            dto.addWriteParsedExp(formulaParsedExpDTO);
                            formulaParsedExpDTO.addWriteField(dto);
                        }
                    }
                }
                catch (UnknownReadWriteException e) {
                    if (DataEngineConsts.FormulaType.CALCULATE != formulaParsedExpDTO.getParsedExpression().getFormulaType()) break block10;
                    graph.setProperty(FORMULA_FIELD_ERROR, (Object)true);
                }
            }
            FormulaTrackUtil.parseExpression(expression, link -> link2exp.computeIfAbsent(link, key -> new ArrayList()).add(exp), (form, link) -> {
                formula.setEffectiveForm((String)form);
                form2calLink.computeIfAbsent((String)form, kew -> new HashSet()).add(link);
            });
        }
        this.loadCalDataLinks(graph, form2calLink);
        for (Map.Entry entry : link2exp.entrySet()) {
            graph.addNode(GraphHelper.createDataWrapper((NodeLabel)FORMULA_DATALINK, (String)((String)entry.getKey()), new HashSet((Collection)entry.getValue())));
        }
    }

    private void loadCalDataLinks(IGraphEditor graph, Map<String, Set<String>> datalinkCodeMap) {
        for (Map.Entry<String, Set<String>> entry : datalinkCodeMap.entrySet()) {
            ArrayList<String> dataLinkKeys = new ArrayList<String>();
            List<DataLinkDefine> dataLinkDefines = this.runtimeViewController.queryDataLinkDefineByUniquecodes(entry.getKey(), (Collection<String>)entry.getValue());
            for (DataLinkDefine dataLinkDefine : dataLinkDefines) {
                if (dataLinkDefine == null) continue;
                dataLinkKeys.add(dataLinkDefine.getKey());
            }
            INode formNode = graph.getNode(FORMULA_FORM, entry.getKey());
            if (null == formNode) {
                formNode = graph.addNode(FORMULA_FORM, (Object)new FormulaFormDTO(entry.getKey(), Collections.emptyList()));
            }
            ((FormulaFormDTO)formNode.getData(FormulaFormDTO.class)).setCalDatalinks(dataLinkKeys);
        }
    }

    private void loadBalanceExpression(IGraphEditor graph, List<IParsedExpression> balanceExpressions) {
        if (CollectionUtils.isEmpty(balanceExpressions)) {
            return;
        }
        Map<String, List<IParsedExpression>> map = balanceExpressions.stream().collect(Collectors.groupingBy(e -> {
            String formKey = e.getFormKey();
            return StringUtils.hasText(formKey) ? formKey : "00000000-0000-0000-0000-000000000000";
        }));
        List<IParsedExpression> crossExpressions = map.get("00000000-0000-0000-0000-000000000000");
        Map<String, String> crossBalance = this.parseBalanceExpression(crossExpressions);
        graph.forEachNode(FORMULA_FORM, n -> {
            if (!n.getKey().equals("00000000-0000-0000-0000-000000000000")) {
                List expressions = (List)map.get(n.getKey());
                Map<String, String> balance = this.parseBalanceExpression(expressions);
                balance.putAll(crossBalance);
                ((FormulaFormDTO)n.getData(FormulaFormDTO.class)).setBlanceExpressions(balance);
            }
        });
    }

    private Map<String, String> parseBalanceExpression(List<IParsedExpression> iParsedExpressions) {
        if (CollectionUtils.isEmpty(iParsedExpressions)) {
            return Collections.emptyMap();
        }
        HashMap<String, String> columnFormulas = new HashMap<String, String>();
        for (IParsedExpression iParsedExpression : iParsedExpressions) {
            if (iParsedExpression.getBalanceField() == null) {
                FML_LOGGER.warn("\u62a5\u8868\u53c2\u6570\u7f13\u5b58\u52a0\u8f7d\uff1a\u89e3\u6790\u516c\u5f0f\u5931\u8d25: {}\u8c03\u6574\u6307\u6807\u83b7\u53d6\u5931\u8d25\uff01", (Object)iParsedExpression.getSource().getFormula());
                continue;
            }
            columnFormulas.put(iParsedExpression.getBalanceField().getUID(), iParsedExpression.getBalanceFormula());
        }
        HashMap<String, String> dataFieldFormulas = new HashMap<String, String>();
        List deployInfos = this.runtimeDataSchemeService.getDeployInfoByColumnKeys(new ArrayList(columnFormulas.keySet()));
        for (DataFieldDeployInfo deployInfo : deployInfos) {
            dataFieldFormulas.put(deployInfo.getDataFieldKey(), (String)columnFormulas.get(deployInfo.getColumnModelKey()));
        }
        return dataFieldFormulas;
    }

    private String getFormulaScript(String formulaScheme, String form, List<IParsedExpression> expressions, FmlEngineBaseMonitor monitor) {
        FormulaScriptService formulaScriptService = new FormulaScriptService(this.dataDefinitionController, this.runtimeViewController, this.entityViewController);
        FormDefine formDefine = this.runtimeViewController.queryFormById(form);
        if (null == formDefine) {
            return null;
        }
        FormSchemeDefine formSchemeDefine = this.runtimeViewController.getFormScheme(formDefine.getFormScheme());
        if (null == formSchemeDefine) {
            return null;
        }
        TaskDefine taskDefine = this.runtimeViewController.queryTaskDefine(formSchemeDefine.getTaskKey());
        if (null == taskDefine) {
            return null;
        }
        return formulaScriptService.getFormulaScript(formulaScheme, form, expressions, taskDefine.getFormulaSyntaxStyle(), monitor);
    }

    protected Map<String, String> getFormulaScript(String formulaSchemeKey, IGraph graph) {
        HashMap<String, String> formulaJs = new HashMap<String, String>();
        List forms = graph.getNodes(FORMULA_FORM).stream().map(n -> (FormulaFormDTO)n.getData(FormulaFormDTO.class)).filter(f -> !"00000000-0000-0000-0000-000000000000".equals(f.getFormKey())).collect(Collectors.toList());
        FmlEngineBaseMonitor fmlEngineBaseMonitor = new FmlEngineBaseMonitor(DataEngineConsts.DataEngineRunType.PARSE);
        fmlEngineBaseMonitor.start();
        for (FormulaFormDTO form : forms) {
            ArrayList<IParsedExpression> exps = new ArrayList<IParsedExpression>();
            for (FormulaDTO formula : form.getFormulas()) {
                exps.addAll(formula.getExpressions().stream().map(FormulaParsedExp::getParsedExpression).filter(f -> DataEngineConsts.FormulaType.CALCULATE == f.getFormulaType()).collect(Collectors.toList()));
            }
            String js = this.getFormulaScript(formulaSchemeKey, form.getFormKey(), exps, fmlEngineBaseMonitor);
            formulaJs.put(form.getFormKey(), js);
        }
        fmlEngineBaseMonitor.finish();
        return formulaJs;
    }

    private void loadFormulaScript(FormulaSchemeDefine formulaScheme, IGraph graph) {
        Map<String, String> formulaJs = this.getFormulaScript(formulaScheme.getKey(), graph);
        if (this.enableJsDiskCache()) {
            for (Map.Entry<String, String> entry : formulaJs.entrySet()) {
                DiskDataUtils.write(DiskDataUtils.tempPath(JS_DIR, formulaScheme.getKey(), entry.getKey()), entry.getValue());
            }
        } else {
            for (Map.Entry<String, String> entry : formulaJs.entrySet()) {
                INode node = graph.getNode(FORMULA_FORM, entry.getKey());
                if (null == node) continue;
                FormulaFormDTO form = (FormulaFormDTO)node.getData(FormulaFormDTO.class);
                form.setJs(entry.getValue());
            }
        }
    }

    protected boolean enableJsDiskCache() {
        return this.enableJsDiskCache;
    }

    public IGraph getFormulaGraph(IRunTimeViewController runtimeViewController, String formulaSchemeKey) {
        NrFormulaGraphService service = new NrFormulaGraphService();
        service.enableJsDiskCache = this.enableJsDiskCache;
        service.enabledFilter = this.enabledFilter;
        service.disableFormulaSchemes = this.disableFormulaSchemes;
        service.formulaSchemeDao = this.formulaSchemeDao;
        service.formulaDao = this.formulaDao;
        service.formulaConditionLinkDao = this.formulaConditionLinkDao;
        service.formulaConditionDao = this.formulaConditionDao;
        service.runtimeDataSchemeService = this.runtimeDataSchemeService;
        service.dataDefinitionController = this.dataDefinitionController;
        service.entityViewController = this.entityViewController;
        service.queryUtils = this.queryUtils;
        service.runtimeViewController = runtimeViewController;
        service.parsedExpressionFilter = this.parsedExpressionFilter;
        return service.getFormulaGraph(formulaSchemeKey, false);
    }
}

