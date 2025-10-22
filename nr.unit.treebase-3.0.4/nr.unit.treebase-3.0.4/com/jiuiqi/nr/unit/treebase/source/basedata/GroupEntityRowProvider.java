/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 */
package com.jiuiqi.nr.unit.treebase.source.basedata;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
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
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import java.util.List;

public class GroupEntityRowProvider
implements IUnitTreeEntityRowProvider {
    private IEntityTable dataTable;
    private IEntityRefer referBBLXEntity;
    private IUnitTreeEntityRowCounter nodeCounter;

    public GroupEntityRowProvider(IUnitTreeContext context, IUnitTreeEntityDataQuery entityDataQuery, IEntityRefer referBBLXEntity, UnitTreeNodeCountPloy nodeCountPloy) {
        this.referBBLXEntity = referBBLXEntity;
        this.dataTable = entityDataQuery.makeFullTreeData(context);
        this.nodeCounter = this.getNodeCounter(this.dataTable, nodeCountPloy);
    }

    @Override
    public List<IEntityRow> getRootRows() {
        return this.dataTable.getRootRows();
    }

    @Override
    public List<IEntityRow> getChildRows(IBaseNodeData parent) {
        return this.dataTable.getChildRows(parent.getKey());
    }

    @Override
    public IEntityTable getStructTreeRows() {
        return this.dataTable;
    }

    @Override
    public IEntityRow findEntityRow(IBaseNodeData rowData) {
        if (rowData != null && StringUtils.isNotEmpty((String)rowData.getKey())) {
            return this.dataTable.findByEntityKey(rowData.getKey());
        }
        return null;
    }

    @Override
    public List<IEntityRow> getAllRows() {
        return this.dataTable.getAllRows();
    }

    @Override
    public String[] getNodePath(IBaseNodeData rowData) {
        IEntityRow row = this.findEntityRow(rowData);
        return DefaultTreeEntityRowProvider.checkedNodePath(row);
    }

    @Override
    public boolean isLeaf(IBaseNodeData rowData) {
        return this.nodeCounter.isLeaf(rowData);
    }

    @Override
    public int getDirectChildCount(IBaseNodeData parent) {
        return this.nodeCounter.getDirectChildCount(parent);
    }

    @Override
    public int getShowCountNumber(IBaseNodeData data) {
        return this.nodeCounter.getShowCountNumber(data);
    }

    @Override
    public int getAllChildCount(IBaseNodeData parent) {
        return this.nodeCounter.getAllChildCount(parent);
    }

    protected IUnitTreeEntityRowCounter getNodeCounter(IEntityTable dataTable, UnitTreeNodeCountPloy nodeCountPloy) {
        switch (nodeCountPloy) {
            case COUNT_OF_LEAF: {
                return new LeafNodeCounter(dataTable);
            }
            case COUNT_OF_LEAF_AND_NOT_CHA_E: {
                return this.referBBLXEntity != null ? new LeafAndNotChaCounter(dataTable, this.referBBLXEntity) : new LeafNodeCounter(dataTable);
            }
            case COUNT_OF_ALL_CHILD_AND_NOT_CHA_E: {
                return this.referBBLXEntity != null ? new TreeNodeAndNotChaECounter(dataTable, this.referBBLXEntity) : new DefaultUnitTreeRowCounter(dataTable);
            }
        }
        return new DefaultUnitTreeRowCounter(dataTable);
    }
}

