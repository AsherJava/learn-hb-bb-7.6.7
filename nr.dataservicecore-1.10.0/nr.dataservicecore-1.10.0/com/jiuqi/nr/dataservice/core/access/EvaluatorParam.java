/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataservice.core.access;

import com.jiuqi.nr.dataservice.core.access.ResouceType;

public class EvaluatorParam {
    private String taskId;
    private String formSchemeId;
    private int resourceType = ResouceType.FORM.getCode();

    public EvaluatorParam() {
    }

    public EvaluatorParam(String taskId, String formSchemeId, int resourceType) {
        this.taskId = taskId;
        this.formSchemeId = formSchemeId;
        this.resourceType = resourceType;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getFormSchemeId() {
        return this.formSchemeId;
    }

    public void setFormSchemeId(String formSchemeId) {
        this.formSchemeId = formSchemeId;
    }

    public int getResourceType() {
        return this.resourceType;
    }

    public void setResourceType(int resourceType) {
        this.resourceType = resourceType;
    }
}

