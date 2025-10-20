/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.todo.TaskDTO
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDTO
 *  com.jiuqi.va.domain.workflow.VaContext
 *  com.jiuqi.va.domain.workflow.VaWorkflowContext
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 *  com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeService
 *  com.jiuqi.va.feign.client.TodoClient
 *  com.jiuqi.va.trans.domain.VaTransMessageDO
 *  com.jiuqi.va.trans.service.VaTransMessageService
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.va.workflow.service.plus.approval.impl;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.todo.TaskDTO;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.domain.workflow.ProcessNodeDO;
import com.jiuqi.va.domain.workflow.ProcessNodeDTO;
import com.jiuqi.va.domain.workflow.VaContext;
import com.jiuqi.va.domain.workflow.VaWorkflowContext;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeService;
import com.jiuqi.va.feign.client.TodoClient;
import com.jiuqi.va.trans.domain.VaTransMessageDO;
import com.jiuqi.va.trans.service.VaTransMessageService;
import com.jiuqi.va.workflow.dao.WorkflowProcessNodeDao;
import com.jiuqi.va.workflow.service.plus.approval.WorkflowPlusApprovalConsultService;
import com.jiuqi.va.workflow.utils.VaWorkFlowDataUtils;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class WorkflowPlusApprovalConsultServiceImpl
implements WorkflowPlusApprovalConsultService {
    @Autowired
    private WorkflowProcessNodeService workflowProcessNodeService;
    @Autowired
    private VaTransMessageService vaTransMessageService;
    @Autowired
    private TodoClient todoClient;
    @Autowired
    private WorkflowProcessNodeDao workflowProcessNodeDao;

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void consult(WorkflowDTO workflowDTO) {
        String taskId = workflowDTO.getTaskId();
        UserLoginDTO user = VaWorkFlowDataUtils.getCurrentUserInfo();
        String userId = user.getId();
        Map task = workflowDTO.getExtInfo();
        ArrayList<Map> completeTodoTaskList = new ArrayList<Map>();
        ArrayList deleteTodoTaskList = new ArrayList();
        ProcessNodeDTO processNodeDTO = new ProcessNodeDTO();
        processNodeDTO.setNodeid(taskId);
        List processNodeDOList = this.workflowProcessNodeService.listProcessNode(processNodeDTO);
        if (CollectionUtils.isEmpty(processNodeDOList)) {
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.processnotesixt"));
        }
        workflowDTO.setNodeCode(((ProcessNodeDO)processNodeDOList.get(0)).getNodecode());
        HashSet<String> delUserIdSet = new HashSet<String>();
        String delegateId = "";
        for (ProcessNodeDO processNodeDO : processNodeDOList) {
            if (userId.equals(processNodeDO.getCompleteuserid()) && StringUtils.hasText(processNodeDO.getDelegateuser())) {
                delegateId = processNodeDO.getDelegateuser();
                delUserIdSet.add(delegateId);
            }
            if (!StringUtils.hasText(processNodeDO.getDelegateuser()) || !userId.equals(processNodeDO.getDelegateuser())) continue;
            delUserIdSet.add(processNodeDO.getCompleteuserid());
        }
        if (StringUtils.hasText(delegateId)) {
            if (!userId.equals(delegateId)) {
                HashMap paramMap = new HashMap();
                paramMap.put("delegateId", delegateId);
                workflowDTO.setExtInfo((Map)paramMap);
            }
            for (ProcessNodeDO processNodeDO : processNodeDOList) {
                if (!StringUtils.hasText(processNodeDO.getDelegateuser()) || !delegateId.equals(processNodeDO.getDelegateuser()) || userId.equals(processNodeDO.getCompleteuserid())) continue;
                delUserIdSet.add(processNodeDO.getCompleteuserid());
            }
        }
        for (String delUserId : delUserIdSet) {
            HashMap<String, String> delegateTodoTask = new HashMap<String, String>();
            delegateTodoTask.put("PARTICIPANT", delUserId);
            delegateTodoTask.put("TASKID", taskId);
            delegateTodoTask.put("BIZCODE", workflowDTO.getBizCode());
            deleteTodoTaskList.add(delegateTodoTask);
        }
        String completeComment = workflowDTO.getApprovalComment() == null ? "\u540c\u610f" : workflowDTO.getApprovalComment();
        task.put("COMPLETEUSER", userId);
        task.put("COMPLETEUNITCODE", user.getLoginUnit());
        task.put("COMPLETERESULT", 1);
        task.put("COMPLETETIME", new Date().getTime());
        task.put("COMPLETECOMMENT", completeComment);
        task.put("PROCESSSTATUS", 0);
        task.put("consult", true);
        completeTodoTaskList.add(task);
        ProcessNodeDO processNode = (ProcessNodeDO)processNodeDOList.stream().filter(nodeDO -> Objects.equals(nodeDO.getCompleteuserid(), userId) && Objects.equals(nodeDO.getNodeid(), taskId)).collect(Collectors.toList()).get(0);
        processNode.setCountersignflag(new BigDecimal(1));
        processNode.setCompleteunitcode(String.valueOf(task.get("UNITCODE")));
        processNode.setCompletetime(new Date());
        processNode.setCompleteresult("\u63d0\u4ea4\u610f\u89c1");
        processNode.setCompletecomment(completeComment);
        processNode.setCommentcolor(new BigDecimal(1));
        if (workflowDTO.isCommonComment()) {
            processNode.addExtInfo("addCommonComment", (Object)true);
        }
        processNode.addExtInfo("delNodeUserSet", new ArrayList(delUserIdSet));
        this.workflowProcessNodeService.update(processNode);
        VaTransMessageDO vaTransMessageDO = new VaTransMessageDO();
        String msgId = UUID.randomUUID().toString();
        vaTransMessageDO.setId(msgId);
        vaTransMessageDO.setBizcategory(workflowDTO.getBizType());
        vaTransMessageDO.setBiztype(workflowDTO.getBizDefine());
        vaTransMessageDO.setBizcode(workflowDTO.getBizCode());
        vaTransMessageDO.setExchangename(workflowDTO.getBizModule());
        vaTransMessageDO.setDefinename("workflow-complete");
        HashMap paramMap = new HashMap();
        paramMap.put("completeTodoTasks", completeTodoTaskList);
        paramMap.put("deleteTodoTasks", deleteTodoTaskList);
        this.vaTransMessageService.insertTrans(vaTransMessageDO, JSONUtil.toJSONString(paramMap));
        VaWorkflowContext vaWorkflowContext = VaContext.getVaWorkflowContext();
        Map customParam = vaWorkflowContext.getCustomParam();
        customParam.put("transMessageId", msgId);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void retract(ProcessNodeDO processNode) {
        VaWorkflowContext vaWorkflowContext = VaContext.getVaWorkflowContext();
        Map customParam = vaWorkflowContext.getCustomParam();
        WorkflowDTO workflowDTO = vaWorkflowContext.getWorkflowDTO();
        String processInstanceId = workflowDTO.getProcessInstanceId();
        String taskId = workflowDTO.getTaskId();
        String bizType = workflowDTO.getBizType();
        String bizDefine = workflowDTO.getBizDefine();
        String bizCode = workflowDTO.getBizCode();
        String userId = ShiroUtil.getUser().getId();
        processNode.setCompleteresult("\u53d6\u56de");
        this.workflowProcessNodeDao.update(processNode);
        processNode.setOrdernum(null);
        processNode.setReceivetime(null);
        processNode.setCompleteunitcode(null);
        processNode.setCompletetime(null);
        processNode.setCompleteresult(null);
        processNode.setCompletecomment(null);
        processNode.setCommentcolor(null);
        processNode.setTenantName(ShiroUtil.getTenantName());
        this.workflowProcessNodeService.add(processNode);
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTaskId(workflowDTO.getTaskId());
        taskDTO.setProcessId(workflowDTO.getProcessInstanceId());
        taskDTO.setParticipant(userId);
        taskDTO.setPagination(false);
        taskDTO.setBackendRequest(true);
        List rows = this.todoClient.listHistoryTask(taskDTO).getRows();
        if (CollectionUtils.isEmpty(rows)) {
            throw new RuntimeException("va.workflow.taskbeendealneedtorefresh");
        }
        Map todoTaskHistoryMap = (Map)rows.get(rows.size() - 1);
        todoTaskHistoryMap.remove("PROCESSSTATUS");
        todoTaskHistoryMap.remove("COMPLETEUSER");
        todoTaskHistoryMap.remove("COMPLETEUNITCODE");
        todoTaskHistoryMap.remove("COMPLETETIME");
        todoTaskHistoryMap.remove("COMPLETERESULT");
        todoTaskHistoryMap.remove("COMPLETECOMMENT");
        todoTaskHistoryMap.remove("RECEIVETIME");
        todoTaskHistoryMap.put("PARTICIPANT", userId);
        ArrayList<Map> todoTaskList = new ArrayList<Map>();
        todoTaskList.add(todoTaskHistoryMap);
        VaTransMessageDO vaTransMessageDO = new VaTransMessageDO();
        String msgId = UUID.randomUUID().toString();
        vaTransMessageDO.setId(msgId);
        vaTransMessageDO.setBizcategory(bizType);
        vaTransMessageDO.setBiztype(bizDefine);
        vaTransMessageDO.setBizcode(bizCode);
        vaTransMessageDO.setExchangename(workflowDTO.getBizModule());
        vaTransMessageDO.setDefinename("workflow-retract");
        HashMap<String, Object> inputParamJson = new HashMap<String, Object>();
        inputParamJson.put("TASKID", taskId);
        inputParamJson.put("PROCESSID", processInstanceId);
        inputParamJson.put("todoTasks", todoTaskList);
        inputParamJson.put("approvalFlag", 1);
        this.vaTransMessageService.insertTrans(vaTransMessageDO, JSONUtil.toJSONString(inputParamJson));
        customParam.put("transMessageId", msgId);
        customParam.put("todoTasks", todoTaskList);
    }
}

