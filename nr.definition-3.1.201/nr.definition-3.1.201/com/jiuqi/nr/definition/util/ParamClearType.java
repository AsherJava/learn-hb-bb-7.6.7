/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.util;

public enum ParamClearType {
    TASK("\u6309\u4efb\u52a1\u6e05\u9664", 0),
    OPTIONAL("\u6309\u9009\u62e9\u8303\u56f4\u6e05\u9664", 1),
    CONDITIONAL("\u6309\u4e0d\u7b26\u5408\u62a5\u8868\u9002\u5e94\u6761\u4ef6\u6e05\u9664", 2);

    private int intValue;
    private String name;

    private ParamClearType(String name, int value) {
        this.intValue = value;
    }

    public int getValue() {
        return this.intValue;
    }
}

