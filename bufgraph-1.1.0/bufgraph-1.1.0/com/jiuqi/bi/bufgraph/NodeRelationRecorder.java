/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.collection.PageArrayList
 */
package com.jiuqi.bi.bufgraph;

import com.jiuqi.bi.bufgraph.IntValue;
import com.jiuqi.bi.util.collection.PageArrayList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class NodeRelationRecorder {
    private List<List<Integer>> _incomingRels;
    private List<List<Integer>> _outgoingRels;

    NodeRelationRecorder(int size) {
        if (size < 4096) {
            this._incomingRels = new ArrayList<List<Integer>>(size);
            this._outgoingRels = new ArrayList<List<Integer>>(size);
        } else {
            this._incomingRels = new PageArrayList(4096, size);
            this._outgoingRels = new PageArrayList(4096, size);
        }
        for (int i = 0; i < size; ++i) {
            this._incomingRels.add(null);
            this._outgoingRels.add(null);
        }
    }

    NodeRelationRecorder() {
        this._incomingRels = new PageArrayList();
        this._outgoingRels = new PageArrayList();
    }

    List<Integer> getIncomingRels(int nodeId) {
        return nodeId >= this._incomingRels.size() ? null : this._incomingRels.get(nodeId);
    }

    List<Integer> getOutgoingRels(int nodeId) {
        return nodeId >= this._outgoingRels.size() ? null : this._outgoingRels.get(nodeId);
    }

    void addIncomingRel(int nodeId, int relId) {
        int len = this._incomingRels.size();
        if (len <= nodeId) {
            for (int i = len; i <= nodeId; ++i) {
                this._incomingRels.add(null);
            }
        }
        if (this._incomingRels.get(nodeId) == null) {
            this._incomingRels.set(nodeId, new ArrayList());
        }
        this._incomingRels.get(nodeId).add(relId);
    }

    void addOutgoingRel(int nodeId, int relId) {
        int len = this._outgoingRels.size();
        if (len <= nodeId) {
            for (int i = len; i <= nodeId; ++i) {
                this._outgoingRels.add(null);
            }
        }
        if (this._outgoingRels.get(nodeId) == null) {
            this._outgoingRels.set(nodeId, new ArrayList());
        }
        this._outgoingRels.get(nodeId).add(relId);
    }

    Iterator<IntValue> getIncomingNodeIds() {
        return new NodeIdIterator(this._incomingRels);
    }

    Iterator<IntValue> getOutgoingNodeIds() {
        return new NodeIdIterator(this._outgoingRels);
    }

    void clearRelationInfos() {
        this._incomingRels = null;
        this._outgoingRels = null;
    }

    private class NodeIdIterator
    implements Iterator<IntValue> {
        private List<List<Integer>> rels;
        private int pos;
        private IntValue value;

        public NodeIdIterator(List<List<Integer>> rels) {
            this.rels = rels;
            this.value = new IntValue(0);
        }

        @Override
        public boolean hasNext() {
            for (int i = this.pos; i < this.rels.size(); ++i) {
                if (this.rels.get(i) == null) continue;
                this.pos = i;
                return true;
            }
            return false;
        }

        @Override
        public IntValue next() {
            this.value.setValue(this.pos);
            ++this.pos;
            return this.value;
        }
    }
}

