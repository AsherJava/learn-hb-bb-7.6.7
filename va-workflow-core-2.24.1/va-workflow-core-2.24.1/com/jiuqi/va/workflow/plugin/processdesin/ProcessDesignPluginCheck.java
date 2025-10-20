/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.fasterxml.jackson.databind.node.ObjectNode
 *  com.fasterxml.jackson.databind.node.TextNode
 *  com.jiuqi.va.biz.domain.PluginCheckResultDTO
 *  com.jiuqi.va.biz.domain.PluginCheckResultVO
 *  com.jiuqi.va.biz.domain.PluginCheckType
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.intf.model.PluginCheck
 *  com.jiuqi.va.biz.intf.model.PluginDefine
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.workflow.ProcessParam
 *  com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamDTO
 *  com.jiuqi.va.domain.workflow.sequencecondition.SequenceConditionView
 */
package com.jiuqi.va.workflow.plugin.processdesin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.jiuqi.va.biz.domain.PluginCheckResultDTO;
import com.jiuqi.va.biz.domain.PluginCheckResultVO;
import com.jiuqi.va.biz.domain.PluginCheckType;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.PluginCheck;
import com.jiuqi.va.biz.intf.model.PluginDefine;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.workflow.ProcessParam;
import com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamDTO;
import com.jiuqi.va.domain.workflow.sequencecondition.SequenceConditionView;
import com.jiuqi.va.workflow.plugin.processdesin.ProcessDesignPluginDefine;
import com.jiuqi.va.workflow.plugin.processparam.ProcessParamPluginDefine;
import com.jiuqi.va.workflow.service.WorkflowPublicParamService;
import com.jiuqi.va.workflow.utils.VaWorkflowUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Component
public class ProcessDesignPluginCheck
implements PluginCheck {
    @Autowired
    private WorkflowPublicParamService publicParamService;
    private final Set<String> requireNodeTypeSet = new HashSet<String>(Arrays.asList("StartNoneEvent", "UserTask", "EndNoneEvent", "SequenceFlow"));
    private final Set<String> notRequireNodeTypeSet = new HashSet<String>(Arrays.asList("ParallelGateway", "JoinParallelGateway"));

    public String getName() {
        return "processDesignPlugin";
    }

    public Class<? extends PluginDefine> getPluginDefine() {
        return ProcessDesignPluginDefine.class;
    }

    public PluginCheckResultVO checkPlugin(PluginDefine pluginDefine, ModelDefine modelDefine) {
        PluginCheckResultDTO checkResult;
        ProcessDesignPluginDefine processDesignPluginDefine = (ProcessDesignPluginDefine)pluginDefine;
        PluginCheckResultVO pluginCheckResultVO = new PluginCheckResultVO();
        pluginCheckResultVO.setPluginName(this.getName());
        ArrayList<PluginCheckResultDTO> checkResults = new ArrayList<PluginCheckResultDTO>();
        pluginCheckResultVO.setCheckResults(checkResults);
        JsonNode data = processDesignPluginDefine.getData();
        HashMap<String, Integer> countMap = new HashMap<String, Integer>();
        ArrayNode oldArrayNode = (ArrayNode)data.get("childShapes");
        if (oldArrayNode.isEmpty()) {
            PluginCheckResultDTO checkResult2 = this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u6d41\u7a0b\u8bbe\u8ba1\u754c\u9762\u4e0d\u80fd\u4e3a\u7a7a", "");
            checkResults.add(checkResult2);
            return pluginCheckResultVO;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode subProcessArrayNode = objectMapper.createArrayNode();
        for (JsonNode jsonNode : oldArrayNode) {
            if (ObjectUtils.isEmpty(jsonNode.get("childShapes"))) continue;
            JsonNode childShapes = jsonNode.get("childShapes");
            for (JsonNode childShape : childShapes) {
                ((ObjectNode)childShape).put("subProcessNode", true);
                subProcessArrayNode.add(childShape);
            }
        }
        ArrayNode childShapes = oldArrayNode.deepCopy();
        childShapes.addAll(subProcessArrayNode);
        ArrayList<String> secondUserNode = new ArrayList<String>();
        for (int i = 0; i < childShapes.size(); ++i) {
            boolean subProcessNode;
            JsonNode jsonNode = childShapes.get(i);
            String nodeType = jsonNode.get("stencil").get("id").textValue();
            ArrayNode outgoingNodes = (ArrayNode)jsonNode.get("outgoing");
            boolean bl = subProcessNode = !ObjectUtils.isEmpty(jsonNode.get("subProcessNode")) && jsonNode.get("subProcessNode").asBoolean();
            if (!"StartNoneEvent".equals(nodeType) || subProcessNode) continue;
            for (int j = 0; j < outgoingNodes.size(); ++j) {
                String outgoingId = outgoingNodes.get(j).get("resourceId").textValue();
                for (JsonNode childShape : childShapes) {
                    ArrayNode sequenceGoing;
                    String nextResourceId = childShape.get("resourceId").textValue();
                    if (!outgoingId.equals(nextResourceId) || (sequenceGoing = (ArrayNode)childShape.get("outgoing")) == null || sequenceGoing.isEmpty()) continue;
                    for (int z = 0; z < sequenceGoing.size(); ++z) {
                        String secondnodeId = sequenceGoing.get(z).get("resourceId").textValue();
                        secondUserNode.add(secondnodeId);
                    }
                }
            }
        }
        HashMap outgoingMap = new HashMap();
        HashMap incomingMap = new HashMap();
        for (JsonNode childShape : childShapes) {
            ArrayNode outgoingNodes = (ArrayNode)childShape.get("outgoing");
            String curNodeId = childShape.get("resourceId").textValue();
            for (JsonNode outgoingNode : outgoingNodes) {
                String outgoingNodeResourceId = outgoingNode.get("resourceId").textValue();
                if (outgoingMap.containsKey(curNodeId)) {
                    ((Set)outgoingMap.get(curNodeId)).add(outgoingNodeResourceId);
                } else {
                    HashSet<String> outgoingSet = new HashSet<String>();
                    outgoingSet.add(outgoingNodeResourceId);
                    outgoingMap.put(curNodeId, outgoingSet);
                }
                if (incomingMap.containsKey(outgoingNodeResourceId)) {
                    ((Set)incomingMap.get(outgoingNodeResourceId)).add(curNodeId);
                    continue;
                }
                HashSet<String> incomingSet = new HashSet<String>();
                incomingSet.add(curNodeId);
                incomingMap.put(outgoingNodeResourceId, incomingSet);
            }
        }
        for (int i = 0; i < childShapes.size(); ++i) {
            PluginCheckResultDTO checkResultDTO;
            JsonNode conditionViewNode;
            JsonNode expression;
            JsonNode properties;
            JsonNode selectNextApprovalNode;
            Object resourceId;
            PluginCheckResultDTO checkResultDTO2;
            boolean subProcessNode;
            JsonNode jsonNode = childShapes.get(i);
            String nodeName = jsonNode.get("properties").get("name").textValue();
            String nodeType = jsonNode.get("stencil").get("id").textValue();
            ArrayNode outgoingNodes = (ArrayNode)jsonNode.get("outgoing");
            String curNodeId = jsonNode.get("resourceId").textValue();
            boolean bl = subProcessNode = !ObjectUtils.isEmpty(jsonNode.get("subProcessNode")) && jsonNode.get("subProcessNode").asBoolean();
            if ("SequenceFlow".equals(nodeType)) {
                String currTitle;
                String string = currTitle = StringUtils.hasText(nodeName) ? nodeName : curNodeId;
                if (CollectionUtils.isEmpty((Collection)outgoingMap.get(curNodeId))) {
                    checkResultDTO2 = this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u8f6c\u79fb\u7ebf\u6ca1\u6709\u8fde\u51fa\u8282\u70b9:" + currTitle, "");
                    checkResults.add(checkResultDTO2);
                }
                if (CollectionUtils.isEmpty((Collection)incomingMap.get(curNodeId))) {
                    checkResultDTO2 = this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u8f6c\u79fb\u7ebf\u6ca1\u6709\u8fde\u5165\u8282\u70b9:" + currTitle, "");
                    checkResults.add(checkResultDTO2);
                }
            }
            if (("UserTask".equals(nodeType) || "EndNoneEvent".equals(nodeType) || "StartNoneEvent".equals(nodeType)) && !StringUtils.hasText(nodeName)) {
                checkResultDTO2 = this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u8282\u70b9\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a:" + nodeType, "");
                checkResults.add(checkResultDTO2);
            }
            if ("UserTask".equals(nodeType) || "StartNoneEvent".equals(nodeType)) {
                if (outgoingNodes == null || outgoingNodes.isEmpty()) {
                    checkResultDTO2 = this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u8282\u70b9\u6ca1\u6709\u8fde\u51fa\u7ebf:" + nodeName, nodeName);
                    checkResults.add(checkResultDTO2);
                } else {
                    for (int j = 0; j < outgoingNodes.size(); ++j) {
                        String outgoingId = outgoingNodes.get(j).get("resourceId").textValue();
                        for (JsonNode childShape : childShapes) {
                            ArrayNode sequenceGoing;
                            resourceId = childShape.get("resourceId").textValue();
                            if (!outgoingId.equals(resourceId) || (sequenceGoing = (ArrayNode)childShape.get("outgoing")) != null && !sequenceGoing.isEmpty()) continue;
                            PluginCheckResultDTO checkResultDTO3 = this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u8282\u70b9\u8fde\u51fa\u7ebf\u6ca1\u6709\u8fde\u63a5\u5230\u540e\u7ee7\u8282\u70b9:" + nodeName, nodeName);
                            checkResults.add(checkResultDTO3);
                        }
                    }
                }
            }
            if ("UserTask".equals(nodeType) && !ObjectUtils.isEmpty(selectNextApprovalNode = jsonNode.get("properties").get("selectnextapprovalnode"))) {
                boolean selectNextApprovalNodeFlag = selectNextApprovalNode.asBoolean();
                if (outgoingNodes.size() > 1 && !selectNextApprovalNodeFlag) {
                    for (int j = 0; j < outgoingNodes.size(); ++j) {
                        String outgoingId = outgoingNodes.get(j).get("resourceId").textValue();
                        for (JsonNode childShape : childShapes) {
                            String resourceId2 = childShape.get("resourceId").textValue();
                            if (!outgoingId.equals(resourceId2)) continue;
                            JsonNode properties2 = childShape.get("properties");
                            JsonNode expression2 = properties2.get("conditionsequenceflow").get("expression");
                            JsonNode conditionViewNode2 = properties2.get("conditionview");
                            boolean conditionViewFlag = this.checkConditionView(conditionViewNode2);
                            if (expression2 != null && StringUtils.hasText(expression2.asText()) || !conditionViewFlag) continue;
                            PluginCheckResultDTO checkResultDTO4 = this.getPluginCheckResultDTO(PluginCheckType.WARN, "\u4eba\u5de5\u8282\u70b9\u540e\u6709\u591a\u6761\u8f6c\u79fb\u7ebf\u4e14\u5b58\u5728\u672a\u914d\u7f6e\u8f6c\u79fb\u7ebf\u6761\u4ef6\u7684\u8f6c\u79fb\u7ebf:" + nodeName, nodeName);
                            checkResults.add(checkResultDTO4);
                        }
                    }
                }
            }
            if (outgoingNodes.size() > 1 && !this.requireNodeTypeSet.contains(nodeType) && !this.notRequireNodeTypeSet.contains(nodeType)) {
                for (int j = 0; j < outgoingNodes.size(); ++j) {
                    String outgoingId = outgoingNodes.get(j).get("resourceId").textValue();
                    for (JsonNode childShape : childShapes) {
                        resourceId = childShape.get("resourceId").textValue();
                        if (!outgoingId.equals(resourceId)) continue;
                        properties = childShape.get("properties");
                        expression = properties.get("conditionsequenceflow").get("expression");
                        conditionViewNode = properties.get("conditionview");
                        boolean conditionViewFlag = this.checkConditionView(conditionViewNode);
                        if (expression != null && StringUtils.hasText(expression.asText()) || !conditionViewFlag) continue;
                        PluginCheckResultDTO checkResultDTO5 = this.getPluginCheckResultDTO(PluginCheckType.WARN, "\u8282\u70b9\u540e\u6709\u591a\u6761\u8f6c\u79fb\u7ebf\u4e14\u5b58\u5728\u672a\u914d\u7f6e\u8f6c\u79fb\u7ebf\u6761\u4ef6\u7684\u8f6c\u79fb\u7ebf:" + nodeName, nodeName);
                        checkResults.add(checkResultDTO5);
                    }
                }
            }
            if (Boolean.TRUE.equals(subProcessNode) && "StartNoneEvent".equals(nodeType) && outgoingNodes.size() > 1) {
                for (int j = 0; j < outgoingNodes.size(); ++j) {
                    String outgoingId = outgoingNodes.get(j).get("resourceId").textValue();
                    for (JsonNode childShape : childShapes) {
                        resourceId = childShape.get("resourceId").textValue();
                        if (!outgoingId.equals(resourceId)) continue;
                        properties = childShape.get("properties");
                        expression = properties.get("conditionsequenceflow").get("expression");
                        conditionViewNode = properties.get("conditionview");
                        boolean conditionViewFlag = this.checkConditionView(conditionViewNode);
                        if (expression != null && StringUtils.hasText(expression.asText()) || !conditionViewFlag) continue;
                        checkResultDTO = this.getPluginCheckResultDTO(PluginCheckType.WARN, "\u5f00\u59cb\u8282\u70b9\u540e\u6709\u591a\u6761\u8f6c\u79fb\u7ebf\u4e14\u5b58\u5728\u672a\u914d\u7f6e\u8f6c\u79fb\u7ebf\u6761\u4ef6\u7684\u8f6c\u79fb\u7ebf:" + nodeName, nodeName);
                        checkResults.add(checkResultDTO);
                    }
                }
            }
            if (nodeType.equals("UserTask") && !secondUserNode.contains(curNodeId)) {
                String multiinstanceType = jsonNode.get("properties").get("multiinstance_type").asText();
                JsonNode donotgeneratetodotaskNode = jsonNode.get("properties").get("donotgeneratetodotask");
                boolean doNotGenerateTodoTask = false;
                if (donotgeneratetodotaskNode != null) {
                    doNotGenerateTodoTask = donotgeneratetodotaskNode.asBoolean();
                }
                if ("None".equals(multiinstanceType)) {
                    JsonNode usertaskassignment = jsonNode.get("properties").get("usertaskassignment");
                    if ((usertaskassignment instanceof TextNode && usertaskassignment.textValue().isEmpty() || usertaskassignment instanceof ArrayNode && usertaskassignment.isEmpty()) && !doNotGenerateTodoTask) {
                        checkResultDTO = this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u8282\u70b9\u53c2\u4e0e\u8005\u7b56\u7565\u672a\u914d\u7f6e:" + nodeName, nodeName);
                        checkResults.add(checkResultDTO);
                    }
                } else {
                    String multiinstanceCollection = jsonNode.get("properties").get("multiinstance_collection").toString();
                    if ("\"[]\"".equals(multiinstanceCollection) && !doNotGenerateTodoTask) {
                        checkResultDTO = this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u8282\u70b9\u53c2\u4e0e\u8005\u7b56\u7565\u672a\u914d\u7f6e:" + nodeName, nodeName);
                        checkResults.add(checkResultDTO);
                    }
                }
            }
            if ("EndNoneEvent".equals(nodeType) || "StartNoneEvent".equals(nodeType)) {
                if (subProcessNode) {
                    ProcessDesignPluginCheck.countNodeType(countMap, nodeType + "_subprocess", nodeType);
                    continue;
                }
                ProcessDesignPluginCheck.countNodeType(countMap, nodeType, nodeType);
                continue;
            }
            ProcessDesignPluginCheck.countNodeType(countMap, nodeType, nodeType);
        }
        Integer startCount = (Integer)countMap.get("StartNoneEvent");
        Integer userCount = (Integer)countMap.get("UserTask");
        Integer entCount = (Integer)countMap.get("EndNoneEvent");
        int nodeTypeCount = countMap.size();
        if (startCount == null || startCount != 1) {
            checkResult = this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u5fc5\u987b\u5305\u542b\u4e00\u4e2a\u5f00\u59cb\u8282\u70b9", "");
            checkResults.add(checkResult);
        }
        if (entCount == null || entCount != 1) {
            checkResult = this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u5fc5\u987b\u5305\u542b\u4e00\u4e2a\u7ed3\u675f\u8282\u70b9", "");
            checkResults.add(checkResult);
        }
        for (String notRequireNodeType : this.notRequireNodeTypeSet) {
            if (!countMap.containsKey(notRequireNodeType)) continue;
            --nodeTypeCount;
        }
        if (countMap.keySet().containsAll(this.requireNodeTypeSet)) {
            if (nodeTypeCount <= this.requireNodeTypeSet.size() && userCount < 2) {
                checkResult = this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u5fc5\u987b\u5305\u542b\u81f3\u5c112\u4e2a\u4eba\u5de5\u8282\u70b9", "");
                checkResults.add(checkResult);
            }
        } else if (userCount == null) {
            checkResult = this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u5fc5\u987b\u5305\u542b\u81f3\u5c111\u4e2a\u4eba\u5de5\u8282\u70b9", "");
            checkResults.add(checkResult);
        }
        this.checkUsedProcessParam(modelDefine, data, checkResults);
        pluginCheckResultVO.setCheckResults(checkResults);
        return pluginCheckResultVO;
    }

    private void checkUsedProcessParam(ModelDefine modelDefine, JsonNode data, List<PluginCheckResultDTO> checkResults) {
        String dataStr = JSONUtil.toJSONString((Object)data);
        Set<String> usedProcessParams = VaWorkflowUtils.extractProcessParam(dataStr);
        if (usedProcessParams.isEmpty()) {
            return;
        }
        ProcessParamPluginDefine processParamPlugin = (ProcessParamPluginDefine)modelDefine.getPlugins().find("processParamPlugin");
        List<ProcessParam> processParams = processParamPlugin != null ? processParamPlugin.getProcessParam() : new ArrayList<ProcessParam>();
        Set<Object> allProcessParams = CollectionUtils.isEmpty(processParams) ? new HashSet() : processParams.stream().map(param -> "[" + param.getParamName() + "]").collect(Collectors.toSet());
        usedProcessParams.removeAll(allProcessParams);
        if (usedProcessParams.isEmpty()) {
            return;
        }
        List<WorkflowPublicParamDTO> list = this.publicParamService.list(new WorkflowPublicParamDTO());
        Set publicParamNames = list.stream().map(param -> "[" + param.getParamname() + "]").collect(Collectors.toSet());
        usedProcessParams.removeAll(publicParamNames);
        if (!usedProcessParams.isEmpty()) {
            PluginCheckResultDTO checkResult = this.getPluginCheckResultDTO(PluginCheckType.WARN, "\u6d41\u7a0b\u53c2\u6570:" + String.join((CharSequence)"\uff0c", usedProcessParams) + "\u672a\u5728\u53c2\u6570\u8bbe\u7f6e\u4e2d\u5b9a\u4e49\uff0c\u8bf7\u68c0\u67e5", "");
            checkResults.add(checkResult);
        }
    }

    private boolean checkConditionView(JsonNode conditionViewNode) {
        String conditionViewStr = JSONUtil.toJSONString((Object)conditionViewNode);
        if (!StringUtils.hasText(conditionViewStr) || "\"\"".equals(conditionViewStr)) {
            return true;
        }
        SequenceConditionView conditionView = (SequenceConditionView)JSONUtil.parseObject((String)conditionViewStr, SequenceConditionView.class);
        if (conditionView == null) {
            return true;
        }
        List groupInfos = conditionView.getGroupInfo();
        if (CollectionUtils.isEmpty(groupInfos)) {
            return true;
        }
        return groupInfos.stream().allMatch(info -> CollectionUtils.isEmpty(info.getInfo()));
    }

    private static void countNodeType(HashMap<String, Integer> countMap, String nodeType, String nodeType1) {
        if (countMap.containsKey(nodeType)) {
            countMap.put(nodeType, countMap.get(nodeType1) + 1);
        } else {
            countMap.put(nodeType, 1);
        }
    }

    private PluginCheckResultDTO getPluginCheckResultDTO(PluginCheckType checkType, String message, String objectPath) {
        PluginCheckResultDTO pluginCheckResultDTO = new PluginCheckResultDTO();
        pluginCheckResultDTO.setObjectpath(objectPath);
        pluginCheckResultDTO.setType(checkType);
        pluginCheckResultDTO.setMessage(message);
        return pluginCheckResultDTO;
    }
}

