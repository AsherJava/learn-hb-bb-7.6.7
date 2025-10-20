/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 *  com.jiuqi.va.domain.workflow.WorkflowProcessStatus
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.workflow.service;

import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import com.jiuqi.va.domain.workflow.WorkflowProcessStatus;
import com.jiuqi.va.mapper.domain.TenantDO;

public interface WorkflowProcessInstanceService {
    public R changeProcessStatus(TenantDO var1, WorkflowProcessStatus var2);

    public R refreshParticipant(TenantDO var1);

    public R getProcessBizType(TenantDO var1);

    public R processForecast(WorkflowDTO var1);

    public R billProcessInfo(TenantDO var1);

    public R getProcessInfo(TenantDO var1);

    public String doTerminate(String var1, int var2);

    public String doPauseOrRecovery(String var1, int var2);
}

