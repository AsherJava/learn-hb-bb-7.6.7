/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.engine.grouping;

import java.util.List;

public class EnumRowItem {
    private String originalCode;
    private String code;
    private String title;
    private String parentCode;
    private List<String> parentPath;
    private boolean isLeaf;
    private Object order;

    public EnumRowItem(String code, String title, String parentCode) {
        this.code = code;
        this.title = title;
        this.parentCode = parentCode;
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

    public String getParentCode() {
        return this.parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public List<String> getParentPath() {
        return this.parentPath;
    }

    public void setParentPath(List<String> parentPath) {
        this.parentPath = parentPath;
    }

    public boolean isLeaf() {
        return this.isLeaf;
    }

    public void setLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public String getOriginalCode() {
        return this.originalCode;
    }

    public void setOriginalCode(String originalCode) {
        this.originalCode = originalCode;
    }

    public Object getOrder() {
        return this.order;
    }

    public void setOrder(Object order) {
        this.order = order;
    }
}

