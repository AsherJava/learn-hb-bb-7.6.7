/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.itreebase.node.BaseNodeDataImpl
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.itreebase.source.search.ISearchNodeProvider
 */
package com.jiuiqi.nr.unit.treebase.source.basedata;

import com.jiuiqi.nr.unit.treebase.source.basedata.IGroupDataTable;
import com.jiuqi.nr.itreebase.node.BaseNodeDataImpl;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.source.search.ISearchNodeProvider;
import java.util.List;

public class GroupSearchNodeProvider
implements ISearchNodeProvider {
    private IGroupDataTable groupDataTable;
    private ISearchNodeProvider baseSearchProvider;

    public GroupSearchNodeProvider(ISearchNodeProvider baseSearchProvider, IGroupDataTable groupDataTable) {
        this.groupDataTable = groupDataTable;
        this.baseSearchProvider = baseSearchProvider;
    }

    public int getTotalSize() {
        return this.baseSearchProvider.getTotalSize();
    }

    public List<IBaseNodeData> getTotalPage(String[] keywords) {
        return this.wrapperNodePath(this.baseSearchProvider.getTotalPage(keywords));
    }

    public List<IBaseNodeData> getOnePage(String[] keywords, int pageSize, int currentPage) {
        return this.wrapperNodePath(this.baseSearchProvider.getOnePage(keywords, pageSize, currentPage));
    }

    private List<IBaseNodeData> wrapperNodePath(List<IBaseNodeData> oriList) {
        oriList.forEach(n -> {
            BaseNodeDataImpl impl = (BaseNodeDataImpl)n;
            impl.setPath(this.groupDataTable.getNodePath((IBaseNodeData)impl));
        });
        return oriList;
    }
}

