/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.common;

public enum FieldCompareSymbols {
    MORETHAN("\u5927\u4e8e"),
    LESSTHAN("\u5c0f\u4e8e"),
    EQUE("\u7b49\u4e8e"),
    NOTEQUE("\u4e0d\u7b49\u4e8e"),
    BETWEEN("\u4ecb\u4e8e"),
    NOTBETWEEN("\u4e0d\u4ecb\u4e8e"),
    MOREANDEQUE("\u5927\u4e8e\u6216\u7b49\u4e8e"),
    LESSANDEQUE("\u5c0f\u4e8e\u6216\u7b49\u4e8e"),
    CONTAIN("\u5305\u542b"),
    NOTCONTAIN("\u4e0d\u5305\u542b"),
    REPEAT("\u91cd\u590d\u503c"),
    ONLY("\u552f\u4e00\u503c"),
    RANK("\u6392\u540d"),
    AVERAGE("\u5e73\u5747\u503c");

    private String value;

    private FieldCompareSymbols(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

