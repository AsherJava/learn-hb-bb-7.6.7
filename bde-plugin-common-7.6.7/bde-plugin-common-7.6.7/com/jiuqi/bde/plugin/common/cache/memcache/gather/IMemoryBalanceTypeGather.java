/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.plugin.common.cache.memcache.gather;

import com.jiuqi.bde.plugin.common.cache.memcache.gather.IMemoryBalanceType;
import java.util.List;

public interface IMemoryBalanceTypeGather {
    public IMemoryBalanceType getMemoryBalanceType(String var1);

    public List<IMemoryBalanceType> list();
}

