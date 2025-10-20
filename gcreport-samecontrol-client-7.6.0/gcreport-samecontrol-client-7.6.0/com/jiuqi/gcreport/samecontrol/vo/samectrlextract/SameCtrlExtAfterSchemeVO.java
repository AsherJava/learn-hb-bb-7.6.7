/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.samecontrol.vo.samectrlextract;

import java.util.Map;

public class SameCtrlExtAfterSchemeVO {
    private String schemeId;
    private String orgId;
    private Map<String, String> assistDim;

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getOrgId() {
        return this.orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public Map<String, String> getAssistDim() {
        return this.assistDim;
    }

    public void setAssistDim(Map<String, String> assistDim) {
        this.assistDim = assistDim;
    }
}

