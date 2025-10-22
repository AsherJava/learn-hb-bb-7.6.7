/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider
 *  com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.common.itree.ITreeBreadthFirstIterator
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.itreebase.node.BaseNodeDataImpl
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.itreebase.source.ITreeNodeProvider
 *  org.json.JSONObject
 */
package com.jiuqi.nr.dataentry.internal.overview;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.common.itree.ITreeBreadthFirstIterator;
import com.jiuqi.nr.dataentry.internal.overview.OverviewRootDataRow;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.itreebase.node.BaseNodeDataImpl;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.source.ITreeNodeProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONObject;

public class OverviewTreeNodeProvider
implements ITreeNodeProvider {
    protected IBaseNodeData actionNode;
    protected IUnitTreeNodeBuilder nodeBuilder;
    protected IUnitTreeEntityRowProvider dimRowProvider;
    protected IUnitTreeContext ctx;

    public OverviewTreeNodeProvider(IUnitTreeEntityRowProvider dimRowProvider, IUnitTreeNodeBuilder nodeBuilder, IBaseNodeData actionNode, IUnitTreeContext ctx) {
        this.actionNode = actionNode;
        this.nodeBuilder = nodeBuilder;
        this.dimRowProvider = dimRowProvider;
        this.ctx = ctx;
    }

    public List<ITree<IBaseNodeData>> getRoots() {
        Object disabled;
        ArrayList<ITree<IBaseNodeData>> root = new ArrayList<ITree<IBaseNodeData>>();
        OverviewRootDataRow rootDataRow = OverviewRootDataRow.getRootDataRow();
        BaseNodeDataImpl instance = BaseNodeDataImpl.getInstance((String)"all-unit", (String)"all-unit", (String)rootDataRow.getTitle());
        ITree baseNodeDataITree = new ITree();
        baseNodeDataITree.setKey(instance.getKey());
        baseNodeDataITree.setCode(instance.getKey());
        baseNodeDataITree.setTitle(instance.getTitle());
        baseNodeDataITree.setLeaf(false);
        baseNodeDataITree.setData((INode)instance);
        baseNodeDataITree.setChildren(this.getChildren((IBaseNodeData)instance));
        JSONObject customVariable = this.ctx.getCustomVariable();
        if (customVariable != null && !customVariable.isEmpty() && (disabled = customVariable.get("disabled")) != null && disabled instanceof Boolean) {
            baseNodeDataITree.setDisabled(((Boolean)disabled).booleanValue());
        }
        root.add(baseNodeDataITree);
        return root;
    }

    public List<ITree<IBaseNodeData>> getChildren(IBaseNodeData parentNode) {
        ArrayList<ITree<IBaseNodeData>> children = new ArrayList<ITree<IBaseNodeData>>();
        List datas = new ArrayList();
        datas = "all-unit".equals(parentNode.getKey()) ? this.dimRowProvider.getRootRows() : this.dimRowProvider.getChildRows(parentNode);
        this.nodeBuilder.beforeCreateITreeNode(datas);
        for (IEntityRow row : datas) {
            children.add((ITree<IBaseNodeData>)this.nodeBuilder.buildTreeNode(row));
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

