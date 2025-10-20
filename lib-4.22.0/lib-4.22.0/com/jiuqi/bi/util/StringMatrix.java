/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util;

import com.jiuqi.bi.util.Matrix;

@Deprecated
public class StringMatrix
extends Matrix {
    private static final long serialVersionUID = 1L;

    public StringMatrix() {
    }

    public StringMatrix(int colCount, int rowCount) {
        super(colCount, rowCount);
    }

    public String get(int x, int y) {
        return (String)this.getItem(x, y);
    }

    public void set(int x, int y, String value) {
        this.setItem(x, y, value);
    }
}

