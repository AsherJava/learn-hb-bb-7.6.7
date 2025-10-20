/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.cache;

import com.jiuqi.dc.base.common.cache.proider.ICacheProvider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.context.ApplicationContext;

public class CacheProviderManager {
    private static Map<String, ICacheProvider> cacheProviderMap = new HashMap<String, ICacheProvider>();
    private static List<ICacheProvider> cacheProviderList = new ArrayList<ICacheProvider>();
    private static ApplicationContext applicationContext;

    private CacheProviderManager() {
    }

    public static void init() {
        applicationContext.getBeansOfType(ICacheProvider.class).values().stream().forEach(item -> {
            if (item.getCacheDefine() == null) {
                return;
            }
            if (!cacheProviderMap.containsKey(item.getCacheDefine().getCode())) {
                cacheProviderList.add((ICacheProvider)item);
                cacheProviderMap.put(item.getCacheDefine().getCode(), (ICacheProvider)item);
            }
        });
        cacheProviderList.sort((p1, p2) -> {
            Integer p1Order = p1.getCacheDefine().getOrder() == null ? Integer.MAX_VALUE : p1.getCacheDefine().getOrder();
            Integer p2Order = p2.getCacheDefine().getOrder() == null ? Integer.MAX_VALUE : p2.getCacheDefine().getOrder();
            return p1Order.compareTo(p2Order);
        });
    }

    public static ICacheProvider getCacheProvider(String code) {
        return cacheProviderMap.get(code);
    }

    public static List<ICacheProvider> getCacheProviders() {
        return Collections.unmodifiableList(cacheProviderList);
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        if (CacheProviderManager.applicationContext == null) {
            CacheProviderManager.applicationContext = applicationContext;
            CacheProviderManager.init();
        }
    }
}

