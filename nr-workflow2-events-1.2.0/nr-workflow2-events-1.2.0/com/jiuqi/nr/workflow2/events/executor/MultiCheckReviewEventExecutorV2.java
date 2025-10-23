/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.multcheck2.common.CheckSource
 *  com.jiuqi.nr.multcheck2.service.IMCMonitor
 *  com.jiuqi.nr.multcheck2.web.result.MCUploadResult
 *  com.jiuqi.nr.multcheck2.web.vo.MCLabel
 *  com.jiuqi.nr.multcheck2.web.vo.MCRunVO
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
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.JavaBeanUtils
 *  com.jiuqi.nr.workflow2.service.execute.runtime.EventFinishedResult
 *  com.jiuqi.nr.workflow2.service.execute.runtime.EventOperateInfo
 *  com.jiuqi.nr.workflow2.service.para.ProcessExecuteEnv
 *  org.json.JSONObject
 */
package com.jiuqi.nr.workflow2.events.executor;

import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.multcheck2.common.CheckSource;
import com.jiuqi.nr.multcheck2.service.IMCMonitor;
import com.jiuqi.nr.multcheck2.web.result.MCUploadResult;
import com.jiuqi.nr.multcheck2.web.vo.MCLabel;
import com.jiuqi.nr.multcheck2.web.vo.MCRunVO;
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
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.JavaBeanUtils;
import com.jiuqi.nr.workflow2.events.executor.AbstractActionEventExecutor;
import com.jiuqi.nr.workflow2.events.executor.EventDependentServiceHelper;
import com.jiuqi.nr.workflow2.events.monitor.ProcessMCRunMonitor;
import com.jiuqi.nr.workflow2.service.execute.runtime.EventFinishedResult;
import com.jiuqi.nr.workflow2.service.execute.runtime.EventOperateInfo;
import com.jiuqi.nr.workflow2.service.para.ProcessExecuteEnv;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.json.JSONObject;

public class MultiCheckReviewEventExecutorV2
extends AbstractActionEventExecutor {
    protected EventDependentServiceHelper helper;

    public MultiCheckReviewEventExecutorV2(JSONObject eventJsonConfig, EventDependentServiceHelper helper) {
        super(eventJsonConfig);
        this.helper = helper;
    }

    public IEventFinishedResult executionEvent(IProcessAsyncMonitor monitor, IOperateResultSet operateResultSet, IActionArgs actionArgs, IBusinessKey businessKey) throws Exception {
        String unitId = businessKey.getBusinessObject().getDimensions().getDWDimensionValue().getValue().toString();
        String period = businessKey.getBusinessObject().getDimensions().getPeriodDimensionValue().getValue().toString();
        FormSchemeDefine formScheme = this.helper.runtimeParamHelper.getFormScheme(businessKey.getTask(), period);
        MCRunVO vo = new MCRunVO();
        vo.setTask(businessKey.getTask());
        vo.setPeriod(period);
        ArrayList<String> unitIds = new ArrayList<String>();
        unitIds.add(unitId);
        vo.setOrgCodes(unitIds);
        vo.setSource(CheckSource.FLOW);
        vo.setDimSetMap(this.getDimensionValueMap(actionArgs));
        vo.setFormScheme(formScheme.getKey());
        IEntityDefine entityDefine = this.helper.runtimeParamHelper.getProcessEntityDefinition(businessKey.getTask());
        vo.setOrg(entityDefine.getId());
        ProcessMCRunMonitor mcRunMonitor = new ProcessMCRunMonitor(monitor);
        MCUploadResult result = this.helper.singleMCExecuteService.uploadSingleExecute((IMCMonitor)mcRunMonitor, vo);
        WFMonitorCheckResult wfMonitorCheckResult = !result.getFailedList().isEmpty() ? WFMonitorCheckResult.CHECK_UN_PASS : WFMonitorCheckResult.CHECK_PASS;
        operateResultSet.setOperateResult((Object)JavaBeanUtils.toJSONStr((Object)result));
        operateResultSet.setOperateResult(businessKey.getBusinessObject(), (IEventOperateInfo)new EventOperateInfo(wfMonitorCheckResult, "finalaccountsaudit2"));
        EventExecutionStatus finishStatus = WFMonitorCheckResult.CHECK_PASS == wfMonitorCheckResult ? EventExecutionStatus.FINISH : EventExecutionStatus.STOP;
        return new EventFinishedResult(finishStatus, EventExecutionAffect.IMPACT_REPORTING_CHECK);
    }

    public IEventFinishedResult executionEvent(IProcessAsyncMonitor monitor, IOperateResultSet operateResultSet, IActionArgs actionArgs, IBusinessKeyCollection businessKeyCollection) throws Exception {
        ProcessExecuteEnv envParam = this.getEnvParam(actionArgs);
        IBusinessObjectCollection businessObjects = businessKeyCollection.getBusinessObjects();
        FormSchemeDefine formScheme = this.helper.runtimeParamHelper.getFormScheme(businessKeyCollection.getTask(), envParam.getPeriod());
        MCRunVO vo = new MCRunVO();
        vo.setTask(businessKeyCollection.getTask());
        vo.setPeriod(envParam.getPeriod());
        vo.setOrgCodes(this.toUnitIds(businessObjects));
        vo.setSource(CheckSource.FLOW);
        vo.setDimSetMap(this.getDimensionValueMap(actionArgs));
        vo.setFormScheme(formScheme.getKey());
        IEntityDefine entityDefine = this.helper.runtimeParamHelper.getProcessEntityDefinition(businessKeyCollection.getTask());
        vo.setOrg(entityDefine.getId());
        ProcessMCRunMonitor mcRunMonitor = new ProcessMCRunMonitor(monitor);
        MCUploadResult mcUploadResult = this.helper.multiMCExecuteService.uploadMultiExecute((IMCMonitor)mcRunMonitor, vo);
        operateResultSet.setOperateResult((Object)JavaBeanUtils.toJSONStr((Object)mcUploadResult));
        if (mcUploadResult.getErrorMsg() != null) {
            return new EventFinishedResult(EventExecutionStatus.STOP, EventExecutionAffect.IMPACT_REPORTING_CHECK);
        }
        List failedList = mcUploadResult.getFailedList();
        List failedUnitIds = failedList.stream().map(MCLabel::getCode).collect(Collectors.toList());
        for (IBusinessObject businessObject : businessObjects) {
            String unitId = businessObject.getDimensions().getDWDimensionValue().getValue().toString();
            if (failedUnitIds.contains(unitId)) {
                operateResultSet.setOperateResult(businessObject, (IEventOperateInfo)new EventOperateInfo(WFMonitorCheckResult.CHECK_UN_PASS));
                continue;
            }
            operateResultSet.setOperateResult(businessObject, (IEventOperateInfo)new EventOperateInfo(WFMonitorCheckResult.CHECK_PASS));
        }
        return new EventFinishedResult(EventExecutionStatus.FINISH, EventExecutionAffect.IMPACT_REPORTING_CHECK);
    }

    private List<String> toUnitIds(IBusinessObjectCollection businessObjects) {
        HashSet<String> unitIds = new HashSet<String>();
        for (IBusinessObject businessObject : businessObjects) {
            unitIds.add(businessObject.getDimensions().getDWDimensionValue().getValue().toString());
        }
        return new ArrayList<String>(unitIds);
    }
}

