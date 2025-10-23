/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.IProcessDefinitionService
 *  com.jiuqi.nr.workflow2.engine.core.IProcessEngine
 *  com.jiuqi.nr.workflow2.engine.core.IProcessEngineFactory
 *  com.jiuqi.nr.workflow2.engine.core.IProcessRuntimeService
 *  com.jiuqi.nr.workflow2.engine.core.actor.IActor
 *  com.jiuqi.nr.workflow2.engine.core.event.IActionEventExecutor
 *  com.jiuqi.nr.workflow2.engine.core.event.IActionEventExecutorFactory
 *  com.jiuqi.nr.workflow2.engine.core.event.IActionEventFactory
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionStatus
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventFinishedResult
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventOperateInfo
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.WFMonitorCheckResult
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserActionEvent
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserTask
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBizObjectOperateResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyDependencies
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IOperateResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessInstance
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.JavaBeanUtils
 *  com.jiuqi.nr.workflow2.engine.dflt.process.io.dimension.OperateBusinessKeyCollection
 */
package com.jiuqi.nr.workflow2.service.impl;

import com.jiuqi.nr.workflow2.engine.core.IProcessDefinitionService;
import com.jiuqi.nr.workflow2.engine.core.IProcessEngine;
import com.jiuqi.nr.workflow2.engine.core.IProcessEngineFactory;
import com.jiuqi.nr.workflow2.engine.core.IProcessRuntimeService;
import com.jiuqi.nr.workflow2.engine.core.actor.IActor;
import com.jiuqi.nr.workflow2.engine.core.event.IActionEventExecutor;
import com.jiuqi.nr.workflow2.engine.core.event.IActionEventExecutorFactory;
import com.jiuqi.nr.workflow2.engine.core.event.IActionEventFactory;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionStatus;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventFinishedResult;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventOperateInfo;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.WFMonitorCheckResult;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserActionEvent;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserTask;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBizObjectOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyDependencies;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessInstance;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.JavaBeanUtils;
import com.jiuqi.nr.workflow2.engine.dflt.process.io.dimension.OperateBusinessKeyCollection;
import com.jiuqi.nr.workflow2.service.IProcessExecuteService;
import com.jiuqi.nr.workflow2.service.IProcessQueryService;
import com.jiuqi.nr.workflow2.service.dimension.BusinessKeyDependencies;
import com.jiuqi.nr.workflow2.service.enumeration.IProcessActionExecuteAttrKeys;
import com.jiuqi.nr.workflow2.service.enumeration.ProcessExecuteStatus;
import com.jiuqi.nr.workflow2.service.execute.events.BatchCompleteProcessTaskPosEvent;
import com.jiuqi.nr.workflow2.service.execute.events.BatchCompleteProcessTaskPreEvent;
import com.jiuqi.nr.workflow2.service.execute.events.CompleteProcessTaskPosEvent;
import com.jiuqi.nr.workflow2.service.execute.events.CompleteProcessTaskPreEvent;
import com.jiuqi.nr.workflow2.service.execute.runtime.EventFinishedResult;
import com.jiuqi.nr.workflow2.service.execute.runtime.EventOperateColumn;
import com.jiuqi.nr.workflow2.service.execute.runtime.EventOperateInfo;
import com.jiuqi.nr.workflow2.service.execute.runtime.IEventOperateColumn;
import com.jiuqi.nr.workflow2.service.execute.runtime.IEventOperateResult;
import com.jiuqi.nr.workflow2.service.para.IProcessExecutePara;
import com.jiuqi.nr.workflow2.service.para.IProcessRunPara;
import com.jiuqi.nr.workflow2.service.para.ProcessExecuteEnv;
import com.jiuqi.nr.workflow2.service.result.IProcessExecuteResult;
import com.jiuqi.nr.workflow2.service.result.ProcessExecuteResult;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

public class ProcessExecuteService
implements IProcessExecuteService {
    @Autowired
    private IActionEventFactory actionEventFactory;
    @Autowired
    protected WorkflowSettingsService settingService;
    @Autowired
    protected IProcessQueryService processQueryService;
    @Autowired
    protected IProcessEngineFactory processEngineFactory;
    @Autowired
    protected ApplicationEventPublisher applicationEventPublisher;

    @Override
    public IProcessExecuteResult executeProcess(IProcessExecutePara executePara, IBusinessKey businessKey, IProcessAsyncMonitor monitor, IEventOperateResult resultManager) throws Exception {
        IProcessInstance instance = this.processQueryService.queryInstances((IProcessRunPara)executePara, businessKey);
        if (instance == null) {
            return new ProcessExecuteResult(ProcessExecuteStatus.ENV_CHECK_ERROR, "\u6d41\u7a0b\u5b9e\u4f8b\u4e0d\u5b58\u5728\uff0c\u6267\u884c\u5931\u8d25\uff01");
        }
        WorkflowSettingsDO flowSettings = this.settingService.queryWorkflowSettings(executePara.getTaskKey());
        IProcessEngine processEngine = this.processEngineFactory.getProcessEngine(flowSettings.getWorkflowEngine());
        IProcessDefinitionService processDefinitionService = processEngine.getProcessDefinitionService();
        IUserTask userTask = processDefinitionService.queryUserTask(flowSettings.getWorkflowDefine(), instance.getCurrentUserTask());
        if (userTask == null) {
            return new ProcessExecuteResult(ProcessExecuteStatus.ENV_CHECK_ERROR, "\u65e0\u6548\u7684\u6d41\u7a0b\u8282\u70b9\uff0c\u6267\u884c\u5931\u8d25\uff01");
        }
        IUserAction userAction = processDefinitionService.queryAction(flowSettings.getWorkflowDefine(), instance.getCurrentUserTask(), executePara.getActionCode());
        if (userAction == null) {
            return new ProcessExecuteResult(ProcessExecuteStatus.ENV_CHECK_ERROR, "\u8282\u70b9\u52a8\u4f5c\u4e0d\u5b58\u5728\uff0c\u6267\u884c\u5931\u8d25\uff01");
        }
        this.putActionArgs(executePara, userAction);
        this.applicationEventPublisher.publishEvent(new CompleteProcessTaskPreEvent(executePara, businessKey));
        IEventFinishedResult executionStatus = this.singlePreEventExecute(executePara, businessKey, monitor, resultManager, processEngine, flowSettings, userTask, userAction);
        if (EventExecutionStatus.STOP == executionStatus.getFinishStatus()) {
            return new ProcessExecuteResult(ProcessExecuteStatus.PRE_EVENT_CHECK_ERROR, userAction);
        }
        BusinessKeyDependencies businessKeyDependencies = new BusinessKeyDependencies(businessKey.getTask(), resultManager);
        if (businessKeyDependencies.size() == 1) {
            this.doCompleteTask(executePara, businessKey, monitor, resultManager, processEngine, flowSettings);
        } else {
            this.doCompleteTask(executePara, (IBusinessKeyCollection)businessKeyDependencies, monitor, resultManager, processEngine, flowSettings, userTask, userAction, businessKeyDependencies);
        }
        this.singlePosEventExecute(executePara, businessKey, monitor, resultManager, processEngine, flowSettings, userTask, userAction);
        this.applicationEventPublisher.publishEvent(new CompleteProcessTaskPosEvent(executePara, businessKey));
        return new ProcessExecuteResult(ProcessExecuteStatus.SUCCESS, userAction);
    }

    @Override
    public IProcessExecuteResult executeProcess(IProcessExecutePara executePara, IBusinessKeyCollection businessKeyCollection, IProcessAsyncMonitor monitor, IEventOperateResult resultManager) throws Exception {
        WorkflowSettingsDO flowSettings = this.settingService.queryWorkflowSettings(executePara.getTaskKey());
        IProcessEngine processEngine = this.processEngineFactory.getProcessEngine(flowSettings.getWorkflowEngine());
        IProcessDefinitionService processDefinitionService = processEngine.getProcessDefinitionService();
        IUserTask userTask = processDefinitionService.queryUserTask(flowSettings.getWorkflowDefine(), executePara.getUserTaskCode());
        if (userTask == null) {
            return new ProcessExecuteResult(ProcessExecuteStatus.ENV_CHECK_ERROR, "\u65e0\u6548\u7684\u6d41\u7a0b\u8282\u70b9\uff0c\u6267\u884c\u5931\u8d25\uff01");
        }
        IUserAction userAction = processDefinitionService.queryAction(flowSettings.getWorkflowDefine(), userTask.getCode(), executePara.getActionCode());
        if (userAction == null) {
            return new ProcessExecuteResult(ProcessExecuteStatus.ENV_CHECK_ERROR, "\u8282\u70b9\u52a8\u4f5c\u4e0d\u5b58\u5728\uff0c\u6267\u884c\u5931\u8d25\uff01");
        }
        this.putActionArgs(executePara, userAction);
        this.applicationEventPublisher.publishEvent(new BatchCompleteProcessTaskPreEvent(executePara, businessKeyCollection));
        IEventFinishedResult executionStatus = this.multiPreEventExecutor(executePara, businessKeyCollection, monitor, resultManager, processEngine, flowSettings, userTask, userAction);
        if (EventExecutionStatus.STOP == executionStatus.getFinishStatus()) {
            return new ProcessExecuteResult(ProcessExecuteStatus.PRE_EVENT_CHECK_ERROR, userAction);
        }
        BusinessKeyDependencies businessKeyDependencies = new BusinessKeyDependencies(businessKeyCollection.getTask(), resultManager);
        this.doCompleteTask(executePara, (IBusinessKeyCollection)businessKeyDependencies, monitor, resultManager, processEngine, flowSettings, userTask, userAction, businessKeyDependencies);
        this.multiPosEventExecutor(executePara, businessKeyCollection, monitor, resultManager, processEngine, flowSettings, userTask, userAction);
        this.applicationEventPublisher.publishEvent(new BatchCompleteProcessTaskPosEvent(executePara, businessKeyCollection));
        return new ProcessExecuteResult(ProcessExecuteStatus.SUCCESS, userAction);
    }

    protected IEventFinishedResult singlePreEventExecute(IProcessExecutePara executePara, IBusinessKey businessKey, IProcessAsyncMonitor monitor, IEventOperateResult resultManager, IProcessEngine processEngine, WorkflowSettingsDO flowSettings, IUserTask userTask, IUserAction userAction) throws Exception {
        IProcessDefinitionService processDefinitionService = processEngine.getProcessDefinitionService();
        List previousEvents = processDefinitionService.queryAllPreviousEvents(flowSettings.getWorkflowDefine(), userTask.getCode(), userAction.getCode());
        return this.singleExecutor(previousEvents, monitor, resultManager, executePara, businessKey);
    }

    protected IEventFinishedResult singlePosEventExecute(IProcessExecutePara executePara, IBusinessKey businessKey, IProcessAsyncMonitor monitor, IEventOperateResult resultManager, IProcessEngine processEngine, WorkflowSettingsDO flowSettings, IUserTask userTask, IUserAction userAction) throws Exception {
        List<IBusinessObject> businessObjects = resultManager.allCheckPassBusinessObjects();
        if (businessObjects.isEmpty() || !businessObjects.contains(businessKey.getBusinessObject())) {
            return new EventFinishedResult(EventExecutionStatus.FINISH);
        }
        IProcessDefinitionService processDefinitionService = processEngine.getProcessDefinitionService();
        List postEvents = processDefinitionService.queryAllPostEvents(flowSettings.getWorkflowDefine(), userTask.getCode(), userAction.getCode());
        return this.singleExecutor(postEvents, monitor, resultManager, executePara, businessKey);
    }

    protected void doCompleteTask(IProcessExecutePara executePara, IBusinessKey businessKey, IProcessAsyncMonitor monitor, IEventOperateResult resultManager, IProcessEngine processEngine, WorkflowSettingsDO flowSettings) {
        IProcessRuntimeService processRuntimeService = processEngine.getProcessRuntimeService();
        processRuntimeService.completeTask(businessKey, executePara.getTaskId(), executePara.getActionCode(), IActor.fromContext(), (IActionArgs)executePara.getCustomVariable());
    }

    protected IEventFinishedResult multiPreEventExecutor(IProcessExecutePara executePara, IBusinessKeyCollection businessKeyCollection, IProcessAsyncMonitor monitor, IEventOperateResult resultManager, IProcessEngine processEngine, WorkflowSettingsDO flowSettings, IUserTask userTask, IUserAction userAction) throws Exception {
        IProcessDefinitionService processDefinitionService = processEngine.getProcessDefinitionService();
        List previousEvents = processDefinitionService.queryAllPreviousEvents(flowSettings.getWorkflowDefine(), userTask.getCode(), userAction.getCode());
        if (!previousEvents.isEmpty()) {
            IProcessRuntimeService processRuntimeService = processEngine.getProcessRuntimeService();
            IBizObjectOperateResult canCompleteInstanceResult = processRuntimeService.queryCanCompleteInstance(businessKeyCollection, userTask.getCode(), userAction.getCode(), IActor.fromContext(), (IActionArgs)executePara.getCustomVariable());
            this.setOperateResult((IBizObjectOperateResult<Void>)canCompleteInstanceResult, resultManager.getOperateResultSet(IEventOperateColumn.DEF_OPT_COLUMN));
        }
        return this.multiExecutor(previousEvents, monitor, resultManager, executePara, businessKeyCollection);
    }

    protected IEventFinishedResult multiPosEventExecutor(IProcessExecutePara executePara, IBusinessKeyCollection businessKeyCollection, IProcessAsyncMonitor monitor, IEventOperateResult resultManager, IProcessEngine processEngine, WorkflowSettingsDO flowSettings, IUserTask userTask, IUserAction userAction) throws Exception {
        OperateBusinessKeyCollection passBusinessObjects = new OperateBusinessKeyCollection(businessKeyCollection.getTask(), resultManager.allCheckPassBusinessObjects());
        if (passBusinessObjects.getBusinessObjects().size() > 0) {
            IProcessDefinitionService processDefinitionService = processEngine.getProcessDefinitionService();
            List postEvents = processDefinitionService.queryAllPostEvents(flowSettings.getWorkflowDefine(), userTask.getCode(), executePara.getActionCode());
            return this.multiExecutor(postEvents, monitor, resultManager, executePara, (IBusinessKeyCollection)passBusinessObjects);
        }
        return new EventFinishedResult(EventExecutionStatus.FINISH);
    }

    protected void doCompleteTask(IProcessExecutePara executePara, IBusinessKeyCollection businessKeyCollection, IProcessAsyncMonitor monitor, IEventOperateResult resultManager, IProcessEngine processEngine, WorkflowSettingsDO flowSettings, IUserTask userTask, IUserAction userAction, IBusinessKeyDependencies dependencies) {
        IProcessRuntimeService processRuntimeService = processEngine.getProcessRuntimeService();
        IBizObjectOperateResult bizObjectOperateResult = processRuntimeService.batchCompleteTask(businessKeyCollection, userTask.getCode(), userAction.getCode(), IActor.fromContext(), (IActionArgs)executePara.getCustomVariable(), dependencies);
        this.setOperateResult((IBizObjectOperateResult<Void>)bizObjectOperateResult, resultManager.getOperateResultSet(IEventOperateColumn.DEF_OPT_COLUMN));
    }

    protected IEventFinishedResult singleExecutor(List<IUserActionEvent> actionEvents, IProcessAsyncMonitor monitor, IEventOperateResult resultManager, IProcessExecutePara executePara, IBusinessKey businessKey) throws Exception {
        for (IUserActionEvent event : actionEvents) {
            IActionEventExecutorFactory executorFactory = this.actionEventFactory.queryActionEventExecutorFactory(event.getDefinitionId());
            IActionEventExecutor actionEventExecutor = executorFactory.createActionEventExecutor(event.getSettings());
            EventOperateColumn eventOperateColumn = new EventOperateColumn(event);
            IOperateResultSet operateResultSet = resultManager.getOperateResultSet(eventOperateColumn);
            IEventFinishedResult eventFinishedResult = actionEventExecutor.executionEvent(monitor, operateResultSet, (IActionArgs)executePara.getCustomVariable(), businessKey);
            eventOperateColumn.setAffectStatus(eventFinishedResult.getAffectStatus());
            eventOperateColumn.setCompleteDependentType(eventFinishedResult.getCompleteDependentType());
            if (EventExecutionStatus.STOP != eventFinishedResult.getFinishStatus()) continue;
            return eventFinishedResult;
        }
        return new EventFinishedResult(EventExecutionStatus.FINISH);
    }

    protected IEventFinishedResult multiExecutor(List<IUserActionEvent> actionEvents, IProcessAsyncMonitor monitor, IEventOperateResult resultManager, IProcessExecutePara executePara, IBusinessKeyCollection businessKeyCollection) throws Exception {
        for (IUserActionEvent event : actionEvents) {
            if ((businessKeyCollection = new OperateBusinessKeyCollection(businessKeyCollection.getTask(), resultManager.allCheckPassBusinessObjects())).getBusinessObjects().size() == 0) {
                return new EventFinishedResult(EventExecutionStatus.FINISH);
            }
            IActionEventExecutorFactory executorFactory = this.actionEventFactory.queryActionEventExecutorFactory(event.getDefinitionId());
            IActionEventExecutor actionEventExecutor = executorFactory.createActionEventExecutor(event.getSettings());
            EventOperateColumn eventOperateColumn = new EventOperateColumn(event);
            IOperateResultSet operateResultSet = resultManager.getOperateResultSet(eventOperateColumn);
            IEventFinishedResult eventFinishedResult = actionEventExecutor.executionEvent(monitor, operateResultSet, (IActionArgs)executePara.getCustomVariable(), businessKeyCollection);
            eventOperateColumn.setAffectStatus(eventFinishedResult.getAffectStatus());
            eventOperateColumn.setCompleteDependentType(eventFinishedResult.getCompleteDependentType());
            if (EventExecutionStatus.STOP != eventFinishedResult.getFinishStatus()) continue;
            return eventFinishedResult;
        }
        return new EventFinishedResult(EventExecutionStatus.FINISH);
    }

    private void setOperateResult(IBizObjectOperateResult<Void> bizObjectOperateResult, IOperateResultSet operateResultSet) {
        Iterable businessObjects = bizObjectOperateResult.getBusinessObjects();
        for (IBusinessObject businessObject : businessObjects) {
            IOperateResult result = bizObjectOperateResult.getResult((Object)businessObject);
            if (!result.isSuccessful()) {
                operateResultSet.setOperateResult(businessObject, (IEventOperateInfo)new EventOperateInfo(WFMonitorCheckResult.CHECK_UN_PASS, result.getError().getErrorCode().toString(), result.getError().getErrorData()));
                continue;
            }
            operateResultSet.setOperateResult(businessObject, (IEventOperateInfo)new EventOperateInfo(WFMonitorCheckResult.CHECK_PASS));
        }
    }

    private void putActionArgs(IProcessExecutePara executePara, IUserAction userAction) {
        ProcessExecuteEnv processExecuteEnv = new ProcessExecuteEnv();
        processExecuteEnv.setTaskKey(executePara.getTaskKey());
        processExecuteEnv.setPeriod(executePara.getPeriod());
        processExecuteEnv.setActionCode(executePara.getActionCode());
        processExecuteEnv.setUserTaskCode(executePara.getUserTaskCode());
        processExecuteEnv.setActionTitle(userAction.getAlias());
        processExecuteEnv.setEnvVariables(executePara.getCustomVariable().getValue());
        executePara.getCustomVariable().getValue().put(IProcessActionExecuteAttrKeys.process_action_execute.attrKey, (Object)JavaBeanUtils.toJSONStr((Object)processExecuteEnv));
    }
}

