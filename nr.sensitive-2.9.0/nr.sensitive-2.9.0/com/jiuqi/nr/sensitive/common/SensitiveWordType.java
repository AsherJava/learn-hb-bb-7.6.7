/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.sensitive.common;

public enum SensitiveWordType {
    STRING("\u5b57\u7b26\u4e32\u5305\u542b\u7c7b", 0),
    REGULAR_EXPRESSION("\u6b63\u5219\u8868\u8fbe\u5f0f\u7c7b", 1);

    private String name;
    private Integer value;

    private SensitiveWordType(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return this.value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}

