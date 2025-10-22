/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbrbill.enums;

public enum ClbrBillTypeEnum {
    INITIATE,
    RECEIVE,
    THIRD_PARTY;


    public static ClbrBillTypeEnum getEnumByName(String name) {
        ClbrBillTypeEnum[] values;
        for (ClbrBillTypeEnum value : values = ClbrBillTypeEnum.values()) {
            if (!value.name().equals(name)) continue;
            return value;
        }
        return null;
    }
}

