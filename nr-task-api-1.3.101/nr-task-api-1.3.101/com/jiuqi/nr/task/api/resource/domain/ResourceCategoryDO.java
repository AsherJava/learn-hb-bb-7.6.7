/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.api.resource.domain;

import com.jiuqi.nr.task.api.resource.domain.Category;
import com.jiuqi.nr.task.api.tree.TreeData;

public class ResourceCategoryDO
extends Category
implements TreeData {
    private String key;
    private String title;
    private String icon;
    private String parent;

    public ResourceCategoryDO(String type) {
        super(type);
    }

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

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String getParent() {
        return this.parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}

