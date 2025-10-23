/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.monitor.IProgressMonitor
 *  com.jiuqi.nr.data.access.service.IFormConditionAccessService
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.workflow2.engine.core.actor.IActor
 *  com.jiuqi.nr.workflow2.engine.core.exception.Error
 *  com.jiuqi.nr.workflow2.engine.core.exception.ErrorCode
 *  com.jiuqi.nr.workflow2.engine.core.exception.TaskNotFoundError
 *  com.jiuqi.nr.workflow2.engine.core.exception.TaskNotFoundError$TaskNotFoundErrorData
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserActionPath
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBizObjectOperateResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyDependencies
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectSet
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IOperateResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BizObjectOperateResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.OperateResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime.action;

import com.jiuqi.bi.monitor.IProgressMonitor;
import com.jiuqi.nr.data.access.service.IFormConditionAccessService;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.workflow2.engine.common.definition.model.ProcessDefinition;
import com.jiuqi.nr.workflow2.engine.common.definition.model.UserTask;
import com.jiuqi.nr.workflow2.engine.core.actor.IActor;
import com.jiuqi.nr.workflow2.engine.core.exception.Error;
import com.jiuqi.nr.workflow2.engine.core.exception.ErrorCode;
import com.jiuqi.nr.workflow2.engine.core.exception.TaskNotFoundError;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserActionPath;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBizObjectOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyDependencies;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectSet;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BizObjectOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.OperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.engine.dflt.process.definition.DefaultProcessDefinitionService;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.TransactionUtil;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.action.ActionCollector;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.action.SameOperationResult;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.action.UnitStatusBatchMaintainer;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessInstanceDO;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessInstanceLockManager;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessInstanceQuery;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessOperationDO;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessOperationQuery;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessRepositoryUtil;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.todo.TodoUtils;
import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections4.map.HashedMap;

public class ActionBatchRunner
implements Closeable {
    protected IBusinessKeyCollection businessKeys;
    protected RuntimeBusinessKeyCollection rtBusinessKeys;
    protected String userTaskCode;
    protected String actionCode;
    protected IActor actor;
    protected IBusinessKeyDependencies dependencies;
    protected IActionArgs args;
    protected ProcessInstanceQuery istQuery;
    protected ProcessOperationQuery optQuery;
    protected DefaultProcessDefinitionService processDefinitionService;
    protected ProcessInstanceLockManager instanceLockManager;
    protected TransactionUtil transactionUtil;
    protected IProgressMonitor monitor;
    private IFormConditionAccessService formAccessService;
    private IRunTimeViewController viewController;
    protected BizObjectOperateResult<Void> operateResult = new BizObjectOperateResult();
    protected List<ProcessInstanceDO> toCompleteInstances;
    protected ProcessInstanceLockManager.ProcessInstanceLock lock;
    protected static final String PROCESSENGINEID = "jiuqi.nr.default";
    protected UserTask currentUserTask;
    protected IUserActionPath currenActionPath;
    protected Set<IBusinessObject> blockingInstances;
    protected Map<String, ProcessInstanceDO> orignalInstances = new HashedMap<String, ProcessInstanceDO>();

    public void setBusinessKeys(RuntimeBusinessKeyCollection rtBusinessKeys) {
        this.rtBusinessKeys = rtBusinessKeys;
        this.businessKeys = rtBusinessKeys.getBusinessKeys();
    }

    public void setUserTaskCode(String userTaskCode) {
        this.userTaskCode = userTaskCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public void setActor(IActor actor) {
        this.actor = actor;
    }

    public void setDependencies(IBusinessKeyDependencies dependencies) {
        this.dependencies = dependencies;
    }

    public void setActionArgs(IActionArgs args) {
        this.args = args;
    }

    public void setInstanceQuery(ProcessInstanceQuery istQuery) {
        this.istQuery = istQuery;
    }

    public void setOperationQuery(ProcessOperationQuery optQuery) {
        this.optQuery = optQuery;
    }

    public void setProcessDefinitionService(DefaultProcessDefinitionService processDefinitionService) {
        this.processDefinitionService = processDefinitionService;
    }

    public void setProcessInstanceLockManager(ProcessInstanceLockManager instanceLockManager) {
        this.instanceLockManager = instanceLockManager;
    }

    public void setTransactionUtil(TransactionUtil transactionUtil) {
        this.transactionUtil = transactionUtil;
    }

    public void setProgressMonitor(IProgressMonitor monitor) {
        this.monitor = monitor;
    }

    public void setFormConditionAccessService(IFormConditionAccessService formAccessService) {
        this.formAccessService = formAccessService;
    }

    public void setRunTimeViewController(IRunTimeViewController viewController) {
        this.viewController = viewController;
    }

    public IBizObjectOperateResult<Void> run() {
        if (this.businessKeys.getBusinessObjects() == null || this.businessKeys.getBusinessObjects().size() == 0) {
            return IBizObjectOperateResult.emptyResult();
        }
        String taskName = "nr.workflow2.engine.dft.batchcomplete";
        this.monitor.startTask("nr.workflow2.engine.dft.batchcomplete", new int[]{20, 20, 20, 30, 10});
        this.queryInstances();
        if (this.toCompleteInstances == null || this.toCompleteInstances.isEmpty()) {
            this.monitor.finishTask("nr.workflow2.engine.dft.batchcomplete");
            return this.operateResult;
        }
        this.verifyProcessDefinition();
        if (this.toCompleteInstances == null || this.toCompleteInstances.isEmpty()) {
            this.monitor.finishTask("nr.workflow2.engine.dft.batchcomplete");
            return this.operateResult;
        }
        this.monitor.stepIn();
        if (this.monitor.isCanceled()) {
            return SameOperationResult.newCancelResult(this.businessKeys.getBusinessObjects());
        }
        this.lockInstacnes();
        if (this.toCompleteInstances == null || this.toCompleteInstances.isEmpty()) {
            this.monitor.finishTask("nr.workflow2.engine.dft.batchcomplete");
            return this.operateResult;
        }
        this.verifyTask();
        if (this.toCompleteInstances == null || this.toCompleteInstances.isEmpty()) {
            this.monitor.finishTask("nr.workflow2.engine.dft.batchcomplete");
            return this.operateResult;
        }
        this.monitor.stepIn();
        if (this.monitor.isCanceled()) {
            return SameOperationResult.newCancelResult(this.businessKeys.getBusinessObjects());
        }
        this.verifyAction();
        if (this.toCompleteInstances == null || this.toCompleteInstances.isEmpty()) {
            this.monitor.finishTask("nr.workflow2.engine.dft.batchcomplete");
            return this.operateResult;
        }
        this.filterByDependencies();
        if (this.toCompleteInstances == null || this.toCompleteInstances.isEmpty()) {
            this.monitor.finishTask("nr.workflow2.engine.dft.batchcomplete");
            return this.operateResult;
        }
        this.monitor.stepIn();
        if (this.monitor.isCanceled()) {
            return SameOperationResult.newCancelResult(this.businessKeys.getBusinessObjects());
        }
        this.transactionUtil.runWithinTransaction(() -> {
            this.operateInstance();
            this.maintainUnitStatus();
        });
        this.monitor.stepIn();
        this.unLockInstance();
        this.sendTodo();
        this.monitor.stepIn();
        this.monitor.finishTask("nr.workflow2.engine.dft.batchcomplete");
        return this.operateResult;
    }

    public IBizObjectOperateResult<Void> verify() {
        if (this.businessKeys.getBusinessObjects() == null || this.businessKeys.getBusinessObjects().size() == 0) {
            return IBizObjectOperateResult.emptyResult();
        }
        this.queryInstances();
        if (this.toCompleteInstances == null || this.toCompleteInstances.isEmpty()) {
            return this.operateResult;
        }
        this.verifyProcessDefinition();
        if (this.toCompleteInstances == null || this.toCompleteInstances.isEmpty()) {
            return this.operateResult;
        }
        this.verifyTask();
        if (this.toCompleteInstances == null || this.toCompleteInstances.isEmpty()) {
            return this.operateResult;
        }
        this.verifyAction();
        for (ProcessInstanceDO instance : this.toCompleteInstances) {
            this.operateResult.appendResult(instance.getBusinessKey().getBusinessObject(), OperateResult.newSuccessResult());
        }
        return this.operateResult;
    }

    protected void queryInstances() {
        Map<IBusinessObject, ProcessInstanceDO> instancesFromBizkey = this.istQuery.queryInstances(this.businessKeys, ProcessInstanceQuery.QueryFieldMode.QUERY_SUMMARY_FIELD);
        HashMap<String, ProcessInstanceDO> toCompleteInstancesFromId = new HashMap<String, ProcessInstanceDO>();
        for (IBusinessObject businessObject : this.businessKeys.getBusinessObjects()) {
            ProcessInstanceDO instance = instancesFromBizkey.get(businessObject);
            if (instance == null) {
                this.appendFailResultWithDependencies(businessObject, (IOperateResult<Void>)OperateResult.newFailResult((ErrorCode)ErrorCode.INSTANCE_NOT_FOUND));
                continue;
            }
            toCompleteInstancesFromId.put(instance.getId(), instance);
        }
        this.toCompleteInstances = new ArrayList(toCompleteInstancesFromId.values());
    }

    protected void verifyProcessDefinition() {
        if (this.toCompleteInstances.isEmpty()) {
            return;
        }
        String processDefId = this.rtBusinessKeys.getWorkflowSettings().getWorkflowDefine();
        ErrorCode errorCode = null;
        ProcessDefinition procDef = (ProcessDefinition)this.processDefinitionService.queryProcess(processDefId);
        if (procDef == null) {
            errorCode = ErrorCode.PROCESSDEFINITION_NOT_FOUND;
        } else {
            this.currentUserTask = procDef.getUserTask(this.userTaskCode);
            if (this.currentUserTask == null) {
                errorCode = ErrorCode.USERTASK_NOT_FOUND;
            } else {
                this.currenActionPath = this.currentUserTask.getActionPath(this.actionCode);
                if (this.currenActionPath == null) {
                    errorCode = ErrorCode.USERACTION_NOT_FOUND;
                }
            }
        }
        if (errorCode != null) {
            for (ProcessInstanceDO instance : this.toCompleteInstances) {
                IOperateResult error = OperateResult.newFailResult((ErrorCode)errorCode);
                this.operateResult.appendResult(instance.getBusinessKey().getBusinessObject(), error);
            }
            this.toCompleteInstances.clear();
        }
    }

    protected void verifyTask() {
        for (int i = this.toCompleteInstances.size() - 1; i >= 0; --i) {
            ProcessInstanceDO instance = this.toCompleteInstances.get(i);
            if (instance.getCurNode().equals(this.userTaskCode)) continue;
            TaskNotFoundError.TaskNotFoundErrorData errorData = new TaskNotFoundError.TaskNotFoundErrorData();
            errorData.setCurrentTaskId(instance.getCurTaskId());
            errorData.setCurrentUserTask(instance.getCurNode());
            errorData.setCurrentStatus(instance.getCurStatus());
            TaskNotFoundError error = new TaskNotFoundError(errorData);
            IBusinessObject businessObject = instance.getBusinessKey().getBusinessObject();
            this.appendFailResultWithDependencies(businessObject, (IOperateResult<Void>)OperateResult.newFailResult((Error)error));
            this.toCompleteInstances.remove(i);
        }
    }

    protected void lockInstacnes() {
        List<String> lockInstanceIds = this.toCompleteInstances.stream().map(t -> t.getId()).collect(Collectors.toList());
        this.lock = this.instanceLockManager.createLock(this.istQuery.getQueryModel(), lockInstanceIds);
        this.instanceLockManager.lock(this.lock);
        Map<String, ProcessInstanceDO> instancesLocked = this.istQuery.queryInstances(this.businessKeys.getTask(), this.lock.getLockedInstanceIds(), ProcessInstanceQuery.QueryFieldMode.QUERY_SUMMARY_FIELD);
        LinkedList<ProcessInstanceDO> toCompleteInstancesNext = new LinkedList<ProcessInstanceDO>();
        for (ProcessInstanceDO tcInstance : this.toCompleteInstances) {
            ProcessInstanceDO instance = instancesLocked.get(tcInstance.getId());
            if (instance == null) {
                IBusinessObject businessObject = tcInstance.getBusinessKey().getBusinessObject();
                this.appendFailResultWithDependencies(businessObject, (IOperateResult<Void>)OperateResult.newFailResult((ErrorCode)ErrorCode.CONCURRENT_OPERATION));
                continue;
            }
            toCompleteInstancesNext.add(instance);
        }
        this.toCompleteInstances = toCompleteInstancesNext;
    }

    protected void verifyAction() {
        ActionCollector actionCollector = new ActionCollector();
        actionCollector.setActor(this.actor);
        actionCollector.setCurrentUserTask(this.currentUserTask);
        IBusinessObjectSet canActBusinessObjects = actionCollector.getCanActBusinessKeys(this.rtBusinessKeys, this.actionCode);
        for (int i = this.toCompleteInstances.size() - 1; i >= 0; --i) {
            ProcessInstanceDO instance = this.toCompleteInstances.get(i);
            IBusinessObject businessObject = instance.getBusinessKey().getBusinessObject();
            if (canActBusinessObjects.contains((Object)businessObject)) continue;
            this.appendFailResultWithDependencies(businessObject, (IOperateResult<Void>)OperateResult.newFailResult((ErrorCode)ErrorCode.UNAUTHORIZED));
            this.toCompleteInstances.remove(i);
        }
    }

    protected void appendFailResultWithDependencies(IBusinessObject businessObject, IOperateResult<Void> result) {
        this.operateResult.appendResult(businessObject, result);
        if (this.dependencies != null) {
            if (this.blockingInstances == null) {
                this.blockingInstances = new HashSet<IBusinessObject>();
            }
            for (IBusinessObject dependencyObject : this.dependencies.getDependencies(businessObject)) {
                this.operateResult.appendResult(dependencyObject, OperateResult.newFailResult((ErrorCode)ErrorCode.DEPENDDENCY_BLOCKING));
                this.blockingInstances.add(dependencyObject);
            }
        }
    }

    protected void filterByDependencies() {
        if (this.blockingInstances == null || this.blockingInstances.isEmpty()) {
            return;
        }
        for (int i = this.toCompleteInstances.size() - 1; i >= 0; --i) {
            IBusinessObject businessObject = this.toCompleteInstances.get(i).getBusinessKey().getBusinessObject();
            if (!this.blockingInstances.contains(businessObject)) continue;
            this.toCompleteInstances.remove(i);
        }
    }

    protected void operateInstance() {
        ArrayList<ProcessOperationDO> operations = new ArrayList<ProcessOperationDO>(this.toCompleteInstances.size());
        for (ProcessInstanceDO instance : this.toCompleteInstances) {
            ProcessInstanceDO orignalInstance = new ProcessInstanceDO();
            orignalInstance.setCurNode(instance.getCurNode());
            orignalInstance.setCurTaskId(instance.getCurTaskId());
            this.orignalInstances.put(instance.getId(), orignalInstance);
            ProcessOperationDO operation = ProcessRepositoryUtil.operate(instance, this.currenActionPath, IActor.fromContext(), this.args);
            operations.add(operation);
            instance.setLastOperationId(operation.getId());
        }
        Set<String> successModifiedIds = this.istQuery.modifyInstances(this.toCompleteInstances, this.lock.getLockId());
        for (int i = this.toCompleteInstances.size() - 1; i >= 0; --i) {
            ProcessInstanceDO instance = this.toCompleteInstances.get(i);
            IBusinessObject businessObject = instance.getBusinessKey().getBusinessObject();
            if (successModifiedIds.contains(instance.getId())) {
                this.operateResult.appendResult(businessObject, OperateResult.newSuccessResult());
                continue;
            }
            this.operateResult.appendResult(businessObject, OperateResult.newFailResult((ErrorCode)ErrorCode.UNKNOW));
            this.toCompleteInstances.remove(i);
        }
        this.monitor.stepIn();
        List<ProcessOperationDO> operationsToInsert = operations.stream().filter(t -> successModifiedIds.contains(t.getInstanceId())).collect(Collectors.toList());
        this.optQuery.insertOperations(operationsToInsert);
        this.monitor.stepIn();
    }

    protected void maintainUnitStatus() {
        WorkflowObjectType objectType = this.rtBusinessKeys.getWorkflowSettings().getWorkflowObjectType();
        if (objectType != WorkflowObjectType.FORM && objectType != WorkflowObjectType.FORM_GROUP) {
            return;
        }
        if (this.toCompleteInstances == null || this.toCompleteInstances.isEmpty()) {
            return;
        }
        Collection modifiedBusinessKeys = this.toCompleteInstances.stream().map(t -> t.getBusinessKey()).collect(Collectors.toList());
        try (UnitStatusBatchMaintainer maintainer = new UnitStatusBatchMaintainer();){
            maintainer.setFormOrGroupbusinessKeys(modifiedBusinessKeys);
            maintainer.setActor(this.actor);
            maintainer.setTaskKey(this.rtBusinessKeys.getTaskKey());
            maintainer.setFormSchemeKey(this.rtBusinessKeys.getFormSchemeKey());
            maintainer.setWorkflowSettings(this.rtBusinessKeys.getWorkflowSettings());
            maintainer.setInstanceQuery(this.istQuery);
            maintainer.setProcessInstanceLockManager(this.instanceLockManager);
            maintainer.setFormConditionAccessService(this.formAccessService);
            maintainer.setRunTimeViewController(this.viewController);
            maintainer.setTransactionUtil(this.transactionUtil);
            maintainer.run();
        }
    }

    protected void sendTodo() {
        TodoUtils.onBatchTaskCompleted(this.rtBusinessKeys, this.toCompleteInstances, this.orignalInstances, this.args.getString("COMMENT"));
        this.monitor.stepIn();
    }

    protected void unLockInstance() {
        if (this.lock != null) {
            this.lock.close();
            this.lock = null;
        }
    }

    @Override
    public void close() throws IOException {
        this.unLockInstance();
    }
}

