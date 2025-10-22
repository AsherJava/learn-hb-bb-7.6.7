/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.util;

import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.executors.SortCalcItem;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.node.CalcExpression;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class CalcExpressionSortUtil {
    public static void sort(List<CalcExpression> expressions, IMonitor monitor) {
        Collections.sort(expressions);
        Map<QueryField, SortCalcItem> sortExpressions = CalcExpressionSortUtil.initSortExpressions(expressions);
        ArrayList<CalcExpression> sortedExpressions = new ArrayList<CalcExpression>();
        Collection<SortCalcItem> allItemsForSort = sortExpressions.values();
        while (!allItemsForSort.isEmpty()) {
            boolean found = CalcExpressionSortUtil.scanItems(sortedExpressions, allItemsForSort);
            if (found) continue;
            for (SortCalcItem curItem : allItemsForSort) {
                curItem.putToSortedExpressions(sortedExpressions);
            }
            monitor.message("\u7a0b\u5e8f\u5f02\u5e38\uff0c\u65e0\u6cd5\u5904\u7406\u5faa\u73af\u4f9d\u8d56\u7684\u5355\u5143\u683c\u5217\u8868\uff1a" + CalcExpressionSortUtil.toString(allItemsForSort), null);
            break;
        }
        expressions.clear();
        expressions.addAll(sortedExpressions);
    }

    private static Map<QueryField, SortCalcItem> initSortExpressions(List<CalcExpression> expressions) {
        HashMap<QueryField, SortCalcItem> sortExpressions = new HashMap<QueryField, SortCalcItem>();
        for (CalcExpression expression : expressions) {
            QueryField writeQueryField = CalcExpressionSortUtil.getWriteQueryField(expression);
            SortCalcItem item = (SortCalcItem)sortExpressions.get(writeQueryField);
            if (item == null) {
                item = new SortCalcItem();
                item.setWriteQueryField(writeQueryField);
                sortExpressions.put(writeQueryField, item);
            }
            item.addExpression(expression);
            QueryFields queryFields = expression.getQueryFields();
            for (QueryField queryField : queryFields) {
                if (queryField.equals(writeQueryField)) continue;
                item.getReadQueryFields().add(queryField);
            }
            if (expression.getExtendReadKeys() == null || expression.getExtendAssignKey() == null) continue;
            for (String extendReadKey : expression.getExtendReadKeys()) {
                if (extendReadKey == null || extendReadKey.equals(writeQueryField.getFieldName())) continue;
                QueryTable table = new QueryTable("EXT", "EXT");
                QueryField extQueryField = new QueryField(expression.getExtendAssignKey(), expression.getExtendAssignKey(), table);
                item.getReadQueryFields().add(extQueryField);
            }
        }
        for (SortCalcItem item : sortExpressions.values()) {
            for (QueryField readKey : item.getReadQueryFields()) {
                SortCalcItem rely = (SortCalcItem)sortExpressions.get(readKey);
                if (rely == null) continue;
                rely.getNextCalcItems().add(item);
                ++item.tag;
            }
        }
        return sortExpressions;
    }

    public static void analysisCycles(List<CalcExpression> expressions, List<CalcExpression> noCycles, List<CalcExpression> cycles, IMonitor monitor) {
        Map<QueryField, SortCalcItem> sortExpressions = CalcExpressionSortUtil.initSortExpressions(expressions);
        Collection<SortCalcItem> allItemsForSort = sortExpressions.values();
        while (!allItemsForSort.isEmpty()) {
            boolean found = CalcExpressionSortUtil.scanItems(noCycles, allItemsForSort);
            if (found) continue;
            for (SortCalcItem curItem : allItemsForSort) {
                curItem.putToSortedExpressions(cycles);
            }
        }
    }

    public static void analysisCycles_new(QueryContext qContext, List<CalcExpression> expressions, List<CalcExpression> noCycles, List<CalcExpression> cycles) {
        Map<QueryField, SortCalcItem> sortExpressions = CalcExpressionSortUtil.initSortExpressions(expressions);
        Collection<SortCalcItem> allItemsForSort = sortExpressions.values();
        while (!allItemsForSort.isEmpty()) {
            boolean found = CalcExpressionSortUtil.scanItems(noCycles, allItemsForSort);
            if (found) continue;
            Stack<SortCalcItem> cycle = CalcExpressionSortUtil.findCycle(allItemsForSort);
            if (cycle != null) {
                StringBuilder buff = new StringBuilder();
                buff.append("\u53d1\u73b0\u4f9d\u8d56\u73af\uff1a\n");
                for (SortCalcItem item : cycle) {
                    item.printExpressions(qContext, buff);
                }
                qContext.getMonitor().message(buff.toString(), cycle);
                for (SortCalcItem curItem : cycle) {
                    curItem.putToSortedExpressions(cycles);
                    sortExpressions.remove(curItem.getWriteQueryField());
                }
                ArrayList<CalcExpression> exps = new ArrayList<CalcExpression>();
                for (SortCalcItem item : sortExpressions.values()) {
                    item.putToSortedExpressions(exps);
                }
                CalcExpressionSortUtil.analysisCycles_new(qContext, exps, noCycles, cycles);
                break;
            }
            for (SortCalcItem curItem : allItemsForSort) {
                curItem.putToSortedExpressions(cycles);
            }
        }
    }

    public static SortCalcItem findCalcCycle(QueryContext qContext, List<CalcExpression> expressions) {
        Map<QueryField, SortCalcItem> sortExpressions = CalcExpressionSortUtil.initSortExpressions(expressions);
        Collection<SortCalcItem> allItemsForSort = sortExpressions.values();
        while (!allItemsForSort.isEmpty()) {
            boolean found = false;
            Iterator<SortCalcItem> i = allItemsForSort.iterator();
            while (i.hasNext()) {
                SortCalcItem curItem = i.next();
                if (curItem.tag != 0) continue;
                i.remove();
                for (SortCalcItem next : curItem.getNextCalcItems()) {
                    --next.tag;
                }
                found = true;
            }
            if (found) continue;
            Stack<SortCalcItem> cycle = CalcExpressionSortUtil.findCycle(allItemsForSort);
            if (cycle == null) break;
            StringBuilder buff = new StringBuilder();
            buff.append("\u53d1\u73b0\u4f9d\u8d56\u73af\uff1a\n");
            return (SortCalcItem)cycle.firstElement();
        }
        return null;
    }

    public static QueryField getWriteQueryField(CalcExpression expression) {
        DynamicDataNode assignNode = expression.getAssignNode();
        if (assignNode != null && assignNode.getQueryField() != null) {
            return assignNode.getQueryField();
        }
        if (expression.getExtendAssignKey() != null) {
            QueryTable table = new QueryTable("EXT", "EXT");
            return new QueryField(expression.getExtendAssignKey(), expression.getExtendAssignKey(), table);
        }
        return null;
    }

    private static boolean scanItems(List<CalcExpression> sortedExpressions, Collection<SortCalcItem> allItemsForSort) {
        boolean found = false;
        Iterator<SortCalcItem> i = allItemsForSort.iterator();
        while (i.hasNext()) {
            SortCalcItem curItem = i.next();
            if (curItem.tag != 0) continue;
            curItem.putToSortedExpressions(sortedExpressions);
            i.remove();
            for (SortCalcItem next : curItem.getNextCalcItems()) {
                --next.tag;
            }
            found = true;
        }
        return found;
    }

    private static SortCalcItem forceGetItem(List<CalcExpression> sortedExpressions, Collection<SortCalcItem> allItemsForSort, IMonitor monitor) {
        Stack<SortCalcItem> route = new Stack<SortCalcItem>();
        for (SortCalcItem start : allItemsForSort) {
            SortCalcItem item = CalcExpressionSortUtil.tryGetItem(route, start, monitor);
            if (item == null) continue;
            return item;
        }
        return null;
    }

    private static SortCalcItem tryGetItem(Stack<SortCalcItem> route, SortCalcItem current, IMonitor monitor) {
        if (current.tag <= 0) {
            return null;
        }
        if (route.contains(current)) {
            monitor.message("\u7531\u4e8e\u8ba1\u7b97\u65f6\u9047\u5230\u5faa\u73af\u8def\u5f84\uff0c\u5f3a\u5236\u4ece[" + current.toString() + "\u5355\u5143\u683c\u5f00\u59cb\u8ba1\u7b97\u3002\u5f53\u524d\u5faa\u73af\u8def\u5f84\u4e3a\uff1a" + CalcExpressionSortUtil.toString(route), null);
            for (SortCalcItem next : current.getNextCalcItems()) {
                --next.tag;
            }
            return current;
        }
        route.push(current);
        for (SortCalcItem next : current.getNextCalcItems()) {
            SortCalcItem item = CalcExpressionSortUtil.tryGetItem(route, next, monitor);
            if (item == null) continue;
            return item;
        }
        route.pop();
        return null;
    }

    private static Stack<SortCalcItem> findCycle(Collection<SortCalcItem> allItemsForSort) {
        Stack<SortCalcItem> route = new Stack<SortCalcItem>();
        for (SortCalcItem start : allItemsForSort) {
            if (start.isVisited()) continue;
            boolean findCycle = CalcExpressionSortUtil.tryFindCycle(route, start);
            start.setVisited(true);
            if (!findCycle) continue;
            return route;
        }
        return null;
    }

    private static boolean tryFindCycle(Stack<SortCalcItem> route, SortCalcItem current) {
        if (current.tag <= 0) {
            return false;
        }
        if (route.contains(current)) {
            return true;
        }
        route.push(current);
        for (SortCalcItem next : current.getNextCalcItems()) {
            if (next.isVisited()) continue;
            boolean findCycle = CalcExpressionSortUtil.tryFindCycle(route, next);
            next.setVisited(true);
            if (!findCycle) continue;
            return true;
        }
        route.pop();
        return false;
    }

    private static String toString(Collection<SortCalcItem> route) {
        StringBuilder buffer = new StringBuilder("[");
        boolean started = false;
        for (SortCalcItem cell : route) {
            if (started) {
                buffer.append("->");
            } else {
                started = true;
            }
            buffer.append(cell.toString());
        }
        buffer.append(']');
        return buffer.toString();
    }
}

