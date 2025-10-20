/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.bufgraph;

import com.jiuqi.bi.bufgraph.GraphException;
import com.jiuqi.bi.bufgraph.IntValue;
import com.jiuqi.bi.bufgraph.NodeRelationRecorder;
import com.jiuqi.bi.bufgraph.model.Direction;
import com.jiuqi.bi.bufgraph.model.Graph;
import com.jiuqi.bi.bufgraph.model.Node;
import com.jiuqi.bi.bufgraph.model.Relationship;
import com.jiuqi.bi.bufgraph.reader.INodeReader;
import com.jiuqi.bi.bufgraph.reader.IRelationshipReader;
import com.jiuqi.bi.bufgraph.reader.disk.DiskNodeReader;
import com.jiuqi.bi.bufgraph.reader.disk.DiskRelationshipReader;
import com.jiuqi.bi.bufgraph.reader.luc.LuceneNodeReader;
import com.jiuqi.bi.bufgraph.storage.GraphStorageFactory;
import com.jiuqi.bi.bufgraph.storage.INodeStore;
import com.jiuqi.bi.bufgraph.storage.IRelationshipStore;
import com.jiuqi.bi.bufgraph.storage.disk.DiskRelationshipStore;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GraphBuilder {
    private INodeStore nodeStorage;
    private IRelationshipStore relStorage;
    private INodeReader nodeReader;
    private IRelationshipReader relReader;
    private NodeRelationRecorder recorder;
    private boolean ignoreNotExistNode = false;

    private GraphBuilder() {
    }

    public static GraphBuilder newMemoryGraphBuilder(int initNodeSize, int initRelSize) throws GraphException {
        GraphBuilder builder = new GraphBuilder();
        builder.nodeStorage = GraphStorageFactory.createNodeStorage(initNodeSize, false, null);
        builder.relStorage = GraphStorageFactory.createRelationshipStorage(initRelSize, null);
        builder.recorder = new NodeRelationRecorder(initNodeSize);
        return builder;
    }

    public static GraphBuilder newDiskGraphBuilder(int initNodeSize, int initRelSize, String storePath) throws GraphException {
        if (storePath == null) {
            storePath = System.getProperty("user.dir") + "/" + System.currentTimeMillis();
        }
        GraphBuilder builder = new GraphBuilder();
        builder.nodeStorage = GraphStorageFactory.createNodeStorage(initNodeSize, false, storePath);
        builder.relStorage = new DiskRelationshipStore(storePath, initRelSize);
        builder.recorder = new NodeRelationRecorder(initNodeSize);
        return builder;
    }

    public static GraphBuilder newGraphBuilder(INodeStore nodeStore, IRelationshipStore relStore) {
        GraphBuilder builder = new GraphBuilder();
        builder.nodeStorage = nodeStore;
        builder.relStorage = relStore;
        builder.recorder = new NodeRelationRecorder();
        return builder;
    }

    public static Graph loadGraphFromDisk(String storePath) throws IOException, GraphException {
        File f = new File(storePath);
        if (!f.exists() || !f.isDirectory()) {
            throw new IOException("\u6307\u5b9a\u7684\u76ee\u5f55\u4e0d\u5b58\u5728\uff1a" + storePath);
        }
        if (!f.canRead()) {
            throw new IOException("\u5f53\u524d\u7528\u6237\u5bf9\u8be5\u76ee\u5f55\u6ca1\u6709\u8bbf\u95ee\u6743\u9650\uff1a" + storePath);
        }
        f = new File(storePath, "index");
        INodeReader nr = f.exists() ? new LuceneNodeReader(storePath) : new DiskNodeReader(storePath);
        DiskRelationshipReader rr = new DiskRelationshipReader(storePath);
        return new Graph(nr, rr);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Graph loadGraphFromCsv(File nodeFile, File relFile, String separator) throws IOException, GraphException {
        HashMap<String, String> props;
        String[] data;
        String[] columns;
        int relLines;
        int nodeLines;
        String line = null;
        try (FileInputStream input = new FileInputStream(nodeFile);
             LineNumberReader r = new LineNumberReader(new InputStreamReader(input));){
            r.skip(nodeFile.length());
            nodeLines = r.getLineNumber();
        }
        input = new FileInputStream(relFile);
        try (LineNumberReader r = new LineNumberReader(new InputStreamReader(input));){
            r.skip(nodeFile.length());
            relLines = r.getLineNumber();
        }
        finally {
            input.close();
        }
        GraphBuilder builder = GraphBuilder.newMemoryGraphBuilder(nodeLines - 1, relLines - 1);
        builder.setIgnoreNotExistNode(true);
        input = new FileInputStream(nodeFile);
        try (BufferedReader bufferReader = new BufferedReader(new InputStreamReader((InputStream)input, "utf-8"));){
            line = bufferReader.readLine();
            columns = line.split(separator);
            while ((line = bufferReader.readLine()) != null) {
                data = line.split(separator);
                if (data.length < 3) continue;
                props = null;
                if (data.length > 3 && columns.length > 3) {
                    props = new HashMap<String, String>();
                    int min = data.length > columns.length ? columns.length : data.length;
                    for (int i = 3; i < min; ++i) {
                        props.put(columns[i], data[i]);
                    }
                }
                builder.addNode(data[0], data[1], data[2], props);
            }
        }
        input = new FileInputStream(relFile);
        bufferReader = new BufferedReader(new InputStreamReader((InputStream)input, "utf-8"));
        try {
            line = bufferReader.readLine();
            columns = line.split(separator);
            while ((line = bufferReader.readLine()) != null) {
                data = line.split(separator);
                if (data.length < 2) continue;
                props = null;
                if (data.length > 3 && columns.length > 3) {
                    props = new HashMap();
                    for (int i = 3; i < data.length; ++i) {
                        props.put(columns[i], data[i]);
                    }
                }
                String type = data.length > 2 ? data[2] : "default";
                builder.addRelationship(type, data[0], data[1], props);
            }
        }
        finally {
            bufferReader.close();
        }
        return builder.build();
    }

    public void setIgnoreNotExistNode(boolean ignoreNotExistNode) {
        this.ignoreNotExistNode = ignoreNotExistNode;
    }

    public Node addNode(String uid, String type, String title, Map<String, String> props) throws GraphException {
        Node node = this.nodeStorage.addNode(uid, type, title, props);
        return node;
    }

    public Node addNode(String uid, String title) throws GraphException {
        return this.addNode(uid, "", title, null);
    }

    public Relationship addRelationship(String type, String headUid, String tailUid, Map<String, String> props) throws GraphException {
        if (this.nodeReader == null) {
            this.nodeStorage.finishAdd();
            this.nodeReader = this.nodeStorage.createReader();
        }
        int head = this.nodeReader.findIdByUid(headUid);
        int tail = this.nodeReader.findIdByUid(tailUid);
        if (head == -1) {
            if (!this.ignoreNotExistNode) {
                throw new GraphException("\u672a\u77e5\u7684\u8282\u70b9\uff1a" + headUid);
            }
            return null;
        }
        if (tail == -1) {
            if (!this.ignoreNotExistNode) {
                throw new GraphException("\u672a\u77e5\u7684\u8282\u70b9\uff1a" + tailUid);
            }
            return null;
        }
        Relationship rel = this.relStorage.addRelationship(type, head, tail, props);
        this.recorder.addIncomingRel(tail, rel.getId());
        this.recorder.addOutgoingRel(head, rel.getId());
        return rel;
    }

    public Relationship addRelationship(String headUid, String tailUid) throws GraphException {
        return this.addRelationship("", headUid, tailUid, null);
    }

    public Graph build() throws GraphException {
        List<Integer> rel_ids;
        int nodeId;
        Iterator<IntValue> nodeIds = this.recorder.getIncomingNodeIds();
        while (nodeIds.hasNext()) {
            nodeId = nodeIds.next().intValue();
            rel_ids = this.recorder.getIncomingRels(nodeId);
            this.nodeStorage.updateFirstRelationshipId(nodeId, Direction.INCOMING, rel_ids.get(0));
        }
        nodeIds = this.recorder.getOutgoingNodeIds();
        while (nodeIds.hasNext()) {
            nodeId = nodeIds.next().intValue();
            rel_ids = this.recorder.getOutgoingRels(nodeId);
            this.nodeStorage.updateFirstRelationshipId(nodeId, Direction.OUTGOING, rel_ids.get(0));
        }
        this.nodeStorage.finishUpdate();
        if (this.relReader == null) {
            this.relStorage.finishAdd();
            this.relReader = this.relStorage.createReader();
        }
        int relSize = this.relReader.size();
        for (int i = 0; i < relSize; ++i) {
            Integer relId;
            Relationship rel = this.relReader.findById(i);
            int headPrev = -1;
            int headNext = -1;
            int tailPrev = -1;
            int tailNext = -1;
            List<Integer> rel_ids2 = this.recorder.getIncomingRels(rel.getHeadNodeId());
            if (rel_ids2 != null && (relId = this.getNeighbourRelationId(rel.getId(), rel_ids2, false, false)) != null) {
                headPrev = relId;
            }
            if ((rel_ids2 = this.recorder.getOutgoingRels(rel.getHeadNodeId())) != null && (relId = this.getNeighbourRelationId(rel.getId(), rel_ids2, true, false)) != null) {
                headNext = relId;
            }
            if ((rel_ids2 = this.recorder.getIncomingRels(rel.getTailNodeId())) != null && (relId = this.getNeighbourRelationId(rel.getId(), rel_ids2, true, true)) != null) {
                tailPrev = relId;
            }
            if ((rel_ids2 = this.recorder.getOutgoingRels(rel.getTailNodeId())) != null && (relId = this.getNeighbourRelationId(rel.getId(), rel_ids2, true, false)) != null) {
                tailNext = relId;
            }
            this.relStorage.updateRelatedRelationshipId(i, headPrev, headNext, tailPrev, tailNext);
        }
        this.recorder.clearRelationInfos();
        this.relStorage.finishUpdate();
        this.nodeStorage.close();
        this.relStorage.close();
        return new Graph(this.nodeReader, this.relReader);
    }

    private Integer getNeighbourRelationId(int curr_rel_id, List<Integer> rel_ids, boolean morethan, boolean must) {
        if (morethan) {
            int size = rel_ids.size();
            if (rel_ids.get(size - 1) < curr_rel_id) {
                return must ? null : rel_ids.get(size - 1);
            }
            for (int p = 0; p < rel_ids.size(); ++p) {
                Integer relId = rel_ids.get(p);
                if (relId <= curr_rel_id) continue;
                return relId;
            }
            if (must) {
                return null;
            }
            return this.getFirstValidRelId(rel_ids, curr_rel_id);
        }
        if (rel_ids.get(0) > curr_rel_id) {
            return must ? null : rel_ids.get(0);
        }
        for (int p = rel_ids.size() - 1; p >= 0; --p) {
            Integer relId = rel_ids.get(p);
            if (relId >= curr_rel_id) continue;
            return relId;
        }
        if (must) {
            return null;
        }
        return this.getFirstValidRelId(rel_ids, curr_rel_id);
    }

    private Integer getFirstValidRelId(List<Integer> rel_ids, int curr_rel_id) {
        for (int p = 0; p < rel_ids.size(); ++p) {
            Integer relId = rel_ids.get(p);
            if (relId == curr_rel_id) continue;
            return relId;
        }
        return null;
    }
}

