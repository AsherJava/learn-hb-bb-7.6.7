/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.itreebase.node.BaseNodeDataImpl
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.itreebase.nodeicon.IconSourceScheme
 *  com.jiuqi.nr.itreebase.nodeicon.impl.IconSourceOfCustom
 *  com.jiuqi.nr.itreebase.nodeicon.impl.IconSourceSchemeOfStatus
 *  com.jiuqi.nr.itreebase.source.ITreeNodeProvider
 *  com.jiuqi.nr.itreebase.source.search.ISearchNodeProvider
 *  com.jiuqi.nr.unit.treestore.systemconfig.UnitTreeSystemConfig
 */
package com.jiuiqi.nr.unit.treebase.source.basedata;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper;
import com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeGroupField;
import com.jiuiqi.nr.unit.treebase.entity.counter2.IUnitTreeNodeCounter;
import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery;
import com.jiuiqi.nr.unit.treebase.entity.search.SearchNodeWithMemory;
import com.jiuiqi.nr.unit.treebase.enumeration.UnitTreeNodeCountPloy;
import com.jiuiqi.nr.unit.treebase.node.builder.UnitTreeNodeBuilderHelper;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeDataSource;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder;
import com.jiuiqi.nr.unit.treebase.source.basedata.GroupDataTable;
import com.jiuiqi.nr.unit.treebase.source.basedata.GroupEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.source.basedata.GroupNodeAllChildCounter;
import com.jiuiqi.nr.unit.treebase.source.basedata.GroupNodeDataBuilder;
import com.jiuiqi.nr.unit.treebase.source.basedata.GroupNodeDirectChildCounter;
import com.jiuiqi.nr.unit.treebase.source.basedata.GroupNodeProvider;
import com.jiuiqi.nr.unit.treebase.source.basedata.GroupSearchNodeProvider;
import com.jiuiqi.nr.unit.treebase.source.basedata.IGroupDataRow;
import com.jiuiqi.nr.unit.treebase.source.basedata.IGroupDataTable;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.itreebase.node.BaseNodeDataImpl;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceScheme;
import com.jiuqi.nr.itreebase.nodeicon.impl.IconSourceOfCustom;
import com.jiuqi.nr.itreebase.nodeicon.impl.IconSourceSchemeOfStatus;
import com.jiuqi.nr.itreebase.source.ITreeNodeProvider;
import com.jiuqi.nr.itreebase.source.search.ISearchNodeProvider;
import com.jiuqi.nr.unit.treestore.systemconfig.UnitTreeSystemConfig;

public class UnitTreeBaseDataSource
implements IUnitTreeDataSource {
    public static final String SOURCE_ID = "unit-tree-base-data-source";
    protected final IUnitTreeContextWrapper contextWrapper;
    protected final IUnitTreeEntityDataQuery entityDataQuery;
    protected final UnitTreeSystemConfig unitTreeSystemConfig;
    protected final UnitTreeNodeBuilderHelper nodeBuilderHelper;

    public UnitTreeBaseDataSource(IUnitTreeContextWrapper contextWrapper, IUnitTreeEntityDataQuery entityDataQuery, UnitTreeSystemConfig unitTreeSystemConfig) {
        this.contextWrapper = contextWrapper;
        this.entityDataQuery = entityDataQuery;
        this.unitTreeSystemConfig = unitTreeSystemConfig;
        this.nodeBuilderHelper = (UnitTreeNodeBuilderHelper)BeanUtil.getBean(UnitTreeNodeBuilderHelper.class);
    }

    @Override
    public String getSourceId() {
        return SOURCE_ID;
    }

    @Override
    public IconSourceScheme[] getNodeIconSource(IUnitTreeContext ctx) {
        return new IconSourceScheme[]{this.contextWrapper.getBBLXIConSourceScheme(ctx.getEntityDefine()), new IconSourceSchemeOfStatus(), new IconSourceOfCustom()};
    }

    @Override
    public ITreeNodeProvider getTreeNodeProvider(IUnitTreeContext ctx) {
        UnitTreeGroupField groupField = this.contextWrapper.getDimGroupFieldConfig(ctx.getTaskDefine());
        IUnitTreeEntityRowProvider dimRowProvider = this.getUnitTreeEntityRowProvider(ctx);
        UnitTreeNodeCountPloy nodeCountPloy = UnitTreeNodeCountPloy.translatePloy(this.unitTreeSystemConfig.isCountOfDiffUnit(), this.unitTreeSystemConfig.isCountOfLeaves());
        GroupDataTable groupDataTable = new GroupDataTable(dimRowProvider, groupField, nodeCountPloy);
        IBaseNodeData actionNode = this.getActionNode(ctx, groupDataTable, dimRowProvider);
        IUnitTreeNodeBuilder dimNodeBuilder = this.nodeBuilderHelper.getNodeBuilder(ctx, dimRowProvider);
        GroupNodeDataBuilder nodeBuilder = new GroupNodeDataBuilder(dimNodeBuilder, ctx.getIconProvider(), groupField, actionNode);
        return new GroupNodeProvider(groupDataTable, dimRowProvider, nodeBuilder, actionNode);
    }

    @Override
    public ISearchNodeProvider getSearchDataProvider(IUnitTreeContext ctx) {
        UnitTreeNodeCountPloy nodeCountPloy = UnitTreeNodeCountPloy.translatePloy(this.unitTreeSystemConfig.isCountOfDiffUnit(), this.unitTreeSystemConfig.isCountOfLeaves());
        UnitTreeGroupField groupField = this.contextWrapper.getDimGroupFieldConfig(ctx.getTaskDefine());
        IUnitTreeEntityRowProvider dimRowProvider = this.getUnitTreeEntityRowProvider(ctx);
        GroupDataTable groupDataTable = new GroupDataTable(dimRowProvider, groupField, nodeCountPloy);
        SearchNodeWithMemory searchNodeWithMemory = new SearchNodeWithMemory(dimRowProvider);
        return new GroupSearchNodeProvider(searchNodeWithMemory, groupDataTable);
    }

    @Override
    public IUnitTreeEntityRowProvider getUnitTreeEntityRowProvider(IUnitTreeContext ctx) {
        UnitTreeNodeCountPloy nodeCountPloy = UnitTreeNodeCountPloy.translatePloy(this.unitTreeSystemConfig.isCountOfDiffUnit(), this.unitTreeSystemConfig.isCountOfLeaves());
        IEntityRefer referBBLXEntity = this.contextWrapper.getBBLXEntityRefer(ctx.getEntityDefine());
        return new GroupEntityRowProvider(ctx, this.entityDataQuery, referBBLXEntity, nodeCountPloy);
    }

    @Override
    public IUnitTreeNodeCounter getNodeCounter(IUnitTreeContext ctx) {
        UnitTreeNodeCountPloy nodeCountPloy = UnitTreeNodeCountPloy.translatePloy(this.unitTreeSystemConfig.isCountOfDiffUnit(), this.unitTreeSystemConfig.isCountOfLeaves());
        UnitTreeGroupField groupField = this.contextWrapper.getDimGroupFieldConfig(ctx.getTaskDefine());
        IUnitTreeEntityRowProvider dimRowProvider = this.getUnitTreeEntityRowProvider(ctx);
        GroupDataTable groupDataTable = new GroupDataTable(dimRowProvider, groupField, nodeCountPloy);
        IBaseNodeData actionNode = this.getActionNode(ctx, groupDataTable, dimRowProvider);
        if (this.unitTreeSystemConfig.isCountOfAllChildrenNum()) {
            return new GroupNodeAllChildCounter(groupDataTable, dimRowProvider, actionNode);
        }
        return new GroupNodeDirectChildCounter(groupDataTable, dimRowProvider, actionNode);
    }

    protected IBaseNodeData getActionNode(IUnitTreeContext ctx, IGroupDataTable groupDataTable, IUnitTreeEntityRowProvider entityRowProvider) {
        IBaseNodeData actionNode = ctx.getITreeContext().getActionNode();
        IGroupDataRow groupRow = groupDataTable.findGroupRow(actionNode);
        if (groupRow != null) {
            return actionNode;
        }
        IEntityRow entityRow = entityRowProvider.findEntityRow(actionNode);
        if (entityRow != null) {
            return actionNode;
        }
        IGroupDataRow firstDimRow = groupDataTable.findFirstDimRow();
        if (firstDimRow != null) {
            actionNode = new BaseNodeDataImpl();
            actionNode.setKey(firstDimRow.getEntityKeyData());
            actionNode.setCode(firstDimRow.getCode());
            actionNode.setTitle(firstDimRow.getTitle());
        }
        return actionNode;
    }
}

