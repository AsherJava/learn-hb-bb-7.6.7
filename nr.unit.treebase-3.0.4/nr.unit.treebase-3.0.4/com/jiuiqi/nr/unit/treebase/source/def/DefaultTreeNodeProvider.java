/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.common.itree.ITreeBreadthFirstIterator
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.itreebase.source.ITreeNodeProvider
 */
package com.jiuiqi.nr.unit.treebase.source.def;

import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.common.itree.ITreeBreadthFirstIterator;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.source.ITreeNodeProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DefaultTreeNodeProvider
implements ITreeNodeProvider {
    protected IBaseNodeData actionNode;
    protected IUnitTreeNodeBuilder nodeBuilder;
    protected IUnitTreeEntityRowProvider dimRowProvider;

    public DefaultTreeNodeProvider(IUnitTreeEntityRowProvider dimRowProvider, IUnitTreeNodeBuilder nodeBuilder, IBaseNodeData actionNode) {
        this.actionNode = actionNode;
        this.nodeBuilder = nodeBuilder;
        this.dimRowProvider = dimRowProvider;
    }

    public List<ITree<IBaseNodeData>> getRoots() {
        return this.getChildren(null);
    }

    public List<ITree<IBaseNodeData>> getChildren(IBaseNodeData parentNode) {
        List<IEntityRow> chRows;
        ArrayList<ITree<IBaseNodeData>> children = new ArrayList<ITree<IBaseNodeData>>();
        List<IEntityRow> list = chRows = null == parentNode ? this.dimRowProvider.getRootRows() : this.dimRowProvider.getChildRows(parentNode);
        assert (chRows != null);
        if (!chRows.isEmpty()) {
            this.nodeBuilder.beforeCreateITreeNode(chRows);
        }
        for (IEntityRow row : chRows) {
            children.add(this.nodeBuilder.buildTreeNode(row));
        }
        return children;
    }

    public List<ITree<IBaseNodeData>> getTree() {
        List<String> path;
        List<ITree<IBaseNodeData>> tree = this.getRoots();
        if (this.actionNode != null && StringUtils.isNotEmpty((String)this.actionNode.getKey()) && null != (path = Arrays.asList(this.dimRowProvider.getNodePath(this.actionNode))) && !path.isEmpty()) {
            this.clearSelectState(tree);
            ITreeBreadthFirstIterator iterator = new ITreeBreadthFirstIterator(tree);
            while (iterator.hasNext()) {
                ITree target = iterator.peekFirst();
                if (path.contains(target.getKey())) {
                    target.setExpanded(true);
                    target.setSelected(target.getKey().equals(this.actionNode.getKey()));
                    target.setChildren(this.getChildren((IBaseNodeData)target.getData()));
                }
                iterator.next();
            }
        }
        return tree;
    }

    protected void clearSelectState(List<ITree<IBaseNodeData>> tree) {
        ITreeBreadthFirstIterator iterator = new ITreeBreadthFirstIterator(tree);
        while (iterator.hasNext()) {
            iterator.next().setSelected(false);
        }
    }
}

