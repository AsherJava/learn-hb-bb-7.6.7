/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.bean.CustomPeriodData
 *  com.jiuqi.nr.dataentry.internal.service.FormGroupProvider
 *  com.jiuqi.nr.dataentry.model.FuncExecuteConfig
 *  com.jiuqi.nr.dataentry.paramInfo.FormSchemeData
 *  com.jiuqi.nr.dataentry.paramInfo.TaskData
 *  com.jiuqi.nr.dataentry.service.IDataEntryParamService
 *  com.jiuqi.nr.dataentry.service.ITemplateConfigService
 *  com.jiuqi.nr.dataentry.util.DataEntryUtil
 *  com.jiuqi.nr.definition.common.PeriodMatchingType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.IViewDeployController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskLinkDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormSchemePeriodService
 *  com.jiuqi.nr.definitionext.taskExtConfig.internal.controller.TaskExtConfigController
 *  com.jiuqi.nr.designer.common.NrDesignLogHelper
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fmdm.BatchFMDMDTO
 *  com.jiuqi.nr.fmdm.FMDMDataDTO
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nr.fmdm.IFMDMData
 *  com.jiuqi.nr.fmdm.IFMDMDataService
 *  com.jiuqi.nr.fmdm.exception.FMDMUpdateException
 *  com.jiuqi.nr.fmdm.internal.dto.QueryParamDTO
 *  com.jiuqi.nr.formtype.internal.impl.FormTypeApplyServiceImpl
 *  com.jiuqi.nr.formtype.service.IFormTypeService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.input.EntityQueryByViewInfo
 *  com.jiuqi.nr.jtable.params.output.EntityData
 *  com.jiuqi.nr.jtable.params.output.EntityReturnInfo
 *  com.jiuqi.nr.jtable.service.IJtableEntityService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataUpdator
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.internal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.CustomPeriodData;
import com.jiuqi.nr.dataentry.internal.service.FormGroupProvider;
import com.jiuqi.nr.dataentry.model.FuncExecuteConfig;
import com.jiuqi.nr.dataentry.paramInfo.FormSchemeData;
import com.jiuqi.nr.dataentry.paramInfo.TaskData;
import com.jiuqi.nr.dataentry.service.IDataEntryParamService;
import com.jiuqi.nr.dataentry.service.ITemplateConfigService;
import com.jiuqi.nr.dataentry.util.DataEntryUtil;
import com.jiuqi.nr.definition.common.PeriodMatchingType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.IViewDeployController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskLinkDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormSchemePeriodService;
import com.jiuqi.nr.definitionext.taskExtConfig.internal.controller.TaskExtConfigController;
import com.jiuqi.nr.designer.common.NrDesignLogHelper;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.finalaccountsaudit.common.EntityQueryHelper;
import com.jiuqi.nr.finalaccountsaudit.common.FmdmHelper;
import com.jiuqi.nr.finalaccountsaudit.common.INvwaExecuteCallBack;
import com.jiuqi.nr.finalaccountsaudit.common.INvwaRowCallBack;
import com.jiuqi.nr.finalaccountsaudit.common.NvwaDataEngineHelper;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.ApiJSONUtil;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.AssTaskFormSchemeInfo;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.AssTaskToFormSchemes;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.CheckConfigurationContent;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EntityCheckContext;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EntityCheckResult;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EntityCheckUpData;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EntityCheckUpDecrease;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EntityCheckUpRecord;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EntityCheckUpType;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EntityCheckUpUnitInfo;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EntityCheckVersionObjectInfo;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EnumStructure;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.MistakeUnitInfo;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.MultCheckObj;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.MultCheckType;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.PeriodSchemeInfo;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.RelationTaskAndFormScheme;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.SelectStructure;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.controller.IEntityCheckController;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.dto.EntityCheckUpDTO;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.internal.controller.EntityCheckDWZDMController;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.internal.controller.EntityCheckHandle;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.internal.controller.FMDMDataMapController;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.internal.controller.FieldContextWrapper;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.service.rest.IUnitManageEntityCheckData;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common.ConfigInfo;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common.EntityCheckConfigData;
import com.jiuqi.nr.fmdm.BatchFMDMDTO;
import com.jiuqi.nr.fmdm.FMDMDataDTO;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.fmdm.IFMDMData;
import com.jiuqi.nr.fmdm.IFMDMDataService;
import com.jiuqi.nr.fmdm.exception.FMDMUpdateException;
import com.jiuqi.nr.fmdm.internal.dto.QueryParamDTO;
import com.jiuqi.nr.formtype.internal.impl.FormTypeApplyServiceImpl;
import com.jiuqi.nr.formtype.service.IFormTypeService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.EntityQueryByViewInfo;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.params.output.EntityReturnInfo;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaDataUpdator;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class EntityCheckController
implements IEntityCheckController {
    private static final Logger log = LoggerFactory.getLogger(EntityCheckController.class);
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    IViewDeployController deployController;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    FormGroupProvider formGroupProvider;
    @Autowired
    private EntityQueryHelper entityQueryHelper;
    @Autowired
    private IEntityMetaService metaService;
    @Autowired
    private DimensionUtil dimensionUtil;
    private String xbysFieldCode;
    private String sndmFieldCode;
    @Autowired
    EntityCheckHandle entityCheckHandle;
    @Autowired
    private IFMDMDataService fmdmDataService;
    @Autowired
    TaskExtConfigController taskExtConfigController;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private FormTypeApplyServiceImpl formTypeApplyService;
    @Autowired
    private IFormTypeService iFormTypeService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Resource
    private IDataEntryParamService dataEntryParamService;
    @Resource
    private ITemplateConfigService templateConfigService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private IJtableEntityService jtableEntityService;
    @Autowired
    private DataModelService modelService;
    @Autowired
    private EntityCheckDWZDMController entityCheckDWZdmController;
    @Autowired
    private IRuntimeFormSchemePeriodService iRuntimeFormSchemePeriodService;
    @Autowired(required=false)
    private List<IUnitManageEntityCheckData> iUnitManageEntityCheckDataServices = Collections.emptyList();

    @Override
    public String entityCheckUps(EntityCheckUpDTO entityCheckUpDTO, CheckConfigurationContent configurationContent, Map<Integer, Map<String, EntityCheckUpRecord>> recordList) throws Exception {
        String taskKey = entityCheckUpDTO.getTaskKey();
        String formSchemeKey = entityCheckUpDTO.getFormSchemeKey();
        String period = entityCheckUpDTO.getPeriod();
        String WebTabName = entityCheckUpDTO.getWebTabName();
        String associatedTaskKey = entityCheckUpDTO.getAssociatedTaskKey();
        String associatedFormSchemeKey = entityCheckUpDTO.getAssociatedFormSchemeKey();
        String associatedperiod = entityCheckUpDTO.getAssociatedPeriod();
        String filterValue = entityCheckUpDTO.getFilterValue();
        if (StringUtils.isEmpty((String)taskKey) || StringUtils.isEmpty((String)formSchemeKey) || StringUtils.isEmpty((String)period)) {
            return this.entityCheckHandle.returnRsultMap("", "", true, "CurParameterExpInfo", WebTabName);
        }
        String currentTitle = this.entityCheckHandle.getCurrentTitle(this.runTimeViewController, this.periodEngineService, taskKey, formSchemeKey, period);
        if (StringUtils.isEmpty((String)associatedTaskKey) || StringUtils.isEmpty((String)associatedFormSchemeKey) || StringUtils.isEmpty((String)associatedperiod)) {
            return this.entityCheckHandle.returnRsultMap("", "", true, "AssParameterExpInfo", WebTabName);
        }
        String contrastTitle = this.entityCheckHandle.getContrastTitle(this.runTimeViewController, associatedTaskKey, associatedFormSchemeKey, associatedperiod);
        try {
            boolean isCheckUpTable = this.entityCheckHandle.checkUpTableExist(taskKey, formSchemeKey);
            if (!isCheckUpTable) {
                return this.entityCheckHandle.returnRsultMap(currentTitle, contrastTitle, true, "ExpInfo1", WebTabName);
            }
            String results = null;
            switch (WebTabName) {
                case "\u51cf\u5c11\u5355\u4f4d": {
                    String tableName = this.entityCheckHandle.getTableName(formSchemeKey);
                    if (tableName != null) {
                        this.updateZjysData(tableName, formSchemeKey, associatedFormSchemeKey, period + "|" + associatedperiod, recordList);
                    }
                    results = this.entityCheckHandle.getJSDWTabResult(configurationContent, formSchemeKey, period, currentTitle, contrastTitle, recordList, filterValue);
                    break;
                }
                case "\u53ef\u80fd\u6709\u8bef\u5355\u4f4d": {
                    if (configurationContent.getXbys() != null && StringUtils.isNotEmpty((String)configurationContent.getXbys().getCode())) {
                        this.xbysFieldCode = configurationContent.getXbys().getCode();
                    }
                    this.updateXBYS(formSchemeKey, period, recordList, this.xbysFieldCode);
                    results = this.entityCheckHandle.getYWDWTabResult(configurationContent, formSchemeKey, period, currentTitle, contrastTitle, recordList);
                    break;
                }
                case "\u6838\u5bf9\u7ed3\u679c\u5206\u6790": {
                    results = this.entityCheckHandle.getHDJGFXTabResult(configurationContent, formSchemeKey, period, currentTitle, contrastTitle, recordList, entityCheckUpDTO.isDetailed());
                }
            }
            return results;
        }
        catch (Exception e) {
            log.error("\u83b7\u53d6\u6237\u6570\u6838\u5bf9\u7ed3\u679c\u5f02\u5e38", e);
            return this.entityCheckHandle.returnRsultMap(currentTitle, contrastTitle, true, e.getMessage(), WebTabName);
        }
    }

    private void updateXBYS(String formSchemeKey, String period, Map<Integer, Map<String, EntityCheckUpRecord>> recordList, String xbys) {
        QueryParamDTO fMDMDataDTO = new QueryParamDTO();
        fMDMDataDTO.setFormSchemeKey(formSchemeKey);
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("DATATIME", (Object)period);
        fMDMDataDTO.setDimensionValueSet(dimensionValueSet);
        fMDMDataDTO.setFilter(true);
        fMDMDataDTO.setAuthorityType(AuthorityType.Read);
        List dataList = this.fmdmDataService.list((FMDMDataDTO)fMDMDataDTO);
        HashMap<String, IFMDMData> dataMap = new HashMap<String, IFMDMData>();
        if (dataList != null && dataList.size() > 0) {
            for (IFMDMData data : dataList) {
                dataMap.put(data.getValue("CODE").getAsString(), data);
            }
        }
        for (Map.Entry<Integer, Map<String, EntityCheckUpRecord>> entry : recordList.entrySet()) {
            if (entry.getKey() <= EntityCheckUpType.DECREASE.getIndex()) continue;
            Map<String, EntityCheckUpRecord> recordMap = entry.getValue();
            for (Map.Entry<String, EntityCheckUpRecord> recordEntry : recordMap.entrySet()) {
                EntityCheckUpData checkUpData = recordEntry.getValue().getCheckUpData();
                IFMDMData ifmdmData = (IFMDMData)dataMap.get(recordEntry.getKey());
                if (checkUpData == null || ifmdmData == null) continue;
                checkUpData.setXbys(ifmdmData.getValue(xbys).getAsString());
            }
        }
    }

    private void updateZjysData(String tableName, String formSchemeKey, String associatedFormSchemeKey, String period, Map<Integer, Map<String, EntityCheckUpRecord>> recordList) {
        List<EntityCheckUpUnitInfo> entityCheckUp = this.getEntityCheckUp(tableName, formSchemeKey, period, associatedFormSchemeKey);
        if (!CollectionUtils.isEmpty(entityCheckUp)) {
            Map recordMap = recordList.getOrDefault(EntityCheckUpType.DECREASE.getIndex(), Collections.emptyMap());
            for (EntityCheckUpUnitInfo info : entityCheckUp) {
                EntityCheckUpData checkUpData;
                EntityCheckUpRecord entityCheckUpRecord = (EntityCheckUpRecord)recordMap.get(info.getDwzdm());
                if (entityCheckUpRecord == null || (checkUpData = entityCheckUpRecord.getCheckUpData()) == null) continue;
                checkUpData.setZjys(info.getZjys());
            }
        }
    }

    @Override
    public Map<Integer, Map<String, EntityCheckUpRecord>> getRecord(String taskKey, String formSchemeKey, String period, String associatedTaskKey, String associatedFormSchemeKey, String associatedperiod, CheckConfigurationContent configurationContent) {
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        Map<String, EntityCheckUpDecrease> checkUpRecordInDb = this.QueryAllRecordByTask(this.entityCheckHandle.getTableName(formSchemeDefine), formSchemeKey, taskKey, period + "|" + associatedperiod, associatedFormSchemeKey);
        try {
            if (StringUtils.isEmpty((String)taskKey) || StringUtils.isEmpty((String)formSchemeKey) || StringUtils.isEmpty((String)period)) {
                return null;
            }
            if (StringUtils.isEmpty((String)associatedTaskKey) || StringUtils.isEmpty((String)associatedFormSchemeKey) || StringUtils.isEmpty((String)associatedperiod)) {
                return null;
            }
            boolean isCheckUpTable = this.entityCheckHandle.checkUpTableExist(taskKey, formSchemeKey);
            if (!isCheckUpTable) {
                return null;
            }
            if (null == configurationContent && null == (configurationContent = this.getCheckConfigurationContent(taskKey, formSchemeKey, associatedTaskKey, associatedFormSchemeKey))) {
                return null;
            }
            EntityCheckVersionObjectInfo currentVersionObjectInfo = this.entityCheckHandle.getVersionObject(taskKey, formSchemeKey, period, configurationContent.getMatchingInfo().getMatchingType(), configurationContent.getMatchingInfo().getCurrentFormula(), false);
            if (null == currentVersionObjectInfo) {
                return null;
            }
            FieldContextWrapper fieldContextWrapper = (FieldContextWrapper)BeanUtil.getBean(FieldContextWrapper.class);
            fieldContextWrapper.init(currentVersionObjectInfo, configurationContent);
            EntityCheckVersionObjectInfo contrastVersionObjectInfo = this.entityCheckHandle.getVersionObject(associatedTaskKey, associatedFormSchemeKey, associatedperiod, configurationContent.getMatchingInfo().getMatchingType(), configurationContent.getMatchingInfo().getRelatedFormula(), true);
            if (null == contrastVersionObjectInfo) {
                return null;
            }
            fieldContextWrapper.prepareFieldContextWrapper(configurationContent, currentVersionObjectInfo, contrastVersionObjectInfo);
            FMDMDataMapController lastfmdmDataMapController = new FMDMDataMapController(contrastVersionObjectInfo, fieldContextWrapper.getLstfieldContext(), configurationContent);
            FMDMDataMapController curfmdmDataMapController = new FMDMDataMapController(currentVersionObjectInfo, fieldContextWrapper.getCurfieldContext(), configurationContent);
            Map<Integer, Map<String, EntityCheckUpRecord>> records = this.entityCheckHandle.getRecords(fieldContextWrapper, currentVersionObjectInfo, contrastVersionObjectInfo, checkUpRecordInDb, configurationContent, curfmdmDataMapController, lastfmdmDataMapController);
            return records;
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public EntityCheckResult getEntityCheckResult(Map<Integer, Map<String, EntityCheckUpRecord>> records, CheckConfigurationContent configurationContent, String scop) {
        EntityCheckResult result = new EntityCheckResult(scop);
        MultCheckObj mcObj = new MultCheckObj(0, 0, 0, 0, 0);
        result.setCheckObj(mcObj);
        this.xbysFieldCode = configurationContent.getXbys() != null && StringUtils.isNotEmpty((String)configurationContent.getXbys().getCode()) ? configurationContent.getXbys().getCode() : "XBYS";
        if (configurationContent.getSndm() != null && StringUtils.isNotEmpty((String)configurationContent.getSndm().getCode())) {
            this.sndmFieldCode = configurationContent.getSndm().getCode();
        }
        if (records == null) {
            mcObj.setTrue(false);
            mcObj.setMessage("\u6838\u5bf9\u7ed3\u679c\u96c6\u4e3anull");
            mcObj.setCheckType(MultCheckType.ERROR);
        }
        Map<Integer, Map<String, EntityCheckUpRecord>> filterRecords = records.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> ((Map)entry.getValue()).entrySet().stream().filter(innerEntry -> innerEntry.getValue() != null && ((EntityCheckUpRecord)innerEntry.getValue()).getCheckUpData() != null && ((EntityCheckUpRecord)innerEntry.getValue()).getCheckUpData().getParentsEntityKeyDataPath() != null && EntityCheckController.listContainsKey(((EntityCheckUpRecord)innerEntry.getValue()).getCheckUpData().getParentsEntityKeyDataPath(), scop)).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))));
        result.setResultMap(filterRecords);
        return result;
    }

    @Override
    public CheckConfigurationContent getCheckConfigurationContent(String taskKey, String formSchemeKey, String associatedTaskKey, String associatedFormSchemeKey) {
        CheckConfigurationContent configurationContent = null;
        try {
            Object basicCheckItems = this.taskExtConfigController.getTaskExtConfigDefineBySchemakeyCache(taskKey, formSchemeKey, "taskextension-entitycheck");
            if (basicCheckItems == null) {
                return configurationContent;
            }
            ObjectMapper objectMapper = new ObjectMapper();
            EntityCheckConfigData entityCheckConfigData = (EntityCheckConfigData)basicCheckItems;
            FmdmHelper curFmdmHelper = FmdmHelper.newHelper(formSchemeKey);
            FmdmHelper conFmdmHelper = FmdmHelper.newHelper(associatedFormSchemeKey);
            if (entityCheckConfigData.getEntityCheckEnable()) {
                for (int i = 0; i < entityCheckConfigData.getConfigInfos().size(); ++i) {
                    ConfigInfo configInfo = entityCheckConfigData.getConfigInfos().get(i);
                    if (!associatedTaskKey.equals(configInfo.getAssTaskKey()) || !associatedFormSchemeKey.equals(configInfo.getAssFormSchemeKey())) continue;
                    configurationContent = new CheckConfigurationContent();
                    if (StringUtils.isNotEmpty((String)configInfo.getConfigData().getJSYY())) {
                        configurationContent.SetJSYYEntityView(this.entityQueryHelper.getEnumViewByEntityId(configInfo.getConfigData().getJSYY()));
                    }
                    if (StringUtils.isNotEmpty((String)configInfo.getConfigData().getXBYS())) {
                        IFMDMAttribute xbys = curFmdmHelper.queryByCode(configInfo.getConfigData().getXBYS());
                        configurationContent.setXbys(xbys);
                        ColumnModelDefine columnModelDefine = this.modelService.getColumnModelDefineByCode(xbys.getTableID(), xbys.getCode());
                        TableModelDefine tableModeDefine = this.modelService.getTableModelDefineById(columnModelDefine.getReferTableID());
                        configurationContent.SetXBYSEntityView(this.entityQueryHelper.getEnumViewByTableName(tableModeDefine.getCode()));
                    }
                    if (StringUtils.isNotEmpty((String)configInfo.getConfigData().getSNDM())) {
                        configurationContent.setSndm(curFmdmHelper.queryByCode(configInfo.getConfigData().getSNDM()));
                        configurationContent.setSndmFormula(configInfo.getSndmFormula());
                    }
                    if (StringUtils.isNotEmpty((String)configInfo.getConfigData().getDWDM())) {
                        configurationContent.setDwdm(curFmdmHelper.queryByCode(configInfo.getConfigData().getDWDM()));
                    }
                    if (StringUtils.isNotEmpty((String)configInfo.getConfigData().getBBLX())) {
                        configurationContent.setBblx(curFmdmHelper.queryByCode(configInfo.getConfigData().getBBLX()));
                    }
                    if (StringUtils.isNotEmpty((String)configInfo.getConfigData().getSNBBLX())) {
                        configurationContent.setSnbblx(conFmdmHelper.queryByCode(configInfo.getConfigData().getSNBBLX()));
                    }
                    configurationContent.setFilterValue(configInfo.getFilterValue());
                    configurationContent.setMatchingInfo(configInfo.getMatchingInfo());
                    return configurationContent;
                }
            }
        }
        catch (Exception e) {
            log.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return configurationContent;
    }

    @Override
    public String getContrastUnitScopKeyByCurrentScopKey(EntityCheckVersionObjectInfo currentVersionObjectInfo, String associatedTaskKey, String associatedFormSchemeKey, String assperiod, int matchingType, String expressionFormula, String scop) throws Exception {
        return this.getContrastUnitScopKeyByCurrentScopKey(currentVersionObjectInfo, associatedTaskKey, associatedFormSchemeKey, assperiod, matchingType, expressionFormula, null, scop);
    }

    public String getContrastUnitScopKeyByCurrentScopKey(EntityCheckVersionObjectInfo currentVersionObjectInfo, String associatedTaskKey, String associatedFormSchemeKey, String assperiod, int matchingType, String expressionFormula, Map<String, List<IFMDMData>> fmdmDataMap, String scop) throws Exception {
        List<IFMDMData> ifmdmData;
        String contrastUnitScopKey = "";
        IEntityRow row = currentVersionObjectInfo.getEntityTable().findByEntityKey(scop);
        if (row == null) {
            return contrastUnitScopKey;
        }
        String curDwzdm = this.entityCheckDWZdmController.getDwzdmToFmdmDataByMatchingType(currentVersionObjectInfo, scop);
        if (null == fmdmDataMap) {
            fmdmDataMap = this.entityCheckDWZdmController.getDataMapByMatchingType(associatedTaskKey, associatedFormSchemeKey, assperiod, matchingType, expressionFormula);
        }
        if (!fmdmDataMap.isEmpty() && !CollectionUtils.isEmpty(ifmdmData = fmdmDataMap.get(curDwzdm))) {
            if (ifmdmData.size() == 1) {
                contrastUnitScopKey = ifmdmData.get(0).getValue("CODE").getAsString();
            } else {
                for (IFMDMData data : ifmdmData) {
                    if (!row.getAsString("CODE").equals(data.getValue("CODE").getAsString())) continue;
                    contrastUnitScopKey = data.getValue("CODE").getAsString();
                }
            }
        }
        return contrastUnitScopKey;
    }

    @Override
    public String EntityCheckUp(String taskKey, String formSchemeKey, String period, String scop, String WebTabName, boolean isDetailed, String associatedTaskKey, String associatedFormSchemeKey, String associatedperiod, JtableContext jtableContext, String filterValue) throws Exception {
        if (!this.iUnitManageEntityCheckDataServices.isEmpty()) {
            for (IUnitManageEntityCheckData unitManageEntityCheckData : this.iUnitManageEntityCheckDataServices) {
                unitManageEntityCheckData.queryUnitManageEntityCheckData(taskKey, formSchemeKey, period, associatedTaskKey, associatedFormSchemeKey, associatedperiod);
            }
        }
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        Map<String, EntityCheckUpDecrease> checkUpRecordInDb = this.QueryAllRecordByTask(this.entityCheckHandle.getTableName(formSchemeDefine), formSchemeKey, taskKey, period + "|" + associatedperiod, associatedFormSchemeKey);
        if (StringUtils.isEmpty((String)taskKey) || StringUtils.isEmpty((String)formSchemeKey) || StringUtils.isEmpty((String)period)) {
            return this.entityCheckHandle.returnRsultMap("", "", true, "CurParameterExpInfo", WebTabName);
        }
        String currentTitle = this.entityCheckHandle.getTitleInfo(taskKey, formSchemeKey, period);
        if (StringUtils.isEmpty((String)associatedTaskKey) || StringUtils.isEmpty((String)associatedFormSchemeKey) || StringUtils.isEmpty((String)associatedperiod)) {
            return this.entityCheckHandle.returnRsultMap("", "", true, "AssParameterExpInfo", WebTabName);
        }
        String contrastTitle = this.entityCheckHandle.getTitleInfo(associatedTaskKey, associatedFormSchemeKey, associatedperiod);
        try {
            boolean isCheckUpTable = this.entityCheckHandle.checkUpTableExist(taskKey, formSchemeKey);
            if (!isCheckUpTable) {
                return this.entityCheckHandle.returnRsultMap(currentTitle, contrastTitle, true, "ExpInfo1", WebTabName);
            }
            CheckConfigurationContent configurationContent = this.getCheckConfigurationContent(taskKey, formSchemeKey, associatedTaskKey, associatedFormSchemeKey);
            if (null == configurationContent) {
                return this.entityCheckHandle.returnRsultMap(currentTitle, contrastTitle, true, "ExpInfo2", WebTabName);
            }
            EntityCheckVersionObjectInfo currentVersionObjectInfo = this.entityCheckHandle.getVersionObject(taskKey, formSchemeKey, period, configurationContent.getMatchingInfo().getMatchingType(), configurationContent.getMatchingInfo().getCurrentFormula(), false);
            if (null == currentVersionObjectInfo) {
                return this.entityCheckHandle.returnRsultMap(currentTitle, contrastTitle, true, "ExpInfo6", WebTabName);
            }
            FieldContextWrapper fieldContextWrapper = (FieldContextWrapper)BeanUtil.getBean(FieldContextWrapper.class);
            fieldContextWrapper.init(currentVersionObjectInfo, configurationContent);
            EntityCheckVersionObjectInfo contrastVersionObjectInfo = this.entityCheckHandle.getVersionObject(associatedTaskKey, associatedFormSchemeKey, associatedperiod, configurationContent.getMatchingInfo().getMatchingType(), configurationContent.getMatchingInfo().getRelatedFormula(), true);
            if (null == contrastVersionObjectInfo) {
                return this.entityCheckHandle.returnRsultMap(currentTitle, contrastTitle, true, "ExpInfo4", WebTabName);
            }
            fieldContextWrapper.prepareFieldContextWrapper(configurationContent, currentVersionObjectInfo, contrastVersionObjectInfo);
            FMDMDataMapController lastfmdmDataMapController = new FMDMDataMapController(contrastVersionObjectInfo, fieldContextWrapper.getLstfieldContext(), configurationContent);
            FMDMDataMapController curfmdmDataMapController = new FMDMDataMapController(currentVersionObjectInfo, fieldContextWrapper.getCurfieldContext(), configurationContent);
            Map<Integer, Map<String, EntityCheckUpRecord>> records = this.entityCheckHandle.getRecords(fieldContextWrapper, currentVersionObjectInfo, contrastVersionObjectInfo, checkUpRecordInDb, configurationContent, curfmdmDataMapController, lastfmdmDataMapController);
            this.xbysFieldCode = configurationContent.getXbys() != null && StringUtils.isNotEmpty((String)configurationContent.getXbys().getCode()) ? configurationContent.getXbys().getCode() : "XBYS";
            if (configurationContent.getSndm() != null && StringUtils.isNotEmpty((String)configurationContent.getSndm().getCode())) {
                this.sndmFieldCode = configurationContent.getSndm().getCode();
            }
            if (records == null) {
                return this.entityCheckHandle.returnRsultMap(currentTitle, contrastTitle, true, "Exception", WebTabName);
            }
            Map<Integer, Map<String, EntityCheckUpRecord>> filterRecords = records.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> ((Map)entry.getValue()).entrySet().stream().filter(innerEntry -> innerEntry.getValue() != null && ((EntityCheckUpRecord)innerEntry.getValue()).getCheckUpData() != null && ((EntityCheckUpRecord)innerEntry.getValue()).getCheckUpData().getParentsEntityKeyDataPath() != null && EntityCheckController.listContainsKey(((EntityCheckUpRecord)innerEntry.getValue()).getCheckUpData().getParentsEntityKeyDataPath(), scop)).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))));
            int currentYCount = curfmdmDataMapController.queryEntityRowsByScop(currentVersionObjectInfo, scop).size() - curfmdmDataMapController.getMergerAndBalance();
            String associatedScop = "";
            if (StringUtils.isNotEmpty((String)scop)) {
                associatedScop = this.getContrastUnitScopKeyByCurrentScopKey(currentVersionObjectInfo, associatedTaskKey, associatedFormSchemeKey, associatedperiod, configurationContent.getMatchingInfo().getMatchingType(), configurationContent.getMatchingInfo().getRelatedFormula(), scop);
            }
            int lastYCount = lastfmdmDataMapController.queryEntityRowsByScop(contrastVersionObjectInfo, associatedScop).size() - lastfmdmDataMapController.getMergerAndBalance();
            EntityCheckUpRecord recordCount = new EntityCheckUpRecord();
            recordCount.SetLastEntityCount(lastYCount);
            recordCount.SetCurrentEntityCount(currentYCount);
            filterRecords.get(-1).put("-1", recordCount);
            String results = null;
            switch (WebTabName) {
                case "\u51cf\u5c11\u5355\u4f4d": {
                    results = this.entityCheckHandle.getJSDWTabResult(configurationContent, formSchemeKey, period, currentTitle, contrastTitle, filterRecords, filterValue);
                    break;
                }
                case "\u53ef\u80fd\u6709\u8bef\u5355\u4f4d": {
                    results = this.entityCheckHandle.getYWDWTabResult(configurationContent, formSchemeKey, period, currentTitle, contrastTitle, filterRecords);
                    break;
                }
                case "\u6838\u5bf9\u7ed3\u679c\u5206\u6790": {
                    results = this.entityCheckHandle.getHDJGFXTabResult(configurationContent, formSchemeKey, period, currentTitle, contrastTitle, filterRecords, isDetailed);
                }
            }
            return results;
        }
        catch (Exception e) {
            log.info("\u6237\u6570\u6838\u5bf9\u5f02\u5e38\uff1a" + e.getMessage());
            return this.entityCheckHandle.returnRsultMap(currentTitle, contrastTitle, true, "Exception", WebTabName);
        }
    }

    public static boolean listContainsKey(String[] array, String key) {
        for (String item : array) {
            if (!item.equals(key)) continue;
            return true;
        }
        return false;
    }

    @Override
    public String GetAuditResult(AsyncTaskMonitor asyncTaskMonitor, String taskKey, String formSchemeKey, String period, String scop, String associatedTaskKey, String associatedFormSchemeKey, String associatedperiod, JtableContext jtableContext) throws Exception {
        MultCheckObj mcObj = new MultCheckObj(0, 0, 0, 0, 0);
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        if (asyncTaskMonitor != null) {
            asyncTaskMonitor.progressAndMessage(0.15, "\u6b63\u5728\u83b7\u53d6\u6237\u6570\u6838\u5bf9\u51cf\u5c11\u5355\u4f4d\u7ed3\u679c\u96c6");
        }
        Map<String, EntityCheckUpDecrease> checkUpRecordInDb = this.QueryAllRecordByTask(this.entityCheckHandle.getTableName(formSchemeDefine), formSchemeKey, taskKey, period + "|" + associatedperiod, associatedFormSchemeKey);
        try {
            EntityCheckVersionObjectInfo contrastVersionObjectInfo;
            EntityCheckVersionObjectInfo currentVersionObjectInfo;
            CheckConfigurationContent configurationContent;
            boolean isCheckUpTable;
            if (StringUtils.isEmpty((String)taskKey) || StringUtils.isEmpty((String)formSchemeKey) || StringUtils.isEmpty((String)period)) {
                mcObj.setTrue(false);
                mcObj.setMessage("\u5f53\u524d\u4efb\u52a1\u3001\u65b9\u6848\u3001\u65f6\u671f\u53c2\u6570\u5f02\u5e38");
                return ApiJSONUtil.objectToJsonStr(mcObj);
            }
            if (StringUtils.isEmpty((String)associatedTaskKey) || StringUtils.isEmpty((String)associatedFormSchemeKey) || StringUtils.isEmpty((String)associatedperiod)) {
                mcObj.setTrue(false);
                mcObj.setMessage("\u5173\u8054\u4efb\u52a1\u3001\u65b9\u6848\u3001\u65f6\u671f\u53c2\u6570\u5f02\u5e38");
                return ApiJSONUtil.objectToJsonStr(mcObj);
            }
            if (asyncTaskMonitor != null) {
                asyncTaskMonitor.progressAndMessage(0.3, "\u6b63\u5728\u83b7\u53d6\u5f53\u524d\u4efb\u52a1\u6807\u9898\u4ee5\u53ca\u5bf9\u6bd4\u4efb\u52a1\u6807\u9898");
            }
            String currentTitle = this.entityCheckHandle.getTitleInfo(taskKey, formSchemeKey, period);
            String contrastTitle = this.entityCheckHandle.getTitleInfo(associatedTaskKey, associatedFormSchemeKey, associatedperiod);
            if (asyncTaskMonitor != null) {
                asyncTaskMonitor.progressAndMessage(0.4, "\u6b63\u5728\u68c0\u67e5\u662f\u5426\u6709\u6237\u6570\u6838\u5bf9\u8bb0\u5f55\u8868");
            }
            if (!(isCheckUpTable = this.entityCheckHandle.checkUpTableExist(taskKey, formSchemeKey))) {
                mcObj.setTrue(false);
                mcObj.setMessage("\u6237\u6570\u6838\u5bf9\u8bb0\u5f55\u8868\u4e0d\u5b58\u5728\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u4efb\u52a1\u521b\u5efa\u5b58\u50a8\u8868");
                return ApiJSONUtil.objectToJsonStr(mcObj);
            }
            if (asyncTaskMonitor != null) {
                asyncTaskMonitor.progressAndMessage(0.55, "\u6b63\u5728\u83b7\u53d6\u6838\u5bf9\u914d\u7f6e\u5185\u5bb9\u5bf9\u8c61");
            }
            if (null == (configurationContent = this.getCheckConfigurationContent(taskKey, formSchemeKey, associatedTaskKey, associatedFormSchemeKey))) {
                mcObj.setTrue(false);
                mcObj.setMessage("\u83b7\u53d6\u6838\u5bf9\u914d\u7f6e\u5185\u5bb9\u5931\u8d25\uff0c\u8bf7\u68c0\u67e5\u662f\u5426\u914d\u7f6e\u6838\u5bf9\u914d\u7f6e");
                return ApiJSONUtil.objectToJsonStr(mcObj);
            }
            if (asyncTaskMonitor != null) {
                asyncTaskMonitor.progressAndMessage(0.6, "\u6b63\u5728\u83b7\u53d6\u5f53\u524d\u7248\u672c\u5bf9\u8c61\u4fe1\u606f");
            }
            if (null == (currentVersionObjectInfo = this.entityCheckHandle.getVersionObject(taskKey, formSchemeKey, period, configurationContent.getMatchingInfo().getMatchingType(), configurationContent.getMatchingInfo().getCurrentFormula(), false))) {
                mcObj.setTrue(false);
                mcObj.setMessage("\u83b7\u53d6\u5f53\u671f\u7248\u672c\u5bf9\u8c61\u5931\u8d25");
                return ApiJSONUtil.objectToJsonStr(mcObj);
            }
            FieldContextWrapper fieldContextWrapper = (FieldContextWrapper)BeanUtil.getBean(FieldContextWrapper.class);
            fieldContextWrapper.init(currentVersionObjectInfo, configurationContent);
            String associatedScop = "";
            if (StringUtils.isNotEmpty((String)scop)) {
                associatedScop = this.getContrastUnitScopKeyByCurrentScopKey(currentVersionObjectInfo, associatedTaskKey, associatedFormSchemeKey, associatedperiod, configurationContent.getMatchingInfo().getMatchingType(), configurationContent.getMatchingInfo().getRelatedFormula(), scop);
            }
            if (asyncTaskMonitor != null) {
                asyncTaskMonitor.progressAndMessage(0.65, "\u6b63\u5728\u83b7\u53d6\u5bf9\u6bd4\u7248\u672c\u5bf9\u8c61\u4fe1\u606f");
            }
            if (null == (contrastVersionObjectInfo = this.entityCheckHandle.getVersionObject(associatedTaskKey, associatedFormSchemeKey, associatedperiod, configurationContent.getMatchingInfo().getMatchingType(), configurationContent.getMatchingInfo().getRelatedFormula(), true))) {
                mcObj.setTrue(false);
                mcObj.setMessage("\u83b7\u53d6\u5bf9\u6bd4\u7248\u672c\u5bf9\u8c61\u5931\u8d25,\u8bf7\u68c0\u67e5\u662f\u5426\u914d\u7f6e\u5173\u8054\u4efb\u52a1");
                return ApiJSONUtil.objectToJsonStr(mcObj);
            }
            fieldContextWrapper.prepareFieldContextWrapper(configurationContent, currentVersionObjectInfo, contrastVersionObjectInfo);
            FMDMDataMapController lastfmdmDataMapController = new FMDMDataMapController(contrastVersionObjectInfo, fieldContextWrapper.getLstfieldContext(), configurationContent);
            FMDMDataMapController curfmdmDataMapController = new FMDMDataMapController(currentVersionObjectInfo, fieldContextWrapper.getCurfieldContext(), configurationContent);
            Map<Integer, Map<String, EntityCheckUpRecord>> records = this.entityCheckHandle.getRecords(fieldContextWrapper, currentVersionObjectInfo, contrastVersionObjectInfo, checkUpRecordInDb, configurationContent, curfmdmDataMapController, lastfmdmDataMapController);
            this.xbysFieldCode = configurationContent.getXbys() != null && StringUtils.isNotEmpty((String)configurationContent.getXbys().getCode()) ? configurationContent.getXbys().getCode() : "XBYS";
            if (configurationContent.getSndm() != null && StringUtils.isNotEmpty((String)configurationContent.getSndm().getCode())) {
                this.sndmFieldCode = configurationContent.getSndm().getCode();
            }
            if (asyncTaskMonitor != null) {
                asyncTaskMonitor.progressAndMessage(0.9, "\u6b63\u5728\u83b7\u53d6\u6237\u6570\u6838\u5bf9\u7ed3\u679c\u4fe1\u606f");
            }
            Map<Integer, Map> filterRecords = records.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> ((Map)entry.getValue()).entrySet().stream().filter(innerEntry -> innerEntry.getValue() != null && ((EntityCheckUpRecord)innerEntry.getValue()).getCheckUpData() != null && ((EntityCheckUpRecord)innerEntry.getValue()).getCheckUpData().getParentsEntityKeyDataPath() != null && EntityCheckController.listContainsKey(((EntityCheckUpRecord)innerEntry.getValue()).getCheckUpData().getParentsEntityKeyDataPath(), scop)).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))));
            int dqdwCount = 0;
            int yzdwCount = 0;
            int jsdwCount = 0;
            int ywdwCount = 0;
            int xzdwCount = 0;
            dqdwCount = curfmdmDataMapController.queryEntityRowsByScop(currentVersionObjectInfo, scop).size() - curfmdmDataMapController.getMergerAndBalance();
            mcObj.setDqdwCount(dqdwCount);
            int type = EntityCheckUpType.DECREASE.getIndex();
            jsdwCount = filterRecords.get(type).size();
            mcObj.setJsdwCount(jsdwCount);
            int errorCount = 0;
            for (int n = 0; n < 4; ++n) {
                int[] groupNum = new int[]{};
                switch (n) {
                    case 0: {
                        groupNum = new int[]{3};
                        break;
                    }
                    case 1: {
                        groupNum = new int[]{4, 5, 8};
                        break;
                    }
                    case 2: {
                        groupNum = new int[]{6};
                        break;
                    }
                    case 3: {
                        groupNum = new int[]{7};
                    }
                }
                for (int a : groupNum) {
                    errorCount += filterRecords.get(a).size();
                }
            }
            ywdwCount = errorCount;
            mcObj.setYwdwCount(ywdwCount);
            int xzdwtype = EntityCheckUpType.INCREASE.getIndex();
            xzdwCount = filterRecords.get(xzdwtype).size();
            mcObj.setXzdwCount(xzdwCount);
            yzdwCount = dqdwCount - xzdwCount;
            mcObj.setYzdwCount(yzdwCount);
            NrDesignLogHelper.log((String)"\u6237\u6570\u6838\u5bf9", (String)"\u6267\u884c\u6237\u6570\u6838\u5bf9", (int)NrDesignLogHelper.LOGLEVEL_INFO);
        }
        catch (Exception e) {
            NrDesignLogHelper.log((String)"\u6237\u6570\u6838\u5bf9", (String)e.getMessage(), (int)NrDesignLogHelper.LOGLEVEL_INFO);
        }
        return ApiJSONUtil.objectToJsonStr(mcObj);
    }

    @Override
    public boolean updateBatchToFMDM(EntityCheckContext entityCheckContext) {
        String formSchemeKey = entityCheckContext.getContext().getFormSchemeKey();
        String period = ((DimensionValue)entityCheckContext.getContext().getDimensionSet().get("DATATIME")).getValue();
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formSchemeDefine.getTaskKey());
        String dataSchemeId = taskDefine.getDataScheme();
        String dwView = formSchemeDefine.getDw();
        IEntityDefine entityDefine = null;
        try {
            entityDefine = this.metaService.queryEntity(dwView);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        EntityViewDefine defaultEntityView = this.entityQueryHelper.getDwEntityView(formSchemeKey);
        String mainDimName = this.dimensionUtil.getDwMainDimName(formSchemeKey);
        List<MistakeUnitInfo> mistakeUnitInfos = entityCheckContext.getMistakeUnitInfo();
        if (mistakeUnitInfos != null && mistakeUnitInfos.size() > 0) {
            BatchFMDMDTO batchFMDMDTO = new BatchFMDMDTO();
            batchFMDMDTO.setFormSchemeKey(formSchemeKey);
            for (MistakeUnitInfo mUnit : mistakeUnitInfos) {
                FMDMDataDTO fmdmDataDTO = new FMDMDataDTO();
                fmdmDataDTO.setFormSchemeKey(formSchemeKey);
                DimensionValueSet dimensionValueSet = new DimensionValueSet();
                dimensionValueSet.setValue("DATATIME", (Object)period);
                dimensionValueSet.setValue(mainDimName, (Object)mUnit.getFmdmid());
                fmdmDataDTO.setDimensionValueSet(dimensionValueSet);
                fmdmDataDTO.setValue(this.xbysFieldCode, (Object)mUnit.getZjys());
                if (StringUtils.isNotEmpty((String)this.sndmFieldCode)) {
                    fmdmDataDTO.setValue(this.sndmFieldCode, (Object)mUnit.getSndm());
                }
                batchFMDMDTO.addFmdmUpdateDTO(fmdmDataDTO);
            }
            try {
                this.fmdmDataService.batchUpdateFMDM(batchFMDMDTO);
            }
            catch (FMDMUpdateException e) {
                log.error(e.getMessage(), e);
            }
        }
        return true;
    }

    @Override
    public boolean insertEntityCheckUp(EntityCheckContext entityCheckContext) throws Exception {
        boolean resultBolean = true;
        final ArrayList<EntityCheckUpUnitInfo> insertList = new ArrayList<EntityCheckUpUnitInfo>();
        ArrayList<EntityCheckUpUnitInfo> updateList = new ArrayList<EntityCheckUpUnitInfo>();
        final String formSchemeKey = entityCheckContext.getContext().getFormSchemeKey();
        final String period = ((DimensionValue)entityCheckContext.getContext().getDimensionSet().get("DATATIME")).getValue().toString();
        final String associatedFormSchemeKey = entityCheckContext.getAssociatedFormSchemeKey();
        final String associatedperiod = entityCheckContext.getAssociatedperiod();
        String periodStr = period + "|" + associatedperiod;
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        List<EntityCheckUpUnitInfo> entityCheckUpList = this.getEntityCheckUp(this.entityCheckHandle.getTableName(formSchemeDefine), formSchemeKey, periodStr, associatedFormSchemeKey);
        List<EntityCheckUpUnitInfo> unitInfos = entityCheckContext.getUnitInfo();
        for (EntityCheckUpUnitInfo unitInfo : unitInfos) {
            if (entityCheckUpList.contains(unitInfo)) {
                updateList.add(unitInfo);
                continue;
            }
            insertList.add(unitInfo);
        }
        if (updateList.size() > 0) {
            resultBolean = this.updateEntityCheckUp(entityCheckContext, updateList);
        }
        if (insertList.size() > 0) {
            NvwaDataEngineHelper nvwaDataEngineHelper = (NvwaDataEngineHelper)BeanUtil.getBean(NvwaDataEngineHelper.class);
            nvwaDataEngineHelper.setTableName(this.entityCheckHandle.getTableName(formSchemeDefine));
            resultBolean = nvwaDataEngineHelper.execute(new INvwaExecuteCallBack(){

                @Override
                public void execute(INvwaUpdatableDataSet iNvwaUpdatableDataSet, INvwaDataUpdator iNvwaDataUpdator, List<ColumnModelDefine> columns) throws Exception {
                    for (EntityCheckUpUnitInfo unitInfo : insertList) {
                        INvwaDataRow iNvwaDataRow = iNvwaDataUpdator.addInsertRow();
                        block19: for (int i = 0; i < columns.size(); ++i) {
                            ColumnModelDefine columnModelDefine = columns.get(i);
                            switch (columnModelDefine.getCode()) {
                                case "ECU_TK_KEY": {
                                    iNvwaDataRow.setValue(i, (Object)associatedFormSchemeKey);
                                    continue block19;
                                }
                                case "ECU_FC_KEY": {
                                    iNvwaDataRow.setValue(i, (Object)formSchemeKey);
                                    continue block19;
                                }
                                case "ECU_UNIT_KEY": {
                                    iNvwaDataRow.setValue(i, (Object)unitInfo.getDwzdm());
                                    continue block19;
                                }
                                case "ECU_CHECK": {
                                    iNvwaDataRow.setValue(i, (Object)"");
                                    continue block19;
                                }
                                case "ECU_UNITNAME": {
                                    iNvwaDataRow.setValue(i, (Object)unitInfo.getDwmc());
                                    continue block19;
                                }
                                case "ECU_JSYS": {
                                    iNvwaDataRow.setValue(i, (Object)unitInfo.getZjys());
                                    continue block19;
                                }
                                case "PERIOD": {
                                    iNvwaDataRow.setValue(i, (Object)(period + "|" + associatedperiod));
                                    continue block19;
                                }
                                default: {
                                    iNvwaDataRow.setValue(i, (Object)"00000000-0000-0000-0000-000000000000");
                                }
                            }
                        }
                    }
                }
            });
        }
        return resultBolean;
    }

    @Override
    public List<EntityCheckUpUnitInfo> queryEntityCheckUp(EntityCheckContext entityCheckContext) {
        String formSchemeKey = entityCheckContext.getContext().getFormSchemeKey();
        String period = ((DimensionValue)entityCheckContext.getContext().getDimensionSet().get("DATATIME")).getValue().toString();
        String associatedFormSchemeKey = entityCheckContext.getAssociatedFormSchemeKey();
        String associatedperiod = entityCheckContext.getAssociatedperiod();
        String periodStr = period + "|" + associatedperiod;
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        return this.getEntityCheckUp(this.entityCheckHandle.getTableName(formSchemeDefine), formSchemeKey, periodStr, associatedFormSchemeKey);
    }

    private boolean updateEntityCheckUp(EntityCheckContext entityCheckContext, List<EntityCheckUpUnitInfo> unitInfos) throws Exception {
        String formSchemeKey = entityCheckContext.getContext().getFormSchemeKey();
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        String associatedFormSchemeKey = entityCheckContext.getAssociatedFormSchemeKey();
        String period = ((DimensionValue)entityCheckContext.getContext().getDimensionSet().get("DATATIME")).getValue().toString();
        String associatedperiod = entityCheckContext.getAssociatedperiod();
        String periodStr = period + "|" + associatedperiod;
        ArrayList<String> unitKeys = new ArrayList<String>();
        final HashMap<String, EntityCheckUpUnitInfo> unitKeyAndObj = new HashMap<String, EntityCheckUpUnitInfo>();
        for (EntityCheckUpUnitInfo unitInfo : unitInfos) {
            unitKeys.add(unitInfo.getDwzdm());
            unitKeyAndObj.put(unitInfo.getDwzdm(), unitInfo);
        }
        String table_name = this.entityCheckHandle.getTableName(formSchemeDefine);
        HashMap<String, Object> conditions = new HashMap<String, Object>();
        conditions.put("ECU_FC_KEY", formSchemeKey);
        conditions.put("PERIOD", periodStr);
        conditions.put("ECU_TK_KEY", associatedFormSchemeKey);
        conditions.put("ECU_UNIT_KEY", unitKeys);
        final NvwaDataEngineHelper nvwaDataEngineHelper = (NvwaDataEngineHelper)BeanUtil.getBean(NvwaDataEngineHelper.class);
        nvwaDataEngineHelper.setTableName(table_name);
        nvwaDataEngineHelper.setConditions(conditions);
        return nvwaDataEngineHelper.execute(new INvwaExecuteCallBack(){

            @Override
            public void execute(INvwaUpdatableDataSet iNvwaUpdatableDataSet, INvwaDataUpdator iNvwaDataUpdator, List<ColumnModelDefine> columns) throws Exception {
                for (INvwaDataRow row : iNvwaUpdatableDataSet) {
                    EntityCheckUpUnitInfo unitInfo = (EntityCheckUpUnitInfo)unitKeyAndObj.get(row.getValue(nvwaDataEngineHelper.getColumnModelDefine("ECU_UNIT_KEY")));
                    INvwaDataRow iNvwaDataRow = iNvwaDataUpdator.addUpdateRow(row.getRowKey());
                    block7: for (int i = 0; i < columns.size(); ++i) {
                        ColumnModelDefine columnModelDefine = columns.get(i);
                        switch (columnModelDefine.getCode()) {
                            case "ECU_JSYS": {
                                iNvwaDataRow.setValue(i, (Object)unitInfo.getZjys());
                                continue block7;
                            }
                            default: {
                                iNvwaDataRow.setValue(i, row.getValue(columnModelDefine));
                            }
                        }
                    }
                }
            }
        });
    }

    private boolean deleteEntityCheckUp(EntityCheckContext entityCheckContext, List<EntityCheckUpUnitInfo> unitInfos) throws Exception {
        String formSchemeKey = entityCheckContext.getContext().getFormSchemeKey();
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        String associatedFormSchemeKey = entityCheckContext.getAssociatedFormSchemeKey();
        String period = ((DimensionValue)entityCheckContext.getContext().getDimensionSet().get("DATATIME")).getValue().toString();
        String associatedperiod = entityCheckContext.getAssociatedperiod();
        String periodStr = period + "|" + associatedperiod;
        ArrayList<String> unitKeys = new ArrayList<String>();
        HashMap<String, EntityCheckUpUnitInfo> unitKeyAndObj = new HashMap<String, EntityCheckUpUnitInfo>();
        for (EntityCheckUpUnitInfo unitInfo : unitInfos) {
            unitKeys.add(unitInfo.getDwzdm());
            unitKeyAndObj.put(unitInfo.getDwzdm(), unitInfo);
        }
        String table_name = this.entityCheckHandle.getTableName(formSchemeDefine);
        HashMap<String, Object> conditions = new HashMap<String, Object>();
        conditions.put("ECU_FC_KEY", formSchemeKey);
        conditions.put("PERIOD", periodStr);
        conditions.put("ECU_TK_KEY", associatedFormSchemeKey);
        conditions.put("ECU_UNIT_KEY", unitKeys);
        NvwaDataEngineHelper nvwaDataEngineHelper = (NvwaDataEngineHelper)BeanUtil.getBean(NvwaDataEngineHelper.class);
        nvwaDataEngineHelper.setTableName(table_name);
        nvwaDataEngineHelper.setConditions(conditions);
        return nvwaDataEngineHelper.execute(new INvwaExecuteCallBack(){

            @Override
            public void execute(INvwaUpdatableDataSet iNvwaUpdatableDataSet, INvwaDataUpdator iNvwaDataUpdator, List<ColumnModelDefine> columns) throws Exception {
                iNvwaDataUpdator.deleteAll();
            }
        });
    }

    private List<EntityCheckUpUnitInfo> getEntityCheckUp(String table_name, String fc_key, String period, String last_fc_key) {
        final ArrayList<EntityCheckUpUnitInfo> list = new ArrayList<EntityCheckUpUnitInfo>();
        HashMap<String, Object> conditions = new HashMap<String, Object>();
        conditions.put("ECU_FC_KEY", fc_key);
        conditions.put("PERIOD", period);
        conditions.put("ECU_TK_KEY", last_fc_key);
        NvwaDataEngineHelper nvwaDataEngineHelper = (NvwaDataEngineHelper)BeanUtil.getBean(NvwaDataEngineHelper.class);
        nvwaDataEngineHelper.setTableName(table_name);
        nvwaDataEngineHelper.setConditions(conditions);
        nvwaDataEngineHelper.query(new INvwaRowCallBack(){

            @Override
            public void queryForObject(List<ColumnModelDefine> columns, MemoryDataSet<NvwaQueryColumn> rows) {
                for (DataRow nextRow : rows) {
                    EntityCheckUpUnitInfo ecInfo = new EntityCheckUpUnitInfo();
                    for (int i = 0; i < columns.size(); ++i) {
                        if (columns.get(i).getCode().equals("ECU_UNIT_KEY")) {
                            ecInfo.setDwzdm(nextRow.getString(i));
                            continue;
                        }
                        if (columns.get(i).getCode().equals("ECU_UNITNAME")) {
                            ecInfo.setDwmc(nextRow.getString(i));
                            continue;
                        }
                        if (!columns.get(i).getCode().equals("ECU_JSYS")) continue;
                        ecInfo.setZjys(nextRow.getString(i));
                    }
                    list.add(ecInfo);
                }
            }
        });
        return list;
    }

    public Map<String, EntityCheckUpDecrease> QueryAllRecordByTask(String table_name, final String fc_key, final String tk_key, final String period, String last_fc_key) {
        final HashMap<String, EntityCheckUpDecrease> recordData = new HashMap<String, EntityCheckUpDecrease>();
        HashMap<String, Object> conditions = new HashMap<String, Object>();
        conditions.put("ECU_FC_KEY", fc_key);
        conditions.put("PERIOD", period);
        conditions.put("ECU_TK_KEY", last_fc_key);
        NvwaDataEngineHelper nvwaDataEngineHelper = (NvwaDataEngineHelper)BeanUtil.getBean(NvwaDataEngineHelper.class);
        nvwaDataEngineHelper.setTableName(table_name);
        nvwaDataEngineHelper.setConditions(conditions);
        nvwaDataEngineHelper.query(new INvwaRowCallBack(){

            @Override
            public void queryForObject(List<ColumnModelDefine> columns, MemoryDataSet<NvwaQueryColumn> rows) {
                for (DataRow nextRow : rows) {
                    EntityCheckUpDecrease record = new EntityCheckUpDecrease();
                    for (int i = 0; i < columns.size(); ++i) {
                        if (columns.get(i).getCode().equals("ECU_UNIT_KEY")) {
                            record.SetDWZDM(nextRow.getString(i));
                            continue;
                        }
                        if (columns.get(i).getCode().equals("ECU_UNITNAME")) {
                            record.SetDWMC(nextRow.getString(i));
                            continue;
                        }
                        if (!columns.get(i).getCode().equals("ECU_JSYS")) continue;
                        record.SetJSYS(nextRow.getString(i));
                    }
                    record.SetTaskId(tk_key);
                    record.SetFromScheme(fc_key);
                    record.SetPeriod(period);
                    recordData.put(record.GetDWZDM(), record);
                }
            }
        });
        return recordData;
    }

    @Override
    public String getRelationTaskJsonStr(String formSchemeKey) throws Exception {
        List taskLinkDefines = this.runTimeViewController.queryLinksByCurrentFormScheme(formSchemeKey);
        ArrayList<SelectStructure> selectStructureList = new ArrayList<SelectStructure>();
        HashMap<String, String> structureMap = new HashMap<String, String>();
        for (TaskLinkDefine taskLink : taskLinkDefines) {
            SelectStructure selectStructure = new SelectStructure();
            FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(taskLink.getRelatedFormSchemeKey());
            if (formSchemeDefine == null || StringUtils.isEmpty((String)taskLink.getDescription()) || StringUtils.isEmpty((String)formSchemeDefine.getTaskKey()) || StringUtils.isEmpty((String)formSchemeDefine.getTitle()) || StringUtils.isEmpty((String)formSchemeDefine.getKey())) continue;
            selectStructure.setKey(formSchemeDefine.getTaskKey());
            selectStructure.setTitle(taskLink.getDescription());
            if (structureMap.containsKey(selectStructure.getKey())) continue;
            structureMap.put(selectStructure.getKey(), selectStructure.getTitle());
            selectStructureList.add(selectStructure);
        }
        return ApiJSONUtil.objectToJsonStr(selectStructureList);
    }

    @Override
    public int getRelationTaskCount(String formSchemeKey) {
        List taskLinkDefines = this.runTimeViewController.queryLinksByCurrentFormScheme(formSchemeKey);
        int count = 0;
        for (TaskLinkDefine taskLink : taskLinkDefines) {
            FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(taskLink.getRelatedFormSchemeKey());
            if (formSchemeDefine == null) continue;
            ++count;
        }
        return count;
    }

    @Override
    public String getRelationTaskTitle(String taskKey, String formSchemeKey, String period) throws Exception {
        String title = null;
        List taskLinkDefines = this.runTimeViewController.queryLinksByCurrentFormScheme(formSchemeKey);
        TaskLinkDefine taskDefine = null;
        for (TaskLinkDefine taskLink : taskLinkDefines) {
            FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(taskLink.getRelatedFormSchemeKey());
            if (formSchemeDefine == null) continue;
            taskDefine = taskLink;
        }
        if (taskDefine == null) {
            return null;
        }
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(taskDefine.getRelatedFormSchemeKey());
        RelationTaskAndFormScheme relationObj = new RelationTaskAndFormScheme();
        relationObj.setTaskKey(formSchemeDefine.getTaskKey());
        relationObj.setTaskTitle(taskDefine.getDescription());
        relationObj.setFormSchemeKey(taskDefine.getRelatedFormSchemeKey());
        relationObj.setFormSchemenTitle(taskDefine.getTitle());
        String contrastPeriod = "";
        PeriodMatchingType periodMatchType = taskDefine.getConfiguration();
        DefaultPeriodAdapter periodAdapter = new DefaultPeriodAdapter();
        PeriodWrapper periodWrapper = new PeriodWrapper(period);
        if (periodMatchType == PeriodMatchingType.PERIOD_TYPE_CURRENT) {
            contrastPeriod = period;
        } else if (periodMatchType == PeriodMatchingType.PERIOD_TYPE_NEXT) {
            periodAdapter.nextPeriod(periodWrapper);
            contrastPeriod = periodWrapper.toString();
        } else if (periodMatchType == PeriodMatchingType.PERIOD_TYPE_OFFSET) {
            periodAdapter.modify(periodWrapper, PeriodModifier.parse((String)taskDefine.getPeriodOffset()));
            contrastPeriod = periodWrapper.toString();
        } else if (periodMatchType == PeriodMatchingType.PERIOD_TYPE_PREVIOUS) {
            periodAdapter.priorPeriod(periodWrapper);
            contrastPeriod = periodWrapper.toString();
        } else if (periodMatchType == PeriodMatchingType.PERIOD_TYPE_SPECIFIED) {
            contrastPeriod = taskDefine.getSpecified();
        }
        relationObj.setPeriod(contrastPeriod);
        title = taskDefine.getDescription() + " | " + taskDefine.getTitle() + " | " + (StringUtils.isEmpty((String)contrastPeriod) ? contrastPeriod : periodAdapter.getPeriodTitle(contrastPeriod));
        HashMap<String, Object> resultmap = new HashMap<String, Object>();
        resultmap.put("constrastInfo", relationObj);
        resultmap.put("title", title);
        return ApiJSONUtil.objectToJsonStr(resultmap);
    }

    @Override
    public String getRelationTaskToFromSchemeJsonStr(String taskKey, String formSchemeKey, String period) throws Exception {
        List taskLinkDefines = this.runTimeViewController.queryLinksByCurrentFormScheme(formSchemeKey);
        ArrayList<RelationTaskAndFormScheme> list = new ArrayList<RelationTaskAndFormScheme>();
        for (TaskLinkDefine taskLink : taskLinkDefines) {
            FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(taskLink.getRelatedFormSchemeKey());
            if (formSchemeDefine == null) continue;
            RelationTaskAndFormScheme relationObj = new RelationTaskAndFormScheme();
            if (StringUtils.isEmpty((String)taskLink.getDescription()) || StringUtils.isEmpty((String)formSchemeDefine.getTaskKey()) || StringUtils.isEmpty((String)formSchemeDefine.getTitle()) || StringUtils.isEmpty((String)formSchemeDefine.getKey())) continue;
            relationObj.setFormSchemeKey(formSchemeDefine.getKey());
            relationObj.setFormSchemenTitle(formSchemeDefine.getTitle());
            relationObj.setTaskKey(formSchemeDefine.getTaskKey());
            relationObj.setTaskTitle(taskLink.getDescription());
            String contrastPeriod = " ";
            PeriodMatchingType periodMatchType = taskLink.getConfiguration();
            DefaultPeriodAdapter periodAdapter = new DefaultPeriodAdapter();
            PeriodWrapper periodWrapper = new PeriodWrapper(period);
            if (periodMatchType == PeriodMatchingType.PERIOD_TYPE_CURRENT) {
                contrastPeriod = period;
            } else if (periodMatchType == PeriodMatchingType.PERIOD_TYPE_NEXT) {
                periodAdapter.nextPeriod(periodWrapper);
                contrastPeriod = periodWrapper.toString();
            } else if (periodMatchType == PeriodMatchingType.PERIOD_TYPE_OFFSET) {
                periodAdapter.modify(periodWrapper, PeriodModifier.parse((String)taskLink.getPeriodOffset()));
                contrastPeriod = periodWrapper.toString();
            } else if (periodMatchType == PeriodMatchingType.PERIOD_TYPE_PREVIOUS) {
                periodAdapter.priorPeriod(periodWrapper);
                contrastPeriod = periodWrapper.toString();
            } else if (periodMatchType == PeriodMatchingType.PERIOD_TYPE_SPECIFIED) {
                contrastPeriod = taskLink.getSpecified();
            }
            relationObj.setPeriod(contrastPeriod);
            if (list.contains(relationObj)) continue;
            list.add(relationObj);
        }
        List taskToFromSchemeList = list.stream().filter(r -> r.getTaskKey().equals(taskKey)).collect(Collectors.toList());
        ArrayList selectStructureList = new ArrayList();
        for (RelationTaskAndFormScheme r2 : taskToFromSchemeList) {
            HashMap<String, SelectStructure> scheme = new HashMap<String, SelectStructure>();
            SelectStructure selectStructure = new SelectStructure();
            selectStructure.setKey(r2.getFormSchemeKey());
            selectStructure.setTitle(r2.getFormSchemenTitle());
            selectStructure.setToPeriod(r2.getPeriod());
            if (!scheme.containsKey(selectStructure.getKey())) {
                scheme.put("scheme", selectStructure);
            }
            selectStructureList.add(scheme);
        }
        HashMap resultmap = new HashMap();
        resultmap.put("formSchemes", selectStructureList);
        return ApiJSONUtil.objectToJsonStr(resultmap);
    }

    @Override
    public String eneityDataCheckResult(String asyncTaskID) {
        Object object = this.asyncTaskManager.queryDetail(asyncTaskID);
        if (null != object) {
            return (String)object;
        }
        return null;
    }

    @Override
    public String getMasterEntityKey(String formSchemeKey) {
        return this.entityQueryHelper.getDwEntityView(formSchemeKey).getEntityId();
    }

    @Override
    public int getCurrentUnitCount(String formSchemeKey, String period) {
        try {
            EntityViewDefine entityView = this.entityQueryHelper.getDwEntityView(formSchemeKey);
            IEntityTable currentTable = this.entityQueryHelper.buildEntityTable(entityView, period, formSchemeKey, true);
            List currentAllEntity = currentTable.getAllRows();
            return currentAllEntity.size();
        }
        catch (Exception e) {
            return 0;
        }
    }

    @Override
    public List<PeriodSchemeInfo> querySchemePeriodMapByTask(String taskKey) throws Exception {
        ArrayList<PeriodSchemeInfo> periodSchemeInfos = new ArrayList<PeriodSchemeInfo>();
        HashMap schemeDimensionMap = new HashMap();
        List schemeList = this.dataEntryParamService.runtimeFormSchemeList(taskKey);
        FuncExecuteConfig templateConfig = this.templateConfigService.getFuncExecuteConfigByCode("dataentry_defaultFuncode");
        for (int i = 0; i < schemeList.size(); ++i) {
            FormSchemeData scheme = (FormSchemeData)schemeList.get(i);
            List entitys = scheme.getEntitys();
            HashMap<String, DimensionValue> dimensions = new HashMap<String, DimensionValue>();
            for (EntityViewData entity : entitys) {
                DimensionValue value;
                if (this.periodEntityAdapter.isPeriodEntity(entity.getKey())) {
                    value = new DimensionValue();
                    value.setName(entity.getDimensionName());
                    if (8 == scheme.getPeriodType()) {
                        value.setType(8);
                        EntityQueryByViewInfo entityQueryInfo = new EntityQueryByViewInfo();
                        JtableContext jtableContext = new JtableContext();
                        entityQueryInfo.setContext(jtableContext);
                        jtableContext.setFormSchemeKey(scheme.getKey());
                        jtableContext.setTaskKey(taskKey);
                        entityQueryInfo.setEntityViewKey(entity.getKey());
                        EntityReturnInfo queryEntityData = this.jtableEntityService.queryCustomPeriodData(entityQueryInfo);
                        ArrayList<CustomPeriodData> customPeriods = new ArrayList<CustomPeriodData>();
                        String currValue = "";
                        for (EntityData entityData : queryEntityData.getEntitys()) {
                            CustomPeriodData customPeriod = new CustomPeriodData();
                            customPeriod.setCode(entityData.getCode());
                            customPeriod.setTitle(entityData.getTitle());
                            customPeriods.add(customPeriod);
                            if (!StringUtils.isEmpty((String)currValue)) continue;
                            currValue = entityData.getCode();
                        }
                        if (customPeriods.size() > 0) {
                            if (StringUtils.isNotEmpty((String)currValue)) {
                                value.setValue(currValue);
                            } else {
                                value.setValue(((CustomPeriodData)customPeriods.get(customPeriods.size() - 1)).getCode());
                            }
                            String period = templateConfig.getConfig().getDataEntryViewConfig().getPeriod();
                            if (StringUtils.isNotEmpty((String)period) && customPeriods.stream().filter(item -> item.getCode().equals(period)).count() > 0L) {
                                value.setValue(period);
                            }
                        } else {
                            value.setValue("1900N0001");
                        }
                    } else {
                        value.setType(scheme.getPeriodType());
                        PeriodWrapper currentPeriod = DataEntryUtil.getCurrPeriod((int)scheme.getPeriodType(), (int)scheme.getPeriodOffset(), (String)scheme.getFromPeriod(), (String)scheme.getToPeriod());
                        String period = templateConfig.getConfig().getDataEntryViewConfig().getPeriod();
                        if (StringUtils.isNotEmpty((String)period)) {
                            try {
                                int periodOffset = Integer.parseInt(period);
                                currentPeriod.modifyPeriod(periodOffset);
                                value.setValue(currentPeriod.toString());
                                PeriodWrapper fromPeriod = new PeriodWrapper(scheme.getFromPeriod());
                                PeriodWrapper toPeriod = new PeriodWrapper(scheme.getToPeriod());
                                if (periodOffset > 0) {
                                    if (currentPeriod.compareTo((Object)toPeriod) > 0) {
                                        value.setValue(scheme.getToPeriod());
                                    }
                                } else if (periodOffset < 0 && currentPeriod.compareTo((Object)fromPeriod) < 0) {
                                    value.setValue(scheme.getFromPeriod());
                                }
                            }
                            catch (Exception e) {
                                value.setValue(period);
                            }
                        } else {
                            value.setValue(currentPeriod.toString());
                        }
                    }
                    dimensions.put(entity.getDimensionName(), value);
                    continue;
                }
                if (!entity.isMasterEntity()) continue;
                value = new DimensionValue();
                value.setName(entity.getDimensionName());
                dimensions.put(entity.getDimensionName(), value);
                value.setValue("");
            }
            schemeDimensionMap.put(scheme.getKey(), dimensions);
        }
        List schemePeriodLinkDefineList = this.runTimeViewController.querySchemePeriodLinkByTask(taskKey);
        List schemePeriodLinkDefineListAfterFormat = schemePeriodLinkDefineList.stream().sorted((periodOne, periodTwo) -> PeriodUtils.comparePeriod((String)periodOne.getPeriodKey(), (String)periodTwo.getPeriodKey())).collect(Collectors.toList());
        for (SchemePeriodLinkDefine schemePeriodLinkDefineInfo : schemePeriodLinkDefineListAfterFormat) {
            if (!StringUtils.isNotEmpty((String)schemePeriodLinkDefineInfo.getPeriodKey()) || !StringUtils.isNotEmpty((String)schemePeriodLinkDefineInfo.getSchemeKey())) continue;
            PeriodSchemeInfo periodSchemeInfo = new PeriodSchemeInfo();
            periodSchemeInfo.setSchemeKey(schemePeriodLinkDefineInfo.getSchemeKey());
            periodSchemeInfo.setDimensions((Map)schemeDimensionMap.get(schemePeriodLinkDefineInfo.getSchemeKey()));
            periodSchemeInfo.setPeriodKey(schemePeriodLinkDefineInfo.getPeriodKey());
            periodSchemeInfos.add(periodSchemeInfo);
        }
        return periodSchemeInfos;
    }

    @Override
    public AssTaskFormSchemeInfo querySchemePeriodMapByTaskAndFormSchemePeriod(String taskKey, String formSchemeKey, String period) throws Exception {
        AssTaskFormSchemeInfo assTaskFormSchemeInfo = new AssTaskFormSchemeInfo();
        ArrayList<SelectStructure> assTasks = new ArrayList<SelectStructure>();
        ArrayList<AssTaskToFormSchemes> assTasksToForms = new ArrayList<AssTaskToFormSchemes>();
        HashMap<String, List> assFormSchemes = new HashMap<String, List>();
        Object basicCheckItems = this.taskExtConfigController.getTaskExtConfigDefineBySchemakeyCache(taskKey, formSchemeKey, "taskextension-entitycheck");
        EntityCheckConfigData entityCheckConfigData = (EntityCheckConfigData)basicCheckItems;
        if (entityCheckConfigData.getEntityCheckEnable()) {
            assTaskFormSchemeInfo.setAssTaskCount(entityCheckConfigData.getConfigInfos().size());
            for (int i = 0; i < entityCheckConfigData.getConfigInfos().size(); ++i) {
                ConfigInfo configInfo = entityCheckConfigData.getConfigInfos().get(i);
                SelectStructure taskSelect = new SelectStructure();
                taskSelect.setKey(configInfo.getAssTaskKey());
                TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(configInfo.getAssTaskKey());
                if (taskDefine == null) continue;
                taskSelect.setTitle(taskDefine.getTitle());
                if (!assTasks.contains(taskSelect)) {
                    assTasks.add(taskSelect);
                }
                SelectStructure formSchemeSelect = new SelectStructure();
                formSchemeSelect.setKey(configInfo.getAssFormSchemeKey());
                FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(configInfo.getAssFormSchemeKey());
                formSchemeSelect.setTitle(formSchemeDefine.getTitle());
                IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(formSchemeDefine.getDateTime());
                String assPeriod = this.getAssociatedPeriod(period, configInfo.getAssociation().getConfiguration(), configInfo.getAssociation().getSpecified(), configInfo.getAssociation().getPeriodOffset());
                SchemePeriodLinkDefine schemePeriodLinkDefine = this.runTimeViewController.querySchemePeriodLinkByPeriodAndTask(assPeriod, taskDefine.getKey());
                List schemePeriodLinkDefines = this.runTimeViewController.querySchemePeriodLinkByTask(taskDefine.getKey());
                List list = schemePeriodLinkDefines.stream().map(SchemePeriodLinkDefine::getPeriodKey).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
                if (schemePeriodLinkDefine == null && !list.isEmpty()) {
                    String end = (String)list.get(0);
                    String begin = (String)list.get(list.size() - 1);
                    if (begin != null && periodProvider.comparePeriod(begin, assPeriod) >= 0) {
                        assPeriod = begin;
                    } else if (end != null && periodProvider.comparePeriod(assPeriod, end) >= 0) {
                        assPeriod = end;
                    }
                }
                formSchemeSelect.setToPeriod(assPeriod);
                if (assFormSchemes.containsKey(configInfo.getAssTaskKey())) {
                    ((List)assFormSchemes.get(configInfo.getAssTaskKey())).add(formSchemeSelect);
                    continue;
                }
                ArrayList<SelectStructure> formSchemes = new ArrayList<SelectStructure>();
                formSchemes.add(formSchemeSelect);
                assFormSchemes.put(configInfo.getAssTaskKey(), formSchemes);
            }
            assTaskFormSchemeInfo.setAssTasks(assTasks);
            assFormSchemes.forEach((key, value) -> {
                AssTaskToFormSchemes assTaskToFormSchemes = new AssTaskToFormSchemes();
                assTaskToFormSchemes.setAssTaskKey((String)key);
                assTaskToFormSchemes.setAssFormSchemes((List<SelectStructure>)value);
                TaskData taskData = this.dataEntryParamService.getRuntimeTaskByKey(key);
                IPeriodProvider periodProvider = null;
                for (EntityViewData view : taskData.getEntitys()) {
                    if (!this.periodEntityAdapter.isPeriodEntity(view.getKey())) continue;
                    periodProvider = this.periodEntityAdapter.getPeriodProvider(view.getKey());
                }
                ArrayList<CustomPeriodData> customPeriodDatas = new ArrayList<CustomPeriodData>();
                for (IPeriodRow periodRow : periodProvider.getPeriodItems()) {
                    CustomPeriodData data = new CustomPeriodData();
                    data.setCode(periodRow.getCode());
                    data.setTitle(periodRow.getTitle());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    data.setStartPeriod(sdf.format(periodRow.getStartDate()));
                    data.setEndPeriod(sdf.format(periodRow.getEndDate()));
                    customPeriodDatas.add(data);
                }
                assTaskToFormSchemes.setCustomPeriodDatas(customPeriodDatas);
                assTasksToForms.add(assTaskToFormSchemes);
            });
            assTaskFormSchemeInfo.setAssTasksToForms(assTasksToForms);
        }
        return assTaskFormSchemeInfo;
    }

    public String getAssociatedPeriod(String period, int configuration, String specified, String periodOffset) {
        String associatedPeriod = "";
        PeriodMatchingType type = PeriodMatchingType.forValue((int)configuration);
        DefaultPeriodAdapter periodAdapter = new DefaultPeriodAdapter();
        PeriodWrapper periodWrapper = new PeriodWrapper(period);
        switch (type) {
            case PERIOD_TYPE_CURRENT: {
                associatedPeriod = period;
                break;
            }
            case PERIOD_TYPE_PREVIOUS: {
                periodAdapter.priorPeriod(periodWrapper);
                associatedPeriod = periodWrapper.toString();
                break;
            }
            case PERIOD_TYPE_NEXT: {
                periodAdapter.nextPeriod(periodWrapper);
                associatedPeriod = periodWrapper.toString();
                break;
            }
            case PERIOD_TYPE_SPECIFIED: {
                associatedPeriod = specified;
                break;
            }
            case PERIOD_TYPE_OFFSET: {
                periodAdapter.modify(periodWrapper, PeriodModifier.parse((String)periodOffset));
                associatedPeriod = periodWrapper.toString();
                break;
            }
            case PERIOD_TYPE_ALL: {
                break;
            }
        }
        return associatedPeriod;
    }

    @Override
    public List<EnumStructure> getJSYYSlectData(String taskKey, String formSchemeKey, String associatedTaskKey, String associatedFormSchemeKey) {
        ArrayList<EnumStructure> enumStructureList = null;
        try {
            CheckConfigurationContent configurationContent = this.getCheckConfigurationContent(taskKey, formSchemeKey, associatedTaskKey, associatedFormSchemeKey);
            if (null != configurationContent.GetJSYYEntityView()) {
                enumStructureList = new ArrayList<EnumStructure>();
                EnumStructure enumStructure = new EnumStructure();
                enumStructure.setKey(null);
                enumStructure.setValue("\u65e0");
                enumStructureList.add(enumStructure);
                EntityViewDefine viewDefine = configurationContent.GetJSYYEntityView();
                IEntityTable jsyyentity = this.entityQueryHelper.buildEntityTable(viewDefine, null, formSchemeKey, false);
                List jsyyRows = jsyyentity.getAllRows();
                for (IEntityRow dataRow : jsyyRows) {
                    EnumStructure jsyyEnumStructure = new EnumStructure();
                    jsyyEnumStructure.setKey(dataRow.getEntityKeyData());
                    jsyyEnumStructure.setValue(dataRow.getTitle());
                    enumStructureList.add(jsyyEnumStructure);
                }
                return enumStructureList;
            }
        }
        catch (Exception ex) {
            return enumStructureList;
        }
        return enumStructureList;
    }
}

