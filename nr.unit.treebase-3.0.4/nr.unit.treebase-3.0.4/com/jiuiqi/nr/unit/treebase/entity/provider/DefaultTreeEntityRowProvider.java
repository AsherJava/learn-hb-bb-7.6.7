/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.unit.treecommon.utils.JavaBeanUtils
 */
package com.jiuiqi.nr.unit.treebase.entity.provider;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.entity.counter.DefaultUnitTreeRowCounter;
import com.jiuiqi.nr.unit.treebase.entity.counter.IUnitTreeEntityRowCounter;
import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.unit.treecommon.utils.JavaBeanUtils;
import java.util.Collections;
import java.util.List;

public class DefaultTreeEntityRowProvider
implements IUnitTreeEntityRowProvider {
    protected IUnitTreeContext context;
    protected IEntityTable dataTable;
    protected IUnitTreeEntityRowCounter rowCounter;
    protected IUnitTreeEntityDataQuery entityDataQuery;

    public DefaultTreeEntityRowProvider(IUnitTreeContext context, IUnitTreeEntityDataQuery entityDataQuery) {
        this.context = context;
        this.entityDataQuery = entityDataQuery;
    }

    @Override
    public List<IEntityRow> getRootRows() {
        this.dataTable = this.entityDataQuery.makeIEntityTable(this.context);
        this.rowCounter = new DefaultUnitTreeRowCounter(this.dataTable);
        return this.dataTable.getRootRows();
    }

    @Override
    public List<IEntityRow> getChildRows(IBaseNodeData parent) {
        this.dataTable = this.entityDataQuery.makeIEntityTable(this.context);
        this.rowCounter = new DefaultUnitTreeRowCounter(this.dataTable);
        return this.dataTable.getChildRows(parent.getKey());
    }

    @Override
    public IEntityTable getStructTreeRows() {
        this.dataTable = this.entityDataQuery.makeIEntityTable(this.context);
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
        return this.rowCounter.isLeaf(rowData);
    }

    @Override
    public int getDirectChildCount(IBaseNodeData parent) {
        return this.rowCounter.getDirectChildCount(parent);
    }

    @Override
    public int getAllChildCount(IBaseNodeData parent) {
        return this.rowCounter.getAllChildCount(parent);
    }

    public static String[] checkedNodePath(IEntityRow targetRow) {
        if (targetRow != null) {
            return (String[])JavaBeanUtils.appendElement((Object[])targetRow.getParentsEntityKeyDataPath(), (Object[])new String[]{targetRow.getEntityKeyData()});
        }
        return new String[0];
    }
}

