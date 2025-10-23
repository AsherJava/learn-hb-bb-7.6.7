/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.formio.format;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum Currency {
    CNY("\uffe5"),
    USD("$"),
    EUR("\u20ac");

    private final String value;
    private static final Map<String, Currency> VALUE_MAP;

    private Currency(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static Currency getByValue(String value) {
        if (value == null) {
            return null;
        }
        return VALUE_MAP.get(value);
    }

    static {
        VALUE_MAP = Arrays.stream(Currency.values()).collect(Collectors.toMap(Currency::getValue, r -> r));
    }
}

