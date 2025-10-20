/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.internal.springcache.DefaultCacheProvider
 *  com.jiuqi.va.query.template.vo.QueryTemplateCacheVO
 *  com.jiuqi.va.query.tree.vo.MenuTreeVO
 */
package com.jiuqi.va.query.cache;

import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.internal.springcache.DefaultCacheProvider;
import com.jiuqi.va.query.template.vo.QueryTemplateCacheVO;
import com.jiuqi.va.query.tree.vo.MenuTreeVO;
import java.util.List;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;

@Component
public class QueryCacheManage {
    private NedisCacheManager manager;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public QueryTemplateCacheVO getTemplate(String templateId, DoThingsNoCache<String> back) {
        if (this.manager == null) {
            this.manager = DefaultCacheProvider.getCacheManager((String)"DC_QUERY_MANAGE");
        }
        NedisCache cache = this.manager.getCache(templateId);
        Cache.ValueWrapper valueWrapper = cache.hGet("DC_QUERY_TEMPLATE", (Object)templateId);
        QueryTemplateCacheVO datas = null;
        if (valueWrapper != null) {
            datas = (QueryTemplateCacheVO)valueWrapper.get();
        }
        if (datas == null) {
            datas = back.getTemplate(templateId);
            NedisCache nedisCache = cache;
            synchronized (nedisCache) {
                cache.hSet("DC_QUERY_TEMPLATE", (Object)templateId, (Object)datas);
            }
        }
        return datas;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<MenuTreeVO> initTree(DoTreeInitNoCache<String> back) {
        if (this.manager == null) {
            this.manager = DefaultCacheProvider.getCacheManager((String)"DC_QUERY_MANAGE");
        }
        NedisCache cache = this.manager.getCache("DC_QUERY_TEMPLATE_TREE");
        Cache.ValueWrapper valueWrapper = cache.hGet("DC_QUERY_TEMPLATE", (Object)"DC_QUERY_TEMPLATE_TREE_VALUE");
        cache.get("DC_QUERY_TEMPLATE");
        List<MenuTreeVO> datas = null;
        if (valueWrapper != null) {
            datas = (List<MenuTreeVO>)valueWrapper.get();
        }
        if (datas == null) {
            datas = back.initTree();
            QueryCacheManage queryCacheManage = this;
            synchronized (queryCacheManage) {
                cache.hSet("DC_QUERY_TEMPLATE", (Object)"DC_QUERY_TEMPLATE_TREE_VALUE", datas);
            }
        }
        return datas;
    }

    public void clearOneCache(String key) {
        if (this.manager == null) {
            this.manager = DefaultCacheProvider.getCacheManager((String)"DC_QUERY_MANAGE");
        }
        this.manager.getCacheNames().forEach(v -> {
            if (v.equals(key)) {
                this.manager.getCache(v).clear();
            }
        });
    }

    public void clearAllCache() {
        if (this.manager == null) {
            this.manager = DefaultCacheProvider.getCacheManager((String)"DC_QUERY_MANAGE");
        }
        this.manager.getCacheNames().forEach(v -> this.manager.getCache(v).clear());
    }

    public void clearTreeCache() {
        if (this.manager == null) {
            this.manager = DefaultCacheProvider.getCacheManager((String)"DC_QUERY_MANAGE");
        }
        this.manager.getCacheNames().forEach(v -> {
            if (v.equals("DC_QUERY_TEMPLATE_TREE")) {
                this.manager.getCache(v).clear();
            }
        });
    }

    @FunctionalInterface
    public static interface DoTreeInitNoCache<T> {
        public List<MenuTreeVO> initTree();
    }

    @FunctionalInterface
    public static interface DoThingsNoCache<T> {
        public QueryTemplateCacheVO getTemplate(String var1);
    }
}

