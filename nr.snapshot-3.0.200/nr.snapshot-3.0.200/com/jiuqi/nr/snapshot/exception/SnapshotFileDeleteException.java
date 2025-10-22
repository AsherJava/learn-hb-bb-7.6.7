/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.snapshot.exception;

import java.io.IOException;

public class SnapshotFileDeleteException
extends IOException {
    public SnapshotFileDeleteException(String message) {
        super(message);
    }

    public SnapshotFileDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}

