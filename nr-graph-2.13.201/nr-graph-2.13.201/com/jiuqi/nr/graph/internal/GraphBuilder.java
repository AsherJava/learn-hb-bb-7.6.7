/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.graph.internal;

import com.jiuqi.nr.graph.IGraphEditor;
import com.jiuqi.nr.graph.define.GraphDefineBuilder;
import com.jiuqi.nr.graph.internal.GraphEditor;

public class GraphBuilder
extends GraphDefineBuilder {
    public GraphBuilder(String graphName) {
        super(graphName);
    }

    public IGraphEditor createGraph() {
        return new GraphEditor(this.getGraphDefine());
    }
}

