/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.itreebase.nodemap.TreeNode
 */
package com.jiuqi.nr.batch.summary.storage.condition;

import com.jiuqi.nr.batch.summary.storage.condition.CustomConditionRow;
import com.jiuqi.nr.batch.summary.storage.condition.CustomConditionRowImpl;
import com.jiuqi.nr.batch.summary.storage.condition.CustomConditionRowProvider;
import com.jiuqi.nr.batch.summary.storage.entity.CustomCalibreRow;
import com.jiuqi.nr.itreebase.nodemap.TreeNode;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomConditionRowProviderImpl
implements CustomConditionRowProvider {
    private List<CustomConditionRow> allRows;
    private List<TreeNode<CustomConditionRow>> dataTree;
    private Map<String, TreeNode<CustomConditionRow>> dataMap;

    public CustomConditionRowProviderImpl(List<CustomCalibreRow> conditionRows) {
        this.init(conditionRows);
    }

    @Override
    public List<CustomConditionRow> getAllRows() {
        return this.allRows;
    }

    @Override
    public List<CustomConditionRow> getRootRows() {
        return this.dataTree.stream().map(TreeNode::getData).collect(Collectors.toList());
    }

    @Override
    public CustomConditionRow findRow(String rowCode) {
        return (CustomConditionRow)this.dataMap.get(rowCode).getData();
    }

    @Override
    public List<CustomConditionRow> getChildRows(String parentCode) {
        TreeNode<CustomConditionRow> parent = this.dataMap.get(parentCode);
        if (parent != null) {
            return parent.getChildren().stream().map(TreeNode::getData).collect(Collectors.toList());
        }
        return new ArrayList<CustomConditionRow>();
    }

    @Override
    public List<CustomConditionRow> getAllChildRows(String parentCode) {
        TreeNode<CustomConditionRow> parent = this.dataMap.get(parentCode);
        if (parent != null) {
            return parent.getAllChildren().stream().map(TreeNode::getData).collect(Collectors.toList());
        }
        return new ArrayList<CustomConditionRow>();
    }

    @Override
    public int getMaxDepth() {
        int depth = 0;
        for (TreeNode<CustomConditionRow> row : this.dataTree) {
            int temp = row.getMaxDepth();
            if (temp <= depth) continue;
            depth = temp;
        }
        return depth;
    }

    @Override
    public int getTotalCount() {
        return this.allRows.size();
    }

    public Map<String, TreeNode<CustomConditionRow>> getDataMap() {
        return this.dataMap;
    }

    private void init(List<CustomCalibreRow> conditionRows) {
        this.allRows = new ArrayList<CustomConditionRow>();
        this.dataTree = new ArrayList<TreeNode<CustomConditionRow>>();
        this.dataMap = new LinkedHashMap<String, TreeNode<CustomConditionRow>>();
        if (conditionRows != null) {
            this.allRows = conditionRows.stream().map(this::translate).collect(Collectors.toList());
            this.allRows.forEach(r -> this.dataMap.put(r.getCode(), (TreeNode<CustomConditionRow>)new TreeNode(r.getCode(), r)));
            for (Map.Entry<String, TreeNode<CustomConditionRow>> entry : this.dataMap.entrySet()) {
                TreeNode<CustomConditionRow> node = entry.getValue();
                TreeNode<CustomConditionRow> parentNode = this.dataMap.get(((CustomConditionRow)node.getData()).getParentCode());
                if (parentNode == null) {
                    this.dataTree.add(node);
                    continue;
                }
                parentNode.appendChild(node);
            }
        }
    }

    private CustomConditionRow translate(CustomCalibreRow row) {
        CustomConditionRowImpl impl = new CustomConditionRowImpl(this);
        impl.setKey(row.getCode());
        impl.setCode(row.getCode());
        impl.setTitle(row.getTitle());
        impl.setScheme(row.getScheme());
        impl.setParentCode(row.getParentCode());
        impl.setOrdinal(row.getOrdinal());
        impl.setValue(row.getValue());
        return impl;
    }
}

