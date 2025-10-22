/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.block;

public class QueryGridPage {
    public static final String CONST_TOTALPAGE = "totalpage";
    public static final String CONST_CURRENTPAGE = "pagenum";
    public static final String CONST_NEXTITEMINDEX = "nextindex";
    public static final String CONST_NEXTROWINDEX = "nextrowindex";
    public static final String CONST_DIMENSIONVALUE = "dimensionvalue";
    public static final String CONST_PRETPAGE = "prepagenum";
    public static final String CONST_PREITEMINDEX = "preindex";
    public static final String CONST_PREROWINDEX = "prerowindex";
    public static final String CONST_PREDIMENSIONVALUE = "predimensionvalue";
    public static final String CONST_ISFILTER = "isfilter";
    public static final String CONST_ISPAGELOAD = "ispagechange";
    public static final String CONST_PREFIELDINDEX = "preFieldIndex";
    public static int CONST_DEFAULTPAGESIZE = 20;
    private int count = 1;
    private int curPageNum = 1;
    private int pageSize = 20;
    private int nextItemIndex;
    private int nextRowIndex;
    private String dimValueSet;
    boolean isPageLoad = false;
    private int prePageNum = 1;
    private int preItemIndex = 0;
    private int preRowIndex = 0;
    private String preDimValueSet;
    boolean isFilterModel;
    boolean isIndexChange = false;
    private int preFieldIndex;

    public int getTotalPage() {
        return this.count;
    }

    public void setTotalPage(int count) {
        this.count = count;
    }

    public int getCurPageNum() {
        return this.curPageNum;
    }

    public void setCurPageNum(int curPageNum) {
        this.curPageNum = curPageNum;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getNextItemIndex() {
        return this.nextItemIndex;
    }

    public void setNextItemIndex(int next) {
        this.nextItemIndex = next;
        this.isIndexChange = true;
    }

    public int getNextRowIndex() {
        return this.nextRowIndex;
    }

    public void setNextRowIndex(int next) {
        this.nextRowIndex = next;
    }

    public String getDimValueSet() {
        return this.dimValueSet;
    }

    public void setDimValueSet(String dimValueSet) {
        this.dimValueSet = dimValueSet;
    }

    public boolean getIsPageLoad() {
        return this.isPageLoad;
    }

    public void setIsPageLoad(boolean isPageLoad) {
        this.isPageLoad = isPageLoad;
    }

    public int getPrePageNum() {
        return this.prePageNum;
    }

    public void setPrePageNum(int prePageNum) {
        this.prePageNum = prePageNum;
    }

    public int getPreItemIndex() {
        return this.preItemIndex;
    }

    public void setPreItemIndex(int preItemIndex) {
        this.preItemIndex = preItemIndex;
    }

    public int getPreRowIndex() {
        return this.preRowIndex;
    }

    public void setPreRowIndex(int preRowIndex) {
        this.preRowIndex = preRowIndex;
    }

    public String getPreDimValueSet() {
        return this.preDimValueSet;
    }

    public void setPreDimValueSet(String preDimValueSet) {
        this.preDimValueSet = preDimValueSet;
    }

    public void setIsFilterModel(boolean isFilterModel) {
        this.isFilterModel = isFilterModel;
    }

    public boolean getIsFilterModel() {
        return this.isFilterModel;
    }

    public boolean isIndexChange() {
        return this.isIndexChange;
    }

    public void setIndexChange(boolean isChange) {
        this.isIndexChange = isChange;
    }

    public int getPreFieldIndex() {
        return this.preFieldIndex;
    }

    public void setPreFieldIndex(int preFieldIndex) {
        this.preFieldIndex = preFieldIndex;
    }
}

