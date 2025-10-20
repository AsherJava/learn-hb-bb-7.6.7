/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.workflow.service.detection;

import com.jiuqi.va.workflow.domain.detection.WorkflowDetectResultDO;
import com.jiuqi.va.workflow.domain.detection.WorkflowDetectionElement;
import com.jiuqi.va.workflow.service.WorkflowDetectionHelperService;
import com.jiuqi.va.workflow.service.WorkflowDetectionService;
import com.jiuqi.va.workflow.service.detection.BaseDetectionHandler;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubProcessDetection
extends BaseDetectionHandler {
    @Autowired
    private WorkflowDetectionService workflowDetectionService;
    @Autowired
    private WorkflowDetectionHelperService helperService;

    @Override
    public String getName() {
        return "SubProcess";
    }

    @Override
    public List<WorkflowDetectionElement> detect(WorkflowDetectionElement element, List<WorkflowDetectResultDO> result, Map<String, WorkflowDetectionElement> eleMap) {
        String elementName = element.getElementName();
        String elementType = element.getElementType();
        String subStartId = element.getSubStartId();
        WorkflowDetectionElement subStartNode = eleMap.get(subStartId);
        boolean subFailFlag = false;
        ArrayList<WorkflowDetectResultDO> subResult = new ArrayList<WorkflowDetectResultDO>();
        if (subStartNode != null) {
            LinkedList<WorkflowDetectionElement> subQueue = new LinkedList<WorkflowDetectionElement>();
            subQueue.add(subStartNode);
            this.workflowDetectionService.detectOne(eleMap, subResult, subQueue);
            subFailFlag = subResult.stream().anyMatch(subR -> Objects.equals(subR.getStatus(), 1));
        }
        ArrayList<WorkflowDetectionElement> nextNodes = new ArrayList();
        if (subFailFlag) {
            WorkflowDetectResultDO resultDO1 = this.helperService.createDetectFailResult(elementName, elementType, "\u5b50\u6d41\u7a0b\u68c0\u6d4b\u5931\u8d25");
            result.add(resultDO1);
        } else {
            nextNodes = this.defaultDetection(element, result, eleMap);
        }
        result.addAll(subResult);
        return nextNodes;
    }
}

