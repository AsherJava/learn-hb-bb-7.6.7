/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.itreebase.source.search.ISearchNodeDataPage
 *  com.jiuqi.nr.itreebase.source.search.ISearchNodeProvider
 *  com.jiuqi.nr.itreebase.source.search.SearchContextData
 *  com.jiuqi.nr.itreebase.source.search.SearchNodeDataPage
 *  com.jiuqi.nr.unit.treecommon.utils.JavaBeanUtils
 *  javax.annotation.Resource
 *  org.json.JSONObject
 */
package com.jiuiqi.nr.unit.treebase.web.service.impl;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextBuilder;
import com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeContextData;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeDataSource;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeDataSourceHelper;
import com.jiuiqi.nr.unit.treebase.web.service.IUnitTreeSearchService;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.source.search.ISearchNodeDataPage;
import com.jiuqi.nr.itreebase.source.search.ISearchNodeProvider;
import com.jiuqi.nr.itreebase.source.search.SearchContextData;
import com.jiuqi.nr.itreebase.source.search.SearchNodeDataPage;
import com.jiuqi.nr.unit.treecommon.utils.JavaBeanUtils;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service(value="unit-tree-search-data-service")
public class UnitTreeSearchService
implements IUnitTreeSearchService {
    static final String KEY_OF_SEARCH_CONTEXT = "searchContext";
    @Resource
    private IUnitTreeContextBuilder contextBuilder;
    @Resource
    private IUnitTreeDataSourceHelper treeSourceHelper;

    @Override
    public ISearchNodeDataPage searchingOnePageNodes(UnitTreeContextData contextData) {
        IUnitTreeContext context = this.contextBuilder.createTreeContext(contextData);
        SearchContextData searchContext = this.getSearchContext(context);
        if (searchContext != null) {
            IUnitTreeDataSource dataSource = this.treeSourceHelper.getBaseTreeDataSource(context.getDataSourceId());
            ISearchNodeProvider searchDataProvider = dataSource.getSearchDataProvider(context);
            int pageSize = searchContext.getPageSize();
            int currentPage = searchContext.getCurrentPage();
            String[] keyWords = searchContext.getKeywords();
            List onePage = searchDataProvider.getOnePage(keyWords, pageSize, currentPage);
            int totalSize = searchDataProvider.getTotalSize();
            return this.getSearchOnePageData(onePage, totalSize, pageSize, currentPage);
        }
        return this.getSearchOnePageData(new ArrayList<IBaseNodeData>(), 0, 50, 0);
    }

    private ISearchNodeDataPage getSearchOnePageData(List<IBaseNodeData> pageData, int totalSize, int pageSize, int currentPage) {
        SearchNodeDataPage impl = new SearchNodeDataPage();
        impl.setTotalSize(totalSize);
        impl.setPageSize(pageSize);
        impl.setCurrentPage(currentPage);
        impl.setPageData(pageData);
        return impl;
    }

    private SearchContextData getSearchContext(IUnitTreeContext context) {
        JSONObject customVariable = context.getCustomVariable();
        return (SearchContextData)JavaBeanUtils.toJavaBean((String)customVariable.get(KEY_OF_SEARCH_CONTEXT).toString(), SearchContextData.class);
    }
}

