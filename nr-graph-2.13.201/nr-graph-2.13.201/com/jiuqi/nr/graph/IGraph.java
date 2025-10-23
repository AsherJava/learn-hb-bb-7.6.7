/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.graph;

import com.jiuqi.nr.graph.IEdge;
import com.jiuqi.nr.graph.INode;
import com.jiuqi.nr.graph.IProperties;
import com.jiuqi.nr.graph.label.EdgeLabel;
import com.jiuqi.nr.graph.label.GraphLabel;
import com.jiuqi.nr.graph.label.ILabelabled;
import com.jiuqi.nr.graph.label.IndexLabel;
import com.jiuqi.nr.graph.label.NodeLabel;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface IGraph
extends IProperties,
ILabelabled<GraphLabel> {
    public INode getNode(NodeLabel var1, String var2);

    public INode getNode(IndexLabel var1, String var2);

    public List<INode> getNodes(NodeLabel var1);

    public List<INode> getNodes(NodeLabel var1, String ... var2);

    public List<INode> getNodes(IndexLabel var1);

    public List<INode> getNodes(IndexLabel var1, String ... var2);

    public int nodeSize();

    public int nodeSize(NodeLabel var1);

    public int nodeSize(IndexLabel var1);

    public int edgeSize();

    public int edgeSize(EdgeLabel var1);

    public void forEachNode(Consumer<INode> var1);

    public void forEachNode(NodeLabel var1, Consumer<INode> var2);

    public void forEachNode(NodeLabel var1, Collection<String> var2, BiConsumer<String, INode> var3);

    public void forEachNode(IndexLabel var1, Consumer<INode> var2);

    public void forEachNode(IndexLabel var1, BiConsumer<String, INode> var2);

    public void forEachNode(IndexLabel var1, Collection<String> var2, BiConsumer<String, INode> var3);

    public void forEachEdge(Consumer<IEdge> var1);

    public void forEachEdge(EdgeLabel var1, Consumer<IEdge> var2);
}

