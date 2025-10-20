/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bde.common.constant.RequestSourceTypeEnum
 *  com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO
 *  com.jiuqi.bde.common.dto.FloatRegionConfigVO
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.bde.common.dto.fetch.init.FetchFormDTO
 *  com.jiuqi.bde.common.dto.fetch.init.FetchInitTaskDTO
 *  com.jiuqi.bde.common.dto.fetch.init.FetchRegionDTO
 *  com.jiuqi.bde.common.dto.fetch.request.FetchRequestContextDTO
 *  com.jiuqi.bde.common.dto.fetch.request.FetchRequestDTO
 *  com.jiuqi.bde.common.dto.fetch.request.FetchRequestFixedSettingDTO
 *  com.jiuqi.bde.common.dto.fetch.request.FetchRequestFloatSettingDTO
 *  com.jiuqi.bde.fetch.client.OrgMappingClient
 *  com.jiuqi.bde.log.fetch.dto.FetchItemLogDTO
 *  com.jiuqi.budget.component.domain.FormulaExeParam
 *  com.jiuqi.budget.component.service.FormulaExecService
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.utils.BeanConvertUtil
 *  com.jiuqi.gcreport.bde.common.utils.BdeClientUtil
 *  com.jiuqi.gcreport.bde.common.vo.FetchSettingVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.AdjustPeriodSettingVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSchemeVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FixedAdaptSettingDTO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FixedFieldDefineSettingDTO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.enums.AdaptContextEnum
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchFloatSettingService
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSchemeNrService
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSettingService
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.utils.AdaptCondiUtils
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.utils.FetchTaskUtil
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.common.TableKind
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.paramInfo.BatchCalculateInfo
 *  com.jiuqi.nr.dataentry.paramInfo.FormReadWriteAccessData
 *  com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessProvider
 *  com.jiuqi.nr.dataentry.readwrite.bean.ReadWriteAccessCacheParams
 *  com.jiuqi.nr.dataentry.readwrite.impl.ReadWriteAccessCacheManager
 *  com.jiuqi.nr.dataentry.service.IBatchCalculateService
 *  com.jiuqi.nr.dataentry.util.Consts$FormAccessLevel
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataRegionService
 *  com.jiuqi.nr.efdc.bean.FetchEfdcParam
 *  com.jiuqi.nr.efdc.internal.pojo.QueryObjectImpl
 *  com.jiuqi.nr.efdc.pojo.EfdcInfo
 *  com.jiuqi.nr.efdc.service.IEFDCConfigService
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo
 *  com.jiuqi.nr.jtable.params.input.EntityQueryByViewInfo
 *  com.jiuqi.nr.jtable.params.output.EntityByKeyReturnInfo
 *  com.jiuqi.nr.jtable.params.output.EntityData
 *  com.jiuqi.nr.jtable.params.output.EntityReturnInfo
 *  com.jiuqi.nr.jtable.service.IJtableEntityService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 */
package com.jiuqi.gcreport.bde.fetch.impl.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bde.common.constant.RequestSourceTypeEnum;
import com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO;
import com.jiuqi.bde.common.dto.FloatRegionConfigVO;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.common.dto.fetch.init.FetchFormDTO;
import com.jiuqi.bde.common.dto.fetch.init.FetchInitTaskDTO;
import com.jiuqi.bde.common.dto.fetch.init.FetchRegionDTO;
import com.jiuqi.bde.common.dto.fetch.request.FetchRequestContextDTO;
import com.jiuqi.bde.common.dto.fetch.request.FetchRequestDTO;
import com.jiuqi.bde.common.dto.fetch.request.FetchRequestFixedSettingDTO;
import com.jiuqi.bde.common.dto.fetch.request.FetchRequestFloatSettingDTO;
import com.jiuqi.bde.fetch.client.OrgMappingClient;
import com.jiuqi.bde.log.fetch.dto.FetchItemLogDTO;
import com.jiuqi.budget.component.domain.FormulaExeParam;
import com.jiuqi.budget.component.service.FormulaExecService;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.utils.BeanConvertUtil;
import com.jiuqi.gcreport.bde.common.utils.BdeClientUtil;
import com.jiuqi.gcreport.bde.common.vo.FetchSettingVO;
import com.jiuqi.gcreport.bde.fetch.impl.handler.FetchDataFormDTO;
import com.jiuqi.gcreport.bde.fetch.impl.service.GcFetchDataExecuteService;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.AdjustPeriodSettingVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSchemeVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FixedAdaptSettingDTO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FixedFieldDefineSettingDTO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.enums.AdaptContextEnum;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchFloatSettingService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSchemeNrService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSettingService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.utils.AdaptCondiUtils;
import com.jiuqi.gcreport.bde.fetchsetting.impl.utils.FetchTaskUtil;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.common.TableKind;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.paramInfo.BatchCalculateInfo;
import com.jiuqi.nr.dataentry.paramInfo.FormReadWriteAccessData;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessProvider;
import com.jiuqi.nr.dataentry.readwrite.bean.ReadWriteAccessCacheParams;
import com.jiuqi.nr.dataentry.readwrite.impl.ReadWriteAccessCacheManager;
import com.jiuqi.nr.dataentry.service.IBatchCalculateService;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataRegionService;
import com.jiuqi.nr.efdc.bean.FetchEfdcParam;
import com.jiuqi.nr.efdc.internal.pojo.QueryObjectImpl;
import com.jiuqi.nr.efdc.pojo.EfdcInfo;
import com.jiuqi.nr.efdc.service.IEFDCConfigService;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo;
import com.jiuqi.nr.jtable.params.input.EntityQueryByViewInfo;
import com.jiuqi.nr.jtable.params.output.EntityByKeyReturnInfo;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.params.output.EntityReturnInfo;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GcFetchDataExecuteServiceImpl
implements GcFetchDataExecuteService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GcFetchDataExecuteServiceImpl.class);
    @Autowired
    private RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private IJtableEntityService jtableEntityService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private ReadWriteAccessProvider accessProvider;
    @Autowired
    private IRuntimeDataRegionService dataRegionService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IEFDCConfigService efdcConfigService;
    @Autowired
    private FetchSchemeNrService fetchSchemeService;
    @Autowired
    private FetchSettingService fetchSettingService;
    @Autowired
    private FetchFloatSettingService fetchFloatSettingService;
    @Autowired
    private FormulaExecService formulaExecService;
    @Autowired
    private IBatchCalculateService batchCalculateService;
    @Autowired
    private OrgMappingClient orgMappingClient;

    @Override
    public FetchInitTaskDTO buildFetchInitTask(EfdcInfo efdcInfo) {
        FormSchemeDefine formScheme;
        this.doBasicCheck();
        TaskDefine taskDefine = null;
        try {
            taskDefine = this.runTimeAuthViewController.queryTaskDefine(efdcInfo.getTaskKey());
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u672a\u627e\u5230" + efdcInfo.getTaskKey() + "\u5bf9\u5e94\u7684\u4efb\u52a1\u5b9a\u4e49:" + e.getMessage());
        }
        if (taskDefine == null) {
            throw new BusinessRuntimeException("\u672a\u627e\u5230" + efdcInfo.getTaskKey() + "\u5bf9\u5e94\u7684\u4efb\u52a1\u5b9a\u4e49\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u68c0\u67e5\u53d6\u6570\u7528\u6237\u662f\u5426\u5177\u6709\u62a5\u8868\u4efb\u52a1\u6743\u9650");
        }
        try {
            formScheme = this.runTimeAuthViewController.getFormScheme(efdcInfo.getFormSchemeKey());
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u672a\u627e\u5230" + efdcInfo.getTaskKey() + "\u5bf9\u5e94\u7684\u62a5\u8868\u65b9\u6848:" + e.getMessage());
        }
        JtableContext context = this.newJtableContext(efdcInfo);
        List entityList = this.jtableParamService.getEntityList(formScheme.getKey());
        EntityViewData masterEntity = this.getMasterEntity(entityList);
        if (masterEntity == null) {
            throw new BusinessRuntimeException("\u672a\u627e\u5230\u62a5\u8868\u65b9\u6848" + efdcInfo.getFormSchemeKey() + "\u5bf9\u5e94\u7684\u4e3b\u4f53\u89c6\u56fe\u4fe1\u606f,\u8bf7\u68c0\u67e5\u62a5\u8868\u65b9\u6848\u914d\u7f6e");
        }
        EntityQueryByKeyInfo entityQueryByKeyInfo = this.newEntityQueryByKeyInfo(efdcInfo, context, masterEntity);
        EntityByKeyReturnInfo queryEntityDataByKey = this.jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo);
        efdcInfo.getVariableMap().put("UNIT_NAME", queryEntityDataByKey.getEntity().getTitle());
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((JtableContext)context);
        FetchEfdcParam efdcParam = this.getEfdcParam(formScheme, dimensionValueSet, efdcInfo);
        FormulaSchemeDefine extractformulaScheme = this.efdcConfigService.getSoluctionByDimensions(efdcParam.getQueryObj(), efdcParam.getDimMap(), efdcParam.getEntityId());
        if (extractformulaScheme == null || StringUtils.isEmpty((String)extractformulaScheme.getKey())) {
            throw new BusinessRuntimeException(queryEntityDataByKey.getEntity().getTitle() + "\u3010\u8d22\u52a1\u63d0\u53d6\u65b9\u6848\u672a\u914d\u7f6e\u3011");
        }
        String newLine = System.lineSeparator();
        StringBuffer detailLog = new StringBuffer();
        detailLog.append(String.format("\u5355\u4f4d\uff1a%s ", queryEntityDataByKey.getEntity().getTitle()));
        detailLog.append(newLine);
        detailLog.append(String.format("\u65f6\u671f\uff1a%s ", efdcInfo.getParas().get("DATATIME").toString()));
        detailLog.append(newLine);
        List<String> formKeyList = this.getFormKeyList(efdcInfo.getFormKey(), context.getFormSchemeKey());
        List<String> fetchFormKeyList = this.filterForm(context, extractformulaScheme.getKey(), detailLog, formKeyList);
        FetchInitTaskDTO fetchTaskDTO = this.convertFetchInitTaskDTO(efdcInfo, fetchFormKeyList, extractformulaScheme.getKey(), dimensionValueSet, entityList, taskDefine, formScheme);
        fetchTaskDTO.setUnitName(queryEntityDataByKey.getEntity().getTitle());
        fetchTaskDTO.setPeriodScheme(efdcInfo.getParas().get("DATATIME").toString());
        fetchTaskDTO.setCurrency(efdcInfo.getDimensionSet().get("MD_CURRENCY") == null ? null : ((DimensionValue)efdcInfo.getDimensionSet().get("MD_CURRENCY")).getValue());
        List formulaSchemeDefineList = this.efdcConfigService.getRPTFormulaScheme(efdcParam.getQueryObj(), efdcParam.getDimMap(), efdcParam.getEntityId());
        if (!CollectionUtils.isEmpty((Collection)formulaSchemeDefineList)) {
            StringJoiner formulaSchemeKeys = new StringJoiner(";");
            for (FormulaSchemeDefine formulaSchemeDefine : formulaSchemeDefineList) {
                formulaSchemeKeys.add(formulaSchemeDefine.getKey());
            }
            fetchTaskDTO.setFormulaSchemeKeys(formulaSchemeKeys.toString());
        }
        OrgMappingDTO orgMappingDto = (OrgMappingDTO)BdeClientUtil.parseResponse((BusinessResponseEntity)this.orgMappingClient.getOrgMappingByUnitCode(fetchTaskDTO.getBblx(), fetchTaskDTO.getUnitCode()));
        fetchTaskDTO.setOrgMapping(orgMappingDto);
        return fetchTaskDTO;
    }

    private Boolean queryIsIncludeAdjustVoucher(FetchSchemeVO fetchScheme) {
        return Objects.nonNull(fetchScheme) && Objects.nonNull(fetchScheme.getIncludeAdjustVchr()) && 0 != fetchScheme.getIncludeAdjustVchr();
    }

    @Override
    public void doCalculate(FetchInitTaskDTO fetchTask) {
        if (StringUtils.isEmpty((String)fetchTask.getFormulaSchemeKeys())) {
            LOGGER.info("\u6ca1\u6709\u516c\u5f0f\u65b9\u6848\uff0c\u65e0\u6cd5\u8fd0\u7b97\uff01");
            return;
        }
        if (CollectionUtils.isEmpty((Collection)fetchTask.getFetchForms())) {
            return;
        }
        BatchCalculateInfo batchCalculateInfo = new BatchCalculateInfo();
        batchCalculateInfo.setTaskKey(fetchTask.getTaskId());
        batchCalculateInfo.setDimensionSet((Map)JsonUtils.readValue((String)fetchTask.getDimensionSetStr(), (TypeReference)new TypeReference<Map<String, DimensionValue>>(){}));
        batchCalculateInfo.setVariableMap(fetchTask.getVariableMap());
        HashMap<String, Object> formulas = new HashMap<String, Object>();
        for (FetchFormDTO fetchForm : fetchTask.getFetchForms()) {
            formulas.put(fetchForm.getId(), null);
        }
        batchCalculateInfo.setFormulas(formulas);
        batchCalculateInfo.setFormulaSchemeKey(fetchTask.getFormulaSchemeKeys());
        batchCalculateInfo.setFormSchemeKey(fetchTask.getFormSchemeId());
        this.batchCalculateService.batchCalculateForm(batchCalculateInfo);
    }

    @Override
    public FetchRequestDTO buildFetchContext(FetchDataFormDTO fetchForm, FetchRegionDTO fetchRegion) {
        FetchSettingCond fetchSettingCond = new FetchSettingCond(fetchForm.getFetchSchemeId(), fetchForm.getFormSchemeId(), fetchForm.getFormId(), fetchRegion.getId());
        List listDataLinkFixedSettingRowRecords = this.fetchSettingService.listDataLinkFixedSettingRowRecords(fetchSettingCond);
        FloatRegionConfigVO fetchFloatSetting = this.fetchFloatSettingService.getFetchFloatSetting(fetchSettingCond);
        FetchRequestDTO fetchRequestDTO = new FetchRequestDTO();
        FetchRequestContextDTO fetchContext = (FetchRequestContextDTO)BeanConvertUtil.convert((Object)fetchForm, FetchRequestContextDTO.class, (String[])new String[0]);
        fetchContext.setRegionId(fetchRegion.getId());
        ArrayList<FetchRequestFixedSettingDTO> fixedSettingList = new ArrayList<FetchRequestFixedSettingDTO>();
        FetchRequestFixedSettingDTO fixedSetting = null;
        block0: for (FixedFieldDefineSettingDTO fieldDefineSettingVO : listDataLinkFixedSettingRowRecords) {
            fixedSetting = (FetchRequestFixedSettingDTO)BeanConvertUtil.convert((Object)fieldDefineSettingVO, FetchRequestFixedSettingDTO.class, (String[])new String[0]);
            FormulaExeParam formulaExeParam = null;
            for (FixedAdaptSettingDTO fixedAdaptSetting : fieldDefineSettingVO.getFixedSettingData()) {
                if (StringUtils.isEmpty((String)fixedAdaptSetting.getAdaptFormula()) || "#".equals(fixedAdaptSetting.getAdaptFormula())) {
                    fixedSetting.setLogicFormula(fixedAdaptSetting.getLogicFormula());
                    fixedSetting.setBizModelFormula((Map)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)fixedAdaptSetting.getBizModelFormula()), (TypeReference)new TypeReference<Map<String, List<FixedFetchSourceRowSettingVO>>>(){}));
                    fixedSettingList.add(fixedSetting);
                    continue block0;
                }
                formulaExeParam = new FormulaExeParam();
                String orgType = FetchTaskUtil.getOrgTypeByTaskAndCtx((String)fetchForm.getTaskId());
                String adaptExpression = AdaptCondiUtils.expressionOrgTypeConvert((String)fixedAdaptSetting.getAdaptFormula(), (String)orgType);
                formulaExeParam.setFormulaExpress(adaptExpression);
                HashMap<String, String> adaptContext = new HashMap<String, String>(8);
                adaptContext.put(AdaptContextEnum.MD_ORG.getKey(), fetchForm.getUnitCode());
                adaptContext.put(AdaptContextEnum.DATATIME.getKey(), fetchForm.getPeriodScheme());
                adaptContext.put(orgType, fetchForm.getUnitCode());
                formulaExeParam.setDimValMap(adaptContext);
                if (!this.formulaExecService.getAdaptVal(formulaExeParam)) continue;
                fixedSetting.setLogicFormula(fixedAdaptSetting.getLogicFormula());
                fixedSetting.setBizModelFormula((Map)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)fixedAdaptSetting.getBizModelFormula()), (TypeReference)new TypeReference<Map<String, List<FixedFetchSourceRowSettingVO>>>(){}));
                fixedSettingList.add(fixedSetting);
                continue block0;
            }
        }
        fetchRequestDTO.setFetchContext(fetchContext);
        if (fetchFloatSetting != null) {
            fetchRequestDTO.setFloatSetting(new FetchRequestFloatSettingDTO(fetchFloatSetting.getQueryType(), fetchFloatSetting.getQueryConfigInfo()));
        }
        fetchRequestDTO.setFixedSetting(fixedSettingList);
        return fetchRequestDTO;
    }

    @Override
    public FetchItemLogDTO buildFetchLogInfo(EfdcInfo efdcInfo) {
        FormSchemeDefine formScheme;
        try {
            formScheme = this.runTimeAuthViewController.getFormScheme(efdcInfo.getFormSchemeKey());
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u672a\u627e\u5230" + efdcInfo.getTaskKey() + "\u5bf9\u5e94\u7684\u62a5\u8868\u65b9\u6848:" + e.getMessage());
        }
        JtableContext context = this.newJtableContext(efdcInfo);
        List entityList = this.jtableParamService.getEntityList(formScheme.getKey());
        EntityViewData masterEntity = this.getMasterEntity(entityList);
        if (masterEntity == null) {
            throw new BusinessRuntimeException("\u672a\u627e\u5230\u62a5\u8868\u65b9\u6848" + efdcInfo.getFormSchemeKey() + "\u5bf9\u5e94\u7684\u4e3b\u4f53\u89c6\u56fe\u4fe1\u606f,\u8bf7\u68c0\u67e5\u62a5\u8868\u65b9\u6848\u914d\u7f6e");
        }
        EntityQueryByKeyInfo entityQueryByKeyInfo = this.newEntityQueryByKeyInfo(efdcInfo, context, masterEntity);
        EntityByKeyReturnInfo queryEntityDataByKey = this.jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo);
        EntityViewData datatimeEntity = this.jtableParamService.getDataTimeEntity(formScheme.getKey());
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((JtableContext)context);
        String periodCode = dimensionValueSet.getValue(datatimeEntity.getDimensionName()).toString();
        FetchItemLogDTO log = new FetchItemLogDTO();
        log.setRunnerId("#");
        log.setUnitCode(queryEntityDataByKey.getEntity().getCode());
        log.setUnitName(queryEntityDataByKey.getEntity().getTitle());
        log.setPeriodScheme(periodCode);
        log.setCurrency(efdcInfo.getDimensionSet().get("MD_CURRENCY") == null ? null : ((DimensionValue)efdcInfo.getDimensionSet().get("MD_CURRENCY")).getValue());
        return log;
    }

    /*
     * WARNING - void declaration
     */
    private FetchInitTaskDTO convertFetchInitTaskDTO(EfdcInfo efdcInfo, List<String> accessFormKeys, String fetchSchemeId, DimensionValueSet dimensionValueSet, List<EntityViewData> entityList, TaskDefine taskDefine, FormSchemeDefine formScheme) {
        void var18_23;
        String unitCode = efdcInfo.getParas().get("UnitCode").toString();
        String dataPeriod = efdcInfo.getParas().get("DATATIME").toString();
        FetchInitTaskDTO fetchTaskDTO = new FetchInitTaskDTO();
        fetchTaskDTO.setRequestSourceType(RequestSourceTypeEnum.NR_FETCH.getCode());
        fetchTaskDTO.setUnitCode(unitCode);
        fetchTaskDTO.setIncludeUncharged(Boolean.valueOf(efdcInfo.isContainsUnbVou()));
        String[] times = FetchTaskUtil.parseDataTime((String)dataPeriod);
        fetchTaskDTO.setPeriodScheme(dataPeriod);
        fetchTaskDTO.setStartDateStr(times[0]);
        fetchTaskDTO.setEndDateStr(times[1]);
        fetchTaskDTO.setForceSkipEtlHandle(Boolean.valueOf(efdcInfo.getVariableMap() == null ? false : Boolean.TRUE.equals(efdcInfo.getVariableMap().get("SKIP_ETL_HANDLE"))));
        fetchTaskDTO.setFetchSchemeId(fetchSchemeId);
        FetchSchemeVO fetchScheme = this.fetchSchemeService.getFetchScheme(fetchSchemeId);
        Assert.isNotNull((Object)fetchScheme, (String)String.format("\u6839\u636e\u53d6\u6570\u65b9\u6848\u6807\u8bc6\u3010%1$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u53d6\u6570\u65b9\u6848\uff0c\u8bf7\u68c0\u67e5\u53d6\u6570\u65b9\u6848\u914d\u7f6e", fetchSchemeId), (Object[])new Object[0]);
        fetchTaskDTO.setIncludeAdjustVchr(this.queryIsIncludeAdjustVoucher(fetchScheme));
        if (efdcInfo.isAccount()) {
            for (AdjustPeriodSettingVO adjustPeriodSettingVO : fetchScheme.getAdjustPeriodSettingVOs()) {
                if (!dataPeriod.equals(adjustPeriodSettingVO.getAdjustPeriod())) continue;
                fetchTaskDTO.setStartAdjustPeriod(adjustPeriodSettingVO.getStartAdjustPeriod());
                fetchTaskDTO.setEndAdjustPeriod(adjustPeriodSettingVO.getEndAdjustPeriod());
                break;
            }
        }
        fetchTaskDTO.setFetchSchemeTitle(fetchScheme.getName());
        fetchTaskDTO.setFormSchemeId(efdcInfo.getFormSchemeKey());
        fetchTaskDTO.setFormSchemeTitle(formScheme.getTitle());
        fetchTaskDTO.setTaskId(efdcInfo.getTaskKey());
        fetchTaskDTO.setTaskTitle(taskDefine.getTitle());
        ContextUser user = NpContextHolder.getContext().getUser();
        Assert.isNotNull((Object)user);
        fetchTaskDTO.setUsername(StringUtils.isEmpty((String)user.getName()) ? user.getFullname() : user.getName());
        fetchTaskDTO.setRpUnitType(FetchTaskUtil.getOrgTypeByTaskAndCtx((String)efdcInfo.getTaskKey()));
        fetchTaskDTO.setCurrency(efdcInfo.getDimensionSet().get("MD_CURRENCY") == null ? null : ((DimensionValue)efdcInfo.getDimensionSet().get("MD_CURRENCY")).getValue());
        fetchTaskDTO.setBblx(this.getBblx(efdcInfo, fetchSchemeId));
        String targetKey = "";
        String periodCode = "";
        for (EntityViewData entityViewData : entityList) {
            if (TableKind.TABLE_KIND_ENTITY.name().equals(entityViewData.getKind()) && StringUtils.isEmpty((String)targetKey) && dimensionValueSet.hasValue(entityViewData.getDimensionName())) {
                targetKey = dimensionValueSet.getValue(entityViewData.getDimensionName()).toString();
            }
            if (!TableKind.TABLE_KIND_ENTITY_PERIOD.name().equals(entityViewData.getKind()) || !StringUtils.isEmpty((String)periodCode) || !dimensionValueSet.hasValue(entityViewData.getDimensionName())) continue;
            periodCode = dimensionValueSet.getValue(entityViewData.getDimensionName()).toString();
        }
        HashMap dimMap = new HashMap();
        for (Map.Entry entry : efdcInfo.getDimensionSet().entrySet()) {
            if (targetKey.equals(((DimensionValue)entry.getValue()).getValue()) || periodCode.equals(((DimensionValue)entry.getValue()).getValue()) || ((DimensionValue)entry.getValue()).getValue() == null) continue;
            dimMap.put(entry.getKey(), ((DimensionValue)entry.getValue()).getValue());
        }
        EntityViewData entityViewData = this.jtableParamService.getDwEntity(formScheme.getKey());
        String string = entityViewData.getTableName();
        if (string != null) {
            String string2 = string.toUpperCase();
        }
        dimMap.put("ORGTYPE", var18_23);
        fetchTaskDTO.setOtherEntity(dimMap);
        fetchTaskDTO.setDimensionSetStr(JsonUtils.writeValueAsString((Object)efdcInfo.getDimensionSet()));
        fetchTaskDTO.setVariableMap(efdcInfo.getVariableMap());
        HashSet regionIdSet = new HashSet();
        ArrayList<FetchFormDTO> formFormList = new ArrayList<FetchFormDTO>(accessFormKeys.size());
        for (String formKey : accessFormKeys) {
            FormDefine formDefine = this.runTimeAuthViewController.queryFormById(formKey);
            FetchFormDTO fetchForm = new FetchFormDTO(formDefine.getKey(), formDefine.getFormCode(), formDefine.getTitle());
            FetchSettingCond fetchSettingCond = new FetchSettingCond(fetchSchemeId, formScheme.getKey(), formKey);
            Map<String, DataRegionDefine> regionMap = this.dataRegionService.getDataRegionsInForm(formKey).stream().collect(Collectors.toMap(IBaseMetaItem::getKey, item -> item, (k1, k2) -> k2));
            regionIdSet.addAll(this.fetchFloatSettingService.listFetchFloatSettingByFormId(fetchSettingCond).stream().map(FloatRegionConfigVO::getRegionId).collect(Collectors.toSet()));
            regionIdSet.addAll(this.fetchSettingService.listFetchSettingByFormId(fetchSettingCond).stream().map(FetchSettingVO::getRegionId).collect(Collectors.toSet()));
            for (String regionId : regionIdSet) {
                DataRegionDefine regionDefine = regionMap.get(regionId);
                if (regionDefine == null) continue;
                FetchRegionDTO fetchRegion = new FetchRegionDTO(regionDefine.getKey(), regionDefine.getKey(), regionDefine.getTitle());
                fetchForm.addFetchRegion(fetchRegion);
            }
            if (CollectionUtils.isEmpty((Collection)fetchForm.getFetchRegions())) continue;
            formFormList.add(fetchForm);
        }
        fetchTaskDTO.setFetchForms(formFormList);
        fetchTaskDTO.setFetchFormCt(formFormList.size());
        return fetchTaskDTO;
    }

    private JtableContext newJtableContext(EfdcInfo efdcInfo) {
        JtableContext context = new JtableContext();
        context.setDimensionSet(efdcInfo.getDimensionSet());
        context.setFormSchemeKey(efdcInfo.getFormSchemeKey());
        context.setTaskKey(efdcInfo.getTaskKey());
        context.setVariableMap(efdcInfo.getVariableMap());
        return context;
    }

    private EntityViewData getMasterEntity(List<EntityViewData> entityList) {
        for (EntityViewData entity : entityList) {
            if (!entity.isMasterEntity()) continue;
            return entity;
        }
        return null;
    }

    private EntityQueryByKeyInfo newEntityQueryByKeyInfo(EfdcInfo efdcInfo, JtableContext context, EntityViewData masterEntity) {
        EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
        String dimensionValue = ((DimensionValue)efdcInfo.getDimensionSet().get(masterEntity.getDimensionName())).getValue();
        entityQueryByKeyInfo.setEntityKey(dimensionValue);
        entityQueryByKeyInfo.setEntityViewKey(masterEntity.getKey());
        entityQueryByKeyInfo.setContext(context);
        return entityQueryByKeyInfo;
    }

    private FetchEfdcParam getEfdcParam(FormSchemeDefine formScheme, DimensionValueSet dimensionValueSet, EfdcInfo efdcInfo) {
        EntityViewData dwEntity = this.jtableParamService.getDwEntity(formScheme.getKey());
        String targetKey = dimensionValueSet.getValue(dwEntity.getDimensionName()).toString();
        EntityViewData datatimeEntity = this.jtableParamService.getDataTimeEntity(formScheme.getKey());
        String periodCode = dimensionValueSet.getValue(datatimeEntity.getDimensionName()).toString();
        List dimEntityList = this.jtableParamService.getDimEntityList(formScheme.getKey());
        HashMap<String, String> dimMap = new HashMap<String, String>();
        for (EntityViewData entityInfo : dimEntityList) {
            boolean periodView = this.periodEntityAdapter.isPeriodEntity(entityInfo.getKey());
            if (entityInfo.isMasterEntity() || periodView || !dimensionValueSet.hasValue(entityInfo.getDimensionName())) continue;
            for (Map.Entry entry : efdcInfo.getDimensionSet().entrySet()) {
                if (((String)entry.getKey()).indexOf(entityInfo.getTableName()) == -1) continue;
                dimMap.put(entityInfo.getTableName(), ((DimensionValue)entry.getValue()).getValue());
            }
        }
        dimMap.put(datatimeEntity.getDimensionName(), periodCode);
        HashMap<String, String> paras = new HashMap<String, String>();
        paras.put("DATATIME", periodCode);
        EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
        entityQueryByKeyInfo.setEntityKey(targetKey);
        entityQueryByKeyInfo.setEntityViewKey(FetchTaskUtil.getEntityIdByTaskAndCtx((String)formScheme.getTaskKey()));
        JtableContext context = new JtableContext();
        context.setDimensionSet(efdcInfo.getDimensionSet());
        context.setFormSchemeKey(efdcInfo.getFormSchemeKey());
        context.setTaskKey(efdcInfo.getTaskKey());
        context.setVariableMap(efdcInfo.getVariableMap());
        entityQueryByKeyInfo.setContext(context);
        EntityByKeyReturnInfo entityByKeyReturnInfo = this.jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo);
        if (null != entityByKeyReturnInfo && null != entityByKeyReturnInfo.getEntity()) {
            String unitCode = entityByKeyReturnInfo.getEntity().getId();
            paras.put("UnitCode", unitCode);
        } else {
            EntityQueryByViewInfo entityQueryInfo = new EntityQueryByViewInfo();
            entityQueryByKeyInfo.setEntityViewKey(dwEntity.getKey());
            EntityReturnInfo entityReturnInfo = this.jtableEntityService.queryEntityData(entityQueryInfo);
            if (!entityReturnInfo.getEntitys().isEmpty()) {
                String unitCode = ((EntityData)entityReturnInfo.getEntitys().get(0)).getId();
                paras.put("UnitCode", unitCode);
            }
        }
        efdcInfo.setParas(paras);
        if (StringUtils.isEmpty((String)targetKey)) {
            return null;
        }
        String formSchemeKey = efdcInfo.getFormSchemeKey();
        String taskKey = efdcInfo.getTaskKey();
        QueryObjectImpl queryObjectImpl = new QueryObjectImpl(taskKey, formSchemeKey, targetKey);
        FetchEfdcParam result = new FetchEfdcParam();
        result.setDimMap(dimMap);
        result.setEntityId(dwEntity.getKey());
        result.setQueryObj(queryObjectImpl);
        return result;
    }

    private List<String> getFormKeyList(String formKey, String formSchemeKey) {
        List formKeyList = FetchTaskUtil.splitToList((String)formKey, (String)";");
        if (CollectionUtils.isEmpty((Collection)formKeyList)) {
            try {
                List formDefines = this.runTimeAuthViewController.queryAllFormDefinesByFormScheme(formSchemeKey);
                if (formDefines == null) {
                    return Collections.emptyList();
                }
                formKeyList = formDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
            }
            catch (Exception e) {
                throw new RuntimeException("\u83b7\u53d6\u5f53\u524d\u65b9\u6848\u4e0b\u6240\u6709\u62a5\u8868\u5931\u8d25\uff1a" + e.getMessage(), e);
            }
        }
        return formKeyList.stream().filter(item -> !"#".equals(item)).collect(Collectors.toList());
    }

    private FormReadWriteAccessData newFormReadWriteAccessData(JtableContext context, List<String> formkeyList) {
        ReadWriteAccessCacheParams readWriteAccessCacheParams = new ReadWriteAccessCacheParams(context, formkeyList, Consts.FormAccessLevel.FORM_DATA_WRITE);
        ReadWriteAccessCacheManager readWriteAccessCacheManager = (ReadWriteAccessCacheManager)BeanUtil.getBean(ReadWriteAccessCacheManager.class);
        readWriteAccessCacheManager.initCache(readWriteAccessCacheParams);
        FormReadWriteAccessData formReadWriteAccessData = new FormReadWriteAccessData(context, Consts.FormAccessLevel.FORM_DATA_WRITE, formkeyList);
        FormReadWriteAccessData accessForms = this.accessProvider.getAccessForms(formReadWriteAccessData, readWriteAccessCacheManager);
        return accessForms;
    }

    private List<String> filterForm(JtableContext context, String fetchSchemeKey, StringBuffer detailLog, List<String> formKeyList) {
        HashSet hasFetchSchemeFormKeySet = new HashSet();
        hasFetchSchemeFormKeySet.addAll(this.fetchSettingService.lisFormKeyBySchemeKey(fetchSchemeKey, context.getFormSchemeKey()));
        hasFetchSchemeFormKeySet.addAll(this.fetchFloatSettingService.listFormKeyBySchemeKey(fetchSchemeKey, context.getFormSchemeKey()));
        formKeyList = formKeyList.stream().filter(formKey -> hasFetchSchemeFormKeySet.contains(formKey)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(formKeyList)) {
            return Collections.emptyList();
        }
        FormReadWriteAccessData accessForms = this.newFormReadWriteAccessData(context, formKeyList);
        List accessFormKeys = accessForms.getFormKeys();
        if (!CollectionUtils.isEmpty((Collection)accessForms.getNoAccessnFormKeys())) {
            StringJoiner noAccessnLog = new StringJoiner(",");
            for (String formKey2 : accessForms.getNoAccessnFormKeys()) {
                FormDefine formDefine = this.runTimeAuthViewController.queryFormById(formKey2);
                String oneFormKeyReason = accessForms.getOneFormKeyReason(formKey2);
                if (StringUtils.isEmpty((String)oneFormKeyReason) || oneFormKeyReason.contains("\u62a5\u8868\u4e0d\u7b26\u5408\u9002\u5e94\u6027\u6761\u4ef6")) continue;
                noAccessnLog.add("\u3010" + formDefine.getTitle() + "\u3011\u56e0\u4e3a\u3010" + oneFormKeyReason + "\u3011\u65e0\u5199\u6743\u9650\uff0c\u8df3\u8fc7\u53d6\u6570\n");
            }
            detailLog.append(noAccessnLog);
            detailLog.append(System.lineSeparator());
        }
        return accessFormKeys;
    }

    private String getBblx(EfdcInfo efdcInfo, String fetchSchemeId) {
        EntityViewData dwEntity = this.jtableParamService.getDwEntity(efdcInfo.getFormSchemeKey());
        JtableContext jtableContext = new JtableContext();
        jtableContext.setDimensionSet(efdcInfo.getDimensionSet());
        jtableContext.setFormSchemeKey(efdcInfo.getFormSchemeKey());
        jtableContext.setFormulaSchemeKey(fetchSchemeId);
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

    private String doBasicCheck() {
        return null;
    }
}

