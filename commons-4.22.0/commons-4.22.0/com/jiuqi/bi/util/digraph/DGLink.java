/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.digraph;

import com.jiuqi.bi.util.digraph.DGNode;

public class DGLink<T> {
    private DGNode<T> initial;
    private DGNode<T> terminal;
    private Object tag;

    public DGLink() {
    }

    public DGLink(DGNode<T> initial, DGNode<T> terminal) {
        this.initial = initial;
        this.terminal = terminal;
    }

    public DGNode<T> getInitial() {
        return this.initial;
    }

    public void setInitial(DGNode<T> initial) {
        this.initial = initial;
    }

    public DGNode<T> getTerminal() {
        return this.terminal;
    }

    public void setTerminal(DGNode<T> terminal) {
        this.terminal = terminal;
    }

    public Object getTag() {
        return this.tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }
}

