/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.node.ObjectNode
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.RedisLockUtil
 *  com.jiuqi.va.domain.workflow.ProcessDO
 *  com.jiuqi.va.domain.workflow.ProcessHistoryDO
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 *  com.jiuqi.va.domain.workflow.exception.WorkflowException
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.workflow.mq.listener;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.RedisLockUtil;
import com.jiuqi.va.domain.workflow.ProcessDO;
import com.jiuqi.va.domain.workflow.ProcessHistoryDO;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import com.jiuqi.va.domain.workflow.exception.WorkflowException;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.workflow.model.WorkflowModel;
import com.jiuqi.va.workflow.model.WorkflowModelHelper;
import com.jiuqi.va.workflow.mq.listener.AbstractWorkflowQueueListener;
import com.jiuqi.va.workflow.service.impl.help.WorkflowParamService;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import com.jiuqi.va.workflow.utils.VaWorkflowUtils;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Component
public class WorkflowRetractQueueListener
extends AbstractWorkflowQueueListener {
    @Autowired
    private WorkflowModelHelper workflowModelHelper;
    @Autowired
    private WorkflowParamService workflowParamService;

    public String getJoinName() {
        return "VA_WORKFLOW_RETRACT_QUEUE";
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
        String globMsgId = param.get("globMsgId").textValue();
        if (this.workflowModelHelper.isRetractReject(workflowDTO)) {
            return this.retractReject(workflowDTO, globMsgId, messageId);
        }
        return this.retract(workflowDTO, globMsgId, messageId);
    }

    private R retract(WorkflowDTO workflowDTO, String globMsgId, String messageId) {
        ProcessDO processDO = (ProcessDO)workflowDTO.getExtInfo("processDO");
        if (processDO == null) {
            throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.processfinished"));
        }
        VaWorkflowUtils.setTractId((TenantDO)workflowDTO);
        String processInstanceId = processDO.getId();
        R r = R.ok();
        Runnable retractProcessRunnable = () -> {
            try {
                WorkflowModel workflowModel = this.workflowParamService.getModel(processDO.getDefinekey(), processDO.getDefineversion().longValue());
                workflowDTO.setProcessInstanceId(processInstanceId);
                R rs = workflowModel.retractProcess(workflowDTO);
                r.putAll((Map)rs);
                if (rs != null && rs.getCode() != 0) {
                    throw new WorkflowException(rs.getMsg());
                }
            }
            catch (WorkflowException e) {
                this.vaBizErrorService.insertBizError(globMsgId, messageId, (Throwable)e);
                throw new WorkflowException(e.getMessage());
            }
        };
        R redisResult = R.ok();
        String lockKey = workflowDTO.getTenantName() + "processInstanceId:" + processInstanceId;
        RedisLockUtil.execute((Runnable)retractProcessRunnable, (String)lockKey, (long)10000L, (boolean)true, (R)redisResult);
        if (redisResult.getCode() == 0) {
            return r;
        }
        return redisResult;
    }

    private R retractReject(WorkflowDTO workflowDTO, String globMsgId, String messageId) {
        String lockKey;
        String processInstanceId;
        long processDefineVersion;
        String defineKey;
        String bizCode = workflowDTO.getBizCode();
        ProcessDO processDO = (ProcessDO)workflowDTO.getExtInfo("processDO");
        if (processDO == null) {
            ProcessHistoryDO historyDO = (ProcessHistoryDO)workflowDTO.getExtInfo("processHistoryDO");
            defineKey = historyDO.getDefinekey();
            processDefineVersion = historyDO.getDefineversion().longValue();
            processInstanceId = historyDO.getId();
            lockKey = workflowDTO.getTenantName() + ":SUBMIT:" + bizCode;
        } else {
            processInstanceId = processDO.getId();
            processDefineVersion = processDO.getDefineversion().longValue();
            defineKey = processDO.getDefinekey();
            lockKey = workflowDTO.getTenantName() + "processInstanceId:" + processInstanceId;
        }
        Assert.hasText(defineKey, VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams") + "workflowDefineKey");
        Assert.hasText(processInstanceId, VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams") + "processInstanceId");
        workflowDTO.setUniqueCode(defineKey);
        workflowDTO.setProcessInstanceId(processInstanceId);
        workflowDTO.setProcessDefineVersion(Long.valueOf(processDefineVersion));
        VaWorkflowUtils.setTractId((TenantDO)workflowDTO);
        R r = R.ok();
        Runnable retractProcessRunnable = () -> {
            try {
                WorkflowModel workflowModel = this.workflowParamService.getModel(defineKey, processDefineVersion);
                R rs = workflowModel.retractRejectProcess(workflowDTO);
                r.putAll((Map)rs);
                if (rs != null && rs.getCode() != 0) {
                    throw new WorkflowException(rs.getMsg());
                }
            }
            catch (WorkflowException e) {
                this.vaBizErrorService.insertBizError(globMsgId, messageId, (Throwable)e);
                throw new WorkflowException(e.getMessage());
            }
        };
        R redisResult = R.ok();
        RedisLockUtil.execute((Runnable)retractProcessRunnable, (String)lockKey, (long)10000L, (boolean)true, (R)redisResult);
        if (redisResult.getCode() == 0) {
            return r;
        }
        return redisResult;
    }
}

