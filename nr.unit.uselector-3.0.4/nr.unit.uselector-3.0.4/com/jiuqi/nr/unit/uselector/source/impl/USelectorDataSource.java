/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.entity.query.UnitTreeEntityDataQuery
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.unit.uselector.source.impl;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.entity.query.UnitTreeEntityDataQuery;
import com.jiuqi.nr.unit.uselector.source.IUSelectorDataSource;
import com.jiuqi.nr.unit.uselector.source.IUSelectorEntityRowProvider;
import com.jiuqi.nr.unit.uselector.source.impl.USelectorEntityRowProvider;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class USelectorDataSource
implements IUSelectorDataSource {
    @Resource
    private UnitTreeEntityDataQuery entityDataQuery;

    @Override
    public String getSourceId() {
        return "unit-selector-tree-data-source";
    }

    @Override
    public IUSelectorEntityRowProvider getUSelectorEntityRowProvider(IUnitTreeContext ctx) {
        return new USelectorEntityRowProvider(ctx, this.entityDataQuery);
    }
}

