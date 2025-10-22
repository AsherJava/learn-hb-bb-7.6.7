/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.snapshot.exception;

import java.io.IOException;

public class SnapshotFileNotFoundException
extends IOException {
    private final String fileKey;

    public SnapshotFileNotFoundException(String fileKey) {
        super("file '" + fileKey + "' not found.");
        this.fileKey = fileKey;
    }

    public String getFileKey() {
        return this.fileKey;
    }
}

