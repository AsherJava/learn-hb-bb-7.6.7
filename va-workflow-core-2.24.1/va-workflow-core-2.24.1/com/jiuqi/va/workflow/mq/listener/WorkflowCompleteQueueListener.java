/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.node.ObjectNode
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.RedisLockUtil
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 *  com.jiuqi.va.domain.workflow.comment.WorkflowCommentDTO
 *  com.jiuqi.va.domain.workflow.exception.WorkflowException
 */
package com.jiuqi.va.workflow.mq.listener;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.RedisLockUtil;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import com.jiuqi.va.domain.workflow.comment.WorkflowCommentDTO;
import com.jiuqi.va.domain.workflow.exception.WorkflowException;
import com.jiuqi.va.workflow.model.WorkflowModel;
import com.jiuqi.va.workflow.mq.listener.AbstractWorkflowQueueListener;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class WorkflowCompleteQueueListener
extends AbstractWorkflowQueueListener {
    private static final Logger log = LoggerFactory.getLogger(WorkflowCompleteQueueListener.class);

    public String getJoinName() {
        return "VA_WORKFLOW_COMPLETE_QUEUE";
    }

    @Override
    protected R doMessage(final ObjectNode param, final String messageId) throws InterruptedException {
        String content = param.get("content").textValue();
        final WorkflowDTO workflowDTO = (WorkflowDTO)JSONUtil.parseObject((String)content, WorkflowDTO.class);
        workflowDTO.setTransMessageId(messageId);
        String processInstanceId = workflowDTO.getProcessInstanceId();
        final R r = R.ok();
        Runnable completeTaskRunnable = new Runnable(){

            @Override
            public void run() {
                String globMsgId = null;
                try {
                    globMsgId = param.get("globMsgId").textValue();
                    WorkflowModel workflowModel = WorkflowCompleteQueueListener.this.getModel(workflowDTO.getUniqueCode(), workflowDTO.getProcessDefineVersion());
                    R rs = workflowModel.completeTask(workflowDTO);
                    r.putAll((Map)rs);
                    if (rs != null && rs.getCode() != 0) {
                        throw new WorkflowException(rs.getMsg());
                    }
                }
                catch (Exception e) {
                    WorkflowCompleteQueueListener.this.vaBizErrorService.insertBizError(globMsgId, messageId, (Throwable)e);
                    throw new WorkflowException(e.getMessage());
                }
                if (workflowDTO.isCommonComment() && StringUtils.hasText(workflowDTO.getApprovalComment())) {
                    try {
                        WorkflowCommentDTO workflowCommentDTO = new WorkflowCommentDTO();
                        workflowCommentDTO.setUsername(ShiroUtil.getUser().getId().toString());
                        workflowCommentDTO.setComment(workflowDTO.getApprovalComment());
                        WorkflowCompleteQueueListener.this.workflowCommentService.add(workflowCommentDTO);
                    }
                    catch (Exception e) {
                        log.error("\u5e38\u7528\u610f\u89c1\u6dfb\u52a0\u5931\u8d25", e);
                    }
                }
            }
        };
        R redisResult = R.ok();
        String lockKey = workflowDTO.getTenantName() + "processInstanceId:" + processInstanceId;
        RedisLockUtil.execute((Runnable)completeTaskRunnable, (String)lockKey, (long)10000L, (boolean)true, (R)redisResult);
        if (redisResult.getCode() == 0) {
            return r;
        }
        return redisResult;
    }
}

