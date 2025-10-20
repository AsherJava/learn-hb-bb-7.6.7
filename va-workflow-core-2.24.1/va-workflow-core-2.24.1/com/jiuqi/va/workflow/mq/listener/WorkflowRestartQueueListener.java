/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.node.ObjectNode
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.domain.workflow.ProcessDTO
 *  com.jiuqi.va.domain.workflow.ProcessHistoryDO
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 */
package com.jiuqi.va.workflow.mq.listener;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.domain.workflow.ProcessDTO;
import com.jiuqi.va.domain.workflow.ProcessHistoryDO;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import com.jiuqi.va.workflow.model.WorkflowModel;
import com.jiuqi.va.workflow.mq.listener.AbstractWorkflowQueueListener;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class WorkflowRestartQueueListener
extends AbstractWorkflowQueueListener {
    public String getJoinName() {
        return "VA_WORKFLOW_RESTART_QUEUE";
    }

    @Override
    protected R doMessage(ObjectNode param, String messageId) {
        String content = param.get("content").textValue();
        WorkflowDTO workflowDTO = (WorkflowDTO)JSONUtil.parseObject((String)content, WorkflowDTO.class);
        workflowDTO.setTransMessageId(messageId);
        UserLoginDTO submitUser = null;
        ProcessDTO processDTO = new ProcessDTO();
        processDTO.setBizcode(workflowDTO.getBizCode());
        List processHistorys = this.vaWorkflowProcessService.listHistory(processDTO);
        if (!CollectionUtils.isEmpty(processHistorys)) {
            ProcessHistoryDO processHistory = (ProcessHistoryDO)processHistorys.get(processHistorys.size() - 1);
            workflowDTO.setUniqueCode(processHistory.getDefinekey());
            workflowDTO.setProcessDefineVersion(Long.valueOf(processHistory.getDefineversion().longValue()));
            submitUser = new UserLoginDTO();
            submitUser.setId(processHistory.getStartuser());
            submitUser.setLoginUnit(processHistory.getStartunitcode());
        }
        WorkflowModel workflowModel = this.getModel(workflowDTO.getUniqueCode(), workflowDTO.getProcessDefineVersion());
        R r = workflowModel.reStartProcess(workflowDTO, submitUser);
        return r;
    }
}

