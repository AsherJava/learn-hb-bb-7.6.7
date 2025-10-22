/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 */
package com.jiuiqi.nr.unit.treebase.source.def;

import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder;
import com.jiuiqi.nr.unit.treebase.source.def.DefaultSelectFirstNodeProvider;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.LoggerFactory;

public class ExpandAllLevelTreeNodeProvider
extends DefaultSelectFirstNodeProvider {
    public ExpandAllLevelTreeNodeProvider(IUnitTreeEntityRowProvider dimRowProvider, IUnitTreeNodeBuilder nodeBuilder, IBaseNodeData actionNode) {
        super(dimRowProvider, nodeBuilder, actionNode);
    }

    @Override
    public List<ITree<IBaseNodeData>> getTree() {
        IEntityTable structTreeRows = this.dimRowProvider.getStructTreeRows();
        if (structTreeRows == null) {
            LoggerFactory.getLogger(this.getClass()).error("\u6784\u5efa\u5b8c\u6574\u7684\u6811\u5f62\u7ed3\u6784\u6570\u636e\u5931\u8d25\uff01");
        }
        List<ITree<IBaseNodeData>> tree = this.getChildren(structTreeRows, null);
        if (this.actionNode == null && !tree.isEmpty()) {
            this.actionNode = (IBaseNodeData)tree.get(0).getData();
        }
        ArrayDeque nodeQue = new ArrayDeque(0);
        tree.forEach(nodeQue::addLast);
        while (!nodeQue.isEmpty()) {
            ITree target = (ITree)nodeQue.peekFirst();
            List<ITree<IBaseNodeData>> children = this.getChildren(structTreeRows, (IBaseNodeData)target.getData());
            if (children != null && !children.isEmpty()) {
                target.setExpanded(true);
                target.setChildren(children);
                target.setSelected(target.getKey().equals(this.actionNode.getKey()));
                children.forEach(nodeQue::addLast);
            }
            nodeQue.pollFirst();
        }
        return tree;
    }

    public List<ITree<IBaseNodeData>> getChildren(IEntityTable structTree, IBaseNodeData parentNode) {
        ArrayList<ITree<IBaseNodeData>> children = new ArrayList<ITree<IBaseNodeData>>();
        List chRows = null == parentNode ? structTree.getRootRows() : structTree.getChildRows(parentNode.getKey());
        this.nodeBuilder.beforeCreateITreeNode(chRows);
        for (IEntityRow row : chRows) {
            children.add(this.nodeBuilder.buildTreeNode(row));
        }
        return children;
    }
}

