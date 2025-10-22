/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextBuilder
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextCache
 *  com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeContextData
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.itreebase.collection.IFilterCacheSetHelper
 *  com.jiuqi.nr.itreebase.collection.IFilterStringList
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.unit.uselector.cacheset;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextBuilder;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextCache;
import com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeContextData;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.itreebase.collection.IFilterCacheSetHelper;
import com.jiuqi.nr.itreebase.collection.IFilterStringList;
import com.jiuqi.nr.unit.uselector.cacheset.USelectorResultSet;
import com.jiuqi.nr.unit.uselector.source.IUSelectorDataSource;
import com.jiuqi.nr.unit.uselector.source.IUSelectorDataSourceHelper;
import com.jiuqi.nr.unit.uselector.source.IUSelectorEntityRowProvider;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class USelectorResultSetImpl
implements USelectorResultSet {
    @Resource
    private IUnitTreeContextCache contextCache;
    @Resource
    private IFilterCacheSetHelper cacheSetHelper;
    @Resource
    private IUnitTreeContextBuilder contextBuilder;
    @Resource
    private IUSelectorDataSourceHelper sourceHelper;

    @Override
    public List<String> getFilterSet(String selector) {
        IFilterStringList cacheSetInRedis = this.cacheSetHelper.getInstance(selector);
        return cacheSetInRedis.toList();
    }

    @Override
    public List<IEntityRow> getFilterEntityRows(String selector) {
        List<String> rowKeys = this.getFilterSet(selector);
        UnitTreeContextData contextData = this.contextCache.getUnitTreeContextData(selector);
        IUnitTreeContext treeContext = this.contextBuilder.createTreeContext(contextData);
        IUSelectorDataSource dataSource = this.sourceHelper.getBaseTreeDataSource(treeContext.getDataSourceId());
        IUSelectorEntityRowProvider entityRowProvider = dataSource.getUSelectorEntityRowProvider(treeContext);
        return entityRowProvider.getCheckRows(rowKeys);
    }

    @Override
    public IUnitTreeContext getRunContext(String selector) {
        UnitTreeContextData contextData = this.contextCache.getUnitTreeContextData(selector);
        return this.contextBuilder.createTreeContext(contextData);
    }
}

