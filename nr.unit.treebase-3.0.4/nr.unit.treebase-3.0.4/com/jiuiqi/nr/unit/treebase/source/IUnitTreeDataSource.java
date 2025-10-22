/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.itreebase.nodeicon.IconSourceScheme
 *  com.jiuqi.nr.itreebase.nodeicon.impl.IconSourceSchemeOfDefault
 *  com.jiuqi.nr.itreebase.source.ITreeNodeProvider
 *  com.jiuqi.nr.itreebase.source.search.ISearchNodeProvider
 */
package com.jiuiqi.nr.unit.treebase.source;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.entity.counter2.IUnitTreeNodeCounter;
import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder;
import com.jiuiqi.nr.unit.treebase.source.def.DefaultTreeNodeBuilder;
import com.jiuiqi.nr.unit.treebase.source.def.DefaultTreeNodeProvider;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceScheme;
import com.jiuqi.nr.itreebase.nodeicon.impl.IconSourceSchemeOfDefault;
import com.jiuqi.nr.itreebase.source.ITreeNodeProvider;
import com.jiuqi.nr.itreebase.source.search.ISearchNodeProvider;
import java.util.HashMap;
import java.util.Map;

public interface IUnitTreeDataSource {
    public String getSourceId();

    default public Map<String, Object> getStaticResource(IUnitTreeContext ctx) {
        return new HashMap<String, Object>();
    }

    default public IconSourceScheme[] getNodeIconSource(IUnitTreeContext ctx) {
        return new IconSourceScheme[]{new IconSourceSchemeOfDefault()};
    }

    default public ITreeNodeProvider getTreeNodeProvider(IUnitTreeContext ctx) {
        IUnitTreeEntityRowProvider dimRowProvider = this.getUnitTreeEntityRowProvider(ctx);
        IUnitTreeNodeBuilder nodeBuilder = this.getNodeBuilder(ctx, dimRowProvider);
        return new DefaultTreeNodeProvider(dimRowProvider, nodeBuilder, ctx.getITreeContext().getActionNode());
    }

    default public IUnitTreeNodeBuilder getNodeBuilder(IUnitTreeContext ctx, IUnitTreeEntityRowProvider dimRowProvider) {
        return new DefaultTreeNodeBuilder(dimRowProvider, ctx.getIconProvider());
    }

    default public IUnitTreeNodeCounter getNodeCounter(IUnitTreeContext ctx) {
        return null;
    }

    public ISearchNodeProvider getSearchDataProvider(IUnitTreeContext var1);

    public IUnitTreeEntityRowProvider getUnitTreeEntityRowProvider(IUnitTreeContext var1);
}

