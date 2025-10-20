/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.exception.BdeRuntimeException
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.bde.fetchsetting.client.enums.BizTypeEnum
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSchemeVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchFloatSettingDesDao
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchSchemeDao
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchSettingDao
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchSettingDesDao
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchFloatSettingEO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSchemeEO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingDesEO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingEO
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.va.domain.common.JSONUtil
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.service.impl;

import com.jiuqi.bde.common.exception.BdeRuntimeException;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.bde.fetchsetting.client.enums.BizTypeEnum;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSchemeVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.DimensionSettingDao;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.DimensionValueSettingDao;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchFloatSettingDesDao;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchSchemeDao;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchSettingDao;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchSettingDesDao;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.DimensionSettingEO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.DimensionValueSettingEO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchFloatSettingEO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSchemeEO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingDesEO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingEO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.utils.FetchSettingNrUtil;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.AdjustPeriodSettingService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchFloatSettingService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSchemeNrService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSettingService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.utils.FetchSchemeLogHelperUtil;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.va.domain.common.JSONUtil;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FetchSchemeNrServiceImpl
implements FetchSchemeNrService {
    @Autowired
    private FetchSchemeDao fetchSchemeDao;
    @Autowired
    private FetchSettingDao fetchSettingDao;
    @Autowired
    private FetchSettingDesDao fetchSettingDesDao;
    @Autowired
    private FetchFloatSettingService fetchFloatSettingService;
    @Autowired
    private FetchFloatSettingDesDao fetchFloatSettingDesDao;
    @Autowired
    private DimensionSettingDao dimensionSettingDao;
    @Autowired
    private DimensionValueSettingDao dimensionValueSettingDao;
    @Autowired
    private FetchSettingService fetchSettingService;
    private final NedisCache bdeFetchSchemeCache;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private SystemIdentityService systemIdentityService;
    @Autowired
    private DefinitionAuthorityProvider authorityProvider;
    @Autowired
    private AdjustPeriodSettingService adjustPeriodSettingService;
    private static final int FETCH_SCHEME_NAME_LENGTH = 36;
    @Autowired
    IRunTimeViewController iRunTimeViewController;

    public FetchSchemeNrServiceImpl(@Autowired NedisCacheProvider cacheProvider) {
        NedisCacheManager cacheManager = cacheProvider.getCacheManager("BDE_FETCHSCHEME_MANAGE");
        this.bdeFetchSchemeCache = cacheManager.getCache("BDE_FETCHSCHEME");
    }

    @Override
    public List<FetchSchemeVO> listFetchScheme(String formSchemeId) {
        List fetchSchemeDatas = (List)this.bdeFetchSchemeCache.get(formSchemeId, () -> this.fetchSchemeDao.listFetchSchemeByFormSchemeId(formSchemeId));
        if (CollectionUtils.isEmpty((Collection)fetchSchemeDatas)) {
            return CollectionUtils.newArrayList();
        }
        return fetchSchemeDatas.stream().filter(fetchSchemeEO -> this.authorityProvider.canReadFormulaScheme(fetchSchemeEO.getId())).map(this::convertFetchSchemeEOToVO).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void saveFetchScheme(FetchSchemeVO fetchSchemeVO) {
        this.checkFetchScheme(fetchSchemeVO);
        FetchSchemeEO fetchScheme = this.convertFetchSchemeVOToEO(fetchSchemeVO);
        this.fetchSchemeDao.save(fetchScheme);
        this.fetchSchemeCacheClear();
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void deleteFetchScheme(FetchSchemeVO fetchSchemeVO) {
        FetchSchemeEO fetchScheme = this.convertFetchSchemeVOToEO(fetchSchemeVO);
        this.fetchSchemeDao.delete(fetchScheme);
        String fetchSchemeId = fetchSchemeVO.getId();
        this.fetchSettingService.deleteByFetchSchemeId(fetchSchemeId);
        this.fetchSettingDesDao.deleteByFetchSchemeId(fetchSchemeId);
        this.fetchFloatSettingService.deleteByFetchSchemeId(fetchSchemeId);
        this.fetchFloatSettingDesDao.deleteByFetchSchemeId(fetchSchemeId);
        this.dimensionSettingDao.deleteByFetchSchemeId(fetchSchemeId);
        this.dimensionValueSettingDao.deleteByFetchSchemeId(fetchSchemeId);
        this.fetchSchemeCacheClear();
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public int updateFetchScheme(FetchSchemeVO fetchSchemeVO) {
        this.checkFetchScheme(fetchSchemeVO);
        FetchSchemeVO oldFetchScheme = this.getFetchScheme(fetchSchemeVO.getId());
        Assert.isNotNull((Object)oldFetchScheme, (String)"\u53d6\u6570\u65b9\u6848\u66f4\u65b0\u7684\u76ee\u6807\u4e3a\u7a7a", (Object[])new Object[0]);
        if (!Objects.isNull(fetchSchemeVO.getBizType()) && !Objects.equals(oldFetchScheme.getBizType(), fetchSchemeVO.getBizType())) {
            throw new BdeRuntimeException("\u53d6\u6570\u65b9\u6848\u4e0d\u5141\u8bb8\u66f4\u6539\u4e1a\u52a1\u7c7b\u578b");
        }
        FetchSchemeEO fetchScheme = this.convertFetchSchemeVOToEO(fetchSchemeVO);
        int result = this.fetchSchemeDao.update(fetchScheme);
        this.fetchSchemeCacheClear();
        FetchSchemeLogHelperUtil.updateFetchSchemeLogInfo(fetchSchemeVO, oldFetchScheme.getName());
        return result;
    }

    @Override
    public FetchSchemeVO getFetchScheme(String id) {
        FetchSchemeEO fetchScheme = this.getFetchSchemeById(id);
        if (null == fetchScheme) {
            return null;
        }
        return this.convertFetchSchemeEOToVO(fetchScheme);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public String copyFetchScheme(String sourceId, String name) {
        List<DimensionValueSettingEO> dimensionValueSettingEOS;
        List<DimensionSettingEO> dimensionSettingEOS;
        List fetchFloatSettingDesEOS;
        FetchSchemeEO fetchSchemeEO = this.getFetchSchemeById(sourceId);
        if (Objects.isNull(fetchSchemeEO)) {
            throw new BdeRuntimeException("\u8981\u590d\u5236\u7684\u6e90\u53d6\u6570\u65b9\u6848\u4e0d\u5b58\u5728\uff01");
        }
        String sourceFetchSchemeName = fetchSchemeEO.getName();
        String newFetchSchemeId = UUIDUtils.newHalfGUIDStr();
        FetchSchemeVO fetchSchemeVO = this.convertFetchSchemeEOToVO(fetchSchemeEO);
        FetchSchemeVO newFetchScheme = new FetchSchemeVO();
        BeanUtils.copyProperties(fetchSchemeVO, newFetchScheme);
        newFetchScheme.setOrdinal(new BigDecimal(System.currentTimeMillis()));
        newFetchScheme.setId(newFetchSchemeId);
        newFetchScheme.setName(name);
        this.saveFetchScheme(newFetchScheme);
        this.adjustPeriodSettingService.copyAdjustPeriodSettingByFetchSchemeId(fetchSchemeEO.getId(), newFetchSchemeId);
        List fetchSettingEOS = this.fetchSettingDao.listFetchSettingByFetchSchemeId(sourceId);
        this.handlerFetchSettingList(newFetchSchemeId, fetchSettingEOS);
        List fetchSettingDesEOS = this.fetchSettingDesDao.listFetchSettingByFetchSchemeId(sourceId);
        this.handlerFetchSettingDesList(newFetchSchemeId, fetchSettingDesEOS);
        FetchSettingCond fetchFloatSettingCond = new FetchSettingCond();
        fetchFloatSettingCond.setFetchSchemeId(sourceId);
        List<FetchFloatSettingEO> fetchFloatSettingEOS = this.fetchFloatSettingService.getFetchFloatSettingListByCond(fetchFloatSettingCond);
        if (!CollectionUtils.isEmpty(fetchFloatSettingEOS)) {
            fetchFloatSettingEOS.forEach(item -> {
                item.setId(UUIDUtils.newHalfGUIDStr());
                item.setFetchSchemeId(newFetchSchemeId);
            });
            this.fetchFloatSettingService.addBatch(fetchFloatSettingEOS);
        }
        if (!CollectionUtils.isEmpty((Collection)(fetchFloatSettingDesEOS = this.fetchFloatSettingDesDao.listFetchSettingByFetchSchemeId(sourceId)))) {
            fetchFloatSettingDesEOS.forEach(item -> {
                item.setId(UUIDUtils.newHalfGUIDStr());
                item.setFetchSchemeId(newFetchSchemeId);
            });
            this.fetchFloatSettingDesDao.addBatch(fetchFloatSettingDesEOS);
        }
        if ((dimensionSettingEOS = this.dimensionSettingDao.listFetchSettingByFetchSchemeId(sourceId)) != null) {
            dimensionSettingEOS.forEach(item -> {
                item.setId(UUIDUtils.newHalfGUIDStr());
                item.setFetchSchemeId(newFetchSchemeId);
            });
            this.dimensionSettingDao.addBatch(dimensionSettingEOS);
        }
        if (!CollectionUtils.isEmpty(dimensionValueSettingEOS = this.dimensionValueSettingDao.listFetchSettingByFetchSchemeId(sourceId))) {
            dimensionValueSettingEOS.forEach(item -> {
                item.setId(UUIDUtils.newHalfGUIDStr());
                item.setFetchSchemeId(newFetchSchemeId);
            });
            this.dimensionValueSettingDao.addBatch(dimensionValueSettingEOS);
        }
        FetchSchemeLogHelperUtil.copyFetchSchemeLogInfo(fetchSchemeVO.getFormSchemeId(), name, sourceFetchSchemeName, fetchSchemeVO.getBizType());
        return newFetchSchemeId;
    }

    private void handlerFetchSettingDesList(String newFetchSchemeId, List<FetchSettingDesEO> fetchSettingDesEOS) {
        if (CollectionUtils.isEmpty(fetchSettingDesEOS)) {
            return;
        }
        Map<String, List<FetchSettingDesEO>> dataLinkIdToEO = fetchSettingDesEOS.stream().collect(Collectors.groupingBy(FetchSettingDesEO::getDataLinkId));
        dataLinkIdToEO.forEach((dataLinkId, settingList) -> {
            for (FetchSettingDesEO item : settingList) {
                String newId = UUIDUtils.newHalfGUIDStr();
                item.setId(newId);
                item.setFetchSchemeId(newFetchSchemeId);
                List fixedAdaptSettingVOS = FetchSettingNrUtil.convertFixedSettingDataStr((String)item.getFixedSettingData());
                item.setFixedSettingData(JSONUtil.toJSONString((Object)fixedAdaptSettingVOS));
            }
        });
        this.fetchSettingDesDao.addBatch(fetchSettingDesEOS);
    }

    private void handlerFetchSettingList(String newFetchSchemeId, List<FetchSettingEO> fetchSettingEOS) {
        if (CollectionUtils.isEmpty(fetchSettingEOS)) {
            return;
        }
        Map<String, List<FetchSettingEO>> dataLinkIdToEO = fetchSettingEOS.stream().collect(Collectors.groupingBy(FetchSettingEO::getDataLinkId));
        dataLinkIdToEO.forEach((dataLinkId, settingList) -> {
            for (FetchSettingEO item : settingList) {
                String newId = UUIDUtils.newHalfGUIDStr();
                item.setId(newId);
                item.setFetchSchemeId(newFetchSchemeId);
                List fixedAdaptSettingVOS = FetchSettingNrUtil.convertFixedSettingDataStr((String)item.getFixedSettingData());
                item.setFixedSettingData(JSONUtil.toJSONString((Object)fixedAdaptSettingVOS));
            }
        });
        this.fetchSettingService.addBatch(fetchSettingEOS);
    }

    @Override
    public Boolean canEditFetchScheme(String taskId) {
        boolean businessManager = this.systemIdentityService.isBusinessManager();
        boolean admin = this.systemIdentityService.isAdmin();
        if (businessManager || admin) {
            return true;
        }
        return this.authorityProvider.canModeling(taskId);
    }

    @Override
    public Boolean exchangeOrdinal(String srcId, String targetId) {
        FetchSchemeVO srcFetchScheme = this.getFetchScheme(srcId);
        Assert.isNotNull((Object)srcFetchScheme, (String)String.format("\u6807\u8bc6\u3010%1$s\u3011\u5bf9\u5e94\u7684\u6570\u636e\u65b9\u6848\u5df2\u7ecf\u88ab\u4fee\u6539\u6216\u5220\u9664\uff0c\u8bf7\u5237\u65b0\u540e\u91cd\u8bd5", srcId), (Object[])new Object[0]);
        FetchSchemeVO targetFetchScheme = this.getFetchScheme(targetId);
        Assert.isNotNull((Object)srcFetchScheme, (String)String.format("\u6807\u8bc6\u3010%1$s\u3011\u5bf9\u5e94\u7684\u6570\u636e\u65b9\u6848\u5df2\u7ecf\u88ab\u4fee\u6539\u6216\u5220\u9664\uff0c\u8bf7\u5237\u65b0\u540e\u91cd\u8bd5", srcId), (Object[])new Object[0]);
        this.fetchSchemeDao.updateOrdinalById(srcId, targetFetchScheme.getOrdinal());
        this.fetchSchemeDao.updateOrdinalById(targetId, srcFetchScheme.getOrdinal());
        this.fetchSchemeCacheClear();
        return true;
    }

    @Override
    public Boolean queryIncludeAdjustVoucherByFetchSchemeId(String fetchSchemeId) {
        FetchSchemeVO fetchScheme = this.getFetchScheme(fetchSchemeId);
        return Objects.nonNull(fetchScheme) && Objects.nonNull(fetchScheme.getIncludeAdjustVchr()) && 0 != fetchScheme.getIncludeAdjustVchr();
    }

    @Override
    public void updateIncludeAdjustVoucherByFetchSchemeId(String fetchSchemeId, Integer includeAdjustVchr) {
        Assert.isNotEmpty((String)fetchSchemeId, (String)"\u66f4\u65b0\u662f\u5426\u5305\u542b\u8c03\u6574\u51ed\u8bc1\uff0c\u7f3a\u5c11\u53d6\u6570\u65b9\u6848ID", (Object[])new Object[0]);
        int successNum = this.fetchSchemeDao.updateIncludeAdjustVoucherByFetchSchemeId(fetchSchemeId, includeAdjustVchr.intValue());
        if (successNum < 1) {
            throw new BusinessRuntimeException("\u66f4\u65b0\u662f\u5426\u5305\u542b\u8c03\u6574\u51ed\u8bc1\u5931\u8d25");
        }
        this.fetchSchemeCacheClear();
    }

    private boolean contains(String srcFormula, int beginIndex, String oldId) {
        if (beginIndex + oldId.length() > srcFormula.length()) {
            return false;
        }
        return oldId.equals(srcFormula.substring(beginIndex, beginIndex + oldId.length()));
    }

    @Override
    public List<FetchSchemeVO> listFetchSchemeByIdList(List<String> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return Collections.emptyList();
        }
        ArrayList<FetchSchemeEO> fetchSchemeDatas = new ArrayList<FetchSchemeEO>();
        for (String id : idList) {
            FetchSchemeEO fetchSchemeEO = this.getFetchSchemeById(id);
            if (fetchSchemeEO == null) continue;
            fetchSchemeDatas.add(fetchSchemeEO);
        }
        if (CollectionUtils.isEmpty(fetchSchemeDatas)) {
            return Collections.emptyList();
        }
        return fetchSchemeDatas.stream().map(fetchScheme -> {
            FetchSchemeVO fetchSchemeVO = this.convertFetchSchemeEOToVO((FetchSchemeEO)fetchScheme);
            return fetchSchemeVO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<FetchSchemeVO> listFetchSchemeByBizType(String bizType) {
        List fetchSchemeEOList = this.fetchSchemeDao.loadAllByBizType(BizTypeEnum.getEnumByCode((String)bizType).getCode());
        ArrayList<FetchSchemeVO> fetchSchemeVOList = new ArrayList<FetchSchemeVO>();
        for (FetchSchemeEO fetchSchemeEO : fetchSchemeEOList) {
            FetchSchemeVO fetchSchemeVO = this.convertFetchSchemeEOToVO(fetchSchemeEO);
            fetchSchemeVOList.add(fetchSchemeVO);
        }
        return fetchSchemeVOList;
    }

    private void checkFetchScheme(FetchSchemeVO fetchSchemeVO) {
        List names;
        if (fetchSchemeVO.getName().length() > 36 || fetchSchemeVO.getName().getBytes(StandardCharsets.UTF_8).length > 36) {
            throw new BdeRuntimeException("\u53d6\u6570\u65b9\u6848\u540d\u79f0\u8d85\u8fc7\u6700\u5927\u957f\u5ea6\uff0c\u8bf7\u4fee\u6539\u3002");
        }
        List fetchSchemeDatas = this.fetchSchemeDao.loadAllByBizType(fetchSchemeVO.getBizType());
        if (!CollectionUtils.isEmpty((Collection)fetchSchemeDatas) && (names = fetchSchemeDatas.stream().filter(item -> item.getFormSchemeId().equals(fetchSchemeVO.getFormSchemeId())).map(FetchSchemeEO::getName).collect(Collectors.toList())).contains(fetchSchemeVO.getName())) {
            throw new BdeRuntimeException("\u53d6\u6570\u65b9\u6848\u540d\u79f0\u4e0d\u5141\u8bb8\u91cd\u590d");
        }
    }

    @Override
    public void fetchSchemeCacheClear() {
        this.bdeFetchSchemeCache.clear();
        this.adjustPeriodSettingService.cleanCache();
    }

    private FetchSchemeEO getFetchSchemeById(String id) {
        return (FetchSchemeEO)this.bdeFetchSchemeCache.get(id, () -> this.fetchSchemeDao.selectById(id));
    }

    private FetchSchemeEO convertFetchSchemeVOToEO(FetchSchemeVO fetchSchemeVO) {
        FetchSchemeEO fetchScheme = new FetchSchemeEO();
        BeanUtils.copyProperties(fetchSchemeVO, fetchScheme);
        fetchScheme.setBizType(BizTypeEnum.getEnumByCode((String)fetchSchemeVO.getBizType()));
        return fetchScheme;
    }

    private FetchSchemeVO convertFetchSchemeEOToVO(FetchSchemeEO fetchSchemeEO) {
        FetchSchemeVO fetchSchemeVO = new FetchSchemeVO();
        BeanUtils.copyProperties(fetchSchemeEO, fetchSchemeVO);
        fetchSchemeVO.setBizType(fetchSchemeEO.getBizType().getCode());
        fetchSchemeVO.setAdjustPeriodSettingVOs(this.adjustPeriodSettingService.listAdjustPeriodSettingByFetchSchemeId(fetchSchemeEO.getId()));
        return fetchSchemeVO;
    }

    @Override
    public List<FetchSchemeVO> listFetchSchemeByTaskCodeAndName(String taskCode, String name) {
        List formSchemeDefines;
        Assert.isNotEmpty((String)taskCode, (String)"\u4efb\u52a1\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        try {
            List allTaskDefines = this.runTimeViewController.getAllTaskDefines();
            List taskDefinesByTaskCode = allTaskDefines.stream().filter(taskDefine -> taskDefine.getTaskCode().equals(taskCode)).collect(Collectors.toList());
            Assert.isTrue((taskDefinesByTaskCode.size() == 1 ? 1 : 0) != 0, (String)("\u6839\u636e\u4efb\u52a1\u6807\u8bc6[" + taskCode + "]\u67e5\u8be2\u4efb\u52a1\u5931\u8d25\uff0c\u4efb\u52a1\u6807\u8bc6\u4e0d\u552f\u4e00"), (Object[])new Object[0]);
            formSchemeDefines = this.runTimeViewController.queryFormSchemeByTask(((TaskDefine)taskDefinesByTaskCode.get(0)).getKey());
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u83b7\u53d6\u4efb\u52a1[" + taskCode + "]\u7684\u62a5\u8868\u65b9\u6848\u5931\u8d25", (Throwable)e);
        }
        List fetchSchemeEos = this.fetchSchemeDao.selectByFormSchemesAndName(formSchemeDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList()), Optional.of(name));
        if (CollectionUtils.isEmpty((Collection)fetchSchemeEos)) {
            return Collections.emptyList();
        }
        return fetchSchemeEos.stream().map(this::convertFetchSchemeEOToVO).collect(Collectors.toList());
    }
}

