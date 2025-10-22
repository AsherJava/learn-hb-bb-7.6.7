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
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper;
import com.jiuiqi.nr.unit.treebase.entity.provider.DefaultTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import java.util.Collections;
import java.util.List;

public class AsyncUnitTreeEntityRowProvider
implements IUnitTreeEntityRowProvider {
    protected IEntityTable dataTable;
    protected IUnitTreeContext context;
    protected IUnitTreeContextWrapper contextWrapper;
    protected IUnitTreeEntityDataQuery entityDataQuery;

    public AsyncUnitTreeEntityRowProvider(IUnitTreeContext context, IUnitTreeEntityDataQuery entityDataQuery, IUnitTreeContextWrapper contextWrapper) {
        this.context = context;
        this.contextWrapper = contextWrapper;
        this.entityDataQuery = entityDataQuery;
    }

    @Override
    public List<IEntityRow> getRootRows() {
        this.dataTable = this.entityDataQuery.makeIEntityTable(this.context);
        return this.dataTable.getRootRows();
    }

    @Override
    public List<IEntityRow> getChildRows(IBaseNodeData parent) {
        this.dataTable = this.entityDataQuery.makeIEntityTable(this.context);
        return this.dataTable.getChildRows(parent.getKey());
    }

    @Override
    public IEntityTable getStructTreeRows() {
        if (this.dataTable == null) {
            this.dataTable = this.entityDataQuery.makeFullTreeData(this.context);
        }
        return this.dataTable;
    }

    @Override
    public String[] getNodePath(IBaseNodeData rowData) {
        IEntityRow row = this.findEntityRow(rowData);
        return DefaultTreeEntityRowProvider.checkedNodePath(row);
    }

    @Override
    public IEntityRow findEntityRow(IBaseNodeData rowData) {
        IEntityRow targetRow = null;
        if (rowData != null && StringUtils.isNotEmpty((String)rowData.getKey())) {
            if (this.dataTable != null) {
                targetRow = this.dataTable.findByEntityKey(rowData.getKey());
            }
            if (targetRow == null) {
                this.dataTable = this.entityDataQuery.makeIEntityTable(this.context, Collections.singletonList(rowData.getKey()));
                targetRow = this.dataTable.findByEntityKey(rowData.getKey());
            }
        }
        return targetRow;
    }

    @Override
    public List<IEntityRow> getAllRows() {
        this.dataTable = this.entityDataQuery.makeIEntityTable(this.context);
        return this.dataTable.getAllRows();
    }

    @Override
    public boolean isLeaf(IBaseNodeData rowData) {
        return false;
    }

    @Override
    public int getDirectChildCount(IBaseNodeData parent) {
        return 0;
    }
}

