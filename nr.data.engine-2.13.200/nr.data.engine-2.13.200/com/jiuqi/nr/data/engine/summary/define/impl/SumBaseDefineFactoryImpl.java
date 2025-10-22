/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.engine.summary.define.impl;

import com.jiuqi.nr.data.engine.summary.define.IRelationInfoProvider;
import com.jiuqi.nr.data.engine.summary.define.ISumBaseDefineFactory;
import com.jiuqi.nr.data.engine.summary.define.ISumBaseZBProvider;
import com.jiuqi.nr.data.engine.summary.define.impl.RelationInfoProviderImpl;
import com.jiuqi.nr.data.engine.summary.define.impl.SumBaseZBProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SumBaseDefineFactoryImpl
implements ISumBaseDefineFactory {
    @Autowired
    private RelationInfoProviderImpl relationInfoProvider;
    @Autowired
    private SumBaseZBProviderImpl sumBaseZBProvider;

    @Override
    public String getType() {
        return "nr";
    }

    @Override
    public ISumBaseZBProvider getZBProvider() {
        return this.sumBaseZBProvider;
    }

    @Override
    public IRelationInfoProvider getRelationInfoProvider() {
        return this.relationInfoProvider;
    }
}

