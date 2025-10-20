/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.workflow.service;

import com.jiuqi.va.workflow.domain.detection.WorkflowDetectResultDO;
import com.jiuqi.va.workflow.domain.detection.WorkflowDetectionDO;
import com.jiuqi.va.workflow.domain.detection.WorkflowDetectionDTO;
import com.jiuqi.va.workflow.domain.detection.WorkflowDetectionElement;
import com.jiuqi.va.workflow.domain.detection.WorkflowDetectionVO;
import java.util.List;
import java.util.Map;

public interface WorkflowDetectionService {
    public WorkflowDetectionVO detectionParamExtract(WorkflowDetectionDTO var1);

    public WorkflowDetectionVO detectionExecute(WorkflowDetectionDTO var1);

    public void saveDetectInfo(List<WorkflowDetectionDO> var1, WorkflowDetectionDTO var2, List<Map<String, Object>> var3);

    public void saveRetryInfo(String var1, String var2, String var3);

    public WorkflowDetectionVO getDetectionData(WorkflowDetectionDTO var1);

    public void detectOne(Map<String, WorkflowDetectionElement> var1, List<WorkflowDetectResultDO> var2, List<WorkflowDetectionElement> var3);
}

