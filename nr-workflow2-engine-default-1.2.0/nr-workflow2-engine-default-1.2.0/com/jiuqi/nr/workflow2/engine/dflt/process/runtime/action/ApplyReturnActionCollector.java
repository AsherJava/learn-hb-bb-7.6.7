/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IActorStrategy
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectSet
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessObjectSet
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKeyCollection
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime.action;

import com.jiuqi.nr.workflow2.engine.common.definition.model.RetriveActionPath;
import com.jiuqi.nr.workflow2.engine.common.definition.model.UserAction;
import com.jiuqi.nr.workflow2.engine.common.definition.model.UserTask;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IActorStrategy;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectSet;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessObjectSet;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.ActorStrategyUtil;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.action.ActionCollector;
import java.util.List;

public class ApplyReturnActionCollector
extends ActionCollector {
    private UserTask retriveToNode;

    public UserTask getRetriveToNode() {
        return this.retriveToNode;
    }

    @Override
    public void collectActions(List<IUserAction> userActions, RuntimeBusinessKey rtBusinessKey) {
        UserAction applyReturnAction = this.findApplyReturnAction(this.currentUserTask);
        if (applyReturnAction != null && this.actorIsMatch(rtBusinessKey)) {
            userActions.add(applyReturnAction);
        }
    }

    private boolean actorIsMatch(RuntimeBusinessKey rtBusinessKey) {
        return ActorStrategyUtil.getInstance().actorIsMatch(this.retriveToNode.getActionExecutors(), this.actor, rtBusinessKey);
    }

    @Override
    public boolean canAct(RuntimeBusinessKey rtBusinessKey, String actionCode, IActionArgs actionArgs) {
        UserAction applyReturnAction = this.findApplyReturnAction(this.currentUserTask);
        if (applyReturnAction != null) {
            return this.actorIsMatch(rtBusinessKey);
        }
        return false;
    }

    @Override
    public IBusinessObjectSet getCanActBusinessKeys(RuntimeBusinessKeyCollection rtBbusinessKeys, String actionCode) {
        UserAction applyReturnAction = this.findApplyReturnAction(this.currentUserTask);
        if (applyReturnAction == null) {
            return new BusinessObjectSet();
        }
        List<IActorStrategy> actorStrategies = this.retriveToNode.getActionExecutors();
        return ActorStrategyUtil.getInstance().getMatchBusinessKeys(actorStrategies, this.actor, rtBbusinessKeys);
    }

    public UserAction findApplyReturnAction(UserTask currentUserTask) {
        RetriveActionPath applyReturnPath = currentUserTask.getApplyReturnAction();
        if (applyReturnPath == null) {
            return null;
        }
        this.retriveToNode = applyReturnPath.getDestUserTask();
        return applyReturnPath.getUserAction();
    }
}

