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

public class NodeModifyParam
extends RunTimeContextData {
    private BaseNodeDataImpl tagNode;

    public BaseNodeDataImpl getTagNode() {
        return this.tagNode;
    }

    @JsonDeserialize(using=BaseNodeJsonDeserializer.class)
    public void setTagNode(BaseNodeDataImpl tagNode) {
        this.tagNode = tagNode;
    }
}

