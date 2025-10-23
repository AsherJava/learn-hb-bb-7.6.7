/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.access.service.IFormConditionAccessService
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.workflow2.engine.core.actor.IActor
 *  com.jiuqi.nr.workflow2.engine.core.exception.InstanceConcurrentOperationException
 *  com.jiuqi.nr.workflow2.engine.core.exception.InstanceNotFoundException
 *  com.jiuqi.nr.workflow2.engine.core.exception.TaskNotFoundError$TaskNotFoundErrorData
 *  com.jiuqi.nr.workflow2.engine.core.exception.TaskNotFoundException
 *  com.jiuqi.nr.workflow2.engine.core.exception.UnauthorizedException
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserActionPath
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserTask
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime.action;

import com.jiuqi.nr.data.access.service.IFormConditionAccessService;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.workflow2.engine.common.definition.model.UserTask;
import com.jiuqi.nr.workflow2.engine.core.actor.IActor;
import com.jiuqi.nr.workflow2.engine.core.exception.InstanceConcurrentOperationException;
import com.jiuqi.nr.workflow2.engine.core.exception.InstanceNotFoundException;
import com.jiuqi.nr.workflow2.engine.core.exception.TaskNotFoundError;
import com.jiuqi.nr.workflow2.engine.core.exception.TaskNotFoundException;
import com.jiuqi.nr.workflow2.engine.core.exception.UnauthorizedException;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserActionPath;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserTask;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.engine.dflt.process.definition.DefaultProcessDefinitionService;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.TransactionUtil;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.action.ActionCollector;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.action.UnitStatusMaintainer;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessInstanceDO;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessInstanceLockManager;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessInstanceQuery;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessOperationDO;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessOperationQuery;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessRepositoryUtil;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.todo.TodoUtils;
import java.io.Closeable;
import java.util.Arrays;

public class ActionRunner
implements Closeable {
    protected String instanceId;
    protected String taskId;
    protected RuntimeBusinessKey rtBusinessKey;
    protected IActor actor;
    protected String actionCode;
    protected IActionArgs args;
    protected ProcessInstanceQuery istQuery;
    protected ProcessOperationQuery optQuery;
    protected DefaultProcessDefinitionService processDefinitionService;
    protected ProcessInstanceLockManager instanceLockManager;
    protected TransactionUtil transactionUtil;
    private IFormConditionAccessService formAccessService;
    private IRunTimeViewController viewController;
    protected ProcessInstanceDO instance;
    protected ProcessInstanceLockManager.ProcessInstanceLock lock;
    protected static final String PROCESSENGINEID = "jiuqi.nr.default";
    protected String orignalTaskId;
    protected String orignalUserTask;
    protected IUserTask newUserTask;

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public void setBusinessKey(RuntimeBusinessKey rtBusinessKey) {
        this.rtBusinessKey = rtBusinessKey;
    }

    public void setActor(IActor actor) {
        this.actor = actor;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
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

    public void setFormConditionAccessService(IFormConditionAccessService formAccessService) {
        this.formAccessService = formAccessService;
    }

    public void setRunTimeViewController(IRunTimeViewController viewController) {
        this.viewController = viewController;
    }

    public void run() {
        this.lockInstance();
        this.queryInstance();
        this.verifyTask();
        this.verifyAction();
        this.transactionUtil.runWithinTransaction(() -> {
            this.operateInstance();
            this.maintainUnitStatus();
        });
        this.unLockInstance();
        this.sendTodo();
    }

    protected void queryInstance() {
        this.instance = this.istQuery.queryInstance(this.instanceId);
        if (this.instance == null) {
            throw new InstanceNotFoundException(PROCESSENGINEID, this.instanceId);
        }
    }

    protected void verifyTask() {
        if (!this.instance.getCurTaskId().equals(this.taskId)) {
            TaskNotFoundError.TaskNotFoundErrorData errorData = new TaskNotFoundError.TaskNotFoundErrorData();
            errorData.setOrginalTaskId(this.taskId);
            errorData.setCurrentTaskId(this.instance.getCurTaskId());
            errorData.setCurrentUserTask(this.instance.getCurNode());
            errorData.setCurrentStatus(this.instance.getCurStatus());
            throw new TaskNotFoundException(PROCESSENGINEID, errorData);
        }
    }

    protected void verifyAction() {
        UserTask currentUserTask = (UserTask)this.processDefinitionService.getUserTask(this.instance.getProcessDefinitionId(), this.instance.getCurNode());
        ActionCollector actionCollector = new ActionCollector();
        actionCollector.setActor(this.actor);
        actionCollector.setCurrentUserTask(currentUserTask);
        if (!actionCollector.canAct(this.rtBusinessKey, this.actionCode, this.args)) {
            throw new UnauthorizedException(PROCESSENGINEID, this.taskId, this.actor, this.actionCode);
        }
    }

    protected void lockInstance() {
        this.lock = this.instanceLockManager.createLock(this.istQuery.getQueryModel(), Arrays.asList(this.instanceId));
        this.instanceLockManager.lock(this.lock);
        if (!this.lock.getLockedInstanceIds().contains(this.instanceId)) {
            throw new InstanceConcurrentOperationException(PROCESSENGINEID, this.instanceId);
        }
    }

    protected void operateInstance() {
        this.orignalTaskId = this.instance.getCurTaskId();
        this.orignalUserTask = this.instance.getCurNode();
        IUserActionPath actionPath = this.processDefinitionService.getActionPath(this.instance.getProcessDefinitionId(), this.instance.getCurNode(), this.actionCode);
        this.newUserTask = actionPath.getDestUserTask();
        ProcessOperationDO operation = ProcessRepositoryUtil.operate(this.instance, actionPath, IActor.fromContext(), this.args);
        this.instance.setLastOperationId(operation.getId());
        this.istQuery.modifyInstance(this.instance, this.lock.getLockId());
        this.optQuery.insertOperation(operation);
    }

    protected void maintainUnitStatus() {
        WorkflowObjectType objectType = this.rtBusinessKey.getWorkflowSettings().getWorkflowObjectType();
        if (objectType != WorkflowObjectType.FORM && objectType != WorkflowObjectType.FORM_GROUP) {
            return;
        }
        try (UnitStatusMaintainer maintainer = new UnitStatusMaintainer();){
            maintainer.setFormOrGroupbusinessKey(this.instance.getBusinessKey());
            maintainer.setActor(this.actor);
            maintainer.setTaskKey(this.rtBusinessKey.getTaskKey());
            maintainer.setFormSchemeKey(this.rtBusinessKey.getFormSchemeKey());
            maintainer.setWorkflowSettings(this.rtBusinessKey.getWorkflowSettings());
            maintainer.setInstanceQuery(this.istQuery);
            maintainer.setProcessInstanceLockManager(this.instanceLockManager);
            maintainer.setFormConditionAccessService(this.formAccessService);
            maintainer.setRunTimeViewController(this.viewController);
            maintainer.setTransactionUtil(this.transactionUtil);
            maintainer.run();
        }
    }

    protected void sendTodo() {
        TodoUtils.onTaskCompleted(this.rtBusinessKey, this.instance, this.orignalTaskId, this.orignalUserTask, this.newUserTask, this.args.getString("COMMENT"));
    }

    protected void unLockInstance() {
        if (this.lock != null) {
            this.instanceLockManager.unlock(this.lock);
            this.lock = null;
        }
    }

    @Override
    public void close() {
        this.unLockInstance();
    }
}

