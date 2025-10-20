/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.collection.PageArrayList
 */
package com.jiuqi.bi.dataset.idx;

import com.jiuqi.bi.util.collection.PageArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class BMapTreeNode {
    private Object value;
    private Map<Object, BMapTreeNode> children;
    private List<Integer> rowIdxes;

    public BMapTreeNode(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return this.value;
    }

    public List<Integer> getRowIdxes() {
        return this.rowIdxes;
    }

    public BMapTreeNode findChild(Object value) {
        return this.children.get(value);
    }

    public BMapTreeNode addChildIfAbsent(Object value) {
        BMapTreeNode node;
        BMapTreeNode bMapTreeNode;
        if (this.children == null) {
            this.children = new HashMap<Object, BMapTreeNode>();
        }
        return (bMapTreeNode = this.children.putIfAbsent(value, node = new BMapTreeNode(value))) == null ? node : bMapTreeNode;
    }

    public BMapTreeNode addChildIfAbsent(Object value, int rowIdx) {
        BMapTreeNode node = this.addChildIfAbsent(value);
        if (node.rowIdxes == null) {
            node.rowIdxes = new PageArrayList();
        }
        node.rowIdxes.add(rowIdx);
        return node;
    }
}

