/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.workflow.VaContext
 *  com.jiuqi.va.domain.workflow.VaWorkflowContext
 */
package com.jiuqi.va.workflow.service.detection;

import com.jiuqi.va.domain.workflow.VaContext;
import com.jiuqi.va.domain.workflow.VaWorkflowContext;
import com.jiuqi.va.workflow.domain.detection.WorkflowDetectResultDO;
import com.jiuqi.va.workflow.domain.detection.WorkflowDetectionElement;
import com.jiuqi.va.workflow.service.detection.BaseDetectionHandler;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class JoinParallelGatewayDetection
extends BaseDetectionHandler {
    @Override
    public String getName() {
        return "JoinParallelGateway";
    }

    @Override
    public List<WorkflowDetectionElement> detect(WorkflowDetectionElement element, List<WorkflowDetectResultDO> result, Map<String, WorkflowDetectionElement> eleMap) {
        String pgwCode;
        VaWorkflowContext vaWorkflowContext = VaContext.getVaWorkflowContext();
        Map customParam = vaWorkflowContext.getCustomParam();
        Map pgwMap = (Map)customParam.get("pgwMap");
        Integer count = (Integer)pgwMap.get(pgwCode = element.getPgwCode());
        if (count > 1) {
            pgwMap.put(pgwCode, count - 1);
            return null;
        }
        List<WorkflowDetectionElement> nextNodes = this.defaultDetection(element, result, eleMap);
        return nextNodes;
    }
}

