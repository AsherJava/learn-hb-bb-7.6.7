/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.io.BIFFReader
 *  com.jiuqi.bi.io.BIFFWriter
 *  com.jiuqi.bi.io.IBIFFInput
 */
package com.jiuqi.bi.quickreport.engine.result;

import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.io.BIFFReader;
import com.jiuqi.bi.io.BIFFWriter;
import com.jiuqi.bi.io.IBIFFInput;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class WritebackData
implements Iterable<Map.Entry<String, MemoryDataSet<?>>> {
    private Map<String, MemoryDataSet<?>> datas = new HashMap();
    private static final byte BIFF_SHEETNAME = 17;
    private static final byte BIFF_SHEETDATA = 18;

    public void put(String sheetName, MemoryDataSet<?> dataSet) {
        this.datas.put(sheetName, dataSet);
    }

    public MemoryDataSet<?> get(String sheetName) {
        return this.datas.get(sheetName);
    }

    public boolean isEmpty() {
        return this.datas.isEmpty();
    }

    @Override
    public Iterator<Map.Entry<String, MemoryDataSet<?>>> iterator() {
        return this.datas.entrySet().iterator();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void save(OutputStream outStream) throws IOException {
        try (BIFFWriter writer = new BIFFWriter(outStream);){
            for (Map.Entry<String, MemoryDataSet<?>> entry : this.datas.entrySet()) {
                writer.write((byte)17, entry.getKey());
                byte[] dsData = entry.getValue().save();
                writer.write((byte)18, dsData);
            }
        }
    }

    public void load(InputStream inStream) throws IOException {
        this.datas.clear();
        String sheetName = null;
        BIFFReader reader = new BIFFReader(inStream);
        while (reader.hasNext()) {
            IBIFFInput in = reader.next();
            switch (in.sign()) {
                case 17: {
                    sheetName = in.readString();
                    break;
                }
                case 18: {
                    byte[] dsData = in.readBytes();
                    MemoryDataSet dataSet = new MemoryDataSet();
                    dataSet.load(dsData);
                    this.datas.put(sheetName, dataSet);
                    sheetName = null;
                    break;
                }
            }
        }
    }
}

