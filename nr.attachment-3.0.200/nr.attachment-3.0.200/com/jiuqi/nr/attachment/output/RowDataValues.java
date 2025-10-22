/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.output;

import com.jiuqi.nr.attachment.message.RowDataInfo;
import java.util.ArrayList;
import java.util.List;

public class RowDataValues {
    private List<RowDataInfo> rowDatas = new ArrayList<RowDataInfo>();
    private Integer totalNumber = 0;
    private long totalSize = 0L;

    public List<RowDataInfo> getRowDatas() {
        return this.rowDatas;
    }

    public void setRowDatas(List<RowDataInfo> rowDatas) {
        this.rowDatas = rowDatas;
    }

    public Integer getTotalNumber() {
        return this.totalNumber;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }

    public long getTotalSize() {
        return this.totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }
}

