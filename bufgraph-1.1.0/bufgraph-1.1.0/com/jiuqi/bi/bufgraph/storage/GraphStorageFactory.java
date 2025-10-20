/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.bufgraph.storage;

import com.jiuqi.bi.bufgraph.storage.GraphStorageException;
import com.jiuqi.bi.bufgraph.storage.INodeStore;
import com.jiuqi.bi.bufgraph.storage.IRelationshipStore;
import com.jiuqi.bi.bufgraph.storage.disk.DiskNodeStore;
import com.jiuqi.bi.bufgraph.storage.disk.DiskRelationshipStore;
import com.jiuqi.bi.bufgraph.storage.luc.LuceneNodeStore;
import com.jiuqi.bi.bufgraph.storage.mem.MemoryNodeStore;
import com.jiuqi.bi.bufgraph.storage.mem.MemoryRelationshipStore;

public class GraphStorageFactory {
    public static INodeStore createNodeStorage(int initCapacity) throws GraphStorageException {
        return GraphStorageFactory.createNodeStorage(initCapacity, false, null);
    }

    public static INodeStore createNodeStorage(int initCapacity, boolean uidSorted, String storePath) throws GraphStorageException {
        if (storePath != null) {
            if (uidSorted) {
                return new DiskNodeStore(storePath, initCapacity);
            }
            return new LuceneNodeStore(storePath, initCapacity);
        }
        return new MemoryNodeStore(initCapacity);
    }

    public static IRelationshipStore createRelationshipStorage(int initCapacity, String storePath) throws GraphStorageException {
        if (initCapacity < 3000000 || storePath == null) {
            return new MemoryRelationshipStore(initCapacity);
        }
        return new DiskRelationshipStore(storePath, initCapacity);
    }
}

