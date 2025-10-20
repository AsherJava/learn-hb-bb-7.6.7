/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.workflow.domain.plus.approval;

import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.workflow.domain.plus.approval.WithdrawPlusApprovalVO;
import java.util.List;

public class WithdrawPlusApprovalDTO
extends TenantDO {
    private List<WithdrawPlusApprovalVO> withdrawPlusApprovalVOList;

    public List<WithdrawPlusApprovalVO> getWithdrawPlusApprovalVOList() {
        return this.withdrawPlusApprovalVOList;
    }

    public void setWithdrawPlusApprovalVOList(List<WithdrawPlusApprovalVO> withdrawPlusApprovalVOList) {
        this.withdrawPlusApprovalVOList = withdrawPlusApprovalVOList;
    }
}

