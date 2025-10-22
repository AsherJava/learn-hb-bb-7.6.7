/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.snapshot.exception;

import java.io.IOException;

public class SnapshotFileDownloadException
extends IOException {
    public SnapshotFileDownloadException(String message) {
        super(message);
    }

    public SnapshotFileDownloadException(String message, Throwable cause) {
        super(message, cause);
    }
}

