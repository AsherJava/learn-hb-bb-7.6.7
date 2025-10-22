/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.engine.dataquery;

import com.jiuqi.nr.data.engine.dataquery.IDataQueryProvider;
import com.jiuqi.nr.data.engine.dataquery.IMultiPeriodQuery;
import com.jiuqi.nr.data.engine.dataquery.MultiPeriodQueryImpl;
import org.springframework.stereotype.Component;

@Component
public class DataQueryProviderImpl
implements IDataQueryProvider {
    @Override
    public IMultiPeriodQuery newMultiPeriodQuery() {
        return new MultiPeriodQueryImpl();
    }
}

