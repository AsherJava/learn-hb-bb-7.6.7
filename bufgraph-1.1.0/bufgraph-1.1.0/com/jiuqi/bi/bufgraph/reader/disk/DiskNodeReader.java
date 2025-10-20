/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.bufgraph.reader.disk;

import com.jiuqi.bi.bufgraph.GraphException;
import com.jiuqi.bi.bufgraph.model.Direction;
import com.jiuqi.bi.bufgraph.model.Node;
import com.jiuqi.bi.bufgraph.model.NodeType;
import com.jiuqi.bi.bufgraph.reader.INodeReader;
import com.jiuqi.bi.bufgraph.storage.GraphStorageException;
import com.jiuqi.bi.bufgraph.storage.disk.DiskNodeStore;
import com.jiuqi.bi.bufgraph.storage.util.ByteUtils;
import com.jiuqi.bi.bufgraph.storage.util.Dictionary;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiskNodeReader
implements INodeReader {
    private String dir;
    private FileChannel nodeChannel;
    private FileChannel propChannel;
    private MappedByteBuffer nodeBuffer;
    private int size;
    private Map<Byte, NodeType> types;
    private Dictionary<Integer> dict_id;
    private Object nodelock = new Object();
    private Object proplock = new Object();

    public DiskNodeReader(String dir) throws GraphStorageException {
        this.dir = dir;
        try {
            this.nodeChannel = FileChannel.open(new File(this.dir, "graph.nodestore").toPath(), StandardOpenOption.READ);
            this.size = (int)(this.nodeChannel.size() / 13L);
            this.nodeBuffer = this.nodeChannel.map(FileChannel.MapMode.READ_ONLY, 0L, this.nodeChannel.size());
            this.propChannel = FileChannel.open(new File(dir, "graph.node.propertystore").toPath(), StandardOpenOption.READ);
        }
        catch (IOException e) {
            throw new GraphStorageException(e.getMessage(), e);
        }
        this.loadNodeTypes();
        this.dict_id = Dictionary.newIntDict();
        try {
            this.dict_id.load(new File(dir, "graph.nodestore.dict.id"));
        }
        catch (IOException e) {
            throw new GraphStorageException(e.getMessage(), e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void loadNodeTypes() throws GraphStorageException {
        try (FileInputStream is = new FileInputStream(new File(this.dir, "graph.nodestore.type"));
             ObjectInputStream ois = new ObjectInputStream(is);){
            Map nodeTypes = (Map)ois.readObject();
            this.types = new HashMap<Byte, NodeType>(nodeTypes.size());
            for (Map.Entry entry : nodeTypes.entrySet()) {
                this.types.put((Byte)entry.getKey(), new NodeType((Byte)entry.getKey(), (String)entry.getValue()));
            }
        }
        catch (FileNotFoundException e) {
            throw new GraphStorageException("\u672a\u627e\u5230\u6587\u4ef6graph.nodestore.type", e);
        }
        catch (ClassNotFoundException e) {
            throw new GraphStorageException("graph.nodestore.type\u6587\u4ef6\u5185\u5bb9\u4e3a\u7a7a");
        }
        catch (IOException e) {
            throw new GraphStorageException(e.getMessage(), e);
        }
    }

    @Override
    public int size() throws GraphStorageException {
        return this.size;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int getFirstRelationshipId(int id, Direction direction) throws GraphStorageException {
        int pos = id * 13 + 1;
        if (direction == Direction.OUTGOING) {
            pos += 4;
        }
        Object object = this.nodelock;
        synchronized (object) {
            this.nodeBuffer.position(pos);
            byte[] dst = new byte[4];
            this.nodeBuffer.get(dst);
            return ByteUtils.bytes2Int(dst);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Node findById(int id) throws GraphStorageException {
        try {
            int propPos;
            byte type;
            Object object = this.nodelock;
            synchronized (object) {
                int nodePos = id * 13;
                this.nodeBuffer.position(nodePos);
                type = this.nodeBuffer.get();
                this.nodeBuffer.position(nodePos + 9);
                propPos = this.nodeBuffer.getInt();
                if (propPos == -1) {
                    return null;
                }
            }
            object = this.proplock;
            synchronized (object) {
                this.propChannel.position(propPos);
                ByteBuffer lenInfo = ByteBuffer.allocate(4);
                this.propChannel.read(lenInfo);
                int len = ByteUtils.bytes2Int(lenInfo.array());
                ByteBuffer dst = ByteBuffer.allocate(len);
                this.propChannel.read(dst);
                Map<String, String> info = DiskNodeStore.loadNodeFromBytes(dst.array());
                String uid = info.get("u");
                String title = info.get("t");
                info.remove("u");
                info.remove("t");
                Node node = new Node(id, uid, title, this.types.get(type));
                for (Map.Entry<String, String> entry : info.entrySet()) {
                    node.setProperty(entry.getKey(), entry.getValue());
                }
                return node;
            }
        }
        catch (IOException e) {
            throw new GraphStorageException(e.getMessage(), e);
        }
    }

    @Override
    public Node findByUid(String uid) throws GraphStorageException {
        int id;
        try {
            id = this.dict_id.getInt(uid);
        }
        catch (IOException e) {
            throw new GraphStorageException(e.getMessage(), e);
        }
        return this.findById(id);
    }

    @Override
    public int findIdByUid(String uid) throws GraphStorageException {
        try {
            return this.dict_id.getInt(uid);
        }
        catch (IOException e) {
            throw new GraphStorageException(e.getMessage(), e);
        }
    }

    @Override
    public List<Node> searchNode(String propertyName, Object searchValue, boolean fuzzy) {
        return null;
    }

    @Override
    public List<Node> searchNodeByTitle(String keyword, boolean fuzzy) throws GraphException {
        return null;
    }

    @Override
    public List<Node> list(String type) throws GraphStorageException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void close() throws GraphStorageException {
        this.dict_id = null;
        try {
            this.nodeChannel.close();
            this.propChannel.close();
        }
        catch (IOException e) {
            throw new GraphStorageException(e.getMessage(), e);
        }
    }
}

