/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper
 *  com.jiuiqi.nr.unit.treebase.entity.counter2.IUnitTreeNodeCounter
 *  com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider
 *  com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery
 *  com.jiuiqi.nr.unit.treebase.entity.query.UnitTreeEntityDataQuery
 *  com.jiuiqi.nr.unit.treebase.source.IUnitTreeDataSource
 *  com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder
 *  com.jiuiqi.nr.unit.treebase.source.basedata.UnitTreeBaseDataSource
 *  com.jiuiqi.nr.unit.treebase.source.org.UnitTreeOrgDataSource
 *  com.jiuqi.nr.itreebase.nodeicon.IconSourceScheme
 *  com.jiuqi.nr.itreebase.source.ITreeNodeProvider
 *  com.jiuqi.nr.itreebase.source.search.ISearchNodeProvider
 *  com.jiuqi.nr.unit.treestore.systemconfig.UnitTreeSystemConfig
 *  javax.annotation.Resource
 *  org.json.JSONObject
 */
package com.jiuqi.nr.unit.uselector.source.impl;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper;
import com.jiuiqi.nr.unit.treebase.entity.counter2.IUnitTreeNodeCounter;
import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery;
import com.jiuiqi.nr.unit.treebase.entity.query.UnitTreeEntityDataQuery;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeDataSource;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder;
import com.jiuiqi.nr.unit.treebase.source.basedata.UnitTreeBaseDataSource;
import com.jiuiqi.nr.unit.treebase.source.org.UnitTreeOrgDataSource;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceScheme;
import com.jiuqi.nr.itreebase.source.ITreeNodeProvider;
import com.jiuqi.nr.itreebase.source.search.ISearchNodeProvider;
import com.jiuqi.nr.unit.treestore.systemconfig.UnitTreeSystemConfig;
import com.jiuqi.nr.unit.uselector.cacheset.USelectorResultSet;
import com.jiuqi.nr.unit.uselector.source.impl.USelectorTreeNodeProvider;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class USelectorTreeDataSource
implements IUnitTreeDataSource {
    public static final String SOURCE_ID = "unit-selector-tree-data-source";
    @Resource
    private USelectorResultSet cacheSet;
    @Resource
    private IUnitTreeContextWrapper contextWrapper;
    @Resource
    private UnitTreeEntityDataQuery entityDataQuery;
    @Resource
    private UnitTreeSystemConfig unitTreeSystemConfig;

    public String getSourceId() {
        return SOURCE_ID;
    }

    public Map<String, Object> getStaticResource(IUnitTreeContext ctx) {
        return this.getDataSource(ctx).getStaticResource(ctx);
    }

    public IconSourceScheme[] getNodeIconSource(IUnitTreeContext ctx) {
        return this.getDataSource(ctx).getNodeIconSource(ctx);
    }

    public ITreeNodeProvider getTreeNodeProvider(IUnitTreeContext ctx) {
        List<String> checklist = this.cacheSet.getFilterSet(this.getSelectorKey(ctx));
        return new USelectorTreeNodeProvider(this.getDataSource(ctx).getTreeNodeProvider(ctx), checklist);
    }

    public IUnitTreeNodeBuilder getNodeBuilder(IUnitTreeContext ctx, IUnitTreeEntityRowProvider entityRowProvider) {
        return this.getDataSource(ctx).getNodeBuilder(ctx, entityRowProvider);
    }

    public IUnitTreeNodeCounter getNodeCounter(IUnitTreeContext ctx) {
        return this.getDataSource(ctx).getNodeCounter(ctx);
    }

    public ISearchNodeProvider getSearchDataProvider(IUnitTreeContext ctx) {
        return this.getDataSource(ctx).getSearchDataProvider(ctx);
    }

    public IUnitTreeEntityRowProvider getUnitTreeEntityRowProvider(IUnitTreeContext ctx) {
        return this.getDataSource(ctx).getUnitTreeEntityRowProvider(ctx);
    }

    private IUnitTreeDataSource getDataSource(IUnitTreeContext ctx) {
        UnitTreeOrgDataSource dataSource = new UnitTreeOrgDataSource(this.contextWrapper, (IUnitTreeEntityDataQuery)this.entityDataQuery, this.unitTreeSystemConfig);
        if (this.contextWrapper.hasDimGroupConfig(ctx.getTaskDefine())) {
            dataSource = new UnitTreeBaseDataSource(this.contextWrapper, (IUnitTreeEntityDataQuery)this.entityDataQuery, this.unitTreeSystemConfig);
        }
        return dataSource;
    }

    private String getSelectorKey(IUnitTreeContext ctx) {
        JSONObject customVariable = ctx.getCustomVariable();
        return customVariable.getString("selectorKey");
    }
}

