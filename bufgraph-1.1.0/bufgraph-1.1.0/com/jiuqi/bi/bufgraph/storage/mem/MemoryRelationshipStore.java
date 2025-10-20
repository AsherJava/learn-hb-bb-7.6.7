/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.bufgraph.storage.mem;

import com.jiuqi.bi.bufgraph.model.Relationship;
import com.jiuqi.bi.bufgraph.model.RelationshipType;
import com.jiuqi.bi.bufgraph.reader.IRelationshipReader;
import com.jiuqi.bi.bufgraph.reader.mem.MemoryRelationshipReader;
import com.jiuqi.bi.bufgraph.storage.GraphStorageException;
import com.jiuqi.bi.bufgraph.storage.IRelationshipStore;
import com.jiuqi.bi.bufgraph.storage.mem.RelationshipList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryRelationshipStore
implements IRelationshipStore {
    private AtomicInteger typeCounter = new AtomicInteger(1);
    private AtomicInteger relationCounter = new AtomicInteger(0);
    private RelationshipList relationships;
    private Map<String, RelationshipType> types;

    public MemoryRelationshipStore(int initCapacity) {
        this.relationships = new RelationshipList(initCapacity);
        this.types = new HashMap<String, RelationshipType>();
    }

    @Override
    public Relationship addRelationship(String type, int headId, int tailId, Map<String, String> props) throws GraphStorageException {
        RelationshipType relType = this.types.get(type);
        if (relType == null) {
            relType = new RelationshipType((byte)this.typeCounter.getAndIncrement(), type);
            this.types.put(type, relType);
        }
        Relationship rel = new Relationship(this.relationCounter.getAndIncrement(), relType);
        rel.setHeadNodeId(headId);
        rel.setTailNodeId(tailId);
        if (props != null && !props.isEmpty()) {
            Set<Map.Entry<String, String>> entries = props.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                rel.setProperty(entry.getKey(), entry.getValue());
            }
        }
        this.relationships.add(rel);
        return rel;
    }

    @Override
    public void updateRelatedRelationshipId(int rel_id, int headPrev, int headNext, int tailPrev, int tailNext) throws GraphStorageException {
        Relationship relationship = this.relationships.get(rel_id);
        relationship.setHeadPrevRelationshipId(headPrev);
        relationship.setHeadNextRelationshipId(headNext);
        relationship.setTailPrevRelationshipId(tailPrev);
        relationship.setTailNextRelationshipId(tailNext);
    }

    @Override
    public IRelationshipReader createReader() throws GraphStorageException {
        return new MemoryRelationshipReader(this.relationships);
    }

    @Override
    public void finishAdd() throws GraphStorageException {
    }

    @Override
    public void finishUpdate() throws GraphStorageException {
    }

    @Override
    public void close() throws GraphStorageException {
    }
}

