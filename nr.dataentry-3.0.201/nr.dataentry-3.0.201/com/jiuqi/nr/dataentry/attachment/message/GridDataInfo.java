/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.attachment.message;

import com.jiuqi.nr.dataentry.attachment.message.ColumnsInfo;
import com.jiuqi.nr.dataentry.attachment.message.RowDatasInfo;
import java.util.List;

public class GridDataInfo {
    private Integer totalSize;
    private Integer pageSize;
    private Integer currentPage;
    private List<ColumnsInfo> columns;
    private List<RowDatasInfo> rowDatas;
    private String totalFileSize;
    private String groupKey;

    public Integer getTotalSize() {
        return this.totalSize;
    }

    public void setTotalSize(Integer totalSize) {
        this.totalSize = totalSize;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public List<ColumnsInfo> getColumns() {
        return this.columns;
    }

    public void setColumns(List<ColumnsInfo> columns) {
        this.columns = columns;
    }

    public List<RowDatasInfo> getRowDatas() {
        return this.rowDatas;
    }

    public void setRowDatas(List<RowDatasInfo> rowDatas) {
        this.rowDatas = rowDatas;
    }

    public String getTotalFileSize() {
        return this.totalFileSize;
    }

    public void setTotalFileSize(String totalFileSize) {
        this.totalFileSize = totalFileSize;
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }
}

