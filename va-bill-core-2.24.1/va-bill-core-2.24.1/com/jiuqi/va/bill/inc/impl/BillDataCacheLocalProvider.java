/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.benmanes.caffeine.cache.Cache
 */
package com.jiuqi.va.bill.inc.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.jiuqi.va.bill.inc.intf.BillCacheMode;
import com.jiuqi.va.bill.inc.intf.BillDataCacheProvider;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class BillDataCacheLocalProvider
implements BillDataCacheProvider {
    @Qualifier(value="billCaffeineCache")
    @Autowired
    private Cache<String, Object> cache;

    @Override
    public int getCacheType() {
        return BillCacheMode.LOCAL.getValue();
    }

    @Override
    public Object get(String key) {
        return this.cache.getIfPresent((Object)key);
    }

    @Override
    public void put(String key, Map<String, Object> value) {
        this.cache.put((Object)key, value);
    }

    @Override
    public void clear(String key) {
        this.cache.invalidate((Object)key);
    }

    @Override
    public void refresh(String key) {
        this.get(key);
    }
}

