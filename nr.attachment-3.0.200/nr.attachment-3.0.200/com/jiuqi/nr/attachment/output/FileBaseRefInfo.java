/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.output;

public class FileBaseRefInfo {
    private String dw;
    private String formKey;

    public FileBaseRefInfo(String dw, String formKey) {
        this.dw = dw;
        this.formKey = formKey;
    }

    public String getDw() {
        return this.dw;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }
}

