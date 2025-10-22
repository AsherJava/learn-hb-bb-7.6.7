/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuqi.nr.dataentity_ext.api.IEntityDataService
 *  com.jiuqi.nr.unit.uselector.source.IUSelectorDataSource
 *  com.jiuqi.nr.unit.uselector.source.IUSelectorEntityRowProvider
 */
package com.jiuqi.nr.dataentry.filter.unitextfilter;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuqi.nr.dataentity_ext.api.IEntityDataService;
import com.jiuqi.nr.dataentry.filter.unitextfilter.UnitExtSelectorEntityRowProvider;
import com.jiuqi.nr.unit.uselector.source.IUSelectorDataSource;
import com.jiuqi.nr.unit.uselector.source.IUSelectorEntityRowProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UnitExtSelectorDataSource
implements IUSelectorDataSource {
    @Autowired
    private IEntityDataService iEntityDataService;

    public String getSourceId() {
        return "unitext-selector-tree-data-source";
    }

    public IUSelectorEntityRowProvider getUSelectorEntityRowProvider(IUnitTreeContext ctx) {
        return new UnitExtSelectorEntityRowProvider(ctx, this.iEntityDataService);
    }
}

