/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.idx;

import com.jiuqi.bi.dataset.BIDataSetImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class DSIndexEntry {
    protected BIDataSetImpl dataset;
    protected boolean hasBuild = false;
    protected Map<Object, ArrayList<Integer>> valueMap;

    public DSIndexEntry(BIDataSetImpl dataset) {
        this.dataset = dataset;
        this.valueMap = new HashMap<Object, ArrayList<Integer>>();
    }

    public synchronized void build() {
        if (this.hasBuild) {
            return;
        }
        int count = this.dataset.getRecordCount();
        for (int i = 0; i < count; ++i) {
            this.process(i, this.dataset.getRowData(i));
        }
        this.commit();
    }

    public List<Integer> search(Object data) {
        Object accepted = this.accept(data);
        ArrayList<Integer> colList = this.valueMap.get(accepted);
        if (colList == null) {
            return new ArrayList<Integer>(0);
        }
        return colList;
    }

    protected abstract Object accept(Object var1);

    protected abstract void process(int var1, Object[] var2);

    protected void commit() {
        this.hasBuild = true;
    }
}

