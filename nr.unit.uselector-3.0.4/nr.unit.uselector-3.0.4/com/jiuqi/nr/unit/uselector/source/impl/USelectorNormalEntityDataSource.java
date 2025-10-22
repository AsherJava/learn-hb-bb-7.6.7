/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper
 *  com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider
 *  com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery
 *  com.jiuiqi.nr.unit.treebase.source.org.UnitTreeOrgDataSource
 *  com.jiuqi.nr.unit.treestore.systemconfig.UnitTreeSystemConfig
 */
package com.jiuqi.nr.unit.uselector.source.impl;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper;
import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery;
import com.jiuiqi.nr.unit.treebase.source.org.UnitTreeOrgDataSource;
import com.jiuqi.nr.unit.treestore.systemconfig.UnitTreeSystemConfig;
import com.jiuqi.nr.unit.uselector.source.impl.USelectorNormalEntityRowProvider;

public class USelectorNormalEntityDataSource
extends UnitTreeOrgDataSource {
    public USelectorNormalEntityDataSource(IUnitTreeContextWrapper contextWrapper, IUnitTreeEntityDataQuery entityDataQuery, UnitTreeSystemConfig unitTreeSystemConfig) {
        super(contextWrapper, entityDataQuery, unitTreeSystemConfig);
    }

    public IUnitTreeEntityRowProvider getUnitTreeEntityRowProvider(IUnitTreeContext ctx) {
        return new USelectorNormalEntityRowProvider(ctx, this.entityDataQuery);
    }
}

