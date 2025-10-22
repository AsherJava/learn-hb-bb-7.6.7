/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 */
package com.jiuqi.gc.financialcubes.query.enums;

import com.jiuqi.common.base.BusinessRuntimeException;

public enum UnitType {
    SINGLE("0"),
    DIFFERENCE("1"),
    MERGE("9");

    private final String value;

    private UnitType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static UnitType getUnitTypeByValue(String value) {
        for (UnitType type : UnitType.values()) {
            if (!type.getValue().equals(value)) continue;
            return type;
        }
        throw new BusinessRuntimeException("\u672a\u77e5\u7684\u5355\u4f4d\u7c7b\u578b: " + value);
    }
}

