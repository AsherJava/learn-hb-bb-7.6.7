/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.exception.UnauthorizedException
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime.action;

import com.jiuqi.nr.workflow2.engine.common.definition.model.UserTask;
import com.jiuqi.nr.workflow2.engine.core.exception.UnauthorizedException;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.action.ActionRunner;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.action.RetriveActionCollector;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessOperationDO;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessRepositoryUtil;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.todo.TodoUtils;

public class RetriveActionRunner
extends ActionRunner {
    private String retriveToStatus;
    private String retriveToOperationId;

    @Override
    protected void verifyAction() {
        UserTask currentUserTask = (UserTask)this.processDefinitionService.getUserTask(this.instance.getProcessDefinitionId(), this.instance.getCurNode());
        RetriveActionCollector actionCollector = new RetriveActionCollector();
        actionCollector.setActor(this.actor);
        actionCollector.setCurrentUserTask(currentUserTask);
        actionCollector.setInstanceId(this.instanceId);
        actionCollector.setOperationQuery(this.optQuery);
        if (!actionCollector.canAct(this.rtBusinessKey, this.actionCode, this.args)) {
            throw new UnauthorizedException("jiuqi.nr.default", this.taskId, this.actor, this.actionCode);
        }
        this.newUserTask = actionCollector.getRetriveToNode();
        this.retriveToStatus = actionCollector.getRetriveToStatus();
        this.retriveToOperationId = actionCollector.getRetriveToOperationId();
    }

    @Override
    protected void operateInstance() {
        this.orignalTaskId = this.instance.getCurTaskId();
        ProcessOperationDO operation = ProcessRepositoryUtil.retrive(this.instance, this.newUserTask.getCode(), this.retriveToStatus, this.actor);
        this.instance.setLastOperationId(this.retriveToOperationId);
        this.istQuery.modifyInstance(this.instance, this.lock.getLockId());
        this.optQuery.insertOperation(operation);
    }

    @Override
    protected void sendTodo() {
        TodoUtils.onTaskRetrived(this.rtBusinessKey, this.instance, this.orignalTaskId, this.orignalUserTask, this.newUserTask, this.args.getString("COMMENT"));
    }
}

