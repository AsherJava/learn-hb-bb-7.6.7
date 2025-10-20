/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.grid2;

import java.io.Serializable;

public class Grid2CellField
implements Serializable {
    private static final long serialVersionUID = 5950761537632495633L;
    public int left;
    public int top;
    public int right;
    public int bottom;

    public Grid2CellField() {
    }

    public Grid2CellField(int l, int t, int r, int b) {
        this.left = l;
        this.top = t;
        this.right = r;
        this.bottom = b;
    }

    public Grid2CellField(Grid2CellField field) {
        this.left = field.left;
        this.top = field.top;
        this.right = field.right;
        this.bottom = field.bottom;
    }
}

