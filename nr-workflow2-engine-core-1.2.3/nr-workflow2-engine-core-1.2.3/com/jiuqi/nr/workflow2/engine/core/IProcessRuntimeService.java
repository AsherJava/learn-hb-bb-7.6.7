/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.monitor.IProgressMonitor
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.workflow2.engine.core;

import com.jiuqi.bi.monitor.IProgressMonitor;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.workflow2.engine.core.actor.IActor;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBizObjectOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyDependencies;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IInstanceIdOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessInstance;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessOperation;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessStatusWithOperation;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessTask;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BizObjectOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.OperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.ProcessStatusWithOperation;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IProcessRuntimeService {
    public IProcessInstance startProcessInstance(String var1, IBusinessKey var2);

    public IBizObjectOperateResult<Void> batchStartProcessInstance(String var1, IBusinessKeyCollection var2);

    public IBizObjectOperateResult<Void> batchStartProcessInstance(String var1, IBusinessKeyCollection var2, IProgressMonitor var3);

    public void deleteProcessInstance(IBusinessKey var1);

    public IBizObjectOperateResult<Void> batchDeleteProcessInstance(IBusinessKeyCollection var1);

    public IBizObjectOperateResult<Void> batchDeleteProcessInstance(IBusinessKeyCollection var1, IProgressMonitor var2);

    public void refreshProcessInstance(IBusinessKey var1);

    public void batchRefreshProcessInstance(IBusinessKeyCollection var1);

    public void batchRefreshProcessInstance(IBusinessKeyCollection var1, IProgressMonitor var2);

    public IProcessInstance queryProcessInstance(IBusinessKey var1);

    public IBizObjectOperateResult<IProcessInstance> batchQueryProcessInstances(IBusinessKeyCollection var1);

    public IProcessStatus queryStatus(IBusinessKey var1);

    public IBizObjectOperateResult<IProcessStatus> batchQueryStatus(IBusinessKeyCollection var1);

    public List<IProcessTask> queryCurrentTask(IBusinessKey var1, IActor var2);

    public boolean existsInstance(String var1);

    public void completeTask(IBusinessKey var1, String var2, String var3, IActor var4, IActionArgs var5);

    public void completeTask(String var1, String var2, String var3, String var4, String var5, IActor var6, IActionArgs var7);

    public IBizObjectOperateResult<Void> queryCanCompleteInstance(IBusinessKeyCollection var1, String var2, String var3, IActor var4, IActionArgs var5);

    public IBizObjectOperateResult<Void> batchCompleteTask(IBusinessKeyCollection var1, String var2, String var3, IActor var4, IActionArgs var5, IBusinessKeyDependencies var6);

    public IBizObjectOperateResult<Void> batchCompleteTask(IBusinessKeyCollection var1, String var2, String var3, IActor var4, IActionArgs var5, IBusinessKeyDependencies var6, IProgressMonitor var7);

    public IInstanceIdOperateResult<Void> batchCompleteTask(String var1, String var2, Collection<String> var3, String var4, String var5, IActor var6, IActionArgs var7);

    public IInstanceIdOperateResult<Void> batchCompleteTask(String var1, String var2, Collection<String> var3, String var4, String var5, IActor var6, IActionArgs var7, IProgressMonitor var8);

    public List<IProcessOperation> queryInstanceHistory(IBusinessKey var1);

    public List<String> getMatchingActors(IBusinessKey var1);

    public Map<DimensionCombination, IProcessStatus> queryEntityStatus(String var1, String var2, DimensionCollection var3);

    default public IProcessStatusWithOperation queryStatusWithOperation(IBusinessKey businessKey) {
        return new ProcessStatusWithOperation(this.queryStatus(businessKey), null);
    }

    default public IBizObjectOperateResult<IProcessStatusWithOperation> batchQueryStatusWithOperation(IBusinessKeyCollection businessKeyCollection) {
        IBizObjectOperateResult<IProcessStatus> statusQueryResult = this.batchQueryStatus(businessKeyCollection);
        BizObjectOperateResult<IProcessStatusWithOperation> result = new BizObjectOperateResult<IProcessStatusWithOperation>();
        for (IBusinessObject bizObject : businessKeyCollection.getBusinessObjects()) {
            IOperateResult curResult = statusQueryResult.getResult(bizObject);
            if (curResult.isSuccessful()) {
                ProcessStatusWithOperation statusWithOperation = new ProcessStatusWithOperation((IProcessStatus)curResult.getResult(), null);
                result.appendResult(bizObject, OperateResult.newSuccessResult(statusWithOperation));
                continue;
            }
            result.appendResult(bizObject, OperateResult.newFailResult(curResult.getError()));
        }
        return result;
    }
}

