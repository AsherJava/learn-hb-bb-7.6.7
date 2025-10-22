/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.calibre2.domain;

import java.util.List;

public class EntityQueryParam {
    private String entityId;
    private List<String> entityKeys;
    private boolean queryParentName;

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public List<String> getEntityKeys() {
        return this.entityKeys;
    }

    public void setEntityKeys(List<String> entityKeys) {
        this.entityKeys = entityKeys;
    }

    public boolean isQueryParentName() {
        return this.queryParentName;
    }

    public void setQueryParentName(boolean queryParentName) {
        this.queryParentName = queryParentName;
    }
}

