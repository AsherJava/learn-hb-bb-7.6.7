/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.tag.management.bean;

import com.jiuqi.nr.tag.management.intf.ITagFacade;

public class TagFacadeImpl
implements ITagFacade {
    private String key;
    private String title;
    private String category;
    private String icon;
    private String order;
    private String description;

    @Override
    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

