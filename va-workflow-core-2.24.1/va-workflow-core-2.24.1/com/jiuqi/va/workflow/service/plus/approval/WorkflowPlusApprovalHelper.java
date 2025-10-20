/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDO
 *  com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDTO
 *  com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalUserDO
 */
package com.jiuqi.va.workflow.service.plus.approval;

import com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDO;
import com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDTO;
import com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalUserDO;
import com.jiuqi.va.workflow.domain.plus.approval.PlusApprovalInfoExtendsDTO;
import com.jiuqi.va.workflow.domain.plus.approval.PlusApprovalUserExtendsDTO;
import java.util.List;
import java.util.Map;

public interface WorkflowPlusApprovalHelper {
    public List<PlusApprovalInfoDTO> convertPlusApprovalDoToDtoList(List<? extends PlusApprovalInfoDO> var1);

    public Map<String, String> getRemoveUserNodeIdMap(List<? extends PlusApprovalInfoDTO> var1, Map<String, String> var2);

    public List<PlusApprovalInfoDTO> getRemoveFromUpdatePlusApprovalDtoList(List<PlusApprovalInfoDTO> var1);

    public boolean getChooseStaffOption();

    public void handleStaffInfo(boolean var1, PlusApprovalUserDO var2, PlusApprovalUserExtendsDTO var3);

    public void handleStaffInfo(boolean var1, PlusApprovalInfoDO var2, PlusApprovalInfoExtendsDTO var3);
}

