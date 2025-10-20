/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.RequestSourceTypeEnum
 *  com.jiuqi.bde.common.dto.FloatRegionConfigVO
 *  com.jiuqi.bde.common.dto.fetch.init.FetchFormDTO
 *  com.jiuqi.bde.common.dto.fetch.init.FetchRangResult
 *  com.jiuqi.bde.common.dto.fetch.init.FetchRegionDTO
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.bde.common.vo.FetchSettingVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.AdjustPeriodSettingVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSchemeVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchFloatSettingService
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSchemeNrService
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSettingService
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
 *  com.jiuqi.va.domain.common.JSONUtil
 */
package com.jiuqi.gcreport.bde.fetch.impl.autofetch.service;

import com.jiuqi.bde.common.constant.RequestSourceTypeEnum;
import com.jiuqi.bde.common.dto.FloatRegionConfigVO;
import com.jiuqi.bde.common.dto.fetch.init.FetchFormDTO;
import com.jiuqi.bde.common.dto.fetch.init.FetchRangResult;
import com.jiuqi.bde.common.dto.fetch.init.FetchRegionDTO;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.bde.common.vo.FetchSettingVO;
import com.jiuqi.gcreport.bde.fetch.impl.autofetch.dto.FinancialFetchInitTaskDTO;
import com.jiuqi.gcreport.bde.fetch.impl.common.ExecuteStateEnum;
import com.jiuqi.gcreport.bde.fetch.impl.entity.GcFetchItemTaskLogEO;
import com.jiuqi.gcreport.bde.fetch.impl.enums.FetchExecuteStateEnum;
import com.jiuqi.gcreport.bde.fetch.impl.service.GcFetchTaskLogService;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.AdjustPeriodSettingVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSchemeVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchFloatSettingService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSchemeNrService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSettingService;
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
import com.jiuqi.va.domain.common.JSONUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FetchParamProcessService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FetchParamProcessService.class);
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
    private IBatchCalculateService batchCalculateService;
    @Autowired
    private FetchSchemeNrService fetchSchemeService;
    @Autowired
    private FetchSettingService fetchSettingService;
    @Autowired
    private FetchFloatSettingService fetchFloatSettingService;
    @Autowired
    private GcFetchTaskLogService taskLogService;

    public FinancialFetchInitTaskDTO getFetchParam(EfdcInfo efdcInfo, StringBuilder result, String taskKey, FetchRangResult fetchRangResult) {
        FormSchemeDefine formScheme;
        String requestInstcId = this.taskLogService.saveFetchLog(efdcInfo, taskKey);
        TaskDefine taskDefine = null;
        try {
            taskDefine = this.runTimeAuthViewController.queryTaskDefine(efdcInfo.getTaskKey());
        }
        catch (Exception e) {
            result.append("\u672a\u627e\u5230" + efdcInfo.getTaskKey() + "\u5bf9\u5e94\u7684\u4efb\u52a1\u5b9a\u4e49:" + e.getMessage());
            this.taskLogService.updateFetchLog(efdcInfo, taskKey, requestInstcId, FetchExecuteStateEnum.FAILED.getStateNum(), "\u672a\u627e\u5230" + efdcInfo.getTaskKey() + "\u5bf9\u5e94\u7684\u4efb\u52a1\u5b9a\u4e49:" + e.getMessage());
            return null;
        }
        if (taskDefine == null) {
            result.append("\u672a\u627e\u5230" + efdcInfo.getTaskKey() + "\u5bf9\u5e94\u7684\u4efb\u52a1\u5b9a\u4e49\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u68c0\u67e5\u53d6\u6570\u7528\u6237\u662f\u5426\u5177\u6709\u62a5\u8868\u4efb\u52a1\u6743\u9650");
            this.taskLogService.updateFetchLog(efdcInfo, taskKey, requestInstcId, FetchExecuteStateEnum.FAILED.getStateNum(), "\u672a\u627e\u5230" + efdcInfo.getTaskKey() + "\u5bf9\u5e94\u7684\u4efb\u52a1\u5b9a\u4e49\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u68c0\u67e5\u53d6\u6570\u7528\u6237\u662f\u5177\u5426\u6709\u62a5\u8868\u4efb\u52a1\u6743\u9650");
            return null;
        }
        String orgType = FetchTaskUtil.getOrgTypeByTaskAndCtx((String)efdcInfo.getTaskKey());
        try {
            formScheme = this.runTimeAuthViewController.getFormScheme(efdcInfo.getFormSchemeKey());
        }
        catch (Exception e) {
            result.append("\u672a\u627e\u5230" + efdcInfo.getFormSchemeKey() + "\u5bf9\u5e94\u7684\u62a5\u8868\u65b9\u6848:" + e.getMessage());
            this.taskLogService.updateFetchLog(efdcInfo, taskKey, requestInstcId, FetchExecuteStateEnum.FAILED.getStateNum(), "\u672a\u627e\u5230" + efdcInfo.getFormSchemeKey() + "\u5bf9\u5e94\u7684\u62a5\u8868\u65b9\u6848:" + e.getMessage());
            return null;
        }
        JtableContext context = this.newJtableContext(efdcInfo);
        List entityList = this.jtableParamService.getEntityList(formScheme.getKey());
        EntityViewData masterEntity = this.getMasterEntity(entityList);
        if (masterEntity == null) {
            result.append("\u672a\u627e\u5230\u62a5\u8868\u65b9\u6848" + efdcInfo.getFormSchemeKey() + "\u5bf9\u5e94\u7684\u4e3b\u4f53\u89c6\u56fe\u4fe1\u606f,\u8bf7\u68c0\u67e5\u62a5\u8868\u65b9\u6848\u914d\u7f6e");
            this.taskLogService.updateFetchLog(efdcInfo, taskKey, requestInstcId, FetchExecuteStateEnum.FAILED.getStateNum(), "\u672a\u627e\u5230\u62a5\u8868\u65b9\u6848" + efdcInfo.getFormSchemeKey() + "\u5bf9\u5e94\u7684\u4e3b\u4f53\u89c6\u56fe\u4fe1\u606f,\u8bf7\u68c0\u67e5\u62a5\u8868\u65b9\u6848\u914d\u7f6e");
            return null;
        }
        EntityQueryByKeyInfo entityQueryByKeyInfo = this.newEntityQueryByKeyInfo(efdcInfo, context, masterEntity);
        EntityByKeyReturnInfo queryEntityDataByKey = this.jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo);
        efdcInfo.getVariableMap().put("UNIT_NAME", queryEntityDataByKey.getEntity().getTitle());
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((JtableContext)context);
        FetchEfdcParam efdcParam = this.getEfdcParam(formScheme, dimensionValueSet, efdcInfo);
        FormulaSchemeDefine extractformulaScheme = this.efdcConfigService.getSoluctionByDimensions(efdcParam.getQueryObj(), efdcParam.getDimMap(), efdcParam.getEntityId());
        if (extractformulaScheme == null || StringUtils.isEmpty((String)extractformulaScheme.getKey())) {
            result.append(queryEntityDataByKey.getEntity().getTitle() + "\u3010\u8d22\u52a1\u63d0\u53d6\u65b9\u6848\u672a\u914d\u7f6e\u3011");
            this.taskLogService.updateFetchLog(efdcInfo, taskKey, requestInstcId, FetchExecuteStateEnum.FAILED.getStateNum(), queryEntityDataByKey.getEntity().getTitle() + "\u3010\u8d22\u52a1\u63d0\u53d6\u65b9\u6848\u672a\u914d\u7f6e\u3011");
            return null;
        }
        List formulaSchemeDefineList = this.efdcConfigService.getRPTFormulaScheme(efdcParam.getQueryObj(), efdcParam.getDimMap(), efdcParam.getEntityId());
        String newLine = System.lineSeparator();
        StringBuffer detailLog = new StringBuffer();
        detailLog.append(String.format("\u5355\u4f4d\uff1a%s ", queryEntityDataByKey.getEntity().getTitle()));
        detailLog.append(newLine);
        detailLog.append(String.format("\u65f6\u671f\uff1a%s ", efdcInfo.getParas().get("DATATIME").toString()));
        detailLog.append(newLine);
        List<String> formKeyList = this.getFormKeyList(efdcInfo.getFormKey(), context.getFormSchemeKey());
        List<String> calcFormKeys = this.getCalcFormKeyList(context, formKeyList);
        List<String> fetchFormKeyList = this.filterForm(context, extractformulaScheme.getKey(), detailLog, formKeyList);
        if (CollectionUtils.isEmpty(fetchFormKeyList)) {
            if (!CollectionUtils.isEmpty(calcFormKeys)) {
                this.calcAll(efdcInfo, calcFormKeys, formulaSchemeDefineList);
            }
            result.append("\u6ca1\u6709\u627e\u5230\u53ef\u4ee5\u53d6\u6570\u7684\u62a5\u8868\uff0c\u53d6\u6570\u5b8c\u6210\uff01");
            this.taskLogService.updateFetchLog(efdcInfo, taskKey, requestInstcId, FetchExecuteStateEnum.SUCCESS.getStateNum(), "\u6ca1\u6709\u627e\u5230\u53ef\u4ee5\u53d6\u6570\u7684\u62a5\u8868\uff0c\u53d6\u6570\u5b8c\u6210\uff01");
            return null;
        }
        FinancialFetchInitTaskDTO fetchTaskDTO = this.convertFetchInitTaskDTO(efdcInfo, requestInstcId, fetchFormKeyList, extractformulaScheme.getKey(), dimensionValueSet, entityList, taskDefine, formScheme, fetchRangResult);
        this.saveItemTaskLogs(fetchTaskDTO.getFetchForms(), requestInstcId);
        fetchTaskDTO.setRpUnitType(orgType);
        fetchTaskDTO.setEfdcInfo(efdcInfo);
        fetchTaskDTO.setForms(calcFormKeys);
        fetchTaskDTO.setFormulaSchemeList(formulaSchemeDefineList);
        return fetchTaskDTO;
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
        entityQueryByKeyInfo.setEntityViewKey(formScheme.getDw());
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
        return formKeyList;
    }

    private List<String> getCalcFormKeyList(JtableContext context, List<String> formKeyList) {
        FormReadWriteAccessData accessForms = this.newFormReadWriteAccessData(context, formKeyList);
        List accessFormKeys = accessForms.getFormKeys();
        return accessFormKeys;
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

    public void calcAll(EfdcInfo efdcInfo, List<String> forms, List<FormulaSchemeDefine> formulaSchemeList) {
        if (CollectionUtils.isEmpty(formulaSchemeList)) {
            LOGGER.info("\u6ca1\u6709\u516c\u5f0f\u65b9\u6848\uff0c\u65e0\u6cd5\u8fd0\u7b97\uff01");
            return;
        }
        if (CollectionUtils.isEmpty(forms)) {
            return;
        }
        BatchCalculateInfo batchCalculateInfo = new BatchCalculateInfo();
        batchCalculateInfo.setTaskKey(efdcInfo.getTaskKey());
        batchCalculateInfo.setDimensionSet(efdcInfo.getDimensionSet());
        batchCalculateInfo.setVariableMap(efdcInfo.getVariableMap());
        HashMap<String, Object> formulas = new HashMap<String, Object>();
        for (String formKey : forms) {
            formulas.put(formKey, null);
        }
        batchCalculateInfo.setFormulas(formulas);
        StringJoiner formulaSchemeKeys = new StringJoiner(";");
        for (FormulaSchemeDefine formulaSchemeDefine : formulaSchemeList) {
            formulaSchemeKeys.add(formulaSchemeDefine.getKey());
        }
        batchCalculateInfo.setFormulaSchemeKey(formulaSchemeKeys.toString());
        batchCalculateInfo.setFormSchemeKey(efdcInfo.getFormSchemeKey());
        this.batchCalculateService.batchCalculateForm(batchCalculateInfo);
    }

    /*
     * WARNING - void declaration
     */
    private FinancialFetchInitTaskDTO convertFetchInitTaskDTO(EfdcInfo efdcInfo, String requestInstcId, List<String> accessFormKeys, String fetchSchemeId, DimensionValueSet dimensionValueSet, List<EntityViewData> entityList, TaskDefine taskDefine, FormSchemeDefine formScheme, FetchRangResult fetchRangResult) {
        void var20_25;
        FinancialFetchInitTaskDTO fetchTaskDTO = new FinancialFetchInitTaskDTO();
        String unitCode = efdcInfo.getParas().get("UnitCode").toString();
        String dataPeriod = efdcInfo.getParas().get("DATATIME").toString();
        fetchTaskDTO.setRequestSourceType(RequestSourceTypeEnum.FINANCIAL_AUTO_FETCH.getCode());
        fetchTaskDTO.setRequestRunnerId(requestInstcId);
        fetchTaskDTO.setUnitCode(unitCode);
        fetchTaskDTO.setIncludeUncharged(efdcInfo.isContainsUnbVou());
        fetchTaskDTO.setPeriodScheme(dataPeriod);
        String[] times = FetchTaskUtil.parseDataTime((String)dataPeriod);
        fetchTaskDTO.setStartDateStr(times[0]);
        fetchTaskDTO.setEndDateStr(times[1]);
        fetchTaskDTO.setFetchSchemeId(fetchSchemeId);
        FetchSchemeVO fetchScheme = this.fetchSchemeService.getFetchScheme(fetchSchemeId);
        Assert.isNotNull((Object)fetchScheme, (String)String.format("\u6839\u636e\u53d6\u6570\u65b9\u6848\u6807\u8bc6\u3010%1$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u53d6\u6570\u65b9\u6848\uff0c\u8bf7\u68c0\u67e5\u53d6\u6570\u65b9\u6848\u914d\u7f6e", fetchSchemeId), (Object[])new Object[0]);
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
        fetchTaskDTO.setCurrency(efdcInfo.getDimensionSet().get("MD_CURRENCY") == null ? null : ((DimensionValue)efdcInfo.getDimensionSet().get("MD_CURRENCY")).getValue());
        fetchTaskDTO.setTaskTitle(taskDefine.getTitle());
        ContextUser user = NpContextHolder.getContext().getUser();
        if (user != null) {
            fetchTaskDTO.setUserName(StringUtils.isEmpty((String)user.getName()) ? user.getFullname() : user.getName());
        }
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
        dimMap.put("ORGTYPE", var20_25);
        fetchTaskDTO.setOtherEntity(dimMap);
        fetchTaskDTO.setDimensionSetStr(JSONUtil.toJSONString((Object)efdcInfo.getDimensionSet()));
        Map formKey2RegionKeysMap = fetchRangResult.getFormRegionsMap();
        Map formKey2DataLinkKeysMap = fetchRangResult.getFromDataLinkMap();
        ArrayList<FetchFormDTO> formFormList = new ArrayList<FetchFormDTO>(accessFormKeys.size());
        for (String formKey : accessFormKeys) {
            DataRegionDefine regionDefine;
            Set currFromRegionIdSet;
            FetchRegionDTO fetchRegion = null;
            FormDefine formDefine = this.runTimeAuthViewController.queryFormById(formKey);
            FetchFormDTO fetchForm = new FetchFormDTO(formDefine.getKey(), formDefine.getFormCode(), formDefine.getTitle());
            Map<String, DataRegionDefine> regionMap = this.dataRegionService.getDataRegionsInForm(formKey).stream().collect(Collectors.toMap(IBaseMetaItem::getKey, item -> item, (k1, k2) -> k2));
            FetchSettingCond fetchSettingCond = new FetchSettingCond(fetchSchemeId, formScheme.getKey(), formKey);
            if (formKey2DataLinkKeysMap.containsKey(formKey)) {
                currFromRegionIdSet = this.fetchSettingService.listFetchSettingByFormId(fetchSettingCond).stream().map(FetchSettingVO::getRegionId).collect(Collectors.toSet());
                assert (currFromRegionIdSet.size() == 1);
                for (String regionId : currFromRegionIdSet) {
                    regionDefine = regionMap.get(regionId);
                    fetchRegion = new FetchRegionDTO(regionDefine.getKey(), regionDefine.getKey(), regionDefine.getTitle());
                }
            } else {
                if (!formKey2RegionKeysMap.containsKey(formKey)) continue;
                currFromRegionIdSet = this.fetchFloatSettingService.listFetchFloatSettingByFormId(fetchSettingCond).stream().map(FloatRegionConfigVO::getRegionId).collect(Collectors.toSet());
                Set mainCodeMatchedRegionIdSet = (Set)formKey2RegionKeysMap.get(formKey);
                for (String currFormRegionId : currFromRegionIdSet) {
                    if (!mainCodeMatchedRegionIdSet.contains(currFormRegionId)) continue;
                    regionDefine = regionMap.get(currFormRegionId);
                    fetchRegion = new FetchRegionDTO(regionDefine.getKey(), regionDefine.getKey(), regionDefine.getTitle());
                }
            }
            assert (fetchRegion != null);
            fetchForm.addFetchRegion(fetchRegion);
            formFormList.add(fetchForm);
        }
        fetchTaskDTO.setFetchForms(formFormList);
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

    private void saveItemTaskLogs(List<FetchFormDTO> fetchForms, String requestTaskId) {
        ArrayList<GcFetchItemTaskLogEO> itemTaskLogList = new ArrayList<GcFetchItemTaskLogEO>();
        for (FetchFormDTO fetchForm : fetchForms) {
            for (FetchRegionDTO fetchRegion : fetchForm.getFetchRegions()) {
                GcFetchItemTaskLogEO itemTaskLogEO = new GcFetchItemTaskLogEO();
                itemTaskLogEO.setId(UUIDUtils.newHalfGUIDStr());
                itemTaskLogEO.setFetchTaskId(requestTaskId);
                itemTaskLogEO.setFormId(fetchForm.getId());
                itemTaskLogEO.setRegionId(fetchRegion.getId());
                itemTaskLogEO.setExecuteState(ExecuteStateEnum.CREATED.getCode());
                itemTaskLogEO.setProcess(0.0);
                itemTaskLogEO.setProcessTime(new Date());
                itemTaskLogEO.setResultContent("\u53d6\u6570\u4efb\u52a1\u6b63\u5728\u6267\u884c");
                itemTaskLogEO.setFetchStatus(0);
                itemTaskLogList.add(itemTaskLogEO);
            }
        }
        this.taskLogService.insertItemTaskLogs(itemTaskLogList);
    }
}

