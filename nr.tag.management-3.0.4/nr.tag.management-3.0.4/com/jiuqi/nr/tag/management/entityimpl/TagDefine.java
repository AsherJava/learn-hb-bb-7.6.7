/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.tag.management.entityimpl;

import com.jiuqi.nr.tag.management.entity.ITagDefine;

public class TagDefine
implements ITagDefine {
    private String key;
    private String owner;
    private String entity;
    private String title;
    private String icon;
    private String category;
    private String formula;
    private boolean shared;
    private String order;
    private String description;
    private boolean rangeModify;

    @Override
    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public String getEntity() {
        return this.entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    @Override
    public boolean getShared() {
        return this.shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
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

    @Override
    public boolean getRangeModify() {
        return this.rangeModify;
    }

    public void setRangeModify(boolean rangeModify) {
        this.rangeModify = rangeModify;
    }
}

