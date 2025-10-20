/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.grid;

import java.io.Serializable;

public class CellField
implements Serializable,
Cloneable,
Comparable {
    private static final long serialVersionUID = -1114963564657625189L;
    public int left;
    public int top;
    public int right;
    public int bottom;
    public static final int FIELDS_EQUAL = 0;
    public static final int FIELDS_CONTAIN = 1;
    public static final int FIELDS_INSIDE = -1;
    public static final int FIELDS_EXCLUDE = 2;
    public static final int FIELDS_CROSS = -2;

    public CellField() {
    }

    public CellField(int l, int t, int r, int b) {
        this.left = l;
        this.top = t;
        this.right = r;
        this.bottom = b;
    }

    public CellField(CellField field) {
        this.left = field.left;
        this.top = field.top;
        this.right = field.right;
        this.bottom = field.bottom;
    }

    public int getArea() {
        return (this.right - this.left + 1) * (this.bottom - this.top + 1);
    }

    public boolean inField(int x, int y) {
        return x >= this.left && x <= this.right && y >= this.top && y <= this.bottom;
    }

    public CellField makeCopy() {
        return (CellField)this.clone();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CellField)) {
            return false;
        }
        CellField field = (CellField)o;
        return this.left == field.left && this.top == field.top && this.right == field.right && this.bottom == field.bottom;
    }

    public int hashCode() {
        return this.left << 24 ^ this.top << 16 ^ this.right << 8 ^ this.bottom;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer("[(");
        buffer.append(this.left);
        buffer.append(",");
        buffer.append(this.top);
        buffer.append("),(");
        buffer.append(this.right);
        buffer.append(",");
        buffer.append(this.bottom);
        buffer.append(")]");
        return buffer.toString();
    }

    public boolean containField(CellField field) {
        return this.left <= field.left && this.top <= field.top && this.right >= field.right && this.bottom >= field.bottom;
    }

    public boolean contains(int col, int row) {
        return this.left <= col && this.right >= col && this.top <= row && this.bottom >= row;
    }

    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public static int compareField(CellField field1, CellField field2) {
        if (field1.equals(field2)) {
            return 0;
        }
        if (field1.containField(field2)) {
            return 1;
        }
        if (field2.containField(field1)) {
            return -1;
        }
        if (field1.inField(field2.left, field2.top) || field1.inField(field2.right, field2.bottom)) {
            return -2;
        }
        if (field1.inField(field2.left, field2.bottom) || field1.inField(field2.right, field2.top)) {
            return -2;
        }
        return 2;
    }

    public int compareTo(Object o) {
        if (o == this) {
            return 0;
        }
        if (o == null) {
            return 1;
        }
        if (o instanceof CellField) {
            CellField f = (CellField)o;
            int retCode = this.left - f.left;
            if (retCode != 0) {
                return retCode;
            }
            retCode = this.top - f.top;
            if (retCode != 0) {
                return retCode;
            }
            retCode = this.right - f.right;
            if (retCode != 0) {
                return retCode;
            }
            return this.bottom - f.bottom;
        }
        return 1;
    }
}

