/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider
 *  javax.annotation.Resource
 */
package com.jiuiqi.nr.unit.treebase.node.builder;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper;
import com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeShowTagsOptions;
import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.entity.query.IEntityQueryPloy;
import com.jiuiqi.nr.unit.treebase.node.builder.BBLXUnitNodeBuilder;
import com.jiuiqi.nr.unit.treebase.node.builder.FMDMNodeDataBuilder;
import com.jiuiqi.nr.unit.treebase.node.builder.FillReportNodeDataBuilder;
import com.jiuiqi.nr.unit.treebase.node.builder.TagsNodeDataBuilder;
import com.jiuiqi.nr.unit.treebase.node.builder.TerminalStateNodeDataBuilder;
import com.jiuiqi.nr.unit.treebase.node.builder.UnitTreeNodeBuilder;
import com.jiuiqi.nr.unit.treebase.node.builder.WorkFlowNodeDataBuilder;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class UnitTreeNodeBuilderHelper {
    @Resource
    private IUnitTreeContextWrapper contextWrapper;

    public IUnitTreeNodeBuilder getNodeBuilder(IUnitTreeContext context, IUnitTreeEntityRowProvider dimRowProvider) {
        IconSourceProvider iconProvider = context.getIconProvider();
        UnitTreeNodeBuilder nodeBuilder = new UnitTreeNodeBuilder(dimRowProvider, iconProvider);
        return this.getNodeBuilder(context, dimRowProvider, nodeBuilder);
    }

    public IUnitTreeNodeBuilder getNodeBuilder(IUnitTreeContext context, IUnitTreeEntityRowProvider dimRowProvider, IUnitTreeNodeBuilder nodeBuilder) {
        if (context.getEntityQueryPloy() == IEntityQueryPloy.MAIN_DIM_QUERY) {
            UnitTreeShowTagsOptions showTagsOptions;
            Map<String, DimensionValue> dimValueSet = context.getDimValueSet();
            IconSourceProvider iconProvider = context.getIconProvider();
            IEntityRefer referBBLXEntity = this.contextWrapper.getBBLXEntityRefer(context.getEntityDefine());
            if (referBBLXEntity != null) {
                nodeBuilder = new BBLXUnitNodeBuilder(nodeBuilder, iconProvider, referBBLXEntity);
            }
            if (this.contextWrapper.canDisplayFMDMAttributes(context.getFormScheme(), context.getEntityDefine(), context.getEntityQueryPloy())) {
                nodeBuilder = new FMDMNodeDataBuilder(context, nodeBuilder);
            }
            if (this.contextWrapper.canShowNodeTags(showTagsOptions = UnitTreeShowTagsOptions.translate2ShowTagsOptions(context.getCustomVariable()))) {
                nodeBuilder = new TagsNodeDataBuilder(context, nodeBuilder, showTagsOptions);
            }
            if (this.contextWrapper.isOpenFillReport(context.getFormScheme()) && dimValueSet != null && !dimValueSet.isEmpty()) {
                nodeBuilder = new FillReportNodeDataBuilder(context, nodeBuilder, iconProvider);
            }
            if (this.contextWrapper.isOpenTerminal(context.getFormScheme()) && dimValueSet != null && !dimValueSet.isEmpty()) {
                nodeBuilder = new TerminalStateNodeDataBuilder(context, nodeBuilder, iconProvider);
            }
            if (this.contextWrapper.canLoadWorkFlowState(context)) {
                nodeBuilder = new WorkFlowNodeDataBuilder(context, nodeBuilder, iconProvider);
            }
        }
        return nodeBuilder;
    }
}

