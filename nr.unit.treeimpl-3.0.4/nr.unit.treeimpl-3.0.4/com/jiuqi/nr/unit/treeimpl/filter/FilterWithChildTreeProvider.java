/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.entity.filter.IFilterEntityRow
 *  com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.itreebase.nodemap.TreeNode
 *  com.jiuqi.nr.itreebase.nodemap.TreeNode$traverPloy
 */
package com.jiuqi.nr.unit.treeimpl.filter;

import com.jiuiqi.nr.unit.treebase.entity.filter.IFilterEntityRow;
import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.itreebase.nodemap.TreeNode;
import com.jiuqi.nr.unit.treeimpl.filter.FilterWithBuildTreeProvider;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FilterWithChildTreeProvider
extends FilterWithBuildTreeProvider {
    public FilterWithChildTreeProvider(IUnitTreeEntityRowProvider baseProvider, IFilterEntityRow checker) {
        super(baseProvider, checker);
    }

    @Override
    protected void initTree() {
        this.tree = new ArrayList();
        this.nodeMap = new LinkedHashMap();
        List allRows = this.baseProvider.getAllRows();
        this.checker.setMatchRangeRows(allRows);
        for (IEntityRow iEntityRow : allRows) {
            if (!this.checker.matchRow(iEntityRow)) continue;
            TreeNode node = new TreeNode(iEntityRow.getEntityKeyData(), (Object)iEntityRow);
            this.nodeMap.put(node.getKey(), node);
        }
        for (Map.Entry entry : this.nodeMap.entrySet()) {
            TreeNode childNode = (TreeNode)entry.getValue();
            TreeNode parentNode = (TreeNode)this.nodeMap.get(((IEntityRow)childNode.getData()).getParentEntityKey());
            if (parentNode != null) {
                boolean hasGrandPa;
                childNode.setParent(parentNode);
                parentNode.appendChild(childNode);
                boolean bl = hasGrandPa = this.nodeMap.get(((IEntityRow)parentNode.getData()).getParentEntityKey()) == null;
                if (this.containsTreeNode(parentNode.getKey()) || !hasGrandPa) continue;
                this.tree.add(parentNode);
                continue;
            }
            this.tree.add(childNode);
        }
    }

    protected boolean containsTreeNode(String key) {
        return this.findTreeNode(key) != null;
    }

    protected TreeNode<IEntityRow> findTreeNode(String key) {
        for (TreeNode treeNode : this.tree) {
            Iterator iterator = treeNode.iterator(TreeNode.traverPloy.DEPTH_FIRST);
            while (iterator.hasNext()) {
                TreeNode next = (TreeNode)iterator.next();
                if (!next.getKey().equals(key)) continue;
                return next;
            }
        }
        return null;
    }
}

