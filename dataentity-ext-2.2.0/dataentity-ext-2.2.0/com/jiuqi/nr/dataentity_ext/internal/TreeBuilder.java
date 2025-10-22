/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 */
package com.jiuqi.nr.dataentity_ext.internal;

import com.jiuqi.nr.dataentity_ext.dto.EntityDataType;
import com.jiuqi.nr.dataentity_ext.dto.IEntityDataRow;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class TreeBuilder {
    public static List<TreeNode> buildTree(List<IEntityDataRow> inputList, IEntityTable entityTable, List<EntityDataType> countType) {
        HashMap<String, TreeNode> allNodes = new HashMap<String, TreeNode>();
        for (IEntityDataRow iEntityDataRow : inputList) {
            String key = iEntityDataRow.getKey();
            IEntityRow entityRow = entityTable.quickFindByEntityKey(key);
            EntityDataType type = entityRow == null ? EntityDataType.NOT_EXIST : EntityDataType.EXIST;
            TreeNode node = new TreeNode(iEntityDataRow, type);
            allNodes.put(key, node);
        }
        HashSet<String> missingParentKeys = new HashSet<String>();
        for (TreeNode node : allNodes.values()) {
            String parentKey = node.data.getParent();
            if (!StringUtils.hasText(parentKey) || !inputList.stream().noneMatch(t -> t.getKey().equals(parentKey))) continue;
            missingParentKeys.add(parentKey);
        }
        LinkedList<String> linkedList = new LinkedList<String>(missingParentKeys);
        HashSet<String> processedKeys = new HashSet<String>();
        HashMap<String, TreeNode> supplementNodes = new HashMap<String, TreeNode>();
        while (!linkedList.isEmpty()) {
            Object parentNode;
            String grandParentKey;
            String parentKey = (String)linkedList.poll();
            if (processedKeys.contains(parentKey)) continue;
            processedKeys.add(parentKey);
            if (!allNodes.containsKey(parentKey)) {
                IEntityRow entityRow = entityTable.quickFindByEntityKey(parentKey);
                if (entityRow == null) continue;
                TreeNode parentNode2 = new TreeNode(new EntityDataRow(entityRow), EntityDataType.EXIST_DISABLE);
                allNodes.put(parentKey, parentNode2);
                supplementNodes.put(parentKey, parentNode2);
            }
            if (!StringUtils.hasText(grandParentKey = ((TreeNode)(parentNode = (TreeNode)allNodes.get(parentKey))).data.getParent()) || !inputList.stream().noneMatch(t -> t.getKey().equals(grandParentKey))) continue;
            linkedList.add(grandParentKey);
        }
        ArrayList<Object> prepareRoots = new ArrayList<Object>();
        for (Object n : allNodes.values()) {
            String string = ((TreeNode)n).data.getParent();
            if (string == null || string.isEmpty()) {
                prepareRoots.add(n);
                continue;
            }
            TreeNode pn = (TreeNode)allNodes.get(string);
            if (pn != null) {
                pn.children.add(n);
                continue;
            }
            prepareRoots.add(n);
        }
        ArrayList<TreeNode> roots = new ArrayList<TreeNode>();
        for (TreeNode treeNode : prepareRoots) {
            String key = treeNode.data.getKey();
            if (inputList.stream().anyMatch(t -> t.getKey().equals(key))) {
                roots.add(treeNode);
                continue;
            }
            TreeBuilder.traverseFindRoot(treeNode, roots, inputList);
        }
        LinkedList<TreeNode> bfsQueue = new LinkedList<TreeNode>(roots);
        while (!bfsQueue.isEmpty()) {
            TreeNode treeNode = (TreeNode)bfsQueue.poll();
            String curKey = treeNode.data.getKey();
            for (TreeNode child : treeNode.children) {
                TreeNode treeNode2 = (TreeNode)allNodes.get(curKey);
                if (treeNode2 != null) {
                    String parentPath = treeNode2.getPath();
                    if (StringUtils.hasText(parentPath)) {
                        child.path = parentPath + "/" + curKey;
                    } else {
                        child.path = curKey;
                    }
                }
                bfsQueue.add(child);
            }
        }
        HashSet<TreeNode> hashSet = new HashSet<TreeNode>();
        Stack<TreeNode> stack = new Stack<TreeNode>();
        for (TreeNode root : roots) {
            stack.push(root);
        }
        while (!stack.isEmpty()) {
            TreeNode n = (TreeNode)stack.peek();
            boolean allChildrenVisited = true;
            for (TreeNode child : n.children) {
                if (hashSet.contains(child)) continue;
                stack.push(child);
                allChildrenVisited = false;
            }
            if (!allChildrenVisited) continue;
            n = (TreeNode)stack.pop();
            int dirChildCount = (int)n.getChildren().stream().filter(o -> CollectionUtils.isEmpty(countType) || countType.contains((Object)o.getType())).count();
            n.childCount = dirChildCount;
            n.allChildCount = dirChildCount;
            for (TreeNode child : n.children) {
                TreeNode treeNode = n;
                treeNode.allChildCount = treeNode.allChildCount + child.allChildCount;
            }
            hashSet.add(n);
        }
        return roots;
    }

    private static void traverseFindRoot(TreeNode node, List<TreeNode> rootNodes, List<IEntityDataRow> inputList) {
        if (CollectionUtils.isEmpty(node.children)) {
            return;
        }
        for (TreeNode child : node.children) {
            String key = child.data.getKey();
            if (inputList.stream().anyMatch(t -> t.getKey().equals(key))) {
                rootNodes.add(child);
                continue;
            }
            TreeBuilder.traverseFindRoot(child, rootNodes, inputList);
        }
    }

    private static class EntityDataRow
    implements IEntityDataRow {
        private final IEntityRow row;

        public EntityDataRow(IEntityRow row) {
            this.row = row;
        }

        @Override
        public String getKey() {
            return this.row.getEntityKeyData();
        }

        @Override
        public String getCode() {
            return this.row.getCode();
        }

        @Override
        public String getTitle() {
            return this.row.getTitle();
        }

        @Override
        public String getParent() {
            return this.row.getParentEntityKey();
        }

        @Override
        public BigDecimal getOrder() {
            Object entityOrder = this.row.getEntityOrder();
            if (entityOrder instanceof BigDecimal) {
                return (BigDecimal)entityOrder;
            }
            return new BigDecimal(0);
        }
    }

    public static class TreeNode {
        private final IEntityDataRow data;
        private final EntityDataType type;
        private final List<TreeNode> children = new ArrayList<TreeNode>();
        private String path;
        private int childCount;
        private int allChildCount;

        public TreeNode(IEntityDataRow data, EntityDataType type) {
            this.data = data;
            this.type = type;
        }

        public IEntityDataRow getData() {
            return this.data;
        }

        public EntityDataType getType() {
            return this.type;
        }

        public List<TreeNode> getChildren() {
            return this.children;
        }

        public String getPath() {
            return this.path;
        }

        public boolean isLeaf() {
            return CollectionUtils.isEmpty(this.children);
        }

        public int getChildCount() {
            return this.childCount;
        }

        public int getAllChildCount() {
            return this.allChildCount;
        }
    }
}

