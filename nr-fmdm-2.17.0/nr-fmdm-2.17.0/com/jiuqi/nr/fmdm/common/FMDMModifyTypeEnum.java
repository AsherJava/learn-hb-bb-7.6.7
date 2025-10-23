/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.fmdm.common;

public enum FMDMModifyTypeEnum {
    ADD("add", "\u65b0\u589e"),
    UPDATE("update", "\u66f4\u65b0"),
    BATCHADD("batchAdd", "\u6279\u91cf\u65b0\u589e"),
    BATCHUPDATE("batchUpdate", "\u6279\u91cf\u66f4\u65b0"),
    UNDEFINED("undefined", "\u672a\u5b9a\u4e49");

    private final String type;

    private FMDMModifyTypeEnum(String type, String title) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}

