/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.itreebase.cache;

import java.io.Serializable;

public interface ITreeCacheArea {
    public boolean contains(String var1);

    public <T extends Serializable> T getCacheData(String var1, Class<T> var2);

    public <T extends Serializable> void putCacheData(String var1, T var2);
}

