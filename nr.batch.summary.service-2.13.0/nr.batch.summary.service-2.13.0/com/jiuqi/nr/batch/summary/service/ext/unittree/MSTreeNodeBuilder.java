/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider
 *  com.jiuiqi.nr.unit.treebase.source.def.DefaultTreeNodeBuilder
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 *  com.jiuqi.nr.batch.summary.storage.utils.DateUtils
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.itreebase.nodeicon.IconCategory
 *  com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider
 */
package com.jiuqi.nr.batch.summary.service.ext.unittree;

import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.source.def.DefaultTreeNodeBuilder;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nr.batch.summary.storage.utils.DateUtils;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.nodeicon.IconCategory;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider;
import java.util.Date;

public class MSTreeNodeBuilder
extends DefaultTreeNodeBuilder {
    private SummaryScheme scheme;
    private String sumDataTimeStr;
    private IconSourceProvider iconProvider;

    public MSTreeNodeBuilder(SummaryScheme scheme, IUnitTreeEntityRowProvider dimRowProvider, IconSourceProvider iconProvider) {
        super(dimRowProvider, iconProvider);
        this.scheme = scheme;
        this.iconProvider = iconProvider;
        this.sumDataTimeStr = DateUtils.date_str_yyyy_mm_dd_HH_mm_ss((Date)scheme.getSumDataTime());
    }

    public ITree<IBaseNodeData> buildTreeNode(IEntityRow row) {
        ITree node = super.buildTreeNode(row);
        node.setLeaf(this.dimRowProvider.isLeaf((IBaseNodeData)node.getData()));
        ((IBaseNodeData)node.getData()).put("batchGatherSchemeCode", (Object)this.scheme.getKey());
        ((IBaseNodeData)node.getData()).put("sumDataTime", (Object)this.sumDataTimeStr);
        ((IBaseNodeData)node.getData()).put("nodeType", (Object)"SUMMARY");
        node.setIcons(new String[]{this.getBaseIconKey((IBaseNodeData)node.getData())});
        node.setDisabled(this.isGroupNode((IBaseNodeData)node.getData()));
        return node;
    }

    protected String getBaseIconKey(IBaseNodeData data) {
        return this.isGroupNode(data) ? this.iconProvider.getDefaultIconKey() : this.iconProvider.getIconKey(IconCategory.NODE_ICONS, "7");
    }

    private boolean isGroupNode(IBaseNodeData data) {
        return this.scheme.getKey().equals(data.getKey());
    }
}

