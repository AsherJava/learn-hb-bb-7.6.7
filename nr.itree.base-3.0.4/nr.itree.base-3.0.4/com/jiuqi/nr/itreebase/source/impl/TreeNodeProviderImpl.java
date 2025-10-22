/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.common.itree.ITreeBreadthFirstIterator
 */
package com.jiuqi.nr.itreebase.source.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.common.itree.ITreeBreadthFirstIterator;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.source.INodeDataBuilder;
import com.jiuqi.nr.itreebase.source.INodeDataSource;
import com.jiuqi.nr.itreebase.source.ITreeNodeProvider;
import java.util.ArrayList;
import java.util.List;

public class TreeNodeProviderImpl
implements ITreeNodeProvider {
    protected IBaseNodeData actionNode;
    protected INodeDataSource dataProvider;
    protected INodeDataBuilder nodeBuilder;

    public TreeNodeProviderImpl(INodeDataSource dataProvider, INodeDataBuilder nodeBuilder, IBaseNodeData actionNode) {
        this.nodeBuilder = nodeBuilder;
        this.dataProvider = dataProvider;
        this.actionNode = actionNode;
    }

    @Override
    public List<ITree<IBaseNodeData>> getRoots() {
        return this.getChildren(null);
    }

    @Override
    public List<ITree<IBaseNodeData>> getChildren(IBaseNodeData parentNode) {
        ArrayList<ITree<IBaseNodeData>> children = new ArrayList<ITree<IBaseNodeData>>();
        List<IBaseNodeData> chRows = null == parentNode ? this.dataProvider.getRoots() : this.dataProvider.getChildren(parentNode);
        this.nodeBuilder.beforeCreateITreeNode(chRows);
        for (IBaseNodeData data : chRows) {
            children.add(this.nodeBuilder.buildTreeNode(data));
        }
        return children;
    }

    @Override
    public List<ITree<IBaseNodeData>> getTree() {
        List<String> path;
        List<ITree<IBaseNodeData>> tree = this.getRoots();
        if (this.actionNode != null && StringUtils.isNotEmpty((String)this.actionNode.getKey()) && null != (path = this.dataProvider.getNodePath(this.actionNode)) && !path.isEmpty()) {
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

