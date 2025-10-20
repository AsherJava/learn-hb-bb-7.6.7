/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.org.impl.tablepaste.vo;

import java.util.Date;
import java.util.UUID;

public enum ColumnValueType {
    OBJECT(Object.class, "OBJECT"),
    BOOLEAN(Boolean.class, "BOOLEAN"),
    INTEGER(Integer.class, "INTEGER"),
    DOUBLE(Double.class, "DOUBLE"),
    DATE(Date.class, "DATE"),
    IDENTIFY(UUID.class, "IDENTIFY"),
    STRING(String.class, "STRING");

    private Class<?> valueClass;
    private String code;

    private ColumnValueType(Class<?> valueClass, String code) {
        this.valueClass = valueClass;
        this.code = code;
    }
}

