/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.node.ArrayNode
 */
package com.jiuqi.va.workflow.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jiuqi.va.workflow.domain.detection.WorkflowDetectResultDO;
import com.jiuqi.va.workflow.domain.detection.WorkflowDetectionElement;
import com.jiuqi.va.workflow.domain.detection.WorkflowDetectionVO;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface WorkflowDetectionHelperService {
    public List<WorkflowDetectionElement> getWorkFlowElements(String var1);

    public void getOutgoingElement(ArrayNode var1, Map<String, JsonNode> var2, List<WorkflowDetectionElement> var3, Set<String> var4);

    public List<Map<String, Object>> getCartesianProduct(Map<String, List<Object>> var1);

    public WorkflowDetectResultDO createDetectResult(String var1, String var2, Set<String> var3, int var4, String var5);

    public WorkflowDetectResultDO createDetectResult(String var1, String var2, int var3, String var4);

    public WorkflowDetectResultDO createDetectFailResult(String var1, String var2, String var3);

    public void enqueue(List<WorkflowDetectionElement> var1, WorkflowDetectionElement var2);

    public List<String> getNextEle(Map<String, WorkflowDetectionElement> var1, String var2, Integer var3, Map<String, Object> var4, ArrayNode var5);

    public void handleDetectResult(WorkflowDetectionVO var1);

    public Object handleParamType(Object var1, Object var2);

    public List<Object> getParamValueList(String var1, Object var2, Object var3, String var4);

    public void getBizTableData(Map<String, Object> var1, Map<String, List<Map<String, Object>>> var2, Map<String, Object> var3, String var4, String var5);
}

