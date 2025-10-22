/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentity_ext.internal.db;

import java.math.BigDecimal;

public class EntityDataDO {
    private String key;
    private String code;
    private String title;
    private String parent;
    private String path;
    private int type;
    private BigDecimal order;
    private int leaf;
    private int childCount;
    private int allChildCount;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParent() {
        return this.parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public BigDecimal getOrder() {
        return this.order;
    }

    public void setOrder(BigDecimal order) {
        this.order = order;
    }

    public int getLeaf() {
        return this.leaf;
    }

    public void setLeaf(int leaf) {
        this.leaf = leaf;
    }

    public int getChildCount() {
        return this.childCount;
    }

    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }

    public int getAllChildCount() {
        return this.allChildCount;
    }

    public void setAllChildCount(int allChildCount) {
        this.allChildCount = allChildCount;
    }
}

