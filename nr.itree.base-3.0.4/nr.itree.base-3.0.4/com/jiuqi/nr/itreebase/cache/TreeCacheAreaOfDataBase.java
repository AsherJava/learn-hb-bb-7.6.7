/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.itreebase.cache;

import com.jiuqi.nr.itreebase.cache.ITreeCacheArea;
import java.io.Serializable;

public class TreeCacheAreaOfDataBase
implements ITreeCacheArea {
    @Override
    public boolean contains(String sourceId) {
        return false;
    }

    @Override
    public <T extends Serializable> T getCacheData(String sourceId, Class<T> clazz) {
        return null;
    }

    @Override
    public <T extends Serializable> void putCacheData(String sourceId, T contentData) {
    }
}

