/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.bufgraph.storage;

import com.jiuqi.bi.bufgraph.model.Relationship;
import com.jiuqi.bi.bufgraph.reader.IRelationshipReader;
import com.jiuqi.bi.bufgraph.storage.GraphStorageException;
import java.util.Map;

public interface IRelationshipStore {
    public Relationship addRelationship(String var1, int var2, int var3, Map<String, String> var4) throws GraphStorageException;

    public void updateRelatedRelationshipId(int var1, int var2, int var3, int var4, int var5) throws GraphStorageException;

    public IRelationshipReader createReader() throws GraphStorageException;

    public void finishAdd() throws GraphStorageException;

    public void finishUpdate() throws GraphStorageException;

    public void close() throws GraphStorageException;
}

