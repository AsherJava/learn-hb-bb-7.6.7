/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.office.excel;

import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.office.excel.CellEdge;
import java.util.Arrays;
import java.util.Objects;

public final class CellStyleKey {
    private final GridCell cell;
    private final CellEdge.EdgeInfo[] edgeInfos;
    private final int backColor;
    private int hashCodeValue = -1;

    public CellStyleKey(GridCell cell, CellEdge.EdgeInfo[] edgeInfos) {
        this.cell = cell;
        this.edgeInfos = edgeInfos;
        this.backColor = cell.getBackColor();
    }

    public int hashCode() {
        if (this.hashCodeValue > 0) {
            return this.hashCodeValue;
        }
        int prime = 31;
        int result = 1;
        result = 31 * result + Arrays.hashCode(this.edgeInfos);
        this.hashCodeValue = result = 31 * result + Objects.hash(this.backColor, this.cell);
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        CellStyleKey other = (CellStyleKey)obj;
        return this.backColor == other.backColor && Objects.equals(this.cell, other.cell) && Arrays.equals(this.edgeInfos, other.edgeInfos);
    }
}

