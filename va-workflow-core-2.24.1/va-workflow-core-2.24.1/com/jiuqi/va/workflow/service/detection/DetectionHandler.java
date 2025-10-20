/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.workflow.service.detection;

import com.jiuqi.va.workflow.domain.detection.WorkflowDetectResultDO;
import com.jiuqi.va.workflow.domain.detection.WorkflowDetectionElement;
import java.util.List;
import java.util.Map;

public interface DetectionHandler {
    public String getName();

    public void execute(WorkflowDetectionElement var1, List<WorkflowDetectionElement> var2, List<WorkflowDetectResultDO> var3, Map<String, WorkflowDetectionElement> var4);
}

