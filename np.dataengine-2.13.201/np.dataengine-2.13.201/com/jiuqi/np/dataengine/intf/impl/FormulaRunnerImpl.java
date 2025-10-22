/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.ASTNodeType
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.CostCalculator
 */
package com.jiuqi.np.dataengine.intf.impl;

import com.jiuqi.bi.syntax.ast.ASTNodeType;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.CostCalculator;
import com.jiuqi.np.dataengine.IPreProcessingHandler;
import com.jiuqi.np.dataengine.PreProcessingHandlerManager;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.collector.FieldExecInfo;
import com.jiuqi.np.dataengine.collector.FmlExecuteCollector;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DataModelLinkColumn;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.common.TempResource;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.definitions.FormulaCallBack;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.exception.CalcRunException;
import com.jiuqi.np.dataengine.exception.CheckRunException;
import com.jiuqi.np.dataengine.exception.ExecuteException;
import com.jiuqi.np.dataengine.exception.ExpressionException;
import com.jiuqi.np.dataengine.executors.CalcExprExecNetwork;
import com.jiuqi.np.dataengine.executors.ExecutorCenter;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.executors.ExprExecNetwork;
import com.jiuqi.np.dataengine.executors.ExprExecNetworkBase;
import com.jiuqi.np.dataengine.executors.ExprExecRegionCreator;
import com.jiuqi.np.dataengine.executors.FmlExecRegionCreator;
import com.jiuqi.np.dataengine.executors.SqlFmlExpresionCalculator;
import com.jiuqi.np.dataengine.executors.SqlFmlExpresionChecker;
import com.jiuqi.np.dataengine.intf.IExpressionEvaluator;
import com.jiuqi.np.dataengine.intf.IFormulaRunner;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.np.dataengine.intf.impl.ExpressionCollection;
import com.jiuqi.np.dataengine.intf.impl.ExpressionEvaluatorImpl;
import com.jiuqi.np.dataengine.node.CalcExpression;
import com.jiuqi.np.dataengine.node.CheckExpression;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.ExpressionUtils;
import com.jiuqi.np.dataengine.node.FormulaShowInfo;
import com.jiuqi.np.dataengine.node.FunctionCalcExpression;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.node.NodeShowInfo;
import com.jiuqi.np.dataengine.parse.AdvanceFunction;
import com.jiuqi.np.dataengine.parse.PreProcessingFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.util.CalcExpressionSortUtil;
import com.jiuqi.np.dataengine.util.DataEngineFormulaParser;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FormulaRunnerImpl
implements IFormulaRunner {
    private static final Logger logger = LoggerFactory.getLogger(FormulaRunnerImpl.class);
    public static final int RUNNER_TYPE_CALC = 1;
    public static final int RUNNER_TYPE_CHECK = 2;
    private FormulaCallBack callBack;
    private ExpressionCollection normalExpressionCollection = new ExpressionCollection();
    private ExpressionCollection lastExpressionCollection = null;
    private DataEngineConsts.DataEngineRunType runnerType;
    private ExecutorContext context;
    private DimensionValueSet masterKeyValues;
    private QueryParam queryParam;
    private boolean multiDimModule = false;

    public void setCallBack(FormulaCallBack callBack) {
        this.callBack = callBack;
    }

    public FormulaRunnerImpl(FormulaCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void prepareCalc(ExecutorContext context, DimensionValueSet masterKeyValues, IMonitor monitor) throws Exception {
        AbstractMonitor abstractMonitor = null;
        try {
            List<CalcExpression> cycleExpressions;
            StringBuilder buf;
            this.runnerType = DataEngineConsts.DataEngineRunType.CALCULATE;
            abstractMonitor = this.initMonitor(monitor);
            this.masterKeyValues = masterKeyValues;
            this.context = context;
            List<IParsedExpression> allExpressions = this.doParse(context, DataEngineConsts.FormulaType.CALCULATE, abstractMonitor);
            QueryContext qContext = new QueryContext(context, this.queryParam, monitor);
            qContext.setNeedTableRegion(true);
            this.doClassify(qContext, allExpressions, abstractMonitor);
            FmlExecuteCollector collector = qContext.getFmlExecuteCollector();
            if (this.normalExpressionCollection.hasOneByOneExpressions()) {
                buf = new StringBuilder();
                cycleExpressions = this.normalExpressionCollection.getCycleExpressions();
                if (this.findRealCycles(qContext) && cycleExpressions.size() > 0) {
                    buf.append("\u62a5\u8868\u516c\u5f0f\u5b58\u5728\u5faa\u73af\u4f9d\u8d56\u7684\u516c\u5f0f\u5217\u8868\n");
                    this.printExpresions(qContext, buf, cycleExpressions);
                    if (collector != null) {
                        collector.getWanningCollector().getCycleExpressions().addAll(cycleExpressions);
                    }
                    abstractMonitor.message(buf.toString(), this);
                }
                buf.setLength(0);
                buf.append("\u53d7\u5faa\u73af\u4f9d\u8d56\u516c\u5f0f\u5f71\u54cd\uff0c\u4ee5\u4e0b\u516c\u5f0f\u6309\u987a\u5e8f\u6267\u884c\uff1a\n");
                this.printExpresions(qContext, buf, this.normalExpressionCollection.getOneByOneExpressions());
                if (collector != null) {
                    collector.getWanningCollector().getOneByOneExpressions().addAll(this.normalExpressionCollection.getOneByOneExpressions());
                }
                abstractMonitor.message(buf.toString(), this);
            }
            if (this.lastExpressionCollection != null && this.lastExpressionCollection.hasOneByOneExpressions()) {
                buf = new StringBuilder();
                cycleExpressions = this.lastExpressionCollection.getCycleExpressions();
                if (this.findRealCycles(qContext) && cycleExpressions.size() > 0) {
                    buf.append("\u8868\u95f4\u516c\u5f0f\u5b58\u5728\u5faa\u73af\u4f9d\u8d56\u7684\u516c\u5f0f\u5217\u8868\n");
                    this.printExpresions(qContext, buf, cycleExpressions);
                    cycleExpressions.forEach(exp -> buf.append(exp.getSource()).append("\n"));
                    if (collector != null) {
                        collector.getWanningCollector().getCycleExpressions().addAll(cycleExpressions);
                    }
                    abstractMonitor.message(buf.toString(), this);
                }
                buf.setLength(0);
                buf.append("\u53d7\u5faa\u73af\u4f9d\u8d56\u516c\u5f0f\u5f71\u54cd\uff0c\u4ee5\u4e0b\u8868\u95f4\u516c\u5f0f\u516c\u5f0f\u6309\u987a\u5e8f\u6267\u884c\uff1a\n");
                this.printExpresions(qContext, buf, this.lastExpressionCollection.getOneByOneExpressions());
                if (collector != null) {
                    collector.getWanningCollector().getOneByOneExpressions().addAll(this.lastExpressionCollection.getOneByOneExpressions());
                }
                abstractMonitor.message(buf.toString(), this);
            }
            abstractMonitor.onProgress(0.05);
        }
        catch (Exception e) {
            CalcRunException ce = new CalcRunException(e);
            abstractMonitor.exception(ce);
        }
    }

    private AbstractMonitor initMonitor(IMonitor monitor) {
        AbstractMonitor abstractMonitor = null;
        if (monitor instanceof AbstractMonitor && (abstractMonitor = (AbstractMonitor)monitor).getRunType() == DataEngineConsts.DataEngineRunType.UNKOWN) {
            abstractMonitor.setRunType(this.runnerType);
        }
        if (abstractMonitor == null) {
            abstractMonitor = new AbstractMonitor(this.runnerType);
        }
        if (this.queryParam.getDataChangeListeners() != null) {
            abstractMonitor.getDataChangeListeners().addAll(this.queryParam.getDataChangeListeners());
        }
        abstractMonitor.start();
        return abstractMonitor;
    }

    private QueryContext getQueryContext(ExecutorContext context, TempResource tempResource, IMonitor monitor) throws ParseException {
        QueryContext qContext = new QueryContext(context, this.queryParam, monitor);
        tempResource.setConnectionProvider(this.queryParam.getConnectionProvider());
        qContext.setTempResource(tempResource);
        qContext.setNeedTableRegion(true);
        DimensionValueSet masterKeys = new DimensionValueSet(this.masterKeyValues);
        qContext.setMasterKeys(masterKeys);
        for (int i = 0; i < masterKeys.size(); ++i) {
            Object dimValue = masterKeys.getValue(i);
            if (!(dimValue instanceof List)) continue;
            List values = (List)dimValue;
            String dimension = masterKeys.getName(i);
            qContext.getTempAssistantTable(dimension, dimValue, 6);
            if (values.size() == 1) {
                masterKeys.setValue(dimension, values.get(0));
                continue;
            }
            if (values.size() <= 1) continue;
            qContext.setBatch(true);
        }
        qContext.setRunnerType(this.runnerType);
        return qContext;
    }

    private void doClassify(QueryContext qContext, List<IParsedExpression> allExpressions, IMonitor monitor) {
        if (this.runnerType == DataEngineConsts.DataEngineRunType.CALCULATE) {
            ArrayList<CalcExpression> normalExpressions = new ArrayList<CalcExpression>();
            ArrayList<CalcExpression> lastExpressions = new ArrayList<CalcExpression>();
            ArrayList<CalcExpression> noCycles = new ArrayList<CalcExpression>();
            ArrayList<CalcExpression> cycles = new ArrayList<CalcExpression>();
            for (IParsedExpression parsedExpression : allExpressions) {
                if (this.hasAdvanceFunction(parsedExpression)) {
                    if (parsedExpression.getSource().getReportName() != null) {
                        this.normalExpressionCollection.getAdvanceExpressions().add(parsedExpression);
                        continue;
                    }
                    this.getLastExpressionCollection().getAdvanceExpressions().add(parsedExpression);
                    continue;
                }
                if (parsedExpression.getSource().getReportName() != null) {
                    this.collectPreProcessingFunctions(parsedExpression, this.normalExpressionCollection);
                    normalExpressions.add((CalcExpression)parsedExpression);
                    continue;
                }
                this.collectPreProcessingFunctions(parsedExpression, this.getLastExpressionCollection());
                lastExpressions.add((CalcExpression)parsedExpression);
            }
            try {
                this.analysisCycles(monitor, normalExpressions, lastExpressions, noCycles, cycles);
            }
            catch (ParseException e) {
                monitor.exception((Exception)((Object)e));
            }
            if (this.lastExpressionCollection != null) {
                this.normalExpressionCollection.wight = this.normalExpressionCollection.size() / (this.normalExpressionCollection.size() + this.lastExpressionCollection.size());
                this.lastExpressionCollection.wight = 1.0 - this.normalExpressionCollection.wight;
            }
        } else {
            for (IParsedExpression parsedExpression : allExpressions) {
                this.collectPreProcessingFunctions(parsedExpression, this.normalExpressionCollection);
                this.normalExpressionCollection.getNetworkExpressions().add(parsedExpression);
            }
        }
    }

    private boolean hasAdvanceFunction(IParsedExpression parsedExpression) {
        if (parsedExpression instanceof FunctionCalcExpression) {
            return false;
        }
        boolean hasAdvanceFunction = false;
        for (IASTNode node : parsedExpression.getRealExpression()) {
            FunctionNode functionNode;
            if (!(node instanceof FunctionNode) || !((functionNode = (FunctionNode)node).getDefine() instanceof AdvanceFunction)) continue;
            hasAdvanceFunction = true;
            break;
        }
        return hasAdvanceFunction;
    }

    private void collectPreProcessingFunctions(IParsedExpression parsedExpression, ExpressionCollection expressionCollection) {
        for (IASTNode node : parsedExpression.getRealExpression()) {
            FunctionNode functionNode;
            IFunction fun;
            if (!(node instanceof FunctionNode) || !((fun = (functionNode = (FunctionNode)node).getDefine()) instanceof PreProcessingFunction)) continue;
            expressionCollection.addPreProcessingFunction(functionNode);
        }
    }

    private void analysisCycles(IMonitor monitor, List<CalcExpression> normalExpressions, List<CalcExpression> lastExpressions, List<CalcExpression> noCycles, List<CalcExpression> cycles) throws ParseException {
        ArrayList<CalcExpression> exps;
        cycles.clear();
        noCycles.clear();
        QueryContext qContext = new QueryContext(this.context, this.queryParam, monitor);
        qContext.setNeedTableRegion(true);
        CalcExpressionSortUtil.analysisCycles(normalExpressions, noCycles, cycles, monitor);
        Collections.sort(cycles);
        this.normalExpressionCollection.getOneByOneExpressions().clear();
        this.normalExpressionCollection.getOneByOneExpressions().addAll(cycles);
        this.normalExpressionCollection.getNetworkExpressions().clear();
        this.normalExpressionCollection.getNetworkExpressions().addAll(noCycles);
        if (cycles.size() > 0 && this.findRealCycles(qContext)) {
            exps = new ArrayList<CalcExpression>(cycles);
            cycles.clear();
            noCycles.clear();
            CalcExpressionSortUtil.analysisCycles_new(qContext, exps, noCycles, cycles);
            this.normalExpressionCollection.getCycleExpressions().addAll(cycles);
        }
        if (lastExpressions.size() > 0) {
            cycles.clear();
            noCycles.clear();
            CalcExpressionSortUtil.analysisCycles(lastExpressions, noCycles, cycles, monitor);
            Collections.sort(cycles);
            this.getLastExpressionCollection().getOneByOneExpressions().clear();
            this.getLastExpressionCollection().getOneByOneExpressions().addAll(cycles);
            this.getLastExpressionCollection().getNetworkExpressions().clear();
            this.getLastExpressionCollection().getNetworkExpressions().addAll(noCycles);
            if (cycles.size() > 0 && this.findRealCycles(qContext)) {
                exps = new ArrayList<CalcExpression>(cycles);
                cycles.clear();
                noCycles.clear();
                CalcExpressionSortUtil.analysisCycles_new(qContext, exps, noCycles, cycles);
                this.getLastExpressionCollection().getCycleExpressions().addAll(cycles);
            }
        }
    }

    private boolean findRealCycles(QueryContext qContext) {
        return qContext.outFMLPlan();
    }

    private ExpressionCollection getLastExpressionCollection() {
        if (this.lastExpressionCollection == null) {
            this.lastExpressionCollection = new ExpressionCollection();
        }
        return this.lastExpressionCollection;
    }

    private boolean canBySql(IParsedExpression parsedExpression) {
        return false;
    }

    private List<IParsedExpression> doParse(ExecutorContext context, DataEngineConsts.FormulaType formulaType, IMonitor monitor) throws ParseException {
        List<IParsedExpression> allExpressions = this.callBack.getParsedExpressions();
        if (allExpressions == null || allExpressions.isEmpty()) {
            List<Formula> formulas = this.callBack.getFormulas();
            allExpressions = DataEngineFormulaParser.parseFormula(context, formulas, formulaType, monitor);
            if (this.multiDimModule && this.queryParam.getMultiDimAdapter() != null) {
                this.queryParam.getMultiDimAdapter().parseMultiDimExpressions(context, this.masterKeyValues, allExpressions, monitor);
            }
        }
        QueryContext qContext = new QueryContext(context, monitor);
        qContext.setNeedTableRegion(true);
        this.printOutFormulas(qContext, allExpressions);
        ArrayList<IParsedExpression> allParsedExpressions = new ArrayList<IParsedExpression>();
        Set<QueryTable> restrictionTables = null;
        if (this.runnerType == DataEngineConsts.DataEngineRunType.CALCULATE) {
            for (int i = 0; i < allExpressions.size(); ++i) {
                CalcExpression calcExpression = (CalcExpression)allExpressions.get(i);
                if (!calcExpression.isValidate()) continue;
                calcExpression.setIndex(i);
                if (calcExpression.isNeedExpand()) {
                    try {
                        DynamicDataNode assignNode = calcExpression.getAssignNode();
                        DataModelLinkColumn dataLink = assignNode.getDataModelLink();
                        if (dataLink == null || dataLink.getExpandDims() == null) continue;
                        if (restrictionTables == null) {
                            restrictionTables = this.initRestrictionTables(allExpressions);
                        }
                        if (!restrictionTables.contains(assignNode.getQueryField().getTable())) {
                            allParsedExpressions.add(calcExpression);
                            continue;
                        }
                        Map<String, List<Object>> expandDims = context.getEnv().getDataModelLinkFinder().expandByDimensions(context, dataLink);
                        DimensionValueSet dimensionRestriction = new DimensionValueSet();
                        if (expandDims != null) {
                            for (String dim : expandDims.keySet()) {
                                List<Object> values = expandDims.get(dim);
                                dimensionRestriction.setValue(dim, values);
                            }
                        }
                        ArrayList<DimensionValueSet> expandDimentsionValueSet = new ArrayList<DimensionValueSet>();
                        ExpressionUtils.expandDims(dimensionRestriction, expandDimentsionValueSet);
                        this.expandExpByDims(qContext, allParsedExpressions, calcExpression, assignNode, expandDimentsionValueSet);
                    }
                    catch (Exception e) {
                        monitor.exception(e);
                    }
                    continue;
                }
                allParsedExpressions.add(calcExpression);
            }
        } else {
            allParsedExpressions.addAll(allExpressions.stream().filter(o -> o.isValidate()).collect(Collectors.toList()));
        }
        return allParsedExpressions;
    }

    private Set<QueryTable> initRestrictionTables(List<IParsedExpression> allExpressions) {
        HashSet<QueryTable> restrictionTables = new HashSet<QueryTable>();
        for (IParsedExpression exp : allExpressions) {
            QueryTable table;
            DimensionValueSet dimensionRestriction;
            CalcExpression calcExp = (CalcExpression)exp;
            DynamicDataNode assignNode = calcExp.getAssignNode();
            if (assignNode == null || (dimensionRestriction = (table = assignNode.getQueryField().getTable()).getDimensionRestriction()) == null || restrictionTables.contains(table)) continue;
            for (int i = 0; i < dimensionRestriction.size(); ++i) {
                String dimName = dimensionRestriction.getName(i);
                if (dimName.equals("DATATIME")) continue;
                restrictionTables.add(table);
            }
        }
        return restrictionTables;
    }

    private void expandExpByDims(QueryContext qContext, List<IParsedExpression> allParsedExpressions, CalcExpression calcExpression, DynamicDataNode assignNode, List<DimensionValueSet> expandDimentsionValueSet) throws ParseException {
        qContext.getMonitor().debug("\u5c55\u5f00\u524d\u516c\u5f0f:" + calcExpression, DataEngineConsts.DebugLogType.COMMON);
        FormulaShowInfo formulaShowInfo = new FormulaShowInfo(DataEngineConsts.FormulaShowType.DATA);
        for (DimensionValueSet dim : expandDimentsionValueSet) {
            try {
                CalcExpression expandExp = (CalcExpression)calcExpression.clone();
                Iterator<IASTNode> iterator = expandExp.iterator();
                while (iterator.hasNext()) {
                    IASTNode node = iterator.next();
                    for (int n = 0; n < node.childrenSize(); ++n) {
                        DynamicDataNode dataNode;
                        DataModelLinkColumn dataLink;
                        IASTNode child = node.getChild(n);
                        if (!(child instanceof DynamicDataNode) || (dataLink = (dataNode = (DynamicDataNode)child).getDataModelLink()) == null || dataLink.getExpandDims() == null) continue;
                        QueryField newQueryField = this.context.getCache().extractQueryField(this.context, dataNode.getDataLink().getField(), null, dim);
                        DynamicDataNode newDataNode = new DynamicDataNode(dataNode.getToken(), newQueryField);
                        NodeShowInfo showInfo = newDataNode.getShowInfo();
                        showInfo.setHasBracket(true);
                        showInfo.setTableName(newQueryField.getTableName());
                        StringBuilder innerAppend = new StringBuilder();
                        for (int i = 0; i < dim.size(); ++i) {
                            innerAppend.append(dim.getName(i));
                            innerAppend.append("=");
                            innerAppend.append("\"").append(dim.getValue(i)).append("\"");
                            innerAppend.append(",");
                        }
                        if (innerAppend.length() > 0) {
                            innerAppend.setLength(innerAppend.length() - 1);
                        }
                        showInfo.setInnerAppend(innerAppend.toString());
                        if (dataNode.equals((Object)assignNode)) {
                            expandExp.setAssignNode(newDataNode);
                        }
                        node.setChild(n, (IASTNode)newDataNode);
                    }
                }
                qContext.getMonitor().debug("\u5c55\u5f00\u516c\u5f0f: " + expandExp.interpret(this.context, Language.FORMULA, formulaShowInfo), DataEngineConsts.DebugLogType.COMMON);
                allParsedExpressions.add(expandExp);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public void prepareCheck(ExecutorContext context, DimensionValueSet masterKeyValues, IMonitor monitor) throws Exception {
        AbstractMonitor abstractMonitor = null;
        try {
            this.runnerType = DataEngineConsts.DataEngineRunType.CHECK;
            abstractMonitor = this.initMonitor(monitor);
            this.masterKeyValues = masterKeyValues;
            this.context = context;
            List<IParsedExpression> allExpressions = this.doParse(context, DataEngineConsts.FormulaType.CHECK, abstractMonitor);
            this.doClassify(null, allExpressions, abstractMonitor);
            if (abstractMonitor != null) {
                abstractMonitor.onProgress(0.05);
            }
        }
        catch (Exception e) {
            CheckRunException ce = new CheckRunException(e);
            abstractMonitor.exception(ce);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run(IMonitor monitor) throws Exception {
        AbstractMonitor abstractMonitor = this.initMonitor(monitor);
        abstractMonitor.addFormulaCount(this.callBack.getParsedExpressions().size());
        try (TempResource tempResource = new TempResource();){
            FmlExecuteCollector fmlExecuteCollector;
            QueryContext qContext = this.getQueryContext(this.context, tempResource, abstractMonitor);
            if (qContext.outFMLPlan()) {
                abstractMonitor.message(this.runnerType.getTitle() + "\u5f00\u59cb\u6267\u884c\uff0c\u7ef4\u5ea6\u503c\uff1a" + this.masterKeyValues, this);
            }
            if ((fmlExecuteCollector = qContext.getFmlExecuteCollector()) != null) {
                fmlExecuteCollector.init(qContext);
            }
            if (this.runnerType == DataEngineConsts.DataEngineRunType.CALCULATE) {
                this.runCalc(qContext, abstractMonitor, this.normalExpressionCollection);
                if (this.lastExpressionCollection != null) {
                    this.runCalc(qContext, abstractMonitor, this.lastExpressionCollection);
                }
            } else if (this.runnerType == DataEngineConsts.DataEngineRunType.CHECK) {
                this.runCheck(qContext, abstractMonitor);
            }
        }
        catch (Exception e) {
            abstractMonitor.exception(e);
        }
        finally {
            try {
                this.queryParam.closeConnection();
            }
            finally {
                abstractMonitor.finish();
            }
        }
    }

    private void runCheck(QueryContext qContext, AbstractMonitor abstractMonitor) throws ExecuteException, Exception {
        List<IParsedExpression> oneByOneExpressions = this.normalExpressionCollection.getOneByOneExpressions();
        List<IParsedExpression> networkExpressions = this.normalExpressionCollection.getNetworkExpressions();
        int totalFmlCount = oneByOneExpressions.size() + networkExpressions.size();
        if (totalFmlCount == 0) {
            return;
        }
        double sqlWight = 0.9 * (double)(oneByOneExpressions.size() / totalFmlCount);
        double sqlStep = oneByOneExpressions.size() > 0 ? 1.0 / (double)oneByOneExpressions.size() : 0.0;
        abstractMonitor.setStep(sqlStep * sqlWight);
        Formula formula = null;
        for (IParsedExpression parsedExpression : oneByOneExpressions) {
            try {
                IExpression expression = parsedExpression.getRealExpression();
                formula = parsedExpression.getSource();
                SqlFmlExpresionChecker checker = new SqlFmlExpresionChecker(qContext.getMasterKeys(), this.queryParam);
                checker.doCheck(qContext, formula, expression, abstractMonitor);
            }
            catch (Exception e) {
                String msg = "\u6267\u884csql\u5ba1\u6838\u516c\u5f0f" + formula + "\u6267\u884c\u51fa\u9519!:" + e.getMessage();
                CheckRunException ce = new CheckRunException(msg, e);
                abstractMonitor.exception(ce);
            }
        }
        double memoryWight = 0.9 - sqlWight;
        this.preProcessingCheckExpressions(qContext);
        this.checkNetworkExpressions(abstractMonitor, qContext, networkExpressions, memoryWight);
        qContext.dropInternalTempTables(null);
    }

    private void checkNetworkExpressions(AbstractMonitor abstractMonitor, QueryContext qContext, List<IParsedExpression> networkExpressions, double memoryWight) throws ExecuteException, Exception {
        if (networkExpressions.size() > 0) {
            ExprExecRegionCreator regionCreator = this.getExecRegionCreater(qContext);
            ExprExecNetwork execNetwork = this.createExecNetwork(qContext, regionCreator, false);
            for (IParsedExpression parsedExpression : networkExpressions) {
                try {
                    CheckExpression checkExpression = (CheckExpression)parsedExpression;
                    execNetwork.arrangeCheckExpression(checkExpression);
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
            execNetwork.initialize(abstractMonitor);
            abstractMonitor.setStep(memoryWight / (double)execNetwork.size());
            abstractMonitor.onProgress(abstractMonitor.getCurrentProgress() + 0.05);
            execNetwork.checkRunTask(abstractMonitor);
        }
    }

    private ExprExecNetwork createExecNetwork(QueryContext qContext, ExprExecRegionCreator regionCreator, boolean isCalc) {
        if (this.multiDimModule && this.queryParam.getMultiDimAdapter() != null) {
            return this.queryParam.getMultiDimAdapter().createNetwork(qContext, regionCreator, isCalc);
        }
        if (isCalc) {
            return new CalcExprExecNetwork(qContext, regionCreator);
        }
        return new ExprExecNetwork(qContext, regionCreator);
    }

    private ExprExecRegionCreator getExecRegionCreater(QueryContext qContext) {
        if (this.multiDimModule && this.queryParam.getMultiDimAdapter() != null) {
            return this.queryParam.getMultiDimAdapter().getFmlExecRegionCreator(qContext, this.queryParam);
        }
        return new FmlExecRegionCreator(qContext, this.queryParam);
    }

    private void preProcessingCheckExpressions(QueryContext qContext) {
        Set<String> preProcessingFunctions = this.normalExpressionCollection.getAllPreProcessingFunctionNames();
        if (preProcessingFunctions != null) {
            for (String funcName : preProcessingFunctions) {
                IPreProcessingHandler handler = PreProcessingHandlerManager.getInstance().findHandlerByName(funcName);
                if (handler == null) continue;
                List<FunctionNode> funList = this.normalExpressionCollection.getPreProcessingFunctionsByName(funcName);
                handler.preProcessing(qContext, funList);
            }
        }
    }

    private void runCalc(QueryContext qContext, AbstractMonitor abstractMonitor, ExpressionCollection expressionCollection) throws ExecuteException, Exception {
        double wight = expressionCollection.wight;
        List<IParsedExpression> oneByOneExpressions = expressionCollection.getOneByOneExpressions();
        List<IParsedExpression> networkExpressions = expressionCollection.getNetworkExpressions();
        List<IParsedExpression> advanceExpressions = expressionCollection.getAdvanceExpressions();
        int totalFmlCount = oneByOneExpressions.size() + networkExpressions.size() + advanceExpressions.size();
        if (totalFmlCount == 0) {
            return;
        }
        double oneByOneWight = 0.9 * wight * (double)(oneByOneExpressions.size() / totalFmlCount);
        double oneByOneStep = oneByOneExpressions.size() > 0 ? 1.0 / (double)oneByOneExpressions.size() : 0.0;
        double networkWight = 0.9 * wight - oneByOneWight;
        abstractMonitor.setStep(oneByOneStep * oneByOneWight);
        this.calcAdvanceExpressions(abstractMonitor, qContext, advanceExpressions);
        this.preProcessingCalcExpressions(expressionCollection, qContext, networkExpressions);
        this.calcNetworkExpressions(abstractMonitor, qContext, networkExpressions, networkWight);
        this.calcOneByOneExpressions(abstractMonitor, qContext, oneByOneExpressions);
        qContext.dropInternalTempTables(null);
    }

    private void calcOneByOneExpressions(AbstractMonitor abstractMonitor, QueryContext qContext, List<IParsedExpression> oneByOneExpressions) {
        for (IParsedExpression parsedExpression : oneByOneExpressions) {
            if (this.canBySql(parsedExpression)) {
                this.calcBySql(qContext, abstractMonitor, parsedExpression);
                continue;
            }
            try {
                QueryContext oneByOneQueryContext = new QueryContext(qContext.getExeContext(), this.queryParam, abstractMonitor);
                oneByOneQueryContext.setNeedMultiCalc(false);
                oneByOneQueryContext.setMasterKeys(qContext.getMasterKeys());
                oneByOneQueryContext.setBatch(qContext.isBatch());
                oneByOneQueryContext.setNeedTableRegion(true);
                oneByOneQueryContext.setTempResource(qContext.getTempResource());
                ExprExecRegionCreator regionCreator = this.getExecRegionCreater(oneByOneQueryContext);
                ExprExecNetwork execNetwork = this.createExecNetwork(oneByOneQueryContext, regionCreator, true);
                CalcExpression calcExpression = (CalcExpression)parsedExpression;
                execNetwork.arrangeCalcExpression(calcExpression);
                execNetwork.initialize(abstractMonitor);
                abstractMonitor.step();
                execNetwork.checkRunTask(abstractMonitor);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    private void calcNetworkExpressions(AbstractMonitor abstractMonitor, QueryContext qContext, List<IParsedExpression> networkExpressions, double networkWight) throws ExecuteException, Exception {
        if (networkExpressions.size() > 0) {
            this.printDuplicateAssign(qContext, networkExpressions);
            qContext.setNeedMultiCalc(false);
            ExprExecRegionCreator regionCreator = this.getExecRegionCreater(qContext);
            ExprExecNetwork execNetwork = this.createExecNetwork(qContext, regionCreator, true);
            for (IParsedExpression parsedExpression : networkExpressions) {
                try {
                    CalcExpression calcExpression = (CalcExpression)parsedExpression;
                    execNetwork.arrangeCalcExpression(calcExpression);
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
            execNetwork.initialize(abstractMonitor);
            abstractMonitor.setStep(networkWight / (double)execNetwork.size());
            abstractMonitor.onProgress(abstractMonitor.getCurrentProgress() + 0.05);
            execNetwork.checkRunTask(abstractMonitor);
            ((CalcExprExecNetwork)execNetwork).tryReCalc(abstractMonitor);
        }
    }

    private void preProcessingCalcExpressions(ExpressionCollection expressionCollection, QueryContext qContext, List<IParsedExpression> expressions) {
        Set<String> preProcessingFunctions = expressionCollection.getAllPreProcessingFunctionNames();
        if (preProcessingFunctions != null) {
            HashSet<QueryField> writeKeys = new HashSet<QueryField>();
            for (IParsedExpression expression : expressions) {
                QueryField writeKey = CalcExpressionSortUtil.getWriteQueryField((CalcExpression)expression);
                if (writeKey == null) continue;
                writeKeys.add(writeKey);
            }
            for (String funcName : preProcessingFunctions) {
                IPreProcessingHandler handler = PreProcessingHandlerManager.getInstance().findHandlerByName(funcName);
                if (handler == null) continue;
                List<FunctionNode> funList = expressionCollection.getPreProcessingFunctionsByName(funcName);
                for (int i = funList.size() - 1; i >= 0; --i) {
                    FunctionNode funcNode = funList.get(i);
                    boolean needWrite = false;
                    block3: for (IASTNode paramNode : funcNode.getParameters()) {
                        for (IASTNode child : paramNode) {
                            DynamicDataNode dataNode;
                            if (!(child instanceof DynamicDataNode) || !writeKeys.contains((dataNode = (DynamicDataNode)child).getQueryField())) continue;
                            needWrite = true;
                            continue block3;
                        }
                    }
                    if (!needWrite) continue;
                    funList.remove(i);
                }
                handler.preProcessing(qContext, funList);
            }
        }
    }

    private void calcBySql(QueryContext qContext, AbstractMonitor abstractMonitor, IParsedExpression parsedExpression) {
        try {
            IExpression expression = parsedExpression.getRealExpression();
            SqlFmlExpresionCalculator calculator = new SqlFmlExpresionCalculator(qContext.getMasterKeys(), this.queryParam);
            calculator.doCalculate(qContext, expression, abstractMonitor);
            abstractMonitor.step();
        }
        catch (Exception e) {
            Formula formula = parsedExpression.getSource();
            String msg = "\u6267\u884csql\u8fd0\u7b97\u516c\u5f0f" + formula + "\u6267\u884c\u51fa\u9519!:" + e.getMessage();
            CalcRunException ce = new CalcRunException(msg, e);
            abstractMonitor.exception(ce);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void calcAdvanceExpressions(AbstractMonitor abstractMonitor, QueryContext qContext, List<IParsedExpression> advanceExpressions) {
        if (advanceExpressions.size() > 0) {
            this.printCalcAdvanceExpressions(qContext, advanceExpressions);
            FormulaShowInfo formulaShowInfo = new FormulaShowInfo(DataEngineConsts.FormulaShowType.DATA);
            ArrayList<DimensionValueSet> runDims = new ArrayList<DimensionValueSet>();
            if (qContext.isBatch()) {
                ExpressionUtils.expandDims(qContext.getMasterKeys(), runDims);
            } else {
                runDims.add(qContext.getMasterKeys());
            }
            for (DimensionValueSet dim : runDims) {
                ExpressionEvaluatorImpl evaluator = new ExpressionEvaluatorImpl(this.queryParam);
                ExecutorCenter execNetwork = null;
                for (IParsedExpression expression : advanceExpressions) {
                    try {
                        CostCalculator costCalculator;
                        if (expression.getAssignNode() != null) {
                            if (execNetwork == null) {
                                QueryContext oneByOneQueryContext = new QueryContext(qContext.getExeContext(), this.queryParam, abstractMonitor);
                                oneByOneQueryContext.setNeedMultiCalc(false);
                                oneByOneQueryContext.setMasterKeys(dim);
                                oneByOneQueryContext.setNeedTableRegion(true);
                                oneByOneQueryContext.setTempResource(qContext.getTempResource());
                                ExprExecRegionCreator regionCreator = this.getExecRegionCreater(qContext);
                                execNetwork = this.createExecNetwork(oneByOneQueryContext, regionCreator, true);
                            }
                            CalcExpression calcExpression = (CalcExpression)expression;
                            ((ExprExecNetworkBase)execNetwork).arrangeCalcExpression(calcExpression);
                            continue;
                        }
                        qContext.getExeContext().setDefaultGroupName(expression.getSource().getReportName());
                        IFunction function = null;
                        for (IASTNode node : expression.getRealExpression()) {
                            IFunction funcDefine;
                            if (node instanceof DynamicDataNode) {
                                DynamicDataNode dataNode = (DynamicDataNode)node;
                                TableModelRunInfo tableInfo = qContext.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(dataNode.getQueryField().getTableName());
                                dataNode.getShowInfo().setTableName(tableInfo.getTableModelDefine().getCode());
                                dataNode.getShowInfo().setHasBracket(true);
                                continue;
                            }
                            if (!(node instanceof FunctionNode) || !((funcDefine = ((FunctionNode)node).getDefine()) instanceof AdvanceFunction)) continue;
                            function = funcDefine;
                        }
                        CostCalculator costCalculator2 = costCalculator = function == null ? null : qContext.getCostCalculator(ASTNodeType.FUNCTION, function.name());
                        if (function != null && costCalculator != null) {
                            try {
                                costCalculator.start();
                                this.evalAdvanceFunction(qContext, formulaShowInfo, dim, evaluator, expression);
                                continue;
                            }
                            finally {
                                costCalculator.end();
                                continue;
                            }
                        }
                        this.evalAdvanceFunction(qContext, formulaShowInfo, dim, evaluator, expression);
                    }
                    catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
                if (execNetwork == null) continue;
                try {
                    execNetwork.initialize(abstractMonitor);
                    abstractMonitor.step();
                    execNetwork.checkRunTask(abstractMonitor);
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }

    private void evalAdvanceFunction(QueryContext qContext, FormulaShowInfo formulaShowInfo, DimensionValueSet dim, IExpressionEvaluator evaluator, IParsedExpression expression) throws InterpretException, ExpressionException {
        String evalFormula = expression.getFormula(qContext, formulaShowInfo);
        List<Formula> conditions = expression.getSource().getConditions();
        if (conditions != null && conditions.size() > 0) {
            StringBuilder buff = new StringBuilder();
            buff.append("IfThen(");
            boolean needAnd = false;
            for (Formula condition : conditions) {
                if (needAnd) {
                    buff.append(" and ");
                }
                buff.append("(").append(condition.getFormula()).append(")");
                needAnd = true;
            }
            buff.append(",").append(evalFormula).append(")");
            evalFormula = buff.toString();
        }
        evaluator.eval(evalFormula, qContext.getExeContext(), dim);
    }

    private void printExpresions(QueryContext qContext, StringBuilder buff, List<? extends IParsedExpression> expressions) {
        for (IParsedExpression iParsedExpression : expressions) {
            Formula source = iParsedExpression.getSource();
            try {
                buff.append(source.getReportName()).append("[").append(source.getCode()).append("]:");
                buff.append(iParsedExpression.getFormula(qContext, new FormulaShowInfo(DataEngineConsts.FormulaShowType.JQ)));
                buff.append("\n");
            }
            catch (InterpretException e) {
                e.printStackTrace();
            }
        }
    }

    private void printDuplicateAssign(QueryContext qContext, List<IParsedExpression> networkExpressions) {
        StringBuilder msg = new StringBuilder();
        if (qContext.outFMLPlan()) {
            List<IParsedExpression> list;
            HashMap<QueryField, ArrayList<IParsedExpression>> assginMap = new HashMap<QueryField, ArrayList<IParsedExpression>>();
            for (IParsedExpression exp : networkExpressions) {
                DynamicDataNode assignNode = exp.getAssignNode();
                if (assignNode == null) continue;
                list = (ArrayList<IParsedExpression>)assginMap.get(assignNode.getQueryField());
                if (list == null) {
                    list = new ArrayList<IParsedExpression>();
                    assginMap.put(assignNode.getQueryField(), (ArrayList<IParsedExpression>)list);
                }
                list.add(exp);
            }
            FmlExecuteCollector collector = qContext.getFmlExecuteCollector();
            for (Map.Entry entry : assginMap.entrySet()) {
                list = (List)entry.getValue();
                if (list.size() <= 1) continue;
                if (msg.length() == 0) {
                    msg.append("\u5b58\u5728\u591a\u6b21\u88ab\u8d4b\u503c\u7684\u6307\u6807\uff1a\n");
                }
                if (collector != null) {
                    FieldExecInfo fieldExecInfo = new FieldExecInfo(((IParsedExpression)list.get(0)).getAssignNode());
                    fieldExecInfo.getLinkedExpressions().addAll(list);
                    collector.getWanningCollector().getDuplicateAssignNodes().add(fieldExecInfo);
                }
                QueryField queryField = (QueryField)entry.getKey();
                msg.append(queryField).append(":\n");
                this.printExpresions(qContext, msg, list);
            }
            if (msg.length() > 0) {
                qContext.getMonitor().message(msg.toString(), this);
            }
        }
    }

    public void setFormulaParam(QueryParam queryParam) {
        this.queryParam = queryParam;
    }

    @Override
    public void setMasterKeyValues(DimensionValueSet masterKeyValues) {
        this.masterKeyValues = masterKeyValues;
    }

    @Override
    public void setMultiDimModule(boolean multiDimModule) {
        this.multiDimModule = multiDimModule;
    }

    private void printOutFormulas(QueryContext qContext, List<IParsedExpression> allExpressions) {
        if (qContext.outFMLPlan()) {
            try {
                StringBuilder msg = new StringBuilder();
                msg.append("\u672c\u6b21").append(this.runnerType.getTitle()).append("\u7684\u6240\u6709\u516c\u5f0f\uff1a\n");
                LinkedHashMap<Formula, ArrayList<IParsedExpression>> formulas = new LinkedHashMap<Formula, ArrayList<IParsedExpression>>();
                for (IParsedExpression iParsedExpression : allExpressions) {
                    ArrayList<IParsedExpression> subList = (ArrayList<IParsedExpression>)formulas.get(iParsedExpression.getSource());
                    if (subList == null) {
                        subList = new ArrayList<IParsedExpression>();
                        formulas.put(iParsedExpression.getSource(), subList);
                    }
                    subList.add(iParsedExpression);
                }
                for (Map.Entry entry : formulas.entrySet()) {
                    Formula source = (Formula)entry.getKey();
                    msg.append(source).append("\n");
                    List subList = (List)entry.getValue();
                    if (subList.size() <= 1) continue;
                    for (IParsedExpression exp : subList) {
                        msg.append("    ").append(exp.getFormula(qContext, new FormulaShowInfo(DataEngineConsts.FormulaShowType.JQ)));
                        msg.append("\n");
                    }
                }
                qContext.getMonitor().message(msg.toString(), this);
            }
            catch (Exception e) {
                qContext.getMonitor().exception(e);
            }
        }
    }

    private void printCalcAdvanceExpressions(QueryContext qContext, List<IParsedExpression> advanceExpressions) {
        FmlExecuteCollector fmlExecuteCollector;
        if (qContext.outFMLPlan()) {
            try {
                StringBuilder msg = new StringBuilder();
                msg.append("\u9700\u8981\u9884\u5148\u6267\u884c\u7684\u53d6\u6570\u516c\u5f0f:\n");
                for (IParsedExpression exp : advanceExpressions) {
                    msg.append(exp.getSource()).append("\n");
                }
                qContext.getMonitor().message(msg.toString(), this);
            }
            catch (Exception e) {
                qContext.getMonitor().exception(e);
            }
        }
        if ((fmlExecuteCollector = qContext.getFmlExecuteCollector()) != null) {
            fmlExecuteCollector.getGlobalInfo().getAdvanceExpressions().addAll(advanceExpressions);
        }
    }
}

