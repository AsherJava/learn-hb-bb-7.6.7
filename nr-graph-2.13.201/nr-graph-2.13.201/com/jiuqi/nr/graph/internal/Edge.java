/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.graph.internal;

import com.jiuqi.nr.graph.IEdge;
import com.jiuqi.nr.graph.INode;
import com.jiuqi.nr.graph.internal.DataRelation;
import com.jiuqi.nr.graph.label.EdgeLabel;

public class Edge
extends DataRelation
implements IEdge {
    private final INode outNode;
    private final INode inNode;

    protected Edge(EdgeLabel label, INode outNode, INode inNode) {
        super(label, outNode.getKey(), inNode.getKey());
        this.outNode = outNode;
        this.inNode = inNode;
    }

    @Override
    public INode getOutNode() {
        return this.outNode;
    }

    @Override
    public INode getInNode() {
        return this.inNode;
    }
}

