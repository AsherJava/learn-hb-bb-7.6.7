/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.cache.CacheProviderManager
 *  com.jiuqi.dc.base.common.event.SyncCacheEvent
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.dc.datamapping.impl.listener;

import com.jiuqi.dc.base.common.cache.CacheProviderManager;
import com.jiuqi.dc.base.common.event.SyncCacheEvent;
import com.jiuqi.dc.datamapping.impl.service.impl.IsolateRefDefineCacheProvider;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class SyncRefCacheEventListener
implements ApplicationListener<SyncCacheEvent>,
ApplicationContextAware {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onApplicationEvent(SyncCacheEvent event) {
        try {
            IsolateRefDefineCacheProvider isolateProvider = (IsolateRefDefineCacheProvider)ApplicationContextRegister.getBean(IsolateRefDefineCacheProvider.class);
            isolateProvider.loadCache(null);
        }
        catch (Exception e) {
            this.logger.error("\u7f13\u5b58\u540c\u6b65\u53d1\u751f\u9519\u8bef", e);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        try {
            CacheProviderManager.setApplicationContext((ApplicationContext)applicationContext);
        }
        catch (Exception e) {
            this.logger.error("\u7f13\u5b58\u7ba1\u7406\u5668\u521d\u59cb\u53d1\u751f\u9519\u8bef", e);
        }
    }
}

