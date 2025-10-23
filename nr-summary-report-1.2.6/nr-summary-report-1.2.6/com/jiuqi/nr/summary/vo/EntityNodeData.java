/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.vo;

import com.jiuqi.nr.summary.vo.CustomNodeData;
import com.jiuqi.nr.summary.vo.NodeType;

public class EntityNodeData
extends CustomNodeData {
    private boolean treeStruct;

    public EntityNodeData(NodeType type) {
        super(type);
    }

    public boolean isTreeStruct() {
        return this.treeStruct;
    }

    public void setTreeStruct(boolean treeStruct) {
        this.treeStruct = treeStruct;
    }
}

