/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.workflow.service.detection;

import com.jiuqi.va.workflow.domain.WorkflowOption;
import com.jiuqi.va.workflow.domain.detection.WorkflowDetectResultDO;
import com.jiuqi.va.workflow.domain.detection.WorkflowDetectionElement;
import com.jiuqi.va.workflow.service.WorkflowDetectionHelperService;
import com.jiuqi.va.workflow.service.detection.BaseDetectionHandler;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EndNoneEventDetection
extends BaseDetectionHandler {
    @Autowired
    private WorkflowDetectionHelperService helperService;

    @Override
    public String getName() {
        return "EndNoneEvent";
    }

    @Override
    public List<WorkflowDetectionElement> detect(WorkflowDetectionElement element, List<WorkflowDetectResultDO> result, Map<String, WorkflowDetectionElement> eleMap) {
        WorkflowDetectResultDO resultDO = this.helperService.createDetectResult(element.getElementName(), element.getElementType(), WorkflowOption.DetectStatus.SUCCESS.getType(), "\u7ed3\u675f");
        result.add(resultDO);
        return null;
    }

    @Override
    protected boolean check(WorkflowDetectionElement element, List<WorkflowDetectionElement> queue, List<WorkflowDetectResultDO> result, Map<String, WorkflowDetectionElement> eleMap) {
        return true;
    }
}

