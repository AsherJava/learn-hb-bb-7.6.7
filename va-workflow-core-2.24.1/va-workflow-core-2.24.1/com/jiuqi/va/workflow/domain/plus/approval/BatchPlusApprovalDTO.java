/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.todo.TaskDTO
 *  com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDO
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.workflow.domain.plus.approval;

import com.jiuqi.va.domain.todo.TaskDTO;
import com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDO;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.List;

public class BatchPlusApprovalDTO
extends TenantDO {
    private List<TaskDTO> taskDTOList;
    private List<PlusApprovalInfoDO> plusApprovalInfoDOList;

    public List<TaskDTO> getTaskDTOList() {
        return this.taskDTOList;
    }

    public List<PlusApprovalInfoDO> getPlusApprovalInfoDOList() {
        return this.plusApprovalInfoDOList;
    }
}

