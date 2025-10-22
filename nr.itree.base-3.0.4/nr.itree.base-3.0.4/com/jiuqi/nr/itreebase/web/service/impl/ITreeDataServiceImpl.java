/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.unit.treecommon.exception.UnitTreeRuntimeException
 *  com.jiuqi.nr.unit.treecommon.utils.JavaBeanUtils
 *  org.json.JSONObject
 */
package com.jiuqi.nr.itreebase.web.service.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.itreebase.context.ITreeContextData;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.source.ITreeDataSource;
import com.jiuqi.nr.itreebase.source.ITreeNodeProvider;
import com.jiuqi.nr.itreebase.source.search.ISearchNodeDataPage;
import com.jiuqi.nr.itreebase.source.search.ISearchNodeProvider;
import com.jiuqi.nr.itreebase.source.search.SearchContextData;
import com.jiuqi.nr.itreebase.source.search.SearchNodeDataPage;
import com.jiuqi.nr.itreebase.web.service.ITreeDataService;
import com.jiuqi.nr.unit.treecommon.exception.UnitTreeRuntimeException;
import com.jiuqi.nr.unit.treecommon.utils.JavaBeanUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ITreeDataServiceImpl
implements ITreeDataService {
    private Map<String, ITreeDataSource> sourceMap;
    static final String KEY_OF_SEARCH_CONTEXT = "searchContext";

    @Autowired(required=true)
    public ITreeDataServiceImpl(List<ITreeDataSource> list) {
        if (null != list) {
            this.sourceMap = new HashMap<String, ITreeDataSource>();
            for (ITreeDataSource e : list) {
                this.sourceMap.put(e.getSourceId(), e);
            }
        }
    }

    @Override
    public List<ITree<IBaseNodeData>> getTree(ITreeContextData contextData) {
        ITreeDataSource dataSource = this.getITreeDataSource(contextData.getDataSourceId());
        ITreeNodeProvider provider = dataSource.getTreeNodeProvider(contextData);
        return provider.getTree();
    }

    @Override
    public List<ITree<IBaseNodeData>> getChildren(ITreeContextData contextData) {
        ITreeDataSource dataSource = this.getITreeDataSource(contextData.getDataSourceId());
        ITreeNodeProvider provider = dataSource.getTreeNodeProvider(contextData);
        return provider.getChildren(contextData.getActionNode());
    }

    @Override
    public ISearchNodeDataPage searchingNodes(ITreeContextData contextData) {
        SearchContextData searchContext = this.getSearchContext(contextData);
        if (searchContext != null) {
            ITreeDataSource dataSource = this.getITreeDataSource(contextData.getDataSourceId());
            ISearchNodeProvider searchDataProvider = dataSource.getSearchDataProvider(contextData);
            int pageSize = searchContext.getPageSize();
            int currentPage = searchContext.getCurrentPage();
            String[] keyWords = searchContext.getKeywords();
            List<IBaseNodeData> onePage = searchDataProvider.getOnePage(keyWords, pageSize, currentPage);
            int totalSize = searchDataProvider.getTotalSize();
            return this.getSearchOnePageData(onePage, totalSize, pageSize, currentPage);
        }
        return this.getSearchOnePageData(new ArrayList<IBaseNodeData>(), 0, 50, 0);
    }

    private ITreeDataSource getITreeDataSource(String sourceId) {
        ITreeDataSource dataSource;
        ITreeDataSource iTreeDataSource = dataSource = StringUtils.isNotEmpty((String)sourceId) ? this.sourceMap.get(sourceId) : null;
        if (dataSource == null) {
            throw new UnitTreeRuntimeException("\u65e0\u6548\u7684\u8d44\u6e90ID\uff0c\u6811\u5f62\u52a0\u8f7d\u5931\u8d25\uff01");
        }
        return dataSource;
    }

    private ISearchNodeDataPage getSearchOnePageData(List<IBaseNodeData> pageData, int totalSize, int pageSize, int currentPage) {
        SearchNodeDataPage impl = new SearchNodeDataPage();
        impl.setTotalSize(totalSize);
        impl.setPageSize(pageSize);
        impl.setCurrentPage(currentPage);
        impl.setPageData(pageData);
        return impl;
    }

    private SearchContextData getSearchContext(ITreeContextData contextData) {
        JSONObject customVariable = contextData.getCustomVariable();
        return (SearchContextData)JavaBeanUtils.toJavaBean((String)customVariable.get(KEY_OF_SEARCH_CONTEXT).toString(), SearchContextData.class);
    }
}

