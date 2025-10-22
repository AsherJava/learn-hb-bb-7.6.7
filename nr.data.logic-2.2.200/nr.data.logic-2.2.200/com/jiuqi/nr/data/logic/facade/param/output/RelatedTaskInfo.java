/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.facade.param.output;

import java.io.Serializable;
import java.util.Map;

public class RelatedTaskInfo
implements Serializable {
    private static final long serialVersionUID = 4821352088410385753L;
    private String relatedTaskKey;
    private String relatedOrgId;
    private Map<String, String> masterKey;

    public RelatedTaskInfo(String relatedTaskKey, String relatedOrgId, Map<String, String> masterKey) {
        this.relatedTaskKey = relatedTaskKey;
        this.relatedOrgId = relatedOrgId;
        this.masterKey = masterKey;
    }

    public String getRelatedTaskKey() {
        return this.relatedTaskKey;
    }

    public Map<String, String> getMasterKey() {
        return this.masterKey;
    }

    public void setRelatedTaskKey(String relatedTaskKey) {
        this.relatedTaskKey = relatedTaskKey;
    }

    public void setMasterKey(Map<String, String> masterKey) {
        this.masterKey = masterKey;
    }

    public String getRelatedOrgId() {
        return this.relatedOrgId;
    }

    public void setRelatedOrgId(String relatedOrgId) {
        this.relatedOrgId = relatedOrgId;
    }
}

