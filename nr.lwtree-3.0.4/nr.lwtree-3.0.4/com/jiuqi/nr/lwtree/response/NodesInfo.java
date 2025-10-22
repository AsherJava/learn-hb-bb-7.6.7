/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 */
package com.jiuqi.nr.lwtree.response;

import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.lwtree.response.INodeInfos;
import com.jiuqi.nr.lwtree.response.NodeIconInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NodesInfo<E extends INode>
implements INodeInfos<E> {
    private List<ITree<E>> nodes = new ArrayList<ITree<E>>(0);
    private Map<String, NodeIconInfo> icons = new HashMap<String, NodeIconInfo>(0);

    public List<ITree<E>> getNodes() {
        return this.nodes;
    }

    public void setNodes(List<ITree<E>> nodes) {
        this.nodes = nodes;
    }

    public Map<String, NodeIconInfo> getIcons() {
        return this.icons;
    }

    public void setIcons(Map<String, NodeIconInfo> icons) {
        this.icons = icons;
    }
}

