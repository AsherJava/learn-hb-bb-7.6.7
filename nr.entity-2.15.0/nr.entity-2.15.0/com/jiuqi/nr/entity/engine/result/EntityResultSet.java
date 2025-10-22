/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.engine.result;

import java.util.List;
import org.springframework.util.Assert;

public abstract class EntityResultSet {
    private int currentIndex = 0;
    private int total;

    public EntityResultSet(int total) {
        this.total = total;
    }

    public Object getObject(String column) {
        Assert.notNull((Object)column, "\u5b9e\u4f53\u5c5e\u6027\u4e0d\u80fd\u4e3a\u7a7a\u3002");
        return this.getColumnObject(this.currentIndex - 1, column);
    }

    public String getKey() {
        return this.getKey(this.currentIndex - 1);
    }

    public String getCode() {
        return this.getCode(this.currentIndex - 1);
    }

    public String getTitle() {
        return this.getTitle(this.currentIndex - 1);
    }

    public String getParent() {
        return this.getParent(this.currentIndex - 1);
    }

    public Object getOrder() {
        return this.getOrder(this.currentIndex - 1);
    }

    public String[] getParents() {
        return this.getParents(this.currentIndex - 1);
    }

    public boolean isLeaf() {
        return this.isLeaf(this.currentIndex - 1);
    }

    public boolean hasChildren() {
        return this.hasChildren(this.currentIndex - 1);
    }

    public abstract List<String> getAllKeys();

    public void merge(EntityResultSet rs) {
        int append = this.append(rs);
        if (append != -1) {
            this.total = append;
            if (this.currentIndex != 0) {
                --this.currentIndex;
            }
        }
    }

    public int getRowIndex() {
        return this.currentIndex;
    }

    public EntityResultSet moveCursors(int index) {
        if (this.total == 0) {
            throw new IllegalArgumentException("\u5f53\u524d\u7ed3\u679c\u96c6\u4e2d\u65e0\u6570\u636e\u3002");
        }
        if (index <= 0 || index > this.total) {
            throw new IllegalArgumentException(String.format("\u6e38\u6807\u8d8a\u754c\uff0c\u6700\u5c0f\u503c%s\uff0c\u6700\u5927\u503c%s\uff0c\u5f53\u524d\u503c%s\u3002", 1, this.total, index));
        }
        this.currentIndex = index;
        return this;
    }

    public boolean next() {
        return ++this.currentIndex <= this.total;
    }

    public int getTotal() {
        return this.total;
    }

    protected abstract Object getColumnObject(int var1, String var2);

    protected abstract String getKey(int var1);

    protected abstract String getCode(int var1);

    protected abstract String getTitle(int var1);

    protected abstract String getParent(int var1);

    protected abstract Object getOrder(int var1);

    protected abstract String[] getParents(int var1);

    public abstract int append(EntityResultSet var1);

    public boolean isLeaf(int index) {
        return false;
    }

    public boolean hasChildren(int index) {
        return false;
    }
}

