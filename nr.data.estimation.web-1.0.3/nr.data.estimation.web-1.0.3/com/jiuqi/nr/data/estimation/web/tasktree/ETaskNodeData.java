/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INodeImpl
 */
package com.jiuqi.nr.data.estimation.web.tasktree;

import com.jiuqi.nr.common.itree.INodeImpl;
import com.jiuqi.nr.data.estimation.web.tasktree.EstimationTaskTreeNode;

public class ETaskNodeData
extends INodeImpl
implements EstimationTaskTreeNode {
    private String nodeType;

    @Override
    public String getNodeType() {
        return this.nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }
}

