/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.event.RefreshCache
 *  com.jiuqi.nr.datascheme.api.service.IdMutexProvider
 *  com.jiuqi.nr.datascheme.api.service.IdMutexProvider$Mutex
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.datascheme.internal.service.impl.cache;

import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.event.RefreshCache;
import com.jiuqi.nr.datascheme.api.service.IdMutexProvider;
import com.jiuqi.nr.datascheme.internal.dao.impl.DataFieldDeployInfoDaoImpl;
import com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO;
import com.jiuqi.nr.datascheme.internal.dto.DataTableDTO;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO;
import com.jiuqi.nr.datascheme.internal.service.DataFieldDeployInfoService;
import com.jiuqi.nr.datascheme.internal.service.DataFieldService;
import com.jiuqi.nr.datascheme.internal.service.DataTableService;
import com.jiuqi.nr.datascheme.internal.service.SchemeRefreshListener;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.support.NullValue;
import org.springframework.util.CollectionUtils;

@Deprecated
public class DataFieldDeployInfoCacheServiceImpl
implements DataFieldDeployInfoService,
SchemeRefreshListener {
    private static final String CACHE_INDEX_NULLVALUE = "CACHE_INDEX_NULLVALUE";
    private static final String OBJECT_CACHE_KEY_PREFIX = "obj_";
    private static final String INDEX_CACHE_KEY_PREFIX = "idx_";
    @Autowired
    private DataFieldDeployInfoDaoImpl dataFieldDeployInfoDao;
    @Autowired
    private DataFieldService dataFieldService;
    @Autowired
    private DataTableService dataTableService;
    @Autowired
    private DataModelService dataModelService;
    private NedisCache cache;
    private final IdMutexProvider idMutexProvider;

    public DataFieldDeployInfoCacheServiceImpl(NedisCacheProvider cacheProvider) {
        if (cacheProvider == null) {
            throw new IllegalArgumentException("'cacheProvider' must not be null.");
        }
        this.cache = cacheProvider.getCacheManager("nr:scheme:runtime").getCache(DataFieldDeployInfoCacheServiceImpl.class.getSimpleName());
        this.idMutexProvider = new IdMutexProvider();
    }

    private String createCacheObjKey(String indexName) {
        if (indexName == null) {
            throw new IllegalArgumentException("'indexName' must not be null.");
        }
        return OBJECT_CACHE_KEY_PREFIX.concat(indexName);
    }

    private String createCacheIndexKey(String indexName) {
        if (indexName == null) {
            throw new IllegalArgumentException("'indexName' must not be null.");
        }
        return INDEX_CACHE_KEY_PREFIX.concat(indexName);
    }

    private <K, V> void mapListAddItem(Map<K, List<V>> map, K key, V value) {
        if (map.containsKey(key)) {
            map.get(key).add(value);
        } else {
            ArrayList<V> columnKeySet = new ArrayList<V>();
            columnKeySet.add(value);
            map.put(key, columnKeySet);
        }
    }

    private void loadCache(String dataSchemeKey) {
        List<DataFieldDeployInfoDO> allDataFieldInfos = this.dataFieldDeployInfoDao.getByDataSchemeKey(dataSchemeKey);
        HashMap<String, DataFieldDeployInfo> columnModelInfoMap = new HashMap<String, DataFieldDeployInfo>();
        HashMap dataFieldIndex = new HashMap();
        HashMap dataTableIndex = new HashMap();
        HashMap tableModelIndex = new HashMap();
        if (null != allDataFieldInfos && !allDataFieldInfos.isEmpty()) {
            for (DataFieldDeployInfo dataFieldDeployInfo : allDataFieldInfos) {
                String columnCacheKey = this.createCacheObjKey(dataFieldDeployInfo.getColumnModelKey());
                columnModelInfoMap.put(columnCacheKey, dataFieldDeployInfo);
                String dataFieldCacheKey = this.createCacheIndexKey(dataFieldDeployInfo.getDataFieldKey());
                this.mapListAddItem(dataFieldIndex, dataFieldCacheKey, dataFieldDeployInfo.getColumnModelKey());
                String dataTableCacheKey = this.createCacheIndexKey(dataFieldDeployInfo.getDataTableKey());
                this.mapListAddItem(dataTableIndex, dataTableCacheKey, dataFieldDeployInfo.getColumnModelKey());
                String tableModelCacheKey = this.createCacheIndexKey(dataFieldDeployInfo.getTableModelKey());
                this.mapListAddItem(tableModelIndex, tableModelCacheKey, dataFieldDeployInfo.getColumnModelKey());
            }
        }
        this.cache.mPut(columnModelInfoMap);
        this.cache.mPut(dataFieldIndex);
        this.cache.mPut(dataTableIndex);
        this.cache.mPut(tableModelIndex);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<DataFieldDeployInfoDO> loadCacheByTableModelKey(String tableModelKey) {
        IdMutexProvider.Mutex mutex = this.idMutexProvider.getMutex(tableModelKey);
        synchronized (mutex) {
            List<DataFieldDeployInfoDO> deployInfos = this.dataFieldDeployInfoDao.getByTableModelKey(tableModelKey);
            if (null != deployInfos && !deployInfos.isEmpty()) {
                this.loadCache(deployInfos.get(0).getDataSchemeKey());
            } else {
                this.putNullObject(this.createCacheIndexKey(tableModelKey));
                List columnModelDefines = this.dataModelService.getColumnModelDefinesByTable(tableModelKey);
                if (null != columnModelDefines && !columnModelDefines.isEmpty()) {
                    for (ColumnModelDefine c : columnModelDefines) {
                        this.putNullObject(this.createCacheObjKey(c.getID()));
                    }
                }
            }
            return deployInfos;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private DataFieldDeployInfo loadCacheByColModelKey(String columModelKey) {
        IdMutexProvider.Mutex mutex = this.idMutexProvider.getMutex(columModelKey);
        synchronized (mutex) {
            DataFieldDeployInfoDO deployInfo = this.dataFieldDeployInfoDao.getByColumnModelKey(columModelKey);
            if (null != deployInfo) {
                this.loadCache(deployInfo.getDataSchemeKey());
            } else {
                ColumnModelDefine columnModelDefine = this.dataModelService.getColumnModelDefineByID(columModelKey);
                String columnCacheKey = this.createCacheObjKey(columModelKey);
                if (null == columnModelDefine) {
                    this.putNullObject(columnCacheKey);
                    return null;
                }
                List<DataFieldDeployInfo> byTableModelKey = this.getByTableModelKey(columnModelDefine.getTableID());
                if (CollectionUtils.isEmpty(byTableModelKey)) {
                    return null;
                }
                Set dfColumnKeys = byTableModelKey.stream().map(DataFieldDeployInfo::getColumnModelKey).collect(Collectors.toSet());
                List allColumns = this.dataModelService.getColumnModelDefinesByTable(columnModelDefine.getTableID());
                for (ColumnModelDefine column : allColumns) {
                    if (dfColumnKeys.contains(column.getID())) continue;
                    this.putNullObject(this.createCacheObjKey(column.getID()));
                }
            }
            return deployInfo;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void loadCacheByDataTalbeKey(String dataTableKey) {
        IdMutexProvider.Mutex mutex = this.idMutexProvider.getMutex(dataTableKey);
        synchronized (mutex) {
            DataTableDTO dataTable = this.dataTableService.getDataTable(dataTableKey);
            if (null == dataTable) {
                this.putNullObject(this.createCacheIndexKey(dataTableKey));
            } else {
                this.loadCache(dataTable.getDataSchemeKey());
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void loadCacheByDataFieldKey(String dataFieldKey) {
        IdMutexProvider.Mutex mutex = this.idMutexProvider.getMutex(dataFieldKey);
        synchronized (mutex) {
            DataFieldDTO dataField = this.dataFieldService.getDataField(dataFieldKey);
            if (null == dataField) {
                this.putNullObject(this.createCacheIndexKey(dataFieldKey));
            } else {
                this.loadCache(dataField.getDataSchemeKey());
            }
        }
    }

    private void putNullObject(String key) {
        this.cache.hSet(CACHE_INDEX_NULLVALUE, (Object)key, NullValue.INSTANCE);
    }

    private boolean isNullObject(String key) {
        Cache.ValueWrapper valueWrapper = this.cache.hGet(CACHE_INDEX_NULLVALUE, (Object)key);
        return null != valueWrapper;
    }

    private Cache.ValueWrapper getValueWrapper(String key) {
        return this.cache.get(key);
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
    public List<DataFieldDeployInfo> getByDataSchemeKey(String dataSchemeKey) {
        return this.dataFieldDeployInfoDao.getByDataSchemeKey(dataSchemeKey).stream().map(v -> v).collect(Collectors.toList());
    }

    @Override
    public List<String> getTableNames(String ... dataTableKeys) {
        return this.dataFieldDeployInfoDao.getTableNames(dataTableKeys);
    }

    @Override
    public String getDataTableByTableModel(String tableModelKey) {
        List<DataFieldDeployInfoDO> infos = this.dataFieldDeployInfoDao.getByTableModelKey(tableModelKey);
        if (null != infos && !infos.isEmpty()) {
            return infos.get(0).getDataTableKey();
        }
        return null;
    }

    @Override
    public DataFieldDeployInfo getByColumnModelKey(String columnKey) {
        String columnCacheKey = this.createCacheObjKey(columnKey);
        if (this.isNullObject(columnCacheKey)) {
            return null;
        }
        Cache.ValueWrapper valueWrapper = this.getValueWrapper(columnCacheKey);
        if (null == valueWrapper) {
            return this.loadCacheByColModelKey(columnKey);
        }
        return (DataFieldDeployInfo)valueWrapper.get();
    }

    @Override
    public List<DataFieldDeployInfo> getByColumnModelKeys(List<String> columnKeys) {
        if (null == columnKeys || columnKeys.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<DataFieldDeployInfo> result = new ArrayList<DataFieldDeployInfo>();
        for (String columnKey : columnKeys) {
            DataFieldDeployInfo deployInfo = this.getByColumnModelKey(columnKey);
            if (null == deployInfo) continue;
            result.add(deployInfo);
        }
        return result;
    }

    private List<DataFieldDeployInfo> getByIndex(String key, Consumer<String> load) {
        String cacheIndexKey = this.createCacheIndexKey(key);
        if (this.isNullObject(cacheIndexKey)) {
            return Collections.emptyList();
        }
        Cache.ValueWrapper valueWrapper = this.getValueWrapper(cacheIndexKey);
        if (null == valueWrapper) {
            load.accept(key);
            valueWrapper = this.getValueWrapper(cacheIndexKey);
        }
        if (null == valueWrapper) {
            return Collections.emptyList();
        }
        Object object = valueWrapper.get();
        if (null == object) {
            return Collections.emptyList();
        }
        return this.getByColumnModelKeys((List)object);
    }

    @Override
    public List<DataFieldDeployInfo> getByDataTableKey(String dataTableKey) {
        return this.getByIndex(dataTableKey, this::loadCacheByDataTalbeKey);
    }

    @Override
    public List<DataFieldDeployInfo> getByTableModelKey(String tableModelKey) {
        return this.getByIndex(tableModelKey, this::loadCacheByTableModelKey);
    }

    @Override
    public List<DataFieldDeployInfo> getByTableName(String tableName) {
        TableModelDefine tableModel = this.dataModelService.getTableModelDefineByName(tableName);
        if (null == tableModel) {
            return Collections.emptyList();
        }
        return this.getByTableModelKey(tableModel.getID());
    }

    @Override
    public List<DataFieldDeployInfo> getByDataFieldKeys(String ... dataFieldKeys) {
        if (null == dataFieldKeys || 0 == dataFieldKeys.length) {
            return Collections.emptyList();
        }
        ArrayList<DataFieldDeployInfo> result = new ArrayList<DataFieldDeployInfo>();
        for (String dataFieldKey : dataFieldKeys) {
            result.addAll(this.getByDataFieldKey(dataFieldKey));
        }
        return result;
    }

    private List<DataFieldDeployInfo> getByDataFieldKey(String dataFieldKey) {
        return this.getByIndex(dataFieldKey, this::loadCacheByDataFieldKey);
    }

    @Override
    public List<DataFieldDeployInfo> getByTableCode(String tableCode) {
        DataTableDTO dataTable = this.dataTableService.getDataTableByCode(tableCode);
        if (null == dataTable) {
            return Collections.emptyList();
        }
        return this.getByDataTableKey(dataTable.getKey());
    }
}

