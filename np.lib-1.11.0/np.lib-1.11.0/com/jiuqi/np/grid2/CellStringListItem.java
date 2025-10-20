/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.grid2;

import com.jiuqi.np.grid2.Stream2;
import com.jiuqi.np.grid2.StreamException2;

public class CellStringListItem {
    public int rowIndex;
    public int colIndex;
    public String string;

    public CellStringListItem() {
        this.string = "";
    }

    public CellStringListItem(int colIndex, int rowIndex, String string) {
        this.colIndex = colIndex;
        this.rowIndex = rowIndex;
        this.string = string;
    }

    public void loadFromStream(Stream2 stream) throws StreamException2 {
        this.colIndex = stream.readInt();
        this.rowIndex = stream.readInt();
        int length = stream.readInt();
        this.string = stream.readString(length);
    }

    public void saveToStream(Stream2 stream) throws StreamException2 {
        stream.writeInt(this.colIndex);
        stream.writeInt(this.rowIndex);
        byte[] data = stream.encodeString(this.string);
        stream.writeInt(data.length);
        stream.writeBuffer(data, 0, data.length);
    }
}

