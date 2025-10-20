/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.bufgraph.model;

import com.jiuqi.bi.bufgraph.GraphException;
import com.jiuqi.bi.bufgraph.VisitLoopException;
import com.jiuqi.bi.bufgraph.model.Direction;
import com.jiuqi.bi.bufgraph.model.Edge;
import com.jiuqi.bi.bufgraph.model.IGraph;
import com.jiuqi.bi.bufgraph.model.Node;
import com.jiuqi.bi.bufgraph.model.Path;
import com.jiuqi.bi.bufgraph.model.Relationship;
import com.jiuqi.bi.bufgraph.reader.INodeReader;
import com.jiuqi.bi.bufgraph.reader.IRelationshipReader;
import com.jiuqi.bi.bufgraph.storage.GraphStorageException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

public class Graph
implements IGraph {
    private INodeReader nodeVisitor;
    private IRelationshipReader relationVisitor;

    public Graph(INodeReader nodeVisitor, IRelationshipReader relationVisitor) {
        this.nodeVisitor = nodeVisitor;
        this.relationVisitor = relationVisitor;
    }

    @Override
    public Node getNodeByUID(String uid) throws GraphException {
        return this.nodeVisitor.findByUid(uid);
    }

    @Override
    public Node getNodeById(int id) throws GraphException {
        return this.nodeVisitor.findById(id);
    }

    public int getNodeSize() throws GraphException {
        return this.nodeVisitor.size();
    }

    public int getEdgeSize() throws GraphException {
        return this.relationVisitor.size();
    }

    public List<Node> getAllNodes() throws GraphException {
        return this.nodeVisitor.list(null);
    }

    public List<Node> getNodesByType(String type) throws GraphException {
        return this.nodeVisitor.list(type);
    }

    public List<Edge> getAllEdges() throws GraphException {
        ArrayList<Edge> edges = new ArrayList<Edge>();
        List<Relationship> ships = this.relationVisitor.list(null);
        for (Relationship r : ships) {
            String hid = this.getNodeById(r.getHeadNodeId()).getUid();
            String tid = this.getNodeById(r.getTailNodeId()).getUid();
            edges.add(new Edge(r.getUid(), hid, tid, r.getType()));
        }
        return edges;
    }

    public List<Node> searchNodeByTitle(String keyword, boolean fuzzy) throws GraphException {
        return this.nodeVisitor.searchNodeByTitle(keyword, fuzzy);
    }

    public List<Node> searchNode(String propertyName, String keyword, boolean fuzzy) throws GraphException {
        return this.nodeVisitor.searchNode(propertyName, keyword, fuzzy);
    }

    @Override
    public List<Node> getRelatedNodes(String uid, Direction direction, String ... nodeTypes) throws GraphException {
        List<Path> paths;
        int nodeId = this.nodeVisitor.findIdByUid(uid);
        List<Path> list = paths = direction == Direction.INCOMING ? this._getIncomingPaths(nodeId) : this._getOutgoingPaths(nodeId);
        if (paths.isEmpty()) {
            return new ArrayList<Node>(0);
        }
        HashSet<String> types = null;
        if (nodeTypes.length > 0) {
            types = new HashSet<String>();
            for (String type : nodeTypes) {
                types.add(type);
            }
        }
        ArrayList<Node> nodes = new ArrayList<Node>();
        HashSet<Integer> set = new HashSet<Integer>();
        for (Path path : paths) {
            int[] ids;
            for (int id : ids = path.getNodeIdList()) {
                boolean notexist;
                if (id == nodeId || !(notexist = set.add(id))) continue;
                Node n = this.nodeVisitor.findById(id);
                if (nodeTypes.length != 0 && !types.contains(n.getType())) continue;
                nodes.add(n);
            }
        }
        return nodes;
    }

    private List<Relationship> getNodeIncomingRelationships(int id) throws GraphException {
        int rel_id = this.nodeVisitor.getFirstRelationshipId(id, Direction.INCOMING);
        if (rel_id == -1) {
            return new ArrayList<Relationship>(0);
        }
        ArrayList<Relationship> relations = new ArrayList<Relationship>();
        Relationship relationship = this.relationVisitor.findById(rel_id);
        relations.add(relationship);
        while (relationship.hasTailPrevRelationship()) {
            rel_id = relationship.getTailPrevRelationshipId();
            relationship = this.relationVisitor.findById(rel_id);
            relations.add(relationship);
        }
        return relations;
    }

    private List<Relationship> getNodeOutgoingRelationships(int id) throws GraphException {
        int relId;
        int rel_id = this.nodeVisitor.getFirstRelationshipId(id, Direction.OUTGOING);
        if (rel_id == -1) {
            return new ArrayList<Relationship>(0);
        }
        ArrayList<Relationship> relations = new ArrayList<Relationship>();
        Relationship relationship = this.relationVisitor.findById(rel_id);
        relations.add(relationship);
        int first = relationship.getId();
        while (relationship.hasHeadNextRelationship() && first != (relationship = this.relationVisitor.findById(relId = relationship.getHeadNextRelationshipId())).getId()) {
            relations.add(relationship);
        }
        return relations;
    }

    public List<Edge[]> getIncomingPaths(String uid) throws GraphException {
        int nodeId = this.nodeVisitor.findIdByUid(uid);
        if (nodeId == -1) {
            throw new GraphException("\u672a\u77e5\u7684\u8282\u70b9\uff1a" + uid);
        }
        List<Path> paths = this._getIncomingPaths(nodeId);
        return this.convert(paths);
    }

    @Override
    public List<Node> getDirectIncomingNodes(String uid) throws GraphException {
        int nodeId = this.nodeVisitor.findIdByUid(uid);
        if (nodeId == -1) {
            throw new GraphException("\u672a\u77e5\u7684\u8282\u70b9\uff1a" + uid);
        }
        List<Relationship> rels = this.getNodeIncomingRelationships(nodeId);
        if (rels.isEmpty()) {
            return new ArrayList<Node>(0);
        }
        ArrayList<Node> nodes = new ArrayList<Node>();
        for (Relationship rel : rels) {
            nodes.add(this.nodeVisitor.findById(rel.getHeadNodeId()));
        }
        return nodes;
    }

    @Override
    public List<Node> getDirectOutgoingNodes(String uid) throws GraphException {
        int nodeId = this.nodeVisitor.findIdByUid(uid);
        if (nodeId == -1) {
            throw new GraphException("\u672a\u77e5\u7684\u8282\u70b9\uff1a" + uid);
        }
        List<Relationship> rels = this.getNodeOutgoingRelationships(nodeId);
        if (rels.isEmpty()) {
            return new ArrayList<Node>(0);
        }
        ArrayList<Node> nodes = new ArrayList<Node>();
        for (Relationship rel : rels) {
            nodes.add(this.nodeVisitor.findById(rel.getTailNodeId()));
        }
        return nodes;
    }

    public List<Edge[]> getOutgoingPaths(String uid) throws GraphException {
        int nodeId = this.nodeVisitor.findIdByUid(uid);
        if (nodeId == -1) {
            throw new GraphException("\u672a\u77e5\u7684\u8282\u70b9\uff1a" + uid);
        }
        List<Path> paths = this._getOutgoingPaths(nodeId);
        return this.convert(paths);
    }

    public List<Edge> getDirectOutgoingEdges(String uid) throws GraphException {
        List<Relationship> relationships = this.getNodeOutgoingRelationships(this.getNodeByUID(uid).getId());
        ArrayList<Edge> edges = new ArrayList<Edge>();
        for (Relationship r : relationships) {
            edges.add(new Edge(r.uid, this.getNodeById((int)r.getHeadNodeId()).uid, this.getNodeById((int)r.getTailNodeId()).uid, r.getType()));
        }
        return edges;
    }

    public List<Edge> getDirectIncomingEdges(String uid) throws GraphException {
        List<Relationship> relationships = this.getNodeIncomingRelationships(this.getNodeByUID(uid).getId());
        ArrayList<Edge> edges = new ArrayList<Edge>();
        for (Relationship r : relationships) {
            edges.add(new Edge(r.uid, this.getNodeById((int)r.getHeadNodeId()).uid, this.getNodeById((int)r.getTailNodeId()).uid, r.getType()));
        }
        return edges;
    }

    private List<Path> _getIncomingPaths(int nodeId) throws GraphException {
        List<Relationship> rels = this.getNodeIncomingRelationships(nodeId);
        if (rels.isEmpty()) {
            return new ArrayList<Path>(0);
        }
        ArrayList<Path> paths = new ArrayList<Path>();
        Stack<Path> stack = new Stack<Path>();
        for (Relationship rel : rels) {
            Path path = new Path();
            path.addRelationship(rel);
            stack.add(path);
        }
        this.dfsTraverseIncomingPath(stack, paths, -1, true);
        return paths;
    }

    private List<Path> _getOutgoingPaths(int nodeId) throws GraphException {
        List<Relationship> rels = this.getNodeOutgoingRelationships(nodeId);
        if (rels.isEmpty()) {
            return new ArrayList<Path>(0);
        }
        ArrayList<Path> paths = new ArrayList<Path>();
        Stack<Path> stack = new Stack<Path>();
        for (Relationship rel : rels) {
            Path path = new Path();
            path.addRelationship(rel);
            stack.add(path);
        }
        this.bfsTraverseOutcomingPath(stack, paths, -1, true);
        return paths;
    }

    public Set<Edge> subGraph(List<String> nodeIds) throws GraphException {
        HashSet<Edge> set = new HashSet<Edge>();
        for (String nodeId : nodeIds) {
            List<Edge[]> incoming = this.getIncomingPaths(nodeId);
            incoming.forEach(c -> set.addAll(Arrays.asList(c)));
            List<Edge[]> outgoing = this.getOutgoingPaths(nodeId);
            outgoing.forEach(c -> set.addAll(Arrays.asList(c)));
            if (!incoming.isEmpty() || !outgoing.isEmpty()) continue;
            set.add(new Edge(nodeId, nodeId, nodeId, "self"));
        }
        return set;
    }

    public List<String> getOnlyAncestor(List<String> nodeIds) throws GraphException {
        ArrayList<Set<String>> data = new ArrayList<Set<String>>();
        for (String nodeId : nodeIds) {
            List<Edge[]> incoming = this.getIncomingPaths(nodeId);
            if (incoming.isEmpty()) {
                if (this.getNodeByUID(nodeId) == null) continue;
                data.add(Collections.singleton(nodeId));
                continue;
            }
            Set headIds = incoming.stream().map(c -> c[0].getHead()).collect(Collectors.toSet());
            data.add(headIds);
        }
        return this.intersect(data);
    }

    public boolean isSameAncestor(List<String> nodeIds) throws GraphException {
        return !this.getOnlyAncestor(nodeIds).isEmpty();
    }

    public int getDistance(String sourceId, String leafId) throws GraphException {
        int source = this.nodeVisitor.findIdByUid(sourceId);
        if (source == -1) {
            throw new GraphException("\u672a\u77e5\u7684\u8282\u70b9\uff1a" + sourceId);
        }
        int leaf = this.nodeVisitor.findIdByUid(leafId);
        if (leaf == -1) {
            throw new GraphException("\u672a\u77e5\u7684\u8282\u70b9\uff1a" + leafId);
        }
        int distance = Integer.MAX_VALUE;
        List<Path> paths = this._getIncomingPaths(leaf);
        for (Path path : paths) {
            if (!path.contains(source)) continue;
            int len = 0;
            int[] nodeIds = path.getNodeIdList();
            for (int i = nodeIds.length - 1; i >= 0; --i) {
                ++len;
                if (nodeIds[i] == source) break;
            }
            if (len <= 0 || len >= distance) continue;
            distance = len;
        }
        return distance == Integer.MAX_VALUE ? -1 : distance;
    }

    public Node getNearestRootNode(Set<String> nodeIds) throws GraphException {
        ArrayList<List<String>> data = new ArrayList<List<String>>();
        for (String nodeId : nodeIds) {
            List<Edge[]> incoming = this.getIncomingPaths(nodeId);
            if (incoming.isEmpty()) {
                if (this.getNodeByUID(nodeId) == null) continue;
                data.add(Arrays.asList(nodeId));
                continue;
            }
            for (Edge[] edges : incoming) {
                ArrayList<String> ll = new ArrayList<String>();
                for (Edge edge : edges) {
                    ll.add(edge.getHead());
                }
                ll.add(nodeId);
                data.add(ll);
            }
        }
        List first = (List)data.get(0);
        for (int i = 1; i < data.size(); ++i) {
            first.retainAll((Collection)data.get(i));
        }
        return first.isEmpty() ? null : this.getNodeByUID((String)first.get(first.size() - 1));
    }

    public List<String> getWholeAncestor(List<String> nodeIds) throws GraphException {
        HashSet<String> data = new HashSet<String>();
        for (String nodeId : nodeIds) {
            List<Edge[]> incoming = this.getIncomingPaths(nodeId);
            if (incoming.isEmpty()) {
                if (this.getNodeByUID(nodeId) == null) continue;
                data.add(nodeId);
                continue;
            }
            Set headIds = incoming.stream().map(c -> c[0].getHead()).collect(Collectors.toSet());
            data.addAll(headIds);
        }
        return new ArrayList<String>(data);
    }

    @Override
    public List<Edge[]> getPaths(String src_uid, String dst_uid) throws GraphException {
        int src_id = this.nodeVisitor.findIdByUid(src_uid);
        if (src_id == -1) {
            throw new GraphException("\u672a\u77e5\u7684\u8282\u70b9\uff1a" + src_uid);
        }
        List<Relationship> rels = this.getNodeOutgoingRelationships(src_id);
        if (rels.isEmpty()) {
            return new ArrayList<Edge[]>(0);
        }
        int dst_id = this.nodeVisitor.findIdByUid(dst_uid);
        if (dst_id == -1) {
            throw new GraphException("\u672a\u77e5\u7684\u8282\u70b9\uff1a" + dst_uid);
        }
        ArrayList<Path> paths = new ArrayList<Path>();
        Stack<Path> stack = new Stack<Path>();
        for (Relationship rel : rels) {
            Path path = new Path();
            path.addRelationship(rel);
            if (rel.getTailNodeId() == dst_id) {
                paths.add(path);
                continue;
            }
            stack.add(path);
        }
        this.bfsTraverseOutcomingPath(stack, paths, dst_id, true);
        return this.convert(paths);
    }

    @Override
    public List<Edge[]> getShortestPaths(Node source, Node target) throws GraphException {
        int sid = source.getFirstRelationshipId(Direction.OUTGOING);
        if (sid == -1) {
            return null;
        }
        Stack<Path> stack = new Stack<Path>();
        Path path = new Path();
        path.addRelationship(this.relationVisitor.findById(sid));
        stack.add(path);
        ArrayList<Path> paths = new ArrayList<Path>();
        this.bfsTraverseOutcomingPath(stack, paths, target.id, true);
        return this.convert(paths);
    }

    @Override
    public synchronized List<Node> topo(boolean allowLoop) throws GraphException {
        int i;
        ArrayList<Node> temp;
        int size = this.nodeVisitor.size();
        ArrayList<Node> unresolved = new ArrayList<Node>(size);
        for (int i2 = 0; i2 < size; ++i2) {
            unresolved.add(this.nodeVisitor.findById(i2));
        }
        ArrayList<Node> resolved = new ArrayList<Node>();
        do {
            temp = new ArrayList<Node>();
            for (Node node : unresolved) {
                if (!node.hasRelationship(Direction.INCOMING)) {
                    temp.add(node);
                    continue;
                }
                boolean alldeleted = true;
                List<Relationship> relationships = this.getNodeIncomingRelationships(node.id);
                for (Relationship r : relationships) {
                    if (r.containProperty("_flag")) continue;
                    alldeleted = false;
                    break;
                }
                if (!alldeleted) continue;
                temp.add(node);
            }
            for (Node node : temp) {
                node.setProperty("_flag", 1);
                List<Relationship> relationships = this.getNodeOutgoingRelationships(node.id);
                for (Relationship r : relationships) {
                    r.setProperty("_flag", 1);
                }
            }
            resolved.addAll(temp);
            unresolved.removeAll(temp);
        } while (!temp.isEmpty());
        size = this.nodeVisitor.size();
        for (i = 0; i < size; ++i) {
            this.nodeVisitor.findById(i).removeProperty("_flag");
        }
        size = this.relationVisitor.size();
        for (i = 0; i < size; ++i) {
            this.relationVisitor.findById(i).removeProperty("_flag");
        }
        if (!unresolved.isEmpty()) {
            if (allowLoop) {
                resolved.addAll(unresolved);
            } else {
                StringBuilder b = new StringBuilder();
                b.append("\u62d3\u6251\u56fe\u4e2d\u5b58\u5728\u73af\u8def\uff1a");
                for (int i3 = 0; i3 < unresolved.size(); ++i3) {
                    if (i3 != 0) {
                        b.append("->");
                    }
                    b.append(((Node)unresolved.get(i3)).getTitle());
                }
                throw new VisitLoopException(b.toString());
            }
        }
        return resolved;
    }

    public boolean testLoop() throws GraphException {
        try {
            this.topo(false);
            return false;
        }
        catch (VisitLoopException e) {
            return true;
        }
    }

    @Override
    public int getDegree(String uid, Direction direction, String ... relTypes) throws GraphException {
        List<Relationship> relationships;
        int id = this.nodeVisitor.findIdByUid(uid);
        if (id == -1) {
            throw new GraphException("\u672a\u77e5\u7684\u8282\u70b9\uff1a" + uid);
        }
        List<Relationship> list = relationships = direction == Direction.OUTGOING ? this.getNodeOutgoingRelationships(id) : this.getNodeIncomingRelationships(id);
        if (relationships.isEmpty()) {
            return 0;
        }
        if (relTypes.length > 0) {
            int size = 0;
            HashSet<String> types = new HashSet<String>();
            for (String type : relTypes) {
                types.add(type);
            }
            for (Relationship rel : relationships) {
                if (!types.contains(rel.getType())) continue;
                ++size;
            }
            return size;
        }
        return relationships.size();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void saveToCsv(String dir) throws GraphException, IOException {
        File nodeFile = new File(dir, "node.csv");
        File relFile = new File(dir, "rel.csv");
        if (!nodeFile.exists()) {
            nodeFile.createNewFile();
        }
        if (!relFile.exists()) {
            relFile.createNewFile();
        }
        int nodeSize = this.nodeVisitor.size();
        String sep = ",";
        FileWriter fw = new FileWriter(nodeFile);
        BufferedWriter bw = new BufferedWriter(fw, 1024);
        try {
            bw.append("uid,type,title");
            Node n = this.nodeVisitor.findById(0);
            Set<String> props = n.propertyKeys();
            for (String prop : props) {
                bw.append(sep).append(prop);
            }
            bw.append(System.getProperty("line.separator"));
            for (int i = 0; i < nodeSize; ++i) {
                Node node = this.nodeVisitor.findById(i);
                bw.append(node.uid).append(sep).append(node.getType()).append(sep).append(node.getTitle());
                for (String prop : props) {
                    bw.append(sep).append(node.getProperty(prop, "").toString());
                }
                bw.append(System.getProperty("line.separator"));
            }
        }
        finally {
            bw.close();
            fw.close();
        }
        int relSize = this.relationVisitor.size();
        fw = new FileWriter(relFile);
        bw = new BufferedWriter(fw, 1024);
        try {
            bw.append("head,tail,type");
            Relationship r = this.relationVisitor.findById(0);
            Set<String> props = r.propertyKeys();
            for (String prop : props) {
                bw.append(sep).append(prop);
            }
            bw.append(System.getProperty("line.separator"));
            for (int i = 0; i < relSize; ++i) {
                Relationship rel = this.relationVisitor.findById(i);
                Node head = this.nodeVisitor.findById(rel.getHeadNodeId());
                Node tail = this.nodeVisitor.findById(rel.getTailNodeId());
                bw.append(head.uid).append(sep).append(tail.uid).append(sep).append(rel.getType());
                for (String prop : props) {
                    bw.append(sep).append(rel.getProperty(prop, "").toString());
                }
                bw.append(System.getProperty("line.separator"));
            }
        }
        finally {
            bw.close();
            fw.close();
        }
    }

    public void release() throws GraphException {
        this.nodeVisitor.close();
        this.relationVisitor.close();
    }

    private void dfsTraverseIncomingPath(Stack<Path> stack, List<Path> paths, int startNodeId, boolean allowLoop) throws GraphException {
        while (!stack.isEmpty()) {
            Path path = stack.pop();
            Relationship relationship = path.getFirst();
            List<Relationship> rels = this.getNodeIncomingRelationships(relationship.getHeadNodeId());
            for (Relationship rel : rels) {
                Path newpath = path.clone();
                newpath.insertRelationshipInHeader(rel);
                if (startNodeId != -1 && rel.getHeadNodeId() == startNodeId) {
                    paths.add(newpath);
                    continue;
                }
                if (path.contains(rel.getHeadNodeId())) {
                    if (allowLoop) break;
                    throw new VisitLoopException(newpath);
                }
                stack.add(newpath);
            }
            if ((startNodeId == -1 || startNodeId != relationship.getHeadNodeId()) && (startNodeId != -1 || relationship.hasHeadPrevRelationship())) continue;
            paths.add(path);
        }
    }

    private void bfsTraverseOutcomingPath(Stack<Path> stack, List<Path> paths, int endNodeId, boolean allowLoop) throws GraphException {
        block0: while (!stack.isEmpty()) {
            Path path = stack.pop();
            Relationship relationship = path.getLast();
            if (endNodeId != -1 && endNodeId == relationship.getTailNodeId() || endNodeId == -1 && !relationship.hasTailNextRelationship()) {
                paths.add(path);
            }
            List<Relationship> rels = this.getNodeOutgoingRelationships(relationship.getTailNodeId());
            for (Relationship rel : rels) {
                Path newpath = path.clone();
                newpath.addRelationship(rel);
                if (endNodeId != -1 && rel.getTailNodeId() == endNodeId) {
                    paths.add(newpath);
                    continue;
                }
                if (path.contains(rel.getTailNodeId())) {
                    if (allowLoop) continue block0;
                    throw new VisitLoopException(newpath);
                }
                stack.add(newpath);
            }
        }
    }

    private Edge convert(Relationship relation) throws GraphStorageException {
        Node h = this.nodeVisitor.findById(relation.getHeadNodeId());
        Node t = this.nodeVisitor.findById(relation.getTailNodeId());
        return new Edge(relation.getUid(), h.getUid(), t.getUid(), relation.getType());
    }

    private List<Edge[]> convert(List<Path> paths) throws GraphStorageException {
        ArrayList<Edge[]> edges = new ArrayList<Edge[]>();
        for (Path p : paths) {
            Edge[] tmp = new Edge[p.length()];
            for (int i = 0; i < tmp.length; ++i) {
                tmp[i] = this.convert(p.get(i));
            }
            edges.add(tmp);
        }
        return edges;
    }

    private List<String> intersect(List<Set<String>> data) {
        ArrayList<String> result = new ArrayList<String>();
        if (data.isEmpty()) {
            return result;
        }
        result.addAll((Collection<String>)data.get(0));
        for (int i = 1; i < data.size(); ++i) {
            Set<String> set = data.get(i);
            Iterator itor = result.iterator();
            while (itor.hasNext()) {
                if (set.contains(itor.next())) continue;
                itor.remove();
            }
            if (result.isEmpty()) break;
        }
        return result;
    }
}

