/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.node.ObjectNode
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.RedisLockUtil
 *  com.jiuqi.va.domain.workflow.ProcessDO
 *  com.jiuqi.va.domain.workflow.ProcessDTO
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 *  com.jiuqi.va.domain.workflow.exception.WorkflowException
 */
package com.jiuqi.va.workflow.mq.listener;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.RedisLockUtil;
import com.jiuqi.va.domain.workflow.ProcessDO;
import com.jiuqi.va.domain.workflow.ProcessDTO;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import com.jiuqi.va.domain.workflow.exception.WorkflowException;
import com.jiuqi.va.workflow.model.WorkflowModel;
import com.jiuqi.va.workflow.mq.listener.AbstractWorkflowQueueListener;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class WorkflowTriggerQueueListener
extends AbstractWorkflowQueueListener {
    public String getJoinName() {
        return "VA_WORKFLOW_TRIGGER_QUEUE";
    }

    @Override
    protected R doMessage(ObjectNode param, String messageId) throws InterruptedException {
        String content = param.get("content").textValue();
        WorkflowDTO workflowDTO = (WorkflowDTO)JSONUtil.parseObject((String)content, WorkflowDTO.class);
        workflowDTO.setTransMessageId(messageId);
        String bizCode = workflowDTO.getBizCode();
        if (!StringUtils.hasText(bizCode)) {
            throw new WorkflowException("\u4e1a\u52a1\u7f16\u53f7\u4e0d\u80fd\u4e3a\u7a7a");
        }
        ProcessDTO processDTO = new ProcessDTO();
        processDTO.setBizcode(bizCode);
        ProcessDO processDO = this.vaWorkflowProcessService.get(processDTO);
        if (processDO == null) {
            throw new WorkflowException("\u6d41\u7a0b\u6ca1\u6709\u63d0\u4ea4\u6216\u8005\u5df2\u7ecf\u5ba1\u6279\u7ed3\u675f");
        }
        String processInstanceId = processDO.getId();
        workflowDTO.setProcessInstanceId(processInstanceId);
        R r = R.ok();
        Runnable triggerTaskRunnable = () -> {
            String globMsgId = null;
            try {
                globMsgId = param.get("globMsgId").textValue();
                WorkflowModel workflowModel = this.getModel(processDO.getDefinekey(), processDO.getDefineversion().longValue());
                R rs = workflowModel.trigger(workflowDTO);
                r.putAll((Map)rs);
                if (rs != null && rs.getCode() != 0) {
                    throw new WorkflowException(rs.getMsg());
                }
            }
            catch (Exception e) {
                this.vaBizErrorService.insertBizError(globMsgId, messageId, (Throwable)e);
                throw new WorkflowException(e.getMessage());
            }
        };
        R redisResult = R.ok();
        String lockKey = workflowDTO.getTenantName() + "processInstanceId:" + processInstanceId;
        RedisLockUtil.execute((Runnable)triggerTaskRunnable, (String)lockKey, (long)10000L, (boolean)true, (R)redisResult);
        if (redisResult.getCode() == 0) {
            return r;
        }
        return redisResult;
    }
}

