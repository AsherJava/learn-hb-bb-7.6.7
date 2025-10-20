/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.unionrule.vo;

import com.jiuqi.gcreport.unionrule.vo.UnionRuleVO;

public class DragRuleVO {
    private UnionRuleVO draggingNode;
    private UnionRuleVO dragPositionNode;
    private String dragNodeUpAndDown;

    public UnionRuleVO getDraggingNode() {
        return this.draggingNode;
    }

    public void setDraggingNode(UnionRuleVO draggingNode) {
        this.draggingNode = draggingNode;
    }

    public UnionRuleVO getDragPositionNode() {
        return this.dragPositionNode;
    }

    public void setDragPositionNode(UnionRuleVO dragPositionNode) {
        this.dragPositionNode = dragPositionNode;
    }

    public String getDragNodeUpAndDown() {
        return this.dragNodeUpAndDown;
    }

    public void setDragNodeUpAndDown(String dragNodeUpAndDown) {
        this.dragNodeUpAndDown = dragNodeUpAndDown;
    }
}

