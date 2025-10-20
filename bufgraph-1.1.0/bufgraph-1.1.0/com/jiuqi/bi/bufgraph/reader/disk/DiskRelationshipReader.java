/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.bufgraph.reader.disk;

import com.jiuqi.bi.bufgraph.model.Relationship;
import com.jiuqi.bi.bufgraph.model.RelationshipType;
import com.jiuqi.bi.bufgraph.reader.IRelationshipReader;
import com.jiuqi.bi.bufgraph.storage.GraphStorageException;
import com.jiuqi.bi.bufgraph.storage.util.ByteUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiskRelationshipReader
implements IRelationshipReader {
    private String dir;
    private FileChannel relChannel;
    private MappedByteBuffer relBuffer;
    private int size;
    private Map<Byte, RelationshipType> types;
    private Object lock = new Object();

    public DiskRelationshipReader(String dir) throws GraphStorageException {
        this.dir = dir;
        try {
            this.relChannel = FileChannel.open(new File(this.dir, "graph.relationstore").toPath(), StandardOpenOption.READ);
            this.size = (int)(this.relChannel.size() / 25L);
            this.relBuffer = this.relChannel.map(FileChannel.MapMode.READ_ONLY, 0L, this.relChannel.size());
        }
        catch (IOException e) {
            throw new GraphStorageException(e.getMessage(), e);
        }
        this.loadRelTypes();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void loadRelTypes() throws GraphStorageException {
        try (FileInputStream is = new FileInputStream(new File(this.dir, "graph.relationstore.type"));
             ObjectInputStream ois = new ObjectInputStream(is);){
            Map relTypes = (Map)ois.readObject();
            this.types = new HashMap<Byte, RelationshipType>(relTypes.size());
            for (Map.Entry entry : relTypes.entrySet()) {
                this.types.put((Byte)entry.getKey(), new RelationshipType((Byte)entry.getKey(), (String)entry.getValue()));
            }
        }
        catch (FileNotFoundException e) {
            throw new GraphStorageException("\u672a\u627e\u5230\u6587\u4ef6graph.relationstore.type", e);
        }
        catch (ClassNotFoundException e) {
            throw new GraphStorageException("graph.relationstore.type\u6587\u4ef6\u5185\u5bb9\u4e3a\u7a7a");
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
    public Relationship findById(int id) throws GraphStorageException {
        int relPos = id * 25;
        Object object = this.lock;
        synchronized (object) {
            this.relBuffer.position(relPos);
            byte type = this.relBuffer.get();
            byte[] dst = new byte[4];
            Relationship rel = new Relationship(id, this.types.get(type));
            this.relBuffer.get(dst);
            rel.setHeadNodeId(ByteUtils.bytes2Int(dst));
            this.relBuffer.get(dst);
            rel.setTailNodeId(ByteUtils.bytes2Int(dst));
            this.relBuffer.get(dst);
            rel.setHeadPrevRelationshipId(ByteUtils.bytes2Int(dst));
            this.relBuffer.get(dst);
            rel.setHeadNextRelationshipId(ByteUtils.bytes2Int(dst));
            this.relBuffer.get(dst);
            rel.setTailPrevRelationshipId(ByteUtils.bytes2Int(dst));
            this.relBuffer.get(dst);
            rel.setTailNextRelationshipId(ByteUtils.bytes2Int(dst));
            return rel;
        }
    }

    @Override
    public List<Relationship> list(String type) throws GraphStorageException {
        byte b = 0;
        Collection<RelationshipType> vls = this.types.values();
        for (RelationshipType r : vls) {
            if (!r.title().equals(type)) continue;
            b = r.type();
            break;
        }
        ArrayList<Relationship> list = new ArrayList<Relationship>();
        for (int i = 0; i < this.size; ++i) {
            int relPos = i * 25;
            this.relBuffer.position(relPos);
            if (this.relBuffer.get() != b) continue;
            list.add(this.findById(i));
        }
        return list;
    }

    @Override
    public void close() throws GraphStorageException {
        try {
            this.relChannel.close();
        }
        catch (IOException e) {
            throw new GraphStorageException(e.getMessage(), e);
        }
    }
}

