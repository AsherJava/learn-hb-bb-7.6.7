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
import com.jiuqi.va.workflow.service.detection.DetectionHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

public abstract class BaseDetectionHandler
implements DetectionHandler {
    @Autowired
    private WorkflowDetectionHelperService helperService;

    protected List<WorkflowDetectionElement> detect(WorkflowDetectionElement element, List<WorkflowDetectResultDO> result, Map<String, WorkflowDetectionElement> eleMap) {
        return this.defaultDetection(element, result, eleMap);
    }

    protected List<WorkflowDetectionElement> defaultDetection(WorkflowDetectionElement element, List<WorkflowDetectResultDO> result, Map<String, WorkflowDetectionElement> eleMap) {
        int status;
        String msg;
        ArrayList<WorkflowDetectionElement> nextNodes = new ArrayList<WorkflowDetectionElement>();
        VaWorkflowContext vaWorkflowContext = VaContext.getVaWorkflowContext();
        WorkflowDTO workflowDTO = vaWorkflowContext.getWorkflowDTO();
        String workflowDefineKey = workflowDTO.getUniqueCode();
        Integer version = (Integer)workflowDTO.getExtInfo("version");
        Map variables = workflowDTO.getWorkflowVariables();
        String elementName = element.getElementName();
        String elementType = element.getElementType();
        List<String> nextNodeIds = this.helperService.getNextEle(eleMap, workflowDefineKey, version, variables, element.getOutgoingList());
        int nextNodeCnt = nextNodeIds.size();
        if (nextNodeCnt == 1) {
            msg = "\u81ea\u52a8";
            status = WorkflowOption.DetectStatus.SUCCESS.getType();
            WorkflowDetectionElement nextNode = eleMap.get(nextNodeIds.get(0));
            if (nextNode == null) {
                msg = "\u6ca1\u6709\u540e\u7eed\u53ef\u8fbe\u8282\u70b9";
                status = WorkflowOption.DetectStatus.FAIL.getType();
            } else {
                nextNodes.add(nextNode);
            }
        } else if (nextNodeCnt == 0) {
            msg = "\u6ca1\u6709\u540e\u7eed\u53ef\u8fbe\u8282\u70b9";
            status = WorkflowOption.DetectStatus.FAIL.getType();
        } else {
            msg = "\u6709\u591a\u4e2a\u540e\u7eed\u53ef\u8fbe\u8282\u70b9";
            status = WorkflowOption.DetectStatus.FAIL.getType();
        }
        WorkflowDetectResultDO resultDO = this.helperService.createDetectResult(elementName, elementType, status, msg);
        result.add(resultDO);
        return nextNodes;
    }

    @Override
    public final void execute(WorkflowDetectionElement element, List<WorkflowDetectionElement> queue, List<WorkflowDetectResultDO> result, Map<String, WorkflowDetectionElement> eleMap) {
        boolean check = this.check(element, queue, result, eleMap);
        if (!check) {
            return;
        }
        List<WorkflowDetectionElement> nextNodes = this.detect(element, result, eleMap);
        this.leave(nextNodes, queue);
    }

    protected boolean check(WorkflowDetectionElement element, List<WorkflowDetectionElement> queue, List<WorkflowDetectResultDO> result, Map<String, WorkflowDetectionElement> eleMap) {
        ArrayNode outgoingList = element.getOutgoingList();
        if (outgoingList == null || outgoingList.isEmpty()) {
            String elementName = element.getElementName();
            String elementType = element.getElementType();
            this.helperService.createDetectFailResult(elementName, elementType, "\u6ca1\u6709\u540e\u7eed\u53ef\u8fbe\u8282\u70b9");
            return false;
        }
        return true;
    }

    protected void leave(List<WorkflowDetectionElement> nextNodes, List<WorkflowDetectionElement> queue) {
        if (!CollectionUtils.isEmpty(nextNodes)) {
            nextNodes.forEach(node -> this.helperService.enqueue(queue, (WorkflowDetectionElement)((Object)node)));
        }
    }
}

