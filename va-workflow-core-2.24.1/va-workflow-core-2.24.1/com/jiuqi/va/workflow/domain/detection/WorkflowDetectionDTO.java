/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.workflow.domain.detection;

import com.jiuqi.va.workflow.domain.detection.WorkflowDetectionDO;
import java.util.List;
import java.util.Map;

public class WorkflowDetectionDTO
extends WorkflowDetectionDO {
    private List<Map<String, Object>> inputParam;
    private boolean retryFlag;
    private String detectiondata;
    private boolean orgFlag;
    private List<String> orgCodes;
    private String detectionresult;
    private boolean searchdata;
    private String bizType;

    public String getBizType() {
        return this.bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public List<String> getOrgCodes() {
        return this.orgCodes;
    }

    public void setOrgCodes(List<String> orgCodes) {
        this.orgCodes = orgCodes;
    }

    public boolean isOrgFlag() {
        return this.orgFlag;
    }

    public void setOrgFlag(boolean orgFlag) {
        this.orgFlag = orgFlag;
    }

    public String getDetectiondata() {
        return this.detectiondata;
    }

    public void setDetectiondata(String detectiondata) {
        this.detectiondata = detectiondata;
    }

    public boolean isSearchdata() {
        return this.searchdata;
    }

    public void setSearchdata(boolean searchdata) {
        this.searchdata = searchdata;
    }

    public String getDetectionresult() {
        return this.detectionresult;
    }

    public void setDetectionresult(String detectionresult) {
        this.detectionresult = detectionresult;
    }

    public List<Map<String, Object>> getInputParam() {
        return this.inputParam;
    }

    public void setInputParam(List<Map<String, Object>> inputParam) {
        this.inputParam = inputParam;
    }

    public boolean isRetryFlag() {
        return this.retryFlag;
    }

    public void setRetryFlag(boolean retryFlag) {
        this.retryFlag = retryFlag;
    }
}

