/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.authz.bean;

import com.jiuqi.nr.authz.bean.QueryParam;

public class UEQueryParam
extends QueryParam {
    private boolean entity;
    private Integer pageCount;
    private Integer page;
    private String taskKey;
    private String formSchemeKey;

    public boolean isEntity() {
        return this.entity;
    }

    public void setEntity(boolean entity) {
        this.entity = entity;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public Integer getPageCount() {
        return this.pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getPage() {
        return this.page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }
}

