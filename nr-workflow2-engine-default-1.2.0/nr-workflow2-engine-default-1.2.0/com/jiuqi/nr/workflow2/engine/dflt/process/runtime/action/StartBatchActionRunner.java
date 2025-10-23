/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.monitor.IProgressMonitor
 *  com.jiuqi.nr.data.access.service.IFormConditionAccessService
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.workflow2.engine.core.actor.IActor
 *  com.jiuqi.nr.workflow2.engine.core.exception.ErrorCode
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBizObjectOperateResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BizObjectOperateResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.OperateResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime.action;

import com.jiuqi.bi.monitor.IProgressMonitor;
import com.jiuqi.nr.data.access.service.IFormConditionAccessService;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.workflow2.engine.common.definition.model.ProcessDefinition;
import com.jiuqi.nr.workflow2.engine.common.definition.model.UserActionPath;
import com.jiuqi.nr.workflow2.engine.common.definition.model.UserTask;
import com.jiuqi.nr.workflow2.engine.core.actor.IActor;
import com.jiuqi.nr.workflow2.engine.core.exception.ErrorCode;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBizObjectOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BizObjectOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.OperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.engine.dflt.process.definition.DefaultProcessDefinitionService;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.TransactionUtil;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StartBatchActionRunner
implements Closeable {
    protected String processDefinitionId;
    protected IActor actor;
    protected IBusinessKeyCollection businessKeys;
    protected RuntimeBusinessKeyCollection rtBusinessKeys;
    protected ProcessInstanceQuery istQuery;
    protected ProcessOperationQuery optQuery;
    protected DefaultProcessDefinitionService processDefinitionService;
    protected TransactionUtil transactionUtil;
    protected IProgressMonitor monitor;
    private IFormConditionAccessService formAccessService;
    private IRunTimeViewController viewController;
    protected ProcessInstanceLockManager instanceLockManager;
    protected static final String PROCESSENGINEID = "jiuqi.nr.default";
    protected Set<IBusinessObject> existsBussinessObject;
    protected UserTask startUserTask;
    protected UserActionPath startAction;
    protected List<ProcessInstanceDO> newInstances;
    BizObjectOperateResult<Void> operateResult;

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public void setActor(IActor actor) {
        this.actor = actor;
    }

    public void setBusinessKeys(RuntimeBusinessKeyCollection rtBusinessKeys) {
        this.rtBusinessKeys = rtBusinessKeys;
        this.businessKeys = rtBusinessKeys.getBusinessKeys();
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

    public void setProcessInstanceLockManager(ProcessInstanceLockManager instanceLockManager) {
        this.instanceLockManager = instanceLockManager;
    }

    public IBizObjectOperateResult<Void> run() {
        String taskName = "batchstart-workflow-instance";
        this.monitor.startTask("batchstart-workflow-instance", new int[]{20, 35, 35, 10});
        this.queryExistInstance();
        if (this.monitor.isCanceled()) {
            return SameOperationResult.newCancelResult(this.businessKeys.getBusinessObjects());
        }
        this.monitor.stepIn();
        this.transactionUtil.runWithinTransaction(() -> {
            this.startInstance();
            this.maintainUnitStatus();
        });
        this.monitor.stepIn();
        this.sendTodo();
        this.monitor.stepIn();
        this.monitor.finishTask("batchstart-workflow-instance");
        return this.operateResult;
    }

    protected void queryExistInstance() {
        this.existsBussinessObject = this.istQuery.queryExistsInstances(this.businessKeys);
    }

    protected void startInstance() {
        ProcessDefinition processDefinition = this.processDefinitionService.getProcess(this.processDefinitionId);
        this.startUserTask = processDefinition.getStartUserTask();
        this.startAction = this.startUserTask.getActionPath("start");
        this.operateResult = new BizObjectOperateResult();
        this.newInstances = new ArrayList<ProcessInstanceDO>();
        ArrayList<ProcessOperationDO> operationDOs = new ArrayList<ProcessOperationDO>();
        for (IBusinessObject businessObject : this.businessKeys.getBusinessObjects()) {
            if (this.existsBussinessObject.contains(businessObject)) {
                this.operateResult.appendResult(businessObject, OperateResult.newFailResult((ErrorCode)ErrorCode.BUSINESSKEY_DUPLICATE));
                continue;
            }
            BusinessKey businessKey = new BusinessKey(this.rtBusinessKeys.getTaskKey(), businessObject);
            ProcessInstanceDO instance = ProcessRepositoryUtil.newInstance(this.processDefinitionId, (IBusinessKey)businessKey, this.startAction, this.actor);
            ProcessOperationDO operation = ProcessRepositoryUtil.newStartOperation(instance, this.startAction, this.actor);
            instance.setLastOperationId(operation.getId());
            this.newInstances.add(instance);
            operationDOs.add(operation);
            this.operateResult.appendResult(businessObject, OperateResult.newSuccessResult());
        }
        if (this.newInstances.isEmpty()) {
            return;
        }
        this.istQuery.insertInstances(this.newInstances);
        this.monitor.stepIn();
        this.optQuery.insertOperations(operationDOs);
    }

    protected void maintainUnitStatus() {
        WorkflowObjectType objectType = this.rtBusinessKeys.getWorkflowSettings().getWorkflowObjectType();
        if (objectType != WorkflowObjectType.FORM && objectType != WorkflowObjectType.FORM_GROUP) {
            return;
        }
        Collection modifiedBusinessKeys = this.newInstances.stream().map(t -> t.getBusinessKey()).collect(Collectors.toList());
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
        TodoUtils.onInstanceBatchStarted(this.rtBusinessKeys, this.newInstances);
    }

    @Override
    public void close() {
    }
}

