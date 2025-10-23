/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.core.DataWrapper
 */
package com.jiuqi.nr.param.transfer.cache;

import com.jiuqi.nr.datascheme.api.core.DataWrapper;
import java.io.Serializable;

public interface TransferCacheService {
    public <T> DataWrapper<T> get(String var1, String var2, Class<T> var3);

    public <T extends Serializable> void put(String var1, String var2, T var3);
}

