/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.unit.uselector.web.request;

import com.jiuqi.nr.unit.uselector.web.request.USRequestParam;

public class FilterSetTagParam
extends USRequestParam {
    private String title;
    private String category;
    private String description;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

