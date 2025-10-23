/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionStatus
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventFinishedResult
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventOperateInfo
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.service.execute.runtime.EventOperateInfo
 *  org.json.JSONObject
 */
package com.jiuqi.nr.workflow2.events.executor;

import com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionStatus;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventFinishedResult;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventOperateInfo;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.events.executor.EventDependentServiceHelper;
import com.jiuqi.nr.workflow2.events.executor.StepByStepRejectEventExecutor;
import com.jiuqi.nr.workflow2.service.execute.runtime.EventOperateInfo;
import org.json.JSONObject;

public class StepByStepRetrieveRejectEventExecutor
extends StepByStepRejectEventExecutor {
    public StepByStepRetrieveRejectEventExecutor(JSONObject eventJsonConfig, EventDependentServiceHelper helper) {
        super(eventJsonConfig, helper);
    }

    @Override
    public IEventFinishedResult executionEvent(IProcessAsyncMonitor monitor, IOperateResultSet operateResultSet, IActionArgs actionArgs, IBusinessKey businessKey) throws Exception {
        IEventFinishedResult eventFinishedResult = super.executionEvent(monitor, operateResultSet, actionArgs, businessKey);
        if (EventExecutionStatus.FINISH == eventFinishedResult.getFinishStatus()) {
            return eventFinishedResult;
        }
        IEventOperateInfo operateResult = operateResultSet.getOperateResult(businessKey.getBusinessObject());
        operateResultSet.setOperateResult(businessKey.getBusinessObject(), (IEventOperateInfo)new EventOperateInfo(operateResult.getCheckResult(), "apply-return-error", (Object)"upload_ing"));
        return eventFinishedResult;
    }
}

