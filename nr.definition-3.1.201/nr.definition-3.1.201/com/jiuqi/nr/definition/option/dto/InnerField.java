/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.dto;

public class InnerField {
    private String code;
    private String title;
    private String entityId;
    private String referEntityId;

    public InnerField() {
    }

    public InnerField(String code, String title, String dw) {
        this.code = code;
        this.title = title;
        this.entityId = dw;
    }

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReferEntityId() {
        return this.referEntityId;
    }

    public void setReferEntityId(String referEntityId) {
        this.referEntityId = referEntityId;
    }
}

