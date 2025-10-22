/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bde.common.constant.RequestSourceTypeEnum
 *  com.jiuqi.bde.common.dto.FixedAdaptSettingVO
 *  com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO
 *  com.jiuqi.bde.common.dto.FloatRegionConfigVO
 *  com.jiuqi.bde.common.dto.fetch.request.FetchRequestContextDTO
 *  com.jiuqi.bde.common.dto.fetch.request.FetchRequestDTO
 *  com.jiuqi.bde.common.dto.fetch.request.FetchRequestFixedSettingDTO
 *  com.jiuqi.bde.common.dto.fetch.request.FetchRequestFloatSettingDTO
 *  com.jiuqi.bde.common.dto.fetch.result.FetchResultDTO
 *  com.jiuqi.bde.common.exception.BdeRuntimeException
 *  com.jiuqi.bde.fetch.client.FetchDataRequestClient
 *  com.jiuqi.budget.component.domain.FormulaExeParam
 *  com.jiuqi.budget.component.service.FormulaExecService
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.base.common.utils.BeanConvertUtil
 *  com.jiuqi.gcreport.bde.common.vo.FetchSettingVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FixedAdaptSettingDTO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FixedFieldDefineSettingDTO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.enums.AdaptContextEnum
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchFloatSettingService
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSchemeNrService
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSettingService
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.utils.AdaptCondiUtils
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.utils.FetchTaskUtil
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.GcFormOperationInfo
 *  com.jiuqi.gcreport.efdcdatacheck.env.GcFetchDataEnvContext
 *  com.jiuqi.gcreport.efdcdatacheck.executor.GcFetchDataExecutor
 *  com.jiuqi.gcreport.efdcdatacheck.vo.GcFetchDataInfo
 *  com.jiuqi.gcreport.efdcdatacheck.vo.GcFetchDataResultInfo
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.common.TableKind
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.efdc.extract.impl.request.DataFetchEnv
 *  com.jiuqi.nr.efdc.extract.impl.request.ExpressionListing
 *  com.jiuqi.nr.efdc.extract.impl.request.FixExpression
 *  com.jiuqi.nr.efdc.extract.impl.response.FixExpResult
 *  com.jiuqi.nr.efdc.internal.pojo.QueryObjectImpl
 *  com.jiuqi.nr.efdc.service.IEFDCConfigService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.feign.client.OrgDataClient
 */
package com.jiuqi.gcreport.bde.fetch.impl.datacheck;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bde.common.constant.RequestSourceTypeEnum;
import com.jiuqi.bde.common.dto.FixedAdaptSettingVO;
import com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO;
import com.jiuqi.bde.common.dto.FloatRegionConfigVO;
import com.jiuqi.bde.common.dto.fetch.request.FetchRequestContextDTO;
import com.jiuqi.bde.common.dto.fetch.request.FetchRequestDTO;
import com.jiuqi.bde.common.dto.fetch.request.FetchRequestFixedSettingDTO;
import com.jiuqi.bde.common.dto.fetch.request.FetchRequestFloatSettingDTO;
import com.jiuqi.bde.common.dto.fetch.result.FetchResultDTO;
import com.jiuqi.bde.common.exception.BdeRuntimeException;
import com.jiuqi.bde.fetch.client.FetchDataRequestClient;
import com.jiuqi.budget.component.domain.FormulaExeParam;
import com.jiuqi.budget.component.service.FormulaExecService;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.common.utils.BeanConvertUtil;
import com.jiuqi.gcreport.bde.common.vo.FetchSettingVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FixedAdaptSettingDTO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FixedFieldDefineSettingDTO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.enums.AdaptContextEnum;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchFloatSettingService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSchemeNrService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSettingService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.utils.AdaptCondiUtils;
import com.jiuqi.gcreport.bde.fetchsetting.impl.utils.FetchTaskUtil;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.GcFormOperationInfo;
import com.jiuqi.gcreport.efdcdatacheck.env.GcFetchDataEnvContext;
import com.jiuqi.gcreport.efdcdatacheck.executor.GcFetchDataExecutor;
import com.jiuqi.gcreport.efdcdatacheck.vo.GcFetchDataInfo;
import com.jiuqi.gcreport.efdcdatacheck.vo.GcFetchDataResultInfo;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.common.TableKind;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.efdc.extract.impl.request.DataFetchEnv;
import com.jiuqi.nr.efdc.extract.impl.request.ExpressionListing;
import com.jiuqi.nr.efdc.extract.impl.request.FixExpression;
import com.jiuqi.nr.efdc.extract.impl.response.FixExpResult;
import com.jiuqi.nr.efdc.internal.pojo.QueryObjectImpl;
import com.jiuqi.nr.efdc.service.IEFDCConfigService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.OrgDataClient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcBDEFetchDataExecutorImpl
implements GcFetchDataExecutor {
    private static final Logger logger = LoggerFactory.getLogger(GcBDEFetchDataExecutorImpl.class);
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private FetchSettingService fetchSettingService;
    @Autowired
    private FetchFloatSettingService fetchFloatSettingService;
    @Autowired
    private FormulaExecService formulaExecService;
    @Autowired
    private FetchDataRequestClient fetchRequestClient;
    @Autowired
    private IJtableParamService entityService;
    @Autowired
    private IEFDCConfigService efdcConfigServiceImpl;
    @Autowired
    private OrgDataClient orgDataClient;
    @Autowired
    private FetchSchemeNrService fetchSchemeService;
    public static final int BDE_FORMULA_SCHEME_KEY_LENGTH = 16;
    private static final String BDE_MEMO_JOIN_SPLITTER = "";
    private static final String NOT_FOUND_REPORT = "gc.efdcDataCheck.notFoundReport";
    private static final String NOT_SETTING_FORMULA = "gc.efdcDataCheck.notSettingFormula";
    private static final String BBLX = "BBLX";
    private static final String DEFAULT_BBLX = "0";

    public GcFetchDataResultInfo execute(GcFetchDataEnvContext context) {
        ArrayList<FixExpResult> fixExpResultList = new ArrayList<FixExpResult>();
        FormulaSchemeDefine solutionFormulaScheme = context.getFormulaSchemeDefine();
        if (solutionFormulaScheme == null || StringUtils.isEmpty((String)solutionFormulaScheme.getKey())) {
            GcFetchDataResultInfo fetchDataResultInfo = new GcFetchDataResultInfo(fixExpResultList);
            return fetchDataResultInfo;
        }
        ExpressionListing cwFmlListing = context.getCwFmlListing();
        List<FixExpResult> fixExpResults = this.queryBdeValues(context.getFormOperationInfo(), cwFmlListing, solutionFormulaScheme.getKey());
        fixExpResultList.addAll(fixExpResults);
        return new GcFetchDataResultInfo(fixExpResultList);
    }

    public boolean isMatch(GcFetchDataEnvContext context) {
        FormulaSchemeDefine solutionFormulaScheme = this.getFormulaScheme(context.getFormOperationInfo());
        if (solutionFormulaScheme == null || StringUtils.isEmpty((String)solutionFormulaScheme.getKey())) {
            return false;
        }
        return solutionFormulaScheme.getKey().length() == 16;
    }

    public GcFetchDataInfo getFieldDefineList(GcFetchDataEnvContext context) {
        GcFetchDataInfo gcFetchDataInfoDTO = new GcFetchDataInfo();
        ArrayList<FieldDefine> retEfdcZbFieldDefines = new ArrayList<FieldDefine>();
        GcFormOperationInfo formOperationInfo = context.getFormOperationInfo();
        Map reportId2ZbGuidMap = context.getReportId2ZbGuidMap();
        Set errorFormKeySet = context.getErrorFormKeySet();
        String formSchemeId = formOperationInfo.getFormSchemeKey();
        String formId = formOperationInfo.getFormKey();
        FormDefine formDefine = this.runTimeViewController.queryFormById(formId);
        if (null == formDefine) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)NOT_FOUND_REPORT, (Object[])new Object[]{formId}));
        }
        boolean filterZb = !MapUtils.isEmpty(reportId2ZbGuidMap);
        HashSet filterZbGuids = null;
        if (filterZb) {
            filterZbGuids = (HashSet)reportId2ZbGuidMap.get(formDefine.getKey());
        }
        if (null == filterZbGuids) {
            filterZb = false;
            filterZbGuids = new HashSet();
        }
        FetchSettingCond fetchSettingCond = new FetchSettingCond();
        FormulaSchemeDefine formulaScheme = this.getFormulaScheme(context.getFormOperationInfo());
        Assert.isNotNull((Object)formulaScheme, (String)GcI18nUtil.getMessage((String)NOT_SETTING_FORMULA, (Object[])new Object[]{formDefine.getTitle()}), (Object[])new Object[0]);
        fetchSettingCond.setFetchSchemeId(formulaScheme.getKey());
        fetchSettingCond.setFormSchemeId(formSchemeId);
        fetchSettingCond.setFormId(formId);
        List fetchSettingVOS = this.fetchSettingService.listFetchSettingByFormId(fetchSettingCond);
        if (CollectionUtils.isEmpty((Collection)fetchSettingVOS)) {
            String msg = errorFormKeySet.contains(formDefine.getKey()) ? BDE_MEMO_JOIN_SPLITTER : GcI18nUtil.getMessage((String)NOT_SETTING_FORMULA, (Object[])new Object[]{formDefine.getTitle()});
            errorFormKeySet.add(formDefine.getKey());
            logger.error(msg);
        }
        HashMap<String, String> fieldDefineMemoMap = new HashMap<String, String>();
        for (FetchSettingVO fetchSettingVO : fetchSettingVOS) {
            String fieldDefineId = fetchSettingVO.getFieldDefineId();
            List fixedSettingData = fetchSettingVO.getFixedSettingData();
            List memoList = fixedSettingData.stream().map(FixedAdaptSettingVO::getMemo).collect(Collectors.toList());
            String memo = String.join((CharSequence)BDE_MEMO_JOIN_SPLITTER, memoList);
            fieldDefineMemoMap.put(fieldDefineId, memo);
        }
        ArrayList<FixExpression> fixFmls = new ArrayList<FixExpression>(128);
        try {
            FieldDefine fieldDefine = null;
            for (Map.Entry fieldDefineMemo : fieldDefineMemoMap.entrySet()) {
                fieldDefine = this.runTimeViewController.queryFieldDefine((String)fieldDefineMemo.getKey());
                if (filterZb && !filterZbGuids.contains(fieldDefine.getKey())) continue;
                FixExpression fml = new FixExpression();
                fml.setFlag(fieldDefine.getKey());
                fml.setPrecision(2);
                fml.setExpression((String)fieldDefineMemo.getValue());
                fixFmls.add(fml);
                retEfdcZbFieldDefines.add(fieldDefine);
            }
        }
        catch (Exception e) {
            throw new BdeRuntimeException((Throwable)e);
        }
        ExpressionListing cwFmlListing = new ExpressionListing();
        cwFmlListing.setFixExpressions(fixFmls);
        cwFmlListing.setFloatExpressions(new ArrayList());
        gcFetchDataInfoDTO.setRetEfdcZbFieldDefines(retEfdcZbFieldDefines);
        gcFetchDataInfoDTO.setExpressionListing(cwFmlListing);
        return gcFetchDataInfoDTO;
    }

    private FormulaSchemeDefine getFormulaScheme(GcFormOperationInfo formOperationInfo) {
        EntityViewData dwEntity = this.entityService.getDwEntity(formOperationInfo.getFormSchemeKey());
        JtableContext excutorJtableContext = new JtableContext((JtableContext)formOperationInfo);
        excutorJtableContext.setFormKey(BDE_MEMO_JOIN_SPLITTER);
        String unitKey = formOperationInfo.getDimensionValueSet().getValue(dwEntity.getDimensionName()).toString();
        List dimEntityList = this.entityService.getDimEntityList(formOperationInfo.getFormSchemeKey());
        HashMap<String, String> dimMap = new HashMap<String, String>();
        for (EntityViewData entityInfo : dimEntityList) {
            DimensionValue dimensionValue = (DimensionValue)formOperationInfo.getDimensionSet().get(entityInfo.getDimensionName());
            dimMap.put(entityInfo.getTableName(), dimensionValue.getValue());
        }
        QueryObjectImpl queryObjectImpl = new QueryObjectImpl(formOperationInfo.getTaskKey(), formOperationInfo.getFormSchemeKey(), unitKey);
        return this.efdcConfigServiceImpl.getSoluctionByDimensions(queryObjectImpl, dimMap, dwEntity.getKey());
    }

    /*
     * WARNING - void declaration
     */
    public List<FixExpResult> queryBdeValues(GcFormOperationInfo formOperationInfo, ExpressionListing cwFmlListing, String soluctionFormulaSchemeKey) {
        void var26_29;
        String formSchemeId = formOperationInfo.getFormSchemeKey();
        String formId = formOperationInfo.getFormKey();
        String regionId = "00000000-0000-0000-0000-000000000000";
        String requestSourceType = RequestSourceTypeEnum.DATACHECK_FETCH.getCode();
        String requestRunnerId = UUIDUtils.newUUIDStr();
        String requestInstcId = UUIDUtils.newHalfGUIDStr();
        String requestTaskId = UUIDUtils.newHalfGUIDStr();
        String taskId = formOperationInfo.getTaskKey().toString();
        String unitCode = BDE_MEMO_JOIN_SPLITTER;
        String periodScheme = BDE_MEMO_JOIN_SPLITTER;
        FetchSettingCond fetchSettingCond = new FetchSettingCond(soluctionFormulaSchemeKey, formSchemeId, formId, null);
        List listDataLinkFixedSettingRowRecords = this.fetchSettingService.listDataLinkFixedSettingRowRecords(fetchSettingCond);
        FloatRegionConfigVO fetchFloatSetting = this.fetchFloatSettingService.getFetchFloatSetting(fetchSettingCond);
        FetchRequestDTO fetchRequestDTO = new FetchRequestDTO(requestRunnerId, requestInstcId, requestTaskId, requestSourceType);
        FormDefine formDefine = this.runTimeViewController.queryFormById(formId);
        if (null == formDefine) {
            logger.info(GcI18nUtil.getMessage((String)NOT_FOUND_REPORT, (Object[])new Object[]{formId}));
            return CollectionUtils.newArrayList();
        }
        List entityList = this.entityService.getEntityList(formSchemeId);
        DimensionValueSet dimensionValueSet = formOperationInfo.getDimensionValueSet();
        if (null == dimensionValueSet) {
            dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)formOperationInfo.getDimensionSet());
        }
        periodScheme = String.valueOf(dimensionValueSet.getValue("DATATIME"));
        unitCode = this.getUnitId(entityList, dimensionValueSet);
        DataFetchEnv dataFetchEnv = this.initFetchEnv(periodScheme, taskId, formSchemeId, unitCode, formOperationInfo.getIncludeUncharged());
        String targetKey = BDE_MEMO_JOIN_SPLITTER;
        String periodCode = BDE_MEMO_JOIN_SPLITTER;
        for (Object entityInfo : entityList) {
            if (TableKind.TABLE_KIND_ENTITY.name().equals(entityInfo.getKind()) && StringUtils.isEmpty((String)targetKey) && dimensionValueSet.hasValue(entityInfo.getDimensionName())) {
                targetKey = dimensionValueSet.getValue(entityInfo.getDimensionName()).toString();
            }
            if (!TableKind.TABLE_KIND_ENTITY_PERIOD.name().equals(entityInfo.getKind()) || !StringUtils.isEmpty((String)periodCode) || !dimensionValueSet.hasValue(entityInfo.getDimensionName())) continue;
            periodCode = dimensionValueSet.getValue(entityInfo.getDimensionName()).toString();
        }
        HashMap dimMap = new HashMap();
        for (Map.Entry entry : formOperationInfo.getDimensionSet().entrySet()) {
            if (targetKey.equals(((DimensionValue)entry.getValue()).getValue()) || periodCode.equals(((DimensionValue)entry.getValue()).getValue()) || ((DimensionValue)entry.getValue()).getValue() == null) continue;
            dimMap.put(entry.getKey(), ((DimensionValue)entry.getValue()).getValue());
        }
        EntityViewData dwEntity = this.entityService.getDwEntity(formSchemeId);
        String string = dwEntity.getTableName();
        if (string != null) {
            String string2 = string.toUpperCase();
        }
        dimMap.put("ORGTYPE", var26_29);
        FetchRequestContextDTO fetchContext = new FetchRequestContextDTO();
        fetchContext.setBblx(dataFetchEnv.getBblx());
        fetchContext.setUnitCode(dataFetchEnv.getUnitCode());
        fetchContext.setUnitName(dataFetchEnv.getUnitName());
        fetchContext.setPeriodScheme(dataFetchEnv.getPeriodScheme());
        fetchContext.setStartDateStr(dataFetchEnv.getStartTime());
        fetchContext.setEndDateStr(dataFetchEnv.getEndTime());
        fetchContext.setIncludeUncharged(Boolean.valueOf(dataFetchEnv.isIncludeUncharged()));
        fetchContext.setIncludeAdjustVchr(this.fetchSchemeService.queryIncludeAdjustVoucherByFetchSchemeId(soluctionFormulaSchemeKey));
        fetchContext.setFormSchemeId(formSchemeId);
        fetchContext.setFetchSchemeId(soluctionFormulaSchemeKey);
        fetchContext.setFormId(formId);
        fetchContext.setRegionId(regionId);
        fetchContext.setTaskId(taskId);
        fetchContext.setOtherEntity(dimMap);
        fetchContext.setDimensionSetStr(JsonUtils.writeValueAsString((Object)formOperationInfo.getDimensionSet()));
        ArrayList<FetchRequestFixedSettingDTO> fixedSettingList = new ArrayList<FetchRequestFixedSettingDTO>();
        FetchRequestFixedSettingDTO fixedSetting = null;
        block2: for (FixedFieldDefineSettingDTO fieldDefineSettingVO : listDataLinkFixedSettingRowRecords) {
            fixedSetting = (FetchRequestFixedSettingDTO)BeanConvertUtil.convert((Object)fieldDefineSettingVO, FetchRequestFixedSettingDTO.class, (String[])new String[0]);
            FormulaExeParam formulaExeParam = null;
            for (FixedAdaptSettingDTO fixedAdaptSetting : fieldDefineSettingVO.getFixedSettingData()) {
                if (StringUtils.isEmpty((String)fixedAdaptSetting.getAdaptFormula()) || "#".equals(fixedAdaptSetting.getAdaptFormula())) {
                    fixedSetting.setLogicFormula(fixedAdaptSetting.getLogicFormula());
                    fixedSetting.setBizModelFormula((Map)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)fixedAdaptSetting.getBizModelFormula()), (TypeReference)new TypeReference<Map<String, List<FixedFetchSourceRowSettingVO>>>(){}));
                    fixedSettingList.add(fixedSetting);
                    continue block2;
                }
                formulaExeParam = new FormulaExeParam();
                String gcOrgType = FetchTaskUtil.getOrgTypeByTaskAndCtx((String)taskId);
                String adaptExpression = AdaptCondiUtils.expressionOrgTypeConvert((String)fixedAdaptSetting.getAdaptFormula(), (String)gcOrgType);
                formulaExeParam.setFormulaExpress(adaptExpression);
                HashMap<String, String> adaptContext = new HashMap<String, String>(8);
                adaptContext.put(AdaptContextEnum.MD_ORG.getKey(), unitCode);
                adaptContext.put(AdaptContextEnum.DATATIME.getKey(), periodScheme);
                adaptContext.put(gcOrgType, unitCode);
                formulaExeParam.setDimValMap(adaptContext);
                if (!this.formulaExecService.getAdaptVal(formulaExeParam)) continue;
                fixedSetting.setLogicFormula(fixedAdaptSetting.getLogicFormula());
                fixedSetting.setBizModelFormula((Map)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)fixedAdaptSetting.getBizModelFormula()), (TypeReference)new TypeReference<Map<String, List<FixedFetchSourceRowSettingVO>>>(){}));
                fixedSettingList.add(fixedSetting);
                continue block2;
            }
        }
        fetchRequestDTO.setFetchContext(fetchContext);
        if (fetchFloatSetting != null) {
            fetchRequestDTO.setFloatSetting(new FetchRequestFloatSettingDTO(fetchFloatSetting.getQueryType(), fetchFloatSetting.getQueryConfigInfo()));
        }
        fetchRequestDTO.setFixedSetting(fixedSettingList);
        BusinessResponseEntity fetchResponseEntity = this.fetchRequestClient.executeFetch(fetchRequestDTO);
        if (!fetchResponseEntity.isSuccess()) {
            logger.error(fetchResponseEntity.getErrorMessage());
            throw new BusinessRuntimeException(fetchResponseEntity.getErrorMessage());
        }
        Map fixedResults = ((FetchResultDTO)fetchResponseEntity.getData()).getFixedResults();
        ArrayList<FixExpResult> fixExpResults = new ArrayList<FixExpResult>();
        for (FixExpression fixExpression : cwFmlListing.getFixExpressions()) {
            FixExpResult fixExpResult = new FixExpResult();
            String bdeFetchResult = Objects.isNull(fixedResults) ? String.valueOf(0) : String.valueOf(fixedResults.get(fixExpression.getFlag()));
            List<String> valueList = Collections.singletonList("null".equals(bdeFetchResult) ? String.valueOf(0) : bdeFetchResult);
            fixExpResult.setValues(valueList);
            List<String> expressionList = Collections.singletonList(fixExpression.getExpression());
            fixExpResult.setExpression(expressionList);
            fixExpResults.add(fixExpResult);
        }
        return fixExpResults;
    }

    private String getUnitId(List<EntityViewData> entityList, DimensionValueSet dimensionValueSet) {
        String dimensionName = null;
        for (EntityViewData entityViewData : entityList) {
            if (!entityViewData.isMasterEntity()) continue;
            dimensionName = entityViewData.getDimensionName();
            break;
        }
        return (String)dimensionValueSet.getValue(dimensionName);
    }

    private DataFetchEnv initFetchEnv(String period, String taskId, String formSchemeKey, String unitId, Boolean includeUncharged) {
        OrgDO orgDO = this.queryOrgDO(unitId, formSchemeKey, period);
        DataFetchEnv env = new DataFetchEnv();
        env.setUnitCode(orgDO.getCode());
        String[] times = PeriodUtil.getTimesArr((String)period);
        env.setStartTime(times[0]);
        env.setEndTime(times[1]);
        env.setPeriodScheme(period);
        env.setTaskID(taskId);
        env.setStopOnSyntaxErr(true);
        env.setInstance(null);
        env.setBblx(this.getBblx(orgDO));
        if (includeUncharged != null) {
            env.setIncludUncharged(includeUncharged.booleanValue());
        }
        return env;
    }

    private String getBblx(OrgDO org) {
        if (!Objects.isNull(org.getValueOf(BBLX))) {
            return org.getValueOf(BBLX).toString();
        }
        return DEFAULT_BBLX;
    }

    private OrgDO queryOrgDO(String orgCode, String formSchemeKey, String formatPeriodStr) {
        Date period = PeriodUtils.getStartDateOfPeriod((String)formatPeriodStr, (boolean)false);
        OrgDTO orgParam = new OrgDTO();
        orgParam.setCode(orgCode);
        orgParam.setSyncOrgBaseInfo(Boolean.valueOf(false));
        orgParam.setVersionDate(period);
        orgParam.setRecoveryflag(Integer.valueOf(-1));
        orgParam.setExtInfo(new HashMap());
        orgParam.setStopflag(Integer.valueOf(-1));
        orgParam.setForceUpdateHistoryVersionData(Boolean.valueOf(true));
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        String orgType = formSchemeDefine.getDw();
        orgType = orgType.substring(0, orgType.indexOf("@"));
        orgParam.setCategoryname(orgType);
        orgParam.setAuthType(OrgDataOption.AuthType.NONE);
        orgParam.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
        return this.orgDataClient.get(orgParam);
    }
}

