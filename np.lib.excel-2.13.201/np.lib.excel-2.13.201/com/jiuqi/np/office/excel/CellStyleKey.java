/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.grid.GridCell
 */
package com.jiuqi.np.office.excel;

import com.jiuqi.np.grid.GridCell;

public final class CellStyleKey {
    private GridCell cell;
    private int lc;
    private int ls;
    private int tc;
    private int ts;
    private int hashCodeValue = -1;

    public CellStyleKey(GridCell cell) {
        this.cell = cell;
        this.lc = cell.getLEdgeColor();
        this.ls = cell.getLEdgeStyle();
        this.tc = cell.getTEdgeColor();
        this.ts = cell.getTEdgeStyle();
    }

    public int hashCode() {
        if (this.hashCodeValue > 0) {
            return this.hashCodeValue;
        }
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.cell == null ? 0 : this.cell.hashCode());
        result = 31 * result + this.lc;
        result = 31 * result + this.ls;
        result = 31 * result + this.tc;
        this.hashCodeValue = result = 31 * result + this.ts;
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
        if (this.cell == null ? other.cell != null : !this.cell.equals((Object)other.cell)) {
            return false;
        }
        if (this.lc != other.lc) {
            return false;
        }
        if (this.ls != other.ls) {
            return false;
        }
        if (this.tc != other.tc) {
            return false;
        }
        return this.ts == other.ts;
    }
}

