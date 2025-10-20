/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.oss;

import com.jiuqi.bi.oss.ObjectStorageException;

public class ObjectStorageMetadataException
extends ObjectStorageException {
    private static final long serialVersionUID = 1L;

    public ObjectStorageMetadataException(String message) {
        super(message);
    }

    public ObjectStorageMetadataException(String message, Throwable e) {
        super(message, e);
    }
}

