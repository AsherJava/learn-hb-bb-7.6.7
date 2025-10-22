/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider
 *  com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery
 *  com.jiuiqi.nr.unit.treebase.entity.query.UnitTreeEntityDataQuery
 *  com.jiuiqi.nr.unit.treebase.source.IUnitTreeDataSource
 *  com.jiuqi.nr.itreebase.source.ITreeNodeProvider
 *  com.jiuqi.nr.itreebase.source.search.ISearchNodeProvider
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.unit.treeimpl.tree.onenode;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery;
import com.jiuiqi.nr.unit.treebase.entity.query.UnitTreeEntityDataQuery;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeDataSource;
import com.jiuqi.nr.itreebase.source.ITreeNodeProvider;
import com.jiuqi.nr.itreebase.source.search.ISearchNodeProvider;
import com.jiuqi.nr.unit.treeimpl.tree.onenode.OneEntityRowProvider;
import com.jiuqi.nr.unit.treeimpl.tree.onenode.OneNodeTreeDataProvider;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class OneNodeDataSourceImpl
implements IUnitTreeDataSource {
    public static final String SOURCE_ID = "only-one-node-tree-scheme";
    @Resource
    private UnitTreeEntityDataQuery entityDataQuery;

    public String getSourceId() {
        return SOURCE_ID;
    }

    public ITreeNodeProvider getTreeNodeProvider(IUnitTreeContext ctx) {
        IUnitTreeEntityRowProvider entityRowProvider = this.getUnitTreeEntityRowProvider(ctx);
        return new OneNodeTreeDataProvider(ctx, entityRowProvider);
    }

    public ISearchNodeProvider getSearchDataProvider(IUnitTreeContext ctx) {
        return null;
    }

    public IUnitTreeEntityRowProvider getUnitTreeEntityRowProvider(IUnitTreeContext ctx) {
        return new OneEntityRowProvider(ctx, (IUnitTreeEntityDataQuery)this.entityDataQuery);
    }
}

