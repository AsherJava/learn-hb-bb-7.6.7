/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.internal.impl.cksr.obj;

import java.util.Set;

public class CheckRecordFormInfo {
    private String formKey;
    private Set<Integer> exeFmlAllCheckTypes;

    public CheckRecordFormInfo(String formKey, Set<Integer> exeFmlAllCheckTypes) {
        this.formKey = formKey;
        this.exeFmlAllCheckTypes = exeFmlAllCheckTypes;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public Set<Integer> getExeFmlAllCheckTypes() {
        return this.exeFmlAllCheckTypes;
    }

    public void setExeFmlAllCheckTypes(Set<Integer> exeFmlAllCheckTypes) {
        this.exeFmlAllCheckTypes = exeFmlAllCheckTypes;
    }
}

