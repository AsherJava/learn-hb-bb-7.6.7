/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.itreebase.source.impl;

import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.source.INodeDataSource;
import com.jiuqi.nr.itreebase.source.search.ISearchNodeProvider;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class TreeSearchProviderImpl
implements ISearchNodeProvider {
    protected int totalSize;
    protected INodeDataSource nodeDataSource;

    public TreeSearchProviderImpl(INodeDataSource nodeDataSource) {
        this.nodeDataSource = nodeDataSource;
    }

    @Override
    public int getTotalSize() {
        return this.totalSize;
    }

    @Override
    public List<IBaseNodeData> getTotalPage(String[] keywords) {
        List<IBaseNodeData> matchRows = this.matchEntityRows(keywords);
        this.totalSize = matchRows.size();
        return matchRows;
    }

    @Override
    public List<IBaseNodeData> getOnePage(String[] keywords, int pageSize, int currentPage) {
        List<IBaseNodeData> matchRows = this.getTotalPage(keywords);
        int fromIndex = currentPage * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, this.totalSize);
        return matchRows.subList(fromIndex, toIndex);
    }

    protected List<IBaseNodeData> matchEntityRows(String[] keywords) {
        ArrayList<IBaseNodeData> matchRows = new ArrayList<IBaseNodeData>();
        List<IBaseNodeData> roots = this.nodeDataSource.getRoots();
        Stack<IBaseNodeData> stack = new Stack<IBaseNodeData>();
        stack.addAll(roots);
        while (!stack.isEmpty()) {
            List<IBaseNodeData> children;
            IBaseNodeData target = (IBaseNodeData)stack.pop();
            if (this.checkEntityRow(target, keywords)) {
                matchRows.add(target);
            }
            if ((children = this.nodeDataSource.getChildren(target)) == null) continue;
            stack.addAll(children);
        }
        return matchRows;
    }

    protected boolean checkEntityRow(IBaseNodeData row, String[] keywords) {
        List<String> strings = Arrays.asList(keywords);
        return this.matchCode(row, strings) || this.matchTitle(row, strings);
    }

    protected boolean matchCode(IBaseNodeData row, List<String> matchCodes) {
        for (String matchCode : matchCodes) {
            if (!StringUtils.isNotEmpty((String)row.getCode()) || !row.getCode().toLowerCase().contains(matchCode.toLowerCase())) continue;
            return true;
        }
        return false;
    }

    protected boolean matchTitle(IBaseNodeData row, List<String> matchTitles) {
        for (String matchTitle : matchTitles) {
            if (!StringUtils.isNotEmpty((String)row.getTitle()) || !row.getTitle().toLowerCase().contains(matchTitle.toLowerCase())) continue;
            return true;
        }
        return false;
    }
}

