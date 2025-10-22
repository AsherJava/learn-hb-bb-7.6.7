/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.fetch.impl.ctcc.intf;

public class EtlFormInfoKey {
    private String formKey;
    private String formTitle;

    public EtlFormInfoKey(String formKey, String formTitle) {
        this.formKey = formKey;
        this.formTitle = formTitle;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public String getFormTitle() {
        return this.formTitle;
    }

    public boolean equals(Object obj) {
        if (obj instanceof EtlFormInfoKey) {
            EtlFormInfoKey key = (EtlFormInfoKey)obj;
            return key.getFormKey().equals(this.getFormKey());
        }
        return false;
    }

    public int hashCode() {
        return this.getFormKey().hashCode();
    }
}

