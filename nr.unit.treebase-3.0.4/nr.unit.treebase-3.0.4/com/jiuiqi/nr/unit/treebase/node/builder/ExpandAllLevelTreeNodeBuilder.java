/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.itreebase.node.BaseNodeDataImpl
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider
 *  com.jiuqi.nr.unit.treestore.systemconfig.UnitTreeSystemConfig
 */
package com.jiuiqi.nr.unit.treebase.node.builder;

import com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.itreebase.node.BaseNodeDataImpl;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider;
import com.jiuqi.nr.unit.treestore.systemconfig.UnitTreeSystemConfig;

public class ExpandAllLevelTreeNodeBuilder
implements IUnitTreeNodeBuilder {
    private IEntityTable structTreeRows;
    private IconSourceProvider iconProvider;
    private UnitTreeSystemConfig unitTreeSystemConfig;

    public ExpandAllLevelTreeNodeBuilder(IconSourceProvider iconProvider, IEntityTable structTreeRows) {
        this.iconProvider = iconProvider;
        this.structTreeRows = structTreeRows;
        this.unitTreeSystemConfig = (UnitTreeSystemConfig)SpringBeanUtils.getBean(UnitTreeSystemConfig.class);
    }

    @Override
    public ITree<IBaseNodeData> buildTreeNode(IEntityRow row) {
        IBaseNodeData data = this.implData(row);
        ITree node = new ITree((INode)data);
        node.setLeaf(this.structTreeRows.getDirectChildCount(row.getEntityKeyData()) == 0);
        node.setIcons(new String[]{this.iconProvider.getDefaultIconKey()});
        return node;
    }

    protected IBaseNodeData implData(IEntityRow row) {
        BaseNodeDataImpl data = new BaseNodeDataImpl();
        data.setKey(row.getEntityKeyData());
        data.setCode(row.getCode());
        data.setTitle(row.getTitle());
        if (this.unitTreeSystemConfig.isCountOfAllChildrenQuantities()) {
            data.put("childCount", (Object)this.structTreeRows.getAllChildCount(row.getEntityKeyData()));
        }
        return data;
    }
}

