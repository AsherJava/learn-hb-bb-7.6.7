/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.np.definition.deploy.RefreshDefinitionCacheEvent
 *  com.jiuqi.nr.datascheme.i18n.language.ILanguageType
 *  com.jiuqi.nr.datascheme.i18n.language.LanguageType
 */
package com.jiuqi.nr.definition.paramlanguage.cache;

import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.np.definition.deploy.RefreshDefinitionCacheEvent;
import com.jiuqi.nr.datascheme.i18n.language.ILanguageType;
import com.jiuqi.nr.datascheme.i18n.language.LanguageType;
import com.jiuqi.nr.definition.deploy.DeployFinishedEvent;
import com.jiuqi.nr.definition.deploy.DeployParams;
import com.jiuqi.nr.definition.internal.dao.RunTimeBigDataTableDao;
import com.jiuqi.nr.definition.internal.impl.RunTimeBigDataTable;
import com.jiuqi.nr.definition.paramlanguage.dao.SysParamLanguageDao;
import com.jiuqi.nr.definition.paramlanguage.entity.SysParamLanguage;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
public class ParamLanguageCache {
    @Autowired
    private SysParamLanguageDao sysParamLanguageDao;
    @Autowired
    private RunTimeBigDataTableDao bigDataDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(ParamLanguageCache.class);
    private static final String CACHE_SYMBOL = "_";
    private static final String CACHE_MANAGER_NAME = "paramLanguage_designer";
    private static final String CACHE_MANAGER_BIGDATA_NAME = "paramLanguage_bigData_designer";
    private final NedisCache cache;
    private final NedisCache bigDataCache;

    public ParamLanguageCache(NedisCacheProvider cacheProvider) {
        NedisCacheManager cacheManager = cacheProvider.getCacheManager("NR_DEFINITION_CACHE_CONFIGURATION");
        this.cache = cacheManager.getCache(CACHE_MANAGER_NAME);
        this.bigDataCache = cacheManager.getCache(CACHE_MANAGER_BIGDATA_NAME);
    }

    public String queryParamLanguage(String resourceKey, int languageType) {
        String cacheKey = resourceKey + CACHE_SYMBOL + languageType;
        Cache.ValueWrapper cachedLanguage = this.cache.get(cacheKey);
        if (cachedLanguage == null) {
            return this.loadCache(this.cache, resourceKey, languageType);
        }
        return (String)cachedLanguage.get();
    }

    private String loadCache(NedisCache cache, String resourceKey, int languageType) {
        String cacheKey = resourceKey + CACHE_SYMBOL + languageType;
        List<SysParamLanguage> sysParamLanguages = this.sysParamLanguageDao.queryLanguageByResKeyAndLanguageType(resourceKey, String.valueOf(languageType));
        SysParamLanguage language = new SysParamLanguage();
        if (!sysParamLanguages.isEmpty()) {
            language = sysParamLanguages.get(0);
            cache.put(cacheKey, (Object)language.getLanguageInfo());
        } else {
            cache.put(cacheKey, (Object)"");
        }
        return language.getLanguageInfo();
    }

    public byte[] queryBigDataParamLanguage(String resourceKey, String category, int languageType) {
        String cacheKey = resourceKey + CACHE_SYMBOL + category + CACHE_SYMBOL + languageType;
        Cache.ValueWrapper cachedLanguage = this.bigDataCache.get(cacheKey);
        if (cachedLanguage == null) {
            return this.loadBigDataCache(this.bigDataCache, category, resourceKey, languageType);
        }
        return (byte[])cachedLanguage.get();
    }

    private byte[] loadBigDataCache(NedisCache cache, String category, String resourceKey, int languageType) {
        String cacheKey = resourceKey + CACHE_SYMBOL + category + CACHE_SYMBOL + languageType;
        RunTimeBigDataTable runTimeBigDataTable = this.bigDataDao.queryigDataDefine(resourceKey, category, languageType);
        byte[] formData = null;
        if (runTimeBigDataTable != null) {
            formData = runTimeBigDataTable.getData();
            cache.put(cacheKey, (Object)formData);
        } else {
            cache.put(cacheKey, null);
        }
        return formData;
    }

    @Deprecated
    public void clean(DeployParams deployParams) {
        Set taskKeys = deployParams.getTaskDefine().getRunTimeKeys();
        this.clean(this.cache, taskKeys);
        Set FormSchemeKeys = deployParams.getFormScheme().getRunTimeKeys();
        this.clean(this.cache, FormSchemeKeys);
        Set FormKeys = deployParams.getForm().getRunTimeKeys();
        this.clean(this.cache, FormKeys);
        this.clean(this.bigDataCache, FormKeys);
        this.cleanFulling(this.bigDataCache, FormKeys);
        Set FormGroupKeys = deployParams.getFormGroup().getRunTimeKeys();
        this.clean(this.cache, FormGroupKeys);
        Set FormulaSchemeKeys = deployParams.getFormulaScheme().getRunTimeKeys();
        this.clean(this.cache, FormulaSchemeKeys);
        Set RegionSettingKeys = deployParams.getRegionSetting().getRunTimeKeys();
        this.clean(this.bigDataCache, RegionSettingKeys);
        Set FormulaKeys = deployParams.getFormula().getRunTimeKeys();
        this.clean(this.cache, FormulaKeys);
    }

    private void cleanFulling(NedisCache bigDataCache, Set<String> formKeys) {
        List<String> allType = this.getAllLanguageType();
        ArrayList<String> needClean = new ArrayList<String>();
        for (String key : formKeys) {
            for (String type : allType) {
                needClean.add(key + CACHE_SYMBOL + "FORM_DATA" + CACHE_SYMBOL + type);
                needClean.add(key + CACHE_SYMBOL + "FILLING_GUIDE" + CACHE_SYMBOL + type);
            }
        }
        bigDataCache.mEvict(needClean);
    }

    private void clean(NedisCache cache, Set<String> keys) {
        List<String> allType = this.getAllLanguageType();
        ArrayList<String> needClean = new ArrayList<String>();
        for (String key : keys) {
            for (String type : allType) {
                needClean.add(key + CACHE_SYMBOL + type);
            }
        }
        cache.mEvict(needClean);
    }

    private List<String> getAllLanguageType() {
        ArrayList<String> allType = new ArrayList<String>();
        List allValues = LanguageType.allValues();
        for (ILanguageType iLanguageType : allValues) {
            allType.add(iLanguageType.getKey());
        }
        return allType;
    }

    public void clear() {
        this.cache.clear();
        this.bigDataCache.clear();
    }

    public void clearByTask(String taskKey) {
        this.clear();
    }

    public void clearByFormScheme(String formSchemeKey) {
        this.clear();
    }

    public void clearByFormulaScheme(String formulaSchemeKey) {
        this.clear();
    }

    @Service
    public static class ParamRefreshEventListener
    implements ApplicationListener<RefreshDefinitionCacheEvent> {
        @Autowired
        private ParamLanguageCache paramLanguageCache;

        @Override
        public void onApplicationEvent(RefreshDefinitionCacheEvent event) {
            LOGGER.info("\u62a5\u8868\u53c2\u6570\u591a\u8bed\u8a00\uff1a\u6e05\u7406\u5168\u90e8\u7f13\u5b58");
            this.paramLanguageCache.cache.clear();
        }
    }

    @Service
    public static class ParamDeployFinishedEventListener
    implements ApplicationListener<DeployFinishedEvent> {
        @Autowired
        private ParamLanguageCache paramLanguageCache;

        @Override
        public void onApplicationEvent(DeployFinishedEvent event) {
            LOGGER.info("\u62a5\u8868\u53c2\u6570\u591a\u8bed\u8a00\uff1a\u6e05\u7406\u7f13\u5b58");
            DeployParams deployParams = event.getDeployParams();
            Set keys = deployParams.getTaskDefine().getRuntimeUninDesignTimeKeys();
            if (!keys.isEmpty()) {
                for (String key : keys) {
                    this.paramLanguageCache.clearByTask(key);
                }
                return;
            }
            keys = deployParams.getFormScheme().getRuntimeUninDesignTimeKeys();
            if (!keys.isEmpty()) {
                for (String key : keys) {
                    this.paramLanguageCache.clearByFormScheme(key);
                }
                return;
            }
            keys = deployParams.getFormulaScheme().getRuntimeUninDesignTimeKeys();
            if (!keys.isEmpty()) {
                for (String key : keys) {
                    this.paramLanguageCache.clearByFormulaScheme(key);
                }
                return;
            }
        }
    }
}

