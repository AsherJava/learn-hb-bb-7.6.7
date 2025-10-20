/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.bufgraph.storage;

import com.jiuqi.bi.bufgraph.model.Direction;
import com.jiuqi.bi.bufgraph.model.Node;
import com.jiuqi.bi.bufgraph.reader.INodeReader;
import com.jiuqi.bi.bufgraph.storage.GraphStorageException;
import java.util.Map;

public interface INodeStore {
    public Node addNode(String var1, String var2, String var3, Map<String, String> var4) throws GraphStorageException;

    public void updateFirstRelationshipId(int var1, Direction var2, int var3) throws GraphStorageException;

    public INodeReader createReader() throws GraphStorageException;

    public void finishAdd() throws GraphStorageException;

    public void finishUpdate() throws GraphStorageException;

    public void close() throws GraphStorageException;
}

