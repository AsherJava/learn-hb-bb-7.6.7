/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.domain.workflow.ProcessDO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDO
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 *  com.jiuqi.va.domain.workflow.forward.process.ForwardProcessDTO
 *  com.jiuqi.va.domain.workflow.forward.process.ForwardProcessNode
 *  com.jiuqi.va.domain.workflow.service.WorkflowForwardProcessService
 *  com.jiuqi.va.feign.client.AuthUserClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.workflow.service.impl.help;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.domain.workflow.ProcessDO;
import com.jiuqi.va.domain.workflow.ProcessNodeDO;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import com.jiuqi.va.domain.workflow.forward.process.ForwardProcessDTO;
import com.jiuqi.va.domain.workflow.forward.process.ForwardProcessNode;
import com.jiuqi.va.domain.workflow.service.WorkflowForwardProcessService;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.workflow.domain.forward.AuditInfo;
import com.jiuqi.va.workflow.domain.forward.RejectDesignateNodeVO;
import com.jiuqi.va.workflow.model.WorkflowModel;
import com.jiuqi.va.workflow.service.impl.help.WorkflowParamService;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import com.jiuqi.va.workflow.utils.VaWorkflowUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class WorkflowRejectDesignateNodeService {
    @Autowired
    private WorkflowParamService workflowParamService;
    @Autowired
    private AuthUserClient authUserClient;
    private WorkflowForwardProcessService workflowForwardProcessService;

    public WorkflowForwardProcessService getWorkflowForwardProcessService() {
        if (this.workflowForwardProcessService == null) {
            this.workflowForwardProcessService = (WorkflowForwardProcessService)ApplicationContextRegister.getBean(WorkflowForwardProcessService.class);
        }
        return this.workflowForwardProcessService;
    }

    public R list(WorkflowDTO workflowDTO, ProcessDO processDO, List<ProcessNodeDO> processNodeDOList) {
        ArrayNode arrayNode = this.getArrayNode(processDO);
        List<ForwardProcessNode> forwardProcess = this.getForwardProcess(workflowDTO, processNodeDOList, arrayNode);
        List<RejectDesignateNodeVO> rejectDesignateNodeVOList = this.getRejectDesignateNodeVOList(forwardProcess);
        String nodeCode = workflowDTO.getNodeCode();
        if (StringUtils.hasText(nodeCode)) {
            List<String> rejectNodesList = this.getRejectDesignateNodesList(nodeCode, arrayNode);
            Collections.reverse(processNodeDOList);
            ProcessNodeDO targetNode = VaWorkflowUtils.findTargetNode(nodeCode, workflowDTO.getSubProcessBranch(), processNodeDOList);
            this.setCanReject(rejectDesignateNodeVOList, targetNode, rejectNodesList);
        }
        VaWorkFlowI18nUtils.convertRejectNodeI18n(rejectDesignateNodeVOList, processDO, arrayNode);
        return R.ok().put("data", rejectDesignateNodeVOList);
    }

    private List<ForwardProcessNode> getForwardProcess(WorkflowDTO workflowDTO, List<ProcessNodeDO> processNodeDOList, ArrayNode arrayNode) {
        ForwardProcessDTO forwardProcessDTO = new ForwardProcessDTO();
        forwardProcessDTO.setBizCode(workflowDTO.getBizCode());
        forwardProcessDTO.setSubProcessBranch(workflowDTO.getSubProcessBranch());
        forwardProcessDTO.setWorkflowDefineData(arrayNode);
        forwardProcessDTO.setProcessNodeDOList(processNodeDOList);
        forwardProcessDTO.setCalculatePendingNode(true);
        return this.getWorkflowForwardProcessService().getForwardProcess(forwardProcessDTO);
    }

    private List<RejectDesignateNodeVO> getRejectDesignateNodeVOList(List<ForwardProcessNode> forwardProcess) {
        ArrayList<RejectDesignateNodeVO> nodes = new ArrayList<RejectDesignateNodeVO>();
        HashMap<String, String> userCache = new HashMap<String, String>();
        for (ForwardProcessNode forwardProcessNode : forwardProcess) {
            Map subProcessNodeMap;
            RejectDesignateNodeVO nodeVO = this.convertForwardToReject(forwardProcessNode, userCache);
            Map pgwNodeMap = forwardProcessNode.getPgwNodeMap();
            if (!CollectionUtils.isEmpty(pgwNodeMap)) {
                nodeVO.setPgwBranch("1");
                nodeVO.setChildren(this.getPgwBranchList(pgwNodeMap, userCache));
            }
            if (!CollectionUtils.isEmpty(subProcessNodeMap = forwardProcessNode.getSubProcessNodeMap())) {
                nodeVO.setSubProcessBranch("1");
                nodeVO.setChildren(this.getSubProcessBranchList(subProcessNodeMap, userCache));
            }
            nodes.add(nodeVO);
            if (!forwardProcessNode.isPendingNode()) continue;
            break;
        }
        return nodes;
    }

    private List<List<RejectDesignateNodeVO>> getSubProcessBranchList(Map<String, List<ForwardProcessNode>> subProcessNodeMap, Map<String, String> userCache) {
        ArrayList<List<RejectDesignateNodeVO>> children = new ArrayList<List<RejectDesignateNodeVO>>();
        for (Map.Entry<String, List<ForwardProcessNode>> entry : subProcessNodeMap.entrySet()) {
            ArrayList<RejectDesignateNodeVO> childrenList = new ArrayList<RejectDesignateNodeVO>();
            for (ForwardProcessNode childForwardNode : entry.getValue()) {
                RejectDesignateNodeVO childVO = this.convertForwardToReject(childForwardNode, userCache);
                childrenList.add(childVO);
                if (!childForwardNode.isPendingNode()) continue;
                break;
            }
            children.add(childrenList);
        }
        return children;
    }

    private List<List<RejectDesignateNodeVO>> getPgwBranchList(Map<String, List<ForwardProcessNode>> pgwNodeMap, Map<String, String> userCache) {
        ArrayList<List<RejectDesignateNodeVO>> children = new ArrayList<List<RejectDesignateNodeVO>>();
        for (Map.Entry<String, List<ForwardProcessNode>> entry : pgwNodeMap.entrySet()) {
            ArrayList<RejectDesignateNodeVO> childrenList = new ArrayList<RejectDesignateNodeVO>();
            for (ForwardProcessNode childForwardNode : entry.getValue()) {
                RejectDesignateNodeVO childVO = this.convertForwardToReject(childForwardNode, userCache);
                childrenList.add(childVO);
                if (!childForwardNode.isPendingNode()) continue;
                break;
            }
            children.add(childrenList);
        }
        return children;
    }

    private RejectDesignateNodeVO convertForwardToReject(ForwardProcessNode forwardProcessNode, Map<String, String> userCache) {
        RejectDesignateNodeVO nodeVO = new RejectDesignateNodeVO();
        nodeVO.setNodeName(forwardProcessNode.getNodeName());
        nodeVO.setAuditInfo(this.getAuditInfos(forwardProcessNode, userCache));
        boolean pendingFlag = forwardProcessNode.isPendingNode();
        nodeVO.setAuditState(pendingFlag ? 2 : 1);
        nodeVO.setAuditStatus(pendingFlag ? VaWorkFlowI18nUtils.getInfo("va.workflow.pending") : VaWorkFlowI18nUtils.getInfo("va.workflow.havebeenapproval"));
        nodeVO.setCanReject(1);
        nodeVO.setStencilId(forwardProcessNode.getNodeCode());
        return nodeVO;
    }

    private void setCanReject(List<RejectDesignateNodeVO> nodes, ProcessNodeDO targetNode, List<String> rejectNodesList) {
        for (RejectDesignateNodeVO node : nodes) {
            boolean canReject = node.getAuditState() == 1 && (rejectNodesList.isEmpty() || rejectNodesList.contains(node.getStencilId()));
            node.setCanReject(canReject ? 1 : 0);
            List<List<RejectDesignateNodeVO>> children = node.getChildren();
            if (CollectionUtils.isEmpty(children)) continue;
            if (StringUtils.hasText(node.getPgwBranch())) {
                this.setPgwBranchNodeCanReject(targetNode, rejectNodesList, children);
                continue;
            }
            node.setCanReject(0);
            this.setSubprocessBranchNodeCanReject(targetNode, rejectNodesList, children);
        }
    }

    private void setSubprocessBranchNodeCanReject(ProcessNodeDO targetNode, List<String> rejectNodesList, List<List<RejectDesignateNodeVO>> children) {
        if (StringUtils.hasText(targetNode.getSubprocessbranch())) {
            this.setCanReject(children.get(0), targetNode, rejectNodesList);
        } else {
            children.get(0).forEach(o -> o.setCanReject(0));
        }
    }

    private void setPgwBranchNodeCanReject(ProcessNodeDO targetNode, List<String> rejectNodesList, List<List<RejectDesignateNodeVO>> children) {
        if (StringUtils.hasText(targetNode.getPgwbranch())) {
            for (List<RejectDesignateNodeVO> child2 : children) {
                List nodeCodeList = child2.stream().map(RejectDesignateNodeVO::getStencilId).collect(Collectors.toList());
                if (nodeCodeList.contains(targetNode.getNodecode())) {
                    this.setCanReject(child2, targetNode, rejectNodesList);
                    continue;
                }
                child2.forEach(o -> o.setCanReject(0));
            }
        } else {
            children.forEach(child -> this.setCanReject((List<RejectDesignateNodeVO>)child, targetNode, rejectNodesList));
        }
    }

    private List<AuditInfo> getAuditInfos(ForwardProcessNode forwardProcessNode, Map<String, String> userCache) {
        ArrayList<AuditInfo> auditInfoList = new ArrayList<AuditInfo>();
        List processNodeDOS = forwardProcessNode.getProcessNodeDOList();
        if (!CollectionUtils.isEmpty(processNodeDOS)) {
            for (ProcessNodeDO processNodeDO : processNodeDOS) {
                AuditInfo auditInfo = new AuditInfo();
                String completeResult = processNodeDO.getCompleteresult();
                if ("\u63d0\u4ea4".equals(completeResult)) {
                    auditInfo.setComment(VaWorkFlowI18nUtils.getInfo("va.workflow.submit"));
                } else {
                    auditInfo.setComment(processNodeDO.getCompletecomment());
                }
                auditInfo.setCompleteTime(processNodeDO.getCompletetime());
                auditInfo.setAuditResult(completeResult);
                BigDecimal completeUserType = processNodeDO.getCompleteusertype();
                String auditUser = this.getUserName(processNodeDO.getCompleteuserid(), userCache);
                if (completeUserType != null) {
                    int completeUserTypeInt = completeUserType.intValue();
                    if (completeUserTypeInt == 1) {
                        auditUser = auditUser + "(" + VaWorkFlowI18nUtils.getInfo("va.workflow.predictprocess.signatureperson") + ")";
                    } else if (completeUserTypeInt == 2) {
                        auditUser = auditUser + "(" + VaWorkFlowI18nUtils.getInfo("va.workflow.predictprocess.agent") + ")";
                    }
                }
                auditInfo.setAuditUser(auditUser);
                auditInfoList.add(auditInfo);
            }
        }
        return auditInfoList;
    }

    private ArrayNode getArrayNode(ProcessDO processDO) {
        WorkflowModel workflowModel = this.workflowParamService.getModel(processDO.getDefinekey(), processDO.getDefineversion().longValue());
        ArrayNode oldArrayNode = this.workflowParamService.getWorkflowProcessNode(workflowModel);
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode subProcessArrayNode = objectMapper.createArrayNode();
        for (JsonNode jsonNode : oldArrayNode) {
            if (ObjectUtils.isEmpty(jsonNode.get("childShapes"))) continue;
            JsonNode childShapes = jsonNode.get("childShapes");
            for (JsonNode childShape : childShapes) {
                String nodeType = childShape.get("stencil").get("id").asText();
                if ("StartNoneEvent".equals(nodeType) || "EndNoneEvent".equals(nodeType)) continue;
                subProcessArrayNode.add(childShape);
            }
        }
        ArrayNode arrayNode = oldArrayNode.deepCopy();
        arrayNode.addAll(subProcessArrayNode);
        return arrayNode;
    }

    private List<String> getRejectDesignateNodesList(String nodeCode, ArrayNode arrayNodes) {
        JsonNode properties = null;
        block0: for (JsonNode jsonNode : arrayNodes) {
            String resourceId = jsonNode.get("resourceId").asText();
            if (nodeCode.equals(resourceId)) {
                properties = jsonNode.get("properties");
                break;
            }
            JsonNode childShapes = jsonNode.get("childShapes");
            if (ObjectUtils.isEmpty(childShapes)) continue;
            for (JsonNode childShape : childShapes) {
                String childResourceId = childShape.get("resourceId").asText();
                if (!nodeCode.equals(childResourceId)) continue;
                properties = childShape.get("properties");
                continue block0;
            }
        }
        return this.getRejectNodeList(properties);
    }

    private List<String> getRejectNodeList(JsonNode properties) {
        ArrayNode rejectNodeArrayNode;
        ArrayList<String> rejectNodesList = new ArrayList<String>();
        if (properties == null) {
            return rejectNodesList;
        }
        JsonNode priorityDefinition = properties.get("prioritydefinition");
        JsonNode rejectNodeParamJsonNode = properties.get("servicetaskresultvariable");
        if (priorityDefinition == null || rejectNodeParamJsonNode == null) {
            return rejectNodesList;
        }
        if ("3".equals(priorityDefinition.asText()) && Objects.nonNull(rejectNodeArrayNode = (ArrayNode)rejectNodeParamJsonNode.get("value"))) {
            for (JsonNode value : rejectNodeArrayNode) {
                JsonNode id = value.get("id");
                if (!Objects.nonNull(id)) continue;
                rejectNodesList.add(value.get("id").asText());
            }
        }
        return rejectNodesList;
    }

    private String getUserName(String userId, Map<String, String> userCache) {
        String userName = "";
        if (!StringUtils.hasText(userId)) {
            return userName;
        }
        if (userCache.containsKey(userId)) {
            userName = userCache.get(userId);
        } else {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(userId);
            UserDO userDO = this.authUserClient.get(userDTO);
            if (userDO != null) {
                userName = userDO.getName();
                userCache.put(userId, userName);
            }
        }
        return userName;
    }
}

