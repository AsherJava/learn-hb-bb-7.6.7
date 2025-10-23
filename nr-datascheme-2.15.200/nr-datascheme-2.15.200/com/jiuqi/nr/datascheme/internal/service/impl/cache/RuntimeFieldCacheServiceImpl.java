/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.HashCacheValue
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.nr.datascheme.api.FieldSearchQuery
 *  com.jiuqi.nr.datascheme.api.ValidationRule
 *  com.jiuqi.nr.datascheme.api.core.DataWrapper
 *  com.jiuqi.nr.datascheme.api.event.RefreshCache
 *  com.jiuqi.nr.datascheme.api.event.RefreshScheme
 *  com.jiuqi.nr.datascheme.api.event.RefreshTable
 *  com.jiuqi.nr.datascheme.api.service.IdMutexProvider
 *  com.jiuqi.nr.datascheme.api.service.IdMutexProvider$Mutex
 *  com.jiuqi.nr.datascheme.api.type.CompareType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 */
package com.jiuqi.nr.datascheme.internal.service.impl.cache;

import com.jiuqi.np.cache.HashCacheValue;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.nr.datascheme.api.FieldSearchQuery;
import com.jiuqi.nr.datascheme.api.ValidationRule;
import com.jiuqi.nr.datascheme.api.core.DataWrapper;
import com.jiuqi.nr.datascheme.api.event.RefreshCache;
import com.jiuqi.nr.datascheme.api.event.RefreshScheme;
import com.jiuqi.nr.datascheme.api.event.RefreshTable;
import com.jiuqi.nr.datascheme.api.service.IdMutexProvider;
import com.jiuqi.nr.datascheme.api.type.CompareType;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO;
import com.jiuqi.nr.datascheme.internal.dto.DataTableDTO;
import com.jiuqi.nr.datascheme.internal.dto.ValidationRuleDTO;
import com.jiuqi.nr.datascheme.internal.service.DataFieldService;
import com.jiuqi.nr.datascheme.internal.service.DataTableService;
import com.jiuqi.nr.datascheme.internal.service.SchemeRefreshListener;
import com.jiuqi.nr.datascheme.internal.service.impl.cache.BaseRuntimeSchemeCache;
import com.jiuqi.nr.datascheme.internal.service.impl.cache.DefaultDataWrapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.support.NullValue;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

@Deprecated
public class RuntimeFieldCacheServiceImpl
extends BaseRuntimeSchemeCache<DataFieldDTO>
implements DataFieldService,
SchemeRefreshListener {
    private static final String IDX_NAME_SCHEME = "scheme_field_code_";
    private static final String IDX_NAME_TABLE_KEY_CODE = "table_field_code_";
    @Autowired
    @Qualifier(value="RuntimeDataFieldServiceImpl-NO_CACHE")
    private DataFieldService dataFieldService;
    @Autowired
    private DataTableService dataTableService;
    private final IdMutexProvider idMutexProvider;
    private final ConcurrentHashMap<String, Object> nullValueCache = new ConcurrentHashMap(1024);

    public RuntimeFieldCacheServiceImpl(NedisCacheProvider cacheProvider) {
        super(cacheProvider, DataFieldDTO.class);
        this.idMutexProvider = new IdMutexProvider();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public DataFieldDTO getDataField(String key) {
        Assert.notNull((Object)key, "key must not be null.");
        if (this.nullValueCache.containsKey(key)) {
            return null;
        }
        Cache.ValueWrapper valueWrapper = this.get(key);
        if (valueWrapper != null) {
            return (DataFieldDTO)valueWrapper.get();
        }
        IdMutexProvider.Mutex mutex = this.idMutexProvider.getMutex(key);
        synchronized (mutex) {
            if (this.nullValueCache.containsKey(key)) {
                return null;
            }
            valueWrapper = this.get(key);
            if (valueWrapper != null) {
                return (DataFieldDTO)valueWrapper.get();
            }
            return this.loadTableCacheByFieldKey(key);
        }
    }

    @Override
    public List<DataFieldDTO> getDataFields(List<String> keys) {
        Assert.notNull(keys, "keys must not be null.");
        if (keys.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<DataFieldDTO> list = new ArrayList<DataFieldDTO>(keys.size());
        for (String key : keys) {
            DataFieldDTO dataField = this.getDataField(key);
            if (dataField == null) continue;
            list.add(dataField);
        }
        return list;
    }

    @Override
    public List<DataFieldDTO> getAllDataField(String scheme) {
        List<DataFieldDTO> fs = this.dataFieldService.getAllDataField(scheme);
        this.fieldValidationRule(fs);
        return fs;
    }

    @Override
    public List<DataFieldDTO> getDataFieldByTable(String table) {
        Assert.notNull((Object)table, "table must not be null.");
        DataTableDTO dataTable = this.dataTableService.getDataTable(table);
        if (dataTable == null) {
            return null;
        }
        DataWrapper<List<DataFieldDTO>> field = this.matchFieldFromCacheByTableKey(table);
        if (field.isPresent()) {
            return (List)field.get();
        }
        return this.loadTableCacheByTableKey(table);
    }

    @Override
    public DataFieldDTO getDataFieldByTableKeyAndCode(String table, String code) {
        Assert.notNull((Object)table, "table must not be null.");
        Assert.notNull((Object)code, "code must not be null.");
        DataTableDTO dataTable = this.dataTableService.getDataTable(table);
        if (dataTable == null) {
            return null;
        }
        DataWrapper<DataFieldDTO> dataFieldByCache = this.matchFieldFromCacheByIndex(IDX_NAME_TABLE_KEY_CODE + table, code, false);
        if (dataFieldByCache.isPresent()) {
            return (DataFieldDTO)dataFieldByCache.get();
        }
        return this.loadTableCacheByTableKey(table).stream().filter(r -> code.equals(r.getCode())).findFirst().orElse(null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public DataFieldDTO getZbKindDataFieldBySchemeKeyAndCode(String scheme, String code) {
        Assert.notNull((Object)scheme, "scheme must not be null.");
        Assert.notNull((Object)code, "code must not be null.");
        DataWrapper<DataFieldDTO> fieldByCache = this.matchFieldFromCacheByIndex(IDX_NAME_SCHEME + scheme, code, true);
        if (fieldByCache.isPresent()) {
            return (DataFieldDTO)fieldByCache.get();
        }
        IdMutexProvider.Mutex mutex = this.idMutexProvider.getMutex(scheme);
        synchronized (mutex) {
            fieldByCache = this.matchFieldFromCacheByIndex(IDX_NAME_SCHEME + scheme, code, true);
            if (fieldByCache.isPresent()) {
                return (DataFieldDTO)fieldByCache.get();
            }
            DataFieldDTO field = this.dataFieldService.getZbKindDataFieldBySchemeKeyAndCode(scheme, code);
            if (field != null) {
                String tableKey = field.getDataTableKey();
                this.loadTableCacheByTableKey(tableKey);
            } else {
                super.putHashIndexEntry(IDX_NAME_SCHEME + scheme, code, "empty");
            }
            return field;
        }
    }

    @Override
    public List<DataFieldDTO> getDataFieldByTableCode(String tableCode) {
        Assert.notNull((Object)tableCode, "tableCode must not be null.");
        DataTableDTO table = this.dataTableService.getDataTableByCode(tableCode);
        if (table == null) {
            return null;
        }
        String key = table.getKey();
        return this.getDataFieldByTable(key);
    }

    @Override
    public List<DataFieldDTO> getBizDataFieldByTableKey(String tableKey) {
        Assert.notNull((Object)tableKey, "tableKey must not be null.");
        DataTableDTO dataTable = this.dataTableService.getDataTable(tableKey);
        return this.getBizDataFieldByTableCode(dataTable);
    }

    @Override
    public List<DataFieldDTO> getBizDataFieldByTableCode(String tableCode) {
        DataTableDTO dataTable = this.dataTableService.getDataTableByCode(tableCode);
        return this.getBizDataFieldByTableCode(dataTable);
    }

    public List<DataFieldDTO> getBizDataFieldByTableCode(DataTableDTO dataTable) {
        if (dataTable == null) {
            return null;
        }
        String[] bizKeys = dataTable.getBizKeys();
        if (bizKeys == null || bizKeys.length == 0) {
            return Collections.emptyList();
        }
        return this.getDataFields(Arrays.asList(bizKeys));
    }

    @Override
    public List<DataFieldDTO> getDataFieldByTableKeyAndType(String tableKey, DataFieldType ... dataFieldType) {
        Assert.notNull((Object)tableKey, "table must not be null.");
        Assert.notNull((Object)dataFieldType, "dataFieldType must not be null.");
        DataTableDTO dataTable = this.dataTableService.getDataTable(tableKey);
        if (0 == dataFieldType.length) {
            return this.getDataFieldByTable(tableKey);
        }
        if (dataTable == null) {
            return null;
        }
        HashSet<DataFieldType> type = new HashSet<DataFieldType>(Arrays.asList(dataFieldType));
        DataWrapper<List<DataFieldDTO>> field = this.matchFieldFromCacheByTableKey(tableKey);
        if (field.isPresent()) {
            return ((List)field.get()).stream().filter(Objects::nonNull).filter(r -> type.contains(r.getDataFieldType())).collect(Collectors.toList());
        }
        List<DataFieldDTO> fields = this.loadTableCacheByTableKey(tableKey);
        return fields.stream().filter(Objects::nonNull).filter(r -> type.contains(r.getDataFieldType())).collect(Collectors.toList());
    }

    @Override
    public List<DataFieldDTO> getDataFieldByTableKeyAndKind(String tableKey, DataFieldKind ... dataFieldKinds) {
        Assert.notNull((Object)tableKey, "table must not be null.");
        Assert.notNull((Object)dataFieldKinds, "dataFieldKinds must not be null.");
        if (0 == dataFieldKinds.length) {
            return this.getDataFieldByTable(tableKey);
        }
        DataTableDTO dataTable = this.dataTableService.getDataTable(tableKey);
        if (dataTable == null) {
            return null;
        }
        HashSet<DataFieldKind> type = new HashSet<DataFieldKind>(Arrays.asList(dataFieldKinds));
        DataWrapper<List<DataFieldDTO>> field = this.matchFieldFromCacheByTableKey(tableKey);
        if (field.isPresent()) {
            return ((List)field.get()).stream().filter(Objects::nonNull).filter(r -> type.contains(r.getDataFieldKind())).collect(Collectors.toList());
        }
        List<DataFieldDTO> fields = this.loadTableCacheByTableKey(tableKey);
        return fields.stream().filter(Objects::nonNull).filter(r -> type.contains(r.getDataFieldKind())).collect(Collectors.toList());
    }

    @Override
    public List<DataFieldDTO> getDataFieldByTableCodeAndType(String tableCode, DataFieldType ... dataFieldType) {
        Assert.notNull((Object)tableCode, "tableCode must not be null.");
        Assert.notNull((Object)dataFieldType, "dataFieldType must not be null.");
        DataTableDTO table = this.dataTableService.getDataTableByCode(tableCode);
        if (table == null) {
            return null;
        }
        return this.getDataFieldByTableKeyAndType(table.getKey(), dataFieldType);
    }

    @Override
    public List<DataFieldDTO> getDataFieldByTableCodeAndKind(String tableCode, DataFieldKind ... dataFieldKinds) {
        Assert.notNull((Object)tableCode, "tableCode must not be null.");
        Assert.notNull((Object)dataFieldKinds, "dataFieldKinds must not be null.");
        DataTableDTO table = this.dataTableService.getDataTableByCode(tableCode);
        if (table == null) {
            return null;
        }
        return this.getDataFieldByTableKeyAndKind(table.getKey(), dataFieldKinds);
    }

    @Override
    public List<DataFieldDTO> getDataFieldBySchemeAndCode(String scheme, String code, DataFieldKind ... dataFieldKinds) {
        return this.dataFieldService.getDataFieldBySchemeAndCode(scheme, code, dataFieldKinds);
    }

    @Override
    public List<DataFieldDTO> searchField(FieldSearchQuery fieldSearchQuery) {
        return this.dataFieldService.searchField(fieldSearchQuery);
    }

    @Override
    public void onClearCache() {
        this.nullValueCache.clear();
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
        if ((schemeTable = refreshTable.getRefreshTable()) == null || schemeTable.isEmpty()) {
            return;
        }
        HashSet<String> removeIndex = new HashSet<String>(schemeTable.size());
        HashSet<String> tableKeys = new HashSet<String>();
        for (Map.Entry schemeEntry : schemeTable.entrySet()) {
            String scheme = ((RefreshScheme)schemeEntry.getKey()).getKey();
            Set tables = (Set)schemeEntry.getValue();
            if (scheme == null || tables == null || tables.isEmpty()) continue;
            removeIndex.add(IDX_NAME_SCHEME + scheme);
            for (RefreshTable table : tables) {
                removeIndex.add(IDX_NAME_TABLE_KEY_CODE + table.getTableKey());
                tableKeys.add(table.getTableKey());
                removeIndex.add(table.getTableKey());
            }
        }
        if (removeIndex.isEmpty()) {
            return;
        }
        if (tableKeys.isEmpty()) {
            throw new IllegalArgumentException("\u53c2\u6570\u9519\u8bef\u65e0\u6cd5\u5220\u9664");
        }
        ArrayList<String> objKeys = new ArrayList<String>();
        IdMutexProvider.Mutex mutex = this.idMutexProvider.getMutex(new HashSet(tableKeys));
        synchronized (mutex) {
            List<Cache.ValueWrapper> valueWrappers = super.mGetKVIndexValue(new ArrayList<String>(tableKeys));
            for (Cache.ValueWrapper valueWrapper : valueWrappers) {
                Set keys;
                if (valueWrapper == null || (keys = (Set)valueWrapper.get()) == null) continue;
                objKeys.addAll(keys);
            }
            if (!objKeys.isEmpty()) {
                super.removeObjects(objKeys);
            }
            super.removeIndexes(removeIndex);
        }
        this.nullValueCache.clear();
    }

    private void loadCache(DataFieldCacheValue cacheValue) {
        super.puts(new ArrayList<DataFieldDTO>(cacheValue.fieldDefines.values()));
        super.putKVIndex(cacheValue.tableKey, new LinkedHashSet<String>(cacheValue.fieldDefines.keySet()));
        super.putHashIndex(IDX_NAME_TABLE_KEY_CODE + cacheValue.tableKey, cacheValue.codeIndex);
        if (cacheValue.schemeKey != null) {
            super.putHashIndex(IDX_NAME_SCHEME + cacheValue.schemeKey, cacheValue.zbcodeIndex);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private DataFieldDTO loadTableCacheByFieldKey(String key) {
        DataFieldDTO dataField = this.dataFieldService.getDataField(key);
        if (dataField != null) {
            String dataTableKey = dataField.getDataTableKey();
            IdMutexProvider.Mutex mutex = this.idMutexProvider.getMutex(dataTableKey);
            synchronized (mutex) {
                Cache.ValueWrapper kvIndexValue = super.getKVIndexValue(dataTableKey);
                if (kvIndexValue != null) {
                    Cache.ValueWrapper valueWrapper;
                    Set keys = (Set)kvIndexValue.get();
                    if (keys == null) {
                        return null;
                    }
                    if (keys.contains(key) && (valueWrapper = super.get(key)) != null) {
                        return (DataFieldDTO)valueWrapper.get();
                    }
                    return null;
                }
                List<DataFieldDTO> dataFieldByTable = this.dataFieldService.getDataFieldByTable(dataTableKey);
                this.fieldValidationRule(dataFieldByTable);
                this.loadCache(new DataFieldCacheValue(dataTableKey, dataFieldByTable));
            }
            return dataField;
        }
        this.nullValueCache.put(key, NullValue.INSTANCE);
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<DataFieldDTO> loadTableCacheByTableKey(String tableKey) {
        IdMutexProvider.Mutex mutex = this.idMutexProvider.getMutex(tableKey);
        synchronized (mutex) {
            DataWrapper<List<DataFieldDTO>> field = this.matchFieldFromCacheByTableKey(tableKey);
            if (field.isPresent()) {
                return (List)field.get();
            }
            List<DataFieldDTO> dataFieldByTable = this.dataFieldService.getDataFieldByTable(tableKey);
            this.fieldValidationRule(dataFieldByTable);
            DataFieldCacheValue cacheValue = new DataFieldCacheValue(tableKey, dataFieldByTable);
            this.loadCache(cacheValue);
            return dataFieldByTable;
        }
    }

    private DataWrapper<List<DataFieldDTO>> matchFieldFromCacheByTableKey(String table) {
        Cache.ValueWrapper kvIndexValue = super.getKVIndexValue(table);
        if (kvIndexValue != null) {
            Set keys = (Set)kvIndexValue.get();
            if (keys == null || keys.isEmpty()) {
                return DefaultDataWrapper.valueOf(Collections.emptyList());
            }
            List<DataFieldDTO> dataFields = this.getDataFields(new ArrayList<String>(keys));
            return DefaultDataWrapper.valueOf(dataFields);
        }
        return DefaultDataWrapper.empty();
    }

    private DataWrapper<DataFieldDTO> matchFieldFromCacheByIndex(String indexName, String code, boolean empty) {
        HashCacheValue<Set<Object>> hashIndexKeys = this.getHashIndexKeys(indexName);
        if (hashIndexKeys.isPresent()) {
            Set keys = (Set)hashIndexKeys.get();
            boolean contains = keys.contains(code);
            if (contains) {
                HashCacheValue<Cache.ValueWrapper> hashIndexValue = this.getHashIndexValue(indexName, code);
                if (hashIndexValue.isPresent()) {
                    Cache.ValueWrapper valueWrapper = (Cache.ValueWrapper)hashIndexValue.get();
                    if (null == valueWrapper) {
                        return DefaultDataWrapper.empty();
                    }
                    String key = (String)valueWrapper.get();
                    if (key == null) {
                        return DefaultDataWrapper.valueOf(null);
                    }
                    if ("empty".equals(key)) {
                        return DefaultDataWrapper.valueOf(null);
                    }
                    return DefaultDataWrapper.valueOf(this.getDataField(key));
                }
                return DefaultDataWrapper.empty();
            }
            if (empty) {
                return DefaultDataWrapper.empty();
            }
            return DefaultDataWrapper.valueOf(null);
        }
        return DefaultDataWrapper.empty();
    }

    private void fieldValidationRule(List<DataFieldDTO> fields) {
        if (fields == null || fields.isEmpty()) {
            return;
        }
        Map<String, List<DataFieldDTO>> fieldByTable = fields.stream().collect(Collectors.groupingBy(DataFieldDTO::getDataTableKey));
        for (Map.Entry<String, List<DataFieldDTO>> fieldBy : fieldByTable.entrySet()) {
            String tableKey = fieldBy.getKey();
            List<DataFieldDTO> fieldList = fieldBy.getValue();
            DataTableDTO dataTable = this.dataTableService.getDataTable(tableKey);
            if (dataTable == null) continue;
            for (DataFieldDTO dataFieldDTO : fieldList) {
                List<ValidationRule> validationRules = dataFieldDTO.getValidationRules();
                if (validationRules == null || validationRules.isEmpty()) continue;
                validationRules.removeIf(r -> r.getCompareType() == CompareType.EQUAL);
                for (ValidationRule validationRule : validationRules) {
                    ValidationRuleDTO dto = (ValidationRuleDTO)validationRule;
                    dto.setFieldCode(dataFieldDTO.getCode());
                    dto.setTableCode(dataTable.getCode());
                    dto.setFieldType(dataFieldDTO.getDataFieldType());
                    dto.setFieldTitle(dataFieldDTO.getTitle());
                }
            }
        }
    }

    @Override
    public List<DataFieldDTO> getDataFieldBySchemeAndKind(String scheme, DataFieldKind ... dataFieldKinds) {
        return this.dataFieldService.getDataFieldBySchemeAndKind(scheme, dataFieldKinds);
    }

    @Override
    public DataFieldDTO getDataFieldFromMdInfoByCode(String dataSchemeKey, String dataFieldCode) {
        DataFieldDTO dataField = this.getZbKindDataFieldBySchemeKeyAndCode(dataSchemeKey, dataFieldCode);
        if (null == dataField) {
            return null;
        }
        DataTableDTO dataTable = this.dataTableService.getDataTable(dataField.getDataTableKey());
        if (null != dataTable && DataTableType.MD_INFO == dataTable.getDataTableType()) {
            return dataField;
        }
        return null;
    }

    private static class DataFieldCacheValue {
        String schemeKey;
        final String tableKey;
        final Map<String, DataFieldDTO> fieldDefines;
        final Map<String, String> codeIndex;
        final Map<String, String> zbcodeIndex;

        public DataFieldCacheValue(String tableKey, List<DataFieldDTO> fieldDefines) {
            this.tableKey = tableKey;
            this.fieldDefines = new LinkedHashMap<String, DataFieldDTO>(fieldDefines.size());
            this.codeIndex = new LinkedHashMap<String, String>(fieldDefines.size());
            this.zbcodeIndex = new LinkedHashMap<String, String>(fieldDefines.size());
            for (DataFieldDTO fieldDefine : fieldDefines) {
                String key = fieldDefine.getKey();
                String code = fieldDefine.getCode();
                this.fieldDefines.put(key, fieldDefine);
                this.codeIndex.put(code, key);
                if (fieldDefine.getDataFieldKind() != DataFieldKind.FIELD_ZB) continue;
                this.zbcodeIndex.put(code, key);
                this.schemeKey = fieldDefine.getDataSchemeKey();
            }
        }
    }
}

