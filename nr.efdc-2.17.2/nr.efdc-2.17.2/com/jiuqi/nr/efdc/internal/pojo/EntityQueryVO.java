/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.efdc.internal.pojo;

import java.util.List;

public class EntityQueryVO {
    private String dimKey;
    private String viewKey;
    private String formSchemeKey;
    private List<String> assistViews;

    public EntityQueryVO() {
    }

    public EntityQueryVO(String dimKey, String viewKey, String formSchemeKey, List<String> assistViews) {
        this.dimKey = dimKey;
        this.viewKey = viewKey;
        this.formSchemeKey = formSchemeKey;
        this.assistViews = assistViews;
    }

    public String getDimKey() {
        return this.dimKey;
    }

    public void setDimKey(String dimKey) {
        this.dimKey = dimKey;
    }

    public String getViewKey() {
        return this.viewKey;
    }

    public void setViewKey(String viewKey) {
        this.viewKey = viewKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public List<String> getAssistViews() {
        return this.assistViews;
    }

    public void setAssistViews(List<String> assistViews) {
        this.assistViews = assistViews;
    }
}

