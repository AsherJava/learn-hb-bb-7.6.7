/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.intf.impl.DataRowImpl
 */
package com.jiuqi.nr.data.engine.grouping.multi;

import com.jiuqi.np.dataengine.intf.impl.DataRowImpl;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class GroupRowTreeNode
implements Iterable<GroupRowTreeNode> {
    private DataRowImpl dataRow;
    private List<GroupRowTreeNode> children;
    private String groupKey;
    private Set<String> childrenKeys;
    private boolean hasRootRow = true;
    private int childrenSize;

    public GroupRowTreeNode(DataRowImpl dataRow, String groupKey) {
        this.groupKey = groupKey;
        this.dataRow = dataRow;
        this.children = new ArrayList<GroupRowTreeNode>();
        this.childrenKeys = new HashSet<String>();
    }

    public DataRowImpl getDataRow() {
        return this.dataRow;
    }

    public List<GroupRowTreeNode> getChildren() {
        return this.children;
    }

    public void addChild(GroupRowTreeNode child) {
        this.addChild(child, false);
    }

    public void addChild(GroupRowTreeNode child, boolean forkAdd) {
        if (!forkAdd && this.childrenKeys.contains(child.getGroupKey())) {
            return;
        }
        this.childrenKeys.add(child.getGroupKey());
        this.children.add(child);
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public int getDeep() {
        return this.dataRow.getGroupTreeDeep();
    }

    public void removeSingleDetailNodes() {
        this.removeSingleDetailNodesHelper(this);
    }

    private void removeSingleDetailNodesHelper(GroupRowTreeNode node) {
        if (node == null) {
            return;
        }
        List<GroupRowTreeNode> children = node.getChildren();
        for (GroupRowTreeNode child : children) {
            this.removeSingleDetailNodesHelper(child);
            if (child.childrenSize != 1) continue;
            child.singleNodeUpgrade();
        }
    }

    private void singleNodeUpgrade() {
        if (this.childrenSize == 1) {
            GroupRowTreeNode grandChild = this.children.get(0);
            this.children = grandChild.children;
            this.childrenKeys = grandChild.childrenKeys;
            this.hasRootRow = grandChild.hasRootRow;
            this.groupKey = grandChild.groupKey;
            this.dataRow = grandChild.dataRow;
            this.childrenSize = grandChild.childrenSize;
        }
    }

    private static void printTree(GroupRowTreeNode node, int level) {
        for (int i = 0; i < level; ++i) {
            System.out.print("   ");
        }
        System.out.println(node.getGroupKey());
        for (GroupRowTreeNode child : node.getChildren()) {
            GroupRowTreeNode.printTree(child, level + 1);
        }
    }

    public void sortTreeByGroupKey() {
        this.sortTreeByComparator(Comparator.comparing(GroupRowTreeNode::getGroupKey));
    }

    public void sortTreeByComparator(Comparator<GroupRowTreeNode> comparator) {
        this.sortTreeHelper(this, comparator);
    }

    private void sortTreeHelper(GroupRowTreeNode node, Comparator<GroupRowTreeNode> comparator) {
        if (node == null) {
            return;
        }
        List<GroupRowTreeNode> children = node.getChildren();
        children.sort(comparator);
        for (GroupRowTreeNode child : children) {
            this.sortTreeHelper(child, comparator);
        }
    }

    @Override
    public Iterator<GroupRowTreeNode> iterator() {
        return new GroupRowTreeNodeIterator();
    }

    public void removeDetailRows() {
        this.removeDetailRowsHelper(this);
    }

    public boolean isDetailRow() {
        return this.dataRow.getGroupTreeDeep() < 0;
    }

    private void removeDetailRowsHelper(GroupRowTreeNode node) {
        node.getChildren().removeIf(child -> {
            if (child.isDetailRow()) {
                return true;
            }
            this.removeDetailRowsHelper((GroupRowTreeNode)child);
            return false;
        });
    }

    public void childrenSizePlus() {
        ++this.childrenSize;
    }

    public void setHasRootRow(boolean hasRootRow) {
        this.hasRootRow = hasRootRow;
    }

    private class GroupRowTreeNodeIterator
    implements Iterator<GroupRowTreeNode> {
        private final Stack<GroupRowTreeNode> stack = new Stack();

        public GroupRowTreeNodeIterator() {
            if (GroupRowTreeNode.this.hasRootRow) {
                this.stack.push(GroupRowTreeNode.this);
            } else {
                for (int i = GroupRowTreeNode.this.children.size() - 1; i >= 0; --i) {
                    this.stack.push((GroupRowTreeNode)GroupRowTreeNode.this.children.get(i));
                }
            }
        }

        @Override
        public boolean hasNext() {
            return !this.stack.isEmpty();
        }

        @Override
        public GroupRowTreeNode next() {
            if (!this.hasNext()) {
                throw new IllegalStateException("No more elements");
            }
            GroupRowTreeNode currentNode = this.stack.pop();
            List<GroupRowTreeNode> children = currentNode.getChildren();
            for (int i = children.size() - 1; i >= 0; --i) {
                this.stack.push(children.get(i));
            }
            return currentNode;
        }
    }
}

