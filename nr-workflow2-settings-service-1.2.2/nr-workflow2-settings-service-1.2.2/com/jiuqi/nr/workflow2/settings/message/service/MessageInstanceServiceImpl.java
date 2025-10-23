/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyDefinition
 *  com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyFactory
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.WorkflowDefineTemplate
 *  com.jiuqi.nr.workflow2.events.executor.msg.enumeration.MessageType
 *  com.jiuqi.nr.workflow2.events.executor.msg.enumeration.MessageVariable
 *  com.jiuqi.nr.workflow2.events.executor.util.NotificationSendUtil
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.workflow2.settings.message.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyDefinition;
import com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyFactory;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.WorkflowDefineTemplate;
import com.jiuqi.nr.workflow2.events.executor.msg.enumeration.MessageType;
import com.jiuqi.nr.workflow2.events.executor.msg.enumeration.MessageVariable;
import com.jiuqi.nr.workflow2.events.executor.util.NotificationSendUtil;
import com.jiuqi.nr.workflow2.settings.message.dto.MessageInstanceBaseContext;
import com.jiuqi.nr.workflow2.settings.message.dto.MessageInstanceQueryContext;
import com.jiuqi.nr.workflow2.settings.message.dto.MessageInstanceStrategyVerifyContext;
import com.jiuqi.nr.workflow2.settings.message.service.MessageInstanceService;
import com.jiuqi.nr.workflow2.settings.message.vo.MessageInstanceVO;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

public class MessageInstanceServiceImpl
implements MessageInstanceService {
    private static final String KEY_OF_CODE = "code";
    private static final String KEY_OF_TITLE = "title";
    @Autowired
    private IActorStrategyFactory actorStrategyFactory;
    @Autowired
    private INvwaSystemOptionService nvwaSystemOptionService;
    @Autowired
    private NotificationSendUtil util;

    @Override
    public MessageInstanceVO messageInstanceInit(MessageInstanceQueryContext context) {
        boolean isMultiEntityCaliberWithReportDimensionEnable = this.util.isMultiEntityCaliberWithReportDimensionEnable(context.getTaskId());
        String entityCaliberVariable = isMultiEntityCaliberWithReportDimensionEnable ? "<span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u5355\u4f4d\u53e3\u5f84\" data-code=\"entityCaliber\">\u3010\u5355\u4f4d\u53e3\u5f84\u3011</span>" : "";
        WorkflowObjectType workflowObjectType = WorkflowObjectType.valueOf((String)context.getSubmitMode());
        String formOrFormGroupVariable = workflowObjectType.equals((Object)WorkflowObjectType.FORM) ? "<span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u62a5\u8868\u540d\u79f0\" data-code=\"reportName\">\u3010\u62a5\u8868\u540d\u79f0\u3011</span>" : (workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP) ? "<span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u5206\u7ec4\u540d\u79f0\" data-code=\"groupName\">\u3010\u5206\u7ec4\u540d\u79f0\u3011</span>" : "");
        WorkflowDefineTemplate workflowDefineTemplate = WorkflowDefineTemplate.valueOf((String)context.getWorkflowDefineTemplate());
        String auditNode_Reject_Text = workflowDefineTemplate.equals((Object)WorkflowDefineTemplate.SUBMIT_WORKFLOW) ? "\u5df2\u9000\u56de\uff0c\u8bf7\u53ca\u65f6\u9001\u5ba1\u3002" : "\u5df2\u9000\u56de\uff0c\u8bf7\u53ca\u65f6\u4e0a\u62a5\u3002";
        String workflowNode = context.getWorkflowNode();
        String actionCode = context.getActionCode();
        String type = context.getType();
        MessageInstanceVO messageInstanceVO = new MessageInstanceVO();
        messageInstanceVO.setUserSelectable(false);
        if (type.equals(MessageType.MESSAGE.code)) {
            if (workflowNode.equals("tsk_submit")) {
                messageInstanceVO.setTitle("\u9001\u5ba1\u901a\u77e5-\u6d88\u606f");
                String content = "<p><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u4efb\u52a1\u540d\u79f0\" data-code=\"taskName\">\u3010\u4efb\u52a1\u540d\u79f0\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u65f6\u671f\" data-code=\"period\">\u3010\u65f6\u671f\u3011</span>" + entityCaliberVariable + "<span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\" data-code=\"mdName\">\u3010\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\u3011</span>" + formOrFormGroupVariable + "\u5df2\u9001\u5ba1\uff0c\u8bf7\u53ca\u65f6\u4e0a\u62a5\u3002" + "</p>";
                messageInstanceVO.setContent(content);
                ArrayList receiver = new ArrayList();
                HashMap<String, Object> receiverMap = new HashMap<String, Object>();
                receiverMap.put("strategy", "reportNodeTodoReceiverStrategy");
                HashMap param = new HashMap();
                param.put("userRoleList", new ArrayList());
                receiverMap.put("param", param);
                receiver.add(receiverMap);
                messageInstanceVO.setReceiver(receiver);
            } else if (workflowNode.equals("tsk_upload")) {
                if (actionCode.equals("act_upload")) {
                    messageInstanceVO.setTitle("\u4e0a\u62a5\u901a\u77e5-\u6d88\u606f");
                    String content = "<p><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u4efb\u52a1\u540d\u79f0\" data-code=\"taskName\">\u3010\u4efb\u52a1\u540d\u79f0\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u65f6\u671f\" data-code=\"period\">\u3010\u65f6\u671f\u3011</span>" + entityCaliberVariable + "<span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\" data-code=\"mdName\">\u3010\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\u3011</span>" + formOrFormGroupVariable + "\u5df2\u4e0a\u62a5\uff0c\u8bf7\u53ca\u65f6\u5ba1\u6279\u3002" + "</p>";
                    messageInstanceVO.setContent(content);
                    ArrayList receiver = new ArrayList();
                    HashMap<String, Object> receiverMap = new HashMap<String, Object>();
                    receiverMap.put("strategy", "auditNodeTodoReceiverStrategy");
                    HashMap param = new HashMap();
                    param.put("userRoleList", new ArrayList());
                    receiverMap.put("param", param);
                    receiver.add(receiverMap);
                    messageInstanceVO.setReceiver(receiver);
                } else if (actionCode.equals("act_return")) {
                    messageInstanceVO.setTitle("\u9000\u5ba1\u901a\u77e5-\u6d88\u606f");
                    String content = "<p><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u4efb\u52a1\u540d\u79f0\" data-code=\"taskName\">\u3010\u4efb\u52a1\u540d\u79f0\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u65f6\u671f\" data-code=\"period\">\u3010\u65f6\u671f\u3011</span>" + entityCaliberVariable + "<span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\" data-code=\"mdName\">\u3010\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\u3011</span>" + formOrFormGroupVariable + "\u5df2\u9000\u5ba1\uff0c\u8bf7\u53ca\u65f6\u9001\u5ba1\u3002" + "</p>";
                    messageInstanceVO.setContent(content);
                    ArrayList receiver = new ArrayList();
                    HashMap<String, Object> receiverMap = new HashMap<String, Object>();
                    receiverMap.put("strategy", "submitNodeTodoReceiverStrategy");
                    HashMap param = new HashMap();
                    param.put("userRoleList", new ArrayList());
                    receiverMap.put("param", param);
                    receiver.add(receiverMap);
                    messageInstanceVO.setReceiver(receiver);
                } else if (actionCode.equals("act_apply_reject")) {
                    messageInstanceVO.setTitle("\u7533\u8bf7\u9000\u56de\u901a\u77e5-\u6d88\u606f");
                    String content = "<p><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u4efb\u52a1\u540d\u79f0\" data-code=\"taskName\">\u3010\u4efb\u52a1\u540d\u79f0\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u65f6\u671f\" data-code=\"period\">\u3010\u65f6\u671f\u3011</span>" + entityCaliberVariable + "<span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\" data-code=\"mdName\">\u3010\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\u3011</span>" + formOrFormGroupVariable + "\u5df2\u7533\u8bf7\u9000\u56de\uff0c\u8bf7\u53ca\u65f6\u5ba1\u6279\u3002" + "</p>";
                    messageInstanceVO.setContent(content);
                    ArrayList receiver = new ArrayList();
                    HashMap<String, Object> receiverMap = new HashMap<String, Object>();
                    receiverMap.put("strategy", "auditNodeTodoReceiverStrategy");
                    HashMap param = new HashMap();
                    param.put("userRoleList", new ArrayList());
                    receiverMap.put("param", param);
                    receiver.add(receiverMap);
                    messageInstanceVO.setReceiver(receiver);
                }
            } else if (workflowNode.equals("tsk_audit")) {
                if (actionCode.equals("act_confirm")) {
                    messageInstanceVO.setTitle("\u786e\u8ba4\u901a\u77e5-\u6d88\u606f");
                    String content = "<p><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u4efb\u52a1\u540d\u79f0\" data-code=\"taskName\">\u3010\u4efb\u52a1\u540d\u79f0\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u65f6\u671f\" data-code=\"period\">\u3010\u65f6\u671f\u3011</span>" + entityCaliberVariable + "<span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\" data-code=\"mdName\">\u3010\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\u3011</span>" + formOrFormGroupVariable + "\u5df2\u786e\u8ba4\u3002" + "</p>";
                    messageInstanceVO.setContent(content);
                    ArrayList receiver = new ArrayList();
                    HashMap<String, Object> receiverMap = new HashMap<String, Object>();
                    receiverMap.put("strategy", "reportNodeTodoReceiverStrategy");
                    HashMap param = new HashMap();
                    param.put("userRoleList", new ArrayList());
                    receiverMap.put("param", param);
                    receiver.add(receiverMap);
                    messageInstanceVO.setReceiver(receiver);
                } else if (actionCode.equals("act_reject")) {
                    messageInstanceVO.setTitle("\u9000\u56de\u901a\u77e5-\u6d88\u606f");
                    String content = "<p><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u4efb\u52a1\u540d\u79f0\" data-code=\"taskName\">\u3010\u4efb\u52a1\u540d\u79f0\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u65f6\u671f\" data-code=\"period\">\u3010\u65f6\u671f\u3011</span>" + entityCaliberVariable + "<span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\" data-code=\"mdName\">\u3010\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\u3011</span>" + formOrFormGroupVariable + auditNode_Reject_Text + "</p>";
                    messageInstanceVO.setContent(content);
                    ArrayList receiver = new ArrayList();
                    HashMap<String, Object> receiverMap = new HashMap<String, Object>();
                    receiverMap.put("strategy", workflowDefineTemplate.equals((Object)WorkflowDefineTemplate.SUBMIT_WORKFLOW) ? "submitNodeTodoReceiverStrategy" : "reportNodeTodoReceiverStrategy");
                    HashMap param = new HashMap();
                    param.put("userRoleList", new ArrayList());
                    receiverMap.put("param", param);
                    receiver.add(receiverMap);
                    messageInstanceVO.setReceiver(receiver);
                }
            }
        } else if (type.equals(MessageType.SHORT_MESSAGE.code)) {
            if (workflowNode.equals("tsk_submit")) {
                messageInstanceVO.setTitle("\u9001\u5ba1\u901a\u77e5-\u77ed\u4fe1");
                String content = "<p><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u4efb\u52a1\u540d\u79f0\" data-code=\"taskName\">\u3010\u4efb\u52a1\u540d\u79f0\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u65f6\u671f\" data-code=\"period\">\u3010\u65f6\u671f\u3011</span>" + entityCaliberVariable + "<span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\" data-code=\"mdName\">\u3010\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\u3011</span>" + formOrFormGroupVariable + "\u5df2\u9001\u5ba1\uff0c\u8bf7\u53ca\u65f6\u4e0a\u62a5\u3002" + "</p>";
                messageInstanceVO.setContent(content);
                ArrayList receiver = new ArrayList();
                HashMap<String, Object> receiverMap = new HashMap<String, Object>();
                receiverMap.put("strategy", "reportNodeTodoReceiverStrategy");
                HashMap param = new HashMap();
                param.put("userRoleList", new ArrayList());
                receiverMap.put("param", param);
                receiver.add(receiverMap);
                messageInstanceVO.setReceiver(receiver);
            } else if (workflowNode.equals("tsk_upload")) {
                if (actionCode.equals("act_upload")) {
                    messageInstanceVO.setTitle("\u4e0a\u62a5\u901a\u77e5-\u77ed\u4fe1");
                    String content = "<p><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u4efb\u52a1\u540d\u79f0\" data-code=\"taskName\">\u3010\u4efb\u52a1\u540d\u79f0\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u65f6\u671f\" data-code=\"period\">\u3010\u65f6\u671f\u3011</span>" + entityCaliberVariable + "<span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\" data-code=\"mdName\">\u3010\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\u3011</span>" + formOrFormGroupVariable + "\u5df2\u4e0a\u62a5\uff0c\u8bf7\u53ca\u65f6\u5ba1\u6279\u3002" + "</p>";
                    messageInstanceVO.setContent(content);
                    ArrayList receiver = new ArrayList();
                    HashMap<String, Object> receiverMap = new HashMap<String, Object>();
                    receiverMap.put("strategy", "auditNodeTodoReceiverStrategy");
                    HashMap param = new HashMap();
                    param.put("userRoleList", new ArrayList());
                    receiverMap.put("param", param);
                    receiver.add(receiverMap);
                    messageInstanceVO.setReceiver(receiver);
                } else if (actionCode.equals("act_return")) {
                    messageInstanceVO.setTitle("\u9000\u5ba1\u901a\u77e5-\u77ed\u4fe1");
                    String content = "<p><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u4efb\u52a1\u540d\u79f0\" data-code=\"taskName\">\u3010\u4efb\u52a1\u540d\u79f0\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u65f6\u671f\" data-code=\"period\">\u3010\u65f6\u671f\u3011</span>" + entityCaliberVariable + "<span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\" data-code=\"mdName\">\u3010\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\u3011</span>" + formOrFormGroupVariable + "\u5df2\u9000\u5ba1\uff0c\u8bf7\u53ca\u65f6\u9001\u5ba1\u3002" + "</p>";
                    messageInstanceVO.setContent(content);
                    ArrayList receiver = new ArrayList();
                    HashMap<String, Object> receiverMap = new HashMap<String, Object>();
                    receiverMap.put("strategy", "submitNodeTodoReceiverStrategy");
                    HashMap param = new HashMap();
                    param.put("userRoleList", new ArrayList());
                    receiverMap.put("param", param);
                    receiver.add(receiverMap);
                    messageInstanceVO.setReceiver(receiver);
                } else if (actionCode.equals("act_apply_reject")) {
                    messageInstanceVO.setTitle("\u7533\u8bf7\u9000\u56de\u901a\u77e5-\u77ed\u4fe1");
                    String content = "<p><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u4efb\u52a1\u540d\u79f0\" data-code=\"taskName\">\u3010\u4efb\u52a1\u540d\u79f0\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u65f6\u671f\" data-code=\"period\">\u3010\u65f6\u671f\u3011</span>" + entityCaliberVariable + "<span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\" data-code=\"mdName\">\u3010\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\u3011</span>" + formOrFormGroupVariable + "\u5df2\u7533\u8bf7\u9000\u56de\uff0c\u8bf7\u53ca\u65f6\u5ba1\u6279\u3002" + "</p>";
                    messageInstanceVO.setContent(content);
                    ArrayList receiver = new ArrayList();
                    HashMap<String, Object> receiverMap = new HashMap<String, Object>();
                    receiverMap.put("strategy", "auditNodeTodoReceiverStrategy");
                    HashMap param = new HashMap();
                    param.put("userRoleList", new ArrayList());
                    receiverMap.put("param", param);
                    receiver.add(receiverMap);
                    messageInstanceVO.setReceiver(receiver);
                }
            } else if (workflowNode.equals("tsk_audit")) {
                if (actionCode.equals("act_confirm")) {
                    messageInstanceVO.setTitle("\u786e\u8ba4\u901a\u77e5-\u77ed\u4fe1");
                    String content = "<p><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u4efb\u52a1\u540d\u79f0\" data-code=\"taskName\">\u3010\u4efb\u52a1\u540d\u79f0\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u65f6\u671f\" data-code=\"period\">\u3010\u65f6\u671f\u3011</span>" + entityCaliberVariable + "<span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\" data-code=\"mdName\">\u3010\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\u3011</span>" + formOrFormGroupVariable + "\u5df2\u786e\u8ba4\u3002" + "</p>";
                    messageInstanceVO.setContent(content);
                    ArrayList receiver = new ArrayList();
                    HashMap<String, Object> receiverMap = new HashMap<String, Object>();
                    receiverMap.put("strategy", "reportNodeTodoReceiverStrategy");
                    HashMap param = new HashMap();
                    param.put("userRoleList", new ArrayList());
                    receiverMap.put("param", param);
                    receiver.add(receiverMap);
                    messageInstanceVO.setReceiver(receiver);
                } else if (actionCode.equals("act_reject")) {
                    messageInstanceVO.setTitle("\u9000\u56de\u901a\u77e5-\u77ed\u4fe1");
                    String content = "<p><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u4efb\u52a1\u540d\u79f0\" data-code=\"taskName\">\u3010\u4efb\u52a1\u540d\u79f0\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u65f6\u671f\" data-code=\"period\">\u3010\u65f6\u671f\u3011</span>" + entityCaliberVariable + "<span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\" data-code=\"mdName\">\u3010\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\u3011</span>" + formOrFormGroupVariable + auditNode_Reject_Text + "</p>";
                    messageInstanceVO.setContent(content);
                    ArrayList receiver = new ArrayList();
                    HashMap<String, Object> receiverMap = new HashMap<String, Object>();
                    receiverMap.put("strategy", workflowDefineTemplate.equals((Object)WorkflowDefineTemplate.SUBMIT_WORKFLOW) ? "submitNodeTodoReceiverStrategy" : "reportNodeTodoReceiverStrategy");
                    HashMap param = new HashMap();
                    param.put("userRoleList", new ArrayList());
                    receiverMap.put("param", param);
                    receiver.add(receiverMap);
                    messageInstanceVO.setReceiver(receiver);
                }
            }
        } else if (type.equals(MessageType.EMAIL.code)) {
            String honorific_user = "<p>\u5c0a\u656c\u7684\u7528\u6237\uff1a</p>";
            String honorific_hello = "<p>&nbsp;&nbsp;\u4f60\u597d\uff01</p>";
            if (workflowNode.equals("tsk_submit")) {
                messageInstanceVO.setTitle("\u9001\u5ba1\u901a\u77e5-\u90ae\u4ef6");
                messageInstanceVO.setSubject("\u9001\u5ba1\u901a\u77e5-\u90ae\u4ef6");
                String content = "<p>&nbsp;&nbsp;<span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u4efb\u52a1\u540d\u79f0\" data-code=\"taskName\">\u3010\u4efb\u52a1\u540d\u79f0\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u65f6\u671f\" data-code=\"period\">\u3010\u65f6\u671f\u3011</span>" + entityCaliberVariable + "<span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\" data-code=\"mdName\">\u3010\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\u3011</span>" + formOrFormGroupVariable + "\u5df2\u9001\u5ba1\uff0c\u8bf7\u53ca\u65f6\u4e0a\u62a5\u3002" + "</p>";
                messageInstanceVO.setContent(honorific_user + honorific_hello + content);
                ArrayList receiver = new ArrayList();
                HashMap<String, Object> receiverMap = new HashMap<String, Object>();
                receiverMap.put("strategy", "reportNodeTodoReceiverStrategy");
                HashMap param = new HashMap();
                param.put("userRoleList", new ArrayList());
                receiverMap.put("param", param);
                receiver.add(receiverMap);
                messageInstanceVO.setReceiver(receiver);
            } else if (workflowNode.equals("tsk_upload")) {
                if (actionCode.equals("act_upload")) {
                    messageInstanceVO.setTitle("\u4e0a\u62a5\u901a\u77e5-\u90ae\u4ef6");
                    messageInstanceVO.setSubject("\u4e0a\u62a5\u901a\u77e5-\u90ae\u4ef6");
                    String content = "<p>&nbsp;&nbsp;<span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u4efb\u52a1\u540d\u79f0\" data-code=\"taskName\">\u3010\u4efb\u52a1\u540d\u79f0\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u65f6\u671f\" data-code=\"period\">\u3010\u65f6\u671f\u3011</span>" + entityCaliberVariable + "<span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\" data-code=\"mdName\">\u3010\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\u3011</span>" + formOrFormGroupVariable + "\u5df2\u4e0a\u62a5\uff0c\u8bf7\u53ca\u65f6\u5ba1\u6279\u3002" + "</p>";
                    messageInstanceVO.setContent(honorific_user + honorific_hello + content);
                    ArrayList receiver = new ArrayList();
                    HashMap<String, Object> receiverMap = new HashMap<String, Object>();
                    receiverMap.put("strategy", "auditNodeTodoReceiverStrategy");
                    HashMap param = new HashMap();
                    param.put("userRoleList", new ArrayList());
                    receiverMap.put("param", param);
                    receiver.add(receiverMap);
                    messageInstanceVO.setReceiver(receiver);
                } else if (actionCode.equals("act_return")) {
                    messageInstanceVO.setTitle("\u9000\u5ba1\u901a\u77e5-\u90ae\u4ef6");
                    messageInstanceVO.setSubject("\u9000\u5ba1\u901a\u77e5-\u90ae\u4ef6");
                    String content = "<p>&nbsp;&nbsp;<span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u4efb\u52a1\u540d\u79f0\" data-code=\"taskName\">\u3010\u4efb\u52a1\u540d\u79f0\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u65f6\u671f\" data-code=\"period\">\u3010\u65f6\u671f\u3011</span>" + entityCaliberVariable + "<span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\" data-code=\"mdName\">\u3010\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\u3011</span>" + formOrFormGroupVariable + "\u5df2\u9000\u5ba1\uff0c\u8bf7\u53ca\u65f6\u9001\u5ba1\u3002" + "</p>";
                    messageInstanceVO.setContent(honorific_user + honorific_hello + content);
                    ArrayList receiver = new ArrayList();
                    HashMap<String, Object> receiverMap = new HashMap<String, Object>();
                    receiverMap.put("strategy", "submitNodeTodoReceiverStrategy");
                    HashMap param = new HashMap();
                    param.put("userRoleList", new ArrayList());
                    receiverMap.put("param", param);
                    receiver.add(receiverMap);
                    messageInstanceVO.setReceiver(receiver);
                } else if (actionCode.equals("act_apply_reject")) {
                    messageInstanceVO.setTitle("\u7533\u8bf7\u9000\u56de\u901a\u77e5-\u90ae\u4ef6");
                    messageInstanceVO.setSubject("\u7533\u8bf7\u9000\u56de\u901a\u77e5-\u90ae\u4ef6");
                    String content = "<p>&nbsp;&nbsp;<span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u4efb\u52a1\u540d\u79f0\" data-code=\"taskName\">\u3010\u4efb\u52a1\u540d\u79f0\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u65f6\u671f\" data-code=\"period\">\u3010\u65f6\u671f\u3011</span>" + entityCaliberVariable + "<span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\" data-code=\"mdName\">\u3010\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\u3011</span>" + formOrFormGroupVariable + "\u5df2\u7533\u8bf7\u9000\u56de\uff0c\u8bf7\u53ca\u65f6\u5ba1\u6279\u3002" + "</p>";
                    messageInstanceVO.setContent(honorific_user + honorific_hello + content);
                    ArrayList receiver = new ArrayList();
                    HashMap<String, Object> receiverMap = new HashMap<String, Object>();
                    receiverMap.put("strategy", "auditNodeTodoReceiverStrategy");
                    HashMap param = new HashMap();
                    param.put("userRoleList", new ArrayList());
                    receiverMap.put("param", param);
                    receiver.add(receiverMap);
                    messageInstanceVO.setReceiver(receiver);
                }
            } else if (workflowNode.equals("tsk_audit")) {
                if (actionCode.equals("act_confirm")) {
                    messageInstanceVO.setTitle("\u786e\u8ba4\u901a\u77e5-\u90ae\u4ef6");
                    messageInstanceVO.setSubject("\u786e\u8ba4\u901a\u77e5-\u90ae\u4ef6");
                    String content = "<p>&nbsp;&nbsp;<span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u4efb\u52a1\u540d\u79f0\" data-code=\"taskName\">\u3010\u4efb\u52a1\u540d\u79f0\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u65f6\u671f\" data-code=\"period\">\u3010\u65f6\u671f\u3011</span>" + entityCaliberVariable + "<span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\" data-code=\"mdName\">\u3010\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\u3011</span>" + formOrFormGroupVariable + "\u5df2\u786e\u8ba4\u3002" + "</p>";
                    messageInstanceVO.setContent(honorific_user + honorific_hello + content);
                    ArrayList receiver = new ArrayList();
                    HashMap<String, Object> receiverMap = new HashMap<String, Object>();
                    receiverMap.put("strategy", "reportNodeTodoReceiverStrategy");
                    HashMap param = new HashMap();
                    param.put("userRoleList", new ArrayList());
                    receiverMap.put("param", param);
                    receiver.add(receiverMap);
                    messageInstanceVO.setReceiver(receiver);
                } else if (actionCode.equals("act_reject")) {
                    messageInstanceVO.setTitle("\u9000\u56de\u901a\u77e5-\u90ae\u4ef6");
                    messageInstanceVO.setSubject("\u9000\u56de\u901a\u77e5-\u90ae\u4ef6");
                    String content = "<p>&nbsp;&nbsp;<span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u4efb\u52a1\u540d\u79f0\" data-code=\"taskName\">\u3010\u4efb\u52a1\u540d\u79f0\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u65f6\u671f\" data-code=\"period\">\u3010\u65f6\u671f\u3011</span>" + entityCaliberVariable + "<span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\" data-code=\"mdName\">\u3010\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\u3011</span>" + formOrFormGroupVariable + auditNode_Reject_Text + "</p>";
                    messageInstanceVO.setContent(honorific_user + honorific_hello + content);
                    ArrayList receiver = new ArrayList();
                    HashMap<String, Object> receiverMap = new HashMap<String, Object>();
                    receiverMap.put("strategy", workflowDefineTemplate.equals((Object)WorkflowDefineTemplate.SUBMIT_WORKFLOW) ? "submitNodeTodoReceiverStrategy" : "reportNodeTodoReceiverStrategy");
                    HashMap param = new HashMap();
                    param.put("userRoleList", new ArrayList());
                    receiverMap.put("param", param);
                    receiver.add(receiverMap);
                    messageInstanceVO.setReceiver(receiver);
                }
            }
        }
        return messageInstanceVO;
    }

    @Override
    public List<Map<String, Object>> getReceiverSource(MessageInstanceBaseContext context) {
        ArrayList<Map<String, Object>> receiverSource = new ArrayList<Map<String, Object>>();
        WorkflowDefineTemplate defineTemplate = WorkflowDefineTemplate.valueOf((String)context.getWorkflowDefineTemplate());
        if (defineTemplate.equals((Object)WorkflowDefineTemplate.SUBMIT_WORKFLOW)) {
            HashMap<String, String> submitNodeActionExecuter = new HashMap<String, String>();
            submitNodeActionExecuter.put(KEY_OF_CODE, "submitNodeActionExecuterStrategy");
            submitNodeActionExecuter.put(KEY_OF_TITLE, "\u9001\u5ba1\u8282\u70b9\u52a8\u4f5c\u6267\u884c\u4eba");
            receiverSource.add(submitNodeActionExecuter);
        }
        HashMap<String, String> reportNodeActionExecuter = new HashMap<String, String>();
        reportNodeActionExecuter.put(KEY_OF_CODE, "reportNodeActionExecuterStrategy");
        reportNodeActionExecuter.put(KEY_OF_TITLE, "\u4e0a\u62a5\u8282\u70b9\u52a8\u4f5c\u6267\u884c\u4eba");
        receiverSource.add(reportNodeActionExecuter);
        HashMap<String, String> auditNodeActionExecuter = new HashMap<String, String>();
        auditNodeActionExecuter.put(KEY_OF_CODE, "auditNodeActionExecuterStrategy");
        auditNodeActionExecuter.put(KEY_OF_TITLE, "\u5ba1\u6279\u8282\u70b9\u52a8\u4f5c\u6267\u884c\u4eba");
        receiverSource.add(auditNodeActionExecuter);
        boolean taskTodoEnable = context.isIsTodoEnabled();
        String optionValue = this.nvwaSystemOptionService.get("nr-flow-todo-id", "PROCESS_UPLOAD_CAN_SEND_MSG");
        boolean isSystemTodoEnabled = optionValue.contains("0");
        if (isSystemTodoEnabled && taskTodoEnable) {
            if (defineTemplate.equals((Object)WorkflowDefineTemplate.SUBMIT_WORKFLOW)) {
                HashMap<String, String> submitNodeTodoReceiver = new HashMap<String, String>();
                submitNodeTodoReceiver.put(KEY_OF_CODE, "submitNodeTodoReceiverStrategy");
                submitNodeTodoReceiver.put(KEY_OF_TITLE, "\u9001\u5ba1\u8282\u70b9\u5f85\u529e\u63a5\u6536\u4eba");
                receiverSource.add(submitNodeTodoReceiver);
            }
            HashMap<String, String> reportNodeTodoReceiver = new HashMap<String, String>();
            reportNodeTodoReceiver.put(KEY_OF_CODE, "reportNodeTodoReceiverStrategy");
            reportNodeTodoReceiver.put(KEY_OF_TITLE, "\u4e0a\u62a5\u8282\u70b9\u5f85\u529e\u63a5\u6536\u4eba");
            receiverSource.add(reportNodeTodoReceiver);
            HashMap<String, String> auditNodeTodoReceiver = new HashMap<String, String>();
            auditNodeTodoReceiver.put(KEY_OF_CODE, "auditNodeTodoReceiverStrategy");
            auditNodeTodoReceiver.put(KEY_OF_TITLE, "\u5ba1\u6279\u8282\u70b9\u5f85\u529e\u63a5\u6536\u4eba");
            receiverSource.add(auditNodeTodoReceiver);
        }
        List strategies = this.actorStrategyFactory.queryAllActorStrategyDefinition();
        for (IActorStrategyDefinition strategy : strategies) {
            HashMap<String, String> strategyMap = new HashMap<String, String>();
            strategyMap.put(KEY_OF_CODE, strategy.getId());
            strategyMap.put(KEY_OF_TITLE, strategy.getTitle());
            receiverSource.add(strategyMap);
        }
        return receiverSource;
    }

    @Override
    public List<Map<String, Object>> receiverDowngrade(MessageInstanceStrategyVerifyContext context) {
        boolean isTodoEnabled = context.isIsTodoEnabled();
        if (isTodoEnabled) {
            return (List)context.getReceiver();
        }
        Object receiver = context.getReceiver();
        ObjectMapper objectMapper = new ObjectMapper();
        List receiverList = (List)objectMapper.convertValue(receiver, (TypeReference)new TypeReference<List<Map<String, Object>>>(){});
        for (Map receiverMap : receiverList) {
            receiverMap.put("strategy", this.downGradeStrategy(receiverMap.get("strategy").toString()));
        }
        return receiverList;
    }

    @Override
    public List<Map<String, Object>> getVariableSource(MessageInstanceBaseContext context) {
        ArrayList<Map<String, Object>> variableSource = new ArrayList<Map<String, Object>>();
        for (MessageVariable variable : MessageVariable.values()) {
            HashMap<String, String> variableSourceMap = new HashMap<String, String>();
            variableSourceMap.put(KEY_OF_CODE, variable.code);
            variableSourceMap.put(KEY_OF_TITLE, variable.title);
            variableSource.add(variableSourceMap);
        }
        return variableSource;
    }

    private String downGradeStrategy(String currentStrategy) {
        switch (currentStrategy) {
            case "submitNodeTodoReceiverStrategy": {
                currentStrategy = "submitNodeActionExecuterStrategy";
                break;
            }
            case "reportNodeTodoReceiverStrategy": {
                currentStrategy = "reportNodeActionExecuterStrategy";
                break;
            }
            case "auditNodeTodoReceiverStrategy": {
                currentStrategy = "auditNodeActionExecuterStrategy";
            }
        }
        return currentStrategy;
    }
}

