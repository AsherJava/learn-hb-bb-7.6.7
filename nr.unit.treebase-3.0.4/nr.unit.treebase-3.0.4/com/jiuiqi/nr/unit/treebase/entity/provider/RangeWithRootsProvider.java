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
import com.jiuiqi.nr.unit.treebase.entity.counter.DefaultUnitTreeRowCounter;
import com.jiuiqi.nr.unit.treebase.entity.counter.IUnitTreeEntityRowCounter;
import com.jiuiqi.nr.unit.treebase.entity.provider.DefaultTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import java.util.Collections;
import java.util.List;

public class RangeWithRootsProvider
implements IUnitTreeEntityRowProvider {
    private List<String> rootRange;
    private IUnitTreeContext context;
    private IUnitTreeEntityRowCounter rowCounter;
    private IEntityTable dataTable;
    private IUnitTreeEntityDataQuery entityDataQuery;

    public RangeWithRootsProvider(IUnitTreeContext context, IUnitTreeEntityDataQuery entityDataQuery, List<String> rootRange) {
        this.context = context;
        this.rootRange = rootRange;
        this.entityDataQuery = entityDataQuery;
    }

    @Override
    public boolean isLeaf(IBaseNodeData rowData) {
        return this.getDirectChildCount(rowData) == 0;
    }

    @Override
    public int getDirectChildCount(IBaseNodeData parent) {
        return this.rowCounter.getDirectChildCount(parent);
    }

    @Override
    public int getAllChildCount(IBaseNodeData parent) {
        return this.rowCounter.getAllChildCount(parent);
    }

    @Override
    public String[] getNodePath(IBaseNodeData rowData) {
        IEntityRow row = this.findEntityRow(rowData);
        return DefaultTreeEntityRowProvider.checkedNodePath(row);
    }

    @Override
    public IEntityRow findEntityRow(IBaseNodeData rowData) {
        return this.dataTable.findByEntityKey(rowData.getKey());
    }

    @Override
    public List<IEntityRow> getAllRows() {
        this.dataTable = this.entityDataQuery.makeIEntityTable(this.context, this.rootRange);
        this.rowCounter = new DefaultUnitTreeRowCounter(this.dataTable);
        return this.dataTable.getAllRows();
    }

    @Override
    public List<IEntityRow> getRootRows() {
        this.dataTable = this.entityDataQuery.makeIEntityTable(this.context, this.rootRange);
        this.rowCounter = new DefaultUnitTreeRowCounter(this.dataTable);
        return this.dataTable.getRootRows();
    }

    @Override
    public List<IEntityRow> getChildRows(IBaseNodeData parent) {
        this.dataTable = this.entityDataQuery.makeRangeFullTreeData(this.context, Collections.singletonList(parent.getKey()));
        this.rowCounter = new DefaultUnitTreeRowCounter(this.dataTable);
        return this.dataTable.getChildRows(parent.getKey());
    }

    @Override
    public IEntityTable getStructTreeRows() {
        this.dataTable = this.entityDataQuery.makeIEntityTable(this.context, this.rootRange);
        return this.dataTable;
    }
}

