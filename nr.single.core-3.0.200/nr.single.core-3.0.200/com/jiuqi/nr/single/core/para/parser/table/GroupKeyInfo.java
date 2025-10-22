/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.para.parser.table;

public class GroupKeyInfo {
    private String code;
    private String value;
    private Object data;

    public GroupKeyInfo() {
    }

    public GroupKeyInfo(String code, String value, Object data) {
        this.code = code;
        this.value = value;
        this.data = data;
    }

    public String getCode() {
        return this.code;
    }

    public String getValue() {
        return this.value;
    }

    public Object getData() {
        return this.data;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

