/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.vo;

import com.jiuqi.nr.summary.vo.CustomNodeData;
import com.jiuqi.nr.summary.vo.NodeType;

public class SummarySolutionNodeData
extends CustomNodeData {
    private String mainTaskId;

    public SummarySolutionNodeData(String mainTaskId) {
        super(NodeType.SUMMARY_SOLUTION);
        this.mainTaskId = mainTaskId;
    }

    public String getMainTaskId() {
        return this.mainTaskId;
    }

    public void setMainTaskId(String mainTaskId) {
        this.mainTaskId = mainTaskId;
    }
}

