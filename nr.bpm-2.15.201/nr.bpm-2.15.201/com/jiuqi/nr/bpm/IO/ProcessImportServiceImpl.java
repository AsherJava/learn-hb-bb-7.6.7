/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.monitor.IProgressMonitor
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.workflow2.engine.core.process.io.IPorcessDataInputStream
 *  com.jiuqi.nr.workflow2.engine.core.process.io.IProcessDataImportResult
 *  com.jiuqi.nr.workflow2.engine.core.process.io.IProcessDataReader
 *  com.jiuqi.nr.workflow2.engine.core.process.io.IProcessIOInstance
 *  com.jiuqi.nr.workflow2.engine.core.process.io.IProcessIOOperation
 *  com.jiuqi.nr.workflow2.engine.core.process.io.common.ProcessIOInstance
 *  com.jiuqi.nr.workflow2.engine.core.process.io.common.ProcessIOOperation
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.DimensionObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormGroupObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormObject
 */
package com.jiuqi.nr.bpm.IO;

import com.jiuqi.bi.monitor.IProgressMonitor;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.nr.bpm.IO.ProcessDataImportResult;
import com.jiuqi.nr.bpm.IO.ProcessIODimensionsBuilder;
import com.jiuqi.nr.bpm.IO.QueryTableModel;
import com.jiuqi.nr.bpm.common.UploadRecordNew;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowNodeSet;
import com.jiuqi.nr.bpm.custom.service.CustomWorkFolwService;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.workflow2.engine.core.process.io.IPorcessDataInputStream;
import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessDataImportResult;
import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessDataReader;
import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessIOInstance;
import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessIOOperation;
import com.jiuqi.nr.workflow2.engine.core.process.io.common.ProcessIOInstance;
import com.jiuqi.nr.workflow2.engine.core.process.io.common.ProcessIOOperation;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.DimensionObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormGroupObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessImportServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(ProcessImportServiceImpl.class);
    private QueryTableModel queryTableModel;
    private ProcessIODimensionsBuilder processIODimensionsBuilder;
    private IWorkflow workflow;
    private WorkflowSettingService workflowSettingService;
    private CustomWorkFolwService customWorkFolwService;
    private IRunTimeViewController runTimeViewController;
    private SystemUserService systemUserService;

    public ProcessImportServiceImpl(QueryTableModel queryTableModel, ProcessIODimensionsBuilder processIODimensionsBuilder, IWorkflow workflow, WorkflowSettingService workflowSettingService, CustomWorkFolwService customWorkFolwService, IRunTimeViewController runTimeViewController) {
        this.queryTableModel = queryTableModel;
        this.processIODimensionsBuilder = processIODimensionsBuilder;
        this.workflow = workflow;
        this.workflowSettingService = workflowSettingService;
        this.customWorkFolwService = customWorkFolwService;
        this.runTimeViewController = runTimeViewController;
        this.systemUserService = (SystemUserService)BeanUtil.getBean(SystemUserService.class);
    }

    public IProcessDataImportResult processData(IPorcessDataInputStream inputStream, IProgressMonitor monitor) {
        ProcessDataImportResult processDataImportResult = new ProcessDataImportResult();
        String taskCode = inputStream.getTaskCode();
        List periods = inputStream.getPeriods();
        if (periods == null || periods.size() == 0) {
            throw new RuntimeException("\u5bfc\u5165\u7684\u65f6\u671f\u6570\u636e\u4e0d\u5b58\u5728");
        }
        Map<String, String> targetNodeIdToCodeMap = this.getNodes(taskCode);
        monitor.startTask("\u6d41\u7a0b\u6570\u636e\u5bfc\u5165", periods.size());
        for (String period : periods) {
            IProcessDataReader processDataReaders = inputStream.getProcessDataReaders(period);
            ArrayList<UploadStateNew> uploadStateNewList = new ArrayList<UploadStateNew>();
            ArrayList<UploadRecordNew> uploadRecordNewList = new ArrayList<UploadRecordNew>();
            HashMap<String, IProcessIOInstance> processIOInstanceMap = new HashMap<String, IProcessIOInstance>();
            FormSchemeDefine formScheme = this.queryTableModel.getFormScheme(taskCode, period);
            WorkFlowType workFlowType = this.workflow.queryStartType(formScheme.getKey());
            boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formScheme.getKey());
            ProcessIOInstance processIOInstance = new ProcessIOInstance();
            HashMap<String, String> sourceNodeIdToCodeMap = new HashMap<String, String>();
            while ((processIOInstance = processDataReaders.readNextInstance()) != null) {
                uploadStateNewList.add(this.buildUploadState((IProcessIOInstance)processIOInstance, workFlowType, defaultWorkflow, sourceNodeIdToCodeMap));
                processIOInstanceMap.put(processIOInstance.getId(), (IProcessIOInstance)processIOInstance);
            }
            ProcessIOOperation processIOOperation = new ProcessIOOperation();
            while ((processIOOperation = processDataReaders.readNextOperation()) != null) {
                uploadRecordNewList.add(this.buildHistoryStateData((IProcessIOOperation)processIOOperation, processIOInstanceMap, workFlowType));
            }
            this.queryTableModel.batchInsertOrUpdateProcessData(formScheme, uploadStateNewList, processDataImportResult, sourceNodeIdToCodeMap, targetNodeIdToCodeMap, defaultWorkflow);
            this.queryTableModel.batchInsertHisProcessData(formScheme, uploadRecordNewList, sourceNodeIdToCodeMap, targetNodeIdToCodeMap, defaultWorkflow);
            monitor.stepIn();
        }
        monitor.finishTask("\u6d41\u7a0b\u6570\u636e\u5bfc\u5165");
        return processDataImportResult;
    }

    private UploadStateNew buildUploadState(IProcessIOInstance processIOInstance, WorkFlowType workFlowType, boolean defaultWorkflow, Map<String, String> sourceNodeIdToCodeMap) {
        UploadStateNew uploadStateNew = new UploadStateNew();
        uploadStateNew.setTaskId(processIOInstance.getCurrentUserTask());
        String status = processIOInstance.getStatus();
        uploadStateNew.setPreEvent(this.stateCovertAction(status, defaultWorkflow));
        IBusinessObject businessObject = processIOInstance.getBusinessObject();
        if (WorkFlowType.ENTITY.equals((Object)workFlowType)) {
            DimensionObject dimensionObject = (DimensionObject)businessObject;
            DimensionCombination dimensions = dimensionObject.getDimensions();
            uploadStateNew.setEntities(dimensions.toDimensionValueSet());
        } else if (WorkFlowType.FORM.equals((Object)workFlowType)) {
            FormObject formObject = (FormObject)businessObject;
            DimensionCombination dimensions = formObject.getDimensions();
            String formKey = formObject.getFormKey();
            uploadStateNew.setEntities(dimensions.toDimensionValueSet());
            uploadStateNew.setFormId(formKey);
        } else if (WorkFlowType.GROUP.equals((Object)workFlowType)) {
            FormGroupObject formGroupObject = (FormGroupObject)businessObject;
            DimensionCombination dimensions = formGroupObject.getDimensions();
            String formGroupKey = formGroupObject.getFormGroupKey();
            uploadStateNew.setEntities(dimensions.toDimensionValueSet());
            uploadStateNew.setFormId(formGroupKey);
        }
        sourceNodeIdToCodeMap.put(processIOInstance.getCurrentUserTask(), processIOInstance.getCurrentUserTaskCode());
        return uploadStateNew;
    }

    private UploadRecordNew buildHistoryStateData(IProcessIOOperation processIOOperation, Map<String, IProcessIOInstance> processIOInstanceMap, WorkFlowType workFlowType) {
        DimensionCombination dimensions;
        UploadRecordNew uploadRecordNew = new UploadRecordNew();
        String instanceId = processIOOperation.getInstanceId();
        IProcessIOInstance iProcessIOInstance = processIOInstanceMap.get(instanceId);
        IBusinessObject businessObject = iProcessIOInstance.getBusinessObject();
        if (WorkFlowType.ENTITY.equals((Object)workFlowType)) {
            DimensionObject dimensionObject = (DimensionObject)businessObject;
            dimensions = dimensionObject.getDimensions();
            uploadRecordNew.setEntities(dimensions.toDimensionValueSet());
        } else if (WorkFlowType.FORM.equals((Object)workFlowType)) {
            FormObject formObject = (FormObject)businessObject;
            dimensions = formObject.getDimensions();
            String formKey = formObject.getFormKey();
            uploadRecordNew.setEntities(dimensions.toDimensionValueSet());
            uploadRecordNew.setFormKey(formKey);
        } else if (WorkFlowType.GROUP.equals((Object)workFlowType)) {
            FormGroupObject formGroupObject = (FormGroupObject)businessObject;
            dimensions = formGroupObject.getDimensions();
            String formGroupKey = formGroupObject.getFormGroupKey();
            uploadRecordNew.setEntities(dimensions.toDimensionValueSet());
            uploadRecordNew.setFormKey(formGroupKey);
        }
        uploadRecordNew.setAction(processIOOperation.getAction());
        uploadRecordNew.setTaskId(processIOOperation.getSourceUserTask());
        uploadRecordNew.setCmt(processIOOperation.getComment());
        String operator = processIOOperation.getOperator();
        User systemUser = this.getSystemUser();
        if (operator != null && operator.equals(systemUser.getId())) {
            operator = "00000000-0000-0000-0000-000000000000";
        }
        uploadRecordNew.setOperator(operator);
        Calendar operateTime = processIOOperation.getOperateTime();
        Date time = operateTime.getTime();
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = formatter.format(time);
            uploadRecordNew.setTime(format);
        }
        catch (Exception e) {
            throw new RuntimeException("\u65f6\u95f4\u683c\u5f0f\u8f6c\u6362\u62a5\u9519");
        }
        return uploadRecordNew;
    }

    private String stateCovertAction(String status, boolean defaultWorkflow) {
        if (defaultWorkflow) {
            if (UploadState.ORIGINAL_SUBMIT.toString().equals(status) || UploadState.ORIGINAL_UPLOAD.toString().equals(status)) {
                return "start".toString();
            }
            if (UploadState.SUBMITED.toString().equals(status)) {
                return "act_submit".toString();
            }
            if (UploadState.UPLOADED.toString().equals(status)) {
                return "act_upload".toString();
            }
            if (UploadState.RETURNED.toString().equals(status)) {
                return "act_return".toString();
            }
            if (UploadState.REJECTED.toString().equals(status)) {
                return "act_reject".toString();
            }
            if (UploadState.CONFIRMED.toString().equals(status)) {
                return "act_confirm".toString();
            }
        } else {
            if (UploadState.ORIGINAL_SUBMIT.toString().equals(status) || UploadState.ORIGINAL_UPLOAD.toString().equals(status)) {
                return "start".toString();
            }
            if (UploadState.SUBMITED.toString().equals(status)) {
                return "cus_submit".toString();
            }
            if (UploadState.UPLOADED.toString().equals(status)) {
                return "cus_upload".toString();
            }
            if (UploadState.RETURNED.toString().equals(status)) {
                return "cus_return".toString();
            }
            if (UploadState.REJECTED.toString().equals(status)) {
                return "cus_reject".toString();
            }
            if (UploadState.CONFIRMED.toString().equals(status)) {
                return "cus_confirm".toString();
            }
        }
        return "start".toString();
    }

    private Map<String, String> getNodes(String taskCode) {
        String workflowId;
        WorkFlowDefine workFlowDefine;
        WorkflowSettingDefine workflowSettingDefine;
        FormSchemeDefine formSchemeDefine;
        boolean defaultWorkflow;
        List formSchemeDefines;
        HashMap<String, String> nodeMap = new HashMap<String, String>();
        TaskDefine taskDefine = this.runTimeViewController.getTaskByCode(taskCode);
        if (taskDefine != null && (formSchemeDefines = this.runTimeViewController.listFormSchemeByTask(taskDefine.getKey())) != null && formSchemeDefines.size() > 0 && !(defaultWorkflow = this.workflow.isDefaultWorkflow((formSchemeDefine = (FormSchemeDefine)formSchemeDefines.get(0)).getKey())) && formSchemeDefines != null && formSchemeDefines.size() > 0 && (workflowSettingDefine = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(formSchemeDefine.getKey())) != null && workflowSettingDefine.getId() != null && (workFlowDefine = this.customWorkFolwService.getWorkFlowDefineByID(workflowId = workflowSettingDefine.getWorkflowId(), 1)) != null) {
            List<WorkFlowNodeSet> workFlowNodeSets = this.customWorkFolwService.getWorkFlowNodeSets(workFlowDefine.getLinkid());
            for (WorkFlowNodeSet workFlowNodeSet : workFlowNodeSets) {
                String id = workFlowNodeSet.getId();
                String code = workFlowNodeSet.getCode();
                nodeMap.put(code, id);
            }
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
}

