/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.api.service.entity.dto;

import java.util.List;

public class EntityQueryDTO {
    private String entityId;
    private String keyWords;
    private String entityKey;
    private List<String> selectedKeys;
    private List<String> locationPath;

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getKeyWords() {
        return this.keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public String getEntityKey() {
        return this.entityKey;
    }

    public void setEntityKey(String entityKey) {
        this.entityKey = entityKey;
    }

    public List<String> getSelectedKeys() {
        return this.selectedKeys;
    }

    public void setSelectedKeys(List<String> selectedKeys) {
        this.selectedKeys = selectedKeys;
    }

    public List<String> getLocationPath() {
        return this.locationPath;
    }

    public void setLocationPath(List<String> locationPath) {
        this.locationPath = locationPath;
    }
}

