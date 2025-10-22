/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.message;

public class SearchFileInfo {
    private String dw;
    private String formKey;

    public SearchFileInfo(String dw, String formKey) {
        this.dw = dw;
        this.formKey = formKey;
    }

    public String getDW() {
        return this.dw;
    }

    public void setDW(String dw) {
        this.dw = dw;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }
}

