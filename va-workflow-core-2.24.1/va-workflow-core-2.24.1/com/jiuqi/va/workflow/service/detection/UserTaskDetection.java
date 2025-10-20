/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.workflow.ProcessDO
 *  com.jiuqi.va.domain.workflow.VaContext
 *  com.jiuqi.va.domain.workflow.VaWorkflowContext
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 *  com.jiuqi.va.domain.workflow.service.WorkflowStrategySevice
 */
package com.jiuqi.va.workflow.service.detection;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.workflow.ProcessDO;
import com.jiuqi.va.domain.workflow.VaContext;
import com.jiuqi.va.domain.workflow.VaWorkflowContext;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import com.jiuqi.va.domain.workflow.service.WorkflowStrategySevice;
import com.jiuqi.va.workflow.domain.WorkflowOption;
import com.jiuqi.va.workflow.domain.detection.WorkflowDetectResultDO;
import com.jiuqi.va.workflow.domain.detection.WorkflowDetectionElement;
import com.jiuqi.va.workflow.service.WorkflowDetectionHelperService;
import com.jiuqi.va.workflow.service.detection.BaseDetectionHandler;
import com.jiuqi.va.workflow.utils.VaWorkFlowDataUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserTaskDetection
extends BaseDetectionHandler {
    private static final Logger logger = LoggerFactory.getLogger(UserTaskDetection.class);
    @Autowired
    private WorkflowDetectionHelperService helperService;
    @Autowired
    private WorkflowStrategySevice strategyService;

    @Override
    public String getName() {
        return "UserTask";
    }

    @Override
    public List<WorkflowDetectionElement> detect(WorkflowDetectionElement element, List<WorkflowDetectResultDO> result, Map<String, WorkflowDetectionElement> eleMap) {
        int status;
        List<String> nextNodeIds;
        int nextNodeCnt;
        HashSet approvalUsers;
        if (element.isSubmitNode()) {
            return this.defaultDetection(element, result, eleMap);
        }
        ArrayList<WorkflowDetectionElement> nextNodes = new ArrayList<WorkflowDetectionElement>();
        VaWorkflowContext vaWorkflowContext = VaContext.getVaWorkflowContext();
        WorkflowDTO workflowDTO = vaWorkflowContext.getWorkflowDTO();
        ProcessDO processDO = vaWorkflowContext.getProcessDO();
        String workflowDefineKey = workflowDTO.getUniqueCode();
        Integer version = (Integer)workflowDTO.getExtInfo("version");
        Map variables = workflowDTO.getWorkflowVariables();
        String elementName = element.getElementName();
        String elementType = element.getElementType();
        ArrayNode outgoingList = element.getOutgoingList();
        Set<String> allUsers = this.getParticipants(element, workflowDTO, processDO, variables);
        String msg = "";
        boolean skipUserNode = false;
        boolean emptyUser = false;
        if (allUsers.isEmpty()) {
            if ("true".equals(element.getSkipEmptyParticipant())) {
                msg = "\u53c2\u4e0e\u8005\u4e3a\u7a7a\u8df3\u8fc7";
                skipUserNode = true;
            } else {
                emptyUser = true;
            }
        }
        String autoAgree = element.getAutoAgree();
        boolean autoAgreeFlag = false;
        if ("02".equals(autoAgree) && !emptyUser && (approvalUsers = result.stream().flatMap(node -> node.getParticipants() == null ? Stream.empty() : node.getParticipants().stream()).collect(Collectors.toCollection(HashSet::new))).containsAll(allUsers)) {
            msg = "\u76f8\u540c\u53c2\u4e0e\u8005\u8df3\u8fc7";
            autoAgreeFlag = true;
        }
        if ((nextNodeCnt = (nextNodeIds = this.helperService.getNextEle(eleMap, workflowDefineKey, version, variables, outgoingList)).size()) == 0) {
            msg = "\u6ca1\u6709\u540e\u7eed\u53ef\u8fbe\u8282\u70b9";
            status = WorkflowOption.DetectStatus.FAIL.getType();
        } else if (nextNodeCnt > 1) {
            String selectNextNode = element.getSelectNextApprovalNode();
            boolean selectNextNodeFlag = "true".equals(selectNextNode);
            if (selectNextNodeFlag) {
                if (!autoAgreeFlag && !skipUserNode) {
                    msg = this.getShowUserNames(allUsers);
                }
                status = WorkflowOption.DetectStatus.SUCCESS.getType();
                for (String nextNodeId : nextNodeIds) {
                    nextNodes.add(eleMap.get(nextNodeId));
                }
            } else {
                msg = "\u6709\u591a\u4e2a\u540e\u7eed\u53ef\u8fbe\u8282\u70b9";
                status = WorkflowOption.DetectStatus.FAIL.getType();
            }
        } else {
            WorkflowDetectionElement nextNode = eleMap.get(nextNodeIds.get(0));
            if (nextNode == null) {
                msg = "\u6ca1\u6709\u540e\u7eed\u53ef\u8fbe\u8282\u70b9";
                status = WorkflowOption.DetectStatus.FAIL.getType();
            } else {
                if (emptyUser) {
                    status = WorkflowOption.DetectStatus.FAIL.getType();
                    msg = msg + "\u672a\u914d\u7f6e\u53c2\u4e0e\u8005";
                } else {
                    status = WorkflowOption.DetectStatus.SUCCESS.getType();
                    if (!autoAgreeFlag && !skipUserNode) {
                        msg = this.getShowUserNames(allUsers);
                    }
                }
                nextNodes.add(nextNode);
            }
        }
        WorkflowDetectResultDO resultDO = this.helperService.createDetectResult(elementName, elementType, allUsers, status, msg);
        result.add(resultDO);
        return nextNodes;
    }

    private Set<String> getParticipants(WorkflowDetectionElement element, WorkflowDTO workflowDTO, ProcessDO processDO, Map<String, Object> variables) {
        String multiInstanceType = element.getMultiInstanceType();
        HashSet<String> allUsers = new HashSet<String>();
        ArrayNode userTaskAssignment = null;
        if ("Parallel".equals(multiInstanceType)) {
            String multiInstanceCollection = element.getMultiInstanceCollection();
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                userTaskAssignment = (ArrayNode)objectMapper.readTree(multiInstanceCollection);
            }
            catch (JsonProcessingException e) {
                logger.error("\u89e3\u6790\u4f1a\u7b7e\u53c2\u4e0e\u8005\u914d\u7f6e\u5f02\u5e38" + e.getMessage(), e);
            }
        } else {
            userTaskAssignment = element.getUserTaskAssignment();
        }
        if (userTaskAssignment == null) {
            return allUsers;
        }
        for (JsonNode assignment : userTaskAssignment) {
            Set users;
            try {
                String strategyModuleName = assignment.get("strategyModuleName").asText();
                String strategyName = assignment.get("strategyName").asText();
                HashMap<String, Object> param = new HashMap<String, Object>();
                param.put("variables", variables);
                param.put("assignParam", assignment.get("items"));
                param.put("workflowDTO", workflowDTO);
                param.put("processDO", processDO);
                param.put("currentNodeCode", element.getElementId());
                users = this.strategyService.execute(strategyModuleName, strategyName, param);
            }
            catch (Exception e) {
                logger.error("\u83b7\u53d6\u5ba1\u6279\u4eba\u5931\u8d25" + e.getMessage());
                continue;
            }
            allUsers.addAll(users);
        }
        return allUsers;
    }

    private String getShowUserNames(Set<String> allUsers) {
        String msg = allUsers.size() > 5 ? allUsers.stream().limit(5L).map(userId -> {
            UserDO userDO = VaWorkFlowDataUtils.getOneUserData(null, userId);
            String userName = "";
            if (userDO != null) {
                userName = userDO.getName();
            }
            return userName;
        }).collect(Collectors.joining("\u3001")) + "..." : allUsers.stream().limit(5L).map(userId -> {
            UserDO userDO = VaWorkFlowDataUtils.getOneUserData(null, userId);
            String userName = "";
            if (userDO != null) {
                userName = userDO.getName();
            }
            return userName;
        }).collect(Collectors.joining("\u3001"));
        return msg;
    }
}

