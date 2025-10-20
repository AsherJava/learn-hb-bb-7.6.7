/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.lucene.document.Document
 *  org.apache.lucene.document.IntPoint
 *  org.apache.lucene.index.DirectoryReader
 *  org.apache.lucene.index.IndexReader
 *  org.apache.lucene.index.IndexableField
 *  org.apache.lucene.index.Term
 *  org.apache.lucene.search.IndexSearcher
 *  org.apache.lucene.search.Query
 *  org.apache.lucene.search.ScoreDoc
 *  org.apache.lucene.search.TermQuery
 *  org.apache.lucene.search.TopDocs
 *  org.apache.lucene.store.Directory
 *  org.apache.lucene.store.FSDirectory
 */
package com.jiuqi.bi.bufgraph.reader.luc;

import com.jiuqi.bi.bufgraph.GraphException;
import com.jiuqi.bi.bufgraph.model.Direction;
import com.jiuqi.bi.bufgraph.model.Node;
import com.jiuqi.bi.bufgraph.model.NodeType;
import com.jiuqi.bi.bufgraph.reader.INodeReader;
import com.jiuqi.bi.bufgraph.storage.GraphStorageException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class LuceneNodeReader
implements INodeReader {
    private String dir;
    private Directory directory;
    private IndexReader idxReader;
    private IndexSearcher idxSearcher;
    private FileChannel nodeChannel;
    private MappedByteBuffer nodeBuffer;
    private Map<Byte, NodeType> types;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public LuceneNodeReader(String dir) throws GraphStorageException {
        this.dir = dir;
        try {
            this.directory = FSDirectory.open((Path)new File(this.dir, "index").toPath());
            this.idxReader = DirectoryReader.open((Directory)this.directory);
            this.idxSearcher = new IndexSearcher(this.idxReader);
            this.nodeChannel = FileChannel.open(new File(this.dir, "graph.nodestore").toPath(), StandardOpenOption.READ);
            this.nodeBuffer = this.nodeChannel.map(FileChannel.MapMode.READ_ONLY, 0L, this.nodeChannel.size());
        }
        catch (IOException e) {
            throw new GraphStorageException(e.getMessage(), e);
        }
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
        try {
            return (int)(this.nodeChannel.size() / 9L);
        }
        catch (IOException e) {
            throw new GraphStorageException(e.getMessage(), e);
        }
    }

    @Override
    public int getFirstRelationshipId(int id, Direction direction) throws GraphStorageException {
        int pos = id * 8;
        if (direction == Direction.OUTGOING) {
            pos += 4;
        }
        this.nodeBuffer.position(pos);
        return this.nodeBuffer.getInt();
    }

    @Override
    public Node findById(int id) throws GraphStorageException {
        Query query = IntPoint.newExactQuery((String)"id", (int)id);
        try {
            TopDocs topDoc = this.idxSearcher.search(query, 1);
            ScoreDoc[] scoreDocs = topDoc.scoreDocs;
            if (scoreDocs.length > 0) {
                Document doc = this.idxSearcher.doc(scoreDocs[0].doc);
                return this.transDocument(doc);
            }
            return null;
        }
        catch (IOException e) {
            throw new GraphStorageException(e.getMessage(), e);
        }
    }

    private Node transDocument(Document doc) throws IOException {
        int id = doc.getField("id").numericValue().intValue();
        String uid = doc.get("uid");
        String title = doc.get("title");
        ByteBuffer dst = ByteBuffer.allocate(8);
        int pos = this.getNodeStorePosition(id);
        this.nodeChannel.position(pos);
        this.nodeChannel.read(dst);
        byte type = dst.get(0);
        int incoming = dst.getInt(0);
        int outgoing = dst.getInt(4);
        Node node = new Node(id, uid, title, this.types.get(type));
        node.setFirstRelationshipId(Direction.INCOMING, incoming);
        node.setFirstRelationshipId(Direction.OUTGOING, outgoing);
        for (IndexableField field : doc) {
            if (field.name().charAt(0) != '_') continue;
            node.setProperty(field.name().substring(1), field.stringValue());
        }
        return node;
    }

    private int getNodeStorePosition(int id) {
        return id * 8;
    }

    @Override
    public Node findByUid(String uid) throws GraphStorageException {
        TermQuery query = new TermQuery(new Term("uid", uid));
        try {
            TopDocs topDoc = this.idxSearcher.search((Query)query, 1);
            ScoreDoc[] scoreDocs = topDoc.scoreDocs;
            if (scoreDocs.length > 0) {
                Document doc = this.idxSearcher.doc(scoreDocs[0].doc);
                return this.transDocument(doc);
            }
            return null;
        }
        catch (IOException e) {
            throw new GraphStorageException(e.getMessage(), e);
        }
    }

    @Override
    public int findIdByUid(String uid) throws GraphStorageException {
        TermQuery query = new TermQuery(new Term("uid", uid));
        try {
            TopDocs topDoc = this.idxSearcher.search((Query)query, 1);
            ScoreDoc[] scoreDocs = topDoc.scoreDocs;
            if (scoreDocs.length > 0) {
                Document doc = this.idxSearcher.doc(scoreDocs[0].doc);
                return doc.getField("id").numericValue().intValue();
            }
            return -1;
        }
        catch (IOException e) {
            throw new GraphStorageException(e.getMessage(), e);
        }
    }

    @Override
    public List<Node> searchNode(String propertyName, Object searchValue, boolean fuzzy) throws GraphException {
        List<Node> nodes = this.list(null);
        ArrayList<Node> list = new ArrayList<Node>();
        if (fuzzy) {
            String sv = searchValue.toString().toLowerCase();
            nodes.forEach(n -> {
                Object v = n.getProperty(propertyName);
                if (v != null && v.toString().toLowerCase().contains(sv)) {
                    list.add((Node)n);
                }
            });
        } else {
            nodes.forEach(n -> {
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
        return this.search("title", keyword);
    }

    @Override
    public List<Node> list(String type) throws GraphStorageException {
        if (type == null) {
            type = "*";
        }
        return this.search("type", type);
    }

    private List<Node> search(String prop, String value) throws GraphStorageException {
        TermQuery query = new TermQuery(new Term(prop, value));
        try {
            TopDocs topDoc = this.idxSearcher.search((Query)query, Integer.MAX_VALUE);
            ScoreDoc[] scoreDocs = topDoc.scoreDocs;
            ArrayList<Node> nodes = new ArrayList<Node>();
            if (scoreDocs.length > 0) {
                for (ScoreDoc sd : scoreDocs) {
                    Document doc = this.idxSearcher.doc(sd.doc);
                    nodes.add(this.transDocument(doc));
                }
            }
            return nodes;
        }
        catch (IOException e) {
            throw new GraphStorageException(e.getMessage(), e);
        }
    }

    @Override
    public void close() throws GraphStorageException {
        IOException e = null;
        try {
            try {
                this.idxReader.close();
            }
            finally {
                this.directory.close();
            }
        }
        catch (IOException e1) {
            e = e1;
        }
        try {
            this.nodeChannel.close();
        }
        catch (IOException e1) {
            e = e1;
        }
        if (e != null) {
            throw new GraphStorageException(e.getMessage(), e);
        }
    }
}

