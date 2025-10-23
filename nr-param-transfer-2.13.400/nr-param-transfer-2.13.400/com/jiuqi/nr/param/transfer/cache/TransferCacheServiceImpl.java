/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.ContextExtension
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.datascheme.api.core.DataWrapper
 *  com.jiuqi.nr.datascheme.internal.service.impl.cache.DefaultDataWrapper
 */
package com.jiuqi.nr.param.transfer.cache;

import com.jiuqi.np.core.context.ContextExtension;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.datascheme.api.core.DataWrapper;
import com.jiuqi.nr.datascheme.internal.service.impl.cache.DefaultDataWrapper;
import com.jiuqi.nr.param.transfer.cache.TransferCacheService;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TransferCacheServiceImpl
implements TransferCacheService {
    private static final Serializable NULL = new Serializable(){};
    private final Logger logger = LoggerFactory.getLogger(TransferCacheServiceImpl.class);
    public static final String DEFAULT_CACHE_NAME = "transfer-cache";
    public static final String ENABLE = "enable";

    @Override
    public <T> DataWrapper<T> get(String cacheName, String id, Class<T> tClass) {
        NpContext context = NpContextHolder.getContext();
        ContextExtension extension = context.getExtension(DEFAULT_CACHE_NAME);
        Object bool = extension.get(ENABLE);
        if (Boolean.FALSE.equals(bool)) {
            return DefaultDataWrapper.empty();
        }
        extension = context.getExtension(cacheName);
        Object attribute = extension.get(id);
        if (NULL.equals(attribute)) {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("hit cache {}", (Object)id);
            }
            return DefaultDataWrapper.valueOf(null);
        }
        if (tClass.isInstance(attribute)) {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("hit cache {}", (Object)id);
            }
            return DefaultDataWrapper.valueOf((Object)attribute);
        }
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("no hit cache {}", (Object)id);
        }
        return DefaultDataWrapper.empty();
    }

    @Override
    public <T extends Serializable> void put(String cacheName, String id, T o) {
        NpContext context = NpContextHolder.getContext();
        ContextExtension extension = context.getExtension(DEFAULT_CACHE_NAME);
        Object bool = extension.get(ENABLE);
        if (Boolean.FALSE.equals(bool)) {
            return;
        }
        extension = context.getExtension(cacheName);
        if (o == null) {
            extension.put(id, NULL);
        } else {
            extension.put(id, o);
        }
    }
}

