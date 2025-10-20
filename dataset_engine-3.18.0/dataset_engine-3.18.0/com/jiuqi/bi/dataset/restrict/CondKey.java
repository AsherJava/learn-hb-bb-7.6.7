/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.restrict;

public final class CondKey {
    private final Object node;
    private final Object[] filterValue;
    private int hash;

    public CondKey(Object node, Object[] filterValue) {
        this.node = node;
        this.filterValue = filterValue;
    }

    public int hashCode() {
        if (this.hash == 0) {
            this.hash += 100 * this.node.hashCode();
            for (Object obj : this.filterValue) {
                if (obj == null) {
                    ++this.hash;
                    continue;
                }
                this.hash += obj.hashCode();
            }
        }
        return this.hash;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof CondKey)) {
            return false;
        }
        CondKey k = (CondKey)obj;
        if (this.node != k.node) {
            return false;
        }
        if (this.filterValue.length != k.filterValue.length) {
            return false;
        }
        for (int i = 0; i < this.filterValue.length; ++i) {
            if (this.isEqual(this.filterValue[i], k.filterValue[i])) continue;
            return false;
        }
        return true;
    }

    private boolean isEqual(Object o1, Object o2) {
        if (o1 == null && o2 == null) {
            return true;
        }
        return o1 != null && o2 != null && o1.equals(o2);
    }
}

