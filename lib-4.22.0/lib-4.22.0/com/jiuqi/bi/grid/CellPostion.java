/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.grid;

import java.io.Serializable;

public class CellPostion
implements Serializable,
Cloneable,
Comparable<CellPostion> {
    private static final long serialVersionUID = 1500L;
    private final int col;
    private final int row;
    private static final char[] CHAR_MAP;
    private static final String[] COL_CACHE;

    public CellPostion(int aCol, int aRow) {
        this.col = aCol;
        this.row = aRow;
    }

    public CellPostion(String posStr) {
        int[] posVal = CellPostion.parsePos(posStr);
        this.col = posVal[0];
        this.row = posVal[1];
    }

    public CellPostion(CellPostion pos) {
        this.col = pos.col;
        this.row = pos.row;
    }

    public int getCol() {
        return this.col;
    }

    public int getRow() {
        return this.row;
    }

    public final int col() {
        return this.col;
    }

    public final int row() {
        return this.row;
    }

    private static void colToString(int col, StringBuffer buffer) {
        int p = buffer.length();
        while (col > 0) {
            int r = col % 26;
            col /= 26;
            buffer.insert(p, CHAR_MAP[r]);
            if (r != 0) continue;
            --col;
        }
    }

    public static void nameOfCol(int col, StringBuffer buffer) {
        if (col < 256) {
            buffer.append(COL_CACHE[col]);
        } else {
            CellPostion.colToString(col, buffer);
        }
    }

    public static String nameOfCol(int col) {
        if (col < 256) {
            return COL_CACHE[col];
        }
        StringBuffer buffer = new StringBuffer(4);
        CellPostion.colToString(col, buffer);
        return buffer.toString();
    }

    public static int valueOfCol(String colStr) {
        int v = 0;
        for (int i = 0; i < colStr.length(); ++i) {
            char ch = colStr.charAt(i);
            if (ch >= 'A' && ch <= 'Z') {
                v = v * 26 + (byte)ch - 65 + 1;
                continue;
            }
            if (ch >= 'a' && ch <= 'z') {
                v = v * 26 + (byte)ch - 97 + 1;
                continue;
            }
            throw new NumberFormatException("\u9519\u8bef\u7684\u5217\u53f7\u540d\u79f0\uff1a" + colStr);
        }
        return v;
    }

    public int hashCode() {
        return this.col * 31 + this.row;
    }

    public String toString() {
        return CellPostion.toString(this.col, this.row);
    }

    public static String toString(int col, int row) {
        StringBuffer buffer = new StringBuffer(8);
        CellPostion.nameOfCol(col, buffer);
        buffer.append(row);
        return buffer.toString();
    }

    public static int[] parsePos(String posStr) {
        int[] p = new int[]{0, 0};
        int c = 0;
        for (int i = 0; i < posStr.length(); ++i) {
            char ch = posStr.charAt(i);
            if (ch >= 'A' && ch <= 'Z') {
                p[0] = p[0] * 26 + (byte)ch - 65 + 1;
                continue;
            }
            if (ch >= '0' && ch <= '9') {
                p[1] = p[1] * 10 + (byte)ch - 48;
                continue;
            }
            if (ch >= 'a' && ch <= 'z') {
                p[0] = p[0] * 26 + (byte)ch - 97 + 1;
                continue;
            }
            if (ch == '$') {
                switch (c) {
                    case 0: {
                        if (p[1] <= 0) break;
                        throw new NumberFormatException("\u9519\u8bef\u7684\u5355\u5143\u683c\u5750\u6807\u4f4d\u7f6e\uff1a" + posStr);
                    }
                    case 1: {
                        if (p[0] != 0 && p[1] <= 0) break;
                        throw new NumberFormatException("\u9519\u8bef\u7684\u5355\u5143\u683c\u5750\u6807\u4f4d\u7f6e\uff1a" + posStr);
                    }
                    default: {
                        throw new NumberFormatException("\u9519\u8bef\u7684\u5355\u5143\u683c\u5750\u6807\u4f4d\u7f6e\uff1a" + posStr);
                    }
                }
                ++c;
                continue;
            }
            throw new NumberFormatException("\u9519\u8bef\u7684\u5355\u5143\u683c\u5750\u6807\u4f4d\u7f6e\uff1a" + posStr);
        }
        return p;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof CellPostion)) {
            return false;
        }
        return this.col == ((CellPostion)obj).col && this.row == ((CellPostion)obj).row;
    }

    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }

    @Override
    public int compareTo(CellPostion pos) {
        if (this.row != pos.row) {
            return this.row - pos.row;
        }
        if (this.col != pos.col) {
            return this.col - pos.col;
        }
        return 0;
    }

    static {
        int i;
        CHAR_MAP = new char[26];
        CellPostion.CHAR_MAP[0] = 90;
        for (i = 1; i < 26; ++i) {
            CellPostion.CHAR_MAP[i] = (char)(65 + i - 1);
        }
        COL_CACHE = new String[256];
        for (i = 0; i < 256; ++i) {
            StringBuffer buffer = new StringBuffer(4);
            CellPostion.colToString(i, buffer);
            CellPostion.COL_CACHE[i] = buffer.toString();
        }
    }
}

