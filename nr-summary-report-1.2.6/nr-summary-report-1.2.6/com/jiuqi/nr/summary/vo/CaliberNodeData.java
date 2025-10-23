/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.vo;

import com.jiuqi.nr.summary.vo.CustomNodeData;
import com.jiuqi.nr.summary.vo.NodeType;

public class CaliberNodeData
extends CustomNodeData {
    private boolean treeStruct;

    public CaliberNodeData() {
        super(NodeType.CALIBER);
    }

    public boolean isTreeStruct() {
        return this.treeStruct;
    }

    public void setTreeStruct(boolean treeStruct) {
        this.treeStruct = treeStruct;
    }
}

