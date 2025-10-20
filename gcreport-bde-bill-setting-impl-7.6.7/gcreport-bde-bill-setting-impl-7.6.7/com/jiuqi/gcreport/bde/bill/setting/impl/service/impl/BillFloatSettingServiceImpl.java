/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.QueryConfigInfo
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.dc.base.common.utils.BeanConvertUtil
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFloatRegionConfigDTO
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillSettingCondiDTO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchFloatSettingDao
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchFloatSettingDesDao
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchFloatSettingDesEO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchFloatSettingEO
 *  com.jiuqi.gcreport.billextract.client.dto.BillFetchSchemeDTO
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.va.domain.common.JSONUtil
 */
package com.jiuqi.gcreport.bde.bill.setting.impl.service.impl;

import com.jiuqi.bde.common.dto.QueryConfigInfo;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.dc.base.common.utils.BeanConvertUtil;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFloatRegionConfigDTO;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillSettingCondiDTO;
import com.jiuqi.gcreport.bde.bill.setting.impl.intf.IBillExtractSchemeUnifiedHandler;
import com.jiuqi.gcreport.bde.bill.setting.impl.service.BillFloatSettingService;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchFloatSettingDao;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchFloatSettingDesDao;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchFloatSettingDesEO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchFloatSettingEO;
import com.jiuqi.gcreport.billextract.client.dto.BillFetchSchemeDTO;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.va.domain.common.JSONUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillFloatSettingServiceImpl
implements BillFloatSettingService,
IBillExtractSchemeUnifiedHandler {
    @Autowired
    private FetchFloatSettingDao fetchFloatSettingDao;
    @Autowired
    private FetchFloatSettingDesDao fetchFloatSettingDesDao;
    private final NedisCache floatSettingCache;

    public BillFloatSettingServiceImpl(@Autowired NedisCacheProvider cacheProvider) {
        NedisCacheManager cacheManager = cacheProvider.getCacheManager("BDE_BILL_EXTRACT_FLOAT_SETTING_MANAGE");
        this.floatSettingCache = cacheManager.getCache("BDE_BILL_EXTRACT_FLOAT_SETTING_SCHEME");
    }

    @Override
    public BillFloatRegionConfigDTO getFloatSetting(BillSettingCondiDTO billSettingCondi) {
        List<FetchFloatSettingEO> fetchFloatSettingList = this.getFloatSettingByCond(billSettingCondi);
        if (fetchFloatSettingList != null && fetchFloatSettingList.size() == 1) {
            return this.convertFloatRegionConfigVO(fetchFloatSettingList.get(0));
        }
        return null;
    }

    @Override
    public Set<String> listTableName(BillSettingCondiDTO billSettingCondi) {
        List<FetchFloatSettingEO> fetchFloatSettingList = this.getFloatSettingByCond(billSettingCondi);
        HashSet tableSet = CollectionUtils.newHashSet();
        for (FetchFloatSettingEO setting : fetchFloatSettingList) {
            tableSet.add(setting.getRegionId());
        }
        return tableSet;
    }

    private BillFloatRegionConfigDTO convertFloatRegionConfigVO(FetchFloatSettingEO fetchFloatSettingData) {
        BillFloatRegionConfigDTO fetchFloatSetting = (BillFloatRegionConfigDTO)BeanConvertUtil.convert((Object)fetchFloatSettingData, BillFloatRegionConfigDTO.class, (String[])new String[0]);
        fetchFloatSetting.setBillType(fetchFloatSettingData.getFormSchemeId());
        fetchFloatSetting.setBillTable(fetchFloatSettingData.getFormId());
        fetchFloatSetting.setQueryType(fetchFloatSettingData.getQueryType());
        if (!StringUtils.isEmpty((String)fetchFloatSettingData.getQueryConfigInfo())) {
            fetchFloatSetting.setQueryConfigInfo((QueryConfigInfo)JSONUtil.parseObject((String)fetchFloatSettingData.getQueryConfigInfo(), QueryConfigInfo.class));
        }
        return fetchFloatSetting;
    }

    public List<FetchFloatSettingEO> getFloatSettingByCond(BillSettingCondiDTO billSettingCondi) {
        Assert.isNotNull((Object)billSettingCondi);
        if (StringUtils.isEmpty((String)billSettingCondi.getSchemeId())) {
            return Collections.emptyList();
        }
        List fetchFloatSettingEOS = (List)this.floatSettingCache.get(billSettingCondi.getSchemeId(), () -> this.fetchFloatSettingDao.listFetchSettingByFetchSchemeId(billSettingCondi.getSchemeId()));
        if (CollectionUtils.isEmpty((Collection)fetchFloatSettingEOS)) {
            return Collections.emptyList();
        }
        return fetchFloatSettingEOS.stream().filter(fetchFloatSetting -> this.filterFetchFloatSetting(billSettingCondi, (FetchFloatSettingEO)fetchFloatSetting)).collect(Collectors.toList());
    }

    private boolean filterFetchFloatSetting(BillSettingCondiDTO fetchSettingCond, FetchFloatSettingEO fetchFloatSetting) {
        if (StringUtils.isNotEmpty((String)fetchSettingCond.getBillType()) && !fetchSettingCond.getBillType().equals(fetchFloatSetting.getFormSchemeId())) {
            return false;
        }
        return !StringUtils.isNotEmpty((String)fetchSettingCond.getBillTable()) || fetchSettingCond.getBillTable().equals(fetchFloatSetting.getFormId());
    }

    @Override
    public int delete(BillFetchSchemeDTO schemeDto) {
        Assert.isNotNull((Object)schemeDto);
        Assert.isNotEmpty((String)schemeDto.getId());
        this.fetchFloatSettingDao.deleteByFetchSchemeId(schemeDto.getId());
        this.floatSettingCache.evict(schemeDto.getId());
        return 0;
    }

    @Override
    public int syncCache(BillFetchSchemeDTO schemeDto) {
        this.floatSettingCache.evict(schemeDto.getId());
        return 1;
    }

    @Override
    public int publish(BillFetchSchemeDTO schemeDto) {
        Assert.isNotNull((Object)schemeDto);
        Assert.isNotEmpty((String)schemeDto.getId());
        this.fetchFloatSettingDao.deleteByFetchSchemeId(schemeDto.getId());
        FetchSettingCond condi = new FetchSettingCond();
        condi.setFormSchemeId(schemeDto.getBillType());
        condi.setFetchSchemeId(schemeDto.getId());
        List desFloatSettingList = this.fetchFloatSettingDesDao.listFetchFloatSettingDesByFetchSchemeId(condi);
        if (CollectionUtils.isEmpty((Collection)desFloatSettingList)) {
            return 0;
        }
        ArrayList<FetchFloatSettingEO> fetchFloatSettingData = new ArrayList<FetchFloatSettingEO>();
        for (FetchFloatSettingDesEO fetchFloatSettingDes : desFloatSettingList) {
            FetchFloatSettingEO fetchFloatSetting = new FetchFloatSettingEO();
            BeanUtils.copyProperties(fetchFloatSettingDes, fetchFloatSetting);
            fetchFloatSettingData.add(fetchFloatSetting);
        }
        this.fetchFloatSettingDao.addBatch(fetchFloatSettingData);
        this.syncCache(schemeDto);
        return fetchFloatSettingData.size();
    }
}

