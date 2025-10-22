/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.entity.counter2.IUnitTreeNodeCounter
 *  com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider
 *  com.jiuiqi.nr.unit.treebase.source.IUnitTreeDataSource
 *  com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder
 *  com.jiuqi.nr.dataentity_ext.api.IEntityDataService
 *  com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider
 *  com.jiuqi.nr.itreebase.source.search.ISearchNodeProvider
 *  com.jiuqi.nr.unit.uselector.cacheset.USelectorResultSet
 *  javax.annotation.Resource
 *  org.json.JSONObject
 */
package com.jiuqi.nr.dataentry.filter.unitextfilter;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.entity.counter2.IUnitTreeNodeCounter;
import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeDataSource;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder;
import com.jiuqi.nr.dataentity_ext.api.IEntityDataService;
import com.jiuqi.nr.dataentry.filter.unitextfilter.UnitExtSearchNodeProvider;
import com.jiuqi.nr.dataentry.filter.unitextfilter.UnitExtTreeEntityRowProvider;
import com.jiuqi.nr.dataentry.filter.unitextfilter.UnitExtTreeNodeBuilder;
import com.jiuqi.nr.dataentry.filter.unitextfilter.UnitExtTreeNodeCounter;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider;
import com.jiuqi.nr.itreebase.source.search.ISearchNodeProvider;
import com.jiuqi.nr.unit.uselector.cacheset.USelectorResultSet;
import java.util.List;
import javax.annotation.Resource;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UnitExtTreeDataSource
implements IUnitTreeDataSource {
    @Resource
    private USelectorResultSet cacheSet;
    @Autowired
    private IEntityDataService iEntityDataService;

    public String getSourceId() {
        return "unitext-selector-tree-data-source";
    }

    public ISearchNodeProvider getSearchDataProvider(IUnitTreeContext ctx) {
        return new UnitExtSearchNodeProvider(ctx, this.iEntityDataService);
    }

    public IUnitTreeEntityRowProvider getUnitTreeEntityRowProvider(IUnitTreeContext ctx) {
        return new UnitExtTreeEntityRowProvider(ctx, this.iEntityDataService);
    }

    public IUnitTreeNodeBuilder getNodeBuilder(IUnitTreeContext ctx, IUnitTreeEntityRowProvider dimRowProvider) {
        IconSourceProvider iconProvider = ctx.getIconProvider();
        List checklist = this.cacheSet.getFilterSet(this.getSelectorKey(ctx));
        return new UnitExtTreeNodeBuilder(dimRowProvider, iconProvider, checklist, ctx);
    }

    public IUnitTreeNodeCounter getNodeCounter(IUnitTreeContext ctx) {
        return new UnitExtTreeNodeCounter(ctx, this.iEntityDataService);
    }

    private String getSelectorKey(IUnitTreeContext ctx) {
        JSONObject customVariable = ctx.getCustomVariable();
        return customVariable.getString("selectorKey");
    }
}

