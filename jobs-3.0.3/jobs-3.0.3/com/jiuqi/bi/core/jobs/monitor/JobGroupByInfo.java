/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.monitor;

public class JobGroupByInfo {
    private int count;
    private String execNode;
    private String publishNode;
    private int state;
    private String categoryId;
    private String categoryTitle;

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getExecNode() {
        return this.execNode;
    }

    public void setExecNode(String execNode) {
        this.execNode = execNode;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryTitle() {
        return this.categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getPublishNode() {
        return this.publishNode;
    }

    public void setPublishNode(String publishNode) {
        this.publishNode = publishNode;
    }
}

