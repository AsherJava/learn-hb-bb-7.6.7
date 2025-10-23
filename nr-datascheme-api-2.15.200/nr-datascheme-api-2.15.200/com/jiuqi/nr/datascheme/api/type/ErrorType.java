/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api.type;

import java.util.ArrayList;

public enum ErrorType {
    NONE(0, "\u65e0"),
    TIP(1, "\u63d0\u793a"),
    WARNING(2, "\u8b66\u544a"),
    ERROR(4, "\u9519\u8bef");

    private int value;
    private String desc;

    private ErrorType(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return this.value;
    }

    public String getDesc() {
        return this.desc;
    }

    public static ErrorType[] interestType(int type) {
        if (type < 0) {
            return new ErrorType[0];
        }
        ArrayList<ErrorType> values = new ArrayList<ErrorType>(ErrorType.values().length);
        for (ErrorType value : ErrorType.values()) {
            if ((value.getValue() & type) == 0) continue;
            values.add(value);
        }
        return values.toArray(new ErrorType[0]);
    }
}

