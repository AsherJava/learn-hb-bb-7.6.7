/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.bufgraph.reader.mem;

import com.jiuqi.bi.bufgraph.model.Relationship;
import com.jiuqi.bi.bufgraph.reader.IRelationshipReader;
import com.jiuqi.bi.bufgraph.storage.GraphStorageException;
import com.jiuqi.bi.bufgraph.storage.mem.RelationshipList;
import java.util.ArrayList;
import java.util.List;

public class MemoryRelationshipReader
implements IRelationshipReader {
    private RelationshipList relationships;

    public MemoryRelationshipReader(RelationshipList relationships) {
        this.relationships = relationships;
    }

    @Override
    public int size() throws GraphStorageException {
        return this.relationships.size();
    }

    @Override
    public Relationship findById(int id) throws GraphStorageException {
        return this.relationships.get(id);
    }

    @Override
    public List<Relationship> list(String type) throws GraphStorageException {
        ArrayList<Relationship> list = new ArrayList<Relationship>();
        for (int i = 0; i < this.relationships.size(); ++i) {
            Relationship rel = this.relationships.get(i);
            if (type != null && !rel.getType().equals(type)) continue;
            list.add(rel);
        }
        return list;
    }

    @Override
    public void close() throws GraphStorageException {
    }
}

