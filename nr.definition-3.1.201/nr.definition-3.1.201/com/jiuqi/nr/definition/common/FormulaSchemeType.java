/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.common;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum FormulaSchemeType {
    FORMULA_SCHEME_TYPE_REPORT(0),
    FORMULA_SCHEME_TYPE_FINANCIAL(1),
    FORMULA_SCHEME_TYPE_PICKNUM(2);

    private int intValue;
    private static Map<Integer, FormulaSchemeType> mappings;

    private static Map<Integer, FormulaSchemeType> getMappings() {
        if (mappings == null) {
            mappings = Arrays.stream(FormulaSchemeType.values()).collect(Collectors.toMap(FormulaSchemeType::getValue, f -> f));
        }
        return mappings;
    }

    private FormulaSchemeType(int value) {
        this.intValue = value;
    }

    public int getValue() {
        return this.intValue;
    }

    public static FormulaSchemeType forValue(int value) {
        return FormulaSchemeType.getMappings().get(value);
    }
}

