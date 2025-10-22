/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacheck.attachment.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class QueryResParam
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String runId;
    private String checkId;
    private String taskKey;
    private String formSchemeKey;
    private String period;
    private List<String> orgCode;
    private Map<String, String> selectDims;

    public String getRunId() {
        return this.runId;
    }

    public void setRunId(String runId) {
        this.runId = runId;
    }

    public String getCheckId() {
        return this.checkId;
    }

    public void setCheckId(String checkId) {
        this.checkId = checkId;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public List<String> getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(List<String> orgCode) {
        this.orgCode = orgCode;
    }

    public Map<String, String> getSelectDims() {
        return this.selectDims;
    }

    public void setSelectDims(Map<String, String> selectDims) {
        this.selectDims = selectDims;
    }
}

