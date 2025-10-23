/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api.core;

import com.jiuqi.nr.datascheme.api.core.INode;
import com.jiuqi.nr.datascheme.api.core.ISchemeNode;

public interface RuntimeDataSchemeNode
extends INode,
ISchemeNode {
    @Override
    public int getType();

    public String getParentKey();

    public String getOrder();
}

