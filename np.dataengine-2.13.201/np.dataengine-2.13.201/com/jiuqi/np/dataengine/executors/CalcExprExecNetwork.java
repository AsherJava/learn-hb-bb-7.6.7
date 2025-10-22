/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IExpression
 */
package com.jiuqi.np.dataengine.executors;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DataRegion;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.exception.ExecuteException;
import com.jiuqi.np.dataengine.exception.ExpressionException;
import com.jiuqi.np.dataengine.executors.CalcExecutor;
import com.jiuqi.np.dataengine.executors.ExecutorBase;
import com.jiuqi.np.dataengine.executors.ExecutorCenter;
import com.jiuqi.np.dataengine.executors.ExprExecCenter;
import com.jiuqi.np.dataengine.executors.ExprExecNetwork;
import com.jiuqi.np.dataengine.executors.ExprExecRegion;
import com.jiuqi.np.dataengine.executors.ExprExecRegionCreator;
import com.jiuqi.np.dataengine.executors.SortExecRegionItem;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.np.dataengine.node.FunctionCalcExpression;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class CalcExprExecNetwork
extends ExprExecNetwork {
    private List<ExprExecRegion> unSortedRegions = new ArrayList<ExprExecRegion>();
    private final int maxCalcCount = 2;
    private List<ExprExecRegion> mergedRegions = new ArrayList<ExprExecRegion>();

    public CalcExprExecNetwork(QueryContext context, ExprExecRegionCreator exprExecCreator) {
        super(context, exprExecCreator);
    }

    @Override
    protected boolean doInitialization(Object initInfo) throws ExecuteException {
        boolean result = super.doInitialization(initInfo);
        if (this.size() > 1) {
            if (this.context.outFMLPlan()) {
                this.context.getMonitor().message("\u5f00\u59cb\u5bf9\u8fd0\u7b97\u6267\u884c\u57df\u8fdb\u884c\u6392\u5e8f", this);
            }
            this.sortRegions();
            if (this.context.outFMLPlan()) {
                this.context.getMonitor().message("\u8fd0\u7b97\u6267\u884c\u57df\u6392\u5e8f\u5b8c\u6210", this);
            }
        }
        return result;
    }

    @Override
    public CalcExecutor arrangeCalcExpression(IExpression exprNode) throws ExpressionException, SyntaxException {
        if (exprNode instanceof FunctionCalcExpression) {
            ExprExecCenter execCenter = null;
            FunctionCalcExpression funcExp = (FunctionCalcExpression)exprNode;
            for (int i = 0; i < this.scopeList.size(); ++i) {
                ExprExecRegion region = (ExprExecRegion)this.scopeList.getValue(i);
                if (!region.isFuncCalc() || !region.getFuncWriteTable().equals(funcExp.getWriteTable())) continue;
                execCenter = region;
                break;
            }
            if (execCenter == null) {
                DataRegion dataRegion = funcExp.getWriteQueryFields().getDataRegion();
                dataRegion.setFuncCalcFlag(1);
                execCenter = this.createExecCenter(dataRegion);
                ((ExprExecRegion)execCenter).setFuncCalc(true);
                this.add(execCenter);
                this.scopeList.addlast((Object)dataRegion, (Object)execCenter);
            }
            this.addToCalcExetutor(funcExp, (ExprExecRegion)execCenter);
            return execCenter.getCalcExecutor();
        }
        return super.arrangeCalcExpression(exprNode);
    }

    protected void sortRegions() {
        int count = this.size();
        ArrayList<SortExecRegionItem> allForSort = new ArrayList<SortExecRegionItem>();
        for (int index = 0; index < count; ++index) {
            ExprExecRegion execRegion = this.getCenter(index);
            CalcExecutor calcExecutor = execRegion.findCalcExecutor();
            SortExecRegionItem item = new SortExecRegionItem(index, execRegion);
            allForSort.add(item);
            execRegion.regionReads = new HashSet<QueryField>();
            execRegion.regionWrites = new HashSet<QueryField>();
            execRegion.regionTableReads = new HashSet<QueryTable>();
            execRegion.regionTableWrites = new HashSet<QueryTable>();
            if (calcExecutor != null) {
                calcExecutor.getReadQueryFields(execRegion.regionReads, execRegion.regionTableReads);
                calcExecutor.getWriteQueryFields(execRegion.regionWrites, execRegion.regionTableWrites);
            }
            for (QueryFields queryFields : execRegion.queryRegion.getAllTableFields().values()) {
                for (QueryField queryField : queryFields) {
                    execRegion.regionReads.add(queryField);
                    execRegion.regionTableReads.add(queryField.getTable());
                }
            }
        }
        ArrayList<ExprExecRegion> sortedRegions = new ArrayList<ExprExecRegion>(count);
        this.sort(allForSort, sortedRegions);
        for (ExecutorCenter executorCenter : this.mergedRegions) {
            this.remove(executorCenter);
        }
        for (ExecutorCenter executorCenter : sortedRegions) {
            this.remove(executorCenter);
            this.add(executorCenter);
        }
        for (ExecutorCenter executorCenter : this.unSortedRegions) {
            this.remove(executorCenter);
            this.add(executorCenter);
        }
        this.sorted = true;
    }

    protected void sort(List<SortExecRegionItem> allForSort, List<ExprExecRegion> sortedRegions) {
        this.buildTopology(allForSort);
        while (!allForSort.isEmpty()) {
            boolean found = this.scanItems(sortedRegions, allForSort);
            if (found) continue;
            StringBuilder buff = new StringBuilder("\u672a\u5b8c\u6210\u6392\u5e8f\u7684\u533a\u57df");
            this.printItems(buff, allForSort);
            this.context.getMonitor().debug(buff.toString(), DataEngineConsts.DebugLogType.COMMON);
            Stack<SortExecRegionItem> cycle = this.findCycle(allForSort);
            if (cycle != null) {
                buff = new StringBuilder("\u627e\u5230\u6267\u884c\u57df\u4f9d\u8d56\u73af");
                this.printItems(buff, cycle);
                this.context.getMonitor().debug(buff.toString(), DataEngineConsts.DebugLogType.COMMON);
                SortExecRegionItem combinedItem = this.combineCycles(cycle);
                if (combinedItem != null) {
                    for (SortExecRegionItem item : cycle) {
                        this.mergedRegions.add(item.getRegion());
                        allForSort.remove(item);
                    }
                    allForSort.add(combinedItem);
                    this.buildTopology(allForSort);
                    this.context.getMonitor().debug("\u5408\u5e76\u6210\u529f,\u5408\u5e76\u540e\u7684\u6267\u884c\u57df:" + combinedItem, DataEngineConsts.DebugLogType.COMMON);
                    continue;
                }
                buff = new StringBuilder("\u5408\u5e76\u4e0d\u6210\u529f\uff0c\u4ee5\u4e0b\u533a\u57df\u672a\u80fd\u6210\u529f\u6392\u5e8f\uff0c\u79fb\u9664\u5230\u672a\u6392\u5e8f\u6267\u884c\u57df\u5217\u8868\uff1a");
                this.printItems(buff, allForSort);
                this.context.getMonitor().debug(buff.toString(), DataEngineConsts.DebugLogType.COMMON);
                for (int i = allForSort.size() - 1; i >= 0; --i) {
                    SortExecRegionItem item;
                    item = allForSort.get(i);
                    this.unSortedRegions.add(item.getRegion());
                    allForSort.remove(i);
                }
                continue;
            }
            buff = new StringBuilder("\u672a\u627e\u5230\u4f9d\u8d56\u73af\uff0c\u4ee5\u4e0b\u533a\u57df\u672a\u80fd\u6210\u529f\u6392\u5e8f\uff0c\u79fb\u9664\u5230\u672a\u6392\u5e8f\u6267\u884c\u57df\u5217\u8868\uff1a");
            this.printItems(buff, allForSort);
            this.context.getMonitor().debug(buff.toString(), DataEngineConsts.DebugLogType.COMMON);
            for (int i = allForSort.size() - 1; i >= 0; --i) {
                SortExecRegionItem item = allForSort.get(i);
                this.unSortedRegions.add(item.getRegion());
                allForSort.remove(i);
            }
        }
    }

    protected void buildTopology(List<SortExecRegionItem> allForSort) {
        for (SortExecRegionItem item : allForSort) {
            item.reset();
        }
        for (SortExecRegionItem item : allForSort) {
            ExprExecRegion region = item.getRegion();
            if (region.regionWrites == null) continue;
            for (SortExecRegionItem other : allForSort) {
                ArrayList otherPrecursors;
                Set<QueryTable> writeTables;
                Object otherReadTables;
                boolean isContains;
                CalcExecutor regionCalcExecutor;
                if (other == item || other.equals(item) || other.getRegion().regionReads == null || (regionCalcExecutor = region.findCalcExecutor()) != null && !regionCalcExecutor.hasExtendAssignKey() && !(isContains = this.checkIntersection((Set<QueryTable>)(otherReadTables = other.getRegion().regionTableReads), writeTables = region.regionTableWrites))) continue;
                otherReadTables = region.regionWrites.iterator();
                while (otherReadTables.hasNext()) {
                    QueryField writeQueryField = (QueryField)otherReadTables.next();
                    if (!other.getRegion().regionReads.contains(writeQueryField)) continue;
                    item.getNexts().add(other);
                    ++other.tag;
                    break;
                }
                if ((otherPrecursors = other.getRegion().precursors) == null || otherPrecursors.indexOf(region) < 0) continue;
                item.getNexts().add(other);
                ++other.tag;
            }
        }
    }

    protected boolean checkIntersection(Set<QueryTable> readTables, Set<QueryTable> writeTables) {
        boolean isContains = false;
        for (QueryTable queryTable : writeTables) {
            if (!readTables.contains(queryTable)) continue;
            return true;
        }
        return isContains;
    }

    protected boolean scanItems(List<ExprExecRegion> sortedRegions, Collection<SortExecRegionItem> allItemsForSort) {
        boolean found = false;
        Iterator<SortExecRegionItem> i = allItemsForSort.iterator();
        while (i.hasNext()) {
            SortExecRegionItem curItem = i.next();
            if (curItem.tag > 0) continue;
            sortedRegions.add(curItem.getRegion());
            i.remove();
            for (SortExecRegionItem next : curItem.getNexts()) {
                --next.tag;
            }
            found = true;
        }
        return found;
    }

    protected Stack<SortExecRegionItem> findCycle(Collection<SortExecRegionItem> allItemsForSort) {
        Stack<SortExecRegionItem> route = new Stack<SortExecRegionItem>();
        for (SortExecRegionItem start : allItemsForSort) {
            boolean findCycle = this.tryFindCycle(route, start);
            if (!findCycle) continue;
            return route;
        }
        return null;
    }

    protected boolean tryFindCycle(Stack<SortExecRegionItem> route, SortExecRegionItem current) {
        if (current.tag <= 0) {
            return false;
        }
        if (route.contains(current)) {
            return true;
        }
        route.push(current);
        for (SortExecRegionItem next : current.getNexts()) {
            boolean findCycle = this.tryFindCycle(route, next);
            if (!findCycle) continue;
            return true;
        }
        route.pop();
        return false;
    }

    public SortExecRegionItem combineCycles(Stack<SortExecRegionItem> cycle) {
        ExprExecRegion region;
        SortExecRegionItem first = null;
        for (SortExecRegionItem item : cycle) {
            region = item.getRegion();
            if (first == null) {
                first = item;
            }
            if (first.getRegion().queryRegion.getDimensions().compareTo(region.queryRegion.getDimensions()) != 0 || first.getRegion().getUseStatResult() != region.getUseStatResult()) {
                return null;
            }
            if (first.getRegion().hasStatExecutor() != region.hasStatExecutor()) {
                return null;
            }
            if (first.getRegion().isFuncCalc() && !region.isFuncCalc() || !first.getRegion().isFuncCalc() && region.isFuncCalc()) {
                return null;
            }
            if (!first.getRegion().queryRegion.hasMultiFloat(this.context) && !region.queryRegion.hasMultiFloat(this.context)) continue;
            return null;
        }
        for (SortExecRegionItem item : cycle) {
            region = item.getRegion();
            if (item == first) continue;
            first.getRegion().combine(region);
            for (int c = 0; c < this.executors.size(); ++c) {
                ExecutorBase executor = (ExecutorBase)this.executors.get(c);
                executor.replacePrecursor(region, first.getRegion());
            }
        }
        return first;
    }

    protected void printItems(StringBuilder buff, Collection<SortExecRegionItem> route) {
        buff.append("[");
        boolean started = false;
        for (SortExecRegionItem item : route) {
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

    public void tryReCalc(Object taskInfo) throws Exception {
        if (this.unSortedRegions.size() > 0) {
            AbstractMonitor monitor = (AbstractMonitor)this.context.getMonitor();
            if (this.context.outFMLPlan()) {
                StringBuilder msg = new StringBuilder();
                msg.append("\u4ee5\u4e0b\u6267\u884c\u57df\u5b58\u5728\u5faa\u73af\u4f9d\u8d56\uff0c\u5c06\u7b97\u591a\u6b21:\n");
                for (ExprExecRegion region : this.unSortedRegions) {
                    msg.append(region.getFeature() + ":" + region.queryRegion).append("\n");
                }
                monitor.message(msg.toString(), this);
            }
            monitor.setStep(0.0);
            for (int i = 0; i < 2; ++i) {
                for (ExprExecRegion region : this.unSortedRegions) {
                    region.state = 2;
                    region.prepare(taskInfo);
                    region.execute(taskInfo);
                }
            }
        }
    }
}

