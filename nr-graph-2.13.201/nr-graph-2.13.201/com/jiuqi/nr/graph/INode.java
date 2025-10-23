/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.graph;

import com.jiuqi.nr.graph.IDataWrapper;
import com.jiuqi.nr.graph.IEdge;
import com.jiuqi.nr.graph.label.EdgeLabel;
import java.util.Collection;
import java.util.function.Consumer;

public interface INode
extends IDataWrapper {
    public Collection<IEdge> getInsideEdges();

    public Collection<IEdge> getInsideEdges(EdgeLabel var1);

    public Collection<IEdge> getOutsideEdges();

    public Collection<IEdge> getOutsideEdges(EdgeLabel var1);

    public Collection<INode> getOutsideNodes(EdgeLabel ... var1);

    public Collection<INode> getInsideNodes(EdgeLabel ... var1);

    public Collection<INode> deepGetOutsideNodes(EdgeLabel ... var1);

    public Collection<INode> deepGetInsideNodes(EdgeLabel ... var1);

    public int insideEdgeSize();

    public int insideEdgeSize(EdgeLabel var1);

    public int outsideEdgeSize();

    public int outsideEdgeSize(EdgeLabel var1);

    public void forEachInsideNode(EdgeLabel var1, Consumer<INode> var2);

    public void forEachOutsideNode(EdgeLabel var1, Consumer<INode> var2);
}

