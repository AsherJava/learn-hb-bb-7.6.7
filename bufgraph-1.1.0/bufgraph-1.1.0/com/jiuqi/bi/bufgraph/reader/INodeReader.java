/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.bufgraph.reader;

import com.jiuqi.bi.bufgraph.GraphException;
import com.jiuqi.bi.bufgraph.model.Direction;
import com.jiuqi.bi.bufgraph.model.Node;
import com.jiuqi.bi.bufgraph.storage.GraphStorageException;
import java.util.List;

public interface INodeReader {
    public int size() throws GraphStorageException;

    public int getFirstRelationshipId(int var1, Direction var2) throws GraphStorageException;

    public Node findById(int var1) throws GraphStorageException;

    public Node findByUid(String var1) throws GraphStorageException;

    public int findIdByUid(String var1) throws GraphStorageException;

    public List<Node> searchNodeByTitle(String var1, boolean var2) throws GraphException;

    public List<Node> searchNode(String var1, Object var2, boolean var3) throws GraphException;

    public List<Node> list(String var1) throws GraphStorageException;

    public void close() throws GraphStorageException;
}

