/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.exception.BdeRuntimeException
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.AdjustPeriodFetchDTO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.AdjustPeriodSettingVO
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.efdc.internal.pojo.QueryObjectImpl
 *  com.jiuqi.nr.efdc.service.IEFDCConfigService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.service.impl;

import com.jiuqi.bde.common.exception.BdeRuntimeException;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.AdjustPeriodFetchDTO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.AdjustPeriodSettingVO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.AdjustPeriodSettingDao;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.AdjustPeriodSettingEO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.AdjustPeriodSettingService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.utils.FetchSchemeLogHelperUtil;
import com.jiuqi.gcreport.bde.fetchsetting.impl.utils.FetchTaskUtil;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.efdc.internal.pojo.QueryObjectImpl;
import com.jiuqi.nr.efdc.service.IEFDCConfigService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdjustPeriodSettingServiceImpl
implements AdjustPeriodSettingService {
    @Autowired
    private AdjustPeriodSettingDao adjustPeriodSettingDao;
    @Autowired
    private IEFDCConfigService iefdcConfigService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IEntityMetaService entityMetaService;
    private final NedisCache cache;

    public AdjustPeriodSettingServiceImpl(@Autowired NedisCacheProvider cacheProvider) {
        NedisCacheManager cacheManager = cacheProvider.getCacheManager("BDE_ADJUST_FETCH_SETTING_MANAGE");
        this.cache = cacheManager.getCache("BDE_ADJUST_FETCH_SETTING");
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void save(List<AdjustPeriodSettingVO> adjustPeriodSettingVOS, String fetchSchemeId) {
        this.adjustPeriodSettingDao.deleteByFetchSchemeId(fetchSchemeId);
        List adjustPeriods = adjustPeriodSettingVOS.stream().map(AdjustPeriodSettingVO::getAdjustPeriod).distinct().collect(Collectors.toList());
        if (adjustPeriods.size() != adjustPeriodSettingVOS.size()) {
            throw new BusinessRuntimeException("\u8c03\u6574\u671f\u4e0d\u5141\u8bb8\u91cd\u590d");
        }
        this.adjustPeriodSettingDao.addBatch(adjustPeriodSettingVOS.stream().map(item -> this.convertAdjustPeriodSettingVOToEO((AdjustPeriodSettingVO)item, fetchSchemeId)).collect(Collectors.toList()));
        this.cleanCache();
    }

    @Override
    public List<AdjustPeriodSettingVO> listAdjustPeriodSettingByFetchSchemeId(String fetchSchemeId) {
        return (List)this.cache.get(fetchSchemeId, () -> this.adjustPeriodSettingDao.listAdjustPeriodSettingByFetchSchemeId(fetchSchemeId).stream().map(this::convertAdjustPeriodSettingEoToVo).collect(Collectors.toList()));
    }

    @Override
    public Boolean isAdjustFetch(AdjustPeriodFetchDTO adjustPeriodFetchDTO) {
        FormSchemeDefine formSchemeDefine = null;
        try {
            formSchemeDefine = this.runTimeViewController.getFormScheme(adjustPeriodFetchDTO.getFormSchemeKey());
        }
        catch (Exception e) {
            throw new BdeRuntimeException("\u62a5\u8868\u65b9\u6848\u672a\u627e\u5230:" + adjustPeriodFetchDTO.getFormSchemeKey(), (Throwable)e);
        }
        QueryObjectImpl queryObject = new QueryObjectImpl(formSchemeDefine.getTaskKey(), adjustPeriodFetchDTO.getFormSchemeKey(), (String)adjustPeriodFetchDTO.getDimensionSet().get("MD_ORG"));
        FormulaSchemeDefine formulaSchemeDefine = this.iefdcConfigService.getSoluctionByDimensions(queryObject, adjustPeriodFetchDTO.getDimensionSet(), FetchTaskUtil.getEntityIdByTaskAndCtx(formSchemeDefine.getTaskKey()));
        if (formulaSchemeDefine != null) {
            return this.isAdjustFetch(formulaSchemeDefine.getKey(), adjustPeriodFetchDTO.getAdjustPeriod());
        }
        return false;
    }

    private Boolean isAdjustFetch(String fetchSchemeId, String adjustFetch) {
        if (StringUtils.isEmpty((String)adjustFetch) || StringUtils.isEmpty((String)fetchSchemeId)) {
            return false;
        }
        List<AdjustPeriodSettingVO> adjustPeriodSettingVOS = this.listAdjustPeriodSettingByFetchSchemeId(fetchSchemeId);
        if (!CollectionUtils.isEmpty(adjustPeriodSettingVOS)) {
            return adjustPeriodSettingVOS.stream().anyMatch(item -> adjustFetch.equals(item.getAdjustPeriod()));
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void deleteByFetchSchemeId(String fetchSchemeId) {
        this.adjustPeriodSettingDao.deleteByFetchSchemeId(fetchSchemeId);
        this.cleanCache();
    }

    @Override
    public void cleanCache() {
        this.cache.clear();
    }

    @Override
    public void copyAdjustPeriodSettingByFetchSchemeId(String oldFetchSchemeId, String newFetchSchemeId) {
        List<AdjustPeriodSettingEO> adjustPeriodSettingEOS = this.adjustPeriodSettingDao.listAdjustPeriodSettingByFetchSchemeId(oldFetchSchemeId);
        adjustPeriodSettingEOS.forEach(item -> {
            item.setId(UUIDUtils.newHalfGUIDStr());
            item.setFetchSchemeId(newFetchSchemeId);
        });
        FetchSchemeLogHelperUtil.saveAdjustPeriodSettingLogInfo(adjustPeriodSettingEOS.stream().map(this::convertAdjustPeriodSettingEoToVo).collect(Collectors.toList()), newFetchSchemeId);
        this.adjustPeriodSettingDao.addBatch(adjustPeriodSettingEOS);
        this.cleanCache();
    }

    private AdjustPeriodSettingEO convertAdjustPeriodSettingVOToEO(AdjustPeriodSettingVO adjustPeriodSettingVo, String fetchSchemeId) {
        AdjustPeriodSettingEO adjustPeriodSettingEO = new AdjustPeriodSettingEO();
        BeanUtils.copyProperties(adjustPeriodSettingVo, adjustPeriodSettingEO);
        adjustPeriodSettingEO.setFetchSchemeId(fetchSchemeId);
        return adjustPeriodSettingEO;
    }

    private AdjustPeriodSettingVO convertAdjustPeriodSettingEoToVo(AdjustPeriodSettingEO adjustPeriodSettingEO) {
        AdjustPeriodSettingVO adjustPeriodSettingVo = new AdjustPeriodSettingVO();
        BeanUtils.copyProperties(adjustPeriodSettingEO, adjustPeriodSettingVo);
        return adjustPeriodSettingVo;
    }
}

