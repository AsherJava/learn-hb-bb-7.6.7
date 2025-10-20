/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.budget.common.consts.CommonConst
 *  com.jiuqi.va.domain.basedata.handle.BaseDataSearchDTO
 *  com.jiuqi.va.domain.basedata.handle.BaseDataSortDTO
 *  com.jiuqi.va.mapper.domain.PageDTO
 */
package com.jiuqi.budget.masterdata.basedata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.budget.common.consts.CommonConst;
import com.jiuqi.budget.masterdata.basedata.BaseDataObj;
import com.jiuqi.budget.masterdata.basedata.QueryDataType;
import com.jiuqi.budget.masterdata.basedata.RangeType;
import com.jiuqi.budget.masterdata.basedata.enums.BaseDataQueryType;
import com.jiuqi.budget.masterdata.intf.AuthType;
import com.jiuqi.va.domain.basedata.handle.BaseDataSearchDTO;
import com.jiuqi.va.domain.basedata.handle.BaseDataSortDTO;
import com.jiuqi.va.mapper.domain.PageDTO;
import java.util.List;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class BaseDataObjDTO
extends BaseDataObj
implements PageDTO {
    private Integer pageNum;
    private Integer total;
    private Integer limit;
    private Boolean pagination;
    private String searchKey;
    private RangeType rangeType;
    private AuthType authType;
    private QueryDataType queryDataType;
    private Boolean ordered;
    private List<String> keyList;
    private Boolean ignoreShareFields;
    private BaseDataQueryType baseDataQueryType = BaseDataQueryType.BASEDATA;
    private List<BaseDataSearchDTO> deepSearchParams;
    private List<BaseDataSortDTO> orderBy;

    public List<BaseDataSortDTO> getOrderBy() {
        return this.orderBy;
    }

    public void setOrderBy(List<BaseDataSortDTO> orderBy) {
        this.orderBy = orderBy;
    }

    public Boolean getIgnoreShareFields() {
        return this.ignoreShareFields;
    }

    public void setIgnoreShareFields(Boolean ignoreShareFields) {
        this.ignoreShareFields = ignoreShareFields;
    }

    public List<String> getKeyList() {
        return this.keyList;
    }

    public void setKeyList(List<String> keyList) {
        this.keyList = keyList;
    }

    public boolean isOrdered() {
        return this.ordered != null && this.ordered != false;
    }

    public void setOrdered(boolean ordered) {
        this.ordered = ordered;
    }

    public QueryDataType getQueryDataType() {
        return this.queryDataType;
    }

    public void setQueryDataType(QueryDataType queryDataType) {
        this.queryDataType = queryDataType;
    }

    public RangeType getRangeType() {
        if (this.rangeType == null) {
            return RangeType.NONE;
        }
        return this.rangeType;
    }

    public void setRangeType(RangeType rangeType) {
        this.rangeType = rangeType;
    }

    public AuthType getAuthType() {
        if (this.authType == null) {
            return AuthType.ACCESS;
        }
        return this.authType;
    }

    public void setAuthType(AuthType authType) {
        this.authType = authType;
    }

    public Integer getPageNum() {
        if (this.pageNum == null) {
            return CommonConst.PAGE_NUM_DEFAULT;
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
            return CommonConst.PAGE_LIMIT_DEFAULT;
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

    public int getOffset() {
        return (this.getPageNum() - 1) * this.getLimit();
    }

    public String getSearchKey() {
        return this.searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public List<BaseDataSearchDTO> getDeepSearchParams() {
        return this.deepSearchParams;
    }

    public void setDeepSearchParams(List<BaseDataSearchDTO> deepSearchParams) {
        this.deepSearchParams = deepSearchParams;
    }

    public BaseDataQueryType getBaseDataQueryType() {
        return this.baseDataQueryType;
    }

    public void setBaseDataQueryType(BaseDataQueryType queryType) {
        this.baseDataQueryType = queryType;
    }

    public String toString() {
        return "BaseDataObjDTO{pageNum=" + this.pageNum + ", total=" + this.total + ", limit=" + this.limit + ", pagination=" + this.pagination + ", searchKey='" + this.searchKey + '\'' + '}';
    }
}

