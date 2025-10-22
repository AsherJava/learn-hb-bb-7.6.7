/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.itreebase.source.search;

import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import java.util.List;

public interface ISearchNodeProvider {
    public int getTotalSize();

    public List<IBaseNodeData> getTotalPage(String[] var1);

    public List<IBaseNodeData> getOnePage(String[] var1, int var2, int var3);
}

