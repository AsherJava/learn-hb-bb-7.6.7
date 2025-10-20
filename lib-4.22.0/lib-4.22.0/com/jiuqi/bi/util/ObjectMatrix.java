/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util;

import com.jiuqi.bi.util.Matrix;

@Deprecated
public final class ObjectMatrix
extends Matrix {
    private static final long serialVersionUID = 1L;

    public ObjectMatrix() {
    }

    public ObjectMatrix(int colCount, int rowCount) {
        super(colCount, rowCount);
    }

    public Object get(int x, int y) {
        return this.getItem(x, y);
    }

    public void set(int x, int y, Object value) {
        this.setItem(x, y, value);
    }
}

