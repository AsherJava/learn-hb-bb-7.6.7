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
 *  com.jiuqi.nr.data.logic.facade.service.ICheckResultService
 *  com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil
 *  com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider
 *  com.jiuqi.nr.itreebase.source.search.ISearchNodeProvider
 *  com.jiuqi.nr.unit.treestore.systemconfig.UnitTreeSystemConfig
 *  com.jiuqi.nr.unit.uselector.cacheset.USelectorResultSet
 *  javax.annotation.Resource
 *  org.json.JSONObject
 */
package com.jiuqi.nr.dataentry.filter.checkResultUnitFilter;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper;
import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery;
import com.jiuiqi.nr.unit.treebase.entity.query.UnitTreeEntityDataQuery;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeDataSource;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder;
import com.jiuqi.nr.data.logic.facade.service.ICheckResultService;
import com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.dataentry.filter.checkResultUnitFilter.CheckResultUnitSearchNodeProvider;
import com.jiuqi.nr.dataentry.filter.checkResultUnitFilter.CheckResultUnitTreeEntityRowProvider;
import com.jiuqi.nr.dataentry.filter.checkResultUnitFilter.CheckResultUnitTreeNodeBuilder;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider;
import com.jiuqi.nr.itreebase.source.search.ISearchNodeProvider;
import com.jiuqi.nr.unit.treestore.systemconfig.UnitTreeSystemConfig;
import com.jiuqi.nr.unit.uselector.cacheset.USelectorResultSet;
import java.util.List;
import javax.annotation.Resource;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CheckResultUnitTreeDataSource
implements IUnitTreeDataSource {
    @Resource
    private USelectorResultSet cacheSet;
    @Resource
    private IUnitTreeContextWrapper contextWrapper;
    @Resource
    private UnitTreeSystemConfig unitTreeSystemConfig;
    @Resource
    private UnitTreeEntityDataQuery entityDataQuery;
    @Autowired
    private DimensionCollectionUtil dimensionCollectionUtil;
    @Autowired
    private ICheckResultService checkResultService;

    public String getSourceId() {
        return "checkResult-unit-selector-tree-data-source";
    }

    public ISearchNodeProvider getSearchDataProvider(IUnitTreeContext ctx) {
        return new CheckResultUnitSearchNodeProvider(this.getUnitTreeEntityRowProvider(ctx), ctx, this.dimensionCollectionUtil, this.checkResultService);
    }

    public IUnitTreeEntityRowProvider getUnitTreeEntityRowProvider(IUnitTreeContext ctx) {
        return new CheckResultUnitTreeEntityRowProvider(ctx, this.dimensionCollectionUtil, this.checkResultService, (IUnitTreeEntityDataQuery)this.entityDataQuery);
    }

    public IUnitTreeNodeBuilder getNodeBuilder(IUnitTreeContext ctx, IUnitTreeEntityRowProvider dimRowProvider) {
        IconSourceProvider iconProvider = ctx.getIconProvider();
        List checklist = this.cacheSet.getFilterSet(this.getSelectorKey(ctx));
        return new CheckResultUnitTreeNodeBuilder(dimRowProvider, iconProvider, checklist);
    }

    private String getSelectorKey(IUnitTreeContext ctx) {
        JSONObject customVariable = ctx.getCustomVariable();
        return customVariable.getString("selectorKey");
    }
}

