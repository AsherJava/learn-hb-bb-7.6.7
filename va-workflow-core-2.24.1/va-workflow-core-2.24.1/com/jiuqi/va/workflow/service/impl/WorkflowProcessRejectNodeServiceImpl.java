/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.jiuqi.va.domain.workflow.NodeRejectType
 *  com.jiuqi.va.domain.workflow.ProcessDO
 *  com.jiuqi.va.domain.workflow.ProcessDTO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDTO
 *  com.jiuqi.va.domain.workflow.ProcessRejectNodeDO
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 *  com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeService
 *  com.jiuqi.va.domain.workflow.service.WorkflowProcessRejectNodeService
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.va.workflow.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jiuqi.va.domain.workflow.NodeRejectType;
import com.jiuqi.va.domain.workflow.ProcessDO;
import com.jiuqi.va.domain.workflow.ProcessDTO;
import com.jiuqi.va.domain.workflow.ProcessNodeDO;
import com.jiuqi.va.domain.workflow.ProcessNodeDTO;
import com.jiuqi.va.domain.workflow.ProcessRejectNodeDO;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeService;
import com.jiuqi.va.domain.workflow.service.WorkflowProcessRejectNodeService;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.workflow.dao.VaWorkflowProcessDao;
import com.jiuqi.va.workflow.dao.WorkflowProcessRejectNodeDao;
import com.jiuqi.va.workflow.model.WorkflowModel;
import com.jiuqi.va.workflow.service.impl.help.WorkflowParamService;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import com.jiuqi.va.workflow.utils.VaWorkflowUtils;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class WorkflowProcessRejectNodeServiceImpl
implements WorkflowProcessRejectNodeService {
    @Autowired
    private WorkflowProcessRejectNodeDao workflowProcessRejectNodeDao;
    @Autowired
    private VaWorkflowProcessDao vaWorkflowProcessDao;
    @Autowired
    private WorkflowParamService workflowParamService;
    private WorkflowProcessNodeService workflowProcessNodeService;

    public WorkflowProcessNodeService getWorkflowProcessNodeService() {
        if (this.workflowProcessNodeService == null) {
            this.workflowProcessNodeService = (WorkflowProcessNodeService)ApplicationContextRegister.getBean(WorkflowProcessNodeService.class);
        }
        return this.workflowProcessNodeService;
    }

    public ProcessRejectNodeDO getRejectNodeInfo(ProcessRejectNodeDO processNode) {
        List nodeDOS = VaWorkflowUtils.getList(this.workflowProcessRejectNodeDao.select(processNode));
        return nodeDOS.stream().filter(o -> o.getDeletedflag() == null || BigDecimal.ZERO.equals(o.getDeletedflag())).findFirst().orElse(null);
    }

    @Transactional
    public void addRejectNodeInfo(ProcessRejectNodeDO processNode) {
        ProcessRejectNodeDO processRejectNodeDO = new ProcessRejectNodeDO();
        processRejectNodeDO.setBizcode(processNode.getBizcode());
        processRejectNodeDO.setProcessdefinekey(processNode.getProcessdefinekey());
        processRejectNodeDO.setProcessdefineversion(processNode.getProcessdefineversion());
        processRejectNodeDO.setBerejectednodecode(processNode.getBerejectednodecode());
        processRejectNodeDO.setSubprocessbranch(processNode.getSubprocessbranch());
        processRejectNodeDO.setNodeid(processNode.getNodeid());
        processRejectNodeDO.setDeletedflag(BigDecimal.ZERO);
        this.delRejectNodeInfo(processRejectNodeDO);
        processNode.setId(UUID.randomUUID().toString());
        this.workflowProcessRejectNodeDao.insert(processNode);
    }

    public void delRejectNodeInfo(ProcessRejectNodeDO processNode) {
        this.workflowProcessRejectNodeDao.logicalDelete(processNode);
    }

    public List<ProcessRejectNodeDO> listRejectNodeInfo(ProcessRejectNodeDO processRejectNodeDO) {
        List nodeDOS = VaWorkflowUtils.getList(this.workflowProcessRejectNodeDao.select(processRejectNodeDO));
        return nodeDOS.stream().filter(o -> o.getDeletedflag() == null || BigDecimal.ZERO.equals(o.getDeletedflag())).collect(Collectors.toList());
    }

    public List<ProcessRejectNodeDO> listRejectNodeInfoWithLogicalDeleted(ProcessRejectNodeDO processRejectNodeDO) {
        return this.workflowProcessRejectNodeDao.select(processRejectNodeDO);
    }

    public boolean checkRejectToSubmitter(WorkflowDTO workflowDTO) {
        ProcessNodeDTO processNodeDTO = new ProcessNodeDTO();
        processNodeDTO.setNodeid(workflowDTO.getTaskId());
        List processNodeDOList = this.getWorkflowProcessNodeService().listProcessNode(processNodeDTO);
        ProcessNodeDO processNodeDO = (ProcessNodeDO)processNodeDOList.get(0);
        String nodeCode = processNodeDO.getNodecode();
        ProcessDTO processDTO = new ProcessDTO();
        processDTO.setId(processNodeDO.getProcessid());
        ProcessDO process = this.vaWorkflowProcessDao.getProcess(processDTO);
        WorkflowModel model = this.workflowParamService.getModel(process.getDefinekey(), process.getDefineversion().longValue());
        ArrayNode workflowProcessNode = this.workflowParamService.getWorkflowProcessNode(model);
        if (processNodeDO.getSubprocessbranch() != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            ArrayNode subProcessArrayNode = objectMapper.createArrayNode();
            for (JsonNode jsonNode : workflowProcessNode) {
                if (ObjectUtils.isEmpty(jsonNode.get("childShapes"))) continue;
                JsonNode childShapes = jsonNode.get("childShapes");
                for (JsonNode childShape : childShapes) {
                    String nodeType = childShape.get("stencil").get("id").asText();
                    if ("StartNoneEvent".equals(nodeType) || "EndNoneEvent".equals(nodeType)) continue;
                    subProcessArrayNode.add(childShape);
                }
            }
            workflowProcessNode = workflowProcessNode.deepCopy();
            workflowProcessNode.addAll(subProcessArrayNode);
        }
        String submitNodeCode = this.getSubmitNodeCode(workflowProcessNode);
        Integer rejectType = workflowDTO.getRejectType();
        if (rejectType == null) {
            rejectType = this.getRejectType(workflowProcessNode, nodeCode);
        }
        if (NodeRejectType.REJECT_TERMINATE.getValue() == rejectType.intValue()) {
            return false;
        }
        if (NodeRejectType.REJECT_TO_SUBMITUSER.getValue() == rejectType.intValue()) {
            return true;
        }
        if (NodeRejectType.REJECT_TO_PREVIOUS.getValue() == rejectType.intValue()) {
            String prevNodeCode = this.getPrevNodeCode(nodeCode, processNodeDO);
            return submitNodeCode.equals(prevNodeCode);
        }
        if (NodeRejectType.REJECT_TO_DESIGNATE.getValue() == rejectType.intValue()) {
            String rejectNode = workflowDTO.getRejectNode();
            return submitNodeCode.equals(rejectNode);
        }
        return false;
    }

    private String getSubmitNodeCode(ArrayNode workflowProcessNode) {
        for (JsonNode jsonNode : workflowProcessNode) {
            String nodeType = jsonNode.get("stencil").get("id").asText();
            if (!"StartNoneEvent".equals(nodeType)) continue;
            ArrayNode outgoings = (ArrayNode)jsonNode.get("outgoing");
            String outgoingId = outgoings.get(0).get("resourceId").asText();
            for (JsonNode node : workflowProcessNode) {
                if (!outgoingId.equals(node.get("resourceId").asText())) continue;
                return node.get("target").get("resourceId").asText();
            }
        }
        throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.notfoundsubmitnode"));
    }

    private Integer getRejectType(ArrayNode workflowProcessNode, String nodeCode) {
        for (JsonNode node : workflowProcessNode) {
            String resourceId = node.get("resourceId").asText();
            if (!nodeCode.equals(resourceId)) continue;
            JsonNode rejectTypeNode = node.get("properties").get("prioritydefinition");
            return rejectTypeNode == null || "".equals(rejectTypeNode.asText()) ? NodeRejectType.REJECT_TO_SUBMITUSER.getValue() : rejectTypeNode.asInt();
        }
        return NodeRejectType.REJECT_TO_SUBMITUSER.getValue();
    }

    private String getPrevNodeCode(String nodeCode, ProcessNodeDO currNodeDO) {
        boolean subProcessNodeFlag;
        ProcessNodeDO prevNode = new ProcessNodeDO();
        ProcessNodeDTO processNodeDTO = new ProcessNodeDTO();
        processNodeDTO.setProcessid(currNodeDO.getProcessid());
        processNodeDTO.setOrder("DESC");
        List allProcessNodeList = this.getWorkflowProcessNodeService().listProcessNode(processNodeDTO);
        List<ProcessNodeDO> completeProcessNodeDOs = allProcessNodeList.stream().filter(processNodeDO -> processNodeDO.getCompleteresult() != null && processNodeDO.getRejectstatus() == null).collect(Collectors.toList());
        boolean pgwNodeFlag = StringUtils.hasText(currNodeDO.getPgwnodeid());
        if (pgwNodeFlag) {
            List<ProcessNodeDO> branchNodeDOs = completeProcessNodeDOs.stream().filter(processNodeDO -> currNodeDO.getPgwbranch().equals(processNodeDO.getPgwbranch()) && currNodeDO.getPgwnodeid().equals(processNodeDO.getPgwnodeid()) && !processNodeDO.getNodeid().equals(currNodeDO.getPgwnodeid())).collect(Collectors.toList());
            Set nodeCodeSets = branchNodeDOs.stream().map(ProcessNodeDO::getNodecode).collect(Collectors.toSet());
            ProcessNodeDO pgwNode = this.getPgwNode(currNodeDO.getPgwnodeid(), allProcessNodeList);
            if (nodeCodeSets.isEmpty()) {
                boolean endFlag = false;
                for (ProcessNodeDO processNodeDO2 : allProcessNodeList) {
                    if (new BigDecimal(1).equals(processNodeDO2.getRejectstatus()) || new BigDecimal(1).equals(processNodeDO2.getHiddenflag())) continue;
                    if (endFlag && !pgwNode.getNodeid().equals(processNodeDO2.getNodeid())) {
                        prevNode = processNodeDO2;
                        break;
                    }
                    if (!pgwNode.getNodeid().equals(processNodeDO2.getNodeid())) continue;
                    endFlag = true;
                }
            } else {
                prevNode = this.getPrevProcessNodeDO(branchNodeDOs, nodeCode);
            }
        }
        if (subProcessNodeFlag = StringUtils.hasText(currNodeDO.getSubprocessnodeid())) {
            List<ProcessNodeDO> branchNodeDOs = completeProcessNodeDOs.stream().filter(processNodeDO -> currNodeDO.getSubprocessbranch().equals(processNodeDO.getSubprocessbranch()) && currNodeDO.getSubprocessnodeid().equals(processNodeDO.getSubprocessnodeid()) && !processNodeDO.getNodeid().equals(currNodeDO.getSubprocessnodeid())).collect(Collectors.toList());
            Set nodeCodeSets = branchNodeDOs.stream().map(ProcessNodeDO::getNodecode).collect(Collectors.toSet());
            ProcessNodeDO subProcessNode = this.getSubProcessNode(currNodeDO.getSubprocessnodeid(), allProcessNodeList);
            if (nodeCodeSets.isEmpty() || nodeCodeSets.size() == 1 && nodeCodeSets.contains(currNodeDO.getNodecode())) {
                boolean endFlag = false;
                for (ProcessNodeDO processNodeDO3 : allProcessNodeList) {
                    if (new BigDecimal(1).equals(processNodeDO3.getRejectstatus()) || new BigDecimal(1).equals(processNodeDO3.getHiddenflag())) continue;
                    if (endFlag && !subProcessNode.getNodeid().equals(processNodeDO3.getNodeid())) {
                        prevNode = processNodeDO3;
                        break;
                    }
                    if (!subProcessNode.getNodeid().equals(processNodeDO3.getNodeid())) continue;
                    endFlag = true;
                }
            } else {
                prevNode = this.getPrevProcessNodeDO(branchNodeDOs, nodeCode);
            }
        }
        if (!pgwNodeFlag && !subProcessNodeFlag) {
            prevNode = this.getPrevProcessNodeDO(completeProcessNodeDOs, nodeCode);
        }
        return prevNode.getNodecode();
    }

    private ProcessNodeDO getPgwNode(String pgwNodeId, List<ProcessNodeDO> processNodeDOs) {
        ProcessNodeDO node = processNodeDOs.stream().filter(processNodeDO -> processNodeDO.getNodeid().equals(pgwNodeId)).findFirst().get();
        if (node.getNodeid().equals(node.getPgwnodeid())) {
            return node;
        }
        return this.getPgwNode(node.getPgwnodeid(), processNodeDOs);
    }

    private ProcessNodeDO getSubProcessNode(String subProcessNodeId, List<ProcessNodeDO> processNodeDOs) {
        ProcessNodeDO node = processNodeDOs.stream().filter(processNodeDO -> processNodeDO.getNodeid().equals(subProcessNodeId)).findFirst().get();
        if (node.getNodeid().equals(node.getSubprocessnodeid())) {
            return node;
        }
        return this.getSubProcessNode(node.getSubprocessnodeid(), processNodeDOs);
    }

    private ProcessNodeDO getPrevProcessNodeDO(List<ProcessNodeDO> completeProcessNodeDO, String currTaskDefineKey) {
        boolean temp = true;
        ProcessNodeDO prevNode = null;
        for (ProcessNodeDO processNodeDO : completeProcessNodeDO) {
            if (new BigDecimal(1).equals(processNodeDO.getHiddenflag())) continue;
            if (currTaskDefineKey.equals(processNodeDO.getNodecode())) {
                temp = true;
                continue;
            }
            if (temp && !"\u5ba1\u6279\u9a73\u56de".equals(processNodeDO.getCompleteresult())) {
                prevNode = processNodeDO;
                break;
            }
            temp = false;
        }
        return prevNode;
    }

    public void restoreRejectNodeInfo(ProcessRejectNodeDO processRejectNodeDO) {
        this.workflowProcessRejectNodeDao.restore(processRejectNodeDO);
    }
}

