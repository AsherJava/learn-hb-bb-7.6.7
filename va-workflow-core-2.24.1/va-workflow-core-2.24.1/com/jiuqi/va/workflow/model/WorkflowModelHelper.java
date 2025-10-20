/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.jiuqi.va.domain.workflow.ProcessNodeDO
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 *  com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDO
 *  com.jiuqi.va.domain.workflow.retract.RetractRejectInfo
 */
package com.jiuqi.va.workflow.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jiuqi.va.domain.workflow.ProcessNodeDO;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDO;
import com.jiuqi.va.domain.workflow.retract.RetractRejectInfo;
import com.jiuqi.va.workflow.formula.WorkflowContext;
import java.util.List;
import java.util.Map;

public interface WorkflowModelHelper {
    public String getBizDefineTitle(String var1, String var2, String var3);

    public PlusApprovalInfoDO getPlusApprovalInfoDo(String var1, String var2, String var3);

    public void packageParamPlusApproval(Map<String, Object> var1, WorkflowDTO var2);

    public List<Map<String, Object>> getTodoTransferInfo(String var1, String var2, String var3, String var4);

    public Map<String, String> getBillVerifyCodeMap(List<String> var1, String var2);

    public void calculateAutoTaskUser(WorkflowContext var1, JsonNode var2, WorkflowDTO var3) throws JsonProcessingException;

    public boolean isRetractReject(WorkflowDTO var1);

    public void checkRetractReject(WorkflowDTO var1, ArrayNode var2);

    public void handlePgwRetractRejectSubmit(List<ProcessNodeDO> var1, ProcessNodeDO var2, RetractRejectInfo var3, ArrayNode var4);

    public void handleCommonRetractRejectSubmit(ProcessNodeDO var1, List<ProcessNodeDO> var2, RetractRejectInfo var3);

    public void handleSubRetractRejectSub(ProcessNodeDO var1, List<ProcessNodeDO> var2, List<ProcessNodeDO> var3, RetractRejectInfo var4);

    public void handlePgwRetractRejectPgw(ProcessNodeDO var1, List<ProcessNodeDO> var2, List<ProcessNodeDO> var3, RetractRejectInfo var4);

    public void handleCommonRetractRejectPgw(ProcessNodeDO var1, List<ProcessNodeDO> var2, List<ProcessNodeDO> var3, List<ProcessNodeDO> var4, RetractRejectInfo var5);

    public void handlePgwRetractRejectCommon(List<ProcessNodeDO> var1, ProcessNodeDO var2, List<ProcessNodeDO> var3, RetractRejectInfo var4, ArrayNode var5);

    public void handleCommonRetractRejectCommon(List<ProcessNodeDO> var1, ProcessNodeDO var2, List<ProcessNodeDO> var3, List<ProcessNodeDO> var4, RetractRejectInfo var5);

    public void checkRetract(WorkflowDTO var1, List<ProcessNodeDO> var2, String var3);
}

