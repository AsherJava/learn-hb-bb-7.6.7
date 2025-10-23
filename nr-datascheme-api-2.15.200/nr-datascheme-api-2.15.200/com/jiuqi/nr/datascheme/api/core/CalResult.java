/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api.core;

public enum CalResult {
    SUCCESS(0, "\u6210\u529f"),
    FAIL(1, "\u5931\u8d25");

    private final int code;
    private final String message;

    private CalResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public static CalResult forValue(int code) {
        for (CalResult result : CalResult.values()) {
            if (result.getCode() != code) continue;
            return result;
        }
        return null;
    }
}

