/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.loader;

public class Record {
    private Object[] buf;

    public Record(int fieldSize) {
        this.buf = new Object[fieldSize];
    }

    public Record(Object[] buf) {
        this.buf = buf;
    }

    public Object get(int index) {
        return this.buf[index];
    }

    public void set(int index, Object value) {
        this.buf[index] = value;
    }
}

