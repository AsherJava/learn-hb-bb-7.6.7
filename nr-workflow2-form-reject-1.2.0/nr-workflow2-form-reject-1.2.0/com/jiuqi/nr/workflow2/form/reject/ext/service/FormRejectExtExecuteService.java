/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBizObjectOperateResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IOperateResult
 *  com.jiuqi.nr.workflow2.engine.dflt.process.io.dimension.ProcessDimensionCollection
 *  com.jiuqi.nr.workflow2.service.enumeration.ProcessExecuteStatus
 *  com.jiuqi.nr.workflow2.service.execute.runtime.IEventOperateResult
 *  com.jiuqi.nr.workflow2.service.impl.ProcessExecuteService
 *  com.jiuqi.nr.workflow2.service.para.IProcessExecutePara
 *  com.jiuqi.nr.workflow2.service.para.IProcessRunPara
 *  com.jiuqi.nr.workflow2.service.result.IProcessExecuteResult
 */
package com.jiuqi.nr.workflow2.form.reject.ext.service;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBizObjectOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IOperateResult;
import com.jiuqi.nr.workflow2.engine.dflt.process.io.dimension.ProcessDimensionCollection;
import com.jiuqi.nr.workflow2.form.reject.ext.service.FormRejectExecuteService;
import com.jiuqi.nr.workflow2.form.reject.ext.service.IFormRejectJudgeHelper;
import com.jiuqi.nr.workflow2.form.reject.service.IFormRejectRunTimeService;
import com.jiuqi.nr.workflow2.service.enumeration.ProcessExecuteStatus;
import com.jiuqi.nr.workflow2.service.execute.runtime.IEventOperateResult;
import com.jiuqi.nr.workflow2.service.impl.ProcessExecuteService;
import com.jiuqi.nr.workflow2.service.para.IProcessExecutePara;
import com.jiuqi.nr.workflow2.service.para.IProcessRunPara;
import com.jiuqi.nr.workflow2.service.result.IProcessExecuteResult;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class FormRejectExtExecuteService
extends ProcessExecuteService {
    @Autowired
    private IFormRejectJudgeHelper judgeHelper;
    @Autowired
    private IFormRejectRunTimeService rejectRunTimeService;
    @Autowired
    private FormRejectExecuteService formRejectExecuteService;

    public IProcessExecuteResult executeProcess(IProcessExecutePara executePara, IBusinessKey businessKey, IProcessAsyncMonitor monitor, IEventOperateResult resultManager) throws Exception {
        if (this.judgeHelper.isFormRejectAction(executePara) && this.judgeHelper.isInstanceAtAuditAndUpload((IProcessRunPara)executePara, businessKey)) {
            IProcessExecuteResult executeResult = super.executeProcess(executePara, businessKey, monitor, resultManager);
            if (ProcessExecuteStatus.SUCCESS == executeResult.getExecuteStatus()) {
                return this.formRejectExecuteService.executeProcess(executePara, businessKey.getBusinessObject().getDimensions(), monitor);
            }
            return executeResult;
        }
        if (this.judgeHelper.isFormRejectAction(executePara) && this.judgeHelper.isInstanceAtReportAndReject((IProcessRunPara)executePara, businessKey)) {
            return this.formRejectExecuteService.executeProcess(executePara, businessKey.getBusinessObject().getDimensions(), monitor);
        }
        IProcessExecuteResult executeResult = super.executeProcess(executePara, businessKey, monitor, resultManager);
        if (ProcessExecuteStatus.SUCCESS == executeResult.getExecuteStatus() && this.judgeHelper.isFormRejectMode((IProcessRunPara)executePara) && "act_upload".equals(executePara.getActionCode())) {
            List<DimensionCombination> combinations = Collections.singletonList(businessKey.getBusinessObject().getDimensions());
            this.rejectRunTimeService.clearFormRejectRecord(executePara.getTaskKey(), executePara.getPeriod(), combinations);
        }
        return executeResult;
    }

    public IProcessExecuteResult executeProcess(IProcessExecutePara executePara, IBusinessKeyCollection businessKeyCollection, IProcessAsyncMonitor monitor, IEventOperateResult resultManager) throws Exception {
        if (this.judgeHelper.isFormRejectMode((IProcessRunPara)executePara) && this.judgeHelper.isFormRejectAction(executePara)) {
            executePara.setUserTaskCode("tsk_audit");
            IProcessExecuteResult executeResult = super.executeProcess(executePara, businessKeyCollection, monitor, resultManager);
            if (ProcessExecuteStatus.SUCCESS == executeResult.getExecuteStatus()) {
                List<DimensionCombination> combinations = this.getReportedCombinations(executePara, businessKeyCollection, "rejected");
                ProcessDimensionCollection dimensionCollection = new ProcessDimensionCollection(combinations);
                return this.formRejectExecuteService.executeProcess(executePara, (DimensionCollection)dimensionCollection, monitor);
            }
            return executeResult;
        }
        IProcessExecuteResult executeResult = super.executeProcess(executePara, businessKeyCollection, monitor, resultManager);
        if (ProcessExecuteStatus.SUCCESS == executeResult.getExecuteStatus() && this.judgeHelper.isFormRejectMode((IProcessRunPara)executePara) && "act_upload".equals(executePara.getActionCode())) {
            List<DimensionCombination> combinations = this.getReportedCombinations(executePara, businessKeyCollection, "reported");
            this.rejectRunTimeService.clearFormRejectRecord(executePara.getTaskKey(), executePara.getPeriod(), combinations);
        }
        return executeResult;
    }

    protected List<DimensionCombination> getReportedCombinations(IProcessExecutePara executePara, IBusinessKeyCollection businessKeyCollection, String statueCode) {
        ArrayList<DimensionCombination> combinations = new ArrayList<DimensionCombination>();
        IBizObjectOperateResult operateResult = this.processQueryService.queryInstanceState((IProcessRunPara)executePara, businessKeyCollection);
        Iterable businessObjects = operateResult.getBusinessObjects();
        for (IBusinessObject businessObject : businessObjects) {
            IOperateResult stateResult = operateResult.getResult((Object)businessObject);
            if (!stateResult.isSuccessful() || !statueCode.equalsIgnoreCase(((IProcessStatus)stateResult.getResult()).getCode())) continue;
            combinations.add(businessObject.getDimensions());
        }
        return combinations;
    }
}

