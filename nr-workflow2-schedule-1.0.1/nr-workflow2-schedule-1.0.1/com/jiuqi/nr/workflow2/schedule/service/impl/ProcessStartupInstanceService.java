/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.nr.workflow2.service.IProcessInstanceService
 *  com.jiuqi.nr.workflow2.service.dimension.EProcessDimensionName
 *  com.jiuqi.nr.workflow2.service.helper.IProcessDimensionsBuilder
 *  com.jiuqi.nr.workflow2.service.helper.IReportDimensionHelper
 *  com.jiuqi.nr.workflow2.service.para.EProcessRangeDimType
 *  com.jiuqi.nr.workflow2.service.para.IProcessRunPara
 *  com.jiuqi.nr.workflow2.service.para.ProcessBatchRunPara
 *  com.jiuqi.nr.workflow2.service.para.ProcessRangeDims
 */
package com.jiuqi.nr.workflow2.schedule.service.impl;

import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.schedule.bi.jobs.monitor.IProcessStartupAsyncMonitor;
import com.jiuqi.nr.workflow2.schedule.bi.jobs.param.IProcessStartupRunPara;
import com.jiuqi.nr.workflow2.schedule.service.IProcessStartupInstanceService;
import com.jiuqi.nr.workflow2.service.IProcessInstanceService;
import com.jiuqi.nr.workflow2.service.dimension.EProcessDimensionName;
import com.jiuqi.nr.workflow2.service.helper.IProcessDimensionsBuilder;
import com.jiuqi.nr.workflow2.service.helper.IReportDimensionHelper;
import com.jiuqi.nr.workflow2.service.para.EProcessRangeDimType;
import com.jiuqi.nr.workflow2.service.para.IProcessRunPara;
import com.jiuqi.nr.workflow2.service.para.ProcessBatchRunPara;
import com.jiuqi.nr.workflow2.service.para.ProcessRangeDims;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class ProcessStartupInstanceService
implements IProcessStartupInstanceService {
    @Autowired
    protected WorkflowSettingsService settingsService;
    @Autowired
    protected IProcessInstanceService processInstanceService;
    @Autowired
    protected IReportDimensionHelper reportDimensionHelper;
    @Autowired
    protected IProcessDimensionsBuilder processDimensionsBuilder;

    @Override
    public void startInstances(IProcessStartupRunPara startupRunPara, IOperateResultSet operateResultSet, IProcessStartupAsyncMonitor monitor) {
        this.startWithDefaultEngineVersion_2_0(startupRunPara, operateResultSet, monitor);
    }

    protected void startWithDefaultEngineVersion_2_0(IProcessStartupRunPara startupRunPara, IOperateResultSet operateResultSet, IProcessStartupAsyncMonitor monitor) {
        ProcessBatchRunPara executePara = this.getExecutePara(startupRunPara);
        IBusinessKeyCollection businessKeyCollection = this.processDimensionsBuilder.buildBusinessKeyCollection(executePara);
        this.processInstanceService.startInstances((IProcessRunPara)executePara, businessKeyCollection, (IProcessAsyncMonitor)monitor, operateResultSet);
    }

    protected ProcessBatchRunPara getExecutePara(IProcessStartupRunPara startupRunPara) {
        ProcessBatchRunPara executePara = new ProcessBatchRunPara();
        executePara.setTaskKey(startupRunPara.getTaskKey());
        executePara.setPeriod(startupRunPara.getPeriod());
        List reportDimensions = this.reportDimensionHelper.getAllReportDimensions(startupRunPara.getTaskKey());
        for (DataDimension dimension : reportDimensions) {
            if (DimensionType.PERIOD == dimension.getDimensionType()) continue;
            ProcessRangeDims processRangeDims = new ProcessRangeDims();
            String dimensionName = this.reportDimensionHelper.getDimensionName(dimension);
            processRangeDims.setDimensionName(dimensionName);
            processRangeDims.setDimensionKey(dimension.getDimKey());
            processRangeDims.setRangeType(EProcessRangeDimType.ALL);
            executePara.getReportDimensions().add(processRangeDims);
        }
        WorkflowObjectType workflowObjectType = this.settingsService.queryTaskWorkflowObjectType(startupRunPara.getTaskKey());
        if (workflowObjectType.equals((Object)WorkflowObjectType.FORM)) {
            ProcessRangeDims formRangeDims = new ProcessRangeDims();
            formRangeDims.setDimensionName(EProcessDimensionName.PROCESS_FORM.dimName);
            formRangeDims.setDimensionKey(EProcessDimensionName.PROCESS_FORM.dimName);
            formRangeDims.setRangeType(EProcessRangeDimType.ALL);
            executePara.getReportDimensions().add(formRangeDims);
        } else if (workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP)) {
            ProcessRangeDims formGroupRangeDims = new ProcessRangeDims();
            formGroupRangeDims.setDimensionName(EProcessDimensionName.PROCESS_GROUP.dimName);
            formGroupRangeDims.setDimensionKey(EProcessDimensionName.PROCESS_GROUP.dimName);
            formGroupRangeDims.setRangeType(EProcessRangeDimType.ALL);
            executePara.getReportDimensions().add(formGroupRangeDims);
        }
        return executePara;
    }
}

