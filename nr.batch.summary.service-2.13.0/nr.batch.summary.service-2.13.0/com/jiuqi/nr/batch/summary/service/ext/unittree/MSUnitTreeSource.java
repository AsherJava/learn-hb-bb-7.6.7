/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider
 *  com.jiuiqi.nr.unit.treebase.source.IUnitTreeDataSource
 *  com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider
 *  com.jiuqi.nr.itreebase.source.ITreeNodeProvider
 *  com.jiuqi.nr.itreebase.source.search.ISearchNodeProvider
 */
package com.jiuqi.nr.batch.summary.service.ext.unittree;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeDataSource;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder;
import com.jiuqi.nr.batch.summary.service.ext.unittree.BSEntityRowQuery;
import com.jiuqi.nr.batch.summary.service.ext.unittree.MSEntityRowProvider;
import com.jiuqi.nr.batch.summary.service.ext.unittree.MSTreeDataProvider;
import com.jiuqi.nr.batch.summary.service.ext.unittree.MSTreeNodeBuilder;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider;
import com.jiuqi.nr.itreebase.source.ITreeNodeProvider;
import com.jiuqi.nr.itreebase.source.search.ISearchNodeProvider;
import java.util.HashMap;

public class MSUnitTreeSource
implements IUnitTreeDataSource {
    private SummaryScheme scheme;
    private BSEntityRowQuery entityRowQuery;

    public MSUnitTreeSource(SummaryScheme scheme, BSEntityRowQuery entityRowQuery) {
        this.scheme = scheme;
        this.entityRowQuery = entityRowQuery;
    }

    public String getSourceId() {
        return "more.summary.scheme.unit.tree.source";
    }

    public ITreeNodeProvider getTreeNodeProvider(IUnitTreeContext ctx) {
        IUnitTreeEntityRowProvider entityRowProvider = this.getUnitTreeEntityRowProvider(ctx);
        IUnitTreeNodeBuilder nodeBuilder = this.getNodeBuilder(ctx, entityRowProvider);
        return new MSTreeDataProvider(entityRowProvider, nodeBuilder, ctx.getActionNode());
    }

    public IUnitTreeNodeBuilder getNodeBuilder(IUnitTreeContext ctx, IUnitTreeEntityRowProvider entityRowProvider) {
        IconSourceProvider iconProvider = ctx.getIconProvider();
        return new MSTreeNodeBuilder(this.scheme, entityRowProvider, iconProvider);
    }

    public ISearchNodeProvider getSearchDataProvider(IUnitTreeContext ctx) {
        return null;
    }

    public IUnitTreeEntityRowProvider getUnitTreeEntityRowProvider(IUnitTreeContext ctx) {
        HashMap<String, Object> customVariableMap = new HashMap<String, Object>();
        customVariableMap.put("dimType", this.scheme.getTargetDim().getTargetDimType().value + "");
        customVariableMap.put("dimValue", this.scheme.getTargetDim().getDimValue());
        customVariableMap.put("batchGatherSchemeCode", this.scheme.getKey());
        IEntityTable entityTable = this.entityRowQuery.makeExecuteReader(ctx, customVariableMap);
        return new MSEntityRowProvider(this.scheme, entityTable);
    }
}

