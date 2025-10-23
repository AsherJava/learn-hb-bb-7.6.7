/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.graph.internal;

import com.jiuqi.nr.graph.IEdge;
import com.jiuqi.nr.graph.IGraph;
import com.jiuqi.nr.graph.INode;
import com.jiuqi.nr.graph.define.EdgeDefine;
import com.jiuqi.nr.graph.define.GraphDefine;
import com.jiuqi.nr.graph.define.IndexDefine;
import com.jiuqi.nr.graph.function.AttrValueGetter;
import com.jiuqi.nr.graph.internal.Properties;
import com.jiuqi.nr.graph.internal.SimilarNodes;
import com.jiuqi.nr.graph.label.EdgeLabel;
import com.jiuqi.nr.graph.label.GraphLabel;
import com.jiuqi.nr.graph.label.IndexLabel;
import com.jiuqi.nr.graph.label.NodeLabel;
import com.jiuqi.nr.graph.util.GraphUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.springframework.util.Assert;

public class Graph
extends Properties
implements IGraph {
    protected GraphDefine graphDefine;
    protected SimilarNodes[] nodePool;
    protected SimilarNodes[] indexPool;
    protected Collection<IEdge> edgePool;

    public static Graph copy(Graph graph) {
        Assert.notNull((Object)graph, "graphDefine must not be null.");
        Graph newGraph = new Graph();
        newGraph.properties = graph.properties;
        newGraph.graphDefine = graph.graphDefine;
        newGraph.nodePool = new SimilarNodes[newGraph.graphDefine.nodeDefineSize()];
        Graph.copy(graph.nodePool, newGraph.nodePool);
        newGraph.indexPool = new SimilarNodes[newGraph.graphDefine.indexDefineSize()];
        Graph.copy(graph.indexPool, newGraph.indexPool);
        newGraph.edgePool = new ArrayList<IEdge>(graph.edgePool);
        return newGraph;
    }

    private static void copy(SimilarNodes[] pool, SimilarNodes[] newPool) {
        for (int i = 0; i < pool.length; ++i) {
            if (null == pool[i]) continue;
            newPool[i] = new SimilarNodes(pool[i].getLabel(), pool[i].size(), 1.0f);
            newPool[i].putAll(pool[i]);
        }
    }

    @Override
    public GraphLabel getLabel() {
        return this.graphDefine.getLabel();
    }

    protected SimilarNodes getNodePool(NodeLabel nodeLabel) {
        return this.getNodePool(nodeLabel, SimilarNodes::emptySimilarNodes);
    }

    protected SimilarNodes getNodePool(NodeLabel nodeLabel, Supplier<SimilarNodes> errorHandler) {
        if (nodeLabel.getIndex() >= this.nodePool.length) {
            return errorHandler.get();
        }
        SimilarNodes similarNodes = this.nodePool[nodeLabel.getIndex()];
        if (null == similarNodes) {
            if (!GraphUtils.equalsLabel(nodeLabel, this.graphDefine.getNodeDefine(nodeLabel))) {
                return errorHandler.get();
            }
            this.nodePool[nodeLabel.getIndex()] = similarNodes = new SimilarNodes(nodeLabel);
        } else if (!GraphUtils.equalsLabel(nodeLabel, similarNodes)) {
            return errorHandler.get();
        }
        return similarNodes;
    }

    protected SimilarNodes getIndexPool(IndexLabel indexLabel) {
        return this.getIndexPool(indexLabel, SimilarNodes::emptySimilarNodes);
    }

    private SimilarNodes getIndexPool(IndexLabel indexLabel, Supplier<SimilarNodes> errorHandler) {
        if (indexLabel.getIndex() >= this.indexPool.length) {
            return errorHandler.get();
        }
        SimilarNodes similarNodes = this.indexPool[indexLabel.getIndex()];
        if (null == similarNodes) {
            if (!GraphUtils.equalsLabel(indexLabel, this.graphDefine.getIndexDefine(indexLabel))) {
                return errorHandler.get();
            }
            IndexDefine indexDefine = this.graphDefine.getIndexDefine(indexLabel);
            this.reloadIndex(indexDefine.getNodeLabel());
            similarNodes = this.indexPool[indexLabel.getIndex()];
        } else if (!GraphUtils.equalsLabel(indexLabel, similarNodes)) {
            return errorHandler.get();
        }
        return similarNodes;
    }

    private void reloadIndex(IndexDefine indexDefine) {
        IndexLabel indexLabel = indexDefine.getLabel();
        SimilarNodes similarNodes = new SimilarNodes(indexLabel);
        this.indexPool[indexLabel.getIndex()] = similarNodes;
        SimilarNodes similarNodes2 = similarNodes;
        NodeLabel nodeLabel = indexDefine.getNodeLabel();
        AttrValueGetter<INode, String> keyGetter = indexDefine.getKeyGetter();
        this.getNodePool(nodeLabel).values().forEach(node -> {
            String key = (String)keyGetter.get((INode)node);
            if (null != key) {
                similarNodes2.put(key, node);
            }
        });
    }

    private void reloadIndex(NodeLabel nodeLabel) {
        Assert.notNull((Object)nodeLabel, "nodeLabel must not be null.");
        Iterator<IndexDefine> iterator = this.graphDefine.indexDefineIterator(nodeLabel);
        while (iterator.hasNext()) {
            this.reloadIndex(iterator.next());
        }
    }

    @Override
    public INode getNode(NodeLabel nodeLabel, String nodeKey) {
        Assert.notNull((Object)nodeLabel, "nodeLabel must not be null.");
        SimilarNodes similarNodes = this.getNodePool(nodeLabel);
        return (INode)similarNodes.get(nodeKey);
    }

    @Override
    public INode getNode(IndexLabel indexLabel, String indexKey) {
        Assert.notNull((Object)indexLabel, "indexLabel must not be null.");
        SimilarNodes similarNodes = this.getIndexPool(indexLabel);
        return (INode)similarNodes.get(indexKey);
    }

    @Override
    public List<INode> getNodes(NodeLabel nodeLabel) {
        Assert.notNull((Object)nodeLabel, "nodeLabel must not be null.");
        return new ArrayList<INode>(this.getNodePool(nodeLabel).values());
    }

    @Override
    public List<INode> getNodes(NodeLabel nodeLabel, String ... nodeKeys) {
        Assert.notNull((Object)nodeLabel, "nodeLabel must not be null.");
        if (null == nodeKeys || 0 == nodeKeys.length) {
            return Collections.emptyList();
        }
        SimilarNodes similarNodes = this.getNodePool(nodeLabel);
        return Arrays.stream(nodeKeys).map(similarNodes::get).collect(Collectors.toList());
    }

    @Override
    public List<INode> getNodes(IndexLabel indexLabel) {
        Assert.notNull((Object)indexLabel, "indexLabel must not be null.");
        return new ArrayList<INode>(this.getIndexPool(indexLabel).values());
    }

    @Override
    public List<INode> getNodes(IndexLabel indexLabel, String ... indexKeys) {
        Assert.notNull((Object)indexLabel, "indexLabel must not be null.");
        if (null == indexKeys || 0 == indexKeys.length) {
            return Collections.emptyList();
        }
        SimilarNodes similarNodes = this.getIndexPool(indexLabel);
        return Arrays.stream(indexKeys).map(similarNodes::get).collect(Collectors.toList());
    }

    @Override
    public int nodeSize() {
        int size = 0;
        for (SimilarNodes similarNodes : this.nodePool) {
            if (null == similarNodes) continue;
            size += similarNodes.size();
        }
        return size;
    }

    @Override
    public int nodeSize(NodeLabel nodeLabel) {
        Assert.notNull((Object)nodeLabel, "nodeLabel must not be null.");
        return this.getNodePool(nodeLabel).size();
    }

    @Override
    public int nodeSize(IndexLabel indexLabel) {
        Assert.notNull((Object)indexLabel, "indexLabel must not be null.");
        return this.getIndexPool(indexLabel).size();
    }

    @Override
    public int edgeSize() {
        int size = 0;
        for (SimilarNodes similarNodes : this.nodePool) {
            if (null == similarNodes) continue;
            for (INode node : similarNodes.values()) {
                size += node.outsideEdgeSize();
            }
        }
        return size;
    }

    @Override
    public int edgeSize(EdgeLabel edgeLabel) {
        Assert.notNull((Object)edgeLabel, "edgeLabel must not be null.");
        int size = 0;
        EdgeDefine edgeDefine = this.graphDefine.getEdgeDefine(edgeLabel);
        if (null != edgeDefine) {
            SimilarNodes similarNodes = this.getNodePool(edgeDefine.getOutLabel());
            for (INode node : similarNodes.values()) {
                size += node.outsideEdgeSize();
            }
        }
        return size;
    }

    @Override
    public void forEachNode(Consumer<INode> action) {
        Assert.notNull(action, "action must not be null.");
        for (SimilarNodes similarNodes : this.nodePool) {
            if (null == similarNodes) continue;
            for (INode node : similarNodes.values()) {
                action.accept(node);
            }
        }
    }

    @Override
    public void forEachNode(NodeLabel nodeLabel, Consumer<INode> action) {
        Assert.notNull((Object)nodeLabel, "nodeLabel must not be null.");
        Assert.notNull(action, "action must not be null.");
        this.getNodePool(nodeLabel).values().forEach(action);
    }

    @Override
    public void forEachNode(NodeLabel nodeLabel, Collection<String> nodeKeys, BiConsumer<String, INode> action) {
        Assert.notNull((Object)nodeLabel, "nodeLabel must not be null.");
        Assert.notNull(action, "action must not be null.");
        if (null == nodeKeys || nodeKeys.isEmpty()) {
            return;
        }
        SimilarNodes similarNodes = this.getNodePool(nodeLabel);
        for (String nodeKey : nodeKeys) {
            action.accept(nodeKey, (INode)similarNodes.get(nodeKey));
        }
    }

    @Override
    public void forEachNode(IndexLabel indexLabel, Consumer<INode> action) {
        Assert.notNull((Object)indexLabel, "indexLabel must not be null.");
        Assert.notNull(action, "action must not be null.");
        this.getIndexPool(indexLabel).values().forEach(action);
    }

    @Override
    public void forEachNode(IndexLabel indexLabel, BiConsumer<String, INode> action) {
        Assert.notNull((Object)indexLabel, "indexLabel must not be null.");
        Assert.notNull(action, "action must not be null.");
        Set entrySet = this.getIndexPool(indexLabel).entrySet();
        for (Map.Entry entry : entrySet) {
            action.accept((String)entry.getKey(), (INode)entry.getValue());
        }
    }

    @Override
    public void forEachNode(IndexLabel indexLabel, Collection<String> indexKeys, BiConsumer<String, INode> action) {
        Assert.notNull((Object)indexLabel, "indexLabel must not be null.");
        Assert.notNull(action, "action must not be null.");
        if (null == indexKeys || indexKeys.isEmpty()) {
            return;
        }
        SimilarNodes similarNodes = this.getIndexPool(indexLabel);
        for (String indexKey : indexKeys) {
            action.accept(indexKey, (INode)similarNodes.get(indexKey));
        }
    }

    @Override
    public void forEachEdge(Consumer<IEdge> action) {
        Assert.notNull(action, "action must not be null.");
        this.edgePool.forEach(action);
    }

    @Override
    public void forEachEdge(EdgeLabel edgeLabel, Consumer<IEdge> action) {
        Assert.notNull((Object)edgeLabel, "edgeLabel must not be null.");
        Assert.notNull(action, "action must not be null.");
        this.edgePool.forEach(e -> {
            if (((EdgeLabel)e.getLabel()).equals(edgeLabel)) {
                action.accept((IEdge)e);
            }
        });
    }
}

