/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.nvwa.cellbook.json.CellBookSerialize
 *  com.jiuqi.nvwa.cellbook.model.CellBook
 */
package com.jiuqi.nr.summary.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.nr.summary.model.PageInfo;
import com.jiuqi.nvwa.cellbook.json.CellBookSerialize;
import com.jiuqi.nvwa.cellbook.model.CellBook;
import java.util.HashMap;
import java.util.Map;

public class PreviewResult {
    @JsonSerialize(using=CellBookSerialize.class)
    private CellBook cellBook;
    private Map<Integer, PageInfo> pageInfos = new HashMap<Integer, PageInfo>();

    public PreviewResult() {
    }

    public PreviewResult(CellBook cellBook) {
        this.cellBook = cellBook;
    }

    public CellBook getCellBook() {
        return this.cellBook;
    }

    public void setCellBook(CellBook cellBook) {
        this.cellBook = cellBook;
    }

    public Map<Integer, PageInfo> getPageInfos() {
        return this.pageInfos;
    }

    public void setPageInfos(Map<Integer, PageInfo> pageInfos) {
        this.pageInfos = pageInfos;
    }
}

