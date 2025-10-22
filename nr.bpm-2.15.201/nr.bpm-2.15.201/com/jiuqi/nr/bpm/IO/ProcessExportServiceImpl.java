/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.monitor.IProgressMonitor
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.definition.service.ITaskService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.workflow2.engine.core.process.io.ProcessDataType
 *  com.jiuqi.nr.workflow2.engine.core.process.io.common.ProcessIOInstance
 *  com.jiuqi.nr.workflow2.engine.core.process.io.common.ProcessIOOperation
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.DimensionObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormGroupObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormObject
 */
package com.jiuqi.nr.bpm.IO;

import com.jiuqi.bi.monitor.IProgressMonitor;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.nr.bpm.IO.ProcessIODimensionsBuilder;
import com.jiuqi.nr.bpm.IO.QueryTableModel;
import com.jiuqi.nr.bpm.common.UploadRecordNew;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowNodeSet;
import com.jiuqi.nr.bpm.custom.service.CustomWorkFolwService;
import com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.definition.service.ITaskService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.workflow2.engine.core.process.io.ProcessDataType;
import com.jiuqi.nr.workflow2.engine.core.process.io.common.ProcessIOInstance;
import com.jiuqi.nr.workflow2.engine.core.process.io.common.ProcessIOOperation;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.DimensionObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormGroupObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessExportServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(ProcessExportServiceImpl.class);
    private QueryTableModel queryTableModel;
    private ProcessIODimensionsBuilder processIODimensionsBuilder;
    private IRunTimeViewController runTimeViewController;
    private WorkflowSettingService workflowSettingService;
    private CustomWorkFolwService customWorkFolwService;
    private Map<String, List<ProcessIOInstance>> processIOInstanceMap = new HashMap<String, List<ProcessIOInstance>>();
    private Map<String, List<ProcessIOOperation>> processIOOperationMap = new HashMap<String, List<ProcessIOOperation>>();
    private Map<String, ProcessIOInstance> instanceToUUID = new HashMap<String, ProcessIOInstance>();
    private List<String> periods = new ArrayList<String>();
    private WorkFlowType workFlowType;
    private boolean defaultWorkflow;
    private SystemUserService systemUserService;
    private final ITaskService taskService;
    private final IPeriodEntityAdapter periodEntityAdapter;
    private final IEntityMetaService entityMetaService;

    public ProcessExportServiceImpl(QueryTableModel queryTableModel, ProcessIODimensionsBuilder processIODimensionsBuilder, IRunTimeViewController runTimeViewController, WorkflowSettingService workflowSettingService, CustomWorkFolwService customWorkFolwService, WorkFlowType workFlowType, boolean defaultWorkflow) {
        this.queryTableModel = queryTableModel;
        this.processIODimensionsBuilder = processIODimensionsBuilder;
        this.runTimeViewController = runTimeViewController;
        this.workflowSettingService = workflowSettingService;
        this.customWorkFolwService = customWorkFolwService;
        this.systemUserService = (SystemUserService)BeanUtil.getBean(SystemUserService.class);
        this.taskService = (ITaskService)BeanUtil.getBean(ITaskService.class);
        this.periodEntityAdapter = (IPeriodEntityAdapter)BeanUtil.getBean(IPeriodEntityAdapter.class);
        this.entityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
        this.workFlowType = workFlowType;
        this.defaultWorkflow = defaultWorkflow;
    }

    public void processData(IBusinessKeyCollection businessKeys, Set<ProcessDataType> outputDataTypes, IProgressMonitor monitor) {
        Map<String, List<DimensionCombination>> periodToDimensionCombinations = this.splitBusinessCollection(businessKeys);
        if (periodToDimensionCombinations == null) {
            throw new RuntimeException("\u5bfc\u51fa\u7684\u6570\u636e\u53c2\u6570\u4e3a\u7a7a,\u8bf7\u6838\u5b9e\u4f20\u5165\u7684\u53c2\u6570");
        }
        monitor.startTask("\u6d41\u7a0b\u6570\u636e\u5bfc\u51fa", periodToDimensionCombinations.size() + 1);
        try {
            String task = businessKeys.getTask();
            List formSchemeDefines = this.runTimeViewController.listFormSchemeByTask(task);
            Map<String, String> nodes = this.getNodes(formSchemeDefines);
            Map<String, List<UploadStateNew>> uploadStateMap = this.batchQueryUplaodState(businessKeys, formSchemeDefines);
            Map<String, List<UploadRecordNew>> historyStateMap = this.batchQueryHisUplaodState(businessKeys, formSchemeDefines);
            monitor.stepIn();
            for (Map.Entry<String, List<DimensionCombination>> entry : periodToDimensionCombinations.entrySet()) {
                String period = entry.getKey();
                FormSchemeDefine formScheme = this.getFormScheme(task, period);
                List<UploadStateNew> stateList = uploadStateMap.get(period);
                List<UploadRecordNew> historyStateList = historyStateMap.get(period);
                Map<String, UploadStateNew> stateMap = this.convertStateMap(stateList);
                Map<String, List<UploadRecordNew>> hisStateMap = this.convertHisStateMap(historyStateList);
                this.buildProcessData(stateMap, hisStateMap, formScheme, nodes);
                monitor.stepIn();
            }
            monitor.finishTask("\u6d41\u7a0b\u6570\u636e\u5bfc\u51fa");
        }
        catch (Exception e) {
            logger.error("\u9ed8\u8ba4\u6d41\u7a0b1.0\u5f15\u64ce\u6570\u636e\u5bfc\u51fa\u5f02\u5e38 \u5f02\u5e38\u4fe1\u606f\u4e3a\uff1a{}", (Object)e.getMessage());
            monitor.finishTask("\u6d41\u7a0b\u6570\u636e\u5bfc\u51fa");
            throw new RuntimeException(e);
        }
    }

    private Map<String, UploadStateNew> convertStateMap(List<UploadStateNew> stateList) {
        LinkedHashMap<String, UploadStateNew> map = new LinkedHashMap<String, UploadStateNew>();
        if (stateList != null) {
            for (UploadStateNew state : stateList) {
                DimensionValueSet entities = state.getEntities();
                String formId = state.getFormId();
                String uniqueKey = this.uniqueKey(entities, formId);
                map.put(uniqueKey, state);
            }
        }
        return map;
    }

    private Map<String, List<UploadRecordNew>> convertHisStateMap(List<UploadRecordNew> hisStateList) {
        LinkedHashMap<String, List<UploadRecordNew>> map = new LinkedHashMap<String, List<UploadRecordNew>>();
        if (hisStateList != null) {
            for (UploadRecordNew hisState : hisStateList) {
                String formId;
                DimensionValueSet entities = hisState.getEntities();
                String uniqueKey = this.uniqueKey(entities, formId = hisState.getFormKey());
                if (map.get(uniqueKey) == null) {
                    LinkedList<UploadRecordNew> hisStates = new LinkedList<UploadRecordNew>();
                    hisStates.add(hisState);
                    map.put(uniqueKey, hisStates);
                    continue;
                }
                ((List)map.get(uniqueKey)).add(hisState);
            }
        }
        return map;
    }

    private void buildProcessData(Map<String, UploadStateNew> stateMap, Map<String, List<UploadRecordNew>> hisStateMap, FormSchemeDefine formScheme, Map<String, String> nodes) {
        if (stateMap == null) {
            // empty if block
        }
        for (Map.Entry<String, UploadStateNew> entry : stateMap.entrySet()) {
            String uniqueKey = entry.getKey();
            UploadStateNew uploadStateNew = entry.getValue();
            ProcessIOInstance processIOInstance = this.buildProcessInstance(uploadStateNew, formScheme, nodes);
            boolean isForceReport = uploadStateNew.getForce().equals("1");
            List<UploadRecordNew> hisStateDataList = hisStateMap.get(uniqueKey);
            this.buildProcessOperation(processIOInstance, hisStateDataList, isForceReport);
        }
    }

    public List<String> getPeriods() {
        return this.periods;
    }

    public List<ProcessIOInstance> getProcessIOInstances(String period) {
        return this.processIOInstanceMap.get(period);
    }

    public List<ProcessIOOperation> getProcessIOOperations(String period) {
        return this.processIOOperationMap.get(period);
    }

    private void convertData(List<DimensionCombination> dimensionCombinationList, List<DimensionValueSet> dims, List<String> formOrGroupKeys, IBusinessObjectCollection businessObjects) {
        block4: {
            block3: {
                if (!WorkFlowType.ENTITY.equals((Object)this.workFlowType)) break block3;
                for (DimensionCombination dimensionCombination : dimensionCombinationList) {
                    dims.add(dimensionCombination.toDimensionValueSet());
                }
                break block4;
            }
            if (!WorkFlowType.FORM.equals((Object)this.workFlowType) && !WorkFlowType.GROUP.equals((Object)this.workFlowType)) break block4;
            for (DimensionCombination dimensionCombination : dimensionCombinationList) {
                Collection deimensionObject = businessObjects.getDeimensionObject(dimensionCombination);
                for (String formKey : deimensionObject) {
                    formOrGroupKeys.add(formKey);
                }
                dims.add(dimensionCombination.toDimensionValueSet());
            }
        }
    }

    private ProcessIOInstance buildProcessInstance(UploadStateNew processStateData, FormSchemeDefine formScheme, Map<String, String> nodes) {
        ProcessIOInstance processIOInstance = new ProcessIOInstance();
        String uuid = UUID.randomUUID().toString();
        processIOInstance.setId(uuid);
        DimensionValueSet dimensionValueSet = processStateData.getEntities();
        String period = dimensionValueSet.getValue("DATATIME").toString();
        IBusinessObject businessObject = this.processIODimensionsBuilder.buildBusinessObject(formScheme.getTaskKey(), period, dimensionValueSet, processStateData.getFormId());
        processIOInstance.setBusinessObject(businessObject);
        processIOInstance.setCurrentUserTask(processStateData.getTaskId());
        processIOInstance.setStatus(this.convertState(processStateData.getPreEvent(), processStateData.getTaskId(), formScheme));
        processIOInstance.setCurrentUserTaskCode(this.defaultWorkflow ? processStateData.getTaskId() : nodes.get(processStateData.getTaskId()));
        Calendar startTime = Calendar.getInstance();
        if (processStateData.getStartTime() != null) {
            startTime.setTime(processStateData.getStartTime());
        }
        processIOInstance.setStartTime(startTime);
        Calendar updateTime = Calendar.getInstance();
        if (processStateData.getUpdateTime() != null) {
            updateTime.setTime(processStateData.getUpdateTime());
        }
        processIOInstance.setUpdateTime(updateTime);
        if (this.processIOInstanceMap.get(period) == null) {
            ArrayList<ProcessIOInstance> instances = new ArrayList<ProcessIOInstance>();
            instances.add(processIOInstance);
            this.processIOInstanceMap.put(period, instances);
        } else {
            this.processIOInstanceMap.get(period).add(processIOInstance);
        }
        return processIOInstance;
    }

    public void buildProcessOperation(ProcessIOInstance processIOInstance, List<UploadRecordNew> hisStateDataList, boolean isForceReport) {
        if (hisStateDataList == null) {
            return;
        }
        ArrayList<ProcessIOOperation> instanceOperations = new ArrayList<ProcessIOOperation>();
        for (int i = 0; i < hisStateDataList.size(); ++i) {
            String stateNode;
            UploadRecordNew uploadRecordNew = hisStateDataList.get(i);
            DimensionValueSet entities = uploadRecordNew.getEntities();
            String period = entities.getValue("DATATIME").toString();
            ProcessIOOperation processIOOperation = new ProcessIOOperation();
            processIOOperation.setInstanceId(processIOInstance.getId());
            String targetNode = stateNode = processIOInstance.getCurrentUserTask();
            if (i + 1 < hisStateDataList.size()) {
                targetNode = hisStateDataList.get(i + 1).getTaskId();
            }
            processIOOperation.setTargetUserTask(targetNode);
            processIOOperation.setSourceUserTask(uploadRecordNew.getTaskId());
            processIOOperation.setAction(uploadRecordNew.getAction());
            String operator = uploadRecordNew.getOperator();
            if (operator != null && "00000000-0000-0000-0000-000000000000".equals(operator)) {
                User systemUser = this.getSystemUser();
                operator = systemUser.getId();
            }
            processIOOperation.setOperator(operator);
            String time = uploadRecordNew.getTime();
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = sdf.parse(time);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                processIOOperation.setOperateTime(calendar);
            }
            catch (Exception sdf) {
                // empty catch block
            }
            processIOOperation.setComment(uploadRecordNew.getCmt());
            if (this.processIOOperationMap.get(period) == null) {
                ArrayList<ProcessIOOperation> operations = new ArrayList<ProcessIOOperation>();
                operations.add(processIOOperation);
                this.processIOOperationMap.put(period, operations);
            } else {
                this.processIOOperationMap.get(period).add(processIOOperation);
            }
            instanceOperations.add(processIOOperation);
        }
        ProcessIOOperation latestOpt = null;
        for (ProcessIOOperation opt : instanceOperations) {
            if (latestOpt == null) {
                latestOpt = opt;
                continue;
            }
            if (!opt.getOperateTime().after(latestOpt.getOperateTime())) continue;
            latestOpt = opt;
        }
        if (latestOpt != null) {
            latestOpt.setForceReport(isForceReport);
        }
    }

    private String uniqueKey(DimensionValueSet dimensionValue, String formOrGroupKey) {
        StringBuffer sb = new StringBuffer();
        sb.append("'");
        DimensionSet dimensionSet = dimensionValue.getDimensionSet();
        for (int i = 0; i < dimensionSet.size(); ++i) {
            if ("RECORDKEY".equals(dimensionSet.get(i))) continue;
            String dimension = dimensionSet.get(i);
            sb.append(dimensionValue.getValue(dimension).toString()).append(";");
        }
        if (WorkFlowType.FORM.equals((Object)this.workFlowType) || WorkFlowType.GROUP.equals((Object)this.workFlowType)) {
            sb.append(formOrGroupKey);
        }
        sb.append("'");
        return sb.toString();
    }

    private Map<String, List<DimensionCombination>> splitBusinessCollection(IBusinessKeyCollection businessKeys) {
        if (businessKeys == null) {
            logger.error("\u6d41\u7a0b\u6570\u636e\u5bfc\u51fa\u53c2\u6570\u4e3a\u7a7a");
            return null;
        }
        HashMap<String, List<DimensionCombination>> dimensionValueMap = new HashMap<String, List<DimensionCombination>>();
        IBusinessObjectCollection businessObjects = businessKeys.getBusinessObjects();
        DimensionCollection dimensionCollection = businessObjects.getDimensions();
        List dimensionCombinationsList = dimensionCollection.getDimensionCombinations();
        for (DimensionCombination dimensionCombination : dimensionCombinationsList) {
            FixedDimensionValue periodDimensionValue = dimensionCombination.getPeriodDimensionValue();
            String period = periodDimensionValue.getValue().toString();
            if (dimensionValueMap.get(period) == null) {
                ArrayList<DimensionCombination> businessObjectCollectionList = new ArrayList<DimensionCombination>();
                businessObjectCollectionList.add(dimensionCombination);
                dimensionValueMap.put(period, businessObjectCollectionList);
            } else {
                ((List)dimensionValueMap.get(period)).add(dimensionCombination);
            }
            if (this.periods == null) {
                this.periods = new ArrayList<String>();
            }
            if (this.periods.contains(period)) continue;
            this.periods.add(period);
        }
        return dimensionValueMap;
    }

    private FormSchemeDefine getFormScheme(String taskKey, String period) {
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        SchemePeriodLinkDefine schemePeriodLinkDefine = runTimeViewController.getSchemePeriodLinkByPeriodAndTask(period, taskKey);
        if (schemePeriodLinkDefine == null) {
            throw new RuntimeException("\u62a5\u8868\u65b9\u6848\u4e0d\u5b58\u5728,\u8bf7\u6838\u5b9e\u8be5\u4efb\u52a1:" + taskKey + ",\u8be5\u65f6\u671f:" + period + "\u4e0b,\u662f\u5426\u5b58\u5728\u5bf9\u5e94\u7684\u62a5\u8868\u65b9\u6848");
        }
        return runTimeViewController.getFormScheme(schemePeriodLinkDefine.getSchemeKey());
    }

    private String convertState(String action, String node, FormSchemeDefine formScheme) {
        TaskFlowsDefine flowsSetting = formScheme.getFlowsSetting();
        if ("start".equals(action)) {
            if (node.equals("tsk_upload")) {
                if (flowsSetting.isUnitSubmitForCensorship()) {
                    return UploadState.SUBMITED.toString();
                }
                return UploadState.ORIGINAL_UPLOAD.toString();
            }
            if (node.equals("tsk_submit")) {
                return UploadState.ORIGINAL_SUBMIT.toString();
            }
            return UploadState.ORIGINAL_UPLOAD.toString();
        }
        if ("act_submit".equals(action) || "cus_submit".equals(action)) {
            return UploadState.SUBMITED.toString();
        }
        if ("act_return".equals(action) || "cus_return".equals(action)) {
            return UploadState.RETURNED.toString();
        }
        if ("act_upload".equals(action) || "act_cancel_confirm".equals(action) || "cus_upload".equals(action)) {
            return UploadState.UPLOADED.toString();
        }
        if ("act_confirm".equals(action) || "cus_confirm".equals(action)) {
            return UploadState.CONFIRMED.toString();
        }
        if ("act_reject".equals(action) || "cus_reject".equals(action)) {
            return UploadState.REJECTED.toString();
        }
        return UploadState.ORIGINAL_UPLOAD.toString();
    }

    private Map<String, List<UploadStateNew>> batchQueryUplaodState(IBusinessKeyCollection businessKeys, List<FormSchemeDefine> formSchemeDefines) {
        ArrayList<DimensionValueSet> dims = new ArrayList<DimensionValueSet>();
        ArrayList<String> formOrGroupKeys = new ArrayList<String>();
        IBusinessObjectCollection businessObjects = businessKeys.getBusinessObjects();
        DimensionCollection valueSets = businessObjects.getDimensions();
        List dimensionCombinations = valueSets.getDimensionCombinations();
        this.convertData(dimensionCombinations, dims, formOrGroupKeys, businessKeys.getBusinessObjects());
        HashMap<String, List<UploadStateNew>> uploadStateMap = new HashMap<String, List<UploadStateNew>>();
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.mergeDimensionValueSet(dims);
        BusinessObjectSet businessObjectSet = new BusinessObjectSet(businessObjects);
        List<DataDimension> allReportDimensions = this.getAllReportDimensions(businessKeys.getTask());
        ReportDimensionValueSetTransfer transfer = new ReportDimensionValueSetTransfer(allReportDimensions.stream().map(this::getDimensionName).collect(Collectors.toList()));
        LinkedList<UploadStateNew> stateNewList = new LinkedList<UploadStateNew>();
        for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
            List<UploadStateNew> uploadStateNews = this.queryTableModel.queryProcessStateDataList(formSchemeDefine, dimensionValueSet, formOrGroupKeys);
            stateNewList.addAll(this.filterState(uploadStateNews, businessObjectSet, transfer));
        }
        if (stateNewList != null && stateNewList.size() > 0) {
            for (UploadStateNew uploadStateNew : stateNewList) {
                if (uploadStateNew == null) continue;
                DimensionValueSet entities = uploadStateNew.getEntities();
                String period = entities.getValue("DATATIME").toString();
                if (uploadStateMap.get(period) == null) {
                    LinkedList<UploadStateNew> uploadStateNewList = new LinkedList<UploadStateNew>();
                    uploadStateNewList.add(uploadStateNew);
                    uploadStateMap.put(period, uploadStateNewList);
                    continue;
                }
                ((List)uploadStateMap.get(period)).add(uploadStateNew);
            }
        }
        return uploadStateMap;
    }

    private Map<String, List<UploadRecordNew>> batchQueryHisUplaodState(IBusinessKeyCollection businessKeys, List<FormSchemeDefine> formSchemeDefines) {
        ArrayList<DimensionValueSet> dims = new ArrayList<DimensionValueSet>();
        ArrayList<String> formOrGroupKeys = new ArrayList<String>();
        IBusinessObjectCollection businessObjects = businessKeys.getBusinessObjects();
        DimensionCollection valueSets = businessObjects.getDimensions();
        List dimensionCombinations = valueSets.getDimensionCombinations();
        this.convertData(dimensionCombinations, dims, formOrGroupKeys, businessKeys.getBusinessObjects());
        HashMap<String, List<UploadRecordNew>> hisUploadStateMap = new HashMap<String, List<UploadRecordNew>>();
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.mergeDimensionValueSet(dims);
        BusinessObjectSet businessObjectSet = new BusinessObjectSet(businessObjects);
        List<DataDimension> allReportDimensions = this.getAllReportDimensions(businessKeys.getTask());
        ReportDimensionValueSetTransfer transfer = new ReportDimensionValueSetTransfer(allReportDimensions.stream().map(this::getDimensionName).collect(Collectors.toList()));
        LinkedList<UploadRecordNew> hisStateRecordList = new LinkedList<UploadRecordNew>();
        for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
            List<UploadRecordNew> uploadRecordNews = this.queryTableModel.queryProcessHistoryStates(formSchemeDefine, dimensionValueSet, formOrGroupKeys);
            hisStateRecordList.addAll(this.filterRecord(uploadRecordNews, businessObjectSet, transfer));
        }
        if (hisStateRecordList != null && hisStateRecordList.size() > 0) {
            for (UploadRecordNew uploadStateNew : hisStateRecordList) {
                if (uploadStateNew == null) continue;
                DimensionValueSet entities = uploadStateNew.getEntities();
                String period = entities.getValue("DATATIME").toString();
                if (hisUploadStateMap.get(period) == null) {
                    LinkedList<UploadRecordNew> hisStateList = new LinkedList<UploadRecordNew>();
                    hisStateList.add(uploadStateNew);
                    hisUploadStateMap.put(period, hisStateList);
                    continue;
                }
                ((List)hisUploadStateMap.get(period)).add(uploadStateNew);
            }
        }
        return hisUploadStateMap;
    }

    private Map<String, String> getNodes(List<FormSchemeDefine> formSchemeDefines) {
        HashMap<String, String> nodeMap = new HashMap<String, String>();
        try {
            String workflowId;
            WorkFlowDefine workFlowDefine;
            FormSchemeDefine formSchemeDefine;
            WorkflowSettingDefine workflowSettingDefine;
            if (!this.defaultWorkflow && formSchemeDefines != null && formSchemeDefines.size() > 0 && (workflowSettingDefine = this.workflowSettingService.getWorkflowDefineByFormSchemeKey((formSchemeDefine = formSchemeDefines.get(0)).getKey())) != null && workflowSettingDefine.getId() != null && (workFlowDefine = this.customWorkFolwService.getWorkFlowDefineByID(workflowId = workflowSettingDefine.getWorkflowId(), 1)) != null) {
                List<WorkFlowNodeSet> workFlowNodeSets = this.customWorkFolwService.getWorkFlowNodeSets(workFlowDefine.getLinkid());
                for (WorkFlowNodeSet workFlowNodeSet : workFlowNodeSets) {
                    String id = workFlowNodeSet.getId();
                    String code = workFlowNodeSet.getCode();
                    nodeMap.put(id, code);
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return nodeMap;
    }

    private User getSystemUser() {
        List users = this.systemUserService.getUsers();
        if (users != null && users.size() > 0) {
            return (User)users.get(0);
        }
        return null;
    }

    private List<DataDimension> getAllReportDimensions(String taskKey) {
        ArrayList<DataDimension> rptDimensionDefines = new ArrayList<DataDimension>();
        List dataDimension = this.taskService.getDataDimension(taskKey);
        rptDimensionDefines.add(dataDimension.stream().filter(e -> e.getDimensionType() == DimensionType.UNIT).findFirst().orElse(null));
        rptDimensionDefines.add(dataDimension.stream().filter(e -> e.getDimensionType() == DimensionType.PERIOD).findFirst().orElse(null));
        rptDimensionDefines.addAll(this.taskService.getReportDimension(taskKey));
        return rptDimensionDefines;
    }

    private String getDimensionName(DataDimension dataDimension) {
        String dimKey = dataDimension.getDimKey();
        if (this.periodEntityAdapter.isPeriodEntity(dimKey)) {
            return this.periodEntityAdapter.getPeriodEntity(dimKey).getDimensionName();
        }
        if ("ADJUST".equals(dimKey)) {
            return "ADJUST";
        }
        return this.entityMetaService.queryEntity(dimKey).getDimensionName();
    }

    private List<UploadStateNew> filterState(List<UploadStateNew> origin, BusinessObjectSet businessObjectSet, ReportDimensionValueSetTransfer transfer) {
        ArrayList<UploadStateNew> filterResult = new ArrayList<UploadStateNew>();
        for (UploadStateNew uploadStateNew : origin) {
            DimensionCombinationBuilder dimensionBuilder = new DimensionCombinationBuilder(transfer.TransferToReportDimensionValueSet(uploadStateNew.getEntities()));
            Object businessObject = this.workFlowType.equals((Object)WorkFlowType.FORM) ? new FormObject(dimensionBuilder.getCombination(), uploadStateNew.getFormId()) : (this.workFlowType.equals((Object)WorkFlowType.GROUP) ? new FormGroupObject(dimensionBuilder.getCombination(), uploadStateNew.getFormId()) : new DimensionObject(dimensionBuilder.getCombination()));
            if (!businessObjectSet.contains((IBusinessObject)businessObject)) continue;
            filterResult.add(uploadStateNew);
        }
        return filterResult;
    }

    private List<UploadRecordNew> filterRecord(List<UploadRecordNew> origin, BusinessObjectSet businessObjectSet, ReportDimensionValueSetTransfer transfer) {
        ArrayList<UploadRecordNew> filterResult = new ArrayList<UploadRecordNew>();
        for (UploadRecordNew uploadRecordNew : origin) {
            DimensionCombinationBuilder dimensionBuilder = new DimensionCombinationBuilder(transfer.TransferToReportDimensionValueSet(uploadRecordNew.getEntities()));
            Object businessObject = this.workFlowType.equals((Object)WorkFlowType.FORM) ? new FormObject(dimensionBuilder.getCombination(), uploadRecordNew.getFormKey()) : (this.workFlowType.equals((Object)WorkFlowType.GROUP) ? new FormGroupObject(dimensionBuilder.getCombination(), uploadRecordNew.getFormKey()) : new DimensionObject(dimensionBuilder.getCombination()));
            if (!businessObjectSet.contains((IBusinessObject)businessObject)) continue;
            filterResult.add(uploadRecordNew);
        }
        return filterResult;
    }

    private static class BusinessObjectSet {
        private final Set<IBusinessObject> set = new HashSet<IBusinessObject>();

        public BusinessObjectSet(IBusinessObjectCollection rangeBusinessObjects) {
            for (IBusinessObject bizObject : rangeBusinessObjects) {
                this.set.add(bizObject);
            }
        }

        public boolean contains(IBusinessObject businessObject) {
            return this.set.contains(businessObject);
        }
    }

    private static class ReportDimensionValueSetTransfer {
        private final List<String> reportDimensionNames;

        public ReportDimensionValueSetTransfer(List<String> reportDimensionNames) {
            this.reportDimensionNames = reportDimensionNames;
        }

        public DimensionValueSet TransferToReportDimensionValueSet(DimensionValueSet dimensionValueSet) {
            DimensionValueSet reportDimensionValueSet = new DimensionValueSet();
            for (String reportDimensionName : this.reportDimensionNames) {
                reportDimensionValueSet.setValue(reportDimensionName, dimensionValueSet.getValue(reportDimensionName));
            }
            return reportDimensionValueSet;
        }
    }
}

