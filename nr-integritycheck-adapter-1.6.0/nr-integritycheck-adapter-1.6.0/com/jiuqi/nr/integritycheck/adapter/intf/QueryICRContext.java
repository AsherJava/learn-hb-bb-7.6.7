/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.integritycheck.adapter.intf;

import java.io.Serializable;
import java.util.List;

public class QueryICRContext
implements Serializable {
    private static final long serialVersionUID = 8119699216506654910L;
    private String taskKey;
    private String runId;
    private String checkId;
    private List<String> orgKeys;

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

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

    public List<String> getOrgKeys() {
        return this.orgKeys;
    }

    public void setOrgKeys(List<String> orgKeys) {
        this.orgKeys = orgKeys;
    }
}

