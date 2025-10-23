/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.graph;

import com.jiuqi.nr.graph.IDataRelation;
import com.jiuqi.nr.graph.INode;

public interface IEdge
extends IDataRelation {
    public INode getOutNode();

    public INode getInNode();
}

