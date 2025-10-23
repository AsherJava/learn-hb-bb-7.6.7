/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.controller.dto;

import java.util.List;

public class DeleteFormParam {
    private String key;
    private String deleteId;
    private Boolean deleteRefEntity;
    private List<String> baseDataNames;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Boolean isDeleteRefEntity() {
        return this.deleteRefEntity != null && this.deleteRefEntity != false;
    }

    public void setDeleteRefEntity(Boolean deleteRefEntity) {
        this.deleteRefEntity = deleteRefEntity;
    }

    public List<String> getBaseDataNames() {
        return this.baseDataNames;
    }

    public void setBaseDataNames(List<String> baseDataNames) {
        this.baseDataNames = baseDataNames;
    }

    public String getDeleteId() {
        return this.deleteId;
    }

    public void setDeleteId(String deleteId) {
        this.deleteId = deleteId;
    }
}

