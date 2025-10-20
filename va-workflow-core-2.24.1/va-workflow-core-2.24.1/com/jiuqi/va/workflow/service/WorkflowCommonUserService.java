/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.workflow.commonuser.WorkflowCommonUserDO
 *  com.jiuqi.va.domain.workflow.commonuser.WorkflowCommonUserDTO
 */
package com.jiuqi.va.workflow.service;

import com.jiuqi.va.domain.workflow.commonuser.WorkflowCommonUserDO;
import com.jiuqi.va.domain.workflow.commonuser.WorkflowCommonUserDTO;
import java.util.List;

public interface WorkflowCommonUserService {
    public List<WorkflowCommonUserDO> list(WorkflowCommonUserDTO var1);

    public void add(WorkflowCommonUserDTO var1);

    public void delete(WorkflowCommonUserDTO var1);
}

