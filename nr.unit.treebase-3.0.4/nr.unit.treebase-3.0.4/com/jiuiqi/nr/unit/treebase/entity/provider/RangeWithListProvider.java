/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 */
package com.jiuiqi.nr.unit.treebase.entity.provider;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import java.util.ArrayList;
import java.util.List;

public class RangeWithListProvider
implements IUnitTreeEntityRowProvider {
    private IUnitTreeContext context;
    private List<String> entityRange;
    private IUnitTreeEntityDataQuery entityDataQuery;

    public RangeWithListProvider(IUnitTreeContext context, IUnitTreeEntityDataQuery entityDataQuery, List<String> entityRange) {
        this.context = context;
        this.entityRange = entityRange;
        this.entityDataQuery = entityDataQuery;
    }

    @Override
    public boolean isLeaf(IBaseNodeData rowData) {
        return this.getDirectChildCount(rowData) == 0;
    }

    @Override
    public int getDirectChildCount(IBaseNodeData parent) {
        return 0;
    }

    @Override
    public int getAllChildCount(IBaseNodeData parent) {
        return 0;
    }

    @Override
    public String[] getNodePath(IBaseNodeData rowData) {
        return new String[]{rowData.getKey()};
    }

    @Override
    public IEntityRow findEntityRow(IBaseNodeData rowData) {
        if (rowData != null && StringUtils.isNotEmpty((String)rowData.getKey())) {
            IEntityTable dataTable = this.entityDataQuery.makeIEntityTable(this.context, this.entityRange);
            return dataTable.findByEntityKey(rowData.getKey());
        }
        return null;
    }

    @Override
    public List<IEntityRow> getAllRows() {
        IEntityTable dataTable = this.entityDataQuery.makeIEntityTable(this.context, this.entityRange);
        return dataTable.getAllRows();
    }

    @Override
    public List<IEntityRow> getRootRows() {
        return this.getAllRows();
    }

    @Override
    public List<IEntityRow> getChildRows(IBaseNodeData parent) {
        return new ArrayList<IEntityRow>();
    }

    @Override
    public IEntityTable getStructTreeRows() {
        return this.entityDataQuery.makeIEntityTable(this.context, this.entityRange);
    }
}

