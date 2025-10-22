/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbrbill.dto;

import java.util.List;
import java.util.Map;

public class ClbrQueryBillDataResultDTO {
    private Integer total;
    private List<Map<String, Object>> rows;

    public Integer getTotal() {
        return this.total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<Map<String, Object>> getRows() {
        return this.rows;
    }

    public void setRows(List<Map<String, Object>> rows) {
        this.rows = rows;
    }
}

