/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.jiuqi.va.domain.workflow.ProcessDO
 *  com.jiuqi.va.domain.workflow.ProcessDTO
 *  com.jiuqi.va.domain.workflow.ProcessHistoryDO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDTO
 *  com.jiuqi.va.domain.workflow.ProcessRejectNodeDO
 *  com.jiuqi.va.domain.workflow.exception.WorkflowException
 *  com.jiuqi.va.domain.workflow.forward.process.ForwardProcessDTO
 *  com.jiuqi.va.domain.workflow.forward.process.ForwardProcessNode
 *  com.jiuqi.va.domain.workflow.service.VaWorkflowProcessService
 *  com.jiuqi.va.domain.workflow.service.WorkflowForwardProcessService
 *  com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeService
 *  com.jiuqi.va.domain.workflow.service.WorkflowProcessRejectNodeService
 */
package com.jiuqi.va.workflow.service.impl.forward.process;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jiuqi.va.domain.workflow.ProcessDO;
import com.jiuqi.va.domain.workflow.ProcessDTO;
import com.jiuqi.va.domain.workflow.ProcessHistoryDO;
import com.jiuqi.va.domain.workflow.ProcessNodeDO;
import com.jiuqi.va.domain.workflow.ProcessNodeDTO;
import com.jiuqi.va.domain.workflow.ProcessRejectNodeDO;
import com.jiuqi.va.domain.workflow.exception.WorkflowException;
import com.jiuqi.va.domain.workflow.forward.process.ForwardProcessDTO;
import com.jiuqi.va.domain.workflow.forward.process.ForwardProcessNode;
import com.jiuqi.va.domain.workflow.service.VaWorkflowProcessService;
import com.jiuqi.va.domain.workflow.service.WorkflowForwardProcessService;
import com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeService;
import com.jiuqi.va.domain.workflow.service.WorkflowProcessRejectNodeService;
import com.jiuqi.va.workflow.model.WorkflowModel;
import com.jiuqi.va.workflow.service.impl.help.WorkflowParamService;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import com.jiuqi.va.workflow.utils.VaWorkflowUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class WorkflowForwardProcessServiceImpl
implements WorkflowForwardProcessService {
    @Autowired
    private WorkflowProcessNodeService workflowProcessNodeService;
    @Autowired
    private WorkflowParamService workflowParamService;
    @Autowired
    private VaWorkflowProcessService vaWorkflowProcessService;
    @Autowired
    private WorkflowProcessRejectNodeService workflowProcessRejectNodeService;

    public List<ForwardProcessNode> getForwardProcess(ForwardProcessDTO forwardProcessDTO) {
        List<ForwardProcessNode> preparedForwardNodeList = this.prepareForwardNodeList(forwardProcessDTO);
        Map<String, String> rejectSkipNodeMap = this.getRejectSkipNodeMap(forwardProcessDTO);
        return this.calculate(preparedForwardNodeList, new ArrayList<String>(), rejectSkipNodeMap, new HashMap<String, List<ForwardProcessNode>>(), null);
    }

    private Map<String, String> getRejectSkipNodeMap(ForwardProcessDTO forwardProcessDTO) {
        String bizCode = forwardProcessDTO.getBizCode();
        if (!StringUtils.hasText(bizCode)) {
            throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        ProcessRejectNodeDO processRejectNodeDO = new ProcessRejectNodeDO();
        processRejectNodeDO.setBizcode(bizCode);
        List rejectNodeDOS = this.workflowProcessRejectNodeService.listRejectNodeInfoWithLogicalDeleted(processRejectNodeDO);
        HashMap<String, String> resultMap = new HashMap<String, String>(rejectNodeDOS.size());
        for (ProcessRejectNodeDO rejectNodeDO : rejectNodeDOS) {
            String nodeId = rejectNodeDO.getNodeid();
            if (!StringUtils.hasText(nodeId)) continue;
            resultMap.put(nodeId, rejectNodeDO.getBerejectednodecode());
        }
        return resultMap;
    }

    private List<ForwardProcessNode> prepareForwardNodeList(ForwardProcessDTO forwardProcessDTO) {
        ArrayList<ForwardProcessNode> nodes = new ArrayList<ForwardProcessNode>();
        nodes.add(new ForwardProcessNode());
        List<ProcessNodeDO> processNodeDOList = this.getProcessNodeDOList(forwardProcessDTO);
        ArrayNode defineData = this.getArrayNode(forwardProcessDTO);
        HashMap<String, Map<String, List<String>>> pgwBranchInfoMap = new HashMap<String, Map<String, List<String>>>();
        for (ProcessNodeDO processNodeDO : processNodeDOList) {
            if (this.shouldSkip(processNodeDO, forwardProcessDTO)) continue;
            String pgwNodeId = processNodeDO.getPgwnodeid();
            String subProcessNodeId = processNodeDO.getSubprocessnodeid();
            if (StringUtils.hasText(pgwNodeId)) {
                this.collectPgwNode(processNodeDO, defineData, pgwBranchInfoMap, nodes);
                continue;
            }
            if (StringUtils.hasText(subProcessNodeId)) {
                this.collectSubProcessNode(processNodeDO, nodes);
                continue;
            }
            this.collectNode(processNodeDO, nodes);
        }
        nodes.remove(0);
        return nodes;
    }

    private List<ProcessNodeDO> getProcessNodeDOList(ForwardProcessDTO forwardProcessDTO) {
        List processNodeDOList = forwardProcessDTO.getProcessNodeDOList();
        if (!CollectionUtils.isEmpty(processNodeDOList)) {
            return processNodeDOList;
        }
        String bizCode = forwardProcessDTO.getBizCode();
        if (!StringUtils.hasText(bizCode)) {
            throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        ProcessNodeDTO processNodeDTO = new ProcessNodeDTO();
        processNodeDTO.setBizcode(bizCode);
        processNodeDTO.setSearchIgnore(true);
        if (forwardProcessDTO.isFilterExtendNode()) {
            processNodeDTO.setSyscode("WORKFLOW");
        }
        return this.workflowProcessNodeService.listProcessNode(processNodeDTO);
    }

    private ArrayNode getArrayNode(ForwardProcessDTO forwardProcessDTO) {
        ArrayNode defineData = forwardProcessDTO.getWorkflowDefineData();
        if (defineData != null) {
            return defineData;
        }
        String bizCode = forwardProcessDTO.getBizCode();
        if (!StringUtils.hasText(bizCode)) {
            throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        ProcessDTO processDTO = new ProcessDTO();
        processDTO.setBizcode(bizCode);
        ProcessDO processDO = this.vaWorkflowProcessService.get(processDTO);
        if (processDO == null) {
            List processHistoryDOS = this.vaWorkflowProcessService.listHistory(processDTO);
            if (CollectionUtils.isEmpty(processHistoryDOS)) {
                throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.norecorder"));
            }
            ProcessHistoryDO processHistoryDO = (ProcessHistoryDO)processHistoryDOS.get(processHistoryDOS.size() - 1);
            processDO = new ProcessDO();
            processDO.setDefinekey(processHistoryDO.getDefinekey());
            processDO.setDefineversion(processHistoryDO.getDefineversion());
        }
        WorkflowModel workflowModel = this.workflowParamService.getModel(processDO.getDefinekey(), processDO.getDefineversion().longValue());
        return this.workflowParamService.getWorkflowProcessNode(workflowModel);
    }

    private boolean shouldSkip(ProcessNodeDO processNodeDO, ForwardProcessDTO forwardProcessDTO) {
        boolean isHiddenNode = BigDecimal.ONE.equals(processNodeDO.getHiddenflag());
        boolean calculatePendingNode = forwardProcessDTO.isCalculatePendingNode();
        boolean isPendingNode = processNodeDO.getCompletetime() == null && (processNodeDO.getIgnoreflag() == null || BigDecimal.ZERO.equals(processNodeDO.getIgnoreflag())) && !processNodeDO.getNodeid().equals(processNodeDO.getPgwnodeid()) && !processNodeDO.getNodeid().equals(processNodeDO.getSubprocessnodeid());
        boolean differentSubprocessBranch = StringUtils.hasText(forwardProcessDTO.getSubProcessBranch()) && StringUtils.hasText(processNodeDO.getSubprocessbranch()) && !forwardProcessDTO.getSubProcessBranch().equals(processNodeDO.getSubprocessbranch()) && !processNodeDO.getNodeid().equals(processNodeDO.getSubprocessnodeid());
        return isHiddenNode || !calculatePendingNode && isPendingNode || differentSubprocessBranch;
    }

    private void collectPgwNode(ProcessNodeDO processNodeDO, ArrayNode arrayNode, Map<String, Map<String, List<String>>> pgwBranchInfoMap, List<ForwardProcessNode> forwardProcessNodeList) {
        ForwardProcessNode forwardProcessNode = new ForwardProcessNode();
        String nodeCode = processNodeDO.getNodecode();
        String nodeId = processNodeDO.getNodeid();
        String pgwNodeId = processNodeDO.getPgwnodeid();
        forwardProcessNode.setNodeCode(nodeCode);
        forwardProcessNode.setNodeId(nodeId);
        forwardProcessNode.setPgwNodeId(pgwNodeId);
        forwardProcessNode.setNodeName(processNodeDO.getProcessnodename());
        if (pgwNodeId.equals(nodeId)) {
            if (!pgwBranchInfoMap.containsKey(nodeCode)) {
                this.collectPgwBranchInfoMap(arrayNode, pgwBranchInfoMap, nodeCode);
            }
            HashMap pgwNodeMap = new HashMap();
            forwardProcessNode.setPgwNodeMap(pgwNodeMap);
            forwardProcessNodeList.add(forwardProcessNode);
        } else {
            Map<String, List<String>> pgwBranchInfo;
            ForwardProcessNode prevNode = forwardProcessNodeList.get(forwardProcessNodeList.size() - 1);
            Map pgwNodeMap = prevNode.getPgwNodeMap();
            List branchList = pgwNodeMap.computeIfAbsent(this.getPgwBranchId(pgwBranchInfo = pgwBranchInfoMap.get(prevNode.getNodeCode()), nodeCode), k -> new ArrayList());
            if (!branchList.isEmpty() && this.isOldNode(processNodeDO, (ForwardProcessNode)branchList.get(branchList.size() - 1))) {
                forwardProcessNode = (ForwardProcessNode)branchList.get(branchList.size() - 1);
                forwardProcessNode.getProcessNodeDOList().add(processNodeDO);
            } else {
                ArrayList<ProcessNodeDO> processNodeDOS = new ArrayList<ProcessNodeDO>();
                processNodeDOS.add(processNodeDO);
                forwardProcessNode.setProcessNodeDOList(processNodeDOS);
                branchList.add(forwardProcessNode);
            }
            this.updateNodeStatus(forwardProcessNode);
            if (!prevNode.isPendingNode()) {
                prevNode.setPendingNode(forwardProcessNode.isPendingNode());
            }
        }
    }

    private String getPgwBranchId(Map<String, List<String>> pgwBranchInfo, String nodeCode) {
        for (Map.Entry<String, List<String>> entry : pgwBranchInfo.entrySet()) {
            if (!entry.getValue().contains(nodeCode)) continue;
            return entry.getKey();
        }
        return null;
    }

    private void collectPgwBranchInfoMap(ArrayNode arrayNode, Map<String, Map<String, List<String>>> pgwBranchInfoMap, String nodeCode) {
        HashMap<String, ArrayList<String>> pgwBranchInfo = new HashMap<String, ArrayList<String>>();
        for (JsonNode jsonNode : arrayNode) {
            JsonNode resourceId = jsonNode.get("resourceId");
            if (!resourceId.asText().equals(nodeCode)) continue;
            JsonNode outgoings = jsonNode.get("outgoing");
            for (JsonNode outgoing : outgoings) {
                String branchId = outgoing.get("resourceId").asText();
                ArrayList<String> branchNodeCodeList = new ArrayList<String>();
                this.collectPgwBranchNodeCodeList(arrayNode, branchId, branchNodeCodeList);
                pgwBranchInfo.put(branchId, branchNodeCodeList);
            }
        }
        pgwBranchInfoMap.put(nodeCode, pgwBranchInfo);
    }

    private void collectPgwBranchNodeCodeList(ArrayNode arrayNode, String resourceId, List<String> result) {
        for (JsonNode jsonNode : arrayNode) {
            if (!jsonNode.get("resourceId").asText().equals(resourceId)) continue;
            String stencilId = jsonNode.get("stencil").get("id").asText();
            if ("JoinParallelGateway".equals(stencilId)) {
                return;
            }
            if ("UserTask".equals(stencilId)) {
                result.add(resourceId);
            }
            JsonNode outgoings = jsonNode.get("outgoing");
            for (JsonNode outgoing : outgoings) {
                this.collectPgwBranchNodeCodeList(arrayNode, outgoing.get("resourceId").asText(), result);
            }
        }
    }

    private void collectSubProcessNode(ProcessNodeDO processNodeDO, List<ForwardProcessNode> forwardProcessNodeList) {
        ForwardProcessNode forwardProcessNode = new ForwardProcessNode();
        forwardProcessNode.setNodeCode(processNodeDO.getNodecode());
        forwardProcessNode.setNodeId(processNodeDO.getNodeid());
        forwardProcessNode.setNodeName(processNodeDO.getProcessnodename());
        forwardProcessNode.setSubProcessBranch(processNodeDO.getSubprocessbranch());
        forwardProcessNode.setSubProcessNodeId(processNodeDO.getSubprocessnodeid());
        if (processNodeDO.getSubprocessnodeid().equals(processNodeDO.getNodeid())) {
            HashMap subProcessNodeMap = new HashMap();
            forwardProcessNode.setSubProcessNodeMap(subProcessNodeMap);
            forwardProcessNodeList.add(forwardProcessNode);
        } else {
            ForwardProcessNode prevNode = forwardProcessNodeList.get(forwardProcessNodeList.size() - 1);
            Map subProcessNodeMap = prevNode.getSubProcessNodeMap();
            List branchList = subProcessNodeMap.computeIfAbsent(processNodeDO.getSubprocessbranch(), k -> new ArrayList());
            if (!branchList.isEmpty() && this.isOldNode(processNodeDO, (ForwardProcessNode)branchList.get(branchList.size() - 1))) {
                forwardProcessNode = (ForwardProcessNode)branchList.get(branchList.size() - 1);
                forwardProcessNode.getProcessNodeDOList().add(processNodeDO);
            } else {
                ArrayList<ProcessNodeDO> processNodeDOS = new ArrayList<ProcessNodeDO>();
                processNodeDOS.add(processNodeDO);
                forwardProcessNode.setProcessNodeDOList(processNodeDOS);
                branchList.add(forwardProcessNode);
            }
            this.updateNodeStatus(forwardProcessNode);
            boolean pending = prevNode.isPendingNode();
            if (!pending) {
                prevNode.setPendingNode(forwardProcessNode.isPendingNode());
            }
        }
    }

    private void collectNode(ProcessNodeDO processNodeDO, List<ForwardProcessNode> forwardProcessNodeList) {
        ForwardProcessNode prevNode = forwardProcessNodeList.get(forwardProcessNodeList.size() - 1);
        if (this.isOldNode(processNodeDO, prevNode)) {
            prevNode.getProcessNodeDOList().add(processNodeDO);
            this.updateNodeStatus(prevNode);
        } else {
            ForwardProcessNode forwardProcessNode = new ForwardProcessNode();
            forwardProcessNode.setNodeCode(processNodeDO.getNodecode());
            forwardProcessNode.setNodeId(processNodeDO.getNodeid());
            forwardProcessNode.setNodeName(processNodeDO.getProcessnodename());
            ArrayList<ProcessNodeDO> processNodeDOS = new ArrayList<ProcessNodeDO>();
            processNodeDOS.add(processNodeDO);
            forwardProcessNode.setProcessNodeDOList(processNodeDOS);
            forwardProcessNodeList.add(forwardProcessNode);
            this.updateNodeStatus(forwardProcessNode);
        }
    }

    private void updateNodeStatus(ForwardProcessNode forwardProcessNode) {
        List processNodeDOList = forwardProcessNode.getProcessNodeDOList();
        boolean isIgnoredNode = true;
        for (ProcessNodeDO nodeDO : processNodeDOList) {
            BigDecimal ignoreFlag = nodeDO.getIgnoreflag();
            if (ignoreFlag == null || BigDecimal.ZERO.equals(ignoreFlag)) {
                isIgnoredNode = false;
            }
            if ("\u5ba1\u6279\u9a73\u56de".equals(nodeDO.getCompleteresult())) {
                forwardProcessNode.setRejectNode(true);
                forwardProcessNode.setNodeId(nodeDO.getNodeid());
            }
            if (nodeDO.getCompletetime() != null || isIgnoredNode) continue;
            forwardProcessNode.setPendingNode(true);
        }
        forwardProcessNode.setIgnoredNode(isIgnoredNode);
    }

    private boolean isOldNode(ProcessNodeDO curNode, ForwardProcessNode preNode) {
        int counterSignFlag = curNode.getCountersignflag().intValue();
        String curNodeCode = curNode.getNodecode();
        String curNodeId = preNode.getNodeId();
        String preNodeCode = preNode.getNodeCode();
        String preNodeId = preNode.getNodeId();
        String preNodeSubProcessBranch = preNode.getSubProcessBranch();
        String curNodeSubprocessBranch = curNode.getSubprocessbranch();
        if (counterSignFlag == 0) {
            return curNodeCode.equals(preNodeCode) && curNodeId.equals(preNodeId) && Objects.equals(preNodeSubProcessBranch, curNodeSubprocessBranch);
        }
        return curNodeCode.equals(preNodeCode) && Objects.equals(preNodeSubProcessBranch, curNodeSubprocessBranch);
    }

    private List<ForwardProcessNode> calculate(List<ForwardProcessNode> forwardProcessNodeList, List<String> oldPgwNodeIds, Map<String, String> rejectSkipInfoMap, Map<String, List<ForwardProcessNode>> rejectSkipNodeCache, ForwardProcessNode parent) {
        ArrayList<ForwardProcessNode> result = new ArrayList<ForwardProcessNode>();
        HashMap<String, Integer> nodeIndexCache = new HashMap<String, Integer>();
        Map<String, List<String>> oldPgwNodeIdMap = new HashMap<String, List<String>>();
        for (ForwardProcessNode node : forwardProcessNodeList) {
            String nodeCode = node.getNodeCode();
            String nodeId = node.getNodeId();
            if (nodeIndexCache.containsKey(nodeCode) && this.isPgwNode(node)) {
                ForwardProcessNode oldNode = (ForwardProcessNode)result.get((Integer)nodeIndexCache.get(node.getNodeCode()));
                oldPgwNodeIdMap = this.mergePgwNodes(oldNode, node);
            }
            this.processPgwOrSubProcessBranch(rejectSkipInfoMap, rejectSkipNodeCache, node, result, oldPgwNodeIdMap);
            if (rejectSkipInfoMap.containsKey(nodeId) && !oldPgwNodeIds.contains(nodeId)) {
                this.recordSkipNode(node, rejectSkipInfoMap, rejectSkipNodeCache, nodeIndexCache, result, parent);
            }
            if (nodeIndexCache.containsKey(nodeCode)) {
                Integer nodeIndex = (Integer)nodeIndexCache.get(node.getNodeCode());
                List<ForwardProcessNode> removeNodeList = this.getRemoveNodeListAndClearCache(nodeIndex, result, nodeIndexCache);
                result.removeAll(removeNodeList);
            }
            result.add(node);
            nodeIndexCache.put(nodeCode, result.size() - 1);
            if (!this.needAddSkipNode(oldPgwNodeIds, rejectSkipNodeCache, node)) continue;
            this.addSkipNode(rejectSkipNodeCache.remove(nodeCode), result, nodeIndexCache);
        }
        return result;
    }

    private void processPgwOrSubProcessBranch(Map<String, String> rejectSkipInfoMap, Map<String, List<ForwardProcessNode>> rejectSkipNodeCache, ForwardProcessNode node, List<ForwardProcessNode> result, Map<String, List<String>> oldPgwNodeIdMap) {
        Map subProcessNodeMap;
        Map pgwNodeMap = node.getPgwNodeMap();
        if (!CollectionUtils.isEmpty(pgwNodeMap)) {
            this.processPgw(rejectSkipInfoMap, rejectSkipNodeCache, node, oldPgwNodeIdMap, pgwNodeMap);
        }
        if (!CollectionUtils.isEmpty(subProcessNodeMap = node.getSubProcessNodeMap())) {
            this.processSubProcessBranch(rejectSkipInfoMap, rejectSkipNodeCache, node, result, subProcessNodeMap);
        }
    }

    private void processPgw(Map<String, String> rejectSkipInfoMap, Map<String, List<ForwardProcessNode>> rejectSkipNodeCache, ForwardProcessNode node, Map<String, List<String>> oldPgwNodeIdMap, Map<String, List<ForwardProcessNode>> pgwNodeMap) {
        for (Map.Entry<String, List<ForwardProcessNode>> branch : pgwNodeMap.entrySet()) {
            List<ForwardProcessNode> branchNodeList = branch.getValue();
            List<String> oldPgwNodeIds = VaWorkflowUtils.getList(oldPgwNodeIdMap.get(branch.getKey()));
            branch.setValue(this.calculate(branchNodeList, oldPgwNodeIds, rejectSkipInfoMap, rejectSkipNodeCache, node));
        }
    }

    private void processSubProcessBranch(Map<String, String> rejectSkipInfoMap, Map<String, List<ForwardProcessNode>> rejectSkipNodeCache, ForwardProcessNode node, List<ForwardProcessNode> result, Map<String, List<ForwardProcessNode>> subProcessNodeMap) {
        ArrayList<String> removeBranchKeyList = new ArrayList<String>();
        ArrayList<ForwardProcessNode> mainProcessNodeList = new ArrayList<ForwardProcessNode>(result);
        Map<String, Integer> nodeCodeIndexMap = this.getNodeCodeIndexMap(mainProcessNodeList);
        for (Map.Entry<String, List<ForwardProcessNode>> branch : subProcessNodeMap.entrySet()) {
            mainProcessNodeList.addAll((Collection<ForwardProcessNode>)branch.getValue());
            List<ForwardProcessNode> branchForwardProcess = this.calculate(mainProcessNodeList, new ArrayList<String>(), rejectSkipInfoMap, rejectSkipNodeCache, node);
            this.replaceNode(result, branchForwardProcess, nodeCodeIndexMap);
            if (branchForwardProcess.isEmpty()) {
                removeBranchKeyList.add(branch.getKey());
                continue;
            }
            branch.setValue(branchForwardProcess);
        }
        for (String branchKey : removeBranchKeyList) {
            subProcessNodeMap.remove(branchKey);
        }
    }

    private Map<String, Integer> getNodeCodeIndexMap(List<ForwardProcessNode> mainProcessNodeList) {
        HashMap<String, Integer> nodeCodeIndexMap = new HashMap<String, Integer>();
        for (int i = 0; i < mainProcessNodeList.size(); ++i) {
            nodeCodeIndexMap.put(mainProcessNodeList.get(i).getNodeCode(), i);
        }
        return nodeCodeIndexMap;
    }

    private void replaceNode(List<ForwardProcessNode> result, List<ForwardProcessNode> branchForwardProcess, Map<String, Integer> nodeCodeIndexMap) {
        Iterator<ForwardProcessNode> iterator = branchForwardProcess.iterator();
        while (iterator.hasNext()) {
            ForwardProcessNode next = iterator.next();
            String nodeCode = next.getNodeCode();
            if (!nodeCodeIndexMap.containsKey(nodeCode)) continue;
            result.set(nodeCodeIndexMap.get(nodeCode), next);
            iterator.remove();
        }
    }

    private boolean needAddSkipNode(List<String> oldPgwNodeIds, Map<String, List<ForwardProcessNode>> rejectSkipNodeCache, ForwardProcessNode node) {
        String nodeCode = node.getNodeCode();
        String nodeId = node.getNodeId();
        boolean isAgreeNode = !node.isRejectNode() && !node.isIgnoredNode() && !node.isPendingNode();
        boolean notOldPgwNode = !oldPgwNodeIds.contains(nodeId);
        boolean hasRejectSkipNode = rejectSkipNodeCache.containsKey(nodeCode);
        boolean notPgwNodeOrPgwAgree = !this.isPgwNode(node) || node.getPgwNodeMap().values().stream().noneMatch(list -> ((ForwardProcessNode)list.get(list.size() - 1)).isRejectNode());
        return isAgreeNode && hasRejectSkipNode && notOldPgwNode && notPgwNodeOrPgwAgree;
    }

    private Map<String, List<String>> mergePgwNodes(ForwardProcessNode oldNode, ForwardProcessNode newNode) {
        Map oldPgwNodeMap = oldNode.getPgwNodeMap();
        Map newPgwNodeMap = newNode.getPgwNodeMap();
        HashMap<String, List<String>> oldPgwNodeIdMap = new HashMap<String, List<String>>(oldPgwNodeMap.size());
        for (Map.Entry entry : newPgwNodeMap.entrySet()) {
            String branchId = (String)entry.getKey();
            List oldPgwNodeList = oldPgwNodeMap.computeIfAbsent(branchId, k -> new ArrayList());
            oldPgwNodeIdMap.put(branchId, oldPgwNodeList.stream().map(ForwardProcessNode::getNodeId).collect(Collectors.toList()));
            oldPgwNodeList.addAll((Collection)entry.getValue());
            oldPgwNodeMap.put(branchId, oldPgwNodeList);
        }
        newNode.setPgwNodeMap(oldPgwNodeMap);
        return oldPgwNodeIdMap;
    }

    private void recordSkipNode(ForwardProcessNode currentNode, Map<String, String> rejectSkipInfoMap, Map<String, List<ForwardProcessNode>> rejectSkipNodeCache, Map<String, Integer> nodeIndexCache, List<ForwardProcessNode> result, ForwardProcessNode parent) {
        String nodeId = currentNode.getNodeId();
        String beRejectedNodeCode = rejectSkipInfoMap.get(nodeId);
        if (nodeIndexCache.containsKey(beRejectedNodeCode)) {
            int nodeIndex = nodeIndexCache.get(beRejectedNodeCode);
            List<ForwardProcessNode> skipNodeList = result.subList(nodeIndex + 1, result.size());
            ArrayList<ForwardProcessNode> nodes = new ArrayList<ForwardProcessNode>(skipNodeList);
            if (this.isPgwNode(currentNode) && !nodeIndexCache.containsKey(currentNode.getNodeCode())) {
                nodes.add(currentNode);
            }
            rejectSkipNodeCache.put(beRejectedNodeCode, nodes);
        } else if (parent == null) {
            this.handleMainRejectSkipToPgw(rejectSkipNodeCache, result, beRejectedNodeCode);
        } else {
            rejectSkipInfoMap.put(parent.getNodeId(), beRejectedNodeCode);
        }
    }

    private void handleMainRejectSkipToPgw(Map<String, List<ForwardProcessNode>> rejectSkipNodeCache, List<ForwardProcessNode> result, String beRejectedNodeCode) {
        ArrayList<ForwardProcessNode> mainProcessNodeList = new ArrayList<ForwardProcessNode>();
        ArrayList<ForwardProcessNode> branchNodeList = new ArrayList<ForwardProcessNode>();
        String pgwNodeCode = null;
        for (ForwardProcessNode node : result) {
            if (StringUtils.hasText(pgwNodeCode)) {
                mainProcessNodeList.add(node);
                continue;
            }
            boolean hasFoundPgwNode = this.collectBranchNodeList(node, beRejectedNodeCode, branchNodeList);
            if (!hasFoundPgwNode) continue;
            pgwNodeCode = node.getNodeCode();
        }
        rejectSkipNodeCache.put(beRejectedNodeCode, branchNodeList);
        rejectSkipNodeCache.put(pgwNodeCode, mainProcessNodeList);
    }

    private boolean collectBranchNodeList(ForwardProcessNode node, String beRejectedNodeCode, List<ForwardProcessNode> branchNodeList) {
        if (!this.isPgwNode(node)) {
            return false;
        }
        boolean hasFoundBeRejectedNode = false;
        for (List branchNodes : node.getPgwNodeMap().values()) {
            for (ForwardProcessNode branchNode : branchNodes) {
                if (hasFoundBeRejectedNode) {
                    branchNodeList.add(branchNode);
                    continue;
                }
                if (!branchNode.getNodeCode().equals(beRejectedNodeCode)) continue;
                hasFoundBeRejectedNode = true;
            }
            if (!hasFoundBeRejectedNode) continue;
            return true;
        }
        return false;
    }

    private boolean isPgwNode(ForwardProcessNode node) {
        return node != null && node.getNodeId().equals(node.getPgwNodeId());
    }

    private List<ForwardProcessNode> getRemoveNodeListAndClearCache(Integer nodeIndex, List<ForwardProcessNode> result, Map<String, Integer> nodeIndexCache) {
        ArrayList<ForwardProcessNode> removeIndexList = new ArrayList<ForwardProcessNode>();
        for (int i = nodeIndex.intValue(); i < result.size(); ++i) {
            removeIndexList.add(result.get(i));
            nodeIndexCache.remove(result.get(i).getNodeCode());
        }
        return removeIndexList;
    }

    private void addSkipNode(List<ForwardProcessNode> skipNodeList, List<ForwardProcessNode> result, Map<String, Integer> nodeIndexCache) {
        for (ForwardProcessNode processNode : skipNodeList) {
            result.add(processNode);
            nodeIndexCache.put(processNode.getNodeCode(), result.size() - 1);
        }
    }
}

