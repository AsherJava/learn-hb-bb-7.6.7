/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.base.http;

import java.util.Collections;
import java.util.List;

public class PageInfo<T> {
    private int pageNum;
    private int pageSize;
    private int size;
    private int pages;
    private List<T> list;
    private static final int DEFAULT_PAGE_NAM = 1;
    private static final int DEFAULT_PAGE_SIZE = 50;

    public static <T> PageInfo<T> of(List<T> list, int pageNum, int pageSize, int totalSize) {
        return new PageInfo<T>(list, pageNum, pageSize, totalSize);
    }

    public static <T> PageInfo<T> empty() {
        return new PageInfo<T>(Collections.EMPTY_LIST, 1, 50, 0);
    }

    public static <T> PageInfo<T> of(List<T> list, int totalSize) {
        return new PageInfo<T>(list, 1, 50, totalSize);
    }

    public PageInfo() {
    }

    private PageInfo(List<T> list, int pageNum, int pageSize, int totalSize) {
        this.list = list;
        this.size = totalSize;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.pages = (this.size + this.pageSize - 1) / this.pageSize;
    }

    public int getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPages() {
        return this.pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<T> getList() {
        return this.list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}

