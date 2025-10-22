/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.itreebase.source;

import com.jiuqi.nr.itreebase.context.ITreeContext;
import com.jiuqi.nr.itreebase.source.INodeDataBuilder;
import com.jiuqi.nr.itreebase.source.INodeDataSource;
import com.jiuqi.nr.itreebase.source.ITreeNodeProvider;
import com.jiuqi.nr.itreebase.source.impl.NodeDataBuilderImpl;
import com.jiuqi.nr.itreebase.source.impl.TreeNodeProviderImpl;
import com.jiuqi.nr.itreebase.source.impl.TreeSearchProviderImpl;
import com.jiuqi.nr.itreebase.source.search.ISearchNodeProvider;

public interface ITreeDataSource {
    public String getSourceId();

    default public ITreeNodeProvider getTreeNodeProvider(ITreeContext context) {
        INodeDataSource dataProvider = this.getNodeDataSource(context);
        INodeDataBuilder nodeBuilder = this.getNodeBuilder(context, dataProvider);
        return new TreeNodeProviderImpl(dataProvider, nodeBuilder, context.getActionNode());
    }

    default public INodeDataBuilder getNodeBuilder(ITreeContext context, INodeDataSource dataSource) {
        return new NodeDataBuilderImpl(dataSource);
    }

    default public ISearchNodeProvider getSearchDataProvider(ITreeContext context) {
        INodeDataSource nodeDataSource = this.getNodeDataSource(context);
        return new TreeSearchProviderImpl(nodeDataSource);
    }

    public INodeDataSource getNodeDataSource(ITreeContext var1);
}

