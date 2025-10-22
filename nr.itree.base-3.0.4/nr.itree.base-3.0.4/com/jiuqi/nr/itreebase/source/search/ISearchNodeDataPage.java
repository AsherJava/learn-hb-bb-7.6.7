/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.itreebase.source.search;

import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import java.util.List;

public interface ISearchNodeDataPage {
    public int getPageSize();

    public int getCurrentPage();

    public int getTotalSize();

    public List<IBaseNodeData> getPageData();
}

