/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.dto;

import java.io.Serializable;

public class FormSchemePublishDTO
implements Serializable {
    private static final long serialVersionUID = -2872648511409801128L;
    private String formSchemeKey;
    private boolean deployDataScheme;

    public FormSchemePublishDTO(String formSchemeKey, boolean deployDataScheme) {
        this.formSchemeKey = formSchemeKey;
        this.deployDataScheme = deployDataScheme;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public boolean isDeployDataScheme() {
        return this.deployDataScheme;
    }

    public void setDeployDataScheme(boolean deployDataScheme) {
        this.deployDataScheme = deployDataScheme;
    }
}

