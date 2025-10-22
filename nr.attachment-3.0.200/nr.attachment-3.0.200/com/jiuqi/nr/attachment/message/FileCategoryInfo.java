/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.message;

import java.io.Serializable;

public class FileCategoryInfo
implements Serializable {
    private static final long serialVersionUID = -4444231096718369054L;
    private String code;
    private String title;
    private int order;
    private boolean defaultCategory;
    private boolean referenced = false;

    public FileCategoryInfo() {
    }

    public FileCategoryInfo(String code, String title, int order, boolean defaultCategory) {
        this.code = code;
        this.title = title;
        this.order = order;
        this.defaultCategory = defaultCategory;
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

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public boolean isDefaultCategory() {
        return this.defaultCategory;
    }

    public void setDefaultCategory(boolean defaultCategory) {
        this.defaultCategory = defaultCategory;
    }

    public boolean isReferenced() {
        return this.referenced;
    }

    public void setReferenced(boolean referenced) {
        this.referenced = referenced;
    }
}

