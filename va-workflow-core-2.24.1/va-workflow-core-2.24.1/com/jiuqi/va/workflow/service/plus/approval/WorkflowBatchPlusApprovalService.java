/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.workflow.batch.operate.BatchOperateResult
 */
package com.jiuqi.va.workflow.service.plus.approval;

import com.jiuqi.va.domain.workflow.batch.operate.BatchOperateResult;
import com.jiuqi.va.workflow.domain.plus.approval.BatchPlusApprovalDTO;
import com.jiuqi.va.workflow.domain.plus.approval.WithdrawPlusApprovalDTO;
import com.jiuqi.va.workflow.domain.plus.approval.WithdrawPlusApprovalVO;
import java.util.List;

public interface WorkflowBatchPlusApprovalService {
    public BatchOperateResult batchPlusApproval(BatchPlusApprovalDTO var1);

    public List<WithdrawPlusApprovalVO> getUnapprovedPlusApprovalInfo();

    public BatchOperateResult withdrawPlusApproval(WithdrawPlusApprovalDTO var1);
}

