/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.graph.internal;

import com.jiuqi.nr.graph.IDataWrapper;
import com.jiuqi.nr.graph.IEdge;
import com.jiuqi.nr.graph.INode;
import com.jiuqi.nr.graph.define.EdgeDefine;
import com.jiuqi.nr.graph.define.NodeDefine;
import com.jiuqi.nr.graph.exception.GraphException;
import com.jiuqi.nr.graph.internal.DataWrapper;
import com.jiuqi.nr.graph.internal.SimilarEdges;
import com.jiuqi.nr.graph.label.EdgeLabel;
import com.jiuqi.nr.graph.util.GraphUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.springframework.util.Assert;

public class Node
extends DataWrapper
implements INode {
    private final NodeDefine nodeDefine;
    private final SimilarEdges[] outEdgePool;
    private final SimilarEdges[] inEdgePool;

    protected Node(NodeDefine nodeDefine, Object data) {
        super.init(nodeDefine.getLabel(), nodeDefine.getKeyGetter(), data);
        this.nodeDefine = nodeDefine;
        this.outEdgePool = new SimilarEdges[this.nodeDefine.getOutEdgeDefinesSize()];
        this.inEdgePool = new SimilarEdges[this.nodeDefine.getInEdgeDefinesSize()];
    }

    protected Node(NodeDefine nodeDefine, IDataWrapper data) {
        super(data);
        this.nodeDefine = nodeDefine;
        this.outEdgePool = new SimilarEdges[this.nodeDefine.getOutEdgeDefinesSize()];
        this.inEdgePool = new SimilarEdges[this.nodeDefine.getInEdgeDefinesSize()];
    }

    private SimilarEdges getOutEdges(EdgeLabel edgeLabel) {
        return this.getOutEdges(edgeLabel, SimilarEdges::emptyAdjacentNodes);
    }

    private SimilarEdges getInEdges(EdgeLabel edgeLabel) {
        return this.getInEdges(edgeLabel, SimilarEdges::emptyAdjacentNodes);
    }

    private SimilarEdges getOutEdges(EdgeLabel edgeLabel, Supplier<SimilarEdges> errorHandler) {
        if (edgeLabel.getOutIndex() >= this.outEdgePool.length) {
            return errorHandler.get();
        }
        SimilarEdges adjacentNodes = this.outEdgePool[edgeLabel.getOutIndex()];
        if (null == adjacentNodes) {
            if (!GraphUtils.equalsLabel(edgeLabel, this.nodeDefine.getOutEdgeDefine(edgeLabel))) {
                return errorHandler.get();
            }
            this.outEdgePool[edgeLabel.getOutIndex()] = adjacentNodes = new SimilarEdges(edgeLabel);
        } else if (!GraphUtils.equalsLabel(edgeLabel, adjacentNodes)) {
            return errorHandler.get();
        }
        return adjacentNodes;
    }

    private SimilarEdges getInEdges(EdgeLabel edgeLabel, Supplier<SimilarEdges> errorHandler) {
        if (edgeLabel.getInIndex() >= this.inEdgePool.length) {
            return errorHandler.get();
        }
        SimilarEdges adjacentNodes = this.inEdgePool[edgeLabel.getInIndex()];
        if (null == adjacentNodes) {
            if (!GraphUtils.equalsLabel(edgeLabel, this.nodeDefine.getInEdgeDefine(edgeLabel))) {
                return errorHandler.get();
            }
            this.inEdgePool[edgeLabel.getInIndex()] = adjacentNodes = new SimilarEdges(edgeLabel);
        } else if (!GraphUtils.equalsLabel(edgeLabel, adjacentNodes)) {
            return errorHandler.get();
        }
        return adjacentNodes;
    }

    protected void addEdge(IEdge edge) {
        SimilarEdges adjacentNodes;
        Assert.notNull((Object)edge, "edge must not be null.");
        if (this.equals(edge.getOutNode())) {
            adjacentNodes = this.getOutEdges((EdgeLabel)edge.getLabel());
        } else if (this.equals(edge.getInNode())) {
            adjacentNodes = this.getInEdges((EdgeLabel)edge.getLabel());
        } else {
            throw new GraphException("\u6307\u5411\u8282\u70b9\u7c7b\u578b\u5f02\u5e38");
        }
        adjacentNodes.add(edge);
    }

    protected void removeEdge(IEdge edge) {
        SimilarEdges adjacentNodes;
        Assert.notNull((Object)edge, "edge must not be null.");
        if (this.equals(edge.getOutNode())) {
            adjacentNodes = this.getOutEdges((EdgeLabel)edge.getLabel());
        } else if (this.equals(edge.getInNode())) {
            adjacentNodes = this.getInEdges((EdgeLabel)edge.getLabel());
        } else {
            throw new GraphException("\u6307\u5411\u8282\u70b9\u7c7b\u578b\u5f02\u5e38");
        }
        adjacentNodes.remove(edge);
    }

    @Override
    public Collection<IEdge> getInsideEdges() {
        HashSet<IEdge> collection = new HashSet<IEdge>();
        for (SimilarEdges adjacentNodes : this.inEdgePool) {
            if (null == adjacentNodes) continue;
            collection.addAll(adjacentNodes);
        }
        return collection;
    }

    @Override
    public Collection<IEdge> getOutsideEdges() {
        HashSet<IEdge> collection = new HashSet<IEdge>();
        for (SimilarEdges adjacentNodes : this.outEdgePool) {
            if (null == adjacentNodes) continue;
            collection.addAll(adjacentNodes);
        }
        return collection;
    }

    @Override
    public Collection<IEdge> getInsideEdges(EdgeLabel edgeLabel) {
        Assert.notNull((Object)edgeLabel, "edgeLabel must not be null.");
        return new HashSet<IEdge>(this.getInEdges(edgeLabel));
    }

    @Override
    public Collection<IEdge> getOutsideEdges(EdgeLabel edgeLabel) {
        Assert.notNull((Object)edgeLabel, "edgeLabel must not be null.");
        return new HashSet<IEdge>(this.getOutEdges(edgeLabel));
    }

    private Collection<INode> getInsideNodes(EdgeLabel edgeLabel) {
        return this.getInEdges(edgeLabel).stream().map(IEdge::getOutNode).collect(Collectors.toList());
    }

    private Collection<INode> getOutsideNodes(EdgeLabel edgeLabel) {
        return this.getOutEdges(edgeLabel).stream().map(IEdge::getInNode).collect(Collectors.toList());
    }

    private Collection<INode> deepLoop(EdgeLabel[] edgeLabels, BiFunction<INode, EdgeLabel, Collection<INode>> func) {
        if (null == edgeLabels || 0 == edgeLabels.length) {
            return Collections.emptyList();
        }
        Collection<INode> current = func.apply(null, edgeLabels[0]);
        if (edgeLabels.length > 1) {
            ArrayList<INode> next = new ArrayList<INode>();
            for (int i = 1; i < edgeLabels.length; ++i) {
                for (INode iNode : current) {
                    next.addAll(func.apply(iNode, edgeLabels[i]));
                }
                if (next.isEmpty()) {
                    return Collections.emptyList();
                }
                current = next;
            }
        }
        return current;
    }

    @Override
    public Collection<INode> getOutsideNodes(EdgeLabel ... path) {
        return this.deepLoop(path, (node, edgeLabel) -> null == node ? this.getOutsideNodes((EdgeLabel)edgeLabel) : node.getOutsideNodes((EdgeLabel)edgeLabel));
    }

    @Override
    public Collection<INode> getInsideNodes(EdgeLabel ... path) {
        return this.deepLoop(path, (node, edgeLabel) -> null == node ? this.getInsideNodes((EdgeLabel)edgeLabel) : node.getInsideNodes((EdgeLabel)edgeLabel));
    }

    private Collection<INode> deepGetOutNodes(EdgeLabel edgeLabel) {
        EdgeDefine outEdgeDefine = this.nodeDefine.getOutEdgeDefine(edgeLabel);
        if (!outEdgeDefine.getOutLabel().equals(outEdgeDefine.getInLabel())) {
            return this.getOutsideNodes(edgeLabel);
        }
        return GraphUtils.deepGet(n -> null == n ? this.getOutsideNodes(edgeLabel) : n.getOutsideNodes(edgeLabel));
    }

    private Collection<INode> deepGetInNodes(EdgeLabel edgeLabel) {
        EdgeDefine inEdgeDefine = this.nodeDefine.getInEdgeDefine(edgeLabel);
        if (inEdgeDefine.getOutLabel().equals(inEdgeDefine.getInLabel())) {
            return this.getInsideNodes(edgeLabel);
        }
        return GraphUtils.deepGet(n -> null == n ? this.getInsideNodes(edgeLabel) : n.getInsideNodes(edgeLabel));
    }

    @Override
    public Collection<INode> deepGetOutsideNodes(EdgeLabel ... path) {
        return this.deepLoop(path, (node, edgeLabel) -> null == node ? this.deepGetOutNodes((EdgeLabel)edgeLabel) : node.deepGetOutsideNodes((EdgeLabel)edgeLabel));
    }

    @Override
    public Collection<INode> deepGetInsideNodes(EdgeLabel ... path) {
        return this.deepLoop(path, (node, edgeLabel) -> null == node ? this.deepGetInNodes((EdgeLabel)edgeLabel) : node.getOutsideNodes((EdgeLabel)edgeLabel));
    }

    @Override
    public int insideEdgeSize() {
        int size = 0;
        for (SimilarEdges adjacentNodes : this.inEdgePool) {
            if (null == adjacentNodes) continue;
            size += adjacentNodes.size();
        }
        return size;
    }

    @Override
    public int insideEdgeSize(EdgeLabel edgeLabel) {
        Assert.notNull((Object)edgeLabel, "edgeLabel must not be null.");
        return this.getInEdges(edgeLabel).size();
    }

    @Override
    public int outsideEdgeSize() {
        int size = 0;
        for (SimilarEdges adjacentNodes : this.outEdgePool) {
            if (null == adjacentNodes) continue;
            size += adjacentNodes.size();
        }
        return size;
    }

    @Override
    public int outsideEdgeSize(EdgeLabel edgeLabel) {
        Assert.notNull((Object)edgeLabel, "edgeLabel must not be null.");
        return this.getOutEdges(edgeLabel).size();
    }

    public void forEachInsideEdge(Consumer<IEdge> action) {
        Assert.notNull(action, "action must not be null.");
        for (SimilarEdges adjacentNodes : this.inEdgePool) {
            if (null == adjacentNodes) continue;
            for (IEdge edge : adjacentNodes) {
                action.accept(edge);
            }
        }
    }

    public void forEachInsideEdge(EdgeLabel edgeLabel, Consumer<IEdge> action) {
        Assert.notNull((Object)edgeLabel, "edgeLabel must not be null.");
        Assert.notNull(action, "action must not be null.");
        this.getInEdges(edgeLabel).forEach(action);
    }

    public void forEachOutsideEdge(Consumer<IEdge> action) {
        Assert.notNull(action, "action must not be null.");
        for (SimilarEdges adjacentNodes : this.outEdgePool) {
            if (null == adjacentNodes) continue;
            for (IEdge edge : adjacentNodes) {
                action.accept(edge);
            }
        }
    }

    public void forEachOutsideEdge(EdgeLabel edgeLabel, Consumer<IEdge> action) {
        Assert.notNull((Object)edgeLabel, "edgeLabel must not be null.");
        Assert.notNull(action, "action must not be null.");
        this.getInEdges(edgeLabel).forEach(action);
    }

    @Override
    public void forEachInsideNode(EdgeLabel edgeLabel, Consumer<INode> action) {
        Assert.notNull((Object)edgeLabel, "edgeLabel must not be null.");
        Assert.notNull(action, "action must not be null.");
        this.getInEdges(edgeLabel).forEach(edge -> action.accept(edge.getOutNode()));
    }

    @Override
    public void forEachOutsideNode(EdgeLabel edgeLabel, Consumer<INode> action) {
        Assert.notNull((Object)edgeLabel, "edgeLabel must not be null.");
        Assert.notNull(action, "action must not be null.");
        this.getOutEdges(edgeLabel).forEach(edge -> action.accept(edge.getInNode()));
    }
}

