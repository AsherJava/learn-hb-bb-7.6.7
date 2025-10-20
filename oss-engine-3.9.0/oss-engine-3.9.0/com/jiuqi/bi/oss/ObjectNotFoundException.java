/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.oss;

import com.jiuqi.bi.oss.ObjectStorageException;

public class ObjectNotFoundException
extends ObjectStorageException {
    private static final long serialVersionUID = 1L;

    public ObjectNotFoundException(String message) {
        super(message, null);
    }
}

