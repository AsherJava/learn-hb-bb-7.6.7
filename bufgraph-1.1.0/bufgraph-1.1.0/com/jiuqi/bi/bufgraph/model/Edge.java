/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.bufgraph.model;

import com.jiuqi.bi.bufgraph.model.Entity;

public class Edge
extends Entity {
    private static final long serialVersionUID = 1L;
    private String type;
    private String head;
    private String tail;

    public Edge(String uid, String head, String tail, String type) {
        super(0, uid);
        this.head = head;
        this.tail = tail;
        this.type = type;
    }

    public String getHead() {
        return this.head;
    }

    public String getTail() {
        return this.tail;
    }

    public String getType() {
        return this.type;
    }

    public String toString() {
        return this.head + "->" + this.tail;
    }

    public int hashCode() {
        return this.head.hashCode() + this.tail.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof Edge) {
            Edge ed = (Edge)obj;
            return ed.head.equals(this.head) && ed.tail.equals(this.tail);
        }
        return false;
    }
}

