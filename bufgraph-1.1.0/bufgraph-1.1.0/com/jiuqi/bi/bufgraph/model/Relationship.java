/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.bufgraph.model;

import com.jiuqi.bi.bufgraph.model.Entity;
import com.jiuqi.bi.bufgraph.model.RelationshipType;

public final class Relationship
extends Entity {
    private static final long serialVersionUID = 1L;
    private RelationshipType type;
    private int head_id = -1;
    private int tail_id = -1;
    private int head_prev_rel_id = -1;
    private int head_next_rel_id = -1;
    private int tail_prev_rel_id = -1;
    private int tail_next_rel_id = -1;

    public Relationship(int id, RelationshipType type) {
        super(id, null);
        this.type = type;
    }

    public String getType() {
        return this.type.title();
    }

    public byte getTypeSign() {
        return this.type.type();
    }

    public void setHeadNodeId(int id) {
        this.head_id = id;
    }

    public int getHeadNodeId() {
        return this.head_id;
    }

    public void setTailNodeId(int id) {
        this.tail_id = id;
    }

    public int getTailNodeId() {
        return this.tail_id;
    }

    public int getOtherDirectNodeId(int currNodeId) {
        if (currNodeId == this.head_id) {
            return this.tail_id;
        }
        return this.head_id;
    }

    public void setHeadPrevRelationshipId(int relationshipId) {
        this.head_prev_rel_id = relationshipId;
    }

    public int getHeadPrevRelationshipId() {
        return this.head_prev_rel_id;
    }

    public boolean hasHeadPrevRelationship() {
        return this.head_prev_rel_id != -1;
    }

    public void setHeadNextRelationshipId(int relationshipId) {
        this.head_next_rel_id = relationshipId;
    }

    public int getHeadNextRelationshipId() {
        return this.head_next_rel_id;
    }

    public boolean hasHeadNextRelationship() {
        return this.head_next_rel_id != -1;
    }

    public void setTailPrevRelationshipId(int relationshipId) {
        this.tail_prev_rel_id = relationshipId;
    }

    public int getTailPrevRelationshipId() {
        return this.tail_prev_rel_id;
    }

    public boolean hasTailPrevRelationship() {
        return this.tail_prev_rel_id != -1;
    }

    public void setTailNextRelationshipId(int relationshipId) {
        this.tail_next_rel_id = relationshipId;
    }

    public int getTailNextRelationshipId() {
        return this.tail_next_rel_id;
    }

    public boolean hasTailNextRelationship() {
        return this.tail_next_rel_id != -1;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        Relationship r = (Relationship)obj;
        return r.head_id == this.head_id && r.tail_id == this.tail_id;
    }

    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append(this.getHeadNodeId()).append("->").append(this.getTailNodeId());
        b.append("{ id=").append(this.id).append("; uid=").append(this.uid).append("; type=").append(this.type).append("}");
        return b.toString();
    }
}

