/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.access.service.IFormConditionAccessService
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.workflow2.engine.core.actor.IActor
 *  com.jiuqi.nr.workflow2.engine.core.exception.DuplicateBusinessKeyException
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime.action;

import com.jiuqi.nr.data.access.service.IFormConditionAccessService;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.workflow2.engine.common.definition.model.ProcessDefinition;
import com.jiuqi.nr.workflow2.engine.common.definition.model.UserActionPath;
import com.jiuqi.nr.workflow2.engine.common.definition.model.UserTask;
import com.jiuqi.nr.workflow2.engine.core.actor.IActor;
import com.jiuqi.nr.workflow2.engine.core.exception.DuplicateBusinessKeyException;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.engine.dflt.process.definition.DefaultProcessDefinitionService;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.TransactionUtil;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.action.UnitStatusMaintainer;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessInstanceDO;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessInstanceLockManager;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessInstanceQuery;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessOperationDO;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessOperationQuery;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessRepositoryUtil;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.todo.TodoUtils;
import java.io.Closeable;

public class StartActionRunner
implements Closeable {
    protected String processDefinitionId;
    protected IActor actor;
    protected IBusinessKey businessKey;
    protected RuntimeBusinessKey rtBusinessKey;
    protected ProcessInstanceQuery istQuery;
    protected ProcessOperationQuery optQuery;
    protected DefaultProcessDefinitionService processDefinitionService;
    protected TransactionUtil transactionUtil;
    private IFormConditionAccessService formAccessService;
    private IRunTimeViewController viewController;
    protected ProcessInstanceLockManager instanceLockManager;
    protected static final String PROCESSENGINEID = "jiuqi.nr.default";
    protected UserTask startUserTask;
    protected UserActionPath startAction;
    protected ProcessInstanceDO newInstance;

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public void setActor(IActor actor) {
        this.actor = actor;
    }

    public void setBusinessKey(RuntimeBusinessKey rtBusinessKey) {
        this.rtBusinessKey = rtBusinessKey;
        this.businessKey = rtBusinessKey.getBusinessKey();
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

    public void setFormConditionAccessService(IFormConditionAccessService formAccessService) {
        this.formAccessService = formAccessService;
    }

    public void setRunTimeViewController(IRunTimeViewController viewController) {
        this.viewController = viewController;
    }

    public void setProcessInstanceLockManager(ProcessInstanceLockManager instanceLockManager) {
        this.instanceLockManager = instanceLockManager;
    }

    public ProcessInstanceDO run() {
        this.verifyInstanceExists();
        this.transactionUtil.runWithinTransaction(() -> {
            this.startInstance();
            this.maintainUnitStatus();
        });
        this.sendTodo();
        return this.newInstance;
    }

    protected void verifyInstanceExists() {
        ProcessInstanceDO instance = this.istQuery.queryInstance(this.businessKey);
        if (instance != null) {
            throw new DuplicateBusinessKeyException(PROCESSENGINEID, this.businessKey);
        }
    }

    protected void startInstance() {
        ProcessDefinition processDefinition = this.processDefinitionService.getProcess(this.processDefinitionId);
        this.startUserTask = processDefinition.getStartUserTask();
        this.startAction = this.startUserTask.getActionPath("start");
        this.newInstance = ProcessRepositoryUtil.newInstance(this.processDefinitionId, this.businessKey, this.startAction, this.actor);
        ProcessOperationDO operation = ProcessRepositoryUtil.newStartOperation(this.newInstance, this.startAction, this.actor);
        this.newInstance.setLastOperationId(operation.getId());
        this.istQuery.insertInstance(this.newInstance);
        this.optQuery.insertOperation(operation);
    }

    protected void maintainUnitStatus() {
        WorkflowObjectType objectType = this.rtBusinessKey.getWorkflowSettings().getWorkflowObjectType();
        if (objectType != WorkflowObjectType.FORM && objectType != WorkflowObjectType.FORM_GROUP) {
            return;
        }
        try (UnitStatusMaintainer maintainer = new UnitStatusMaintainer();){
            maintainer.setFormOrGroupbusinessKey(this.businessKey);
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
        TodoUtils.onInstanceStarted(this.rtBusinessKey, this.newInstance, this.startUserTask);
    }

    @Override
    public void close() {
    }
}

