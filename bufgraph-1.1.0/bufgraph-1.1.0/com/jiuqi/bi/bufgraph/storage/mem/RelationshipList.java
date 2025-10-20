/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.collection.PageArrayList
 */
package com.jiuqi.bi.bufgraph.storage.mem;

import com.jiuqi.bi.bufgraph.model.Relationship;
import com.jiuqi.bi.util.collection.PageArrayList;
import java.util.ArrayList;
import java.util.List;

public class RelationshipList {
    private List<Relationship> relationships;
    private static final int PAGE_SIZE = 4096;

    public RelationshipList(int size) {
        this.relationships = size < 4096 ? new ArrayList<Relationship>(size) : new PageArrayList(4096, size);
    }

    public Relationship get(int relId) {
        return this.relationships.get(relId);
    }

    public int size() {
        return this.relationships.size();
    }

    public void add(Relationship relationship) {
        int v = relationship.getId();
        if (v >= 4096 && v >= this.relationships.size() && this.relationships instanceof ArrayList) {
            this.relationships = new PageArrayList(this.relationships);
        }
        if (v >= this.relationships.size()) {
            this.relationships.add(relationship);
        } else {
            this.relationships.set(relationship.getId(), relationship);
        }
    }
}

