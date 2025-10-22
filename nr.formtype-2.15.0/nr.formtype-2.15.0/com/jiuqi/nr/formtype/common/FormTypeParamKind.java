/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 */
package com.jiuqi.nr.formtype.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.HashMap;
import java.util.Map;

public enum FormTypeParamKind {
    GROUP(0),
    FORMTYPE(1),
    FORMTYPEDATA(2);

    private int value;
    private static final Map<Integer, FormTypeParamKind> ALL_KIND;

    private FormTypeParamKind(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    @JsonCreator
    public static FormTypeParamKind valueOf(int value) {
        return ALL_KIND.get(value);
    }

    static {
        ALL_KIND = new HashMap<Integer, FormTypeParamKind>();
        for (FormTypeParamKind kind : FormTypeParamKind.values()) {
            ALL_KIND.put(kind.getValue(), kind);
        }
    }
}

