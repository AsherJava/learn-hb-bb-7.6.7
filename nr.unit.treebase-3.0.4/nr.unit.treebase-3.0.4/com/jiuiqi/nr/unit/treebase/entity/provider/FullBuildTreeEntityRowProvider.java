/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 */
package com.jiuiqi.nr.unit.treebase.entity.provider;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.entity.provider.DefaultTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import java.util.List;

public class FullBuildTreeEntityRowProvider
implements IUnitTreeEntityRowProvider {
    private final IEntityTable entityTable;

    public FullBuildTreeEntityRowProvider(IUnitTreeContext context, IUnitTreeEntityDataQuery entityDataQuery) {
        this.entityTable = entityDataQuery.makeFullTreeData(context);
    }

    @Override
    public boolean isLeaf(IBaseNodeData rowData) {
        IEntityRow row = this.findEntityRow(rowData);
        return row.isLeaf();
    }

    @Override
    public int getDirectChildCount(IBaseNodeData parent) {
        return this.entityTable.getDirectChildCount(parent.getKey());
    }

    @Override
    public String[] getNodePath(IBaseNodeData rowData) {
        IEntityRow row = this.findEntityRow(rowData);
        return DefaultTreeEntityRowProvider.checkedNodePath(row);
    }

    @Override
    public IEntityRow findEntityRow(IBaseNodeData rowData) {
        return this.entityTable.findByEntityKey(rowData.getKey());
    }

    @Override
    public List<IEntityRow> getAllRows() {
        return this.entityTable.getAllRows();
    }

    @Override
    public List<IEntityRow> getRootRows() {
        return this.entityTable.getRootRows();
    }

    @Override
    public List<IEntityRow> getChildRows(IBaseNodeData parent) {
        return this.entityTable.getChildRows(parent.getKey());
    }
}

