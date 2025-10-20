/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.util.concurrent.ThreadFactoryBuilder
 *  com.jiuqi.common.base.util.DoubleKeyMap
 *  com.jiuqi.np.cache.NedisCacheManager
 */
package com.jiuqi.gcreport.basedata.impl.cache.impl;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.jiuqi.common.base.util.DoubleKeyMap;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.cache.BaseDataCache;
import com.jiuqi.gcreport.basedata.impl.event.BaseDataChangedEvent;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.np.cache.NedisCacheManager;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class BaseDataCacheImpl
implements ApplicationListener<BaseDataChangedEvent>,
BaseDataCache {
    private static long lastUpdateTime = 0L;
    private static long intervalTime = 1800000L;
    @Autowired
    private NedisCacheManager cacheManger;
    @Autowired
    private ApplicationContext applicationContext;
    private Object mutex = new Object();
    private DoubleKeyMap<String, String, GcBaseData> tableCode_code2BaseDataCache = new DoubleKeyMap();
    private ExecutorService executorService;
    private final String CACHE_NAME = "gcreport:conBaseData";

    public BaseDataCacheImpl() {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("baseData-cache-%d").build();
        this.executorService = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), namedThreadFactory);
    }

    @Override
    public void onApplicationEvent(BaseDataChangedEvent event) {
        this.exeClearCache();
    }

    @Override
    public void clearCache() {
        this.executorService.execute(() -> this.applicationContext.publishEvent(new BaseDataChangedEvent(new BaseDataChangedEvent.BaseDataChangedInfo())));
        this.exeClearCache();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void exeClearCache() {
        Object object = this.mutex;
        synchronized (object) {
            this.cacheManger.getCache("gcreport:conBaseData").clear();
            this.tableCode_code2BaseDataCache.clear();
        }
    }

    @Override
    public GcBaseData queryBaseDataByCode(String tableCode, String code) {
        this.intervalClearCache();
        if (!this.tableCode_code2BaseDataCache.containsKey((Object)tableCode)) {
            this.loadCache(tableCode);
        }
        return (GcBaseData)this.tableCode_code2BaseDataCache.get((Object)tableCode, (Object)code);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void intervalClearCache() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastUpdateTime > intervalTime) {
            Object object = this.mutex;
            synchronized (object) {
                if (currentTime - lastUpdateTime > intervalTime) {
                    this.clearCache();
                    lastUpdateTime = System.currentTimeMillis();
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void loadCache(String tableCode) {
        Object object = this.mutex;
        synchronized (object) {
            List baseDataList = (List)this.cacheManger.getCache("gcreport:conBaseData").get(tableCode, () -> GcBaseDataCenterTool.getInstance().queryBasedataItems(tableCode));
            if (null == baseDataList) {
                return;
            }
            for (GcBaseData iBaseData : baseDataList) {
                if (null == iBaseData) continue;
                this.tableCode_code2BaseDataCache.put((Object)tableCode, (Object)iBaseData.getCode(), (Object)iBaseData);
            }
        }
    }
}

