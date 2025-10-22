/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.component.fix.dto;

public class OrgDataFixDTO {
    private String id;
    private String code;
    private String name;
    private Object oldValue;
    private Object newValue;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getOldValue() {
        return this.oldValue;
    }

    public void setOldValue(Object oldValue) {
        this.oldValue = oldValue;
    }

    public Object getNewValue() {
        return this.newValue;
    }

    public void setNewValue(Object newValue) {
        this.newValue = newValue;
    }
}

