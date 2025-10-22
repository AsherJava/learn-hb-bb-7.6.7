/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.itreebase.source.search.ISearchNodeProvider
 */
package com.jiuiqi.nr.unit.treebase.entity.search;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.entity.query.FMDMNodeTitleQuery;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.source.search.ISearchNodeProvider;
import java.util.List;

public class SearchNodeShowFMDMAttribute
implements ISearchNodeProvider {
    private IUnitTreeContext context;
    private FMDMNodeTitleQuery nodeTitleQuery;
    private ISearchNodeProvider searchNodeProvider;

    public SearchNodeShowFMDMAttribute(IUnitTreeContext context, ISearchNodeProvider searchNodeProvider) {
        this.context = context;
        this.searchNodeProvider = searchNodeProvider;
        this.nodeTitleQuery = new FMDMNodeTitleQuery(context);
    }

    public int getTotalSize() {
        return this.searchNodeProvider.getTotalSize();
    }

    public List<IBaseNodeData> getTotalPage(String[] keywords) {
        List totalPage = this.searchNodeProvider.getTotalPage(keywords);
        totalPage.forEach(nodeData -> nodeData.setTitle(this.nodeTitleQuery.getAttributesTitle(nodeData.getKey())));
        return totalPage;
    }

    public List<IBaseNodeData> getOnePage(String[] keywords, int pageSize, int currentPage) {
        List onePage = this.searchNodeProvider.getOnePage(keywords, pageSize, currentPage);
        onePage.forEach(nodeData -> nodeData.setTitle(this.nodeTitleQuery.getAttributesTitle(nodeData.getKey())));
        return onePage;
    }
}

