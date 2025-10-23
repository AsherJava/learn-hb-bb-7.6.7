/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.IProcessDefinitionService
 *  com.jiuqi.nr.workflow2.engine.core.IProcessEngine
 *  com.jiuqi.nr.workflow2.engine.core.IProcessEngineFactory
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBizObjectOperateResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IOperateResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessInstance
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessOperation
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessStatusWithOperation
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessTask
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormObject
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.dflt.process.runtime.ProcessTask
 *  com.jiuqi.nr.workflow2.service.IProcessQueryService
 *  com.jiuqi.nr.workflow2.service.enumeration.IProcessFormRejectAttrKeys
 *  com.jiuqi.nr.workflow2.service.para.IProcessRunPara
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.workflow2.form.reject.ext.service;

import com.jiuqi.nr.workflow2.engine.core.IProcessDefinitionService;
import com.jiuqi.nr.workflow2.engine.core.IProcessEngine;
import com.jiuqi.nr.workflow2.engine.core.IProcessEngineFactory;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBizObjectOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessInstance;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessOperation;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessStatusWithOperation;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessTask;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormObject;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.ProcessTask;
import com.jiuqi.nr.workflow2.form.reject.ext.engine.FormRejectProcessTask;
import com.jiuqi.nr.workflow2.form.reject.ext.engine.FormRejectUserAction;
import com.jiuqi.nr.workflow2.form.reject.ext.engine.RejectFormStateQueryResult;
import com.jiuqi.nr.workflow2.form.reject.ext.service.FormRejectExecuteParam;
import com.jiuqi.nr.workflow2.form.reject.ext.service.IFormRejectJudgeHelper;
import com.jiuqi.nr.workflow2.form.reject.service.IFormRejectQueryService;
import com.jiuqi.nr.workflow2.service.IProcessQueryService;
import com.jiuqi.nr.workflow2.service.enumeration.IProcessFormRejectAttrKeys;
import com.jiuqi.nr.workflow2.service.para.IProcessRunPara;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;

public class FormRejectExtQueryService
implements IProcessQueryService {
    @Resource(name="com.jiuqi.nr.workflow2.service.impl.ProcessQueryService")
    private IProcessQueryService processQueryService;
    @Autowired
    protected WorkflowSettingsService settingService;
    @Autowired
    protected IProcessEngineFactory processEngineFactory;
    @Autowired
    protected IFormRejectQueryService formRejectQueryService;
    @Autowired
    protected IFormRejectJudgeHelper judgeHelper;

    public IProcessInstance queryInstances(IProcessRunPara runEnvPara, IBusinessKey businessKey) {
        return this.processQueryService.queryInstances(runEnvPara, businessKey);
    }

    public IBizObjectOperateResult<IProcessInstance> queryInstances(IProcessRunPara runEnvPara, IBusinessKeyCollection businessKeyCollection) {
        return this.processQueryService.queryInstances(runEnvPara, businessKeyCollection);
    }

    public IProcessStatus queryInstanceState(IProcessRunPara runEnvPara, IBusinessKey businessKey) {
        if (this.judgeHelper.useFormRejectQuery(runEnvPara) && this.judgeHelper.isInstanceAtReportAndReject(runEnvPara, businessKey)) {
            RejectFormStateQueryResult stateQueryResult = new RejectFormStateQueryResult(this.formRejectQueryService.queryAllFormRecordsInUnit(runEnvPara.getTaskKey(), runEnvPara.getPeriod(), businessKey.getBusinessObject().getDimensions()));
            FormRejectExecuteParam rejectExecuteParam = IFormRejectJudgeHelper.getFormRejectExecuteParam(runEnvPara.getCustomVariable());
            IOperateResult result = stateQueryResult.getResult(new FormObject(businessKey.getBusinessObject().getDimensions(), rejectExecuteParam.getFormId()));
            return (IProcessStatus)result.getResult();
        }
        return this.processQueryService.queryInstanceState(runEnvPara, businessKey);
    }

    public IBizObjectOperateResult<IProcessStatus> queryInstanceState(IProcessRunPara runEnvPara, IBusinessKeyCollection businessKeyCollection) {
        return this.processQueryService.queryInstanceState(runEnvPara, businessKeyCollection);
    }

    public IProcessStatusWithOperation queryInstanceStateWithOperation(IProcessRunPara runEnvPara, IBusinessKey businessKey) {
        return this.processQueryService.queryInstanceStateWithOperation(runEnvPara, businessKey);
    }

    public IProcessStatus queryUnitState(IProcessRunPara runEnvPara, IBusinessKey businessKey) {
        return this.processQueryService.queryUnitState(runEnvPara, businessKey);
    }

    public IBizObjectOperateResult<IProcessStatusWithOperation> queryInstanceStateWithOperation(IProcessRunPara runEnvPara, IBusinessKeyCollection businessKeyCollection) {
        return this.processQueryService.queryInstanceStateWithOperation(runEnvPara, businessKeyCollection);
    }

    public IBizObjectOperateResult<IProcessStatus> queryUnitState(IProcessRunPara runEnvPara, IBusinessKeyCollection businessKeyCollection) {
        return this.processQueryService.queryUnitState(runEnvPara, businessKeyCollection);
    }

    public List<IProcessOperation> queryProcessOperations(IProcessRunPara runEnvPara, IBusinessKey businessKey) {
        List processOperations = this.processQueryService.queryProcessOperations(runEnvPara, businessKey);
        if (this.judgeHelper.useFormRejectQuery(runEnvPara) && this.judgeHelper.isFormRejectMode(runEnvPara)) {
            processOperations.removeIf(optRecord -> "tsk_audit".equals(optRecord.getFromNode()) && "act_reject".equals(optRecord.getAction()));
        }
        return processOperations;
    }

    public List<String> queryMatchingActors(IProcessRunPara runEnvPara, IBusinessKey businessKey) {
        return this.processQueryService.queryMatchingActors(runEnvPara, businessKey);
    }

    public List<IProcessTask> queryCurrentTask(IProcessRunPara runEnvPara, IBusinessKey businessKey) {
        if (this.judgeHelper.isInstanceAtAuditAndUpload(runEnvPara, businessKey) && !this.formRejectQueryService.isRejectAllFormsInUnit(runEnvPara.getTaskKey(), runEnvPara.getPeriod(), businessKey.getBusinessObject().getDimensions())) {
            List processTasks = this.processQueryService.queryCurrentTask(runEnvPara, businessKey);
            return this.formRejectProcessTasksByAuditNode(processTasks);
        }
        if (this.judgeHelper.isInstanceAtReportAndReject(runEnvPara, businessKey) && !this.formRejectQueryService.isRejectAllFormsInUnit(runEnvPara.getTaskKey(), runEnvPara.getPeriod(), businessKey.getBusinessObject().getDimensions())) {
            List processTasks = this.processQueryService.queryCurrentTask(runEnvPara, businessKey);
            return this.formRejectProcessTasksByUploadNode(runEnvPara, processTasks, businessKey);
        }
        return this.processQueryService.queryCurrentTask(runEnvPara, businessKey);
    }

    private List<IProcessTask> formRejectProcessTasksByUploadNode(IProcessRunPara runEnvPara, List<IProcessTask> processTasks, IBusinessKey businessKey) {
        ArrayList<IProcessTask> result = new ArrayList<IProcessTask>(processTasks);
        WorkflowSettingsDO flowSettings = this.settingService.queryWorkflowSettings(businessKey.getTask());
        IProcessEngine processEngine = this.processEngineFactory.getProcessEngine(flowSettings.getWorkflowEngine());
        IProcessDefinitionService processDefinitionService = processEngine.getProcessDefinitionService();
        IUserAction userRejectAction = processDefinitionService.queryAction(flowSettings.getWorkflowDefine(), "tsk_audit", "act_reject");
        FormRejectUserAction formRejectUserAction = new FormRejectUserAction(userRejectAction);
        formRejectUserAction.getProperties().put(IProcessFormRejectAttrKeys.is_form_reject_button.attrKey, true);
        FormRejectProcessTask formRejectProcessTask = new FormRejectProcessTask();
        formRejectProcessTask.appendAction(formRejectUserAction);
        result.add(formRejectProcessTask);
        return result;
    }

    private List<IProcessTask> formRejectProcessTasksByAuditNode(List<IProcessTask> processTasks) {
        ArrayList<IProcessTask> tasks = new ArrayList<IProcessTask>();
        for (IProcessTask task : processTasks) {
            ArrayList<IUserAction> actions = new ArrayList<IUserAction>();
            for (IUserAction userAction : task.getActions()) {
                if ("act_reject".equals(userAction.getCode())) {
                    FormRejectUserAction formRejectUserAction = new FormRejectUserAction(userAction);
                    formRejectUserAction.getProperties().put(IProcessFormRejectAttrKeys.is_form_reject_button.attrKey, true);
                    actions.add(formRejectUserAction);
                    continue;
                }
                actions.add(userAction);
            }
            tasks.add((IProcessTask)new ProcessTask(task.getId(), actions));
        }
        return tasks;
    }
}

