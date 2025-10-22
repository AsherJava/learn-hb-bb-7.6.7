/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.internal.model.impl;

import com.jiuqi.nr.entity.model.IEntityGroup;

public class EntityGroupImpl
implements IEntityGroup {
    private String id;
    private String title;
    private String parentId;

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}

