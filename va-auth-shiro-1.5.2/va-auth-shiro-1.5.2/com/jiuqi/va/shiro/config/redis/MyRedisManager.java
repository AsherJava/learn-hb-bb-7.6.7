/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.data.redis.core.Cursor
 *  org.springframework.data.redis.core.RedisTemplate
 *  org.springframework.data.redis.core.ScanOptions
 */
package com.jiuqi.va.shiro.config.redis;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;

public class MyRedisManager {
    private int expire = 1800;
    private RedisTemplate<byte[], byte[]> redisTemplate;

    public void init() {
    }

    public RedisTemplate<byte[], byte[]> getRedisTemplate() {
        return this.redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<byte[], byte[]> template) {
        this.redisTemplate = template;
    }

    public int getExpire() {
        return this.expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public byte[] get(byte[] key) {
        return (byte[])this.redisTemplate.opsForValue().get((Object)key);
    }

    public List<byte[]> multiGet(Collection<byte[]> keys) {
        return this.redisTemplate.opsForValue().multiGet(keys);
    }

    public byte[] set(byte[] key, byte[] value, int expire) {
        this.redisTemplate.opsForValue().set((Object)key, (Object)value, (long)expire, TimeUnit.SECONDS);
        return value;
    }

    public void del(byte[] key) {
        this.redisTemplate.delete((Object)key);
    }

    public Set<byte[]> keys(String pattern) {
        return (Set)this.redisTemplate.execute(connection -> {
            HashSet<Object> keysTmp = new HashSet<Object>();
            try (Cursor cursor = connection.scan(ScanOptions.scanOptions().match(pattern).count(10000L).build());){
                while (cursor.hasNext()) {
                    keysTmp.add(cursor.next());
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
            return keysTmp;
        });
    }
}

