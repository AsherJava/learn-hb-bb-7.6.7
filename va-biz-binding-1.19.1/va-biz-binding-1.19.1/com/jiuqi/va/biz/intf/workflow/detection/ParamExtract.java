/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.workflow.detection;

import com.jiuqi.va.biz.domain.workflow.detection.WorkflowDetectionFormula;
import java.util.Map;

public interface ParamExtract {
    public String getModule();

    public Map<String, Object> getWorkflowDetectionParam(WorkflowDetectionFormula var1);
}

