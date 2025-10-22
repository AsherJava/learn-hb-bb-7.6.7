/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.annotation.message;

import java.io.Serializable;

public class FormSchemeMappingMessage
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String formSchemeKey;
    private String formSchemeCode;
    private String formSchemeTitle;

    public FormSchemeMappingMessage() {
    }

    public FormSchemeMappingMessage(String formSchemeKey, String formSchemeCode, String formSchemeTitle) {
        this.formSchemeKey = formSchemeKey;
        this.formSchemeCode = formSchemeCode;
        this.formSchemeTitle = formSchemeTitle;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getFormSchemeCode() {
        return this.formSchemeCode;
    }

    public void setFormSchemeCode(String formSchemeCode) {
        this.formSchemeCode = formSchemeCode;
    }

    public String getFormSchemeTitle() {
        return this.formSchemeTitle;
    }

    public void setFormSchemeTitle(String formSchemeTitle) {
        this.formSchemeTitle = formSchemeTitle;
    }
}

