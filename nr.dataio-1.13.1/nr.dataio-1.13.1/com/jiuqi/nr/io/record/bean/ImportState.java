/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.io.record.bean;

public enum ImportState {
    WAITING(0),
    PROCESSING(1),
    CANCELLED(2),
    FINISHED(4),
    EXCEPTION(5),
    FAILED(6);

    private final int code;

    private ImportState(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public static ImportState getState(int code) {
        for (ImportState state : ImportState.values()) {
            if (state.getCode() != code) continue;
            return state;
        }
        return null;
    }
}

