/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.bufgraph.storage.disk;

import com.jiuqi.bi.bufgraph.model.Relationship;
import com.jiuqi.bi.bufgraph.model.RelationshipType;
import com.jiuqi.bi.bufgraph.reader.IRelationshipReader;
import com.jiuqi.bi.bufgraph.reader.disk.DiskRelationshipReader;
import com.jiuqi.bi.bufgraph.storage.GraphStorageException;
import com.jiuqi.bi.bufgraph.storage.IRelationshipStore;
import com.jiuqi.bi.bufgraph.storage.util.ByteUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class DiskRelationshipStore
implements IRelationshipStore {
    public static final String REL_BASIC_FILE = "graph.relationstore";
    public static final String REL_TYPE_FILE = "graph.relationstore.type";
    public static final String REL_ID_FILE = "graph.relationstore.dict.id";
    public static final int RELATION_BIT_SIZE = 25;
    private String dir;
    private FileChannel relChannel;
    private ByteBuffer relBuffer;
    private AtomicInteger typeCounter = new AtomicInteger(1);
    private AtomicInteger relIdCounter = new AtomicInteger(0);
    private Map<String, RelationshipType> types;
    private static byte[] EMPTY_BYTES = ByteUtils.int2Bytes(-1);

    public DiskRelationshipStore(String storePathDir, int initCapacity) throws GraphStorageException {
        this.dir = storePathDir;
        OpenOption[] opts = new OpenOption[]{StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING};
        try {
            this.relChannel = FileChannel.open(new File(this.dir, REL_BASIC_FILE).toPath(), opts);
            this.relBuffer = this.relChannel.map(FileChannel.MapMode.READ_WRITE, 0L, initCapacity * 25);
        }
        catch (IOException e) {
            throw new GraphStorageException(e.getMessage(), e);
        }
        this.types = new HashMap<String, RelationshipType>();
    }

    @Override
    public Relationship addRelationship(String type, int headId, int tailId, Map<String, String> props) throws GraphStorageException {
        RelationshipType relType = this.types.get(type);
        if (relType == null) {
            relType = new RelationshipType((byte)this.typeCounter.getAndIncrement(), type);
            this.types.put(type, relType);
        }
        Relationship rel = new Relationship(this.relIdCounter.getAndIncrement(), relType);
        if (props != null && !props.isEmpty()) {
            Set<Map.Entry<String, String>> entries = props.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                rel.setProperty(entry.getKey(), entry.getValue());
            }
        }
        this.relBuffer.put(relType.type());
        this.relBuffer.put(ByteUtils.int2Bytes(headId));
        this.relBuffer.put(ByteUtils.int2Bytes(tailId));
        this.relBuffer.put(EMPTY_BYTES);
        this.relBuffer.put(EMPTY_BYTES);
        this.relBuffer.put(EMPTY_BYTES);
        this.relBuffer.put(EMPTY_BYTES);
        return rel;
    }

    @Override
    public void updateRelatedRelationshipId(int rel_id, int headPrev, int headNext, int tailPrev, int tailNext) throws GraphStorageException {
        int pos = rel_id * 25 + 9;
        this.relBuffer.position(pos);
        this.relBuffer.put(ByteUtils.int2Bytes(headPrev));
        this.relBuffer.put(ByteUtils.int2Bytes(headNext));
        this.relBuffer.put(ByteUtils.int2Bytes(tailPrev));
        this.relBuffer.put(ByteUtils.int2Bytes(tailNext));
    }

    @Override
    public IRelationshipReader createReader() throws GraphStorageException {
        return new DiskRelationshipReader(this.dir);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void finishAdd() throws GraphStorageException {
        Exception ex = null;
        try {
            this.relChannel.force(false);
        }
        catch (IOException e1) {
            ex = e1;
        }
        try (FileOutputStream out = new FileOutputStream(new File(this.dir, REL_TYPE_FILE));
             ObjectOutputStream oos = new ObjectOutputStream(out);){
            HashMap<Byte, String> relTypes = new HashMap<Byte, String>(this.types.size());
            for (RelationshipType nt : this.types.values()) {
                relTypes.put(nt.type(), nt.title());
            }
            oos.writeObject(relTypes);
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
            this.relChannel.force(false);
        }
        catch (IOException e1) {
            throw new GraphStorageException(e1.getMessage(), e1);
        }
    }

    @Override
    public void close() throws GraphStorageException {
        try {
            this.relChannel.close();
        }
        catch (IOException e) {
            throw new GraphStorageException(e.getMessage(), e);
        }
        this.relBuffer = null;
        this.relChannel = null;
    }
}

