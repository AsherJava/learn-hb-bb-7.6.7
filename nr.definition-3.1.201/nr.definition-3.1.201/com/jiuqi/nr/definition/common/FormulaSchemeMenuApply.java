/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonValue
 */
package com.jiuqi.nr.definition.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum FormulaSchemeMenuApply {
    SINGLE_CALCULATION(2, "\u5355\u7b97\u6a21\u5f0f");

    private final int value;
    private final String name;
    private static Map<Integer, FormulaSchemeMenuApply> mappings;

    private FormulaSchemeMenuApply(int value, String name) {
        this.value = value;
        this.name = name;
    }

    private static Map<Integer, FormulaSchemeMenuApply> getMappings() {
        if (mappings == null) {
            mappings = Arrays.stream(FormulaSchemeMenuApply.values()).collect(Collectors.toMap(FormulaSchemeMenuApply::getValue, f -> f));
        }
        return mappings;
    }

    @JsonCreator
    public static FormulaSchemeMenuApply forValue(int value) {
        return FormulaSchemeMenuApply.getMappings().get(value);
    }

    @JsonValue
    public int getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }
}

