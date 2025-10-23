/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.nr.workflow2.service.impl.ProcessInstanceService
 *  com.jiuqi.nr.workflow2.service.para.IProcessRunPara
 */
package com.jiuqi.nr.workflow2.form.reject.ext.service;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.form.reject.service.IFormRejectRunTimeService;
import com.jiuqi.nr.workflow2.service.impl.ProcessInstanceService;
import com.jiuqi.nr.workflow2.service.para.IProcessRunPara;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;

public class FormRejectExtInstancesService
extends ProcessInstanceService {
    @Autowired
    private IFormRejectRunTimeService rejectRunTimeService;

    public void startInstances(IProcessRunPara runEnvPara, IBusinessKeyCollection businessKeyCollection, IProcessAsyncMonitor monitor, IOperateResultSet operateResultSet) {
        super.startInstances(runEnvPara, businessKeyCollection, monitor, operateResultSet);
    }

    public void clearInstances(IProcessRunPara runEnvPara, IBusinessKeyCollection businessKeyCollection, IProcessAsyncMonitor monitor, IOperateResultSet operateResultSet) {
        super.clearInstances(runEnvPara, businessKeyCollection, monitor, operateResultSet);
        WorkflowObjectType workflowObjectType = this.settingService.queryTaskWorkflowObjectType(runEnvPara.getTaskKey());
        if (WorkflowObjectType.MD_WITH_SFR == workflowObjectType) {
            ArrayList<DimensionCombination> combinations = new ArrayList<DimensionCombination>();
            IBusinessObjectCollection businessObjects = businessKeyCollection.getBusinessObjects();
            for (IBusinessObject businessObject : businessObjects) {
                combinations.add(businessObject.getDimensions());
            }
            this.rejectRunTimeService.clearFormRejectRecord(runEnvPara.getTaskKey(), runEnvPara.getPeriod(), combinations);
        }
    }

    public void refreshActors(IProcessRunPara runEnvPara, IBusinessKeyCollection businessKeyCollection, IProcessAsyncMonitor monitor, IOperateResultSet operateResultSet) {
        super.refreshActors(runEnvPara, businessKeyCollection, monitor, operateResultSet);
    }
}

