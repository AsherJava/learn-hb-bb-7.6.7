/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 */
package com.jiuiqi.nr.unit.treebase.entity.provider;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper;
import com.jiuiqi.nr.unit.treebase.entity.counter.DefaultUnitTreeRowCounter;
import com.jiuiqi.nr.unit.treebase.entity.counter.IUnitTreeEntityRowCounter;
import com.jiuiqi.nr.unit.treebase.entity.counter.LeafAndNotChaCounter;
import com.jiuiqi.nr.unit.treebase.entity.counter.LeafNodeCounter;
import com.jiuiqi.nr.unit.treebase.entity.counter.TreeNodeAndNotChaECounter;
import com.jiuiqi.nr.unit.treebase.entity.provider.DefaultTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery;
import com.jiuiqi.nr.unit.treebase.enumeration.UnitTreeNodeCountPloy;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UnitTreeEntityRowProvider
implements IUnitTreeEntityRowProvider {
    protected IEntityTable dataTable;
    protected IUnitTreeContext context;
    protected UnitTreeNodeCountPloy nodeCountPloy;
    protected IUnitTreeEntityRowCounter rowCounter;
    protected IUnitTreeContextWrapper contextWrapper;
    protected IUnitTreeEntityDataQuery entityDataQuery;
    protected boolean isFullyBuiltCounter = false;

    public UnitTreeEntityRowProvider(IUnitTreeContext context, IUnitTreeEntityDataQuery entityDataQuery, IUnitTreeContextWrapper contextWrapper, UnitTreeNodeCountPloy nodeCountPloy) {
        this.context = context;
        this.nodeCountPloy = nodeCountPloy;
        this.contextWrapper = contextWrapper;
        this.entityDataQuery = entityDataQuery;
    }

    @Override
    public List<IEntityRow> getRootRows() {
        this.dataTable = this.entityDataQuery.makeIEntityTable(this.context);
        List rootRows = this.dataTable.getRootRows();
        this.rowCounter = this.getNodeCounter(rootRows);
        this.isFullyBuiltCounter = true;
        return rootRows;
    }

    @Override
    public List<IEntityRow> getChildRows(IBaseNodeData parent) {
        this.dataTable = this.entityDataQuery.makeIEntityTable(this.context);
        List childRows = this.dataTable.getChildRows(parent.getKey());
        this.rowCounter = this.getNodeCounter(childRows);
        return childRows;
    }

    @Override
    public IEntityTable getStructTreeRows() {
        if (this.dataTable == null) {
            this.dataTable = this.entityDataQuery.makeFullTreeData(this.context);
            this.rowCounter = this.getNodeCounter(this.dataTable);
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
        this.rowCounter = this.getNodeCounter(this.dataTable);
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
    public int getShowCountNumber(IBaseNodeData data) {
        return this.rowCounter.getShowCountNumber(data);
    }

    @Override
    public int getAllChildCount(IBaseNodeData parent) {
        return this.rowCounter.getAllChildCount(parent);
    }

    protected IUnitTreeEntityRowCounter getNodeCounter(List<IEntityRow> rangeRows) {
        if (!this.isFullyBuiltCounter) {
            List<String> rangeKeys = rangeRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
            if (!rangeKeys.isEmpty()) {
                IEntityTable dataTable = this.entityDataQuery.makeRangeFullTreeData(this.context, rangeKeys, false);
                return this.getNodeCounter(dataTable);
            }
            return this.getNodeCounter(this.dataTable);
        }
        return this.rowCounter;
    }

    protected IUnitTreeEntityRowCounter getNodeCounter(IEntityTable dataTable) {
        IEntityRefer referBBLXEntity = this.contextWrapper.getBBLXEntityRefer(this.context.getEntityDefine());
        switch (this.nodeCountPloy) {
            case COUNT_OF_LEAF: {
                return new LeafNodeCounter(dataTable);
            }
            case COUNT_OF_LEAF_AND_NOT_CHA_E: {
                return referBBLXEntity != null ? new LeafAndNotChaCounter(dataTable, referBBLXEntity) : new LeafNodeCounter(dataTable);
            }
            case COUNT_OF_ALL_CHILD_AND_NOT_CHA_E: {
                return referBBLXEntity != null ? new TreeNodeAndNotChaECounter(dataTable, referBBLXEntity) : new DefaultUnitTreeRowCounter(dataTable);
            }
        }
        return new DefaultUnitTreeRowCounter(dataTable);
    }
}

