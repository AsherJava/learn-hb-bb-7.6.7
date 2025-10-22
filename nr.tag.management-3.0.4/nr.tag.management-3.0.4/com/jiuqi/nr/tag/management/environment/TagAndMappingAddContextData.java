/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.tag.management.environment;

import java.util.List;

public class TagAndMappingAddContextData {
    private String entityId;
    private String title;
    private String category;
    private String description;
    private List<String> entityDataKeys;

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

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

    public List<String> getEntityDataKeys() {
        return this.entityDataKeys;
    }

    public void setEntityDataKeys(List<String> entityDataKeys) {
        this.entityDataKeys = entityDataKeys;
    }
}

