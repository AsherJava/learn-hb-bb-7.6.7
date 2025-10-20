/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.ent.bamboocloud.bim.dto.schema;

public abstract class SchemaParentDTO {
    protected String bimRequestId;

    public SchemaParentDTO() {
    }

    public SchemaParentDTO(String bimRequestId) {
        this.bimRequestId = bimRequestId;
    }

    public String getBimRequestId() {
        return this.bimRequestId;
    }

    public void setBimRequestId(String bimRequestId) {
        this.bimRequestId = bimRequestId;
    }
}

