/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.common;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum FormulaCheckType {
    FORMULA_CHECK_NONE(0),
    FORMULA_CHECK_ERROR(4),
    FORMULA_CHECK_WARNING(2),
    FORMULA_CHECK_HINT(1),
    FORMULA_CHECK_VALIDATION(8);

    private int intValue;
    private static Map<Integer, FormulaCheckType> mappings;

    private static Map<Integer, FormulaCheckType> getMappings() {
        if (mappings == null) {
            mappings = Arrays.stream(FormulaCheckType.values()).collect(Collectors.toMap(FormulaCheckType::getValue, f -> f));
        }
        return mappings;
    }

    private FormulaCheckType(int value) {
        this.intValue = value;
    }

    public int getValue() {
        return this.intValue;
    }

    public static FormulaCheckType forValue(int value) {
        return FormulaCheckType.getMappings().get(value);
    }
}

