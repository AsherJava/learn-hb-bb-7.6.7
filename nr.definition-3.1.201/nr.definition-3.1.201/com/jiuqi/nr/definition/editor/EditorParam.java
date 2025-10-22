/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.editor;

public class EditorParam {
    private String formKey;
    private String regionKey;
    private int viewType;
    private boolean customStyle;
    private String editorKey;

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public int getViewType() {
        return this.viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public boolean isCustomStyle() {
        return this.customStyle;
    }

    public void setCustomStyle(boolean customStyle) {
        this.customStyle = customStyle;
    }

    public String getEditorKey() {
        return this.editorKey;
    }

    public void setEditorKey(String editorKey) {
        this.editorKey = editorKey;
    }
}

