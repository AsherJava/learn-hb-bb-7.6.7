/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.script.facade;

import com.jiuqi.nr.single.core.script.facade.TPSList;

public class TPSStringList {
    private TPSList list;

    public TPSList getList() {
        if (this.list == null) {
            this.list = new TPSList();
        }
        return this.list;
    }

    public int getCount() {
        return this.getList().getCount();
    }

    public void add(String p) {
        this.getList().add(p);
    }

    public void delete(int index) {
        this.getList().delete(index);
    }

    public void clear() {
        this.getList().clear();
    }
}

