/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.multcheck2.provider;

import com.jiuqi.nr.multcheck2.common.CheckRestultState;
import com.jiuqi.nr.multcheck2.provider.FailedOrgInfo;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class CheckItemResult
implements Serializable {
    private String runId;
    private CheckRestultState result;
    private List<String> successOrgs;
    private List<String> successWithExplainOrgs;
    private Map<String, FailedOrgInfo> failedOrgs;
    private List<String> ignoreOrgs;
    private String runConfig;

    public String getRunId() {
        return this.runId;
    }

    public void setRunId(String runId) {
        this.runId = runId;
    }

    public CheckRestultState getResult() {
        return this.result;
    }

    public void setResult(CheckRestultState result) {
        this.result = result;
    }

    public List<String> getSuccessOrgs() {
        return this.successOrgs;
    }

    public void setSuccessOrgs(List<String> successOrgs) {
        this.successOrgs = successOrgs;
    }

    public Map<String, FailedOrgInfo> getFailedOrgs() {
        return this.failedOrgs;
    }

    public void setFailedOrgs(Map<String, FailedOrgInfo> failedOrgs) {
        this.failedOrgs = failedOrgs;
    }

    public List<String> getIgnoreOrgs() {
        return this.ignoreOrgs;
    }

    public void setIgnoreOrgs(List<String> ignoreOrgs) {
        this.ignoreOrgs = ignoreOrgs;
    }

    public String getRunConfig() {
        return this.runConfig;
    }

    public void setRunConfig(String runConfig) {
        this.runConfig = runConfig;
    }

    public List<String> getSuccessWithExplainOrgs() {
        return this.successWithExplainOrgs;
    }

    public void setSuccessWithExplainOrgs(List<String> successWithExplainOrgs) {
        this.successWithExplainOrgs = successWithExplainOrgs;
    }
}

