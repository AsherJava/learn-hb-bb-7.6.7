/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider
 *  com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.itreebase.node.BaseNodeDataImpl
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider
 *  com.jiuqi.nr.unit.treestore.systemconfig.UnitTreeSystemConfig
 */
package com.jiuqi.nr.dataentry.internal.overview;

import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.itreebase.node.BaseNodeDataImpl;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider;
import com.jiuqi.nr.unit.treestore.systemconfig.UnitTreeSystemConfig;

public class OverViewTreeNodeBuilder
implements IUnitTreeNodeBuilder {
    protected IconSourceProvider iconProvider;
    protected IUnitTreeEntityRowProvider dimRowProvider;
    protected UnitTreeSystemConfig unitTreeSystemConfig;
    public static final String KEY_OF_CHILD_COUNT = "childCount";

    public OverViewTreeNodeBuilder(IUnitTreeEntityRowProvider dimRowProvider, IconSourceProvider iconProvider) {
        this.iconProvider = iconProvider;
        this.dimRowProvider = dimRowProvider;
        this.unitTreeSystemConfig = (UnitTreeSystemConfig)SpringBeanUtils.getBean(UnitTreeSystemConfig.class);
    }

    public ITree<IBaseNodeData> buildTreeNode(IEntityRow row) {
        IBaseNodeData data = this.implData(row);
        ITree node = new ITree((INode)data);
        if (row.getCode().equals("all-unit")) {
            node.setLeaf(false);
        } else {
            node.setLeaf(!row.hasChildren());
        }
        node.setIcons(new String[]{this.iconProvider.getDefaultIconKey()});
        return node;
    }

    protected IBaseNodeData implData(IEntityRow row) {
        BaseNodeDataImpl data = new BaseNodeDataImpl();
        if (row.getCode().equals("all-unit")) {
            data.setKey(row.getCode());
            data.setCode(row.getCode());
            data.setTitle(row.getTitle());
        } else {
            data.setKey(row.getEntityKeyData());
            data.setCode(row.getCode());
            data.setTitle(row.getTitle());
        }
        return data;
    }
}

