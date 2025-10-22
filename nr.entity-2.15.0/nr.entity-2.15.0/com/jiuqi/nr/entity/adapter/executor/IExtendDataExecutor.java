/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.adapter.executor;

import com.jiuqi.nr.entity.adapter.EntityDataAdapter;
import com.jiuqi.nr.entity.adapter.provider.IDataModifyProvider;
import com.jiuqi.nr.entity.adapter.provider.IDataQueryProvider;
import com.jiuqi.nr.entity.adapter.provider.IDefineQueryProvider;
import com.jiuqi.nr.entity.adapter.provider.ProviderMethodEnum;
import com.jiuqi.nr.entity.param.IEntityQueryParam;

public interface IExtendDataExecutor<T extends EntityDataAdapter> {
    public boolean isEnable(IEntityQueryParam var1, ProviderMethodEnum var2);

    public double getOrder();

    public IDataQueryProvider getDataQueryProvider(IEntityQueryParam var1);

    public IDataModifyProvider getDataModifyProvider();

    @Deprecated
    public IDefineQueryProvider getOrgDefineProvider();

    default public IDefineQueryProvider getDefineProvider() {
        return this.getOrgDefineProvider();
    }
}

