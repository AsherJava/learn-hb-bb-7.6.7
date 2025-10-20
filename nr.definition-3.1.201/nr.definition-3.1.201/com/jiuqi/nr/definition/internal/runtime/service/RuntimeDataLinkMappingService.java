/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.np.definition.internal.service.RuntimeDefinitionRefreshListener
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.interval.service.cache.RuntimeDefinitionCache
 *  com.jiuqi.xlib.utils.CollectionUtils
 */
package com.jiuqi.nr.definition.internal.runtime.service;

import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.np.definition.internal.service.RuntimeDefinitionRefreshListener;
import com.jiuqi.nr.definition.common.DataLinkMappingCacheObj;
import com.jiuqi.nr.definition.deploy.DeployParams;
import com.jiuqi.nr.definition.facade.DataLinkMappingDefine;
import com.jiuqi.nr.definition.internal.dao.RuntimeDataLinkMappingDefineDao;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataLinkMappingService;
import com.jiuqi.nr.definition.internal.runtime.service.RuntimeDefinitionChangeListener;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.interval.service.cache.RuntimeDefinitionCache;
import com.jiuqi.xlib.utils.CollectionUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.cache.Cache;

@Deprecated
public class RuntimeDataLinkMappingService
implements IRuntimeDataLinkMappingService,
RuntimeDefinitionChangeListener,
RuntimeDefinitionRefreshListener {
    private final RuntimeDataLinkMappingDefineDao dataLinkMappingDao;
    private final RuntimeDefinitionCache<DataLinkMappingCacheObj> cache;
    private final RuntimeDataLinkMappingCacheLoader cacheLoader = new RuntimeDataLinkMappingCacheLoader();

    public RuntimeDataLinkMappingService(RuntimeDataLinkMappingDefineDao dataLinkMappingDao, NedisCacheProvider cacheProvider) {
        this.dataLinkMappingDao = dataLinkMappingDao;
        this.cache = new RuntimeDefinitionCache(cacheProvider.getCacheManager("NR_DEFINITION_CACHE_CONFIGURATION"), DataLinkMappingCacheObj.class);
    }

    @Override
    public List<DataLinkMappingDefine> queryDataLinkMappingByFormKey(String formKey) {
        if (formKey == null) {
            return Collections.emptyList();
        }
        DataLinkMappingCacheObj cacheObj = new DataLinkMappingCacheObj();
        Cache.ValueWrapper valueWrapper = this.cache.getObject(formKey);
        if (valueWrapper == null) {
            return (List)RuntimeDefinitionCache.synchronizedRun((Object)this, () -> {
                DataLinkMappingCacheObj cacheObj1;
                Cache.ValueWrapper valueWrapp = this.cache.getObject(formKey);
                if (valueWrapp != null && (cacheObj1 = (DataLinkMappingCacheObj)valueWrapp.get()) != null) {
                    return cacheObj1.getValue();
                }
                List<DataLinkMappingDefine> dataLinkMapping = this.cacheLoader.loadCacheByFormKey(formKey);
                if (CollectionUtils.isEmpty(dataLinkMapping)) {
                    this.cache.putNullObject(formKey);
                }
                return dataLinkMapping;
            });
        }
        cacheObj = (DataLinkMappingCacheObj)valueWrapper.get();
        if (cacheObj == null) {
            return Collections.emptyList();
        }
        return cacheObj.getValue();
    }

    public void onClearCache() {
        this.cache.clear();
    }

    @Override
    public void onDeploy(DeployParams deployParams) {
        ArrayList<String> deploideFormKeys = new ArrayList<String>();
        for (String formKey : deployParams.getForm().getRuntimeUninDesignTimeKeys()) {
            deploideFormKeys.add(formKey);
        }
        this.cache.removeObjects(deploideFormKeys);
    }

    private class RuntimeDataLinkMappingCacheLoader {
        private RuntimeDataLinkMappingCacheLoader() {
        }

        public List<DataLinkMappingDefine> loadCacheByFormKey(String formKey) {
            DataLinkMappingCacheObj cacheObj = new DataLinkMappingCacheObj();
            List<DataLinkMappingDefine> dataLinkMapping = RuntimeDataLinkMappingService.this.dataLinkMappingDao.getByFormKey(formKey);
            if (CollectionUtils.isEmpty(dataLinkMapping)) {
                return Collections.emptyList();
            }
            cacheObj.setFormKey(formKey);
            cacheObj.setValue(dataLinkMapping);
            RuntimeDataLinkMappingService.this.cache.putObject((IModelDefineItem)cacheObj);
            return dataLinkMapping;
        }
    }
}

