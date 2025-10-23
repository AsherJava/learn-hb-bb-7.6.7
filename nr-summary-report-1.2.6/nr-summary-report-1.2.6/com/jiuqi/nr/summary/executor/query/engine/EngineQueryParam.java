/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.query.result.ColumnInfo
 */
package com.jiuqi.nr.summary.executor.query.engine;

import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.query.result.ColumnInfo;
import com.jiuqi.nr.summary.executor.query.ds.SummaryDSModel;
import com.jiuqi.nr.summary.model.PageInfo;
import java.util.Map;

public class EngineQueryParam {
    private PageInfo pagerInfo;
    private boolean isPaginate = true;
    private boolean floatQuery = false;
    private final SummaryDSModel dsModel;
    private Metadata<ColumnInfo> metadata;
    private Map<String, Integer> columnMap;

    public EngineQueryParam(SummaryDSModel dsModel) {
        this.dsModel = dsModel;
    }

    public Map<String, Integer> getColumnMap() {
        return this.columnMap;
    }

    public PageInfo getPagerInfo() {
        return this.pagerInfo;
    }

    public void setPagerInfo(PageInfo pagerInfo) {
        if (pagerInfo != null && pagerInfo.getPageSize() > 0 && pagerInfo.getPageIndex() >= 0) {
            this.isPaginate = true;
        }
        this.pagerInfo = pagerInfo;
    }

    public boolean isPaginate() {
        return this.isPaginate;
    }

    public void setPaginate(boolean paginate) {
        this.isPaginate = paginate;
    }

    public void setColumnMap(Map<String, Integer> columnMap) {
        this.columnMap = columnMap;
    }

    public SummaryDSModel getDsModel() {
        return this.dsModel;
    }

    public Metadata<ColumnInfo> getMetadata() {
        return this.metadata;
    }

    public void setMetadata(Metadata<ColumnInfo> metadata) {
        this.metadata = metadata;
    }
}

