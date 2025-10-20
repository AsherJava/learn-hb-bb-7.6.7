/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.bufgraph.storage;

import com.jiuqi.bi.bufgraph.GraphException;

public class GraphStorageException
extends GraphException {
    private static final long serialVersionUID = 1L;

    public GraphStorageException(String message) {
        super(message);
    }

    public GraphStorageException(String message, Throwable e) {
        super(message, e);
    }
}

