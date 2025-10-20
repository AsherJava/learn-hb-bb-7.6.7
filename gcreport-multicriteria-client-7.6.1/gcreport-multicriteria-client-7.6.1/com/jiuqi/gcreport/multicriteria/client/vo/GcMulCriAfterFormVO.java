/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.multicriteria.client.vo;

import java.util.List;

public class GcMulCriAfterFormVO {
    private String id;
    private String taskId;
    private String schemeId;
    private List<String> formKeys;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }
}

