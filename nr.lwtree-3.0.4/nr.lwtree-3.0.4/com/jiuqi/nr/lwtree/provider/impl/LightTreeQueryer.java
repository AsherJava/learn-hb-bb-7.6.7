/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 */
package com.jiuqi.nr.lwtree.provider.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.lwtree.para.ITreeParamsInitializer;
import com.jiuqi.nr.lwtree.query.IEntityRowQueryerImpl;
import com.jiuqi.nr.lwtree.request.LightTreeLoadParam;

public class LightTreeQueryer
extends IEntityRowQueryerImpl {
    public LightTreeQueryer(ITreeParamsInitializer loadInfo, LightTreeLoadParam ltParam) {
        super(loadInfo);
    }

    @Override
    public IEntityTable getIEntityTable() {
        if (this.rs == null) {
            String period = this.loadInfo.getPeriod();
            String periodDimName = this.loadInfo.getPeriodDimName();
            DimensionValueSet valueSet = new DimensionValueSet();
            valueSet.setValue(periodDimName, (Object)period);
            this.rs = this.dataEngineMgr.makeIEntityTable(this.loadInfo);
        }
        return this.rs;
    }
}

