/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.common;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum FormulaSchemeDisplayMode {
    FORMULA_SCHEME_DISPLAYMODE_NOBUTTON(0),
    FORMULA_SCHEME_DISPLAYMODE_ALONEBUTTON(1),
    FORMULA_SCHEME_DISPLAYMODE_DROPDOWN(2);

    private int intValue;
    private static Map<Integer, FormulaSchemeDisplayMode> mappings;

    private static Map<Integer, FormulaSchemeDisplayMode> getMappings() {
        if (mappings == null) {
            mappings = Arrays.stream(FormulaSchemeDisplayMode.values()).collect(Collectors.toMap(FormulaSchemeDisplayMode::getValue, f -> f));
        }
        return mappings;
    }

    private FormulaSchemeDisplayMode(int value) {
        this.intValue = value;
    }

    public int getValue() {
        return this.intValue;
    }

    public static FormulaSchemeDisplayMode forValue(int value) {
        return FormulaSchemeDisplayMode.getMappings().get(value);
    }
}

