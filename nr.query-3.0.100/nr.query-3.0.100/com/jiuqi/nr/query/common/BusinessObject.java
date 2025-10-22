/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.common;

import com.jiuqi.nr.query.common.IBusinessObject;

public class BusinessObject
implements IBusinessObject {
    public static final String OBJECTTITLE = "title";
    private boolean isNew;
    private boolean isDirty;
    private boolean isDeleted;
    private String id;
    private String title;
    private String order;

    @Override
    public void IsNew(boolean value) {
        this.isNew = value;
    }

    public boolean getIsNew() {
        return this.isNew;
    }

    @Override
    public void IsDirty(boolean value) {
        this.isDirty = value;
    }

    public boolean getIsDirty() {
        return this.isDirty;
    }

    @Override
    public void IsDeleted(boolean value) {
        this.isDeleted = value;
    }

    public boolean getIsDeleted() {
        return this.isDeleted;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}

