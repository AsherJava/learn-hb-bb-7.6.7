/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.dto;

import java.io.Serializable;
import java.util.List;

public class FormPublishDTO
implements Serializable {
    private static final long serialVersionUID = -2872648511409801128L;
    private String formSchemeKey;
    private List<String> formKeys;
    private boolean deployDataTable;

    public FormPublishDTO(String formSchemeKey, List<String> formKeys) {
        this.formSchemeKey = formSchemeKey;
        this.formKeys = formKeys;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }

    public boolean isDeployDataTable() {
        return this.deployDataTable;
    }

    public void setDeployDataTable(boolean deployDataTable) {
        this.deployDataTable = deployDataTable;
    }
}

