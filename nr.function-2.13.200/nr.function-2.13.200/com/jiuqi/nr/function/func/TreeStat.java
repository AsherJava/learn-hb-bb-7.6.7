/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaShowType
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.executors.StatItem
 *  com.jiuqi.np.dataengine.executors.StatUnit
 *  com.jiuqi.np.dataengine.intf.IExpressionEvaluator
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IMainDimFilter
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.node.FormulaShowInfo
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 */
package com.jiuqi.nr.function.func;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.executors.StatItem;
import com.jiuqi.np.dataengine.executors.StatUnit;
import com.jiuqi.np.dataengine.intf.IExpressionEvaluator;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IMainDimFilter;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.node.FormulaShowInfo;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.function.func.cache.UnitTreeCacheInfo;
import com.jiuqi.nr.function.util.FuncExpressionParseUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TreeStat
extends Function
implements IReportFunction {
    private static final long serialVersionUID = 3545753495118294621L;

    public TreeStat() {
        this.parameters().add(new Parameter("Expression", 0, "\u6c42\u503c\u8868\u8fbe\u5f0f"));
        this.parameters().add(new Parameter("Condition", 6, "\u7edf\u8ba1\u6761\u4ef6"));
        this.parameters().add(new Parameter("Mode", 6, "\u7edf\u8ba1\u65b9\u5f0f"));
        this.parameters().add(new Parameter("unitCode", 6, "\u5355\u4f4d\u4ee3\u7801"));
        this.parameters().add(new Parameter("StatLevel", 3, "\u5b50\u8282\u70b9\u7edf\u8ba1\u7ea7\u6b21"));
    }

    public String name() {
        return "TreeStat";
    }

    public String title() {
        return "\u5bf9\u6307\u5b9a\u7ea7\u6b21\u5b50\u8282\u70b9\u4e2d\u6ee1\u8db3\u6761\u4ef6\u7684\u5355\u4f4d\u7684\u6c42\u503c\u8868\u8fbe\u5f0f\u8fdb\u884c\u7edf\u8ba1,\u53ea\u7edf\u8ba1\u975e\u7a7a\u503c";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 0;
    }

    public String category() {
        return "\u5176\u5b83\u51fd\u6570";
    }

    public int validate(IContext context, List<IASTNode> parameters) throws SyntaxException {
        QueryContext qContext = (QueryContext)context;
        try {
            String exp = FuncExpressionParseUtil.getEvalExp(context, parameters.get(0));
            String condition = (String)parameters.get(1).evaluate(context);
            int level = ((Number)parameters.get(4).evaluate(context)).intValue();
            String mode = (String)parameters.get(2).evaluate(context);
            StatItem.parseStatKind((String)mode);
            if (level < 0) {
                throw new SyntaxException("\u5b50\u8282\u70b9\u7edf\u8ba1\u7ea7\u6b21\u4e0d\u80fd\u4e3a\u8d1f\u6570:" + level);
            }
            ReportFormulaParser formulaParser = qContext.getExeContext().getCache().getFormulaParser(true);
            formulaParser.parseEval(exp, (IContext)qContext);
            if (StringUtils.isNotEmpty((String)condition)) {
                formulaParser.parseCond(condition, (IContext)qContext);
            }
        }
        catch (Exception e) {
            throw new SyntaxException("\u51fd\u6570" + this.name() + "\u53c2\u6570\u6821\u9a8c\u51fa\u9519\uff1a" + e.getMessage(), (Throwable)e);
        }
        return this.getResultType((IContext)qContext, parameters);
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        if (context instanceof QueryContext) {
            QueryContext qContext = (QueryContext)context;
            IMonitor monitor = qContext.getMonitor();
            try {
                String exp = FuncExpressionParseUtil.getEvalExp(context, parameters.get(0));
                String condition = (String)parameters.get(1).evaluate(context);
                String mode = (String)parameters.get(2).evaluate(context);
                int statKind = StatItem.parseStatKind((String)mode);
                IASTNode unitParam = parameters.get(3);
                String unitKey = (String)unitParam.evaluate(context);
                int level = ((Number)parameters.get(4).evaluate(context)).intValue();
                if (level < 0) {
                    throw new SyntaxException("\u5b50\u8282\u70b9\u7edf\u8ba1\u7ea7\u6b21\u4e0d\u80fd\u4e3a\u8d1f\u6570:" + level);
                }
                if (monitor != null && monitor.isDebug()) {
                    monitor.message("\u7236\u7ea7\u5355\u4f4d\uff1a" + unitKey, (Object)this);
                    monitor.message("\u7edf\u8ba1\u7ea7\u6b21\uff1a" + (level > 0 ? level + "\u7ea7" : "\u6240\u6709\u4e0b\u7ea7"), (Object)this);
                    monitor.message("\u7edf\u8ba1\u65b9\u5f0f\uff1a" + StatItem.STAT_KIND_MODE_TITLES[statKind], (Object)this);
                    if (StringUtils.isNotEmpty((String)condition)) {
                        IExpression conditionNode = qContext.getFormulaParser().parseCond(condition, (IContext)qContext);
                        FormulaShowInfo showInfo = new FormulaShowInfo(DataEngineConsts.FormulaShowType.DATA);
                        monitor.message("\u7edf\u8ba1\u6761\u4ef6\uff1a" + conditionNode.interpret((IContext)qContext, Language.EXPLAIN, (Object)showInfo), (Object)this);
                    }
                    monitor.message("\u6c42\u503c\u8868\u8fbe\u5f0f\uff1a" + exp, (Object)this);
                }
                ExecutorContext exeContext = new ExecutorContext(qContext.getExeContext().getRuntimeController());
                ReportFmlExecEnvironment destEnv = (ReportFmlExecEnvironment)qContext.getExeContext().getEnv();
                exeContext.setEnv((IFmlExecEnvironment)new ReportFmlExecEnvironment(destEnv.getController(), destEnv.getRuntimeController(), destEnv.getEntityViewRunTimeController(), destEnv.getFormSchemeKey()));
                String unitDim = exeContext.getEnv().getUnitDimesion(exeContext);
                List<IEntityRow> levelUnits = this.getLevelUnits(qContext, unitKey, unitParam, level);
                List units = new ArrayList<String>(levelUnits.size());
                for (int i = 0; i < levelUnits.size(); ++i) {
                    units.add(levelUnits.get(i).getEntityKeyData());
                }
                IDataAccessProvider dataAccessProvider = (IDataAccessProvider)SpringBeanProvider.getBean(IDataAccessProvider.class);
                DimensionValueSet dimValues = new DimensionValueSet(qContext.getCurrentMasterKey());
                dimValues.setValue(unitDim, units);
                if (StringUtils.isNotEmpty((String)condition)) {
                    IMainDimFilter filter = dataAccessProvider.newMainDimFilter();
                    units = filter.filterByCondition(exeContext, dimValues, condition);
                    dimValues.setValue(unitDim, units);
                }
                if (monitor != null && monitor.isDebug()) {
                    monitor.message("\u53c2\u4e0e\u7edf\u8ba1\u7684\u4e0b\u7ea7\u5355\u4f4d\u4e2a\u6570\u4e3a\uff1a" + units.size(), (Object)this);
                }
                IExpressionEvaluator evaluator = dataAccessProvider.newExpressionEvaluator();
                exeContext.setDefaultGroupName(qContext.getDefaultGroupName());
                Map result = evaluator.evalBatch(Collections.singletonList(exp), exeContext, dimValues);
                int dataType = exeContext.getCache().getFormulaParser(exeContext).parseEval(exp, (IContext)qContext).getType((IContext)qContext);
                StatUnit statUnit = StatItem.createStatUnit((int)statKind, (int)dataType);
                for (Object[] values : result.values()) {
                    if (values == null || values.length != 1 || values[0] == null) continue;
                    statUnit.statistic(AbstractData.valueOf((Object)values[0], (int)dataType));
                }
                return statUnit.getResult().getAsObject();
            }
            catch (Exception e) {
                throw new SyntaxException(e.getMessage(), (Throwable)e);
            }
        }
        return null;
    }

    private List<IEntityRow> getLevelUnits(QueryContext qContext, String unitKey, IASTNode unitParam, int level) throws Exception {
        IEntityTable entityTable = this.getDimEntityTable(qContext, unitParam);
        IEntityRow parent = entityTable.findByCode(unitKey);
        String parentEntityKey = parent.getEntityKeyData();
        ArrayList<IEntityRow> childs = new ArrayList<IEntityRow>();
        if (level == 0) {
            childs.addAll(entityTable.getAllChildRows(parentEntityKey));
        } else if (level == 1) {
            childs.addAll(entityTable.getChildRows(parentEntityKey));
        } else if (level > 1) {
            this.getChildRowsByLevel(childs, parentEntityKey, entityTable, 1, level);
        }
        return childs;
    }

    private IEntityTable getDimEntityTable(QueryContext qContext, IASTNode unitParam) throws Exception {
        String cacheKey = "UnitTreeCacheInfo";
        UnitTreeCacheInfo cacheInfo = (UnitTreeCacheInfo)qContext.getCache().get(cacheKey);
        if (cacheInfo == null) {
            cacheInfo = new UnitTreeCacheInfo();
            cacheInfo.doInit(qContext, unitParam, null, cacheKey, false);
        }
        return cacheInfo.getDimTable();
    }

    public void getChildRowsByLevel(List<IEntityRow> resultRows, String parentEntityKey, IEntityTable entityTable, int currentLevel, int maxLevel) {
        if (currentLevel > maxLevel) {
            return;
        }
        List childrens = entityTable.getChildRows(parentEntityKey);
        if (childrens != null) {
            resultRows.addAll(childrens);
        }
        ++currentLevel;
        for (IEntityRow childRow : childrens) {
            this.getChildRowsByLevel(resultRows, childRow.getEntityKeyData(), entityTable, currentLevel, maxLevel);
        }
    }
}

