/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.vo;

import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import java.util.List;
import java.util.Map;

public class ManalOffsetParamsVO
extends QueryParamsVO {
    private List<Map<String, String>> records;
    private List<String> recordIds;

    public List<Map<String, String>> getRecords() {
        return this.records;
    }

    public void setRecords(List<Map<String, String>> records) {
        this.records = records;
    }

    public List<String> getRecordIds() {
        return this.recordIds;
    }

    public void setRecordIds(List<String> recordIds) {
        this.recordIds = recordIds;
    }
}

