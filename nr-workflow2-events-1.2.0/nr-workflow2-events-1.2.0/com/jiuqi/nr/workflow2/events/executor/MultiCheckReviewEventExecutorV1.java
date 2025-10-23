/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.dataentry.bean.BatchExecuteTaskParam
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionAffect
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionStatus
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventFinishedResult
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventOperateInfo
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.WFMonitorCheckResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.service.execute.runtime.EventFinishedResult
 *  com.jiuqi.nr.workflow2.service.execute.runtime.EventOperateInfo
 *  org.json.JSONObject
 */
package com.jiuqi.nr.workflow2.events.executor;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.dataentry.bean.BatchExecuteTaskParam;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionAffect;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionStatus;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventFinishedResult;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventOperateInfo;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.WFMonitorCheckResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.events.executor.AbstractActionEventExecutor;
import com.jiuqi.nr.workflow2.events.executor.EventDependentServiceHelper;
import com.jiuqi.nr.workflow2.events.monitor.ProcessAsyncTaskMonitor;
import com.jiuqi.nr.workflow2.service.execute.runtime.EventFinishedResult;
import com.jiuqi.nr.workflow2.service.execute.runtime.EventOperateInfo;
import org.json.JSONObject;

public class MultiCheckReviewEventExecutorV1
extends AbstractActionEventExecutor {
    protected EventDependentServiceHelper helper;

    public MultiCheckReviewEventExecutorV1(JSONObject eventJsonConfig, EventDependentServiceHelper helper) {
        super(eventJsonConfig);
        this.helper = helper;
    }

    public IEventFinishedResult executionEvent(IProcessAsyncMonitor monitor, IOperateResultSet operateResultSet, IActionArgs actionArgs, IBusinessKey businessKey) {
        String checkResultDetail = this.helper.finalAccountsAuditService.comprehensiveAudit(this.getExecuteTaskParam(actionArgs), (AsyncTaskMonitor)new ProcessAsyncTaskMonitor(monitor));
        WFMonitorCheckResult wfMonitorCheckResult = checkResultDetail != null ? WFMonitorCheckResult.CHECK_UN_PASS : WFMonitorCheckResult.CHECK_PASS;
        operateResultSet.setOperateResult((Object)checkResultDetail);
        operateResultSet.setOperateResult(businessKey.getBusinessObject(), (IEventOperateInfo)new EventOperateInfo(wfMonitorCheckResult, "finalaccountsaudit1"));
        EventExecutionStatus finishStatus = WFMonitorCheckResult.CHECK_PASS == wfMonitorCheckResult ? EventExecutionStatus.FINISH : EventExecutionStatus.STOP;
        return new EventFinishedResult(finishStatus, EventExecutionAffect.IMPACT_REPORTING_CHECK);
    }

    public IEventFinishedResult executionEvent(IProcessAsyncMonitor monitor, IOperateResultSet operateResultSet, IActionArgs actionArgs, IBusinessKeyCollection businessKeyCollection) throws Exception {
        BatchExecuteTaskParam batchExecuteTaskParam = this.getBatchExecuteTaskParam(actionArgs);
        String checkResultDetail = this.helper.finalAccountsAuditService.bathComprehensiveAudit(batchExecuteTaskParam, (AsyncTaskMonitor)new ProcessAsyncTaskMonitor(monitor));
        WFMonitorCheckResult wfMonitorCheckResult = checkResultDetail != null ? WFMonitorCheckResult.CHECK_UN_PASS : WFMonitorCheckResult.CHECK_PASS;
        operateResultSet.setOperateResult((Object)checkResultDetail);
        EventExecutionStatus finishStatus = WFMonitorCheckResult.CHECK_PASS == wfMonitorCheckResult ? EventExecutionStatus.FINISH : EventExecutionStatus.STOP;
        return new EventFinishedResult(finishStatus, EventExecutionAffect.IMPACT_REPORTING_CHECK);
    }
}

