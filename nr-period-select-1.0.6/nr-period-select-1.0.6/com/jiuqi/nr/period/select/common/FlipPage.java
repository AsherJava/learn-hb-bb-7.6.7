/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.period.select.common;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum FlipPage {
    NONE(0),
    PRE(1),
    NEXT(2);

    private int intValue;
    private static Map<Integer, FlipPage> mappings;

    private static Map<Integer, FlipPage> getMappings() {
        if (mappings == null) {
            mappings = Arrays.stream(FlipPage.values()).collect(Collectors.toMap(FlipPage::getValue, f -> f));
        }
        return mappings;
    }

    private FlipPage(int value) {
        this.intValue = value;
    }

    public int getValue() {
        return this.intValue;
    }

    public static FlipPage forValue(int value) {
        return FlipPage.getMappings().get(value);
    }
}

