/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.dataflow.serviceImpl;

import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.dataflow.service.IDataentryQueryStateService;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowAction;
import com.jiuqi.nr.bpm.de.dataflow.service.IOtherAction;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractQueryState
implements IDataentryQueryStateService {
    @Autowired(required=false)
    Map<String, IOtherAction> otherActionResult;

    public List<WorkflowAction> otherWorkflowAction(List<Task> tasks, BusinessKey businessKey) {
        ArrayList<WorkflowAction> otherActions = new ArrayList<WorkflowAction>();
        if (this.otherActionResult != null) {
            for (Map.Entry<String, IOtherAction> todo : this.otherActionResult.entrySet()) {
                WorkflowAction otherAction;
                IOtherAction otherActionMethod = todo.getValue();
                boolean enabled = otherActionMethod.enabled(tasks, businessKey);
                if (!enabled || (otherAction = otherActionMethod.otherAction(tasks, businessKey)) == null || otherAction.getCode() == null) continue;
                otherActions.add(otherAction);
            }
            return otherActions;
        }
        return null;
    }
}

