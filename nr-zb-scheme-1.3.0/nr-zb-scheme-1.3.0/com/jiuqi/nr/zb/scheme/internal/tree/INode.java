/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.internal.tree;

import com.jiuqi.nr.zb.scheme.common.NodeType;
import java.io.Serializable;

public interface INode
extends Serializable {
    public String getKey();

    public String getCode();

    public String getTitle();

    public String getParentKey();

    public NodeType getNodeType();
}

