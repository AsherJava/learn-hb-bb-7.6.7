/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.data.redis.core.RedisTemplate
 */
package com.jiuqi.va.bill.inc.impl;

import com.jiuqi.va.bill.inc.intf.BillCacheMode;
import com.jiuqi.va.bill.inc.intf.BillDataCacheProvider;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class BillDataCacheRedisProvider
implements BillDataCacheProvider {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public int getCacheType() {
        return BillCacheMode.REDIS.getValue();
    }

    @Override
    public Object get(String key) {
        return this.redisTemplate.opsForValue().get((Object)key);
    }

    @Override
    public void put(String key, Map<String, Object> value) {
        this.redisTemplate.opsForValue().set((Object)key, value, 3L, TimeUnit.MINUTES);
    }

    @Override
    public void clear(String key) {
        this.redisTemplate.delete((Object)key);
    }

    @Override
    public void refresh(String key) {
        this.redisTemplate.expire((Object)key, 3L, TimeUnit.MINUTES);
    }
}

