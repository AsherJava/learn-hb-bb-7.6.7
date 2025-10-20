/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.topo;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private Object bindingData;
    private List<Node> prevNodes = new ArrayList<Node>();
    private List<Node> nextNodes = new ArrayList<Node>();

    public Node(Object bindingData) {
        this.bindingData = bindingData;
    }

    public Object getBindingData() {
        return this.bindingData;
    }

    public List<Node> getPrevNodes() {
        return this.prevNodes;
    }

    public List<Node> getNextNodes() {
        return this.nextNodes;
    }

    public String toString() {
        return this.bindingData.toString();
    }
}

