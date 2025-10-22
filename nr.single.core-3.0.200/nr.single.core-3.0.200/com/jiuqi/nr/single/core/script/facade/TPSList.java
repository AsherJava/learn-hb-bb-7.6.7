/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.script.facade;

import java.util.ArrayList;
import java.util.List;

public class TPSList {
    private List<Object> data;

    public List<Object> getData() {
        if (this.data == null) {
            this.data = new ArrayList<Object>();
        }
        return this.data;
    }

    public int getIndexOf(Object p) {
        return this.getData().indexOf(p);
    }

    public int getCount() {
        return this.getData().size();
    }

    public Object getItem(int index) {
        return this.getData().get(index);
    }

    public void setItem(int index, Object p) {
        if (this.getCount() == 0 || index >= this.getCount()) {
            return;
        }
        this.getData().set(index, p);
    }

    public int add(Object p) {
        this.getData().add(p);
        return this.getData().size();
    }

    public void addBlock(List<Object> list, int count) {
        for (int i = 0; i < count - 1; ++i) {
            this.getData().add(list.get(i));
        }
    }

    public void remove(Object p) {
        if (this.getData().size() == 0) {
            return;
        }
        int id = this.data.indexOf(p);
        if (id >= 0) {
            this.data.remove(id);
        }
    }

    public void delete(int index) {
        if (this.getData().size() == 0) {
            return;
        }
        if (this.data.size() > index) {
            this.data.remove(index);
        }
    }

    public void deleteLast() {
        if (this.getData().size() == 0) {
            return;
        }
        this.data.remove(this.data.size() - 1);
    }

    public void clear() {
        this.getData().clear();
    }
}

