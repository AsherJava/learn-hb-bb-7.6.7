/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.report.remote.controller.vo;

import java.util.List;

public class PreviewResultVo {
    private int totalCount;
    private List<Object[]> result;

    public int getTotalCount() {
        return this.totalCount;
    }

    public List<Object[]> getResult() {
        return this.result;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public void setResult(List<Object[]> result) {
        this.result = result;
    }
}

