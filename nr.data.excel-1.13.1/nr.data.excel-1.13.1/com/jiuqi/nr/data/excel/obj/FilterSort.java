/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datacrud.LinkSort
 *  com.jiuqi.nr.datacrud.PageInfo
 *  com.jiuqi.nr.datacrud.spi.RowFilter
 */
package com.jiuqi.nr.data.excel.obj;

import com.jiuqi.nr.datacrud.LinkSort;
import com.jiuqi.nr.datacrud.PageInfo;
import com.jiuqi.nr.datacrud.spi.RowFilter;
import java.util.List;
import java.util.Map;

public class FilterSort {
    private final Map<String, List<RowFilter>> rowFilter;
    private final Map<String, List<LinkSort>> linkSort;
    private final Map<String, PageInfo> topPageInfo;

    public FilterSort(Map<String, List<RowFilter>> rowFilter, Map<String, List<LinkSort>> linkSort, Map<String, PageInfo> topPageInfo) {
        this.rowFilter = rowFilter;
        this.linkSort = linkSort;
        this.topPageInfo = topPageInfo;
    }

    public Map<String, List<RowFilter>> getRowFilter() {
        return this.rowFilter;
    }

    public Map<String, List<LinkSort>> getLinkSort() {
        return this.linkSort;
    }

    public Map<String, PageInfo> getTopPageInfo() {
        return this.topPageInfo;
    }
}

