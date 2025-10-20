/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Stream
 *  com.jiuqi.bi.util.StreamException
 */
package com.jiuqi.bi.grid;

import com.jiuqi.bi.grid.CellValue;
import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class ValueList
implements Serializable {
    private static final long serialVersionUID = 5540008411199933820L;
    private List<CellValue> lowValues = new ArrayList<CellValue>();
    private List<CellValue> highValues = new ArrayList<CellValue>();
    private static final int MaxWordSign = 65535;

    public int add(CellValue cv) {
        if (ValueList.isLow(cv.col) && ValueList.isLow(cv.row)) {
            this.lowValues.add(cv);
            return this.lowValues.size() - 1;
        }
        this.highValues.add(cv);
        return this.count() - 1;
    }

    private static boolean isLow(int index) {
        return index >= 0 && index < 65535;
    }

    public int count() {
        return this.lowValues.size() + this.highValues.size();
    }

    public CellValue get(int index) {
        return index < this.lowValues.size() ? this.lowValues.get(index) : this.highValues.get(index - this.lowValues.size());
    }

    public CellValue find(int col, int row) {
        List<CellValue> values = ValueList.isLow(col) && ValueList.isLow(row) ? this.lowValues : this.highValues;
        for (int i = 0; i < values.size(); ++i) {
            CellValue cv = this.lowValues.get(i);
            if (cv.col != col || cv.row != row) continue;
            return cv;
        }
        return null;
    }

    public void loadLowFromStream(Stream stream) throws StreamException {
        this.lowValues.clear();
        int c = stream.readInt();
        for (int i = 0; i < c; ++i) {
            CellValue cv = new CellValue();
            cv.col = stream.readWord();
            cv.row = stream.readWord();
            cv.value = ValueList.readString(stream);
            this.lowValues.add(cv);
        }
    }

    public void loadHighFromStream(Stream stream) throws StreamException {
        this.highValues.clear();
        int c = stream.readInt();
        for (int i = 0; i < c; ++i) {
            CellValue cv = new CellValue();
            cv.col = stream.readInt();
            cv.row = stream.readInt();
            cv.value = ValueList.readString(stream);
            this.highValues.add(cv);
        }
    }

    public void saveLowToStream(Stream stream) throws StreamException {
        stream.writeInt(this.lowValues.size());
        for (int i = 0; i < this.lowValues.size(); ++i) {
            CellValue cv = this.lowValues.get(i);
            stream.writeWord(cv.col);
            stream.writeWord(cv.row);
            ValueList.writeString(stream, cv.value);
        }
    }

    public void saveHighToStream(Stream stream) throws StreamException {
        stream.writeInt(this.highValues.size());
        for (int i = 0; i < this.highValues.size(); ++i) {
            CellValue cv = this.highValues.get(i);
            stream.writeInt(cv.col);
            stream.writeInt(cv.row);
            ValueList.writeString(stream, cv.value);
        }
    }

    private static String readString(Stream stream) throws StreamException {
        int len = stream.readWord();
        if (len == 65535) {
            len = stream.readInt();
        }
        return stream.readString(len);
    }

    private static void writeString(Stream stream, String value) throws StreamException {
        if (value == null || value.isEmpty()) {
            stream.writeWord(0);
            return;
        }
        byte[] b = stream.encodeString(value);
        if (b.length < 65535) {
            stream.writeWord(b.length);
        } else {
            stream.writeWord(65535);
            stream.writeInt(b.length);
        }
        stream.writeBuffer(b, 0, b.length);
    }
}

