/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.entity.filter.IFilterEntityRow
 *  com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 */
package com.jiuqi.nr.unit.treeimpl.filter;

import com.jiuiqi.nr.unit.treebase.entity.filter.IFilterEntityRow;
import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FilterEntityRowProvider
implements IUnitTreeEntityRowProvider {
    protected IFilterEntityRow filter;
    protected IUnitTreeEntityRowProvider baseProvider;

    public FilterEntityRowProvider(IUnitTreeEntityRowProvider baseProvider, IFilterEntityRow filter) {
        this.baseProvider = baseProvider;
        this.filter = filter;
    }

    public boolean isLeaf(IBaseNodeData rowData) {
        return this.getDirectChildCount(rowData) == 0;
    }

    public int getDirectChildCount(IBaseNodeData parent) {
        return 0;
    }

    public int getAllChildCount(IBaseNodeData parent) {
        return 0;
    }

    public String[] getNodePath(IBaseNodeData rowData) {
        return new String[]{rowData.getKey()};
    }

    public IEntityRow findEntityRow(IBaseNodeData rowData) {
        if (rowData != null && StringUtils.isNotEmpty((String)rowData.getKey())) {
            List<IEntityRow> allRows = this.getAllRows();
            return allRows.stream().filter(r -> r.getEntityKeyData().equals(rowData.getKey())).findFirst().orElse(null);
        }
        return null;
    }

    public List<IEntityRow> getAllRows() {
        List allRows = this.baseProvider.getAllRows();
        this.filter.setMatchRangeRows(allRows);
        return allRows.stream().filter(row -> this.filter.matchRow(row)).collect(Collectors.toList());
    }

    public List<IEntityRow> getRootRows() {
        return this.getAllRows();
    }

    public List<IEntityRow> getChildRows(IBaseNodeData parent) {
        return new ArrayList<IEntityRow>();
    }

    public IEntityTable getStructTreeRows() {
        return this.baseProvider.getStructTreeRows();
    }

    public int getShowCountNumber(IBaseNodeData data) {
        return 0;
    }
}

