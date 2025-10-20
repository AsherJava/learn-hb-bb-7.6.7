/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.bufgraph.reader.mem;

import com.jiuqi.bi.bufgraph.GraphException;
import com.jiuqi.bi.bufgraph.model.Direction;
import com.jiuqi.bi.bufgraph.model.Node;
import com.jiuqi.bi.bufgraph.reader.INodeReader;
import com.jiuqi.bi.bufgraph.storage.GraphStorageException;
import com.jiuqi.bi.bufgraph.storage.mem.NodeList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryNodeReader
implements INodeReader {
    private NodeList nodes;
    private Map<String, Node> finder;

    public MemoryNodeReader(NodeList nodes) throws GraphStorageException {
        this.nodes = nodes;
        int size = nodes.size();
        this.finder = new HashMap<String, Node>(size);
        for (int i = 0; i < size; ++i) {
            Node node = nodes.get(i);
            this.finder.put(node.getUid(), node);
        }
    }

    @Override
    public int size() throws GraphStorageException {
        return this.nodes.size();
    }

    @Override
    public int getFirstRelationshipId(int id, Direction direction) throws GraphStorageException {
        return this.nodes.get(id).getFirstRelationshipId(direction);
    }

    @Override
    public Node findById(int id) throws GraphStorageException {
        return this.nodes.get(id);
    }

    @Override
    public Node findByUid(String uid) throws GraphStorageException {
        return this.finder.get(uid);
    }

    @Override
    public int findIdByUid(String uid) throws GraphStorageException {
        Node v = this.finder.get(uid);
        return v == null ? -1 : v.getId();
    }

    @Override
    public List<Node> searchNode(String propertyName, Object searchValue, boolean fuzzy) {
        if (searchValue == null) {
            return new ArrayList<Node>();
        }
        ArrayList<Node> list = new ArrayList<Node>();
        if (fuzzy) {
            String sv = searchValue.toString().toLowerCase();
            this.nodes.forEach(n -> {
                Object v = n.getProperty(propertyName);
                if (v != null && v.toString().toLowerCase().contains(sv)) {
                    list.add((Node)n);
                }
            });
        } else {
            this.nodes.forEach(n -> {
                Object v = n.getProperty(propertyName);
                if (v != null && v.equals(searchValue)) {
                    list.add((Node)n);
                }
            });
        }
        return list;
    }

    @Override
    public List<Node> searchNodeByTitle(String keyword, boolean fuzzy) throws GraphException {
        ArrayList<Node> list = new ArrayList<Node>();
        if (fuzzy) {
            String kv = keyword.toLowerCase();
            this.nodes.forEach(n -> {
                String title = n.getTitle();
                if (title != null && title.toLowerCase().contains(kv)) {
                    list.add((Node)n);
                }
            });
        } else {
            this.nodes.forEach(n -> {
                String title = n.getTitle();
                if (title != null && title.equals(keyword)) {
                    list.add((Node)n);
                }
            });
        }
        return list;
    }

    @Override
    public List<Node> list(String type) throws GraphStorageException {
        ArrayList<Node> list = new ArrayList<Node>();
        for (int i = 0; i < this.nodes.size(); ++i) {
            if (type != null && !this.nodes.get(i).getType().equals(type)) continue;
            list.add(this.nodes.get(i));
        }
        return list;
    }

    @Override
    public void close() throws GraphStorageException {
    }
}

