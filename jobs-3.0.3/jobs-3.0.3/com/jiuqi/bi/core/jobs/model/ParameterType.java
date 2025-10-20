/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.model;

public enum ParameterType {
    BOOLEAN,
    INTEGER,
    STRING,
    DOUBLE,
    BIGDECIMAL,
    DATETIME;


    public static ParameterType typeOf(String name) {
        if (BOOLEAN.name().equalsIgnoreCase(name)) {
            return BOOLEAN;
        }
        if (INTEGER.name().equalsIgnoreCase(name)) {
            return INTEGER;
        }
        if (STRING.name().equalsIgnoreCase(name)) {
            return STRING;
        }
        if (DOUBLE.name().equalsIgnoreCase(name)) {
            return DOUBLE;
        }
        if (BIGDECIMAL.name().equalsIgnoreCase(name)) {
            return BIGDECIMAL;
        }
        if (DATETIME.name().equalsIgnoreCase(name)) {
            return DATETIME;
        }
        return STRING;
    }
}

