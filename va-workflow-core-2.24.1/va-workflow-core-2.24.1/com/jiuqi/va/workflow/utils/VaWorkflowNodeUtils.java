/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.fasterxml.jackson.databind.node.TextNode
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.workflow.ProcessNodeDO
 *  com.jiuqi.va.domain.workflow.VaContext
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 *  com.jiuqi.va.domain.workflow.service.WorkflowFormulaSevice
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.workflow.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.workflow.ProcessNodeDO;
import com.jiuqi.va.domain.workflow.VaContext;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import com.jiuqi.va.domain.workflow.service.WorkflowFormulaSevice;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.workflow.utils.SequenceConditionUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.springframework.util.ObjectUtils;

public final class VaWorkflowNodeUtils {
    private VaWorkflowNodeUtils() {
    }

    private static WorkflowFormulaSevice getWorkflowFormulaSevice() {
        return (WorkflowFormulaSevice)ApplicationContextRegister.getBean(WorkflowFormulaSevice.class);
    }

    public static void getNodes(ArrayNode outGoings, ArrayNode arrayNode, ArrayNode sequenceFlowArrayNode, List<Map<String, String>> nodes) {
        block0: for (JsonNode outgoing : outGoings) {
            for (JsonNode jsonNode : arrayNode) {
                String nodeType;
                String nodeId = jsonNode.get("resourceId").asText();
                if (!outgoing.get("resourceId").asText().equals(nodeId)) continue;
                String id = jsonNode.get("stencil").get("id").asText();
                String string = nodeType = jsonNode.get("stencil").get("nodeType") == null ? "" : jsonNode.get("stencil").get("nodeType").asText();
                if ("UserTask".equals(id) || "ParallelGateway".equals(id) || "Manual".equals(nodeType)) {
                    String nodeName = jsonNode.get("properties").get("name").asText();
                    HashMap<String, String> node = new HashMap<String, String>();
                    node.put("nodeId", nodeId);
                    node.put("nodeName", nodeName);
                    for (Map<String, String> nodeMap : nodes) {
                        if (!nodeId.equals(nodeMap.get("nodeId"))) continue;
                        continue block0;
                    }
                    nodes.add(node);
                }
                if ("SubProcess".equals(id)) {
                    ArrayNode childShapes = (ArrayNode)jsonNode.get("childShapes");
                    ArrayNode copyChildShapes = childShapes.deepCopy();
                    copyChildShapes.addAll(sequenceFlowArrayNode);
                    HashSet<String> resourceIdSet = new HashSet<String>();
                    for (JsonNode childShape : childShapes) {
                        String type = childShape.get("stencil").get("id").asText();
                        if (!"StartNoneEvent".equals(type)) continue;
                        ArrayNode childShapeOutGoings = (ArrayNode)childShape.get("outgoing");
                        VaWorkflowNodeUtils.getChildShapeNodes(childShapeOutGoings, copyChildShapes, nodes, resourceIdSet);
                        break;
                    }
                }
                VaWorkflowNodeUtils.getNodes((ArrayNode)jsonNode.get("outgoing"), arrayNode, sequenceFlowArrayNode, nodes);
            }
        }
    }

    private static void getChildShapeNodes(ArrayNode outGoings, ArrayNode arrayNode, List<Map<String, String>> nodes, Set<String> resourceIdSet) {
        for (JsonNode outgoing : outGoings) {
            for (JsonNode jsonNode : arrayNode) {
                String nodeType;
                String nodeId = jsonNode.get("resourceId").asText();
                if (!outgoing.get("resourceId").asText().equals(nodeId) || resourceIdSet.contains(nodeId)) continue;
                String id = jsonNode.get("stencil").get("id").asText();
                String string = nodeType = jsonNode.get("stencil").get("nodeType") == null ? "" : jsonNode.get("stencil").get("nodeType").asText();
                if ("UserTask".equals(id) || "ParallelGateway".equals(id) || "Manual".equals(nodeType)) {
                    String nodeName = jsonNode.get("properties").get("name").asText();
                    HashMap<String, String> node = new HashMap<String, String>();
                    node.put("nodeId", nodeId);
                    node.put("nodeName", nodeName);
                    nodes.add(node);
                }
                VaWorkflowNodeUtils.getChildShapeNodes((ArrayNode)jsonNode.get("outgoing"), arrayNode, nodes, resourceIdSet);
            }
        }
    }

    public static void getNodesInfo(ArrayNode outgoings, ArrayNode arrayNode, List<Map<String, Object>> nodes, ArrayNode sequenceFlow) {
        block0: for (JsonNode outgoing : outgoings) {
            for (JsonNode jsonNode : arrayNode) {
                String nodeType;
                String nodeId = jsonNode.get("resourceId").asText();
                if (!outgoing.get("resourceId").asText().equals(nodeId)) continue;
                String id = jsonNode.get("stencil").get("id").asText();
                String string = nodeType = jsonNode.get("stencil").get("nodeType") == null ? "" : jsonNode.get("stencil").get("nodeType").asText();
                if ("UserTask".equals(id) || "ParallelGateway".equals(id) || "SubProcess".equals(id) || "Manual".equals(nodeType)) {
                    for (Map<String, Object> nodeMap : nodes) {
                        if (!nodeId.equals(nodeMap.get("nodeId"))) continue;
                        continue block0;
                    }
                    Map node = JSONUtil.parseMap((String)jsonNode.get("properties").toString());
                    node.put("nodeId", nodeId);
                    node.put("nodeType", "Manual".equals(nodeType) ? nodeType : id);
                    nodes.add(node);
                }
                if ("SubProcess".equals(id)) {
                    ArrayNode childShapes = (ArrayNode)jsonNode.get("childShapes");
                    ArrayNode copyChildShapes = childShapes.deepCopy();
                    copyChildShapes.addAll(sequenceFlow);
                    for (JsonNode childShape : childShapes) {
                        String type = childShape.get("stencil").get("id").asText();
                        if (!"StartNoneEvent".equals(type)) continue;
                        ArrayNode childShapeOutGoings = (ArrayNode)childShape.get("outgoing");
                        VaWorkflowNodeUtils.getNodesInfo(childShapeOutGoings, copyChildShapes, nodes, sequenceFlow);
                        break;
                    }
                }
                VaWorkflowNodeUtils.getNodesInfo((ArrayNode)jsonNode.get("outgoing"), arrayNode, nodes, sequenceFlow);
            }
        }
    }

    public static String getPgwJoinNodeCode(ArrayNode nodes, String resourceId) {
        String pgwJoinNodeCode = null;
        HashMap nodeMap = new HashMap();
        nodes.forEach(node -> nodeMap.put(node.get("resourceId").asText(), node));
        LinkedList<String> stack = new LinkedList<String>();
        HashSet<String> uniqueSet = new HashSet<String>();
        stack.push(resourceId);
        while (!stack.isEmpty()) {
            String nodeCode = (String)stack.pop();
            if (!uniqueSet.add(nodeCode)) continue;
            JsonNode jsonNode = (JsonNode)nodeMap.get(nodeCode);
            if ("JoinParallelGateway".equals(jsonNode.get("stencil").get("id").asText())) {
                pgwJoinNodeCode = jsonNode.get("resourceId").asText();
                break;
            }
            ArrayNode outgoings = (ArrayNode)jsonNode.get("outgoing");
            if (outgoings == null || outgoings.isEmpty()) continue;
            for (JsonNode outgoing : outgoings) {
                stack.push(outgoing.get("resourceId").asText());
            }
        }
        return pgwJoinNodeCode;
    }

    public static String getSubmitNodeDefineId(ArrayNode nodes) {
        String submitNodeDefineId = null;
        block0: for (JsonNode node : nodes) {
            if (!"StartNoneEvent".equals(node.get("stencil").get("id").asText())) continue;
            ArrayNode outgoings = (ArrayNode)node.get("outgoing");
            if (outgoings == null || outgoings.isEmpty()) {
                return null;
            }
            String resourceId = outgoings.get(0).get("resourceId").asText();
            for (JsonNode jsonNode : nodes) {
                String outgoingId = jsonNode.get("resourceId").asText();
                if (!Objects.equals(outgoingId, resourceId)) continue;
                JsonNode seqOutgoings = jsonNode.get("outgoing");
                if (seqOutgoings == null || seqOutgoings.isEmpty()) {
                    return null;
                }
                submitNodeDefineId = seqOutgoings.get(0).get("resourceId").asText();
                break block0;
            }
        }
        return submitNodeDefineId;
    }

    public static Map<String, List<ProcessNodeDO>> packagePgwBranchMap(List<ProcessNodeDO> processNodeDOList, String pgwNodeId) {
        HashMap<String, List<ProcessNodeDO>> branchNodesMap = new HashMap<String, List<ProcessNodeDO>>();
        for (ProcessNodeDO nodeDO : processNodeDOList) {
            if (Objects.equals(pgwNodeId, nodeDO.getNodeid())) continue;
            String currPgwBranch = nodeDO.getPgwbranch();
            if (branchNodesMap.containsKey(currPgwBranch)) {
                ((List)branchNodesMap.get(currPgwBranch)).add(nodeDO);
                continue;
            }
            ArrayList<ProcessNodeDO> nodeList = new ArrayList<ProcessNodeDO>();
            nodeList.add(nodeDO);
            branchNodesMap.put(currPgwBranch, nodeList);
        }
        return branchNodesMap;
    }

    public static Map<String, List<ProcessNodeDO>> packageSubBranchMap(List<ProcessNodeDO> processNodeDOList, String subProcessNodeId) {
        HashMap<String, List<ProcessNodeDO>> branchNodesMap = new HashMap<String, List<ProcessNodeDO>>();
        for (ProcessNodeDO nodeDO : processNodeDOList) {
            if (Objects.equals(subProcessNodeId, nodeDO.getNodeid())) continue;
            String currSubBranch = nodeDO.getSubprocessbranch();
            if (branchNodesMap.containsKey(currSubBranch)) {
                ((List)branchNodesMap.get(currSubBranch)).add(nodeDO);
                continue;
            }
            ArrayList<ProcessNodeDO> nodeList = new ArrayList<ProcessNodeDO>();
            nodeList.add(nodeDO);
            branchNodesMap.put(currSubBranch, nodeList);
        }
        return branchNodesMap;
    }

    public static JsonNode adjustChildShapeOrder(JsonNode childShapes, ArrayNode arrayNodes) {
        String nodeType;
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode sequenceFlowArrayNode = mapper.createArrayNode();
        ArrayNode copyChildShapes = (ArrayNode)childShapes.deepCopy();
        ArrayNode childNode = mapper.createArrayNode();
        for (JsonNode jsonNode : arrayNodes) {
            nodeType = jsonNode.get("stencil").get("id").asText();
            if (!"SequenceFlow".equals(nodeType)) continue;
            sequenceFlowArrayNode.add(jsonNode);
        }
        copyChildShapes.addAll(sequenceFlowArrayNode);
        for (JsonNode jsonNode : copyChildShapes) {
            nodeType = jsonNode.get("stencil").get("id").asText();
            if (!"StartNoneEvent".equals(nodeType)) continue;
            childNode.add(jsonNode);
            ArrayNode outgoings = (ArrayNode)jsonNode.get("outgoing");
            VaWorkflowNodeUtils.adjustChildShapeOrder((JsonNode)copyChildShapes, outgoings, childNode);
            break;
        }
        return childNode;
    }

    private static void adjustChildShapeOrder(JsonNode childShapes, ArrayNode outGoings, ArrayNode childNode) {
        block0: for (JsonNode outgoing : outGoings) {
            for (JsonNode jsonNode : childShapes) {
                String nodeId = jsonNode.get("resourceId").asText();
                if (!outgoing.get("resourceId").asText().equals(nodeId)) continue;
                childNode.add(jsonNode);
                VaWorkflowNodeUtils.adjustChildShapeOrder(childShapes, (ArrayNode)jsonNode.get("outgoing"), childNode);
                continue block0;
            }
        }
    }

    public static JsonNode getNextNode(String resourceId, ArrayNode arrayNode) {
        LinkedHashMap<String, JsonNode> nextNodeMap = new LinkedHashMap<String, JsonNode>();
        WorkflowDTO workflowDTO = VaContext.getVaWorkflowContext().getWorkflowDTO();
        for (JsonNode jsonNode : arrayNode) {
            if (!resourceId.equals(jsonNode.get("resourceId").asText())) continue;
            ArrayNode outgoings = (ArrayNode)jsonNode.get("outgoing");
            for (int i = 0; i < outgoings.size(); ++i) {
                String outgoingId = outgoings.get(i).get("resourceId").asText();
                for (JsonNode flowNode : arrayNode) {
                    if (!outgoingId.equals(flowNode.get("resourceId").asText())) continue;
                    JsonNode properties = flowNode.get("properties");
                    JsonNode conditionNode = properties.get("conditionsequenceflow");
                    JsonNode conditionViewNode = properties.get("conditionview");
                    boolean executeResult = conditionNode instanceof TextNode || "\"\"".equals(conditionNode.get("expression").toString()) ? true : VaWorkflowNodeUtils.getWorkflowFormulaSevice().judge(workflowDTO.getUniqueCode(), null, workflowDTO.getWorkflowVariables(), conditionNode.toString());
                    if (!executeResult || !SequenceConditionUtils.executeConditionView(JSONUtil.toJSONString((Object)conditionViewNode))) continue;
                    nextNodeMap.put(outgoingId, flowNode);
                }
            }
            Set entries = nextNodeMap.entrySet();
            if (entries.size() == 1) {
                Map.Entry nextNode = entries.iterator().next();
                JsonNode nextNodeValue = (JsonNode)nextNode.getValue();
                JsonNode nodeInfo = VaWorkflowNodeUtils.getJsonNodeInfo(arrayNode, nextNodeValue);
                if (nodeInfo == null) break;
                return nodeInfo;
            }
            if (entries.size() <= 1) break;
            JsonNode properties = jsonNode.get("properties");
            if (ObjectUtils.isEmpty(properties)) {
                return null;
            }
            JsonNode sequenceFlowOrderNode = properties.get("sequencefloworder");
            if (ObjectUtils.isEmpty(sequenceFlowOrderNode)) {
                return null;
            }
            JsonNode sequenceFlowOrder = sequenceFlowOrderNode.get("sequenceFlowOrder");
            if (ObjectUtils.isEmpty(sequenceFlowOrder)) {
                Map.Entry nextNode = entries.iterator().next();
                JsonNode nextNodeValue = (JsonNode)nextNode.getValue();
                JsonNode nodeInfo = VaWorkflowNodeUtils.getJsonNodeInfo(arrayNode, nextNodeValue);
                if (nodeInfo == null) break;
                return nodeInfo;
            }
            for (JsonNode sequenceFlow : sequenceFlowOrder) {
                JsonNode nextNode;
                JsonNode nodeInfo;
                if (!nextNodeMap.containsKey(sequenceFlow.asText()) || (nodeInfo = VaWorkflowNodeUtils.getJsonNodeInfo(arrayNode, nextNode = (JsonNode)nextNodeMap.get(sequenceFlow.asText()))) == null) continue;
                return nodeInfo;
            }
        }
        return null;
    }

    private static JsonNode getJsonNodeInfo(ArrayNode arrayNode, JsonNode nextNodeValue) {
        String nextResourceId = nextNodeValue.get("target").get("resourceId").asText();
        for (JsonNode nodeInfo : arrayNode) {
            String nodeType;
            if (!nextResourceId.equals(nodeInfo.get("resourceId").asText()) || "EndNoneEvent".equals(nodeType = nextNodeValue.get("stencil").get("id").asText())) continue;
            return nodeInfo;
        }
        return null;
    }
}

