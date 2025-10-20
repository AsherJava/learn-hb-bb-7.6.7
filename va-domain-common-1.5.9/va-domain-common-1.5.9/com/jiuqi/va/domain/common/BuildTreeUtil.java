/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.domain.common;

import com.jiuqi.va.domain.common.TreeVO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BuildTreeUtil {
    public static <T> TreeVO<T> build(List<TreeVO<T>> nodes, TreeVO<T> root) {
        if (root == null) {
            return null;
        }
        if (nodes == null) {
            return root;
        }
        HashMap<String, TreeVO<T>> map = new HashMap<String, TreeVO<T>>();
        for (TreeVO<T> node : nodes) {
            map.put(node.getId(), node);
        }
        ArrayList topNodes = new ArrayList();
        String pid = null;
        TreeVO pNode = null;
        for (TreeVO<T> node : nodes) {
            pid = node.getParentid();
            if (pid == null || root.getId().equals(pid) || !map.containsKey(pid)) {
                topNodes.add(node);
                continue;
            }
            if (!map.containsKey(pid)) continue;
            pNode = (TreeVO)map.get(pid);
            pNode.addChild(node);
            pNode.setHasParent(true);
            pNode.setHasChildren(true);
        }
        root.setChildren(topNodes);
        return root;
    }
}

