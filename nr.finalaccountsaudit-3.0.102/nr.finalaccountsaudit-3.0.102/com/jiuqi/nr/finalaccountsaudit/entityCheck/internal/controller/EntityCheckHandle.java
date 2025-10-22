/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IExpressionEvaluator
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.definition.common.TaskLinkMatchingType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.definitionext.taskExtConfig.internal.controller.TaskExtConfigController
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fmdm.FMDMDataDTO
 *  com.jiuqi.nr.fmdm.IFMDMData
 *  com.jiuqi.nr.fmdm.IFMDMDataService
 *  com.jiuqi.nr.fmdm.domain.EntityInfoDO
 *  com.jiuqi.nr.fmdm.internal.dto.QueryParamDTO
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IJtableDataEngineService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.period.common.rest.PeriodDataSelectObject
 *  com.jiuqi.nr.period.common.utils.PeriodException
 *  com.jiuqi.nr.period.common.utils.PeriodPropertyGroup
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nr.period.common.utils.StringUtils
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.internal.controller;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IExpressionEvaluator;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.definition.common.TaskLinkMatchingType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definitionext.taskExtConfig.internal.controller.TaskExtConfigController;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.finalaccountsaudit.common.DataQueryHelper;
import com.jiuqi.nr.finalaccountsaudit.common.EntityQueryHelper;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.AnalysisTableData;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.ApiJSONUtil;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.CheckConfigurationContent;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EntityCheckUpAnalysis;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EntityCheckUpData;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EntityCheckUpDecrease;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EntityCheckUpKey;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EntityCheckUpRecord;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EntityCheckUpType;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EntityCheckVersionObjectInfo;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EnumStructure;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.JSYYTableData;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.TableColums;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.XBYSTableData;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.dto.EntityCheckUpDTO;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.internal.controller.FMDMDataMapController;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.internal.controller.FieldContext;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.internal.controller.FieldContextWrapper;
import com.jiuqi.nr.fmdm.FMDMDataDTO;
import com.jiuqi.nr.fmdm.IFMDMData;
import com.jiuqi.nr.fmdm.IFMDMDataService;
import com.jiuqi.nr.fmdm.domain.EntityInfoDO;
import com.jiuqi.nr.fmdm.internal.dto.QueryParamDTO;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableDataEngineService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.period.common.rest.PeriodDataSelectObject;
import com.jiuqi.nr.period.common.utils.PeriodException;
import com.jiuqi.nr.period.common.utils.PeriodPropertyGroup;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(value="prototype")
public class EntityCheckHandle {
    private static final Logger log = LoggerFactory.getLogger(EntityCheckHandle.class);
    @Autowired
    private EntityQueryHelper entityQueryHelper;
    @Autowired
    private IEntityMetaService metaService;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private DataModelService modelService;
    @Autowired
    private IFMDMDataService fmdmDataService;
    @Autowired
    TaskExtConfigController taskExtConfigController;
    @Resource
    private IEntityViewRunTimeController entityViewCtrl;

    public EntityCheckVersionObjectInfo getVersionObject(String taskKey, String formSchemeKey, String period, int matchingType, String expressionFormula, boolean isAssociated) throws Exception {
        EntityCheckVersionObjectInfo versionObject = new EntityCheckVersionObjectInfo();
        versionObject.setTaskKey(taskKey);
        versionObject.setFromSchemeKey(formSchemeKey);
        versionObject.setPeriod(period);
        versionObject.setMatchingType(matchingType);
        versionObject.setExpressionFormula(expressionFormula);
        EntityViewData masterEntityView = this.jtableParamService.getDwEntity(formSchemeKey);
        EntityViewData periodEntityView = this.jtableParamService.getDataTimeEntity(formSchemeKey);
        if (null == masterEntityView || null == periodEntityView) {
            return null;
        }
        versionObject.setMasterEntityView(masterEntityView);
        versionObject.setPeriodEntityView(periodEntityView);
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue(periodEntityView.getDimensionName(), (Object)period);
        QueryParamDTO fMDMDataDTO = new QueryParamDTO();
        fMDMDataDTO.setFormSchemeKey(formSchemeKey);
        fMDMDataDTO.setDimensionValueSet(dimensionValueSet);
        fMDMDataDTO.setFilter(true);
        if (isAssociated) {
            com.jiuqi.np.dataengine.executors.ExecutorContext context = new com.jiuqi.np.dataengine.executors.ExecutorContext(this.dataDefinitionRuntimeController);
            context.getVariableManager().add(new Variable("associatedTaskKey", "\u5355\u673a\u7248\u83b7\u53d6\u6838\u5bf9\u4efb\u52a1key", 6, (Object)taskKey));
            fMDMDataDTO.setContext((IContext)context);
        }
        List dataList = this.fmdmDataService.list((FMDMDataDTO)fMDMDataDTO);
        HashMap<String, IFMDMData> dataMap = new HashMap<String, IFMDMData>(dataList.size());
        if (dataList != null && dataList.size() > 0) {
            for (IFMDMData data : dataList) {
                dataMap.put(data.getValue("CODE").getAsString(), data);
            }
        }
        versionObject.setFmdmDataMap(dataMap);
        IDataAccessProvider accessProvider = (IDataAccessProvider)SpringBeanProvider.getBean(IDataAccessProvider.class);
        IExpressionEvaluator evaluator = accessProvider.newExpressionEvaluator();
        JtableContext executorTableContext = new JtableContext();
        executorTableContext.setTaskKey(taskKey);
        executorTableContext.setFormSchemeKey(formSchemeKey);
        executorTableContext.setDimensionSet(DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet));
        IJtableDataEngineService tableDataEngineService = (IJtableDataEngineService)SpringBeanProvider.getBean(IJtableDataEngineService.class);
        com.jiuqi.np.dataengine.executors.ExecutorContext executorContext = tableDataEngineService.getExecutorContext(executorTableContext);
        if (isAssociated) {
            executorContext.getVariableManager().add(new Variable("associatedTaskKey", "\u5355\u673a\u7248\u83b7\u53d6\u6838\u5bf9\u4efb\u52a1key", 6, (Object)taskKey));
        }
        ArrayList<String> list = new ArrayList<String>();
        list.add(expressionFormula);
        Map expressionFormulaMap = evaluator.evalBatch(list, executorContext, dimensionValueSet);
        versionObject.setExpressionFormulaMap(expressionFormulaMap);
        IEntityModel entityModel = this.metaService.getEntityModel(masterEntityView.getKey());
        versionObject.setEntityModel(entityModel);
        TableModelDefine tableModelDefine = this.metaService.getTableModel(masterEntityView.getKey());
        versionObject.setTableModelDefine(tableModelDefine);
        EntityViewDefine entityView = this.entityQueryHelper.getDwEntityView(formSchemeKey);
        versionObject.setEntityView(entityView);
        IEntityTable currentTable = null;
        try {
            ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
            ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewCtrl, formSchemeKey);
            context.setEnv((IFmlExecEnvironment)environment);
            if (isAssociated) {
                context.getVariableManager().add(new Variable("associatedTaskKey", "\u5355\u673a\u7248\u83b7\u53d6\u6838\u5bf9\u4efb\u52a1key", 6, (Object)taskKey));
            }
            IEntityQuery entityQuery = this.entityQueryHelper.getEntityQuery(entityView, period);
            context.setVarDimensionValueSet(entityQuery.getMasterKeys());
            currentTable = entityQuery.executeReader((IContext)context);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
        versionObject.setEntityTable(currentTable);
        return versionObject;
    }

    public String getTitleInfo(String taskKey, String formSchemeKey, String period) {
        String title = "";
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        DefaultPeriodAdapter periodAdapter = new DefaultPeriodAdapter();
        String periodTitle = periodAdapter.getPeriodTitle(period);
        title = taskDefine.getTitle() + " | " + formSchemeDefine.getTitle() + " | " + periodTitle;
        return title;
    }

    public String getCurrentTitle(IRunTimeViewController runTimeViewController, PeriodEngineService periodEngineService, String taskKey, String formSchemeKey, String period) throws JQException {
        String title = "";
        TaskDefine currentTaskDefine = runTimeViewController.queryTaskDefine(taskKey);
        FormSchemeDefine currentFormSchemeDefine = runTimeViewController.getFormScheme(formSchemeKey);
        List<PeriodDataSelectObject> periodDataSelectObjects = this.queryPeriodDataByPeriodKey(periodEngineService, currentTaskDefine.getDateTime());
        String periodTitle = periodDataSelectObjects.stream().filter(periodDataSelectObject -> periodDataSelectObject.getCode().equals(period)).map(PeriodDataSelectObject::getTitle).findFirst().orElse(null);
        if (!StringUtils.isNotEmpty((String)periodTitle)) {
            DefaultPeriodAdapter periodAdapter = new DefaultPeriodAdapter();
            periodTitle = periodAdapter.getPeriodTitle(period);
        }
        title = currentTaskDefine.getTitle() + " | " + currentFormSchemeDefine.getTitle() + " | " + periodTitle;
        return title;
    }

    private List<PeriodDataSelectObject> queryPeriodDataByPeriodKey(PeriodEngineService periodEngineService, String id) throws JQException {
        if (com.jiuqi.nr.period.common.utils.StringUtils.isEmpty((String)id)) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_101);
        }
        try {
            IPeriodEntityAdapter periodAdapter = periodEngineService.getPeriodAdapter();
            IPeriodProvider periodProvider = periodAdapter.getPeriodProvider(id);
            IPeriodEntity periodEntity = periodAdapter.getPeriodEntity(id);
            PeriodPropertyGroup periodPropertyGroup = periodEntity.getPeriodPropertyGroup();
            List periodItems = periodProvider.getPeriodItems();
            if (periodEntity.getPeriodType().equals((Object)PeriodType.CUSTOM)) {
                return this.periodRowToPeriodDataSelectObject(periodPropertyGroup, periodItems);
            }
            ArrayList<IPeriodRow> titleModifyList = new ArrayList<IPeriodRow>();
            for (IPeriodRow periodItem : periodItems) {
                if (periodItem.getTitle().equals(PeriodUtils.getDateStrFromPeriod((String)periodItem.getCode()))) continue;
                titleModifyList.add(periodItem);
            }
            return titleModifyList.stream().map(PeriodDataSelectObject::defineToObject).collect(Collectors.toList());
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_106, (Throwable)e);
        }
    }

    private List<PeriodDataSelectObject> periodRowToPeriodDataSelectObject(PeriodPropertyGroup periodPropertyGroup, List<IPeriodRow> periodItems) {
        List<Object> periodDataSelectObjects = new ArrayList();
        if (periodPropertyGroup == null) {
            periodDataSelectObjects = periodItems.stream().map(PeriodDataSelectObject::defineToObject).collect(Collectors.toList());
        } else {
            switch (periodPropertyGroup) {
                case PERIOD_GROUP_BY_YEAR: {
                    periodDataSelectObjects = periodItems.stream().map(a -> {
                        PeriodDataSelectObject periodDataSelectObject = PeriodDataSelectObject.defineToObject((IPeriodRow)a);
                        periodDataSelectObject.setGroup(a.getYear() + PeriodPropertyGroup.PERIOD_GROUP_BY_YEAR.getGroupName());
                        return periodDataSelectObject;
                    }).collect(Collectors.toList());
                    break;
                }
                default: {
                    periodDataSelectObjects = periodItems.stream().map(PeriodDataSelectObject::defineToObject).collect(Collectors.toList());
                }
            }
        }
        return periodDataSelectObjects;
    }

    public String getContrastTitle(IRunTimeViewController runTimeViewController, String associatedTaskKey, String associatedFormSchemeKey, String associatedperiod) {
        String title = "";
        TaskDefine currentTaskDefine = runTimeViewController.queryTaskDefine(associatedTaskKey);
        if (currentTaskDefine != null) {
            FormSchemeDefine currentFormSchemeDefine = runTimeViewController.getFormScheme(associatedFormSchemeKey);
            DefaultPeriodAdapter periodAdapter = new DefaultPeriodAdapter();
            String associatedperiodTitle = periodAdapter.getPeriodTitle(associatedperiod);
            title = currentTaskDefine.getTitle() + " | " + currentFormSchemeDefine.getTitle() + " | " + associatedperiodTitle;
        }
        return title;
    }

    public boolean checkUpTableExist(String taskKey, String formSchemeKey) throws Exception {
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        int isDestry = this.dataDefinitionRuntimeController.checkTableDefineIsDestry(this.getTableName(formSchemeDefine));
        return isDestry > 0;
    }

    public Map<Integer, Map<String, EntityCheckUpRecord>> getRecords(FieldContextWrapper fieldContextWrapper, EntityCheckVersionObjectInfo currentVersionObjectInfo, EntityCheckVersionObjectInfo contrastVersionObjectInfo, Map<String, EntityCheckUpDecrease> checkUpRecordInDb, CheckConfigurationContent configurationContent, FMDMDataMapController curfmdmDataMapController, FMDMDataMapController lastfmdmDataMapController) throws Exception {
        return this.getRecords(fieldContextWrapper, currentVersionObjectInfo, contrastVersionObjectInfo, checkUpRecordInDb, configurationContent, null, curfmdmDataMapController, lastfmdmDataMapController);
    }

    public Map<Integer, Map<String, EntityCheckUpRecord>> getRecords(FieldContextWrapper fieldContextWrapper, EntityCheckVersionObjectInfo currentVersionObjectInfo, EntityCheckVersionObjectInfo contrastVersionObjectInfo, Map<String, EntityCheckUpDecrease> checkUpRecordInDb, CheckConfigurationContent configurationContent, Map<String, String> sndmFormulaValues, FMDMDataMapController curfmdmDataMapController, FMDMDataMapController lastfmdmDataMapController) throws Exception {
        Map<Integer, Map<String, EntityCheckUpRecord>> records = this.InitEntityCheckUpRecordObj();
        HashMap<String, IFMDMData> sameSndm = new HashMap<String, IFMDMData>();
        HashMap<String, EntityCheckUpRecord> decreaseNameRecord = new HashMap<String, EntityCheckUpRecord>();
        HashSet<String> lastSndmToExpValue = new HashSet<String>();
        Map<String, IFMDMData> lastFMDMDataMap = lastfmdmDataMapController.getEntityMaps();
        block12: for (Map.Entry<String, IFMDMData> entry : lastFMDMDataMap.entrySet()) {
            String string = entry.getKey();
            IFMDMData value = entry.getValue();
            TaskLinkMatchingType matchType = TaskLinkMatchingType.forValue((int)configurationContent.getMatchingInfo().getMatchingType());
            switch (matchType) {
                case MATCHING_TYPE_PRIMARYKEY: {
                    Map<String, IFMDMData> codeMaps = curfmdmDataMapController.getBizKeyMaps();
                    String code = value.getValue("CODE").getAsString();
                    if (codeMaps.isEmpty() || codeMaps.containsKey(code)) break;
                    int type = EntityCheckUpType.DECREASE.getIndex();
                    if (records.get(type).containsKey(string)) continue block12;
                    EntityCheckUpRecord record = this.ParseRowDataToRecord(fieldContextWrapper.getLstfieldContext(), EntityCheckUpType.DECREASE, configurationContent, value, contrastVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                    records.get(type).put(string, record);
                    if (decreaseNameRecord.containsKey(record.GetCheckUpData().GetDWMC())) continue block12;
                    decreaseNameRecord.put(record.GetCheckUpData().GetDWMC(), record);
                    break;
                }
                case MATCHING_TYPE_CODE: {
                    Map<String, IFMDMData> orgCodeMaps = curfmdmDataMapController.getCodeMaps();
                    String orgCode = value.getValue("ORGCODE").getAsString();
                    if (orgCodeMaps.isEmpty() || orgCodeMaps.containsKey(orgCode)) break;
                    int type = EntityCheckUpType.DECREASE.getIndex();
                    if (records.get(type).containsKey(string)) continue block12;
                    EntityCheckUpRecord record = this.ParseRowDataToRecord(fieldContextWrapper.getLstfieldContext(), EntityCheckUpType.DECREASE, configurationContent, value, contrastVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                    records.get(type).put(string, record);
                    if (decreaseNameRecord.containsKey(record.GetCheckUpData().GetDWMC())) continue block12;
                    decreaseNameRecord.put(record.GetCheckUpData().GetDWMC(), record);
                    break;
                }
                case MATCHING_TYPE_TITLE: {
                    Map<String, IFMDMData> dwmcMaps = curfmdmDataMapController.getDwmcMaps();
                    String name = value.getValue("NAME").getAsString();
                    if (dwmcMaps.isEmpty() || dwmcMaps.containsKey(name)) break;
                    int type = EntityCheckUpType.DECREASE.getIndex();
                    if (records.get(type).containsKey(string)) continue block12;
                    EntityCheckUpRecord record = this.ParseRowDataToRecord(fieldContextWrapper.getLstfieldContext(), EntityCheckUpType.DECREASE, configurationContent, value, contrastVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                    records.get(type).put(string, record);
                    if (decreaseNameRecord.containsKey(record.GetCheckUpData().GetDWMC())) continue block12;
                    decreaseNameRecord.put(record.GetCheckUpData().GetDWMC(), record);
                    break;
                }
                case FORM_TYPE_EXPRESSION: {
                    int type;
                    Map<String, String> lastExpValues = lastfmdmDataMapController.getCodeToExpValues();
                    String expValue = lastExpValues.get(string);
                    Map<String, IFMDMData> curExpToFmdmData = curfmdmDataMapController.getExpValueToFmdmData();
                    if (curExpToFmdmData.isEmpty() || curExpToFmdmData.containsKey(expValue) || records.get(type = EntityCheckUpType.DECREASE.getIndex()).containsKey(string)) break;
                    EntityCheckUpRecord record = this.ParseRowDataToRecord(fieldContextWrapper.getLstfieldContext(), EntityCheckUpType.DECREASE, configurationContent, value, contrastVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                    records.get(type).put(string, record);
                    if (decreaseNameRecord.containsKey(record.GetCheckUpData().GetDWMC())) break;
                    decreaseNameRecord.put(record.GetCheckUpData().GetDWMC(), record);
                }
            }
        }
        Map<String, IFMDMData> curFMDMDataMap = curfmdmDataMapController.getEntityMaps();
        if (configurationContent.getSndm() != null) {
            if (sndmFormulaValues == null) {
                sndmFormulaValues = this.getExpFormulaValues(contrastVersionObjectInfo, configurationContent.getSndmFormula());
            }
            for (String string : sndmFormulaValues.values()) {
                lastSndmToExpValue.add(string);
            }
        }
        block14: for (Map.Entry<String, IFMDMData> entry : curFMDMDataMap.entrySet()) {
            String key = entry.getKey();
            IFMDMData value = entry.getValue();
            TaskLinkMatchingType matchType = TaskLinkMatchingType.forValue((int)configurationContent.getMatchingInfo().getMatchingType());
            if (configurationContent.getSndm() != null) {
                String sndm;
                String string = sndm = value.getValue(fieldContextWrapper.getCurfieldContext().getSNDM().getCode()) == null ? null : value.getValue(fieldContextWrapper.getCurfieldContext().getSNDM().getCode()).getAsString();
                if (!StringUtils.isEmpty(sndm)) {
                    IFMDMData sndmRow = (IFMDMData)sameSndm.get(sndm);
                    if (sndmRow == null) {
                        sameSndm.put(sndm, value);
                    } else {
                        String sndmRowCode = sndmRow.getValue("CODE") == null ? null : sndmRow.getValue("CODE").getAsString();
                        int type = EntityCheckUpType.SAME_SN_CODE.getIndex();
                        if (!records.get(type).containsKey(key)) {
                            EntityCheckUpRecord record = this.ParseRowDataToRecord(fieldContextWrapper.getCurfieldContext(), EntityCheckUpType.SAME_SN_CODE, configurationContent, value, currentVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                            records.get(type).put(key, record);
                        }
                        if (!records.get(type).containsKey(sndmRowCode)) {
                            EntityCheckUpRecord record = this.ParseRowDataToRecord(fieldContextWrapper.getCurfieldContext(), EntityCheckUpType.SAME_SN_CODE, configurationContent, value, currentVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                            records.get(type).put(key, record);
                        }
                    }
                }
            }
            switch (matchType) {
                case MATCHING_TYPE_PRIMARYKEY: {
                    int type;
                    String sndm;
                    String lastRowKey;
                    String code;
                    Map<String, IFMDMData> codeMaps = lastfmdmDataMapController.getBizKeyMaps();
                    String string = code = value.getValue("CODE") == null ? null : value.getValue("CODE").getAsString();
                    if (!codeMaps.isEmpty() && !codeMaps.containsKey(code)) {
                        String xbys;
                        int type2 = EntityCheckUpType.INCREASE.getIndex();
                        if (!records.get(type2).containsKey(key)) {
                            EntityCheckUpRecord record = this.ParseRowDataToRecord(fieldContextWrapper.getCurfieldContext(), EntityCheckUpType.INCREASE, configurationContent, value, currentVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                            records.get(type2).put(key, record);
                        }
                        if (configurationContent.getSndm() != null && configurationContent.getXbys() != null) {
                            String sndm2;
                            String xbys2 = value.getValue(fieldContextWrapper.getCurfieldContext().getXBYS().getCode()) == null ? null : value.getValue(fieldContextWrapper.getCurfieldContext().getXBYS().getCode()).getAsString();
                            String string2 = sndm2 = value.getValue(fieldContextWrapper.getCurfieldContext().getSNDM().getCode()) == null ? null : value.getValue(fieldContextWrapper.getCurfieldContext().getSNDM().getCode()).getAsString();
                            if (this.getXbysLxCode().equals(xbys2)) {
                                type2 = EntityCheckUpType.INCREASE_HAVE_SNDM.getIndex();
                                if (records.get(type2).containsKey(key)) continue block14;
                                EntityCheckUpRecord record = this.ParseRowDataToRecord(fieldContextWrapper.getCurfieldContext(), EntityCheckUpType.INCREASE_HAVE_SNDM, configurationContent, value, currentVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                                records.get(type2).put(key, record);
                                break;
                            }
                            if (!StringUtils.isNotEmpty((String)sndm2) || records.get(type2 = EntityCheckUpType.INCREASE_HAVE_SNDM.getIndex()).containsKey(key)) continue block14;
                            EntityCheckUpRecord record = this.ParseRowDataToRecord(fieldContextWrapper.getCurfieldContext(), EntityCheckUpType.INCREASE_HAVE_SNDM, configurationContent, value, currentVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                            records.get(type2).put(key, record);
                            break;
                        }
                        if (configurationContent.getSndm() != null) {
                            String sndm3 = value.getValue(fieldContextWrapper.getCurfieldContext().getSNDM().getCode()) == null ? null : value.getValue(fieldContextWrapper.getCurfieldContext().getSNDM().getCode()).getAsString();
                            if (!StringUtils.isNotEmpty(sndm3) || records.get(type2 = EntityCheckUpType.INCREASE_HAVE_SNDM.getIndex()).containsKey(key)) continue block14;
                            EntityCheckUpRecord record = this.ParseRowDataToRecord(fieldContextWrapper.getCurfieldContext(), EntityCheckUpType.INCREASE_HAVE_SNDM, configurationContent, value, currentVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                            records.get(type2).put(key, record);
                            break;
                        }
                        if (configurationContent.getXbys() == null) continue block14;
                        String string3 = xbys = value.getValue(fieldContextWrapper.getCurfieldContext().getXBYS().getCode()) == null ? null : value.getValue(fieldContextWrapper.getCurfieldContext().getXBYS().getCode()).getAsString();
                        if (!this.getXbysLxCode().equals(xbys) || records.get(type2 = EntityCheckUpType.INCREASE_HAVE_SNDM.getIndex()).containsKey(key)) continue block14;
                        EntityCheckUpRecord record = this.ParseRowDataToRecord(fieldContextWrapper.getCurfieldContext(), EntityCheckUpType.INCREASE_HAVE_SNDM, configurationContent, value, currentVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                        records.get(type2).put(key, record);
                        break;
                    }
                    String curRowKey = ((EntityInfoDO)value).getEntityRow().getParentEntityKey();
                    if (!curRowKey.equals(lastRowKey = ((EntityInfoDO)codeMaps.get(code)).getEntityRow().getParentEntityKey())) {
                        int type3;
                        EntityCheckHandle.checkNodeStatus(curRowKey, matchType, curfmdmDataMapController, lastfmdmDataMapController);
                        EntityCheckHandle.checkNodeStatus(lastRowKey, matchType, curfmdmDataMapController, lastfmdmDataMapController);
                        if ((NodeStatus.CONTINUOUS.equals((Object)EntityCheckHandle.checkNodeStatus(curRowKey, matchType, curfmdmDataMapController, lastfmdmDataMapController)) && NodeStatus.CONTINUOUS.equals((Object)EntityCheckHandle.checkNodeStatus(lastRowKey, matchType, curfmdmDataMapController, lastfmdmDataMapController)) || NodeStatus.NEW.equals((Object)EntityCheckHandle.checkNodeStatus(curRowKey, matchType, curfmdmDataMapController, lastfmdmDataMapController)) && NodeStatus.CONTINUOUS.equals((Object)EntityCheckHandle.checkNodeStatus(lastRowKey, matchType, curfmdmDataMapController, lastfmdmDataMapController))) && !records.get(type3 = EntityCheckUpType.INCREASE_HAVE_SNDM.getIndex()).containsKey(key)) {
                            EntityCheckUpRecord record = this.ParseRowDataToRecord(fieldContextWrapper.getCurfieldContext(), EntityCheckUpType.INCREASE_HAVE_SNDM, configurationContent, value, currentVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                            records.get(type3).put(key, record);
                        }
                    }
                    if (configurationContent.getSndm() != null && configurationContent.getXbys() != null) {
                        int type4;
                        String sndm4;
                        String xbys;
                        String string4 = xbys = value.getValue(fieldContextWrapper.getCurfieldContext().getXBYS().getCode()) == null ? null : value.getValue(fieldContextWrapper.getCurfieldContext().getXBYS().getCode()).getAsString();
                        if (!this.getXbysLxCode().equals(xbys)) {
                            int type5 = EntityCheckUpType.INCREASE_HAVE_SNDM.getIndex();
                            if (records.get(type5).containsKey(key)) continue block14;
                            EntityCheckUpRecord record = this.ParseRowDataToRecord(fieldContextWrapper.getCurfieldContext(), EntityCheckUpType.INCREASE_HAVE_SNDM, configurationContent, value, currentVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                            records.get(type5).put(key, record);
                            break;
                        }
                        String string5 = sndm4 = value.getValue(fieldContextWrapper.getCurfieldContext().getSNDM().getCode()) == null ? null : value.getValue(fieldContextWrapper.getCurfieldContext().getSNDM().getCode()).getAsString();
                        if (StringUtils.isEmpty(sndm4)) {
                            int type6 = EntityCheckUpType.NULL_SN_CODE.getIndex();
                            if (records.get(type6).containsKey(key)) continue block14;
                            EntityCheckUpRecord record = this.ParseRowDataToRecord(fieldContextWrapper.getCurfieldContext(), EntityCheckUpType.NULL_SN_CODE, configurationContent, value, currentVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                            records.get(type6).put(key, record);
                            break;
                        }
                        if (lastSndmToExpValue.contains(sndm4) || records.get(type4 = EntityCheckUpType.SN_CODE_NON_EXIST.getIndex()).containsKey(key)) continue block14;
                        EntityCheckUpRecord record = this.ParseRowDataToRecord(fieldContextWrapper.getCurfieldContext(), EntityCheckUpType.SN_CODE_NON_EXIST, configurationContent, value, currentVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                        records.get(type4).put(key, record);
                        break;
                    }
                    if (configurationContent.getXbys() != null) {
                        int type7;
                        String xbys;
                        String string6 = xbys = value.getValue(fieldContextWrapper.getCurfieldContext().getXBYS().getCode()) == null ? null : value.getValue(fieldContextWrapper.getCurfieldContext().getXBYS().getCode()).getAsString();
                        if (this.getXbysLxCode().equals(xbys) || records.get(type7 = EntityCheckUpType.INCREASE_HAVE_SNDM.getIndex()).containsKey(key)) continue block14;
                        EntityCheckUpRecord record = this.ParseRowDataToRecord(fieldContextWrapper.getCurfieldContext(), EntityCheckUpType.INCREASE_HAVE_SNDM, configurationContent, value, currentVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                        records.get(type7).put(key, record);
                        break;
                    }
                    if (configurationContent.getSndm() == null || !StringUtils.isEmpty(sndm = value.getValue(fieldContextWrapper.getCurfieldContext().getSNDM().getCode()) == null ? null : value.getValue(fieldContextWrapper.getCurfieldContext().getSNDM().getCode()).getAsString()) || records.get(type = EntityCheckUpType.NULL_SN_CODE.getIndex()).containsKey(key)) continue block14;
                    EntityCheckUpRecord record = this.ParseRowDataToRecord(fieldContextWrapper.getCurfieldContext(), EntityCheckUpType.NULL_SN_CODE, configurationContent, value, currentVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                    records.get(type).put(key, record);
                    break;
                }
                case MATCHING_TYPE_CODE: {
                    int type;
                    String sndm;
                    String lastRowKey;
                    String orgCode;
                    Map<String, IFMDMData> orgCodeMaps = lastfmdmDataMapController.getCodeMaps();
                    String string = orgCode = value.getValue("ORGCODE") == null ? null : value.getValue("ORGCODE").getAsString();
                    if (!orgCodeMaps.isEmpty() && !orgCodeMaps.containsKey(orgCode)) {
                        String xbys;
                        int type8 = EntityCheckUpType.INCREASE.getIndex();
                        if (!records.get(type8).containsKey(key)) {
                            EntityCheckUpRecord record = this.ParseRowDataToRecord(fieldContextWrapper.getCurfieldContext(), EntityCheckUpType.INCREASE, configurationContent, value, currentVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                            records.get(type8).put(key, record);
                        }
                        if (configurationContent.getSndm() != null && configurationContent.getXbys() != null) {
                            String sndm5;
                            String xbys3 = value.getValue(fieldContextWrapper.getCurfieldContext().getXBYS().getCode()) == null ? null : value.getValue(fieldContextWrapper.getCurfieldContext().getXBYS().getCode()).getAsString();
                            String string7 = sndm5 = value.getValue(fieldContextWrapper.getCurfieldContext().getSNDM().getCode()) == null ? null : value.getValue(fieldContextWrapper.getCurfieldContext().getSNDM().getCode()).getAsString();
                            if (this.getXbysLxCode().equals(xbys3)) {
                                type8 = EntityCheckUpType.INCREASE_HAVE_SNDM.getIndex();
                                if (records.get(type8).containsKey(key)) continue block14;
                                EntityCheckUpRecord record = this.ParseRowDataToRecord(fieldContextWrapper.getCurfieldContext(), EntityCheckUpType.INCREASE_HAVE_SNDM, configurationContent, value, currentVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                                records.get(type8).put(key, record);
                                break;
                            }
                            if (!StringUtils.isNotEmpty((String)sndm5) || records.get(type8 = EntityCheckUpType.INCREASE_HAVE_SNDM.getIndex()).containsKey(key)) continue block14;
                            EntityCheckUpRecord record = this.ParseRowDataToRecord(fieldContextWrapper.getCurfieldContext(), EntityCheckUpType.INCREASE_HAVE_SNDM, configurationContent, value, currentVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                            records.get(type8).put(key, record);
                            break;
                        }
                        if (configurationContent.getSndm() != null) {
                            String sndm6 = value.getValue(fieldContextWrapper.getCurfieldContext().getSNDM().getCode()) == null ? null : value.getValue(fieldContextWrapper.getCurfieldContext().getSNDM().getCode()).getAsString();
                            if (!StringUtils.isNotEmpty(sndm6) || records.get(type8 = EntityCheckUpType.INCREASE_HAVE_SNDM.getIndex()).containsKey(key)) continue block14;
                            EntityCheckUpRecord record = this.ParseRowDataToRecord(fieldContextWrapper.getCurfieldContext(), EntityCheckUpType.INCREASE_HAVE_SNDM, configurationContent, value, currentVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                            records.get(type8).put(key, record);
                            break;
                        }
                        if (configurationContent.getXbys() == null) continue block14;
                        String string8 = xbys = value.getValue(fieldContextWrapper.getCurfieldContext().getXBYS().getCode()) == null ? null : value.getValue(fieldContextWrapper.getCurfieldContext().getXBYS().getCode()).getAsString();
                        if (!this.getXbysLxCode().equals(xbys) || records.get(type8 = EntityCheckUpType.INCREASE_HAVE_SNDM.getIndex()).containsKey(key)) continue block14;
                        EntityCheckUpRecord record = this.ParseRowDataToRecord(fieldContextWrapper.getCurfieldContext(), EntityCheckUpType.INCREASE_HAVE_SNDM, configurationContent, value, currentVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                        records.get(type8).put(key, record);
                        break;
                    }
                    String curRowKey = ((EntityInfoDO)value).getEntityRow().getParentEntityKey();
                    if (!curRowKey.equals(lastRowKey = ((EntityInfoDO)orgCodeMaps.get(orgCode)).getEntityRow().getParentEntityKey())) {
                        int type9;
                        EntityCheckHandle.checkNodeStatus(curRowKey, matchType, curfmdmDataMapController, lastfmdmDataMapController);
                        EntityCheckHandle.checkNodeStatus(lastRowKey, matchType, curfmdmDataMapController, lastfmdmDataMapController);
                        if ((NodeStatus.CONTINUOUS.equals((Object)EntityCheckHandle.checkNodeStatus(curRowKey, matchType, curfmdmDataMapController, lastfmdmDataMapController)) && NodeStatus.CONTINUOUS.equals((Object)EntityCheckHandle.checkNodeStatus(lastRowKey, matchType, curfmdmDataMapController, lastfmdmDataMapController)) || NodeStatus.NEW.equals((Object)EntityCheckHandle.checkNodeStatus(curRowKey, matchType, curfmdmDataMapController, lastfmdmDataMapController)) && NodeStatus.CONTINUOUS.equals((Object)EntityCheckHandle.checkNodeStatus(lastRowKey, matchType, curfmdmDataMapController, lastfmdmDataMapController))) && !records.get(type9 = EntityCheckUpType.INCREASE_HAVE_SNDM.getIndex()).containsKey(key)) {
                            EntityCheckUpRecord record = this.ParseRowDataToRecord(fieldContextWrapper.getCurfieldContext(), EntityCheckUpType.INCREASE_HAVE_SNDM, configurationContent, value, currentVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                            records.get(type9).put(key, record);
                        }
                    }
                    if (configurationContent.getSndm() != null && configurationContent.getXbys() != null) {
                        int type10;
                        String sndm7;
                        String xbys;
                        String string9 = xbys = value.getValue(fieldContextWrapper.getCurfieldContext().getXBYS().getCode()) == null ? null : value.getValue(fieldContextWrapper.getCurfieldContext().getXBYS().getCode()).getAsString();
                        if (!this.getXbysLxCode().equals(xbys)) {
                            int type11 = EntityCheckUpType.INCREASE_HAVE_SNDM.getIndex();
                            if (records.get(type11).containsKey(key)) continue block14;
                            EntityCheckUpRecord record = this.ParseRowDataToRecord(fieldContextWrapper.getCurfieldContext(), EntityCheckUpType.INCREASE_HAVE_SNDM, configurationContent, value, currentVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                            records.get(type11).put(key, record);
                            break;
                        }
                        String string10 = sndm7 = value.getValue(fieldContextWrapper.getCurfieldContext().getSNDM().getCode()) == null ? null : value.getValue(fieldContextWrapper.getCurfieldContext().getSNDM().getCode()).getAsString();
                        if (StringUtils.isEmpty(sndm7)) {
                            int type12 = EntityCheckUpType.NULL_SN_CODE.getIndex();
                            if (records.get(type12).containsKey(key)) continue block14;
                            EntityCheckUpRecord record = this.ParseRowDataToRecord(fieldContextWrapper.getCurfieldContext(), EntityCheckUpType.NULL_SN_CODE, configurationContent, value, currentVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                            records.get(type12).put(key, record);
                            break;
                        }
                        if (lastSndmToExpValue.contains(sndm7) || records.get(type10 = EntityCheckUpType.SN_CODE_NON_EXIST.getIndex()).containsKey(key)) continue block14;
                        EntityCheckUpRecord record = this.ParseRowDataToRecord(fieldContextWrapper.getCurfieldContext(), EntityCheckUpType.SN_CODE_NON_EXIST, configurationContent, value, currentVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                        records.get(type10).put(key, record);
                        break;
                    }
                    if (configurationContent.getXbys() != null) {
                        int type13;
                        String xbys;
                        String string11 = xbys = value.getValue(fieldContextWrapper.getCurfieldContext().getXBYS().getCode()) == null ? null : value.getValue(fieldContextWrapper.getCurfieldContext().getXBYS().getCode()).getAsString();
                        if (this.getXbysLxCode().equals(xbys) || records.get(type13 = EntityCheckUpType.INCREASE_HAVE_SNDM.getIndex()).containsKey(key)) continue block14;
                        EntityCheckUpRecord record = this.ParseRowDataToRecord(fieldContextWrapper.getCurfieldContext(), EntityCheckUpType.INCREASE_HAVE_SNDM, configurationContent, value, currentVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                        records.get(type13).put(key, record);
                        break;
                    }
                    if (configurationContent.getSndm() == null || !StringUtils.isEmpty(sndm = value.getValue(fieldContextWrapper.getCurfieldContext().getSNDM().getCode()) == null ? null : value.getValue(fieldContextWrapper.getCurfieldContext().getSNDM().getCode()).getAsString()) || records.get(type = EntityCheckUpType.NULL_SN_CODE.getIndex()).containsKey(key)) continue block14;
                    EntityCheckUpRecord record = this.ParseRowDataToRecord(fieldContextWrapper.getCurfieldContext(), EntityCheckUpType.NULL_SN_CODE, configurationContent, value, currentVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                    records.get(type).put(key, record);
                    break;
                }
                case MATCHING_TYPE_TITLE: {
                    int type;
                    String sndm;
                    EntityCheckUpRecord record;
                    String lastRowKey;
                    String name;
                    Map<String, IFMDMData> dwmcMaps = lastfmdmDataMapController.getDwmcMaps();
                    String string = name = value.getValue("NAME") == null ? null : value.getValue("NAME").getAsString();
                    if (!dwmcMaps.isEmpty() && !dwmcMaps.containsKey(name)) {
                        String xbys;
                        int type14 = EntityCheckUpType.INCREASE.getIndex();
                        if (!records.get(type14).containsKey(key)) {
                            EntityCheckUpRecord record2 = this.ParseRowDataToRecord(fieldContextWrapper.getCurfieldContext(), EntityCheckUpType.INCREASE, configurationContent, value, currentVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                            records.get(type14).put(key, record2);
                        }
                        if (configurationContent.getSndm() != null && configurationContent.getXbys() != null) {
                            String sndm8;
                            String xbys4 = value.getValue(fieldContextWrapper.getCurfieldContext().getXBYS().getCode()) == null ? null : value.getValue(fieldContextWrapper.getCurfieldContext().getXBYS().getCode()).getAsString();
                            String string12 = sndm8 = value.getValue(fieldContextWrapper.getCurfieldContext().getSNDM().getCode()) == null ? null : value.getValue(fieldContextWrapper.getCurfieldContext().getSNDM().getCode()).getAsString();
                            if (this.getXbysLxCode().equals(xbys4)) {
                                type14 = EntityCheckUpType.INCREASE_HAVE_SNDM.getIndex();
                                if (records.get(type14).containsKey(key)) continue block14;
                                EntityCheckUpRecord record3 = this.ParseRowDataToRecord(fieldContextWrapper.getCurfieldContext(), EntityCheckUpType.INCREASE_HAVE_SNDM, configurationContent, value, currentVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                                records.get(type14).put(key, record3);
                                break;
                            }
                            if (!StringUtils.isNotEmpty((String)sndm8) || records.get(type14 = EntityCheckUpType.INCREASE_HAVE_SNDM.getIndex()).containsKey(key)) continue block14;
                            EntityCheckUpRecord record4 = this.ParseRowDataToRecord(fieldContextWrapper.getCurfieldContext(), EntityCheckUpType.INCREASE_HAVE_SNDM, configurationContent, value, currentVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                            records.get(type14).put(key, record4);
                            break;
                        }
                        if (configurationContent.getSndm() != null) {
                            String sndm9 = value.getValue(fieldContextWrapper.getCurfieldContext().getSNDM().getCode()) == null ? null : value.getValue(fieldContextWrapper.getCurfieldContext().getSNDM().getCode()).getAsString();
                            if (!StringUtils.isNotEmpty(sndm9) || records.get(type14 = EntityCheckUpType.INCREASE_HAVE_SNDM.getIndex()).containsKey(key)) continue block14;
                            EntityCheckUpRecord record5 = this.ParseRowDataToRecord(fieldContextWrapper.getCurfieldContext(), EntityCheckUpType.INCREASE_HAVE_SNDM, configurationContent, value, currentVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                            records.get(type14).put(key, record5);
                            break;
                        }
                        if (configurationContent.getXbys() == null) continue block14;
                        String string13 = xbys = value.getValue(fieldContextWrapper.getCurfieldContext().getXBYS().getCode()) == null ? null : value.getValue(fieldContextWrapper.getCurfieldContext().getXBYS().getCode()).getAsString();
                        if (!this.getXbysLxCode().equals(xbys) || records.get(type14 = EntityCheckUpType.INCREASE_HAVE_SNDM.getIndex()).containsKey(key)) continue block14;
                        EntityCheckUpRecord record6 = this.ParseRowDataToRecord(fieldContextWrapper.getCurfieldContext(), EntityCheckUpType.INCREASE_HAVE_SNDM, configurationContent, value, currentVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                        records.get(type14).put(key, record6);
                        break;
                    }
                    String curRowKey = ((EntityInfoDO)value).getEntityRow().getParentEntityKey();
                    if (!curRowKey.equals(lastRowKey = ((EntityInfoDO)dwmcMaps.get(name)).getEntityRow().getParentEntityKey())) {
                        int type15;
                        EntityCheckHandle.checkNodeStatus(curRowKey, matchType, curfmdmDataMapController, lastfmdmDataMapController);
                        EntityCheckHandle.checkNodeStatus(lastRowKey, matchType, curfmdmDataMapController, lastfmdmDataMapController);
                        if ((NodeStatus.CONTINUOUS.equals((Object)EntityCheckHandle.checkNodeStatus(curRowKey, matchType, curfmdmDataMapController, lastfmdmDataMapController)) && NodeStatus.CONTINUOUS.equals((Object)EntityCheckHandle.checkNodeStatus(lastRowKey, matchType, curfmdmDataMapController, lastfmdmDataMapController)) || NodeStatus.NEW.equals((Object)EntityCheckHandle.checkNodeStatus(curRowKey, matchType, curfmdmDataMapController, lastfmdmDataMapController)) && NodeStatus.CONTINUOUS.equals((Object)EntityCheckHandle.checkNodeStatus(lastRowKey, matchType, curfmdmDataMapController, lastfmdmDataMapController))) && !records.get(type15 = EntityCheckUpType.INCREASE_HAVE_SNDM.getIndex()).containsKey(key)) {
                            EntityCheckUpRecord record7 = this.ParseRowDataToRecord(fieldContextWrapper.getCurfieldContext(), EntityCheckUpType.INCREASE_HAVE_SNDM, configurationContent, value, currentVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                            records.get(type15).put(key, record7);
                        }
                    }
                    if (configurationContent.getSndm() != null && configurationContent.getXbys() != null) {
                        int type16;
                        String sndm10;
                        String xbys;
                        String string14 = xbys = value.getValue(fieldContextWrapper.getCurfieldContext().getXBYS().getCode()) == null ? null : value.getValue(fieldContextWrapper.getCurfieldContext().getXBYS().getCode()).getAsString();
                        if (!this.getXbysLxCode().equals(xbys)) {
                            int type17 = EntityCheckUpType.INCREASE_HAVE_SNDM.getIndex();
                            if (records.get(type17).containsKey(key)) continue block14;
                            record = this.ParseRowDataToRecord(fieldContextWrapper.getCurfieldContext(), EntityCheckUpType.INCREASE_HAVE_SNDM, configurationContent, value, currentVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                            records.get(type17).put(key, record);
                            break;
                        }
                        String string15 = sndm10 = value.getValue(fieldContextWrapper.getCurfieldContext().getSNDM().getCode()) == null ? null : value.getValue(fieldContextWrapper.getCurfieldContext().getSNDM().getCode()).getAsString();
                        if (StringUtils.isEmpty(sndm10)) {
                            int type18 = EntityCheckUpType.NULL_SN_CODE.getIndex();
                            if (records.get(type18).containsKey(key)) continue block14;
                            EntityCheckUpRecord record8 = this.ParseRowDataToRecord(fieldContextWrapper.getCurfieldContext(), EntityCheckUpType.NULL_SN_CODE, configurationContent, value, currentVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                            records.get(type18).put(key, record8);
                            break;
                        }
                        if (lastSndmToExpValue.contains(sndm10) || records.get(type16 = EntityCheckUpType.SN_CODE_NON_EXIST.getIndex()).containsKey(key)) continue block14;
                        EntityCheckUpRecord record9 = this.ParseRowDataToRecord(fieldContextWrapper.getCurfieldContext(), EntityCheckUpType.SN_CODE_NON_EXIST, configurationContent, value, currentVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                        records.get(type16).put(key, record9);
                        break;
                    }
                    if (configurationContent.getXbys() != null) {
                        int type19;
                        String xbys;
                        String string16 = xbys = value.getValue(fieldContextWrapper.getCurfieldContext().getXBYS().getCode()) == null ? null : value.getValue(fieldContextWrapper.getCurfieldContext().getXBYS().getCode()).getAsString();
                        if (this.getXbysLxCode().equals(xbys) || records.get(type19 = EntityCheckUpType.INCREASE_HAVE_SNDM.getIndex()).containsKey(key)) continue block14;
                        record = this.ParseRowDataToRecord(fieldContextWrapper.getCurfieldContext(), EntityCheckUpType.INCREASE_HAVE_SNDM, configurationContent, value, currentVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                        records.get(type19).put(key, record);
                        break;
                    }
                    if (configurationContent.getSndm() == null || !StringUtils.isEmpty(sndm = value.getValue(fieldContextWrapper.getCurfieldContext().getSNDM().getCode()) == null ? null : value.getValue(fieldContextWrapper.getCurfieldContext().getSNDM().getCode()).getAsString()) || records.get(type = EntityCheckUpType.NULL_SN_CODE.getIndex()).containsKey(key)) continue block14;
                    record = this.ParseRowDataToRecord(fieldContextWrapper.getCurfieldContext(), EntityCheckUpType.NULL_SN_CODE, configurationContent, value, currentVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                    records.get(type).put(key, record);
                    break;
                }
                case FORM_TYPE_EXPRESSION: {
                    int type;
                    String sndm;
                    EntityCheckUpRecord record;
                    String lastRowKey;
                    Map<String, String> curExpValues = curfmdmDataMapController.getCodeToExpValues();
                    String expValue = curExpValues.get(key);
                    Map<String, IFMDMData> lastExpToFmdmData = lastfmdmDataMapController.getExpValueToFmdmData();
                    if (!lastExpToFmdmData.isEmpty() && !lastExpToFmdmData.containsKey(expValue)) {
                        String xbys;
                        int type20 = EntityCheckUpType.INCREASE.getIndex();
                        if (!records.get(type20).containsKey(key)) {
                            EntityCheckUpRecord record10 = this.ParseRowDataToRecord(fieldContextWrapper.getCurfieldContext(), EntityCheckUpType.INCREASE, configurationContent, value, currentVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                            records.get(type20).put(key, record10);
                        }
                        if (configurationContent.getSndm() != null && configurationContent.getXbys() != null) {
                            String sndm11;
                            xbys = value.getValue(fieldContextWrapper.getCurfieldContext().getXBYS().getCode()) == null ? null : value.getValue(fieldContextWrapper.getCurfieldContext().getXBYS().getCode()).getAsString();
                            String string = sndm11 = value.getValue(fieldContextWrapper.getCurfieldContext().getSNDM().getCode()) == null ? null : value.getValue(fieldContextWrapper.getCurfieldContext().getSNDM().getCode()).getAsString();
                            if (this.getXbysLxCode().equals(xbys)) {
                                type20 = EntityCheckUpType.INCREASE_HAVE_SNDM.getIndex();
                                if (records.get(type20).containsKey(key)) continue block14;
                                EntityCheckUpRecord record11 = this.ParseRowDataToRecord(fieldContextWrapper.getCurfieldContext(), EntityCheckUpType.INCREASE_HAVE_SNDM, configurationContent, value, currentVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                                records.get(type20).put(key, record11);
                                break;
                            }
                            if (!StringUtils.isNotEmpty((String)sndm11) || records.get(type20 = EntityCheckUpType.INCREASE_HAVE_SNDM.getIndex()).containsKey(key)) continue block14;
                            EntityCheckUpRecord record12 = this.ParseRowDataToRecord(fieldContextWrapper.getCurfieldContext(), EntityCheckUpType.INCREASE_HAVE_SNDM, configurationContent, value, currentVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                            records.get(type20).put(key, record12);
                            break;
                        }
                        if (configurationContent.getSndm() != null) {
                            String sndm12 = value.getValue(fieldContextWrapper.getCurfieldContext().getSNDM().getCode()) == null ? null : value.getValue(fieldContextWrapper.getCurfieldContext().getSNDM().getCode()).getAsString();
                            if (!StringUtils.isNotEmpty(sndm12) || records.get(type20 = EntityCheckUpType.INCREASE_HAVE_SNDM.getIndex()).containsKey(key)) continue block14;
                            EntityCheckUpRecord record13 = this.ParseRowDataToRecord(fieldContextWrapper.getCurfieldContext(), EntityCheckUpType.INCREASE_HAVE_SNDM, configurationContent, value, currentVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                            records.get(type20).put(key, record13);
                            break;
                        }
                        if (configurationContent.getXbys() == null) continue block14;
                        String string = xbys = value.getValue(fieldContextWrapper.getCurfieldContext().getXBYS().getCode()) == null ? null : value.getValue(fieldContextWrapper.getCurfieldContext().getXBYS().getCode()).getAsString();
                        if (!this.getXbysLxCode().equals(xbys) || records.get(type20 = EntityCheckUpType.INCREASE_HAVE_SNDM.getIndex()).containsKey(key)) continue block14;
                        EntityCheckUpRecord record14 = this.ParseRowDataToRecord(fieldContextWrapper.getCurfieldContext(), EntityCheckUpType.INCREASE_HAVE_SNDM, configurationContent, value, currentVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                        records.get(type20).put(key, record14);
                        break;
                    }
                    String curRowKey = ((EntityInfoDO)value).getEntityRow().getParentEntityKey();
                    if (!curRowKey.equals(lastRowKey = ((EntityInfoDO)lastExpToFmdmData.get(expValue)).getEntityRow().getParentEntityKey())) {
                        int type21;
                        EntityCheckHandle.checkNodeStatus(curRowKey, matchType, curfmdmDataMapController, lastfmdmDataMapController);
                        EntityCheckHandle.checkNodeStatus(lastRowKey, matchType, curfmdmDataMapController, lastfmdmDataMapController);
                        if ((NodeStatus.CONTINUOUS.equals((Object)EntityCheckHandle.checkNodeStatus(curRowKey, matchType, curfmdmDataMapController, lastfmdmDataMapController)) && NodeStatus.CONTINUOUS.equals((Object)EntityCheckHandle.checkNodeStatus(lastRowKey, matchType, curfmdmDataMapController, lastfmdmDataMapController)) || NodeStatus.NEW.equals((Object)EntityCheckHandle.checkNodeStatus(curRowKey, matchType, curfmdmDataMapController, lastfmdmDataMapController)) && NodeStatus.CONTINUOUS.equals((Object)EntityCheckHandle.checkNodeStatus(lastRowKey, matchType, curfmdmDataMapController, lastfmdmDataMapController))) && !records.get(type21 = EntityCheckUpType.INCREASE_HAVE_SNDM.getIndex()).containsKey(key)) {
                            EntityCheckUpRecord record15 = this.ParseRowDataToRecord(fieldContextWrapper.getCurfieldContext(), EntityCheckUpType.INCREASE_HAVE_SNDM, configurationContent, value, currentVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                            records.get(type21).put(key, record15);
                        }
                    }
                    if (configurationContent.getSndm() != null && configurationContent.getXbys() != null) {
                        int type22;
                        String sndm13;
                        String xbys;
                        String string = xbys = value.getValue(fieldContextWrapper.getCurfieldContext().getXBYS().getCode()) == null ? null : value.getValue(fieldContextWrapper.getCurfieldContext().getXBYS().getCode()).getAsString();
                        if (!this.getXbysLxCode().equals(xbys)) {
                            int type23 = EntityCheckUpType.INCREASE_HAVE_SNDM.getIndex();
                            if (records.get(type23).containsKey(key)) continue block14;
                            record = this.ParseRowDataToRecord(fieldContextWrapper.getCurfieldContext(), EntityCheckUpType.INCREASE_HAVE_SNDM, configurationContent, value, currentVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                            records.get(type23).put(key, record);
                            break;
                        }
                        String string17 = sndm13 = value.getValue(fieldContextWrapper.getCurfieldContext().getSNDM().getCode()) == null ? null : value.getValue(fieldContextWrapper.getCurfieldContext().getSNDM().getCode()).getAsString();
                        if (StringUtils.isEmpty(sndm13)) {
                            int type24 = EntityCheckUpType.NULL_SN_CODE.getIndex();
                            if (records.get(type24).containsKey(key)) continue block14;
                            EntityCheckUpRecord record16 = this.ParseRowDataToRecord(fieldContextWrapper.getCurfieldContext(), EntityCheckUpType.NULL_SN_CODE, configurationContent, value, currentVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                            records.get(type24).put(key, record16);
                            break;
                        }
                        if (lastSndmToExpValue.contains(sndm13) || records.get(type22 = EntityCheckUpType.SN_CODE_NON_EXIST.getIndex()).containsKey(key)) continue block14;
                        EntityCheckUpRecord record2 = this.ParseRowDataToRecord(fieldContextWrapper.getCurfieldContext(), EntityCheckUpType.SN_CODE_NON_EXIST, configurationContent, value, currentVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                        records.get(type22).put(key, record2);
                        break;
                    }
                    if (configurationContent.getXbys() != null) {
                        int type25;
                        String xbys;
                        String string = xbys = value.getValue(fieldContextWrapper.getCurfieldContext().getXBYS().getCode()) == null ? null : value.getValue(fieldContextWrapper.getCurfieldContext().getXBYS().getCode()).getAsString();
                        if (this.getXbysLxCode().equals(xbys) || records.get(type25 = EntityCheckUpType.INCREASE_HAVE_SNDM.getIndex()).containsKey(key)) continue block14;
                        record = this.ParseRowDataToRecord(fieldContextWrapper.getCurfieldContext(), EntityCheckUpType.INCREASE_HAVE_SNDM, configurationContent, value, currentVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                        records.get(type25).put(key, record);
                        break;
                    }
                    if (configurationContent.getSndm() == null) break;
                    String string = sndm = value.getValue(fieldContextWrapper.getCurfieldContext().getSNDM().getCode()) == null ? null : value.getValue(fieldContextWrapper.getCurfieldContext().getSNDM().getCode()).getAsString();
                    if (!StringUtils.isEmpty(sndm) || records.get(type = EntityCheckUpType.NULL_SN_CODE.getIndex()).containsKey(key)) break;
                    record = this.ParseRowDataToRecord(fieldContextWrapper.getCurfieldContext(), EntityCheckUpType.NULL_SN_CODE, configurationContent, value, currentVersionObjectInfo.getEntityModel().getBizKeyField().getTableID(), checkUpRecordInDb);
                    records.get(type).put(key, record);
                }
            }
        }
        Object var15_19 = null;
        Object var16_26 = null;
        Map<String, EntityCheckUpRecord> map = records.get(EntityCheckUpType.INCREASE.getIndex());
        Map<String, EntityCheckUpRecord> map2 = records.get(EntityCheckUpType.DECREASE.getIndex());
        if (null != map && null != map2) {
            for (String key : map.keySet()) {
                EntityCheckUpRecord decreaceRecord;
                int type;
                EntityCheckUpRecord currentRecord = map.get(key);
                EntityCheckUpRecord checkUpRecord = null;
                checkUpRecord = map2.get(key);
                if (null != checkUpRecord) {
                    type = EntityCheckUpType.ZJ_SAME_ENTITY_CODE.getIndex();
                    records.get(type).put(key + "0", currentRecord);
                    decreaceRecord = checkUpRecord.Clone();
                    decreaceRecord.GetCheckUpData().SetXBYS(currentRecord.GetCheckUpData().GetXBYS());
                    decreaceRecord.GetCheckUpData().SetSNDM(currentRecord.GetCheckUpData().GetSNDM());
                    decreaceRecord.GetKey().SetFmdmId(currentRecord.GetKey().GetFmdmId());
                    records.get(type).put(key + "1", decreaceRecord);
                }
                if (null == (checkUpRecord = (EntityCheckUpRecord)decreaseNameRecord.get(currentRecord.GetCheckUpData().GetDWMC()))) continue;
                type = EntityCheckUpType.ZJ_SAME_ENTITY_NAME.getIndex();
                records.get(type).put(key + "0", currentRecord);
                decreaceRecord = checkUpRecord.Clone();
                decreaceRecord.GetCheckUpData().SetXBYS(currentRecord.GetCheckUpData().GetXBYS());
                decreaceRecord.GetCheckUpData().SetSNDM(currentRecord.GetCheckUpData().GetSNDM());
                decreaceRecord.GetKey().SetFmdmId(currentRecord.GetKey().GetFmdmId());
                records.get(type).put(key + "1", decreaceRecord);
            }
        }
        return records;
    }

    private static NodeStatus checkNodeStatus(String nodeKey, TaskLinkMatchingType matchType, FMDMDataMapController curfmdmDataMapController, FMDMDataMapController lastfmdmDataMapController) {
        boolean currentExists = curfmdmDataMapController.getMapByMatchingType(matchType).containsKey(nodeKey);
        boolean previousExists = lastfmdmDataMapController.getMapByMatchingType(matchType).containsKey(nodeKey);
        if (currentExists && previousExists) {
            return NodeStatus.CONTINUOUS;
        }
        if (currentExists) {
            return NodeStatus.NEW;
        }
        if (previousExists) {
            return NodeStatus.REMOVED;
        }
        return null;
    }

    private Map<Integer, Map<String, EntityCheckUpRecord>> InitEntityCheckUpRecordObj() {
        HashMap<Integer, Map<String, EntityCheckUpRecord>> records = new HashMap<Integer, Map<String, EntityCheckUpRecord>>();
        records.put(1, new LinkedHashMap());
        records.put(2, new LinkedHashMap());
        records.put(3, new LinkedHashMap());
        records.put(4, new LinkedHashMap());
        records.put(5, new LinkedHashMap());
        records.put(6, new LinkedHashMap());
        records.put(7, new LinkedHashMap());
        records.put(8, new LinkedHashMap());
        records.put(-1, new LinkedHashMap());
        return records;
    }

    private EntityCheckUpRecord ParseRowDataToRecord(FieldContext fieldContext, EntityCheckUpType type, CheckConfigurationContent configurationContent, IFMDMData fmdmData, String tableKey, Map<String, EntityCheckUpDecrease> jsRecordInDb) throws Exception {
        EntityCheckUpRecord record = new EntityCheckUpRecord();
        EntityCheckUpKey key = new EntityCheckUpKey();
        key.SetFmdmId(fmdmData.getFMDMKey());
        key.SetDWZDM(fmdmData.getValue("CODE").getAsString());
        key.SetCheckType(type);
        record.SetKey(key);
        EntityCheckUpData item = new EntityCheckUpData();
        if (type.equals((Object)EntityCheckUpType.DECREASE) || type.equals((Object)EntityCheckUpType.INCREASE)) {
            if (jsRecordInDb != null && jsRecordInDb.containsKey(key.GetDWZDM())) {
                EntityCheckUpDecrease dbRecord = null;
                dbRecord = jsRecordInDb.get(key.GetDWZDM());
                item.SetZJYS(dbRecord.GetJSYS());
                if (fieldContext.getXBYS() != null && fieldContext.getXBYS().getCode() != null) {
                    item.SetXBYS(fmdmData.getValue(fieldContext.getXBYS().getCode()).getAsString());
                }
                item.SetDWZDM(fmdmData.getValue("CODE").getAsString());
                item.setOrgCode(fmdmData.getValue("ORGCODE").getAsString());
                if (configurationContent.getDwdm() != null) {
                    item.SetDWDM(fmdmData.getValue(fieldContext.getDWDM().getCode()).getAsString());
                }
                if (configurationContent.getSndm() != null) {
                    item.SetSNDM(fmdmData.getValue(fieldContext.getSNDM().getCode()).getAsString());
                }
            } else {
                if (fieldContext.getXBYS() != null && fieldContext.getXBYS().getCode() != null) {
                    item.SetXBYS(fmdmData.getValue(fieldContext.getXBYS().getCode()).getAsString());
                }
                item.SetDWZDM(fmdmData.getValue("CODE").getAsString());
                item.setOrgCode(fmdmData.getValue("ORGCODE").getAsString());
                if (configurationContent.getDwdm() != null) {
                    item.SetDWDM(fmdmData.getValue(fieldContext.getDWDM().getCode()).getAsString());
                }
                if (configurationContent.getSndm() != null) {
                    item.SetSNDM(fmdmData.getValue(fieldContext.getSNDM().getCode()).getAsString());
                }
            }
        } else {
            if (fieldContext.getXBYS() != null && fieldContext.getXBYS().getCode() != null) {
                item.SetXBYS(fmdmData.getValue(fieldContext.getXBYS().getCode()).getAsString());
            }
            item.SetDWZDM(fmdmData.getValue("CODE").getAsString());
            item.setOrgCode(fmdmData.getValue("ORGCODE").getAsString());
            if (configurationContent.getDwdm() != null) {
                item.SetDWDM(fmdmData.getValue(fieldContext.getDWDM().getCode()).getAsString());
            }
            if (fieldContext.getSNDM() != null) {
                item.SetSNDM(fmdmData.getValue(fieldContext.getSNDM().getCode()).getAsString());
            }
        }
        item.SetDWMC(fmdmData.getValue("NAME").getAsString());
        item.setParentsEntityKeyDataPath(((EntityInfoDO)fmdmData).getEntityRow().getParentsEntityKeyDataPath());
        String error = this.GetErrorDesc(type, item.GetSNDM());
        if (error != null && !error.equals("")) {
            item.SetErrorDescription(error);
        }
        record.SetCheckUpData(item);
        return record;
    }

    private String GetErrorDesc(EntityCheckUpType type, String sndm) {
        String error = null == sndm || "".equals(sndm) ? "" : sndm;
        switch (type) {
            case INCREASE: {
                error = "\u65b0\u589e";
                break;
            }
            case DECREASE: {
                error = "\u51cf\u5c11";
                break;
            }
            case SN_CODE_NON_EXIST: {
                error = StringUtils.isEmpty((String)sndm) ? "\u4e0a\u5e74\u4ee3\u7801\u4e0d\u5b58\u5728" : String.format("\u4e0a\u5e74\u4ee3\u7801\u3010%s\u3011\u4e0d\u5b58\u5728", sndm);
                break;
            }
            case NULL_SN_CODE: {
                error = "\u8fde\u7eed\u4e0a\u62a5\u5355\u4f4d\u4e0a\u5e74\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a";
                break;
            }
            case INCREASE_HAVE_SNDM: {
                error = "\u65b0\u589e\u5355\u4f4d\u4e0a\u5e74\u4ee3\u7801\u5e94\u8be5\u4e3a\u7a7a";
                break;
            }
            default: {
                error = null;
            }
        }
        return error;
    }

    public String getJSDWTabResult(CheckConfigurationContent configurationContent, String formSchemeKey, String period, String currentTitle, String contrastTitle, Map<Integer, Map<String, EntityCheckUpRecord>> records, String filterValue) throws Exception {
        ArrayList<EnumStructure> jsyyEnumList = new ArrayList<EnumStructure>();
        String zjysColumnTitle = "\u51cf\u5c11\u539f\u56e0";
        String zjysTabTitle = "\u51cf\u5c11\u5355\u4f4d";
        if (null != configurationContent.GetJSYYEntityView()) {
            EntityViewDefine viewDefine = configurationContent.GetJSYYEntityView();
            TableModelDefine tableModel = this.metaService.getTableModel(viewDefine.getEntityId());
            IEntityTable jsyyentity = this.entityQueryHelper.buildEntityTable(viewDefine, period, formSchemeKey, false);
            List jsyyRows = jsyyentity.getAllRows();
            for (Object dataRow : jsyyRows) {
                EnumStructure enumStructure = new EnumStructure();
                enumStructure.setKey(dataRow.getEntityKeyData());
                enumStructure.setValue(dataRow.getTitle());
                jsyyEnumList.add(enumStructure);
            }
            if (tableModel.getTitle() != null && !"".equals(tableModel.getTitle())) {
                zjysColumnTitle = tableModel.getTitle();
                if (tableModel.getTitle().endsWith("\u539f\u56e0")) {
                    int index = tableModel.getTitle().lastIndexOf("\u539f\u56e0");
                    if (index != -1) {
                        zjysTabTitle = tableModel.getTitle().substring(0, index) + "\u5355\u4f4d";
                    }
                } else {
                    zjysTabTitle = tableModel.getTitle();
                }
            }
        }
        List<Object> jsyyList = new ArrayList();
        Map<String, EntityCheckUpRecord> JsyyRecords = records.get(2);
        for (String JsKey : JsyyRecords.keySet()) {
            JSYYTableData jsyyTableData = new JSYYTableData();
            jsyyTableData.setDWZDM(JsyyRecords.get(JsKey).GetCheckUpData().GetDWZDM());
            jsyyTableData.setDWMC(JsyyRecords.get(JsKey).GetCheckUpData().GetDWMC());
            jsyyTableData.setZJYS(JsyyRecords.get(JsKey).GetCheckUpData().GetZJYS());
            jsyyTableData.setIsModify(false);
            jsyyTableData.setOrgCode(JsyyRecords.get(JsKey).GetCheckUpData().getOrgCode());
            jsyyList.add(jsyyTableData);
        }
        Comparator<JSYYTableData> comparator = new Comparator<JSYYTableData>(){

            @Override
            public int compare(JSYYTableData data1, JSYYTableData data2) {
                if (data1.getZJYS() == null && data2.getZJYS() != null) {
                    return -1;
                }
                if (data1.getZJYS() != null && data2.getZJYS() == null) {
                    return 1;
                }
                return data1.getDWZDM().compareTo(data2.getDWZDM());
            }
        };
        Collections.sort(jsyyList, comparator);
        if (StringUtils.isNotEmpty((String)filterValue)) {
            jsyyList = jsyyList.stream().filter(item -> item.getZJYS() != null && item.getZJYS().equals(filterValue)).collect(Collectors.toList());
        }
        int jsyyindex = 0;
        int i = 1;
        for (JSYYTableData jSYYTableData : jsyyList) {
            jSYYTableData.setIndex(jsyyindex);
            jSYYTableData.setID(i);
            ++jsyyindex;
            ++i;
        }
        ArrayList<TableColums> jsyyColumsList = new ArrayList<TableColums>();
        jsyyColumsList.add(new TableColums("id", "\u5e8f\u53f7", 80));
        jsyyColumsList.add(new TableColums("orgCode", "\u663e\u793a\u4ee3\u7801"));
        jsyyColumsList.add(new TableColums("dwmc", "\u5355\u4f4d\u540d\u79f0"));
        jsyyColumsList.add(new TableColums("zjys", zjysColumnTitle, "select"));
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("tableData", jsyyList);
        hashMap.put("columns", jsyyColumsList);
        hashMap.put("EnumList", jsyyEnumList);
        hashMap.put("currentTitle", currentTitle);
        hashMap.put("contrastTitle", contrastTitle);
        hashMap.put("tabName", zjysTabTitle);
        return ApiJSONUtil.objectToJsonStr(hashMap);
    }

    public String getYWDWTabResult(CheckConfigurationContent configurationContent, String formSchemeKey, String period, String currentTitle, String contrastTitle, Map<Integer, Map<String, EntityCheckUpRecord>> records) throws Exception {
        ArrayList<EnumStructure> xbysEnumList = new ArrayList<EnumStructure>();
        if (null != configurationContent.GetXBYSEntityView()) {
            IEntityTable XBYSTable = this.entityQueryHelper.buildEntityTable(configurationContent.GetXBYSEntityView(), period, formSchemeKey, false);
            List xbysRows = XBYSTable.getAllRows();
            for (IEntityRow dataRow : xbysRows) {
                EnumStructure xbysEnum = new EnumStructure();
                xbysEnum.setKey(dataRow.getEntityKeyData());
                xbysEnum.setValue(dataRow.getTitle());
                xbysEnumList.add(xbysEnum);
            }
        }
        ArrayList<XBYSTableData> xbysList = new ArrayList<XBYSTableData>();
        ArrayList<String> filterSameUnit = new ArrayList<String>();
        int xbysindex = 0;
        for (int n = 0; n < 4; ++n) {
            int[] groupNum = new int[]{};
            String groupName = "";
            switch (n) {
                case 0: {
                    groupName = "\u4e0a\u5e74\u4ee3\u7801\u91cd\u7801";
                    groupNum = new int[]{3};
                    break;
                }
                case 1: {
                    groupName = "\u4e0a\u5e74\u4ee3\u7801\u9519\u8bef";
                    groupNum = new int[]{4, 5, 8};
                    break;
                }
                case 2: {
                    groupName = "\u589e\u51cf\u540c\u540d";
                    groupNum = new int[]{6};
                    break;
                }
                case 3: {
                    groupName = "\u589e\u51cf\u540c\u7801";
                    groupNum = new int[]{7};
                }
            }
            int num = 1;
            for (int a : groupNum) {
                Map<String, EntityCheckUpRecord> XBYSRecords = records.get(a);
                for (String XbKey : XBYSRecords.keySet()) {
                    if (a == 4 || a == 5 || a == 8 || a == 3) {
                        if (filterSameUnit.contains(XBYSRecords.get(XbKey).GetCheckUpData().GetDWZDM())) continue;
                        filterSameUnit.add(XBYSRecords.get(XbKey).GetCheckUpData().GetDWZDM());
                    }
                    XBYSTableData xbysTableData = new XBYSTableData();
                    xbysTableData.setFmdmid(XBYSRecords.get(XbKey).GetKey().GetFmdmId());
                    xbysTableData.setDwdm(XBYSRecords.get(XbKey).GetCheckUpData().GetDWDM());
                    xbysTableData.setIndex(xbysindex);
                    xbysTableData.setGroupName(groupName);
                    xbysTableData.setId(num);
                    xbysTableData.setDwzdm(XBYSRecords.get(XbKey).GetCheckUpData().GetDWZDM());
                    xbysTableData.setDwmc(XBYSRecords.get(XbKey).GetCheckUpData().GetDWMC());
                    xbysTableData.setErrorDescription(XBYSRecords.get(XbKey).GetCheckUpData().GetErrorDescription());
                    xbysTableData.setSndm(XBYSRecords.get(XbKey).GetCheckUpData().GetSNDM());
                    xbysTableData.setZjys(XBYSRecords.get(XbKey).GetCheckUpData().GetXBYS());
                    xbysTableData.setIsModify(false);
                    xbysTableData.setOrgCode(XBYSRecords.get(XbKey).GetCheckUpData().getOrgCode());
                    xbysList.add(xbysTableData);
                    ++num;
                    ++xbysindex;
                }
            }
        }
        ArrayList<TableColums> xbysColumsList = new ArrayList<TableColums>();
        xbysColumsList.add(new TableColums("groupName", " ", "", 0));
        xbysColumsList.add(new TableColums("id", "\u5e8f\u53f7", "", 1, 80));
        xbysColumsList.add(new TableColums("orgCode", "\u663e\u793a\u4ee3\u7801", "", 2));
        xbysColumsList.add(new TableColums("dwmc", "\u5355\u4f4d\u540d\u79f0", "", 3));
        xbysColumsList.add(new TableColums("errorDescription", "\u9519\u8bef\u8bf4\u660e", "", 4));
        xbysColumsList.add(new TableColums("sndm", "\u4e0a\u5e74\u4ee3\u7801", "sndmInput", 5));
        xbysColumsList.add(new TableColums("zjys", "\u65b0\u62a5\u56e0\u7d20", "select", 6));
        HashMap<String, Object> xbysmap = new HashMap<String, Object>();
        xbysmap.put("tableData", xbysList);
        xbysmap.put("columns", xbysColumsList);
        xbysmap.put("EnumList", xbysEnumList);
        xbysmap.put("currentTitle", currentTitle);
        xbysmap.put("contrastTitle", contrastTitle);
        return ApiJSONUtil.objectToJsonStr(xbysmap);
    }

    /*
     * WARNING - void declaration
     */
    public String getHDJGFXTabResult(CheckConfigurationContent configurationContent, String formSchemeKey, String period, String currentTitle, String contrastTitle, Map<Integer, Map<String, EntityCheckUpRecord>> records, boolean isDetailed) throws Exception {
        int IIndex;
        ArrayList<AnalysisTableData> analysisDataList;
        ArrayList<TableColums> analysisTitleList;
        List<EntityCheckUpRecord> enumDic;
        EntityCheckUpAnalysis analysis = new EntityCheckUpAnalysis();
        analysis.setIncrease(new HashMap<String, List<EntityCheckUpRecord>>());
        List increaseEnums = new ArrayList();
        if (null != configurationContent.GetXBYSEntityView()) {
            EntityViewDefine viewDefine = configurationContent.GetXBYSEntityView();
            IEntityTable increaseEnumTable = this.entityQueryHelper.buildEntityTable(viewDefine, period, formSchemeKey, false);
            increaseEnums = increaseEnumTable.getAllRows();
            for (Object row : increaseEnums) {
                String key = row.getEntityKeyData();
                if (analysis.getIncrease().containsKey(key)) continue;
                analysis.getIncrease().put(key, new ArrayList());
            }
        }
        analysis.getIncrease().put("-1", new ArrayList());
        analysis.setDecrease(new HashMap<String, List<EntityCheckUpRecord>>());
        List decreaseEnums = new ArrayList();
        if (null != configurationContent.GetJSYYEntityView()) {
            EntityViewDefine viewDefine = configurationContent.GetJSYYEntityView();
            IEntityTable decreaseEnumTable = this.entityQueryHelper.buildEntityTable(viewDefine, period, formSchemeKey, false);
            decreaseEnums = decreaseEnumTable.getAllRows();
            for (Object row : decreaseEnums) {
                String string = row.getEntityKeyData();
                if (analysis.getDecrease().containsKey(string)) continue;
                analysis.getDecrease().put(string, new ArrayList());
            }
        }
        analysis.getDecrease().put("-1", new ArrayList());
        Map<String, EntityCheckUpRecord> increaseEntity = records.get(EntityCheckUpType.INCREASE.getIndex());
        Map<String, EntityCheckUpRecord> decreaseEntity = records.get(EntityCheckUpType.DECREASE.getIndex());
        Map<String, EntityCheckUpRecord> tempEntity = records.get(-1);
        if (tempEntity != null) {
            analysis.setCurrentCount(tempEntity.get("-1").GetCurrentEntityCount());
            analysis.setLastYCount(tempEntity.get("-1").GetLastEntityCount());
        }
        if (null != increaseEntity) {
            analysis.setIncreaseCount(increaseEntity.size());
            for (EntityCheckUpRecord entityCheckUpRecord : increaseEntity.values()) {
                String xzys = entityCheckUpRecord.GetCheckUpData().GetXBYS();
                if (null != xzys && "".equals(xzys)) {
                    analysis.getIncrease().get("-1").add(entityCheckUpRecord);
                    continue;
                }
                enumDic = null;
                enumDic = analysis.getIncrease().get(xzys);
                if (null == enumDic || xzys == this.getXzCode()) {
                    analysis.getIncrease().get("-1").add(entityCheckUpRecord);
                    continue;
                }
                analysis.getIncrease().get(xzys).add(entityCheckUpRecord);
            }
        }
        if (null != decreaseEntity) {
            analysis.setDecreaseCount(decreaseEntity.size());
            for (EntityCheckUpRecord entityCheckUpRecord : decreaseEntity.values()) {
                String jsys = entityCheckUpRecord.GetCheckUpData().GetZJYS();
                if (null != jsys && "".equals(jsys)) {
                    analysis.getDecrease().get("-1").add(entityCheckUpRecord);
                    continue;
                }
                enumDic = null;
                enumDic = analysis.getDecrease().get(jsys);
                if (null == enumDic) {
                    analysis.getDecrease().get("-1").add(entityCheckUpRecord);
                    continue;
                }
                analysis.getDecrease().get(jsys).add(entityCheckUpRecord);
            }
        }
        HashMap<String, String> xbyyEnum = new HashMap<String, String>();
        for (Object row : increaseEnums) {
            xbyyEnum.put(row.getEntityKeyData(), row.getTitle());
        }
        HashMap<String, String> hashMap = new HashMap<String, String>();
        for (IEntityRow row : decreaseEnums) {
            hashMap.put(row.getEntityKeyData(), row.getTitle());
        }
        if (isDetailed) {
            analysisTitleList = new ArrayList<TableColums>();
            analysisTitleList.add(new TableColums("key", "\u9879\u76ee\uff08\u5355\u4f4d\u540d\u79f0\uff09"));
            analysisTitleList.add(new TableColums("value", "\u5c0f\u8ba1"));
            analysisDataList = new ArrayList<AnalysisTableData>();
            analysisDataList.add(new AnalysisTableData("\u4e0a\u5e74\u5355\u4f4d\u5408\u8ba1", String.valueOf(analysis.getLastYCount())));
            analysisDataList.add(new AnalysisTableData("\u672c\u5e74\u5355\u4f4d\u5408\u8ba1", String.valueOf(analysis.getCurrentCount())));
            analysisDataList.add(new AnalysisTableData("\u51c0\u589e\u51cf\u6570", String.valueOf(analysis.getCurrentCount() - analysis.getLastYCount())));
            if (configurationContent.getXbys() == null) {
                analysisDataList.add(new AnalysisTableData("\u672c\u5e74\u65b0\u589e\u5c0f\u8ba1", String.valueOf(analysis.getIncreaseCount() - (analysis.getIncrease().keySet().contains(this.getXbysLxCode()) ? analysis.getIncrease().get(this.getXbysLxCode()).size() : 0))));
                int IIndex2 = 1;
                for (String string : analysis.getIncrease().keySet()) {
                    if (!"-1".equals(string)) {
                        if (this.getXbysLxCode().equals(string)) continue;
                        analysisDataList.add(new AnalysisTableData(IIndex2 + "." + (String)xbyyEnum.get(string), String.valueOf(analysis.getIncrease().get(string).size())));
                    }
                    ++IIndex2;
                }
                analysisDataList.add(new AnalysisTableData(xbyyEnum.size() > 0 ? "?.\u672a\u77e5\u539f\u56e0\u5c0f\u8ba1" : "\u65b0\u589e", String.valueOf(analysis.getIncrease().get("-1").size() + (analysis.getIncrease().keySet().contains(this.getXbysLxCode()) ? analysis.getIncrease().get(this.getXbysLxCode()).size() : 0))));
                analysisDataList.add(new AnalysisTableData("\u672c\u5e74\u51cf\u5c11\u5c0f\u8ba1", String.valueOf(analysis.getDecreaseCount())));
                int dIndex = 1;
                for (String string : analysis.getDecrease().keySet()) {
                    if (!"-1".equals(string)) {
                        analysisDataList.add(new AnalysisTableData(dIndex + "." + (String)hashMap.get(string), String.valueOf(analysis.getDecrease().get(string).size())));
                    }
                    ++dIndex;
                }
                analysisDataList.add(new AnalysisTableData(hashMap.size() > 0 ? "?.\u672a\u77e5\u539f\u56e0\u5c0f\u8ba1" : "\u51cf\u5c11", String.valueOf(analysis.getDecrease().get("-1").size())));
            } else {
                analysisDataList.add(new AnalysisTableData("\u672c\u5e74\u65b0\u589e\u5c0f\u8ba1", String.valueOf(analysis.getIncreaseCount())));
                int IIndex2 = 1;
                for (String string : analysis.getIncrease().keySet()) {
                    if ("-1".equals(string) || this.getXbysLxCode().equals(string)) continue;
                    analysisDataList.add(new AnalysisTableData(IIndex2 + "." + (String)xbyyEnum.get(string), String.valueOf(analysis.getIncrease().get(string).size())));
                    ++IIndex2;
                }
                analysisDataList.add(new AnalysisTableData(xbyyEnum.size() > 0 ? "?.\u672a\u77e5\u539f\u56e0\u5c0f\u8ba1" : "\u65b0\u589e", String.valueOf(analysis.getIncrease().get("-1").size() + (analysis.getIncrease().keySet().contains(this.getXbysLxCode()) ? analysis.getIncrease().get(this.getXbysLxCode()).size() : 0))));
                analysisDataList.add(new AnalysisTableData("\u672c\u5e74\u51cf\u5c11\u5c0f\u8ba1", String.valueOf(analysis.getDecreaseCount())));
                int dIndex = 1;
                for (String string : analysis.getDecrease().keySet()) {
                    if ("-1".equals(string)) continue;
                    analysisDataList.add(new AnalysisTableData(dIndex + "." + (String)hashMap.get(string), String.valueOf(analysis.getDecrease().get(string).size())));
                    ++dIndex;
                }
                analysisDataList.add(new AnalysisTableData(hashMap.size() > 0 ? "?.\u672a\u77e5\u539f\u56e0\u5c0f\u8ba1" : "\u51cf\u5c11", String.valueOf(analysis.getDecrease().get("-1").size())));
            }
            HashMap<String, Object> analysisMap = new HashMap<String, Object>();
            analysisMap.put("tableData", analysisDataList);
            analysisMap.put("columns", analysisTitleList);
            analysisMap.put("currentTitle", currentTitle);
            analysisMap.put("contrastTitle", contrastTitle);
            return ApiJSONUtil.objectToJsonStr(analysisMap);
        }
        analysisTitleList = new ArrayList();
        analysisTitleList.add(new TableColums("key", "\u9879\u76ee\uff08\u5355\u4f4d\u540d\u79f0\uff09"));
        analysisTitleList.add(new TableColums("value", "\u5c0f\u8ba1"));
        analysisTitleList.add(new TableColums("orgCode", "\u663e\u793a\u4ee3\u7801"));
        analysisDataList = new ArrayList();
        analysisDataList.add(new AnalysisTableData("\u4e0a\u5e74\u5355\u4f4d\u5408\u8ba1", String.valueOf(analysis.getLastYCount())));
        analysisDataList.add(new AnalysisTableData("\u672c\u5e74\u5355\u4f4d\u5408\u8ba1", String.valueOf(analysis.getCurrentCount())));
        analysisDataList.add(new AnalysisTableData("\u51c0\u589e\u51cf\u6570", String.valueOf(analysis.getCurrentCount() - analysis.getLastYCount())));
        if (configurationContent.getXbys() == null) {
            analysisDataList.add(new AnalysisTableData("\u672c\u5e74\u65b0\u589e\u5c0f\u8ba1", String.valueOf(analysis.getIncreaseCount())));
            IIndex = 1;
            for (String string : analysis.getIncrease().keySet()) {
                if (!"-1".equals(string)) {
                    if (this.getXbysLxCode().equals(string)) continue;
                    analysisDataList.add(new AnalysisTableData(IIndex + "." + (String)xbyyEnum.get(string), String.valueOf(analysis.getIncrease().get(string).size())));
                    for (EntityCheckUpRecord entityCheckUpRecord : analysis.getIncrease().get(string)) {
                        analysisDataList.add(new AnalysisTableData(entityCheckUpRecord.GetCheckUpData().GetDWMC(), "", entityCheckUpRecord.GetKey().GetDWZDM(), entityCheckUpRecord.getCheckUpData().getOrgCode()));
                    }
                }
                ++IIndex;
            }
            analysisDataList.add(new AnalysisTableData(xbyyEnum.size() > 0 ? "?.\u672a\u77e5\u539f\u56e0\u5c0f\u8ba1" : "\u65b0\u589e", String.valueOf(analysis.getIncrease().get("-1").size() + (analysis.getIncrease().keySet().contains(this.getXbysLxCode()) ? analysis.getIncrease().get(this.getXbysLxCode()).size() : 0))));
            ArrayList<String> list = new ArrayList<String>();
            list.add("-1");
            list.add(this.getXbysLxCode());
            for (String string : list) {
                if (!analysis.getIncrease().keySet().contains(string)) continue;
                for (EntityCheckUpRecord entityCheckUpRecord : analysis.getIncrease().get(string)) {
                    analysisDataList.add(new AnalysisTableData(entityCheckUpRecord.GetCheckUpData().GetDWMC(), "", entityCheckUpRecord.GetKey().GetDWZDM(), entityCheckUpRecord.getCheckUpData().getOrgCode()));
                }
            }
            analysisDataList.add(new AnalysisTableData("\u672c\u5e74\u51cf\u5c11\u5c0f\u8ba1", String.valueOf(analysis.getDecreaseCount())));
            boolean bl = true;
            for (String string : analysis.getDecrease().keySet()) {
                void var20_41;
                if (!"-1".equals(string)) {
                    analysisDataList.add(new AnalysisTableData((int)var20_41 + "." + (String)hashMap.get(string), String.valueOf(analysis.getDecrease().get(string).size())));
                    for (EntityCheckUpRecord ecRecord : analysis.getDecrease().get(string)) {
                        analysisDataList.add(new AnalysisTableData(ecRecord.GetCheckUpData().GetDWMC(), "", ecRecord.GetKey().GetDWZDM(), ecRecord.getCheckUpData().getOrgCode()));
                    }
                }
                ++var20_41;
            }
            analysisDataList.add(new AnalysisTableData(hashMap.size() > 0 ? "?.\u672a\u77e5\u539f\u56e0\u5c0f\u8ba1" : "\u51cf\u5c11", String.valueOf(analysis.getDecrease().get("-1").size())));
            for (EntityCheckUpRecord entityCheckUpRecord : analysis.getDecrease().get("-1")) {
                analysisDataList.add(new AnalysisTableData(entityCheckUpRecord.GetCheckUpData().GetDWMC(), "", entityCheckUpRecord.GetKey().GetDWZDM(), entityCheckUpRecord.getCheckUpData().getOrgCode()));
            }
        } else {
            analysisDataList.add(new AnalysisTableData("\u672c\u5e74\u65b0\u589e\u5c0f\u8ba1", String.valueOf(analysis.getIncreaseCount())));
            IIndex = 1;
            for (String string : analysis.getIncrease().keySet()) {
                if (!"-1".equals(string)) {
                    if (this.getXbysLxCode().equals(string)) continue;
                    analysisDataList.add(new AnalysisTableData(IIndex + "." + (String)xbyyEnum.get(string), String.valueOf(analysis.getIncrease().get(string).size())));
                    for (EntityCheckUpRecord entityCheckUpRecord : analysis.getIncrease().get(string)) {
                        analysisDataList.add(new AnalysisTableData(entityCheckUpRecord.GetCheckUpData().GetDWMC(), "", entityCheckUpRecord.GetKey().GetDWZDM(), entityCheckUpRecord.getCheckUpData().getOrgCode()));
                    }
                }
                ++IIndex;
            }
            analysisDataList.add(new AnalysisTableData(xbyyEnum.size() > 0 ? "?.\u672a\u77e5\u539f\u56e0\u5c0f\u8ba1" : "\u65b0\u589e", String.valueOf(analysis.getIncrease().get("-1").size() + (analysis.getIncrease().keySet().contains(this.getXbysLxCode()) ? analysis.getIncrease().get(this.getXbysLxCode()).size() : 0))));
            ArrayList list = new ArrayList();
            list.add("-1");
            list.add(this.getXbysLxCode());
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                String string = (String)iterator.next();
                if (!analysis.getIncrease().keySet().contains(string)) continue;
                for (EntityCheckUpRecord entityCheckUpRecord : analysis.getIncrease().get(string)) {
                    analysisDataList.add(new AnalysisTableData(entityCheckUpRecord.GetCheckUpData().GetDWMC(), "", entityCheckUpRecord.GetKey().GetDWZDM(), entityCheckUpRecord.getCheckUpData().getOrgCode()));
                }
            }
            analysisDataList.add(new AnalysisTableData("\u672c\u5e74\u51cf\u5c11\u5c0f\u8ba1", String.valueOf(analysis.getDecreaseCount())));
            boolean bl = true;
            for (String string : analysis.getDecrease().keySet()) {
                void var20_45;
                if (!"-1".equals(string)) {
                    analysisDataList.add(new AnalysisTableData((int)var20_45 + "." + (String)hashMap.get(string), String.valueOf(analysis.getDecrease().get(string).size())));
                    for (EntityCheckUpRecord ecRecord : analysis.getDecrease().get(string)) {
                        analysisDataList.add(new AnalysisTableData(ecRecord.GetCheckUpData().GetDWMC(), "", ecRecord.GetKey().GetDWZDM(), ecRecord.getCheckUpData().getOrgCode()));
                    }
                }
                ++var20_45;
            }
            analysisDataList.add(new AnalysisTableData(hashMap.size() > 0 ? "?.\u672a\u77e5\u539f\u56e0\u5c0f\u8ba1" : "\u51cf\u5c11", String.valueOf(analysis.getDecrease().get("-1").size())));
            for (EntityCheckUpRecord entityCheckUpRecord : analysis.getDecrease().get("-1")) {
                analysisDataList.add(new AnalysisTableData(entityCheckUpRecord.GetCheckUpData().GetDWMC(), "", entityCheckUpRecord.GetKey().GetDWZDM(), entityCheckUpRecord.getCheckUpData().getOrgCode()));
            }
        }
        HashMap<String, Object> analysisMap = new HashMap<String, Object>();
        analysisMap.put("tableData", analysisDataList);
        analysisMap.put("columns", analysisTitleList);
        analysisMap.put("currentTitle", currentTitle);
        analysisMap.put("contrastTitle", contrastTitle);
        return ApiJSONUtil.objectToJsonStr(analysisMap);
    }

    public String returnRsultMap(String currentTitle, String contrastTitle, boolean abnormal, String abnormalMessage, String WebTabName) throws Exception {
        HashMap<String, Object> resultmap = new HashMap<String, Object>();
        resultmap.put("currentTitle", currentTitle);
        resultmap.put("contrastTitle", contrastTitle);
        resultmap.put("abnormal", abnormal);
        resultmap.put("abnormalMessage", abnormalMessage);
        switch (WebTabName) {
            case "\u51cf\u5c11\u5355\u4f4d": {
                resultmap.put("tableData", new ArrayList());
                ArrayList<TableColums> jsyyColumsList = new ArrayList<TableColums>();
                jsyyColumsList.add(new TableColums("id", "\u5e8f\u53f7", 80));
                jsyyColumsList.add(new TableColums("orgCode", "\u663e\u793a\u4ee3\u7801"));
                jsyyColumsList.add(new TableColums("dwmc", "\u5355\u4f4d\u540d\u79f0"));
                jsyyColumsList.add(new TableColums("zjys", "\u51cf\u5c11\u539f\u56e0", "select"));
                resultmap.put("columns", jsyyColumsList);
                resultmap.put("EnumList", new ArrayList());
                resultmap.put("tabName", "\u51cf\u5c11\u5355\u4f4d");
                break;
            }
            case "\u53ef\u80fd\u6709\u8bef\u5355\u4f4d": {
                resultmap.put("tableData", new ArrayList());
                ArrayList<TableColums> xbysColumsList = new ArrayList<TableColums>();
                xbysColumsList.add(new TableColums("groupName", " ", "", 0));
                xbysColumsList.add(new TableColums("id", "\u5e8f\u53f7", "", 1, 80));
                xbysColumsList.add(new TableColums("orgCode", "\u663e\u793a\u4ee3\u7801", "", 2));
                xbysColumsList.add(new TableColums("dwmc", "\u5355\u4f4d\u540d\u79f0", "", 3));
                xbysColumsList.add(new TableColums("errorDescription", "\u9519\u8bef\u8bf4\u660e", "", 4));
                xbysColumsList.add(new TableColums("sndm", "\u4e0a\u5e74\u4ee3\u7801", "sndmInput", 5));
                xbysColumsList.add(new TableColums("zjys", "\u65b0\u62a5\u56e0\u7d20", "select", 6));
                resultmap.put("columns", xbysColumsList);
                resultmap.put("EnumList", new ArrayList());
                break;
            }
            case "\u6838\u5bf9\u7ed3\u679c\u5206\u6790": {
                ArrayList<AnalysisTableData> analysisDataList = new ArrayList<AnalysisTableData>();
                analysisDataList.add(new AnalysisTableData("\u4e0a\u5e74\u5355\u4f4d\u5408\u8ba1", "0"));
                analysisDataList.add(new AnalysisTableData("\u672c\u5e74\u5355\u4f4d\u5408\u8ba1", "0"));
                analysisDataList.add(new AnalysisTableData("\u51c0\u589e\u51cf\u6570", "0"));
                analysisDataList.add(new AnalysisTableData("\u672c\u5e74\u65b0\u589e\u5c0f\u8ba1", "0"));
                analysisDataList.add(new AnalysisTableData("\u672c\u5e74\u51cf\u5c11\u5c0f\u8ba1", "0"));
                resultmap.put("tableData", analysisDataList);
                ArrayList<TableColums> analysisTitleList = new ArrayList<TableColums>();
                analysisTitleList.add(new TableColums("key", "\u9879\u76ee\uff08\u5355\u4f4d\u540d\u79f0\uff09"));
                analysisTitleList.add(new TableColums("value", "\u5c0f\u8ba1"));
                analysisTitleList.add(new TableColums("orgCode", "\u663e\u793a\u4ee3\u7801"));
                resultmap.put("columns", analysisTitleList);
            }
        }
        return ApiJSONUtil.objectToJsonStr(resultmap);
    }

    public String getXbysLxCode() {
        return "0";
    }

    public String getXzCode() {
        return "0";
    }

    public String getTableName(FormSchemeDefine formSchemeDefine) {
        DataQueryHelper dataQueryHelper = (DataQueryHelper)BeanUtil.getBean(DataQueryHelper.class);
        String tableName = "SYS_ENTITYCHECKUP_" + formSchemeDefine.getFormSchemeCode();
        return dataQueryHelper.getLibraryTableName(formSchemeDefine.getTaskKey(), tableName);
    }

    public String getTableName(String formSchemeKey) {
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        if (formSchemeDefine == null) {
            return null;
        }
        DataQueryHelper dataQueryHelper = (DataQueryHelper)BeanUtil.getBean(DataQueryHelper.class);
        String tableName = "SYS_ENTITYCHECKUP_" + formSchemeDefine.getFormSchemeCode();
        return dataQueryHelper.getLibraryTableName(formSchemeDefine.getTaskKey(), tableName);
    }

    public Map<String, String> getExpFormulaValues(EntityCheckVersionObjectInfo versionObjectInfo, String expressionFormula) throws Exception {
        HashMap<String, String> codeToFormulaValues = new HashMap<String, String>();
        IDataAccessProvider accessProvider = (IDataAccessProvider)SpringBeanProvider.getBean(IDataAccessProvider.class);
        IExpressionEvaluator evaluator = accessProvider.newExpressionEvaluator();
        JtableContext executorTableContext = new JtableContext();
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue(versionObjectInfo.getPeriodEntityView().getDimensionName(), (Object)versionObjectInfo.getPeriod());
        executorTableContext.setTaskKey(versionObjectInfo.getTaskKey());
        executorTableContext.setFormSchemeKey(versionObjectInfo.getFromSchemeKey());
        executorTableContext.setDimensionSet(DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet));
        IJtableDataEngineService tableDataEngineService = (IJtableDataEngineService)SpringBeanProvider.getBean(IJtableDataEngineService.class);
        com.jiuqi.np.dataengine.executors.ExecutorContext executorContext = tableDataEngineService.getExecutorContext(executorTableContext);
        executorContext.getVariableManager().add(new Variable("associatedTaskKey", "\u5355\u673a\u7248\u83b7\u53d6\u6838\u5bf9\u4efb\u52a1key", 6, (Object)versionObjectInfo.getTaskKey()));
        ArrayList<String> list = new ArrayList<String>();
        list.add(expressionFormula);
        Map expressionFormulaMap = evaluator.evalBatch(list, executorContext, dimensionValueSet);
        for (Map.Entry entry : expressionFormulaMap.entrySet()) {
            Object obj;
            String key = (String)entry.getKey();
            Object[] objects = (Object[])entry.getValue();
            if (objects == null || objects.length <= 0 || !((obj = objects[0]) instanceof String) || obj == null || !StringUtils.isNotEmpty((String)((String)obj))) continue;
            codeToFormulaValues.put(key, (String)obj);
        }
        return codeToFormulaValues;
    }

    public String getJSDWTabResult(CheckConfigurationContent configurationContent, EntityCheckUpDTO entityCheckUpDTO, String currentTitle, String contrastTitle, List<Map<Integer, Map<String, EntityCheckUpRecord>>> recordList) throws Exception {
        ArrayList<EnumStructure> jsyyEnumList = new ArrayList<EnumStructure>();
        String zjysColumnTitle = "\u51cf\u5c11\u539f\u56e0";
        String zjysTabTitle = "\u51cf\u5c11\u5355\u4f4d";
        if (null != configurationContent.GetJSYYEntityView()) {
            EntityViewDefine viewDefine = configurationContent.GetJSYYEntityView();
            TableModelDefine tableModel = this.metaService.getTableModel(viewDefine.getEntityId());
            IEntityTable jsyyentity = this.entityQueryHelper.buildEntityTable(viewDefine, entityCheckUpDTO.getPeriod(), entityCheckUpDTO.getFormSchemeKey(), false);
            List jsyyRows = jsyyentity.getAllRows();
            Iterator iterator = jsyyRows.iterator();
            while (iterator.hasNext()) {
                IEntityRow dataRow = (IEntityRow)iterator.next();
                EnumStructure jsyyEnumStructure = new EnumStructure();
                jsyyEnumStructure.setKey(dataRow.getEntityKeyData());
                jsyyEnumStructure.setValue(dataRow.getTitle());
                jsyyEnumList.add(jsyyEnumStructure);
            }
            if (tableModel.getTitle() != null && !"".equals(tableModel.getTitle())) {
                zjysColumnTitle = tableModel.getTitle();
                if (tableModel.getTitle().endsWith("\u539f\u56e0")) {
                    int index = tableModel.getTitle().lastIndexOf("\u539f\u56e0");
                    if (index != -1) {
                        zjysTabTitle = tableModel.getTitle().substring(0, index) + "\u5355\u4f4d";
                    }
                } else {
                    zjysTabTitle = tableModel.getTitle();
                }
            }
        }
        int jsyyindex = 0;
        int i = 1;
        ArrayList<JSYYTableData> jsyyList = new ArrayList<JSYYTableData>();
        for (Map<Integer, Map<String, EntityCheckUpRecord>> records : recordList) {
            Map<String, EntityCheckUpRecord> JsyyRecords = records.get(2);
            for (String JsKey : JsyyRecords.keySet()) {
                JSYYTableData jsyyTableData = new JSYYTableData();
                jsyyTableData.setIndex(jsyyindex);
                jsyyTableData.setID(i);
                jsyyTableData.setDWZDM(JsyyRecords.get(JsKey).GetCheckUpData().GetDWZDM());
                jsyyTableData.setDWMC(JsyyRecords.get(JsKey).GetCheckUpData().GetDWMC());
                jsyyTableData.setZJYS(JsyyRecords.get(JsKey).GetCheckUpData().GetZJYS());
                jsyyTableData.setIsModify(false);
                jsyyList.add(jsyyTableData);
                ++jsyyindex;
                ++i;
            }
        }
        ArrayList<TableColums> jsyyColumsList = new ArrayList<TableColums>();
        jsyyColumsList.add(new TableColums("id", "\u5e8f\u53f7", 80));
        jsyyColumsList.add(new TableColums("orgCode", "\u663e\u793a\u4ee3\u7801"));
        jsyyColumsList.add(new TableColums("dwmc", "\u5355\u4f4d\u540d\u79f0"));
        jsyyColumsList.add(new TableColums("zjys", zjysColumnTitle, "select"));
        HashMap<String, Object> resultmap = new HashMap<String, Object>();
        resultmap.put("tableData", jsyyList);
        resultmap.put("columns", jsyyColumsList);
        resultmap.put("EnumList", jsyyEnumList);
        resultmap.put("currentTitle", currentTitle);
        resultmap.put("contrastTitle", contrastTitle);
        resultmap.put("tabName", zjysTabTitle);
        return ApiJSONUtil.objectToJsonStr(resultmap);
    }

    public String getYWDWTabResult(CheckConfigurationContent configurationContent, EntityCheckUpDTO entityCheckUpDTO, String currentTitle, String contrastTitle, List<Map<Integer, Map<String, EntityCheckUpRecord>>> recordList) throws Exception {
        List<String> scope = entityCheckUpDTO.getScope();
        ArrayList<EnumStructure> xbysEnumList = new ArrayList<EnumStructure>();
        if (null != configurationContent.GetXBYSEntityView()) {
            IEntityTable XBYSTable = this.entityQueryHelper.buildEntityTable(configurationContent.GetXBYSEntityView(), entityCheckUpDTO.getPeriod(), entityCheckUpDTO.getFormSchemeKey(), false);
            List xbysRows = XBYSTable.getAllRows();
            for (IEntityRow dataRow : xbysRows) {
                EnumStructure xbysEnum = new EnumStructure();
                xbysEnum.setKey(dataRow.getEntityKeyData());
                xbysEnum.setValue(dataRow.getTitle());
                xbysEnumList.add(xbysEnum);
            }
        }
        ArrayList<XBYSTableData> xbysList = new ArrayList<XBYSTableData>();
        ArrayList<String> filterSameUnit = new ArrayList<String>();
        int xbysindex = 0;
        for (int n = 0; n < 4; ++n) {
            int[] groupNum = new int[]{};
            String groupName = "";
            switch (n) {
                case 0: {
                    groupName = "\u4e0a\u5e74\u4ee3\u7801\u91cd\u7801";
                    groupNum = new int[]{3};
                    break;
                }
                case 1: {
                    groupName = "\u4e0a\u5e74\u4ee3\u7801\u9519\u8bef";
                    groupNum = new int[]{4, 5, 8};
                    break;
                }
                case 2: {
                    groupName = "\u589e\u51cf\u540c\u540d";
                    groupNum = new int[]{6};
                    break;
                }
                case 3: {
                    groupName = "\u589e\u51cf\u540c\u7801";
                    groupNum = new int[]{7};
                }
            }
            int num = 1;
            for (int a : groupNum) {
                for (Map<Integer, Map<String, EntityCheckUpRecord>> records : recordList) {
                    Map<String, EntityCheckUpRecord> XBYSRecords = records.get(a);
                    for (String XbKey : XBYSRecords.keySet()) {
                        if (a == 4 || a == 5 || a == 8 || a == 3) {
                            if (filterSameUnit.contains(XBYSRecords.get(XbKey).GetCheckUpData().GetDWZDM())) continue;
                            filterSameUnit.add(XBYSRecords.get(XbKey).GetCheckUpData().GetDWZDM());
                        }
                        XBYSTableData xbysTableData = new XBYSTableData();
                        xbysTableData.setFmdmid(XBYSRecords.get(XbKey).GetKey().GetFmdmId());
                        xbysTableData.setDwdm(XBYSRecords.get(XbKey).GetCheckUpData().GetDWDM());
                        xbysTableData.setIndex(xbysindex);
                        xbysTableData.setGroupName(groupName);
                        xbysTableData.setId(num);
                        xbysTableData.setDwzdm(XBYSRecords.get(XbKey).GetCheckUpData().GetDWZDM());
                        xbysTableData.setDwmc(XBYSRecords.get(XbKey).GetCheckUpData().GetDWMC());
                        xbysTableData.setErrorDescription(XBYSRecords.get(XbKey).GetCheckUpData().GetErrorDescription());
                        xbysTableData.setSndm(XBYSRecords.get(XbKey).GetCheckUpData().GetSNDM());
                        xbysTableData.setZjys(XBYSRecords.get(XbKey).GetCheckUpData().GetXBYS());
                        xbysTableData.setIsModify(false);
                        xbysList.add(xbysTableData);
                        ++num;
                        ++xbysindex;
                    }
                }
            }
        }
        ArrayList<TableColums> xbysColumsList = new ArrayList<TableColums>();
        xbysColumsList.add(new TableColums("groupName", " ", "", 0));
        xbysColumsList.add(new TableColums("id", "\u5e8f\u53f7", "", 1, 80));
        xbysColumsList.add(new TableColums("orgCode", "\u663e\u793a\u4ee3\u7801", "", 2));
        xbysColumsList.add(new TableColums("dwmc", "\u5355\u4f4d\u540d\u79f0", "", 3));
        xbysColumsList.add(new TableColums("errorDescription", "\u9519\u8bef\u8bf4\u660e", "", 4));
        xbysColumsList.add(new TableColums("sndm", "\u4e0a\u5e74\u4ee3\u7801", "sndmInput", 5));
        xbysColumsList.add(new TableColums("zjys", "\u65b0\u62a5\u56e0\u7d20", "select", 6));
        HashMap<String, Object> xbysmap = new HashMap<String, Object>();
        xbysmap.put("tableData", xbysList);
        xbysmap.put("columns", xbysColumsList);
        xbysmap.put("EnumList", xbysEnumList);
        xbysmap.put("currentTitle", currentTitle);
        xbysmap.put("contrastTitle", contrastTitle);
        return ApiJSONUtil.objectToJsonStr(xbysmap);
    }

    public static enum NodeStatus {
        NEW,
        REMOVED,
        CONTINUOUS;

    }
}

