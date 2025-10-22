/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.io.params.input;

public enum UnitEntityDataRowTypeEnum {
    NOT_EXIST(-1, "\u4e0d\u5b58\u5728"),
    ADD(0, "\u65b0\u589e"),
    UPDATE(1, "\u4fee\u6539");

    private final int type;
    private final String desc;

    private UnitEntityDataRowTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return this.type;
    }

    public String getDesc() {
        return this.desc;
    }
}

