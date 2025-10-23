/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBizObjectOperateResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessInstance
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessOperation
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessStatusWithOperation
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessTask
 */
package com.jiuqi.nr.workflow2.service;

import com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBizObjectOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessInstance;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessOperation;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessStatusWithOperation;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessTask;
import com.jiuqi.nr.workflow2.service.para.IProcessRunPara;
import java.util.List;

public interface IProcessQueryService {
    public IProcessInstance queryInstances(IProcessRunPara var1, IBusinessKey var2);

    public IBizObjectOperateResult<IProcessInstance> queryInstances(IProcessRunPara var1, IBusinessKeyCollection var2);

    public IProcessStatus queryInstanceState(IProcessRunPara var1, IBusinessKey var2);

    public IProcessStatusWithOperation queryInstanceStateWithOperation(IProcessRunPara var1, IBusinessKey var2);

    public IBizObjectOperateResult<IProcessStatus> queryInstanceState(IProcessRunPara var1, IBusinessKeyCollection var2);

    public IBizObjectOperateResult<IProcessStatusWithOperation> queryInstanceStateWithOperation(IProcessRunPara var1, IBusinessKeyCollection var2);

    public IProcessStatus queryUnitState(IProcessRunPara var1, IBusinessKey var2);

    public IBizObjectOperateResult<IProcessStatus> queryUnitState(IProcessRunPara var1, IBusinessKeyCollection var2);

    public List<IProcessOperation> queryProcessOperations(IProcessRunPara var1, IBusinessKey var2);

    public List<String> queryMatchingActors(IProcessRunPara var1, IBusinessKey var2);

    public List<IProcessTask> queryCurrentTask(IProcessRunPara var1, IBusinessKey var2);
}

