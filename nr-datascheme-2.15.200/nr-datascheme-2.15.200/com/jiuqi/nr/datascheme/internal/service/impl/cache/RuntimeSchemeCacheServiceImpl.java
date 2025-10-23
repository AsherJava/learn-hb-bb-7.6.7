/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.HashCacheValue
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.nr.datascheme.api.core.DataWrapper
 *  com.jiuqi.nr.datascheme.api.event.RefreshCache
 *  com.jiuqi.nr.datascheme.api.event.RefreshScheme
 *  com.jiuqi.nr.datascheme.api.service.IdMutexProvider
 *  com.jiuqi.nr.datascheme.api.service.IdMutexProvider$Mutex
 */
package com.jiuqi.nr.datascheme.internal.service.impl.cache;

import com.jiuqi.np.cache.HashCacheValue;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.nr.datascheme.api.core.DataWrapper;
import com.jiuqi.nr.datascheme.api.event.RefreshCache;
import com.jiuqi.nr.datascheme.api.event.RefreshScheme;
import com.jiuqi.nr.datascheme.api.service.IdMutexProvider;
import com.jiuqi.nr.datascheme.internal.dto.DataDimDTO;
import com.jiuqi.nr.datascheme.internal.dto.DataSchemeDTO;
import com.jiuqi.nr.datascheme.internal.service.DataSchemeService;
import com.jiuqi.nr.datascheme.internal.service.SchemeRefreshListener;
import com.jiuqi.nr.datascheme.internal.service.impl.cache.BaseRuntimeSchemeCache;
import com.jiuqi.nr.datascheme.internal.service.impl.cache.DataSchemeCacheValue;
import com.jiuqi.nr.datascheme.internal.service.impl.cache.DefaultDataWrapper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

@Deprecated
public class RuntimeSchemeCacheServiceImpl
extends BaseRuntimeSchemeCache<DataSchemeCacheValue>
implements DataSchemeService,
SchemeRefreshListener {
    @Autowired
    private DataSchemeService dataSchemeService;
    private final IdMutexProvider idMutexProvider;
    private static final String IDX_NAME_CODE = "code_scheme";
    private final Function<DataSchemeDTO, DataSchemeDTO> copy = r -> {
        if (r == null) {
            return null;
        }
        return r.clone();
    };
    private final Function<List<DataSchemeDTO>, List<DataSchemeDTO>> listCopy = r -> r.stream().map(this.copy).filter(Objects::nonNull).collect(Collectors.toList());

    public RuntimeSchemeCacheServiceImpl(NedisCacheProvider cacheProvider) {
        super(cacheProvider, DataSchemeCacheValue.class);
        this.idMutexProvider = new IdMutexProvider();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public DataSchemeDTO getDataScheme(String key) {
        Assert.notNull((Object)key, "key must not be null.");
        Cache.ValueWrapper valueWrapper = this.get(key);
        if (valueWrapper != null) {
            DataSchemeCacheValue cacheValue = (DataSchemeCacheValue)valueWrapper.get();
            if (cacheValue == null) {
                return null;
            }
            return this.copy.apply(cacheValue.getDataScheme());
        }
        HashSet<String> mutex = new HashSet<String>(1);
        mutex.add(key);
        IdMutexProvider.Mutex mutex2 = this.idMutexProvider.getMutex(mutex);
        synchronized (mutex2) {
            valueWrapper = this.get(key);
            if (valueWrapper != null) {
                DataSchemeCacheValue cacheValue = (DataSchemeCacheValue)valueWrapper.get();
                if (cacheValue == null) {
                    return null;
                }
                return this.copy.apply(cacheValue.getDataScheme());
            }
            DataSchemeDTO dataScheme = this.dataSchemeService.getDataScheme(key);
            List<DataDimDTO> dims = this.dataSchemeService.getDataSchemeDimension(key);
            DataSchemeCacheValue value = new DataSchemeCacheValue();
            value.setDataScheme(dataScheme);
            value.setDims(dims);
            if (dataScheme != null) {
                super.put(value);
                super.putHashIndexEntry(IDX_NAME_CODE, value.getCode(), value.getKey());
                return this.copy.apply(dataScheme);
            }
            super.putNullObject(key);
            return null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public DataSchemeDTO getDataSchemeByCode(String code) {
        Assert.notNull((Object)code, "code must not be null.");
        DataWrapper<DataSchemeDTO> cacheValue = this.getDataSchemeCacheValueByCode(code);
        if (cacheValue.isPresent()) {
            return (DataSchemeDTO)cacheValue.get();
        }
        HashSet<String> mutex = new HashSet<String>(1);
        mutex.add(code);
        IdMutexProvider.Mutex mutex2 = this.idMutexProvider.getMutex(mutex);
        synchronized (mutex2) {
            cacheValue = this.getDataSchemeCacheValueByCode(code);
            if (cacheValue.isPresent()) {
                return (DataSchemeDTO)cacheValue.get();
            }
            DataSchemeDTO dataScheme = this.dataSchemeService.getDataSchemeByCode(code);
            if (dataScheme == null) {
                super.putHashIndexEntry(IDX_NAME_CODE, code, "empty");
                return null;
            }
            List<DataDimDTO> dims = this.dataSchemeService.getDataSchemeDimension(dataScheme.getKey());
            DataSchemeCacheValue value = new DataSchemeCacheValue();
            value.setDataScheme(dataScheme);
            value.setDims(dims);
            super.put(value);
            super.putHashIndexEntry(IDX_NAME_CODE, value.getCode(), value.getKey());
            return this.copy.apply(dataScheme);
        }
    }

    private DataWrapper<DataSchemeDTO> getDataSchemeCacheValueByCode(String code) {
        HashCacheValue<Cache.ValueWrapper> hashIndexValue = super.getHashIndexValue(IDX_NAME_CODE, code);
        if (hashIndexValue.isPresent()) {
            Cache.ValueWrapper valueWrapper = (Cache.ValueWrapper)hashIndexValue.get();
            if (valueWrapper == null) {
                return DefaultDataWrapper.empty();
            }
            String key = (String)valueWrapper.get();
            if (key == null) {
                return DefaultDataWrapper.valueOf(null);
            }
            if ("empty".equals(key)) {
                return DefaultDataWrapper.valueOf(null);
            }
            return DefaultDataWrapper.valueOf(this.getDataScheme(key));
        }
        return DefaultDataWrapper.empty();
    }

    @Override
    public List<DataSchemeDTO> getDataSchemeByParent(String parent) {
        Assert.notNull((Object)parent, "parent must not be null.");
        return this.dataSchemeService.getDataSchemeByParent(parent);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<DataSchemeDTO> getDataSchemes(List<String> keys) {
        Assert.notNull(keys, "keys must not be null.");
        keys = keys.stream().filter(Objects::nonNull).collect(Collectors.toList());
        if (keys.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<DataSchemeDTO> list = new ArrayList<DataSchemeDTO>(keys.size());
        Map<String, Cache.ValueWrapper> cacheValue = super.gets(keys);
        HashSet<String> unCacheKeys = new HashSet<String>(keys);
        for (String string : keys) {
            Cache.ValueWrapper valueWrapper = cacheValue.get(string);
            if (valueWrapper == null) continue;
            DataSchemeCacheValue value = (DataSchemeCacheValue)valueWrapper.get();
            if (value != null) {
                list.add(this.copy.apply(value.getDataScheme()));
            }
            unCacheKeys.remove(string);
        }
        if (unCacheKeys.isEmpty()) {
            return list;
        }
        IdMutexProvider.Mutex mutex = this.idMutexProvider.getMutex(new HashSet<String>(unCacheKeys));
        synchronized (mutex) {
            keys = new ArrayList<String>(unCacheKeys);
            cacheValue = super.gets(keys);
            for (String key : keys) {
                Cache.ValueWrapper valueWrapper = cacheValue.get(key);
                if (valueWrapper == null) continue;
                DataSchemeCacheValue value = (DataSchemeCacheValue)valueWrapper.get();
                if (value != null) {
                    list.add(this.copy.apply(value.getDataScheme()));
                }
                unCacheKeys.remove(key);
            }
            if (unCacheKeys.isEmpty()) {
                return list;
            }
            List<DataSchemeDTO> list2 = this.dataSchemeService.getDataSchemes(new ArrayList<String>(unCacheKeys));
            if (list2 != null && !list2.isEmpty()) {
                ArrayList<DataSchemeCacheValue> values = new ArrayList<DataSchemeCacheValue>(list2.size());
                list.addAll((Collection<DataSchemeDTO>)this.listCopy.apply(list2));
                HashMap<String, String> codeIndex = new HashMap<String, String>(list2.size());
                for (DataSchemeDTO dataScheme : list2) {
                    unCacheKeys.remove(dataScheme.getKey());
                    List<DataDimDTO> dims = this.dataSchemeService.getDataSchemeDimension(dataScheme.getKey());
                    DataSchemeCacheValue value = new DataSchemeCacheValue();
                    value.setDataScheme(dataScheme);
                    value.setDims(dims);
                    values.add(value);
                    codeIndex.put(value.getCode(), value.getKey());
                }
                super.puts(values);
                super.putHashIndex(IDX_NAME_CODE, codeIndex);
            }
            unCacheKeys.forEach(x$0 -> super.putNullObject((String)x$0));
            return list;
        }
    }

    @Override
    public List<DataSchemeDTO> getAllDataScheme() {
        return this.dataSchemeService.getAllDataScheme();
    }

    @Override
    public List<DataSchemeDTO> searchByKeyword(String keyword) {
        return this.dataSchemeService.searchByKeyword(keyword);
    }

    @Override
    public List<DataDimDTO> getDataSchemeDimension(String key) {
        DataWrapper<DataSchemeCacheValue> cacheValue = this.getDataSchemeCacheValue(key);
        if (cacheValue.isPresent()) {
            DataSchemeCacheValue value = (DataSchemeCacheValue)cacheValue.get();
            if (value == null) {
                return Collections.emptyList();
            }
            return value.getDims();
        }
        DataSchemeDTO dataScheme = this.getDataScheme(key);
        if (dataScheme == null) {
            return null;
        }
        cacheValue = this.getDataSchemeCacheValue(key);
        if (cacheValue.isPresent()) {
            DataSchemeCacheValue value = (DataSchemeCacheValue)cacheValue.get();
            if (value != null) {
                return value.getDims();
            }
            return Collections.emptyList();
        }
        return null;
    }

    @Override
    public void onClearCache() {
        this.cache.clear();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void onClearCache(@NonNull RefreshCache refreshTable) {
        Map table;
        if (refreshTable.isRefreshAll()) {
            this.onClearCache();
        }
        if ((table = refreshTable.getRefreshTable()) == null || table.isEmpty()) {
            return;
        }
        ArrayList<String> schemes = new ArrayList<String>();
        ArrayList<String> codes = new ArrayList<String>();
        for (Map.Entry entry : table.entrySet()) {
            RefreshScheme refreshScheme = (RefreshScheme)entry.getKey();
            String scheme = refreshScheme.getKey();
            String code = refreshScheme.getCode();
            schemes.add(scheme);
            codes.add(code);
        }
        super.removeObjects(schemes);
        IdMutexProvider.Mutex mutex = this.idMutexProvider.getMutex(IDX_NAME_CODE);
        synchronized (mutex) {
            super.removeIndexEntry(IDX_NAME_CODE, codes.toArray());
        }
    }

    private DataWrapper<DataSchemeCacheValue> getDataSchemeCacheValue(String key) {
        Cache.ValueWrapper valueWrapper = this.get(key);
        if (valueWrapper != null) {
            return DefaultDataWrapper.valueOf((DataSchemeCacheValue)valueWrapper.get());
        }
        return DefaultDataWrapper.empty();
    }
}

