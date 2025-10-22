/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.readwrite.bean;

import java.util.HashSet;

public class FormBatchAuthCache {
    private HashSet<String> notReadForms;
    private HashSet<String> notWriteForms;

    public HashSet<String> getNotReadForms() {
        return this.notReadForms;
    }

    public void setNotReadForms(HashSet<String> notReadForms) {
        this.notReadForms = notReadForms;
    }

    public HashSet<String> getNotWriteForms() {
        return this.notWriteForms;
    }

    public void setNotWriteForms(HashSet<String> notWriteForms) {
        this.notWriteForms = notWriteForms;
    }
}

