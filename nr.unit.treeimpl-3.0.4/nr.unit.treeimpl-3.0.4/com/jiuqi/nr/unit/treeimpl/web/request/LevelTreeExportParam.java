/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.jiuiqi.nr.unit.treebase.context.impl.RunTimeContextData
 *  com.jiuqi.nr.itreebase.node.BaseNodeDataImpl
 *  com.jiuqi.nr.itreebase.node.BaseNodeJsonDeserializer
 */
package com.jiuqi.nr.unit.treeimpl.web.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jiuiqi.nr.unit.treebase.context.impl.RunTimeContextData;
import com.jiuqi.nr.itreebase.node.BaseNodeDataImpl;
import com.jiuqi.nr.itreebase.node.BaseNodeJsonDeserializer;

public class LevelTreeExportParam
extends RunTimeContextData {
    private BaseNodeDataImpl rootLevelNode;

    public BaseNodeDataImpl getRootLevelNode() {
        return this.rootLevelNode;
    }

    @JsonDeserialize(using=BaseNodeJsonDeserializer.class)
    public void setRootLevelNode(BaseNodeDataImpl rootLevelNode) {
        this.rootLevelNode = rootLevelNode;
    }
}

