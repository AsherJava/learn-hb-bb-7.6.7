/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.idx.tree;

import java.util.ArrayList;
import java.util.List;

public class IndexItem {
    private List<Integer> rowIndeies = new ArrayList<Integer>();

    List<Integer> getRowIndexs() {
        return this.rowIndeies;
    }

    public int getFirstRowIndex() {
        if (this.rowIndeies.size() > 0) {
            return this.rowIndeies.get(0);
        }
        return -1;
    }

    public Integer[] getAllIndex() {
        return this.rowIndeies.toArray(new Integer[this.rowIndeies.size()]);
    }
}

