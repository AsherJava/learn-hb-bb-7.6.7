/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.entity.counter.DefaultUnitTreeRowCounter
 *  com.jiuiqi.nr.unit.treebase.entity.counter.IUnitTreeEntityRowCounter
 *  com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider
 *  com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.unit.treecommon.utils.JavaBeanUtils
 */
package com.jiuqi.nr.dataentry.internal.overview;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.entity.counter.DefaultUnitTreeRowCounter;
import com.jiuiqi.nr.unit.treebase.entity.counter.IUnitTreeEntityRowCounter;
import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.dataentry.internal.overview.OverviewRootDataRow;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.unit.treecommon.utils.JavaBeanUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OverviewTreeEntityRowProvider
implements IUnitTreeEntityRowProvider {
    protected IUnitTreeContext context;
    protected IEntityTable dataTable;
    protected IUnitTreeEntityRowCounter rowCounter;
    protected IUnitTreeEntityDataQuery entityDataQuery;
    private IEntityRow rootEntityRow;

    public OverviewTreeEntityRowProvider(IUnitTreeContext context, IUnitTreeEntityDataQuery entityDataQuery) {
        this.context = context;
        this.entityDataQuery = entityDataQuery;
        this.rootEntityRow = OverviewRootDataRow.getRootDataRow();
    }

    public List<IEntityRow> getRootRows() {
        ArrayList<IEntityRow> allRows = new ArrayList<IEntityRow>();
        this.dataTable = this.entityDataQuery.makeIEntityTable(this.context);
        this.rowCounter = new DefaultUnitTreeRowCounter(this.dataTable);
        allRows.addAll(this.dataTable.getRootRows());
        return allRows;
    }

    public List<IEntityRow> getChildRows(IBaseNodeData parent) {
        this.dataTable = this.entityDataQuery.makeIEntityTable(this.context);
        this.rowCounter = new DefaultUnitTreeRowCounter(this.dataTable);
        if (parent.getKey().equals("all-unit")) {
            return this.dataTable.getRootRows();
        }
        return this.dataTable.getChildRows(parent.getKey());
    }

    public String[] getNodePath(IBaseNodeData rowData) {
        ArrayList<String> paths = new ArrayList<String>();
        if (rowData.getKey().equals("all-unit")) {
            paths.add("all-unit");
            return paths.toArray(new String[paths.size()]);
        }
        IEntityRow row = this.findEntityRow(rowData);
        return OverviewTreeEntityRowProvider.checkedNodePath(row);
    }

    public IEntityRow findEntityRow(IBaseNodeData rowData) {
        IEntityRow targetRow = null;
        if (rowData != null && StringUtils.isNotEmpty((String)rowData.getKey())) {
            if (rowData.getKey().equals("all-unit")) {
                return OverviewRootDataRow.getRootDataRow();
            }
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

    public List<IEntityRow> getAllRows() {
        ArrayList<IEntityRow> allRows = new ArrayList<IEntityRow>();
        this.dataTable = this.entityDataQuery.makeIEntityTable(this.context);
        allRows.add((IEntityRow)OverviewRootDataRow.getRootDataRow());
        allRows.addAll(this.dataTable.getAllRows());
        return allRows;
    }

    public boolean isLeaf(IBaseNodeData rowData) {
        return this.rowCounter.isLeaf(rowData);
    }

    public int getDirectChildCount(IBaseNodeData parent) {
        return this.rowCounter.getDirectChildCount(parent);
    }

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

