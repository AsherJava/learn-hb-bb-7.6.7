/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.budget.common.consts.CommonConst
 *  com.jiuqi.va.domain.org.OrgDataSearchDTO
 *  com.jiuqi.va.domain.org.OrgDataSortDTO
 *  com.jiuqi.va.mapper.domain.PageDTO
 */
package com.jiuqi.budget.masterdata.organization;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.budget.common.consts.CommonConst;
import com.jiuqi.budget.masterdata.basedata.QueryDataType;
import com.jiuqi.budget.masterdata.basedata.RangeType;
import com.jiuqi.budget.masterdata.intf.AuthType;
import com.jiuqi.budget.masterdata.organization.OrgDataObj;
import com.jiuqi.va.domain.org.OrgDataSearchDTO;
import com.jiuqi.va.domain.org.OrgDataSortDTO;
import com.jiuqi.va.mapper.domain.PageDTO;
import java.util.List;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class OrgDataObjDTO
extends OrgDataObj
implements PageDTO {
    private Integer pageNum;
    private Integer total;
    private Integer limit;
    private Boolean pagination;
    private QueryDataType queryDataType;
    private RangeType rangeType;
    private AuthType authType;
    private List<String> keyList;
    private List<OrgDataSearchDTO> deepSearch;
    List<OrgDataSortDTO> orderBy;

    public List<OrgDataSortDTO> getOrderBy() {
        return this.orderBy;
    }

    public void setOrderBy(List<OrgDataSortDTO> orderBy) {
        this.orderBy = orderBy;
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

    @Override
    public String toString() {
        return "OrgDataObjDTO{pageNum=" + this.pageNum + ", total=" + this.total + ", limit=" + this.limit + ", pagination=" + this.pagination + ", queryDataType=" + (Object)((Object)this.queryDataType) + ", rangeType=" + (Object)((Object)this.rangeType) + ", authType=" + (Object)((Object)this.authType) + ", keyList=" + this.keyList + '}';
    }

    public QueryDataType getQueryDataType() {
        return this.queryDataType;
    }

    public void setQueryDataType(QueryDataType queryDataType) {
        this.queryDataType = queryDataType;
    }

    public RangeType getRangeType() {
        return this.rangeType;
    }

    public void setRangeType(RangeType rangeType) {
        this.rangeType = rangeType;
    }

    public AuthType getAuthType() {
        return this.authType;
    }

    public void setAuthType(AuthType authType) {
        this.authType = authType;
    }

    public Boolean getPagination() {
        return this.pagination;
    }

    public List<String> getKeyList() {
        return this.keyList;
    }

    public void setKeyList(List<String> keyList) {
        this.keyList = keyList;
    }

    public List<OrgDataSearchDTO> getDeepSearch() {
        return this.deepSearch;
    }

    public void setDeepSearch(List<OrgDataSearchDTO> deepSearch) {
        this.deepSearch = deepSearch;
    }
}

