/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.bufgraph.reader;

import com.jiuqi.bi.bufgraph.model.Relationship;
import com.jiuqi.bi.bufgraph.storage.GraphStorageException;
import java.util.List;

public interface IRelationshipReader {
    public int size() throws GraphStorageException;

    public Relationship findById(int var1) throws GraphStorageException;

    public List<Relationship> list(String var1) throws GraphStorageException;

    public void close() throws GraphStorageException;
}

