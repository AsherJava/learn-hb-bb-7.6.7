/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.examine.common;

import java.util.HashMap;

public enum ExamineType {
    REFUSE(1, "\u5783\u573e\u53c2\u6570"),
    QUOTE(2, "\u9519\u8bef\u5f15\u7528"),
    ERROR(4, "\u9519\u8bef\u53c2\u6570");

    private int value;
    private String msg;
    private static HashMap<Integer, ExamineType> mappings;

    private ExamineType(int value, String msg) {
        this.value = value;
        this.msg = msg;
        ExamineType.getMappings().put(value, this);
    }

    public String getMsg() {
        return this.msg;
    }

    public int getValue() {
        return this.value;
    }

    private static synchronized HashMap<Integer, ExamineType> getMappings() {
        if (mappings == null) {
            mappings = new HashMap();
        }
        return mappings;
    }

    public static ExamineType forValue(int value) {
        return ExamineType.getMappings().get(value);
    }
}

