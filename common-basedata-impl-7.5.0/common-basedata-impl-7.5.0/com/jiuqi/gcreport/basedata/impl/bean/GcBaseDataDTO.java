/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.gcreport.basedata.api.enums.AuthType
 *  com.jiuqi.gcreport.basedata.api.enums.QueryDataType
 *  com.jiuqi.gcreport.basedata.api.enums.RangeType
 *  com.jiuqi.va.mapper.domain.PageDTO
 */
package com.jiuqi.gcreport.basedata.impl.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.gcreport.basedata.api.enums.AuthType;
import com.jiuqi.gcreport.basedata.api.enums.QueryDataType;
import com.jiuqi.gcreport.basedata.api.enums.RangeType;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseDataDO;
import com.jiuqi.va.mapper.domain.PageDTO;
import java.util.List;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class GcBaseDataDTO
extends GcBaseDataDO
implements PageDTO {
    private Integer pageNum;
    private Integer total;
    private Integer limit;
    private Boolean pagination;
    private String searchKey;
    private AuthType authType;
    private QueryDataType queryDataType;
    private RangeType rangeType;
    private boolean lazyLoad;
    private Boolean ordered;
    private List<String> baseDataCodes;

    public int getOffset() {
        return (this.getPageNum() - 1) * this.getLimit();
    }

    public Integer getPageNum() {
        if (this.pageNum == null) {
            return 1;
        }
        return this.pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getTotal() {
        return this.total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public int getLimit() {
        if (this.limit == null) {
            return 50;
        }
        return this.limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public boolean isPagination() {
        if (this.pagination == null) {
            return false;
        }
        return this.pagination;
    }

    public void setPagination(Boolean pagination) {
        this.pagination = pagination;
    }

    public String getSearchKey() {
        return this.searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public AuthType getAuthType() {
        return this.authType;
    }

    public void setAuthType(AuthType authType) {
        this.authType = authType;
    }

    public QueryDataType getQueryDataType() {
        return this.queryDataType;
    }

    public void setQueryDataType(QueryDataType queryDataType) {
        this.queryDataType = queryDataType;
    }

    public boolean isOrdered() {
        return this.ordered != null && this.ordered != false;
    }

    public void setOrdered(boolean ordered) {
        this.ordered = ordered;
    }

    public List<String> getBaseDataCodes() {
        return this.baseDataCodes;
    }

    public void setBaseDataCodes(List<String> baseDataCodes) {
        this.baseDataCodes = baseDataCodes;
    }

    public boolean isLazyLoad() {
        return this.lazyLoad;
    }

    public void setLazyLoad(boolean lazyLoad) {
        this.lazyLoad = lazyLoad;
    }

    public RangeType getRangeType() {
        return this.rangeType;
    }

    public void setRangeType(RangeType rangeType) {
        this.rangeType = rangeType;
    }
}

