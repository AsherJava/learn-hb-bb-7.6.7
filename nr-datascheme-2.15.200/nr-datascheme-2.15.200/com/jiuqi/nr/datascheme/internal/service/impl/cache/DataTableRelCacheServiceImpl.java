/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.nr.datascheme.api.DataTableRel
 *  com.jiuqi.nr.datascheme.api.event.RefreshCache
 */
package com.jiuqi.nr.datascheme.internal.service.impl.cache;

import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.nr.datascheme.api.DataTableRel;
import com.jiuqi.nr.datascheme.api.event.RefreshCache;
import com.jiuqi.nr.datascheme.internal.dao.IDataTableRelDao;
import com.jiuqi.nr.datascheme.internal.entity.DataTableRelDO;
import com.jiuqi.nr.datascheme.internal.service.DataTableRelService;
import com.jiuqi.nr.datascheme.internal.service.SchemeRefreshListener;
import com.jiuqi.nr.datascheme.internal.service.impl.cache.DataFieldDeployInfoCacheServiceImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.util.CollectionUtils;

@Deprecated
public class DataTableRelCacheServiceImpl
implements DataTableRelService,
SchemeRefreshListener {
    private static final String CACHE_LOADED = "cache_loaded";
    private static final String INDEX_CACHE_KEY_PREFIX = "idx_";
    @Autowired
    private IDataTableRelDao<DataTableRelDO> iDataTableRelDao;
    private NedisCache cache;

    public DataTableRelCacheServiceImpl(NedisCacheProvider cacheProvider) {
        if (cacheProvider == null) {
            throw new IllegalArgumentException("'cacheProvider' must not be null.");
        }
        this.cache = cacheProvider.getCacheManager("nr:scheme:runtime").getCache(DataFieldDeployInfoCacheServiceImpl.class.getSimpleName());
    }

    @Override
    public void onClearCache() {
        this.cache.clear();
    }

    @Override
    public void onClearCache(RefreshCache refreshTable) {
        this.cache.clear();
    }

    @Override
    public DataTableRel getBySrcTable(String srcTableKey) {
        Cache.ValueWrapper valueWrapper = this.cache.get(srcTableKey);
        if (null == valueWrapper) {
            if (this.isLoaded()) {
                return null;
            }
            this.loadCache();
            return this.getBySrcTable(srcTableKey);
        }
        return (DataTableRel)valueWrapper.get();
    }

    @Override
    public List<DataTableRel> getByDesTable(String desTableKey) {
        Cache.ValueWrapper valueWrapper = this.cache.get(INDEX_CACHE_KEY_PREFIX.concat(desTableKey));
        if (null == valueWrapper) {
            if (this.isLoaded()) {
                return Collections.emptyList();
            }
            this.loadCache();
            return this.getByDesTable(desTableKey);
        }
        List srcTableKeys = (List)valueWrapper.get();
        List rels = this.cache.mGet(srcTableKeys);
        if (CollectionUtils.isEmpty(rels)) {
            return Collections.emptyList();
        }
        return rels.stream().map(v -> (DataTableRel)v.get()).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private boolean isLoaded() {
        return this.cache.exists(CACHE_LOADED);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void loadCache() {
        if (this.isLoaded()) {
            return;
        }
        DataTableRelCacheServiceImpl dataTableRelCacheServiceImpl = this;
        synchronized (dataTableRelCacheServiceImpl) {
            if (this.isLoaded()) {
                return;
            }
            List<DataTableRelDO> all = this.iDataTableRelDao.getAll();
            HashMap<String, DataTableRelDO> objMap = new HashMap<String, DataTableRelDO>();
            HashMap<String, List> indexMap = new HashMap<String, List>();
            for (DataTableRelDO dataTableRelDO : all) {
                objMap.put(dataTableRelDO.getSrcTableKey(), dataTableRelDO);
                indexMap.computeIfAbsent(INDEX_CACHE_KEY_PREFIX.concat(dataTableRelDO.getDesTableKey()), k -> new ArrayList()).add(dataTableRelDO.getSrcTableKey());
            }
            this.cache.mPut(objMap);
            this.cache.mPut(indexMap);
            this.cache.put(CACHE_LOADED, (Object)CACHE_LOADED);
        }
    }
}

