/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.jiuqi.va.domain.workflow.VaContext
 *  com.jiuqi.va.domain.workflow.VaWorkflowContext
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 */
package com.jiuqi.va.workflow.service.detection;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jiuqi.va.domain.workflow.VaContext;
import com.jiuqi.va.domain.workflow.VaWorkflowContext;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import com.jiuqi.va.workflow.domain.WorkflowOption;
import com.jiuqi.va.workflow.domain.detection.WorkflowDetectResultDO;
import com.jiuqi.va.workflow.domain.detection.WorkflowDetectionElement;
import com.jiuqi.va.workflow.service.WorkflowDetectionHelperService;
import com.jiuqi.va.workflow.service.detection.BaseDetectionHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParallelGatewayDetection
extends BaseDetectionHandler {
    @Autowired
    private WorkflowDetectionHelperService helperService;

    @Override
    public String getName() {
        return "ParallelGateway";
    }

    @Override
    public List<WorkflowDetectionElement> detect(WorkflowDetectionElement element, List<WorkflowDetectResultDO> result, Map<String, WorkflowDetectionElement> eleMap) {
        VaWorkflowContext vaWorkflowContext = VaContext.getVaWorkflowContext();
        Map customParam = vaWorkflowContext.getCustomParam();
        WorkflowDTO workflowDTO = vaWorkflowContext.getWorkflowDTO();
        String workflowDefineKey = workflowDTO.getUniqueCode();
        Integer version = (Integer)workflowDTO.getExtInfo("version");
        Map variables = workflowDTO.getWorkflowVariables();
        String elementName = element.getElementName();
        String elementType = element.getElementType();
        String elementId = element.getElementId();
        ArrayNode outgoingList = element.getOutgoingList();
        List<String> nextNodeIds = this.helperService.getNextEle(eleMap, workflowDefineKey, version, variables, outgoingList);
        int nextNodeCnt = nextNodeIds.size();
        if (nextNodeCnt == 0) {
            WorkflowDetectResultDO resultDO = this.helperService.createDetectFailResult(elementName, elementType, "\u6ca1\u6709\u540e\u7eed\u53ef\u8fbe\u8282\u70b9");
            result.add(resultDO);
            return null;
        }
        HashMap<String, Integer> pgwMap = new HashMap<String, Integer>();
        pgwMap.put(elementId, nextNodeCnt);
        customParam.put("pgwMap", pgwMap);
        ArrayList<WorkflowDetectionElement> nextNodes = new ArrayList<WorkflowDetectionElement>();
        for (String nextNodeId : nextNodeIds) {
            nextNodes.add(eleMap.get(nextNodeId));
        }
        WorkflowDetectResultDO resultDO = this.helperService.createDetectResult(elementName, elementType, WorkflowOption.DetectStatus.SUCCESS.getType(), "\u53d1\u6563");
        result.add(resultDO);
        return nextNodes;
    }
}

