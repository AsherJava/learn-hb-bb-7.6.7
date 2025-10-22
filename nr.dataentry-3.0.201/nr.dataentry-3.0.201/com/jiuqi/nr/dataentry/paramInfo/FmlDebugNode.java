/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.paramInfo;

import com.jiuqi.nr.dataentry.paramInfo.FmlGraphNode;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FmlDebugNode
implements Serializable {
    private static final long serialVersionUID = 1L;
    private FmlGraphNode node;
    private List<FmlDebugNode> children;

    public FmlDebugNode(boolean isLeaf) {
        this.node = new FmlGraphNode(isLeaf);
        this.children = new ArrayList<FmlDebugNode>();
    }

    public void setText(String text) {
        this.node.setText(text);
    }

    public void setExpandHolderPosition(int expandHolderPosition) {
        this.node.setExpandHolderPosition(expandHolderPosition);
    }

    public FmlGraphNode getNode() {
        return this.node;
    }

    public void setNode(FmlGraphNode node) {
        this.node = node;
    }

    public List<FmlDebugNode> getChildren() {
        return this.children;
    }

    public void setChildren(List<FmlDebugNode> children) {
        this.children = children;
    }

    public void setData(boolean hasDebugMessage) {
        HashMap<String, Boolean> map = new HashMap<String, Boolean>();
        map.put("hasDebugMessage", hasDebugMessage);
        this.node.setData(map);
    }
}

