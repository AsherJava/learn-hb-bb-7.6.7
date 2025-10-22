/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.unit.uselector.filter.listselect;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FTableUnitRow {
    public static final String TYPE_OF_UNIT = "unit";
    public static final String TYPE_OF_MORE = "more";
    private String key;
    private String code;
    private String title;
    private String type;
    private Boolean checked;

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

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty(value="_checked")
    public Boolean getChecked() {
        return this.checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}

