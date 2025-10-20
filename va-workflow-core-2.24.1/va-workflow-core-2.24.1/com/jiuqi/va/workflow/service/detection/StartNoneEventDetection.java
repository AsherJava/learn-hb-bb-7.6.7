/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.node.ArrayNode
 */
package com.jiuqi.va.workflow.service.detection;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jiuqi.va.workflow.domain.detection.WorkflowDetectResultDO;
import com.jiuqi.va.workflow.domain.detection.WorkflowDetectionElement;
import com.jiuqi.va.workflow.service.detection.BaseDetectionHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class StartNoneEventDetection
extends BaseDetectionHandler {
    @Override
    public String getName() {
        return "StartNoneEvent";
    }

    @Override
    public List<WorkflowDetectionElement> detect(WorkflowDetectionElement element, List<WorkflowDetectResultDO> result, Map<String, WorkflowDetectionElement> eleMap) {
        ArrayNode outgoingList = element.getOutgoingList();
        if (outgoingList == null || outgoingList.isEmpty()) {
            return null;
        }
        String seqResourceId = outgoingList.get(0).get("resourceId").asText();
        WorkflowDetectionElement sequenceFlowEle = eleMap.get(seqResourceId);
        if (sequenceFlowEle == null) {
            return null;
        }
        ArrayNode outgoingList1 = sequenceFlowEle.getOutgoingList();
        if (outgoingList1 == null || outgoingList1.isEmpty()) {
            return null;
        }
        String submitResourceId = outgoingList1.get(0).get("resourceId").asText();
        ArrayList<WorkflowDetectionElement> nextNodes = new ArrayList<WorkflowDetectionElement>();
        nextNodes.add(eleMap.get(submitResourceId));
        return nextNodes;
    }

    @Override
    protected boolean check(WorkflowDetectionElement element, List<WorkflowDetectionElement> queue, List<WorkflowDetectResultDO> result, Map<String, WorkflowDetectionElement> eleMap) {
        return true;
    }
}

