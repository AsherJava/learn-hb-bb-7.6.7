/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbrbill.enums;

public enum BillTypeEnum {
    NORMAL,
    WRITEOFF;


    public static BillTypeEnum getEnumByName(String name) {
        BillTypeEnum[] values;
        for (BillTypeEnum value : values = BillTypeEnum.values()) {
            if (!value.name().equals(name)) continue;
            return value;
        }
        return null;
    }
}

