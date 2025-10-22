/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.core.INode
 */
package com.jiuqi.nr.dataresource;

import com.jiuqi.nr.datascheme.api.core.INode;

public interface DataResourceNode
extends INode,
Comparable<DataResourceNode> {
    public int getType();

    public String getParentKey();

    public String getOrder();

    public String getDimKey();

    public String getDataTableKey();

    public String getSource();

    public String getDataSchemeKey();

    public String getLinkZb();
}

