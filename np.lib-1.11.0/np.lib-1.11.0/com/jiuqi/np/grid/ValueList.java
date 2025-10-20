/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Stream
 *  com.jiuqi.bi.util.StreamException
 */
package com.jiuqi.np.grid;

import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import com.jiuqi.np.grid.CellValue;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ValueList
implements Serializable {
    private static final long serialVersionUID = 5540008411199933820L;
    private List list = new ArrayList();
    private static final int MaxWordSign = 65535;

    public String get(int index) {
        return ((CellValue)this.list.get((int)index)).value;
    }

    public int add(CellValue cv) {
        this.list.add(cv);
        return this.list.size() - 1;
    }

    public int col(int index) {
        return ((CellValue)this.list.get((int)index)).col;
    }

    public int row(int index) {
        return ((CellValue)this.list.get((int)index)).row;
    }

    public int count() {
        return this.list.size();
    }

    public CellValue find(int col, int row) {
        for (int i = 0; i < this.list.size(); ++i) {
            CellValue cv = (CellValue)this.list.get(i);
            if (cv.col != col || cv.row != row) continue;
            return cv;
        }
        return null;
    }

    public void loadFromStream(Stream stream) throws StreamException {
        this.list.clear();
        int c = stream.readInt();
        int l = 0;
        for (int i = 0; i < c; ++i) {
            CellValue cv = new CellValue();
            cv.col = stream.readWord();
            cv.row = stream.readWord();
            l = stream.readWord();
            if (l == 65535) {
                l = stream.readInt();
            }
            cv.value = stream.readString(l);
            this.list.add(cv);
        }
    }

    public void saveToStream(Stream stream) throws StreamException {
        stream.writeInt(this.list.size());
        for (int i = 0; i < this.list.size(); ++i) {
            CellValue cv = (CellValue)this.list.get(i);
            stream.writeWord(cv.col);
            stream.writeWord(cv.row);
            if (cv.value != null && cv.value.length() > 0) {
                byte[] b = stream.encodeString(cv.value);
                if (b.length <= 65535) {
                    stream.writeWord(b.length);
                } else {
                    stream.writeWord(65535);
                    stream.writeInt(b.length);
                }
                stream.writeBuffer(b, 0, b.length);
                continue;
            }
            stream.writeWord(0);
        }
    }
}

