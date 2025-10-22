/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.tag.manager.bean;

import com.jiuqi.nr.tag.manager.bean.TagObject;
import java.io.Serializable;

public class TagImpl
implements Serializable {
    private static final long serialVersionUID = 4926197416952056427L;
    private String key;
    private String owner;
    private String viewKey;
    private String title;
    private String category;
    private String formula;
    private Boolean shared;
    private String order;
    private String description;
    private Boolean rangeModify;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getViewKey() {
        return this.viewKey;
    }

    public void setViewKey(String viewKey) {
        this.viewKey = viewKey;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public Boolean getShared() {
        return this.shared;
    }

    public void setShared(Boolean shared) {
        this.shared = shared;
    }

    public Boolean getRangeModify() {
        return this.rangeModify;
    }

    public void setRangeModify(Boolean rangeModify) {
        this.rangeModify = rangeModify;
    }

    public static TagImpl assign2Impl(TagObject tagObj) {
        TagImpl tagImpl = new TagImpl();
        tagImpl.setKey(tagObj.getKey());
        tagImpl.setFormula(tagObj.getFormula());
        tagImpl.setTitle(tagObj.getTitle());
        tagImpl.setDescription(tagObj.getDescription());
        tagImpl.setCategory(tagObj.getCategory());
        tagImpl.setOrder(tagObj.getOrder());
        tagImpl.setOwner(tagObj.getOwner());
        tagImpl.setViewKey(tagObj.getViewKey());
        tagImpl.setShared(tagObj.getShared());
        tagImpl.setRangeModify(tagObj.getRangeModify());
        return tagImpl;
    }
}

