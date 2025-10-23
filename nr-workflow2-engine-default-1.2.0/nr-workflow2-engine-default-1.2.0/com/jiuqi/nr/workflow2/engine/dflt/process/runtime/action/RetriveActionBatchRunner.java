/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.actor.IActor
 *  com.jiuqi.nr.workflow2.engine.core.exception.ErrorCode
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IActorStrategy
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectSet
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IOperateResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.OperateResult
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime.action;

import com.jiuqi.nr.workflow2.engine.common.definition.model.ProcessDefinition;
import com.jiuqi.nr.workflow2.engine.common.definition.model.UserAction;
import com.jiuqi.nr.workflow2.engine.common.definition.model.UserTask;
import com.jiuqi.nr.workflow2.engine.core.actor.IActor;
import com.jiuqi.nr.workflow2.engine.core.exception.ErrorCode;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IActorStrategy;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectSet;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.OperateResult;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.ActorStrategyUtil;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.action.ActionBatchRunner;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.action.RetriveActionCollector;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessInstanceDO;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessOperationDO;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessRepositoryUtil;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.todo.TodoUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class RetriveActionBatchRunner
extends ActionBatchRunner {
    private UserTask retriveToUserTask;
    private Map<String, String> retriveToStatus = new HashMap<String, String>();
    private Map<String, String> retriveToOperationIds = new HashMap<String, String>();

    @Override
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
            } else if (this.currentUserTask.getRetriveActionPath() == null) {
                errorCode = ErrorCode.USERACTION_NOT_FOUND;
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

    @Override
    protected void verifyAction() {
        RetriveActionCollector actionCollector = new RetriveActionCollector();
        actionCollector.setActor(this.actor);
        actionCollector.setCurrentUserTask(this.currentUserTask);
        actionCollector.setOperationQuery(this.optQuery);
        for (int i = this.toCompleteInstances.size() - 1; i >= 0; --i) {
            ProcessInstanceDO instance = (ProcessInstanceDO)this.toCompleteInstances.get(i);
            actionCollector.setInstanceId(instance.getId());
            UserAction action = actionCollector.findRetriveAction();
            if (action == null) {
                IBusinessObject businessObject = instance.getBusinessKey().getBusinessObject();
                this.appendFailResultWithDependencies(businessObject, (IOperateResult<Void>)OperateResult.newFailResult((ErrorCode)ErrorCode.UNAUTHORIZED));
                this.toCompleteInstances.remove(i);
                continue;
            }
            if (this.retriveToUserTask == null) {
                this.retriveToUserTask = actionCollector.getRetriveToNode();
            }
            this.retriveToStatus.put(instance.getId(), actionCollector.getRetriveToStatus());
            this.retriveToOperationIds.put(instance.getId(), actionCollector.getRetriveToOperationId());
        }
        if (this.retriveToUserTask == null) {
            return;
        }
        List<IActorStrategy> actorStrategies = this.retriveToUserTask.getActionExecutors();
        IBusinessObjectSet canActBusinessObjects = ActorStrategyUtil.getInstance().getMatchBusinessKeys(actorStrategies, this.actor, this.rtBusinessKeys);
        for (int i = this.toCompleteInstances.size() - 1; i >= 0; --i) {
            ProcessInstanceDO instance = (ProcessInstanceDO)this.toCompleteInstances.get(i);
            IBusinessObject businessObject = instance.getBusinessKey().getBusinessObject();
            if (canActBusinessObjects.contains((Object)businessObject)) continue;
            this.appendFailResultWithDependencies(businessObject, (IOperateResult<Void>)OperateResult.newFailResult((ErrorCode)ErrorCode.UNAUTHORIZED));
            this.toCompleteInstances.remove(i);
        }
    }

    @Override
    protected void operateInstance() {
        ArrayList<ProcessOperationDO> operations = new ArrayList<ProcessOperationDO>(this.toCompleteInstances.size());
        for (ProcessInstanceDO instance : this.toCompleteInstances) {
            ProcessInstanceDO orignalInstance = new ProcessInstanceDO();
            orignalInstance.setCurNode(instance.getCurNode());
            orignalInstance.setCurTaskId(instance.getCurTaskId());
            this.orignalInstances.put(instance.getId(), orignalInstance);
            ProcessOperationDO operation = ProcessRepositoryUtil.retrive(instance, this.retriveToUserTask.getCode(), this.retriveToStatus.get(instance.getId()), IActor.fromContext());
            instance.setLastOperationId(this.retriveToOperationIds.get(instance.getId()));
            operations.add(operation);
        }
        Set<String> successModifiedIds = this.istQuery.modifyInstances(this.toCompleteInstances, this.lock.getLockId());
        for (int i = this.toCompleteInstances.size() - 1; i >= 0; --i) {
            ProcessInstanceDO instance = (ProcessInstanceDO)this.toCompleteInstances.get(i);
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

    @Override
    protected void sendTodo() {
        TodoUtils.onBatchTaskRetrived(this.rtBusinessKeys, this.toCompleteInstances, this.orignalInstances, this.args.getString("COMMENT"));
        this.monitor.stepIn();
    }
}

