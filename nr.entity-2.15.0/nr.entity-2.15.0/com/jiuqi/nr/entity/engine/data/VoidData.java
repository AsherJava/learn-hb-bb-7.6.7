/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.engine.data;

import com.jiuqi.nr.entity.engine.data.AbstractData;

public final class VoidData
extends AbstractData {
    private static final long serialVersionUID = -2992491744149928789L;
    public static final VoidData VOID = new VoidData(true);

    public VoidData() {
        this(false);
    }

    private VoidData(boolean isNull) {
        super(-2, isNull);
    }

    @Override
    public String getAsString() {
        return "VOID";
    }

    public int hashCode() {
        return this.isNull ? 32 : 64;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        return obj instanceof VoidData;
    }
}

