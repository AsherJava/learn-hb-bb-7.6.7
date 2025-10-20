/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.fetch.impl.entity;

import com.jiuqi.gcreport.bde.fetch.impl.handler.FetchDataFormDTO;

public class GcSyncEtlFetchVO {
    private FetchDataFormDTO fetchForm;
    private String fetchTaskId;

    public FetchDataFormDTO getFetchForm() {
        return this.fetchForm;
    }

    public void setFetchForm(FetchDataFormDTO fetchForm) {
        this.fetchForm = fetchForm;
    }

    public String getFetchTaskId() {
        return this.fetchTaskId;
    }

    public void setFetchTaskId(String fetchTaskId) {
        this.fetchTaskId = fetchTaskId;
    }

    public String toString() {
        return "GcSyncEtlFetchVO [fetchForm=" + this.fetchForm + ", fetchTaskId=" + this.fetchTaskId + "]";
    }
}

