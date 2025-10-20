/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.digraph;

import com.jiuqi.bi.util.digraph.DGLink;
import java.util.ArrayList;
import java.util.List;

public class DGNode<T>
implements Comparable<DGNode<T>> {
    private T item;
    private Object tag;
    private List<DGLink<T>> depends;
    private List<DGLink<T>> affects;
    private int level;
    private int order;
    int _inDegrees;

    public DGNode() {
        this(null);
    }

    public DGNode(T item) {
        this.item = item;
        this.depends = new ArrayList<DGLink<T>>();
        this.affects = new ArrayList<DGLink<T>>();
        this.level = -1;
        this.order = -1;
    }

    public T get() {
        return this.item;
    }

    public void set(T item) {
        this.item = item;
    }

    public Object getTag() {
        return this.tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public List<DGLink<T>> getDepends() {
        return this.depends;
    }

    public List<DGLink<T>> getAffects() {
        return this.affects;
    }

    public int getOrder() {
        return this.order;
    }

    void setOrder(int order) {
        this.order = order;
    }

    public int getLevel() {
        return this.level;
    }

    void setLevel(int level) {
        this.level = level;
    }

    public String toString() {
        return this.item == null ? "null" : this.item.toString();
    }

    @Override
    public int compareTo(DGNode<T> o) {
        return this.order - o.order;
    }
}

