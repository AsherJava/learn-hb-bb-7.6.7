/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataSet
 */
package com.jiuqi.bi.quickreport.engine.context.cache;

import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.quickreport.engine.context.cache.DSFilterKey;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

final class DataSetCache {
    private Queue<DSFilterKey> keys = new LinkedList<DSFilterKey>();
    private Map<DSFilterKey, BIDataSet> finder = new HashMap<DSFilterKey, BIDataSet>();
    private int bufSize;
    public static final int DEFAULT_BUFFER_SIZE = 32;

    public DataSetCache(int bufSize) {
        this.bufSize = bufSize;
    }

    public DataSetCache() {
        this(32);
    }

    public void put(DSFilterKey key, BIDataSet dataset) {
        this.keys.offer(key);
        this.finder.put(key, dataset);
        if (this.keys.size() >= this.bufSize) {
            DSFilterKey tmpKey = this.keys.poll();
            this.finder.remove(tmpKey);
        }
    }

    public BIDataSet get(DSFilterKey key) {
        return this.finder.get(key);
    }

    public void remove(String dataSetName) {
        Iterator i = this.keys.iterator();
        while (i.hasNext()) {
            DSFilterKey key = (DSFilterKey)i.next();
            if (!key.getDataSetName().equalsIgnoreCase(dataSetName) && !key.isJoined(dataSetName)) continue;
            i.remove();
            this.finder.remove(key);
        }
    }

    public void clear() {
        this.keys.clear();
        this.finder.clear();
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder("[");
        Iterator i = this.keys.iterator();
        while (i.hasNext()) {
            DSFilterKey key = (DSFilterKey)i.next();
            buffer.append(key);
            BIDataSet dataset = this.finder.get(key);
            if (dataset != null) {
                buffer.append('=').append(dataset.getRecordCount());
            }
            if (!i.hasNext()) continue;
            buffer.append(", ");
        }
        buffer.append("]");
        return buffer.toString();
    }
}

