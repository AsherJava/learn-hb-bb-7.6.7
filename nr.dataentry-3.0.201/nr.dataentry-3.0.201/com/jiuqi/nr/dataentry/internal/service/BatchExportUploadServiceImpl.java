/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nr.bpm.common.UploadRecordDetail
 *  com.jiuqi.nr.bpm.common.UploadStatusDetail
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionState
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean
 *  com.jiuqi.nr.bpm.de.dataflow.bean.DataEntryParam
 *  com.jiuqi.nr.bpm.de.dataflow.constont.UploadStateEnum
 *  com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService
 *  com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow
 *  com.jiuqi.nr.bpm.upload.UploadState
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.param.IAccessResult
 *  com.jiuqi.nr.data.access.param.IBatchAccessResult
 *  com.jiuqi.nr.data.access.service.IDataAccessService
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.entity.model.IEntityDefine
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
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.dataengine.model.OrderByItem
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.bpm.common.UploadRecordDetail;
import com.jiuqi.nr.bpm.common.UploadStatusDetail;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionState;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.DataEntryParam;
import com.jiuqi.nr.bpm.de.dataflow.constont.UploadStateEnum;
import com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.data.access.param.IBatchAccessResult;
import com.jiuqi.nr.data.access.service.IDataAccessService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.dataentry.internal.service.FormGroupProvider;
import com.jiuqi.nr.dataentry.paramInfo.ExportExcelState;
import com.jiuqi.nr.dataentry.service.BatchExportUploadService;
import com.jiuqi.nr.dataentry.service.IOverViewBaseService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.entity.model.IEntityDefine;
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
import com.jiuqi.nvwa.dataengine.INvwaDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.dataengine.model.OrderByItem;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class BatchExportUploadServiceImpl
implements BatchExportUploadService {
    private static final Logger logger = LoggerFactory.getLogger(BatchExportUploadServiceImpl.class);
    private static final String DW_FIELD = "MDCODE";
    private static final String PERIOD_FIELD = "PERIOD";
    @Resource
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Resource
    private IRunTimeViewController runtimeView;
    @Resource
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IJtableEntityService jtableEntityService;
    @Autowired
    private IDataentryFlowService dataFlowService;
    @Autowired
    private UserService<User> userService;
    @Autowired
    private FormGroupProvider formGroupProvider;
    @Autowired
    private IWorkflow workflow;
    @Autowired
    private IOverViewBaseService overViewBaseService;
    @Autowired
    private INvwaDataAccessProvider iNvwaDataAccessProvider;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired
    private IDataAccessServiceProvider dataAccessServiceProvider;
    private BiFunction<String, Map<String, User>, String> findUserNameById = (processor, userMap) -> {
        if (StringUtils.isEmpty((String)processor)) {
            return processor;
        }
        if (processor.equals("00000000-0000-0000-0000-000000000000")) {
            processor = "\u7cfb\u7edf\u7ba1\u7406\u5458";
        } else {
            User user = (User)userMap.get(processor);
            if (user != null) {
                processor = user.getNickname();
            }
        }
        return processor;
    };

    @Override
    public UploadStatusDetail getUploadDeatils(ExportExcelState exportExcelState) throws Exception {
        TableModelDefine uploadStateTable = null;
        TableModelDefine uploadHistoryTable = null;
        List uploadHistoryFields = new ArrayList();
        List uploadStateFields = null;
        FormSchemeDefine formScheme = null;
        UploadStatusDetail uploadStatusDetail = new UploadStatusDetail();
        WorkFlowType wordFlowType = null;
        try {
            formScheme = this.runtimeView.getFormScheme(exportExcelState.getFormSchemeKey());
            wordFlowType = formScheme.getFlowsSetting().getWordFlowType();
            uploadStateTable = this.dataModelService.getTableModelDefineByName("SYS_UP_ST_" + formScheme.getFormSchemeCode());
            uploadHistoryTable = this.dataModelService.getTableModelDefineByName("SYS_UP_HI_" + formScheme.getFormSchemeCode());
            uploadStateFields = this.dataModelService.getColumnModelDefinesByTable(uploadStateTable.getID());
            if (uploadHistoryTable != null) {
                uploadHistoryFields = this.dataModelService.getColumnModelDefinesByTable(uploadHistoryTable.getID());
            }
        }
        catch (Exception e2) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e2.getMessage(), e2);
            return uploadStatusDetail;
        }
        QueryEnvironment queryEnvironment = new QueryEnvironment();
        queryEnvironment.setFormSchemeKey(formScheme.getKey());
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        EntityViewData targetEntityInfo = this.jtableParamService.getDwEntity(formScheme.getKey());
        EntityViewData periodEntityInfo = this.jtableParamService.getDataTimeEntity(formScheme.getKey());
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(exportExcelState.getDimensionSet());
        IDataAssist dataAssist = this.dataAccessProvider.newDataAssist(executorContext);
        String entityId = dimensionValueSet.getValue(targetEntityInfo.getDimensionName()).toString();
        String period = dimensionValueSet.getValue(periodEntityInfo.getDimensionName()).toString();
        String entityName = targetEntityInfo.getDimensionName();
        ColumnModelDefine prevevent = null;
        int preveventIndex = -1;
        int entityFieldIndex = -1;
        ArrayList<String> filteredValues = new ArrayList<String>();
        String formKey = exportExcelState.getFormKey();
        if (formKey != null && wordFlowType != null && (wordFlowType.equals((Object)WorkFlowType.GROUP) || wordFlowType.equals((Object)WorkFlowType.FORM))) {
            filteredValues.add(formKey);
        }
        ColumnModelDefine entityField = null;
        String bizKeyFieldsStr = uploadStateTable.getBizKeys();
        NvwaQueryModel queryModel = new NvwaQueryModel();
        DataAccessContext dataAccessContext = new DataAccessContext(this.dataModelService);
        JtableContext context = new JtableContext();
        context.setDimensionSet(exportExcelState.getDimensionSet());
        context.setFormKey(exportExcelState.getFormKey());
        context.setFormSchemeKey(exportExcelState.getFormSchemeKey());
        context.setTaskKey(exportExcelState.getTaskKey());
        int summaryScope = exportExcelState.getSummaryScope();
        Object units = new HashMap<String, String>();
        HashMap<String, String> unitsState = new HashMap<String, String>();
        List<String> entityIds = new ArrayList<String>();
        HashSet<String> fields = new HashSet<String>();
        fields.add("ALL");
        EntityQueryByViewInfo queryEntityData = new EntityQueryByViewInfo();
        queryEntityData.setEntityViewKey(targetEntityInfo.getKey());
        queryEntityData.setParentKey(entityId);
        queryEntityData.setContext(context);
        queryEntityData.setReadAuth(false);
        queryEntityData.setAllChildren(!exportExcelState.isOnlyDirectChild());
        queryEntityData.setSorted(true);
        EntityReturnInfo entityReturnInfo = this.jtableEntityService.queryEntityData(queryEntityData);
        boolean flowsType = this.workflow.isDefaultWorkflow(formScheme.getKey());
        ArrayList<EntityData> allEntitys = entityReturnInfo.getEntitys();
        ArrayList<EntityData> result = new ArrayList<EntityData>();
        result.addAll(allEntitys);
        this.allEntityData((List<EntityData>)allEntitys, result, !exportExcelState.isOnlyDirectChild());
        allEntitys = result;
        List childIds = allEntitys.stream().map(e -> e.getId()).collect(Collectors.toList());
        String formKeyStr = "";
        if (wordFlowType.equals((Object)WorkFlowType.GROUP)) {
            List allForms = this.runtimeView.getAllFormsInGroup(formKey);
            List formIds = allForms.stream().map(e -> e.getKey()).collect(Collectors.toList());
            formKeyStr = String.join((CharSequence)";", formIds);
        } else {
            formKeyStr = formKey;
        }
        JtableContext jcontext = new JtableContext();
        jcontext.setFormSchemeKey(exportExcelState.getFormSchemeKey());
        HashMap<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
        Map<String, DimensionValue> map = exportExcelState.getDimensionSet();
        for (Map.Entry<String, DimensionValue> item : map.entrySet()) {
            dimensionSet.put(item.getKey(), item.getValue());
        }
        DimensionValue dv = new DimensionValue();
        dv.setName(entityName);
        dv.setValue(String.join((CharSequence)";", childIds));
        dimensionSet.put(entityName, dv);
        dimensionSet.put("DATATIME", exportExcelState.getDimensionSet().get("DATATIME"));
        jcontext.setDimensionSet(dimensionSet);
        jcontext.setTaskKey(exportExcelState.getTaskKey());
        List<String> FormKeyList = new ArrayList();
        if (formKeyStr == null) {
            FormKeyList = this.runtimeView.queryAllFormKeysByFormScheme(exportExcelState.getFormSchemeKey());
        } else {
            for (String key : formKeyStr.split(";")) {
                FormKeyList.add(key);
            }
        }
        IDataAccessService dataAccessService = this.dataAccessServiceProvider.getDataAccessService(exportExcelState.getTaskKey(), exportExcelState.getFormSchemeKey());
        DimensionCollection dimCollection = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.buildDimensionCollection(dimensionSet, (String)exportExcelState.getFormSchemeKey());
        IBatchAccessResult batchAccessResult = dataAccessService.getVisitAccess(dimCollection, FormKeyList);
        HashMap dimensionValueFormInfoMap = new HashMap();
        List dimCollectionList = dimCollection.getDimensionCombinations();
        for (DimensionCombination dimensionComin : dimCollectionList) {
            String[] dimValuesAry;
            Map currDimensionSet = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionComin.toDimensionValueSet());
            String dimValues = ((DimensionValue)currDimensionSet.get(entityName)).getValue();
            for (String entityKey : dimValuesAry = dimValues.split(";")) {
                ArrayList<String> accessForms = new ArrayList<String>();
                for (String key : FormKeyList) {
                    IAccessResult accessResult = batchAccessResult.getAccess(dimensionComin, key);
                    if (!accessResult.haveAccess()) continue;
                    accessForms.add(key);
                }
                if (dimensionValueFormInfoMap.containsKey(entityKey)) {
                    ((List)dimensionValueFormInfoMap.get(entityKey)).addAll(accessForms);
                    continue;
                }
                dimensionValueFormInfoMap.put(entityKey, accessForms);
            }
        }
        for (EntityData entityData : allEntitys) {
            List accessForms;
            if (wordFlowType.equals((Object)WorkFlowType.GROUP) && CollectionUtils.isEmpty(accessForms = (List)dimensionValueFormInfoMap.get(entityData.getId()))) continue;
            entityIds.add(entityData.getId());
            units.put(entityData.getId(), entityData.getTitle());
        }
        if (entityReturnInfo.getEntitys() == null || entityReturnInfo.getEntitys().isEmpty()) {
            EntityQueryByKeyInfo queryEntityDataByKey = new EntityQueryByKeyInfo();
            queryEntityDataByKey.setEntityViewKey(targetEntityInfo.getKey());
            queryEntityDataByKey.setEntityKey(entityId);
            queryEntityDataByKey.setContext(context);
            EntityByKeyReturnInfo entityByKeyReturnInfo = this.jtableEntityService.queryEntityDataByKey(queryEntityDataByKey);
            EntityData entity = entityByKeyReturnInfo.getEntity();
            if (entity != null) {
                entityIds.add(entity.getId());
                units.put(entity.getId(), entity.getTitle());
            }
        }
        if (wordFlowType.equals((Object)WorkFlowType.FORM)) {
            JtableContext jtableContext = new JtableContext();
            jtableContext.setFormSchemeKey(exportExcelState.getFormSchemeKey());
            jtableContext.setDimensionSet(exportExcelState.getDimensionSet());
            List<String> filterAuth = this.overViewBaseService.filterAuth(jtableContext, entityIds, formKey, targetEntityInfo, WorkFlowType.FORM);
            entityIds = filterAuth;
            HashMap units2 = new HashMap();
            for (String string : filterAuth) {
                units2.put(string, units.get(string));
            }
            units = units2;
        }
        dimensionValueSet.setValue(entityName, entityIds);
        int index = 0;
        int indexColumn = 0;
        for (ColumnModelDefine columnModelDefine : uploadStateFields) {
            if (bizKeyFieldsStr.contains(columnModelDefine.getID())) {
                OrderByItem orderByItem = new OrderByItem(columnModelDefine);
                orderByItem.setDesc(true);
                queryModel.getOrderByItems().add(index, orderByItem);
                ++index;
            }
            if (columnModelDefine.getCode().equals("PREVEVENT")) {
                prevevent = columnModelDefine;
                preveventIndex = indexColumn;
            }
            if (columnModelDefine.getCode().equals("FORMID") && wordFlowType != null && (wordFlowType.equals((Object)WorkFlowType.GROUP) || wordFlowType.equals((Object)WorkFlowType.FORM))) {
                queryModel.getColumnFilters().put(columnModelDefine, filteredValues);
            }
            if (columnModelDefine.getCode().equals(DW_FIELD)) {
                String referTableID;
                if (columnModelDefine.getCode().equals(DW_FIELD)) {
                    entityField = columnModelDefine;
                    entityFieldIndex = indexColumn;
                }
                if ((referTableID = columnModelDefine.getReferTableID()) == null) {
                    Object value = dimensionValueSet.getValue(columnModelDefine.getCode().equals(PERIOD_FIELD) ? "DATATIME" : columnModelDefine.getCode());
                    if (value != null) {
                        queryModel.getColumnFilters().put(columnModelDefine, value);
                    }
                } else {
                    TableModelDefine modelDefine = this.dataModelService.getTableModelDefineById(columnModelDefine.getReferTableID());
                    IEntityDefine entityByCode = this.iEntityMetaService.queryEntityByCode(modelDefine.getCode());
                    Object value = dimensionValueSet.getValue(entityByCode.getDimensionName());
                    if (value != null) {
                        queryModel.getColumnFilters().put(columnModelDefine, value);
                    }
                }
            }
            if (columnModelDefine.getCode().equals(PERIOD_FIELD)) {
                queryModel.getColumnFilters().put(columnModelDefine, dimensionValueSet.getValue("DATATIME"));
            }
            queryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
            ++indexColumn;
        }
        queryModel.setMainTableName(uploadStateTable.getName());
        INvwaDataAccess dataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
        MemoryDataSet dataSet = dataAccess.executeQuery(dataAccessContext);
        if (entityField == null) {
            logger.error("\u4e0a\u62a5\u72b6\u6001\u8868\u4e3b\u4f53\u6307\u6807\u83b7\u53d6\u5931\u8d25\uff0c\u65e0\u6cd5\u5bfc\u51fa");
            return uploadStatusDetail;
        }
        int totalCount = dataSet.size();
        for (int i = 0; i < totalCount; ++i) {
            DataRow item = dataSet.get(i);
            String value = item.getString(preveventIndex);
            String unitKey = item.getString(entityFieldIndex);
            this.countStatus(uploadStatusDetail, value, unitKey, unitsState);
        }
        uploadStatusDetail.setUnitCount(entityIds.size());
        if (StringUtils.isNotEmpty((String)exportExcelState.getFilter())) {
            ArrayList<String> filterEntity = new ArrayList<String>();
            DimensionValueSet dim = new DimensionValueSet();
            WorkFlowType queryStartType = this.dataFlowService.queryStartType(formScheme.getKey());
            for (String entity : entityIds) {
                DataEntryParam param = new DataEntryParam();
                dim.setValue(entityName, (Object)entity);
                dim.setValue("DATATIME", (Object)period);
                if (!filteredValues.isEmpty() && !wordFlowType.equals((Object)WorkFlowType.ENTITY)) {
                    dim.setValue("FORMID", filteredValues.stream().findFirst().get());
                    param.setFormKey(filteredValues.stream().findFirst().get().toString());
                    ArrayList<String> formKeys = new ArrayList<String>();
                    formKeys.add(filteredValues.stream().findFirst().get().toString());
                    if (wordFlowType.equals((Object)WorkFlowType.GROUP)) {
                        param.setGroupKeys(formKeys);
                        if (!formKeys.isEmpty() && formKeys.size() == 1) {
                            param.setGroupKey((String)formKeys.get(0));
                        }
                    } else {
                        param.setFormKeys(formKeys);
                        if (!formKeys.isEmpty() && formKeys.size() == 1) {
                            param.setFormKey((String)formKeys.get(0));
                        }
                    }
                }
                param.setDim(dim);
                param.setFormSchemeKey(formScheme.getKey());
                ActionState actionStates = this.dataFlowService.queryState(param);
                ActionStateBean actionState = null;
                switch (queryStartType) {
                    case FORM: {
                        actionState = actionStates.getFormState();
                        break;
                    }
                    case GROUP: {
                        actionState = actionStates.getGroupState();
                        break;
                    }
                    default: {
                        actionState = actionStates.getUnitState();
                    }
                }
                String filter = exportExcelState.getFilter();
                if (exportExcelState.isForceUpoload()) {
                    if (!actionState.isForceUpload()) continue;
                    if (!flowsType && actionState != null && filter.equals(UploadStateEnum.ORIGINAL_UPLOAD.getCode()) && actionState.getCode().equals(UploadStateEnum.SUBMITED.getCode())) {
                        filterEntity.add(entity);
                    }
                    if ((actionState != null || !UploadStateEnum.ORIGINAL_SUBMIT.getCode().equals(filter) && !UploadStateEnum.ORIGINAL_UPLOAD.getCode().equals(filter)) && (actionState == null || !actionState.getCode().equals(filter))) continue;
                    filterEntity.add(entity);
                    continue;
                }
                if (!flowsType && actionState != null && filter.equals(UploadStateEnum.ORIGINAL_UPLOAD.getCode()) && actionState.getCode().equals(UploadStateEnum.SUBMITED.getCode())) {
                    filterEntity.add(entity);
                }
                if ((actionState != null || !UploadStateEnum.ORIGINAL_SUBMIT.getCode().equals(filter) && !UploadStateEnum.ORIGINAL_UPLOAD.getCode().equals(filter)) && (actionState == null || !actionState.getCode().equals(filter))) continue;
                filterEntity.add(entity);
            }
            entityIds = filterEntity;
            dimensionValueSet.setValue(entityName, entityIds);
        }
        String bizKeyHisFieldsStr = uploadHistoryTable.getBizKeys();
        NvwaQueryModel queryModelHis = new NvwaQueryModel();
        DataAccessContext dataAccessContextHis = new DataAccessContext(this.dataModelService);
        ColumnModelDefine time_1 = null;
        ColumnModelDefine curevent = null;
        ColumnModelDefine operator = null;
        entityField = null;
        int time_1Index = -1;
        int cureventIndex = -1;
        int operatorIndex = -1;
        int historyIndex = 0;
        for (ColumnModelDefine columnModelDefine : uploadHistoryFields) {
            if ("TIME_".equals(columnModelDefine.getCode())) {
                OrderByItem item = new OrderByItem(columnModelDefine, false);
                queryModelHis.getOrderByItems().add(item);
            }
            if (columnModelDefine.getCode().equals("TIME_")) {
                time_1 = columnModelDefine;
                time_1Index = historyIndex;
            }
            if (columnModelDefine.getCode().equals("CUREVENT")) {
                curevent = columnModelDefine;
                cureventIndex = historyIndex;
            }
            if (columnModelDefine.getCode().equals("OPERATOR")) {
                operator = columnModelDefine;
                operatorIndex = historyIndex;
            }
            if (columnModelDefine.getCode().equals("FORMID") && wordFlowType != null && (wordFlowType.equals((Object)WorkFlowType.GROUP) || wordFlowType.equals((Object)WorkFlowType.FORM))) {
                queryModelHis.getColumnFilters().put(columnModelDefine, filteredValues);
            }
            if (columnModelDefine.getCode().equals(DW_FIELD)) {
                String referTableID;
                if (columnModelDefine.getCode().equals(DW_FIELD)) {
                    entityField = columnModelDefine;
                    entityFieldIndex = historyIndex;
                }
                if ((referTableID = columnModelDefine.getReferTableID()) == null) {
                    Object value = dimensionValueSet.getValue(columnModelDefine.getCode().equals(PERIOD_FIELD) ? "DATATIME" : columnModelDefine.getCode());
                    if (value != null) {
                        queryModelHis.getColumnFilters().put(columnModelDefine, value);
                    }
                } else {
                    TableModelDefine modelDefine = this.dataModelService.getTableModelDefineById(columnModelDefine.getReferTableID());
                    IEntityDefine entityByCode = this.iEntityMetaService.queryEntityByCode(modelDefine.getCode());
                    Object value = dimensionValueSet.getValue(entityByCode.getDimensionName());
                    if (value != null) {
                        queryModelHis.getColumnFilters().put(columnModelDefine, value);
                    }
                }
            }
            if (columnModelDefine.getCode().equals(PERIOD_FIELD)) {
                queryModelHis.getColumnFilters().put(columnModelDefine, dimensionValueSet.getValue("DATATIME"));
            }
            queryModelHis.getColumns().add(new NvwaQueryColumn(columnModelDefine));
            ++historyIndex;
        }
        queryModelHis.setMainTableName(uploadHistoryTable.getName());
        if (entityField == null) {
            logger.error("\u4e0a\u62a5\u5386\u53f2\u8868\u4e3b\u4f53\u6307\u6807\u83b7\u53d6\u5931\u8d25\uff0c\u65e0\u6cd5\u5bfc\u51fa");
            return uploadStatusDetail;
        }
        queryModelHis.setMainTableName(uploadHistoryTable.getName());
        INvwaDataAccess dataAccessHis = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(queryModelHis);
        MemoryDataSet dataSetHis = dataAccessHis.executeQuery(dataAccessContextHis);
        ArrayList<UploadRecordDetail> uploadRecordDetails = new ArrayList<UploadRecordDetail>();
        HashSet<String> userIds = new HashSet<String>();
        uploadStatusDetail.setUploadRecordDetail(uploadRecordDetails);
        UploadRecordDetail record = new UploadRecordDetail();
        int hisCount = dataSetHis.size();
        String currUnit = "";
        HashMap<String, UploadRecordDetail> recordUnit = new HashMap<String, UploadRecordDetail>();
        for (int indexHis = 0; indexHis < entityIds.size(); ++indexHis) {
            record = new UploadRecordDetail();
            recordUnit.put(entityIds.get(indexHis), record);
            uploadRecordDetails.add(record);
            this.setRecords((Map<String, String>)units, unitsState, record, "start", "", entityIds.get(indexHis), "");
        }
        for (int i = 0; i < hisCount; ++i) {
            DataRow item = dataSetHis.get(i);
            String value = item.getString(cureventIndex);
            String processor = item.getString(operatorIndex);
            String unitKey = item.getString(entityFieldIndex);
            Calendar date = item.getDate(time_1Index);
            SimpleDateFormat dfDT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = date != null ? dfDT.format(date.getTime()) : null;
            userIds.add(processor);
            if (unitKey.equals(currUnit)) {
                this.setRecords((Map<String, String>)units, unitsState, record, value, processor, unitKey, time);
                continue;
            }
            currUnit = unitKey;
            int indexOf = entityIds.indexOf(unitKey);
            if (indexOf == -1) continue;
            record = (UploadRecordDetail)uploadRecordDetails.get(indexOf);
            if (recordUnit.containsKey(unitKey)) {
                ((UploadRecordDetail)recordUnit.get(unitKey)).setUnitKey(null);
                record = (UploadRecordDetail)uploadRecordDetails.get(uploadRecordDetails.indexOf(recordUnit.get(unitKey)));
            }
            this.setRecords((Map<String, String>)units, unitsState, record, value, processor, unitKey, time);
        }
        if (!flowsType) {
            uploadStatusDetail.setOriginalNum(uploadStatusDetail.getSubmitedNum() + uploadStatusDetail.getReturnedNum());
            uploadStatusDetail.setSubmitedNum(0);
            uploadStatusDetail.setReturnedNum(0);
            List uploadRecordDetail = uploadStatusDetail.getUploadRecordDetail();
            for (UploadRecordDetail item : uploadRecordDetail) {
                if (!item.getState().equals("\u5df2\u9000\u5ba1") && !item.getState().equals("\u5df2\u9001\u5ba1") && !item.getState().equals("\u672a\u9001\u5ba1")) continue;
                item.setState("\u672a\u4e0a\u62a5");
            }
        }
        uploadStatusDetail.setOriginalNum(uploadStatusDetail.getUnitCount() - (uploadStatusDetail.getConfirmedNum() + uploadStatusDetail.getRejectedNum() + uploadStatusDetail.getReturnedNum() + uploadStatusDetail.getSubmitedNum() + uploadStatusDetail.getUploadedNum()));
        this.setRecordUser(userIds, uploadRecordDetails);
        return uploadStatusDetail;
    }

    private Map<String, User> getAllUser(Set<String> userIds) {
        userIds = userIds.stream().filter(e -> StringUtils.isNotEmpty((String)e)).collect(Collectors.toSet());
        List users = this.userService.get((String[])userIds.stream().toArray(String[]::new));
        Map<String, User> userMap = users.stream().collect(Collectors.toMap(User::getId, Function.identity(), (o, n) -> n));
        return userMap;
    }

    private void setRecordUser(Set<String> userIds, List<UploadRecordDetail> uploadRecordDetails) {
        Map<String, User> userMap = this.getAllUser(userIds);
        uploadRecordDetails.stream().forEach(e -> {
            e.setInitialUpdateProcessor(this.findUserNameById.apply(e.getInitialUpdateProcessor(), userMap));
            e.setInitialRejectProcessor(this.findUserNameById.apply(e.getInitialRejectProcessor(), userMap));
            e.setLastUpdateProcessor(this.findUserNameById.apply(e.getLastUpdateProcessor(), userMap));
            e.setLastRejectProcessor(this.findUserNameById.apply(e.getLastRejectProcessor(), userMap));
        });
    }

    private void setRecords(Map<String, String> units, Map<String, String> unitsState, UploadRecordDetail record, String value, String processor, String unitKey, String time) {
        UploadState state;
        if (record.getUnitKey() == null) {
            record.setUnitKey(unitKey);
            record.setUnit(units.get(unitKey));
            record.setState(unitsState.get(unitKey) == null ? "\u672a\u4e0a\u62a5" : unitsState.get(unitKey));
        }
        if ((state = this.getState(value)).equals((Object)UploadState.UPLOADED)) {
            if (record.getInitialUpdateTime() == null) {
                record.setInitialUpdateTime(time);
            }
            if (record.getInitialUpdateProcessor() == null) {
                record.setInitialUpdateProcessor(processor);
            }
            record.setLastUpdateTime(time);
            record.setLastUpdateProcessor(processor);
            record.incrementUploadCount();
        }
        if (state.equals((Object)UploadState.REJECTED)) {
            if (record.getInitialRejectTime() == null) {
                record.setInitialRejectTime(time);
            }
            if (record.getInitialRejectProcessor() == null) {
                record.setInitialRejectProcessor(processor);
            }
            record.setLastRejectTime(time);
            record.setLastRejectProcessor(processor);
            record.incrementRejectCount();
        }
    }

    private List<String> getChildEntityIDs(EntityData entitydata, int summaryScope, Map<String, String> units) {
        ArrayList<String> entityIds = new ArrayList<String>();
        for (EntityData entityChildData : entitydata.getChildren()) {
            entityIds.add(entityChildData.getId());
            units.put(entityChildData.getId(), entityChildData.getTitle());
            if (summaryScope <= 1) continue;
            entityIds.addAll(this.getChildEntityIDs(entityChildData, summaryScope, units));
        }
        return entityIds;
    }

    private EntityData getEntityData(String entityId, EntityViewData entityViewData, JtableContext context) {
        EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
        entityQueryByKeyInfo.setEntityViewKey(entityViewData.getKey());
        entityQueryByKeyInfo.setEntityKey(entityId);
        entityQueryByKeyInfo.setContext(context);
        EntityByKeyReturnInfo entityDataByKey = this.jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo);
        EntityData entitySelf = entityDataByKey.getEntity();
        EntityQueryByViewInfo entityQueryInfo = new EntityQueryByViewInfo();
        entityQueryInfo.setEntityViewKey(entityViewData.getKey());
        entityQueryInfo.setParentKey(entityId);
        entityQueryInfo.setAllChildren(true);
        entityQueryInfo.setContext(context);
        EntityReturnInfo entityReturnInfo = this.jtableEntityService.queryEntityData(entityQueryInfo);
        entitySelf.setChildren(entityReturnInfo.getEntitys());
        return entitySelf;
    }

    private void countStatus(UploadStatusDetail uploadStatusDetail, String state, String unitKey, Map<String, String> unitsState) {
        UploadState uploadState = this.getState(state);
        switch (uploadState) {
            case SUBMITED: {
                uploadStatusDetail.incrementSubmitedNum();
                unitsState.put(unitKey, "\u5df2\u9001\u5ba1");
                break;
            }
            case RETURNED: {
                uploadStatusDetail.incrementReturnedNum();
                unitsState.put(unitKey, "\u5df2\u9000\u5ba1");
                break;
            }
            case UPLOADED: {
                uploadStatusDetail.incrementUploadedNum();
                unitsState.put(unitKey, "\u5df2\u4e0a\u62a5");
                break;
            }
            case CONFIRMED: {
                uploadStatusDetail.incrementConfirmedNum();
                unitsState.put(unitKey, "\u5df2\u786e\u8ba4");
                break;
            }
            case REJECTED: {
                uploadStatusDetail.incrementRejectedNum();
                unitsState.put(unitKey, "\u5df2\u9000\u56de");
                break;
            }
            case ORIGINAL_UPLOAD: {
                uploadStatusDetail.incrementOriginalNum();
                unitsState.put(unitKey, "\u672a\u4e0a\u62a5");
                break;
            }
            default: {
                uploadStatusDetail.incrementOriginalNum();
                unitsState.put(unitKey, "\u672a\u4e0a\u62a5");
            }
        }
    }

    private UploadState getState(String state) {
        switch (state) {
            case "act_submit": 
            case "cus_submit": {
                return UploadState.SUBMITED;
            }
            case "act_return": 
            case "cus_return": {
                return UploadState.RETURNED;
            }
            case "act_upload": 
            case "cus_upload": 
            case "act_cancel_confirm": {
                return UploadState.UPLOADED;
            }
            case "act_confirm": 
            case "cus_confirm": {
                return UploadState.CONFIRMED;
            }
            case "act_reject": 
            case "cus_reject": {
                return UploadState.REJECTED;
            }
            case "start": {
                return UploadState.ORIGINAL_UPLOAD;
            }
        }
        return UploadState.ORIGINAL;
    }

    private void allEntityData(List<EntityData> list, List<EntityData> result, boolean isAllChildren) {
        if (!isAllChildren) {
            for (EntityData entityData : list) {
                List children = entityData.getChildren();
                if (children.isEmpty()) continue;
                int index = result.indexOf(entityData);
                result.addAll(index + 1, children);
            }
        } else {
            for (EntityData entityData : list) {
                List children = entityData.getChildren();
                if (children.isEmpty()) continue;
                this.setAllChildren(entityData, result, children);
            }
        }
    }

    private void setAllChildren(EntityData parenData, List<EntityData> result, List<EntityData> childrenList) {
        int index = result.indexOf(parenData);
        result.addAll(index + 1, childrenList);
        for (EntityData entityData : childrenList) {
            if (entityData.getChildren().isEmpty()) continue;
            this.setAllChildren(entityData, result, entityData.getChildren());
        }
    }
}

