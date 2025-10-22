/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.common;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum FormulaSyntaxStyle {
    FORMULA_SYNTAX_STYLE_TRADITION(1),
    FORMULA_SYNTAX_STYLE_EXCEL(2);

    private int intValue;
    private static Map<Integer, FormulaSyntaxStyle> mappings;

    private static Map<Integer, FormulaSyntaxStyle> getMappings() {
        if (mappings == null) {
            mappings = Arrays.stream(FormulaSyntaxStyle.values()).collect(Collectors.toMap(FormulaSyntaxStyle::getValue, f -> f));
        }
        return mappings;
    }

    private FormulaSyntaxStyle(int value) {
        this.intValue = value;
    }

    public int getValue() {
        return this.intValue;
    }

    public static FormulaSyntaxStyle forValue(int value) {
        return FormulaSyntaxStyle.getMappings().get(value);
    }
}

