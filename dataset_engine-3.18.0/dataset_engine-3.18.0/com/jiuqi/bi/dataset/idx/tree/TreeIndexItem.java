/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.idx.tree;

import java.util.ArrayList;
import java.util.List;

public class TreeIndexItem {
    private Integer rowIndex;
    private List<TreeIndexItem> subIndexItems = new ArrayList<TreeIndexItem>();
    private List<Integer> recordIndexes;

    public TreeIndexItem(Integer rowIndex) {
        this.rowIndex = rowIndex;
    }

    public Integer getRowIndex() {
        return this.rowIndex;
    }

    public TreeIndexItem addSubIndex(Integer rowIndex) {
        TreeIndexItem item = new TreeIndexItem(rowIndex);
        this.subIndexItems.add(item);
        return item;
    }

    public void initRecordIndexList() {
        if (this.recordIndexes == null) {
            this.recordIndexes = new ArrayList<Integer>();
        }
    }

    public void addRecordIndex(Integer index) {
        this.recordIndexes.add(index);
    }

    List<TreeIndexItem> getDirectSubIndexItems() {
        return this.subIndexItems;
    }

    boolean isLeaf() {
        return this.recordIndexes != null;
    }

    List<Integer> getRecordIndexes() {
        ArrayList<TreeIndexItem> items = new ArrayList<TreeIndexItem>();
        this.getLeafSubIndexItems(this, items);
        ArrayList<Integer> rds = new ArrayList<Integer>();
        for (TreeIndexItem item : items) {
            if (item.recordIndexes == null) continue;
            rds.addAll(item.recordIndexes);
        }
        return rds;
    }

    private void getLeafSubIndexItems(TreeIndexItem item, List<TreeIndexItem> items) {
        if (item.subIndexItems.isEmpty()) {
            items.add(item);
        }
        for (TreeIndexItem idx : item.subIndexItems) {
            this.getLeafSubIndexItems(idx, items);
        }
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("[rowIdx:");
        buf.append(this.rowIndex);
        buf.append(";\t");
        if (this.subIndexItems.isEmpty()) {
            if (this.recordIndexes != null) {
                buf.append("recordIdxes:{");
                buf.append(this.recordIndexes.get(0));
                for (int i = 1; i < this.recordIndexes.size(); ++i) {
                    buf.append(", ").append(this.recordIndexes.get(i));
                }
                buf.append("}");
            }
        } else {
            buf.append("subIdxes:{");
            buf.append(this.subIndexItems.get(0).getRowIndex());
            for (int i = 1; i < this.subIndexItems.size(); ++i) {
                buf.append(", ").append(this.subIndexItems.get(i).getRowIndex());
            }
            buf.append("}");
        }
        buf.append("]");
        return buf.toString();
    }
}

