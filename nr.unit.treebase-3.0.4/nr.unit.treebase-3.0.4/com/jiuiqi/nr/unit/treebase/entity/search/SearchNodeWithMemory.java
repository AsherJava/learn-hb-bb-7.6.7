/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.itreebase.node.BaseNodeDataImpl
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.itreebase.source.search.ISearchNodeProvider
 */
package com.jiuiqi.nr.unit.treebase.entity.search;

import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.itreebase.node.BaseNodeDataImpl;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.source.search.ISearchNodeProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SearchNodeWithMemory
implements ISearchNodeProvider {
    protected int totalSize;
    private IUnitTreeEntityRowProvider entityRowProvider;

    public SearchNodeWithMemory(IUnitTreeEntityRowProvider entityRowProvider) {
        this.entityRowProvider = entityRowProvider;
    }

    public int getTotalSize() {
        return this.totalSize;
    }

    public List<IBaseNodeData> getTotalPage(String[] keywords) {
        List<IEntityRow> matchRows = this.matchEntityRows(keywords);
        this.totalSize = matchRows.size();
        return matchRows.stream().map(this::madeSearchNodeData).collect(Collectors.toList());
    }

    public List<IBaseNodeData> getOnePage(String[] keywords, int pageSize, int currentPage) {
        List<IEntityRow> matchRows = this.matchEntityRows(keywords);
        this.totalSize = matchRows.size();
        int fromIndex = currentPage * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, this.totalSize);
        List<IEntityRow> pageRows = matchRows.subList(fromIndex, toIndex);
        return pageRows.stream().map(this::madeSearchNodeData).collect(Collectors.toList());
    }

    protected List<IEntityRow> matchEntityRows(String[] keywords) {
        List<IEntityRow> allRows = this.entityRowProvider.getAllRows();
        ArrayList<IEntityRow> matchRows = new ArrayList<IEntityRow>();
        for (IEntityRow row : allRows) {
            if (!this.checkEntityRow(row, keywords)) continue;
            matchRows.add(row);
        }
        return matchRows;
    }

    protected boolean checkEntityRow(IEntityRow row, String[] keywords) {
        List<String> strings = Arrays.asList(keywords);
        return this.matchCode(row, strings) || this.matchTitle(row, strings);
    }

    protected boolean matchCode(IEntityRow row, List<String> matchCodes) {
        for (String matchCode : matchCodes) {
            if (!row.getCode().toLowerCase().contains(matchCode.toLowerCase())) continue;
            return true;
        }
        return false;
    }

    protected boolean matchTitle(IEntityRow row, List<String> matchTitles) {
        for (String matchTitle : matchTitles) {
            if (!row.getTitle().toLowerCase().contains(matchTitle.toLowerCase())) continue;
            return true;
        }
        return false;
    }

    protected IBaseNodeData madeSearchNodeData(IEntityRow row) {
        BaseNodeDataImpl impl = new BaseNodeDataImpl();
        impl.putKey(row.getEntityKeyData());
        impl.putCode(row.getCode());
        impl.putTitle(row.getTitle());
        impl.setPath(this.entityRowProvider.getNodePath((IBaseNodeData)impl));
        return impl;
    }
}

