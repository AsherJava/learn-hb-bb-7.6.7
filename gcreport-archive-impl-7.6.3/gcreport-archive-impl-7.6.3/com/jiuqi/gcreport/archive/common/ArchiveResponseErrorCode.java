/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.archive.common;

public enum ArchiveResponseErrorCode {
    SUCCESS(0),
    DUMP(1),
    DUMP_FAILED(2);

    private int errorCode;

    private ArchiveResponseErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return this.errorCode;
    }
}

