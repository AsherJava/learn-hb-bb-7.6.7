/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider
 *  com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 */
package com.jiuqi.nr.unit.uselector.source.impl;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import java.util.Collections;
import java.util.List;

public class USelectorNormalEntityRowProvider
implements IUnitTreeEntityRowProvider {
    private IEntityTable dataTable;
    protected IUnitTreeContext context;
    protected IUnitTreeEntityDataQuery entityDataQuery;

    public USelectorNormalEntityRowProvider(IUnitTreeContext context, IUnitTreeEntityDataQuery entityDataQuery) {
        this.context = context;
        this.entityDataQuery = entityDataQuery;
    }

    public boolean isLeaf(IBaseNodeData rowData) {
        return this.getDirectChildCount(rowData) == 0;
    }

    public int getDirectChildCount(IBaseNodeData parent) {
        return this.dataTable.getDirectChildCount(parent.getKey());
    }

    public String[] getNodePath(IBaseNodeData rowData) {
        return this.dataTable.getParentsEntityKeyDataPath(rowData.getKey());
    }

    public IEntityRow findEntityRow(IBaseNodeData rowData) {
        if (rowData != null && StringUtils.isNotEmpty((String)rowData.getKey())) {
            this.dataTable = this.entityDataQuery.makeIEntityTable(this.context, Collections.singletonList(rowData.getKey()));
            return this.dataTable.findByEntityKey(rowData.getKey());
        }
        return null;
    }

    public List<IEntityRow> getAllRows() {
        this.dataTable = this.entityDataQuery.makeIEntityTable(this.context);
        return this.dataTable.getAllRows();
    }

    public List<IEntityRow> getRootRows() {
        this.dataTable = this.entityDataQuery.makeIEntityTable(this.context);
        return this.dataTable.getRootRows();
    }

    public List<IEntityRow> getChildRows(IBaseNodeData parent) {
        this.dataTable = this.entityDataQuery.makeRangeFullTreeData(this.context, Collections.singletonList(parent.getKey()));
        return this.dataTable.getChildRows(parent.getKey());
    }

    public IEntityTable getStructTreeRows() {
        this.dataTable = this.entityDataQuery.makeIEntityTable(this.context);
        return this.dataTable;
    }
}

