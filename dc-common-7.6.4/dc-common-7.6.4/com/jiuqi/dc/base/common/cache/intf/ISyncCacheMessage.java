/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.cache.intf;

import com.jiuqi.dc.base.common.cache.intf.ICacheDefine;

public interface ISyncCacheMessage {
    public String getId();

    public ICacheDefine getCacheDefine();

    public Object getCache();
}

