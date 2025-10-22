/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.lwtree.provider;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.lwtree.provider.ITreeSearchProvider;
import com.jiuqi.nr.lwtree.query.IEntityRowQueryer;
import com.jiuqi.nr.lwtree.request.SearchParam;
import com.jiuqi.nr.lwtree.response.INodeInfos;
import com.jiuqi.nr.lwtree.response.NodePageInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public abstract class ITreeSearchAdapter<E extends INode>
implements ITreeSearchProvider<E> {
    private IEntityRowQueryer queryer;
    private SearchParam searchParam;

    public ITreeSearchAdapter(IEntityRowQueryer queryer, SearchParam searchParam) {
        this.queryer = queryer;
        this.searchParam = searchParam;
    }

    @Override
    public INodeInfos<E> searchNode() {
        NodePageInfo nodesInfo = new NodePageInfo();
        if (this.searchParam != null) {
            String searchText = this.searchParam.getSearchText();
            if (StringUtils.isNotEmpty((String)(searchText = ITreeSearchAdapter.trim(searchText)))) {
                List<E> data = this.getSearchNodes(searchText);
                int totalCount = data.size();
                int pagesize = this.searchParam.getPagesize();
                int currentPage = this.searchParam.getCurrentPage();
                boolean isMaxPage = (currentPage + 1) * pagesize >= totalCount;
                data = this.pageSearchResult(data, totalCount, pagesize, currentPage);
                nodesInfo = this.buildPageNodesInfo(data, isMaxPage, pagesize, currentPage, totalCount);
            }
        }
        return nodesInfo;
    }

    protected NodePageInfo<E> buildPageNodesInfo(List<E> data, boolean isMaxPage, int pagesize, int currentPage, int totalCount) {
        NodePageInfo<E> nodesPageInfo = new NodePageInfo<E>();
        nodesPageInfo.setPageData(data);
        nodesPageInfo.setPagesize(pagesize);
        nodesPageInfo.setCurrentPage(currentPage);
        nodesPageInfo.setMaxPage(isMaxPage);
        nodesPageInfo.setTotalCount(totalCount);
        return nodesPageInfo;
    }

    protected List<E> pageSearchResult(List<E> data, int totalCount, int pagesize, int currentPage) {
        if (data != null && data.size() > 0) {
            int totalPage;
            int n = totalPage = totalCount % pagesize == 0 ? totalCount / pagesize : totalCount / pagesize + 1;
            if (currentPage < totalPage) {
                int fromIndex = currentPage * pagesize;
                int toIndex = fromIndex + pagesize >= totalCount ? totalCount : fromIndex + pagesize;
                List<E> pageRows = data.subList(fromIndex, toIndex);
                return pageRows;
            }
        }
        return data;
    }

    protected List<E> getSearchNodes(String searchText) {
        ArrayList<E> data = new ArrayList<E>(0);
        List<IEntityRow> rows = this.searchNodes(searchText);
        for (IEntityRow r : rows) {
            data.add(this.buidDataRow(r));
        }
        return data;
    }

    protected abstract E buidDataRow(IEntityRow var1);

    protected List<IEntityRow> searchNodes(String searchText) {
        ArrayList<IEntityRow> rows = new ArrayList<IEntityRow>(0);
        String[] searchTxtArr = searchText.split("\\|");
        Stack stack = new Stack();
        List<IEntityRow> rootRows = this.queryer.getRootRows();
        rootRows.forEach(stack::push);
        while (!stack.isEmpty()) {
            List<IEntityRow> childRows;
            IEntityRow row = (IEntityRow)stack.pop();
            String title = row.getTitle();
            String code = row.getCode();
            for (String sTxt : searchTxtArr) {
                if (title != null && title.contains(sTxt)) {
                    rows.add(row);
                    break;
                }
                if (code == null || !code.contains(sTxt)) continue;
                rows.add(row);
                break;
            }
            if ((childRows = this.queryer.getChildRows(row.getEntityKeyData())) == null || childRows.isEmpty()) continue;
            childRows.forEach(stack::push);
        }
        return rows;
    }

    public static String trim(String str) {
        if (str != null) {
            return str.replaceAll("\\s*", "");
        }
        return null;
    }
}

