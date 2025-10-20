/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.samecontrol.vo.samectrlrule;

import com.jiuqi.gcreport.samecontrol.vo.samectrlrule.SameCtrlRuleVO;

public class SameCtrlRuleDragRuleVO {
    private SameCtrlRuleVO draggingNode;
    private SameCtrlRuleVO dragPositionNode;
    private String dragNodeUpAndDown;

    public SameCtrlRuleVO getDraggingNode() {
        return this.draggingNode;
    }

    public void setDraggingNode(SameCtrlRuleVO draggingNode) {
        this.draggingNode = draggingNode;
    }

    public SameCtrlRuleVO getDragPositionNode() {
        return this.dragPositionNode;
    }

    public void setDragPositionNode(SameCtrlRuleVO dragPositionNode) {
        this.dragPositionNode = dragPositionNode;
    }

    public String getDragNodeUpAndDown() {
        return this.dragNodeUpAndDown;
    }

    public void setDragNodeUpAndDown(String dragNodeUpAndDown) {
        this.dragNodeUpAndDown = dragNodeUpAndDown;
    }
}

