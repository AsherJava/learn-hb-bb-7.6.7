/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.HashCacheValue
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.nr.datascheme.api.event.RefreshCache
 *  com.jiuqi.nr.datascheme.api.event.RefreshScheme
 *  com.jiuqi.nr.datascheme.api.event.RefreshTable
 *  com.jiuqi.nr.datascheme.api.service.IdMutexProvider
 *  com.jiuqi.nr.datascheme.api.service.IdMutexProvider$Mutex
 */
package com.jiuqi.nr.datascheme.internal.service.impl.cache;

import com.jiuqi.np.cache.HashCacheValue;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.nr.datascheme.api.event.RefreshCache;
import com.jiuqi.nr.datascheme.api.event.RefreshScheme;
import com.jiuqi.nr.datascheme.api.event.RefreshTable;
import com.jiuqi.nr.datascheme.api.service.IdMutexProvider;
import com.jiuqi.nr.datascheme.internal.dto.DataTableDTO;
import com.jiuqi.nr.datascheme.internal.service.DataTableService;
import com.jiuqi.nr.datascheme.internal.service.SchemeRefreshListener;
import com.jiuqi.nr.datascheme.internal.service.impl.cache.BaseRuntimeSchemeCache;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

@Deprecated
public class RuntimeTableCacheServiceImpl
extends BaseRuntimeSchemeCache<DataTableDTO>
implements DataTableService,
SchemeRefreshListener {
    private static final String IDX_NAME_CODE = "code_table_key";
    private static final String IDX_NAME_SCHEME = "scheme_table_key_";
    private static final String IDX_NAME_SCHEME_ROOT = "scheme_root_table_key_";
    @Autowired
    @Qualifier(value="RuntimeDataTableServiceImpl-NO_CACHE")
    private DataTableService dataTableService;
    private final IdMutexProvider idMutexProvider;
    private final Function<DataTableDTO, DataTableDTO> copy = r -> {
        if (r == null) {
            return null;
        }
        return r.clone();
    };
    private final Function<List<DataTableDTO>, List<DataTableDTO>> listCopy = r -> r.stream().map(this.copy).filter(Objects::nonNull).collect(Collectors.toList());

    public RuntimeTableCacheServiceImpl(NedisCacheProvider cacheManger) {
        super(cacheManger, DataTableDTO.class);
        this.idMutexProvider = new IdMutexProvider();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public DataTableDTO getDataTable(String key) {
        Assert.notNull((Object)key, "key must not be null.");
        Cache.ValueWrapper valueWrapper = this.get(key);
        if (valueWrapper != null) {
            DataTableDTO dto = (DataTableDTO)valueWrapper.get();
            return this.copy.apply(dto);
        }
        HashSet<String> mutex = new HashSet<String>(1);
        mutex.add(key);
        IdMutexProvider.Mutex mutex2 = this.idMutexProvider.getMutex(mutex);
        synchronized (mutex2) {
            valueWrapper = this.get(key);
            if (valueWrapper != null) {
                DataTableDTO dto = (DataTableDTO)valueWrapper.get();
                return this.copy.apply(dto);
            }
            DataTableDTO dataTable = this.dataTableService.getDataTable(key);
            if (dataTable != null) {
                this.put(dataTable);
                this.putHashIndexEntry(IDX_NAME_CODE, dataTable.getCode(), key);
                return this.copy.apply(dataTable);
            }
            this.putNullObject(key);
            return null;
        }
    }

    @Override
    public DataTableDTO getDataTableByCode(String code) {
        Assert.notNull((Object)code, "code must not be null.");
        HashCacheValue<Cache.ValueWrapper> hashValue = this.getHashIndexValue(IDX_NAME_CODE, code);
        if (!hashValue.isPresent()) {
            return this.putIfAbsent(code);
        }
        Cache.ValueWrapper valueWrapper = (Cache.ValueWrapper)hashValue.get();
        if (valueWrapper == null) {
            return this.putIfAbsent(code);
        }
        String key = (String)valueWrapper.get();
        if ("empty".equals(key)) {
            return null;
        }
        return this.getDataTable(key);
    }

    @Override
    public List<DataTableDTO> getLatestDataTableByScheme(String scheme) {
        return this.dataTableService.getLatestDataTableByScheme(scheme);
    }

    @Override
    public Instant getLatestDataTableUpdateTime(String scheme) {
        return this.dataTableService.getLatestDataTableUpdateTime(scheme);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private DataTableDTO putIfAbsent(String code) {
        HashSet<String> mutex = new HashSet<String>(1);
        mutex.add(code);
        IdMutexProvider.Mutex mutex2 = this.idMutexProvider.getMutex(mutex);
        synchronized (mutex2) {
            Cache.ValueWrapper valueWrapper;
            HashCacheValue<Cache.ValueWrapper> hashValue = this.getHashIndexValue(IDX_NAME_CODE, code);
            if (hashValue.isPresent() && (valueWrapper = (Cache.ValueWrapper)hashValue.get()) != null) {
                String key = (String)valueWrapper.get();
                if ("empty".equals(key)) {
                    return null;
                }
                return this.getDataTable(key);
            }
            DataTableDTO dataTable = this.dataTableService.getDataTableByCode(code);
            if (dataTable != null) {
                super.put(dataTable);
                super.putHashIndexEntry(IDX_NAME_CODE, dataTable.getCode(), dataTable.getKey());
                return this.copy.apply(dataTable);
            }
            super.putHashIndexEntry(IDX_NAME_CODE, code, "empty");
            return null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<DataTableDTO> getDataTables(List<String> keys) {
        Assert.notNull(keys, "keys must not be null.");
        keys = keys.stream().filter(Objects::nonNull).collect(Collectors.toList());
        if (keys.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<DataTableDTO> tables = new ArrayList<DataTableDTO>(keys.size());
        Map<String, Cache.ValueWrapper> all = this.gets(keys);
        HashMap<String, Integer> indexMap = new HashMap<String, Integer>(keys.size());
        for (int index = 0; index < keys.size(); ++index) {
            String key = keys.get(index);
            Cache.ValueWrapper valueWrapper = all.get(key);
            if (valueWrapper != null) {
                tables.add(this.copy.apply((DataTableDTO)valueWrapper.get()));
                continue;
            }
            indexMap.put(key, index);
        }
        if (indexMap.isEmpty()) {
            return this.listCopy.apply(tables);
        }
        Set<String> unCacheKeys = indexMap.keySet();
        IdMutexProvider.Mutex mutex = this.idMutexProvider.getMutex(new HashSet(unCacheKeys));
        synchronized (mutex) {
            Map<String, Cache.ValueWrapper> unCache = this.gets(unCacheKeys);
            for (Map.Entry entry : indexMap.entrySet()) {
                String key = (String)entry.getKey();
                Cache.ValueWrapper valueWrapper = unCache.get(key);
                if (valueWrapper == null) continue;
                tables.add(this.copy.apply((DataTableDTO)valueWrapper.get()));
                unCacheKeys.remove(key);
            }
            if (unCacheKeys.isEmpty()) {
                return this.listCopy.apply(tables);
            }
            List<DataTableDTO> dataTables = this.dataTableService.getDataTables(new ArrayList<String>(unCacheKeys));
            super.puts(dataTables);
            for (DataTableDTO dataTable : dataTables) {
                String tableKey = dataTable.getKey();
                Integer index = (Integer)indexMap.get(tableKey);
                if (index != null) {
                    tables.add(dataTable);
                }
                unCacheKeys.remove(tableKey);
                super.putHashIndexEntry(IDX_NAME_CODE, dataTable.getCode(), tableKey);
            }
            unCacheKeys.forEach(x$0 -> super.putNullObject((String)x$0));
            return tables.stream().filter(Objects::nonNull).collect(Collectors.toList());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<DataTableDTO> getAllDataTable(String scheme) {
        Assert.notNull((Object)scheme, "scheme must not be null.");
        String indexName = IDX_NAME_SCHEME + scheme;
        List<DataTableDTO> tables = this.getDataTablesByIndexName(indexName);
        if (tables != null) {
            return tables;
        }
        HashSet<String> idMutex = new HashSet<String>(1);
        idMutex.add(scheme);
        IdMutexProvider.Mutex mutex = this.idMutexProvider.getMutex(idMutex);
        synchronized (mutex) {
            tables = this.getDataTablesByIndexName(indexName);
            if (tables != null) {
                return tables;
            }
            tables = this.dataTableService.getAllDataTable(scheme);
            this.putDataTables(tables, indexName);
            return this.listCopy.apply(tables);
        }
    }

    @Override
    public List<DataTableDTO> getDataTableByGroup(String parentKey) {
        Assert.notNull((Object)parentKey, "parentKey must not be null.");
        return this.dataTableService.getDataTableByGroup(parentKey);
    }

    private void putDataTables(List<DataTableDTO> dataTableByGroup, String indexName) {
        super.puts(dataTableByGroup);
        ArrayList<String> tableKeys = new ArrayList<String>();
        for (DataTableDTO dataTable : dataTableByGroup) {
            String tableKey = dataTable.getKey();
            tableKeys.add(tableKey);
            super.putHashIndexEntry(IDX_NAME_CODE, dataTable.getCode(), tableKey);
        }
        if (tableKeys.isEmpty()) {
            tableKeys = null;
        }
        super.putKVIndex(indexName, tableKeys);
    }

    private List<DataTableDTO> getDataTablesByIndexName(String indexName) {
        Cache.ValueWrapper kvIndexValue = super.getKVIndexValue(indexName);
        if (kvIndexValue != null) {
            List tableKeys = (List)kvIndexValue.get();
            if (tableKeys != null && !tableKeys.isEmpty()) {
                return this.getDataTables(new ArrayList<String>(tableKeys));
            }
            return Collections.emptyList();
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<DataTableDTO> getDataTableByScheme(String schemeKey) {
        Assert.notNull((Object)schemeKey, "schemeKey must not be null.");
        String indexName = IDX_NAME_SCHEME_ROOT + schemeKey;
        List<DataTableDTO> tables = this.getDataTablesByIndexName(indexName);
        if (tables != null) {
            return tables;
        }
        HashSet<String> idMutex = new HashSet<String>(1);
        idMutex.add(schemeKey);
        IdMutexProvider.Mutex mutex = this.idMutexProvider.getMutex(idMutex);
        synchronized (mutex) {
            tables = this.getDataTablesByIndexName(indexName);
            if (tables != null) {
                return tables;
            }
            tables = this.dataTableService.getDataTableByScheme(schemeKey);
            this.putDataTables(tables, indexName);
            return this.listCopy.apply(tables);
        }
    }

    @Override
    public List<DataTableDTO> searchBy(String scheme, String keyword, int type) {
        return this.dataTableService.searchBy(scheme, keyword, type);
    }

    @Override
    public List<DataTableDTO> searchBy(List<String> schemes, String keyword, int type) {
        return this.dataTableService.searchBy(schemes, keyword, type);
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
        Map schemeTable;
        if (refreshTable.isRefreshAll()) {
            this.onClearCache();
        }
        if ((schemeTable = refreshTable.getRefreshTable()) == null) {
            return;
        }
        HashSet<String> tableKeys = new HashSet<String>(16);
        HashSet<String> tableCodes = new HashSet<String>(16);
        HashSet<String> removeIndexNames = new HashSet<String>(schemeTable.size() * 2);
        for (Map.Entry schemeEntry : schemeTable.entrySet()) {
            String scheme = ((RefreshScheme)schemeEntry.getKey()).getKey();
            Set tables = (Set)schemeEntry.getValue();
            if (scheme == null || tables == null || tables.isEmpty()) continue;
            for (RefreshTable table : tables) {
                String tableKey = table.getTableKey();
                tableKeys.add(tableKey);
                String tableCode = table.getTableCode();
                tableCodes.add(tableCode);
            }
            removeIndexNames.add(IDX_NAME_SCHEME + scheme);
            removeIndexNames.add(IDX_NAME_SCHEME_ROOT + scheme);
        }
        super.removeObjects(tableKeys);
        super.removeIndexes(removeIndexNames);
        IdMutexProvider.Mutex mutex = this.idMutexProvider.getMutex(IDX_NAME_CODE);
        synchronized (mutex) {
            super.removeIndexEntry(IDX_NAME_CODE, tableCodes.toArray());
        }
    }
}

