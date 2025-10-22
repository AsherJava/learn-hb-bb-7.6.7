/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.snapshot.exception;

import java.io.IOException;

public class SnapshotFileUploadException
extends IOException {
    public SnapshotFileUploadException(String message) {
        super(message);
    }

    public SnapshotFileUploadException(String message, Throwable cause) {
        super(message, cause);
    }
}

