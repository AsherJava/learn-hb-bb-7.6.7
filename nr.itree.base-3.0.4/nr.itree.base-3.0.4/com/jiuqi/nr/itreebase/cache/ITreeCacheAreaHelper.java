/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.itreebase.cache;

import com.jiuqi.nr.itreebase.cache.ITreeCacheArea;
import com.jiuqi.nr.itreebase.cache.ITreeCacheAreaType;
import java.io.Serializable;

public interface ITreeCacheAreaHelper {
    public ITreeCacheArea getCacheArea(ITreeCacheAreaType var1);

    public boolean contains(ITreeCacheAreaType var1, String var2);

    public <T extends Serializable> T getCacheData(ITreeCacheAreaType var1, String var2, Class<T> var3);

    public <T extends Serializable> void putCacheData(ITreeCacheAreaType var1, String var2, T var3);
}

