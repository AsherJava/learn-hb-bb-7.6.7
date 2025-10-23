/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.api.resource.domain;

import com.jiuqi.nr.task.api.resource.domain.Category;
import com.jiuqi.nr.task.api.resource.domain.SearchType;

public class ResourceSearchResultDO
extends Category {
    private String key;
    private String title;
    private String code;
    private String path;
    private String icon;
    private SearchType type;
    private String resourceType;

    public ResourceSearchResultDO(String categoryType) {
        super(categoryType);
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setType(SearchType type) {
        this.type = type;
    }

    public String getResourceType() {
        return this.resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public SearchType getType() {
        return this.type;
    }

    public void setType(String key, String title) {
        this.type = new SearchType();
        this.type.setKey(key);
        this.type.setTitle(title);
    }
}

