/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.FIFOCache
 */
package com.jiuqi.np.definition.common;

import com.jiuqi.np.cache.FIFOCache;
import java.util.UUID;

public class UUIDStringPool {
    public static final UUIDStringPool INSTANCE = new UUIDStringPool();
    private final FIFOCache<UUID, String> cache = new FIFOCache(0x100000L);

    private UUIDStringPool() {
    }

    public String getString(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("'id' must not be null.");
        }
        String idString = (String)this.cache.get((Object)id);
        if (idString == null) {
            idString = id.toString();
            this.cache.put((Object)id, (Object)idString);
        }
        return idString;
    }

    public static void main(String[] args) {
    }
}

