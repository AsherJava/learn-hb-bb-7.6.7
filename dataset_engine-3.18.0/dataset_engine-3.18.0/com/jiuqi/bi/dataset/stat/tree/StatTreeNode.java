/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.stat.tree;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.stat.tree.StatDataRecord;
import com.jiuqi.bi.dataset.stat.tree.TreeIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class StatTreeNode {
    private StatTreeNode parent;
    private List<StatTreeNode> children;
    private BIDataRow refDimDsRow;
    private List<StatDataRecord> dataRecords = new ArrayList<StatDataRecord>();
    private boolean tag;

    public StatTreeNode(BIDataRow refDimDsRow) {
        this.refDimDsRow = refDimDsRow;
        this.children = new ArrayList<StatTreeNode>();
    }

    public void setParent(StatTreeNode parent) {
        if (this.parent != null) {
            this.parent.children.remove(this);
        }
        this.parent = parent;
        if (parent != null) {
            parent.children.add(this);
        }
    }

    public boolean isLeaf() {
        return this.children.isEmpty();
    }

    public List<StatTreeNode> getChildren() {
        return this.children;
    }

    public StatTreeNode get(int index) {
        return this.children.get(index);
    }

    public int size() {
        return this.children.size();
    }

    public Iterator<StatTreeNode> iterator() {
        return new TreeIterator(this);
    }

    public StatTreeNode getParent() {
        return this.parent;
    }

    public BIDataRow getDataRow() {
        return this.refDimDsRow;
    }

    public List<StatDataRecord> getDataRecords() {
        return this.dataRecords;
    }

    public void setTag(boolean tag) {
        this.tag = tag;
    }

    public boolean getTag() {
        return this.tag;
    }

    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append("ref->").append(this.refDimDsRow);
        return b.toString();
    }
}

