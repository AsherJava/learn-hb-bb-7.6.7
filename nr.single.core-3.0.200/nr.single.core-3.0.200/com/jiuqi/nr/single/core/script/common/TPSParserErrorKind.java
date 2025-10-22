/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.script.common;

import java.util.HashMap;

public enum TPSParserErrorKind {
    INO_EORROR(0),
    ICOMMENT_ERROR(1),
    ISTRING_ERROR(2),
    ICHAR_ERROR(3),
    ISYNTAX_ERROR(4);

    private int intValue;
    private static HashMap<Integer, TPSParserErrorKind> mappings;

    private static synchronized HashMap<Integer, TPSParserErrorKind> getMappings() {
        if (mappings == null) {
            mappings = new HashMap();
        }
        return mappings;
    }

    private TPSParserErrorKind(int value) {
        this.intValue = value;
        TPSParserErrorKind.getMappings().put(value, this);
    }

    public int getValue() {
        return this.intValue;
    }

    public static TPSParserErrorKind forValue(int value) {
        return TPSParserErrorKind.getMappings().get(value);
    }
}

