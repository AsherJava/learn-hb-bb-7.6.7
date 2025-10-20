/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bde.common.constant.DataRegionTypeEnum
 *  com.jiuqi.bde.common.dto.FloatRegionConfigVO
 *  com.jiuqi.bde.common.dto.FloatZbMappingVO
 *  com.jiuqi.bde.common.dto.QueryConfigInfo
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.GcPeriodUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingEO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.executor.utils.FetchSettingNrUtil
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchFloatSettingService
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSettingService
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.utils.FetchTaskUtil
 *  com.jiuqi.gcreport.bde.penetrate.client.dto.GcFetchBatchPierceDTO
 *  com.jiuqi.gcreport.bde.penetrate.client.dto.GcFetchPierceDTO
 *  com.jiuqi.gcreport.bde.penetrate.client.vo.GcFetchPierceDataRegionParamVO
 *  com.jiuqi.gcreport.bde.penetrate.client.vo.GcFetchPierceParamVO
 *  com.jiuqi.gcreport.bde.penetrate.client.vo.NrLinkInfoVO
 *  com.jiuqi.gcreport.bde.penetrate.client.vo.QueryBblxParam
 *  com.jiuqi.gcreport.listedcompanyauthz.service.FListedCompanyAuthzService
 *  com.jiuqi.np.core.exception.BusinessException
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.datacrud.IDataValue
 *  com.jiuqi.nr.datacrud.IRegionDataSet
 *  com.jiuqi.nr.datacrud.IRowData
 *  com.jiuqi.nr.datacrud.QueryInfoBuilder
 *  com.jiuqi.nr.datacrud.api.IDataQueryService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataLinkService
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataRegionService
 *  com.jiuqi.nr.efdc.internal.pojo.QueryObjectImpl
 *  com.jiuqi.nr.efdc.service.IEFDCConfigService
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.base.RegionData
 *  com.jiuqi.nr.jtable.service.IJtableDataEngineService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DataCrudUtil
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nr.zbquery.util.PeriodUtil
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.feign.client.OrgDataClient
 */
package com.jiuqi.gcreport.bde.penetrate.impl.pierce.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bde.common.constant.DataRegionTypeEnum;
import com.jiuqi.bde.common.dto.FloatRegionConfigVO;
import com.jiuqi.bde.common.dto.FloatZbMappingVO;
import com.jiuqi.bde.common.dto.QueryConfigInfo;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.GcPeriodUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingEO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.utils.FetchSettingNrUtil;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchFloatSettingService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSettingService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.utils.FetchTaskUtil;
import com.jiuqi.gcreport.bde.penetrate.client.dto.GcFetchBatchPierceDTO;
import com.jiuqi.gcreport.bde.penetrate.client.dto.GcFetchPierceDTO;
import com.jiuqi.gcreport.bde.penetrate.client.vo.GcFetchPierceDataRegionParamVO;
import com.jiuqi.gcreport.bde.penetrate.client.vo.GcFetchPierceParamVO;
import com.jiuqi.gcreport.bde.penetrate.client.vo.NrLinkInfoVO;
import com.jiuqi.gcreport.bde.penetrate.client.vo.QueryBblxParam;
import com.jiuqi.gcreport.bde.penetrate.impl.intf.IBeforePenetrateDataEnable;
import com.jiuqi.gcreport.bde.penetrate.impl.intf.IBeforePenetrateEnableGather;
import com.jiuqi.gcreport.bde.penetrate.impl.pierce.service.GcEfdcFetchPierceService;
import com.jiuqi.gcreport.bde.penetrate.impl.pierce.service.GcFetchPierceService;
import com.jiuqi.gcreport.bde.penetrate.impl.pierce.service.IMergeUnitPenetrateEnable;
import com.jiuqi.gcreport.bde.penetrate.impl.pierce.util.UUIDValidator;
import com.jiuqi.gcreport.listedcompanyauthz.service.FListedCompanyAuthzService;
import com.jiuqi.np.core.exception.BusinessException;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.datacrud.IDataValue;
import com.jiuqi.nr.datacrud.IRegionDataSet;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.datacrud.QueryInfoBuilder;
import com.jiuqi.nr.datacrud.api.IDataQueryService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataLinkService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataRegionService;
import com.jiuqi.nr.efdc.internal.pojo.QueryObjectImpl;
import com.jiuqi.nr.efdc.service.IEFDCConfigService;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.service.IJtableDataEngineService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DataCrudUtil;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.zbquery.util.PeriodUtil;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.OrgDataClient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class GcFetchPierceServiceImpl
implements GcFetchPierceService {
    private static final Logger log = LoggerFactory.getLogger(GcFetchPierceServiceImpl.class);
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private RunTimeAuthViewController authViewController;
    @Autowired
    private OrgDataClient orgDataClient;
    @Autowired
    private IEFDCConfigService efdcConfigService;
    @Autowired
    private IRuntimeDataLinkService iRuntimeDataLinkService;
    @Autowired
    private IDataQueryService dataQueryService;
    @Autowired
    private FetchSettingService settingService;
    @Autowired
    private FetchFloatSettingService floatSettingService;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private GcEfdcFetchPierceService efdcFetchPierceService;
    @Autowired
    private IMergeUnitPenetrateEnable mergeUnitPenetrateEnable;
    @Autowired
    private IRuntimeDataRegionService iRuntimeDataRegionService;
    @Autowired
    private FListedCompanyAuthzService fListedCompanyAuthzService;
    @Autowired
    private IJtableDataEngineService jtableDataEngineService;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IBeforePenetrateEnableGather penetrateBeforeEnableGather;
    private static final String RECORDKEY = "RECORDKEY";

    @Override
    public GcFetchPierceParamVO queryPierceParams(GcFetchPierceDTO efdcRequestInfo) {
        Assert.isNotNull((Object)efdcRequestInfo);
        Assert.isNotEmpty((Collection)efdcRequestInfo.getLinkKeys());
        Assert.isNotEmpty((Map)efdcRequestInfo.getDimensionSet());
        String formSchemeKey = efdcRequestInfo.getFormSchemeKey();
        Map dimensionMap = efdcRequestInfo.getDimensionSet();
        String orgCode = (String)dimensionMap.get("MD_ORG");
        Map<String, String> dimMap = dimensionMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> Objects.isNull(e.getValue()) ? "" : (String)e.getValue()));
        String datatime = dimMap.get("DATATIME");
        String gcOrgType = dimMap.get("MD_GCORGTYPE");
        FormSchemeDefine formSchemeDefine = this.iRunTimeViewController.getFormScheme(formSchemeKey);
        String contextEntityId = FetchTaskUtil.getEntityIdByTaskAndCtx((String)formSchemeDefine.getTaskKey());
        String periodStr = PeriodUtil.toNrPeriod((String)datatime, (PeriodType)formSchemeDefine.getPeriodType());
        dimMap.put("DATATIME", periodStr);
        boolean isCanPenetrate = this.fListedCompanyAuthzService.checkPenetratePermission(orgCode, gcOrgType);
        if (!isCanPenetrate) {
            OrgDTO orgParam = new OrgDTO();
            orgParam.setCode(orgCode);
            Date period = PeriodUtils.getStartDateOfPeriod((String)periodStr, (boolean)false);
            orgParam.setVersionDate(period);
            orgParam.setCategoryname(gcOrgType);
            orgParam.setAuthType(OrgDataOption.AuthType.NONE);
            orgParam.setRecoveryflag(Integer.valueOf(-1));
            orgParam.setStopflag(Integer.valueOf(-1));
            OrgDO org = this.orgDataClient.get(orgParam);
            throw new BusinessRuntimeException(String.format("\u3010%1$s\u3011%2$s\u4e3a\u4e0a\u5e02\u516c\u53f8\uff0c\u8bf7\u8054\u7cfb\u5f53\u524d\u4e0a\u5e02\u516c\u53f8\u7ba1\u7406\u5458\u6388\u6743\u540e\u518d\u6267\u884c\u64cd\u4f5c", orgCode, org.getName()));
        }
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(formSchemeDefine.getTaskKey());
        String taskName = taskDefine.getTitle();
        PeriodWrapper periodWrapper = new PeriodWrapper(periodStr);
        String formatPeriodStr = periodWrapper.toString();
        QueryObjectImpl queryObject = new QueryObjectImpl();
        queryObject.setTaskKey(formSchemeDefine.getTaskKey());
        queryObject.setFormSchemeKey(formSchemeKey);
        queryObject.setMainDim(orgCode);
        FormulaSchemeDefine formulaSchemeDefine = this.efdcConfigService.getSoluctionByDimensions(queryObject, dimMap, formSchemeDefine.getDw());
        DataLinkDefine dataLink = this.iRuntimeDataLinkService.queryDataLink((String)efdcRequestInfo.getLinkKeys().get(0));
        if (dataLink == null) {
            throw new BusinessException(String.format("\u6839\u636e\u94fe\u63a5\u6807\u8bc6\u3010%1$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u914d\u7f6e", efdcRequestInfo.getLinkKeys().get(0)));
        }
        String regionKey = dataLink.getRegionKey();
        DataRegionDefine dataRegionDefine = this.iRunTimeViewController.queryDataRegionDefine(regionKey);
        boolean isFixArea = DataRegionKind.DATA_REGION_SIMPLE.equals((Object)dataRegionDefine.getRegionKind());
        String formKey = dataRegionDefine.getFormKey();
        GcFetchPierceParamVO pierceParamVO = new GcFetchPierceParamVO();
        if (Objects.isNull(formulaSchemeDefine) || StringUtils.isEmpty((String)formulaSchemeDefine.getKey())) {
            pierceParamVO.setProdLine(null);
            return pierceParamVO;
        }
        if (formulaSchemeDefine.getKey().length() == 16) {
            pierceParamVO.setProdLine("BDE");
        } else {
            pierceParamVO.setProdLine("EFDC");
        }
        pierceParamVO.setFormPeriod(dimMap.get("DATATIME"));
        pierceParamVO.setFetchSchemeId(formulaSchemeDefine.getKey());
        pierceParamVO.setTaskId(formSchemeDefine.getTaskKey());
        pierceParamVO.setTaskTitle(taskName);
        pierceParamVO.setBblx(this.queryBblx(orgCode, formatPeriodStr, contextEntityId));
        String orgType = contextEntityId.substring(0, contextEntityId.indexOf("@"));
        pierceParamVO.setOrgType(orgType);
        pierceParamVO.setOrgId(orgCode);
        pierceParamVO.setPeriodStr(formatPeriodStr);
        pierceParamVO.setPeriodType(periodWrapper.getType());
        pierceParamVO.setAcctYear(periodWrapper.getYear());
        pierceParamVO.setAcctPeriod(periodWrapper.getPeriod());
        pierceParamVO.setCurrency(dimensionMap.get("MD_CURRENCY") == null ? null : (String)dimensionMap.get("MD_CURRENCY"));
        pierceParamVO.setRegionId(regionKey);
        pierceParamVO.setFormId(formKey);
        if (isFixArea) {
            pierceParamVO.setLinkInfos(this.queryLinkInfo(efdcRequestInfo));
        } else {
            List<IRowData> iRowData = this.buildRowDataList(dimensionMap, regionKey);
            List<Map<String, Object>> rowDataZbId2valueMapList = this.buildRowDataZbId2valueMapList(dimensionMap, iRowData, pierceParamVO.getTaskId(), formSchemeKey, pierceParamVO.getRegionId(), pierceParamVO.getFormId());
            pierceParamVO.setRowDataZbId2valueMapList(rowDataZbId2valueMapList);
            FetchSettingCond fetchSettingCond = new FetchSettingCond(formulaSchemeDefine.getKey(), formSchemeKey, formKey, regionKey);
            FloatRegionConfigVO floatRegionConfigVO = this.floatSettingService.listFetchFloatSettingByRegionId(fetchSettingCond);
            pierceParamVO.setFloatSetting(this.reSortZbMapping(floatRegionConfigVO, pierceParamVO.getRowDataZbId2valueMapList()));
            String selectedId = this.buildFloatSelectedId(floatRegionConfigVO, efdcRequestInfo.getLinkKeys());
            if (!StringUtils.isEmpty((String)selectedId)) {
                pierceParamVO.setSelectedId(selectedId);
            }
        }
        return pierceParamVO;
    }

    @Override
    public Boolean enable(GcFetchPierceDTO efdcRequestInfo) {
        log.debug("BDE\u7a7f\u900f enable\u63a5\u53e3\u63a5\u6536\u5230\u7684\u8bf7\u6c42\u53c2\u6570\u3010{}\u3011", (Object)JsonUtils.writeValueAsString((Object)efdcRequestInfo));
        if (!this.beforeEnable(efdcRequestInfo)) {
            return false;
        }
        Assert.isNotNull((Object)efdcRequestInfo);
        Assert.isNotEmpty((Collection)efdcRequestInfo.getLinkKeys());
        Assert.isNotEmpty((Map)efdcRequestInfo.getDimensionSet());
        Assert.isNotEmpty((String)efdcRequestInfo.getFormSchemeKey());
        String proLine = "";
        String formSchemeKey = efdcRequestInfo.getFormSchemeKey();
        FormSchemeDefine formSchemeDefine = this.iRunTimeViewController.getFormScheme(formSchemeKey);
        String contextEntityId = FetchTaskUtil.getEntityIdByTaskAndCtx((String)formSchemeDefine.getTaskKey());
        Map dimensionSet = efdcRequestInfo.getDimensionSet();
        String mainDim = (String)dimensionSet.get("MD_ORG");
        Map<String, String> dimMap = dimensionSet.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> Objects.isNull(e.getValue()) ? "" : (String)e.getValue()));
        String datatime = dimMap.get("DATATIME");
        String periodStr = PeriodUtil.toNrPeriod((String)datatime, (PeriodType)formSchemeDefine.getPeriodType());
        dimMap.put("DATATIME", periodStr);
        QueryObjectImpl queryObject = new QueryObjectImpl();
        queryObject.setTaskKey(formSchemeDefine.getTaskKey());
        queryObject.setFormSchemeKey(formSchemeKey);
        queryObject.setMainDim(mainDim);
        FormulaSchemeDefine formulaSchemeDefine = this.efdcConfigService.getSoluctionByDimensions(queryObject, dimMap, formSchemeDefine.getDw());
        if (Objects.isNull(formulaSchemeDefine) || StringUtils.isEmpty((String)formulaSchemeDefine.getKey())) {
            log.info("\u3010\u7a7f\u900f\u67e5\u8be2enable\u3011 \u53d6\u6570\u516c\u5f0f\u65b9\u6848\u4e3a\u7a7a\uff0c\u4e0d\u652f\u6301\u7a7f\u900f");
            return false;
        }
        proLine = formulaSchemeDefine.getKey().length() == 16 ? "BDE" : "EFDC";
        String orgCode = (String)dimensionSet.get("MD_ORG");
        PeriodWrapper periodWrapper = new PeriodWrapper(periodStr);
        String formatPeriodStr = periodWrapper.toString();
        String orgBblx = this.queryBblx(orgCode, formatPeriodStr, contextEntityId);
        if ("BDE".equals(proLine)) {
            Boolean gcUnitPenetrateEnable;
            boolean canPenetrate = true;
            boolean hasFormula = true;
            if ("9".equals(orgBblx) && (Objects.isNull(gcUnitPenetrateEnable = this.mergeUnitPenetrateEnable.enable()) || !gcUnitPenetrateEnable.booleanValue())) {
                canPenetrate = false;
            }
            for (String linkKey : efdcRequestInfo.getLinkKeys()) {
                if (!this.judgeBdeEmptySetting(linkKey, formulaSchemeDefine.getKey(), formSchemeKey)) continue;
                hasFormula = false;
            }
            log.debug(String.format("\u3010\u7a7f\u900f\u67e5\u8be2enable\u3011\u6307\u6807\u53c2\u6570\uff1a%1$s; enable\u6761\u4ef6\uff1a\u4ea7\u54c1\u7ebf=%2$s\uff0c\u62a5\u8868\u7c7b\u578b=%3$s\uff0c\u662f\u5426\u6709\u53d6\u6570\u8bbe\u7f6e=%4$s\uff0c\u662f\u5426\u5141\u8bb8\u7a7f\u900f=%5$s", JsonUtils.writeValueAsString((Object)efdcRequestInfo), proLine, orgBblx, hasFormula, canPenetrate));
            if (canPenetrate && hasFormula) {
                return true;
            }
        } else if ("EFDC".equals(proLine)) {
            boolean canPenetrate = "1".equals(orgBblx);
            boolean hasFormula = this.canEfdcFormulaPenetrate(efdcRequestInfo, formulaSchemeDefine.getKey());
            log.debug(String.format("\u3010\u7a7f\u900f\u67e5\u8be2enable\u3011\u6307\u6807\u53c2\u6570\uff1a%1$s; enable\u6761\u4ef6\uff1a\u4ea7\u54c1\u7ebf=%2$s\uff0c\u62a5\u8868\u7c7b\u578b=%3$s\uff0c\u662f\u5426\u6709\u53d6\u6570\u8bbe\u7f6e=%4$s\uff0c\u662f\u5426\u5141\u8bb8\u7a7f\u900f=%5$s", JsonUtils.writeValueAsString((Object)efdcRequestInfo), proLine, orgBblx, hasFormula, canPenetrate));
            if (canPenetrate && hasFormula) {
                return true;
            }
        }
        return false;
    }

    private boolean canEfdcFormulaPenetrate(GcFetchPierceDTO efdcRequestInfo, String formulaSchemeKey) {
        boolean efdcFormulaIsNotEmpty = this.efdcFetchPierceService.judgeEfdcFormulaIsNotEmpty(efdcRequestInfo);
        if (!efdcFormulaIsNotEmpty) {
            return false;
        }
        String efdcLinkKey = (String)efdcRequestInfo.getLinkKeys().get(0);
        DataLinkDefine dataLink = this.iRuntimeDataLinkService.queryDataLink(efdcLinkKey);
        if (dataLink == null) {
            throw new BusinessException(String.format("\u6839\u636e\u94fe\u63a5\u6807\u8bc6\u3010%1$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u914d\u7f6e", efdcLinkKey));
        }
        DataRegionDefine regionDefine = this.authViewController.queryDataRegionDefine(dataLink.getRegionKey());
        if (regionDefine == null) {
            throw new BusinessException(String.format("\u6839\u636e\u533a\u57df\u6807\u8bc6\u3010%1$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u914d\u7f6e", dataLink.getRegionKey()));
        }
        List allFormulasDefines = this.formulaRunTimeController.getAllFormulasInForm(formulaSchemeKey, regionDefine.getFormKey());
        String dataLoc = String.format("[%1$d,%2$d]", dataLink.getRowNum(), dataLink.getColNum());
        String location = "";
        for (FormulaDefine formulaDefine : allFormulasDefines) {
            if (StringUtils.isEmpty((String)formulaDefine.getExpression()) || !formulaDefine.getExpression().contains("=") || !dataLoc.equals(location = formulaDefine.getExpression().substring(0, formulaDefine.getExpression().indexOf("=")))) continue;
            return true;
        }
        return false;
    }

    private boolean beforeEnable(GcFetchPierceDTO efdcRequestInfo) {
        boolean beforeEnableFlag = true;
        for (IBeforePenetrateDataEnable item : this.penetrateBeforeEnableGather.getBeforeEnablerList()) {
            boolean beforeEnable = item.beforeEnable(efdcRequestInfo);
            if (beforeEnable) continue;
            beforeEnableFlag = false;
        }
        return beforeEnableFlag;
    }

    @Override
    public Map<String, Map<String, Boolean>> queryBatchPierceParams(GcFetchBatchPierceDTO batchPierceDTO) {
        log.debug("BDE\u7a7f\u900f \u6279\u91cfenable\u63a5\u53e3\u63a5\u6536\u5230\u7684\u8bf7\u6c42\u53c2\u6570\u3010{}\u3011", (Object)JsonUtils.writeValueAsString((Object)batchPierceDTO));
        Assert.isNotNull((Object)batchPierceDTO);
        Assert.isNotEmpty((Collection)batchPierceDTO.getLinkKeys());
        Assert.isNotEmpty((Collection)batchPierceDTO.getDimensionSets());
        String proLine = "";
        String formSchemeKey = batchPierceDTO.getFormSchemeKey();
        FormSchemeDefine formSchemeDefine = this.iRunTimeViewController.getFormScheme(formSchemeKey);
        String contextEntityId = FetchTaskUtil.getEntityIdByTaskAndCtx((String)formSchemeDefine.getTaskKey());
        HashMap<String, Map<String, Boolean>> batchOrgEnableParam = new HashMap<String, Map<String, Boolean>>();
        for (Map dimensionSet : batchPierceDTO.getDimensionSets()) {
            HashMap<String, Boolean> dataLinkEnableParamMap = new HashMap<String, Boolean>();
            String mainDim = (String)dimensionSet.get("MD_ORG");
            Map<String, String> dimMap = dimensionSet.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> Objects.isNull(e.getValue()) ? "" : (String)e.getValue()));
            String dimId = JsonUtils.writeValueAsString((Object)dimensionSet);
            String datatime = dimMap.get("DATATIME");
            String periodStr = PeriodUtil.toNrPeriod((String)datatime, (PeriodType)formSchemeDefine.getPeriodType());
            dimMap.put("DATATIME", periodStr);
            if (!this.beforeEnable(new GcFetchPierceDTO(batchPierceDTO.getLinkKeys(), batchPierceDTO.getFormSchemeKey(), dimensionSet, batchPierceDTO.getContextEntityId(), batchPierceDTO.getContextFilterExpression()))) {
                for (String dataLinkId : batchPierceDTO.getLinkKeys()) {
                    log.info("\u3010\u7a7f\u900f\u67e5\u8be2enable\u3011\u6307\u6807\u53c2\u6570\uff1a{}; \u672a\u914d\u7f6e\u53d6\u6570\u65b9\u6848\u4e0d\u5141\u8bb8\u7a7f\u900f", (Object)JsonUtils.writeValueAsString((Object)dimensionSet));
                    dataLinkEnableParamMap.put(dataLinkId, false);
                }
                batchOrgEnableParam.put(dimId, dataLinkEnableParamMap);
                continue;
            }
            QueryObjectImpl queryObject = new QueryObjectImpl();
            queryObject.setTaskKey(formSchemeDefine.getTaskKey());
            queryObject.setFormSchemeKey(formSchemeKey);
            queryObject.setMainDim(mainDim);
            FormulaSchemeDefine formulaSchemeDefine = this.efdcConfigService.getSoluctionByDimensions(queryObject, dimMap, formSchemeDefine.getDw());
            if (Objects.isNull(formulaSchemeDefine) || StringUtils.isEmpty((String)formulaSchemeDefine.getKey())) {
                for (String dataLinkId : batchPierceDTO.getLinkKeys()) {
                    log.info("\u3010\u7a7f\u900f\u67e5\u8be2enable\u3011\u6307\u6807\u53c2\u6570\uff1a{}; \u672a\u914d\u7f6e\u53d6\u6570\u65b9\u6848\u4e0d\u5141\u8bb8\u7a7f\u900f", (Object)JsonUtils.writeValueAsString((Object)dimensionSet));
                    dataLinkEnableParamMap.put(dataLinkId, false);
                }
                batchOrgEnableParam.put(dimId, dataLinkEnableParamMap);
                continue;
            }
            proLine = formulaSchemeDefine.getKey().length() == 16 ? "BDE" : "EFDC";
            PeriodWrapper periodWrapper = new PeriodWrapper(periodStr);
            String formatPeriodStr = periodWrapper.toString();
            String orgCode = (String)dimensionSet.get("MD_ORG");
            String orgBblx = this.queryBblx(orgCode, formatPeriodStr, contextEntityId);
            Map<String, List<DataLinkDefine>> orgRegionDataLinkMap = this.groupDataLinkByRegion(batchPierceDTO.getLinkKeys());
            for (String regionKey : orgRegionDataLinkMap.keySet()) {
                DataRegionDefine dataRegionDefine = this.iRuntimeDataRegionService.queryDataRegion(regionKey);
                if (dataRegionDefine == null) {
                    throw new BusinessException(String.format("\u6839\u636e\u533a\u57df\u6807\u8bc6\u3010%1$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u914d\u7f6e", regionKey));
                }
                String formKey = dataRegionDefine.getFormKey();
                if ("BDE".equals(proLine)) {
                    FetchSettingCond fetchSettingCond = new FetchSettingCond(formulaSchemeDefine.getKey(), formSchemeKey, formKey, regionKey);
                    DataRegionKind regionKind = dataRegionDefine.getRegionKind();
                    dataLinkEnableParamMap.putAll(this.bdeRegionEnable(fetchSettingCond, orgRegionDataLinkMap, proLine, orgBblx, regionKind));
                    continue;
                }
                if (!"EFDC".equals(proLine)) continue;
                dataLinkEnableParamMap.putAll(this.efdcRegionEnable(orgRegionDataLinkMap, proLine, orgBblx, formulaSchemeDefine.getKey(), formKey, regionKey));
            }
            batchOrgEnableParam.put(dimId, dataLinkEnableParamMap);
        }
        return batchOrgEnableParam;
    }

    private Map<String, List<DataLinkDefine>> groupDataLinkByRegion(List<String> dataLinkIds) {
        HashMap<String, List<DataLinkDefine>> orgRegionDataLinkMap = new HashMap<String, List<DataLinkDefine>>();
        for (String linkKey : dataLinkIds) {
            DataLinkDefine dataLinkDefine = this.iRuntimeDataLinkService.queryDataLink(linkKey);
            if (dataLinkDefine == null) {
                throw new BusinessException(String.format("\u6839\u636e\u94fe\u63a5\u6807\u8bc6\u3010%1$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u914d\u7f6e", linkKey));
            }
            String regionKey = dataLinkDefine.getRegionKey();
            if (orgRegionDataLinkMap.containsKey(regionKey)) {
                ((List)orgRegionDataLinkMap.get(regionKey)).add(dataLinkDefine);
                continue;
            }
            ArrayList<DataLinkDefine> dataLinkDefines = new ArrayList<DataLinkDefine>();
            dataLinkDefines.add(dataLinkDefine);
            orgRegionDataLinkMap.put(regionKey, dataLinkDefines);
        }
        return orgRegionDataLinkMap;
    }

    private Map<String, Boolean> bdeRegionEnable(FetchSettingCond fetchSettingCond, Map<String, List<DataLinkDefine>> orgRegionDataLinkMap, String proLine, String orgBblx, DataRegionKind regionKind) {
        HashMap<String, Boolean> dataLinkEnableParamMap = new HashMap<String, Boolean>();
        List fetchSettingList = this.settingService.getFetchSettingListByCond(fetchSettingCond);
        List fetchDataLinks = fetchSettingList.stream().map(FetchSettingEO::getDataLinkId).collect(Collectors.toList());
        if (DataRegionKind.DATA_REGION_SIMPLE.equals((Object)regionKind)) {
            for (DataLinkDefine dataLinkDefine : orgRegionDataLinkMap.get(fetchSettingCond.getRegionId())) {
                Boolean gcUnitPenetrateEnable;
                boolean canPenetrate = true;
                boolean hasFormula = false;
                if (fetchDataLinks.contains(dataLinkDefine.getKey())) {
                    hasFormula = true;
                }
                if ("9".equals(orgBblx) && (Objects.isNull(gcUnitPenetrateEnable = this.mergeUnitPenetrateEnable.enable()) || !gcUnitPenetrateEnable.booleanValue())) {
                    canPenetrate = false;
                }
                log.debug(String.format("\u3010\u7a7f\u900f\u67e5\u8be2enable\u3011\uff1a\u4ea7\u54c1\u7ebf=%1$s\uff0c\u62a5\u8868\u7c7b\u578b=%2$s\uff0c\u662f\u5426\u6709\u53d6\u6570\u8bbe\u7f6e=%3$s\uff0c\u662f\u5426\u5141\u8bb8\u7a7f\u900f=%4$s", proLine, orgBblx, hasFormula, canPenetrate));
                if (!canPenetrate || !hasFormula) continue;
                dataLinkEnableParamMap.put(dataLinkDefine.getKey(), true);
            }
        } else {
            FloatRegionConfigVO fetchFloatSetting = this.floatSettingService.getFetchFloatSetting(fetchSettingCond);
            Map dataLinkIdQyertFieldMap = FetchSettingNrUtil.getDataLinkIdQyertFieldMap((FloatRegionConfigVO)fetchFloatSetting);
            for (DataLinkDefine dataLinkDefine : orgRegionDataLinkMap.get(fetchSettingCond.getRegionId())) {
                Boolean gcUnitPenetrateEnable;
                boolean canPenetrate = true;
                boolean hasFormula = false;
                if (!StringUtils.isEmpty((String)((String)dataLinkIdQyertFieldMap.get(dataLinkDefine.getKey()))) || fetchDataLinks.contains(dataLinkDefine.getKey())) {
                    hasFormula = true;
                }
                if ("9".equals(orgBblx) && (Objects.isNull(gcUnitPenetrateEnable = this.mergeUnitPenetrateEnable.enable()) || !gcUnitPenetrateEnable.booleanValue())) {
                    canPenetrate = false;
                }
                log.debug(String.format("\u3010\u7a7f\u900f\u67e5\u8be2enable\u3011\u6761\u4ef6\uff1a\u4ea7\u54c1\u7ebf=%1$s\uff0c\u62a5\u8868\u7c7b\u578b=%2$s\uff0c\u662f\u5426\u6709\u53d6\u6570\u8bbe\u7f6e=%3$s\uff0c\u662f\u5426\u5141\u8bb8\u7a7f\u900f=%4$s", proLine, orgBblx, hasFormula, canPenetrate));
                if (canPenetrate && hasFormula) {
                    dataLinkEnableParamMap.put(dataLinkDefine.getKey(), true);
                    continue;
                }
                dataLinkEnableParamMap.put(dataLinkDefine.getKey(), false);
            }
        }
        return dataLinkEnableParamMap;
    }

    private Map<String, Boolean> efdcRegionEnable(Map<String, List<DataLinkDefine>> orgRegionDataLinkMap, String proLine, String orgBblx, String formulaSchemeKey, String formKey, String regionKey) {
        HashMap<String, Boolean> dataLinkEnableParamMap = new HashMap<String, Boolean>();
        List allFormulasDefines = this.formulaRunTimeController.getAllFormulasInForm(formulaSchemeKey, formKey);
        List allFormulasLocation = allFormulasDefines.stream().filter(formulaDefine -> !StringUtils.isEmpty((String)formulaDefine.getExpression()) && formulaDefine.getExpression().contains("=")).map(formulaDefine -> formulaDefine.getExpression().substring(0, formulaDefine.getExpression().indexOf("="))).collect(Collectors.toList());
        for (DataLinkDefine dataLinkDefine : orgRegionDataLinkMap.get(regionKey)) {
            String dataLoc = String.format("[%1$d,%2$d]", dataLinkDefine.getRowNum(), dataLinkDefine.getColNum());
            boolean canPenetrate = "1".equals(orgBblx);
            boolean hasFormula = allFormulasLocation.contains(dataLoc);
            log.debug(String.format("\u3010\u7a7f\u900f\u67e5\u8be2enable\u3011\u6761\u4ef6\uff1a\u4ea7\u54c1\u7ebf=%1$s\uff0c\u62a5\u8868\u7c7b\u578b=%2$s\uff0c\u662f\u5426\u6709\u53d6\u6570\u8bbe\u7f6e=%3$s\uff0c\u662f\u5426\u5141\u8bb8\u7a7f\u900f=%4$s", proLine, orgBblx, hasFormula, canPenetrate));
            if (canPenetrate && hasFormula) {
                dataLinkEnableParamMap.put(dataLinkDefine.getKey(), true);
                continue;
            }
            dataLinkEnableParamMap.put(dataLinkDefine.getKey(), false);
        }
        return dataLinkEnableParamMap;
    }

    private boolean judgeBdeEmptySetting(String linkKey, String formulaSchemeKey, String formSchemeKey) {
        DataLinkDefine dataLink = this.iRuntimeDataLinkService.queryDataLink(linkKey);
        if (dataLink == null) {
            throw new BusinessException(String.format("\u6839\u636e\u94fe\u63a5\u6807\u8bc6\u3010%1$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u914d\u7f6e", linkKey));
        }
        String regionKey = dataLink.getRegionKey();
        DataRegionDefine dataRegionDefine = this.iRunTimeViewController.queryDataRegionDefine(regionKey);
        boolean isFixArea = DataRegionKind.DATA_REGION_SIMPLE.equals((Object)dataRegionDefine.getRegionKind());
        FetchSettingCond condi = new FetchSettingCond();
        condi.setFetchSchemeId(formulaSchemeKey);
        condi.setFormSchemeId(formSchemeKey);
        condi.setRegionId(dataLink.getRegionKey());
        condi.setDataLinkId(linkKey);
        if (isFixArea) {
            List settings = this.settingService.listDataLinkFixedSettingRowRecords(condi);
            return CollectionUtils.isEmpty((Collection)settings);
        }
        FloatRegionConfigVO fetchFloatSetting = this.floatSettingService.getFetchFloatSetting(condi);
        return ObjectUtils.isEmpty(fetchFloatSetting);
    }

    private List<NrLinkInfoVO> queryLinkInfo(GcFetchPierceDTO efdcRequestInfo) {
        Map dimensionMap = efdcRequestInfo.getDimensionSet();
        ArrayList<NrLinkInfoVO> linkInfoVOList = new ArrayList<NrLinkInfoVO>();
        List linkKeys = efdcRequestInfo.getLinkKeys();
        DataLinkDefine dataLink = this.iRuntimeDataLinkService.queryDataLink((String)linkKeys.get(0));
        if (dataLink == null) {
            throw new BusinessException(String.format("\u6839\u636e\u94fe\u63a5\u6807\u8bc6\u3010%1$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u914d\u7f6e", linkKeys.get(0)));
        }
        for (String linkKey : linkKeys) {
            linkInfoVOList.add(this.querySingleLinkInfo(dimensionMap, linkKey));
        }
        return linkInfoVOList;
    }

    private NrLinkInfoVO querySingleLinkInfo(Map<String, String> dimensionMap, String linkKey) {
        FieldDefine fieldDefine;
        DataLinkDefine dataLink = this.iRuntimeDataLinkService.queryDataLink(linkKey);
        if (dataLink == null) {
            throw new BusinessException(String.format("\u6839\u636e\u94fe\u63a5\u6807\u8bc6\u3010%1$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u914d\u7f6e", linkKey));
        }
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        for (Map.Entry<String, String> dimEntry : dimensionMap.entrySet()) {
            if (dimEntry.getKey().equals(RECORDKEY)) continue;
            dimensionValueSet.setValue(dimEntry.getKey(), (Object)dimEntry.getValue());
        }
        DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(dimensionValueSet);
        QueryInfoBuilder queryInfoBuilder = QueryInfoBuilder.create((String)dataLink.getRegionKey(), (DimensionCombination)dimensionCombinationBuilder.getCombination());
        queryInfoBuilder.select(linkKey);
        IRegionDataSet iRegionDataSet = this.dataQueryService.queryRegionData(queryInfoBuilder.build());
        List dataValuesByLink = iRegionDataSet.getDataValuesByLink(linkKey);
        NrLinkInfoVO nrlinkInfo = new NrLinkInfoVO();
        nrlinkInfo.setLinkId(linkKey);
        nrlinkInfo.setRegionId(dataLink.getRegionKey());
        nrlinkInfo.setZbid(dataLink.getLinkExpression());
        IDataDefinitionRuntimeController dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)SpringContextUtils.getBean(IDataDefinitionRuntimeController.class);
        try {
            fieldDefine = dataDefinitionRuntimeController.queryFieldDefine(dataLink.getLinkExpression());
        }
        catch (Exception e) {
            log.error("\u7a7f\u900f\u67e5\u8be2\u6839\u636e\u6307\u6807id\u83b7\u53d6\u6307\u6807\u540d\u79f0\u5f02\u5e38", e);
            throw new BusinessException("\u7a7f\u900f\u67e5\u8be2\u83b7\u53d6\u6307\u6807\u540d\u79f0\u5f02\u5e38");
        }
        if (Objects.nonNull(fieldDefine)) {
            nrlinkInfo.setZbtitle(fieldDefine.getTitle());
        } else {
            log.error("\u7a7f\u900f\u67e5\u8be2\u83b7\u53d6\u6307\u6807\u540d\u79f0\u5f02\u5e38,\u672a\u83b7\u53d6\u5230\u6709\u6548\u7684\u6307\u6807\u5bf9\u8c61");
        }
        if (!CollectionUtils.isEmpty((Collection)dataValuesByLink)) {
            nrlinkInfo.setValue(((IDataValue)dataValuesByLink.get(0)).getAsObject());
        }
        return nrlinkInfo;
    }

    private List<IRowData> buildRowDataList(Map<String, String> dimensionMap, String regionId) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        for (Map.Entry<String, String> dimEntry : dimensionMap.entrySet()) {
            if (dimEntry.getKey().equals(RECORDKEY)) continue;
            dimensionValueSet.setValue(dimEntry.getKey(), (Object)dimEntry.getValue());
        }
        DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(dimensionValueSet);
        QueryInfoBuilder queryInfoBuilder = QueryInfoBuilder.create((String)regionId, (DimensionCombination)dimensionCombinationBuilder.getCombination());
        IRegionDataSet iRegionDataSet = this.dataQueryService.queryRegionData(queryInfoBuilder.build());
        return iRegionDataSet.getRowData();
    }

    private List<Map<String, Object>> buildRowDataZbId2valueMapList(Map<String, String> dimensionMap, List<IRowData> rowData, String taskKey, String formSchemeKey, String regionKey, String formKey) {
        LinkedHashMap<String, Object> currRowZbIdValueMap;
        ArrayList<Map<String, Object>> rowDataZbId2valueMapList = new ArrayList<Map<String, Object>>();
        if (CollectionUtils.isEmpty(rowData)) {
            return rowDataZbId2valueMapList;
        }
        String recordKeyStr = dimensionMap.get(RECORDKEY);
        List<String> rowIdStrList = new ArrayList<String>();
        if (recordKeyStr.length() >= 41) {
            try {
                rowIdStrList = (List)JsonUtils.readValue((String)recordKeyStr, (TypeReference)new TypeReference<List<String>>(){});
            }
            catch (Exception e) {
                rowIdStrList.add(recordKeyStr);
            }
        } else {
            rowIdStrList.add(recordKeyStr);
        }
        List rowIdList = rowIdStrList.stream().map(rowId -> this.normalizeRowId(dimensionMap, (String)rowId, taskKey, formSchemeKey, regionKey, formKey)).collect(Collectors.toList());
        for (IRowData rowDatum : rowData) {
            currRowZbIdValueMap = new LinkedHashMap<String, Object>();
            if (!rowIdList.contains(rowDatum.getRecKey())) continue;
            for (IDataValue linkDataValue : rowDatum.getLinkDataValues()) {
                currRowZbIdValueMap.put(linkDataValue.getMetaData().getDataField().getKey(), linkDataValue.getAsObject());
            }
            rowDataZbId2valueMapList.add(currRowZbIdValueMap);
        }
        if (CollectionUtils.isEmpty(rowDataZbId2valueMapList)) {
            IRowData iRowData = rowData.get(0);
            currRowZbIdValueMap = new LinkedHashMap();
            for (IDataValue linkDataValue : iRowData.getLinkDataValues()) {
                currRowZbIdValueMap.put(linkDataValue.getMetaData().getDataField().getKey(), linkDataValue.getAsObject());
            }
            rowDataZbId2valueMapList.add(currRowZbIdValueMap);
        }
        return rowDataZbId2valueMapList;
    }

    private String normalizeRowId(Map<String, String> dimensionMap, String recordKeyStr, String taskKey, String formSchemeKey, String regionKey, String formKey) {
        if (UUIDValidator.isValidUUID(recordKeyStr)) {
            return recordKeyStr;
        }
        RegionData regionData = this.jtableParamService.getRegion(regionKey);
        boolean isAllowDuplicateKey = regionData.getAllowDuplicateKey();
        if (isAllowDuplicateKey) {
            String[] split;
            for (String key : split = recordKeyStr.split("\\#\\^\\$")) {
                if (!UUIDValidator.isValidUUID(key)) continue;
                return key;
            }
            log.error("\u6d6e\u52a8\u533a\u57df\u8bbe\u7f6e\u91cd\u7801\uff0c\u4f46\u662f\u672a\u4ece\u884cid{}\u4e0a\u622a\u53d6\u5230UUid", (Object)recordKeyStr);
        }
        JtableContext jtableContext = this.initJtableContext(taskKey, formSchemeKey, formKey, dimensionMap);
        List bizKeyOrderFields = new ArrayList();
        List bizKeyOrderFieldList = this.jtableDataEngineService.getBizKeyOrderFieldList(regionKey, jtableContext);
        if (bizKeyOrderFieldList != null && !bizKeyOrderFieldList.isEmpty()) {
            bizKeyOrderFields = (List)bizKeyOrderFieldList.get(0);
        }
        DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder();
        DataCrudUtil.setBizKeyValueForDimension((DimensionCombinationBuilder)dimensionCombinationBuilder, (String)recordKeyStr, bizKeyOrderFields);
        QueryInfoBuilder queryInfoBuilder = QueryInfoBuilder.create((String)regionKey, (DimensionCombination)dimensionCombinationBuilder.getCombination());
        IRegionDataSet iRegionDataSet = this.dataQueryService.queryRegionData(queryInfoBuilder.build());
        return ((IRowData)iRegionDataSet.getRowData().get(0)).getRecKey();
    }

    private JtableContext initJtableContext(String taskKey, String formSchemeKey, String formKey, Map<String, String> dimensionSet) {
        JtableContext jtableContext = new JtableContext();
        jtableContext.setTaskKey(taskKey);
        jtableContext.setFormSchemeKey(formSchemeKey);
        jtableContext.setFormKey(formKey);
        HashMap<String, DimensionValue> dimensionValueMap = new HashMap<String, DimensionValue>();
        for (Map.Entry<String, String> entries : dimensionSet.entrySet()) {
            if (RECORDKEY.equals(entries.getKey())) continue;
            DimensionValue dimensionValue = new DimensionValue();
            dimensionValue.setName(entries.getKey());
            dimensionValue.setValue(entries.getValue());
            dimensionValueMap.put(entries.getKey(), dimensionValue);
        }
        jtableContext.setDimensionSet(dimensionValueMap);
        return jtableContext;
    }

    private String buildFloatSelectedId(FloatRegionConfigVO floatRegionConfigVO, List<String> linkIdList) {
        QueryConfigInfo queryConfigInfo = floatRegionConfigVO.getQueryConfigInfo();
        List zbMapping = queryConfigInfo.getZbMapping();
        ArrayList<String> floatFixSettingLinkIdList = new ArrayList<String>();
        for (FloatZbMappingVO floatZbMappingVO : zbMapping) {
            if (!"=".equals(floatZbMappingVO.getQueryName())) continue;
            floatFixSettingLinkIdList.add(floatZbMappingVO.getDataLinkId());
        }
        if (CollectionUtils.isEmpty(floatFixSettingLinkIdList)) {
            return "";
        }
        for (String selectedLinkId : linkIdList) {
            if (!floatFixSettingLinkIdList.contains(selectedLinkId)) continue;
            DataLinkDefine dataLink = this.iRuntimeDataLinkService.queryDataLink(selectedLinkId);
            return dataLink.getLinkExpression();
        }
        DataLinkDefine dataLink = this.iRuntimeDataLinkService.queryDataLink((String)floatFixSettingLinkIdList.get(0));
        return dataLink.getLinkExpression();
    }

    private FloatRegionConfigVO reSortZbMapping(FloatRegionConfigVO floatRegionConfigVO, List<Map<String, Object>> rowDataMapList) {
        if (CollectionUtils.isEmpty(rowDataMapList) || ObjectUtils.isEmpty(floatRegionConfigVO)) {
            return floatRegionConfigVO;
        }
        QueryConfigInfo queryConfigInfo = floatRegionConfigVO.getQueryConfigInfo();
        List zbMapping = queryConfigInfo.getZbMapping();
        ArrayList<FloatZbMappingVO> sortedZbMapping = new ArrayList<FloatZbMappingVO>();
        Map<String, Object> zbIdMap = rowDataMapList.get(0);
        block0: for (String zbId : zbIdMap.keySet()) {
            for (FloatZbMappingVO floatZbMappingVO : zbMapping) {
                if (!zbId.equals(floatZbMappingVO.getFieldDefineId())) continue;
                sortedZbMapping.add(floatZbMappingVO);
                continue block0;
            }
        }
        queryConfigInfo.setZbMapping(sortedZbMapping);
        return floatRegionConfigVO;
    }

    private String queryBblx(String orgCode, String formatPeriodStr, String contextEntityId) {
        Date period = GcPeriodUtils.getDateArr((String)formatPeriodStr)[0];
        OrgDTO orgParam = new OrgDTO();
        orgParam.setCode(orgCode);
        orgParam.setSyncOrgBaseInfo(Boolean.valueOf(false));
        orgParam.setVersionDate(period);
        orgParam.setRecoveryflag(Integer.valueOf(-1));
        orgParam.setExtInfo(new HashMap());
        orgParam.setStopflag(Integer.valueOf(-1));
        orgParam.setForceUpdateHistoryVersionData(Boolean.valueOf(true));
        String orgType = contextEntityId.substring(0, contextEntityId.indexOf("@"));
        orgParam.setCategoryname(orgType);
        orgParam.setAuthType(OrgDataOption.AuthType.NONE);
        orgParam.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
        OrgDO org = this.orgDataClient.get(orgParam);
        if (Objects.isNull(org) || Objects.isNull(org.getValueOf("BBLX"))) {
            return "error";
        }
        return org.getValueOf("BBLX").toString();
    }

    @Override
    public String queryBblx(QueryBblxParam bblxParam) {
        Assert.isNotNull((Object)bblxParam.getOrgCode(), (String)"\u67e5\u8be2\u62a5\u8868\u5355\u4f4d\u65f6\uff0c\u5355\u4f4d\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotNull((Object)bblxParam.getFormatPeriodStr(), (String)"\u67e5\u8be2\u62a5\u8868\u5355\u4f4d\u65f6\uff0c\u62a5\u8868\u671f\u95f4\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        FormSchemeDefine formSchemeDefine = this.iRunTimeViewController.getFormScheme(bblxParam.getFormSchemeKey());
        String contextEntityId = FetchTaskUtil.getEntityIdByTaskAndCtx((String)formSchemeDefine.getTaskKey());
        return this.queryBblx(bblxParam.getOrgCode(), bblxParam.getFormatPeriodStr(), contextEntityId);
    }

    @Override
    public GcFetchPierceDataRegionParamVO queryFormByRegionKey(String regionKey) {
        DataRegionDefine dataRegionDefine = this.iRunTimeViewController.queryDataRegionDefine(regionKey);
        String formKey = dataRegionDefine.getFormKey();
        String floatArea = DataRegionKind.DATA_REGION_SIMPLE.equals((Object)dataRegionDefine.getRegionKind()) ? DataRegionTypeEnum.FIXED.getCode() : DataRegionTypeEnum.FLOAT.getCode();
        GcFetchPierceDataRegionParamVO regionParamVO = new GcFetchPierceDataRegionParamVO();
        regionParamVO.setFormKey(formKey);
        regionParamVO.setFloatArea(floatArea);
        return regionParamVO;
    }

    @Override
    public List<NrLinkInfoVO> queryLinkInfo(List<String> linkIdList) {
        if (CollectionUtils.isEmpty(linkIdList)) {
            return CollectionUtils.newArrayList();
        }
        ArrayList linInfos = CollectionUtils.newArrayList();
        for (String linkId : linkIdList) {
            DataLinkDefine dataLink = this.iRuntimeDataLinkService.queryDataLink(linkId);
            if (dataLink == null) {
                throw new BusinessException(String.format("\u6839\u636e\u94fe\u63a5\u6807\u8bc6\u3010%1$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u914d\u7f6e", linkId));
            }
            NrLinkInfoVO nrlinkInfo = new NrLinkInfoVO();
            nrlinkInfo.setLinkId(linkId);
            nrlinkInfo.setRegionId(dataLink.getRegionKey());
            nrlinkInfo.setZbid(dataLink.getLinkExpression());
            nrlinkInfo.setZbtitle(dataLink.getTitle());
            linInfos.add(nrlinkInfo);
        }
        return linInfos;
    }
}

