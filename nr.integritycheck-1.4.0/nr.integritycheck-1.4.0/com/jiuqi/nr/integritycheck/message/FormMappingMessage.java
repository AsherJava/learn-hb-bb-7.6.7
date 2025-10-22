/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.integritycheck.message;

import java.io.Serializable;

public class FormMappingMessage
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String formKey;
    private String formCode;
    private String formTitle;

    public FormMappingMessage() {
    }

    public FormMappingMessage(String formKey, String formCode, String formTitle) {
        this.formKey = formKey;
        this.formCode = formCode;
        this.formTitle = formTitle;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getFormCode() {
        return this.formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getFormTitle() {
        return this.formTitle;
    }

    public void setFormTitle(String formTitle) {
        this.formTitle = formTitle;
    }
}

