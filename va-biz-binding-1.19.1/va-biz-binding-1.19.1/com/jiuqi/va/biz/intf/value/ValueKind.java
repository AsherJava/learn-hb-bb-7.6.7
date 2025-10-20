/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.value;

import java.lang.reflect.Array;
import java.util.Collection;

public enum ValueKind {
    SINGLE,
    RANGE,
    LIST,
    RANGE_GREATER,
    RANGE_LESSER;


    public static ValueKind test(Object value) {
        return value == null ? null : (value.getClass().isArray() ? (Array.get(value, 0) == null ? RANGE_LESSER : (Array.get(value, 1) == null ? RANGE_GREATER : RANGE)) : (value instanceof Collection && ((Collection)value).size() != 1 ? LIST : SINGLE));
    }
}

