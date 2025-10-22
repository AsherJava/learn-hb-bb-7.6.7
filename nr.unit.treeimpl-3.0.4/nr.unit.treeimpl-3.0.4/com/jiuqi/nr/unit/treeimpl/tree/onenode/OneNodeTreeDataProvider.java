/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.itreebase.node.BaseNodeDataImpl
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.itreebase.source.ITreeNodeProvider
 */
package com.jiuqi.nr.unit.treeimpl.tree.onenode;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.itreebase.node.BaseNodeDataImpl;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.source.ITreeNodeProvider;
import java.util.ArrayList;
import java.util.List;

public class OneNodeTreeDataProvider
implements ITreeNodeProvider {
    private IUnitTreeContext context;
    private IUnitTreeEntityRowProvider entityRowProvider;

    public OneNodeTreeDataProvider(IUnitTreeContext context, IUnitTreeEntityRowProvider entityRowProvider) {
        this.context = context;
        this.entityRowProvider = entityRowProvider;
    }

    public List<ITree<IBaseNodeData>> getTree() {
        IBaseNodeData actionNode = this.context.getActionNode();
        IEntityRow rowData = this.entityRowProvider.findEntityRow(actionNode);
        ArrayList<ITree<IBaseNodeData>> treeNodes = new ArrayList<ITree<IBaseNodeData>>();
        if (rowData != null) {
            treeNodes.add(this.buildTreeNode(rowData));
            return treeNodes;
        }
        return treeNodes;
    }

    public List<ITree<IBaseNodeData>> getRoots() {
        return null;
    }

    public List<ITree<IBaseNodeData>> getChildren(IBaseNodeData parentNode) {
        return null;
    }

    private ITree<IBaseNodeData> buildTreeNode(IEntityRow row) {
        IBaseNodeData dataImpl = this.getINodeData(row);
        ITree node = new ITree((INode)dataImpl);
        node.setLeaf(true);
        return node;
    }

    private IBaseNodeData getINodeData(IEntityRow row) {
        BaseNodeDataImpl data = new BaseNodeDataImpl();
        data.setKey(row.getEntityKeyData());
        data.setCode(row.getCode());
        data.setTitle(row.getTitle());
        return data;
    }
}

