/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.vo;

import com.jiuqi.nr.summary.vo.CustomNodeData;
import com.jiuqi.nr.summary.vo.NodeType;

public class ReferNode
extends CustomNodeData {
    private String referEntity;

    public ReferNode(NodeType type) {
        super(type);
    }

    public String getReferEntity() {
        return this.referEntity;
    }

    public void setReferEntity(String referEntity) {
        this.referEntity = referEntity;
    }
}

