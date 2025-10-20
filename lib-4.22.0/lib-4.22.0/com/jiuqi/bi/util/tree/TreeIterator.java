/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.tree;

import com.jiuqi.bi.util.tree.TreeNode;
import java.util.Iterator;
import java.util.Stack;

class TreeIterator
implements Iterator {
    private Stack stack = new Stack();

    public TreeIterator(TreeNode root) {
        this.stack.push(new NodeStub(root, 0));
    }

    @Override
    public boolean hasNext() {
        while (!this.stack.empty()) {
            NodeStub prev = (NodeStub)this.stack.peek();
            if (prev.next < prev.node.size()) {
                return true;
            }
            this.stack.pop();
        }
        return false;
    }

    public Object next() {
        while (!this.stack.empty()) {
            NodeStub prev = (NodeStub)this.stack.peek();
            if (prev.next < prev.node.size()) {
                TreeNode nextNode = prev.node.get(prev.next);
                if (nextNode.size() > 0) {
                    this.stack.push(new NodeStub(nextNode, 0));
                }
                ++prev.next;
                return nextNode.getItem();
            }
            this.stack.pop();
        }
        return null;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("\u6811\u5f62\u679a\u4e3e\u5668\u4e0d\u652f\u6301\u5220\u9664\u64cd\u4f5c\uff01");
    }

    private static class NodeStub {
        public TreeNode node = null;
        public int next = 0;

        public NodeStub(TreeNode node, int next) {
            this.node = node;
            this.next = next;
        }
    }
}

