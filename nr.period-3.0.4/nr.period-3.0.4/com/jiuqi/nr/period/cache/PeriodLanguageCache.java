/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.nvwa.definition.interval.service.cache.RuntimeDefinitionCache
 */
package com.jiuqi.nr.period.cache;

import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.nr.period.common.utils.StringUtils;
import com.jiuqi.nr.period.dao.PeriodLanguageDao;
import com.jiuqi.nr.period.modal.IPeriodLanguage;
import com.jiuqi.nr.period.service.impl.PeriodAdapterServiceImpl;
import com.jiuqi.nvwa.definition.interval.service.cache.RuntimeDefinitionCache;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;

@Service
public class PeriodLanguageCache {
    @Autowired
    private PeriodAdapterServiceImpl periodAdapterService;
    @Autowired
    private PeriodLanguageDao periodDataDao;
    private NedisCacheManager cacheManager;
    private RuntimeDefinitionCache<IPeriodLanguage> cache;
    private static final String IDX_NAME_ALL = "allperiodlanguage";

    @Autowired
    public void setCacheManager(NedisCacheProvider cacheProvider) {
        this.cacheManager = cacheProvider.getCacheManager();
        this.cache = new RuntimeDefinitionCache(this.cacheManager, IPeriodLanguage.class);
    }

    public void clearCache() {
        this.cache.clear();
    }

    private List<IPeriodLanguage> getAllPeriodLanguage() {
        List<IPeriodLanguage> iPeriodEntities = this.initAllPeriodLanguage();
        ArrayList<IPeriodLanguage> cloneData = new ArrayList<IPeriodLanguage>();
        for (IPeriodLanguage iPeriodEntity : iPeriodEntities) {
            cloneData.add(iPeriodEntity);
        }
        return cloneData;
    }

    public List<IPeriodLanguage> getLanguageByEntityAndLanguage(String entity, String language) {
        if (StringUtils.isEmpty(entity) || StringUtils.isEmpty(language)) {
            return Collections.emptyList();
        }
        List<IPeriodLanguage> allPeriodLanguage = this.getAllPeriodLanguage();
        return allPeriodLanguage.stream().filter(e -> entity.equals(e.getEntity()) && language.equals(e.getType())).collect(Collectors.toList());
    }

    public List<IPeriodLanguage> getLanguageByEntity(String entity) {
        if (StringUtils.isEmpty(entity)) {
            return Collections.emptyList();
        }
        List<IPeriodLanguage> allPeriodLanguage = this.getAllPeriodLanguage();
        return allPeriodLanguage.stream().filter(e -> entity.equals(e.getEntity())).collect(Collectors.toList());
    }

    public IPeriodLanguage getPeriodLanguageByEntityAndCode(String entity, String code, String language) {
        if (StringUtils.isEmpty(entity) || StringUtils.isEmpty(language)) {
            return null;
        }
        List<IPeriodLanguage> allPeriodLanguage = this.getAllPeriodLanguage();
        for (IPeriodLanguage e : allPeriodLanguage) {
            if (!(code == null ? entity.equals(e.getEntity()) && language.equals(e.getType()) && null == e.getCode() : entity.equals(e.getEntity()) && language.equals(e.getType()) && code.equals(e.getCode()))) continue;
            return e;
        }
        return null;
    }

    private List<IPeriodLanguage> initAllPeriodLanguage() {
        Cache.ValueWrapper valueWrapper = this.cache.getKVIndexValue(IDX_NAME_ALL);
        if (valueWrapper == null) {
            return (List)this.cache.synchronizedRun(() -> {
                Cache.ValueWrapper revalueWrapper = this.cache.getKVIndexValue(IDX_NAME_ALL);
                if (Objects.nonNull(revalueWrapper)) {
                    return this.getIPeriodLanguage(revalueWrapper);
                }
                return this.loadCache();
            });
        }
        return this.getIPeriodLanguage(valueWrapper);
    }

    private List<IPeriodLanguage> loadCache() {
        List<IPeriodLanguage> periodLanguage = this.loadAllIPeriodLanguage();
        this.cache.putKVIndex(IDX_NAME_ALL, periodLanguage);
        return periodLanguage;
    }

    private List<IPeriodLanguage> loadAllIPeriodLanguage() {
        List<IPeriodLanguage> periodLanguage = this.periodDataDao.queryPeriodLanguage();
        return periodLanguage;
    }

    private List<IPeriodLanguage> getIPeriodLanguage(Cache.ValueWrapper revalueWrapper) {
        List periodLanguages = (List)revalueWrapper.get();
        return periodLanguages;
    }
}

