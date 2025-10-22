/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.examine.common;

import java.util.HashMap;

public enum ExamineStatus {
    CHECK(0),
    INTERRUPT(1),
    FINISH(2);

    private int status;
    private static HashMap<Integer, ExamineStatus> mappings;

    private ExamineStatus(int status) {
        this.status = status;
        ExamineStatus.getMappings().put(status, this);
    }

    private static synchronized HashMap<Integer, ExamineStatus> getMappings() {
        if (mappings == null) {
            mappings = new HashMap();
        }
        return mappings;
    }

    public int getValue() {
        return this.status;
    }

    public static ExamineStatus forValue(int value) {
        return ExamineStatus.getMappings().get(value);
    }
}

