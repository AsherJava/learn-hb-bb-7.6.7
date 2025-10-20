/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.check;

public class OffsetItemCheckResult {
    private final boolean success;
    private final String message;

    public OffsetItemCheckResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static OffsetItemCheckResult success() {
        return new OffsetItemCheckResult(true, null);
    }

    public static OffsetItemCheckResult error(String message) {
        return new OffsetItemCheckResult(false, message);
    }

    public boolean isSuccess() {
        return this.success;
    }

    public String getMessage() {
        return this.message;
    }
}

