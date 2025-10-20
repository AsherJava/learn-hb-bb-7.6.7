/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.parameter.engine.cascade;

import java.util.List;

public interface IGraphNode {
    public List<? extends IGraphNode> getPointToNodeList();

    public List<? extends IGraphNode> getPointFromNodeList();

    public void pointTo(IGraphNode var1);

    public void unPointTo(IGraphNode var1);

    public void pointFrom(IGraphNode var1);

    public void unPointFrom(IGraphNode var1);

    public void pointTo(List<? extends IGraphNode> var1);

    public void pointFrom(List<? extends IGraphNode> var1);

    public boolean canUnion();

    public void union(IGraphNode var1);
}

