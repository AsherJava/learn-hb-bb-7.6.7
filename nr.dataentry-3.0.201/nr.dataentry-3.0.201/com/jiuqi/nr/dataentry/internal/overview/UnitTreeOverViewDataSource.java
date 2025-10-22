/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper
 *  com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider
 *  com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery
 *  com.jiuiqi.nr.unit.treebase.entity.query.UnitTreeEntityDataQuery
 *  com.jiuiqi.nr.unit.treebase.source.IUnitTreeDataSource
 *  com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder
 *  com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider
 *  com.jiuqi.nr.itreebase.nodeicon.IconSourceScheme
 *  com.jiuqi.nr.itreebase.source.ITreeNodeProvider
 *  com.jiuqi.nr.itreebase.source.search.ISearchNodeProvider
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.dataentry.internal.overview;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper;
import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery;
import com.jiuiqi.nr.unit.treebase.entity.query.UnitTreeEntityDataQuery;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeDataSource;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder;
import com.jiuqi.nr.dataentry.internal.overview.OverViewTreeNodeBuilder;
import com.jiuqi.nr.dataentry.internal.overview.OverviewSearchNodeWithMemory;
import com.jiuqi.nr.dataentry.internal.overview.OverviewSelectFirstNodeProvider;
import com.jiuqi.nr.dataentry.internal.overview.OverviewTreeEntityRowProvider;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceScheme;
import com.jiuqi.nr.itreebase.source.ITreeNodeProvider;
import com.jiuqi.nr.itreebase.source.search.ISearchNodeProvider;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component(value="def-virtual-tree-entity-row-scheme-v2")
public class UnitTreeOverViewDataSource
implements IUnitTreeDataSource {
    public static final String DATASOURCEID = "def-virtual-tree-entity-row-scheme-v2";
    @Resource
    private IUnitTreeContextWrapper contextWrapper;
    @Resource
    private UnitTreeEntityDataQuery entityDataQuery;

    public String getSourceId() {
        return DATASOURCEID;
    }

    public IconSourceScheme[] getNodeIconSource(IUnitTreeContext ctx) {
        return new IconSourceScheme[]{this.contextWrapper.getBBLXIConSourceScheme(ctx.getEntityDefine())};
    }

    public ITreeNodeProvider getTreeNodeProvider(IUnitTreeContext ctx) {
        IUnitTreeEntityRowProvider iUnitTreeEntityRowProvider = this.getUnitTreeEntityRowProvider(ctx);
        IUnitTreeNodeBuilder nodeBuilder = this.getNodeBuilder(ctx, iUnitTreeEntityRowProvider);
        return new OverviewSelectFirstNodeProvider(iUnitTreeEntityRowProvider, nodeBuilder, ctx.getActionNode(), ctx);
    }

    public IUnitTreeNodeBuilder getNodeBuilder(IUnitTreeContext context, IUnitTreeEntityRowProvider dimRowProvider) {
        IconSourceProvider iconProvider = context.getIconProvider();
        OverViewTreeNodeBuilder nodeBuilder = new OverViewTreeNodeBuilder(dimRowProvider, iconProvider);
        return nodeBuilder;
    }

    public ISearchNodeProvider getSearchDataProvider(IUnitTreeContext ctx) {
        return new OverviewSearchNodeWithMemory(this.getUnitTreeEntityRowProvider(ctx));
    }

    public IUnitTreeEntityRowProvider getUnitTreeEntityRowProvider(IUnitTreeContext ctx) {
        return new OverviewTreeEntityRowProvider(ctx, (IUnitTreeEntityDataQuery)this.entityDataQuery);
    }
}

