/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.itreebase.nodemap;

import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.nodemap.TreeNode;
import com.jiuqi.nr.itreebase.source.INodeDataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

public abstract class TreeBuilder
implements INodeDataSource {
    protected List<TreeNode<IBaseNodeData>> tree = new ArrayList<TreeNode<IBaseNodeData>>();
    protected Map<String, TreeNode<IBaseNodeData>> nodeMap = new HashMap<String, TreeNode<IBaseNodeData>>();

    protected void initTree() {
        List<IBaseNodeData> roots = this.getRoots();
        if (roots != null && !roots.isEmpty()) {
            this.tree = roots.stream().map(node -> this.buildTreeNode((IBaseNodeData)node, null)).collect(Collectors.toList());
            Stack stack = new Stack();
            stack.addAll(this.tree);
            while (!stack.isEmpty()) {
                TreeNode target = (TreeNode)stack.pop();
                List<IBaseNodeData> children = this.getChildren((IBaseNodeData)target.getData());
                if (children == null || children.isEmpty()) continue;
                TreeNode<IBaseNodeData> parentNode = this.nodeMap.get(target.getKey());
                TreeNode finalTarget = target;
                List childNodes = children.stream().map(node -> this.buildTreeNode((IBaseNodeData)node, finalTarget)).collect(Collectors.toList());
                parentNode.setChildren(childNodes);
                stack.addAll(childNodes);
            }
        }
    }

    protected TreeNode<IBaseNodeData> findTreeNode(IBaseNodeData data) {
        return this.nodeMap.get(data.getKey());
    }

    protected TreeNode<IBaseNodeData> buildTreeNode(IBaseNodeData data, TreeNode<IBaseNodeData> parentNode) {
        TreeNode<IBaseNodeData> node = new TreeNode<IBaseNodeData>(data.getKey(), data);
        node.setParent(parentNode);
        this.nodeMap.put(node.getKey(), node);
        return node;
    }
}

