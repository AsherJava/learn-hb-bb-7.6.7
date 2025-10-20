/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.dc.base.common.tree;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.tree.BaseTreeNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TreeNodeUtil {
    public static <T extends BaseTreeNode> List<T> assembleTree(List<T> listNodes) {
        ArrayList newTreeNodes = new ArrayList();
        newTreeNodes.addAll(listNodes.stream().filter(t -> StringUtils.isEmpty((String)t.getParentCode()) || "null".equals(t.getParentCode()) || "0".equals(t.getParentCode())).collect(Collectors.toList()));
        for (BaseTreeNode t2 : newTreeNodes) {
            t2.setLeaf(true);
            TreeNodeUtil.assembleTree(t2, listNodes);
        }
        for (BaseTreeNode baseTreeNode : newTreeNodes) {
        }
        return newTreeNodes;
    }

    static <T extends BaseTreeNode> String getParentid(T node) {
        if (StringUtils.isEmpty((String)node.getParentCode())) {
            return "";
        }
        return node.getParentCode();
    }

    static <T extends BaseTreeNode> void assembleTree(T node, List<T> listNodes) {
        if (node != null && !CollectionUtils.isEmpty(listNodes)) {
            listNodes.stream().filter(t -> Objects.equals(t.getParentCode(), node.getCode())).forEachOrdered(node::addChild);
            if (!CollectionUtils.isEmpty(node.getChildren())) {
                node.setLeaf(false);
                for (BaseTreeNode t2 : node.getChildren()) {
                    TreeNodeUtil.assembleTree(t2, listNodes);
                }
            } else {
                node.setLeaf(true);
            }
        }
    }
}

