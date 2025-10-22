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
import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import java.util.List;

public class RangeWithTreeProvider
implements IUnitTreeEntityRowProvider {
    protected IUnitTreeContext context;
    protected IUnitTreeEntityDataQuery entityRowQuery;

    public RangeWithTreeProvider(IUnitTreeContext context, IUnitTreeEntityDataQuery entityRowQuery) {
        this.context = context;
        this.entityRowQuery = entityRowQuery;
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
        return IUnitTreeEntityRowProvider.super.getAllChildCount(parent);
    }

    @Override
    public String[] getNodePath(IBaseNodeData rowData) {
        return new String[0];
    }

    @Override
    public IEntityRow findEntityRow(IBaseNodeData rowData) {
        return null;
    }

    @Override
    public List<IEntityRow> getAllRows() {
        return null;
    }

    @Override
    public List<IEntityRow> getRootRows() {
        return null;
    }

    @Override
    public List<IEntityRow> getChildRows(IBaseNodeData parent) {
        return null;
    }

    @Override
    public IEntityTable getStructTreeRows() {
        return null;
    }
}

