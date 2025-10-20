/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.bufgraph.storage.disk;

import com.jiuqi.bi.bufgraph.model.Direction;
import com.jiuqi.bi.bufgraph.model.Node;
import com.jiuqi.bi.bufgraph.model.NodeType;
import com.jiuqi.bi.bufgraph.reader.INodeReader;
import com.jiuqi.bi.bufgraph.reader.disk.DiskNodeReader;
import com.jiuqi.bi.bufgraph.storage.GraphStorageException;
import com.jiuqi.bi.bufgraph.storage.INodeStore;
import com.jiuqi.bi.bufgraph.storage.util.ByteUtils;
import com.jiuqi.bi.bufgraph.storage.util.Dictionary;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class DiskNodeStore
implements INodeStore {
    public static final String NODE_BASIC_FILE = "graph.nodestore";
    public static final String NODE_TYPE_FILE = "graph.nodestore.type";
    public static final String NODE_ID_FILE = "graph.nodestore.dict.id";
    public static final String NODE_PROP_FILE = "graph.node.propertystore";
    public static final int NODE_BIT_SIZE = 13;
    private String dir;
    private FileChannel nodeChannel;
    private FileChannel propChannel;
    private ByteBuffer nodeBuffer;
    private ByteBuffer propBuffer;
    private AtomicInteger typeCounter = new AtomicInteger(1);
    private AtomicInteger nodeIdCounter = new AtomicInteger(0);
    private Map<String, NodeType> types;
    private String curuid = "";
    private int curpos = 0;
    private Dictionary<Integer> dict_id;
    private static byte[] EMPTY_BYTES = ByteUtils.int2Bytes(-1);
    private static String[] FILL_BIT_0 = new String[31];

    public DiskNodeStore(String storePathDir, int initCapacity) throws GraphStorageException {
        this.dir = storePathDir;
        OpenOption[] opts = new OpenOption[]{StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING};
        try {
            this.nodeChannel = FileChannel.open(new File(this.dir, NODE_BASIC_FILE).toPath(), opts);
            this.nodeBuffer = this.nodeChannel.map(FileChannel.MapMode.READ_WRITE, 0L, initCapacity * 13);
            this.propChannel = FileChannel.open(new File(this.dir, NODE_PROP_FILE).toPath(), opts);
            this.propBuffer = ByteBuffer.allocate(0xA00000);
        }
        catch (IOException e) {
            throw new GraphStorageException(e.getMessage(), e);
        }
        this.types = new HashMap<String, NodeType>();
        this.dict_id = Dictionary.newIntDict();
    }

    @Override
    public Node addNode(String uid, String type, String title, Map<String, String> props) throws GraphStorageException {
        if (this.curuid.compareTo(uid) > 0) {
            throw new GraphStorageException("\u6309\u78c1\u76d8\u65b9\u5f0f\u5b58\u50a8\u65f6\uff0c\u5199\u5165\u8282\u70b9\u5fc5\u987b\u6309uid\u6392\u5e8f");
        }
        this.curuid = uid;
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
        try {
            this.nodeBuffer.put(nodeType.type());
            this.nodeBuffer.put(EMPTY_BYTES);
            this.nodeBuffer.put(EMPTY_BYTES);
            this.nodeBuffer.put(ByteUtils.int2Bytes(this.curpos));
            this.dict_id.addDict(uid, node.getId());
            byte[] data = DiskNodeStore.transNodeToBytes(node);
            if (this.propBuffer.remaining() <= data.length + 4) {
                this.flushPropBuffer(false);
            }
            this.propBuffer.put(ByteUtils.int2Bytes(data.length));
            this.propBuffer.put(data);
            this.curpos += data.length + 4;
        }
        catch (IOException e) {
            throw new GraphStorageException(e.getMessage(), e);
        }
        return node;
    }

    static byte[] transNodeToBytes(Node node) throws GraphStorageException {
        StringBuilder b = new StringBuilder();
        b.append("u=").append(node.getUid()).append("&t=").append(node.getTitle());
        Set<String> propKeys = node.propertyKeys();
        if (!propKeys.isEmpty()) {
            for (String s : propKeys) {
                b.append("&").append(s).append("=").append(node.getProperty(s));
            }
        }
        try {
            return b.toString().getBytes("utf-8");
        }
        catch (UnsupportedEncodingException e) {
            throw new GraphStorageException("\u7cfb\u7edf\u4e0d\u652f\u6301UTF-8\u5b57\u7b26\u96c6");
        }
    }

    public static Map<String, String> loadNodeFromBytes(byte[] bytes) throws GraphStorageException {
        String str;
        try {
            str = new String(bytes, "utf-8");
        }
        catch (UnsupportedEncodingException e) {
            throw new GraphStorageException("\u7cfb\u7edf\u4e0d\u652f\u6301UTF-8\u5b57\u7b26\u96c6");
        }
        char[] chars = str.toCharArray();
        int start = 0;
        String key = "";
        String value = "";
        HashMap<String, String> map = new HashMap<String, String>();
        for (int i = 0; i < chars.length; ++i) {
            if (chars[i] == '=') {
                key = str.substring(start, i);
                start = i + 1;
                continue;
            }
            if (chars[i] != '&' && i != chars.length - 1) continue;
            value = chars[i] == '&' ? str.substring(start, i) : str.substring(start);
            start = i + 1;
            map.put(key, value);
        }
        return map;
    }

    static String id2FixedLenString(int id) {
        String s = Integer.toString(id);
        return FILL_BIT_0[31 - s.length()] + s;
    }

    @Override
    public void updateFirstRelationshipId(int nodeId, Direction direction, int relId) throws GraphStorageException {
        int pos = nodeId * 13 + 1;
        if (direction == Direction.OUTGOING) {
            pos += 4;
        }
        this.nodeBuffer.position(pos);
        this.nodeBuffer.put(ByteUtils.int2Bytes(relId));
    }

    private void flushPropBuffer(boolean finished) throws IOException {
        this.propBuffer.flip();
        while (this.propBuffer.hasRemaining()) {
            this.propChannel.write(this.propBuffer);
        }
        if (!finished) {
            this.propBuffer = ByteBuffer.allocate(0xA00000);
        }
    }

    @Override
    public INodeReader createReader() throws GraphStorageException {
        return new DiskNodeReader(this.dir);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void finishAdd() throws GraphStorageException {
        Exception ex = null;
        try {
            this.dict_id.finish();
            this.dict_id.save(new File(this.dir, NODE_ID_FILE));
        }
        catch (IOException e) {
            ex = e;
        }
        try {
            this.nodeChannel.force(false);
            this.flushPropBuffer(true);
        }
        catch (IOException e1) {
            if (ex == null) {
                ex = e1;
            }
            ex.addSuppressed(e1);
        }
        try (FileOutputStream out = new FileOutputStream(new File(this.dir, NODE_TYPE_FILE));
             ObjectOutputStream oos = new ObjectOutputStream(out);){
            HashMap<Byte, String> nodeTypes = new HashMap<Byte, String>(this.types.size());
            for (NodeType nt : this.types.values()) {
                nodeTypes.put(nt.type(), nt.title());
            }
            oos.writeObject(nodeTypes);
        }
        catch (Exception e) {
            if (ex == null) {
                ex = e;
            }
            ex.addSuppressed(e);
        }
        if (ex != null) {
            throw new GraphStorageException(ex.getMessage(), ex);
        }
    }

    @Override
    public void finishUpdate() throws GraphStorageException {
        try {
            this.nodeChannel.force(false);
            this.flushPropBuffer(true);
        }
        catch (IOException e1) {
            throw new GraphStorageException(e1.getMessage(), e1);
        }
    }

    @Override
    public void close() throws GraphStorageException {
        try {
            this.nodeChannel.close();
            this.propChannel.close();
        }
        catch (IOException e) {
            throw new GraphStorageException(e.getMessage(), e);
        }
        this.nodeBuffer = null;
        this.propBuffer = null;
        this.dict_id = null;
    }

    static {
        String str = "0";
        for (int i = 0; i < 31; ++i) {
            DiskNodeStore.FILL_BIT_0[i] = str;
            str = str + "0";
        }
    }
}

