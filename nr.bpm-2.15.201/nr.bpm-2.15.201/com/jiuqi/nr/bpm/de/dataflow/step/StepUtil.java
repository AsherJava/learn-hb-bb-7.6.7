/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 */
package com.jiuqi.nr.bpm.de.dataflow.step;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowAction;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.service.CustomWorkFolwService;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionParam;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.de.dataflow.service.impl.ActionMethod;
import com.jiuqi.nr.bpm.de.dataflow.step.IBatchStepByStepUpload;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchStepByStepParam;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.StepByOptParam;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import com.jiuqi.nr.bpm.upload.utils.ObtainCustomName;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StepUtil {
    private static final Logger logger = LoggerFactory.getLogger(StepUtil.class);
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private CustomWorkFolwService customWorkFolwService;
    @Autowired
    private ActionMethod actionParamMethod;
    @Autowired
    private IWorkflow workflow;
    @Autowired
    NrParameterUtils nrParameterUtils;
    @Autowired
    ObtainCustomName obtainCustomName;
    @Autowired
    private WorkflowSettingService workflowSettingService;
    @Autowired(required=false)
    private IBatchStepByStepUpload stepByStepUpload;

    public FormSchemeDefine getFormSchemeDefine(String formSchemeKey) {
        try {
            return this.runTimeViewController.getFormScheme(formSchemeKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public WorkFlowType uploadType(String formSchemeKey) {
        return this.workflow.queryStartType(formSchemeKey);
    }

    public boolean isUploadEntity(WorkFlowType uploadType) {
        return WorkFlowType.ENTITY.equals((Object)uploadType);
    }

    public boolean isUploadForm(WorkFlowType uploadType) {
        return WorkFlowType.FORM.equals((Object)uploadType);
    }

    public boolean isUploadGroup(WorkFlowType uploadType) {
        return WorkFlowType.GROUP.equals((Object)uploadType);
    }

    public boolean isDfaultWorkflow(String fromSchemeKey) {
        return this.workflow.isDefaultWorkflow(fromSchemeKey);
    }

    public boolean isPartResource() {
        return true;
    }

    public boolean bindProcess(String formSchemeKey, DimensionValueSet dimension, String formOrGroupKey) {
        if (formOrGroupKey == null) {
            formOrGroupKey = this.defaultFormId(formSchemeKey);
        }
        return this.workflow.bindProcess(formSchemeKey, dimension, formOrGroupKey);
    }

    public boolean bindProcess(String formSchemeKey, DimensionValueSet dimension) {
        return this.workflow.bindProcess(formSchemeKey, dimension, null);
    }

    public String defaultFormId(String formSchemeKey) {
        return this.nrParameterUtils.getDefaultFormId(formSchemeKey);
    }

    public WorkFlowType startType(String formSchemeKey) {
        return this.workflow.queryStartType(formSchemeKey);
    }

    public boolean stepByStepUpload(String formSchemeKey, String nodeId, String actionId, StepByOptParam stepByOptParam) {
        boolean stepByStepReport = false;
        if (this.stepByStepUpload != null) {
            stepByStepReport = this.stepByStepUpload.isUpload(stepByOptParam);
        } else if (this.isDfaultWorkflow(formSchemeKey)) {
            if ("act_upload".equals(actionId)) {
                stepByStepReport = this.taskFlowsDefine(formSchemeKey).getStepByStepReport();
            }
        } else {
            stepByStepReport = this.getActionParam(formSchemeKey, nodeId, actionId).isStepByStepReport();
        }
        return stepByStepReport;
    }

    public boolean stepByStepBack(String formSchemeKey, String nodeId, String actionId, StepByOptParam stepByOptParam) {
        boolean stepByStepBack = false;
        if (this.stepByStepUpload != null) {
            stepByStepBack = this.stepByStepUpload.isUpload(stepByOptParam);
        } else if (this.isDfaultWorkflow(formSchemeKey)) {
            if ("act_reject".equals(actionId)) {
                stepByStepBack = this.taskFlowsDefine(formSchemeKey).getStepByStepBack();
            }
        } else {
            stepByStepBack = this.getActionParam(formSchemeKey, nodeId, actionId).isStepByStepBack();
        }
        return stepByStepBack;
    }

    public boolean stepByStepBackAll(String formSchemeKey, String nodeId, String actionId, StepByOptParam stepByOptParam) {
        boolean stepByStepBackAll = false;
        if (this.stepByStepUpload != null) {
            stepByStepBackAll = this.stepByStepUpload.isUploadAll(stepByOptParam);
        } else if (this.isDfaultWorkflow(formSchemeKey)) {
            if ("act_reject".equals(actionId)) {
                stepByStepBackAll = this.taskFlowsDefine(formSchemeKey).getGoBackAllSup();
            }
        } else {
            stepByStepBackAll = this.getActionParam(formSchemeKey, nodeId, actionId).isStepByStepBackAll();
        }
        return stepByStepBackAll;
    }

    public boolean stepByStepUpload(String formSchemeKey, String nodeId, String actionId, BatchStepByStepParam batchStepByStepParam) {
        boolean stepByStepReport = false;
        if (this.stepByStepUpload != null) {
            stepByStepReport = this.stepByStepUpload.isBatchUpload(batchStepByStepParam);
        } else if (this.isDfaultWorkflow(formSchemeKey)) {
            if ("act_upload".equals(actionId)) {
                stepByStepReport = this.taskFlowsDefine(formSchemeKey).getStepByStepReport();
            }
        } else {
            stepByStepReport = this.getActionParam(formSchemeKey, nodeId, actionId).isStepByStepReport();
        }
        return stepByStepReport;
    }

    public boolean stepByStepBack(String formSchemeKey, String nodeId, String actionId, BatchStepByStepParam batchStepByStepParam) {
        boolean stepByStepBack = false;
        if (this.stepByStepUpload != null) {
            stepByStepBack = this.stepByStepUpload.isBatchUpload(batchStepByStepParam);
        } else if (this.isDfaultWorkflow(formSchemeKey)) {
            if ("act_reject".equals(actionId)) {
                stepByStepBack = this.taskFlowsDefine(formSchemeKey).getStepByStepBack();
            }
        } else {
            stepByStepBack = this.getActionParam(formSchemeKey, nodeId, actionId).isStepByStepBack();
        }
        return stepByStepBack;
    }

    public boolean stepByStepBackAll(String formSchemeKey, String nodeId, String actionId, BatchStepByStepParam batchStepByStepParam) {
        boolean stepByStepBack = false;
        if (this.stepByStepUpload != null) {
            stepByStepBack = this.stepByStepUpload.isBatchUploadAll(batchStepByStepParam);
        } else if (this.isDfaultWorkflow(formSchemeKey)) {
            if ("act_reject".equals(actionId)) {
                stepByStepBack = this.taskFlowsDefine(formSchemeKey).getGoBackAllSup();
            }
        } else {
            stepByStepBack = this.getActionParam(formSchemeKey, nodeId, actionId).isStepByStepBackAll();
        }
        return stepByStepBack;
    }

    public TaskFlowsDefine taskFlowsDefine(String formSchemeKey) {
        FormSchemeDefine formScheme = this.getFormSchemeDefine(formSchemeKey);
        TaskFlowsDefine flowsSetting = formScheme.getFlowsSetting();
        return flowsSetting;
    }

    public ActionParam getActionParam(String formSchemeKey, String nodeId, String actionId) {
        WorkflowSettingDefine workflowDefineByFormSchemeKey = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(formSchemeKey);
        WorkFlowDefine workFlowDefineByID = this.customWorkFolwService.getWorkFlowDefineByID(workflowDefineByFormSchemeKey.getWorkflowId(), 1);
        WorkFlowAction actionInfo = this.customWorkFolwService.getWorkflowActionByCode(nodeId, actionId, workFlowDefineByID.getLinkid());
        ActionParam actionParam = this.actionParamMethod.getCustomWlrkflowParam(formSchemeKey, actionInfo, nodeId);
        return actionParam != null ? actionParam : null;
    }

    public ActionStateBean getStateByActionCode(String taskCodeId, String actionCode, String formSchemeKey) {
        return this.obtainCustomName.getActionStateByActionCode(formSchemeKey, actionCode, taskCodeId);
    }

    public boolean enableForceControl(String formSchemeKey, String nodeId, String actionId) {
        boolean enableForceControl = false;
        if (this.isDfaultWorkflow(formSchemeKey)) {
            if ("act_reject".equals(actionId)) {
                enableForceControl = this.taskFlowsDefine(formSchemeKey).isOpenForceControl();
            }
        } else {
            enableForceControl = this.getActionParam(formSchemeKey, nodeId, actionId).isForceControl();
        }
        return enableForceControl;
    }
}

