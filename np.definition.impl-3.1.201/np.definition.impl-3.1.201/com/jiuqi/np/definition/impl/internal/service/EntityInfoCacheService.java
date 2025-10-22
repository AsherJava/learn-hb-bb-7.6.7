/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.np.definition.impl.internal.service;

import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;

@Service
public class EntityInfoCacheService {
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    private NedisCache cache;

    @Autowired
    private void setCacheManager(NedisCacheProvider cacheProvider) {
        NedisCacheManager cacheManager = cacheProvider.getCacheManager("np:definition:impl");
        this.cache = cacheManager.getCache("DEFINITION_ENTITY_INFO");
    }

    public String getEntityIdByBizColumn(String columnId) {
        ColumnModelDefine columnModel = this.dataModelService.getColumnModelDefineByID(columnId);
        if (null == columnModel) {
            return null;
        }
        return this.getEntityIdByTable(columnModel.getTableID());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String getEntityIdByTable(String tableId) {
        Cache.ValueWrapper valueWrapper = this.cache.get(tableId);
        if (null != valueWrapper) {
            return (String)valueWrapper.get();
        }
        EntityInfoCacheService entityInfoCacheService = this;
        synchronized (entityInfoCacheService) {
            valueWrapper = this.cache.get(tableId);
            if (null != valueWrapper) {
                return (String)valueWrapper.get();
            }
            IPeriodEntity iPeriodByTableKey = this.periodEntityAdapter.getIPeriodByTableKey(tableId);
            if (iPeriodByTableKey != null) {
                String code = iPeriodByTableKey.getCode();
                this.cache.put(tableId, (Object)code);
                return code;
            }
            TableModelDefine tableModel = this.dataModelService.getTableModelDefineById(tableId);
            String entityId = null;
            if (null != tableModel) {
                entityId = this.iEntityMetaService.getEntityIdByCode(tableModel.getCode());
            }
            this.cache.put(tableId, entityId);
            return entityId;
        }
    }
}

