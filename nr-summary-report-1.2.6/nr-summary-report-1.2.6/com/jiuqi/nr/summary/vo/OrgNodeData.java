/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.vo;

import com.jiuqi.nr.summary.vo.EntityNodeData;
import com.jiuqi.nr.summary.vo.NodeType;

public class OrgNodeData
extends EntityNodeData {
    private int maxLevel;

    public OrgNodeData() {
        super(NodeType.ORG);
    }

    public int getMaxLevel() {
        return this.maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }
}

