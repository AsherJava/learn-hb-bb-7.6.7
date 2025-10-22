/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bde.common.certify.BdeRequestCertifyConfig
 *  com.jiuqi.bde.common.constant.RequestSourceTypeEnum
 *  com.jiuqi.bde.common.dto.FixedAdaptSettingVO
 *  com.jiuqi.bde.common.dto.FixedFieldDefineSettingVO
 *  com.jiuqi.bde.common.dto.FloatZbMappingVO
 *  com.jiuqi.bde.common.dto.QueryConfigInfo
 *  com.jiuqi.bde.common.dto.fetch.request.FetchRequestContextDTO
 *  com.jiuqi.bde.common.dto.fetch.request.FetchRequestFloatSettingDTO
 *  com.jiuqi.bde.common.exception.BdeRuntimeException
 *  com.jiuqi.bde.common.util.BdeCommonUtil
 *  com.jiuqi.budget.component.domain.FormulaExeParam
 *  com.jiuqi.budget.component.service.FormulaExecService
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.utils.BeanConvertUtil
 *  com.jiuqi.gcreport.bde.common.vo.FetchSettingVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.AdjustPeriodSettingVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.BDEQueryDataCond
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSchemeVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FixedFieldDefineSettingDTO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.GcFetchRequestDTO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.GcFetchRequestFixedSettingDTO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchFloatSettingDesDao
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchSettingDao
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchSettingDesDao
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchFloatSettingDesEO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchFloatSettingEO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingDesEO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingEO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.enums.AdaptContextEnum
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.utils.AdaptCondiUtils
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.utils.BizTypeValidator
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DataTypesConvert
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataLinkService
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo
 *  com.jiuqi.nr.jtable.params.output.EntityByKeyReturnInfo
 *  com.jiuqi.nr.jtable.params.output.EntityData
 *  com.jiuqi.nr.jtable.service.IJtableEntityService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bde.common.certify.BdeRequestCertifyConfig;
import com.jiuqi.bde.common.constant.RequestSourceTypeEnum;
import com.jiuqi.bde.common.dto.FixedAdaptSettingVO;
import com.jiuqi.bde.common.dto.FixedFieldDefineSettingVO;
import com.jiuqi.bde.common.dto.FloatZbMappingVO;
import com.jiuqi.bde.common.dto.QueryConfigInfo;
import com.jiuqi.bde.common.dto.fetch.request.FetchRequestContextDTO;
import com.jiuqi.bde.common.dto.fetch.request.FetchRequestFloatSettingDTO;
import com.jiuqi.bde.common.exception.BdeRuntimeException;
import com.jiuqi.bde.common.util.BdeCommonUtil;
import com.jiuqi.budget.component.domain.FormulaExeParam;
import com.jiuqi.budget.component.service.FormulaExecService;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.utils.BeanConvertUtil;
import com.jiuqi.gcreport.bde.common.vo.FetchSettingVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.AdjustPeriodSettingVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.BDEQueryDataCond;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSchemeVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FixedFieldDefineSettingDTO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.GcFetchRequestDTO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.GcFetchRequestFixedSettingDTO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchFloatSettingDesDao;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchSettingDao;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchSettingDesDao;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchFloatSettingDesEO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchFloatSettingEO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingDesEO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingEO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.enums.AdaptContextEnum;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.utils.FetchSettingNrUtil;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchFloatSettingService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSchemeNrService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSettingService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.utils.AdaptCondiUtils;
import com.jiuqi.gcreport.bde.fetchsetting.impl.utils.BizTypeValidator;
import com.jiuqi.gcreport.bde.fetchsetting.impl.utils.FetchTaskUtil;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataLinkService;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo;
import com.jiuqi.nr.jtable.params.output.EntityByKeyReturnInfo;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FetchSettingServiceImpl
implements FetchSettingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FetchSettingServiceImpl.class);
    @Autowired
    private FetchSettingDao fetchSettingDao;
    @Autowired
    private FetchSettingDesDao fetchSettingDesDao;
    @Autowired
    private FetchSchemeNrService fetchSchemeService;
    @Autowired
    private FetchFloatSettingDesDao fetchFloatSettingDesDao;
    @Autowired
    private FetchFloatSettingService fetchFloatSettingService;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IJtableEntityService jtableEntityService;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private IRuntimeDataLinkService runtimeDataLinkService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private FormulaExecService formulaExecService;
    @Autowired
    private PeriodEngineService periodEngineService;
    private final NedisCache bdeFetchSettingCache;
    private static final String BDE_FETCH_SETTING_KEY = "{FETCHSCHEMEID}_{FORMID}";

    public FetchSettingServiceImpl(@Autowired NedisCacheProvider cacheProvider) {
        NedisCacheManager cacheManager = cacheProvider.getCacheManager("BDE_FETCH_SETTING_MANAGE");
        this.bdeFetchSettingCache = cacheManager.getCache("BDE_FETCH_SETTING");
    }

    @Override
    public List<FetchSettingVO> listFetchSettingByFormId(FetchSettingCond fetchSettingCond) {
        List<FetchSettingEO> fetchSettings = this.getFetchSettingListByCond(fetchSettingCond);
        if (CollectionUtils.isEmpty(fetchSettings)) {
            return Collections.emptyList();
        }
        return fetchSettings.stream().map(this::convertFetchSettingEO2VO).filter(setting -> !CollectionUtils.isEmpty((Collection)setting.getFixedSettingData())).collect(Collectors.toList());
    }

    @Override
    public List<FetchSettingEO> listAllFetchSettingEOByFormId(FetchSettingCond cond) {
        return this.fetchSettingDao.listFetchSettingByFormId(cond.getFormSchemeId(), cond.getFetchSchemeId(), cond.getFormId());
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void fetchSettingPublishByFetchSchemeId(FetchSettingCond fetchSettingCond) {
        this.fetchSettingDao.deleteFetchSettingByFetchSettingCond(fetchSettingCond);
        this.fetchFloatSettingService.deleteFloatFetchSettingByFetchSettingCond(fetchSettingCond);
        this.publishFixTableFetchSetting(this.fetchSettingDesDao.listFetchSettingDesWithStopFlagByFetchSchemeId(fetchSettingCond));
        this.publishFloatSetting(this.fetchFloatSettingDesDao.listFetchFloatSettingDesByFetchSchemeId(fetchSettingCond));
        this.fetchSettingCacheEvictInFetchScheme(fetchSettingCond.getFetchSchemeId(), fetchSettingCond.getFormSchemeId());
        this.fetchFloatSettingService.fetchFloatSettingCacheEvit(fetchSettingCond.getFetchSchemeId());
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void fetchSettingPublishByFormId(FetchSettingCond fetchSettingCond) {
        if (StringUtils.isEmpty((String)fetchSettingCond.getFormId())) {
            return;
        }
        this.fetchSettingDao.deleteFetchSettingByFetchSettingCond(fetchSettingCond);
        this.fetchFloatSettingService.deleteFloatFetchSettingByFetchSettingCond(fetchSettingCond);
        this.publishFixTableFetchSetting(this.fetchSettingDesDao.listFetchSettingDesWithStopFlagByFormId(fetchSettingCond));
        this.publishFloatSetting(this.fetchFloatSettingDesDao.listFetchFloatSettingDesByFormId(fetchSettingCond));
        this.fetchSettingCacheEvict(fetchSettingCond.getFetchSchemeId(), fetchSettingCond.getFormId());
        this.fetchFloatSettingService.fetchFloatSettingCacheEvit(fetchSettingCond.getFetchSchemeId());
    }

    private void publishFloatSetting(List<FetchFloatSettingDesEO> fetchFloatSettingDesData) {
        if (CollectionUtils.isEmpty(fetchFloatSettingDesData)) {
            return;
        }
        ArrayList<FetchFloatSettingEO> fetchFloatSettingData = new ArrayList<FetchFloatSettingEO>();
        for (FetchFloatSettingDesEO fetchFloatSettingDes : fetchFloatSettingDesData) {
            FetchFloatSettingEO fetchFloatSetting = new FetchFloatSettingEO();
            BeanUtils.copyProperties(fetchFloatSettingDes, fetchFloatSetting);
            fetchFloatSettingData.add(fetchFloatSetting);
        }
        this.fetchFloatSettingService.addBatch(fetchFloatSettingData);
    }

    private void publishFixTableFetchSetting(List<FetchSettingDesEO> fetchSettingDesData) {
        if (CollectionUtils.isEmpty(fetchSettingDesData)) {
            return;
        }
        ArrayList<FetchSettingEO> fetchSettingData = new ArrayList<FetchSettingEO>();
        for (FetchSettingDesEO fetchSettingDes : fetchSettingDesData) {
            FetchSettingEO fetchSetting = new FetchSettingEO();
            BeanUtils.copyProperties(fetchSettingDes, fetchSetting);
            fetchSettingData.add(fetchSetting);
        }
        this.fetchSettingDao.addBatch(fetchSettingData);
    }

    @Override
    public List<FixedFieldDefineSettingDTO> listDataLinkFixedSettingRowRecords(FetchSettingCond fetchSettingCond) {
        List<FetchSettingEO> fetchSettings = this.getFetchSettingListByCond(fetchSettingCond);
        if (CollectionUtils.isEmpty(fetchSettings)) {
            return CollectionUtils.newArrayList();
        }
        LinkedList<FixedFieldDefineSettingDTO> fixedFieldDefineSettingVOs = new LinkedList<FixedFieldDefineSettingDTO>();
        for (FetchSettingEO fetchSetting : fetchSettings) {
            FixedFieldDefineSettingDTO runtimeFixedZbSettingVO = FetchSettingNrUtil.getRuntimeFixedZbSettingVOFromEo(fetchSetting, this.iRunTimeViewController, this.runtimeDataLinkService);
            if (Objects.isNull(runtimeFixedZbSettingVO) || CollectionUtils.isEmpty((Collection)runtimeFixedZbSettingVO.getFixedSettingData())) continue;
            fixedFieldDefineSettingVOs.add(runtimeFixedZbSettingVO);
        }
        return fixedFieldDefineSettingVOs;
    }

    @Override
    public Map<String, Double> getBDEQueryDataMapping(BDEQueryDataCond bdeQueryDataCond) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getBDEFloatPenetrateTableData(BDEQueryDataCond bdeQueryDataCond) {
        return null;
    }

    @Override
    public GcFetchRequestDTO getBDEPenetrateParam(BDEQueryDataCond bdeQueryDataCond) {
        Assert.isNotNull((Object)bdeQueryDataCond.getFloatSetting());
        GcFetchRequestDTO bdeFetchTask = this.createBaseBDEPenetrateTaskDTO(bdeQueryDataCond);
        bdeFetchTask.setFilters(this.createFilterMapList(bdeQueryDataCond.getFloatSetting().getQueryConfigInfo(), bdeQueryDataCond.getRowDataZbId2valueMapList()));
        if (bdeFetchTask.getFetchContext().getIncludeUncharged() == null) {
            bdeFetchTask.getFetchContext().setIncludeUncharged(Boolean.valueOf(true));
        }
        bdeFetchTask.getFetchContext().setIncludeAdjustVchr(Boolean.valueOf(this.queryIsIncludeAdjustVoucher(bdeQueryDataCond.getFetchSchemeId())));
        bdeFetchTask.setStandaloneServer(BdeCommonUtil.isStandaloneServer());
        bdeFetchTask.setAppName(BdeRequestCertifyConfig.getAppName().toUpperCase());
        return bdeFetchTask;
    }

    private boolean queryIsIncludeAdjustVoucher(String fetchSchemeId) {
        FetchSchemeVO fetchScheme = this.fetchSchemeService.getFetchScheme(fetchSchemeId);
        return Objects.nonNull(fetchScheme) && Objects.nonNull(fetchScheme.getIncludeAdjustVchr()) && 0 != fetchScheme.getIncludeAdjustVchr();
    }

    @Override
    public Map<String, Double> getBDEFloatPenetrateResultByRanks(BDEQueryDataCond bdeQueryDataCond) {
        return null;
    }

    @Override
    public List<String> lisFormKeyBySchemeKey(String fetchSchemeKey, String formSchemeKey) {
        FetchSettingCond fetchSettingCond = new FetchSettingCond();
        fetchSettingCond.setFetchSchemeId(fetchSchemeKey);
        fetchSettingCond.setFormSchemeId(formSchemeKey);
        List<FetchSettingEO> fetchSettingList = this.getFetchSettingListByCond(fetchSettingCond);
        if (CollectionUtils.isEmpty(fetchSettingList)) {
            return Collections.emptyList();
        }
        return fetchSettingList.stream().map(FetchSettingEO::getFormId).collect(Collectors.toList());
    }

    @Override
    public void deleteByFetchSchemeId(String fetchSchemeId) {
        FetchSchemeVO fetchScheme = this.fetchSchemeService.getFetchScheme(fetchSchemeId);
        if (Objects.isNull(fetchScheme)) {
            return;
        }
        List formDefines = this.iRunTimeViewController.queryAllFormDefinesByFormScheme(fetchScheme.getFormSchemeId());
        this.fetchSettingDao.deleteByFetchSchemeId(fetchSchemeId);
        for (FormDefine formDefine : formDefines) {
            this.fetchSettingCacheEvict(fetchSchemeId, formDefine.getKey());
        }
    }

    @Override
    public void addBatch(List<FetchSettingEO> fetchSettingEOS) {
        if (CollectionUtils.isEmpty(fetchSettingEOS)) {
            return;
        }
        this.fetchSettingDao.addBatch(fetchSettingEOS);
        for (FetchSettingEO fetchSettingEO : fetchSettingEOS) {
            this.fetchSettingCacheEvict(fetchSettingEO.getFetchSchemeId(), fetchSettingEO.getFormId());
        }
    }

    private GcFetchRequestDTO createBaseBDEPenetrateTaskDTO(BDEQueryDataCond bdeQueryDataCond) {
        List<String> formkeyList;
        String formInfo;
        GcFetchRequestDTO fetchRequestDTO = new GcFetchRequestDTO();
        fetchRequestDTO.setRequestSourceType(RequestSourceTypeEnum.PENETRATE.getCode());
        FetchRequestContextDTO fetchContext = (FetchRequestContextDTO)BeanConvertUtil.convert((Object)bdeQueryDataCond, FetchRequestContextDTO.class, (String[])new String[0]);
        fetchContext.setEndDateStr(bdeQueryDataCond.getEndDate());
        fetchContext.setStartDateStr(bdeQueryDataCond.getBeginDate());
        String orgType = FetchTaskUtil.getOrgTypeByTaskAndCtx(bdeQueryDataCond.getTaskId());
        String orgName = this.queryOrgName(bdeQueryDataCond.getUnitCode(), orgType, DateUtils.parse((String)bdeQueryDataCond.getEndDate()));
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(bdeQueryDataCond.getTaskId());
        String periodStr = bdeQueryDataCond.getPeriodStr();
        String periodTitle = bdeQueryDataCond.getPeriodStr();
        if (StringUtils.isEmpty((String)periodStr)) {
            Date date = DateUtils.parse((String)bdeQueryDataCond.getEndDate());
            periodStr = PeriodUtils.getPeriodFromDate((int)taskDefine.getPeriodType().type(), (Date)date);
        }
        periodTitle = this.periodEngineService.getPeriodAdapter().getPeriodProvider(taskDefine.getDateTime()).getPeriodTitle(periodStr);
        FetchSchemeVO fetchScheme = this.fetchSchemeService.getFetchScheme(bdeQueryDataCond.getFetchSchemeId());
        Assert.isNotNull((Object)fetchScheme, (String)String.format("\u6839\u636e\u53d6\u6570\u65b9\u6848\u6807\u8bc6\u3010%1$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u53d6\u6570\u65b9\u6848\uff0c\u8bf7\u68c0\u67e5\u53d6\u6570\u65b9\u6848\u914d\u7f6e", bdeQueryDataCond.getFetchSchemeId()), (Object[])new Object[0]);
        for (AdjustPeriodSettingVO adjustPeriodSettingVO : fetchScheme.getAdjustPeriodSettingVOs()) {
            if (!periodStr.equals(adjustPeriodSettingVO.getAdjustPeriod())) continue;
            fetchContext.setStartAdjustPeriod(adjustPeriodSettingVO.getStartAdjustPeriod());
            fetchContext.setEndAdjustPeriod(adjustPeriodSettingVO.getEndAdjustPeriod());
            break;
        }
        fetchContext.setPeriodTitle(periodTitle);
        fetchContext.setUnitName(orgName);
        fetchContext.setPeriodScheme(periodStr);
        boolean floatQuery = bdeQueryDataCond.getFloatSetting() != null && bdeQueryDataCond.getFloatSetting().getQueryConfigInfo() != null && !CollectionUtils.isEmpty((Collection)bdeQueryDataCond.getFloatSetting().getQueryConfigInfo().getZbMapping());
        ArrayList<GcFetchRequestFixedSettingDTO> fixedSettingList = new ArrayList<GcFetchRequestFixedSettingDTO>();
        GcFetchRequestFixedSettingDTO fixedSetting = null;
        block1: for (FixedFieldDefineSettingVO fieldDefineSettingVO : bdeQueryDataCond.getFixedSettingDatas()) {
            FormulaExeParam formulaExeParam = null;
            boolean noFilterAdaptCondi = !bdeQueryDataCond.isFilterAdaptCondi() || StringUtils.isEmpty((String)((FixedAdaptSettingVO)fieldDefineSettingVO.getFixedSettingData().get(0)).getAdaptFormula()) || "#".equals(((FixedAdaptSettingVO)fieldDefineSettingVO.getFixedSettingData().get(0)).getAdaptFormula());
            for (FixedAdaptSettingVO fixedAdaptSetting : fieldDefineSettingVO.getFixedSettingData()) {
                fixedSetting = (GcFetchRequestFixedSettingDTO)BeanConvertUtil.convert((Object)fieldDefineSettingVO, GcFetchRequestFixedSettingDTO.class, (String[])new String[0]);
                if (noFilterAdaptCondi) {
                    fixedSetting.setLogicFormula(fixedAdaptSetting.getLogicFormula());
                    fixedSetting.setBizModelFormula(fixedAdaptSetting.getBizModelFormula());
                    fixedSetting.setAdaptFormula(fixedAdaptSetting.getAdaptFormula());
                    fixedSettingList.add(fixedSetting);
                    continue;
                }
                Assert.isNotNull((Object)orgType, (String)"\u9700\u8981\u8fc7\u6ee4\u9002\u5e94\u6761\u4ef6\u65f6\uff0c\u5355\u4f4d\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
                formulaExeParam = new FormulaExeParam();
                String adaptExpression = AdaptCondiUtils.expressionOrgTypeConvert((String)fixedAdaptSetting.getAdaptFormula(), (String)orgType);
                formulaExeParam.setFormulaExpress(adaptExpression);
                HashMap<String, String> adaptContext = new HashMap<String, String>(8);
                adaptContext.put(AdaptContextEnum.MD_ORG.getKey(), bdeQueryDataCond.getUnitCode());
                if (StringUtils.isEmpty((String)bdeQueryDataCond.getPeriodStr())) {
                    FormSchemeDefine formSchemeDefine = this.iRunTimeViewController.getFormScheme(bdeQueryDataCond.getFormSchemeId());
                    String endPeriodStr = this.parsePeriod(bdeQueryDataCond.getEndDate(), formSchemeDefine.getPeriodType());
                    adaptContext.put(AdaptContextEnum.DATATIME.getKey(), endPeriodStr);
                } else {
                    adaptContext.put(AdaptContextEnum.DATATIME.getKey(), bdeQueryDataCond.getPeriodStr());
                }
                adaptContext.put(orgType, bdeQueryDataCond.getUnitCode());
                formulaExeParam.setDimValMap(adaptContext);
                if (!this.formulaExecService.getAdaptVal(formulaExeParam)) continue;
                fixedSetting.setLogicFormula(fixedAdaptSetting.getLogicFormula());
                fixedSetting.setBizModelFormula(fixedAdaptSetting.getBizModelFormula());
                fixedSetting.setAdaptFormula(fixedAdaptSetting.getAdaptFormula());
                fixedSettingList.add(fixedSetting);
                continue block1;
            }
        }
        if (bdeQueryDataCond.isFilterAdaptCondi() && !floatQuery && CollectionUtils.isEmpty(fixedSettingList)) {
            throw new BdeRuntimeException("\u8be5\u6307\u6807\u672a\u914d\u7f6e\u89c4\u5219");
        }
        fetchRequestDTO.setFetchContext(fetchContext);
        if (bdeQueryDataCond.getFloatSetting() != null) {
            fetchRequestDTO.setFloatSetting(new FetchRequestFloatSettingDTO(bdeQueryDataCond.getFloatSetting().getQueryType(), bdeQueryDataCond.getFloatSetting().getQueryConfigInfo()));
        }
        fetchRequestDTO.setFixedSetting(fixedSettingList);
        FormSchemeDefine formSchemeDefine = this.iRunTimeViewController.getFormScheme(bdeQueryDataCond.getFormSchemeId());
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)bdeQueryDataCond.getDimensionSet());
        HashMap<String, String> dimMap = new HashMap<String, String>(8);
        if (formSchemeDefine != null && !StringUtils.isEmpty((String)formSchemeDefine.getDims())) {
            String[] dims;
            for (String dim : dims = formSchemeDefine.getDims().split(";")) {
                TableModelDefine tableModelDefine = this.entityMetaService.getTableModel(dim);
                String dimValue = (String)dimensionValueSet.getValue(tableModelDefine.getName());
                if (StringUtils.isEmpty((String)dimValue)) continue;
                dimMap.put(tableModelDefine.getName(), dimValue);
            }
        }
        fetchContext.setOtherEntity(dimMap);
        fetchContext.setDimensionSetStr(JSONUtil.toJSONString((Object)bdeQueryDataCond.getDimensionSet()));
        fetchContext.setBblx(this.getBblx(bdeQueryDataCond));
        if (!StringUtils.isEmpty((String)bdeQueryDataCond.getPeriodStr())) {
            String[] times = FetchTaskUtil.parseDataTime(bdeQueryDataCond.getPeriodStr());
            fetchContext.setPeriodScheme(bdeQueryDataCond.getPeriodStr());
            fetchContext.setStartDateStr(times[0]);
            fetchContext.setEndDateStr(times[1]);
        } else {
            fetchContext.setStartDateStr(bdeQueryDataCond.getBeginDate());
            fetchContext.setEndDateStr(bdeQueryDataCond.getEndDate());
        }
        fetchContext.setTaskId(bdeQueryDataCond.getTaskId());
        HashMap<String, String> extInfo = new HashMap<String, String>();
        ContextUser user = NpContextHolder.getContext().getUser();
        if (user != null) {
            extInfo.put("userName", user.getName());
        }
        if (!StringUtils.isEmpty((String)(formInfo = this.getFormInfoListByFormKeys(formkeyList = FetchTaskUtil.splitToList(bdeQueryDataCond.getFormId(), ";"))))) {
            extInfo.put("formTitle", formInfo);
        }
        fetchRequestDTO.setExtInfo(extInfo);
        return fetchRequestDTO;
    }

    private String queryOrgName(String orgCode, String orgType, Date versionDate) {
        OrgDTO orgParam = new OrgDTO();
        ArrayList<String> orgCodeparam = new ArrayList<String>();
        orgCodeparam.add(orgCode);
        orgParam.setOrgCodes(orgCodeparam);
        orgParam.setSyncOrgBaseInfo(Boolean.valueOf(false));
        orgParam.setVersionDate(versionDate);
        orgParam.setRecoveryflag(Integer.valueOf(-1));
        orgParam.setExtInfo(new HashMap());
        orgParam.setStopflag(Integer.valueOf(-1));
        orgParam.setForceUpdateHistoryVersionData(Boolean.valueOf(true));
        orgParam.setCategoryname(orgType);
        orgParam.setAuthType(OrgDataOption.AuthType.NONE);
        orgParam.setQueryDataStructure(OrgDataOption.QueryDataStructure.BASIC);
        PageVO page = ((OrgDataClient)ApplicationContextRegister.getBean(OrgDataClient.class)).list(orgParam);
        if (Objects.isNull(page) || page.getTotal() <= 0) {
            LOGGER.error("\u6839\u636e\u5355\u4f4d\u53c2\u6570{}\u672a\u83b7\u53d6\u5230\u5355\u4f4d\u5bf9\u8c61", (Object)orgParam);
            return "";
        }
        return ((OrgDO)page.getRows().get(0)).getName();
    }

    private String parsePeriod(String dateString, PeriodType periodType) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(dateString);
            return PeriodUtils.getPeriodFromDate((int)PeriodType.fromCode((int)periodType.code()).ordinal(), (Date)date);
        }
        catch (Exception e) {
            throw new BdeRuntimeException("\u65e5\u671f\u8f6c\u6362\u5931\u8d25", (Throwable)e);
        }
    }

    private List<Map<String, Object>> createFilterMapList(QueryConfigInfo queryConfigInfo, List<Map<String, Object>> rowDataZbId2valueMapList) {
        if (CollectionUtils.isEmpty(rowDataZbId2valueMapList)) {
            return Collections.emptyList();
        }
        if (queryConfigInfo == null) {
            return Collections.emptyList();
        }
        List zbMapping = queryConfigInfo.getZbMapping();
        if (CollectionUtils.isEmpty((Collection)zbMapping)) {
            return Collections.emptyList();
        }
        HashSet<String> filterZbIdSet = new HashSet<String>();
        for (FloatZbMappingVO zbMappingVO : zbMapping) {
            if (StringUtils.isEmpty((String)zbMappingVO.getQueryName()) || !zbMappingVO.getQueryName().startsWith("${")) continue;
            filterZbIdSet.add(zbMappingVO.getFieldDefineId());
        }
        if (filterZbIdSet.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<Map<String, Object>> filterCodeToValueMapList = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> zbId2valueMap : rowDataZbId2valueMapList) {
            HashMap<String, Object> filterCodeToValueMap = new HashMap<String, Object>();
            for (String filterZbId : filterZbIdSet) {
                filterCodeToValueMap.put(filterZbId, zbId2valueMap.get(filterZbId));
            }
            filterCodeToValueMapList.add(filterCodeToValueMap);
        }
        return filterCodeToValueMapList;
    }

    private String getBblx(BDEQueryDataCond bdeQueryDataCond) {
        EntityViewData dwEntity = this.jtableParamService.getDwEntity(bdeQueryDataCond.getFormSchemeId());
        JtableContext jtableContext = new JtableContext();
        jtableContext.setDimensionSet(bdeQueryDataCond.getDimensionSet());
        jtableContext.setFormSchemeKey(bdeQueryDataCond.getFormSchemeId());
        jtableContext.setFormulaSchemeKey(bdeQueryDataCond.getFetchSchemeId());
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((JtableContext)jtableContext);
        if (dimensionValueSet.getValue(dwEntity.getDimensionName()) == null) {
            return null;
        }
        String unitKey = dimensionValueSet.getValue(dwEntity.getDimensionName()).toString();
        String unitBblx = "";
        IEntityModel dwEntityModel = this.entityMetaService.getEntityModel(dwEntity.getKey());
        IEntityAttribute bblxField = dwEntityModel.getBblxField();
        if (bblxField != null) {
            EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
            entityQueryByKeyInfo.setContext(jtableContext);
            entityQueryByKeyInfo.setEntityKey(unitKey);
            entityQueryByKeyInfo.setEntityViewKey(dwEntity.getKey());
            entityQueryByKeyInfo.getCaptionFields().add(bblxField.getCode());
            EntityByKeyReturnInfo queryEntityDataByKey = this.jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo);
            int bblxIndex = queryEntityDataByKey.getCells().indexOf(bblxField.getCode());
            EntityData entity = queryEntityDataByKey.getEntity();
            if (entity != null && bblxIndex >= 0) {
                unitBblx = (String)entity.getData().get(bblxIndex);
            }
        }
        return unitBblx;
    }

    private String getFormInfoListByFormKeys(List<String> accessFormKeys) {
        StringBuffer formNames = new StringBuffer();
        accessFormKeys.stream().forEach(formKey -> {
            FormDefine formDefine = this.runTimeAuthViewController.queryFormById(formKey);
            formNames.append(formDefine.getTitle()).append("\u3001");
        });
        if (formNames.length() > 0) {
            formNames.delete(formNames.length() - 1, formNames.length());
        }
        return formNames.toString();
    }

    private FetchSettingVO convertFetchSettingEO2VO(FetchSettingEO fetchSettingData) {
        String fixedSettingDataStr = fetchSettingData.getFixedSettingData();
        List<FixedAdaptSettingVO> adaptSettingVOList = this.convertFixedSettingDataStr(fixedSettingDataStr);
        FetchSettingVO fetchSetting = new FetchSettingVO();
        BeanUtils.copyProperties(fetchSettingData, fetchSetting);
        fetchSetting.setFixedSettingData(adaptSettingVOList);
        this.setFieldDefineInfo(fetchSetting);
        return fetchSetting;
    }

    private void setFieldDefineInfo(FetchSettingVO fetchSetting) {
        try {
            FieldDefine fieldDefine = this.iRunTimeViewController.queryFieldDefine(fetchSetting.getFieldDefineId());
            if (Objects.nonNull(fieldDefine)) {
                fetchSetting.setDataLinkName(fieldDefine.getTitle());
                fetchSetting.setFieldDefineType(Integer.valueOf(DataTypesConvert.fieldTypeToDataType((FieldType)fieldDefine.getType())));
            } else {
                FetchSchemeNrService fetchSchemeService = (FetchSchemeNrService)ApplicationContextRegister.getBean(FetchSchemeNrService.class);
                FetchSchemeVO fetchScheme = fetchSchemeService.getFetchScheme(fetchSetting.getFetchSchemeId());
                Assert.isNotNull((Object)fetchScheme, (String)"\u6839\u636e\u53d6\u6570\u8bbe\u7f6e\u7684\u53d6\u6570\u65b9\u6848ID\u83b7\u53d6\u53d6\u6570\u65b9\u6848\u4e3a\u7a7a", (Object[])new Object[0]);
                BizTypeValidator.isValidBud((String)fetchScheme.getBizType(), (String)String.format("\u6839\u636e\u6307\u6807ID%1$s\u672a\u83b7\u53d6\u5230\u6307\u6807\u5bf9\u8c61", fetchSetting.getFieldDefineId()));
            }
        }
        catch (Exception e) {
            throw new BdeRuntimeException("\u67e5\u8be2\u56fa\u5b9a\u8868\u8bbe\u7f6e\u65f6\uff0c\u6839\u636e\u6307\u6807id\u3010" + fetchSetting.getFieldDefineId() + "\u3011\u83b7\u53d6\u6307\u6807\u5f02\u5e38", (Throwable)e);
        }
    }

    @Override
    public void fetchSettingCacheClear() {
        this.bdeFetchSettingCache.clear();
    }

    @Override
    public void fetchSettingCacheEvictInFetchScheme(String fetchSchemeId, String formSchemeKey) {
        List formDefines;
        try {
            formDefines = this.runTimeAuthViewController.queryAllFormDefinesByFormScheme(formSchemeKey);
        }
        catch (Exception e) {
            LOGGER.error("\u6839\u636e\u62a5\u8868\u65b9\u6848\u6807\u8bc6{}\u83b7\u53d6\u62a5\u8868\u51fa\u73b0\u9519\u8bef", (Object)formSchemeKey, (Object)e);
            this.fetchSettingCacheClear();
            return;
        }
        if (CollectionUtils.isEmpty((Collection)formDefines)) {
            return;
        }
        List formIdList = formDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
        for (String formId : formIdList) {
            this.fetchSettingCacheEvict(fetchSchemeId, formId);
        }
    }

    @Override
    public void fetchSettingCacheEvict(String fetchSchemeId, String formId) {
        this.bdeFetchSettingCache.evict(BDE_FETCH_SETTING_KEY.replace("{FETCHSCHEMEID}", fetchSchemeId).replace("{FORMID}", formId));
    }

    @Override
    public void fetchSettingCacheEvictInFetchScheme(String fetchSchemeId, List<String> formIdList) {
        for (String formId : formIdList) {
            this.fetchSettingCacheEvict(fetchSchemeId, formId);
        }
    }

    @Override
    public List<FetchSettingEO> getFetchSettingListByCond(FetchSettingCond fetchSettingCond) {
        Assert.isNotNull((Object)fetchSettingCond);
        if (StringUtils.isEmpty((String)fetchSettingCond.getFetchSchemeId())) {
            return Collections.emptyList();
        }
        List<FetchSettingEO> fetchSettingEOS = StringUtils.isEmpty((String)fetchSettingCond.getFormId()) ? this.getFetchSettingListInFetchScheme(fetchSettingCond) : this.getFetchSettingListInForm(fetchSettingCond.getFormSchemeId(), fetchSettingCond.getFetchSchemeId(), fetchSettingCond.getFormId());
        if (CollectionUtils.isEmpty(fetchSettingEOS)) {
            return fetchSettingEOS;
        }
        return fetchSettingEOS.stream().filter(fetchSetting -> this.filterEnableFetchSettingByCond(fetchSettingCond, (FetchSettingEO)fetchSetting)).collect(Collectors.toList());
    }

    @Override
    public List<FixedAdaptSettingVO> convertFixedSettingDataStr(String fixedSettingDataStr) {
        if (StringUtils.isEmpty((String)fixedSettingDataStr)) {
            return new ArrayList<FixedAdaptSettingVO>();
        }
        List fixedAdaptSettingVOS = (List)JsonUtils.readValue((String)fixedSettingDataStr, (TypeReference)new TypeReference<List<FixedAdaptSettingVO>>(){});
        return fixedAdaptSettingVOS.stream().filter(item -> Objects.isNull(item.getStopFlag()) || item.getStopFlag() == false).collect(Collectors.toList());
    }

    private List<FetchSettingEO> getFetchSettingListInFetchScheme(FetchSettingCond fetchSettingCond) {
        if (StringUtils.isEmpty((String)fetchSettingCond.getFetchSchemeId()) || StringUtils.isEmpty((String)fetchSettingCond.getFormSchemeId())) {
            return Collections.emptyList();
        }
        String formSchemeId = fetchSettingCond.getFormSchemeId();
        List formDefines = this.iRunTimeViewController.queryAllFormDefinesByFormScheme(formSchemeId);
        ArrayList<FetchSettingEO> fetchSettingEOS = new ArrayList<FetchSettingEO>();
        for (FormDefine formDefine : formDefines) {
            fetchSettingEOS.addAll(this.getFetchSettingListInForm(formSchemeId, fetchSettingCond.getFetchSchemeId(), formDefine.getKey()));
        }
        return fetchSettingEOS;
    }

    private List<FetchSettingEO> getFetchSettingListInForm(String formSchemeId, String fetchSchemeId, String formId) {
        if (StringUtils.isEmpty((String)fetchSchemeId)) {
            return Collections.emptyList();
        }
        if (StringUtils.isEmpty((String)formId)) {
            return Collections.emptyList();
        }
        String cacheKey = BDE_FETCH_SETTING_KEY.replace("{FETCHSCHEMEID}", fetchSchemeId).replace("{FORMID}", formId);
        return (List)this.bdeFetchSettingCache.get(cacheKey, () -> this.fetchSettingDao.listEnableFetchSettingByFormId(formSchemeId, fetchSchemeId, formId));
    }

    private boolean filterEnableFetchSettingByCond(FetchSettingCond fetchSettingCond, FetchSettingEO fetchSetting) {
        if (!StringUtils.isEmpty((String)fetchSettingCond.getFormSchemeId()) && !fetchSettingCond.getFormSchemeId().equals(fetchSetting.getFormSchemeId())) {
            return false;
        }
        if (!StringUtils.isEmpty((String)fetchSettingCond.getFormId()) && !fetchSettingCond.getFormId().equals(fetchSetting.getFormId())) {
            return false;
        }
        if (!StringUtils.isEmpty((String)fetchSettingCond.getRegionId()) && !fetchSettingCond.getRegionId().equals(fetchSetting.getRegionId())) {
            return false;
        }
        if (!StringUtils.isEmpty((String)fetchSettingCond.getDataLinkId()) && !fetchSettingCond.getDataLinkId().equals(fetchSetting.getDataLinkId())) {
            return false;
        }
        return 1 != fetchSetting.getStopFlag();
    }
}

