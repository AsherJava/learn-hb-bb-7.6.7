/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.core.DataSchemeNode
 *  com.jiuqi.nr.datascheme.api.core.ITree
 *  com.jiuqi.nr.datascheme.api.spi.NodeFilter
 */
package com.jiuqi.nr.datascheme.internal.service;

import com.jiuqi.nr.datascheme.api.core.DataSchemeNode;
import com.jiuqi.nr.datascheme.api.core.ITree;
import com.jiuqi.nr.datascheme.api.spi.NodeFilter;
import java.util.List;

public interface IDesignDataSchemeTreeService {
    public List<ITree<DataSchemeNode>> getRootTree(String var1, int var2, NodeFilter var3);

    public List<ITree<DataSchemeNode>> getChildTree(DataSchemeNode var1, int var2, NodeFilter var3);

    public List<ITree<DataSchemeNode>> getSpecifiedTree(DataSchemeNode var1, String var2, int var3, NodeFilter var4);
}

