/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.examine.common;

import java.util.HashMap;

public enum RestoreStatus {
    UNRESTORE(0),
    SUCCESS(1),
    MARKSUCCESS(2),
    IGNORE(3),
    FAILED(4);

    private int status;
    private static HashMap<Integer, RestoreStatus> mappings;

    private RestoreStatus(int status) {
        this.status = status;
        RestoreStatus.getMappings().put(status, this);
    }

    private static synchronized HashMap<Integer, RestoreStatus> getMappings() {
        if (mappings == null) {
            mappings = new HashMap();
        }
        return mappings;
    }

    public int getValue() {
        return this.status;
    }

    public static RestoreStatus forValue(int value) {
        return RestoreStatus.getMappings().get(value);
    }
}

