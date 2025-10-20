/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.office.excel;

import com.jiuqi.bi.grid.GridCell;

public final class CellFontKey {
    private byte[] data = new byte[6];
    private static final int DATA_START = 4;
    private static final int DATA_LEN = 6;
    private static final int STYLE_MASK = 15;

    public CellFontKey(GridCell cell) {
        System.arraycopy(cell.getPropData(), 4, this.data, 0, 6);
        this.data[5] = (byte)(this.data[5] & 0xF);
    }

    public int hashCode() {
        return this.data.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        byte[] objData = ((CellFontKey)obj).data;
        for (int i = 0; i < 6; ++i) {
            if (this.data[i] == objData[i]) continue;
            return false;
        }
        return true;
    }
}

