/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.bufgraph.model;

import java.io.Serializable;

public final class RelationshipType
implements Serializable {
    private static final long serialVersionUID = 1L;
    private byte type;
    private transient String title;

    public RelationshipType(byte type, String title) {
        this.type = type;
        this.title = title;
    }

    public byte type() {
        return this.type;
    }

    public String title() {
        return this.title;
    }

    public String toString() {
        return this.title;
    }
}

