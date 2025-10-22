/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeContextData
 *  com.jiuqi.nr.itreebase.node.BaseNodeDataImpl
 */
package com.jiuqi.nr.unit.treeimpl.web.request;

import com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeContextData;
import com.jiuqi.nr.itreebase.node.BaseNodeDataImpl;

public class NodeDragParam
extends UnitTreeContextData {
    private BaseNodeDataImpl dragNode;
    private BaseNodeDataImpl dropNode;
    private int position;
    private int state;

    public BaseNodeDataImpl getDragNode() {
        return this.dragNode;
    }

    public void setDragNode(BaseNodeDataImpl dragNode) {
        this.dragNode = dragNode;
    }

    public BaseNodeDataImpl getDropNode() {
        return this.dropNode;
    }

    public void setDropNode(BaseNodeDataImpl dropNode) {
        this.dropNode = dropNode;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }
}

