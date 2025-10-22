/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.itreebase.node.BaseNodeDataImpl
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.itreebase.source.search.ISearchNodeProvider
 */
package com.jiuiqi.nr.unit.treebase.entity.search;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.entity.query.FMDMNodeTitleQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.itreebase.node.BaseNodeDataImpl;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.source.search.ISearchNodeProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchNodeWithMemoryExtendAllDisplayField
implements ISearchNodeProvider {
    protected int totalSize;
    private FMDMNodeTitleQuery nodeTitleQuery;
    private IUnitTreeEntityRowProvider entityRowProvider;

    public SearchNodeWithMemoryExtendAllDisplayField(IUnitTreeContext context, IUnitTreeEntityRowProvider entityRowProvider) {
        this.entityRowProvider = entityRowProvider;
        this.nodeTitleQuery = new FMDMNodeTitleQuery(context);
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
        this.nodeTitleQuery.batchQueryCacheRowTitle(allRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList()));
        ArrayList<IEntityRow> matchRows = new ArrayList<IEntityRow>();
        for (IEntityRow row : allRows) {
            if (!this.checkEntityRow(row, keywords)) continue;
            matchRows.add(row);
        }
        return matchRows;
    }

    protected boolean checkEntityRow(IEntityRow row, String[] keywords) {
        return this.matchCode(row, keywords) || this.matchTitle(row, keywords) || this.matchDisplayField(row, keywords);
    }

    protected boolean matchCode(IEntityRow row, String[] matchCodes) {
        for (String matchCode : matchCodes) {
            if (!row.getCode().toLowerCase().contains(matchCode.toLowerCase())) continue;
            return true;
        }
        return false;
    }

    protected boolean matchTitle(IEntityRow row, String[] matchTitles) {
        for (String matchTitle : matchTitles) {
            if (!row.getTitle().toLowerCase().contains(matchTitle.toLowerCase())) continue;
            return true;
        }
        return false;
    }

    protected boolean matchDisplayField(IEntityRow row, String[] matchDisplayFields) {
        for (String matchDisplayField : matchDisplayFields) {
            if (!this.nodeTitleQuery.getAttributesTitle(row.getEntityKeyData()).toLowerCase().contains(matchDisplayField.toLowerCase())) continue;
            return true;
        }
        return false;
    }

    protected IBaseNodeData madeSearchNodeData(IEntityRow row) {
        BaseNodeDataImpl impl = new BaseNodeDataImpl();
        impl.putKey(row.getEntityKeyData());
        impl.putCode(row.getCode());
        impl.putTitle(this.nodeTitleQuery.getAttributesTitle(row.getEntityKeyData()));
        impl.setPath(this.entityRowProvider.getNodePath((IBaseNodeData)impl));
        return impl;
    }
}

