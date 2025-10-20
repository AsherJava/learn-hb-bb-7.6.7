/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.idx;

import com.jiuqi.bi.dataset.idx.BMapTreeNode;
import java.util.ArrayList;
import java.util.List;

class BMapTree {
    private BMapTreeNode root = new BMapTreeNode("-");

    public void add(int rowIdx, Object[] keyData) {
        int i;
        if (keyData == null || keyData.length == 0) {
            return;
        }
        BMapTreeNode node = this.root;
        for (i = 0; i < keyData.length - 1; ++i) {
            node = node.addChildIfAbsent(keyData[i]);
        }
        node.addChildIfAbsent(keyData[i], rowIdx);
    }

    public List<Integer> search(Object[] data) {
        BMapTreeNode node = this.root;
        BMapTreeNode child = null;
        for (int i = 0; i < data.length; ++i) {
            child = node.findChild(data[i]);
            if (child == null) {
                return new ArrayList<Integer>();
            }
            node = child;
        }
        if (child != null) {
            return new ArrayList<Integer>(child.getRowIdxes());
        }
        return new ArrayList<Integer>();
    }
}

