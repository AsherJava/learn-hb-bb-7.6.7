/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.query.client.detail.data;

import com.jiuqi.dc.query.client.detail.data.DetailQueryContext;
import com.jiuqi.dc.query.client.detail.data.DetailQueryPreData;
import com.jiuqi.dc.query.client.detail.data.DetailRowDataVO;
import java.util.LinkedHashMap;
import java.util.List;

public class DetailQueryDataContent {
    private DetailQueryContext detailQueryContext;
    private DetailQueryPreData detailQueryPreData;
    private LinkedHashMap<Integer, List<DetailRowDataVO>> detailRowData;

    public DetailQueryContext getDetailQueryContext() {
        return this.detailQueryContext;
    }

    public void setDetailQueryContext(DetailQueryContext detailQueryContext) {
        this.detailQueryContext = detailQueryContext;
    }

    public DetailQueryPreData getDetailQueryPreData() {
        return this.detailQueryPreData;
    }

    public void setDetailQueryPreData(DetailQueryPreData detailQueryPreData) {
        this.detailQueryPreData = detailQueryPreData;
    }

    public LinkedHashMap<Integer, List<DetailRowDataVO>> getDetailRowData() {
        return this.detailRowData;
    }

    public void setDetailRowData(LinkedHashMap<Integer, List<DetailRowDataVO>> detailRowData) {
        this.detailRowData = detailRowData;
    }
}

