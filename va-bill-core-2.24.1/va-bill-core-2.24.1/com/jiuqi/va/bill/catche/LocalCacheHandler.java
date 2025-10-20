/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bill.catche;

import com.jiuqi.va.bill.catche.LocalCacheEntity;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class LocalCacheHandler {
    private static final long SECOND_TIME = 1000L;
    private static final int DEFUALT_VALIDITY_TIME = 20;
    private static final Timer timer = new Timer();
    private static final ConcurrentHashMap<String, LocalCacheEntity> map = new ConcurrentHashMap(new HashMap(0x100000));

    public static void addCache(LocalCacheEntity localCacheEntity) {
        LocalCacheHandler.addCache(localCacheEntity, 20);
    }

    public static synchronized void addCache(LocalCacheEntity localCacheEntity, int validityTime) {
        map.put(localCacheEntity.getCacheKey(), localCacheEntity);
        timer.schedule((TimerTask)new TimeoutTimerTask(localCacheEntity.getCacheKey()), (long)validityTime * 1000L);
    }

    public static synchronized LocalCacheEntity getCache(String key) {
        return map.get(key);
    }

    public static synchronized boolean isConcurrent(String key) {
        return map.containsKey(key);
    }

    public static synchronized void removeCache(String key) {
        map.remove(key);
    }

    public static int getCacheSize() {
        return map.size();
    }

    public static synchronized void clearCache() {
        if (null != timer) {
            timer.cancel();
        }
        map.clear();
    }

    static class TimeoutTimerTask
    extends TimerTask {
        private String ceKey;

        public TimeoutTimerTask(String key) {
            this.ceKey = key;
        }

        @Override
        public void run() {
            LocalCacheHandler.removeCache(this.ceKey);
        }
    }
}

