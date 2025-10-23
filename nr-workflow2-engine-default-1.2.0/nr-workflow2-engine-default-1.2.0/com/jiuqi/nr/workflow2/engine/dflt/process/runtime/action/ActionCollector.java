/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.actor.IActor
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IActorStrategy
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserActionPath
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectSet
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessObjectSet
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKeyCollection
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime.action;

import com.jiuqi.nr.workflow2.engine.common.definition.model.UserTask;
import com.jiuqi.nr.workflow2.engine.core.actor.IActor;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IActorStrategy;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserActionPath;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectSet;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessObjectSet;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.ActorStrategyUtil;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.action.UserActionRuntime;
import java.util.HashMap;
import java.util.List;

public class ActionCollector {
    protected IActor actor;
    protected UserTask currentUserTask;

    public void setActor(IActor actor) {
        this.actor = actor;
    }

    public void setCurrentUserTask(UserTask currentUserTask) {
        this.currentUserTask = currentUserTask;
    }

    public void collectActions(List<IUserAction> userActions, RuntimeBusinessKey rtBusinessKey) {
        if (!ActorStrategyUtil.getInstance().actorIsMatch(this.currentUserTask.getActionExecutors(), this.actor, rtBusinessKey)) {
            return;
        }
        for (IUserActionPath actionPath : this.currentUserTask.getActionPaths()) {
            IUserAction userAction = actionPath.getUserAction();
            if (userAction.getCode().equals("act_upload") || userAction.getCode().equals("act_submit")) {
                HashMap<String, Object> properties = new HashMap<String, Object>();
                properties.put("ENABLE_FORCE_REPORT", this.enableForceReport(this.currentUserTask));
                UserActionRuntime reportAction = new UserActionRuntime(userAction, properties);
                userActions.add(reportAction);
                continue;
            }
            userActions.add(userAction);
        }
    }

    private boolean enableForceReport(UserTask currentUserTask) {
        Object pEnableForceReport = currentUserTask.getProperties("ENABLE_FORCE_REPORT");
        Object pForceReportRole = currentUserTask.getProperties("FORCE_REPORT_ROLE");
        if (pEnableForceReport == null || !((Boolean)pEnableForceReport).booleanValue()) {
            return false;
        }
        if (ActorStrategyUtil.getInstance().actorIsSystemUser(this.actor)) {
            return true;
        }
        return pForceReportRole != null && ActorStrategyUtil.getInstance().actorIsGrantedToRole(this.actor, (String)pForceReportRole);
    }

    public boolean canAct(RuntimeBusinessKey rtBusinessKey, String actionCode, IActionArgs actionArgs) {
        boolean actionExists = this.currentUserTask.getActionPaths().stream().anyMatch(o -> o.getUserAction().getCode().equals(actionCode));
        if (!actionExists) {
            return false;
        }
        if (actionArgs.getBoolean("FORCE_REPORT") && !this.enableForceReport(this.currentUserTask)) {
            return false;
        }
        return ActorStrategyUtil.getInstance().actorIsMatch(this.currentUserTask.getActionExecutors(), this.actor, rtBusinessKey);
    }

    public IBusinessObjectSet getCanActBusinessKeys(RuntimeBusinessKeyCollection rtBbusinessKeys, String actionCode) {
        boolean actionExists = this.currentUserTask.getActionPaths().stream().anyMatch(o -> o.getUserAction().getCode().equals(actionCode));
        if (!actionExists) {
            return new BusinessObjectSet();
        }
        List<IActorStrategy> actorStrategies = this.currentUserTask.getActionExecutors();
        return ActorStrategyUtil.getInstance().getMatchBusinessKeys(actorStrategies, this.actor, rtBbusinessKeys);
    }
}

