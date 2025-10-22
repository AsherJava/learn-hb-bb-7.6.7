/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.io.params.output;

import java.io.Serializable;

public class ImportInformations
implements Serializable {
    private static final long serialVersionUID = -6616272784567701531L;
    private String formKey;
    private String formCode;
    private String formTitle;
    private String message;
    private String unitCode;

    public ImportInformations(String formKey, String formCode, String formTitle, String message, String unitCode) {
        this.formKey = formKey;
        this.formCode = formCode;
        this.formTitle = formTitle;
        this.message = message;
        this.unitCode = unitCode;
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

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }
}

