/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
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
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormObject
 *  com.jiuqi.nr.workflow2.engine.dflt.process.io.dimension.OperateBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.service.execute.runtime.EventOperateInfo
 *  com.jiuqi.nr.workflow2.service.para.IProcessExecutePara
 *  com.jiuqi.nr.workflow2.service.para.IProcessRunPara
 */
package com.jiuqi.nr.workflow2.form.reject.ext.service;

import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
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
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormObject;
import com.jiuqi.nr.workflow2.engine.dflt.process.io.dimension.OperateBusinessKeyCollection;
import com.jiuqi.nr.workflow2.form.reject.ext.service.StepByStepFormRejectEventExecutor;
import com.jiuqi.nr.workflow2.service.execute.runtime.EventOperateInfo;
import com.jiuqi.nr.workflow2.service.para.IProcessExecutePara;
import com.jiuqi.nr.workflow2.service.para.IProcessRunPara;

public class StepByStepFormRejectEventWithParentExecutor
extends StepByStepFormRejectEventExecutor {
    public StepByStepFormRejectEventWithParentExecutor(IProcessExecutePara envParam) {
        super(envParam);
    }

    @Override
    public IEventFinishedResult executionEvent(IProcessAsyncMonitor monitor, IOperateResultSet operateResultSet, IActionArgs actionArgs, IBusinessKey businessKey) throws Exception {
        String[] parentsEntityKeyDataPath;
        IEntityTable entityTable = this.getEntityTable((IProcessRunPara)this.envParam, AuthorityType.None);
        IBusinessObject businessObject = businessKey.getBusinessObject();
        String unitId = businessObject.getDimensions().getDWDimensionValue().getValue().toString();
        for (String parentEntityKey : parentsEntityKeyDataPath = entityTable.getParentsEntityKeyDataPath(unitId)) {
            IEntityRow parentEntityRow = entityTable.findByEntityKey(parentEntityKey);
            IFormObject parentBusinessObject = this.toBusinessObject(businessObject, parentEntityRow);
            operateResultSet.setOperateResult((IBusinessObject)parentBusinessObject, (IEventOperateInfo)new EventOperateInfo(WFMonitorCheckResult.CHECK_PASS));
        }
        OperateBusinessKeyCollection operateBusinessKeyCollection = new OperateBusinessKeyCollection(this.envParam.getTaskKey(), operateResultSet.allCheckPassBusinessObjects());
        return super.executionEvent(monitor, operateResultSet, actionArgs, (IBusinessKeyCollection)operateBusinessKeyCollection);
    }

    @Override
    public IEventFinishedResult executionEvent(IProcessAsyncMonitor monitor, IOperateResultSet operateResultSet, IActionArgs actionArgs, IBusinessKeyCollection businessKeyCollection) throws Exception {
        IEntityTable entityTable = this.getEntityTable((IProcessRunPara)this.envParam, AuthorityType.None);
        IBusinessObjectCollection businessObjects = businessKeyCollection.getBusinessObjects();
        for (IBusinessObject businessObject : businessObjects) {
            String[] parentsEntityKeyDataPath;
            String unitId = businessObject.getDimensions().getDWDimensionValue().getValue().toString();
            for (String parentEntityKey : parentsEntityKeyDataPath = entityTable.getParentsEntityKeyDataPath(unitId)) {
                IEntityRow parentEntityRow = entityTable.findByEntityKey(parentEntityKey);
                IFormObject parentBusinessObject = this.toBusinessObject(businessObject, parentEntityRow);
                operateResultSet.setOperateResult((IBusinessObject)parentBusinessObject, (IEventOperateInfo)new EventOperateInfo(WFMonitorCheckResult.CHECK_PASS));
            }
        }
        OperateBusinessKeyCollection operateBusinessKeyCollection = new OperateBusinessKeyCollection(this.envParam.getTaskKey(), operateResultSet.allCheckPassBusinessObjects());
        return super.executionEvent(monitor, operateResultSet, actionArgs, (IBusinessKeyCollection)operateBusinessKeyCollection);
    }
}

