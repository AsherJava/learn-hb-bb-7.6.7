/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.entity.filter.IFilterEntityRow
 *  com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.itreebase.node.BaseNodeDataImpl
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.itreebase.nodemap.TreeNode
 *  com.jiuqi.nr.itreebase.nodemap.TreeNode$traverPloy
 */
package com.jiuqi.nr.unit.treeimpl.filter;

import com.jiuiqi.nr.unit.treebase.entity.filter.IFilterEntityRow;
import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.itreebase.node.BaseNodeDataImpl;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.nodemap.TreeNode;
import com.jiuqi.nr.unit.treeimpl.filter.FilterWithQueryTreeProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

public class FilterWithBuildTreeProvider
extends FilterWithQueryTreeProvider {
    protected List<TreeNode<IEntityRow>> tree;
    protected Map<String, TreeNode<IEntityRow>> nodeMap;

    public FilterWithBuildTreeProvider(IUnitTreeEntityRowProvider baseProvider, IFilterEntityRow checker) {
        super(baseProvider, checker);
        this.initTree();
    }

    @Override
    public String[] getNodePath(IBaseNodeData rowData) {
        TreeNode<IEntityRow> treeNode = this.nodeMap.get(rowData.getKey());
        if (treeNode != null) {
            return treeNode.getPath();
        }
        return new String[]{rowData.getKey()};
    }

    @Override
    public IEntityRow findEntityRow(IBaseNodeData rowData) {
        TreeNode<IEntityRow> treeNode = this.nodeMap.get(rowData.getKey());
        if (treeNode != null) {
            return (IEntityRow)treeNode.getData();
        }
        return null;
    }

    @Override
    public List<IEntityRow> getAllRows() {
        ArrayList<IEntityRow> allRows = new ArrayList<IEntityRow>();
        for (TreeNode<IEntityRow> treeNode : this.tree) {
            Iterator iterator = treeNode.iterator(TreeNode.traverPloy.DEPTH_FIRST);
            while (iterator.hasNext()) {
                allRows.add((IEntityRow)((TreeNode)iterator.next()).getData());
            }
        }
        return allRows;
    }

    @Override
    public List<IEntityRow> getRootRows() {
        return this.tree.stream().map(TreeNode::getData).collect(Collectors.toList());
    }

    @Override
    public List<IEntityRow> getChildRows(IBaseNodeData parent) {
        TreeNode<IEntityRow> treeNode = this.nodeMap.get(parent.getKey());
        if (treeNode != null) {
            return treeNode.getChildren().stream().map(TreeNode::getData).collect(Collectors.toList());
        }
        return new ArrayList<IEntityRow>();
    }

    @Override
    public int getDirectChildCount(IBaseNodeData parent) {
        TreeNode<IEntityRow> treeNode = this.nodeMap.get(parent.getKey());
        if (treeNode != null) {
            return treeNode.getChildren().size();
        }
        return 0;
    }

    protected void initTree() {
        this.tree = new ArrayList<TreeNode<IEntityRow>>();
        this.nodeMap = new HashMap<String, TreeNode<IEntityRow>>();
        List<IEntityRow> roots = this.filterChildRows(this.baseProvider.getRootRows());
        if (roots != null && !roots.isEmpty()) {
            this.tree = roots.stream().map(node -> this.buildTreeNode((IEntityRow)node, null)).collect(Collectors.toList());
            Stack<TreeNode<IEntityRow>> stack = new Stack<TreeNode<IEntityRow>>();
            this.travers2Stack(stack, this.tree);
            while (!stack.isEmpty()) {
                TreeNode<IEntityRow> target = stack.pop();
                List<IEntityRow> children = this.filterChildRows(this.baseProvider.getChildRows((IBaseNodeData)BaseNodeDataImpl.getInstance((String)target.getKey())));
                if (children == null || children.isEmpty()) continue;
                TreeNode<IEntityRow> parentNode = this.nodeMap.get(target.getKey());
                TreeNode<IEntityRow> finalTarget = target;
                List<TreeNode<IEntityRow>> childNodes = children.stream().map(node -> this.buildTreeNode((IEntityRow)node, finalTarget)).collect(Collectors.toList());
                parentNode.setChildren(childNodes);
                this.travers2Stack(stack, childNodes);
            }
        }
    }

    protected TreeNode<IEntityRow> buildTreeNode(IEntityRow row, TreeNode<IEntityRow> parentNode) {
        TreeNode node = new TreeNode(row.getEntityKeyData(), (Object)row);
        node.setParent(parentNode);
        this.nodeMap.put(node.getKey(), (TreeNode<IEntityRow>)node);
        return node;
    }

    private void travers2Stack(Stack<TreeNode<IEntityRow>> stack, List<TreeNode<IEntityRow>> nodes) {
        for (int i = nodes.size() - 1; i >= 0; --i) {
            stack.push(nodes.get(i));
        }
    }
}

