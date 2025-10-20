/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.ExecuteSettingVO
 *  com.jiuqi.bde.common.intf.FetchTaskContext
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.dc.base.common.cache.intf.CacheEntity
 */
package com.jiuqi.bde.bizmodel.execute.model.basedata;

import com.jiuqi.bde.common.dto.ExecuteSettingVO;
import com.jiuqi.bde.common.intf.FetchTaskContext;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.dc.base.common.cache.intf.CacheEntity;
import java.util.Date;
import java.util.List;

public class BaseDataDataCondi
implements CacheEntity {
    private String year;
    private String period;
    private FetchTaskContext fetchTaskContext;
    private List<ExecuteSettingVO> fetchSettingList;
    private String baseDataDefine;
    List<String> baseDataCodeList;

    public BaseDataDataCondi(FetchTaskContext fetchTaskContext, List<ExecuteSettingVO> fetchSettingList, String baseDataDefine, List<String> baseDataCodeList) {
        this.fetchTaskContext = fetchTaskContext;
        Date date = DateUtils.parse((String)fetchTaskContext.getEndDateStr());
        this.year = String.valueOf(DateUtils.getYearOfDate((Date)date));
        this.period = String.valueOf(DateUtils.getDateFieldValue((Date)date, (int)2));
        this.fetchSettingList = fetchSettingList;
        this.baseDataDefine = baseDataDefine;
        this.baseDataCodeList = baseDataCodeList;
    }

    public String getCacheKey() {
        return null;
    }

    public List<String> getBaseDataCodeList() {
        return this.baseDataCodeList;
    }

    public void setBaseDataCodeList(List<String> baseDataCodeList) {
        this.baseDataCodeList = baseDataCodeList;
    }

    public String getYear() {
        return this.year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public FetchTaskContext getFetchTaskContext() {
        return this.fetchTaskContext;
    }

    public void setFetchTaskContext(FetchTaskContext fetchTaskContext) {
        this.fetchTaskContext = fetchTaskContext;
    }

    public List<ExecuteSettingVO> getFetchSettingList() {
        return this.fetchSettingList;
    }

    public void setFetchSettingList(List<ExecuteSettingVO> fetchSettingList) {
        this.fetchSettingList = fetchSettingList;
    }

    public String getBaseDataDefine() {
        return this.baseDataDefine;
    }

    public void setBaseDataDefine(String baseDataDefine) {
        this.baseDataDefine = baseDataDefine;
    }
}

