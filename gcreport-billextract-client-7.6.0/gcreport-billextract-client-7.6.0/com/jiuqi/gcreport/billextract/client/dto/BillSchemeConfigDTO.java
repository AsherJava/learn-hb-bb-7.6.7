/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.billextract.client.dto;

public class BillSchemeConfigDTO {
    private String taskId;
    private String schemeId;
    private String billId;
    private String assistDim;
    private String bblx;
    private String fetchSchemeId;

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getBillId() {
        return this.billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getAssistDim() {
        return this.assistDim;
    }

    public void setAssistDim(String assistDim) {
        this.assistDim = assistDim;
    }

    public String getBblx() {
        return this.bblx;
    }

    public void setBblx(String bblx) {
        this.bblx = bblx;
    }

    public String getFetchSchemeId() {
        return this.fetchSchemeId;
    }

    public void setFetchSchemeId(String fetchSchemeId) {
        this.fetchSchemeId = fetchSchemeId;
    }

    public String toString() {
        return "BillSchemeConfigDTO [taskId=" + this.taskId + ", schemeId=" + this.schemeId + ", billId=" + this.billId + ", assistDim=" + this.assistDim + ", bblx=" + this.bblx + ", fetchSchemeId=" + this.fetchSchemeId + "]";
    }
}

