/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.event.RefreshCache
 */
package com.jiuqi.nr.datascheme.internal.service.impl.cache;

import com.jiuqi.nr.datascheme.api.event.RefreshCache;
import com.jiuqi.nr.datascheme.internal.service.SchemeRefreshListener;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultCacheRefreshService {
    @Autowired(required=false)
    private List<SchemeRefreshListener> listeners;
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultCacheRefreshService.class);

    public void onClearCache(RefreshCache source) {
        if (this.listeners == null) {
            return;
        }
        boolean refreshAll = source.isRefreshAll();
        if (this.listeners != null) {
            LOGGER.info("\u6570\u636e\u65b9\u6848\u7f13\u5b58\u6e05\u9664\u5668\u6267\u884c\u5f00\u59cb\uff01\u53c2\u6570- {}", (Object)source);
            for (SchemeRefreshListener listener : this.listeners) {
                try {
                    if (refreshAll) {
                        listener.onClearCache();
                        continue;
                    }
                    listener.onClearCache(source);
                }
                catch (Exception e) {
                    LOGGER.error("\u54cd\u5e94\u53c2\u6570\u5237\u65b0\u4e8b\u4ef6\u5931\u8d25\u3002\u5c06\u91cd\u8bd5\u4e00\u6b21\u5237\u65b0\u5168\u90e8\u7f13\u5b58\u3002\u76d1\u542c\u5668\uff1a" + listener.getClass().getName(), e);
                    try {
                        listener.onClearCache();
                    }
                    catch (Exception ee) {
                        LOGGER.error("\u91cd\u8bd5\u5237\u65b0\u7f13\u5b58\u5931\u8d25" + listener.getClass().getName(), ee);
                    }
                }
            }
            LOGGER.info("\u6570\u636e\u65b9\u6848\u7f13\u5b58\u6e05\u9664\u5668\u6267\u884c\u5b8c\u6bd5\uff01\u53c2\u6570- {}", (Object)source);
        }
    }
}

