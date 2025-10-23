/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.web.vo;

public class GenerateItem {
    private String formKey;
    private String formPath;
    private int addCount;
    private int updateCount;

    public GenerateItem(String formKey, String formPath, int addCount, int updateCount) {
        this.formKey = formKey;
        this.formPath = formPath;
        this.addCount = addCount;
        this.updateCount = updateCount;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getFormPath() {
        return this.formPath;
    }

    public void setFormPath(String formPath) {
        this.formPath = formPath;
    }

    public int getAddCount() {
        return this.addCount;
    }

    public void setAddCount(int addCount) {
        this.addCount = addCount;
    }

    public int getUpdateCount() {
        return this.updateCount;
    }

    public void setUpdateCount(int updateCount) {
        this.updateCount = updateCount;
    }
}

