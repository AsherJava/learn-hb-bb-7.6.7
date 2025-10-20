/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.core.model;

import com.jiuqi.np.core.model.PageInfo;
import com.jiuqi.np.core.model.TableHeaderInfo;
import java.io.Serializable;
import java.util.List;

public class BigPageResult
implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<TableHeaderInfo> head;
    private List<List<Object>> rows;
    private PageInfo paging;
    private List<String> sortedBy;

    public BigPageResult(List<TableHeaderInfo> head, List<List<Object>> rows, PageInfo paging, List<String> sortedBy) {
        this.head = head;
        this.rows = rows;
        this.paging = paging;
        this.sortedBy = sortedBy;
    }

    public static BigPageResult build(List<TableHeaderInfo> head, List<List<Object>> rows) {
        return new BigPageResult(head, rows, null, null);
    }

    public List<TableHeaderInfo> getHead() {
        return this.head;
    }

    public List<List<Object>> getRows() {
        return this.rows;
    }

    public PageInfo getPaging() {
        return this.paging;
    }

    public List<String> getSortedBy() {
        return this.sortedBy;
    }

    public void setHead(List<TableHeaderInfo> head) {
        this.head = head;
    }

    public void setRows(List<List<Object>> rows) {
        this.rows = rows;
    }

    public void setPaging(PageInfo paging) {
        this.paging = paging;
    }

    public void setSortedBy(List<String> sortedBy) {
        this.sortedBy = sortedBy;
    }
}

