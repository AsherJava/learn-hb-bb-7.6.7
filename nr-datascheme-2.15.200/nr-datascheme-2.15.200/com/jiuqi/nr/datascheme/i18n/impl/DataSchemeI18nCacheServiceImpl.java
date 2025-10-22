/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.HashCacheValue
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.nr.datascheme.api.event.RefreshCache
 *  com.jiuqi.nr.datascheme.api.event.RefreshScheme
 *  com.jiuqi.nr.datascheme.api.service.IdMutexProvider
 *  com.jiuqi.nr.datascheme.api.service.IdMutexProvider$Mutex
 */
package com.jiuqi.nr.datascheme.i18n.impl;

import com.jiuqi.np.cache.HashCacheValue;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.nr.datascheme.api.event.RefreshCache;
import com.jiuqi.nr.datascheme.api.event.RefreshScheme;
import com.jiuqi.nr.datascheme.api.service.IdMutexProvider;
import com.jiuqi.nr.datascheme.i18n.IDataSchemeI18nService;
import com.jiuqi.nr.datascheme.i18n.dao.DataSchemeI18nDao;
import com.jiuqi.nr.datascheme.i18n.entity.DataSchemeI18nDO;
import com.jiuqi.nr.datascheme.i18n.language.ILanguageType;
import com.jiuqi.nr.datascheme.i18n.language.LanguageType;
import com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO;
import com.jiuqi.nr.datascheme.internal.service.DataFieldService;
import com.jiuqi.nr.datascheme.internal.service.SchemeRefreshListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class DataSchemeI18nCacheServiceImpl
implements IDataSchemeI18nService,
SchemeRefreshListener {
    private final String defaultKey = "defaultKey";
    @Autowired
    private DataFieldService dataFieldService;
    @Autowired
    private DataSchemeI18nDao dataSchemeI18nDao;
    private NedisCache cache;
    private final IdMutexProvider idMutexProvider;

    public DataSchemeI18nCacheServiceImpl(NedisCacheProvider cacheProvider) {
        if (cacheProvider == null) {
            throw new IllegalArgumentException("'cacheProvider' must not be null.");
        }
        this.cache = cacheProvider.getCacheManager("nr:scheme:runtime").getCache(DataSchemeI18nCacheServiceImpl.class.getSimpleName());
        this.idMutexProvider = new IdMutexProvider();
    }

    private String createIndex(String schemeKey, String type) {
        return schemeKey.concat(type);
    }

    @Override
    public DataSchemeI18nDO getByFieldKey(String schemeKey, String fieldKey, String type) {
        Assert.notNull((Object)schemeKey, "schemeKey must not be null.");
        Assert.notNull((Object)fieldKey, "fieldKey must not be null.");
        Assert.notNull((Object)type, "type must not be null.");
        HashCacheValue hashCacheValue = this.cache.hGetIfExists(this.createIndex(schemeKey, type), (Object)fieldKey);
        if (!hashCacheValue.isPresent()) {
            return this.putIfAbsent(schemeKey, fieldKey, type);
        }
        Cache.ValueWrapper valueWrapper = (Cache.ValueWrapper)hashCacheValue.get();
        if (null == valueWrapper) {
            return null;
        }
        return (DataSchemeI18nDO)valueWrapper.get();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public DataSchemeI18nDO putIfAbsent(String schemeKey, String fieldKey, String type) {
        DataFieldDTO dataField = this.dataFieldService.getDataField(fieldKey);
        if (null == dataField) {
            return null;
        }
        String index = this.createIndex(schemeKey, type);
        IdMutexProvider.Mutex mutex = this.idMutexProvider.getMutex(index);
        synchronized (mutex) {
            HashCacheValue hashCacheValue = this.cache.hGetIfExists(this.createIndex(schemeKey, type), (Object)fieldKey);
            if (hashCacheValue.isPresent()) {
                Cache.ValueWrapper valueWrapper = (Cache.ValueWrapper)hashCacheValue.get();
                if (null == valueWrapper) {
                    return null;
                }
                return (DataSchemeI18nDO)valueWrapper.get();
            }
            List i18nList = this.dataSchemeI18nDao.getBySchemeKey(schemeKey, type);
            Map<Object, Object> i18nMap = null;
            if (null == i18nList || i18nList.isEmpty()) {
                i18nMap = new HashMap<String, Object>(1);
                i18nMap.put("defaultKey", null);
            } else {
                i18nMap = i18nList.stream().collect(Collectors.toMap(DataSchemeI18nDO::getKey, v -> v));
            }
            this.cache.hMSet(index, i18nMap);
            return i18nMap.getOrDefault(fieldKey, null);
        }
    }

    @Override
    public void onClearCache() {
        this.cache.clear();
    }

    @Override
    public void onClearCache(RefreshCache refreshTable) {
        Map table;
        if (refreshTable.isRefreshAll()) {
            this.onClearCache();
        }
        if ((table = refreshTable.getRefreshTable()) == null || table.isEmpty()) {
            return;
        }
        List<ILanguageType> allValues = LanguageType.allValues();
        ArrayList<String> schemes = new ArrayList<String>();
        for (RefreshScheme refreshScheme : table.keySet()) {
            String key = refreshScheme.getKey();
            for (ILanguageType type : allValues) {
                schemes.add(this.createIndex(key, type.getKey()));
            }
        }
        this.cache.mEvict(schemes);
    }
}

