/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.formcopy.common;

import java.util.HashMap;
import java.util.Map;

public enum FormCopyRecordType {
    NOT_ALL(0, "\u6ca1\u6709\u590d\u5236\u8fc7"),
    PULL_RECORD(1, "\u62c9\u53d6\u590d\u5236\u8bb0\u5f55"),
    PUSH_RECORD(2, "\u63a8\u9001\u590d\u5236\u8bb0\u5f55"),
    PULL_AND_PUSH_RECORD(3, "\u62c9\u53bb\u548c\u63a8\u9001\u590d\u5236");

    private final int value;
    private final String title;
    private static final Map<Integer, FormCopyRecordType> valueMap;

    private FormCopyRecordType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public static FormCopyRecordType valueOf(int value) {
        return valueMap.get(value);
    }

    static {
        valueMap = new HashMap<Integer, FormCopyRecordType>();
        for (FormCopyRecordType type : FormCopyRecordType.values()) {
            valueMap.put(type.getValue(), type);
        }
    }
}

