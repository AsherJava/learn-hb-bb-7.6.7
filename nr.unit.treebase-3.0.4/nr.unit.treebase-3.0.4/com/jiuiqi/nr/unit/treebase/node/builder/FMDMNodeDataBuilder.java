/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 */
package com.jiuiqi.nr.unit.treebase.node.builder;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.entity.query.FMDMNodeTitleQuery;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import java.util.List;
import java.util.stream.Collectors;

public class FMDMNodeDataBuilder
implements IUnitTreeNodeBuilder {
    protected IUnitTreeContext context;
    protected IUnitTreeNodeBuilder nodeBuilder;
    protected FMDMNodeTitleQuery nodeTitleQuery;
    private static final String KEY_OF_REAL_TITLE = "title";

    public FMDMNodeDataBuilder(IUnitTreeContext context, IUnitTreeNodeBuilder nodeBuilder) {
        this.context = context;
        this.nodeBuilder = nodeBuilder;
        this.nodeTitleQuery = new FMDMNodeTitleQuery(context);
    }

    @Override
    public void beforeCreateITreeNode(List<IEntityRow> rows) {
        this.nodeBuilder.beforeCreateITreeNode(rows);
        this.nodeTitleQuery.batchQueryCacheRowTitle(rows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList()));
    }

    @Override
    public ITree<IBaseNodeData> buildTreeNode(IEntityRow row) {
        ITree<IBaseNodeData> node = this.nodeBuilder.buildTreeNode(row);
        ((IBaseNodeData)node.getData()).put(KEY_OF_REAL_TITLE, (Object)node.getTitle());
        node.setTitle(this.nodeTitleQuery.getAttributesTitle(row.getEntityKeyData()));
        return node;
    }
}

