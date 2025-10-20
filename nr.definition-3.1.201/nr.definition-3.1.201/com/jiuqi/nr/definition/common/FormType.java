/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.common;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum FormType {
    FORM_TYPE_FIX(0),
    FORM_TYPE_FLOAT(1),
    FORM_TYPE_TEXT_INFO(2),
    FORM_TYPE_ATTACHED(3),
    FORM_TYPE_QUERY(4),
    FORM_TYPE_SURVEY(5),
    FORM_TYPE_CARD_INPUT(6),
    FORM_TYPE_ENTITY(7),
    FORM_TYPE_INTERMEDIATE(8),
    FORM_TYPE_FMDM(9),
    FORM_TYPE_ANALYSISREPORT(10),
    FORM_TYPE_NEWFMDM(11),
    FORM_TYPE_INSERTANALYSIS(12),
    FORM_TYPE_ACCOUNT(13);

    private int intValue;
    private static Map<Integer, FormType> mappings;

    private static Map<Integer, FormType> getMappings() {
        if (mappings == null) {
            mappings = Arrays.stream(FormType.values()).collect(Collectors.toMap(FormType::getValue, f -> f));
        }
        return mappings;
    }

    private FormType(int value) {
        this.intValue = value;
    }

    public int getValue() {
        return this.intValue;
    }

    public static FormType forValue(int value) {
        return FormType.getMappings().get(value);
    }
}

