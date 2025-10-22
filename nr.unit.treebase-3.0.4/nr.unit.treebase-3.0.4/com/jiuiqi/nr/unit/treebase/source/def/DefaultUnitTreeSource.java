/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider
 *  com.jiuqi.nr.itreebase.nodeicon.IconSourceScheme
 *  com.jiuqi.nr.itreebase.source.ITreeNodeProvider
 *  com.jiuqi.nr.itreebase.source.search.ISearchNodeProvider
 *  javax.annotation.Resource
 */
package com.jiuiqi.nr.unit.treebase.source.def;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper;
import com.jiuiqi.nr.unit.treebase.entity.provider.DefaultTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.entity.query.UnitTreeEntityDataQuery;
import com.jiuiqi.nr.unit.treebase.entity.search.SearchNodeWithMemory;
import com.jiuiqi.nr.unit.treebase.node.builder.BBLXUnitNodeBuilder;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeDataSource;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder;
import com.jiuiqi.nr.unit.treebase.source.def.DefaultSelectFirstNodeProvider;
import com.jiuiqi.nr.unit.treebase.source.def.DefaultTreeNodeBuilder;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceScheme;
import com.jiuqi.nr.itreebase.source.ITreeNodeProvider;
import com.jiuqi.nr.itreebase.source.search.ISearchNodeProvider;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component(value="def-tree-entity-row-scheme-v2")
public class DefaultUnitTreeSource
implements IUnitTreeDataSource {
    public static final String SOURCE_ID = "def-tree-entity-row-scheme";
    @Resource
    private IUnitTreeContextWrapper contextWrapper;
    @Resource
    private UnitTreeEntityDataQuery entityDataQuery;

    @Override
    public String getSourceId() {
        return SOURCE_ID;
    }

    @Override
    public IconSourceScheme[] getNodeIconSource(IUnitTreeContext ctx) {
        return new IconSourceScheme[]{this.contextWrapper.getBBLXIConSourceScheme(ctx.getEntityDefine())};
    }

    @Override
    public ITreeNodeProvider getTreeNodeProvider(IUnitTreeContext ctx) {
        IUnitTreeEntityRowProvider entityRowProvider = this.getUnitTreeEntityRowProvider(ctx);
        IUnitTreeNodeBuilder nodeBuilder = this.getNodeBuilder(ctx, entityRowProvider);
        return new DefaultSelectFirstNodeProvider(entityRowProvider, nodeBuilder, ctx.getActionNode());
    }

    @Override
    public IUnitTreeNodeBuilder getNodeBuilder(IUnitTreeContext context, IUnitTreeEntityRowProvider dimRowProvider) {
        IconSourceProvider iconProvider = context.getIconProvider();
        IUnitTreeNodeBuilder nodeBuilder = new DefaultTreeNodeBuilder(dimRowProvider, iconProvider);
        IEntityRefer referBBLXEntity = this.contextWrapper.getBBLXEntityRefer(context.getEntityDefine());
        if (referBBLXEntity != null) {
            nodeBuilder = new BBLXUnitNodeBuilder(nodeBuilder, iconProvider, referBBLXEntity);
        }
        return nodeBuilder;
    }

    @Override
    public ISearchNodeProvider getSearchDataProvider(IUnitTreeContext ctx) {
        return new SearchNodeWithMemory(this.getUnitTreeEntityRowProvider(ctx));
    }

    @Override
    public IUnitTreeEntityRowProvider getUnitTreeEntityRowProvider(IUnitTreeContext ctx) {
        return new DefaultTreeEntityRowProvider(ctx, this.entityDataQuery);
    }
}

