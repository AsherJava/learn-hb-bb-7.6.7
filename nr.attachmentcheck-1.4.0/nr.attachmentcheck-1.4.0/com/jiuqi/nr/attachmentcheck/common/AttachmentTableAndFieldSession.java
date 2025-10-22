/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.attachmentcheck.common;

import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.nr.attachmentcheck.bean.AttachmentFormStruct;
import com.jiuqi.util.StringUtils;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;

@Component
public class AttachmentTableAndFieldSession
implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String NAME = "REMOTE_NR";
    private NedisCacheManager cacheManager;
    private static final String CACHENAME = "BLOBITEMS_PARAM";

    @Autowired
    public void setCacheManager(NedisCacheProvider sessionCacheProvider) {
        this.cacheManager = sessionCacheProvider.getCacheManager(NAME);
    }

    public void saveResult(String cacheKey, List<AttachmentFormStruct> items) {
        NedisCache caffeineCache = this.cacheManager.getCache(CACHENAME);
        caffeineCache.put(cacheKey, items);
    }

    public Object getResult(String cacheKey) {
        if (StringUtils.isEmpty((String)cacheKey)) {
            return null;
        }
        NedisCache caffeineCache = this.cacheManager.getCache(CACHENAME);
        Cache.ValueWrapper valueWrapper = caffeineCache.get(cacheKey);
        if (valueWrapper != null) {
            return valueWrapper.get();
        }
        return null;
    }
}

