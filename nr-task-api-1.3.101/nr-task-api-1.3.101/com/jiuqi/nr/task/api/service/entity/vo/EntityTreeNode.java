/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.common.NodeType
 */
package com.jiuqi.nr.task.api.service.entity.vo;

import com.jiuqi.nr.entity.common.NodeType;
import com.jiuqi.nr.task.api.service.entity.dto.EntityDataDTO;

public class EntityTreeNode
extends EntityDataDTO {
    private NodeType nodeType;

    public NodeType getNodeType() {
        return this.nodeType;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }
}

