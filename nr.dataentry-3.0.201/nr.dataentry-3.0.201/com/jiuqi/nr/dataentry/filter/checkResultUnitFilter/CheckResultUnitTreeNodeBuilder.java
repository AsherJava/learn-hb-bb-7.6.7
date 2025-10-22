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
package com.jiuqi.nr.dataentry.filter.checkResultUnitFilter;

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
import java.util.List;

public class CheckResultUnitTreeNodeBuilder
implements IUnitTreeNodeBuilder {
    protected IconSourceProvider iconProvider;
    protected IUnitTreeEntityRowProvider dimRowProvider;
    protected UnitTreeSystemConfig unitTreeSystemConfig;
    protected List<String> checklist;
    protected Boolean firstNode;

    public CheckResultUnitTreeNodeBuilder(IUnitTreeEntityRowProvider dimRowProvider, IconSourceProvider iconProvider, List<String> checklist) {
        this.iconProvider = iconProvider;
        this.dimRowProvider = dimRowProvider;
        this.unitTreeSystemConfig = (UnitTreeSystemConfig)SpringBeanUtils.getBean(UnitTreeSystemConfig.class);
        this.checklist = checklist;
        this.firstNode = true;
    }

    public ITree<IBaseNodeData> buildTreeNode(IEntityRow row) {
        IBaseNodeData data = this.implData(row);
        ITree node = new ITree((INode)data);
        node.setLeaf(true);
        node.setIcons(new String[]{this.iconProvider.getDefaultIconKey()});
        if (this.checklist != null && this.checklist.contains(node.getKey())) {
            node.setChecked(true);
        }
        if (this.firstNode.booleanValue()) {
            node.setSelected(true);
            this.firstNode = false;
        }
        return node;
    }

    protected IBaseNodeData implData(IEntityRow row) {
        BaseNodeDataImpl data = new BaseNodeDataImpl();
        data.setKey(row.getEntityKeyData());
        data.setCode(row.getCode());
        data.setTitle(row.getTitle());
        return data;
    }
}

