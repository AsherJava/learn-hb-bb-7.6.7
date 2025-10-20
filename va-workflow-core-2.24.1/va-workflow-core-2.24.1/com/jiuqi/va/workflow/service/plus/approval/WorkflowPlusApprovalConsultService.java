/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.workflow.ProcessNodeDO
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 */
package com.jiuqi.va.workflow.service.plus.approval;

import com.jiuqi.va.domain.workflow.ProcessNodeDO;
import com.jiuqi.va.domain.workflow.WorkflowDTO;

public interface WorkflowPlusApprovalConsultService {
    public void consult(WorkflowDTO var1);

    public void retract(ProcessNodeDO var1);
}

