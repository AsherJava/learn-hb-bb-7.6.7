/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.core.ITree
 *  com.jiuqi.nr.datascheme.api.core.RuntimeDataSchemeNode
 *  com.jiuqi.nr.datascheme.api.spi.NodeFilter
 */
package com.jiuqi.nr.datascheme.internal.service;

import com.jiuqi.nr.datascheme.api.core.ITree;
import com.jiuqi.nr.datascheme.api.core.RuntimeDataSchemeNode;
import com.jiuqi.nr.datascheme.api.spi.NodeFilter;
import java.util.List;

public interface IRuntimeDataSchemeTreeService {
    public List<ITree<RuntimeDataSchemeNode>> getRootTree(String var1, int var2, NodeFilter var3);

    public List<ITree<RuntimeDataSchemeNode>> getChildTree(RuntimeDataSchemeNode var1, int var2, NodeFilter var3);

    public List<ITree<RuntimeDataSchemeNode>> getSpecifiedTree(RuntimeDataSchemeNode var1, String var2, int var3, NodeFilter var4);
}

