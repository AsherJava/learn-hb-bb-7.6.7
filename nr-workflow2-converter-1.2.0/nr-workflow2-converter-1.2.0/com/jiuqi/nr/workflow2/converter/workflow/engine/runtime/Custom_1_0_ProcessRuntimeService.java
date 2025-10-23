/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.monitor.IProgressMonitor
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.workflow2.engine.core.IProcessRuntimeService
 *  com.jiuqi.nr.workflow2.engine.core.actor.IActor
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBizObjectOperateResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyDependencies
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IInstanceIdOperateResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessInstance
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessOperation
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessTask
 */
package com.jiuqi.nr.workflow2.converter.workflow.engine.runtime;

import com.jiuqi.bi.monitor.IProgressMonitor;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.workflow2.converter.workflow.engine.runtime.Default_1_0_ProcessRuntimeService;
import com.jiuqi.nr.workflow2.engine.core.IProcessRuntimeService;
import com.jiuqi.nr.workflow2.engine.core.actor.IActor;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBizObjectOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyDependencies;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IInstanceIdOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessInstance;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessOperation;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessTask;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Custom_1_0_ProcessRuntimeService
implements IProcessRuntimeService {
    @Autowired
    private Default_1_0_ProcessRuntimeService default_1_0_processRuntimeService;

    public IProcessInstance startProcessInstance(String processDefinitionId, IBusinessKey businessKey) {
        return null;
    }

    public IBizObjectOperateResult<Void> batchStartProcessInstance(String processDefinitionId, IBusinessKeyCollection businessKeys) {
        return null;
    }

    public IBizObjectOperateResult<Void> batchStartProcessInstance(String processDefinitionId, IBusinessKeyCollection businessKeys, IProgressMonitor monitor) {
        return null;
    }

    public void deleteProcessInstance(IBusinessKey businessKey) {
    }

    public IBizObjectOperateResult<Void> batchDeleteProcessInstance(IBusinessKeyCollection businessKeys) {
        return null;
    }

    public IBizObjectOperateResult<Void> batchDeleteProcessInstance(IBusinessKeyCollection businessKeys, IProgressMonitor monitor) {
        return null;
    }

    public void refreshProcessInstance(IBusinessKey businessKey) {
    }

    public void batchRefreshProcessInstance(IBusinessKeyCollection businessKeyCollection) {
    }

    public void batchRefreshProcessInstance(IBusinessKeyCollection businessKeyCollection, IProgressMonitor monitor) {
    }

    public IProcessInstance queryProcessInstance(IBusinessKey businessKey) {
        return null;
    }

    public IBizObjectOperateResult<IProcessInstance> batchQueryProcessInstances(IBusinessKeyCollection businessKeyCollection) {
        return null;
    }

    public IProcessStatus queryStatus(IBusinessKey businessKey) {
        return null;
    }

    public IBizObjectOperateResult<IProcessStatus> batchQueryStatus(IBusinessKeyCollection businessKeyCollection) {
        return null;
    }

    public List<IProcessTask> queryCurrentTask(IBusinessKey businessKey, IActor actor) {
        return Collections.emptyList();
    }

    public boolean existsInstance(String taskKey) {
        return this.default_1_0_processRuntimeService.existsInstance(taskKey);
    }

    public void completeTask(IBusinessKey businessKey, String taskId, String actionCode, IActor actor, IActionArgs args) {
    }

    public void completeTask(String taskKey, String period, String instanceId, String taskId, String actionCode, IActor actor, IActionArgs args) {
    }

    public IBizObjectOperateResult<Void> queryCanCompleteInstance(IBusinessKeyCollection businessKeyCollection, String userTaskCode, String actionCode, IActor actor, IActionArgs args) {
        return null;
    }

    public IBizObjectOperateResult<Void> batchCompleteTask(IBusinessKeyCollection businessKeyCollection, String userTaskCode, String actionCode, IActor actor, IActionArgs args, IBusinessKeyDependencies dependencies) {
        return null;
    }

    public IBizObjectOperateResult<Void> batchCompleteTask(IBusinessKeyCollection businessKeyCollection, String userTaskCode, String actionCode, IActor actor, IActionArgs args, IBusinessKeyDependencies dependencies, IProgressMonitor monitor) {
        return null;
    }

    public IInstanceIdOperateResult<Void> batchCompleteTask(String taskKey, String period, Collection<String> instanceIds, String userTaskCode, String actionCode, IActor actor, IActionArgs args) {
        return null;
    }

    public IInstanceIdOperateResult<Void> batchCompleteTask(String taskKey, String period, Collection<String> instanceIds, String userTaskCode, String actionCode, IActor actor, IActionArgs args, IProgressMonitor monitor) {
        return null;
    }

    public List<IProcessOperation> queryInstanceHistory(IBusinessKey businessKey) {
        return Collections.emptyList();
    }

    public List<String> getMatchingActors(IBusinessKey businessKey) {
        return Collections.emptyList();
    }

    public Map<DimensionCombination, IProcessStatus> queryEntityStatus(String taskKey, String period, DimensionCollection dimensions) {
        return Collections.emptyMap();
    }
}

