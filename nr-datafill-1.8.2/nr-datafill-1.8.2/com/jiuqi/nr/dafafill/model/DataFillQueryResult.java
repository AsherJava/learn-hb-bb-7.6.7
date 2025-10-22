/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 */
package com.jiuqi.nr.dafafill.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.nr.dafafill.model.DataFillDimensionTitle;
import com.jiuqi.nr.dafafill.model.PageInfo;
import com.jiuqi.nr.dafafill.model.QueryField;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataFillQueryResult
implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<DimensionValueSet, List<AbstractData>> datas;
    private PageInfo pageInfo;
    private int totalCount;
    private List<QueryField> addZbs;
    private Map<String, DataFillDimensionTitle> masterDimensionTitles;
    @JsonIgnore
    private Map<String, Serializable> caches;

    public Map<DimensionValueSet, List<AbstractData>> getDatas() {
        return this.datas;
    }

    public void setDatas(Map<DimensionValueSet, List<AbstractData>> datas) {
        this.datas = datas;
    }

    public PageInfo getPageInfo() {
        return this.pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<QueryField> getAddZbs() {
        return this.addZbs;
    }

    public void setAddZbs(List<QueryField> addZbs) {
        this.addZbs = addZbs;
    }

    public Map<String, DataFillDimensionTitle> getMasterDimensionTitles() {
        return this.masterDimensionTitles;
    }

    public Map<String, Serializable> getCaches() {
        if (this.caches == null) {
            this.caches = new HashMap<String, Serializable>();
        }
        return this.caches;
    }

    public void setCaches(Map<String, Serializable> caches) {
        this.caches = caches;
    }

    public void setMasterDimensionTitles(Map<String, DataFillDimensionTitle> masterDimensionTitles) {
        this.masterDimensionTitles = masterDimensionTitles;
    }
}

