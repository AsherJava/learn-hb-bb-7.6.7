/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.transmission.data.intf;

public class EntityInfoParam {
    private String code;
    private String title;
    private String entityKeyData;

    public EntityInfoParam() {
    }

    public EntityInfoParam(String code, String title, String entityKeyData) {
        this.code = code;
        this.title = title;
        this.entityKeyData = entityKeyData;
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

    public String getEntityKeyData() {
        return this.entityKeyData;
    }

    public void setEntityKeyData(String entityKeyData) {
        this.entityKeyData = entityKeyData;
    }
}

