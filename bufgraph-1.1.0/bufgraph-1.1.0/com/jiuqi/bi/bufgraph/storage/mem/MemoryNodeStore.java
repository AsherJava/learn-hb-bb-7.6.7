/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.bufgraph.storage.mem;

import com.jiuqi.bi.bufgraph.model.Direction;
import com.jiuqi.bi.bufgraph.model.Node;
import com.jiuqi.bi.bufgraph.model.NodeType;
import com.jiuqi.bi.bufgraph.reader.INodeReader;
import com.jiuqi.bi.bufgraph.reader.mem.MemoryNodeReader;
import com.jiuqi.bi.bufgraph.storage.GraphStorageException;
import com.jiuqi.bi.bufgraph.storage.INodeStore;
import com.jiuqi.bi.bufgraph.storage.mem.NodeList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryNodeStore
implements INodeStore {
    private AtomicInteger typeCounter = new AtomicInteger(1);
    private AtomicInteger nodeIdCounter = new AtomicInteger(0);
    private NodeList nodes;
    private Map<String, NodeType> types;

    public MemoryNodeStore(int initCapacity) {
        this.nodes = new NodeList(initCapacity);
        this.types = new HashMap<String, NodeType>();
    }

    @Override
    public Node addNode(String uid, String type, String title, Map<String, String> props) throws GraphStorageException {
        NodeType nodeType = this.types.get(type);
        if (nodeType == null) {
            nodeType = new NodeType((byte)this.typeCounter.getAndIncrement(), type);
            this.types.put(type, nodeType);
        }
        Node node = new Node(this.nodeIdCounter.getAndIncrement(), uid, title, nodeType);
        if (props != null && !props.isEmpty()) {
            Set<Map.Entry<String, String>> entries = props.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                node.setProperty(entry.getKey(), entry.getValue());
            }
        }
        this.nodes.add(node);
        return node;
    }

    @Override
    public void updateFirstRelationshipId(int nodeId, Direction direction, int relId) throws GraphStorageException {
        this.nodes.get(nodeId).setFirstRelationshipId(direction, relId);
    }

    @Override
    public INodeReader createReader() throws GraphStorageException {
        return new MemoryNodeReader(this.nodes);
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

