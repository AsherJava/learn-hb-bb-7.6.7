/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 */
package com.jiuqi.nr.workflow2.instance.service;

import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.workflow2.instance.context.InstanceBaseContext;
import com.jiuqi.nr.workflow2.instance.context.InstanceOperateContext;

public interface Workflow2InstanceManipulationService {
    public AsyncTaskInfo operateWorkflowInstance(InstanceOperateContext var1);

    public AsyncTaskInfo refreshParticipant(InstanceBaseContext var1);
}

