/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.stat.tree;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.stat.tree.StatTreeNode;
import java.util.ArrayList;
import java.util.HashMap;

class StatTreeBuilder {
    StatTreeBuilder() {
    }

    public static StatTreeNode build(BIDataSetImpl dimDataset, int codeColIdx, int parentColIdx) throws BIDataSetException {
        ArrayList<StatTreeNode> allNodes = new ArrayList<StatTreeNode>();
        HashMap<String, StatTreeNode> nodeFinder = new HashMap<String, StatTreeNode>();
        for (BIDataRow row : dimDataset) {
            if (nodeFinder.containsKey(row.getString(codeColIdx))) continue;
            StatTreeNode node = new StatTreeNode(row);
            allNodes.add(node);
            nodeFinder.put(row.getString(codeColIdx), node);
        }
        StatTreeNode root = new StatTreeNode(null);
        for (int i = 0; i < allNodes.size(); ++i) {
            StatTreeNode node = (StatTreeNode)allNodes.get(i);
            if (node.getParent() == null && !node.getTag()) {
                while (true) {
                    StatTreeNode parent;
                    node.setTag(true);
                    String parentCode = node.getDataRow().getString(parentColIdx);
                    if (parentCode == null || parentCode.length() == 0 || (parent = (StatTreeNode)nodeFinder.get(parentCode)) == null) break;
                    node.setParent(parent);
                    node = parent;
                }
            }
            if ((node = (StatTreeNode)allNodes.get(i)).getParent() != null) continue;
            node.setParent(root);
        }
        return root;
    }
}

