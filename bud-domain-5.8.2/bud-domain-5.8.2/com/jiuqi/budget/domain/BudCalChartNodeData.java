/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.budget.domain;

import java.math.BigDecimal;

public class BudCalChartNodeData {
    private boolean leaf;
    private boolean root;
    private String title;
    private BigDecimal orderNum;
    private int sort;

    public int getSort() {
        return this.sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public boolean isLeaf() {
        return this.leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public boolean isRoot() {
        return this.root;
    }

    public void setRoot(boolean root) {
        this.root = root;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getOrderNum() {
        return this.orderNum;
    }

    public void setOrderNum(BigDecimal orderNum) {
        this.orderNum = orderNum;
    }
}

