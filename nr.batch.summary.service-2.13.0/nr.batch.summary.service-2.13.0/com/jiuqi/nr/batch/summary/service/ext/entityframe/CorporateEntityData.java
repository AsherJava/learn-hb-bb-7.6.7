/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.service.ext.entityframe;

public class CorporateEntityData {
    private String key;
    private String code;
    private String title;
    private String entityId;
    private String entityCode;
    private String dimensionName;

    public CorporateEntityData(String key, String code, String title) {
        this.key = key;
        this.code = code;
        this.title = title;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getEntityCode() {
        return this.entityCode;
    }

    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }

    public String getDimensionName() {
        return this.dimensionName;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
    }
}

