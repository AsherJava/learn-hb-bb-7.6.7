/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Stream
 *  com.jiuqi.bi.util.StreamException
 */
package com.jiuqi.nr.single.core.para.parser.print;

import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import com.jiuqi.nr.single.core.para.parser.print.ColRowPair;
import com.jiuqi.nr.single.core.para.util.Util;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CellValueList {
    private Map<ColRowPair, String> FList = new HashMap<ColRowPair, String>();
    private Map<ColRowPair, String> FExtList = new HashMap<ColRowPair, String>();

    public final void add(ColRowPair colRow, String value) {
        if (colRow.getCol() > 65535 || colRow.getRow() > 65535) {
            this.FExtList.put(colRow, value);
        }
        this.FList.put(colRow, value);
    }

    public final void saveToStream(Stream writeStream) throws IOException, StreamException {
        int count = this.FList.size() - this.FExtList.size();
        writeStream.writeInt(count);
        for (ColRowPair pair : this.FList.keySet()) {
            byte[] b;
            short size;
            if (pair.getCol() > 65535 || pair.getRow() > 65535) continue;
            short col = (short)pair.getCol();
            short row = (short)pair.getRow();
            writeStream.writeShort(col);
            writeStream.writeShort(row);
            String s = this.FList.get(pair);
            if (s.length() >= -1) {
                writeStream.writeShort((short)-1);
                size = (short)s.length();
                writeStream.writeShort(size);
                b = Util.setString(s, size);
                writeStream.write(b, 0, b.length);
                continue;
            }
            size = (short)s.length();
            writeStream.writeShort(size);
            b = Util.setString(s, size);
            writeStream.write(b, 0, b.length);
        }
        count = this.FExtList.size();
        writeStream.writeInt(count);
        Iterator<ColRowPair> iterator = this.FExtList.keySet().iterator();
        while (iterator.hasNext()) {
            byte[] b;
            short size;
            ColRowPair Pair;
            ColRowPair colRowPair = Pair = iterator.next();
            int col = colRowPair.getCol();
            int row = colRowPair.getRow();
            writeStream.writeInt(col);
            writeStream.writeInt(row);
            String s = this.FExtList.get(Pair);
            if (s.length() >= -1) {
                writeStream.writeShort((short)-1);
                size = (short)s.length();
                writeStream.writeShort(size);
                b = Util.setString(s, size);
                writeStream.write(b, 0, b.length);
                continue;
            }
            size = (short)s.length();
            writeStream.writeShort(size);
            b = Util.setString(s, size);
            writeStream.write(b, 0, b.length);
        }
    }
}

