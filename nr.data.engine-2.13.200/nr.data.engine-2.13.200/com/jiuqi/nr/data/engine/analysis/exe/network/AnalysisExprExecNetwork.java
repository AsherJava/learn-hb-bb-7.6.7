/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.ASTNode
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.operator.BinaryOperator
 *  com.jiuqi.bi.syntax.operator.IfThenElse
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$DebugLogType
 *  com.jiuqi.np.dataengine.common.DataRegion
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.common.QueryFields
 *  com.jiuqi.np.dataengine.common.QueryTable
 *  com.jiuqi.np.dataengine.definitions.TableRunInfo
 *  com.jiuqi.np.dataengine.exception.ExecuteException
 *  com.jiuqi.np.dataengine.exception.ExpressionException
 *  com.jiuqi.np.dataengine.executors.CalcExecutor
 *  com.jiuqi.np.dataengine.executors.CheckExecutor
 *  com.jiuqi.np.dataengine.executors.EvalExecutor
 *  com.jiuqi.np.dataengine.executors.ExecutorBase
 *  com.jiuqi.np.dataengine.executors.ExecutorCenter
 *  com.jiuqi.np.dataengine.executors.StatItem
 *  com.jiuqi.np.dataengine.node.CalcExpression
 *  com.jiuqi.np.dataengine.node.CheckExpression
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.node.ExpressionUtils
 *  com.jiuqi.np.dataengine.node.StatisticInfo
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.util.SearchList
 */
package com.jiuqi.nr.data.engine.analysis.exe.network;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.ASTNode;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.operator.BinaryOperator;
import com.jiuqi.bi.syntax.operator.IfThenElse;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DataRegion;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.definitions.TableRunInfo;
import com.jiuqi.np.dataengine.exception.ExecuteException;
import com.jiuqi.np.dataengine.exception.ExpressionException;
import com.jiuqi.np.dataengine.executors.CalcExecutor;
import com.jiuqi.np.dataengine.executors.CheckExecutor;
import com.jiuqi.np.dataengine.executors.EvalExecutor;
import com.jiuqi.np.dataengine.executors.ExecutorBase;
import com.jiuqi.np.dataengine.executors.ExecutorCenter;
import com.jiuqi.np.dataengine.executors.StatItem;
import com.jiuqi.np.dataengine.node.CalcExpression;
import com.jiuqi.np.dataengine.node.CheckExpression;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.ExpressionUtils;
import com.jiuqi.np.dataengine.node.StatisticInfo;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.util.SearchList;
import com.jiuqi.nr.data.engine.analysis.exe.AnalysisContext;
import com.jiuqi.nr.data.engine.analysis.exe.network.AnalysisExecRegionCreator;
import com.jiuqi.nr.data.engine.analysis.exe.network.AnalysisExprExecRegion;
import com.jiuqi.nr.data.engine.analysis.exe.network.AnalysisSortExecRegionItem;
import com.jiuqi.nr.data.engine.analysis.parse.AnalysisDynamicDataNode;
import com.jiuqi.nr.data.engine.analysis.parse.AnalysisExpression;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class AnalysisExprExecNetwork
extends ExecutorCenter {
    private SearchList scopeList = new SearchList();
    private AnalysisExecRegionCreator exprExecCreator;
    public DimensionSet masterDimensions;
    private List<AnalysisExprExecRegion> unSortedRegions = new ArrayList<AnalysisExprExecRegion>();

    public AnalysisExprExecNetwork(QueryContext context, AnalysisExecRegionCreator exprExecCreator) {
        super(context);
        this.exprExecCreator = exprExecCreator;
    }

    protected boolean doInitialization(Object initInfo) throws ExecuteException {
        boolean result = super.doInitialization(initInfo);
        if (this.size() > 1) {
            this.combineExecCenters();
            this.sort();
        }
        return result;
    }

    private void sort() {
        int count = this.size();
        ArrayList<AnalysisExprExecRegion> calcRegions = new ArrayList<AnalysisExprExecRegion>();
        ArrayList<AnalysisExprExecRegion> otherRegions = new ArrayList<AnalysisExprExecRegion>();
        for (int index = 0; index < count; ++index) {
            AnalysisExprExecRegion execRegion = this.getCenter(index);
            CalcExecutor calcExecutor = execRegion.findCalcExecutor();
            if (calcExecutor == null || calcExecutor.size() <= 0) {
                otherRegions.add(execRegion);
                continue;
            }
            EvalExecutor evalExecutor = execRegion.findEvalExecutor();
            if (evalExecutor != null) {
                AnalysisExprExecNetwork.removeLink((ExecutorBase)calcExecutor, (ExecutorBase)evalExecutor);
                AnalysisExprExecNetwork.createLink((ExecutorBase)evalExecutor, (ExecutorBase)calcExecutor);
            }
            calcRegions.add(execRegion);
        }
        this.executors.clear();
        this.executors.addAll(otherRegions);
        this.executors.addAll(calcRegions);
    }

    public int size() {
        return this.executors.size();
    }

    public DataRegion getScope(int index) {
        return (DataRegion)this.scopeList.getKey(index);
    }

    public AnalysisExprExecRegion getCenter(int index) {
        return (AnalysisExprExecRegion)((Object)this.scopeList.getValue(index));
    }

    public AnalysisExprExecRegion setupExepression(QueryFields queryFields, ArrayList<AnalysisExprExecRegion> precursors) {
        AnalysisExprExecRegion result = this.getExecCenter(queryFields, precursors);
        result.queryRegion.addQueryFields(queryFields);
        if (precursors != null && precursors.size() > 0) {
            result.setUseStatResult(true);
            for (int i = 0; i < precursors.size(); ++i) {
                AnalysisExprExecNetwork.createLink((ExecutorBase)((ExecutorBase)precursors.get(i)), (ExecutorBase)result);
            }
        }
        return result;
    }

    public AnalysisExprExecRegion setupExepression(ASTNode node, ArrayList<AnalysisExprExecRegion> precursors) {
        QueryFields queryFields = ExpressionUtils.getQueryFields((IASTNode)node);
        return this.setupExepression(queryFields, precursors);
    }

    private AnalysisExprExecRegion getExecCenter(QueryFields queryFields, ArrayList<AnalysisExprExecRegion> precursors) {
        DataRegion scope;
        int i;
        AnalysisExprExecRegion result = null;
        DataRegion findRegion = queryFields.getDataRegion();
        int index = this.scopeList.findfirstkey((Object)findRegion);
        if (index >= 0) {
            result = (AnalysisExprExecRegion)((Object)this.scopeList.getValue(index));
            if (this.canAccept(result, precursors)) {
                return result;
            }
        } else {
            index = -1 - index;
        }
        int replaceIndex = -1;
        DimensionSet dimensions = findRegion.getDimensions();
        for (i = index; i < this.scopeList.size() && dimensions.compareTo((scope = (DataRegion)this.scopeList.getKey(i)).getDimensions()) == 0; ++i) {
            if (scope.getQueryTables().equals((Object)findRegion.getQueryTables()) && this.canAccept(result = (AnalysisExprExecRegion)((Object)this.scopeList.getValue(i)), precursors)) {
                return result;
            }
            if (!findRegion.getQueryTables().equals((Object)scope.getQueryTables()) || !this.canAccept(result = (AnalysisExprExecRegion)((Object)this.scopeList.getValue(i)), precursors)) continue;
            replaceIndex = i;
            break;
        }
        if (replaceIndex < 0) {
            for (i = index - 1; i >= 0 && dimensions.compareTo((scope = (DataRegion)this.scopeList.getKey(i)).getDimensions()) == 0; --i) {
                if (scope.getQueryTables().equals((Object)findRegion.getQueryTables()) && this.canAccept(result = (AnalysisExprExecRegion)((Object)this.scopeList.getValue(i)), precursors)) {
                    return result;
                }
                if (!findRegion.getQueryTables().equals((Object)scope.getQueryTables()) || !this.canAccept(result = (AnalysisExprExecRegion)((Object)this.scopeList.getValue(i)), precursors)) continue;
                replaceIndex = i;
                break;
            }
        }
        if (replaceIndex < 0) {
            AnalysisExprExecRegion execCenter = this.createExecCenter(findRegion.getDimensions());
            this.add((ExecutorBase)execCenter);
            this.scopeList.addlast((Object)findRegion, (Object)execCenter);
            return execCenter;
        }
        AnalysisExprExecRegion execCenter = (AnalysisExprExecRegion)((Object)this.scopeList.getValue(replaceIndex));
        this.scopeList.remove(replaceIndex);
        this.scopeList.addlast((Object)findRegion, (Object)execCenter);
        return execCenter;
    }

    protected AnalysisExprExecRegion createExecCenter(DimensionSet dimensions) {
        return this.exprExecCreator.createExecRegion(this.masterDimensions, dimensions);
    }

    public boolean canAccept(AnalysisExprExecRegion execCenter, ArrayList<AnalysisExprExecRegion> precursors) {
        if (precursors == null) {
            return true;
        }
        for (int i = 0; i < precursors.size(); ++i) {
            if (execCenter.canAddPrecursor((ExecutorBase)precursors.get(i))) continue;
            return false;
        }
        return true;
    }

    public ArrayList<AnalysisExprExecRegion> createStatItems(IASTNode exprNode) throws SyntaxException {
        ArrayList<AnalysisExprExecRegion> result = new ArrayList<AnalysisExprExecRegion>();
        this.createStatItems(exprNode, result);
        return result;
    }

    private void createStatItems(IASTNode node, ArrayList<AnalysisExprExecRegion> exprStatInfo) throws SyntaxException {
        int count = node.childrenSize();
        for (int i = 0; i < count; ++i) {
            DynamicDataNode dataNode;
            IASTNode child = node.getChild(i);
            if (child instanceof DynamicDataNode && (dataNode = (DynamicDataNode)child).getStatisticInfo() != null) {
                exprStatInfo.add(this.arrangeStatItem(dataNode.getStatisticInfo()));
            }
            this.createStatItems(child, exprStatInfo);
        }
    }

    private AnalysisExprExecRegion arrangeStatItem(StatisticInfo statInfo) throws SyntaxException {
        StatItem statItem = this.context.getStatItemCollection().creatStatItem(this.context, statInfo);
        QueryFields queryFields = statInfo.getQueryFields();
        AnalysisExprExecRegion statCenter = this.setupExepression(queryFields, this.createStatItems((IASTNode)statInfo));
        statCenter.queryRegion.getSqlBuilder().setPrimaryTableName(queryFields.getItem(0).getTableName());
        int index = statCenter.getEvalExecutor().add((IASTNode)statInfo.valueNode);
        statItem.setValue(statCenter.getEvalExecutor(), index);
        if (statInfo.condNode != null) {
            index = statCenter.getEvalExecutor().add(statInfo.condNode);
            statItem.setCondition(statCenter.getEvalExecutor(), index);
        }
        statCenter.getStatExecutor().add(statItem);
        return statCenter;
    }

    public EvalExecutor arrangeEvalExpression(IExpression exprNode) throws ExpressionException, SyntaxException {
        AnalysisExprExecRegion execCenter = this.setupExepression(ExpressionUtils.getQueryFields((IASTNode)exprNode), this.createStatItems((IASTNode)exprNode));
        execCenter.getEvalExecutor().add((IASTNode)exprNode);
        return execCenter.getEvalExecutor();
    }

    public EvalExecutor arrangeCondExpression(IExpression exprNode) throws ExpressionException, SyntaxException {
        AnalysisExprExecRegion execCenter = this.setupExepression(ExpressionUtils.getQueryFields((IASTNode)exprNode), this.createStatItems((IASTNode)exprNode));
        execCenter.getEvalExecutor().add((IASTNode)exprNode);
        return execCenter.getEvalExecutor();
    }

    public CalcExecutor arrangeCalcExpression(IExpression exprNode) throws ExpressionException, SyntaxException {
        AnalysisExprExecRegion execCenter = this.setupExepression(ExpressionUtils.getQueryFields((QueryContext)this.context, (IASTNode)exprNode), this.createStatItems((IASTNode)exprNode));
        execCenter.getCalcExecutor().add((CalcExpression)((AnalysisExpression)exprNode));
        if (exprNode instanceof AnalysisExpression) {
            AnalysisContext aContext = (AnalysisContext)this.context;
            AnalysisExpression calcExpression = (AnalysisExpression)exprNode;
            if (calcExpression.getAssignNode() != null) {
                AnalysisDynamicDataNode dataNode = (AnalysisDynamicDataNode)calcExpression.getAssignNode();
                if (dataNode.isDest()) {
                    aContext.addDestTable(dataNode.getQueryField().getTable());
                }
                dataNode.bindUpdateColumn(aContext);
            }
        }
        return execCenter.getCalcExecutor();
    }

    public CheckExecutor arrangeCheckExpression(IExpression exprNode) throws ExpressionException, SyntaxException {
        CheckExpression CheckExpression2;
        DynamicDataNode leftDataNode;
        QueryFields queryFields = ExpressionUtils.getQueryFields((QueryContext)this.context, (IASTNode)exprNode);
        AnalysisExprExecRegion execCenter = this.setupExepression(queryFields, this.createStatItems((IASTNode)exprNode));
        execCenter.getCheckExecutor().add(exprNode);
        if (exprNode instanceof CheckExpression && (leftDataNode = this.getLeftNode((IASTNode)(CheckExpression2 = (CheckExpression)exprNode).getRealExpression())) != null && leftDataNode.getStatisticInfo() == null) {
            QueryTable table = leftDataNode.getQueryField().getTable();
            TableRunInfo tableInfo = this.context.getExeContext().getCache().getDataDefinitionsCache().getTableInfo(table.getTableName());
            if (tableInfo.getOrderField() != null) {
                execCenter.queryRegion.getSqlBuilder().setPrimaryTable(table);
            }
        }
        return execCenter.getCheckExecutor();
    }

    private DynamicDataNode getLeftNode(IASTNode node) {
        for (IASTNode child : node) {
            if (child instanceof IfThenElse) {
                return this.getLeftNode(child.getChild(1));
            }
            if (!(child instanceof BinaryOperator)) continue;
            IASTNode leftNode = child.getChild(0);
            for (IASTNode leftChild : leftNode) {
                if (!(leftChild instanceof DynamicDataNode)) continue;
                return (DynamicDataNode)leftChild;
            }
        }
        return null;
    }

    private void combineExecCenters() {
        for (int li = this.scopeList.size() - 1; li >= 0; --li) {
            AnalysisExprExecRegion largeCenter = (AnalysisExprExecRegion)((Object)this.scopeList.getValue(li));
            for (int i = li - 1; i >= 0; --i) {
                AnalysisExprExecRegion center = (AnalysisExprExecRegion)((Object)this.scopeList.getValue(i));
                if (largeCenter.findStatExecutor() != null || center.findStatExecutor() != null) continue;
                largeCenter.combine(center);
                for (int c = 0; c < this.executors.size(); ++c) {
                    ExecutorBase executor = (ExecutorBase)this.executors.get(c);
                    executor.replacePrecursor((ExecutorBase)center, (ExecutorBase)largeCenter);
                }
                this.remove((ExecutorBase)center);
                this.scopeList.remove(i);
                --li;
                largeCenter.setMain(true);
            }
        }
    }

    private void sortRegions() {
        int count = this.size();
        ArrayList<AnalysisSortExecRegionItem> allForSort = new ArrayList<AnalysisSortExecRegionItem>();
        for (int index = 0; index < count; ++index) {
            AnalysisExprExecRegion execRegion = this.getCenter(index);
            CalcExecutor calcExecutor = execRegion.findCalcExecutor();
            if (calcExecutor == null || calcExecutor.size() <= 0) continue;
            AnalysisSortExecRegionItem item = new AnalysisSortExecRegionItem(index, execRegion);
            allForSort.add(item);
            execRegion.regionReads = new HashSet<QueryField>();
            calcExecutor.getReadQueryFields(execRegion.regionReads);
            for (QueryFields queryFields : execRegion.queryRegion.getAllTableFields().values()) {
                for (QueryField queryField : queryFields) {
                    execRegion.regionReads.add(queryField);
                }
            }
            execRegion.regionWrites = new HashSet<QueryField>();
            calcExecutor.getWriteQueryFields(execRegion.regionWrites);
        }
        ArrayList<AnalysisExprExecRegion> sortedRegions = new ArrayList<AnalysisExprExecRegion>(count);
        this.sort(allForSort, sortedRegions);
        for (ExecutorCenter executorCenter : sortedRegions) {
            this.remove((ExecutorBase)executorCenter);
            this.add((ExecutorBase)executorCenter);
        }
        for (ExecutorCenter executorCenter : this.unSortedRegions) {
            this.remove((ExecutorBase)executorCenter);
            this.add((ExecutorBase)executorCenter);
        }
    }

    private void sort(List<AnalysisSortExecRegionItem> allForSort, List<AnalysisExprExecRegion> sortedRegions) {
        this.buildTopology(allForSort);
        while (!allForSort.isEmpty()) {
            boolean found = this.scanItems(sortedRegions, allForSort);
            if (found) continue;
            StringBuilder buff = new StringBuilder("\u672a\u5b8c\u6210\u6392\u5e8f\u7684\u533a\u57df");
            this.printItems(buff, allForSort);
            this.context.getMonitor().debug(buff.toString(), DataEngineConsts.DebugLogType.COMMON);
            Stack<AnalysisSortExecRegionItem> cycle = this.findCycle(allForSort);
            if (cycle != null) {
                buff = new StringBuilder("\u627e\u5230\u6267\u884c\u57df\u4f9d\u8d56\u73af");
                this.printItems(buff, cycle);
                this.context.getMonitor().debug(buff.toString(), DataEngineConsts.DebugLogType.COMMON);
                AnalysisSortExecRegionItem combinedItem = this.combineCycles(cycle);
                if (combinedItem != null) {
                    for (AnalysisSortExecRegionItem item : cycle) {
                        allForSort.remove(item);
                    }
                    allForSort.add(combinedItem);
                    this.buildTopology(allForSort);
                    this.context.getMonitor().debug("\u5408\u5e76\u6210\u529f,\u5408\u5e76\u540e\u7684\u6267\u884c\u57df:" + combinedItem, DataEngineConsts.DebugLogType.COMMON);
                    continue;
                }
                buff = new StringBuilder("\u4ee5\u4e0b\u533a\u57df\u672a\u80fd\u6210\u529f\u6392\u5e8f\uff0c\u79fb\u9664\u5230\u672a\u6392\u5e8f\u6267\u884c\u57df\u5217\u8868\uff1a");
                this.printItems(buff, cycle);
                this.context.getMonitor().debug(buff.toString(), DataEngineConsts.DebugLogType.COMMON);
                for (AnalysisSortExecRegionItem item : cycle) {
                    this.remove((ExecutorBase)item.getRegion());
                    this.unSortedRegions.add(item.getRegion());
                    allForSort.remove(item);
                    for (AnalysisSortExecRegionItem forSortItem : allForSort) {
                        forSortItem.getNexts().remove(item);
                    }
                    this.removeNext(allForSort, item);
                }
                this.context.getMonitor().debug("\u5408\u5e76\u5931\u8d25", DataEngineConsts.DebugLogType.COMMON);
                continue;
            }
            buff = new StringBuilder("\u4ee5\u4e0b\u533a\u57df\u672a\u80fd\u6210\u529f\u6392\u5e8f\uff0c\u79fb\u9664\u5230\u672a\u6392\u5e8f\u6267\u884c\u57df\u5217\u8868\uff1a");
            this.printItems(buff, allForSort);
            this.context.getMonitor().debug(buff.toString(), DataEngineConsts.DebugLogType.COMMON);
            for (int i = allForSort.size() - 1; i >= 0; --i) {
                AnalysisSortExecRegionItem item = allForSort.get(i);
                this.remove((ExecutorBase)item.getRegion());
                this.unSortedRegions.add(item.getRegion());
                allForSort.remove(i);
            }
        }
    }

    private void buildTopology(List<AnalysisSortExecRegionItem> allForSort) {
        for (AnalysisSortExecRegionItem item : allForSort) {
            item.reset();
        }
        for (AnalysisSortExecRegionItem item : allForSort) {
            AnalysisExprExecRegion region = item.getRegion();
            if (region.regionWrites == null) continue;
            block2: for (AnalysisSortExecRegionItem other : allForSort) {
                if (other == item || other.equals(item) || other.getRegion().regionReads == null) continue;
                for (QueryField writeQueryField : region.regionWrites) {
                    if (!other.getRegion().regionReads.contains(writeQueryField)) continue;
                    item.getNexts().add(other);
                    ++other.tag;
                    continue block2;
                }
            }
        }
    }

    private boolean scanItems(List<AnalysisExprExecRegion> sortedRegions, Collection<AnalysisSortExecRegionItem> allItemsForSort) {
        boolean found = false;
        Iterator<AnalysisSortExecRegionItem> i = allItemsForSort.iterator();
        while (i.hasNext()) {
            AnalysisSortExecRegionItem curItem = i.next();
            if (curItem.tag > 0) continue;
            sortedRegions.add(curItem.getRegion());
            i.remove();
            for (AnalysisSortExecRegionItem next : curItem.getNexts()) {
                --next.tag;
            }
            found = true;
        }
        return found;
    }

    private Stack<AnalysisSortExecRegionItem> findCycle(Collection<AnalysisSortExecRegionItem> allItemsForSort) {
        Stack<AnalysisSortExecRegionItem> route = new Stack<AnalysisSortExecRegionItem>();
        for (AnalysisSortExecRegionItem start : allItemsForSort) {
            boolean findCycle = this.tryFindCycle(route, start);
            if (!findCycle) continue;
            return route;
        }
        return null;
    }

    private boolean tryFindCycle(Stack<AnalysisSortExecRegionItem> route, AnalysisSortExecRegionItem current) {
        if (current.tag <= 0) {
            return false;
        }
        if (route.contains(current)) {
            return true;
        }
        route.push(current);
        for (AnalysisSortExecRegionItem next : current.getNexts()) {
            boolean findCycle = this.tryFindCycle(route, next);
            if (!findCycle) continue;
            return true;
        }
        route.pop();
        return false;
    }

    public AnalysisSortExecRegionItem combineCycles(Stack<AnalysisSortExecRegionItem> cycle) {
        AnalysisExprExecRegion region;
        AnalysisSortExecRegionItem first = null;
        for (AnalysisSortExecRegionItem item : cycle) {
            region = item.getRegion();
            if (first == null) {
                first = item;
            }
            if (first.getRegion().queryRegion.getDimensions().compareTo(region.queryRegion.getDimensions()) == 0 && first.getRegion().getUseStatResult() == region.getUseStatResult()) continue;
            return null;
        }
        for (AnalysisSortExecRegionItem item : cycle) {
            region = item.getRegion();
            if (item != first) {
                first.getRegion().combine(region);
            }
            for (int c = 0; c < this.executors.size(); ++c) {
                ExecutorBase executor = (ExecutorBase)this.executors.get(c);
                executor.replacePrecursor((ExecutorBase)region, (ExecutorBase)first.getRegion());
            }
        }
        return first;
    }

    private void printItems(StringBuilder buff, Collection<AnalysisSortExecRegionItem> route) {
        buff.append("[");
        boolean started = false;
        for (AnalysisSortExecRegionItem item : route) {
            if (started) {
                if (route instanceof Stack) {
                    buff.append("->");
                } else {
                    buff.append(",");
                }
            } else {
                started = true;
            }
            buff.append(item.toString());
        }
        buff.append(']');
    }

    private void removeNext(List<AnalysisSortExecRegionItem> allForSort, AnalysisSortExecRegionItem item) {
        for (int i = item.getNexts().size() - 1; i >= 0; --i) {
            AnalysisSortExecRegionItem next = item.getNexts().get(i);
            item.getNexts().remove(i);
            this.remove((ExecutorBase)next.getRegion());
            this.unSortedRegions.add(next.getRegion());
            allForSort.remove(next);
            for (AnalysisSortExecRegionItem forSortItem : allForSort) {
                forSortItem.getNexts().remove(next);
            }
            this.removeNext(allForSort, next);
        }
    }
}

