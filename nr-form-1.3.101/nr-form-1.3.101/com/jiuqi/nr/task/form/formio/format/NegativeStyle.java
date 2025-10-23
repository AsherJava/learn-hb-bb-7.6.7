/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.formio.format;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum NegativeStyle {
    NS_0("0", "\u51cf\u53f7"),
    NS_1("1", "\u62ec\u53f7");

    private final String value;
    private final String desc;
    private static final Map<String, NegativeStyle> VALUE_MAP;

    private NegativeStyle(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return this.value;
    }

    public String getDesc() {
        return this.desc;
    }

    public static NegativeStyle getByValue(String value) {
        if (value == null) {
            return null;
        }
        return VALUE_MAP.get(value);
    }

    static {
        VALUE_MAP = Arrays.stream(NegativeStyle.values()).collect(Collectors.toMap(NegativeStyle::getValue, r -> r));
    }
}

