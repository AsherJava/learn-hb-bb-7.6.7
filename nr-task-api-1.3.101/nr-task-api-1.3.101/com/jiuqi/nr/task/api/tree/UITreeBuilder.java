/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.api.tree;

import com.jiuqi.nr.task.api.tree.TreeConfig;
import com.jiuqi.nr.task.api.tree.TreeData;
import com.jiuqi.nr.task.api.tree.UITreeNode;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

public class UITreeBuilder<T extends TreeData> {
    private final UITreeNode<T> root;
    private final List<UITreeNode<T>> treeList;
    private final TreeConfig treeConfig;
    private boolean isBuild;

    public UITreeBuilder() {
        this(TreeConfig.DEFAULT_CONFIG);
    }

    public UITreeBuilder(TreeConfig config) {
        this.treeConfig = config;
        this.root = new UITreeNode();
        this.treeList = new CopyOnWriteArrayList<UITreeNode<T>>();
    }

    public List<UITreeNode<T>> build() {
        this.checkBuilt();
        LinkedHashMap<String, UITreeNode<T>> treeMapTemp = new LinkedHashMap<String, UITreeNode<T>>();
        this.sortAndCopy(treeMapTemp);
        for (UITreeNode node : treeMapTemp.values()) {
            if (null == node) continue;
            String parent = node.getParent();
            if (treeMapTemp.containsKey(parent)) {
                UITreeNode parentNode = (UITreeNode)treeMapTemp.get(parent);
                if (null == parentNode) continue;
                this.addChild(parentNode, node);
                continue;
            }
            this.addChild(this.root, node);
        }
        this.clearBuilder();
        return this.root.getChildren();
    }

    private void addChild(UITreeNode<T> parentNode, UITreeNode<T> node) {
        this.solutionParent(parentNode, node);
        node.setSelected(this.getTreeConfig().isSelected(node.getKey()));
        List<UITreeNode<T>> children = parentNode.getChildren();
        if (null == children) {
            children = new ArrayList<UITreeNode<T>>();
            children.add(node);
            parentNode.setChildren(children);
        } else {
            children.add(node);
            children.sort(this.getTreeConfig().getComparator());
        }
    }

    private void solutionParent(UITreeNode<T> parent, UITreeNode<T> node) {
        parent.setLeaf(false);
        if (this.getTreeConfig().isSelected(node.getKey()) && !parent.isExpand()) {
            parent.setExpand(true);
        }
    }

    private void sortAndCopy(Map<String, UITreeNode<T>> treeMapTemp) {
        if (!CollectionUtils.isEmpty(this.treeList)) {
            this.treeList.stream().sorted(this.getTreeConfig().getComparator()).forEachOrdered(e -> treeMapTemp.put(null != e.getKey() ? e.getKey() : "root", (UITreeNode)e));
        }
    }

    private void clearBuilder() {
        this.treeList.clear();
        this.isBuild = true;
    }

    private void checkBuilt() {
        Assert.isTrue(!this.isBuild, "Current tree has been built.");
    }

    public UITreeBuilder<T> add(UITreeNode<T> n) {
        Assert.notNull(n, "The node cannot be null");
        this.checkBuilt();
        this.treeList.add(n);
        return this;
    }

    public UITreeBuilder<T> add(List<UITreeNode<T>> nodes) {
        Assert.notNull(nodes, "The nodes cannot be null");
        this.checkBuilt();
        this.treeList.addAll(nodes);
        return this;
    }

    private TreeConfig getTreeConfig() {
        return this.treeConfig;
    }
}

