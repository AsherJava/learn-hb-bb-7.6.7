/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formulapenetration.defines;

import com.jiuqi.nr.formulapenetration.defines.ExpressionNode;

public class ExpressionExpandNode
extends ExpressionNode {
    private boolean expanded;
    private int expandHolderPosition;

    public boolean isExpanded() {
        return this.expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public int getExpandHolderPosition() {
        return this.expandHolderPosition;
    }

    public void setExpandHolderPosition(int expandHolderPosition) {
        this.expandHolderPosition = expandHolderPosition;
    }
}

