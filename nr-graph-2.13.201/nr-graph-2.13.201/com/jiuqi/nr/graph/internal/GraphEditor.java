/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.graph.internal;

import com.jiuqi.nr.graph.IDataRelation;
import com.jiuqi.nr.graph.IDataWrapper;
import com.jiuqi.nr.graph.IEdge;
import com.jiuqi.nr.graph.IGraph;
import com.jiuqi.nr.graph.IGraphEditor;
import com.jiuqi.nr.graph.INode;
import com.jiuqi.nr.graph.define.EdgeDefine;
import com.jiuqi.nr.graph.define.GraphDefine;
import com.jiuqi.nr.graph.define.NodeDefine;
import com.jiuqi.nr.graph.exception.GraphException;
import com.jiuqi.nr.graph.function.EdgePredicate;
import com.jiuqi.nr.graph.internal.Edge;
import com.jiuqi.nr.graph.internal.Graph;
import com.jiuqi.nr.graph.internal.Node;
import com.jiuqi.nr.graph.internal.SimilarNodes;
import com.jiuqi.nr.graph.label.EdgeLabel;
import com.jiuqi.nr.graph.label.NodeLabel;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import org.springframework.util.Assert;

public class GraphEditor
extends Graph
implements IGraphEditor {
    private boolean finished;

    public GraphEditor(GraphDefine graphDefine) {
        Assert.notNull((Object)graphDefine, "graphDefine must not be null.");
        this.graphDefine = graphDefine;
        this.nodePool = new SimilarNodes[this.graphDefine.nodeDefineSize()];
        this.indexPool = new SimilarNodes[this.graphDefine.indexDefineSize()];
        this.edgePool = new HashSet();
    }

    @Override
    public IGraph finish() {
        this.autoAddEdge();
        this.finished = true;
        return Graph.copy(this);
    }

    private void checkFinished() {
        if (this.finished) {
            throw new GraphException("\u56fe\u7f16\u8f91\u5df2\u5b8c\u6210\uff0c\u7981\u6b62\u7f16\u8f91");
        }
    }

    @Override
    public INode addNode(NodeLabel nodeLabel, Object data) {
        Assert.notNull((Object)nodeLabel, "nodeLabel must not be null.");
        this.checkFinished();
        SimilarNodes similarNodes = this.getNodePool(nodeLabel, () -> {
            throw new GraphException("\u8282\u70b9\u7c7b\u578b\u5f02\u5e38");
        });
        NodeDefine nodeDefine = this.graphDefine.getNodeDefine(nodeLabel);
        Node node = new Node(nodeDefine, data);
        similarNodes.put(node.getKey(), node);
        return node;
    }

    @Override
    public INode addNode(IDataWrapper dataWrapper) {
        Assert.notNull((Object)dataWrapper, "dataWrapper must not be null.");
        this.checkFinished();
        NodeDefine nodeDefine = this.graphDefine.getNodeDefine((NodeLabel)dataWrapper.getLabel());
        if (null == nodeDefine) {
            throw new GraphException("\u8282\u70b9\u7c7b\u578b\u5f02\u5e38");
        }
        SimilarNodes similarNodes = this.getNodePool((NodeLabel)dataWrapper.getLabel(), () -> {
            throw new GraphException("\u8282\u70b9\u7c7b\u578b\u5f02\u5e38");
        });
        Node node = new Node(nodeDefine, dataWrapper);
        similarNodes.put(node.getKey(), node);
        return node;
    }

    private void autoAddEdge() {
        Iterator<EdgeDefine> edgeDefineIterator = this.graphDefine.edgeDefineIterator();
        while (edgeDefineIterator.hasNext()) {
            EdgeDefine edgeDefine = edgeDefineIterator.next();
            EdgePredicate predicate = edgeDefine.getPredicate();
            if (null == predicate) continue;
            this.forEachNode(edgeDefine.getOutLabel(), (INode outNode) -> this.forEachNode(edgeDefine.getInLabel(), (INode inNode) -> {
                if (predicate.test(outNode.getKey(), outNode.getData(), inNode.getKey(), inNode.getData())) {
                    this.addEdge(edgeDefine.getLabel(), (INode)outNode, (INode)inNode);
                }
            }));
        }
    }

    @Override
    public IEdge addEdge(EdgeLabel edgeLabel, INode outNode, INode inNode) {
        Assert.notNull((Object)edgeLabel, "edgeLabel must not be null.");
        Assert.notNull((Object)outNode, "outNode must not be null.");
        Assert.notNull((Object)inNode, "inNode must not be null.");
        this.checkFinished();
        Edge edge = new Edge(edgeLabel, outNode, inNode);
        this.addEdge(edge, outNode, inNode);
        return edge;
    }

    @Override
    public IEdge addEdge(IDataRelation rel) {
        Assert.notNull((Object)rel, "rel must not be null.");
        this.checkFinished();
        EdgeLabel edgeLabel = (EdgeLabel)rel.getLabel();
        EdgeDefine edgeDefine = this.graphDefine.getEdgeDefine(edgeLabel);
        if (null == edgeDefine) {
            return null;
        }
        NodeLabel outLabel = edgeDefine.getOutLabel();
        NodeLabel inLabel = edgeDefine.getInLabel();
        INode outNode = this.getNode(outLabel, rel.getOutKey());
        INode inNode = this.getNode(inLabel, rel.getInKey());
        if (null == outNode || null == inNode) {
            return null;
        }
        Edge edge = new Edge(edgeLabel, outNode, inNode);
        this.addEdge(edge, outNode, inNode);
        return edge;
    }

    private void addEdge(IEdge edge, INode outNode, INode inNode) {
        if (outNode.equals(inNode)) {
            throw new GraphException("\u5173\u7cfb\u7684\u7aef\u70b9\u4e0d\u80fd\u662f\u540c\u4e00\u8282\u70b9");
        }
        ((Node)outNode).addEdge(edge);
        ((Node)inNode).addEdge(edge);
        this.edgePool.add(edge);
    }

    @Override
    public void removeNode(NodeLabel nodeLabel, String nodeKey) {
        Assert.notNull((Object)nodeLabel, "nodeLabel must not be null.");
        this.checkFinished();
        INode node = (INode)this.nodePool[nodeLabel.getIndex()].remove(nodeKey);
        Collection<IEdge> outsideEdges = node.getOutsideEdges();
        for (IEdge iEdge : outsideEdges) {
            this.removeEdge(iEdge);
        }
        Collection<IEdge> insideEdges = node.getInsideEdges();
        for (IEdge iEdge : insideEdges) {
            this.removeEdge(iEdge);
        }
    }

    @Override
    public void removeNode(INode node) {
        Assert.notNull((Object)node, "node must not be null.");
        this.checkFinished();
        this.removeNode((NodeLabel)node.getLabel(), node.getKey());
    }

    @Override
    public void removeEdge(EdgeLabel edgeLabel, INode outNode, INode inNode) {
        Assert.notNull((Object)edgeLabel, "edgeLabel must not be null.");
        Assert.notNull((Object)outNode, "outNode must not be null.");
        Assert.notNull((Object)inNode, "inNode must not be null.");
        this.checkFinished();
        Edge edge = new Edge(edgeLabel, outNode, inNode);
        this.removeEdge(edge);
    }

    @Override
    public void removeEdge(IEdge edge) {
        Assert.notNull((Object)edge, "edge must not be null.");
        ((Node)edge.getOutNode()).removeEdge(edge);
        ((Node)edge.getOutNode()).removeEdge(edge);
        this.edgePool.remove(edge);
    }
}

