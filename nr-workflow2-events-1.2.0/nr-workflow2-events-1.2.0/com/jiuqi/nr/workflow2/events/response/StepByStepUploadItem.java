/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.events.response;

import java.util.Objects;

public class StepByStepUploadItem {
    private String unitId;
    private String unitCode;
    private String unitTitle;
    private String workflowState;
    private String detailMessage;

    public String getUnitId() {
        return this.unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitTitle() {
        return this.unitTitle;
    }

    public void setUnitTitle(String unitTitle) {
        this.unitTitle = unitTitle;
    }

    public String getWorkflowState() {
        return this.workflowState;
    }

    public void setWorkflowState(String workflowState) {
        this.workflowState = workflowState;
    }

    public String getDetailMessage() {
        return this.detailMessage;
    }

    public void setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
    }

    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        StepByStepUploadItem that = (StepByStepUploadItem)o;
        return Objects.equals(this.unitId, that.unitId);
    }

    public int hashCode() {
        return Objects.hashCode(this.unitId);
    }
}

