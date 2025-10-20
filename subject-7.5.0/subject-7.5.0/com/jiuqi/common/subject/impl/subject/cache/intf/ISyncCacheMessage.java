/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.subject.impl.subject.cache.intf;

import com.jiuqi.common.subject.impl.subject.cache.intf.ICacheDefine;

public interface ISyncCacheMessage {
    public String getId();

    public ICacheDefine getCacheDefine();

    public Object getCache();
}

