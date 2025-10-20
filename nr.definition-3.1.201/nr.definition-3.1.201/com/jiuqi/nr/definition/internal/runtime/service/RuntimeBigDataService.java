/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.np.definition.internal.service.RuntimeDefinitionCacheObject
 *  com.jiuqi.np.definition.internal.service.RuntimeDefinitionOrdinaryCache
 *  com.jiuqi.np.definition.internal.service.RuntimeDefinitionRefreshListener
 */
package com.jiuqi.nr.definition.internal.runtime.service;

import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.np.definition.internal.service.RuntimeDefinitionCacheObject;
import com.jiuqi.np.definition.internal.service.RuntimeDefinitionOrdinaryCache;
import com.jiuqi.np.definition.internal.service.RuntimeDefinitionRefreshListener;
import com.jiuqi.nr.definition.deploy.DeployParams;
import com.jiuqi.nr.definition.facade.BigDataDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.internal.dao.RunTimeBigDataTableDao;
import com.jiuqi.nr.definition.internal.impl.RunTimeBigDataTable;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeBigDataService;
import com.jiuqi.nr.definition.internal.runtime.service.RuntimeDefinitionChangeListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;

@Service
public class RuntimeBigDataService
implements IRuntimeBigDataService,
RuntimeDefinitionChangeListener,
RuntimeDefinitionRefreshListener {
    private final RunTimeBigDataTableDao bigDataDao;
    private final RuntimeDefinitionOrdinaryCache cache;
    private final RuntimeBigDataCacheLoader cacheLoader = new RuntimeBigDataCacheLoader();

    private static String createIndexNameByType(String key, String type) {
        return type.toLowerCase().concat("::").concat(key);
    }

    private static String createFormDataIndexName(String formKey) {
        return "FORM_DATA".toLowerCase().concat("::").concat(formKey);
    }

    private static String createFillingGuideIndexName(String formKey) {
        return "FILLING_GUIDE".toLowerCase().concat("::").concat(formKey);
    }

    private static String createBigSurveyDataIndexName(String formKey) {
        return "BIG_SURVEY_DATA".toLowerCase().concat("::").concat(formKey);
    }

    private static String createBigScriptEditorIndexName(String formKey) {
        return "BIG_SCRIPT_EDITOR".toLowerCase().concat("::").concat(formKey);
    }

    private static String createBigAttachMentIndexName(String formKey) {
        return "ATTACHMENT".toLowerCase().concat("::").concat(formKey);
    }

    private static String createBigExtentStyleIndexName(String formKey) {
        return "EXTENTSTYLE".toLowerCase().concat("::").concat(formKey);
    }

    public RuntimeBigDataService(RunTimeBigDataTableDao bigDataDao, NedisCacheProvider cacheProvider) {
        if (bigDataDao == null) {
            throw new IllegalArgumentException("'bigDataDao' must not be null.");
        }
        this.bigDataDao = bigDataDao;
        this.cache = new RuntimeDefinitionOrdinaryCache(cacheProvider.getCacheManager("NR_DEFINITION_CACHE_CONFIGURATION"), FormDefine.class);
    }

    @Override
    public BigDataDefine getBigDataDefineFromForm(String key, String type) {
        if (key == null) {
            throw new IllegalArgumentException("'key' must not be null.");
        }
        Cache.ValueWrapper valueWrapper = this.cache.getKVIndexValue(RuntimeBigDataService.createIndexNameByType(key, type));
        if (Objects.nonNull(valueWrapper)) {
            return (BigDataDefine)valueWrapper.get();
        }
        return (BigDataDefine)RuntimeDefinitionCacheObject.synchronizedRun((Object)this, () -> {
            Cache.ValueWrapper revalueWrapper = this.cache.getKVIndexValue(RuntimeBigDataService.createIndexNameByType(key, type));
            if (Objects.nonNull(revalueWrapper)) {
                return (BigDataDefine)revalueWrapper.get();
            }
            BigDataDefine bigData = (BigDataDefine)this.cacheLoader.loadBigDataByForm(key, type).get(key);
            if (Objects.isNull(bigData)) {
                return null;
            }
            return bigData;
        });
    }

    public void onClearCache() {
        this.cache.clear();
    }

    @Override
    public void onDeploy(DeployParams deployParams) {
        Set deploiedRegionSettingKeys;
        Set deploiedLinkKeys;
        String form = null;
        Iterator iterator = deployParams.getForm().getRuntimeUninDesignTimeKeys().iterator();
        if (iterator.hasNext()) {
            String formKey;
            form = formKey = (String)iterator.next();
        }
        HashSet<String> bigDataIndexName = new HashSet<String>();
        Set deploiedFormKeys = deployParams.getForm().getRuntimeUninDesignTimeKeys();
        if (deploiedFormKeys != null && deploiedFormKeys.size() > 0) {
            for (Object formKey : deploiedFormKeys) {
                bigDataIndexName.add(RuntimeBigDataService.createFormDataIndexName((String)formKey));
                bigDataIndexName.add(RuntimeBigDataService.createFillingGuideIndexName((String)formKey));
                bigDataIndexName.add(RuntimeBigDataService.createBigSurveyDataIndexName((String)formKey));
                bigDataIndexName.add(RuntimeBigDataService.createBigScriptEditorIndexName((String)formKey));
            }
        }
        if ((deploiedLinkKeys = deployParams.getDataLink().getRuntimeUninDesignTimeKeys()) != null && deploiedLinkKeys.size() > 0) {
            for (String linkKey : deploiedLinkKeys) {
                bigDataIndexName.add(RuntimeBigDataService.createBigAttachMentIndexName(linkKey));
            }
        }
        if ((deploiedRegionSettingKeys = deployParams.getRegionSetting().getRuntimeUninDesignTimeKeys()) != null && deploiedRegionSettingKeys.size() > 0) {
            for (String regionSettingKey : deploiedRegionSettingKeys) {
                bigDataIndexName.add(RuntimeBigDataService.createBigExtentStyleIndexName(regionSettingKey));
            }
        }
        this.cache.removeIndexs(bigDataIndexName);
    }

    private class RuntimeBigDataCacheLoader {
        private RuntimeBigDataCacheLoader() {
        }

        private Map<String, BigDataDefine> loadBigDataByForm(String key, String type) {
            RunTimeBigDataTable data = RuntimeBigDataService.this.bigDataDao.queryigDataDefine(key, type);
            HashMap<String, BigDataDefine> dataMap = new HashMap<String, BigDataDefine>();
            RuntimeBigDataService.this.cache.putKVIndex(RuntimeBigDataService.createIndexNameByType(key, type), (Object)data);
            dataMap.put(key, data);
            return dataMap;
        }
    }
}

