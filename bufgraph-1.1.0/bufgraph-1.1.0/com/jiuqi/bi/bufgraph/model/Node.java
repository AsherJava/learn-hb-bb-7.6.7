/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.bufgraph.model;

import com.jiuqi.bi.bufgraph.model.Direction;
import com.jiuqi.bi.bufgraph.model.Entity;
import com.jiuqi.bi.bufgraph.model.NodeType;

public final class Node
extends Entity {
    private static final long serialVersionUID = 1L;
    private String title;
    private NodeType type;
    private int firstin_rel_id = -1;
    private int firstout_rel_id = -1;

    public Node(int id, String uid, String title, NodeType type) {
        super(id, uid);
        this.title = title;
        this.type = type;
    }

    public String getTitle() {
        return this.title;
    }

    public String getType() {
        return this.type.title();
    }

    public byte getTypeSign() {
        return this.type.type();
    }

    public void setFirstRelationshipId(Direction direction, int rel_id) {
        if (direction == Direction.INCOMING) {
            this.firstin_rel_id = rel_id;
        } else {
            this.firstout_rel_id = rel_id;
        }
    }

    public int getFirstRelationshipId(Direction direction) {
        return direction == Direction.INCOMING ? this.firstin_rel_id : this.firstout_rel_id;
    }

    public boolean hasRelationship(Direction direction) {
        switch (direction) {
            case INCOMING: {
                return this.firstin_rel_id != -1;
            }
            case OUTGOING: {
                return this.firstout_rel_id != -1;
            }
        }
        return false;
    }

    public boolean hasRelationship() {
        return this.hasRelationship(Direction.INCOMING) || this.hasRelationship(Direction.OUTGOING);
    }

    @Override
    public void optimize() {
        super.optimize();
        this.title = null;
        this.type = null;
    }

    public int hashCode() {
        return this.getUid().hashCode();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Node)) {
            return false;
        }
        return ((Node)obj).getUid().equals(this.getUid());
    }

    public String toString() {
        return "{ id=" + this.id + "; uid=" + this.uid + "; title=" + this.title + "; type=" + this.type + " }";
    }
}

