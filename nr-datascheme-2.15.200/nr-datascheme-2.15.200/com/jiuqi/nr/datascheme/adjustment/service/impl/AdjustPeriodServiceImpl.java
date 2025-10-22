/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.HashCacheValue
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.nr.datascheme.api.AdjustPeriod
 *  com.jiuqi.nr.datascheme.api.event.RefreshCache
 *  com.jiuqi.nr.datascheme.api.event.RefreshScheme
 *  com.jiuqi.nr.datascheme.api.service.IAdjustPeriodService
 */
package com.jiuqi.nr.datascheme.adjustment.service.impl;

import com.jiuqi.np.cache.HashCacheValue;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.nr.datascheme.adjustment.dao.AdjustPeriodDao;
import com.jiuqi.nr.datascheme.adjustment.dao.AdjustPeriodDaoImpl;
import com.jiuqi.nr.datascheme.adjustment.entity.AdjustPeriodDO;
import com.jiuqi.nr.datascheme.adjustment.util.AdjustUtils;
import com.jiuqi.nr.datascheme.api.AdjustPeriod;
import com.jiuqi.nr.datascheme.api.event.RefreshCache;
import com.jiuqi.nr.datascheme.api.event.RefreshScheme;
import com.jiuqi.nr.datascheme.api.service.IAdjustPeriodService;
import com.jiuqi.nr.datascheme.internal.service.SchemeRefreshListener;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

@Service
public class AdjustPeriodServiceImpl
implements IAdjustPeriodService,
SchemeRefreshListener {
    private final AdjustPeriodDao<AdjustPeriodDO> adjustPeriodDao;
    private final NedisCache cache;
    private static final Logger logger = LoggerFactory.getLogger(AdjustPeriodServiceImpl.class);

    public AdjustPeriodServiceImpl(AdjustPeriodDaoImpl adjustPeriodDaoImpl, NedisCacheProvider cacheProvider) {
        if (cacheProvider == null) {
            throw new IllegalArgumentException("'cacheProvider' must not be null.");
        }
        this.adjustPeriodDao = adjustPeriodDaoImpl;
        this.cache = cacheProvider.getCacheManager("nr:scheme:adjust").getCache("nr:scheme:adjust");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<AdjustPeriod> queryAdjustPeriods(String dataSchemeKey) {
        Map objectObjectMap;
        Assert.notNull((Object)dataSchemeKey, "dataSchemeKey must not be null");
        HashCacheValue cacheValue = this.cache.hGetAllIfExists(dataSchemeKey);
        if (cacheValue.isPresent() && Objects.nonNull(objectObjectMap = (Map)cacheValue.get())) {
            return this.unmodifiableList(objectObjectMap);
        }
        AdjustPeriodServiceImpl adjustPeriodServiceImpl = this;
        synchronized (adjustPeriodServiceImpl) {
            Map objectObjectMap2;
            HashCacheValue cacheValueTemp = this.cache.hGetAllIfExists(dataSchemeKey);
            if (cacheValueTemp.isPresent() && Objects.nonNull(objectObjectMap2 = (Map)cacheValueTemp.get())) {
                return this.unmodifiableList(objectObjectMap2);
            }
            List<AdjustPeriodDO> query = this.adjustPeriodDao.list(dataSchemeKey);
            Map<String, List<AdjustPeriodDO>> listMap = query.stream().collect(Collectors.groupingBy(AdjustPeriod::getPeriod));
            listMap.forEach((key, value) -> {
                AdjustPeriodDO notAdjustBean = AdjustUtils.createNotAdjust((AdjustPeriod)value.get(0));
                if (notAdjustBean != null) {
                    value.add(notAdjustBean);
                    query.add(notAdjustBean);
                }
            });
            this.cache.hMSet(dataSchemeKey, listMap);
            return Collections.unmodifiableList(query);
        }
    }

    private List<AdjustPeriod> unmodifiableList(Map<Object, Object> objectObjectMap) {
        List collect = objectObjectMap.values().stream().map(List.class::cast).flatMap(Collection::stream).collect(Collectors.toList());
        return Collections.unmodifiableList(collect);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<AdjustPeriod> queryAdjustPeriods(String dataSchemeKey, String period) {
        Assert.notNull((Object)dataSchemeKey, "dataSchemeKey must not be null");
        Assert.notNull((Object)period, "period must not be null");
        Cache.ValueWrapper valueWrapper = this.cache.hGet(dataSchemeKey, (Object)period);
        if (Objects.nonNull(valueWrapper)) {
            return Collections.unmodifiableList((List)valueWrapper.get());
        }
        AdjustPeriodServiceImpl adjustPeriodServiceImpl = this;
        synchronized (adjustPeriodServiceImpl) {
            Cache.ValueWrapper valueWrapperTemp = this.cache.hGet(dataSchemeKey, (Object)period);
            if (Objects.nonNull(valueWrapperTemp)) {
                return Collections.unmodifiableList((List)valueWrapperTemp.get());
            }
            List<AdjustPeriodDO> periodDOS = this.adjustPeriodDao.list(dataSchemeKey, period);
            periodDOS.add(AdjustUtils.createNotAdjust(dataSchemeKey, period));
            this.cache.hSet(dataSchemeKey, (Object)period, periodDOS);
            List collect = periodDOS.stream().map(AdjustPeriod.class::cast).collect(Collectors.toList());
            return Collections.unmodifiableList(collect);
        }
    }

    public AdjustPeriod queryAdjustPeriods(String dataSchemeKey, String period, String code) {
        Assert.notNull((Object)dataSchemeKey, "dataSchemeKey must not be null");
        Assert.notNull((Object)period, "period must not be null");
        List<AdjustPeriod> periods = this.queryAdjustPeriods(dataSchemeKey, period);
        return periods.stream().filter(p -> p.getCode().equals(code)).findFirst().orElse(null);
    }

    @Override
    public void onClearCache() {
        this.cache.clear();
        logger.info("\u6e05\u9664\u7f13\u5b58: \u6e05\u7406\u5168\u90e8\u8c03\u6574\u671f\u7f13\u5b58");
    }

    @Override
    public void onClearCache(RefreshCache refreshTable) {
        Map map = refreshTable.getRefreshTable();
        if (!CollectionUtils.isEmpty(map)) {
            for (RefreshScheme refreshScheme : map.keySet()) {
                this.cache.evict(refreshScheme.getKey());
                logger.info("\u6e05\u9664\u7f13\u5b58: \u6e05\u7406\u8c03\u6574\u671f\u7f13\u5b58{}", (Object)refreshScheme.getKey());
            }
        }
    }
}

