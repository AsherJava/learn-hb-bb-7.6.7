/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.collection.PageArrayList
 */
package com.jiuqi.bi.bufgraph.storage.mem;

import com.jiuqi.bi.bufgraph.model.Node;
import com.jiuqi.bi.util.collection.PageArrayList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;

public class NodeList
implements Iterable<Node>,
RandomAccess {
    private List<Node> nodes;
    private static final int PAGE_SIZE = 4096;

    public NodeList(int size) {
        this.nodes = size <= 4096 ? new ArrayList<Node>(size) : new PageArrayList(4096, size);
    }

    public Node get(int nodeId) {
        return this.nodes.get(nodeId);
    }

    public int size() {
        return this.nodes.size();
    }

    public void add(Node node) {
        int v = node.getId();
        if (v >= 4096 && v >= this.nodes.size() && this.nodes instanceof ArrayList) {
            this.nodes = new PageArrayList(this.nodes);
        }
        if (v >= this.nodes.size()) {
            this.nodes.add(node);
        } else {
            this.nodes.set(node.getId(), node);
        }
    }

    @Override
    public Iterator<Node> iterator() {
        return this.nodes.iterator();
    }
}

