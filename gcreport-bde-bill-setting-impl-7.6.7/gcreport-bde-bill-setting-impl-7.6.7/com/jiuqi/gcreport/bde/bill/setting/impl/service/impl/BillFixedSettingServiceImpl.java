/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bde.common.dto.FixedAdaptSettingVO
 *  com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFixedSettingDTO
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillSettingCondiDTO
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.FieldAdaptSettingDTO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchSettingDao
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchSettingDesDao
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingDesEO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingEO
 *  com.jiuqi.gcreport.billextract.client.dto.BillFetchSchemeDTO
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.va.domain.common.JSONUtil
 */
package com.jiuqi.gcreport.bde.bill.setting.impl.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bde.common.dto.FixedAdaptSettingVO;
import com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFixedSettingDTO;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillSettingCondiDTO;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.FieldAdaptSettingDTO;
import com.jiuqi.gcreport.bde.bill.setting.impl.intf.IBillExtractSchemeUnifiedHandler;
import com.jiuqi.gcreport.bde.bill.setting.impl.service.BillFixedSettingService;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchSettingDao;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchSettingDesDao;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingDesEO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingEO;
import com.jiuqi.gcreport.billextract.client.dto.BillFetchSchemeDTO;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.va.domain.common.JSONUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillFixedSettingServiceImpl
implements BillFixedSettingService,
IBillExtractSchemeUnifiedHandler {
    @Autowired
    private FetchSettingDao fetchSettingDao;
    @Autowired
    private FetchSettingDesDao fetchSettingDesDao;
    private final NedisCache fixedSettingCache;

    public BillFixedSettingServiceImpl(@Autowired NedisCacheProvider cacheProvider) {
        NedisCacheManager cacheManager = cacheProvider.getCacheManager("BDE_BILL_EXTRACT_FIED_SETTING_MANAGE");
        this.fixedSettingCache = cacheManager.getCache("BDE_BILL_EXTRACT_FIED_SETTING_SCHEME");
    }

    @Override
    public List<BillFixedSettingDTO> getFixedSetting(BillSettingCondiDTO billSettingCondi) {
        List<FetchSettingEO> fetchSettings = this.getFixedSettingByCond(billSettingCondi);
        if (CollectionUtils.isEmpty(fetchSettings)) {
            return CollectionUtils.newArrayList();
        }
        LinkedList<BillFixedSettingDTO> fixedFieldDefineSettingVOs = new LinkedList<BillFixedSettingDTO>();
        for (FetchSettingEO fetchSetting : fetchSettings) {
            fixedFieldDefineSettingVOs.add(BillFixedSettingServiceImpl.readFixedSettingFromEo(fetchSetting));
        }
        return fixedFieldDefineSettingVOs;
    }

    @Override
    public List<FetchSettingEO> getFixedSettingEOS(BillSettingCondiDTO billSettingCondi) {
        List<FetchSettingEO> fetchSettings = this.getFixedSettingByCond(billSettingCondi);
        return fetchSettings;
    }

    public List<FetchSettingEO> getFixedSettingByCond(BillSettingCondiDTO billSettingCondi) {
        Assert.isNotNull((Object)billSettingCondi);
        if (StringUtils.isEmpty((String)billSettingCondi.getSchemeId())) {
            return Collections.emptyList();
        }
        List fetchSettingEOS = (List)this.fixedSettingCache.get(billSettingCondi.getSchemeId(), () -> this.fetchSettingDao.listFetchSettingByFetchSchemeId(billSettingCondi.getSchemeId()));
        if (CollectionUtils.isEmpty((Collection)fetchSettingEOS)) {
            return fetchSettingEOS;
        }
        return fetchSettingEOS.stream().filter(fetchSetting -> this.filterFetchSettingByCond(billSettingCondi, (FetchSettingEO)fetchSetting)).collect(Collectors.toList());
    }

    private boolean filterFetchSettingByCond(BillSettingCondiDTO billSettingCondi, FetchSettingEO fetchSetting) {
        if (!StringUtils.isEmpty((String)billSettingCondi.getBillType()) && !billSettingCondi.getBillType().equals(fetchSetting.getFormSchemeId())) {
            return false;
        }
        if (!StringUtils.isEmpty((String)billSettingCondi.getBillTable()) && !billSettingCondi.getBillTable().equals(fetchSetting.getFormId())) {
            return false;
        }
        if (!StringUtils.isEmpty((String)billSettingCondi.getBillTable()) && !billSettingCondi.getBillTable().equals(fetchSetting.getRegionId())) {
            return false;
        }
        return StringUtils.isEmpty((String)billSettingCondi.getDataField()) || billSettingCondi.getDataField().equals(fetchSetting.getDataLinkId());
    }

    public static BillFixedSettingDTO readFixedSettingFromEo(FetchSettingEO fetchSettingEO) {
        if (Objects.isNull(fetchSettingEO)) {
            return null;
        }
        String adaptSettingListStr = fetchSettingEO.getFixedSettingData();
        List fixedAdaptSettingVOS = (List)JsonUtils.readValue((String)adaptSettingListStr, (TypeReference)new TypeReference<List<FixedAdaptSettingVO>>(){});
        LinkedList<FieldAdaptSettingDTO> fixedSettingData = new LinkedList<FieldAdaptSettingDTO>();
        for (FixedAdaptSettingVO adaptSettingVO : fixedAdaptSettingVOS) {
            FieldAdaptSettingDTO adaptSettingDTO = new FieldAdaptSettingDTO();
            BeanUtils.copyProperties(adaptSettingVO, adaptSettingDTO);
            Map bizModelFormula = adaptSettingVO.getBizModelFormula();
            HashMap bizModelFormulaDTO = new HashMap();
            for (String fetchSourceId : bizModelFormula.keySet()) {
                LinkedList<Map> fetchSourceRowSettingDTOList = new LinkedList<Map>();
                for (FixedFetchSourceRowSettingVO fetchSourceRowSettingVO : (List)bizModelFormula.get(fetchSourceId)) {
                    String fetchSourceRowSettingStr = JSONUtil.toJSONString((Object)fetchSourceRowSettingVO);
                    Map fetchSourceRowSettingMap = JSONUtil.parseMap((String)fetchSourceRowSettingStr);
                    String dimensionSetting = fetchSourceRowSettingVO.getDimensionSetting();
                    if (!StringUtils.isEmpty((String)dimensionSetting)) {
                        List dimListMap = JSONUtil.parseMapArray((String)dimensionSetting, String.class, String.class);
                        for (Map dimMap : dimListMap) {
                            String dimCode = (String)dimMap.get("dimCode");
                            String dimValue = (String)dimMap.get("dimValue");
                            String dimRule = (String)dimMap.get("dimRule");
                            fetchSourceRowSettingMap.put(dimCode, dimValue);
                            fetchSourceRowSettingMap.put(dimCode + "MatchRule", dimRule);
                        }
                    }
                    fetchSourceRowSettingDTOList.add(fetchSourceRowSettingMap);
                }
                bizModelFormulaDTO.put(fetchSourceId, fetchSourceRowSettingDTOList);
            }
            adaptSettingDTO.setBizModelFormula(bizModelFormulaDTO);
            fixedSettingData.add(adaptSettingDTO);
        }
        BillFixedSettingDTO settingDto = new BillFixedSettingDTO();
        settingDto.setBillType(fetchSettingEO.getFormSchemeId());
        settingDto.setBillTable(fetchSettingEO.getFormId());
        settingDto.setDataField(fetchSettingEO.getDataLinkId());
        settingDto.setRegionType(fetchSettingEO.getRegionType());
        settingDto.setFieldAdaptSettings(fixedSettingData);
        return settingDto;
    }

    @Override
    public Set<String> listTableName(BillSettingCondiDTO billSettingCondi) {
        List<FetchSettingEO> fetchSettings = this.getFixedSettingByCond(billSettingCondi);
        if (CollectionUtils.isEmpty(fetchSettings)) {
            return CollectionUtils.newHashSet();
        }
        HashSet tableSet = CollectionUtils.newHashSet();
        for (FetchSettingEO setting : fetchSettings) {
            tableSet.add(setting.getRegionId());
        }
        return tableSet;
    }

    @Override
    public int delete(BillFetchSchemeDTO schemeDto) {
        Assert.isNotNull((Object)schemeDto);
        Assert.isNotEmpty((String)schemeDto.getId());
        this.fetchSettingDao.deleteByFetchSchemeId(schemeDto.getId());
        this.fixedSettingCache.evict(schemeDto.getId());
        return 0;
    }

    @Override
    public int syncCache(BillFetchSchemeDTO schemeDto) {
        Assert.isNotNull((Object)schemeDto);
        Assert.isNotEmpty((String)schemeDto.getId());
        this.fixedSettingCache.evict(schemeDto.getId());
        return 0;
    }

    @Override
    public int publish(BillFetchSchemeDTO schemeDto) {
        Assert.isNotNull((Object)schemeDto);
        Assert.isNotEmpty((String)schemeDto.getId());
        FetchSettingCond condi = new FetchSettingCond();
        condi.setFormSchemeId(schemeDto.getBillType());
        condi.setFetchSchemeId(schemeDto.getId());
        this.fetchSettingDao.deleteByFetchSchemeId(schemeDto.getId());
        List desSettingList = this.fetchSettingDesDao.listFetchSettingDesByFetchSchemeId(condi);
        if (CollectionUtils.isEmpty((Collection)desSettingList)) {
            return 0;
        }
        ArrayList<FetchSettingEO> fetchSettingData = new ArrayList<FetchSettingEO>();
        for (FetchSettingDesEO fetchSettingDes : desSettingList) {
            FetchSettingEO fetchSetting = new FetchSettingEO();
            BeanUtils.copyProperties(fetchSettingDes, fetchSetting);
            fetchSettingData.add(fetchSetting);
        }
        this.fetchSettingDao.addBatch(fetchSettingData);
        this.syncCache(schemeDto);
        return fetchSettingData.size();
    }
}

