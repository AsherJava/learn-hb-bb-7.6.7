/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.unit.uselector.checker;

public enum DisplayType {
    NONE("none"),
    CHECKBOX("checkbox"),
    RADIO("radio"),
    INPUT("input"),
    INPUT_NUMBER("inputNumber");

    String value;

    private DisplayType(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }
}

