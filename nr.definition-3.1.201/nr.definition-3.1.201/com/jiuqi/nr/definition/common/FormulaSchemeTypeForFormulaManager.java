/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.common;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum FormulaSchemeTypeForFormulaManager {
    FORMULA_SCHEME_TYPE_REPORT(1),
    FORMULA_SCHEME_TYPE_FINANCIAL(2);

    private int intValue;
    private static Map<Integer, FormulaSchemeTypeForFormulaManager> mappings;

    private static Map<Integer, FormulaSchemeTypeForFormulaManager> getMappings() {
        if (mappings == null) {
            mappings = Arrays.stream(FormulaSchemeTypeForFormulaManager.values()).collect(Collectors.toMap(FormulaSchemeTypeForFormulaManager::getValue, f -> f));
        }
        return mappings;
    }

    private FormulaSchemeTypeForFormulaManager(int value) {
        this.intValue = value;
    }

    public int getValue() {
        return this.intValue;
    }

    public static FormulaSchemeTypeForFormulaManager forValue(int value) {
        return FormulaSchemeTypeForFormulaManager.getMappings().get(value);
    }
}

