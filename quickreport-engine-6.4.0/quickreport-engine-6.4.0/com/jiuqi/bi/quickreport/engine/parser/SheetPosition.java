/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.cell.Cells
 *  com.jiuqi.bi.syntax.cell.Position
 */
package com.jiuqi.bi.quickreport.engine.parser;

import com.jiuqi.bi.syntax.cell.Cells;
import com.jiuqi.bi.syntax.cell.Position;
import java.io.Serializable;

public final class SheetPosition
implements Cloneable,
Comparable<SheetPosition>,
Serializable {
    private static final long serialVersionUID = 1L;
    private String sheetName;
    private Position position;

    public SheetPosition(String sheetName, Position position) {
        this.sheetName = sheetName;
        this.position = position;
    }

    public SheetPosition(String sheetName, int col, int row) {
        this(sheetName, Position.valueOf((int)col, (int)row));
    }

    public String getSheetName() {
        return this.sheetName;
    }

    public Position getPosition() {
        return this.position;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        Cells.printSheetName((String)this.sheetName, (StringBuilder)buffer);
        buffer.append('!').append(this.position);
        return buffer.toString();
    }

    @Override
    public int compareTo(SheetPosition pos) {
        int c = this.sheetName.compareTo(pos.sheetName);
        if (c != 0) {
            return c;
        }
        return this.position.compareTo(pos.position);
    }

    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public int hashCode() {
        return this.sheetName.hashCode() * 31 + this.position.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof SheetPosition)) {
            return false;
        }
        SheetPosition posObj = (SheetPosition)obj;
        return this.sheetName.equals(posObj.sheetName) && this.position.equals((Object)posObj.position);
    }
}

