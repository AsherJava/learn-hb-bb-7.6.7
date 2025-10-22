/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dafafill.entity;

public class DataFillData {
    private String id;
    private String definitionId;
    private String language;
    private byte[] data;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDefinitionId() {
        return this.definitionId;
    }

    public void setDefinitionId(String definitionId) {
        this.definitionId = definitionId;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public byte[] getData() {
        return this.data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}

