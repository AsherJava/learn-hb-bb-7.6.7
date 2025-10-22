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
package com.jiuqi.nr.unit.treeimpl.tree.onenode;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import java.util.Collections;
import java.util.List;

public class OneEntityRowProvider
implements IUnitTreeEntityRowProvider {
    private IUnitTreeContext context;
    private IUnitTreeEntityDataQuery entityDataQuery;

    public OneEntityRowProvider(IUnitTreeContext context, IUnitTreeEntityDataQuery entityDataQuery) {
        this.context = context;
        this.entityDataQuery = entityDataQuery;
    }

    public boolean isLeaf(IBaseNodeData rowData) {
        return this.getDirectChildCount(rowData) == 0;
    }

    public int getDirectChildCount(IBaseNodeData parent) {
        return 0;
    }

    public String[] getNodePath(IBaseNodeData rowData) {
        return new String[0];
    }

    public IEntityRow findEntityRow(IBaseNodeData rowData) {
        if (rowData != null && StringUtils.isNotEmpty((String)rowData.getKey())) {
            IEntityTable dataTable = this.entityDataQuery.makeIEntityTable(this.context, Collections.singletonList(rowData.getKey()));
            return dataTable.findByEntityKey(rowData.getKey());
        }
        return null;
    }

    public List<IEntityRow> getAllRows() {
        return null;
    }

    public List<IEntityRow> getRootRows() {
        return null;
    }

    public List<IEntityRow> getChildRows(IBaseNodeData parent) {
        return null;
    }

    public IEntityTable getStructTreeRows() {
        return null;
    }
}

