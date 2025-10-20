/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.bufgraph.model;

import com.jiuqi.bi.bufgraph.model.Relationship;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Path {
    private List<Relationship> relations = new ArrayList<Relationship>();
    private Set<Integer> headNodeIds = new HashSet<Integer>();

    public void addRelationship(Relationship relationship) {
        this.relations.add(relationship);
        this.headNodeIds.add(relationship.getHeadNodeId());
    }

    public void insertRelationshipInHeader(Relationship relationship) {
        this.relations.add(0, relationship);
        this.headNodeIds.add(relationship.getHeadNodeId());
    }

    public int length() {
        return this.relations.size();
    }

    public Relationship getFirst() {
        return this.relations.isEmpty() ? null : this.relations.get(0);
    }

    public boolean contains(int nodeId) {
        if (this.headNodeIds.contains(nodeId)) {
            return true;
        }
        int last = this.getLast().getTailNodeId();
        return nodeId == last;
    }

    public Relationship get(int index) {
        return this.relations.get(index);
    }

    public Relationship getLast() {
        return this.relations.isEmpty() ? null : this.relations.get(this.relations.size() - 1);
    }

    public void reverse() {
        Collections.reverse(this.relations);
    }

    public int[] getNodeIdList() {
        int[] ids = new int[this.relations.size() + 1];
        int i = 0;
        for (Relationship r : this.relations) {
            ids[i++] = r.getHeadNodeId();
        }
        ids[ids.length - 1] = this.relations.get(this.relations.size() - 1).getTailNodeId();
        return ids;
    }

    public Path clone() {
        Path path = new Path();
        path.relations.addAll(this.relations);
        path.headNodeIds.addAll(this.headNodeIds);
        return path;
    }

    public void printf() {
        System.out.println(this.toString());
    }

    public String toString() {
        int i;
        StringBuilder b = new StringBuilder();
        for (i = 0; i < this.relations.size(); ++i) {
            if (i > 0) {
                b.append("->");
            }
            b.append(this.relations.get(i).getHeadNodeId());
            if (i != this.relations.size() - 1) continue;
            b.append("->").append(this.relations.get(i).getTailNodeId());
        }
        b.append("(");
        for (i = 0; i < this.relations.size(); ++i) {
            if (i > 0) {
                b.append("->");
            }
            b.append(this.relations.get(i).getId());
        }
        b.append(")");
        return b.toString();
    }
}

