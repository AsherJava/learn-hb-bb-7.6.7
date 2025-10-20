/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.workflow.ProcessNodeDO
 *  com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeService
 *  com.jiuqi.va.trans.service.VaTransMessageService
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.va.workflow.mq.processnode.listener;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.workflow.ProcessNodeDO;
import com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeService;
import com.jiuqi.va.trans.service.VaTransMessageService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Component
public class VaWorkflowProcessNodeQueueMsgHandler {
    @Autowired
    private WorkflowProcessNodeService workflowProcessNodeService;
    @Autowired
    private VaTransMessageService vaTransMessageService;

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Transactional(rollbackFor={Exception.class})
    public void processNodeAdd(Map<String, Object> param) {
        String messageId = param.get("msgId").toString();
        String content = param.get("content").toString();
        Map contentMap = JSONUtil.parseMap((String)content);
        Object processNodesObject = contentMap.get("processNode");
        if (processNodesObject == null) {
            Boolean checkParam = (Boolean)contentMap.get("checkParam");
            if (checkParam == null || checkParam.booleanValue()) throw new RuntimeException("\u7f3a\u5c11\u5fc5\u8981\u53c2\u6570");
            this.vaTransMessageService.doneTransMessage(messageId, ShiroUtil.getTenantName(), null);
            return;
        } else {
            ProcessNodeDO processNode = (ProcessNodeDO)JSONUtil.parseObject((String)JSONUtil.toJSONString(contentMap.get("processNode")), ProcessNodeDO.class);
            this.workflowProcessNodeService.add(processNode);
            this.vaTransMessageService.doneTransMessage(messageId, ShiroUtil.getTenantName(), null);
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public void processNodeAddBatch(Map<String, Object> param) {
        String messageId = param.get("msgId").toString();
        String content = param.get("content").toString();
        Map contentMap = JSONUtil.parseMap((String)content);
        Object processNodesObject = contentMap.get("processNodes");
        Boolean checkParam = (Boolean)contentMap.get("checkParam");
        if (processNodesObject == null && checkParam.booleanValue()) {
            throw new RuntimeException("\u7f3a\u5c11\u5fc5\u8981\u53c2\u6570");
        }
        List processNodes = JSONUtil.parseArray((String)JSONUtil.toJSONString(processNodesObject), ProcessNodeDO.class);
        this.workflowProcessNodeService.addBatch(processNodes, ShiroUtil.getTenantName());
        this.vaTransMessageService.doneTransMessage(messageId, ShiroUtil.getTenantName(), null);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Transactional(rollbackFor={Exception.class})
    public void processNodeUpdate(Map<String, Object> param) {
        String messageId = param.get("msgId").toString();
        String content = param.get("content").toString();
        Map contentMap = JSONUtil.parseMap((String)content);
        Object processNodesObject = contentMap.get("processNode");
        if (processNodesObject == null) {
            Boolean checkParam = (Boolean)contentMap.get("checkParam");
            if (checkParam == null || checkParam.booleanValue()) throw new RuntimeException("\u7f3a\u5c11\u5fc5\u8981\u53c2\u6570");
            this.vaTransMessageService.doneTransMessage(messageId, ShiroUtil.getTenantName(), null);
            return;
        } else {
            ProcessNodeDO processNode = (ProcessNodeDO)JSONUtil.parseObject((String)JSONUtil.toJSONString(contentMap.get("processNode")), ProcessNodeDO.class);
            this.workflowProcessNodeService.update(processNode);
            this.vaTransMessageService.doneTransMessage(messageId, ShiroUtil.getTenantName(), null);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Transactional(rollbackFor={Exception.class})
    public void processNodeChange(Map<String, Object> param) {
        String messageId = param.get("msgId").toString();
        String content = param.get("content").toString();
        Map contentMap = JSONUtil.parseMap((String)content);
        Object processNodesObject = contentMap.get("processNode");
        if (processNodesObject == null) {
            Boolean checkParam = (Boolean)contentMap.get("checkParam");
            if (checkParam == null || checkParam.booleanValue()) throw new RuntimeException("\u7f3a\u5c11\u5fc5\u8981\u53c2\u6570");
            this.vaTransMessageService.doneTransMessage(messageId, ShiroUtil.getTenantName(), null);
            return;
        } else {
            ProcessNodeDO processNode = (ProcessNodeDO)JSONUtil.parseObject((String)JSONUtil.toJSONString(contentMap.get("processNode")), ProcessNodeDO.class);
            String nodeOperate = (String)contentMap.get("nodeOperate");
            if (!StringUtils.hasText(nodeOperate)) {
                throw new RuntimeException("\u7f3a\u5c11\u5fc5\u8981\u53c2\u6570");
            }
            if (nodeOperate.equals("insert")) {
                this.workflowProcessNodeService.add(processNode);
            } else {
                this.workflowProcessNodeService.update(processNode);
            }
            this.vaTransMessageService.doneTransMessage(messageId, ShiroUtil.getTenantName(), null);
        }
    }
}

