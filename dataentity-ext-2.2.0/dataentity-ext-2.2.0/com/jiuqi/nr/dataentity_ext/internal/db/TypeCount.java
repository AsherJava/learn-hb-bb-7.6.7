/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentity_ext.internal.db;

public class TypeCount {
    private int type;
    private int count;

    public TypeCount(int type, int count) {
        this.type = type;
        this.count = count;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

