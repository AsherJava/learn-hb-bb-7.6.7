/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.vo;

import com.jiuqi.nr.summary.vo.EntityNodeData;
import com.jiuqi.nr.summary.vo.NodeType;

public class BaseDataNodeData
extends EntityNodeData {
    private int maxLevel;

    public BaseDataNodeData(NodeType type) {
        super(type);
    }

    public int getMaxLevel() {
        return this.maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }
}

