/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.nvwa.cellbook.json.CellBookSerialize
 *  com.jiuqi.nvwa.cellbook.model.CellBook
 */
package com.jiuqi.nr.zbquery.rest.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.nr.zbquery.model.PageInfo;
import com.jiuqi.nvwa.cellbook.json.CellBookSerialize;
import com.jiuqi.nvwa.cellbook.model.CellBook;

public class QueryResultVO {
    private String cacheId;
    @JsonSerialize(using=CellBookSerialize.class)
    private CellBook cellBook;
    private PageInfo pageInfo;
    private String[] colNames;
    private String hyperlinkEnv;
    private String treeInfo;

    public String getCacheId() {
        return this.cacheId;
    }

    public void setCacheId(String cacheId) {
        this.cacheId = cacheId;
    }

    public CellBook getCellBook() {
        return this.cellBook;
    }

    public void setCellBook(CellBook cellBook) {
        this.cellBook = cellBook;
    }

    public PageInfo getPageInfo() {
        return this.pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public String[] getColNames() {
        return this.colNames;
    }

    public void setColNames(String[] colNames) {
        this.colNames = colNames;
    }

    public String getHyperlinkEnv() {
        return this.hyperlinkEnv;
    }

    public void setHyperlinkEnv(String hyperlinkEnv) {
        this.hyperlinkEnv = hyperlinkEnv;
    }

    public String getTreeInfo() {
        return this.treeInfo;
    }

    public void setTreeInfo(String treeInfo) {
        this.treeInfo = treeInfo;
    }
}

