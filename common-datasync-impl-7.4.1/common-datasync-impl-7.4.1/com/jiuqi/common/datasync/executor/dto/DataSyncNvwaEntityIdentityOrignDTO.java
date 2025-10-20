/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.datasync.executor.dto;

public class DataSyncNvwaEntityIdentityOrignDTO {
    private String entityTableKey;
    private String entityDataKey;
    private String entityId;

    public DataSyncNvwaEntityIdentityOrignDTO() {
    }

    public DataSyncNvwaEntityIdentityOrignDTO(String entityTableKey, String entityDataKey, String entityId) {
        this.entityTableKey = entityTableKey;
        this.entityDataKey = entityDataKey;
        this.entityId = entityId;
    }

    public String getEntityTableKey() {
        return this.entityTableKey;
    }

    public void setEntityTableKey(String entityTableKey) {
        this.entityTableKey = entityTableKey;
    }

    public String getEntityDataKey() {
        return this.entityDataKey;
    }

    public void setEntityDataKey(String entityDataKey) {
        this.entityDataKey = entityDataKey;
    }

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }
}

