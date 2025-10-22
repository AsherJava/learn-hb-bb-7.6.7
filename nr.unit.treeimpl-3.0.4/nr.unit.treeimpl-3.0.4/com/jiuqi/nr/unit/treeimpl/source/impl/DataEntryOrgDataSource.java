/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper
 *  com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeFilterCondition
 *  com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeNodeRangeParam
 *  com.jiuiqi.nr.unit.treebase.entity.counter2.IUnitTreeNodeCounter
 *  com.jiuiqi.nr.unit.treebase.entity.provider.AsyncUnitTreeEntityRowProvider
 *  com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider
 *  com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery
 *  com.jiuiqi.nr.unit.treebase.entity.search.SearchNodeWithMemory
 *  com.jiuiqi.nr.unit.treebase.enumeration.UnitTreeRangeDisplay
 *  com.jiuiqi.nr.unit.treebase.node.builder.RangeListNodeBuilder
 *  com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder
 *  com.jiuiqi.nr.unit.treebase.source.def.AsyncTreeNodeBuilder
 *  com.jiuiqi.nr.unit.treebase.source.org.UnitTreeOrgDataSource
 *  com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider
 *  com.jiuqi.nr.itreebase.source.search.ISearchNodeProvider
 *  com.jiuqi.nr.unit.treestore.systemconfig.UnitTreeSystemConfig
 *  org.json.JSONObject
 */
package com.jiuqi.nr.unit.treeimpl.source.impl;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper;
import com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeFilterCondition;
import com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeNodeRangeParam;
import com.jiuiqi.nr.unit.treebase.entity.counter2.IUnitTreeNodeCounter;
import com.jiuiqi.nr.unit.treebase.entity.provider.AsyncUnitTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery;
import com.jiuiqi.nr.unit.treebase.entity.search.SearchNodeWithMemory;
import com.jiuiqi.nr.unit.treebase.enumeration.UnitTreeRangeDisplay;
import com.jiuiqi.nr.unit.treebase.node.builder.RangeListNodeBuilder;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder;
import com.jiuiqi.nr.unit.treebase.source.def.AsyncTreeNodeBuilder;
import com.jiuiqi.nr.unit.treebase.source.org.UnitTreeOrgDataSource;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider;
import com.jiuqi.nr.itreebase.source.search.ISearchNodeProvider;
import com.jiuqi.nr.unit.treeimpl.filter.FilterWithChildTreeProvider;
import com.jiuqi.nr.unit.treeimpl.filter.UnitTreeNodeFilter;
import com.jiuqi.nr.unit.treestore.systemconfig.UnitTreeSystemConfig;
import java.util.Objects;
import org.json.JSONObject;

public class DataEntryOrgDataSource
extends UnitTreeOrgDataSource {
    public DataEntryOrgDataSource(IUnitTreeContextWrapper contextWrapper, IUnitTreeEntityDataQuery entityDataQuery, UnitTreeSystemConfig unitTreeSystemConfig) {
        super(contextWrapper, entityDataQuery, unitTreeSystemConfig);
    }

    public IUnitTreeNodeBuilder getNodeBuilder(IUnitTreeContext context, IUnitTreeEntityRowProvider entityRowProvider) {
        IconSourceProvider iconProvider = context.getIconProvider();
        AsyncTreeNodeBuilder nodeBuilder = new AsyncTreeNodeBuilder(entityRowProvider, iconProvider);
        IUnitTreeNodeBuilder baseNodeBuilder = this.nodeBuilderHelper.getNodeBuilder(context, entityRowProvider, (IUnitTreeNodeBuilder)nodeBuilder);
        UnitTreeNodeRangeParam rangeParam = UnitTreeNodeRangeParam.translate2EntityRangeParam((JSONObject)context.getCustomVariable());
        if (null != rangeParam && rangeParam.isValidParam()) {
            UnitTreeRangeDisplay rangeType = UnitTreeRangeDisplay.toDisplay((String)rangeParam.getRangeType());
            switch (Objects.requireNonNull(rangeType)) {
                case RANGE_WITH_ROOTS: 
                case RANGE_WITH_TREE: 
                case RANGE_WITH_LIST: {
                    baseNodeBuilder = new RangeListNodeBuilder(baseNodeBuilder);
                }
            }
        }
        return baseNodeBuilder;
    }

    public IUnitTreeNodeCounter getNodeCounter(IUnitTreeContext ctx) {
        return super.getNodeCounter(ctx);
    }

    public IUnitTreeEntityRowProvider getUnitTreeEntityRowProvider(IUnitTreeContext ctx) {
        UnitTreeNodeRangeParam rangeParam = UnitTreeNodeRangeParam.translate2EntityRangeParam((JSONObject)ctx.getCustomVariable());
        UnitTreeFilterCondition filterCondition = UnitTreeFilterCondition.translate2FilterCondition((JSONObject)ctx.getCustomVariable());
        Object provider = null != rangeParam && rangeParam.isValidParam() ? this.getRangeEntityRowProvider(ctx, rangeParam) : new AsyncUnitTreeEntityRowProvider(ctx, this.entityDataQuery, this.contextWrapper);
        if (filterCondition != null && filterCondition.isValidFilter()) {
            UnitTreeNodeFilter filter = new UnitTreeNodeFilter(ctx, filterCondition);
            provider = new FilterWithChildTreeProvider((IUnitTreeEntityRowProvider)provider, filter);
        }
        return provider;
    }

    public ISearchNodeProvider getSearchDataProvider(IUnitTreeContext ctx) {
        UnitTreeFilterCondition filterCondition = UnitTreeFilterCondition.translate2FilterCondition((JSONObject)ctx.getCustomVariable());
        if (filterCondition != null && filterCondition.isValidFilter()) {
            UnitTreeNodeFilter filter = new UnitTreeNodeFilter(ctx, filterCondition);
            IUnitTreeEntityRowProvider provider = super.getUnitTreeEntityRowProvider(ctx);
            provider = new FilterWithChildTreeProvider(provider, filter);
            return new SearchNodeWithMemory(provider);
        }
        return super.getSearchDataProvider(ctx);
    }
}

