/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.entity.filter.IFilterEntityRow
 *  com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.itreebase.node.BaseNodeDataImpl
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 */
package com.jiuqi.nr.unit.treeimpl.filter;

import com.jiuiqi.nr.unit.treebase.entity.filter.IFilterEntityRow;
import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.itreebase.node.BaseNodeDataImpl;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

public class FilterWithQueryTreeProvider
implements IUnitTreeEntityRowProvider {
    protected IFilterEntityRow checker;
    protected IUnitTreeEntityRowProvider baseProvider;
    protected Map<String, Integer> directChildCount = new HashMap<String, Integer>();

    public FilterWithQueryTreeProvider(IUnitTreeEntityRowProvider baseProvider, IFilterEntityRow checker) {
        this.baseProvider = baseProvider;
        this.checker = checker;
    }

    public boolean isLeaf(IBaseNodeData rowData) {
        return this.getDirectChildCount(rowData) == 0;
    }

    public int getDirectChildCount(IBaseNodeData parent) {
        if (this.directChildCount.containsKey(parent.getKey())) {
            return this.directChildCount.get(parent.getKey());
        }
        return 0;
    }

    public String[] getNodePath(IBaseNodeData rowData) {
        ArrayList<String> returnPath = new ArrayList<String>();
        IEntityRow entityRow = this.findEntityRow(rowData);
        if (entityRow != null) {
            String[] path;
            for (String rowKey : path = entityRow.getParentsEntityKeyDataPath()) {
                entityRow = this.findEntityRow((IBaseNodeData)BaseNodeDataImpl.getInstance((String)rowKey));
                if (entityRow == null) continue;
                returnPath.add(rowKey);
            }
            if (!returnPath.contains(rowData.getKey())) {
                returnPath.add(rowData.getKey());
            }
        }
        return returnPath.toArray(new String[0]);
    }

    public IEntityRow findEntityRow(IBaseNodeData rowData) {
        IEntityRow entityRow = this.baseProvider.findEntityRow(rowData);
        if (entityRow != null) {
            this.checker.setMatchRangeRows(Collections.singletonList(entityRow));
            if (this.checker.matchRow(entityRow)) {
                return entityRow;
            }
        }
        return null;
    }

    public List<IEntityRow> getAllRows() {
        List allRows = this.baseProvider.getAllRows();
        this.checker.setMatchRangeRows(allRows);
        return allRows.stream().filter(row -> this.checker.matchRow(row)).collect(Collectors.toList());
    }

    public List<IEntityRow> getRootRows() {
        return this.getChildRows(null);
    }

    public List<IEntityRow> getChildRows(IBaseNodeData parent) {
        List<IEntityRow> childRows = parent == null ? this.baseProvider.getRootRows() : this.baseProvider.getChildRows(parent);
        childRows = this.filterChildRows(childRows);
        for (IEntityRow row : childRows) {
            this.directChildCount.put(row.getEntityKeyData(), this.filterChildRows(this.baseProvider.getChildRows((IBaseNodeData)BaseNodeDataImpl.getInstance((String)row.getEntityKeyData()))).size());
        }
        return childRows;
    }

    public IEntityTable getStructTreeRows() {
        return this.baseProvider.getStructTreeRows();
    }

    protected List<IEntityRow> filterChildRows(List<IEntityRow> childRows) {
        ArrayList<IEntityRow> checkedNodes = new ArrayList<IEntityRow>();
        if (childRows != null && !childRows.isEmpty()) {
            Stack<IEntityRow> stack = new Stack<IEntityRow>();
            this.checker.setMatchRangeRows(childRows);
            this.travers2Stack(stack, childRows);
            while (!stack.isEmpty()) {
                IEntityRow row = stack.pop();
                if (this.checker.matchRow(row)) {
                    checkedNodes.add(row);
                    continue;
                }
                childRows = this.baseProvider.getChildRows((IBaseNodeData)BaseNodeDataImpl.getInstance((String)row.getEntityKeyData()));
                if (childRows == null || childRows.isEmpty()) continue;
                this.checker.setMatchRangeRows(childRows);
                this.travers2Stack(stack, childRows);
            }
        }
        return checkedNodes;
    }

    private void travers2Stack(Stack<IEntityRow> stack, List<IEntityRow> rows) {
        for (int i = rows.size() - 1; i >= 0; --i) {
            stack.push(rows.get(i));
        }
    }
}

