/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.ExecuteSettingVO
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.bde.common.intf.FetchTaskContext
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.dc.base.common.cache.intf.CacheEntity
 */
package com.jiuqi.bde.bizmodel.execute.model.tfv.single;

import com.jiuqi.bde.common.dto.ExecuteSettingVO;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.common.intf.FetchTaskContext;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.dc.base.common.cache.intf.CacheEntity;
import java.util.Date;
import java.util.List;

public class TfvDataCondi
implements CacheEntity {
    private String year;
    private String period;
    private FetchTaskContext fetchTaskContext;
    private List<ExecuteSettingVO> fetchSettingList;
    private OrgMappingDTO orgMapping;

    public TfvDataCondi() {
    }

    public TfvDataCondi(FetchTaskContext fetchTaskContext, List<ExecuteSettingVO> fetchSettingList, OrgMappingDTO orgMapping) {
        this.fetchTaskContext = fetchTaskContext;
        Date date = DateUtils.parse((String)fetchTaskContext.getEndDateStr());
        this.year = String.valueOf(DateUtils.getYearOfDate((Date)date));
        this.period = String.valueOf(DateUtils.getDateFieldValue((Date)date, (int)2));
        this.fetchSettingList = fetchSettingList;
        this.orgMapping = orgMapping;
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

    public OrgMappingDTO getOrgMapping() {
        return this.orgMapping;
    }

    public void setOrgMapping(OrgMappingDTO orgMapping) {
        this.orgMapping = orgMapping;
    }

    public String getCacheKey() {
        return null;
    }

    public String getYear() {
        return this.year;
    }

    public String getPeriod() {
        return this.period;
    }

    public String toString() {
        return "TfvDataCondi [year=" + this.year + ", period=" + this.period + ", fetchTaskContext=" + this.fetchTaskContext + ", fetchSettingList=" + this.fetchSettingList + ", orgMapping=" + this.orgMapping + "]";
    }
}

