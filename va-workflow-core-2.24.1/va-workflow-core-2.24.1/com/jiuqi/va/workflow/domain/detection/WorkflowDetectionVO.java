/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.workflow.domain.detection;

import com.jiuqi.va.workflow.domain.detection.WorkflowDetectionDO;
import java.util.List;
import java.util.Map;

public class WorkflowDetectionVO
extends WorkflowDetectionDO {
    private List<Map<String, Object>> detectionResult;
    private Map<String, Object> detectionData;
    boolean orgFlag;
    List<String> orgCodes;
    List<Map<String, Object>> paramInfoList;

    public Map<String, Object> getDetectionData() {
        return this.detectionData;
    }

    public void setDetectionData(Map<String, Object> detectionData) {
        this.detectionData = detectionData;
    }

    public List<Map<String, Object>> getDetectionResult() {
        return this.detectionResult;
    }

    public void setDetectionResult(List<Map<String, Object>> detectionResult) {
        this.detectionResult = detectionResult;
    }

    public boolean isOrgFlag() {
        return this.orgFlag;
    }

    public void setOrgFlag(boolean orgFlag) {
        this.orgFlag = orgFlag;
    }

    public List<String> getOrgCodes() {
        return this.orgCodes;
    }

    public void setOrgCodes(List<String> orgCodes) {
        this.orgCodes = orgCodes;
    }

    public void setParamInfoList(List<Map<String, Object>> paramInfoList) {
        this.paramInfoList = paramInfoList;
    }

    public List<Map<String, Object>> getParamInfoList() {
        return this.paramInfoList;
    }
}

