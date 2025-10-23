/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.graph.internal;

import com.jiuqi.nr.graph.IEdge;
import com.jiuqi.nr.graph.IGraph;
import com.jiuqi.nr.graph.INode;
import com.jiuqi.nr.graph.internal.Properties;
import com.jiuqi.nr.graph.label.EdgeLabel;
import com.jiuqi.nr.graph.label.GraphLabel;
import com.jiuqi.nr.graph.label.IndexLabel;
import com.jiuqi.nr.graph.label.NodeLabel;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class EmptyGraph
extends Properties
implements IGraph {
    @Override
    public GraphLabel getLabel() {
        return null;
    }

    @Override
    public INode getNode(NodeLabel nodeLabel, String nodeKey) {
        return null;
    }

    @Override
    public INode getNode(IndexLabel indexLabel, String indexKey) {
        return null;
    }

    @Override
    public List<INode> getNodes(NodeLabel nodeLabel) {
        return Collections.emptyList();
    }

    @Override
    public List<INode> getNodes(NodeLabel nodeLabel, String ... nodeKeys) {
        return Collections.emptyList();
    }

    @Override
    public List<INode> getNodes(IndexLabel indexLabel) {
        return Collections.emptyList();
    }

    @Override
    public List<INode> getNodes(IndexLabel indexLabel, String ... indexKeys) {
        return Collections.emptyList();
    }

    @Override
    public int nodeSize() {
        return 0;
    }

    @Override
    public int nodeSize(NodeLabel nodeLabel) {
        return 0;
    }

    @Override
    public int nodeSize(IndexLabel indexLabel) {
        return 0;
    }

    @Override
    public int edgeSize() {
        return 0;
    }

    @Override
    public int edgeSize(EdgeLabel edgeLabel) {
        return 0;
    }

    @Override
    public void forEachNode(Consumer<INode> action) {
    }

    @Override
    public void forEachNode(NodeLabel nodeLabel, Consumer<INode> action) {
    }

    @Override
    public void forEachNode(NodeLabel nodeLabel, Collection<String> nodeKeys, BiConsumer<String, INode> action) {
    }

    @Override
    public void forEachNode(IndexLabel indexLabel, Consumer<INode> action) {
    }

    @Override
    public void forEachNode(IndexLabel indexLabel, BiConsumer<String, INode> action) {
    }

    @Override
    public void forEachNode(IndexLabel indexLabel, Collection<String> indexKeys, BiConsumer<String, INode> action) {
    }

    @Override
    public void forEachEdge(Consumer<IEdge> action) {
    }

    @Override
    public void forEachEdge(EdgeLabel edgeLabel, Consumer<IEdge> action) {
    }
}

