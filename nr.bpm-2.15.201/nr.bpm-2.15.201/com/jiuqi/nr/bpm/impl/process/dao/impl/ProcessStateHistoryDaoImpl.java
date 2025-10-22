/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.nr.common.util.DataEngineAdapter
 *  com.jiuqi.nr.common.util.DimensionChanger
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.data.engine.condition.IConditionCache
 *  com.jiuqi.nr.data.engine.condition.IFormConditionService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataSet
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.util.StringUtils
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.bpm.impl.process.dao.impl;

import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.de.dataflow.util.BusinessGenerator;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.bpm.exception.BpmException;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.impl.process.dao.BatchCompleteParam;
import com.jiuqi.nr.bpm.impl.process.dao.ProcessStateHistoryDao;
import com.jiuqi.nr.bpm.impl.process.dao.UnitState;
import com.jiuqi.nr.bpm.impl.process.dao.UploadProcessInstanceDto;
import com.jiuqi.nr.bpm.impl.upload.dao.TableConstant;
import com.jiuqi.nr.bpm.service.IBatchQueryUploadStateService;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.common.util.DataEngineAdapter;
import com.jiuqi.nr.common.util.DimensionChanger;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.data.engine.condition.IConditionCache;
import com.jiuqi.nr.data.engine.condition.IFormConditionService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.dataengine.INvwaDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaDataSet;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ProcessStateHistoryDaoImpl
implements ProcessStateHistoryDao {
    private static final Logger logger = LoggerFactory.getLogger(ProcessStateHistoryDaoImpl.class);
    @Autowired
    NrParameterUtils nrParameterUtils;
    @Autowired
    IDataDefinitionRuntimeController dataRunTimeController;
    @Autowired
    IRunTimeViewController runTimeViewController;
    @Autowired
    IBatchQueryUploadStateService uploadStateService;
    @Autowired
    IWorkflow workflow;
    @Autowired
    private IFormConditionService formConditionService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private DataEngineAdapter dataEngineAdapter;
    @Autowired
    private INvwaDataAccessProvider iNvwaDataAccessProvider;
    @Autowired
    private BusinessGenerator businessGenerator;
    @Autowired
    private DimensionUtil dimensionUtil;
    @Autowired
    private IEntityMetaService entityMetaService;

    @Override
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void batchUpdateState(BatchCompleteParam batchCompleteParam) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(batchCompleteParam.getFormSchemeKey());
        if (formScheme == null) {
            throw new BpmException(String.format("\u627e\u4e0d\u5230\u65b9\u6848--%s", batchCompleteParam.getFormSchemeKey()));
        }
        String tableCode = TableConstant.getSysUploadStateTableName(formScheme.getFormSchemeCode());
        TableModelDefine table = this.nrParameterUtils.getTableByCode(tableCode);
        List<ColumnModelDefine> allColumns = this.nrParameterUtils.getAllFieldsInTable(table.getID());
        NvwaQueryModel nvwaQueryModel = new NvwaQueryModel();
        DimensionValueSet queryKey = batchCompleteParam.getMasterKey();
        ColumnModelDefine processColumn = null;
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(table.getName());
        for (ColumnModelDefine columnModelDefine : allColumns) {
            switch (columnModelDefine.getCode()) {
                case "PROCESSKEY": {
                    processColumn = columnModelDefine;
                    nvwaQueryModel.getColumnFilters().put(columnModelDefine, this.nrParameterUtils.getProcessKey(formScheme.getKey()));
                    break;
                }
                default: {
                    Object value;
                    String dimensionName = dimensionChanger.getDimensionName(columnModelDefine);
                    if (dimensionName == null || (value = queryKey.getValue(dimensionName)) == null) break;
                    nvwaQueryModel.getColumnFilters().put(columnModelDefine, value);
                }
            }
            nvwaQueryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        List<DimensionValueSet> masterKeys = batchCompleteParam.getMasterKeysList();
        if (processColumn != null) {
            for (DimensionValueSet masterKey : masterKeys) {
                masterKey.setValue("PROCESSKEY", (Object)this.nrParameterUtils.getProcessKey(formScheme.getKey()));
            }
        }
        try {
            List<ColumnModelDefine> dimensionValueSetColumns = this.nrParameterUtils.getDimensionValueSetColumns(table, allColumns);
            this.nrParameterUtils.batchCommitStateData(nvwaQueryModel, context, table.getName(), allColumns, dimensionValueSetColumns, masterKeys, batchCompleteParam.getActionId(), batchCompleteParam.getTaskId(), batchCompleteParam.getForceUpload(), batchCompleteParam.getFormSchemeKey(), batchCompleteParam.getTaskId());
        }
        catch (Exception e) {
            throw new BpmException("batch commit state data error");
        }
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void updateState(BusinessKey businessKey, String currNode, String actionId, boolean isForceUpload, String taskId) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(businessKey.getFormSchemeKey());
        if (formScheme == null) {
            throw new BpmException(String.format("\u627e\u4e0d\u5230\u65b9\u6848--%s", businessKey.getFormSchemeKey()));
        }
        DimensionValueSet masterKeys = this.nrParameterUtils.convertDimensionName(businessKey);
        this.nrParameterUtils.addFormKeyToMasterKeys(masterKeys, businessKey, businessKey.getFormKey());
        this.nrParameterUtils.commitStateQuery(formScheme, masterKeys, actionId, currNode, isForceUpload, taskId);
    }

    @Override
    public UploadStateNew queryUploadState(DimensionValueSet dimensionValueSet, String formKey, FormSchemeDefine formScheme) {
        UploadStateNew uploadState = this.uploadStateService.queryUploadStateNew(dimensionValueSet, formKey, formScheme);
        if (com.jiuqi.bi.util.StringUtils.isEmpty((String)uploadState.getTaskId())) {
            return null;
        }
        return uploadState;
    }

    @Override
    public void deleteUploadStateAndRecord(BusinessKey businessKey) {
        this.uploadStateService.deleteUploadState(businessKey);
        this.uploadStateService.deleteUploadRecord(businessKey);
    }

    @Override
    public List<UploadProcessInstanceDto> queryUploadInstance(String formSchemKey, String period) {
        ArrayList<UploadProcessInstanceDto> uploadProcessInstances = new ArrayList<UploadProcessInstanceDto>();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemKey);
        WorkFlowType flowType = formScheme.getFlowsSetting().getDesignFlowSettingDefine().getWordFlowType();
        String tableCode = TableConstant.getSysUploadStateTableName(formScheme);
        TableModelDefine table = this.nrParameterUtils.getTableByCode(tableCode);
        List<ColumnModelDefine> allColumns = this.nrParameterUtils.getAllFieldsInTable(table.getID());
        NvwaQueryModel nvwaQueryModel = new NvwaQueryModel();
        for (ColumnModelDefine columnModelDefine : allColumns) {
            if ("DATATIME".equals(columnModelDefine.getCode())) {
                nvwaQueryModel.getColumnFilters().put(columnModelDefine, period);
            }
            nvwaQueryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(nvwaQueryModel);
        try {
            String curNode = "";
            String formKey = "";
            DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(table.getName());
            List<ColumnModelDefine> dimensionColumns = this.nrParameterUtils.getDimensionValueSetColumns(table, allColumns);
            INvwaDataSet executeQueryWithRowKey = readOnlyDataAccess.executeQueryWithRowKey(context);
            for (INvwaDataRow iNvwaDataRow : executeQueryWithRowKey) {
                ArrayKey rowKey = iNvwaDataRow.getRowKey();
                DimensionValueSet dimensionValueSet = dimensionChanger.getDimensionValueSet(rowKey, dimensionColumns);
                for (ColumnModelDefine columnModelDefine : allColumns) {
                    Object value;
                    if ("CURNODE".equals(columnModelDefine.getCode())) {
                        curNode = iNvwaDataRow.getValue(columnModelDefine).toString();
                    }
                    if (!"FORMID".equals(columnModelDefine.getCode()) || (value = iNvwaDataRow.getValue(columnModelDefine)) == null) continue;
                    formKey = value.toString();
                }
                if (flowType != WorkFlowType.GROUP && flowType != WorkFlowType.FORM ? formKey != null && !formKey.equals("00000000-0000-0000-0000-000000000000") : formKey == null || formKey.equals("00000000-0000-0000-0000-000000000000")) continue;
                if ("end".equalsIgnoreCase(curNode)) continue;
                UploadProcessInstanceDto instance = new UploadProcessInstanceDto();
                instance.setBusinessKey(this.businessGenerator.buildBusinessKey(formSchemKey, dimensionValueSet, formKey == null ? null : formKey.toString(), formKey == null ? null : formKey.toString()));
                uploadProcessInstances.add(instance);
            }
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
        return uploadProcessInstances;
    }

    private String actionId(FormSchemeDefine formScheme, DimensionValueSet dim, String actionCode, WorkFlowType workflowStartType, IConditionCache conditionCache) {
        String actionId = null;
        HashSet formOrGroupKeys = new HashSet();
        DimensionValueSet defaultValue = this.setDefaultValue(formScheme, dim);
        try {
            Set<String> reportKeyOrGroupKeys = this.getReportKeyOrGroupKeys(formScheme, dim, conditionCache, workflowStartType);
            boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formScheme.getKey());
            actionId = defaultWorkflow ? this.defaultUnitState(formScheme, dim, actionCode, reportKeyOrGroupKeys) : this.unitState(formScheme, dim, actionCode, reportKeyOrGroupKeys);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return actionId;
    }

    private DimensionValueSet setDefaultValue(FormSchemeDefine formScheme, DimensionValueSet dimensionValueSet) {
        DimensionValueSet dim = new DimensionValueSet();
        DimensionSet dimensionSet = dimensionValueSet.getDimensionSet();
        for (int i = 0; i < dimensionSet.size(); ++i) {
            String dimTemp = dimensionSet.get(i);
            dim.setValue(dimTemp, dimensionValueSet.getValue(dimTemp));
        }
        String dims = formScheme.getDims();
        if (StringUtils.isNotEmpty((String)dims)) {
            String[] dimsArray;
            for (String dimKey : dimsArray = dims.split(";")) {
                String dimensionName = this.entityMetaService.getDimensionName(dimKey);
                if ("MD_CURRENCY".equals(dimensionName)) {
                    dim.setValue(dimensionName, (Object)"CNY");
                }
                if ("MD_GCADJTYPE".equals(dimensionName)) {
                    dim.setValue(dimensionName, (Object)"BEFOREADJ");
                }
                if (!"MD_GCORGTYPE".equals(dimensionName)) continue;
                dim.setValue(dimensionName, (Object)"MD_ORG_CORPORATE");
            }
        }
        return dim;
    }

    private Map<DimensionValueSet, String> actionId(FormSchemeDefine formScheme, List<DimensionValueSet> dims, String actionCode, WorkFlowType workflowStartType, IConditionCache conditionCache) {
        HashMap<DimensionValueSet, String> actionMap = new HashMap<DimensionValueSet, String>();
        try {
            for (DimensionValueSet dim : dims) {
                String actionId = this.actionId(formScheme, dim, actionCode, workflowStartType, conditionCache);
                actionMap.put(dim, actionId);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return actionMap;
    }

    private String defaultUnitState(FormSchemeDefine formSchemeDefine, DimensionValueSet dim, String actionCode, Set<String> formOrGroupKeys) {
        List codes;
        ArrayList<String> actionList = new ArrayList<String>();
        DimensionValueSet fliterDim = this.fliterDim(dim);
        Map<DimensionValueSet, ActionStateBean> queryUploadStates = this.uploadStateService.queryUploadStates(fliterDim, new ArrayList<String>(formOrGroupKeys), new ArrayList<String>(formOrGroupKeys), formSchemeDefine);
        if (queryUploadStates != null && queryUploadStates.size() > 0) {
            for (Map.Entry<DimensionValueSet, ActionStateBean> state : queryUploadStates.entrySet()) {
                boolean bindProcess;
                DimensionValueSet dimensionValueSet = state.getKey();
                ActionStateBean uploadState = state.getValue();
                if (uploadState == null || uploadState.getCode() == null || !(bindProcess = this.workflow.bindProcess(formSchemeDefine.getKey(), dim, dimensionValueSet.getValue("FORMID").toString()))) continue;
                actionList.add(uploadState.getCode());
            }
        }
        if (actionList.contains(UploadState.REJECTED.toString())) {
            return "act_reject";
        }
        if (actionList.contains(UploadState.RETURNED.toString())) {
            return "act_return";
        }
        if (actionList.contains(UploadState.SUBMITED.toString())) {
            List submitCodes = queryUploadStates.values().stream().filter(Objects::nonNull).map(e -> e.getCode()).filter(x -> x.equals(UploadState.SUBMITED.toString())).collect(Collectors.toList());
            if (submitCodes.size() > 0 && submitCodes.size() == formOrGroupKeys.size()) {
                return "act_submit";
            }
            return "act_other_submit";
        }
        if (actionList.contains(UploadState.CONFIRMED.toString())) {
            codes = queryUploadStates.values().stream().filter(Objects::nonNull).map(e -> e.getCode()).filter(x -> x.equals(UploadState.CONFIRMED.toString())).collect(Collectors.toList());
            if (codes.size() > 0 && codes.size() == formOrGroupKeys.size()) {
                return "act_confirm";
            }
            return "act_other_confirm";
        }
        if (actionList.contains(UploadState.UPLOADED.toString())) {
            codes = queryUploadStates.values().stream().filter(Objects::nonNull).map(e -> e.getCode()).filter(x -> x.equals(UploadState.UPLOADED.toString())).collect(Collectors.toList());
            if (codes.size() > 0 && codes.size() == formOrGroupKeys.size()) {
                return "act_upload";
            }
            return "act_other_upload";
        }
        if (actionList.contains(UploadState.ORIGINAL_SUBMIT.toString()) || actionList.contains(UploadState.ORIGINAL_UPLOAD.toString())) {
            codes = queryUploadStates.values().stream().filter(Objects::nonNull).map(e -> e.getCode()).filter(x -> x.equals(UploadState.ORIGINAL_SUBMIT.toString()) || x.equals(UploadState.ORIGINAL_UPLOAD.toString())).collect(Collectors.toList());
            if (codes.size() > 0 && codes.size() == formOrGroupKeys.size()) {
                return "start";
            }
            return "act_other_start";
        }
        if ("act_submit".equals(actionCode)) {
            return "act_other_submit";
        }
        if ("act_upload".equals(actionCode)) {
            return "act_other_upload";
        }
        if ("start".equals(actionCode)) {
            if (queryUploadStates == null || queryUploadStates.size() == 0) {
                return "act_other_start";
            }
            if (queryUploadStates != null && queryUploadStates.size() < formOrGroupKeys.size()) {
                return "act_other_start";
            }
        }
        return "start";
    }

    private String unitState(FormSchemeDefine formSchemeDefine, DimensionValueSet dim, String actionCode, Set<String> formOrGroupKeys) {
        List codes;
        ArrayList<String> actionList = new ArrayList<String>();
        DimensionValueSet fliterDim = this.fliterDim(dim);
        Map<DimensionValueSet, ActionStateBean> queryUploadStates = this.uploadStateService.queryUploadStates(fliterDim, new ArrayList<String>(formOrGroupKeys), new ArrayList<String>(formOrGroupKeys), formSchemeDefine);
        if (queryUploadStates != null && queryUploadStates.size() > 0) {
            for (Map.Entry<DimensionValueSet, ActionStateBean> state : queryUploadStates.entrySet()) {
                boolean bindProcess;
                DimensionValueSet dimensionValueSet = state.getKey();
                ActionStateBean uploadState = state.getValue();
                if (uploadState == null || uploadState.getCode() == null || !(bindProcess = this.workflow.bindProcess(formSchemeDefine.getKey(), dim, dimensionValueSet.getValue("FORMID").toString()))) continue;
                actionList.add(uploadState.getCode());
            }
        }
        if (actionList.contains(UploadState.REJECTED.toString())) {
            return "cus_reject";
        }
        if (actionList.contains(UploadState.RETURNED.toString())) {
            return "cus_return";
        }
        if (actionList.contains(UploadState.SUBMITED.toString())) {
            List submitCodes = queryUploadStates.values().stream().filter(Objects::nonNull).map(e -> e.getCode()).filter(x -> x.equals(UploadState.SUBMITED.toString())).collect(Collectors.toList());
            if (submitCodes.size() > 0 && submitCodes.size() == formOrGroupKeys.size()) {
                return "cus_submit";
            }
            return "act_other_submit";
        }
        if (actionList.contains(UploadState.CONFIRMED.toString())) {
            codes = queryUploadStates.values().stream().filter(Objects::nonNull).map(e -> e.getCode()).filter(x -> x.equals(UploadState.CONFIRMED.toString())).collect(Collectors.toList());
            if (codes.size() > 0 && codes.size() == formOrGroupKeys.size()) {
                return "cus_confirm";
            }
            return "act_other_confirm";
        }
        if (actionList.contains(UploadState.UPLOADED.toString())) {
            codes = queryUploadStates.values().stream().filter(Objects::nonNull).map(e -> e.getCode()).filter(x -> x.equals(UploadState.UPLOADED.toString())).collect(Collectors.toList());
            if (codes.size() > 0 && codes.size() == formOrGroupKeys.size()) {
                return "cus_upload";
            }
            return "act_other_upload";
        }
        if (actionList.contains(UploadState.ORIGINAL_SUBMIT.toString()) || actionList.contains(UploadState.ORIGINAL_UPLOAD.toString())) {
            codes = queryUploadStates.values().stream().filter(Objects::nonNull).map(e -> e.getCode()).filter(x -> x.equals(UploadState.ORIGINAL_SUBMIT.toString()) || x.equals(UploadState.ORIGINAL_UPLOAD.toString())).collect(Collectors.toList());
            if (codes.size() > 0 && codes.size() == formOrGroupKeys.size()) {
                return "start";
            }
            return "act_other_start";
        }
        if (actionList.contains(UploadState.RETURNED.toString())) {
            List returnCodes = queryUploadStates.values().stream().filter(Objects::nonNull).map(e -> e.getCode()).filter(x -> x.equals(UploadState.RETURNED.toString())).collect(Collectors.toList());
            if (returnCodes.size() > 0 && returnCodes.size() == formOrGroupKeys.size()) {
                return "cus_return";
            }
            return "start";
        }
        if ("cus_submit".equals(actionCode)) {
            return "act_other_submit";
        }
        if ("cus_upload".equals(actionCode)) {
            return "act_other_upload";
        }
        if ("start".equals(actionCode)) {
            if (queryUploadStates == null || queryUploadStates.size() == 0) {
                return "act_other_start";
            }
            if (queryUploadStates != null && queryUploadStates.size() < formOrGroupKeys.size()) {
                return "act_other_start";
            }
        }
        return "start";
    }

    private DimensionValueSet buildDim(BusinessKey businessKey) {
        return this.nrParameterUtils.convertDimensionName(businessKey);
    }

    private DimensionValueSet fliterDim(DimensionValueSet dimensionValueSet) {
        DimensionValueSet dimensionValue = new DimensionValueSet();
        if (dimensionValueSet != null) {
            DimensionSet dimensionSet = dimensionValueSet.getDimensionSet();
            int size = dimensionSet.size();
            for (int i = 0; i < size; ++i) {
                String dimenName = dimensionSet.get(i);
                Object value = dimensionValueSet.getValue(dimenName);
                if ("FORMID".equals(dimenName)) continue;
                dimensionValue.setValue(dimenName, value);
            }
        }
        return dimensionValue;
    }

    public static <T> List<T> castList(Object obj, Class<T> clazz) {
        ArrayList<T> result = new ArrayList<T>();
        if (obj instanceof List) {
            for (Object o : (List)obj) {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return null;
    }

    @Override
    public void updateState(BusinessKey businessKey, String currNode, String actionId, boolean isForceUpload, IConditionCache conditionCache, String taskId) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(businessKey.getFormSchemeKey());
        if (formScheme == null) {
            throw new BpmException(String.format("\u627e\u4e0d\u5230\u65b9\u6848--%s", businessKey.getFormSchemeKey()));
        }
        DimensionValueSet masterKeys = this.nrParameterUtils.convertDimensionName(businessKey);
        this.nrParameterUtils.addFormKeyToMasterKeys(masterKeys, businessKey, businessKey.getFormKey());
        this.nrParameterUtils.commitStateQuery(formScheme, masterKeys, actionId, currNode, isForceUpload, taskId);
    }

    public DimensionValueSet buildDimensionSet(List<DimensionValueSet> stepUnit, String fromSchemeKey) {
        ArrayList<Object> unitIds = new ArrayList<Object>();
        String period = null;
        String dwMainDimName = this.dimensionUtil.getDwMainDimName(fromSchemeKey);
        if (stepUnit != null && stepUnit.size() > 0) {
            for (DimensionValueSet batchStepUnit : stepUnit) {
                Object value = batchStepUnit.getValue(dwMainDimName);
                period = (String)batchStepUnit.getValue("DATATIME");
                unitIds.add(value);
            }
        }
        DimensionValueSet dims = new DimensionValueSet();
        dims.setValue("DATATIME", period);
        dims.setValue(dwMainDimName, unitIds);
        return dims;
    }

    private Set<String> getReportKeyOrGroupKeys(FormSchemeDefine fromScheme, DimensionValueSet dim, IConditionCache conditionCache, WorkFlowType workflowStartType) {
        HashSet<String> formOrGroupKeys = new HashSet<String>();
        DimensionValueSet defaultValue = this.setDefaultValue(fromScheme, dim);
        if (null == conditionCache) {
            conditionCache = this.formConditionService.getConditionForms(defaultValue, fromScheme.getKey());
        }
        formOrGroupKeys = WorkFlowType.FORM.equals((Object)workflowStartType) ? new HashSet(conditionCache.getSeeForms(defaultValue)) : new HashSet(conditionCache.getSeeFormGroups(defaultValue));
        return formOrGroupKeys;
    }

    private String getTaskCode(String taskCode, String actionCode, FormSchemeDefine formSchemeDefine, DimensionValueSet dim, Set<String> formOrGroupKeys) {
        String taskCodeTemp = taskCode;
        HashMap<String, String> codeToTaskCode = new HashMap<String, String>();
        DimensionValueSet fliterDim = this.fliterDim(dim);
        List<UploadStateNew> queryUploadStates = this.uploadStateService.queryUploadStateNew(formSchemeDefine, fliterDim, new ArrayList<String>(formOrGroupKeys));
        if (queryUploadStates != null && queryUploadStates.size() > 0) {
            for (UploadStateNew uploadStateNew : queryUploadStates) {
                codeToTaskCode.put(uploadStateNew.getPreEvent(), uploadStateNew.getTaskId());
            }
        }
        if (codeToTaskCode != null && codeToTaskCode.size() > 0) {
            taskCodeTemp = (String)codeToTaskCode.get(actionCode);
            if (taskCodeTemp != null) {
                return taskCodeTemp;
            }
            Set keySet = codeToTaskCode.keySet();
            if (keySet.contains("act_reject") || keySet.contains("cus_reject")) {
                taskCodeTemp = codeToTaskCode.get("act_reject") != null ? (String)codeToTaskCode.get("act_reject") : (String)codeToTaskCode.get("cus_reject");
            } else if (keySet.contains("act_submit") || keySet.contains("cus_submit") || keySet.contains("act_return") || keySet.contains("cus_return")) {
                taskCodeTemp = codeToTaskCode.get("act_submit") != null ? (String)codeToTaskCode.get("act_submit") : (String)codeToTaskCode.get("cus_return");
            } else if (keySet.contains("act_confirm") || keySet.contains("cus_confirm")) {
                taskCodeTemp = codeToTaskCode.get("act_confirm") != null ? (String)codeToTaskCode.get("act_confirm") : (String)codeToTaskCode.get("cus_confirm");
            } else if (keySet.contains("act_upload") || keySet.contains("cus_upload")) {
                taskCodeTemp = codeToTaskCode.get("act_upload") != null ? (String)codeToTaskCode.get("act_upload") : (String)codeToTaskCode.get("cus_confirm");
            }
        }
        return taskCodeTemp;
    }

    @Override
    public void updateUnitState(String formSchemeKey, DimensionValueSet dimension, String currNode, String actionId, boolean isForceUpload) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        if (formScheme == null) {
            throw new BpmException(String.format("\u627e\u4e0d\u5230\u65b9\u6848--%s", formSchemeKey));
        }
        String tableCode = TableConstant.getSysUploadStateTableName(formScheme.getFormSchemeCode());
        TableModelDefine table = this.nrParameterUtils.getTableByCode(tableCode);
        List<ColumnModelDefine> allColumns = this.nrParameterUtils.getAllFieldsInTable(table.getID());
        NvwaQueryModel nvwaQueryModel = new NvwaQueryModel();
        for (ColumnModelDefine columnModelDefine : allColumns) {
            nvwaQueryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        IConditionCache conditionCache = this.formConditionService.getConditionForms(dimension, formScheme.getKey());
        WorkFlowType workflowStartType = this.nrParameterUtils.getWorkflowStartType(formSchemeKey);
        List dimensionSetList = DimensionValueSetUtil.getDimensionSetList((DimensionValueSet)dimension);
        ArrayList<UnitState> units = new ArrayList<UnitState>();
        if (!WorkFlowType.ENTITY.equals((Object)workflowStartType)) {
            for (DimensionValueSet dimensionValueSet : dimensionSetList) {
                UnitState calculateUnit = this.calculateUnit(formScheme, dimensionValueSet, actionId, currNode, isForceUpload, workflowStartType, conditionCache);
                units.add(calculateUnit);
            }
        }
        List<ColumnModelDefine> dimensionValueSetColumns = this.nrParameterUtils.getDimensionValueSetColumns(table, allColumns);
        try {
            this.nrParameterUtils.batchCommitUnitStateData(formSchemeKey, nvwaQueryModel, context, table.getName(), allColumns, dimensionValueSetColumns, units);
        }
        catch (Exception e) {
            logger.error("\u72b6\u6001\u8868" + tableCode + ",\u66f4\u65b0\u51fa\u51fa\u9519");
        }
    }

    private UnitState calculateUnit(FormSchemeDefine formScheme, DimensionValueSet dim, String actionCode, String currNode, boolean forceUpload, WorkFlowType workflowStartType, IConditionCache conditionCache) {
        UnitState unitState = new UnitState();
        String actionId = this.actionId(formScheme, dim, actionCode, workflowStartType, conditionCache);
        unitState.setActionCode(actionId);
        this.nrParameterUtils.addFormKeyToMasterKeys(dim, null, "11111111-1111-1111-1111-111111111111");
        unitState.setDimensionValueSet(dim);
        unitState.setTaskId(currNode);
        unitState.setForceUpload(forceUpload);
        return unitState;
    }
}

