/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.dto;

public class TaskQueryDTO {
    private String keyWords;
    private int pageSize;
    private int currentSize;

    public String getKeyWords() {
        return this.keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentSize() {
        return this.currentSize;
    }

    public void setCurrentSize(int currentSize) {
        this.currentSize = currentSize;
    }
}

