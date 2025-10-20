/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.datasync.executor.dto;

public class DataSyncNvwaEntityIdentityDTO {
    private String username;
    private String entityTableName;
    private String entityDataCode;

    public DataSyncNvwaEntityIdentityDTO() {
    }

    public DataSyncNvwaEntityIdentityDTO(String username, String entityTableName, String entityDataCode) {
        this.username = username;
        this.entityTableName = entityTableName;
        this.entityDataCode = entityDataCode;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEntityTableName() {
        return this.entityTableName;
    }

    public void setEntityTableName(String entityTableName) {
        this.entityTableName = entityTableName;
    }

    public String getEntityDataCode() {
        return this.entityDataCode;
    }

    public void setEntityDataCode(String entityDataCode) {
        this.entityDataCode = entityDataCode;
    }
}

