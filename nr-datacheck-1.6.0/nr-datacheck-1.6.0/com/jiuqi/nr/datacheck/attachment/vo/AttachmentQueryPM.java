/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacheck.attachment.vo;

import java.util.Set;

public class AttachmentQueryPM {
    private String runId;
    private String itemKey;
    private Set<String> orgCode;
    private Boolean exportAll;

    public String getRunId() {
        return this.runId;
    }

    public void setRunId(String runId) {
        this.runId = runId;
    }

    public String getItemKey() {
        return this.itemKey;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
    }

    public Set<String> getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(Set<String> orgCode) {
        this.orgCode = orgCode;
    }

    public Boolean getExportAll() {
        return this.exportAll;
    }

    public void setExportAll(Boolean exportAll) {
        this.exportAll = exportAll;
    }
}

