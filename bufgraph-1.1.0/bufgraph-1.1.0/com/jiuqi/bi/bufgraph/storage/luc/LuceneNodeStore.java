/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.lucene.document.Document
 *  org.apache.lucene.document.Field$Store
 *  org.apache.lucene.document.IntPoint
 *  org.apache.lucene.document.StoredField
 *  org.apache.lucene.document.StringField
 *  org.apache.lucene.index.IndexWriter
 *  org.apache.lucene.index.IndexWriterConfig
 *  org.apache.lucene.index.IndexWriterConfig$OpenMode
 *  org.apache.lucene.index.IndexableField
 *  org.apache.lucene.store.Directory
 *  org.apache.lucene.store.FSDirectory
 */
package com.jiuqi.bi.bufgraph.storage.luc;

import com.jiuqi.bi.bufgraph.model.Direction;
import com.jiuqi.bi.bufgraph.model.Node;
import com.jiuqi.bi.bufgraph.model.NodeType;
import com.jiuqi.bi.bufgraph.reader.INodeReader;
import com.jiuqi.bi.bufgraph.reader.luc.LuceneNodeReader;
import com.jiuqi.bi.bufgraph.storage.GraphStorageException;
import com.jiuqi.bi.bufgraph.storage.INodeStore;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class LuceneNodeStore
implements INodeStore {
    public static final String NODE_BASIC_FILE = "graph.nodestore";
    public static final String NODE_TYPE_FILE = "graph.nodestore.type";
    public static final int NODE_BIT_SIZE = 8;
    private String dir;
    private Directory directory;
    private IndexWriter idxWriter;
    private FileChannel nodeChannel;
    private ByteBuffer nodeBuffer;
    private AtomicInteger typeCounter = new AtomicInteger(1);
    private AtomicInteger nodeIdCounter = new AtomicInteger(0);
    private Map<String, NodeType> types;

    public LuceneNodeStore(String storePathDir, int initCapacity) throws GraphStorageException {
        this.dir = storePathDir;
        OpenOption[] opts = new OpenOption[]{StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING};
        try {
            this.directory = FSDirectory.open((Path)new File(this.dir, "index").toPath());
            IndexWriterConfig cfg = new IndexWriterConfig().setOpenMode(IndexWriterConfig.OpenMode.CREATE).setMaxBufferedDocs(5000);
            this.idxWriter = new IndexWriter(this.directory, cfg);
            this.nodeChannel = FileChannel.open(new File(this.dir, NODE_BASIC_FILE).toPath(), opts);
            this.nodeBuffer = this.nodeChannel.map(FileChannel.MapMode.READ_WRITE, 0L, initCapacity * 8);
        }
        catch (IOException e) {
            throw new GraphStorageException(e.getMessage(), e);
        }
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
        Document doc = new Document();
        doc.add((IndexableField)new StringField("uid", uid, Field.Store.YES));
        doc.add((IndexableField)new IntPoint("id", new int[]{node.getId()}));
        doc.add((IndexableField)new StoredField("id", node.getId()));
        doc.add((IndexableField)new StringField("type", type, Field.Store.YES));
        doc.add((IndexableField)new StringField("title", title, Field.Store.YES));
        if (props != null && !props.isEmpty()) {
            Set<Map.Entry<String, String>> entries = props.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                doc.add((IndexableField)new StoredField("_" + entry.getKey(), entry.getValue()));
            }
        }
        try {
            this.idxWriter.addDocument((Iterable)doc);
        }
        catch (IOException e) {
            throw new GraphStorageException(e.getMessage(), e);
        }
        return node;
    }

    @Override
    public void updateFirstRelationshipId(int nodeId, Direction direction, int relId) throws GraphStorageException {
        int pos = nodeId * 8;
        if (direction == Direction.OUTGOING) {
            pos += 4;
        }
        this.nodeBuffer.putInt(pos, relId);
    }

    @Override
    public INodeReader createReader() throws GraphStorageException {
        return new LuceneNodeReader(this.dir);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void finishAdd() throws GraphStorageException {
        Exception ex = null;
        try {
            this.idxWriter.flush();
            this.idxWriter.commit();
        }
        catch (IOException e) {
            ex = e;
        }
        try {
            this.nodeChannel.force(false);
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
        }
        catch (IOException e1) {
            throw new GraphStorageException(e1.getMessage(), e1);
        }
    }

    @Override
    public void close() throws GraphStorageException {
        try {
            this.idxWriter.close();
            this.directory.close();
            this.nodeChannel.close();
        }
        catch (IOException e) {
            throw new GraphStorageException(e.getMessage(), e);
        }
    }
}

