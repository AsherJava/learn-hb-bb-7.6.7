/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.idx.tree;

public final class IndexKey {
    private String[] columnNames;

    public IndexKey(String[] columns) {
        this.columnNames = columns;
    }

    public boolean isCompatible(IndexKey key) {
        int len = Math.min(key.columnNames.length, this.columnNames.length);
        for (int i = 0; i < len; ++i) {
            if (this.columnNames[i].equals(key.columnNames[i])) continue;
            return false;
        }
        return true;
    }

    public int size() {
        return this.columnNames.length;
    }

    public boolean equals(Object obj) {
        if (obj instanceof IndexKey) {
            IndexKey o = (IndexKey)obj;
            if (this.columnNames.length != o.columnNames.length) {
                return false;
            }
            for (int i = 0; i < this.columnNames.length; ++i) {
                if (this.columnNames[i].equals(o.columnNames[i])) continue;
                return false;
            }
            return true;
        }
        return super.equals(obj);
    }

    public int hashCode() {
        int hash = 0;
        for (int i = 0; i < this.columnNames.length; ++i) {
            hash += i * 10 + this.columnNames[i].hashCode();
        }
        return hash;
    }
}

