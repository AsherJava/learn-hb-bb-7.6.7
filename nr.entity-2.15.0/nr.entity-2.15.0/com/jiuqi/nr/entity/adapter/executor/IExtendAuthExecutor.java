/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.adapter.executor;

import com.jiuqi.nr.entity.adapter.EntityDataAdapter;
import com.jiuqi.nr.entity.adapter.provider.IEntityAuthProvider;
import java.util.Date;

public interface IExtendAuthExecutor<T extends EntityDataAdapter> {
    public boolean isEnable(String var1);

    public double getOrder();

    public IEntityAuthProvider getProvider(String var1, Date var2);
}

