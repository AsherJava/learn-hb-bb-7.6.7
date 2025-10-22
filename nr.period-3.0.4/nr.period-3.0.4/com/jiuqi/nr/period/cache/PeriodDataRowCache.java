/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.period.PeriodType
 */
package com.jiuqi.nr.period.cache;

import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.period.cache.PeriodLanguageCache;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.common.utils.StringUtils;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.IPeriodLanguage;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.period.modal.impl.I18nPeriodRow;
import com.jiuqi.nr.period.service.impl.PeriodAdapterServiceImpl;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;

@Service
public class PeriodDataRowCache {
    @Autowired
    private PeriodAdapterServiceImpl periodAdapterService;
    @Autowired
    private PeriodLanguageCache periodLanguageCache;
    private NedisCacheManager cacheManager;
    private static final String IDX_NAME = "obj_";
    public static final String ALL_PERIOD_DATA = "allperioddatacache";

    @Autowired
    public void setCacheManager(NedisCacheProvider cacheProvider) {
        this.cacheManager = cacheProvider.getCacheManager();
    }

    public void clearCache() {
        NedisCache cache = this.cacheManager.getCache(ALL_PERIOD_DATA);
        cache.clear();
    }

    private List<IPeriodRow> getAllPeriodData(String entityId) {
        List<IPeriodRow> iPeriodRows = this.initAllPeriodData(entityId);
        Map<String, String> languagesMap = this.getLanguagesMap(entityId);
        ArrayList<IPeriodRow> cloneData = new ArrayList<IPeriodRow>();
        for (IPeriodRow iPeriodRow : iPeriodRows) {
            I18nPeriodRow i18nPeriodRow = new I18nPeriodRow(iPeriodRow);
            cloneData.add(i18nPeriodRow);
        }
        return cloneData;
    }

    private Map<String, String> getLanguagesMap(String entityId) {
        HashMap<String, String> languageMap = new HashMap<String, String>();
        List<IPeriodLanguage> languages = this.periodLanguageCache.getLanguageByEntityAndLanguage(entityId, NpContextHolder.getContext().getLocale().getLanguage());
        for (IPeriodLanguage language : languages) {
            if (!StringUtils.isNotEmpty(language.getCode())) continue;
            languageMap.put(language.getCode(), language.getTitle());
        }
        return languageMap;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<IPeriodRow> initAllPeriodData(String entityId) {
        NedisCache cache = this.cacheManager.getCache(ALL_PERIOD_DATA);
        Cache.ValueWrapper valueWrapper = cache.get(IDX_NAME + entityId);
        if (valueWrapper == null) {
            Object object = new Object();
            synchronized (object) {
                Cache.ValueWrapper revalueWrapper = cache.get(IDX_NAME + entityId);
                if (Objects.nonNull(revalueWrapper)) {
                    return this.getIPeriodRow(revalueWrapper);
                }
                return this.loadCache(entityId);
            }
        }
        return this.getIPeriodRow(valueWrapper);
    }

    private List<IPeriodRow> loadCache(String entityId) {
        List<IPeriodRow> periodEntitys = this.loadAllIPeriodRows(entityId);
        NedisCache cache = this.cacheManager.getCache(ALL_PERIOD_DATA);
        cache.put(IDX_NAME + entityId, periodEntitys);
        return periodEntitys;
    }

    private List<IPeriodRow> loadAllIPeriodRows(String entityId) {
        List<IPeriodRow> periodEntitys = this.periodAdapterService.getDataListByKey(entityId);
        return periodEntitys;
    }

    private List<IPeriodRow> getIPeriodRow(Cache.ValueWrapper revalueWrapper) {
        List allPeriodKeys = (List)revalueWrapper.get();
        return allPeriodKeys;
    }

    public IPeriodRow getPeriodData(String periodId, String period) {
        List<IPeriodRow> allPeriodData = this.getAllPeriodData(periodId);
        for (IPeriodRow row : allPeriodData) {
            if (!row.getCode().equals(period)) continue;
            return row;
        }
        return null;
    }

    public List<IPeriodRow> getDataListByKey(String periodId) {
        List<IPeriodRow> allPeriodData = this.getAllPeriodData(periodId);
        if (null == allPeriodData) {
            return new ArrayList<IPeriodRow>();
        }
        return allPeriodData;
    }

    public IPeriodRow getCurPeriod(IPeriodEntity queryPeriod) {
        String res = "";
        if (queryPeriod.getType().type() == PeriodType.CUSTOM.type()) {
            List<IPeriodRow> allPeriodData = this.getAllPeriodData(queryPeriod.getKey());
            for (IPeriodRow row : allPeriodData) {
                Date curDate;
                Date date;
                if (null == row.getStartDate() || null == row.getEndDate() || (date = new Date((curDate = new Date()).getYear(), curDate.getMonth(), curDate.getDate())).compareTo(row.getEndDate()) > 0 || date.compareTo(row.getStartDate()) < 0) continue;
                res = row.getCode();
            }
            if (StringUtils.isEmpty(res) && allPeriodData.size() != 0) {
                res = allPeriodData.get(allPeriodData.size() - 1).getCode();
            }
        } else {
            res = PeriodUtils.getPeriodFromDate(queryPeriod.getType().type(), new Date());
        }
        IPeriodRow dataByDataCode = null;
        dataByDataCode = this.getPeriodData(queryPeriod.getKey(), res);
        return dataByDataCode;
    }
}

