/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper
 *  com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery
 *  com.jiuiqi.nr.unit.treebase.source.basedata.UnitTreeBaseDataSource
 *  com.jiuqi.nr.unit.treestore.systemconfig.UnitTreeSystemConfig
 */
package com.jiuqi.nr.unit.treeimpl.source.impl;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper;
import com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery;
import com.jiuiqi.nr.unit.treebase.source.basedata.UnitTreeBaseDataSource;
import com.jiuqi.nr.unit.treestore.systemconfig.UnitTreeSystemConfig;

public class DataEntryBaseDataSource
extends UnitTreeBaseDataSource {
    public DataEntryBaseDataSource(IUnitTreeContextWrapper contextWrapper, IUnitTreeEntityDataQuery entityDataQuery, UnitTreeSystemConfig unitTreeSystemConfig) {
        super(contextWrapper, entityDataQuery, unitTreeSystemConfig);
    }
}

