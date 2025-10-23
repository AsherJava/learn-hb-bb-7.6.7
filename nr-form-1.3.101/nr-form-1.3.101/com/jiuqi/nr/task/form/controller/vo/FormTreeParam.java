/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.controller.vo;

import java.util.HashMap;
import java.util.List;

public class FormTreeParam {
    private String formSchemeKey;
    private String formGroupKey;
    private List<String> selectedKeys;
    private String keyword;
    private boolean sync;
    private HashMap<String, Object> extend;

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getFormGroupKey() {
        return this.formGroupKey;
    }

    public void setFormGroupKey(String formGroupKey) {
        this.formGroupKey = formGroupKey;
    }

    public List<String> getSelectedKeys() {
        return this.selectedKeys;
    }

    public void setSelectedKeys(List<String> selectedKeys) {
        this.selectedKeys = selectedKeys;
    }

    public String getKeyword() {
        return this.keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public boolean getSync() {
        return this.sync;
    }

    public void setSync(boolean sync) {
        this.sync = sync;
    }

    public boolean isSync() {
        return this.sync;
    }

    public HashMap<String, Object> getExtend() {
        return this.extend;
    }

    public void setExtend(HashMap<String, Object> extend) {
        this.extend = extend;
    }
}

