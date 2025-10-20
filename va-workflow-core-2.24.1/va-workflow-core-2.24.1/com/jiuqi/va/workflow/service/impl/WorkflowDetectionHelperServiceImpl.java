/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.fasterxml.jackson.databind.node.TextNode
 *  com.jiuqi.va.biz.intf.model.ModelDefineService
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.workflow.VaContext
 *  com.jiuqi.va.domain.workflow.VaWorkflowContext
 *  com.jiuqi.va.domain.workflow.service.WorkflowFormulaSevice
 */
package com.jiuqi.va.workflow.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.jiuqi.va.biz.intf.model.ModelDefineService;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.workflow.VaContext;
import com.jiuqi.va.domain.workflow.VaWorkflowContext;
import com.jiuqi.va.domain.workflow.service.WorkflowFormulaSevice;
import com.jiuqi.va.workflow.constants.WorkflowDetectionConst;
import com.jiuqi.va.workflow.domain.WorkflowConst;
import com.jiuqi.va.workflow.domain.WorkflowOption;
import com.jiuqi.va.workflow.domain.detection.WorkflowDetectResultDO;
import com.jiuqi.va.workflow.domain.detection.WorkflowDetectionElement;
import com.jiuqi.va.workflow.domain.detection.WorkflowDetectionVO;
import com.jiuqi.va.workflow.model.WorkflowModelDefine;
import com.jiuqi.va.workflow.plugin.processdesin.ProcessDesignPluginDefine;
import com.jiuqi.va.workflow.service.WorkflowDetectionHelperService;
import com.jiuqi.va.workflow.utils.SequenceConditionUtils;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class WorkflowDetectionHelperServiceImpl
implements WorkflowDetectionHelperService {
    private static final Logger logger = LoggerFactory.getLogger(WorkflowDetectionHelperServiceImpl.class);
    @Autowired
    private ModelDefineService modelDefineService;
    @Autowired
    private WorkflowFormulaSevice workflowFormulaSevice;

    @Override
    public List<WorkflowDetectionElement> getWorkFlowElements(String workflowDefineKey) {
        ArrayList<WorkflowDetectionElement> elements = new ArrayList<WorkflowDetectionElement>();
        WorkflowModelDefine define = (WorkflowModelDefine)this.modelDefineService.getDefine(workflowDefineKey);
        ProcessDesignPluginDefine processDesignPluginDefine = (ProcessDesignPluginDefine)define.getPlugins().get(ProcessDesignPluginDefine.class);
        ArrayNode elementsData = (ArrayNode)processDesignPluginDefine.getData().get("childShapes");
        HashMap<String, JsonNode> eleMap = new HashMap<String, JsonNode>(elementsData.size());
        for (JsonNode ele2 : elementsData) {
            eleMap.put(ele2.get("resourceId").asText(), ele2);
        }
        for (JsonNode element : elementsData) {
            JsonNode jsonNode;
            String submitNodeId;
            JsonNode submitNode;
            JsonNode outgoing;
            String outgoingResourceId;
            JsonNode outgoingNode;
            String elementType = element.get("stencil").get("id").asText();
            if (!"StartNoneEvent".equals(elementType)) continue;
            String elementId = element.get("resourceId").asText();
            String elementName = element.get("properties").get("name").asText();
            ArrayNode outgoingList = (ArrayNode)element.get("outgoing");
            WorkflowDetectionElement ele3 = new WorkflowDetectionElement();
            ele3.setElementId(elementId);
            ele3.setElementType(elementType);
            ele3.setElementName(elementName);
            ele3.setOutgoingList(outgoingList);
            elements.add(ele3);
            if (outgoingList == null || outgoingList.isEmpty() || (outgoingNode = (JsonNode)eleMap.get(outgoingResourceId = (outgoing = outgoingList.get(0)).get("resourceId").asText())) == null) break;
            ArrayNode outgoings = (ArrayNode)outgoingNode.get("outgoing");
            WorkflowDetectionElement outgoingEle = new WorkflowDetectionElement();
            outgoingEle.setElementId(outgoingResourceId);
            outgoingEle.setElementName(outgoingNode.get("properties").get("name").asText());
            outgoingEle.setElementType(outgoingNode.get("stencil").get("id").asText());
            outgoingEle.setOutgoingList(outgoings);
            outgoingEle.setSubmitNode(true);
            elements.add(outgoingEle);
            if (outgoings == null || outgoings.isEmpty() || (submitNode = (JsonNode)eleMap.get(submitNodeId = (jsonNode = outgoings.get(0)).get("resourceId").asText())) == null) break;
            ArrayNode submitOutgoing = (ArrayNode)submitNode.get("outgoing");
            WorkflowDetectionElement submitEle = new WorkflowDetectionElement();
            submitEle.setElementId(submitNodeId);
            submitEle.setElementName(submitNode.get("properties").get("name").asText());
            submitEle.setElementType(submitNode.get("stencil").get("id").asText());
            submitEle.setOutgoingList(submitOutgoing);
            submitEle.setSubmitNode(true);
            elements.add(submitEle);
            HashSet<String> uniqueSet = new HashSet<String>();
            this.getOutgoingElement(submitOutgoing, eleMap, elements, uniqueSet);
            break;
        }
        HashMap<String, WorkflowDetectionElement> detectMap = new HashMap<String, WorkflowDetectionElement>();
        elements.forEach(ele -> detectMap.put(ele.getElementId(), (WorkflowDetectionElement)((Object)ele)));
        for (WorkflowDetectionElement element : elements) {
            String elementType = element.getElementType();
            if (!"ParallelGateway".equals(elementType)) continue;
            this.handlePgwEle(detectMap, element);
        }
        return elements;
    }

    private void handlePgwEle(Map<String, WorkflowDetectionElement> detectMap, WorkflowDetectionElement element) {
        String pgwEleId = element.getElementId();
        ArrayNode outgoingList = element.getOutgoingList();
        if (outgoingList != null && !outgoingList.isEmpty()) {
            Stack<String> pgwStack = new Stack<String>();
            HashSet<String> uniquePgwIds = new HashSet<String>();
            for (JsonNode jsonNode : outgoingList) {
                pgwStack.add(jsonNode.get("resourceId").asText());
            }
            while (!pgwStack.isEmpty()) {
                WorkflowDetectionElement element1;
                String eleId = (String)pgwStack.pop();
                if (!uniquePgwIds.add(eleId) || (element1 = detectMap.get(eleId)) == null) continue;
                if ("JoinParallelGateway".equals(element1.getElementType())) {
                    element1.setPgwCode(pgwEleId);
                    continue;
                }
                element1.setPgwCode(pgwEleId);
                ArrayNode outgoingList1 = element1.getOutgoingList();
                if (outgoingList1 == null || outgoingList1.isEmpty()) continue;
                for (JsonNode jsonNode : outgoingList1) {
                    pgwStack.add(jsonNode.get("resourceId").asText());
                }
            }
        }
    }

    @Override
    public void getOutgoingElement(ArrayNode outgoingList, Map<String, JsonNode> eleMap, List<WorkflowDetectionElement> elements, Set<String> uniqueSet) {
        if (outgoingList == null || outgoingList.isEmpty()) {
            return;
        }
        for (JsonNode outgoing : outgoingList) {
            String nodeType;
            String resourceId = outgoing.get("resourceId").asText();
            JsonNode jsonNode = eleMap.get(resourceId);
            if (jsonNode == null || !uniqueSet.add(resourceId)) continue;
            String id = jsonNode.get("stencil").get("id").asText();
            ArrayNode outgoings = (ArrayNode)jsonNode.get("outgoing");
            String string = nodeType = jsonNode.get("stencil").get("nodeType") == null ? "" : jsonNode.get("stencil").get("nodeType").asText();
            if (WorkflowConst.ELEMENTTYPELIST.contains(id) || "Manual".equals(nodeType) || "AUTO".equals(nodeType) || "SubProcess".equals(id)) {
                WorkflowDetectionElement detectionElement = new WorkflowDetectionElement();
                elements.add(detectionElement);
                detectionElement.setElementId(resourceId);
                detectionElement.setElementType(id);
                detectionElement.setOutgoingList(outgoings);
                JsonNode properties = jsonNode.get("properties");
                detectionElement.setElementName(properties.get("name").asText());
                if ("SequenceFlow".equals(id)) {
                    JsonNode conditionsequenceflow = properties.get("conditionsequenceflow");
                    detectionElement.setConditionSequenceFlow(conditionsequenceflow);
                    JsonNode conditionViewNode = properties.get("conditionview");
                    detectionElement.setConditionView(JSONUtil.toJSONString((Object)conditionViewNode));
                } else if ("UserTask".equals(id)) {
                    this.handleUserTaskInfo(detectionElement, properties);
                } else if ("SubProcess".equals(id)) {
                    ArrayNode childShapes = (ArrayNode)jsonNode.get("childShapes");
                    HashMap<String, JsonNode> childEleMap = new HashMap<String, JsonNode>(childShapes.size() + eleMap.size());
                    childEleMap.putAll(eleMap);
                    for (JsonNode childEle : childShapes) {
                        childEleMap.put(childEle.get("resourceId").asText(), childEle);
                    }
                    for (JsonNode childEle : childShapes) {
                        String type = childEle.get("stencil").get("id").asText();
                        if (!"StartNoneEvent".equals(type)) continue;
                        String subStartId = childEle.get("resourceId").asText();
                        detectionElement.setSubStartId(subStartId);
                        ArrayNode childEleOutgoings = (ArrayNode)childEle.get("outgoing");
                        WorkflowDetectionElement detectionElement1 = new WorkflowDetectionElement();
                        detectionElement1.setElementId(childEle.get("resourceId").asText());
                        detectionElement1.setElementName(childEle.get("properties").get("name").asText());
                        detectionElement1.setOutgoingList(childEleOutgoings);
                        detectionElement1.setElementType(type);
                        elements.add(detectionElement1);
                        this.getOutgoingElement(childEleOutgoings, childEleMap, elements, uniqueSet);
                        break;
                    }
                }
                if ("Manual".equals(nodeType) || "AUTO".equals(nodeType)) {
                    detectionElement.setElementStencilType(nodeType);
                }
            }
            this.getOutgoingElement(outgoings, eleMap, elements, uniqueSet);
        }
    }

    private void handleUserTaskInfo(WorkflowDetectionElement detectionElement, JsonNode properties) {
        detectionElement.setAutoAgree(properties.get("autoAgree").asText());
        String multiInstanceType = properties.get("multiinstance_type") == null ? "" : properties.get("multiinstance_type").asText();
        detectionElement.setSkipEmptyParticipant(properties.get("skipemptyparticipant").asText());
        detectionElement.setSelectNextApprovalNode(properties.get("selectnextapprovalnode").asText());
        if ("Parallel".equals(multiInstanceType)) {
            detectionElement.setMultiInstanceType("Parallel");
            detectionElement.setMultiInstanceCollection(properties.get("multiinstance_collection").asText());
        } else {
            detectionElement.setMultiInstanceType("None");
            detectionElement.setUserTaskAssignment((ArrayNode)properties.get("usertaskassignment"));
        }
        detectionElement.setMultiInstanceType("Parallel".equals(multiInstanceType) ? "Parallel" : "None");
    }

    @Override
    public List<Map<String, Object>> getCartesianProduct(Map<String, List<Object>> dataMap) {
        ArrayList<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        if (dataMap.isEmpty()) {
            return result;
        }
        Map<String, List<Object>> newDataMap = dataMap.entrySet().stream().filter(entry -> entry.getValue() != null && !((List)entry.getValue()).isEmpty()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        int[] indices = new int[newDataMap.size()];
        int currentIndex = 0;
        while (true) {
            HashMap row = new HashMap();
            int i = 0;
            for (String key : newDataMap.keySet()) {
                row.put(key, newDataMap.get(key).get(indices[i]));
                ++i;
            }
            result.add(row);
            for (currentIndex = newDataMap.size() - 1; currentIndex >= 0 && indices[currentIndex] == newDataMap.get(this.getKeyAtIndex(newDataMap, currentIndex)).size() - 1; --currentIndex) {
                indices[currentIndex] = 0;
            }
            if (currentIndex < 0) break;
            int n = currentIndex;
            indices[n] = indices[n] + 1;
        }
        return result;
    }

    public boolean workflowSequenceJudge(String workflowDefine, Integer workflowDefineVersion, Map<String, Object> variables, JsonNode condition) {
        if (condition instanceof TextNode || "\"\"".equals(condition.get("expression").toString())) {
            return true;
        }
        return this.workflowFormulaSevice.judge(workflowDefine, workflowDefineVersion, variables, condition.toString());
    }

    public boolean sequenceConditionViewJudge(String conditionView) {
        return SequenceConditionUtils.executeConditionView(conditionView);
    }

    private String getKeyAtIndex(Map<String, List<Object>> dataMap, int index) {
        int i = 0;
        for (String key : dataMap.keySet()) {
            if (i == index) {
                return key;
            }
            ++i;
        }
        return null;
    }

    @Override
    public WorkflowDetectResultDO createDetectResult(String nodeName, String nodeType, Set<String> participants, int status, String msg) {
        WorkflowDetectResultDO resultDO = new WorkflowDetectResultDO();
        resultDO.setNodeName(nodeName);
        resultDO.setNodeType(nodeType);
        resultDO.setParticipants(participants);
        resultDO.setStatus(status);
        resultDO.setMsg(msg);
        String str = nodeName;
        if (StringUtils.hasText(msg)) {
            str = str + "\uff08" + msg + "\uff09";
        }
        if (status == 1) {
            str = str + "\u4e2d\u65ad";
        }
        resultDO.setContent(str + " ");
        return resultDO;
    }

    @Override
    public WorkflowDetectResultDO createDetectResult(String nodeName, String nodeType, int status, String msg) {
        return this.createDetectResult(nodeName, nodeType, null, status, msg);
    }

    @Override
    public WorkflowDetectResultDO createDetectFailResult(String nodeName, String nodeType, String msg) {
        return this.createDetectResult(nodeName, nodeType, WorkflowOption.DetectStatus.FAIL.getType(), msg);
    }

    @Override
    public void enqueue(List<WorkflowDetectionElement> queue, WorkflowDetectionElement nextNode) {
        VaWorkflowContext vaWorkflowContext = VaContext.getVaWorkflowContext();
        Map customParam = vaWorkflowContext.getCustomParam();
        Set uniqueIdSet = (Set)customParam.get("uniqueIdSet");
        if ("JoinParallelGateway".equals(nextNode.getElementType())) {
            queue.add(nextNode);
        } else if (uniqueIdSet.add(nextNode.getElementId())) {
            queue.add(nextNode);
        }
    }

    @Override
    public List<String> getNextEle(Map<String, WorkflowDetectionElement> eleMap, String workflowDefineKey, Integer version, Map<String, Object> variables, ArrayNode outgoingList) {
        ArrayList<String> nextNodeIds = new ArrayList<String>();
        for (JsonNode outgoing : outgoingList) {
            ArrayNode jsonNodes;
            JsonNode condition;
            boolean judge;
            String resourceId = outgoing.get("resourceId").asText();
            WorkflowDetectionElement outgoingEle = eleMap.get(resourceId);
            if (outgoingEle == null || !(judge = this.workflowSequenceJudge(workflowDefineKey, version, variables, condition = outgoingEle.getConditionSequenceFlow())) || !this.sequenceConditionViewJudge(outgoingEle.getConditionView()) || (jsonNodes = outgoingEle.getOutgoingList()) == null || jsonNodes.isEmpty()) continue;
            nextNodeIds.add(jsonNodes.get(0).get("resourceId").asText());
        }
        return nextNodeIds;
    }

    @Override
    public void handleDetectResult(WorkflowDetectionVO workflowDetectionVO) {
        List<Map<String, Object>> detectionResult = workflowDetectionVO.getDetectionResult();
        if (detectionResult == null || detectionResult.isEmpty()) {
            return;
        }
        for (Map<String, Object> result : detectionResult) {
            Object infoObj = result.get("info");
            if (infoObj == null) {
                result.put("info", null);
                continue;
            }
            if (infoObj instanceof String) continue;
            List info = JSONUtil.parseMapArray((String)JSONUtil.toJSONString((Object)infoObj));
            if (CollectionUtils.isEmpty(info)) {
                result.put("info", null);
                continue;
            }
            StringBuilder builder = new StringBuilder();
            for (Map map : info) {
                builder.append(map.get("content"));
            }
            result.put("info", builder.toString());
        }
    }

    @Override
    public Object handleParamType(Object paramTypeObj, Object paramValueObj) {
        if (paramValueObj == null) {
            return null;
        }
        String paramType = (String)paramTypeObj;
        if (paramType == null) {
            paramType = "default";
        }
        String finalParamType = paramType;
        try {
            if (WorkflowDetectionConst.TYPE_STRING.stream().anyMatch(s -> s.equalsIgnoreCase(finalParamType)) || "default".equals(paramType)) {
                return String.valueOf(paramValueObj);
            }
            if (!StringUtils.hasText(paramValueObj.toString())) {
                return null;
            }
            if (WorkflowDetectionConst.TYPE_DATE.stream().anyMatch(s -> s.equalsIgnoreCase(finalParamType))) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                return dateFormat.parse(paramValueObj.toString());
            }
            if ("INTEGER".equalsIgnoreCase(paramType)) {
                return Integer.parseInt(paramValueObj.toString());
            }
            if ("LONG".equalsIgnoreCase(paramType)) {
                return Long.parseLong(paramValueObj.toString());
            }
            if ("BOOLEAN".equalsIgnoreCase(paramType)) {
                return Boolean.parseBoolean(paramValueObj.toString());
            }
            if ("NUMERIC".equalsIgnoreCase(paramType) || "DECIMAL".equalsIgnoreCase(paramType)) {
                return new BigDecimal(paramValueObj.toString());
            }
        }
        catch (NumberFormatException e) {
            throw new RuntimeException("\u53c2\u6570\u6570\u636e\u7c7b\u578b\u8f93\u5165\u9519\u8bef:" + e.getMessage());
        }
        catch (ParseException e) {
            throw new RuntimeException("\u53c2\u6570\u65e5\u671f\u8f93\u5165\u9519\u8bef");
        }
        return paramValueObj;
    }

    @Override
    public List<Object> getParamValueList(String paramName, Object paramValueObj, Object paramTypeObj, String mapping) {
        ArrayList<Object> paramValueList;
        block7: {
            block6: {
                paramValueList = new ArrayList<Object>();
                if (!(paramValueObj instanceof String)) break block6;
                String paramValue = (String)paramValueObj;
                if (!StringUtils.hasText(paramValue)) break block7;
                List valuelist = Arrays.stream(paramValue.split(",")).collect(Collectors.toList());
                for (String value : valuelist) {
                    HashMap<String, Object> paramMap = new HashMap<String, Object>(8);
                    paramMap.put("code", value);
                    paramMap.put("title", value);
                    paramMap.put("paramType", paramTypeObj);
                    paramValueList.add(paramMap);
                }
                break block7;
            }
            if (paramValueObj instanceof List) {
                List paramValues = (List)paramValueObj;
                if (CollectionUtils.isEmpty(paramValues) || mapping == null) {
                    return paramValueList;
                }
                for (Map paramValue : paramValues) {
                    HashMap<String, Object> paramMap = new HashMap<String, Object>(8);
                    paramMap.put("title", paramValue.get("showTitle"));
                    paramMap.put("mapping", mapping);
                    paramMap.put("paramType", paramTypeObj);
                    if (mapping.startsWith("EM_") || mapping.startsWith("MD_ORG")) {
                        paramMap.put("code", paramValue.get("code"));
                    } else {
                        paramMap.put("code", paramValue.get("objectcode"));
                    }
                    paramValueList.add(paramMap);
                }
            }
        }
        return paramValueList;
    }

    @Override
    public void getBizTableData(Map<String, Object> variableMap, Map<String, List<Map<String, Object>>> subTableDataMap, Map<String, Object> masterTableData, String masterId, String billCode) {
        for (Map.Entry<String, Object> entry : variableMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            HashMap<String, Object> tableMap = new HashMap<String, Object>();
            if (value instanceof Map) {
                List<Map<String, Object>> list;
                Map valueMap = (Map)value;
                Object paramType = valueMap.get("paramType");
                Object paramValueObj = valueMap.get("code");
                String mapping = (String)valueMap.get("mapping");
                String title = (String)valueMap.get("title");
                Boolean subParamFlag = (Boolean)valueMap.get("subParamFlag");
                Object paramValue = this.handleParamType(paramType, paramValueObj);
                if (subParamFlag == null || !subParamFlag.booleanValue()) {
                    if (mapping != null || "UNITCODE".equals(key)) {
                        tableMap.put("name", paramValue);
                        tableMap.put("title", title);
                        masterTableData.put(key, tableMap);
                        continue;
                    }
                    masterTableData.put(key, paramValue);
                    continue;
                }
                String subTableName = (String)valueMap.get("subTableName");
                if (!subTableDataMap.containsKey(subTableName)) {
                    subTableDataMap.put(subTableName, new ArrayList());
                }
                if ((list = subTableDataMap.get(subTableName)).isEmpty()) {
                    HashMap<String, String> e = new HashMap<String, String>();
                    e.put("MASTERID", masterId);
                    e.put("BILLCODE", billCode);
                    e.put("ID", UUID.randomUUID().toString());
                    list.add(e);
                }
                if (mapping == null) {
                    list.get(0).put(key, paramValue);
                    continue;
                }
                tableMap.put("name", paramValue);
                tableMap.put("title", title);
                list.get(0).put(key, tableMap);
                continue;
            }
            if (value instanceof List) {
                Map map;
                Boolean subParamFlag;
                List values = (List)value;
                if (CollectionUtils.isEmpty(values)) continue;
                ArrayList subParam = new ArrayList();
                Iterator iterator = values.iterator();
                while (iterator.hasNext() && (subParamFlag = (Boolean)(map = (Map)iterator.next()).get("subParamFlag")) != null && subParamFlag.booleanValue()) {
                    Object paramType = map.get("paramType");
                    Object paramValueObj = map.get("paramType");
                    Object paramValue = this.handleParamType(paramType, paramValueObj);
                    subParam.add(paramType);
                }
                if (subParam.isEmpty()) continue;
            }
            masterTableData.put(key, value);
        }
    }
}

