/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.FloatRegionConfigVO
 *  com.jiuqi.bde.common.dto.QueryConfigInfo
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchFloatSettingDao
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchFloatSettingEO
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.va.domain.common.JSONUtil
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.service.impl;

import com.jiuqi.bde.common.dto.FloatRegionConfigVO;
import com.jiuqi.bde.common.dto.QueryConfigInfo;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchFloatSettingDao;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchFloatSettingEO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchFloatSettingService;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.va.domain.common.JSONUtil;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FetchFloatSettingServiceImpl
implements FetchFloatSettingService {
    @Autowired
    private FetchFloatSettingDao fetchFloatSettingDao;
    private final NedisCache bdeFetchFloatSettingCache;

    public FetchFloatSettingServiceImpl(@Autowired NedisCacheProvider cacheProvider) {
        NedisCacheManager cacheManager = cacheProvider.getCacheManager("BDE_FETCH_FLOAT_SETTING_MANAGE");
        this.bdeFetchFloatSettingCache = cacheManager.getCache("BDE_FETCH_FLOAT_SETTING");
    }

    @Override
    public List<FloatRegionConfigVO> listFetchFloatSettingByFormId(FetchSettingCond fetchSettingCond) {
        List<FetchFloatSettingEO> fetchFloatSettings = this.getFetchFloatSettingListByCond(fetchSettingCond);
        if (CollectionUtils.isEmpty(fetchFloatSettings)) {
            return Collections.emptyList();
        }
        return fetchFloatSettings.stream().map(this::convertEnableFloatRegionConfigVO).collect(Collectors.toList());
    }

    @Override
    public FloatRegionConfigVO listFetchFloatSettingByRegionId(FetchSettingCond fetchSettingCond) {
        Assert.isNotNull((Object)fetchSettingCond.getFetchSchemeId(), (String)"\u6839\u636e\u533a\u57df\u67e5\u8be2\u6d6e\u52a8\u53d6\u6570\u8bbe\u7f6e\uff0c\u53d6\u6570\u65b9\u6848ID\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotNull((Object)fetchSettingCond.getFormSchemeId(), (String)"\u6839\u636e\u533a\u57df\u67e5\u8be2\u6d6e\u52a8\u53d6\u6570\u8bbe\u7f6e\uff0c\u62a5\u8868\u65b9\u6848ID\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotNull((Object)fetchSettingCond.getFormId(), (String)"\u6839\u636e\u533a\u57df\u67e5\u8be2\u6d6e\u52a8\u53d6\u6570\u8bbe\u7f6e\uff0c\u62a5\u8868ID\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotNull((Object)fetchSettingCond.getRegionId(), (String)"\u6839\u636e\u533a\u57df\u67e5\u8be2\u6d6e\u52a8\u53d6\u6570\u8bbe\u7f6e\uff0c\u533a\u57dfID\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        List<FetchFloatSettingEO> fetchFloatSettings = this.getFetchFloatSettingListByCond(fetchSettingCond);
        if (CollectionUtils.isEmpty(fetchFloatSettings)) {
            return new FloatRegionConfigVO();
        }
        return (FloatRegionConfigVO)fetchFloatSettings.stream().map(this::convertEnableFloatRegionConfigVO).collect(Collectors.toList()).get(0);
    }

    @Override
    public FloatRegionConfigVO getFetchFloatSetting(FetchSettingCond fetchSettingCond) {
        List<FetchFloatSettingEO> fetchFloatSettingList = this.getFetchFloatSettingListByCond(fetchSettingCond);
        if (fetchFloatSettingList != null && fetchFloatSettingList.size() == 1) {
            return this.convertEnableFloatRegionConfigVO(fetchFloatSettingList.get(0));
        }
        return null;
    }

    private FloatRegionConfigVO convertEnableFloatRegionConfigVO(FetchFloatSettingEO fetchFloatSettingData) {
        FloatRegionConfigVO fetchFloatSetting = new FloatRegionConfigVO();
        BeanUtils.copyProperties(fetchFloatSettingData, fetchFloatSetting);
        if (!StringUtils.isEmpty((String)fetchFloatSettingData.getQueryConfigInfo())) {
            QueryConfigInfo queryConfigInfo = (QueryConfigInfo)JSONUtil.parseObject((String)fetchFloatSettingData.getQueryConfigInfo(), QueryConfigInfo.class);
            if (queryConfigInfo != null && queryConfigInfo.getZbMapping() != null) {
                queryConfigInfo.getZbMapping().removeIf(floatZbMappingVO -> !Objects.isNull(floatZbMappingVO.getStopFlag()) && floatZbMappingVO.getStopFlag() == 1);
            }
            fetchFloatSetting.setQueryConfigInfo(queryConfigInfo);
        }
        return fetchFloatSetting;
    }

    @Override
    public List<String> listFormKeyBySchemeKey(String fetchSchemeKey, String formSchemeKey) {
        FetchSettingCond fetchSettingCond = new FetchSettingCond();
        fetchSettingCond.setFetchSchemeId(fetchSchemeKey);
        fetchSettingCond.setFormSchemeId(formSchemeKey);
        List<FetchFloatSettingEO> fetchFloatSettingList = this.getFetchFloatSettingListByCond(fetchSettingCond);
        if (CollectionUtils.isEmpty(fetchFloatSettingList)) {
            return Collections.emptyList();
        }
        return fetchFloatSettingList.stream().map(FetchFloatSettingEO::getFormId).collect(Collectors.toList());
    }

    @Override
    public void fetchFloatSettingCacheClear() {
        this.bdeFetchFloatSettingCache.clear();
    }

    @Override
    public void fetchFloatSettingCacheEvit(String fetchSchemeId) {
        this.bdeFetchFloatSettingCache.evict(fetchSchemeId);
    }

    @Override
    public void addBatch(List<FetchFloatSettingEO> fetchFloatSettingEOS) {
        this.fetchFloatSettingDao.addBatch(fetchFloatSettingEOS);
        this.fetchFloatSettingCacheEvit(fetchFloatSettingEOS.get(0).getFetchSchemeId());
    }

    @Override
    public void deleteByFetchSchemeId(String fetchSchemeId) {
        this.fetchFloatSettingDao.deleteByFetchSchemeId(fetchSchemeId);
        this.fetchFloatSettingCacheEvit(fetchSchemeId);
    }

    @Override
    public void deleteFloatFetchSettingByFetchSettingCond(FetchSettingCond fetchSettingCond) {
        this.fetchFloatSettingDao.deleteFloatFetchSettingByFetchSettingCond(fetchSettingCond);
        this.fetchFloatSettingCacheEvit(fetchSettingCond.getFetchSchemeId());
    }

    @Override
    public List<FetchFloatSettingEO> getFetchFloatSettingListByCond(FetchSettingCond fetchSettingCond) {
        Assert.isNotNull((Object)fetchSettingCond);
        if (StringUtils.isEmpty((String)fetchSettingCond.getFetchSchemeId())) {
            return Collections.emptyList();
        }
        List fetchFloatSettingEOS = (List)this.bdeFetchFloatSettingCache.get(fetchSettingCond.getFetchSchemeId(), () -> this.fetchFloatSettingDao.listFetchSettingByFetchSchemeId(fetchSettingCond.getFetchSchemeId()));
        if (CollectionUtils.isEmpty((Collection)fetchFloatSettingEOS)) {
            return Collections.emptyList();
        }
        return fetchFloatSettingEOS.stream().filter(fetchFloatSetting -> this.filterFetchFloatSetting(fetchSettingCond, (FetchFloatSettingEO)fetchFloatSetting)).collect(Collectors.toList());
    }

    private boolean filterFetchFloatSetting(FetchSettingCond fetchSettingCond, FetchFloatSettingEO fetchFloatSetting) {
        if (StringUtils.isNotEmpty((String)fetchSettingCond.getFormSchemeId()) && !fetchSettingCond.getFormSchemeId().equals(fetchFloatSetting.getFormSchemeId())) {
            return false;
        }
        if (StringUtils.isNotEmpty((String)fetchSettingCond.getFormId()) && !fetchSettingCond.getFormId().equals(fetchFloatSetting.getFormId())) {
            return false;
        }
        return !StringUtils.isNotEmpty((String)fetchSettingCond.getRegionId()) || fetchSettingCond.getRegionId().equals(fetchFloatSetting.getRegionId());
    }
}

