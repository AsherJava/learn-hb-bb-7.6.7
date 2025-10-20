/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.sql.vo;

import com.jiuqi.va.query.sql.vo.TreeRow;
import java.util.List;
import java.util.Map;

public class ResultVO {
    private List<Map<String, Object>> dataList;
    private List<TreeRow> treeRowList;
    private Integer totalCount;

    public List<Map<String, Object>> getDataList() {
        return this.dataList;
    }

    public void setDataList(List<Map<String, Object>> dataList) {
        this.dataList = dataList;
    }

    public Integer getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<TreeRow> getTreeRowList() {
        return this.treeRowList;
    }

    public void setTreeRowList(List<TreeRow> treeRowList) {
        this.treeRowList = treeRowList;
    }
}

