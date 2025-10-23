/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.exception.ErrorCode
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBizObjectOperateResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectSet
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IOperateResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.OperateResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKey
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime.action;

import com.jiuqi.nr.workflow2.engine.common.definition.model.ProcessDefinition;
import com.jiuqi.nr.workflow2.engine.core.exception.ErrorCode;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBizObjectOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectSet;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.OperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKey;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.action.ActionBatchRunner;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.action.ApplyReturnActionCollector;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.action.SameOperationResult;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessInstanceDO;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.todo.TodoUtils;

public class ApplyReturnActionBatchRunner
extends ActionBatchRunner {
    @Override
    public IBizObjectOperateResult<Void> run() {
        if (this.businessKeys.getBusinessObjects() == null || this.businessKeys.getBusinessObjects().size() == 0) {
            return IBizObjectOperateResult.emptyResult();
        }
        String taskName = "nr.workflow2.engine.dft.batchcomplete";
        this.monitor.startTask("nr.workflow2.engine.dft.batchcomplete", new int[]{80, 20});
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
        this.verifyTask();
        if (this.toCompleteInstances == null || this.toCompleteInstances.isEmpty()) {
            this.monitor.finishTask("nr.workflow2.engine.dft.batchcomplete");
            return this.operateResult;
        }
        this.verifyAction();
        if (this.toCompleteInstances == null || this.toCompleteInstances.isEmpty()) {
            this.monitor.finishTask("nr.workflow2.engine.dft.batchcomplete");
            return this.operateResult;
        }
        this.monitor.stepIn();
        if (this.monitor.isCanceled()) {
            return SameOperationResult.newCancelResult(this.businessKeys.getBusinessObjects());
        }
        this.sendTodo();
        this.monitor.stepIn();
        this.monitor.finishTask("nr.workflow2.engine.dft.batchcomplete");
        return this.operateResult;
    }

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
            } else if (this.currentUserTask.getApplyReturnAction() == null) {
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
        ApplyReturnActionCollector actionCollector = new ApplyReturnActionCollector();
        actionCollector.setActor(this.actor);
        actionCollector.setCurrentUserTask(this.currentUserTask);
        IBusinessObjectSet canActBusinessObjects = actionCollector.getCanActBusinessKeys(this.rtBusinessKeys, this.actionCode);
        for (int i = this.toCompleteInstances.size() - 1; i >= 0; --i) {
            ProcessInstanceDO instance = (ProcessInstanceDO)this.toCompleteInstances.get(i);
            IBusinessObject businessObject = instance.getBusinessKey().getBusinessObject();
            if (canActBusinessObjects.contains((Object)businessObject)) continue;
            this.appendFailResultWithDependencies(businessObject, (IOperateResult<Void>)OperateResult.newFailResult((ErrorCode)ErrorCode.UNAUTHORIZED));
            this.toCompleteInstances.remove(i);
        }
    }

    @Override
    protected void sendTodo() {
        for (ProcessInstanceDO instance : this.toCompleteInstances) {
            RuntimeBusinessKey rtBusinessKey = new RuntimeBusinessKey(instance.getBusinessKey(), this.rtBusinessKeys.getTask(), this.rtBusinessKeys.getFormScheme(), this.rtBusinessKeys.getWorkflowSettings());
            TodoUtils.onApplyReturn(rtBusinessKey, instance, this.args.getString("COMMENT"));
        }
    }
}

