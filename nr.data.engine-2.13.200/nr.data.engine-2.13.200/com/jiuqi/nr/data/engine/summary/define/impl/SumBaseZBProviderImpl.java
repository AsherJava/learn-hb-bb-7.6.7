/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.engine.summary.define.impl;

import com.jiuqi.nr.data.engine.summary.define.ISumBaseZBProvider;
import com.jiuqi.nr.data.engine.summary.define.SumBaseZB;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SumBaseZBProviderImpl
implements ISumBaseZBProvider {
    @Override
    public String getType() {
        return "nr";
    }

    @Override
    public SumBaseZB getSumZBByName(Object tableName, Object zbName) {
        return null;
    }

    @Override
    public List<SumBaseZB> findZB(Object key) {
        return null;
    }
}

