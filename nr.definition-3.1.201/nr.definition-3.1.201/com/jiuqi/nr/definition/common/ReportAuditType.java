/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.common;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum ReportAuditType {
    NONE(0),
    CONVERSION(1),
    ESCALATION(2),
    CUSTOM(3);

    private int intValue;
    private static Map<Integer, ReportAuditType> mappings;

    private static Map<Integer, ReportAuditType> getMappings() {
        if (mappings == null) {
            mappings = Arrays.stream(ReportAuditType.values()).collect(Collectors.toMap(ReportAuditType::getValue, f -> f));
        }
        return mappings;
    }

    private ReportAuditType(int value) {
        this.intValue = value;
    }

    public int getValue() {
        return this.intValue;
    }

    public static ReportAuditType forValue(int value) {
        return ReportAuditType.getMappings().get(value);
    }
}

