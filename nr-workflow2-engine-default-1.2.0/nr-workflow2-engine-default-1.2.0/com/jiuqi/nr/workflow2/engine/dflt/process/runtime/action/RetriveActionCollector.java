/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectSet
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKeyCollection
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime.action;

import com.jiuqi.nr.workflow2.engine.common.definition.model.RetriveActionPath;
import com.jiuqi.nr.workflow2.engine.common.definition.model.UserAction;
import com.jiuqi.nr.workflow2.engine.common.definition.model.UserTask;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectSet;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.ActorStrategyUtil;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.action.ActionCollector;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessOperationDO;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessOperationQuery;
import com.jiuqi.nr.workflow2.engine.dflt.utils.LogUtils;
import java.util.List;

public class RetriveActionCollector
extends ActionCollector {
    private String instanceId;
    private ProcessOperationQuery optQuery;
    private UserTask retriveToNode;
    private String retriveToStatusCode;
    private String retriveToOperationId;

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public void setOperationQuery(ProcessOperationQuery optQuery) {
        this.optQuery = optQuery;
    }

    public UserTask getRetriveToNode() {
        return this.retriveToNode;
    }

    public String getRetriveToStatus() {
        return this.retriveToStatusCode;
    }

    public String getRetriveToOperationId() {
        return this.retriveToOperationId;
    }

    @Override
    public void collectActions(List<IUserAction> userActions, RuntimeBusinessKey rtBusinessKey) {
        UserAction retriveAction = this.findRetriveAction();
        if (retriveAction != null && this.actorIsMatch(rtBusinessKey)) {
            userActions.add(retriveAction);
        }
    }

    @Override
    public boolean canAct(RuntimeBusinessKey rtBusinessKey, String actionCode, IActionArgs actionArgs) {
        UserAction retriveAction = this.findRetriveAction();
        if (retriveAction != null) {
            return this.actorIsMatch(rtBusinessKey);
        }
        return false;
    }

    @Override
    public IBusinessObjectSet getCanActBusinessKeys(RuntimeBusinessKeyCollection rtBbusinessKeys, String actionCode) {
        throw new RuntimeException("\u4e0d\u652f\u6301\u53d6\u56de\u64cd\u4f5c\u6279\u91cf\u6743\u9650\u5224\u65ad\uff0c\u8bf7\u4f7f\u7528RetriveActionCollector#canAct\u51fd\u6570\u9010\u4e2a\u5224\u65ad\u3002");
    }

    private boolean actorIsMatch(RuntimeBusinessKey rtBusinessKey) {
        return ActorStrategyUtil.getInstance().actorIsMatch(this.retriveToNode.getActionExecutors(), this.actor, rtBusinessKey);
    }

    public UserAction findRetriveAction() {
        int r;
        int continuousRetriveCount;
        RetriveActionPath retrivePath = this.currentUserTask.getRetriveActionPath();
        if (retrivePath == null) {
            return null;
        }
        List<ProcessOperationDO> operations = this.optQuery.queryOperations(this.instanceId);
        if (operations.isEmpty()) {
            return null;
        }
        for (r = operations.size(); r > 0; r -= 2 * continuousRetriveCount) {
            continuousRetriveCount = 0;
            for (int j = r - 1; j >= 0 && operations.get(j).getAction().equals("act_retrieve"); --j) {
                ++continuousRetriveCount;
            }
            if (continuousRetriveCount == 0) break;
        }
        if (r <= 0) {
            return null;
        }
        ProcessOperationDO tobeRetriveOperation = operations.get(r - 1);
        if (!this.currentUserTask.getCode().equals(tobeRetriveOperation.getToNode())) {
            LogUtils.LOGGER.warn("\u6d41\u7a0b\u6570\u636e\u5b58\u5728\u4e00\u81f4\u6027\u9519\u8bef\uff0c\u65e0\u6cd5\u5e94\u7528\u53d6\u56de\u64cd\u4f5c\u3002\u6d41\u7a0b\u5b9e\u4f8b\uff1a" + this.instanceId + " \u5f53\u524d\u8282\u70b9\uff1a" + this.currentUserTask.getCode() + " \u64cd\u4f5c\u5386\u53f2\u6700\u65b0\u8282\u70b9\uff1a" + tobeRetriveOperation.getToNode());
            return null;
        }
        if (!retrivePath.getDestUserTask().getCode().equals(tobeRetriveOperation.getFromNode())) {
            return null;
        }
        this.retriveToNode = retrivePath.getDestUserTask();
        --r;
        while (r > 0) {
            int continuousRetriveCount2 = 0;
            for (int j = r - 1; j >= 0 && operations.get(j).getAction().equals("act_retrieve"); --j) {
                ++continuousRetriveCount2;
            }
            if (continuousRetriveCount2 == 0) break;
            r -= 2 * continuousRetriveCount2;
        }
        if (r <= 0) {
            return null;
        }
        ProcessOperationDO retriveToOperation = operations.get(r - 1);
        this.retriveToStatusCode = retriveToOperation.getNewStatus();
        this.retriveToOperationId = retriveToOperation.getId();
        return retrivePath.getUserAction();
    }
}

