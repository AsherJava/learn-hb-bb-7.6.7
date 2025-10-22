/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.entity.query.UnitTreeEntityDataQuery
 *  com.jiuqi.nr.data.logic.facade.service.ICheckResultService
 *  com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil
 *  com.jiuqi.nr.unit.uselector.source.IUSelectorDataSource
 *  com.jiuqi.nr.unit.uselector.source.IUSelectorEntityRowProvider
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.dataentry.filter.checkResultUnitFilter;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.entity.query.UnitTreeEntityDataQuery;
import com.jiuqi.nr.data.logic.facade.service.ICheckResultService;
import com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.dataentry.filter.checkResultUnitFilter.CheckResultUnitSelectorEntityRowProvider;
import com.jiuqi.nr.unit.uselector.source.IUSelectorDataSource;
import com.jiuqi.nr.unit.uselector.source.IUSelectorEntityRowProvider;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CheckResultUnitSelectorDataSource
implements IUSelectorDataSource {
    @Resource
    private UnitTreeEntityDataQuery entityDataQuery;
    @Autowired
    private DimensionCollectionUtil dimensionCollectionUtil;
    @Autowired
    private ICheckResultService checkResultService;

    public String getSourceId() {
        return "checkResult-unit-selector-tree-data-source";
    }

    public IUSelectorEntityRowProvider getUSelectorEntityRowProvider(IUnitTreeContext ctx) {
        return new CheckResultUnitSelectorEntityRowProvider(ctx, this.entityDataQuery, this.dimensionCollectionUtil, this.checkResultService);
    }
}

