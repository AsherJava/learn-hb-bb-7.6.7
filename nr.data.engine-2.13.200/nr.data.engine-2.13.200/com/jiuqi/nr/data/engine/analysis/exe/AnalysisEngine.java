/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.syntax.function.IFunctionProvider
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.operator.And
 *  com.jiuqi.bi.syntax.operator.IfThenElse
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.IReportCellProvider
 *  com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.bi.syntax.reportparser.ReportFunctionProvider
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.QueryParam
 *  com.jiuqi.np.dataengine.common.DataEngineConsts
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$DebugLogType
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaShowType
 *  com.jiuqi.np.dataengine.common.DataLinkColumn
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.common.TempResource
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.exception.ExecuteException
 *  com.jiuqi.np.dataengine.exception.ExpressionException
 *  com.jiuqi.np.dataengine.executors.CalcExprExecNetwork
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.executors.ExprExecNetwork
 *  com.jiuqi.np.dataengine.executors.ExprExecRegionCreator
 *  com.jiuqi.np.dataengine.executors.FmlExecRegionCreator
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.intf.impl.AbstractMonitor
 *  com.jiuqi.np.dataengine.node.CalcExpression
 *  com.jiuqi.np.dataengine.node.CheckExpression
 *  com.jiuqi.np.dataengine.node.ExpressionUtils
 *  com.jiuqi.np.dataengine.node.FormulaShowInfo
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 */
package com.jiuqi.nr.data.engine.analysis.exe;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.syntax.function.IFunctionProvider;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.operator.And;
import com.jiuqi.bi.syntax.operator.IfThenElse;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.IReportCellProvider;
import com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.bi.syntax.reportparser.ReportFunctionProvider;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DataLinkColumn;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.TempResource;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.exception.ExecuteException;
import com.jiuqi.np.dataengine.exception.ExpressionException;
import com.jiuqi.np.dataengine.executors.CalcExprExecNetwork;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.executors.ExprExecNetwork;
import com.jiuqi.np.dataengine.executors.ExprExecRegionCreator;
import com.jiuqi.np.dataengine.executors.FmlExecRegionCreator;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.np.dataengine.node.CalcExpression;
import com.jiuqi.np.dataengine.node.CheckExpression;
import com.jiuqi.np.dataengine.node.ExpressionUtils;
import com.jiuqi.np.dataengine.node.FormulaShowInfo;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.nr.data.engine.analysis.define.AnalysisCaliber;
import com.jiuqi.nr.data.engine.analysis.define.AnalysisModel;
import com.jiuqi.nr.data.engine.analysis.define.FloatRegionConfig;
import com.jiuqi.nr.data.engine.analysis.define.GroupingConfig;
import com.jiuqi.nr.data.engine.analysis.define.OrderField;
import com.jiuqi.nr.data.engine.analysis.exe.AnalysisContext;
import com.jiuqi.nr.data.engine.analysis.exe.AnalysisMonitor;
import com.jiuqi.nr.data.engine.analysis.exe.AnalysisRegion;
import com.jiuqi.nr.data.engine.analysis.exe.IAnalysisEngine;
import com.jiuqi.nr.data.engine.analysis.exe.ParsedFloatRegionConfig;
import com.jiuqi.nr.data.engine.analysis.exe.ParsedGroupingConfig;
import com.jiuqi.nr.data.engine.analysis.exe.ParsedOrderField;
import com.jiuqi.nr.data.engine.analysis.exe.network.AnalysisExecRegionCreator;
import com.jiuqi.nr.data.engine.analysis.exe.network.AnalysisExprExecNetwork;
import com.jiuqi.nr.data.engine.analysis.parse.AnalysisCellFmlProvider;
import com.jiuqi.nr.data.engine.analysis.parse.AnalysisDynamicDataNode;
import com.jiuqi.nr.data.engine.analysis.parse.AnalysisDynamicDataNodeFinder;
import com.jiuqi.nr.data.engine.analysis.parse.AnalysisExpression;
import com.jiuqi.nr.data.engine.analysis.parse.AnalysisFormulaParseUtils;
import com.jiuqi.nr.data.engine.analysis.parse.AnalysisFunctionProvider;
import com.jiuqi.nr.data.engine.util.Consts;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalysisEngine
implements IAnalysisEngine {
    private static final Logger logger = LogFactory.getLogger(AnalysisEngine.class);
    private static final String FIX_REGION_KEY = "fixRegion";
    private Map<String, AnalysisRegion> regions = new HashMap<String, AnalysisRegion>();
    private ExecutorContext context;
    private QueryParam queryParam;
    private List<CalcExpression> calcExpressions = new ArrayList<CalcExpression>();
    private ReportFormulaParser parser;
    private CheckExpression globalCondition;
    private AbstractMonitor monitor = new AbstractMonitor();
    private String reportName;
    private static final AnalysisFunctionProvider funProvider = new AnalysisFunctionProvider();

    @Override
    public void prepare(ExecutorContext context, AnalysisModel model) throws Exception {
        if (DataEngineConsts.DATA_ENGINE_DEBUG) {
            this.monitor.debug(model.toString(), DataEngineConsts.DebugLogType.COMMON);
        }
        this.context = context;
        this.reportName = model.getReportName();
        this.parser = this.getParser(context);
        context.setDefaultGroupName(this.reportName);
        AnalysisContext aContext = this.createAnalysisContext(null, (IMonitor)this.monitor, null);
        List<IParsedExpression> parsedExpressions = AnalysisFormulaParseUtils.parseFormula(aContext, this.parser, model.getFormulas(), this.reportName, (IMonitor)this.monitor);
        String globalFilter = model.getGlobalFilter();
        if (!StringUtils.isEmpty((String)globalFilter)) {
            IExpression condition = this.parseExpression(aContext, globalFilter);
            this.globalCondition = this.parseConditionAsCheck(aContext, condition, globalFilter, "globalFilter");
        }
        List<AnalysisCaliber> rowCalibers = model.getRowCalibers();
        Map<Integer, IExpression> rowConditionMap = this.getConditionMap(aContext, rowCalibers);
        for (FloatRegionConfig regionConfig : model.getRegionConfigMap().values()) {
            IExpression rowCondition = rowConditionMap.get(regionConfig.getRegionRowIndex());
            if (rowCondition == null) continue;
            AnalysisRegion region = this.getRegion(aContext, regionConfig, regionConfig.getRegionCode());
            CheckExpression srcMainDimCondition = this.parseConditionAsCheck(aContext, rowCondition, globalFilter, "SrcMainDimFilter");
            region.getConfig().setSrcMainDimCondition(srcMainDimCondition);
            rowConditionMap.remove(regionConfig.getRegionRowIndex());
        }
        List<AnalysisCaliber> colCalibers = model.getColCalibers();
        Map<Integer, IExpression> colConditionMap = this.getConditionMap(aContext, colCalibers);
        for (IParsedExpression parsedExpression : parsedExpressions) {
            AnalysisExpression analysisExpression = (AnalysisExpression)parsedExpression;
            if (analysisExpression.isCalc()) {
                this.calcExpressions.add(analysisExpression);
                continue;
            }
            AnalysisDynamicDataNode assignNode = (AnalysisDynamicDataNode)analysisExpression.getAssignNode();
            DataLinkColumn column = assignNode.getDataLink();
            if (column == null) continue;
            String regionKey = column.getRegion();
            AnalysisRegion region = this.getRegion(aContext, model.getRegionConfigMap().get(regionKey), regionKey);
            Position pos = column.getGridPosition();
            Consts.CellCaliberType cellCaliberType = model.getCellCaliberTypes().get(pos);
            if (cellCaliberType == null) {
                cellCaliberType = Consts.CellCaliberType.ALL;
            }
            if (pos != null) {
                IExpression colCondition;
                IExpression rowCondition = rowConditionMap.get(pos.row());
                if (rowCondition != null && (cellCaliberType == Consts.CellCaliberType.ALL || cellCaliberType == Consts.CellCaliberType.ROW)) {
                    int rowConditionIndex = region.getConditionJuder().addRowCondition(pos.row(), rowCondition);
                    analysisExpression.setRowConditionIndex(rowConditionIndex);
                }
                if ((colCondition = colConditionMap.get(pos.col())) != null && (cellCaliberType == Consts.CellCaliberType.ALL || cellCaliberType == Consts.CellCaliberType.COL)) {
                    int colConditionIndex = region.getConditionJuder().addColCondition(pos.col(), colCondition);
                    analysisExpression.setColConditionIndex(colConditionIndex);
                }
            }
            region.getExpressions().add((IExpression)analysisExpression);
        }
        this.printParsedInfo(aContext);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void execute(DimensionValueSet destDimension, DimensionValueSet srcDimension) throws Exception {
        try {
            if (DataEngineConsts.DATA_ENGINE_DEBUG) {
                this.monitor.debug("\u5206\u6790\u5f15\u64ce\u5f00\u59cb\u6267\u884c", DataEngineConsts.DebugLogType.COMMON);
                StringBuilder buff = new StringBuilder();
                buff.append("destDimension=").append(destDimension).append("\n");
                buff.append("srcDimension=").append(srcDimension).append("\n");
                this.monitor.debug(buff.toString(), DataEngineConsts.DebugLogType.COMMON);
            }
            DimensionValueSet srcMasterKeys = new DimensionValueSet(srcDimension);
            if (this.globalCondition != null) {
                this.judgeSrcMainDimCondition(srcMasterKeys, this.globalCondition);
            }
            this.executeAnalysis(destDimension, srcMasterKeys, this.monitor);
            this.executeCalc(destDimension, this.monitor);
            if (DataEngineConsts.DATA_ENGINE_DEBUG) {
                this.monitor.debug("\u5206\u6790\u5f15\u64ce\u6267\u884c\u5b8c\u6210", DataEngineConsts.DebugLogType.COMMON);
            }
        }
        finally {
            this.queryParam.closeConnection();
        }
    }

    private ReportFormulaParser getParser(ExecutorContext context) throws ParseException {
        IFmlExecEnvironment env;
        ReportFormulaParser parser = ReportFormulaParser.getInstance();
        parser.setJQReportMode(true);
        parser.registerDynamicNodeProvider(ExecutorContext.getPrioritycontextvariablemanager());
        if (context.getVariableManager() != null) {
            parser.registerDynamicNodeProvider((IReportDynamicNodeProvider)context.getVariableManager());
        }
        AnalysisCellFmlProvider cellProvider = new AnalysisCellFmlProvider();
        parser.registerCellProvider((IReportCellProvider)cellProvider);
        parser.registerDynamicNodeProvider((IReportDynamicNodeProvider)new AnalysisDynamicDataNodeFinder());
        parser.registerDynamicNodeProvider(ExecutorContext.getContextvariablemanager());
        if (context.getEnv() != null && (env = context.getEnv()).getDataNodeFinders() != null) {
            for (IReportDynamicNodeProvider provider : env.getDataNodeFinders()) {
                parser.registerDynamicNodeProvider(provider);
            }
        }
        parser.unregisterFunctionProvider((IFunctionProvider)ReportFunctionProvider.GLOBAL_PROVIDER);
        parser.registerFunctionProvider((IFunctionProvider)funProvider);
        return parser;
    }

    private void executeAnalysis(DimensionValueSet destDimension, DimensionValueSet srcMasterKeys, AbstractMonitor monitor) throws ParseException, ExecuteException, Exception {
        for (AnalysisRegion region : this.regions.values()) {
            CheckExpression srcMainDimCondigion;
            DimensionValueSet srcDimension = new DimensionValueSet(srcMasterKeys);
            if (region.getConfig() != null && (srcMainDimCondigion = region.getConfig().getSrcMainDimCondition()) != null) {
                this.judgeSrcMainDimCondition(srcDimension, srcMainDimCondigion);
            }
            AnalysisContext aContext = null;
            TempResource tempResource = new TempResource();
            Throwable throwable = null;
            try {
                aContext = this.createAnalysisContext(srcDimension, (IMonitor)monitor, tempResource);
                aContext.setConditionJuder(region.getConditionJuder());
                aContext.setMasterKeys(srcDimension);
                aContext.setDestMasterKeys(destDimension);
                aContext.setFloatConfig(region.getConfig());
                AnalysisExecRegionCreator regionCreator = new AnalysisExecRegionCreator(aContext, this.queryParam);
                AnalysisExprExecNetwork execNetwork = new AnalysisExprExecNetwork(aContext, regionCreator);
                for (IExpression expression : region.getConditionJuder().getConditions()) {
                    try {
                        execNetwork.arrangeCondExpression(expression);
                    }
                    catch (Exception e) {
                        logger.error(e.getMessage(), (Throwable)e);
                    }
                }
                for (IExpression expression : region.getExpressions()) {
                    try {
                        execNetwork.arrangeCalcExpression(expression);
                    }
                    catch (Exception e) {
                        logger.error(e.getMessage(), (Throwable)e);
                    }
                }
                if (region.getConfig() != null) {
                    ParsedGroupingConfig groupingConfig;
                    IExpression rowCondition = region.getConfig().getRowCondition();
                    if (rowCondition != null) {
                        execNetwork.arrangeCondExpression(rowCondition);
                    }
                    if ((groupingConfig = region.getConfig().getParsedGroupingConfig()) != null) {
                        for (IExpression iExpression : groupingConfig.getGrouppingFieldExpressions()) {
                            execNetwork.arrangeEvalExpression(iExpression);
                        }
                        aContext.buildDestFieldIndexes();
                        for (List list : groupingConfig.getLevelExpressions().values()) {
                            for (IExpression exp : list) {
                                execNetwork.arrangeEvalExpression(exp);
                            }
                        }
                    }
                }
                execNetwork.initialize(monitor);
                execNetwork.checkRunTask(monitor);
                aContext.commitUpdator();
            }
            catch (Throwable throwable2) {
                throwable = throwable2;
                throw throwable2;
            }
            finally {
                if (tempResource == null) continue;
                if (throwable != null) {
                    try {
                        tempResource.close();
                    }
                    catch (Throwable throwable3) {
                        throwable.addSuppressed(throwable3);
                    }
                    continue;
                }
                tempResource.close();
            }
        }
    }

    private void judgeSrcMainDimCondition(DimensionValueSet srcDimension, CheckExpression srcMainDimCondigion) throws ParseException, ExpressionException, SyntaxException, ExecuteException, Exception {
        AnalysisMonitor condigionMonitor = new AnalysisMonitor(srcDimension);
        condigionMonitor.setMainDim(this.context.getEnv().getUnitDimesion(this.context));
        try (TempResource tempResource = new TempResource();){
            AnalysisContext cContext = this.createAnalysisContext(srcDimension, (IMonitor)condigionMonitor, tempResource);
            cContext.setMasterKeys(srcDimension);
            FmlExecRegionCreator regionCreator = new FmlExecRegionCreator((QueryContext)cContext, false, this.queryParam);
            ExprExecNetwork execNetwork = new ExprExecNetwork((QueryContext)cContext, (ExprExecRegionCreator)regionCreator);
            execNetwork.arrangeCheckExpression((IExpression)srcMainDimCondigion);
            execNetwork.initialize((Object)condigionMonitor);
            execNetwork.checkRunTask((Object)condigionMonitor);
            condigionMonitor.finish();
        }
    }

    private AnalysisContext createAnalysisContext(DimensionValueSet masterKeys, IMonitor monitor, TempResource tempResource) throws ParseException {
        AnalysisContext cContext = new AnalysisContext(this.context, this.queryParam, monitor);
        cContext.setMasterKeys(masterKeys);
        cContext.setTempResource(tempResource);
        if (masterKeys != null) {
            try {
                for (int i = 0; i < masterKeys.size(); ++i) {
                    Object dimValue = masterKeys.getValue(i);
                    if (!(dimValue instanceof List)) continue;
                    List values = (List)dimValue;
                    String dimension = masterKeys.getName(i);
                    cContext.getTempAssistantTable(dimension, values, 6);
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), (Throwable)e);
            }
        }
        cContext.setFormulaParser(this.parser);
        return cContext;
    }

    protected void createTempAssistantTable(int dataType, String dimension, List<?> filterValues, QueryContext qContext) throws Exception {
    }

    private AnalysisRegion getRegion(AnalysisContext aContext, FloatRegionConfig regionConfig, String regionKey) {
        AnalysisRegion region;
        if (StringUtils.isEmpty((String)regionKey)) {
            regionKey = FIX_REGION_KEY;
        }
        if ((region = this.regions.get(regionKey)) == null) {
            region = new AnalysisRegion();
            if (regionConfig != null) {
                ParsedFloatRegionConfig paredConfig = this.parseFloatConfig(aContext, regionConfig);
                region.setConfig(paredConfig);
            }
            this.regions.put(regionKey, region);
        }
        return region;
    }

    private ParsedFloatRegionConfig parseFloatConfig(AnalysisContext aContext, FloatRegionConfig config) {
        GroupingConfig groupingConfig;
        ParsedFloatRegionConfig parsedConfig = new ParsedFloatRegionConfig(config);
        String srcMainDimFilter = config.getSrcMainDimFilter();
        if (StringUtils.isNotEmpty((String)srcMainDimFilter)) {
            IExpression condition = this.parseExpression(aContext, srcMainDimFilter);
            parsedConfig.setSrcMainDimCondition(this.parseConditionAsCheck(aContext, condition, srcMainDimFilter, "SrcMainDimFilter"));
        }
        if (StringUtils.isNotEmpty((String)config.getRowFilter())) {
            parsedConfig.setRowCondition(this.parseExpression(aContext, config.getRowFilter()));
        }
        if ((groupingConfig = config.getGroupingConfig()) != null) {
            this.parseGroupingConfig(aContext, parsedConfig, groupingConfig);
        }
        if (config.getOrderFields().size() > 0) {
            this.parseOrderFields(aContext, config, parsedConfig);
        }
        return parsedConfig;
    }

    private void parseOrderFields(AnalysisContext aContext, FloatRegionConfig config, ParsedFloatRegionConfig parsedConfig) {
        for (int i = 0; i < config.getOrderFields().size(); ++i) {
            OrderField orderField = config.getOrderFields().get(i);
            IExpression exp = this.parseExpression(aContext, orderField.getExpression());
            QueryField field = ExpressionUtils.extractQueryField((IASTNode)exp);
            ParsedOrderField parsedOrderField = new ParsedOrderField(orderField, field);
            parsedConfig.getOrderFields().set(i, parsedOrderField);
        }
    }

    private void parseGroupingConfig(AnalysisContext aContext, ParsedFloatRegionConfig parsedConfig, GroupingConfig groupingConfig) {
        IExpression exp;
        String[] strs;
        ParsedGroupingConfig parsedGroupingConfig = new ParsedGroupingConfig(groupingConfig);
        if (StringUtils.isNotEmpty((String)groupingConfig.getGroupingKey())) {
            for (String str : strs = groupingConfig.getGroupingKey().split(";|,")) {
                try {
                    if (str.startsWith("[") && str.endsWith("]")) {
                        str = str.substring(1, str.length() - 1);
                    }
                    exp = this.parseExpression(aContext, str);
                    parsedGroupingConfig.getGrouppingFieldExpressions().add(exp);
                    QueryField queryField = ExpressionUtils.extractQueryField((IASTNode)exp);
                    parsedGroupingConfig.getGroupingQueryFields().add(queryField);
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), (Throwable)e);
                }
            }
        }
        if (StringUtils.isNotEmpty((String)groupingConfig.getLevelString())) {
            strs = groupingConfig.getLevelString().split(";|,");
            int[] levelLengths = new int[strs.length];
            for (int i = 0; i < strs.length; ++i) {
                int level = Integer.parseInt(strs[i]);
                levelLengths[i] = i == 0 ? level : levelLengths[i - 1] + level;
            }
            parsedGroupingConfig.setLevelLength(levelLengths);
        }
        if (groupingConfig.getGroupingKeyEvalExps().size() > 0) {
            for (String groupExp : groupingConfig.getGroupingKeyEvalExps()) {
                String[] strs2 = groupExp.split("=");
                String destFieldStr = strs2[0];
                QueryField destField = null;
                try {
                    exp = this.parseExpression(aContext, destFieldStr);
                    destField = ExpressionUtils.extractQueryField((IASTNode)exp);
                    String srcExpStr = strs2[1];
                    String[] srcExps = srcExpStr.split(";");
                    if (srcExps.length < parsedGroupingConfig.getLevelLength().length) {
                        String[] newSrcExps = new String[parsedGroupingConfig.getLevelLength().length];
                        for (int i = 0; i < newSrcExps.length; ++i) {
                            int index = i;
                            if (index >= srcExps.length) {
                                index = srcExps.length - 1;
                            }
                            newSrcExps[i] = srcExps[index];
                        }
                        srcExps = newSrcExps;
                    }
                    ArrayList<IExpression> expNodes = new ArrayList<IExpression>(srcExps.length);
                    for (String srcExp : srcExps) {
                        IExpression expNode = this.parseExpression(aContext, srcExp);
                        expNodes.add(expNode);
                    }
                    parsedGroupingConfig.getLevelExpressions().put(destField, expNodes);
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), (Throwable)e);
                }
            }
        }
        parsedConfig.setParsedGroupingConfig(parsedGroupingConfig);
    }

    private CheckExpression parseConditionAsCheck(AnalysisContext aContext, IExpression condition, String formulaExp, String code) {
        Formula formula = new Formula();
        formula.setFormula(formulaExp);
        formula.setReportName(this.reportName);
        formula.setCode(code);
        CheckExpression checkExpression = new CheckExpression(condition, formula);
        return checkExpression;
    }

    private IExpression parseExpression(AnalysisContext aContext, String expression) {
        try {
            IExpression condition = this.parser.parseEval(expression, (IContext)aContext);
            return condition;
        }
        catch (ParseException e) {
            this.monitor.exception((Exception)((Object)new ParseException("\u89e3\u6790\u8868\u8fbe\u5f0f\u51fa\u9519\uff1a" + expression + "\n" + e.getMessage(), (Throwable)e)));
            return null;
        }
    }

    private IASTNode getCondition(IASTNode root, Position pos, Map<Integer, IExpression> rowConditionMap, Map<Integer, IExpression> colConditionMap) {
        IASTNode condition = null;
        if (root instanceof IfThenElse) {
            condition = root.getChild(0);
        }
        if (pos == null) {
            return condition;
        }
        IExpression colondition = colConditionMap.get(pos.col());
        condition = this.mergeCondition(condition, colondition);
        IExpression rowCondition = rowConditionMap.get(pos.row());
        condition = this.mergeCondition(condition, rowCondition);
        return condition;
    }

    private IASTNode mergeCondition(IASTNode condition, IExpression newCondition) {
        if (newCondition != null) {
            condition = condition == null ? newCondition.getChild(0) : new And(null, newCondition.getChild(0), condition);
        }
        return condition;
    }

    private Map<Integer, IExpression> getConditionMap(AnalysisContext aContext, List<AnalysisCaliber> calibers) throws ParseException {
        HashMap<Integer, IExpression> conditionMap = new HashMap<Integer, IExpression>();
        for (AnalysisCaliber caliber : calibers) {
            IExpression rowCondition = this.parseExpression(aContext, caliber.getCondition());
            if (rowCondition == null) continue;
            conditionMap.put(caliber.getIndex(), rowCondition);
        }
        return conditionMap;
    }

    private void executeCalc(DimensionValueSet destDimension, AbstractMonitor monitor) throws Exception {
        if (this.calcExpressions.size() > 0) {
            QueryContext qContext = this.getQueryContext(this.context, destDimension, (IMonitor)monitor);
            FmlExecRegionCreator regionCreator = new FmlExecRegionCreator(qContext, true, this.queryParam);
            CalcExprExecNetwork execNetwork = new CalcExprExecNetwork(qContext, (ExprExecRegionCreator)regionCreator);
            for (CalcExpression calcExpression : this.calcExpressions) {
                try {
                    execNetwork.arrangeCalcExpression((IExpression)calcExpression);
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), (Throwable)e);
                }
            }
            execNetwork.initialize((Object)monitor);
            execNetwork.checkRunTask((Object)monitor);
            execNetwork.tryReCalc((Object)monitor);
            qContext.dropInternalTempTables(null);
        }
    }

    private QueryContext getQueryContext(ExecutorContext context, DimensionValueSet masterKeyValues, IMonitor monitor) throws ParseException {
        QueryContext qContext = new QueryContext(context, this.queryParam, monitor);
        DimensionValueSet masterKeys = new DimensionValueSet(masterKeyValues);
        if (!masterKeys.hasValue("VERSIONID")) {
            masterKeys.setValue("VERSIONID", (Object)"00000000-0000-0000-0000-000000000000");
        }
        qContext.setMasterKeys(masterKeys);
        return qContext;
    }

    private void printParsedInfo(AnalysisContext aContext) throws InterpretException {
        if (DataEngineConsts.DATA_ENGINE_DEBUG) {
            FormulaShowInfo showInfo = new FormulaShowInfo(DataEngineConsts.FormulaShowType.JQ);
            StringBuilder buff = new StringBuilder("\u89e3\u6790\u540e\u5206\u6790\u8868\u914d\u7f6e\uff1a\n");
            for (String regionKey : this.regions.keySet()) {
                buff.append("region_").append(regionKey).append("[\n");
                AnalysisRegion region = this.regions.get(regionKey);
                buff.append("\u53d6\u6570\u516c\u5f0f[\n");
                for (IExpression iExpression : region.getExpressions()) {
                    buff.append(iExpression.interpret((IContext)aContext, Language.FORMULA, (Object)showInfo)).append("\n");
                }
                buff.append("]\n");
                if (this.calcExpressions.size() > 0) {
                    buff.append("\u8fd0\u7b97\u516c\u5f0f[\n");
                    for (IExpression iExpression : this.calcExpressions) {
                        buff.append(iExpression.interpret((IContext)aContext, Language.FORMULA, (Object)showInfo)).append("\n");
                    }
                    buff.append("]\n");
                }
                if (region.getConfig() != null) {
                    buff.append("\u6d6e\u52a8\u8bbe\u7f6e\uff1a").append(region.getConfig()).append("\n");
                }
                if (this.globalCondition != null) {
                    buff.append("\u5168\u5c40\u8fc7\u6ee4\u6761\u4ef6\uff1a").append(this.globalCondition.interpret((IContext)aContext, Language.FORMULA, (Object)showInfo)).append("\n");
                }
                buff.append("]\n");
            }
            this.monitor.debug(buff.toString(), DataEngineConsts.DebugLogType.COMMON);
        }
    }

    public void setQueryParam(QueryParam queryParam) {
        this.queryParam = queryParam;
    }
}

