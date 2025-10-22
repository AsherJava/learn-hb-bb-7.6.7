/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.analysisreport.dto.resp;

public class OpenAnalysisReportDocParamDTO {
    private String dataId;
    private String docFileKey;
    private boolean isReadOnly;
    private boolean showSaveButton;
    private String confirmMessage;

    public String getDocFileKey() {
        return this.docFileKey;
    }

    public void setDocFileKey(String docFileKey) {
        this.docFileKey = docFileKey;
    }

    public boolean isReadOnly() {
        return this.isReadOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.isReadOnly = readOnly;
    }

    public boolean isShowSaveButton() {
        return this.showSaveButton;
    }

    public void setShowSaveButton(boolean showSaveButton) {
        this.showSaveButton = showSaveButton;
    }

    public String getDataId() {
        return this.dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getConfirmMessage() {
        return this.confirmMessage;
    }

    public void setConfirmMessage(String confirmMessage) {
        this.confirmMessage = confirmMessage;
    }
}

