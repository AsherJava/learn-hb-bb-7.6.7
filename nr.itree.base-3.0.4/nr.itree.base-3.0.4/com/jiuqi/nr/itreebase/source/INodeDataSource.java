/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.itreebase.source;

import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import java.util.List;

public interface INodeDataSource {
    public List<String> getNodePath(IBaseNodeData var1);

    public boolean isLeaf(IBaseNodeData var1);

    default public int getShowCountNumber(IBaseNodeData data) {
        return 0;
    }

    public List<IBaseNodeData> getRoots();

    public List<IBaseNodeData> getChildren(IBaseNodeData var1);
}

