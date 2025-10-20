/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.logmanager.client;

import java.util.List;

public class LogDetailRefreshCondition {
    private String requestInstcId;
    private String requestTaskId;
    private List<String> formIdList;

    public String getRequestInstcId() {
        return this.requestInstcId;
    }

    public void setRequestInstcId(String requestInstcId) {
        this.requestInstcId = requestInstcId;
    }

    public String getRequestTaskId() {
        return this.requestTaskId;
    }

    public void setRequestTaskId(String requestTaskId) {
        this.requestTaskId = requestTaskId;
    }

    public List<String> getFormIdList() {
        return this.formIdList;
    }

    public void setFormIdList(List<String> formIdList) {
        this.formIdList = formIdList;
    }
}

