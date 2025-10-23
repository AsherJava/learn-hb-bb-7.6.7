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
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.action.ApplyReturnActionCollector;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.todo.TodoUtils;

public class ApplyReturnActionRunner
extends ActionRunner {
    @Override
    public void run() {
        this.queryInstance();
        this.verifyTask();
        this.verifyAction();
        this.sendTodo();
    }

    @Override
    protected void verifyAction() {
        UserTask currentUserTask = (UserTask)this.processDefinitionService.getUserTask(this.instance.getProcessDefinitionId(), this.instance.getCurNode());
        ApplyReturnActionCollector actionCollector = new ApplyReturnActionCollector();
        actionCollector.setActor(this.actor);
        actionCollector.setCurrentUserTask(currentUserTask);
        if (!actionCollector.canAct(this.rtBusinessKey, this.actionCode, this.args)) {
            throw new UnauthorizedException("jiuqi.nr.default", this.taskId, this.actor, this.actionCode);
        }
    }

    @Override
    protected void sendTodo() {
        TodoUtils.onApplyReturn(this.rtBusinessKey, this.instance, this.args.getString("COMMENT"));
    }
}

